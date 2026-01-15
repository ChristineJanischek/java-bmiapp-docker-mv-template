# Browserbasierte GUI (noVNC)

Diese Variante startet die Swing-GUI in einem virtuellen X-Server (Xvfb) und stellt sie via VNC + noVNC im Browser bereit.

## Start

```bash
# Bauen und starten
docker compose -f docker-compose.novnc.yml up --build -d

# Logs ansehen
docker compose -f docker-compose.novnc.yml logs -f java-app-novnc
```

Öffne dann im Browser:

- **Lokal:** http://localhost:6080/vnc.html
- **In GitHub Codespaces / VS Code Dev Container:**
  1. Öffne das **PORTS**-Panel (unten in VS Code)
  2. Port 6080 sollte automatisch erkannt werden
  3. Klicke auf das **Globe-Symbol** (🌐) neben Port 6080
  4. Oder: Klicke mit Rechtsklick → "Open in Browser"

Dort erscheint der Desktop mit dem Fenster „BMI-Rechner".

## Stop

```bash
docker compose -f docker-compose.novnc.yml down
```

## Hinweise
- Auf Debian/Ubuntu-Base sind alle X11-Libs und noVNC vorinstalliert.
- Standardauflösung ist 1280x800. Ändern via SCREEN_RES, z. B. `1920x1080x24`.
- In `scripts/start_novnc.sh` wird Fluxbox als Window-Manager gestartet. Du kannst das anpassen.
- Die Java-App wird als `start.MainWindow` gestartet.

## Troubleshooting
- Seite lädt, aber kein Fenster
  - Prüfe Logs des Containers (`java-app-novnc`): evtl. Crash beim Start.
- Port 6080 ist belegt
  - Passe Port in `docker-compose.novnc.yml` an (z. B. `6081:6080`).
- Schwarzer Bildschirm
  - Manchmal hilft ein Refresh oder ein anderes Browser-Tab.
