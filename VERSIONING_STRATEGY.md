# 📋 Versions- und Branch-Strategie (Stand: 2025-10-21)

## 🎯 Ziel
Der Fahrplan für die Studierenden soll 1:1 zur tatsächlichen Branch-Struktur im Repository passen. Jede Version (V0–V5) ist als separater Branch nachvollziehbar.

## 🌳 Aktuelle Branches und Bedeutung

- `main` → Version 0: Schüler-Template (Startpunkt)
  - Ohne `Bmirechner.java` und `BmiManager.java` (werden von den Schüler:innen implementiert)
  - Java 21, Maven, Docker/Compose vorbereitet, GUI (`MainWindow`) vorhanden

- `version-1-mvc-gui` → Version 1: GUI + vollständige Musterlösung
  - Referenz-Implementierung (Model + Controller + View)

- `version-2-alter-geschlecht` → Version 2: Erweiterte Funktionalität
  - Erweiterungen um Alter (ComboBox) und Geschlecht (RadioButtons)
  - Anpassung der Interpretation im Modell und Anbindung im Controller

- `version-3-validation` → Version 3: Eingabevalidierung & Fehlerbehandlung
  - Range-Checks, Exception Handling, nutzerfreundliche Meldungen/Markierungen in der GUI

- (optional) `version-4-datenpersistenz` → Version 4: Datenbank/Persistenz
- (optional) `version-5-alternative-plattform` → Version 5: Alternative View (z. B. XML)

Hinweis: `version-0-grundgeruest` ist ein historischer Branch, aktuell identisch/nahe mit `main` und kann als Backup bestehen bleiben.

## 🧭 Fahrplan für Studierende (Schritt-für-Schritt)

### Version 0 (Start in `main`)
1) Repository klonen und im `main`-Branch bleiben.
2) `src/start/` öffnen und die beiden Klassen selbst anlegen/implementieren:
   - `Bmirechner.java` (Model): BMI berechnen + Interpretation (siehe README und UML)
   - `BmiManager.java` (Controller): bindet Model an View/Buttons
3) Bauen/Starten:
   ```bash
   ./build.sh        # oder: mvn clean compile
   ./run.sh          # startet die GUI
   ```

### Version 1 ansehen (Vergleich/Referenz)
```bash
git checkout version-1-mvc-gui
```
– Dient als Musterlösung zum Vergleichen (nicht überschreiben, nur ansehen/testen).

### Version 2 entwickeln (Alter & Geschlecht)
```bash
git checkout version-2-alter-geschlecht
```
– Aufgaben: GUI-Felder anbinden, Modell-Interpretation erweitern, Controller ergänzen.

### Version 3 entwickeln (Validierung)
```bash
git checkout version-3-validation
```
– Aufgaben: Eingabevalidierung, Exceptions, visuelles Feedback in der GUI.

## 🔁 Arbeiten mit Branches

Änderungen machen, committen und pushen:
```bash
git add .
git commit -m "Kurzbeschreibung der Änderung"
git push
```

Vergleiche zwischen Versionen (lokal):
```bash
git diff main..version-1-mvc-gui
git diff version-1-mvc-gui..version-2-alter-geschlecht
git diff version-2-alter-geschlecht..version-3-validation
```

Vergleiche auf GitHub (Beispiele):
- V0 → V1: …/compare/main...version-1-mvc-gui
- V1 → V2: …/compare/version-1-mvc-gui...version-2-alter-geschlecht
- V2 → V3: …/compare/version-2-alter-geschlecht...version-3-validation

Pull Requests (optional, empfohlen für Reviews):
1) Auf dem Ziel-Branch (z. B. `version-2-alter-geschlecht`) pushen
2) Auf GitHub „Compare & pull request“ öffnen
3) Titel/Beschreibung ausfüllen, prüfen/mergen

## 🏷️ Tags (optional)
Auf Wunsch können Versionen zusätzlich getaggt werden (z. B. zu Abgabezwecken):
```bash
git tag -a v0.0 -m "Version 0: Template (main)" main
git tag -a v1.0 -m "Version 1: MVC GUI" version-1-mvc-gui
git tag -a v2.0 -m "Version 2: Alter & Geschlecht" version-2-alter-geschlecht
git tag -a v3.0 -m "Version 3: Validierung" version-3-validation
git push --tags
```

## 🧹 Konventionen & Hygiene
- Keine Build-Artefakte committen (`build/`, `target/` sind in `.gitignore`)
- Branch-Namen aussagekräftig und konsistent halten (siehe Liste oben)
- README pro Version schlank halten (nur relevante Abschnitte hervorheben)

## 🔄 Maintenance
Bugfixes/Verbesserungen gezielt weiterreichen:
1) Fix in der niedrigsten betroffenen Version implementieren
2) In höhere Versionen übernehmen (`git cherry-pick <commit>` oder Merge des Branches)

## 🎓 Vorteile für die Lehre
- ✅ Klare, lineare Progression (V0 → V1 → V2 → V3 …)
- ✅ Jede Version ist lauffähig und testbar
- ✅ Einfaches Vergleichen zwischen Ständen (diff/compare)
- ✅ Saubere Trennung von Themen (Funktionalität vs. Validierung vs. Persistenz)
