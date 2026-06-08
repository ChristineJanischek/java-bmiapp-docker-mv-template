# Version 5: Vererbung auf Basis von Version 4

## Ziel

Ausgehend von Version 4 (Assoziationen) wird in Version 5 das Vererbungsprinzip eingefuehrt, um Redundanzen zu reduzieren und den Code besser wartbar zu machen.

Konkret wird der wiederholte ID-Code in mehreren Entitaeten zentral in einer Basisklasse gebuendelt.

## Analyse der Version 4

In Version 4 enthalten sowohl `Person` als auch `Messung`:

- ein Attribut `id`
- `getId()`
- `setId()`

Das ist funktional korrekt, erzeugt aber doppelte Logik. Aendert sich die ID-Validierung, muss sie in mehreren Klassen gepflegt werden.

## Empfehlung

Fuehre eine abstrakte Basisklasse `BaseEntity` ein und leite Entitaeten davon ab:

- `BaseEntity`: gemeinsame technische ID und zentrale Validierung
- `Person extends BaseEntity`
- `Messung extends BaseEntity`

Damit bleibt das Verhalten gleich, aber die gemeinsame Logik liegt nur noch an einer Stelle (DRY-Prinzip).

## Zielarchitektur

```text
BaseEntity (abstract)
  - id
  - getId()
  - setId() mit Validierung

Person extends BaseEntity
  - vorname, nachname, alter, geschlecht, email
  - List<Messung> messungen (1:N Assoziation)

Messung extends BaseEntity
  - personId, gewicht, groesse, zeitstempel, bmi, kategorie
```

## Schritt-fuer-Schritt Anleitung

### 1. Startpunkt setzen

```bash
git checkout version-4-assoziationen
git checkout -b version-5-vererbung
```

### 2. Basisklasse erstellen

Erstelle `src/start/BaseEntity.java` als abstrakte Klasse mit:

- Attribut `id`
- Konstruktoren ohne und mit ID
- `getId()`
- `setId(int id)` mit zentraler Validierung (z. B. keine negativen IDs)

### 3. Person auf Vererbung umstellen

In `Person`:

- `extends BaseEntity` einfuehren
- lokales Feld `id` entfernen
- Aufrufe auf `super(id)` im Konstruktor setzen
- lokale `getId()/setId()` entfernen

### 4. Messung auf Vererbung umstellen

In `Messung`:

- `extends BaseEntity` einfuehren
- lokales Feld `id` entfernen
- `super(id)` im vollstaendigen Konstruktor nutzen
- lokale `getId()/setId()` entfernen

### 5. Kompatibilitaet pruefen

Wichtig: Aufrufer wie `BmiManager` und `JsonDateiDatenbankSpeicher` sollen weiter mit `getId()` funktionieren.

Da die Methode nun aus `BaseEntity` kommt, sind dort keine API-Aenderungen noetig.

### 6. Tests fuer Vererbung ergaenzen

Erstelle einen Test, der prueft:

- `Person` und `Messung` sind Instanzen von `BaseEntity`
- negative IDs werden zentral abgefangen

### 7. Build und Tests ausfuehren

```bash
mvn test
```

## Definition of Done fuer Version 5

- `BaseEntity` existiert und wird vererbt
- keine doppelte ID-Logik mehr in `Person` und `Messung`
- bestehende Features aus Version 4 funktionieren weiterhin
- Tests inklusive Vererbungstest sind gruen
- README enthaelt Verweis auf Version 5 Vererbung

## Typische Fehler

- `super(id)` im Konstruktor vergessen
- alte `id`-Felder in Subklassen nicht entfernt
- Validierung in Subklassen erneut dupliziert statt zentral in `BaseEntity`

## Naechster Ausbauschritt

Nach dieser Version kannst du in einer weiteren Ausbaustufe zusaetzliche gemeinsame Logik zentralisieren, z. B. Zeitstempel- oder Audit-Felder in einer erweiterten Basisklasse.
