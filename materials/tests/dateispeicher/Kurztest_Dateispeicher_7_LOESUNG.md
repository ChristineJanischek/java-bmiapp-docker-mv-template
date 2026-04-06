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

---

# LÖSUNGEN

## Lösung Aufgabe 1 (5 Punkte)

```java
public void speichereNotenDatenbank(List<Schueler> schueler, List<String> faecher, String ordner) throws IOException {
    Path schemaPfad = Paths.get(ordner, "noten.schema.json");
    Path datenPfad = Paths.get(ordner, "noten.data.json");

    // Beide Dateien wie oben definiert
    String schemaJson = /* ... */;
    String datenJson = /* ... */;

    // Atomares Schreiben mit temp-Dateien
    Path schemaTmp = Paths.get(schemaPfad.toString() + ".tmp");
    Path datenTmp = Paths.get(datenPfad.toString() + ".tmp");

    try {
        // Schreibe in temp-Dateien
        Files.writeString(schemaTmp, schemaJson, StandardCharsets.UTF_8);
        Files.writeString(datenTmp, datenJson, StandardCharsets.UTF_8);

        // Atomic rename (nur bei Erfolg)
        Files.move(schemaTmp, schemaPfad, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        Files.move(datenTmp, datenPfad, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
        // Aufräumen bei Fehler
        Files.deleteIfExists(schemaTmp);
        Files.deleteIfExists(datenTmp);
        throw e;
    }
}
```

## Lösung Aufgabe 2 (4 Punkte)

```java
public List<Schueler> ladeNotenDatenbank(String ordner) throws IOException, IllegalArgumentException {
    Path schemaPfad = Paths.get(ordner, "noten.schema.json");
    Path datenPfad = Paths.get(ordner, "noten.data.json");

    String schema = Files.readString(schemaPfad, StandardCharsets.UTF_8);
    String daten = Files.readString(datenPfad, StandardCharsets.UTF_8);

    List<String> erlaubteFaecher = extractFaecher(schema);

    // Prüfe Datencontainer
    if (!daten.contains("\"schueler\"")) {
        throw new IOException("Daten-Datei ungueltig: Container 'schueler' fehlt");
    }

    List<Schueler> result = new ArrayList<>();

    // Extrahiere und validiere Schüler
    // (Vereinfachtes Parsing):
    String[] entries = daten.split("\"id\":");
    for (int i = 1; i < entries.length; i++) {
        String entry = entries[i];

        // Extrahiere Fächer und Noten
        for (String fach : erlaubteFaecher) {
            if (entry.contains("\"" + fach + "\"")) {
                double note = extractDouble(entry, "\"" + fach + "\": ");

                // Validierung
                if (note < 1.0 || note > 6.0) {
                    throw new IllegalArgumentException(
                        "Note " + note + " fuer Fach '" + fach + "' liegt außerhalb [1.0, 6.0]"
                    );
                }

                if (!erlaubteFaecher.contains(fach)) {
                    throw new IllegalArgumentException(
                        "Fach '" + fach + "' nicht in Fächerliste enthalten"
                    );
                }
            }
        }

        result.add(/* erstelle Schueler-Objekt */);
    }

    return result;
}

private List<String> extractFaecher(String schema) {
    List<String> result = new ArrayList<>();
    int start = schema.indexOf("\"faecher\": [") + 12;
    int end = schema.indexOf("]", start);
    String fachStr = schema.substring(start, end);

    for (String part : fachStr.split(",")) {
        result.add(part.trim().replace("\"", ""));
    }

    return result;
}
```

## Lösung Aufgabe 3 (5 Punkte)

**a) Zwei Redundanzen:**

1. **Fach-Name-Redundanz:** Der Name "Deutsch", "Mathe", "Englisch" ist für jeden Schüler wiederholt.
   Besser: Definiere Fächer einmal im Schema.

2. **Fach-ID-Redundanz:** Die `id` (101, 102, 103) ist ebenfalls für jeden Schüler identisch.
   Besser: IDs als Referenz im Schema führen.

**b) Anomalie (Update-Anomalie):**
Wenn "Englisch" → "English" geändert werden soll, müsste diese Änderung bei **beiden** Schülern (und allen anderen Schülern) durchgeführt werden. Wird sie vergessen, entsteht eine Inkonsistenz. In einer normalisierten Struktur würde nur ein Fach-Record aktualisiert.

## Lösung Aufgabe 4 (5 Punkte)

**a) Sicherheitsproblem (Race Condition):**
Wenn zwei Prozesse **gleichzeitig** die gleiche Notendatei modifizieren:
1. Prozess A liest Datei → "Noten: {...}"
2. Prozess B liest Datei → "Noten: {...}"
3. Prozess A ändert in-memory und schreibt zurück
4. Prozess B ändert in-memory und schreibt zurück (überschreibt A's Änderungen!)

Resultat: **Änderung von Prozess A ist verloren.** → Lost Update Problem.

**b) Sichere Lösung:**
```java
public void addNoteZuSchueler(int schuelerID, String fach, double note, String dateiPfad) throws IOException {
    // 1) Schreibe in temporäre Datei
    Path tmp = Paths.get(dateiPfad + ".tmp");
    String daten = Files.readString(Paths.get(dateiPfad), StandardCharsets.UTF_8);
    String geaendert = daten.replace("\"" + fach + "\": 0.0", "\"" + fach + "\": " + note);
    Files.writeString(tmp, geaendert, StandardCharsets.UTF_8);

    // 2) Atomarer Swap
    Files.move(tmp, Paths.get(dateiPfad), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
}
```

**c) Dateioperationen:**
- `Files.move()` mit `StandardCopyOption.ATOMIC_MOVE`
- Temp-Dateien für isolierte Schreiboperation
- Kein direktes Überschreiben -> kein Zustand mit halbgeschriebener Datei

## Lösung Aufgabe 5 (3 Punkte)

1. **Unveränderliches Audit-Log:** Speichere Änderungen in einer separaten Log-Datei (Wer? Wann? Alte/Neue Note?), um Nachvollziehbarkeit zu gewährleisten.
2. **Validierung vor Speichern:** Prüfe Noten im Wertebereich [1.0, 6.0] und unbekannte Fächer ab, bevor in die Datei geschrieben wird.
3. **Konsistenzsprüfung beim Laden:** Prüfe, dass alle Schüler die gleichen Fächer haben (oder mindestens nur zulässige Fächer).

## Lösung Aufgabe 6 (3 Punkte)

**a) Ungültige Eingaben:**
- Negative Noten oder Noten > 6.0
- Noten außerhalb des 0.5-Schrittbereichs (z.B. 2.3 statt 2.0 oder 2.5)
- NaN, Infinity(bei Benutzer-Double-Eingaben)
- Leere/unbekannte Fachbezeichnungen

**b) Validierungscode:**
```java
for (Schueler s : schueler) {
    for (String fach : faecher) {
        boolean valid = false;
        while (!valid) {
            try {
                Double note = scanner.nextDouble();

                // Validierung
                if (note < 1.0 || note > 6.0) {
                    System.out.println("Fehler: Note muss zwischen 1.0 und 6.0 liegen!");
                    continue;
                }

                // Prüfe, ob Noten nur 0.5-Schritte sind (optional)
                if ((note * 2) % 1 != 0) {
                    System.out.println("Fehler: Nur Noten wie 1.0, 1.5, 2.0, 2.5 erlaubt!");
                    continue;
                }

                s.addNote(fach, note);
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Fehler: Bitte geben Sie eine Dezimalzahl ein!");
                scanner.nextLine();  // Clear buffer
            }
        }
    }
    datenbank.speichern();  // Speichert nach vollständiger Validierung
}
```

**Wichtig:** Speichern erst **nach** vollständiger Validierung, nicht nach jeder Eingabe!
