#!/usr/bin/env bash
set -euo pipefail

# Env
export DISPLAY=${DISPLAY:-:1}
SCREEN_RES=${SCREEN_RES:-1280x800x24}

echo "Starting Xvfb on ${DISPLAY} with resolution ${SCREEN_RES}..."
# Start Xvfb
Xvfb ${DISPLAY} -screen 0 ${SCREEN_RES} -ac +extension GLX +render -noreset &
XVFB_PID=$!

# Kleiner Wait, bis Xvfb bereit ist
sleep 2

echo "Starting Fluxbox window manager..."
# Window Manager (Fluxbox) für schöne Fensterleisten
fluxbox &
sleep 1

echo "Starting x11vnc on port 5900..."
# x11vnc starten (ohne Passwort, nur Demo)
# -forever: bleibt nach Disconnect aktiv
# -shared: mehrere Clients erlaubt
# -nopw: kein Passwort (nur für Demo!)
# -create: fallback, falls DISPLAY nicht existiert
x11vnc -display ${DISPLAY} -forever -shared -nopw -rfbport 5900 -bg -noxdamage &
sleep 1

echo "Starting websockify/noVNC on port 6080..."
# noVNC + websockify auf :6080
# Wenn verfügbar, liegt websockify in /usr/share/novnc/utils
if [ -x /usr/share/novnc/utils/novnc_proxy ]; then
  /usr/share/novnc/utils/novnc_proxy --vnc localhost:5900 --listen 0.0.0.0:6080 &
else
  websockify --web=/usr/share/novnc 0.0.0.0:6080 localhost:5900 &
fi

# Noch kurz warten, dann App starten
sleep 2

echo "Starting Java GUI application (MainWindow)..."
# Java-App (GUI)
cd /app
java -cp build:lib/* start.MainWindow &
JAVA_PID=$!

echo "All services started. Java PID: ${JAVA_PID}, Xvfb PID: ${XVFB_PID}"
echo "Access GUI via http://localhost:6080/vnc.html"

# Warten auf Java-Prozess (damit Container nicht stoppt)
wait ${JAVA_PID}
