# Vorlage verwenden: Option 1 – Template-Repository

Dieses Repository kann als Vorlage (Template) für ähnliche Projekte wie „Rechner/Automaten“ verwendet werden. Diese Anleitung zeigt dir den Ablauf über GitHub Web (einfach) und über die GitHub CLI (skriptbar), sowie einen Weg, gemeinsame Dateien später in abgeleitete Repositories zu synchronisieren.

## Voraussetzungen
- GitHub Account mit Zugriff auf dieses Repository: `ChristineJanischek/java-bmiapp-docker-mv-template`
- Optional (empfohlen): GitHub CLI (gh) installiert und angemeldet
  - Installation: https://cli.github.com/
  - Anmeldung: `gh auth login`

## Schritt 1: Repo als Template markieren (einmalig)
- GitHub Web: Settings → General → Template repository → Haken setzen
- GitHub CLI (Alternative):
  ```bash
  gh repo edit ChristineJanischek/java-bmiapp-docker-mv-template --template
  ```

## Schritt 2: Neues Projekt aus der Vorlage erstellen
- GitHub Web: Auf der Repo-Startseite „Use this template“ klicken → Namen vergeben → Create
- GitHub CLI (Alternative):
  ```bash
  # PRIVAT
  gh repo create <ORG_ODER_USER>/<NEUER_REPO_NAME> --template ChristineJanischek/java-bmiapp-docker-mv-template --private

  # ÖFFENTLICH (falls gewünscht)
  gh repo create <ORG_ODER_USER>/<NEUER_REPO_NAME> --template ChristineJanischek/java-bmiapp-docker-mv-template --public
  ```

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