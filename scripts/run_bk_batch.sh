#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
ZIP_DIR="${1:-$ROOT_DIR/downloads/eingang}"
RUBRIC_DOCX="${2:-$ROOT_DIR/downloads/boegen/BK_Korrekturhilfe_Projekte_OOP_2026.docx}"
PROFILE_JSON="${3:-$ROOT_DIR/scripts/grading_profiles/bk_oop_2026_rubrik24.json}"
OUT_DIR="${4:-$ROOT_DIR/downloads/batch_korrekturhilfen}"
ARCHIVE_DIR="${5:-$ROOT_DIR/downloads/archiv}"
TEACHER_NOTE="${6:-Automatische Korrekturhilfe, final manuell pruefen.}"

mkdir -p "$ZIP_DIR" "$OUT_DIR" "$ARCHIVE_DIR"

if [[ ! -f "$RUBRIC_DOCX" ]]; then
  echo "Fehler: Korrekturhilfe-DOCX nicht gefunden: $RUBRIC_DOCX"
  echo "Lege die Datei dort ab oder uebergib einen Pfad als 2. Parameter."
  exit 1
fi

ZIP_COUNT=$(find "$ZIP_DIR" -maxdepth 1 -type f -name '*.zip' | wc -l)
if [[ "$ZIP_COUNT" -eq 0 ]]; then
  echo "Keine ZIP-Dateien in $ZIP_DIR gefunden."
  echo "Lege Schueler-ZIPs in den Eingang und starte erneut."
  exit 1
fi

python "$ROOT_DIR/scripts/batch_grade_projects.py" \
  --zip-dir "$ZIP_DIR" \
  --profile "$PROFILE_JSON" \
  --rubric-docx "$RUBRIC_DOCX" \
  --out-dir "$OUT_DIR" \
  --teacher-note "$TEACHER_NOTE"

TS="$(date +%Y%m%d_%H%M%S)"
for zip_file in "$ZIP_DIR"/*.zip; do
  [[ -e "$zip_file" ]] || continue
  base_name="$(basename "$zip_file")"
  mv "$zip_file" "$ARCHIVE_DIR/${TS}_${base_name}"
done

echo ""
echo "Fertig."
echo "Ausgaben: $OUT_DIR"
echo "Archivierte ZIPs: $ARCHIVE_DIR"
