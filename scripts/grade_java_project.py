#!/usr/bin/env python3
from __future__ import annotations

import argparse
from pathlib import Path
import tempfile

from grader.analyzer import evaluate_rules
from grader.archive import extract_zip_secure
from grader.models import GradingOutcome, RunConfig
from grader.profile_loader import load_profile
from grader.report_docx import write_report_docx
from grader.report_text import write_markdown_and_html_report
from grader.runtime import ensure_runtime_requirements
from grader.scoring import calculate_linear_grade, calculate_points


def parse_args() -> RunConfig:
    parser = argparse.ArgumentParser(
        description=(
            "Analysiert ein hochgeladenes Java-Projekt (ZIP mit src) "
            "und erzeugt eine ausgefuellte DOCX der Korrekturhilfe."
        )
    )
    parser.add_argument("--zip", required=True, help="Pfad zur ZIP-Datei (Schuelerprojekt)")
    parser.add_argument("--profile", required=True, help="Pfad zum Korrekturhilfe-Profil (JSON)")
    parser.add_argument("--rubric-docx", required=True, help="Pfad zur DOCX-Vorlage der Korrekturhilfe")
    parser.add_argument("--out", required=True, help="Ausgabepfad fuer die DOCX-Ausgabe der Korrekturhilfe")
    parser.add_argument("--out-md", default=None, help="Optionaler Ausgabepfad fuer Markdown")
    parser.add_argument("--out-html", default=None, help="Optionaler Ausgabepfad fuer HTML")
    parser.add_argument("--student", required=True, help="Name des Schuelers/der Schuelerin")
    parser.add_argument("--teacher-note", default=None, help="Optionale Freitext-Anmerkung")

    args = parser.parse_args()

    output_docx_path = Path(args.out).resolve()
    output_md_path = Path(args.out_md).resolve() if args.out_md else output_docx_path.with_suffix(".md")
    output_html_path = Path(args.out_html).resolve() if args.out_html else output_docx_path.with_suffix(".html")

    return RunConfig(
        zip_path=Path(args.zip).resolve(),
        profile_path=Path(args.profile).resolve(),
        rubric_docx_path=Path(args.rubric_docx).resolve(),
        output_docx_path=output_docx_path,
        output_md_path=output_md_path,
        output_html_path=output_html_path,
        student_name=args.student,
        teacher_note=args.teacher_note,
    )


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


def main() -> int:
    ensure_runtime_requirements()
    cfg = parse_args()

    profile = load_profile(cfg.profile_path)

    with tempfile.TemporaryDirectory(prefix="java_grade_") as tmp_dir_name:
        tmp_dir = Path(tmp_dir_name)
        extract_zip_secure(cfg.zip_path, tmp_dir)
        source_root = _detect_source_root(tmp_dir, profile.expected_root)

        results = evaluate_rules(source_root, profile.rules)
        total_points, max_points = calculate_points(results)
        grade = calculate_linear_grade(total_points, max_points, profile.grade_scale)

        outcome = GradingOutcome(
            profile=profile,
            zip_path=cfg.zip_path,
            source_root=source_root,
            results=results,
            total_points=total_points,
            max_points=max_points,
            grade=grade,
        )

        cfg.output_docx_path.parent.mkdir(parents=True, exist_ok=True)
        cfg.output_md_path.parent.mkdir(parents=True, exist_ok=True)
        cfg.output_html_path.parent.mkdir(parents=True, exist_ok=True)
        write_report_docx(
            source_docx_path=cfg.rubric_docx_path,
            output_docx_path=cfg.output_docx_path,
            outcome=outcome,
            student_name=cfg.student_name,
            teacher_note=cfg.teacher_note,
        )
        write_markdown_and_html_report(
            outcome=outcome,
            student_name=cfg.student_name,
            teacher_note=cfg.teacher_note,
            markdown_output_path=cfg.output_md_path,
            html_output_path=cfg.output_html_path,
        )

    print("Korrekturhilfe-Ausgabe erstellt")
    print(f"Schueler/in: {cfg.student_name}")
    print(f"Punkte: {total_points:.2f}/{max_points:.2f}")
    print(f"Note (linear): {grade}")
    print(f"Ausgabe DOCX: {cfg.output_docx_path}")
    print(f"Ausgabe MD: {cfg.output_md_path}")
    print(f"Ausgabe HTML: {cfg.output_html_path}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
