# Schritt-für-Schritt-Anleitung Version 5 (JSON-Dateispeicher mit Strukturtrennung)

## Überblick

In Version 4 speichert die BMI-App Personen, Messungen und statistische Auswertungen nur im Arbeitsspeicher.
Sobald das Programm beendet wird, gehen alle Daten verloren.

In Version 5 erweitern wir die bestehende Anwendung deshalb um einen JSON-Dateispeicher.
Dabei orientieren wir uns an der Idee aus Kurztest_Dateispeicher_5:

- Struktur und Daten werden getrennt gespeichert.
- Die Daten werden als kleine JSON-Datei-Datenbank organisiert.
- Personen und Messungen werden als zusammenhängender Objektspeicher abgelegt.
- Die fachliche Struktur bleibt nachvollziehbar und testbar.

Didaktisch ist das wichtig, weil die Schülerinnen und Schüler hier lernen, dass Persistenz nicht nur bedeutet, Daten irgendwie in eine Datei zu schreiben.
Sie lernen vielmehr, Datenmodell, Beziehungen, Dateiformat und Programmlogik bewusst aufeinander abzustimmen.

---

## Lernziele

Nach dieser Version sollen die Lernenden:

- den Unterschied zwischen flüchtigen Objekten im RAM und persistenten Daten erklären können,
- den Nutzen einer Trennung von Schema und Nutzdaten verstehen,
- ein kleines Datenmodell in einer JSON-Datei-Datenbank umsetzen können,
- 1:N-Beziehungen auch nach dem Speichern und Laden korrekt wiederherstellen können,
- eine bestehende MVC-Anwendung sauber um Persistenz erweitern können,
- typische Fehler beim JSON-Schreiben und JSON-Lesen erkennen und vermeiden.

## Lernpfad-Navigation

- Gesamtpfad: [LERNPFAD_VERSIONEN_0_BIS_6.md](./LERNPFAD_VERSIONEN_0_BIS_6.md)
- Vorher: [SCHRITTE_VERSION_5_VERERBUNG.md](./SCHRITTE_VERSION_5_VERERBUNG.md)
- Weiter mit Version 6: [SCHRITTE_VERSION_6_SICHERE_DATENVERARBEITUNG.md](./SCHRITTE_VERSION_6_SICHERE_DATENVERARBEITUNG.md)

---

## Ausgangspunkt: Was aus Version 4 übernommen wird

Die neue Version baut direkt auf Version 4 auf.
Diese Version enthält bereits:

- die Klasse `Person`,
- die Klasse `Messung`,
- den Controller `BmiManager`,
- die GUI `MainWindow`,
- die statistische Auswertung pro Person,
- die Historie mehrerer Messungen.

Das ist ein didaktisch guter Ausgangspunkt, weil die fachliche Modellierung schon vorhanden ist.
Die neue Aufgabe besteht also nicht darin, das Modell neu zu erfinden, sondern es dauerhaft speicherbar zu machen.

Merksatz für Schülerinnen und Schüler:

> Version 4 kann Daten verwalten.
> Version 5 kann Daten verwalten und dauerhaft sichern.

---

## Fachliche Modellidee: JSON-Datenbank in 3. Normalform

Auch wenn wir keine echte SQL-Datenbank einsetzen, denken wir modellorientiert.
Die Daten werden so gespeichert, dass Redundanzen reduziert werden und Beziehungen sauber abbildbar sind.

### Entitäten

Wir arbeiten mit mindestens zwei Entitäten:

1. Person
2. Messung

### Beziehung

Eine Person kann mehrere Messungen besitzen.
Das ist eine 1:N-Beziehung.

### Warum 3. Normalform hier sinnvoll ist

Die Schülerinnen und Schüler sollen verstehen:

- Personendaten gehören in einen eigenen Bereich.
- Messungsdaten gehören in einen eigenen Bereich.
- Eine Messung speichert nicht alle Personendaten erneut.
- Die Verbindung entsteht über eine ID.

Damit vermeiden wir unnötige Wiederholungen.
Wenn sich z. B. die E-Mail einer Person ändert, muss sie nur an einer Stelle geändert werden.

Didaktische Kernidee:

> Nicht alles, was zusammengehört, wird auch zusammen gespeichert.
> Oft speichert man Daten getrennt und verbindet sie über Schlüssel.

---

## Speicherkonzept

Wie im Kurztest werden Struktur und Daten getrennt gespeichert.

### Strukturdatei

Die Strukturdatei beschreibt, welche Bereiche und Felder es gibt.
Beispiel:

- `bmiapp.schema.json`

Diese Datei ändert sich selten.

### Datendatei

Die Datendatei enthält die konkreten Personen und Messungen.
Beispiel:

- `bmiapp.data.json`

Diese Datei ändert sich häufig.

### Didaktische Begründung

Diese Trennung hilft den Lernenden, zwei Ebenen zu unterscheiden:

- Wie ist etwas aufgebaut?
- Welche konkreten Inhalte gibt es aktuell?

Das entspricht genau dem Unterschied zwischen Datenmodell und Datensatz.

---

## Zielstruktur der JSON-Dateien

### 1. Strukturdatei

Die Strukturdatei kann z. B. so aufgebaut sein:

```json
{
  "database": "bmiapp",
  "version": 5,
  "entities": [
    {
      "name": "personen",
      "primaryKey": "id",
      "fields": [
        { "name": "id", "type": "int", "required": true },
        { "name": "vorname", "type": "string", "required": true },
        { "name": "nachname", "type": "string", "required": true },
        { "name": "alter", "type": "int", "required": true },
        { "name": "geschlecht", "type": "string", "required": true },
        { "name": "email", "type": "string", "required": true }
      ]
    },
    {
      "name": "messungen",
      "primaryKey": "id",
      "foreignKey": "personId",
      "fields": [
        { "name": "id", "type": "int", "required": true },
        { "name": "personId", "type": "int", "required": true },
        { "name": "gewicht", "type": "double", "required": true },
        { "name": "groesse", "type": "double", "required": true },
        { "name": "zeitstempel", "type": "string", "required": true }
      ]
    }
  ]
}
```

### 2. Datendatei

Die Datendatei trennt Personen und Messungen ebenfalls logisch:

```json
{
  "personen": [
    {
      "id": 1,
      "vorname": "Mia",
      "nachname": "Muster",
      "alter": 16,
      "geschlecht": "Frau",
      "email": "mia@example.de"
    }
  ],
  "messungen": [
    {
      "id": 1,
      "personId": 1,
      "gewicht": 62.0,
      "groesse": 1.68,
      "zeitstempel": "2026-04-05T10:15:00"
    }
  ]
}
```

### Warum ist das besser als verschachtelte Komplettobjekte?

Eine verschachtelte Speicherung wäre zwar auf den ersten Blick einfacher, aber fachlich schlechter für diese Lernphase.

Hier sollen die Lernenden gerade verstehen:

- wie Beziehungen modelliert werden,
- wie IDs funktionieren,
- wie man Daten getrennt speichert und später wieder verknüpft.

---

## Architektur für Version 5

Die MVC-Struktur bleibt erhalten.
Neu kommt eine eigene Persistenz-Schicht hinzu.

```text
MainWindow (View)
    ↓
BmiManager (Controller)
    ↓
Person / Messung / Bmirechner (Model)
    ↓
JsonDateiDatenbankSpeicher (Persistenz)
```

Didaktischer Hinweis:

Die Schülerinnen und Schüler sollen hier lernen, dass Dateioperationen nicht in die GUI gehören.
Die GUI löst nur Aktionen aus.
Das eigentliche Speichern und Laden übernimmt eine eigene Klasse.

Das ist sauberer, testbarer und besser wartbar.

---

## Schritt 1: Das Datenmodell für Persistenz vorbereiten

Bevor gespeichert werden kann, brauchen die Objekte stabile Identifikatoren.

### Aufgabe

Ergänze `Person` und `Messung` um eine ID.

### Warum ist das nötig?

Solange Objekte nur im RAM leben, kann Java Referenzen verwalten.
Nach dem Speichern in einer Datei existieren diese Referenzen aber nicht mehr.
Deshalb brauchen wir technische Schlüssel:

- `personId` für Personen
- `messungId` für Messungen
- `personId` als Fremdschlüssel in der Messung

### Didaktische Erklärung

Das ist ein zentraler Schritt vom objektorientierten Denken zum datenbankorientierten Denken.
Die Beziehung zwischen Person und Messung darf nicht nur im Kopf oder im Java-Objekt existieren.
Sie muss explizit speicherbar werden.

### Umsetzungsidee

In `Person`:

```java
private int id;
```

In `Messung`:

```java
private int id;
private int personId;
```

Wichtig:

- IDs müssen eindeutig sein.
- Beim Laden aus JSON müssen die IDs wieder übernommen werden.
- Der Controller sollte neue IDs vergeben können.

---

## Schritt 2: Eine eigene Persistenz-Klasse anlegen

Lege eine Klasse an, z. B.:

```java
public class JsonDateiDatenbankSpeicher {
}
```

Diese Klasse übernimmt:

- Schema schreiben
- Daten schreiben
- Schema minimal prüfen
- Daten laden
- Personen und Messungen wieder verknüpfen

### Didaktische Erklärung

Viele Lernende schreiben Speichercode spontan direkt in die GUI oder in den Controller.
Für kleine Anfängerprogramme ist das verständlich, aber fachlich nicht sauber.

Mit einer eigenen Persistenz-Klasse lernen die Schülerinnen und Schüler:

- Verantwortung zu trennen,
- Klassen nach Aufgaben zu strukturieren,
- Code wiederverwendbar zu machen.

---

## Schritt 3: Das Schema schreiben

Zuerst wird beim Speichern die Strukturdatei erzeugt.

### Ziel

Die Datei `bmiapp.schema.json` beschreibt:

- welche Entitäten es gibt,
- welche Felder Pflichtfelder sind,
- welche Felder Primär- oder Fremdschlüssel sind.

### Beispielidee

```java
String schemaJson = """
{
  \"database\": \"bmiapp\",
  \"version\": 5,
  \"entities\": [
    {
      \"name\": \"personen\",
      \"primaryKey\": \"id\"
    },
    {
      \"name\": \"messungen\",
      \"primaryKey\": \"id\",
      \"foreignKey\": \"personId\"
    }
  ]
}
""";
```

### Didaktischer Schwerpunkt

Hier sollen die Lernenden erkennen:

- Die Strukturdatei speichert keine konkreten BMI-Werte.
- Sie beschreibt nur das Gerüst.
- Ein Datenmodell ist etwas anderes als eine Datensammlung.

Das ist oft ein gedanklicher Sprung und sollte bewusst thematisiert werden.

---

## Schritt 4: Die Datendatei normalisiert schreiben

Nun werden die Nutzdaten gespeichert.

### Fachliche Regel

Personen und Messungen werden in getrennten Arrays gespeichert.

### Warum?

Weil wir keine unnötigen Wiederholungen möchten.
Jede Messung enthält nur den Verweis auf die zugehörige Person, nicht alle Personendaten.

### Beispielaufbau

```java
datenJson.append("{\n");
datenJson.append("  \"personen\": [ ... ],\n");
datenJson.append("  \"messungen\": [ ... ]\n");
datenJson.append("}\n");
```

### Didaktische Erläuterung

Für Schülerinnen und Schüler ist es sehr verlockend, jede Person inklusive aller Messungen direkt verschachtelt zu speichern.
Das kann man technisch machen, aber für diese Lernstufe ist die getrennte Speicherung wertvoller.

Sie trainiert:

- Schlüsselkonzepte,
- Beziehungen,
- saubere Datenmodellierung,
- Wiederaufbau von Objektgraphen.

---

## Schritt 5: JSON sicher schreiben

Beim Schreiben dürfen keine kaputten JSON-Dateien entstehen.

### Worauf ist zu achten?

1. UTF-8 explizit verwenden.
2. Sonderzeichen in Strings escapen.
3. Kein Komma hinter dem letzten Element.
4. Am besten über eine temporäre Datei schreiben und dann atomar verschieben.

### Warum ist das didaktisch wichtig?

Hier lernen die Schülerinnen und Schüler, dass funktionierender Code nicht automatisch robuster Code ist.
Gerade Dateispeicherung ist fehleranfällig.

Ein guter Unterrichtspunkt ist die Frage:

> Was passiert, wenn das Programm während des Schreibens abstürzt?

Damit wird verständlich, warum man mit `.tmp`-Datei und atomischem Move arbeiten sollte.

---

## Schritt 6: Beim Laden zuerst die Struktur prüfen

Bevor Daten geladen werden, wird die Schema-Datei gelesen und minimal validiert.

### Minimalprüfungen

- Gibt es die erwarteten Entitäten?
- Kommen `personen` und `messungen` vor?
- Enthält die Struktur Angaben zu `id` und `personId`?

### Didaktische Erklärung

Das ist ein wichtiger Qualitätsgedanke:

- nicht blind laden,
- zuerst prüfen,
- dann weiterarbeiten.

Die Schülerinnen und Schüler lernen dadurch einen professionellen Arbeitsstil kennen.

---

## Schritt 7: Daten laden und Beziehungen wiederherstellen

Nach dem Lesen der Datendatei müssen die Objekte rekonstruiert werden.

### Reihenfolge

1. Alle Personen laden
2. Personen in einer Map nach ID ablegen
3. Alle Messungen laden
4. Jede Messung über `personId` der passenden Person zuordnen

### Warum in dieser Reihenfolge?

Eine Messung kann erst korrekt zugeordnet werden, wenn die zugehörige Person schon existiert.

### Didaktischer Mehrwert

Hier sehen die Lernenden sehr konkret, wie aus Dateien wieder zusammenhängende Objekte werden.
Das ist der Kern eines einfachen Objektspeichers mit Zusammenhang.

### Typisches Denkmuster

```java
Map<Integer, Person> personenNachId = new HashMap<>();
```

Danach können Messungen so zugeordnet werden:

```java
Person person = personenNachId.get(personId);
if (person != null) {
    person.addMessung(messung);
}
```

---

## Schritt 8: Den BmiManager erweitern

Der Controller soll nicht selbst JSON bauen, aber er soll Speichern und Laden auslösen können.

### Sinnvolle Methoden

```java
public void speichereDaten(Path ordner) throws IOException
public void ladeDaten(Path ordner) throws IOException
```

### Weitere sinnvolle Aufgaben des Controllers

- IDs verwalten,
- geladene Personenliste setzen,
- aktuelle Person nach dem Laden sinnvoll auswählen,
- Fehler an die GUI weitergeben.

### Didaktische Erläuterung

Die Schülerinnen und Schüler sollen hier sehen, dass der Controller Vermittler ist:

- GUI kennt die Persistenzdetails nicht.
- Persistenz kennt die GUI nicht.
- Der Controller verbindet beide Seiten.

---

## Schritt 9: Die GUI um Speichern und Laden ergänzen

In der GUI können zwei neue Buttons ergänzt werden:

- `Speichern`
- `Laden`

### Erwartetes Verhalten

- `Speichern`: schreibt Schema und Daten in einen festen Ordner,
- `Laden`: liest Daten ein und aktualisiert Personenliste, Historie und Statistik.

### Didaktische Erklärung

Gerade hier verstehen Lernende den Mehrwert der Persistenz besonders gut.
Sie können:

1. Personen anlegen,
2. Messungen erfassen,
3. die App schließen,
4. neu starten,
5. die Daten wieder laden.

Das macht die abstrakte Idee von Dauerhaftigkeit sichtbar.

---

## Schritt 10: Statistik bewusst mitdenken

Die App aus Version 4 berechnet bereits statistische Werte wie:

- Anzahl der Messungen,
- Durchschnitts-BMI,
- beste Messung,
- schlechteste Messung,
- BMI-Differenz.

Diese Statistik soll in Version 5 nicht separat gespeichert werden.

### Warum nicht?

Weil Statistik aus Rohdaten berechnet werden kann.
Wenn man berechenbare Werte zusätzlich speichert, entstehen leicht Widersprüche.

### Didaktischer Kernpunkt

Schülerinnen und Schüler sollen den Unterschied verstehen zwischen:

- Primärdaten: Person und Messung
- abgeleiteten Daten: Statistik

Abgeleitete Daten werden neu berechnet, nicht dauerhaft redundant gespeichert.

Das ist ein wichtiger Gedanke im Sinne der Normalisierung.

---

## Schritt 11: Testbare Lösung aufbauen

Eine gute Version 5 ist nicht nur funktional, sondern testbar.

### Sinnvolle Tests

1. Speichern erzeugt beide Dateien.
2. Die Schema-Datei enthält die erwarteten Entitäten.
3. Die Datendatei enthält getrennte Bereiche für Personen und Messungen.
4. Nach dem Laden sind Personen und Messungen vollständig wiederhergestellt.
5. Die Statistik liefert nach dem Laden dieselben Werte wie vor dem Speichern.

### Didaktische Erklärung

Das ist ein sehr guter Anlass, um mit den Lernenden über Qualität zu sprechen:

- Ein Programm ist nicht schon dann gut, wenn es einmal funktioniert.
- Es ist gut, wenn seine Korrektheit überprüfbar ist.

---

## Typische Fehler und wie man sie im Unterricht bespricht

### Fehler 1: Personen und Messungen werden doppelt gespeichert

Problem:
Die Messung enthält zusätzlich noch Vorname, Nachname und E-Mail.

Folge:
Redundanz und Inkonsistenzen.

Didaktische Frage:

> Was passiert, wenn sich der Nachname ändert, aber alte Messungen noch den alten Wert speichern?

---

### Fehler 2: Statistik wird mitgespeichert

Problem:
Durchschnitt, Differenz oder bester BMI werden dauerhaft in JSON geschrieben.

Folge:
Diese Werte können veralten oder falsch sein.

Didaktische Frage:

> Muss man alles speichern, was man anzeigen kann?

---

### Fehler 3: Kein Trennprinzip zwischen Struktur und Daten

Problem:
Alles landet in einer einzigen Datei.

Folge:
Strukturänderungen und Datenspeicherung sind nicht sauber getrennt.

Didaktische Frage:

> Woran erkennt man später, ob ein Fehler in der Modellierung oder in den konkreten Daten liegt?

---

### Fehler 4: Trailing-Komma im JSON

Problem:
Nach dem letzten Element wird noch ein Komma angehängt.

Folge:
Ungültiges JSON.

Didaktische Chance:

Die Lernenden erkennen, dass textbasierte Formate exakt eingehalten werden müssen.

---

### Fehler 5: Laden ohne Validierung

Problem:
Die Datei wird blind eingelesen.

Folge:
Spätere Fehler sind schwer nachvollziehbar.

Didaktische Frage:

> Warum ist eine frühe Plausibilitätsprüfung oft besser als ein später Absturz an ganz anderer Stelle?

---

## Musterlösung als Umsetzungsfahrplan

Die Musterlösung kann in dieser Reihenfolge entwickelt werden:

### Phase 1: Modell erweitern

- `Person` bekommt `id`
- `Messung` bekommt `id` und `personId`
- Konstruktoren und Getter/Setter werden angepasst

### Phase 2: Persistenzklasse entwickeln

- Methode zum Schreiben des Schemas
- Methode zum Schreiben der Daten
- Methode zum Laden der Daten
- Hilfsmethoden für Escaping, atomisches Schreiben und Minimalvalidierung

### Phase 3: Controller anbinden

- `BmiManager` erhält Methoden für Speichern und Laden
- ID-Zähler werden sauber verwaltet
- geladene Daten werden als neue Arbeitsbasis gesetzt

### Phase 4: GUI ergänzen

- Buttons für Speichern und Laden
- Erfolgs- und Fehlermeldungen
- Anzeige nach dem Laden aktualisieren

### Phase 5: Tests ergänzen

- Roundtrip-Test: speichern und wieder laden
- Kontrolltest für Statistik nach dem Laden
- Test für Strukturtrennung

---

## Unterrichtlich sinnvolle Zwischenfragen

Diese Fragen helfen, die Lösung nicht nur technisch, sondern auch fachlich zu verstehen:

1. Warum reicht es nicht, einfach `toString()` in eine Datei zu schreiben?
2. Warum speichern wir Statistik nicht dauerhaft mit?
3. Warum brauchen wir IDs, obwohl wir doch schon Objekte haben?
4. Warum ist es sinnvoll, Personen und Messungen getrennt zu speichern?
5. Welche Rolle hat der Controller in einer MVC-Anwendung mit Persistenz?
6. Warum ist die Trennung von Schema und Daten auch für Fehlersuche hilfreich?

---

## Erwartung an eine gute Schülerlösung

Eine gute Lösung erkennt man daran, dass:

- die bestehende Version 4 nicht zerstört, sondern sinnvoll erweitert wird,
- Person und Messung über IDs verbunden bleiben,
- Struktur und Daten in getrennten JSON-Dateien liegen,
- Statistik aus geladenen Daten wieder korrekt berechnet wird,
- Speichern und Laden über eigene Methoden und eine eigene Persistenzklasse laufen,
- die App nach einem Neustart wieder denselben fachlichen Zustand herstellen kann.

---

## Zusammenfassung für Schülerinnen und Schüler

Version 5 ist der Schritt von einer reinen Objektverwaltung zu einer kleinen datenbankartigen Anwendung.

Die wichtigste fachliche Idee lautet:

> Daten sollen nicht nur existieren, solange das Programm läuft.
> Sie sollen strukturiert, nachvollziehbar und dauerhaft gespeichert werden.

Die wichtigste technische Idee lautet:

> Wir speichern nicht einfach alles in eine Datei,
> sondern trennen Struktur und Nutzdaten,
> normalisieren die Daten,
> und stellen Beziehungen beim Laden wieder her.

Wenn die Schülerinnen und Schüler diese Gedanken verstanden haben, ist das Lernziel von Version 5 erreicht.
