from __future__ import annotations

from datetime import datetime
from html import escape

from grader.action_plan import generate_personal_action_plan
from grader.models import GradingOutcome


_GROUP_LABELS = {
    "F": "Formales",
    "FU": "Funktionalitaet",
    "D": "Dokumentation",
    "K": "Kapselung",
    "T": "Testumgebung",
    "I": "Individualisierung",
    "R": "Rechner",
    "A": "Automat",
    "O": "OOP",
}

_REPORT_TITLE = "Korrekturhilfe Projekt OOP"


def _group_for_rule(rule_id: str) -> str:
    prefix = ""
    for ch in rule_id:
        if ch.isalpha():
            prefix += ch
        else:
            break
    return _GROUP_LABELS.get(prefix, "Allgemein")


def _status_text(passed: bool) -> str:
    return "OK" if passed else "NICHT ERFUELLT"


def _escape_markdown_table_cell(text: str) -> str:
    escaped = text.replace("|", "\\|")
    escaped = escaped.replace("*", "\\*")
    escaped = escaped.replace("_", "\\_")
    return escaped


def _score_formula_text(total: float, maximum: float, grade: float) -> str:
    return (
        "Lineare Notenformel: note = best + (1 - punkte/maxPunkte) * (worst - best). "
        f"Berechnet mit {total:.2f}/{maximum:.2f} Punkten => Note {grade:.2f}."
    )


def _html_list(items: list[str]) -> str:
    body = "\n".join(f"<li>{escape(item)}</li>" for item in items)
    return f"<ul>\n{body}\n</ul>"


def _build_html_report(
    outcome: GradingOutcome,
    student_name: str,
    teacher_note: str | None,
    title: str,
) -> str:
    grouped: dict[str, list] = {}
    for result in outcome.results:
        grouped.setdefault(_group_for_rule(result.rule.id), []).append(result)

    sections: list[str] = []
    sections.append(f"<h1>{escape(title)}</h1>")
    sections.append(
        _html_list(
            [
                f"Datum: {datetime.now().strftime('%Y-%m-%d %H:%M')}",
                f"Schueler/in: {student_name}",
                f"Profil: {outcome.profile.profile_name}",
                f"Projektdatei: {outcome.zip_path.name}",
            ]
        )
    )

    sections.append("<h2>Gesamtergebnis</h2>")
    sections.append(
        _html_list(
            [
                f"Punkte: {outcome.total_points:.2f}/{outcome.max_points:.2f}",
                f"Note (linear): {outcome.grade:.2f}",
                _score_formula_text(outcome.total_points, outcome.max_points, outcome.grade),
            ]
        )
    )

    sections.append("<h2>Korrekturhilfe-Raster</h2>")
    for group_name, items in grouped.items():
        sections.append(f"<h3>{escape(group_name)}</h3>")
        rows: list[str] = []
        for result in items:
            rows.append(
                "\n".join(
                    [
                        "<tr>",
                        f"<td>{escape(result.rule.title)}</td>",
                        f"<td style=\"text-align: right;\">{result.achieved_points:.2f}/{result.rule.points:.2f}</td>",
                        f"<td>{escape(_status_text(result.passed))}</td>",
                        f"<td>{escape(result.note)}</td>",
                        "</tr>",
                    ]
                )
            )
        sections.append(
            "\n".join(
                [
                    "<table>",
                    "<thead>",
                    "<tr>",
                    "<th>Kriterium</th>",
                    "<th style=\"text-align: right;\">Punkte</th>",
                    "<th>Status</th>",
                    "<th>Anmerkung</th>",
                    "</tr>",
                    "</thead>",
                    "<tbody>",
                    "\n".join(rows),
                    "</tbody>",
                    "</table>",
                ]
            )
        )

    sections.append("<h2>Einzelkriterien</h2>")
    for result in outcome.results:
        sections.append(f"<h3>{escape(result.rule.id)} - {escape(result.rule.title)}</h3>")
        sections.append(
            _html_list(
                [
                    f"Kriterium: {result.rule.description}",
                    f"Punkte: {result.achieved_points:.2f}/{result.rule.points:.2f}",
                    f"Status: {_status_text(result.passed)}",
                    f"Anmerkung: {result.note}",
                ]
            )
        )

    action_plan = generate_personal_action_plan(outcome, student_name)

    sections.append("<h2>Handlungsempfehlung (Juni-Abgabe)</h2>")
    sections.append("<h3>Fokus</h3>")
    sections.append(_html_list([f"[ ] {todo}" for todo in action_plan.focus_todos]))

    sections.append("<h3>2 Individuelle Erweiterungen</h3>")
    sections.append(
        _html_list(
            [
                f"[ ] Erweiterung {idx}: {todo}"
                for idx, todo in enumerate(action_plan.extension_todos, start=1)
            ]
        )
    )

    sections.append("<h3>Marschplan bis Anfang Juni</h3>")
    sections.append(_html_list([f"[ ] {todo}" for todo in action_plan.timeline_todos]))

    sections.append("<h2>Bemerkung</h2>")
    sections.append(f"<p>{escape(teacher_note or 'Keine zusaetzliche Bemerkung.')}</p>")

    html_body = "\n".join(sections)
    return f"""<!doctype html>
<html lang=\"de\">
<head>
  <meta charset=\"utf-8\">
  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">
  <title>{escape(title)}</title>
  <style>
    body {{
            margin: 1.5cm;
      font-family: Calibri, 'Segoe UI', Arial, sans-serif;
      font-size: 11pt;
      color: #111;
      line-height: 1.35;
      background: #fff;
            max-width: 180mm;
    }}
    h1 {{
      font-size: 19pt;
      margin-bottom: 0.5em;
    }}
    h2 {{
      font-size: 14pt;
      margin-top: 1.2em;
      margin-bottom: 0.4em;
      border-bottom: 1px solid #b9c3d1;
      padding-bottom: 4px;
    }}
    h3 {{
      font-size: 12pt;
      margin-top: 1em;
      margin-bottom: 0.3em;
    }}
    table {{
      border-collapse: collapse;
      width: 100%;
      margin: 0.6em 0 1em 0;
      font-size: 10.5pt;
            table-layout: fixed;
    }}
    th, td {{
      border: 1px solid #9ca9ba;
      padding: 6px 8px;
      vertical-align: top;
            overflow-wrap: anywhere;
            word-break: break-word;
            hyphens: auto;
    }}
    th {{
      background: #e8eef7;
      text-align: left;
    }}
        th:nth-child(1), td:nth-child(1) {{ width: 38%; }}
        th:nth-child(2), td:nth-child(2) {{ width: 14%; }}
        th:nth-child(3), td:nth-child(3) {{ width: 14%; }}
        th:nth-child(4), td:nth-child(4) {{ width: 34%; }}
    ul {{ margin-top: 0.3em; }}
    hr {{ border: 0; border-top: 1px solid #c8d0dd; margin: 1.1em 0; }}
    @media print {{
            body {{ margin: 1.2cm; max-width: none; }}
      h1, h2, h3 {{ page-break-after: avoid; }}
      table {{ page-break-inside: auto; }}
      tr {{ page-break-inside: avoid; page-break-after: auto; }}
    }}
  </style>
</head>
<body>
{html_body}
</body>
</html>
"""


def _validate_html_report(html_text: str) -> None:
    # Guardrail: Style-Recovery als reiner PRE-Block soll nie im finalen Output landen.
    if "<pre>" in html_text.lower():
        raise ValueError("HTML-Report enthaelt unerwuenschten <pre>-Fallback")
    required_tokens = ["<h1>", "<h2>", "<table>", "<tbody>"]
    missing = [token for token in required_tokens if token not in html_text.lower()]
    if missing:
        raise ValueError(f"HTML-Report ist unvollstaendig, fehlende Elemente: {', '.join(missing)}")


def build_markdown_report(
    outcome: GradingOutcome,
    student_name: str,
    teacher_note: str | None,
) -> str:
    lines: list[str] = []
    lines.append(f"# {_REPORT_TITLE}")
    lines.append("")
    lines.append(f"- Datum: {datetime.now().strftime('%Y-%m-%d %H:%M')}")
    lines.append(f"- Schueler/in: {student_name}")
    lines.append(f"- Profil: {outcome.profile.profile_name}")
    lines.append(f"- Projektdatei: {outcome.zip_path.name}")
    lines.append("")
    lines.append("## Gesamtergebnis")
    lines.append("")
    lines.append(f"- Punkte: {outcome.total_points:.2f}/{outcome.max_points:.2f}")
    lines.append(f"- Note (linear): {outcome.grade:.2f}")
    lines.append(f"- {_score_formula_text(outcome.total_points, outcome.max_points, outcome.grade)}")
    lines.append("")

    grouped: dict[str, list] = {}
    for result in outcome.results:
        grouped.setdefault(_group_for_rule(result.rule.id), []).append(result)

    lines.append("## Korrekturhilfe-Raster")
    lines.append("")
    for group_name, items in grouped.items():
        lines.append(f"### {group_name}")
        lines.append("")
        lines.append("| Kriterium | Punkte | Status | Anmerkung |")
        lines.append("|---|---:|---|---|")
        for result in items:
            points = f"{result.achieved_points:.2f}/{result.rule.points:.2f}"
            note = _escape_markdown_table_cell(result.note)
            title = _escape_markdown_table_cell(result.rule.title)
            status = _status_text(result.passed)
            lines.append(f"| {title} | {points} | {status} | {note} |")
        lines.append("")

    lines.append("## Einzelkriterien")
    lines.append("")
    for result in outcome.results:
        lines.append(f"### {result.rule.id} - {result.rule.title}")
        lines.append("")
        lines.append(f"- Kriterium: {result.rule.description}")
        lines.append(f"- Punkte: {result.achieved_points:.2f}/{result.rule.points:.2f}")
        lines.append(f"- Status: {_status_text(result.passed)}")
        lines.append(f"- Anmerkung: {result.note}")
        lines.append("")

    action_plan = generate_personal_action_plan(outcome, student_name)

    lines.append("## Handlungsempfehlung (Juni-Abgabe)")
    lines.append("")
    lines.append("### Fokus")
    lines.append("")
    for todo in action_plan.focus_todos:
        lines.append(f"- [ ] {todo}")
    lines.append("")

    lines.append("### 2 Individuelle Erweiterungen")
    lines.append("")
    for idx, todo in enumerate(action_plan.extension_todos, start=1):
        lines.append(f"- [ ] Erweiterung {idx}: {todo}")
    lines.append("")

    lines.append("### Marschplan bis Anfang Juni")
    lines.append("")
    for todo in action_plan.timeline_todos:
        lines.append(f"- [ ] {todo}")
    lines.append("")

    lines.append("## Bemerkung")
    lines.append("")
    lines.append(teacher_note or "Keine zusaetzliche Bemerkung.")
    lines.append("")

    return "\n".join(lines)


def write_markdown_and_html_report(
    outcome: GradingOutcome,
    student_name: str,
    teacher_note: str | None,
    markdown_output_path,
    html_output_path,
) -> tuple[str, str]:
    html_title = _REPORT_TITLE
    markdown_text = build_markdown_report(
        outcome=outcome,
        student_name=student_name,
        teacher_note=teacher_note,
    )
    html_text = _build_html_report(
        outcome=outcome,
        student_name=student_name,
        teacher_note=teacher_note,
        title=html_title,
    )
    _validate_html_report(html_text)

    markdown_output_path.write_text(markdown_text, encoding="utf-8")
    html_output_path.write_text(html_text, encoding="utf-8")

    return str(markdown_output_path), str(html_output_path)
