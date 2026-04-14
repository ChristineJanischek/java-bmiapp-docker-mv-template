#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOG_FILE="$ROOT_DIR/docs/ELEARNING_MODERNISIERUNG/MIGRATIONS_PROTOKOLL.md"

MILESTONE=""
STEP=""
STATUS=""
ARTIFACT=""
NOTE=""

usage() {
  cat <<EOF
Usage:
  ./scripts/log_elearning_step.sh --milestone <M#> --step <text> --status <done|in-progress|blocked> [--artifact <path-or-id>] [--note <text>]

Example:
  ./scripts/log_elearning_step.sh \
    --milestone M2 \
    --step "Lesson-UI verbessert" \
    --status in-progress \
    --artifact "frontend/src/pages/LessonPage.tsx" \
    --note "Mobile Header refactored"
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --milestone)
      MILESTONE="${2:-}"
      shift 2
      ;;
    --step)
      STEP="${2:-}"
      shift 2
      ;;
    --status)
      STATUS="${2:-}"
      shift 2
      ;;
    --artifact)
      ARTIFACT="${2:-}"
      shift 2
      ;;
    --note)
      NOTE="${2:-}"
      shift 2
      ;;
    -h|--help)
      usage
      exit 0
      ;;
    *)
      echo "Unbekannter Parameter: $1" >&2
      usage
      exit 1
      ;;
  esac
done

if [[ -z "$MILESTONE" || -z "$STEP" || -z "$STATUS" ]]; then
  echo "Fehler: --milestone, --step und --status sind Pflichtfelder." >&2
  usage
  exit 1
fi

if [[ "$STATUS" != "done" && "$STATUS" != "in-progress" && "$STATUS" != "blocked" ]]; then
  echo "Fehler: --status muss einer der Werte done, in-progress oder blocked sein." >&2
  exit 1
fi

TIMESTAMP="$(date -u +"%Y-%m-%dT%H:%M:%SZ")"

if [[ ! -f "$LOG_FILE" ]]; then
  echo "Fehler: Protokolldatei nicht gefunden: $LOG_FILE" >&2
  exit 1
fi

{
  echo
  echo "- Zeitpunkt: $TIMESTAMP"
  echo "- Meilenstein: $MILESTONE"
  echo "- Schritt: $STEP"
  echo "- Status: $STATUS"
  if [[ -n "$ARTIFACT" ]]; then
    echo "- Artefakt(e): $ARTIFACT"
  else
    echo "- Artefakt(e): -"
  fi
  if [[ -n "$NOTE" ]]; then
    echo "- Notiz: $NOTE"
  else
    echo "- Notiz: -"
  fi
} >> "$LOG_FILE"

echo "Eintrag geschrieben: $LOG_FILE"
