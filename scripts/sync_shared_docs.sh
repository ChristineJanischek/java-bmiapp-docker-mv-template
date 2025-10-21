#!/usr/bin/env bash
set -euo pipefail

# sync_shared_docs.sh
#
# Zweck:
#   Synchronisiert ausgewählte, geteilte Dateien aus dem Template-Repo
#   in eine Liste von Ziel-Repositories (gleiche Struktur) und eröffnet
#   automatisch Pull Requests via GitHub CLI (gh).
#
# Voraussetzungen:
#   - gh (GitHub CLI) installiert und via `gh auth login` angemeldet
#   - git installiert
#   - Zugriff/Push-Berechtigung auf Ziel-Repositories
#
# Nutzung:
#   scripts/sync_shared_docs.sh [OPTIONEN] <ORG/REPO> [<ORG/REPO> ...]
#
#   Optionen:
#     -f <datei>   Datei mit Ziel-Repos (eine Zeile pro Eintrag: ORG/NAME)
#     -n           Dry-Run (zeigt nur an, was passieren würde)
#     -b <branch>  Name des Update-Branches in Ziel-Repos
#     -m <msg>     Commit-/PR-Message
#
# Beispiele:
#   scripts/sync_shared_docs.sh myorg/ProjektA myorg/ProjektB
#   scripts/sync_shared_docs.sh -n -f repos.txt
#   scripts/sync_shared_docs.sh -b sync-shared-$(date +%Y%m%d) myorg/ProjektA

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"/.. && pwd)"
TMP_ROOT="${ROOT_DIR}/build/tmp-sync-$(date +%Y%m%d-%H%M%S)"
mkdir -p "${TMP_ROOT}"

DRY_RUN=false
BRANCH_NAME="sync-shared-docs-$(date +%Y%m%d)"
COMMIT_MSG="chore: Sync shared docs from template"
REPO_FILE=""

# Standard-Dateiliste (kann via Umgebungsvariable überschrieben werden)
DEFAULT_FILES=(
  "README.md"
  "VERSIONING_STRATEGY.md"
  "SECURE_CODING.md"
  "MVC_KONZEPT.md"
  "MVC_ANLEITUNG.md"
  "ASSOZIATIONEN.md"
  "KONTROLLSTRUKTUREN.md"
  "GRUNDGERUEST_KLASSE.md"
  "SINGLE_ENTRY_POINT.md"
  "GUI_DOCKER.md"
  "GUI_BROWSER.md"
  "TEST_GUI.md"
)

IFS=' ' read -r -a FILES <<< "${SYNC_FILES:-${DEFAULT_FILES[*]}}"

usage() {
  echo "Usage: $0 [-n] [-f repo_list.txt] [-b branch_name] [-m message] <ORG/REPO> ..."
}

while getopts ":f:nb:m:" opt; do
  case ${opt} in
    f) REPO_FILE="${OPTARG}" ;;
    n) DRY_RUN=true ;;
    b) BRANCH_NAME="${OPTARG}" ;;
    m) COMMIT_MSG="${OPTARG}" ;;
    *) usage; exit 1 ;;
  esac
done
shift $((OPTIND -1))

CLI_REPOS=("$@")

if ! command -v gh >/dev/null 2>&1; then
  echo "Fehler: 'gh' (GitHub CLI) nicht gefunden. Bitte installieren und 'gh auth login' ausführen." >&2
  exit 1
fi
if ! command -v git >/dev/null 2>&1; then
  echo "Fehler: 'git' nicht gefunden." >&2
  exit 1
fi

TARGET_REPOS=()
if [[ -n "${REPO_FILE}" ]]; then
  if [[ ! -f "${REPO_FILE}" ]]; then
    echo "Fehler: Datei nicht gefunden: ${REPO_FILE}" >&2
    exit 1
  fi
  while IFS= read -r line; do
    [[ -z "${line}" ]] && continue
    [[ "${line}" =~ ^# ]] && continue
    TARGET_REPOS+=("${line}")
  done < "${REPO_FILE}"
fi

TARGET_REPOS+=("${CLI_REPOS[@]}")

if [[ ${#TARGET_REPOS[@]} -eq 0 ]]; then
  usage; exit 1
fi

echo "Synchronisiere Dateien: ${FILES[*]}"
echo "Update-Branch: ${BRANCH_NAME}"
echo "Commit/PR-Message: ${COMMIT_MSG}"
${DRY_RUN} && echo "Modus: DRY-RUN"

SUMMARY=()

for REPO in "${TARGET_REPOS[@]}"; do
  echo "\n=== Verarbeite ${REPO} ==="
  # Standardbranch ermitteln
  DEFAULT_BRANCH=$(gh repo view "${REPO}" --json defaultBranchRef -q .defaultBranchRef.name)
  echo "Default-Branch: ${DEFAULT_BRANCH}"

  WORKDIR="${TMP_ROOT}/$(echo "${REPO}" | tr '/' '_')"
  if [[ -d "${WORKDIR}" ]]; then rm -rf "${WORKDIR}"; fi
  echo "Clone nach: ${WORKDIR}"

  if ${DRY_RUN}; then
    echo "DRY-RUN: Würde clonen und Branch anlegen: ${BRANCH_NAME}"
    continue
  fi

  git clone --quiet "https://github.com/${REPO}.git" "${WORKDIR}"
  pushd "${WORKDIR}" >/dev/null

  # Neuen Branch erstellen (oder neu starten, falls vorhanden)
  if git rev-parse --verify --quiet "${BRANCH_NAME}" >/dev/null; then
    git branch -D "${BRANCH_NAME}" >/dev/null
  fi
  git checkout -b "${BRANCH_NAME}" "origin/${DEFAULT_BRANCH}" >/dev/null

  # Dateien kopieren
  CHANGED=false
  for F in "${FILES[@]}"; do
    SRC="${ROOT_DIR}/${F}"
    DEST="${WORKDIR}/${F}"
    if [[ -e "${SRC}" ]]; then
      mkdir -p "$(dirname "${DEST}")"
      cp -R "${SRC}" "${DEST}"
      git add "${F}" >/dev/null || true
      CHANGED=true
      echo "→ aktualisiert: ${F}"
    else
      echo "(übersprungen, Quelle fehlt): ${F}"
    fi
  done

  if ! ${CHANGED}; then
    echo "Keine Änderungen – überspringe PR."
    popd >/dev/null
    continue
  fi

  git commit -m "${COMMIT_MSG}" >/dev/null || true
  git push -u origin "${BRANCH_NAME}" >/dev/null

  # PR erstellen
  PR_URL=$(gh pr create --title "${COMMIT_MSG}" --body "Automatische Aktualisierung gemeinsamer Dokumente aus Template-Repo." --base "${DEFAULT_BRANCH}")
  echo "PR erstellt: ${PR_URL}"
  SUMMARY+=("${REPO}: ${PR_URL}")

  popd >/dev/null
done

echo "\n==== Zusammenfassung ===="
if [[ ${#SUMMARY[@]} -eq 0 ]]; then
  echo "Keine PRs erstellt."
else
  for item in "${SUMMARY[@]}"; do
    echo "$item"
  done
fi

echo "\nFertig. Temporäre Daten unter: ${TMP_ROOT}"