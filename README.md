# 🧑‍💻 Aufgabe: BMI-Rechner in Docker mit eigener Ausgabe

> Hinweis: Diese README wird beim Öffnen des Workspace in VS Code automatisch angezeigt (Einstellung "workbench.startupEditor": "readme").
> Deaktivieren: In `.vscode/settings.json` den Wert auf `none` setzen oder in VS Code unter Einstellungen `Startup Editor` auf `none` ändern.

## ☕ Java Version & Setup

Dieses Projekt verwendet **Java 21 LTS** (Long-Term Support).

- **Version**: OpenJDK 21.0.5 LTS (Eclipse Temurin)
- **Build-Tool**: Maven 4.x
- **Weitere Informationen**: Siehe [docs/GETTING_STARTED/JAVA21_UPGRADE.md](docs/GETTING_STARTED/JAVA21_UPGRADE.md)

### 🔧 System vorbereiten & Anwendung starten

**👉 [📖 Zur kompletten Setup- & Build-Anleitung](docs/GETTING_STARTED/SETUP_UND_BUILD.md)**

Diese Anleitung erklärt dir:
- ✅ Java 21 LTS installieren
- ✅ Maven konfigurieren
- ✅ Projekt compilieren
- ✅ Anwendung starten und testen
- ✅ Docker nutzen (optional)

### Quick-Start Befehle
```bash
# Java 21 installieren (einmalig)
sudo apt-get install -y openjdk-21-jdk-headless
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Projekt bauen
mvn clean package -DskipTests

# Anwendung starten (GUI-Fenster öffnet sich)
java -jar target/bmi-rechner-1.0.0.jar
```

### 🌐 GUI im Browser mit Docker & NoVNC

Wenn Sie die Anwendung im Browser anschauen möchten (empfohlen für bessere Kompatibilität):

```bash
# Docker-Image bauen und starten
docker-compose -f docker-compose.novnc.yml up -d

# Öffne im Browser: http://localhost:6080
```

👉 **[Mehr Details: Docker & NoVNC Anleitung](docs/GUI_DEVELOPMENT/DOCKER_NOVNC_ANLEITUNG.md)**

## 🚀 Schnellstart

Für deine erste Orientierung:

1. **Neu im Projekt?** Starte mit [docs/GETTING_STARTED/TEMPLATE_QUICKSTART.md](docs/GETTING_STARTED/TEMPLATE_QUICKSTART.md)
2. **Schrittweise Anleitung für deine Version?** Siehe [👣 Schritt-für-Schritt-Anleitungen](#-schritt-für-schritt-anleitungen)
3. **Fragen zu Konzepten?** Sieh dir die [📚 Dokumentation nach Thema](#-dokumentation--übersicht) an

## 🔀 Versionsfahrplan (Überblick)

Diese Branches entsprechen den Versionen im Unterricht. Starte im Branch `main` (Version 0) und arbeite dich hoch.

- `main` → **Version 0**: Schüler-Template (ohne Bmirechner.java, ohne BmiManager.java)
- `version-1-mvc-gui` → **Version 1**: Musterlösung mit MVC (Model + Controller + GUI)
- `version-2-methoden` → **Version 2**: Methoden, Kontrollstrukturen & Algorithmen
- `version-3-validation` → **Version 3**: Eingabevalidierung & Fehlerbehandlung
- `version-4-assoziationen` → **Version 4 (Musterlösung): Assoziationen - Personen & Messungen** ⭐ NEU

**Schnellstart für Schüler (Version 0):**
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

Weitere Details zum Fahrplan: [docs/KONZEPTE/VERSIONING_STRATEGY.md](docs/KONZEPTE/VERSIONING_STRATEGY.md)

## 🧩 Als Template verwenden

Du möchtest dieses Projekt als Vorlage für ähnliche Aufgaben nutzen? Lies die Anleitung in [docs/GETTING_STARTED/TEMPLATE_GUIDE.md](docs/GETTING_STARTED/TEMPLATE_GUIDE.md). Dort steht:
- Wie du das Repo als Template markierst (GitHub Web/CLI)
- Wie du neue Repositories aus der Vorlage erstellst
- Wie du gemeinsame Dateien später per Skript (`scripts/sync_shared_docs.sh`) in abgeleitete Repos synchronisierst

---

## 👣 Schritt-für-Schritt-Anleitungen

Für jede Version gibt es eine eigene, ausführliche Schritt-für-Schritt-Anleitung:

| Version | Branch | Anleitung | Beschreibung |
|---------|--------|-----------|-------------|
| **0** | `main` | [docs/ANLEITUNGEN/SCHRITTE_VERSION_0.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_0.md) | Einstieg & Grundgerüst – Starte hier! |
| **1** | `version-1-mvc-gui` | [docs/ANLEITUNGEN/SCHRITTE_VERSION_1.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_1.md) | MVC-Pattern + GUI implementieren |
| **2** | `version-2-methoden` | [docs/ANLEITUNGEN/SCHRITTE_VERSION_2.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_2.md) | Methoden, Kontrollstrukturen, Algorithmen |
| **3** | `version-3-validation` | [docs/ANLEITUNGEN/SCHRITTE_VERSION_3.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_3.md) | Validierung & Fehlerbehandlung |
| **4** | `version-4-assoziationen` | [docs/ANLEITUNGEN/SCHRITTE_VERSION_4.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_4.md) | ⭐ Assoziationen (1:N) – Personen & Messungen |

**Weitere Tipps:**
- Achte auf Secure Coding (Eingabeprüfungen, sinnvolle Fehlermeldungen)
- Nutze die Dokumentation zur Vertiefung von Konzepten

---

## 📚 Dokumentation – Übersicht

Die komplette Dokumentation ist nach Themen organisiert. Nutze diesen Index, um schnell zu finden, was du brauchst:

### 🎯 Erste Schritte & Getting Started
- [TEMPLATE_QUICKSTART.md](docs/GETTING_STARTED/TEMPLATE_QUICKSTART.md) – Schneller Einstieg für Anfänger
- [TEMPLATE_GUIDE.md](docs/GETTING_STARTED/TEMPLATE_GUIDE.md) – Dieses Projekt als Vorlage nutzen
- [JAVA21_UPGRADE.md](docs/GETTING_STARTED/JAVA21_UPGRADE.md) – Informationen zu Java 21 LTS

### 🏗️ Konzepte & Architektur
- [VERSIONING_STRATEGY.md](docs/KONZEPTE/VERSIONING_STRATEGY.md) – Überblick über Branches und Versionen
- [MVC_KONZEPT.md](docs/KONZEPTE/MVC_KONZEPT.md) – Das MVC-Prinzip erklärt (Model-View-Controller)
- [MVC_ANLEITUNG.md](docs/KONZEPTE/MVC_ANLEITUNG.md) – Praktische Anleitung zur MVC-Implementierung
- [SINGLE_ENTRY_POINT.md](docs/KONZEPTE/SINGLE_ENTRY_POINT.md) – Single Entry Point-Prinzip

### ☕ Java Programmierung – Grundlagen
- [GRUNDGERUEST_KLASSE.md](docs/JAVA_PROGRAMMIERUNG/GRUNDGERUEST_KLASSE.md) – Grundgerüst einer Klasse
- [KONTROLLSTRUKTUREN.md](docs/JAVA_PROGRAMMIERUNG/KONTROLLSTRUKTUREN.md) – if, else, switch, for, while
- [ASSOZIATIONEN.md](docs/JAVA_PROGRAMMIERUNG/ASSOZIATIONEN.md) – Beziehungen zwischen Klassen
- [METHODEN_UEBERSCHREIBEN.md](docs/JAVA_PROGRAMMIERUNG/METHODEN_UEBERSCHREIBEN.md) – Methoden überschreiben (@Override)
- [POLYMORPHIE.md](docs/JAVA_PROGRAMMIERUNG/POLYMORPHIE.md) – Polymorphie und ihre Anwendung
- [INTELLIGENTE_METHODENWAHL.md](docs/JAVA_PROGRAMMIERUNG/INTELLIGENTE_METHODENWAHL.md) – Wann welche Methode nutzen
- [INFORMATION_CONTANIER_KLASSEN.md](docs/INFORMATION_CONTANIER_KLASSEN.md) – Container-Klassen (ArrayList, HashMap, HashSet, etc.)

### 🛡️ Best Practices & Code-Qualität
- [EXCEPTION_HANDLING.md](docs/BEST_PRACTICES/EXCEPTION_HANDLING.md) – **Ausnahmebehandlung (Try-Catch, eigene Exceptions)**
- [SECURE_CODING.md](docs/BEST_PRACTICES/SECURE_CODING.md) – Sichere Programmierung & Eingabevalidierung
- [KAPSELUNG.md](docs/BEST_PRACTICES/KAPSELUNG.md) – Encapsulation Prinzip & Information Hiding
- [UNIT_TESTING.md](docs/BEST_PRACTICES/UNIT_TESTING.md) – Unit Tests mit JUnit 5

### 🖥️ GUI & Benutzeroberfläche
- [GUI_ECLIPSE_WINDOWSBUILDER.md](docs/GUI_DEVELOPMENT/GUI_ECLIPSE_WINDOWSBUILDER.md) – Swing-GUI mit Eclipse WindowBuilder
- [Ereignissteuerung_und_Controller.md](docs/GUI_DEVELOPMENT/Ereignissteuerung_und_Controller.md) – **Event-Listener und Controller-Integration (BmiManager, MainWindow)**
- [GUI_DOCKER.md](docs/GUI_DEVELOPMENT/GUI_DOCKER.md) – GUI im Docker-Container ausführen
- [GUI_BROWSER.md](docs/GUI_DEVELOPMENT/GUI_BROWSER.md) – GUI im Browser mit noVNC
- [GUI_VERFUEGBARKEIT_ANALYSE.md](docs/GUI_DEVELOPMENT/GUI_VERFUEGBARKEIT_ANALYSE.md) – Verfügbarkeit verschiedener GUI-Techniken analysieren
- [TEST_GUI.md](docs/GUI_DEVELOPMENT/TEST_GUI.md) – GUI manuell testen

### 📝 Release Notes & Versionshistorie
- [CHANGELOG_VERSION_2.md](docs/RELEASE_NOTES/CHANGELOG_VERSION_2.md) – Changelog für Version 2
- [UPGRADE_SUMMARY.md](docs/RELEASE_NOTES/UPGRADE_SUMMARY.md) – Zusammenfassung von Upgrades und Änderungen

---

## 📖 Dokumentation nach Lernzielen

### Wenn du... dann lese

| Ziel | Dateien |
|------|---------|
| **Anfänger bin und nicht weißt, wo ich anfangen soll** | [TEMPLATE_QUICKSTART.md](docs/GETTING_STARTED/TEMPLATE_QUICKSTART.md) + [SCHRITTE_VERSION_0.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_0.md) |
| **MVC verstehen möchte** | [MVC_KONZEPT.md](docs/KONZEPTE/MVC_KONZEPT.md) + [MVC_ANLEITUNG.md](docs/KONZEPTE/MVC_ANLEITUNG.md) |
| **Eine GUI mit Swing erstellen möchte** | [GUI_ECLIPSE_WINDOWSBUILDER.md](docs/GUI_DEVELOPMENT/GUI_ECLIPSE_WINDOWSBUILDER.md) + [SCHRITTE_VERSION_1.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_1.md) |
| **Event-Listener und Controller verbinden möchte** | [Ereignissteuerung_und_Controller.md](docs/GUI_DEVELOPMENT/Ereignissteuerung_und_Controller.md) |
| **Kontrollflusselemente (if, for, while) verstehen möchte** | [KONTROLLSTRUKTUREN.md](docs/JAVA_PROGRAMMIERUNG/KONTROLLSTRUKTUREN.md) + [SCHRITTE_VERSION_2.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_2.md) |
| **Eingaben validieren und Fehler behandeln möchte** | [EXCEPTION_HANDLING.md](docs/BEST_PRACTICES/EXCEPTION_HANDLING.md) + [SECURE_CODING.md](docs/BEST_PRACTICES/SECURE_CODING.md) + [SCHRITTE_VERSION_3.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_3.md) |
| **Assoziationen (1:N) und Container-Klassen nutzen möchte** | [ASSOZIATIONEN.md](docs/JAVA_PROGRAMMIERUNG/ASSOZIATIONEN.md) + [INFORMATION_CONTANIER_KLASSEN.md](docs/INFORMATION_CONTANIER_KLASSEN.md) + [SCHRITTE_VERSION_4.md](docs/ANLEITUNGEN/SCHRITTE_VERSION_4.md) |
| **Klassen ordnungsgemäß strukturieren möchte** | [GRUNDGERUEST_KLASSE.md](docs/JAVA_PROGRAMMIERUNG/GRUNDGERUEST_KLASSE.md) + [KAPSELUNG.md](docs/BEST_PRACTICES/KAPSELUNG.md) |
| **Unit Tests schreiben möchte** | [UNIT_TESTING.md](docs/BEST_PRACTICES/UNIT_TESTING.md) |
| **Polymorphie verstehen möchte** | [POLYMORPHIE.md](docs/JAVA_PROGRAMMIERUNG/POLYMORPHIE.md) |
| **Container-Klassen (ArrayList, HashMap, etc.) verstehen möchte** | [INFORMATION_CONTANIER_KLASSEN.md](docs/INFORMATION_CONTANIER_KLASSEN.md) |
| **Sichere Code-Praktiken lernen möchte** | [SECURE_CODING.md](docs/BEST_PRACTICES/SECURE_CODING.md) + [EXCEPTION_HANDLING.md](docs/BEST_PRACTICES/EXCEPTION_HANDLING.md) |
| **Dieses Projekt als Template für andere Aufgaben nutzen möchte** | [TEMPLATE_GUIDE.md](docs/GETTING_STARTED/TEMPLATE_GUIDE.md) |

---

## 🛠️ Docker & Container

Das Projekt kann in Docker ausgeführt werden. Weitere Informationen:

```bash
# Mit Docker Compose (mit noVNC)
docker-compose up -d

# GUI im Browser öffnen
# http://localhost:6080
```

Details: [docs/GUI_DEVELOPMENT/GUI_DOCKER.md](docs/GUI_DEVELOPMENT/GUI_DOCKER.md) und [docs/GUI_DEVELOPMENT/GUI_BROWSER.md](docs/GUI_DEVELOPMENT/GUI_BROWSER.md)

---

## 📋 Checklisten nach Version

### ✅ Version 0 (main) – Grundlagen
- [ ] Repository klonen und öffnen
- [ ] Java 21 verfügbar? (siehe `java -version`)
- [ ] `Bmirechner.java` erstellt (Model mit BMI-Berechnung)
- [ ] `BmiManager.java` erstellt (Controller)
- [ ] `MainWindow.java` benutzbar (GUI)
- [ ] `./build.sh` läuft erfolgreich
- [ ] `./run.sh` startet die Anwendung

### ✅ Version 1 (version-1-mvc-gui) – MVC + GUI
- [ ] MVC-Pattern verstanden
- [ ] Model-Klasse korrekt implementiert
- [ ] Controller-Klasse korrekt implementiert
- [ ] GUI mit Swing/AWT erstellt
- [ ] Buttons und Text-Felder funktionieren
- [ ] Eingaben in Berechnung fließen ein
- [ ] Ausgabe wird angezeigt

### ✅ Version 2 (version-2-methoden) – Methoden & Kontrollstrukturen
- [ ] Mehrere Methoden in `Bmirechner` implementiert
- [ ] BMI-Kategorien mit if/else umgesetzt
- [ ] Schleifen (for/while) wo sinnvoll genutzt
- [ ] Algorithmen korrekt implementiert

### ✅ Version 3 (version-3-validation) – Validierung & Fehlerbehandlung
- [ ] Eingabevalidierung implementiert
- [ ] Try-Catch für Fehlerbehandlung
- [ ] Aussagekräftige Fehlermeldungen
- [ ] Unit Tests geschrieben und grün

### ✅ Version 4 (version-4-assoziationen) – Assoziationen & Messungs-Historie
- [ ] `Person.java` mit 1:N Assoziation erstellt
- [ ] `Messung.java` mit Bmirechner für Berechnung
- [ ] Person-Liste in `BmiManager` implementiert
- [ ] `addMessung()` und `getMessungen()` funktionieren
- [ ] Statistik-Methoden (Durchschnitt, beste/schlechteste Messung)
- [ ] GUI erweitert: Person-Verwaltung, Messungs-Historie, Statistik
- [ ] LocalDateTime für Zeitstempel genutzt
- [ ] Kapselung mit defensiven Kopien implementiert
- [ ] Alle Tests grün

---

## 💡 Häufig Gestellte Fragen (FAQ)

### Wie starte ich?
Siehe [TEMPLATE_QUICKSTART.md](docs/GETTING_STARTED/TEMPLATE_QUICKSTART.md)

### Welche Version soll ich bearbeiten?
Starte mit Version 0 (`main`). Folge dann dem [Versionsfahrplan](#-versionsfahrplan-überblick).

### Wie funktioniert das MVC-Pattern?
[MVC_KONZEPT.md](docs/KONZEPTE/MVC_KONZEPT.md) erklärt das Pattern. [MVC_ANLEITUNG.md](docs/KONZEPTE/MVC_ANLEITUNG.md) zeigt die Praxis.

### Wie erstelle ich eine GUI?
[GUI_ECLIPSE_WINDOWSBUILDER.md](docs/GUI_DEVELOPMENT/GUI_ECLIPSE_WINDOWSBUILDER.md) hat die Schritte.

### Wie beheble ich Fehler?
[EXCEPTION_HANDLING.md](docs/BEST_PRACTICES/EXCEPTION_HANDLING.md) zeigt Try-Catch und Custom Exceptions.

### Wie teste ich meinen Code?
[UNIT_TESTING.md](docs/BEST_PRACTICES/UNIT_TESTING.md) erklärt JUnit 5 Tests.

---

## 📁 Projektstruktur

```
.
├── README.md                          ← Du bist hier!
├── docs/                              ← Komplette Dokumentation
│   ├── GETTING_STARTED/               ← Erste Schritte
│   ├── ANLEITUNGEN/                   ← Schritt-für-Schritt für jede Version
│   ├── KONZEPTE/                      ← Architektur & Prinzipien
│   ├── JAVA_PROGRAMMIERUNG/           ← Java Grundlagen
│   ├── BEST_PRACTICES/                ← Code-Qualität & Sicherheit
│   ├── GUI_DEVELOPMENT/               ← GUI-Entwicklung
│   └── RELEASE_NOTES/                 ← Versionshistorie
├── src/
│   ├── start/                         ← Dein Java-Code hier
│   │   ├── Main.java
│   │   ├── MainWindow.java
│   │   ├── Bmirechner.java            ← Model (du schreibst das)
│   │   └── BmiManager.java            ← Controller (du schreibst das)
│   └── test/                          ← Unit Tests
├── build/                             ← Kompilierte Dateien
├── lib/                               ← Externe Bibliotheken
├── scripts/                           ← Hilfsskripte
├── pom.xml                            ← Maven-Konfiguration
├── Dockerfile                         ← Docker-Bild
├── docker-compose.yml                 ← Docker Compose
├── build.sh                           ← Build-Skript
└── run.sh                             ← Start-Skript
```

---

## 🤝 Beitragen

Hast du Verbesserungsvorschläge für die Dokumentation? Erstelle ein Issue oder einen Pull Request!

---

## 📄 Lizenz

Siehe LICENSE-Datei (falls vorhanden)

---

**Happy Coding! 🎉**

Fragen? Schau in der [Dokumentation](docs/) nach oder öffne ein Issue.
