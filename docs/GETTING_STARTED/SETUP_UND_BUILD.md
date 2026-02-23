# 🚀 Setup, Build und Live-Ausführung der BMI-Anwendung

> Komplette Schritt-für-Schritt-Anleitung zur Installation, zum Compilieren und zur Live-Ausführung der Java BMI-Rechner Anwendung  
> **Version**: 1.0 | **Aktualisiert**: 11. Februar 2026

---

## 📋 Systemvoraussetzungen

### Erforderliche Software

| Komponente | Version | Status |
|-----------|---------|--------|
| **Java** | 21 LTS (OpenJDK 21.0.10+) | ✅ Erforderlich |
| **Maven** | 4.x | ✅ Erforderlich (benötigt Java 17+) |
| **Git** | beliebig | Optional (für Repository-Klone) |

### Getestete Systeme

- ✅ Ubuntu 24.04 LTS (Focal) mit OpenJDK 21
- ✅ Ubuntu 22.04 LTS mit OpenJDK 21
- ✅ Linux (allgemein) mit Java 21
- 🔧 macOS und Windows: Bitte JAVA_HOME manuell setzen

---

## 🔧 Schritt 1: Java 21 LTS installieren

### 1.1 Installation auf Linux (Ubuntu/Debian)

```bash
# Repository-Listen aktualisieren
sudo apt-get update

# Java 21 LTS installieren (headless version ohne GUI-Abhängigkeiten)
sudo apt-get install -y openjdk-21-jdk-headless
```

**Dauer**: ~5-10 Minuten (abhängig von Internetverbindung)

### 1.2 Überprüfe die Installation

```bash
# Java-Version anzeigen
java -version
```

**Erwartete Ausgabe**:
```
openjdk version "21.0.10" 2026-XX-XX LTS
OpenJDK Runtime Environment (build 21.0.10+...)
OpenJDK 64-Bit Server VM (build 21.0.10+...)
```

### 1.3 Fehlerbehandlung: JAVA_HOME setzen

Falls Java mehrere Versionen auf dem System hat, setze `JAVA_HOME` explizit:

```bash
# Finde den Java 21-Pfad
ls -la /usr/lib/jvm/ | grep java-21

# Setze JAVA_HOME in der aktuellen Shell
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Überprüfe
echo $JAVA_HOME
```

**Permanent setzen** (optional, in `~/.bashrc` oder `~/.zshrc`):
```bash
echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.bashrc
source ~/.bashrc
```

---

## 🛠️ Schritt 2: Maven überprüfen und konfigurieren

### 2.1 Maven-Version anzeigen

```bash
# Maven-Version und Java-Version anzeigen
mvn -version
```

**Erwartete Ausgabe**:
```
Apache Maven 4.0.0-rc-5 (...)
Maven home: ...
Java version: 21.0.10, vendor: Ubuntu
Default locale: en, platform encoding: UTF-8
```

### 2.2 Falls Maven nicht installiert ist

```bash
# Überprüfe ob Maven vorhanden ist
which mvn

# Falls nicht, installiere es
sudo apt-get install -y maven
```

---

## 📦 Schritt 3: Projekt clonen und vorbereiten

### 3.1 Repository klonen

```bash
# Repository klonen
git clone https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template.git

# In das Projekt-Verzeichnis wechseln
cd java-bmiapp-docker-mv-template
```

### 3.2 Verzeichnis-Struktur überprüfen

```bash
# Überprüfe dass pom.xml existiert
ls -la pom.xml

# Wichtige Verzeichnisse anzeigen
tree -L 2 src/
```

**Erwartete Struktur**:
```
src/
├── start/
│   ├── Main.java
│   ├── MainWindow.java
│   ├── Bmirechner.java
│   ├── BmiManager.java
│   ├── Person.java
│   ├── Messung.java
│   └── images/
└── test/
    └── java/
        └── start/
            └── BmirechnerTest.java
```

---

## 🏗️ Schritt 4: Projekt compilieren

### 4.1 Clean Build durchführen

Entfernt alte Build-Artefakte und compiliert von Grund auf:

```bash
# Setze JAVA_HOME (falls noch nicht geschehen)
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Clean Build
cd /workspaces/java-bmiapp-docker-mv-template
mvn clean compile
```

**Dauer**: 20-30 Sekunden (beim ersten Mal länger, da Dependencies heruntergeladen werden)

**Erwartete Ausgabe endet mit**:
```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  X.XXX s
[INFO] Finished at: YYYY-MM-DD HH:mm:ss
```

### 4.2 Fehlerbehandlung bei der Compilation

**Fehler**: `cannot find symbol ... interpretiereIntelligent`

**Lösung**: Diese Methode existiert in diesem Branch nicht. Die pom.xml ist bereits konfiguriert, um TestIntelligent.java auszuschließen.

```bash
# Überprüfe dass die pom.xml das Exclude enthält
grep -n "TestIntelligent" pom.xml
```

---

## 📦 Schritt 5: JAR-Archiv bauen

### 5.1 Package erstellen

```bash
# Mit Maven eine executable JAR-Datei bauen
mvn clean package -DskipTests
```

**Was geschieht hier**:
1. ✅ Quellcode wird compiliert
2. ✅ Ressourcen (Images) werden kopiert
3. ✅ JAR-Archiv wird erstellt
4. ✅ Main-Class wird in MANIFEST.MF eingetragen

**Dauer**: 30-60 Sekunden

**Erwartete Ausgabe**:
```
[INFO] Building jar: .../target/bmi-rechner-1.0.0.jar
[INFO] BUILD SUCCESS
```

### 5.2 Überprüfe das JAR-Archiv

```bash
# Überprüfe dass die JAR-Datei existiert
ls -lh target/bmi-rechner-*.jar

# Überprüfe die Größe (sollte ca. 10-50 KB sein)
file target/bmi-rechner-*.jar
```

**Ausgabe**:
```
-rw-r--r-- 1 user user 45K Feb 11 09:38 target/bmi-rechner-1.0.0.jar
```

---

## 🚀 Schritt 6: Anwendung starten

### 6.1 Einfache Variante (Befehlszeile)

```bash
# JAR-Datei ausführen
/usr/lib/jvm/java-21-openjdk-amd64/bin/java -jar target/bmi-rechner-1.0.0.jar
```

### 6.2 Mit JAVA_HOME-Variable

```bash
# Setze JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Starte die Anwendung
java -jar target/bmi-rechner-1.0.0.jar
```

### 6.3 Im Hintergrund starten (Linux)

```bash
# Starte im Hintergrund und leite Output in Log-Datei
nohup java -jar target/bmi-rechner-1.0.0.jar > bmi-app.log 2>&1 &

# Überprüfe den Status
ps aux | grep java

# Logs anzeigen
tail -f bmi-app.log
```

### 6.4 Anwendung beenden

```bash
# Finde den Java-Prozess
ps aux | grep "bmi-rechner"

# Beende den Prozess
kill <PID>

# Oder force-kill
kill -9 <PID>
```

---

## 🐳 Schritt 7: Mit Docker ausführen (optional)

### 7.1 Docker-Container mit Anwendung bauen

```bash
# Dockerfile nutzen (für headless-Betrieb)
docker build -f Dockerfile -t bmi-rechner:latest .

# Oder mit NoVNC-GUI (für Browser-Zugang)
docker build -f Dockerfile.novnc -t bmi-rechner:novnc .
```

### 7.2 Container starten

```bash
# Einfacher Container
docker run -it bmi-rechner:latest

# Mit NoVNC (GUI im Browser)
docker-compose -f docker-compose.novnc.yml up

# Öffne den Browser unter: http://localhost:6080
# Passwort: (standard, normalerweise "password")
```

---

## 🧪 Schritt 8: Anwendung testen

### 8.1 Maven-Tests ausführen

```bash
# Nur Unit-Tests laufen lassen
mvn test

# Tests mit Ausgabe
mvn test -X
```

### 8.2 Spezifische Test-Klasse testen

```bash
# Nur BmirechnerTest laufen lassen
mvn test -Dtest=BmirechnerTest

# Mit Muster (z.B. alle Tests mit "Bmirechner" im Namen)
mvn test -Dtest=*Bmirechner*
```

---

## 📊 Schnellreferenz: Alle Befehle

| Befehl | Beschreibung |
|--------|-------------|
| `mvn clean` | Löscht alte Build-Dateien |
| `mvn compile` | Compiliert Java-Code |
| `mvn package` | Erstellt JAR-Archiv |
| `mvn test` | Führt Unit-Tests aus |
| `mvn clean package -DskipTests` | Build ohne Tests |
| `java -jar target/bmi-rechner-*.jar` | Startet die Anwendung |

---

## 🔍 Troubleshooting

### Problem: "Java: command not found"

**Solution**:
```bash
# Überprüfe ob Java installiert ist
which java

# Falls nicht, installiere es
sudo apt-get install -y openjdk-21-jdk-headless

# Setze JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
```

---

### Problem: "Maven 4.x requires Java 17 or newer"

**Solution**:
```bash
# Überprüfe Java-Version
java -version

# Falls Java 11 angezeigt wird, setze JAVA_HOME auf Java 21
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Überprüfe dass Maven die richtige Java-Version sieht
mvn -version
```

---

### Problem: "JAR-Datei nicht ausführbar"

**Solution**:
```bash
# Überprüfe dass Main-Class in MANIFEST.MF korrekt ist
unzip -p target/bmi-rechner-*.jar META-INF/MANIFEST.MF

# Sollte enthalten: Main-Class: start.MainWindow

# Wenn nicht, baue erneut
mvn clean package
```

---

## 📚 Weitere Ressourcen

- [Java 21 LTS Features](https://www.oracle.com/java/technologies/javase/21all-relnotes.html)
- [Maven Official Guide](https://maven.apache.org/guides/)
- [BMI-Rechner Dokumentation](../KONZEPTE/MVC_KONZEPT.md)

---

**Autor**: Christine Janischek  
**Datum**: 11. Februar 2026  
**Version**: 1.0
