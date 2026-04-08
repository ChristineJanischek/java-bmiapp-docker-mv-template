#!/usr/bin/env python3
"""Export markdown tests to Word-friendly HTML files.

Usage:
  python scripts/export_tests_to_word_html.py

Input:
  materials/tests/**/*.md

Output:
  materials/tests_word_html/**/*.html
"""

from __future__ import annotations

import importlib
from pathlib import Path
import sys


WORKSPACE_ROOT = Path(__file__).resolve().parents[1]
INPUT_ROOT = WORKSPACE_ROOT / "materials" / "tests"
OUTPUT_ROOT = WORKSPACE_ROOT / "materials" / "tests_word_html"

HTML_TEMPLATE = """<!doctype html>
<html lang=\"de\">
<head>
  <meta charset=\"utf-8\">
  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">
  <title>{title}</title>
  <style>
    body {{
      margin: 2.2cm;
      font-family: Calibri, "Segoe UI", Arial, sans-serif;
      font-size: 11pt;
      line-height: 1.35;
      color: #111;
      background: #fff;
    }}
    h1, h2, h3, h4 {{
      margin-top: 1.1em;
      margin-bottom: 0.45em;
      line-height: 1.2;
      page-break-after: avoid;
    }}
    h1 {{ font-size: 19pt; }}
    h2 {{ font-size: 15pt; }}
    h3 {{ font-size: 13pt; }}
    p {{ margin: 0.45em 0; }}
    ul, ol {{ margin: 0.4em 0 0.7em 1.2em; }}
    li {{ margin: 0.2em 0; }}
    pre {{
      white-space: pre-wrap;
      word-break: break-word;
      font-family: Consolas, "Courier New", monospace;
      font-size: 10pt;
      border: 1px solid #d8dde6;
      border-radius: 6px;
      padding: 10px;
      background: #f8fafc;
      margin: 0.7em 0;
    }}
    code {{
      font-family: Consolas, "Courier New", monospace;
      font-size: 10pt;
    }}
    table {{
      border-collapse: collapse;
      width: 100%;
      margin: 0.8em 0;
      font-size: 10.5pt;
    }}
    th, td {{
      border: 1px solid #bfc7d5;
      padding: 6px 8px;
      vertical-align: top;
    }}
    th {{
      background: #eef3fb;
      text-align: left;
    }}
    hr {{
      border: 0;
      border-top: 1px solid #c6ccd6;
      margin: 1.1em 0;
    }}
    blockquote {{
      margin: 0.8em 0;
      padding: 0.2em 0.8em;
      border-left: 3px solid #b9c4d8;
      color: #334155;
      background: #f8fafc;
    }}
    img {{ max-width: 100%; height: auto; }}
  </style>
</head>
<body>
{content}
</body>
</html>
"""


def markdown_to_html(markdown_text: str) -> str:
  try:
    markdown_module = importlib.import_module("markdown")
  except ImportError:
    print(
      "Fehlendes Python-Paket: markdown\n"
      "Bitte zuerst installieren, z.B.:\n"
      "  pip install -r requirements.txt"
    )
    raise SystemExit(1)

  return markdown_module.markdown(
        markdown_text,
        extensions=[
            "fenced_code",
      "codehilite",
            "tables",
            "sane_lists",
            "nl2br",
            "attr_list",
        ],
    extension_configs={
      "codehilite": {
        "guess_lang": False,
        "pygments_style": "friendly",
        "noclasses": True,
      }
    },
        output_format="html5",
    )


def export_file(md_file: Path) -> Path:
    rel = md_file.relative_to(INPUT_ROOT)
    out_file = (OUTPUT_ROOT / rel).with_suffix(".html")
    out_file.parent.mkdir(parents=True, exist_ok=True)

    markdown_text = md_file.read_text(encoding="utf-8")
    html_fragment = markdown_to_html(markdown_text)
    html_full = HTML_TEMPLATE.format(title=rel.stem, content=html_fragment)
    out_file.write_text(html_full, encoding="utf-8")
    return out_file


def main() -> int:
    if not INPUT_ROOT.exists():
        print(f"Eingabeordner nicht gefunden: {INPUT_ROOT}")
        return 1

    md_files = sorted(INPUT_ROOT.rglob("*.md"))
    if not md_files:
        print("Keine Markdown-Dateien gefunden.")
        return 0

    exported = [export_file(md_file) for md_file in md_files]

    print(f"Export abgeschlossen: {len(exported)} Dateien")
    print(f"Ausgabeordner: {OUTPUT_ROOT}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
