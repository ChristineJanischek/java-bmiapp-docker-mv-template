# 🐳 BMI-Anwendung mit Docker & NoVNC ausführen

> Einfache Anleitung zur Ausführung der Java BMI-Anwendung mit Docker und NoVNC (GUI im Browser)  
> **Version**: 1.0 | **Aktualisiert**: 11. Februar 2026

---

## 🖥️ GUI im Browser mit NoVNC

Nach dem Bauen des Docker-Images können Sie die Anwendung direkt im Browser anschauen und verwenden, ohne lokale Java-Tools zu benötigen.

---

## 📋 Voraussetzungen

- ✅ Docker Desktop oder Docker CLI
- ✅ Docker-Compose (normalerweise mit Docker Desktop enthalten)
- ✅ Ein moderner Browser (Chrome, Firefox, Safari, Edge)
- ✅ Port 6080 verfügbar (für NoVNC-Zugang)

---

## 🚀 Schnellstart: 3 Schritte

### Schritt 1: Docker-Image bauen

```bash
cd /workspaces/java-bmiapp-docker-mv-template

# Docker-Image mit NoVNC bauen
docker build -f Dockerfile.novnc -t bmi-rechner:novnc .
```

**Dauer**: 5-10 Minuten (beim ersten Mal, danach stark schneller durch Caching)

### Schritt 2: Container starten

```bash
# Mit Docker Compose starten (Datenbank + Anwendung)
docker-compose -f docker-compose.novnc.yml up -d

# Oder nur die App ohne Datenbank
docker run -it -p 6080:6080 bmi-rechner:novnc
```

### Schritt 3: Im Browser öffnen

```
🌐 http://localhost:6080
```

Das war's! Die BMI-Anwendung sollte jetzt im Browser angezeigt werden.

---

## 🎮 NoVNC Bedienung im Browser

### Grundlagen

| Aktion | Taste/Klick |
|--------|-----------|
| **Maus-Klick** | Normaler Linksklick |
| **Rechtsklick** | Ctrl + Linksklick |
| **Shift/Alt/Ctrl** | `Shift`, `Alt`, `Ctrl` Taste am linken Rand |
| **Tastatur** | Direkt eingeben |
| **Menü öffnen** | Klick auf 🎨 Symbol oben links |

### Häufige Probleme

**Problem**: Maus responsive verzögert
- **Lösung**: Refresh-Rate in NoVNC-Einstellungen reduzieren

**Problem**: Keyboard-Input funktioniert nicht
- **Lösung**: Im NoVNC-Menü "Keyboard" → "en" wählen und Sprache ggf. anpassen

**Problem**: Fenster wird nicht angezeigt
- **Lösung**: Im Browser F11 drücken für Vollbildmodus, oder Browser-Größe vergrößern

---

## 📦 Detaillierte Docker-Befehle

### Container starten mit Docker Compose

```bash
# Mit Datenbank und vollständigem Setup
docker-compose -f docker-compose.novnc.yml up

# Im Hintergrund starten
docker-compose -f docker-compose.novnc.yml up -d

# Mit Logs verfolgen
docker-compose -f docker-compose.novnc.yml up -d && docker-compose -f docker-compose.novnc.yml logs -f
```

### Container stoppen und entfernen

```bash
# Container stoppen
docker-compose -f docker-compose.novnc.yml down

# Container UND Volumes löschen (Datenbank wird zurückgesetzt)
docker-compose -f docker-compose.novnc.yml down -v
```

### Container-Status überprüfen

```bash
# Laufende Container anzeigen
docker-compose -f docker-compose.novnc.yml ps

# Logs anzeigen
docker-compose -f docker-compose.novnc.yml logs -f java-app-novnc

# In den Container einsteigen (falls nötig)
docker-compose -f docker-compose.novnc.yml exec java-app-novnc bash
```

---

## 🔧 Umweltoptionen

### Ports anpassen

Falls Port 6080 bereits besetzt ist, ändern Sie die `docker-compose.novnc.yml`:

```yaml
services:
  java-app-novnc:
    ports:
      - "6080:6080"  # Ändern Sie die erste Zahl (z.B. "8080:6080")
```

Dann öffnen Sie: `http://localhost:8080`

### Bildschirm-Auflösung ändern

In `docker-compose.novnc.yml` oder als Umgebungsvariable:

```bash
docker run -e SCREEN_RES=1920x1080x24 -p 6080:6080 bmi-rechner:novnc
```

### Speicher und CPU begrenzen

```bash
docker run -m 2g --cpus 2 -p 6080:6080 bmi-rechner:novnc
```

---

## 🎯 Verschiedene Start-Szenarien

### Szenario 1: Entwicklung (Echtzeit-Testen)

```bash
# 1. Image bauen
docker build -f Dockerfile.novnc -t bmi-rechner:dev .

# 2. Mit volumeneinbindung starten (Quellcode-Änderungen sofort aktiv)
docker run -it \
  -v /workspaces/java-bmiapp-docker-mv-template/src:/app/src \
  -p 6080:6080 \
  bmi-rechner:dev
```

Dann im Container neu compilieren:
```bash
javac -d build src/start/*.java
```

### Szenario 2: Production (mit Datenbank)

```bash
# Mit vollständigem Setup
docker-compose -f docker-compose.novnc.yml up -d
```

### Szenario 3: Nur Anwendung (ohne Datenbank)

```bash
# Starten ohne Datenbank
docker run -d \
  -p 6080:6080 \
  --name bmi-app \
  bmi-rechner:novnc \
  /usr/local/bin/start_novnc.sh
```

---

## 🔍 Troubleshooting

### Problem: "Port 6080 is already allocated"

```bash
# Finde den Prozess auf Port 6080
lsof -i :6080

# Oder mit Docker
docker ps -a | grep 6080

# Stoppe den Container
docker-compose -f docker-compose.novnc.yml down

# Oder verwende einen anderen Port
docker-compose -f docker-compose.novnc.yml -p <PORT> up
```

### Problem: "Cannot connect to Docker daemon"

```bash
# Docker starten (falls gestoppt)
sudo systemctl start docker

# Docker-Berechtigung überprüfen
docker ps

# Falls Fehler: Benutzer zur docker-Gruppe hinzufügen
sudo usermod -aG docker $USER
newgrp docker
```

### Problem: "NoVNC zeigt schwarzer Bildschirm"

```bash
# Logs überprüfen
docker-compose -f docker-compose.novnc.yml logs java-app-novnc | tail -20

# Container neu starten
docker-compose -f docker-compose.novnc.yml restart java-app-novnc
```

### Problem: "Anwendungs-Fenster wird nicht angezeigt"

```bash
# In den Container einsteigen
docker-compose -f docker-compose.novnc.yml exec java-app-novnc bash

# Überprüfe ob die Anwendung läuft
ps aux | grep java

# Starten Sie die App neu
export DISPLAY=:1
java -cp build start.MainWindow
```

---

## 📊 Datenbankverbindung (optional)

Die NoVNC-Umgebung enthaltet auch eine MySQL-Datenbank (Port 3306), falls die Anwendung damit arbeitet.

```bash
# In den Container einsteigen und Datenbank testen
docker-compose -f docker-compose.novnc.yml exec db mysql -u root -p

# (Standard-Passwort ist oft "root")
```

---

## 🛑 Container stoppen und aufräumen

```bash
# Nur Container stoppen (Daten bleiben erhalten)
docker-compose -f docker-compose.novnc.yml stop

# Container komplett entfernen
docker-compose -f docker-compose.novnc.yml down

# Images löschen
docker rmi bmi-rechner:novnc

# Alles aufräumen
docker system prune -a
```

---

## 📚 Weitere Ressourcen

- [NoVNC Dokumentation](https://novnc.com/)
- [Docker Dokumentation](https://docs.docker.com/)
- [Docker Compose Reference](https://docs.docker.com/compose/compose-file/)

---

**Autor**: Christine Janischek  
**Datum**: 11. Februar 2026  
**Version**: 1.0
