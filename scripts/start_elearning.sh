#!/usr/bin/env bash
# ---------------------------------------------------------------------------
# start_elearning.sh – Startet E-Learning Backend + Frontend fuer Codespace
# Verwendung: ./scripts/start_elearning.sh [--backend-only | --frontend-only]
# ---------------------------------------------------------------------------
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BACKEND_DIR="$REPO_ROOT/elearning-modern/backend"
FRONTEND_DIR="$REPO_ROOT/elearning-modern/frontend"
BACKEND_PID_FILE="/tmp/elearning_backend.pid"

# Java 21 explizit setzen (im Codespace ist default Java 11)
export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64"
export PATH="$JAVA_HOME/bin:$PATH"

MODE="${1:-both}"

cleanup() {
  echo ""
  echo "Beende E-Learning Server..."
  if [[ -f "$BACKEND_PID_FILE" ]]; then
    kill "$(cat "$BACKEND_PID_FILE")" 2>/dev/null || true
    rm -f "$BACKEND_PID_FILE"
  fi
  exit 0
}
trap cleanup INT TERM

start_backend() {
  echo "==> Backend starten (Spring Boot auf Port 8080)..."
  echo "    JAVA_HOME: $JAVA_HOME"
  cd "$BACKEND_DIR"
  mvn -q spring-boot:run -Dspring-boot.run.jvmArguments="-Dlogging.level.root=WARN" &
  echo $! > "$BACKEND_PID_FILE"
  echo "    Backend-PID: $(cat "$BACKEND_PID_FILE")"
  echo "    API erreichbar unter: http://localhost:8080/api/v1/modules"
}

start_frontend() {
  echo "==> Frontend starten (Vite Dev-Server auf Port 5173)..."
  cd "$FRONTEND_DIR"
  if [[ ! -d node_modules ]]; then
    echo "    npm install laeuft..."
    npm install --silent
  fi
  npm run dev
}

echo ""
echo "  E-Learning Live-Test"
echo "  ====================="
echo ""

case "$MODE" in
  --backend-only)
    start_backend
    wait
    ;;
  --frontend-only)
    start_frontend
    ;;
  *)
    start_backend
    sleep 5  # Backend Zeit zum Starten geben
    start_frontend  # blockiert im Vordergrund (Vite Dev-Server)
    ;;
esac
