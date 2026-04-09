#!/usr/bin/env python3
from __future__ import annotations

import argparse
import csv
from datetime import datetime
from pathlib import Path
import tempfile

from grader.analyzer import evaluate_rules
from grader.archive import extract_zip_secure
from grader.models import GradingOutcome
from grader.profile_loader import load_profile
from grader.report_docx import write_report_docx
from grader.report_text import write_markdown_and_html_report
from grader.scoring import calculate_linear_grade, calculate_points


def _detect_source_root(extract_dir: Path, expected_root: str) -> Path:
    direct = extract_dir / expected_root
    if direct.exists() and direct.is_dir():
        return direct

    for candidate in extract_dir.rglob(expected_root):
        if candidate.is_dir():
            return candidate

    java_files = list(extract_dir.rglob("*.java"))
    if java_files:
        return extract_dir

    raise FileNotFoundError(f"Kein Quellcode-Verzeichnis '{expected_root}' in ZIP gefunden")


def _student_name_from_zip(zip_path: Path) -> str:
    name = zip_path.stem
    # Remove optional archive timestamp prefix: YYYYMMDD_HHMMSS_
    parts = name.split("_", 2)
    if len(parts) == 3 and len(parts[0]) == 8 and len(parts[1]) == 6 and parts[0].isdigit() and parts[1].isdigit():
        name = parts[2]
    for prefix in ("schueler_", "projekt_", "abgabe_"):
        if name.lower().startswith(prefix):
            name = name[len(prefix):]
            break
    return name.replace("_", " ").strip() or zip_path.stem


def _grade_single(zip_path: Path, profile, rubric_docx: Path, out_dir: Path, teacher_note: str | None) -> dict:
    with tempfile.TemporaryDirectory(prefix="batch_grade_") as tmp_dir_name:
        tmp_dir = Path(tmp_dir_name)
        extract_zip_secure(zip_path, tmp_dir)
        source_root = _detect_source_root(tmp_dir, profile.expected_root)

        results = evaluate_rules(source_root, profile.rules)
        total_points, max_points = calculate_points(results)
        grade = calculate_linear_grade(total_points, max_points, profile.grade_scale)

        student_name = _student_name_from_zip(zip_path)
        safe_name = student_name.lower().replace(" ", "_")
        out_docx = out_dir / f"bewertung_{safe_name}.docx"
        out_md = out_dir / f"bewertung_{safe_name}.md"
        out_html = out_dir / f"bewertung_{safe_name}.html"

        outcome = GradingOutcome(
            profile=profile,
            zip_path=zip_path,
            source_root=source_root,
            results=results,
            total_points=total_points,
            max_points=max_points,
            grade=grade,
        )

        write_report_docx(
            source_docx_path=rubric_docx,
            output_docx_path=out_docx,
            outcome=outcome,
            student_name=student_name,
            teacher_note=teacher_note,
        )
        write_markdown_and_html_report(
            outcome=outcome,
            student_name=student_name,
            teacher_note=teacher_note,
            markdown_output_path=out_md,
            html_output_path=out_html,
        )

    return {
        "student": student_name,
        "zip": str(zip_path),
        "points": f"{total_points:.2f}/{max_points:.2f}",
        "grade": f"{grade:.2f}",
        "docx": str(out_docx),
        "md": str(out_md),
        "html": str(out_html),
        "status": "ok",
    }


def _write_summary(out_dir: Path, rows: list[dict]) -> None:
    csv_path = out_dir / "bewertung_uebersicht.csv"
    with csv_path.open("w", newline="", encoding="utf-8") as fp:
        writer = csv.DictWriter(
            fp,
            fieldnames=["student", "zip", "points", "grade", "status", "docx", "md", "html"],
        )
        writer.writeheader()
        writer.writerows(rows)

    md_lines = ["# Bewertungsuebersicht", "", "| Schueler/in | Punkte | Note | Status |", "|---|---:|---:|---|"]
    for row in rows:
        md_lines.append(f"| {row['student']} | {row['points']} | {row['grade']} | {row['status']} |")
    (out_dir / "bewertung_uebersicht.md").write_text("\n".join(md_lines) + "\n", encoding="utf-8")


def _write_ranking(out_dir: Path, rows: list[dict]) -> tuple[Path, Path]:
    ranked: list[dict] = []
    for row in rows:
        if row["status"] != "ok":
            continue
        try:
            grade_value = float(row["grade"])
            achieved, maximum = row["points"].split("/")
            achieved_value = float(achieved)
            max_value = float(maximum)
            ratio = (achieved_value / max_value) if max_value > 0 else 0.0
        except (ValueError, ZeroDivisionError):
            continue
        ranked.append(
            {
                **row,
                "grade_value": grade_value,
                "ratio": ratio,
                "achieved_value": achieved_value,
            }
        )

    ranked.sort(key=lambda item: (item["grade_value"], -item["achieved_value"], item["student"].lower()))

    ranking_csv = out_dir / "bewertung_rangliste.csv"
    with ranking_csv.open("w", newline="", encoding="utf-8") as fp:
        writer = csv.DictWriter(
            fp,
            fieldnames=["rang", "student", "points", "grade", "punkte_prozent", "status"],
        )
        writer.writeheader()
        for idx, row in enumerate(ranked, start=1):
            writer.writerow(
                {
                    "rang": idx,
                    "student": row["student"],
                    "points": row["points"],
                    "grade": row["grade"],
                    "punkte_prozent": f"{row['ratio'] * 100.0:.2f}",
                    "status": row["status"],
                }
            )

    ranking_md = out_dir / "bewertung_rangliste.md"
    lines = [
        "# Bewertungs-Rangliste",
        "",
        "| Rang | Schueler/in | Punkte | Note | Punktequote |",
        "|---:|---|---:|---:|---:|",
    ]
    for idx, row in enumerate(ranked, start=1):
        lines.append(
            f"| {idx} | {row['student']} | {row['points']} | {row['grade']} | {row['ratio'] * 100.0:.2f}% |"
        )
    if not ranked:
        lines.append("| - | Keine erfolgreichen Bewertungen | - | - | - |")
    ranking_md.write_text("\n".join(lines) + "\n", encoding="utf-8")

    return ranking_csv, ranking_md


def _write_run_statistics(out_dir: Path, rows: list[dict]) -> tuple[Path, Path]:
    timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    total = len(rows)
    success_rows = [row for row in rows if row["status"] == "ok"]
    success_count = len(success_rows)
    error_count = total - success_count

    grades: list[float] = []
    point_ratios: list[float] = []
    for row in success_rows:
        try:
            grades.append(float(row["grade"]))
            achieved, maximum = row["points"].split("/")
            max_value = float(maximum)
            if max_value > 0:
                point_ratios.append(float(achieved) / max_value)
        except (ValueError, ZeroDivisionError):
            continue

    avg_grade = sum(grades) / len(grades) if grades else 0.0
    best_grade = min(grades) if grades else 0.0
    worst_grade = max(grades) if grades else 0.0
    avg_points_percent = (sum(point_ratios) / len(point_ratios) * 100.0) if point_ratios else 0.0

    history_csv = out_dir / "bewertung_laufhistorie.csv"
    file_exists = history_csv.exists()
    with history_csv.open("a", newline="", encoding="utf-8") as fp:
        writer = csv.DictWriter(
            fp,
            fieldnames=[
                "timestamp",
                "gesamt",
                "erfolgreich",
                "fehler",
                "durchschnitt_note",
                "beste_note",
                "schlechteste_note",
                "durchschnitt_punkte_prozent",
            ],
        )
        if not file_exists:
            writer.writeheader()
        writer.writerow(
            {
                "timestamp": timestamp,
                "gesamt": total,
                "erfolgreich": success_count,
                "fehler": error_count,
                "durchschnitt_note": f"{avg_grade:.2f}",
                "beste_note": f"{best_grade:.2f}",
                "schlechteste_note": f"{worst_grade:.2f}",
                "durchschnitt_punkte_prozent": f"{avg_points_percent:.2f}",
            }
        )

    run_md = out_dir / "bewertung_laufbericht.md"
    lines = [
        "# Bewertungs-Laufbericht",
        "",
        f"- Zeitpunkt: {timestamp}",
        f"- Gesamtanzahl Projekte: {total}",
        f"- Erfolgreich bewertet: {success_count}",
        f"- Fehlerhaft: {error_count}",
        f"- Durchschnittsnote: {avg_grade:.2f}",
        f"- Beste Note: {best_grade:.2f}",
        f"- Schlechteste Note: {worst_grade:.2f}",
        f"- Durchschnittliche Punktequote: {avg_points_percent:.2f}%",
        "",
        "## Hinweise",
        "",
        "- Diese Kennzahlen beziehen sich nur auf erfolgreich bewertete Projekte.",
        "- Die komplette Historie steht in bewertung_laufhistorie.csv.",
    ]
    run_md.write_text("\n".join(lines) + "\n", encoding="utf-8")

    return history_csv, run_md


def main() -> int:
    parser = argparse.ArgumentParser(description="Batch-Bewertung fuer mehrere Schuelerprojekte (ZIP).")
    parser.add_argument("--zip-dir", required=True, help="Ordner mit ZIP-Dateien")
    parser.add_argument("--profile", required=True, help="Pfad zum Bewertungsprofil (JSON)")
    parser.add_argument("--rubric-docx", required=True, help="Pfad zum DOCX-Bewertungsbogen")
    parser.add_argument("--out-dir", required=True, help="Ausgabeordner")
    parser.add_argument("--teacher-note", default=None, help="Optionale Bemerkung fuer alle Berichte")
    args = parser.parse_args()

    zip_dir = Path(args.zip_dir).resolve()
    out_dir = Path(args.out_dir).resolve()
    out_dir.mkdir(parents=True, exist_ok=True)

    profile = load_profile(Path(args.profile).resolve())
    rubric_docx = Path(args.rubric_docx).resolve()

    zip_files = sorted(zip_dir.glob("*.zip"))
    if not zip_files:
        print(f"Keine ZIP-Dateien gefunden in: {zip_dir}")
        return 1

    rows: list[dict] = []
    for zip_path in zip_files:
        try:
            row = _grade_single(zip_path, profile, rubric_docx, out_dir, args.teacher_note)
        except Exception as exc:
            row = {
                "student": _student_name_from_zip(zip_path),
                "zip": str(zip_path),
                "points": "-",
                "grade": "-",
                "docx": "-",
                "md": "-",
                "html": "-",
                "status": f"error: {exc}",
            }
        rows.append(row)

    _write_summary(out_dir, rows)
    ranking_csv, ranking_md = _write_ranking(out_dir, rows)
    history_csv, run_md = _write_run_statistics(out_dir, rows)

    ok_count = sum(1 for row in rows if row["status"] == "ok")
    print(f"Batch abgeschlossen: {ok_count}/{len(rows)} erfolgreich")
    print(f"Ausgabeordner: {out_dir}")
    print(f"Uebersicht: {out_dir / 'bewertung_uebersicht.csv'}")
    print(f"Rangliste: {ranking_csv}")
    print(f"Rangliste (MD): {ranking_md}")
    print(f"Laufhistorie: {history_csv}")
    print(f"Laufbericht: {run_md}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
