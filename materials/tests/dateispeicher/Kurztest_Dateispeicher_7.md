# Kurztest: JSON-Datenspeicherung für Schülernoten – Normalisierung und Datenkonsistenz

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: JSON-Noten-Datenbank mit mehreren Entitäten (5 Punkte)

**Thema:** Speicherung von Schülern und ihren Noten mit Trennung

Ein Schulportal soll Schüler und ihre Noten (mit Fächern) speichern.
Es sollen zwei Dateien geschrieben werden:
- `noten.schema.json` (Struktur mit Fächerliste und Notenskala)
- `noten.data.json` (Schüler und ihre Noten)

```java
public class JsonNotenDatenbankWriter {

    public void speichereNotenDatenbank(List<Schueler> schueler, List<String> faecher, String ordner) throws IOException {
        Path schemaPfad = Paths.get(ordner, "noten.schema.json");
        Path datenPfad = Paths.get(ordner, "noten.data.json");

        Files.createDirectories(Paths.get(ordner));

        // 1) Schema-JSON mit Fächerliste und Notenskala:
        StringBuilder schemaJson = new StringBuilder();
        schemaJson.append("{\n");
        schemaJson.append("  \"entity\": \"schueler\",\n");
        schemaJson.append("  \"version\": 1,\n");
        schemaJson.append("  \"notenskala\": \"1.0-6.0 (1=sehr gut, 6=ungenuegend)\",\n");
        schemaJson.append("  \"faecher\": [");

        for (int i = 0; i < faecher.size(); i++) {
            schemaJson.append("\"").append(faecher.get(i)).append("\"");
            if (i < faecher.size() - 1) {
                schemaJson.append(", ");
            }
        }

        schemaJson.append("],\n");
        schemaJson.append("  \"fields\": [\n");
        schemaJson.append("    {\"name\": \"id\", \"type\": \"int\", \"required\": true},\n");
        schemaJson.append("    {\"name\": \"name\", \"type\": \"string\", \"required\": true},\n");
        schemaJson.append("    {\"name\": \"klasse\", \"type\": \"string\", \"required\": true}\n");
        schemaJson.append("  ]\n");
        schemaJson.append("}\n");

        // 2) Daten-JSON mit Schülern und Noten:
        StringBuilder datenJson = new StringBuilder();
        datenJson.append("{\n  \"schueler\": [\n");

        for (int i = 0; i < schueler.size(); i++) {
            Schueler s = schueler.get(i);

            datenJson.append("    {\n");
            datenJson.append("      \"id\": ").append(s.getId()).append(",\n");
            datenJson.append("      \"name\": \"").append(s.getName()).append("\",\n");
            datenJson.append("      \"klasse\": \"").append(s.getKlasse()).append("\",\n");
            datenJson.append("      \"noten\": {\n");

            Map<String, Double> noten = s.getNoten();
            int fachCount = 0;
            for (String fach : noten.keySet()) {
                datenJson.append("        \"").append(fach).append("\": ").append(noten.get(fach));
                if (fachCount++ < noten.size() - 1) {
                    datenJson.append(",");
                }
                datenJson.append("\n");
            }

            datenJson.append("      }\n");
            datenJson.append("    }");

            if (i < schueler.size() - 1) {
                datenJson.append(",");
            }
            datenJson.append("\n");
        }

        datenJson.append("  ]\n}");

        // TODO: Schreibe beide Dateien mit atomarer Sicherheit:
        //  1) Erstelle temporäre Dateien (schema.tmp, noten.tmp)
        //  2) Schreibe in Temporäre Dateien
        //  3) Bei Erfolg: rename (atomic) in echte Zieldateien
        //  4) Bei Fehler: Lösche Temporäre Dateien und werfe Exception




    }
}
```

Vervollständige die Schreiblogik mit **atomarem Schreiben** über temporäre Dateien.

---

## Aufgabe 2: JSON-Noten lesen und Konsistenzprüfung (4 Punkte)

**Thema:** Gewährleisten, dass alle Schülernoten den Fächern entsprechen

```java
public class JsonNotenDatenbankReader {

    public List<Schueler> ladeNotenDatenbank(String ordner) throws IOException, IllegalArgumentException {
        Path schemaPfad = Paths.get(ordner, "noten.schema.json");
        Path datenPfad = Paths.get(ordner, "noten.data.json");

        String schema = Files.readString(schemaPfad, StandardCharsets.UTF_8);  // 0.5 Punkte
        String daten = Files.readString(datenPfad, StandardCharsets.UTF_8);    // 0.5 Punkte

        // Extrahiere Fächerliste aus Schema
        List<String> erlaubteFaecher = extractFaecher(schema);  // 0.5 Punkte

        // TODO:
        // - Prüfe, dass Daten den Container "schueler" enthaelt (0.5 Punkte)
        // - Prüfe beim Laden: Jedes Fach eines Schülers muss in erlaubteFaecher enthalten sein (1 Punkt)
        //   Falls nicht: werfe IllegalArgumentException
        // - Prüfe: Jede Note liegt zwischen 1.0 und 6.0 (1 Punkt)
        // - Gib die validierte List<Schueler> zurück (1 Punkt)

        return new ArrayList<>();  // Placeholder
    }

    private List<String> extractFaecher(String schema) {
        // Extrahiert Fächerliste zwischen "faecher": [ ... ]
        List<String> result = new ArrayList<>();
        int start = schema.indexOf("\"faecher\":");
        int end = schema.indexOf("]", start);
        String fachStr = schema.substring(start, end);
        // Vereinfachtes Parsing (in echtem Code: echten JSON-Parser verwenden)
        // ...
        return result;
    }
}
```

---

## Aufgabe 3: Datenmodellierungs-Fehler erkennen (5 Punkte)

**Thema:** Erfassung von Änderungsproblemen

Gegeben ist folgende fehlerhafte Datenstruktur ohne Normalisierung:

```json
{
  "schueler_mit_faecher": [
    {
      "schueler_id": 1, "schueler_name": "Anna", "klasse": "10a",
      "faecher": [
        {"id": 101, "name": "Deutsch", "note": 2.0},
        {"id": 102, "name": "Mathe", "note": 1.5},
        {"id": 103, "name": "Englisch", "note": 2.5}
      ]
    },
    {
      "schueler_id": 2, "schueler_name": "Ben", "klasse": "10a",
      "faecher": [
        {"id": 101, "name": "Deutsch", "note": 3.0},
        {"id": 102, "name": "Mathe", "note": 2.0},
        {"id": 103, "name": "Englisch", "note": 2.5}
      ]
    }
  ]
}
```

**Aufgabe:**

a) Identifiziere **zwei Redundanzen** in dieser Struktur.

___________________________________________________________________________

___________________________________________________________________________

b) Beschreibe eine **Anomalie**, die auftreten kann, wenn der Fachname "Englisch" → "English" geändert wird.

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 4: Transaktionale Sicherheit – Fehlerhafte Implementierung (5 Punkte)

**Thema:** Konsistenz bei gleichzeitigen Änderungen verhindern

```java
public class UnsichereNotenAenderung {

    public void addNoteZuSchueler(int schuelerID, String fach, double note, String dateiPfad) throws IOException {
        String daten = Files.readString(Paths.get(dateiPfad), StandardCharsets.UTF_8);

        // Ändere Daten in-memory
        daten = daten.replace(
            "\"" + fach + "\": 0.0",    // Platzhalter für neue Note
            "\"" + fach + "\": " + note  // Neue Note eintragen
        );

        // Schreibe direkt zurück (UNSICHER!)
        Files.writeString(Paths.get(dateiPfad), daten, StandardCharsets.UTF_8);
    }
}
```

**Aufgaben:**

a) Beschreibe das **Sicherheitsproblem** dieser Implementierung (Szenario: zwei Prozesse ändern gleichzeitig).

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

b) Skizziere eine **sichere Lösung**.

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

c) Welche Dateioperationen brauchst du dafür? (Stichwort: atomic operations)

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Best Practices bei Schulnotenverwaltung (3 Punkte)

**Thema:** Robustheit und Nachvollziehbarkeit

Nenne drei Best Practices für die Verwaltung von Schülernoten in JSON-Dateien:

1. ________________________________________________________________
2. ________________________________________________________________
3. ________________________________________________________________

---

## Aufgabe 6: Fehlerhafte Noten-Validierung (3 Punkte)

**Thema:** Eingabevalidierung

Ein Programm speichert Noten wie folgt:

```java
for (Schueler s : schueler) {
    for (String fach : faecher) {
        Double note = scanner.nextDouble();  // Benutzer gibt Note ein
        s.addNote(fach, note);  // Keine Validierung!
        datenbank.speichern();  // Speichert sofort
    }
}
```

**Aufgabe:**

a) Welche **ungültigen Eingaben** könnten zu Problemen führen?

___________________________________________________________________________

___________________________________________________________________________

b) Wie sollte die Validierung aussehen (Pseudocode)?

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Erwartungshorizont (Kurzüberblick)

- **Sehr gut (23-25 Punkte):** Atomares Schreiben verstanden, Konsistenzprüfung sauber, Anomalien und Normalisierung klar analysiert.
- **Gut (19-22 Punkte):** Trennung und Validierung grundsätzlich richtig, kleinere Lücken bei Redundanzanalyse oder Fehlerbehandlung.
- **Ausreichend (14-18 Punkte):** Grundideen erkannt, aber Lücken bei transaktionaler Sicherheit oder vollständiger Konsistenzprüfung.
- **Unter 14 Punkte:** Wichtige Konzepte (Normalisierung, Atomarität, Validierung) nicht hinreichend verstanden.
