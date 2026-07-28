# Naming-Konvention fuer Versionen in Anleitungen

Diese Regel gilt fuer Dateien in `docs/ANLEITUNGEN`, damit Schueler sofort erkennen,
welches Thema in welcher Version umgesetzt wird.

## 1. Dateinamen-Regeln

### A) Kernanleitungen je Version

Format:
`SCHRITTE_VERSION_<N>.md`
oder
`SCHRITTE_VERSION_<N>_<THEMA>.md`

Beispiele:
- `SCHRITTE_VERSION_3.md`
- `SCHRITTE_VERSION_4.md`
- `SCHRITTE_VERSION_5_VERERBUNG.md`

### B) Vertiefungsmodule mit klarer Version

Format:
`LERNMODUL_VERSION_<N>_<THEMA>.md`

Beispiel:
- `LERNMODUL_VERSION_4_ASSOZIATIONEN.md`

### C) Nicht mehr verwenden (Legacy)

Nicht mehr verwenden:
`VERSION_<N>_<THEMA>.md`

Grund: Diese Form ist fuer Schueler weniger eindeutig und kollidiert mit dem
sonstigen Schema im Projekt.

## 2. Inhalts-Regel

Wenn eine Datei versioniert benannt ist (z. B. `...VERSION_4...`), muss in den
ersten Zeilen des Dokuments die passende Versionsangabe stehen (z. B. `Version 4`).

## 3. Ausgangs- und Loesungssituation

Fuer lernzielorientierte Module soll zusaetzlich klar beschrieben sein:
- Ausgangssituation: vorherige Version und Branch
- Loesungssituation: Zielversion und Branch

## 4. Automatische Pruefung (Routine)

Pruefskript:
`python3 scripts/check_guide_version_naming.py`

Das Skript prueft:
- Legacy-Dateinamen (`VERSION_<N>_...`) sind nicht mehr vorhanden
- versionierte Dateien folgen dem Namensschema
- Versionszahl im Dateinamen stimmt mit der Versionsangabe im Dokument ueberein

## 5. Empfohlener Ablauf bei neuen Anleitungen

1. Dateiname nach obiger Konvention waehlen.
2. Titel und Einleitung mit passender Versionszahl schreiben.
3. Ausgangs-/Loesungssituation ergaenzen (falls Lernmodul).
4. Pruefskript ausfuehren.
5. Erst dann committen.
