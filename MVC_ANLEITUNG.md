# Schritt-für-Schritt-Anleitung: BMI-Rechner mit MVC und GUI-Test im Browser

Diese Anleitung führt dich von der Implementierung des MVC-Prinzips bis zum Test der grafischen Oberfläche im Browser (noVNC).

---

## 1. Model (Bmirechner.java)
- Lege die Datei `src/start/Bmirechner.java` an.
- Implementiere Attribute, Konstruktoren, Getter/Setter, Methoden `berechne()`, `interpretiere()`, `toString()`.
- Schreibe Unit-Tests für die Methoden.

---

## 2. Controller (BmiManager.java)
- Lege die Datei `src/start/BmiManager.java` an.
- Implementiere laut UML:
  - Attribut: `model: Bmirechner`
  - Methoden: `berechneBMI()`, `interpretiereBMI()`, `getModel()`
- Der Controller ruft Methoden des Modells auf und stellt die Ergebnisse bereit.

---

## 3. View (MainWindow.java)
- Die Datei `src/start/MainWindow.java` ist vorgegeben und bereits vollständig implementiert.
- Sie enthält alle GUI-Elemente und ist mit dem Controller verbunden.
- Du musst hier nichts ändern, aber den Code verstehen!

---

## 4. Kompilieren

```bash
# Wechsle ins Projektverzeichnis
cd /workspaces/java-bmiapp-docker-mv-template

# Kompiliere alle Java-Dateien ins build-Verzeichnis
javac -d build src/start/*.java
```

---

## 5. Docker-Container mit Browser-GUI starten

```bash
# Baue und starte die noVNC-Variante (GUI im Browser)
docker compose -f docker-compose.novnc.yml up --build -d
```

---

## 6. GUI im Browser testen

1. Öffne das **PORTS-Panel** in VS Code (unten) und suche Port 6080.
2. Klicke auf das **Globe-Symbol (🌐)** neben Port 6080 oder öffne im Browser:
   - http://localhost:6080/vnc.html
   - (im Codespace: https://[dein-codespace]-6080.app.github.dev/vnc.html)
3. Klicke auf **"Connect"**.
4. Die grafische Oberfläche erscheint. Teste die App:
   - Gewicht und Größe eingeben
   - "Berechne BMI" klicken
   - "Interpretiere BMI" klicken
   - "Leeren" und "Schließen" testen

---

## 7. Fehlerbehebung
- Wenn die GUI nicht erscheint, prüfe die Container-Logs:
  ```bash
  docker compose -f docker-compose.novnc.yml logs -f java-app-novnc
  ```
- Stelle sicher, dass alle PNG-Bilder im richtigen Ordner liegen (`src/start/images/`).
- Kompiliere nach Änderungen immer neu und baue den Container erneut.

---

## 8. Visualisierung des MVC-Prinzips

```
View (MainWindow)
    ↓ nutzt
Controller (BmiManager)
    ↓ steuert
Model (Bmirechner)
```

---

Viel Erfolg beim Umsetzen und Testen deines BMI-Rechners mit MVC und grafischer Oberfläche!