from __future__ import annotations

from datetime import datetime
from html import escape

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


def build_markdown_report(
    outcome: GradingOutcome,
    student_name: str,
    teacher_note: str | None,
) -> str:
    lines: list[str] = []
    lines.append("# Bewertungsbogen Projekt OOP")
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

    lines.append("## Bewertungsraster")
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

    lines.append("## Bemerkung")
    lines.append("")
    lines.append(teacher_note or "Keine zusaetzliche Bemerkung.")
    lines.append("")

    return "\n".join(lines)


def markdown_to_html(markdown_text: str, title: str) -> str:
    try:
        import markdown as markdown_module
    except ImportError:
        # Fallback, falls Markdown-Paket in einer Umgebung fehlt.
        escaped = escape(markdown_text).replace("\n", "<br>\n")
        html_body = f"<pre>{escaped}</pre>"
    else:
        html_body = markdown_module.markdown(
            markdown_text,
            extensions=["tables", "fenced_code", "sane_lists", "nl2br"],
            output_format="html5",
        )

    return f"""<!doctype html>
<html lang=\"de\">
<head>
  <meta charset=\"utf-8\">
  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">
  <title>{escape(title)}</title>
  <style>
    body {{
      margin: 2cm;
      font-family: Calibri, 'Segoe UI', Arial, sans-serif;
      font-size: 11pt;
      color: #111;
      line-height: 1.35;
      background: #fff;
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
    }}
    th, td {{
      border: 1px solid #9ca9ba;
      padding: 6px 8px;
      vertical-align: top;
    }}
    th {{
      background: #e8eef7;
      text-align: left;
    }}
    ul {{ margin-top: 0.3em; }}
    hr {{ border: 0; border-top: 1px solid #c8d0dd; margin: 1.1em 0; }}
    @media print {{
      body {{ margin: 1.4cm; }}
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


def write_markdown_and_html_report(
    outcome: GradingOutcome,
    student_name: str,
    teacher_note: str | None,
    markdown_output_path,
    html_output_path,
) -> tuple[str, str]:
    markdown_text = build_markdown_report(
        outcome=outcome,
        student_name=student_name,
        teacher_note=teacher_note,
    )
    html_text = markdown_to_html(markdown_text, "Bewertungsbogen Projekt OOP")

    markdown_output_path.write_text(markdown_text, encoding="utf-8")
    html_output_path.write_text(html_text, encoding="utf-8")

    return str(markdown_output_path), str(html_output_path)
