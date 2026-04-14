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

## Musterlösung: Wohin kommt welcher Quellcode?

Der wichtigste Punkt für Version 5 ist die saubere Verteilung des Codes auf die vorhandenen Klassen.
Die JSON-Logik kommt **nicht** komplett in eine einzige Datei und auch **nicht** in die GUI.

Für diese Version ist die Aufteilung so sinnvoll:

| Datei | Aufgabe |
|---|---|
| `src/start/Person.java` | Personendaten und Liste der Messungen |
| `src/start/Messung.java` | Einzelne Messung mit `id`, `personId`, Zeitstempel und BMI |
| `src/start/BmiManager.java` | Controller: erstellt Personen/Messungen und ruft Speichern/Laden auf |
| `src/start/JsonDateiDatenbankSpeicher.java` | komplette JSON-Persistenz |
| `src/start/MainWindow.java` | Buttons `Speichern` und `Laden`, Anzeige aktualisieren |

### Merksatz

- `Person` und `Messung` speichern **Fachdaten**.
- `BmiManager` steuert den Ablauf.
- `JsonDateiDatenbankSpeicher` schreibt und liest JSON.
- `MainWindow` reagiert nur auf Benutzeraktionen.

### Datei 1: `Person.java`

In diese Klasse gehören:

- das Feld `id`,
- die persönlichen Stammdaten,
- die Liste der Messungen,
- Methoden wie `addMessung()` und `getMessungen()`.

```java
package start;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private int id;
    private String vorname;
    private String nachname;
    private int alter;
    private String geschlecht;
    private String email;
    private List<Messung> messungen;

    public Person(String vorname, String nachname, int alter, String geschlecht, String email) {
        this(0, vorname, nachname, alter, geschlecht, email);
    }

    public Person(int id, String vorname, String nachname, int alter, String geschlecht, String email) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
        this.geschlecht = geschlecht;
        this.email = email;
        this.messungen = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public int getAlter() {
        return alter;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public String getEmail() {
        return email;
    }

    public void addMessung(Messung messung) {
        if (messung == null) {
            throw new IllegalArgumentException("Messung darf nicht null sein");
        }
        messungen.add(messung);
    }

    public List<Messung> getMessungen() {
        return new ArrayList<>(messungen);
    }

    public String getFullName() {
        return vorname + " " + nachname;
    }
}
```

### Datei 2: `Messung.java`

In diese Klasse gehören:

- `id` als Primärschlüssel,
- `personId` als Fremdschlüssel,
- Gewicht, Größe und Zeitstempel,
- BMI-Berechnung über `Bmirechner`.

```java
package start;

import java.time.LocalDateTime;

public class Messung {

    private int id;
    private int personId;
    private double gewicht;
    private double groesse;
    private LocalDateTime zeitstempel;
    private double bmi;
    private String kategorie;
    private Bmirechner rechner;

    public Messung(double gewicht, double groesse) {
        this(0, 0, gewicht, groesse, LocalDateTime.now());
    }

    public Messung(int id, int personId, double gewicht, double groesse, LocalDateTime zeitstempel) {
        this.id = id;
        this.personId = personId;
        this.gewicht = gewicht;
        this.groesse = groesse;
        this.zeitstempel = zeitstempel;

        this.rechner = new Bmirechner();
        this.bmi = rechner.berechne(gewicht, groesse);
        rechner.interpretiere();
        this.kategorie = rechner.getKategorie();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public double getGewicht() {
        return gewicht;
    }

    public double getGroesse() {
        return groesse;
    }

    public LocalDateTime getZeitstempel() {
        return zeitstempel;
    }

    public double getBmi() {
        return bmi;
    }

    public String getKategorie() {
        return kategorie;
    }
}
```

### Datei 3: `JsonDateiDatenbankSpeicher.java`

Hier gehört der gesamte JSON-Code hinein.
Diese Klasse ist die eigentliche Musterlösung für Version 5.

Wichtige Methoden dieser Klasse:

- `speichern(List<Person> personen, Path ordnerPfad)`
- `laden(Path ordnerPfad)`
- `erzeugeSchemaJson()`
- `erzeugeDatenJson(List<Person> personen)`
- `parseDaten(String daten)`
- `atomarSchreiben(Path zielDatei, String inhalt)`
- `escapeJson(String text)`

```java
package start;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDateiDatenbankSpeicher {

    private static final String SCHEMA_DATEI = "bmiapp.schema.json";
    private static final String DATEN_DATEI = "bmiapp.data.json";

    public void speichern(List<Person> personen, Path ordnerPfad) throws IOException {
        Files.createDirectories(ordnerPfad);
        atomarSchreiben(ordnerPfad.resolve(SCHEMA_DATEI), erzeugeSchemaJson());
        atomarSchreiben(ordnerPfad.resolve(DATEN_DATEI), erzeugeDatenJson(personen));
    }

    public List<Person> laden(Path ordnerPfad) throws IOException {
        String schema = Files.readString(ordnerPfad.resolve(SCHEMA_DATEI), StandardCharsets.UTF_8);
        String daten = Files.readString(ordnerPfad.resolve(DATEN_DATEI), StandardCharsets.UTF_8);

        pruefeSchemaMinimal(schema);
        pruefeDatenMinimal(daten);

        return parseDaten(daten);
    }

    private String erzeugeSchemaJson() {
        return """
            {
              "database": "bmiapp",
              "version": 5,
              "entities": [
                {
                  "name": "personen",
                  "primaryKey": "id"
                },
                {
                  "name": "messungen",
                  "primaryKey": "id",
                  "foreignKey": "personId"
                }
              ]
            }
            """;
    }

    private String erzeugeDatenJson(List<Person> personen) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"personen\": [\n");

        for (int i = 0; i < personen.size(); i++) {
            Person p = personen.get(i);
            json.append("    {")
                .append("\"id\": ").append(p.getId()).append(", ")
                .append("\"vorname\": \"").append(escapeJson(p.getVorname())).append("\", ")
                .append("\"nachname\": \"").append(escapeJson(p.getNachname())).append("\", ")
                .append("\"alter\": ").append(p.getAlter()).append(", ")
                .append("\"geschlecht\": \"").append(escapeJson(p.getGeschlecht())).append("\", ")
                .append("\"email\": \"").append(escapeJson(p.getEmail())).append("\"")
                .append("}");
            if (i < personen.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ],\n");
        json.append("  \"messungen\": [\n");

        List<Messung> alleMessungen = new ArrayList<>();
        for (Person p : personen) {
            alleMessungen.addAll(p.getMessungen());
        }

        for (int i = 0; i < alleMessungen.size(); i++) {
            Messung m = alleMessungen.get(i);
            json.append("    {")
                .append("\"id\": ").append(m.getId()).append(", ")
                .append("\"personId\": ").append(m.getPersonId()).append(", ")
                .append("\"gewicht\": ").append(m.getGewicht()).append(", ")
                .append("\"groesse\": ").append(m.getGroesse()).append(", ")
                .append("\"zeitstempel\": \"").append(m.getZeitstempel()).append("\"")
                .append("}");
            if (i < alleMessungen.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ]\n");
        json.append("}\n");
        return json.toString();
    }

    private List<Person> parseDaten(String daten) throws IOException {
        String personenArray = extrahiereArrayInhalt(daten, "personen");
        String messungenArray = extrahiereArrayInhalt(daten, "messungen");

        List<String> personenObjekte = splitJsonObjekte(personenArray);
        List<String> messungsObjekte = splitJsonObjekte(messungenArray);

        List<Person> personen = new ArrayList<>();
        Map<Integer, Person> personenNachId = new HashMap<>();

        // 1. Personen lesen und in Map nach ID ablegen
        for (String pObj : personenObjekte) {
            if (pObj.isBlank()) {
                continue;
            }

            Map<String, String> werte = parseObjektFelder(pObj);
            int id = parseInt(werte, "id");

            Person person = new Person(
                id,
                parseString(werte, "vorname"),
                parseString(werte, "nachname"),
                parseInt(werte, "alter"),
                parseString(werte, "geschlecht"),
                parseString(werte, "email")
            );

            personen.add(person);
            personenNachId.put(id, person);
        }

        // 2. Messungen lesen und über personId zuordnen
        for (String mObj : messungsObjekte) {
            if (mObj.isBlank()) {
                continue;
            }

            Map<String, String> werte = parseObjektFelder(mObj);
            int personId = parseInt(werte, "personId");
            Person person = personenNachId.get(personId);

            if (person == null) {
                throw new IOException("Messung verweist auf unbekannte personId: " + personId);
            }

            int id = parseInt(werte, "id");
            double gewicht = parseDouble(werte, "gewicht");
            double groesse = parseDouble(werte, "groesse");
            LocalDateTime zeitstempel = LocalDateTime.parse(parseString(werte, "zeitstempel"));

            Messung messung = new Messung(id, personId, gewicht, groesse, zeitstempel);
            person.addMessung(messung);
        }

        return personen;
    }

    // Hilfsmethode 1: JSON-Array-Inhalt für einen Container extrahieren
    // Beispiel: bei arrayName="personen" wird nur der Inhalt zwischen [ ... ] zurückgegeben
    private String extrahiereArrayInhalt(String json, String arrayName) throws IOException {
        String marker = "\"" + arrayName + "\"";
        int markerIndex = json.indexOf(marker);
        if (markerIndex < 0) {
            throw new IOException("Array fehlt: " + arrayName);
        }

        int start = json.indexOf('[', markerIndex);
        if (start < 0) {
            throw new IOException("Ungueltiges JSON: '[' fehlt fuer " + arrayName);
        }

        int tiefe = 0;
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') {
                tiefe++;
            } else if (c == ']') {
                tiefe--;
                if (tiefe == 0) {
                    return json.substring(start + 1, i);
                }
            }
        }

        throw new IOException("Ungueltiges JSON: ']' fehlt fuer " + arrayName);
    }

    // Hilfsmethode 2: Ein Array-Inhalt wird in einzelne JSON-Objekte aufgeteilt
    // Beispiel: {..},{..},{..} -> Liste mit 3 Objekt-Strings
    private List<String> splitJsonObjekte(String arrayInhalt) throws IOException {
        List<String> objekte = new ArrayList<>();
        int objektStart = -1;
        int tiefe = 0;

        for (int i = 0; i < arrayInhalt.length(); i++) {
            char c = arrayInhalt.charAt(i);
            if (c == '{') {
                if (tiefe == 0) {
                    objektStart = i;
                }
                tiefe++;
            } else if (c == '}') {
                tiefe--;
                if (tiefe < 0) {
                    throw new IOException("Ungueltiges JSON: zu viele '}'");
                }
                if (tiefe == 0 && objektStart >= 0) {
                    objekte.add(arrayInhalt.substring(objektStart, i + 1));
                    objektStart = -1;
                }
            }
        }

        if (tiefe != 0) {
            throw new IOException("Ungueltiges JSON: unausgeglichene Objekte");
        }

        return objekte;
    }

    // Hilfsmethode 3: Einzelnes JSON-Objekt in Map<String, String> umwandeln
    // Beispiel: {"id":1,"vorname":"Mia"} -> Map mit id=1, vorname=Mia
    private Map<String, String> parseObjektFelder(String objektJson) throws IOException {
        Map<String, String> werte = new HashMap<>();
        String inhalt = objektJson.trim();

        if (!inhalt.startsWith("{") || !inhalt.endsWith("}")) {
            throw new IOException("Ungueltiges Objekt-JSON: " + objektJson);
        }

        inhalt = inhalt.substring(1, inhalt.length() - 1).trim();
        if (inhalt.isEmpty()) {
            return werte;
        }

        List<String> paare = splitTopLevel(inhalt);
        for (String paar : paare) {
            int doppelpunktIndex = indexOfTopLevelColon(paar);
            if (doppelpunktIndex < 0) {
                throw new IOException("Ungueltiges Schluessel-Wert-Paar: " + paar);
            }

            String keyRaw = paar.substring(0, doppelpunktIndex).trim();
            String valueRaw = paar.substring(doppelpunktIndex + 1).trim();

            String key = unquote(keyRaw);
            String value = valueRaw;
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = unescapeJson(unquote(value));
            }
            werte.put(key, value);
        }

        return werte;
    }

    // Hilfsmethode: trennt Schluessel-Wert-Paare auf Top-Level (ohne Trennung innerhalb von Strings)
    private List<String> splitTopLevel(String text) {
        List<String> teile = new ArrayList<>();
        int start = 0;
        boolean inString = false;
        boolean escaped = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (c == '"') {
                inString = !inString;
                continue;
            }
            if (c == ',' && !inString) {
                teile.add(text.substring(start, i).trim());
                start = i + 1;
            }
        }

        teile.add(text.substring(start).trim());
        return teile;
    }

    // Hilfsmethode: findet den Doppelpunkt eines Schluessel-Wert-Paars auf Top-Level
    private int indexOfTopLevelColon(String text) {
        boolean inString = false;
        boolean escaped = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (c == '"') {
                inString = !inString;
                continue;
            }
            if (c == ':' && !inString) {
                return i;
            }
        }

        return -1;
    }

    // Hilfsmethode: entfernt die umschliessenden Anfuehrungszeichen
    private String unquote(String text) throws IOException {
        String trimmed = text.trim();
        if (!trimmed.startsWith("\"") || !trimmed.endsWith("\"")) {
            throw new IOException("Erwarteter JSON-String, erhalten: " + text);
        }
        return trimmed.substring(1, trimmed.length() - 1);
    }

    private String unescapeJson(String text) {
        StringBuilder result = new StringBuilder();
        boolean escaped = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!escaped) {
                if (c == '\\') {
                    escaped = true;
                } else {
                    result.append(c);
                }
            } else {
                switch (c) {
                    case 'n' -> result.append('\n');
                    case 'r' -> result.append('\r');
                    case 't' -> result.append('\t');
                    case '"' -> result.append('"');
                    case '\\' -> result.append('\\');
                    default -> result.append(c);
                }
                escaped = false;
            }
        }

        if (escaped) {
            result.append('\\');
        }

        return result.toString();
    }

    private void pruefeSchemaMinimal(String schema) throws IOException {
        if (!schema.contains("\"personen\"") || !schema.contains("\"messungen\"")) {
            throw new IOException("Schema-Datei ungueltig");
        }
    }

    private void pruefeDatenMinimal(String daten) throws IOException {
        if (!daten.contains("\"personen\"") || !daten.contains("\"messungen\"")) {
            throw new IOException("Daten-Datei ungueltig");
        }
    }

    private void atomarSchreiben(Path zielDatei, String inhalt) throws IOException {
        Path tempDatei = Path.of(zielDatei.toString() + ".tmp");
        Files.writeString(tempDatei, inhalt, StandardCharsets.UTF_8);
        Files.move(tempDatei, zielDatei,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE);
    }

    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}
```

### Datei 4: `BmiManager.java`

In den Controller gehört **kein** manueller JSON-String-Aufbau.
Der Controller delegiert an `JsonDateiDatenbankSpeicher`.

```java
package start;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BmiManager {

    private List<Person> personenListe;
    private Person aktuellePerson;
    private int naechstePersonId;
    private int naechsteMessungId;
    private final JsonDateiDatenbankSpeicher dateiSpeicher;

    public BmiManager() {
        this.personenListe = new ArrayList<>();
        this.aktuellePerson = null;
        this.naechstePersonId = 1;
        this.naechsteMessungId = 1;
        this.dateiSpeicher = new JsonDateiDatenbankSpeicher();
    }

    public Person erstellePerson(String vorname, String nachname, int alter,
            String geschlecht, String email) {
        Person person = new Person(naechstePersonId++, vorname, nachname, alter, geschlecht, email);
        personenListe.add(person);
        return person;
    }

    public Messung erstelleMessung(double gewicht, double groesse) {
        if (aktuellePerson == null) {
            throw new IllegalStateException("Keine Person ausgewählt");
        }

        Messung messung = new Messung(naechsteMessungId++, aktuellePerson.getId(), gewicht, groesse);
        aktuellePerson.addMessung(messung);
        return messung;
    }

    public void speichereDaten(Path ordnerPfad) throws IOException {
        dateiSpeicher.speichern(personenListe, ordnerPfad);
    }

    public void ladeDaten(Path ordnerPfad) throws IOException {
        List<Person> geladenePersonen = dateiSpeicher.laden(ordnerPfad);
        this.personenListe = new ArrayList<>(geladenePersonen);
        this.aktuellePerson = personenListe.isEmpty() ? null : personenListe.get(0);
        aktualisiereIdZaehler();
    }

    private void aktualisiereIdZaehler() {
        int maxPersonId = 0;
        int maxMessungId = 0;

        for (Person p : personenListe) {
            if (p.getId() > maxPersonId) {
                maxPersonId = p.getId();
            }
            for (Messung m : p.getMessungen()) {
                if (m.getId() > maxMessungId) {
                    maxMessungId = m.getId();
                }
            }
        }

        naechstePersonId = maxPersonId + 1;
        naechsteMessungId = maxMessungId + 1;
    }
}
```

### Datei 5: `MainWindow.java`

In die GUI kommen nur die beiden Auslöser für Speichern und Laden.
Die GUI kennt also den Speicherordner und ruft den Controller auf.

```java
package start;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;

public class MainWindow {

    private static final Path STANDARD_DATEN_ORDNER = Paths.get("data", "bmiapp");
    private BmiManager manager;

    private void speichereDaten() {
        try {
            manager.speichereDaten(STANDARD_DATEN_ORDNER);
            JOptionPane.showMessageDialog(null,
                "Daten erfolgreich gespeichert in:\n" + STANDARD_DATEN_ORDNER);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                "Fehler beim Speichern:\n" + e.getMessage());
        }
    }

    private void ladeDaten() {
        try {
            manager.ladeDaten(STANDARD_DATEN_ORDNER);
            aktualisierePersonenAuswahl();
            aktualisiereAnzeige();
            JOptionPane.showMessageDialog(null,
                "Daten erfolgreich geladen aus:\n" + STANDARD_DATEN_ORDNER);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                "Fehler beim Laden:\n" + e.getMessage());
        }
    }
}
```

### Reihenfolge beim Einbauen

Wenn die Klasse noch nicht fertig ist, dann sollte die Musterlösung in genau dieser Reihenfolge umgesetzt werden:

1. `Person.java` um `id` ergänzen.
2. `Messung.java` um `id` und `personId` ergänzen.
3. `JsonDateiDatenbankSpeicher.java` vollständig anlegen.
4. `BmiManager.java` um `speichereDaten()` und `ladeDaten()` erweitern.
5. `MainWindow.java` um zwei Buttons und zwei Methoden ergänzen.

### Was nicht in welche Klasse gehört

- Kein JSON-String-Aufbau in `MainWindow`
- Kein Dateizugriff in `Person`
- Kein GUI-Code in `JsonDateiDatenbankSpeicher`
- Keine Statistik dauerhaft in JSON speichern

---

## Schritt 1: Das Datenmodell für Persistenz vorbereiten

Bevor gespeichert werden kann, brauchen die Objekte stabile Identifikatoren.

### Aufgabe

Ergänze `Person` und `Messung` um eine ID.

### Warum ist das nötig?

Solange Objekte nur im RAM leben, kann Java Referenzen verwalten.
Nach dem Speichern in einer Datei existieren diese Referenzen aber nicht mehr.
Deshalb brauchen wir technische Schlüssel:

- `id` für Personen
- `id` für Messungen
- `personId` als Fremdschlüssel in der Messung

### Didaktische Erklärung

Das ist ein zentraler Schritt vom objektorientierten Denken zum datenbankorientierten Denken.
Die Beziehung zwischen Person und Messung darf nicht nur im Kopf oder im Java-Objekt existieren.
Sie muss explizit speicherbar werden.

### Implementierung: Klasse Person erweitern

**Schritt 1a: Vorlage für Schüler**

Ergänze in der Klasse `Person`:

```java
// TODO: ID-Feld hinzufügen (private int id)
// TODO: Getter für id schreiben: public int getId() { ... }
// TODO: Setter für id schreiben: public void setId(int id) { ... }
```

Bei der Initialisierung neuer Personen im Controller sollte die ID 0 sein:

```java
// TODO: Im Konstruktor: this.id = 0;
```

**Schritt 1b: Referenzlösung**

Hier ist die vollständige Implementierung für Klasse `Person`:

```java
public class Person {
    private int id;  // Eindeutiger Schlüssel in der Datenbank
    private String vorname;
    private String nachname;
    private int alter;
    private String geschlecht;
    private String email;
    private List<Messung> messungen;
    
    // Konstruktor (ID wird anfangs auf 0 gesetzt, wird später vom Controller vergeben)
    public Person(String vorname, String nachname, int alter, 
                  String geschlecht, String email) {
        this.id = 0;  // Wird später durch DB vergeben
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
        this.geschlecht = geschlecht;
        this.email = email;
        this.messungen = new ArrayList<>();
    }
    
    // Getter und Setter für ID
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    // Weitere Getter/Setter bleiben wie zuvor...
    public String getVorname() { return vorname; }
    public void setVorname(String vorname) { this.vorname = vorname; }
    
    public String getNachname() { return nachname; }
    public void setNachname(String nachname) { this.nachname = nachname; }
    
    public int getAlter() { return alter; }
    public void setAlter(int alter) { this.alter = alter; }
    
    public String getGeschlecht() { return geschlecht; }
    public void setGeschlecht(String geschlecht) { this.geschlecht = geschlecht; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public List<Messung> getMessungen() { return messungen; }
    
    public void addMessung(Messung messung) {
        messungen.add(messung);
    }
}
```

### Implementierung: Klasse Messung erweitern

**Schritt 1c: Vorlage für Schüler**

Ergänze in der Klasse `Messung`:

```java
// TODO: ID-Feld hinzufügen (private int id)
// TODO: personId-Feld hinzufügen (private int personId)
// TODO: Getter und Setter für beide Felder schreiben

```

**Schritt 1d: Referenzlösung**

Hier ist die vollständige Implementierung für Klasse `Messung`:

```java
public class Messung {
    private int id;           // Eindeutiger Schlüssel in der Datenbank
    private int personId;     // Fremdschlüssel zur Person
    private double gewicht;
    private double groesse;
    private LocalDateTime zeitstempel;
    
    // Konstruktor (ID und personId werden anfangs auf 0 gesetzt, 
    // werden später vom Controller vergeben)
    public Messung(double gewicht, double groesse) {
        this.id = 0;              // Wird später durch DB vergeben
        this.personId = 0;        // Wird später vom Controller gesetzt
        this.gewicht = gewicht;
        this.groesse = groesse;
        this.zeitstempel = LocalDateTime.now();
    }
    
    // Getter und Setter für ID
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    // Getter und Setter für personId (Fremdschlüssel)
    public int getPersonId() {
        return personId;
    }
    
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    
    // Weitere Getter/Setter bleiben wie zuvor...
    public double getGewicht() { return gewicht; }
    public void setGewicht(double gewicht) { this.gewicht = gewicht; }
    
    public double getGroesse() { return groesse; }
    public void setGroesse(double groesse) { this.groesse = groesse; }
    
    public LocalDateTime getZeitstempel() { return zeitstempel; }
    public void setZeitstempel(LocalDateTime zeitstempel) { 
        this.zeitstempel = zeitstempel; 
    }
    
    // Hilfsmethode: BMI berechnen
    public double getBmi() {
        if (groesse <= 0) return 0;
        return gewicht / (groesse * groesse);
    }
}
```

### Wichtige Hinweise

- IDs müssen eindeutig sein. Der Controller verwaltet einen ID-Zähler.
- Beim Laden aus JSON müssen die IDs wieder übernommen werden (sonst sind neue Messungen nach dem Laden nicht korrekt zugeordnet).
- Der Controller sollte neue IDs vergeben können.
- `personId` in `Messung` ist der Fremdschlüssel, der die Beziehung zur Person speichert.

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

### Vorlage: Grundstruktur der Klasse

Hier ist das Grundgerüst, das Schüler ausfüllen können:

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Persistenzschicht für BMI-Anwendung.
 * 
 * Verantwortung:
 * - Schema als JSON speichern
 * - Daten normalisiert speichern (Personen und Messungen getrennt)
 * - Schema validieren beim Laden
 * - Daten laden und wieder verknüpfen
 */
public class JsonDateiDatenbankSpeicher {
    
    // Dateinamen
    private static final String SCHEMA_DATEI = "bmiapp.schema.json";
    private static final String DATA_DATEI = "bmiapp.data.json";
    
    // TODO: Methode schreiben: public void speichere(Path ordner, List<Person> personen)
    // TODO: Methode schreiben: public List<Person> lade(Path ordner)
    // TODO: Hilfsmethode: private void schreibeSchema(Path ordner)
    // TODO: Hilfsmethode: private void schreibeDaten(Path ordner, List<Person> personen)
    // TODO: Hilfsmethode: private void validiereSchema(String schemaJson)
    // TODO: Hilfsmethode: private String escapeJson(String text)
}
```

### Referenzlösung: Vollständige Klasse

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Persistenzschicht für BMI-Anwendung mit JSON-Dateispeicher.
 * 
 * Architektur:
 * - Schema und Daten werden getrennt gespeichert
 * - Schema: bmiapp.schema.json (ändert sich selten)
 * - Daten: bmiapp.data.json (ändert sich häufig)
 * - Personen und Messungen sind genorma lisiert gespeichert
 * 
 * Didaktisches Ziel:
 * Lernende verstehen die Trennung von Modell und Datensatz
 * sowie die Rekonstruktion von 1:N-Beziehungen aus flachen Daten.
 */
public class JsonDateiDatenbankSpeicher {
    
    private static final String SCHEMA_DATEI = "bmiapp.schema.json";
    private static final String DATA_DATEI = "bmiapp.data.json";
    private static final String TMP_SUFFIX = ".tmp";
    
    /**
     * Speichert alle Personen und deren Messungen in Schema + Datendatei.
     * 
     * @param ordner Zielverzeichnis für die JSON-Dateien
     * @param personen Liste aller Personen mit ihren Messungen
     * @throws IOException bei Dateifehlern
     */
    public void speichere(Path ordner, List<Person> personen) throws IOException {
        // Sicherstelle, dass das Verzeichnis existiert
        Files.createDirectories(ordner);
        
        // Schema erzeugen (ändert sich meist nicht)
        schreibeSchema(ordner);
        
        // Aktuelle Daten speichern
        schreibeDaten(ordner, personen);
    }
    
    /**
     * Lädt all Personen und ihre Messungen aus Schema + Datendatei.
     * 
     * @param ordner Quellverzeichnis mit JSON-Dateien
     * @return Liste aller Personen mit zugeordneten Messungen
     * @throws IOException bei Dateifehlern
     */
    public List<Person> lade(Path ordner) throws IOException {
        Path schemaPath = ordner.resolve(SCHEMA_DATEI);
        Path dataPath = ordner.resolve(DATA_DATEI);
        
        // Validierung: Dateien müssen existieren
        if (!Files.exists(schemaPath)) {
            throw new FileNotFoundException("Schema-Datei nicht gefunden: " + schemaPath);
        }
        if (!Files.exists(dataPath)) {
            throw new FileNotFoundException("Daten-Datei nicht gefunden: " + dataPath);
        }
        
        // Schema laden und validieren
        String schemaJson = new String(Files.readAllBytes(schemaPath), 
                                      StandardCharsets.UTF_8);
        validiereSchema(schemaJson);
        
        // Daten laden
        String dataJson = new String(Files.readAllBytes(dataPath), 
                                    StandardCharsets.UTF_8);
        
        // Objekte rekonstruieren
        return ladenUndRekonstruieren(dataJson);
    }
    
    /**
     * Schreibt die Schema-Datei.
     * Das Schema beschreibt die Struktur (nicht die konkreten Daten).
     */
    private void schreibeSchema(Path ordner) throws IOException {
        String schemaJson = """
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
}""";
        
        // Sicher schreiben: über temporäre Datei
        Path schemaPath = ordner.resolve(SCHEMA_DATEI);
        Path tmpPath = ordner.resolve(SCHEMA_DATEI + TMP_SUFFIX);
        
        Files.writeString(tmpPath, schemaJson, StandardCharsets.UTF_8);
        Files.move(tmpPath, schemaPath, StandardCopyOption.REPLACE_EXISTING);
    }
    
    /**
     * Schreibt die Daten-Datei mit normalisierten Personen und Messungen.
     */
    private void schreibeDaten(Path ordner, List<Person> personen) 
            throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"personen\": [\n");
        
        // Personen speichern
        for (int i = 0; i < personen.size(); i++) {
            Person p = personen.get(i);
            json.append("    {\n");
            json.append("      \"id\": ").append(p.getId()).append(",\n");
            json.append("      \"vorname\": \"").append(escapeJson(p.getVorname()))
                .append("\",\n");
            json.append("      \"nachname\": \"").append(escapeJson(p.getNachname()))
                .append("\",\n");
            json.append("      \"alter\": ").append(p.getAlter()).append(",\n");
            json.append("      \"geschlecht\": \"")
                .append(escapeJson(p.getGeschlecht())).append("\",\n");
            json.append("      \"email\": \"").append(escapeJson(p.getEmail()))
                .append("\"\n");
            json.append("    }");
            if (i < personen.size() - 1) json.append(",");
            json.append("\n");
        }
        
        json.append("  ],\n");
        json.append("  \"messungen\": [\n");
        
        // Messungen speichern (flach, ohne Verschachtelung)
        List<Messung> alleMessungen = new ArrayList<>();
        for (Person p : personen) {
            alleMessungen.addAll(p.getMessungen());
        }
        
        for (int i = 0; i < alleMessungen.size(); i++) {
            Messung m = alleMessungen.get(i);
            json.append("    {\n");
            json.append("      \"id\": ").append(m.getId()).append(",\n");
            json.append("      \"personId\": ").append(m.getPersonId())
                .append(",\n");
            json.append("      \"gewicht\": ").append(m.getGewicht()).append(",\n");
            json.append("      \"groesse\": ").append(m.getGroesse()).append(",\n");
            json.append("      \"zeitstempel\": \"")
                .append(m.getZeitstempel().format(DateTimeFormatter.ISO_DATE_TIME))
                .append("\"\n");
            json.append("    }");
            if (i < alleMessungen.size() - 1) json.append(",");
            json.append("\n");
        }
        
        json.append("  ]\n");
        json.append("}\n");
        
        // Sicher schreiben über temporäre Datei
        Path dataPath = ordner.resolve(DATA_DATEI);
        Path tmpPath = ordner.resolve(DATA_DATEI + TMP_SUFFIX);
        
        Files.writeString(tmpPath, json.toString(), StandardCharsets.UTF_8);
        Files.move(tmpPath, dataPath, StandardCopyOption.REPLACE_EXISTING);
    }
    
    /**
     * Validiert, dass das Schema die erwartete Struktur hat.
     */
    private void validiereSchema(String schemaJson) throws IOException {
        // Minimalprüfung: Erwartete Entitäten müssen vorhanden sein
        if (!schemaJson.contains("\"personen\"")) {
            throw new IOException("Schema-Fehler: Entität 'personen' nicht gefunden");
        }
        if (!schemaJson.contains("\"messungen\"")) {
            throw new IOException("Schema-Fehler: Entität 'messungen' nicht gefunden");
        }
    }
    
    /**
     * Lädt die Daten und rekonstruiert die Objektbeziehungen.
     */
    private List<Person> ladenUndRekonstruieren(String dataJson) 
            throws IOException {
        List<Person> personen = new ArrayList<>();
        Map<Integer, Person> personenNachId = new HashMap<>();
        
        // *** VEREINFACHTE IMPLEMENTIERUNG FÜR DIDAKTISCHE KLARHEIT ***
        // (In echter Anwendung würde man einen JSON-Parser wie Gson verwenden)
        
        // Personen extrahieren (Pattern: "vorname": "Text")
        String[] personBlöcke = dataJson.split("\"personen\"")[1]
            .split("\"messungen\"")[0]
            .split("},");
        
        for (String block : personBlöcke) {
            if (!block.trim().isEmpty() && block.contains("\"vorname\"")) {
                try {
                    @SuppressWarnings("unused")
                    int id = extraziereInt(block, "\"id\":");
                    String vorname = extraziereString(block, "\"vorname\":");
                    String nachname = extraziereString(block, "\"nachname\":");
                    int alter = extraziereInt(block, "\"alter\":");
                    String geschlecht = extraziereString(block, "\"geschlecht\":");
                    String email = extraziereString(block, "\"email\":");
                    
                    Person p = new Person(vorname, nachname, alter, geschlecht, email);
                    p.setId(id);
                    personen.add(p);
                    personenNachId.put(id, p);
                } catch (Exception e) {
                    throw new IOException("Fehler beim Laden von Person: " + e.getMessage());
                }
            }
        }
        
        // Messungen extrahieren und verknüpfen
        String[] messungBlöcke = dataJson.split("\"messungen\"")[1].split("},");
        
        for (String block : messungBlöcke) {
            if (!block.trim().isEmpty() && block.contains("\"personId\"")) {
                try {
                    int id = extraziereInt(block, "\"id\":");
                    int personId = extraziereInt(block, "\"personId\":");
                    double gewicht = extraziereDouble(block, "\"gewicht\":");
                    double groesse = extraziereDouble(block, "\"groesse\":");
                    String zeitstempel = extraziereString(block, "\"zeitstempel\":");
                    
                    Messung m = new Messung(gewicht, groesse);
                    m.setId(id);
                    m.setPersonId(personId);
                    m.setZeitstempel(LocalDateTime.parse(zeitstempel));
                    
                    // Messung zur Person hinzufügen (Beziehung wiederherstellen!)
                    Person person = personenNachId.get(personId);
                    if (person != null) {
                        person.addMessung(m);
                    } else {
                        throw new IOException(
                            "Messung hat ungültigen Fremdschlüssel: personId=" + personId);
                    }
                } catch (Exception e) {
                    throw new IOException("Fehler beim Laden von Messung: " + e.getMessage());
                }
            }
        }
        
        return personen;
    }
    
    /**
     * Nur für didaktische Vereinfachung!
     * In echten Projekten: Gson, Jackson oder JSON-P verwenden.
     */
    private int extraziereInt(String text, String field) {
        int start = text.indexOf(field) + field.length();
        int ende = text.indexOf(",", start);
        if (ende == -1) ende = text.indexOf("}", start);
        return Integer.parseInt(text.substring(start, ende).trim());
    }
    
    private double extraziereDouble(String text, String field) {
        int start = text.indexOf(field) + field.length();
        int ende = text.indexOf(",", start);
        if (ende == -1) ende = text.indexOf("}", start);
        return Double.parseDouble(text.substring(start, ende).trim());
    }
    
    private String extraziereString(String text, String field) {
        int start = text.indexOf(field) + field.length();
        start = text.indexOf("\"", start) + 1;
        int ende = text.indexOf("\"", start);
        String result = text.substring(start, ende);
        // Unescaped
        result = result.replace("\\\"", "\"");
        result = result.replace("\\\\", "\\");
        return result;
    }
    
    /**
     * Escapt Sonderzeichen für sichere JSON-Strings.
     */
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}
```

### Wichtiges Hinweisschild für Schüler

**Anmerkung zur Vereinfachung:**

Diese Implementierung nutzt String-Parsing statt eines richtigen JSON-Parsers (Gson, Jackson).
Das ist für Lernziele didaktisch wertvoll:
- Man sieht genau, wie der Aufbau des JSON funktioniert
- Man versteht das Escaping und die Struktur

**In echten Projekten** sollte man immer eine spezialisierte Bibliothek verwenden!

---

## Schritt 3: Das Schema schreiben

Zuerst wird beim Speichern die Strukturdatei erzeugt.

### Ziel

Die Datei `bmiapp.schema.json` beschreibt:

- welche Entitäten es gibt,
- welche Felder Pflichtfelder sind,
- welche Felder Primär- oder Fremdschlüssel sind.

### Didaktischer Schwerpunkt

Hier sollen die Lernenden erkennen:

- Die Strukturdatei speichert keine konkreten BMI-Werte.
- Sie beschreibt nur das Gerüst.
- Ein Datenmodell ist etwas anderes als eine Datensammlung.

Das ist oft ein gedanklicher Sprung und sollte bewusst thematisiert werden.

### Implementierung: Methode schreibeSchema()

**Vorlage für Schüler:**

```java
/**
 * Schreibt die Schema-Datei (Strukturdefinition).
 * Das Schema ändert sich selten, die Daten aber häufig.
 */
private void schreibeSchema(Path ordner) throws IOException {
    // TODO: JSON-String mit folgender Struktur aufbauen:
    // - database: "bmiapp"
    // - version: 5
    // - entities: Array mit zwei Einträgen:
    //   1. personen (primaryKey: id)
    //   2. messungen (primaryKey: id, foreignKey: personId)
    
    // TODO: Datei bmiapp.schema.json schreiben (nutze tmp-Datei!)
}
```

**Referenzlösung (aus Schritt 2 entnommen):**

Siehe die Methode `schreibeSchema()` in der `JsonDateiDatenbankSpeicher`-Klasse oben (Schritt 2, Referenzlösung).

Die Methode:
1. Erzeugt einen Multi-Line-String mit dem kompletten JSON-Schema
2. Speichert ihn über eine temporäre Datei (`bmiapp.schema.json.tmp`)
3. Verschiebt die Datei atomar zu `bmiapp.schema.json`

**Didaktischer Fokus:**

- Das Schema ist statisch und wird nur einmal geschrieben.
- Es enthält keine Personendaten, nur die Struktur.
- Dies ist ein gutes Thema, um über „Daten vs. Metadaten" zu sprechen.

---

## Schritt 4: Die Datendatei normalisiert schreiben

Nun werden die Nutzdaten gespeichert.

### Fachliche Regel

Personen und Messungen werden in getrennten Arrays gespeichert.

### Warum?

Weil wir keine unnötigen Wiederholungen möchten.
Jede Messung enthält nur den Verweis auf die zugehörige Person, nicht alle Personendaten.

### Didaktische Erläuterung

Für Schülerinnen und Schüler ist es sehr verlockend, jede Person inklusive aller Messungen direkt verschachtelt zu speichern.
Das kann man technisch machen, aber für diese Lernstufe ist die getrennte Speicherung wertvoller.

Sie trainiert:

- Schlüsselkonzepte,
- Beziehungen,
- saubere Datenmodellierung,
- Wiederaufbau von Objektgraphen.

### Implementierung: Methode schreibeDaten()

**Vorlage für Schüler:**

```java
/**
 * Schreibt die Daten-Datei mit normalisierten Personen und Messungen.
 */
private void schreibeDaten(Path ordner, List<Person> personen) 
        throws IOException {
    // TODO: Aufbau des JSON-Strings:
    // 1. Öffne JSON-Objekt: "{"
    // 2. Array "personen": Schreibe alle Personen mit ihren Feldern
    //    - Wichtig: Verwende escapeJson() für Strings!
    //    - Kein Komma nach der letzten Person!
    // 3. Array "messungen": Sammle ALLE Messungen aller Personen
    //    - Flache Liste, nicht verschachtelt!
    //    - Jede Messung hat personId als Fremdschlüssel
    // 4. Schließe JSON-Objekt: "}"
    
    // TODO: Sicher schreiben (über tmp-Datei mit atomarem Move)
}
```

**Referenzlösung (aus Schritt 2 entnommen):**

Siehe die Methode `schreibeDaten()` in der `JsonDateiDatenbankSpeicher`-Klasse oben (Schritt 2).

Die Methode:
1. Erstellt einen `StringBuilder` für schrittweisen Aufbau
2. Speichert strukturiert alle Personen in einem Array
3. Sammelt alle Messungen (von allen Personen) und speichert sie in einem separaten Array
4. Escapet dabei alle Strings mit `escapeJson()`
5. Schreibt atomar über tmp-Datei

**Beispiel einer geladenen bmiapp.data.json:**

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
    },
    {
      "id": 2,
      "vorname": "Tom",
      "nachname": "Test",
      "alter": 17,
      "geschlecht": "Mann",
      "email": "tom@example.de"
    }
  ],
  "messungen": [
    {
      "id": 1,
      "personId": 1,
      "gewicht": 62.0,
      "groesse": 1.68,
      "zeitstempel": "2026-04-05T10:15:00"
    },
    {
      "id": 2,
      "personId": 1,
      "gewicht": 61.5,
      "groesse": 1.68,
      "zeitstempel": "2026-04-12T14:20:00"
    },
    {
      "id": 3,
      "personId": 2,
      "gewicht": 75.0,
      "groesse": 1.78,
      "zeitstempel": "2026-04-06T09:00:00"
    }
  ]
}
```

**Beobachtungen:**
- Mias Daten (`id: 1`) erscheinen NUR EINMAL im Array `personen`
- Ihre Messungen (`personId: 1`) erscheinen im Array `messungen` und referenzieren nur die `id: 1`
- Das vermeidet Redundanz und Inkonsistenzen

---

## Schritt 5: JSON sicher schreiben

Beim Schreiben dürfen keine kaputten JSON-Dateien entstehen.

### Worauf ist zu achten?

1. **UTF-8 explizit verwenden** – Umlaute und Sonderzeichen sonst zerstört
2. **Sonderzeichen in Strings escapen** – `"` wird zu `\"`
3. **Kein Komma hinter dem letzten Element** – Sonst invalidies JSON
4. **Atomar schreiben über temporäre Datei** – Falls Absturz während Speichern

### Warum ist das didaktisch wichtig?

Hier lernen die Schülerinnen und Schüler, dass funktionierender Code nicht automatisch robuster Code ist.
Gerade Dateispeicherung ist fehleranfällig.

Ein guter Unterrichtspunkt ist die Frage:

> Was passiert, wenn das Programm während des Schreibens abstürzt?

Damit wird verständlich, warum man mit `.tmp`-Datei und atomischem Move arbeiten sollte.

### Implementierung: Sichere Schreibweise

**Vorlage für Schüler:**

```java
/**
 * Escapet Sonderzeichen für sichere JSON-Strings.
 */
private String escapeJson(String text) {
    // TODO: Folgende Zeichen escapen:
    // - Backslash: \ wird zu \\
    // - Anführungszeichen: " wird zu \"
    // - Zeilenumbruch: \n
    // - Wagenrücklauf: \r
    // - Tabulator: \t
}

/**
 * Sicheres Schreiben mit tmp-Datei und atomarem Move.
 */
private void schreibeSicherZuDatei(Path targetPath, String inhalt) 
        throws IOException {
    // TODO: 
    // 1. Erstelle temporäre Datei: targetPath + ".tmp"
    // 2. Schreibe Inhalt in tmp-Datei mit UTF-8
    // 3. Verschiebe tmp-Datei auf Original (atomar)
    //    - Falls Original existiert: überschreibe
}
```

**Referenzlösung (aus Schritt 2):**

Siehe die Methoden `escapeJson()` und die sicheren Schreibvorgänge in:
- `schreibeSchema()`
- `schreibeDaten()`

aus der `JsonDateiDatenbankSpeicher`-Klasse.

**Konkrete Implementierung der escapeJson()-Methode:**

```java
private String escapeJson(String text) {
    if (text == null) return "";
    return text.replace("\\", "\\\\")       // Reihenfolge: erst \!
               .replace("\"", "\\\"")
               .replace("\n", "\\n")
               .replace("\r", "\\r")
               .replace("\t", "\\t");
}
```

**Wichtige Hinweise:**

1. **Reihenfolge beim Escaping:** Der Backslash muss ZUERST ersetzt werden, sonst ersetzt man die gerade eingefügten Backslashes nochmal!

2. **Atomares Schreiben:**

```java
Path targetPath = ordner.resolve(DATA_DATEI);
Path tmpPath = ordner.resolve(DATA_DATEI + ".tmp");

// Schreib in tmp
Files.writeString(tmpPath, json.toString(), StandardCharsets.UTF_8);

// Verschieb atomar (dieser Moment ist "All-or-Nothing")
Files.move(tmpPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
```

**Lerneffekt für Schüler:**

- Wenn die JVM nach `writeString()` abstürzt → Die tmp-Datei existiert unvollständig, die alte Datei bleibt intakt
- Wenn die JVM nach `move()` abstürzt → Der Move ist atomar, also entweder komplett oder gar nicht geschehen
- Resultat: Die Datei ist immer konsistent, nicht halb geschrieben!

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

### Implementierung: Validierung des Schemas

**Vorlage für Schüler:**

```java
/**
 * Validiert, dass das Schema die erwartete Struktur hat.
 */
private void validiereSchema(String schemaJson) throws IOException {
    // TODO: Prüfungen:
    // - Entität "personen" vorhanden?
    // - Entität "messungen" vorhanden?
    // - (Optional: Feldnamen prüfen?)
    
    // Falls nicht OK: throw new IOException("Beschreibung des Fehlers");
}
```

**Referenzlösung (aus Schritt 2):**

```java
private void validiereSchema(String schemaJson) throws IOException {
    // Minimalprüfung: Erwartete Entitäten müssen vorhanden sein
    if (!schemaJson.contains("\"personen\"")) {
        throw new IOException("Schema-Fehler: Entität 'personen' nicht gefunden");
    }
    if (!schemaJson.contains("\"messungen\"")) {
        throw new IOException("Schema-Fehler: Entität 'messungen' nicht gefunden");
    }
}
```

**Lerneffekt:**

Wenn jemand später die Schema-Datei versehentlich beschädigt, bricht das Laden mit einer klaren Fehlermeldung ab – statt später beim Zugriff auf nicht erwartete Daten zu crashen.

---

## Schritt 7: Daten laden und Beziehungen wiederherstellen

Nach dem Lesen der Datendatei müssen die Objekte rekonstruiert werden.

### Reihenfolge

1. **Alle Personen laden** – in eine Liste und als Map nach ID
2. **Personen in einer Map nach ID ablegen** – für schnellen Zugriff
3. **Alle Messungen laden** – aus der flachen Struktur
4. **Jede Messung über `personId` der passenden Person zuordnen** – Beziehung wiederherstellen

### Warum in dieser Reihenfolge?

Eine Messung kann erst korrekt zugeordnet werden, wenn die zugehörige Person schon existiert.
Sonst: `NullPointerException` bei ungültigem Fremdschlüssel.

### Didaktischer Mehrwert

Hier sehen die Lernenden sehr konkret, wie aus Dateien wieder zusammenhängende Objekte werden.
Das ist der Kern eines einfachen Objektspeichers mit Zusammenhang.

### Typisches Denkmuster

```java
// Schritt 1: Personen laden
Map<Integer, Person> personenNachId = new HashMap<>();
for (Person p : geladenePersonen) {
    personenNachId.put(p.getId(), p);
}

// Schritt 2: Messungen laden und verknüpfen
for (Messung m : geladeneMes sungen) {
    // Schritt 3: Fremdschlüssel auflösen
    Person person = personenNachId.get(m.getPersonId());
    
    // Schritt 4: Beziehung herstellen
    if (person != null) {
        person.addMessung(m);
    } else {
        // Fehlerbehandlung: Ungültige personId!
        throw new IOException("Messung " + m.getId() + 
            " hat ungültige personId: " + m.getPersonId());
    }
}
```

### Implementierung: Methode ladenUndRekonstruieren()

**Vorlage für Schüler:**

```java
/**
 * Lädt die Daten aus JSON und rekonstruiert die Objektbeziehungen.
 */
private List<Person> ladenUndRekonstruieren(String dataJson) 
        throws IOException {
    
    List<Person> personen = new ArrayList<>();
    Map<Integer, Person> personenNachId = new HashMap<>();
    
    // TODO: PHASE 1 - Personen laden
    // - Extrahiere "personen"-Array aus dataJson
    // - Für jede Person: ID, Vorname, Nachname, etc. auslesen
    // - Person-Objekt erstellen
    // - In Liste UND Map speichern (Map: id → Person)
    
    // TODO: PHASE 2 - Messungen laden und verknüpfen
    // - Extrahiere "messungen"-Array aus dataJson
    // - Für jede Messung: ID, personId, Gewicht, Größe, Zeitstempel auslesen
    // - Messung-Objekt erstellen
    // - Passende Person aus Map holen: personenNachId.get(personId)
    // - Fehlerbehandlung: Person existiert nicht?
    // - Messung zur Person hinzufügen: person.addMessung(messung)
    
    return personen;
}
```

**Referenzlösung (aus Schritt 2):**

Siehe die Methode `ladenUndRekonstruieren()` in der `JsonDateiDatenbankSpeicher`-Klasse (Schritt 2).

Die Methode zeigt:
1. **PHASE 1:** Personen extrahieren und in HashMap speichern
2. **PHASE 2:** Messungen extrahieren und über `personId` die passende Person finden
3. **Fehlerbehandlung:** Falls eine Messung eine ungültige `personId` hat

**Wichtiger Code-Ausschnitt aus der Referenzlösung:**

```java
// PHASE 1: Personen laden
// ...Person-Extrahierung...
Person p = new Person(vorname, nachname, alter, geschlecht, email);
p.setId(id);
personen.add(p);
personenNachId.put(id, p);  // <-- Hier: ID als Schlüssel!

// PHASE 2: Messungen laden und verknüpfen
// ...Messung-Extrahierung...
Messung m = new Messung(gewicht, groesse);
m.setId(id);
m.setPersonId(personId);

// HIER PASSIERT DIE MAGIE: Beziehung wiederherstellen!
Person person = personenNachId.get(personId);
if (person != null) {
    person.addMessung(m);  // <-- Messung zur Person hinzufügen!
} else {
    throw new IOException("Ungültige personId: " + personId);
}
```

### Lerneffekt

Die Lernenden verstehen:
- **Normalisierung:** Separater Speicher für Personen und Messungen
- **Denormalisierung beim Laden:** Wieder zusammenführen via IDs
- **Integrität:** Eine Messung ohne gültige Person ist ein Fehler
- **Objekt-Graphen:** Aus flachen Dateien entstehen wieder verbundene Objekte

---

## Schritt 8: Den BmiManager erweitern

Der Controller soll nicht selbst JSON bauen, aber er soll Speichern und Laden auslösen können.

### Sinnvolle Methoden

```java
public void speichereDaten(Path ordner) throws IOException
public void ladeDaten(Path ordner) throws IOException
```

### Didaktische Erläuterung

Die Schülerinnen und Schüler sollen hier sehen, dass der Controller Vermittler ist:

- GUI kennt die Persistenzdetails nicht.
- Persistenz kennt die GUI nicht.
- Der Controller verbindet beide Seiten.

### Implementierung: BmiManager erweitern

**Vorlage für Schüler:**

```java
public class BmiManager {
    private List<Person> personen;
    private Person aktuellePersonn;
    private JsonDateiDatenbankSpeicher speicher;
    
    // ID-Verwaltung
    private int nächstePersonId = 1;
    private int nächsteMessungId = 1;
    
    public BmiManager() {
        this.personen = new ArrayList<>();
        this.speicher = new JsonDateiDatenbankSpeicher();
        // TODO: nächstePersonId und nächsteMessungId korrekt initialisieren
    }
    
    /**
     * Speichert alle Personen und Messungen in JSON-Dateien.
     */
    public void speichereDaten(Path ordner) throws IOException {
        // TODO: speicher.speichere(ordner, personen) aufrufen
        // TODO: Bei Erfolg: Meldung an Benutzer
        // TODO: Bei Fehler: Exception abfangen und sinnvoll weiterleiten
    }
    
    /**
     * Lädt alle Personen und Messungen aus JSON-Dateien.
     */
    public void ladeDaten(Path ordner) throws IOException {
        // TODO: List<Person> geladenPersonen = speicher.lade(ordner)
        // TODO: this.personen = geladenPersonen
        // TODO: Höchste IDs ermitteln (für nächste neue Person/Messung)
        // TODO: aktuellePersonen auf erste Person setzen (falls vorhanden)
        // TODO: Bei Fehler: Exception abfangen und sinnvoll weiterleiten
    }
    
    /**
     * Erstellt eine neue Person und vergibt eine eindeutige ID.
     */
    public void personHinzufügen(String vorname, String nachname, 
                                 int alter, String geschlecht, String email) {
        Person p = new Person(vorname, nachname, alter, geschlecht, email);
        p.setId(nächstePersonId++);  // TODO: ID zuweisen
        personen.add(p);
    }
    
    /**
     * Fügt eine neue Messung zur aktuellen Person hinzu.
     */
    public void messungHinzufügen(double gewicht, double groesse) {
        if (aktuellePersonn == null) {
            throw new IllegalArgumentException("Keine Person ausgewählt");
        }
        Messung m = new Messung(gewicht, groesse);
        m.setId(nächsteMessungId++);  // TODO: ID zuweisen
        m.setPersonId(aktuellePersonn.getId());  // TODO: Fremdschlüssel setzen
        aktuellePersonn.addMessung(m);
    }
    
    // Weitere Methoden (Getter, Statistik, etc.) bleiben wie zuvor...
}
```

**Referenzlösung: Vollständige Methoden**

```java
public class BmiManager {
    private List<Person> personen;
    private Person aktuellePersonn;
    private JsonDateiDatenbankSpeicher speicher;
    
    private int nächstePersonId = 1;
    private int nächsteMessungId = 1;
    
    public BmiManager() {
        this.personen = new ArrayList<>();
        this.speicher = new JsonDateiDatenbankSpeicher();
    }
    
    /**
     * Speichert alle Personen und Messungen in JSON-Dateien.
     */
    public void speichereDaten(Path ordner) throws IOException {
        if (ordner == null) {
            throw new IllegalArgumentException("Ordnerpfad darf nicht null sein");
        }
        
        try {
            speicher.speichere(ordner, personen);
            System.out.println("✓ Daten erfolgreich gespeichert in: " + ordner);
        } catch (IOException e) {
            System.err.println("✗ Fehler beim Speichern: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Lädt alle Personen und Messungen aus JSON-Dateien.
     */
    public void ladeDaten(Path ordner) throws IOException {
        if (ordner == null) {
            throw new IllegalArgumentException("Ordnerpfad darf nicht null sein");
        }
        
        try {
            List<Person> geladenPersonen = speicher.lade(ordner);
            this.personen = geladenPersonen;
            
            // Ermittle höchste IDs für weitere neue Einträge
            nächstePersonId = 1;
            nächsteMessungId = 1;
            
            for (Person p : personen) {
                if (p.getId() >= nächstePersonId) {
                    nächstePersonId = p.getId() + 1;
                }
                for (Messung m : p.getMessungen()) {
                    if (m.getId() >= nächsteMessungId) {
                        nächsteMessungId = m.getId() + 1;
                    }
                }
            }
            
            // Setze aktuelle Person auf die erste (falls vorhanden)
            if (!personen.isEmpty()) {
                aktuellePersonn = personen.get(0);
            }
            
            System.out.println("✓ Daten erfolgreich geladen: " 
                + personen.size() + " Personen");
        } catch (IOException e) {
            System.err.println("✗ Fehler beim Laden: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Erstellt eine neue Person und vergibt eine eindeutige ID.
     */
    public void personHinzufügen(String vorname, String nachname, 
                                 int alter, String geschlecht, String email) {
        Person p = new Person(vorname, nachname, alter, geschlecht, email);
        p.setId(nächstePersonId++);  // Eindeutige ID vergeben
        personen.add(p);
        if (aktuellePersonn == null) {
            aktuellePersonn = p;  // Erste Person wird automatisch aktuelle
        }
    }
    
    /**
     * Fügt eine neue Messung zur aktuellen Person hinzu.
     */
    public void messungHinzufügen(double gewicht, double groesse) {
        if (aktuellePersonn == null) {
            throw new IllegalArgumentException("Keine Person ausgewählt");
        }
        
        Messung m = new Messung(gewicht, groesse);
        m.setId(nächsteMessungId++);        // Eindeutige ID vergeben
        m.setPersonId(aktuellePersonn.getId());  // Fremdschlüssel setzen
        aktuellePersonn.addMessung(m);
    }
    
    // Weitere Methoden...
}
```

### Wichtige Controller-Aufgaben

1. **ID-Verwaltung:** Der Controller verwaltet zwei Zähler (`nächstePersonId`, `nächsteMessungId`). Diese werden beim Laden neu berechnet!
2. **Fehlerbehandlung:** IO-Exceptions werden weiterpropagiert, aber mit aussagekräftigen Meldungen
3. **State-Management:** Nach dem Laden muss die aktuelle Person sinnvoll gesetzt werden
4. **Entkopplung:** Die GUI ruft nur `speichereDaten(ordner)` und `ladeDaten(ordner)` auf, nicht JSON-spezifische Details

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

### Implementierung: GUI-Buttons und ActionListener

**Vorlage für Schüler:**

```java
public class MainWindow extends JFrame {
    private BmiManager manager;
    private JButton btnSpeichern;
    private JButton btnLaden;
    
    // ... weitere GUI-Komponenten ...
    
    public MainWindow(BmiManager manager) {
        this.manager = manager;
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        // Bestehende Buttons...
        
        // TODO: Neue Buttons erstellen
        btnSpeichern = new JButton("💾 Speichern");
        btnLaden = new JButton("📂 Laden");
        
        // TODO: Action-Listener registrieren
        btnSpeichern.addActionListener(e -> onSpeichern());
        btnLaden.addActionListener(e -> onLaden());
    }
    
    /**
     * Wird aufgerufen, wenn Benutzer auf "Speichern" klickt.
     */
    private void onSpeichern() {
        // TODO: 
        // 1. manager.speichereDaten(Pfad) aufrufen
        // 2. Bei Erfolg: JOptionPane.showMessageDialog("Gespeichert!")
        // 3. Bei Fehler: JOptionPane.showErrorDialog(Fehlermeldung)
    }
    
    /**
     * Wird aufgerufen, wenn Benutzer auf "Laden" klickt.
     */
    private void onLaden() {
        // TODO:
        // 1. manager.ladeDaten(Pfad) aufrufen
        // 2. Die GUI komplett aktualisieren:
        //    - Personenliste neu füllen
        //    - Historie aktualisieren
        //    - Statistik neu berechnen
        // 3. Bei Erfolg: JOptionPane.showMessageDialog("Geladen!")
        // 4. Bei Fehler: JOptionPane.showErrorDialog(Fehlermeldung)
    }
}
```

**Referenzlösung: ActionListener implementieren**

```java
private void onSpeichern() {
    try {
        // Fester Speicherort: user.home/bmiapp_data
        Path speicherOrt = Paths.get(System.getProperty("user.home"), 
                                     "bmiapp_data");
        manager.speichereDaten(speicherOrt);
        
        JOptionPane.showMessageDialog(this,
            "Daten erfolgreich gespeichert in:\n" + speicherOrt,
            "Speichern erfolgreich",
            JOptionPane.INFORMATION_MESSAGE);
            
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this,
            "Fehler beim Speichern:\n" + ex.getMessage(),
            "Speicherfehler",
            JOptionPane.ERROR_MESSAGE);
    }
}

private void onLaden() {
    try {
        // Gleicher Speicherort wie onSpeichern()
        Path speicherOrt = Paths.get(System.getProperty("user.home"), 
                                     "bmiapp_data");
        
        manager.ladeDaten(speicherOrt);
        
        // GUI komplete aktualisieren
        aktuelisierePersonenliste();
        aktuelisiereHistorie();
        aktuelisiereStatistik();
        
        JOptionPane.showMessageDialog(this,
            "Daten erfolgreich geladen!",
            "Laden erfolgreich",
            JOptionPane.INFORMATION_MESSAGE);
            
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this,
            "Fehler beim Laden:\n" + ex.getMessage(),
            "Ladefehler",
            JOptionPane.ERROR_MESSAGE);
    }
}

/**
 * Hilfsmethode: Personenliste in JComboBox neu füllen
 */
private void aktuelisierePerson enliste() {
    cboPersonen.removeAllItems();
    for (Person p : manager.getPersonen()) {
        cboPersonen.addItem(p.getVorname() + " " + p.getNachname());
    }
}

/**
 * Hilfsmethode: Historie des aktuellen Messung aktualisieren
 */
private void aktuelisiereHistorie() {
    lstHistorie.removeAllItems();
    Person aktuelle = manager.getAktuellePersonn();
    if (aktuelle != null) {
        for (Messung m : aktuelle.getMessungen()) {
            String eintrag = String.format("BMI: %.2f | Größe: %.2f | Gewicht: %.1f kg",
                m.getBmi(), m.getGroesse(), m.getGewicht());
            lstHistorie.addItem(eintrag);
        }
    }
}

/**
 * Hilfsmethode: Statistik neu berechnen und anzeigen
 */
private void aktuelisiereStatistik() {
    Person aktuelle = manager.getAktuellePersonn();
    if (aktuelle != null) {
        int anzUb = aktuelle.getMessungen().size();
        double durchBMI = // Berechnung...
        lblStatistik.setText("Messungen: " + anzUb + " | Ø BMI: " + durchBMI);
    }
}
```

### Wichtige GUI-Punkte

1. **Fester Speicherort:** Nutze `System.getProperty("user.home")`, damit die Datei auf jedem System gespeichert wird
2. **Fehlerbehandlung:** Try-Catch um IO-Exceptions
3. **Benutzer-Feedback:** JOptionPane für Erfolgs- oder Fehlermeldungen
4. **Nach dem Laden:** GUI komplett aktualisieren (Personenliste, Historie, Statistik)
5. **Atomare Operationen:** Speichern und Laden sollten ganz oder gar nicht erfolgen

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

### Implementierung: Example Junit Tests

**Vorlage für Schüler:**

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class JsonDateiDatenbankSpeicherTest {
    
    private JsonDateiDatenbankSpeicher speicher = 
        new JsonDateiDatenbankSpeicher();
    
    /**
     * Test 1: Speichern erzeugt beide Dateien.
     */
    @Test
    public void test_speichernErzeugtBeideDateien(@TempDir Path tmpDir) 
            throws IOException {
        // TODO:
        // 1. Erstelle eine Person mit einer Messung
        // 2. Speichere sie
        // 3. Prüfe, ob bmiapp.schema.json existiert
        // 4. Prüfe, ob bmiapp.data.json existiert
    }
    
    /**
     * Test 2: Roundtrip-Test: Speichern und wieder laden
     */
    @Test
    public void test_speichernUndLadenRoundtrip(@TempDir Path tmpDir) 
            throws IOException {
        // TODO:
        // 1. Erstelle Original-Person mit Messungen
        // 2. Speichere sie
        // 3. Lade sie neu
        // 4. Prüfe: ID-Konsistenz, Anzahl Messungen, Werte gleich?
    }
    
    /**
     * Test 3: Ungültige personId wird erkannt
     */
    @Test
    public void test_ladenMitUngültigemnFremdschlüssel(@TempDir Path tmpDir) 
            throws IOException {
        // TODO:
        // 1. Erstelle eine data.json mit ungültigem personId in Messung
        // 2. Versuche zu laden
        // 3. Prüfe: IOException wird geworfen?
    }
}
```

**Referenzlösung: Konkrete JUnit-Tests**

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JsonDateiDatenbankSpeicherTest {
    
    private JsonDateiDatenbankSpeicher speicher = 
        new JsonDateiDatenbankSpeicher();
    
    /**
     * Test 1: Speichern erzeugt beide Dateien.
     */
    @Test
    public void test_speichernErzeugtBeideDateien(@TempDir Path tmpDir) 
            throws IOException {
        // Arrange: Testdaten vorbereiten
        List<Person> personen = new ArrayList<>();
        Person p = new Person("Max", "Mustermann", 16, "Mann", "max@test.de");
        p.setId(1);
        
        Messung m = new Messung(70.0, 1.75);
        m.setId(1);
        m.setPersonId(1);
        p.addMessung(m);
        personen.add(p);
        
        // Act: Speichern
        speicher.speichere(tmpDir, personen);
        
        // Assert: Dateien prüfen
        assertTrue(Files.exists(tmpDir.resolve("bmiapp.schema.json")),
            "Schema-Datei sollte existieren");
        assertTrue(Files.exists(tmpDir.resolve("bmiapp.data.json")),
            "Daten-Datei sollte existieren");
    }
    
    /**
     * Test 2: Speichern und wieder laden (Roundtrip)
     */
    @Test
    public void test_speichernUndLadenRoundtrip(@TempDir Path tmpDir) 
            throws IOException {
        // Arrange: Testdaten vorbereiten
        List<Person> original = new ArrayList<>();
        Person p1 = new Person("Mia", "Muster", 16, "Frau", "mia@test.de");
        p1.setId(1);
        
        Messung m1 = new Messung(62.0, 1.68);
        m1.setId(1);
        m1.setPersonId(1);
        p1.addMessung(m1);
        
        Messung m2 = new Messung(61.5, 1.68);
        m2.setId(2);
        m2.setPersonId(1);
        p1.addMessung(m2);
        
        original.add(p1);
        
        // Act: Speichern
        speicher.speichere(tmpDir, original);
        
        // Act: Laden
        List<Person> geladen = speicher.lade(tmpDir);
        
        // Assert: Vollständigkeit prüfen
        assertEquals(1, geladen.size(), "Sollte eine Person haben");
        Person pGeladen = geladen.get(0);
        assertEquals(1, pGeladen.getId(), "Person-ID sollte 1 sein");
        assertEquals("Mia", pGeladen.getVorname(), "Vorname sollte Mia sein");
        assertEquals(2, pGeladen.getMessungen().size(), 
            "Person sollte 2 Messungen haben");
        
        // Messungen prüfen
        Messung mGeladen1 = pGeladen.getMessungen().get(0);
        assertEquals(1, mGeladen1.getId(), "Erste Messung-ID sollte 1 sein");
        assertEquals(62.0, mGeladen1.getGewicht(), "Gewicht sollte 62.0 sein");
    }
    
    /**
     * Test 3: Schema-Validierung prüft auf erwartete Entitäten
     */
    @Test
    public void test_ladenMitFehlenderSchemaEntity(@TempDir Path tmpDir) 
            throws IOException {
        // Arrange: Unvollständiges Schema
        String schlechtesSchema = """
            {
              "database": "bmiapp",
              "entities": []
            }
            """;
        Files.writeString(tmpDir.resolve("bmiapp.schema.json"), 
            schlechtesSchema);
        Files.writeString(tmpDir.resolve("bmiapp.data.json"), 
            "{}");
        
        // Act & Assert: Sollte IOException werfen
        assertThrows(IOException.class, () -> {
            speicher.lade(tmpDir);
        }, "Beim Laden mit fehlender Entity sollte IOException geworfen werden");
    }
}
```

### Wichtige Test-Punkte

1. **@TempDir:** JUnit 5 Feature - erstellt temporäres Verzeichnis für Tests
2. **Arrange-Act-Assert:** Standard Test-Struktur
3. **AssertEquals:** Prüfe Werte auf Gleichheit
4. **AssertThrows:** Prüfe, dass Exception geworfen wird
5. **Edge Cases:** Tests sollten auch Fehler und Grenzvälleberücksichtigen

### Lerneffekt

Tests machen die Anforderungen explizit und dokumentieren das erwartbare Verhalten. Schüler lernen, dass guter Code testbar ist!

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
