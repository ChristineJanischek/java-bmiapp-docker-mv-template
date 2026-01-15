# Schritt-für-Schritt-Anleitung: BMI-Rechner mit MVC und GUI-Test

Diese Anleitung führt dich von der Implementierung des MVC-Prinzips bis zum Test der grafischen Oberfläche im Browser (noVNC).

---

## Übersicht: Die drei Komponenten im MVC-Prinzip

Bevor du loslegst, hier ein Überblick über alle drei Klassen, die du implementieren/nutzen wirst:

```text
┌──────────────────────────────────────────────────────────────┐
│ View (MainWindow)                                            │
│ - tfGewicht, tfGroesse, cbAlter, rbMaennlich, rbWeiblich    │
│ - taErgebnis                                                 │
│ - btBerechneBmi, btInterpretiereBmi, btLeeren, btSchliessen │
│ - manager: BmiManager                                        │
│ - addEventListeners() – Buttons sind aktiv!                 │
└─────────────────────────────┬──────────────────────────────┘
                              │ nutzt / ruft auf
                              ↓
┌──────────────────────────────────────────────────────────────┐
│ Controller (BmiManager)                                       │
│ - model: Bmirechner                                          │
│ - berechneBMI(gewicht, groesse): double                      │
│ - interpretiereIntelligent(gewicht, groesse, alter, ...) ──→ Logik-Koordination
└─────────────────────────────┬──────────────────────────────┘
                              │ nutzt / ruft auf
                              ↓
┌──────────────────────────────────────────────────────────────┐
│ Model (Bmirechner)                                           │
│ - gewicht, groesse, ergebnis, kategorie                      │
│ - berechne(gewicht, groesse): double                         │
│ - interpretiere(bmi, alter, geschlecht): String             │
│ - getErgebnis(), getKategorie()                             │
└──────────────────────────────────────────────────────────────┘
```

**Der Ablauf:**
1. Benutzer klickt Button → MainWindow reagiert
2. MainWindow ruft BmiManager auf
3. BmiManager ruft Bmirechner auf
4. Bmirechner liefert Ergebnis
5. MainWindow zeigt Ergebnis an

---

## 1. Model (Bmirechner.java)

- Lege die Datei `src/start/Bmirechner.java` an.
- Implementiere Attribute, Konstruktoren, Getter/Setter, Methoden `berechne()`, `interpretiere()`, `toString()`.
- Schreibe Unit-Tests für die Methoden.

Detaillierte Anleitung: [SCHRITTE_VERSION_0.md](../ANLEITUNGEN/SCHRITTE_VERSION_0.md)

---

## 2. Controller (BmiManager.java)

- Lege die Datei `src/start/BmiManager.java` an.
- Implementiere die Methoden: `berechneBMI()`, `interpretiereBMI()`, `getModel()`
- Der Controller ruft Methoden des Modells auf und stellt die Ergebnisse bereit.

**Wichtig:** Die Ereignissteuerung (Event-Listener & Button-Actions) ist separat dokumentiert:

👉 [Ereignissteuerung_und_Controller.md](../GUI_DEVELOPMENT/Ereignissteuerung_und_Controller.md)

---

## 3. View (MainWindow.java)

- Die Datei `src/start/MainWindow.java` ist vorgegeben und bereits vollständig implementiert.
- Sie enthält alle GUI-Elemente und ist mit dem Controller verbunden.
- Du musst hier nichts ändern, aber den Code verstehen!

Weitere Infos zur GUI-Erstellung mit WindowBuilder:

👉 [GUI_ECLIPSE_WINDOWSBUILDER.md](../GUI_DEVELOPMENT/GUI_ECLIPSE_WINDOWSBUILDER.md)

---

## 4. Kompilieren

```bash
# Wechsle ins Projektverzeichnis
cd /workspaces/java-bmiapp-docker-mv-template

# Kompiliere alle Java-Dateien ins build-Verzeichnis
mvn clean compile

# Oder mit Skript:
./build.sh
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

## Weitere Ressourcen

| Thema | Anleitung |
|-------|-----------|
| **MVC-Konzept verstehen** | [MVC_KONZEPT.md](./MVC_KONZEPT.md) |
| **Schritt-für-Schritt (Version 0)** | [SCHRITTE_VERSION_0.md](../ANLEITUNGEN/SCHRITTE_VERSION_0.md) |
| **Schritt-für-Schritt (Version 1)** | [SCHRITTE_VERSION_1.md](../ANLEITUNGEN/SCHRITTE_VERSION_1.md) |
| **Ereignissteuerung & Controller-Integration** | [Ereignissteuerung_und_Controller.md](../GUI_DEVELOPMENT/Ereignissteuerung_und_Controller.md) |
| **GUI mit WindowBuilder** | [GUI_ECLIPSE_WINDOWSBUILDER.md](../GUI_DEVELOPMENT/GUI_ECLIPSE_WINDOWSBUILDER.md) |
| **Klassen strukturieren** | [GRUNDGERUEST_KLASSE.md](../JAVA_PROGRAMMIERUNG/GRUNDGERUEST_KLASSE.md) |

---

**Viel Erfolg beim Umsetzen deines BMI-Rechners mit MVC und grafischer Oberfläche!**