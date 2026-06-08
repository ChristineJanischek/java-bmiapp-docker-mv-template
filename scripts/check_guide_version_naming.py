#!/usr/bin/env python3
"""Prueft die Versions-Namenskonvention fuer Anleitungsdateien.

Regeln:
1) Keine Legacy-Dateinamen im Stil VERSION_<N>_THEMA.md
2) Versionierte Kernanleitungen: SCHRITTE_VERSION_<N>[_THEMA].md
3) Versionierte Vertiefungen: LERNMODUL_VERSION_<N>_<THEMA>.md
4) In versionierten Dateien muss in den ersten Zeilen die passende
   Versionsangabe vorkommen (z. B. "Version 4").
"""

from __future__ import annotations

import re
import sys
from pathlib import Path

LEGACY_PATTERN = re.compile(r"^VERSION_(\d+)_.*\.md$")
SCHRITTE_PATTERN = re.compile(r"^SCHRITTE_VERSION_(\d+)(?:_[A-Z0-9_]+)?\.md$")
LERNMODUL_PATTERN = re.compile(r"^LERNMODUL_VERSION_(\d+)_[A-Z0-9_]+\.md$")


def extract_version(file_name: str) -> int | None:
    for pattern in (SCHRITTE_PATTERN, LERNMODUL_PATTERN):
        match = pattern.match(file_name)
        if match:
            return int(match.group(1))
    return None


def main() -> int:
    repo_root = Path(__file__).resolve().parents[1]
    guides_dir = repo_root / "docs" / "ANLEITUNGEN"

    if not guides_dir.exists():
        print(f"FEHLER: Verzeichnis nicht gefunden: {guides_dir}")
        return 2

    legacy_files: list[Path] = []
    content_errors: list[str] = []
    checked_files: list[Path] = []

    for file_path in sorted(guides_dir.glob("*.md")):
        name = file_path.name

        if LEGACY_PATTERN.match(name):
            legacy_files.append(file_path)

        version = extract_version(name)
        if version is None:
            continue

        checked_files.append(file_path)
        lines = file_path.read_text(encoding="utf-8").splitlines()
        header = "\n".join(lines[:80])

        expected = re.compile(rf"\bVersion\s*{version}\b", re.IGNORECASE)
        if not expected.search(header):
            content_errors.append(
                f"{file_path}: erwartete Versionsangabe 'Version {version}' in den ersten 80 Zeilen nicht gefunden"
            )

    if legacy_files:
        print("FEHLER: Legacy-Dateinamen gefunden. Bitte auf neue Konvention umstellen:")
        for path in legacy_files:
            print(f"  - {path}")

    if content_errors:
        print("FEHLER: Inhaltliche Versionsangabe passt nicht zum Dateinamen:")
        for error in content_errors:
            print(f"  - {error}")

    if legacy_files or content_errors:
        print("\nPruefung fehlgeschlagen.")
        return 1

    print("OK: Versions-Namenskonvention eingehalten.")
    print(f"Gepruefte versionierte Dateien: {len(checked_files)}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
