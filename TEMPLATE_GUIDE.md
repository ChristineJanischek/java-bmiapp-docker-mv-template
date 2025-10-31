# GitHub Template Repository - Vollständige Nutzungsanleitung

Dieses Repository (`java-bmiapp-docker-mv-template`) dient als **Template** für neue Java-Projekte mit MVC-Architektur, Docker-Support, Git-Hooks und JUnit-Tests. Diese Anleitung zeigt dir den kompletten Ablauf von der Template-Aktivierung bis zur Nutzung in neuen Projekten (z.B. MindLink).

## ✨ Was ist in diesem Template enthalten?

### Projekt-Features
- **Java 21** mit Maven Build-System
- **MVC-Architektur** (Model-View-Controller Pattern)
- **Swing GUI** mit Bildunterstützung
- **Docker & Docker Compose** (inkl. noVNC für Browser-GUI)
- **JUnit 5** Test-Suite mit 15+ automatisierten Tests
- **Git Hooks:**
  - Auto-Push nach Commit (`.git/hooks/post-commit`)
  - Pre-Push Tests (`.git/hooks/pre-push`) - blockiert bei fehlschlagenden Tests
- **Versionierte Branches** für schrittweises Lernen (version-0 bis version-3)
- **GitHub Classroom Ready** - Autograding mit `mvn test`


---

## 🚀 Schnellstart: Template-Repository Setup

### Schritt 1: Template auf GitHub aktivieren (einmalig)

**Variante A: GitHub Web UI (empfohlen)**

1. Öffne Repository Settings:
   ```
   https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template/settings
   ```

2. Scrolle zu **"General"** → **"Template repository"**

3. ☑ Aktiviere die Checkbox **"Template repository"**

4. Speichern

**Variante B: GitHub CLI**
```bash
gh repo edit ChristineJanischek/java-bmiapp-docker-mv-template --template
```

✅ **Fertig!** Das Repository steht nun als Template zur Verfügung.

---

### Schritt 2: Neues Repository aus Template erstellen (z.B. MindLink)

**Variante A: GitHub Web UI (empfohlen)**

1. Gehe zur Template-Repository Seite:
   ```
   https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template
   ```

2. Klicke auf **"Use this template"** (grüner Button oben rechts)

3. Wähle **"Create a new repository"**

4. Konfiguriere das neue Repository:
   - **Owner:** `ChristineJanischek` (oder deine Organisation)
   - **Repository name:** `mindlink` (oder beliebiger Name)
   - **Description:** z.B. "MindLink - Cognitive Training Application"
   - **Public/Private:** Nach Bedarf wählen
   - ☑ **"Include all branches"** - WICHTIG aktivieren! (Sonst fehlen version-0 bis version-3)

5. Klicke auf **"Create repository from template"**

6. ✅ **Fertig!** Neues Repository mit vollständiger Struktur ist erstellt.

**Variante B: GitHub CLI**

```bash
# Neues Repository erstellen und direkt klonen
gh repo create mindlink \
  --template ChristineJanischek/java-bmiapp-docker-mv-template \
  --public \
  --clone

cd mindlink

# Alle Branches vom Template holen (falls nicht automatisch übernommen)
git fetch origin
git branch -r | grep -v HEAD | sed 's|origin/||' | xargs -I {} git checkout -b {} origin/{}
git checkout main
```

---

### Schritt 3: Lokal klonen und Template-Remote hinzufügen

```bash
# Repository klonen (falls noch nicht geschehen)
git clone https://github.com/ChristineJanischek/mindlink.git
cd mindlink

# Template als Remote hinzufügen für spätere Updates
git remote add template https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template.git

# Remotes überprüfen
git remote -v
# Output:
# origin    https://github.com/ChristineJanischek/mindlink.git (fetch)
# origin    https://github.com/ChristineJanischek/mindlink.git (push)
# template  https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template.git (fetch)
# template  https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template.git (push)

# Alle Branches auflisten
git branch -a
```

---

## 🔄 Updates vom Template ins neue Repository holen

Wenn das Original-Template später aktualisiert wird (z.B. neue Features, Bug-Fixes, verbesserte Hooks), kannst du diese Änderungen einfach übernehmen:

### Alle Änderungen mergen

```bash
cd /pfad/zu/mindlink

# Template-Updates holen
git fetch template

# Aktuellen Stand des Templates anzeigen
git log template/main --oneline -10

# Alle Änderungen in deinen main-Branch mergen
git checkout main
git merge template/main --allow-unrelated-histories

# Konflikte lösen (falls vorhanden)
# ... manuelle Konfliktauflösung ...
git add .
git commit -m "Merge updates from template"

# Push nach Remote
git push origin main
```

### Nur bestimmte Commits übernehmen

```bash
# Bestimmte Commits cherry-picken
git fetch template
git log template/main --oneline -20

# Einzelnen Commit übernehmen
git cherry-pick <commit-hash>

# Push
git push origin main
```

### Nur bestimmte Dateien übernehmen

```bash
# Nur Git Hooks aktualisieren
git fetch template
git checkout template/main -- .git/hooks/pre-push
git checkout template/main -- .git/hooks/post-commit

# Commit
git add .git/hooks/
git commit -m "Update Git hooks from template"
git push origin main

# Nur pom.xml Dependency-Updates übernehmen
git checkout template/main -- pom.xml
git add pom.xml
git commit -m "Update pom.xml dependencies from template"
git push origin main
```

---

## 🛠 Projekt für MindLink anpassen

Nach dem Erstellen aus dem Template solltest du projekt-spezifische Anpassungen vornehmen:

### 1. Maven-Konfiguration anpassen (`pom.xml`)

```xml
<groupId>com.mindlink</groupId>
<artifactId>mindlink-app</artifactId>
<version>1.0.0</version>
<name>MindLink Application</name>
<description>MindLink - Cognitive Training Application</description>
```

### 2. Package-Struktur umbenennen

```bash
# Neue Package-Struktur erstellen
mkdir -p src/mindlink
mkdir -p src/test/java/mindlink

# Dateien verschieben
mv src/start/* src/mindlink/ 2>/dev/null || true
mv src/test/java/start/* src/test/java/mindlink/ 2>/dev/null || true

# Alte Verzeichnisse entfernen
rm -rf src/start
rm -rf src/test/java/start

# Package-Deklarationen in allen Java-Dateien ändern
find src/mindlink -name "*.java" -exec sed -i 's/package start;/package mindlink;/g' {} +
find src/test/java/mindlink -name "*.java" -exec sed -i 's/package start;/package mindlink;/g' {} +

# Import-Statements anpassen
find src/test/java/mindlink -name "*.java" -exec sed -i 's/import start\./import mindlink./g' {} +

# pom.xml Jar-Plugin Main-Class anpassen
sed -i 's/<mainClass>start\.MainWindow<\/mainClass>/<mainClass>mindlink.MainWindow<\/mainClass>/g' pom.xml
```

### 3. Klassen umbenennen (Beispiel)

```bash
# Bmirechner → MindLinkCore
mv src/mindlink/Bmirechner.java src/mindlink/MindLinkCore.java

# In MindLinkCore.java: class Bmirechner → class MindLinkCore ändern
sed -i 's/public class Bmirechner/public class MindLinkCore/g' src/mindlink/MindLinkCore.java

# Alle Referenzen im Projekt aktualisieren
grep -rl "Bmirechner" src/ | xargs sed -i 's/Bmirechner/MindLinkCore/g'

# Test-Klasse umbenennen
mv src/test/java/mindlink/BmirechnerTest.java src/test/java/mindlink/MindLinkCoreTest.java
sed -i 's/class BmirechnerTest/class MindLinkCoreTest/g' src/test/java/mindlink/MindLinkCoreTest.java
```

### 4. README und Dokumentation anpassen

```bash
# README.md für MindLink anpassen
# ... manuell editieren ...

# Build und Test
mvn clean test

# Commit
git add .
git commit -m "Adapt template for MindLink project"
git push origin main
```

---

Hinweise:
- GitHub-Template-Repositories kopieren standardmäßig nur den Standard-Branch (meist `main`).
- Die Branches für Version 1–3 (z. B. `version-1-mvc-gui`, `version-2-methoden`, `version-3-validation`) musst du bei Bedarf im neuen Repo separat anlegen.

Beispiel (im neuen Repo):
```bash
# In das neue Repo wechseln
git clone https://github.com/<ORG_ODER_USER>/<NEUER_REPO_NAME>
cd <NEUER_REPO_NAME>

# Branches für spätere Unterrichtsstufen anlegen (optional)
git checkout -b version-1-mvc-gui && git push -u origin version-1-mvc-gui
git checkout -b version-2-methoden && git push -u origin version-2-methoden
git checkout -b version-3-validation && git push -u origin version-3-validation

# Zurück zu main
git checkout main
```

## Schritt 3: Branch- und Schutzregeln (empfohlen)
Richte Branch-Protection für `main` ein (und ggf. für die Versions-Branches):
- „Require pull request reviews before merging“
- „Require status checks to pass before merging“ (sofern CI vorhanden)
- „Include administrators“ (optional)

## Schritt 4: Gemeinsame Dateien später aktualisieren (Sync)
Abgeleitete Repositories erhalten keine automatischen Updates vom Template. Nutze das mitgelieferte Skript `scripts/sync_shared_docs.sh`, um gemeinsame Dateien (z. B. README, VERSIONING_STRATEGY, Secure-Coding-Doku) per Pull Request in Ziel-Repos zu aktualisieren.

Kurzüberblick:
```bash
# Beispiel: Zwei Ziel-Repositories aktualisieren
scripts/sync_shared_docs.sh <ORG_ODER_USER>/ProjektA <ORG_ODER_USER>/ProjektB

# Dry-Run (nur anzeigen, was passieren würde)
scripts/sync_shared_docs.sh -n <ORG_ODER_USER>/ProjektA

# Repos aus Datei einlesen (eine Zeile pro repo: ORG/NAME)
scripts/sync_shared_docs.sh -f repos.txt
```

Details und Optionen siehe Skript-Header.

## Empfohlene gemeinsame Dateien
- README.md (Schülerleitfaden)
- VERSIONING_STRATEGY.md (Versionsfahrplan V0–V3)
- SECURE_CODING.md, MVC_KONZEPT.md, MVC_ANLEITUNG.md, ASSOZIATIONEN.md, KONTROLLSTRUKTUREN.md, GRUNDGERUEST_KLASSE.md, SINGLE_ENTRY_POINT.md
- GUI_* und Docker-Dokumente nach Bedarf (z. B. `GUI_DOCKER.md`, `GUI_BROWSER.md`, `docker-compose.novnc.yml`)

## Optional: Releases/Tags
Lege im neuen Repo Tags für Unterrichtsstände an (z. B. `v0.0`, `v1.0`, …), damit Schüler/innen sich gezielt Stände ansehen können.

---
Fragen oder Verbesserungen? Öffne ein Issue oder passe die Dateien im Template an – alle abgeleiteten Repos profitieren von klaren, konsistenten Unterlagen.