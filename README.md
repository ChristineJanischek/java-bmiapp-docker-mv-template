# 🧑‍💻 Aufgabe: BMI-Rechner in Docker mit eigener Ausgabe

## 🎯 Ziel

Entwickle eine eigene BMI-App (Body Mass Index) und lerne dabei:
- Das Grundgerüst einer Klasse selbstständig zu programmieren
- Einen Unit-Test zu implementieren
- Das Modell um eine Steuerungsklasse (BmiManager) zu erweitern
- Die Programmlogik für `berechne()` und `interpretiere()` zu implementieren
- Die BmiApp um eine grafische Benutzeroberfläche (MainWindow) zu erweitern
- Vorgehensweise im Management von Versionen (siehe `VERSIONING_STRATEGY.md`)

Wende bei der Umsetzung des Projektes das **MVC-Prinzip** an und berücksichtige in jedem Entwicklungsschritt die Prinzipien des **Secure Coding**.


## 📄 Konzepte und Prinzipien (MVC)

Weitere Erklärungen und Beispiel-Prompts findest du in [info.md](./info.md).

### Architektur und Secure Coding:
- **Model-View-Controller-Prinzip (MVC)**: Trennung von Datenmodell, Darstellung und Steuerung
- **Single Entry Point-Prinzip**: Ein zentraler Einstiegspunkt für die Anwendung
- **Prinzip der geringsten Berechtigung**: Kapselung und sinnvolle Zugriffsmodifikatoren

### Benutzerfreundlichkeit (Usability & Softwareergonomie):
- Eingabefehler vermeiden durch geeignete UI-Elemente
- Benutzerführung und Assistenz bieten

### Validierung (Secure Coding):
- Eingaben prüfen und validieren
- Ausgaben bereinigen und sicher darstellen

### Vorgehensweise:

#### 1. Bmirechner-Klasse (Model)
- Erstelle die Klasse `Bmirechner` und implementiere die Grundfunktionalität
- Implementiere alle erforderlichen Attribute, Konstruktoren, Getter/Setter und Methoden
- Die `toString()`-Methode gibt alle Attributwerte formatiert zurück

**UML-Klassendiagramm:**

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

## 📄 Spezifikation Version 0: Prototyp
**Basis-Implementierung:**
- Implementierung der `Bmirechner`-Klasse (Model)
Implementierung der `BmiManager`-Klasse (Controller).
Der `BmiManager` übernimmt die Steuerung der Anwendung und koordiniert die Interaktion zwischen Model (`Bmirechner`) und View (`Main`).
Der bestehende Unit-Test in `Main.java` muss so umgeschrieben werden, dass die Steuerung über den `BmiManager` erfolgt.
Das Model (`Bmirechner`), die View (`Main`) und der Controller (`BmiManager`) müssen gemäß dem MVC-Prinzip umgesetzt werden.
Alle drei Komponenten sollen in dieser Version funktionsfähig und miteinander verbunden sein.
- Unit-Test in `Main.java` zur Überprüfung der Funktionalität

## 📄 Spezifikation Version 1: Benutzeroberfläche (View)

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
- Die Steuerung (Controller) und das Modell (Model) werden selbst implementiert.
- Die GUI ist der Einstiegspunkt für die Anwendung.
- Eine Schritt-für-Schritt-Anleitung findet sich in [MVC_ANLEITUNG.md](./MVC_ANLEITUNG.md).

## 📄 Spezifikation Version 2: Controller-Integration
**Ereignissteuerung und MVC-Verknüpfung:**
- Implementierung der Ereignissteuerung für die Buttons "Berechnen" und "Interpretieren"
- Der BmiManager steuert die Interaktion zwischen Model und View
- Objektorientierte Verwaltung der Komponenten

## 📄 Spezifikation Version 3: Erweiterte Funktionalität
**Erweiterung um Alter und Geschlecht:**
- **Neue GUI-Elemente:**
  - ComboBox für das Alter (`cbAlter`)
  - RadioButtons für das Geschlecht (`rbGeschlecht`)
- **Implementierung der Logik:**
  - BMI-Berechnung mit `berechne()`
  - BMI-Interpretation mit `interpretiere()`:
    - Untergewichtig
    - Normalgewicht
    - Übergewicht
    - Adipositas
- **Ereignissteuerung:**
  - Ereignissteuerung in der Steuerungsklasse `BmiManager` 

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
