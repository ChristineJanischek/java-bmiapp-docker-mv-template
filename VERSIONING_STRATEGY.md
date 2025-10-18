# 📋 Versionsverwaltungsstrategie für BMI-Rechner Musterlösung

## 🎯 Ziel
Jede Entwicklungsversion (V0-V5) soll als separate, nachvollziehbare Lösung im Repository verfügbar sein.

## 🌳 Branch-Strategie

### Haupt-Branches:
- `main` - Finale, vollständige Musterlösung (Version 5)
- `version-0-grundgeruest` - Basis-Implementierung
- `version-1-gui` - Mit grafischer Benutzeroberfläche
- `version-2-controller` - Mit Controller-Integration
- `version-3-erweiterte-funktionen` - Mit Alter/Geschlecht
- `version-4-datenpersistenz` - Mit Datenbankanbindung
- `version-5-alternative-plattform` - Mit XML-View

### Workflow:

1. **Version 0 erstellen:**
   ```bash
   git checkout -b version-0-grundgeruest
   # Bmirechner-Klasse implementieren
   git add .
   git commit -m "Version 0: Grundgerüst mit Bmirechner-Klasse"
   git tag v0.0 -m "Version 0: Grundgerüst"
   git push origin version-0-grundgeruest --tags
   ```

2. **Version 1 auf Basis von Version 0:**
   ```bash
   git checkout -b version-1-gui version-0-grundgeruest
   # GUI implementieren
   git add .
   git commit -m "Version 1: GUI mit MainWindow hinzugefügt"
   git tag v1.0 -m "Version 1: GUI"
   git push origin version-1-gui --tags
   ```

3. **Weitere Versionen analog aufbauen**

4. **Main Branch aktualisieren:**
   ```bash
   git checkout main
   git merge version-5-alternative-plattform
   git push origin main
   ```

## 🏷️ Tag-Konvention
- `v0.0` - Version 0: Grundgerüst
- `v1.0` - Version 1: GUI
- `v2.0` - Version 2: Controller
- `v3.0` - Version 3: Erweiterte Funktionen
- `v4.0` - Version 4: Datenpersistenz
- `v5.0` - Version 5: Alternative Plattform

## 📚 Für Studierende

### Eine bestimmte Version auschecken:
```bash
# Als Branch
git checkout version-1-gui

# Oder als Tag
git checkout v1.0
```

### Unterschiede zwischen Versionen anzeigen:
```bash
git diff version-0-grundgeruest..version-1-gui
```

### Alle verfügbaren Versionen anzeigen:
```bash
# Branches
git branch -a

# Tags
git tag -l
```

## 📝 README-Anpassung
In jeder Version sollte die README.md angepasst werden und nur die relevanten Abschnitte enthalten:
- Version 0: Nur bis "Spezifikation Version 1"
- Version 1: Bis "Spezifikation Version 2"
- usw.

## 🔄 Maintenance
Bei Bugfixes oder Verbesserungen:
1. Fix im entsprechenden Version-Branch durchführen
2. Mit `git cherry-pick` in nachfolgende Versionen übernehmen
3. Oder alle Branches nacheinander mergen

## 🎓 Vorteile für Lehre
- ✅ Studierende können schrittweise lernen
- ✅ Jede Version ist lauffähig und testbar
- ✅ Klare Progression erkennbar
- ✅ Vergleiche zwischen Versionen möglich
- ✅ Individuelles Tempo möglich
