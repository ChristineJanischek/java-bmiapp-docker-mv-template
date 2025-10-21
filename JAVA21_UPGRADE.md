# Java 21 LTS Upgrade Dokumentation

## Zusammenfassung

Das BMI-Rechner-Projekt wurde erfolgreich auf **Java 21 LTS** (Long-Term Support) aktualisiert.

## Durchgeführte Änderungen

### 1. Java 21 Installation
- **Installierte Version**: OpenJDK 21.0.5 LTS (Eclipse Temurin)
- **Build**: Temurin-21.0.5+11-LTS
- **Installationsmethode**: SDKMAN

### 2. Build-Tool-Konfiguration
- **Hinzugefügt**: Maven POM-Datei (`pom.xml`)
- **Compiler-Einstellungen**:
  - `maven.compiler.source`: 21
  - `maven.compiler.target`: 21
  - `maven.compiler.release`: 21

### 3. Project-Struktur
```
java-bmiapp-docker-mv-template/
├── pom.xml                 (NEU - Maven-Konfiguration)
├── build.sh                (NEU - Build-Skript)
├── src/
│   └── start/
│       ├── Main.java
│       ├── MainWindow.java
│       ├── BmiManager.java
│       ├── Bmirechner.java
│       └── DatabaseConnector.java
└── build/                  (Kompilierte Klassen)
```

## Build-Befehle

### Mit Maven
```bash
# Clean und Compile
mvn clean compile

# Nur Compile
mvn compile

# Package (JAR erstellen)
mvn package
```

### Mit Build-Skript
```bash
./build.sh
```

### Manuelle Kompilierung (ohne Maven)
```bash
javac -d build src/start/*.java
```

## Vorteile von Java 21

1. **Long-Term Support (LTS)**: Unterstützung bis September 2028
2. **Neue Features**:
   - Virtual Threads (Project Loom)
   - Pattern Matching für switch
   - Record Patterns
   - Sequenced Collections
   - String Templates (Preview)
3. **Performance-Verbesserungen**: Generational ZGC, bessere Garbage Collection
4. **Sicherheit**: Neueste Sicherheitsupdates und Patches

## Docker-Kompatibilität

Das Dockerfile verwendet bereits `eclipse-temurin:21-jdk-alpine`, daher ist es vollständig kompatibel mit dem Upgrade.

## Systemvoraussetzungen

- **Minimale Java-Version**: Java 21
- **Empfohlene JVM**: Eclipse Temurin (OpenJDK)
- **Build-Tool**: Maven 3.6.3 oder höher

## Verifizierung

### Java-Version prüfen
```bash
java -version
```

Erwartete Ausgabe:
```
openjdk version "21.0.5" 2024-10-15 LTS
OpenJDK Runtime Environment Temurin-21.0.5+11 (build 21.0.5+11-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.5+11 (build 21.0.5+11-LTS, mixed mode, sharing)
```

### Projekt kompilieren
```bash
mvn clean compile
```

### Erfolgskriterium
```
[INFO] BUILD SUCCESS
[INFO] Compiling 5 source files with javac [debug release 21] to build
```

## Migration von Java 11 zu Java 21

Keine Breaking Changes identifiziert. Der vorhandene Code ist vollständig kompatibel mit Java 21.

## Nächste Schritte (Optional)

1. **Java 21 Features nutzen**:
   - Virtual Threads für bessere Concurrency
   - Pattern Matching zur Vereinfachung des Codes
   - Record Classes für Datenmodelle

2. **Build optimieren**:
   - Dependency Management mit Maven
   - Unit Tests hinzufügen
   - CI/CD Pipeline einrichten

## Datum des Upgrades

**19. Oktober 2025**

## Technische Details

- **Vorherige Java-Version**: Java 11.0.14.1 LTS
- **Neue Java-Version**: Java 21.0.5 LTS
- **Build-Tool**: Maven 3.x
- **Compiler-Plugin**: maven-compiler-plugin 3.13.0
