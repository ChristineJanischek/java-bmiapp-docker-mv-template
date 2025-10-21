# GUI mit Docker/Compose anzeigen

Dieses Projekt kann die Swing-GUI (`start.MainWindow`) direkt aus einem Docker-Container anzeigen. Dazu wird X11-Forwarding genutzt.

## Voraussetzungen
- Linux Desktop mit X-Server (z. B. Ubuntu, Fedora)
- Docker und docker-compose installiert
- X-Server muss Docker-Clients erlauben (xhost)

## Einmalig erlauben

```bash
# Erlaubt lokalen Docker-Containern, auf den X-Server zuzugreifen
xhost +local:docker
```

Optional restriktiver:
```bash
xhost +SI:localuser:$(whoami)
```

## Build und Start

```bash
# Image bauen und Container starten
docker compose up --build
```

Der Dienst `java-app` startet automatisch die GUI `start.MainWindow`.

## Troubleshooting

- Fehler: `libXtst.so.6: cannot open shared object file`
  - Lösung: Im Image sind X11-Libs installiert. Stellen Sie sicher, dass das neue Image gebaut wurde (`--build`).

- Fehler: `Error: Can't connect to X11 window server ...`
  - Lösung: DISPLAY-Variable korrekt? Host hat `DISPLAY` gesetzt? Führen Sie im Host aus:
    ```bash
    echo $DISPLAY
    xeyes  # sollte ein Fenster öffnen
    ```
  - `xhost` Freigabe gesetzt? (`xhost +local:docker`)

- Kein Icon/Logo sichtbar
  - Prüfen Sie, ob `src/start/images/*` vorhanden ist. Beim Build werden Bilder nach `build/start/images/` kopiert und via `MainWindow.class.getResource("/start/images/...")` geladen.

- Wayland statt X11
  - Unter Wayland kann XWayland aktiv sein. Stellen Sie sicher, dass `/tmp/.X11-unix` vorhanden ist und `DISPLAY` gesetzt ist. Ggf. Wayland auf X11 umstellen oder X11-compat aktivieren.

## Was wird im Container gemacht?
- Alpine-basiertes Java 21 Image
- Installation der X11-Bibliotheken (libx11, libxext, libxrender, libxtst, libxi, ...)
- Kompilierung nach `/app/build`
- Start von `java -cp build:lib/* start.MainWindow`

## Sicherheitshinweis
`xhost +local:docker` öffnet lokalen Zugriff auf Ihren X-Server. Nutzen Sie dies nur in vertrauenswürdigen Umgebungen. Alternative ist eine granulare Xauthority-Konfiguration.
