# 🧑‍💻 Aufgabe: BMI-Rechner in Docker mit eigener Ausgabe

> Hinweis: Diese README wird beim Öffnen des Workspace in VS Code automatisch angezeigt (Einstellung "workbench.startupEditor": "readme").
> Deaktivieren: In `.vscode/settings.json` den Wert auf `none` setzen oder in VS Code unter Einstellungen `Startup Editor` auf `none` ändern.

## ☕ Java Version

Dieses Projekt verwendet **Java 21 LTS** (Long-Term Support).

- **Version**: OpenJDK 21.0.5 LTS (Eclipse Temurin)
- **Build-Tool**: Maven 3.x
- **Weitere Informationen**: Siehe [JAVA21_UPGRADE.md](./JAVA21_UPGRADE.md)

### Build-Befehle
```bash
# Mit Maven
mvn clean compile

# Mit Build-Skript
./build.sh
```

## 🔀 Versionsfahrplan (Überblick)

Diese Branches entsprechen den Versionen im Unterricht. Starte im Branch `main` (Version 0) und arbeite dich hoch.

- `main` → Version 0: Schüler-Template (ohne Bmirechner.java, ohne BmiManager.java)
- `version-1-mvc-gui` → Version 1: Musterlösung mit MVC (Model + Controller + GUI)
- `version-2-methoden` → Version 2: Methoden, Kontrollstrukturen & Algorithmen
- `version-3-validation` → Version 3: Eingabevalidierung & Fehlerbehandlung

Schnellstart für Schüler (Version 0):
```bash
# Repository klonen (main = Version 0)
git clone https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template
cd java-bmiapp-docker-mv-template

# Eigene Implementierung ergänzen
# - src/start/Bmirechner.java (Model)
# - src/start/BmiManager.java (Controller)

# Bauen & Starten
./build.sh
./run.sh
```

Weitere Details zum Fahrplan siehe [VERSIONING_STRATEGY.md](./VERSIONING_STRATEGY.md).

## 🧩 Als Template verwenden

Du möchtest dieses Projekt als Vorlage für ähnliche Aufgaben nutzen? Lies die Anleitung in [TEMPLATE_GUIDE.md](./TEMPLATE_GUIDE.md). Dort steht:
- Wie du das Repo als Template markierst (GitHub Web/CLI)
- Wie du neue Repositories aus der Vorlage erstellst
- Wie du gemeinsame Dateien später per Skript (`scripts/sync_shared_docs.sh`) in abgeleitete Repos synchronisierst

## 👣 Schritt-für-Schritt (für Schüler)

1) Starte im richtigen Branch (Version 0)
```bash
git checkout main
git pull
```

2) Lege die fehlenden Klassen an und implementiere sie
- `src/start/Bmirechner.java` (Model): berechnet den BMI und liefert die Interpretation.
- `src/start/BmiManager.java` (Controller): verbindet View (MainWindow) und Model.

3) Baue und starte die App
```bash
./build.sh     # oder: mvn clean compile
./run.sh       # startet die GUI
```

4) Teste die Funktionen in der GUI
- Gewicht/Größe eingeben → BMI berechnen → Interpretation anzeigen
- Buttons „Leeren“ und „Schließen“ ausprobieren

5) Änderungen speichern und hochladen
```bash
git add .
git commit -m "Implementiere Bmirechner und BmiManager (Version 0)"
git push
```

6) Nächste Versionen ansehen/weiterentwickeln
```bash
# Musterlösung (Vergleich):
git checkout version-1-mvc-gui

# Version 2 (Methoden & Algorithmen):
git checkout version-2-methoden

# Version 3 (Validierung):
git checkout version-3-validation
```

Tipps:
- Lies die Spezifikationen unter „📄 Spezifikation Version X“ weiter unten.
- Achte auf Secure Coding (Eingabeprüfungen, sinnvolle Fehlermeldungen).

## 🛠️ Troubleshooting

- „Class not found“ oder GUI startet nicht:
  - Stelle sicher, dass `./build.sh` ohne Fehler durchläuft.
  - Prüfe, ob die Dateien unter `src/start/` im richtigen Paket `package start;` liegen.
- Docker/noVNC zeigt keine Oberfläche:
  - Container neu bauen/starten: `docker compose -f docker-compose.novnc.yml up --build -d`
  - Browser: http://localhost:6080/vnc.html → Connect
- Falscher Branch/Stand:
  - `git branch -a` zeigt alle Branches
  - `git checkout <branch>` wechselt den Zweig

## 🎯 Ziel

Entwickle eine eigene BMI-App (Body Mass Index) und lerne dabei:
- Das Grundgerüst einer Klasse selbstständig zu programmieren
- Einen Unit-Test zu implementieren
- Das Modell um eine Steuerungsklasse (BmiManager) zu erweitern
- Die Programmlogik für `berechne()` und `interpretiere()` zu implementieren
- Die BmiApp um eine grafische Benutzeroberfläche (MainWindow) zu erweitern
- Vorgehensweise im Management von Versionen (siehe [VERSIONING_STRATEGY.md](./VERSIONING_STRATEGY.md))

Wende bei der Umsetzung des Projektes das **MVC-Prinzip** an und berücksichtige in jedem Entwicklungsschritt die Prinzipien des **Secure Coding**.


## 📄 Konzepte und Prinzipien (MVC)

Weitere Erklärungen und Beispiel-Prompts findest du in [info.md](./INFO.md).

### Architektur und Secure Coding:
 - **Kontrollstrukturen (Schleifen & Alternativen)**: Grundlagen und Beispiele zur Programmlogik (siehe [KONTROLLSTRUKTUREN.md](./KONTROLLSTRUKTUREN.md))
- **Model-View-Controller-Prinzip (MVC)**: Trennung von Datenmodell, Darstellung und Steuerung (siehe [MVC_KONZEPT.md](./MVC_KONZEPT.md))
- **Assoziationen zwischen Klassen**: Verbindungen und Multiplizität (siehe [ASSOZIATIONEN.md](./ASSOZIATIONEN.md))
- **Single Entry Point-Prinzip**: Ein zentraler Einstiegspunkt für die Anwendung (siehe [SINGLE_ENTRY_POINT.md](./SINGLE_ENTRY_POINT.md))
- **Prinzip der geringsten Berechtigung**: Kapselung und sinnvolle Zugriffsmodifikatoren (siehe [KAPSELUNG.md](./KAPSELUNG.md))
  - Arbeitsblatt (PDF): [materials/quiz_kapselung.pdf](./materials/quiz_kapselung.pdf)
- **Secure Coding**: Grundlagen und Best Practices zur Entwicklung sicherer Software (siehe [SECURE_CODING.md](./SECURE_CODING.md))

### Benutzerfreundlichkeit (Usability & Softwareergonomie):
- Eingabefehler vermeiden durch geeignete UI-Elemente
- Benutzerführung und Assistenz bieten

### Validierung (Secure Coding):
- Eingaben prüfen und validieren (siehe [SECURE_CODING.md](./SECURE_CODING.md))
- Ausgaben bereinigen und sicher darstellen

### Vorgehensweise:

#### 1. Bmirechner-Klasse (Model)
Weitere grundlegende Fakten zur Implementierung des Grundgerüsts einer Klasse findest du in [GRUNDGERUEST_KLASSE.md](./GRUNDGERUEST_KLASSE.md).


```text
┌──────────────────────────────────────────────────────────────┐
│                        Bmirechner                            │
├──────────────────────────────────────────────────────────────┤
│ - gewicht: double                                            │
│ - groesse: double                                            │
│ - ergebnis: double                                           │
│ - kategorie: String                                          │
├──────────────────────────────────────────────────────────────┤
│ + Bmirechner()                                               │
│ + setGewicht(pGewicht: double): void                         │
│ + setGroesse(pGroesse: double): void                         │
│ + getGewicht(): double                                       │
│ + getGroesse(): double                                       │
│ + getErgebnis(): double                                      │
│ + getKategorie(): String                                     │
│ + berechne(pGewicht: double, pGroesse: double): double       │
│ + interpretiere(): void                                      │
│ + toString(): String                                         │
└──────────────────────────────────────────────────────────────┘
```

#### Wertetabelle für die BMI-Interpretation

| BMI-Wert         | Kategorie                |
|------------------|------------------------- |
| < 16             | Starkes Untergewicht     |
| 16 – < 17        | Mäßiges Untergewicht     |
| 17 – < 18.5      | Leichtes Untergewicht    |
| 18.5 – < 25      | Normalgewicht            |
| 25 – < 30        | Prädipositas             |
| 30 – < 35        | Adipositas Grad I        |
| 35 – < 40        | Adipositas Grad II       |
| ≥ 40             | Adipositas Grad III      |

```
┌─────────────────────────────────────────────┐
│                BmiManager                   │
├─────────────────────────────────────────────┤
│ - model: Bmirechner                         │
├─────────────────────────────────────────────┤
│ + BmiManager()                              │
│ + BmiManager(pModel: Bmirechner)            │
│ + berechneBMI(pGewicht: double,             │
│               pGroesse: double): double     │
│ + interpretiereBMI(): void                  │
│ + getModel(): Bmirechner                    │
│ + setModel(pModel: Bmirechner): void        │
└─────────────────────────────────────────────┘
```

**Aufgabe:**
Nachdem der Unit-Test für das Modell läuft, sollen die Schüler die Steuerungsklasse `BmiManager` gemäß obigem UML-Diagramm implementieren. Die Klasse übernimmt die Steuerung der BMI-Berechnung und der Interpretation und verbindet das Modell mit der Benutzeroberfläche.

#### 3. Unit-Test (Preview)
- Öffne die Datei `Main.java` im Ordner `src/start`
- Ergänze die `main()`-Methode so, dass:
  - ein Objekt der Klasse `Bmirechner` erstellt wird
  - die Methode `berechne(double pGewicht, double pGroesse)` aufgerufen wird
  - im Terminal die Ausgabe erscheint, z.B.: `Ihr BMI beträgt: 22.5`

#### 4. GUI (View)
- Ersetzt die Main-Klasse als Einstiegspunkt
- Wird vom BmiManager gesteuert

## 📄 Spezifikation Version 0: Prototyp (Model, Controler))
**Basis-Implementierung:**
- Implementierung der `Bmirechner`-Klasse (Model)
Implementierung der `BmiManager`-Klasse (Controller).
Der `BmiManager` übernimmt die Steuerung der Anwendung und koordiniert die Interaktion zwischen Model (`Bmirechner`) und View (`Main`).
Der bestehende Unit-Test in `Main.java` muss so umgeschrieben werden, dass die Steuerung über den `BmiManager` erfolgt.
Das Model (`Bmirechner`), die View (`Main`) und der Controller (`BmiManager`) müssen gemäß dem MVC-Prinzip umgesetzt werden.
Alle drei Komponenten sollen in dieser Version funktionsfähig und miteinander verbunden sein.
- Unit-Test in `Main.java` zur Überprüfung der Funktionalität

## 📄 Spezifikation Version 1: Benutzeroberfläche (View) & Steuerung (Controller)

**Ziel:**
- Die grafische Benutzeroberfläche (GUI) ist vollständig vorgegeben und muss nicht selbst programmiert werden.
- Die Schüler sollen die Struktur und Funktionsweise der GUI verstehen und mit dem Controller (BmiManager) verbinden.
- Die Anwendung wird im Browser über noVNC getestet.

**Was ist zu tun?**
- Verstehe den Aufbau der Klasse `MainWindow.java` (ausführlich kommentiert)
- Implementiere und teste die Steuerungsklasse `BmiManager` gemäß UML
- Kompiliere das Projekt und teste die Anwendung im Browser

**Testanleitung:**

1. **Kompiliere alle Java-Dateien:**
   ```bash
   javac -d build src/start/*.java
   ```
2. **Starte die Anwendung im Browser (noVNC):**
   ```bash
   docker compose -f docker-compose.novnc.yml up --build -d
   ```
3. **Öffne die GUI:**
   - Im VS Code PORTS-Panel Port 6080 öffnen (Globe-Symbol)
   - Oder im Browser: http://localhost:6080/vnc.html
   - Klicke auf "Connect"
4. **Teste die App:**
   - Gewicht und Größe eingeben
   - "Berechne BMI" und "Interpretiere BMI" klicken
   - "Leeren" und "Schließen" testen

**Hinweis:**
- Die Schüler müssen die MainWindow-Klasse nicht selbst schreiben, sondern nur verstehen und nutzen.
- Die Steuerung (Controller/BmiManager) und das Modell (Bmirechner) werden selbst implementiert.
- Die GUI ist der Einstiegspunkt für die Anwendung.
- Die Controller-Integration (BmiManager) ist bereits Bestandteil dieser Version.
- Die Ereignissteuerung für die Buttons ist in der MainWindow-Klasse vorgegeben und nutzt den BmiManager.
- Eine Schritt-für-Schritt-Anleitung findet sich in [MVC_ANLEITUNG.md](./MVC_ANLEITUNG.md).

## 📄 Spezifikation Version 2: Erweiterte Funktionalität (Alter & Geschlecht)
**Erweiterung um Alter und Geschlecht:**
- **Neue GUI-Elemente:**
  - ComboBox für das Alter (`cbAlter`)
  - RadioButtons für das Geschlecht (`rbGeschlecht`)
- **Erweiterte Logik:**
  - BMI-Berechnung bleibt bestehen
  - BMI-Interpretation wird erweitert um alters- und geschlechtsspezifische Kategorien
  - Anpassung der Methode `interpretiere()` im Model
- **Controller-Erweiterung:**
  - BmiManager erhält zusätzliche Methoden für Alter und Geschlecht
  - Ereignissteuerung wird entsprechend angepasst

## 📄 Spezifikation Version 3: Eingabevalidierung und Fehlerbehandlung
**Secure Coding und Benutzerfreundlichkeit:**
- **Eingabevalidierung:**
  - Prüfung auf gültige Zahlenwerte (keine negativen Werte, keine leeren Felder)
  - Bereichsprüfung (z.B. Gewicht: 1-500 kg, Größe: 0.5-2.5 m)
  - Fehlerbehandlung mit aussagekräftigen Meldungen
- **Exception Handling:**
  - Try-Catch-Blöcke für NumberFormatException
  - Benutzerfreundliche Fehlermeldungen in der GUI
- **Usability-Verbesserungen:**
  - Eingabefelder werden bei ungültigen Werten rot markiert
  - Hilfetext für erwartete Eingabeformate 

## 📄 Spezifikation Version 4: Datenpersistenz
**Speicherung von Profildaten:**
- Implementierung von Datenstrukturen zur Speicherung von Personenprofilen
- **Datenbankanbindung:**
  - Steuerung der Datenbank über `BmiManager`
  - Methode: `connectTo(host, db, bn, ps)`
  - Persistente Speicherung von Benutzerdaten
- **Secure Coding:**
  - SQL-Injection vermeiden
  - Eingabevalidierung und Parameterisierung
  - Sichere Passworthandhabung

## 📄 Spezifikation Version 5: Alternative Plattformen
**XML-basierte View:**
- Alternative Implementierung mit XML (z.B. für Android: AndroidAppWindow)
- Plattformübergreifende Darstellung

## 🔧 Entwicklung und Kompilierung

### Lokales Kompilieren und Testen

**1. Alle Java-Dateien kompilieren:**
```bash
javac src/start/*.java
```

**2. Ins src-Verzeichnis wechseln:**
```bash
cd src
```

**3. Anwendung testen:**
```bash
java start.Main
```

## ▶️ Ausführen mit Docker

### Docker Container starten

**1. Zurück ins Hauptverzeichnis wechseln (falls notwendig):**
```bash
cd /workspaces/java-bmiapp-docker-mv-template
```

**2. Docker Image erstellen und Container starten:**
```bash
docker-compose up --build
```

**3. Docker Compose-Prozess beenden:**
```bash
# Drücke STRG+C auf der Tastatur
```

Alternativ in einem neuen Terminal:
```bash
docker-compose down
```

## ✅ Testen und Validierung

Nach der Implementierung solltest du folgende Tests durchführen:
- [ ] Unit-Tests für die `Bmirechner`-Klasse
- [ ] Funktionstest der GUI (alle Eingabefelder und Buttons)
- [ ] Validierung der BMI-Berechnung und -Interpretation
- [ ] Test der Datenbankanbindung (Version 4)
- [ ] Secure Coding: Eingabevalidierung testen

## 📤 Lösung abgeben

### Änderungen committen und pushen:

**1. Alle Dateien für den Commit vormerken:**
```bash
git add .
```

**2. Commit mit aussagekräftiger Nachricht erstellen:**
```bash
git commit -m "BMI-Rechner Version X implementiert"
```
*Hinweis: Ersetze X durch die entsprechende Versionsnummer (1-5)*

**3. Änderungen ins GitHub-Repository hochladen:**
```bash
git push
```

Nach dem Push werden automatische Tests (sofern eingerichtet) ausgeführt und die Lehrkraft kann deine Lösung einsehen und bewerten.

Nach dem Push werden automatische Tests (sofern eingerichtet) ausgeführt und die Lehrkraft kann deine Lösung einsehen und bewerten.



## ✅ Test / Feedback

Wenn du richtig gearbeitet hast, bekommst du automatisches Feedback:
- Gibt dein Programm den Text „Hallo“ aus? ✅

## ✅ Abgeben: Lösung übermitteln
So gehst du vor:

1.Änderungen speichern und alle Dateien zum Commit vormerken:
```bash (Terminal)
git add .
```

2. Commit mit einer Nachricht erstellen:
```bash (Terminal)
git commit -m "Lösung Aufgabe HalloWelt"
```

3. Änderungen ins GitHub-Repository hochladen (pushen):
```bash (Terminal)
git push
```

Danach werden die Tests (sofern eingerichtet) meist automatisch ausgeführt und die Lehrkraft sieht deine Lösung.

- [Schritt-für-Schritt-Anleitung: MVC-Prinzip und Test im Browser](./MVC_ANLEITUNG.md)
