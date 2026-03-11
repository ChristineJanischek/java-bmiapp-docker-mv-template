#!/usr/bin/env bash
set -euo pipefail

# Idempotent setup for branch protection rules.
# Requires GitHub token with repo admin permissions.
# Usage:
#   export GITHUB_TOKEN=<token-with-admin-repo-scope>
#   ./scripts/setup_branch_protection.sh [owner] [repo]

OWNER="${1:-ChristineJanischek}"
REPO="${2:-java-bmiapp-docker-mv-template}"

if ! command -v gh >/dev/null 2>&1; then
  echo "Fehler: gh CLI ist nicht installiert." >&2
  exit 1
fi

if ! gh auth status >/dev/null 2>&1; then
  echo "Fehler: gh auth status ist nicht erfolgreich. Bitte zuerst anmelden." >&2
  exit 1
fi

branches=(
  "main"
  "feat/testsdev"
  "chore/todos-next-block"
)

for branch in "${branches[@]}"; do
  echo "Setze Branch-Protection fuer ${branch}..."
  gh api \
    --method PUT \
    -H "Accept: application/vnd.github+json" \
    "/repos/${OWNER}/${REPO}/branches/${branch}/protection" \
    --input - <<'JSON'
{
  "required_status_checks": null,
  "enforce_admins": true,
  "required_pull_request_reviews": {
    "dismiss_stale_reviews": true,
    "require_code_owner_reviews": true,
    "required_approving_review_count": 1,
    "require_last_push_approval": true
  },
  "restrictions": null,
  "required_linear_history": true,
  "allow_force_pushes": false,
  "allow_deletions": false,
  "block_creations": false,
  "required_conversation_resolution": true,
  "lock_branch": false,
  "allow_fork_syncing": false
}
JSON
  echo "OK: ${branch}"
  echo
 done

echo "Branch-Protection Setup abgeschlossen fuer ${OWNER}/${REPO}."
