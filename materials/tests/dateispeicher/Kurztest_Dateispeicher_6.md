# Kurztest: JSON-Datenspeicherung für Messwerte – Struktur und Daten trennen

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: JSON-Messwert-Datenbank mit Validierung (5 Punkte)

**Thema:** Separates Speichern von Messkontext und Messdaten

Ein Wissenschaftsprojekt speichert Temperaturmessungen über längere Zeit.
Es sollen zwei Dateien geschrieben werden:
- `messungen.schema.json` (Messkontext: Geräte, Einheiten, Gültigkeitsbereiche)
- `messungen.data.json` (konkrete Messwerte mit Timestamps)

```java
public class JsonMesswertDatenbankWriter {

    public void speichereMesswerteDatenbank(List<Temperaturmessung> messungen, String ordner) throws IOException {
        Path schemaPfad = Paths.get(ordner, "messungen.schema.json");
        Path datenPfad = Paths.get(ordner, "messungen.data.json");

        Files.createDirectories(Paths.get(ordner));

        // 1) Schema-JSON (Kontext) erstellen:
        String schemaJson = """
            {
              "entity": "temperaturmessung",
              "version": 1,
              "device": "DS18B20 Sensor",
              "unit": "Celsius",
              "minValid": -50.0,
              "maxValid": 125.0,
              "fields": [
                {"name": "id", "type": "int", "required": true},
                {"name": "temperatur", "type": "double", "required": true},
                {"name": "zeitstempel", "type": "string", "required": true},
                {"name": "ort", "type": "string", "required": true}
              ]
            }
            """;

        // 2) Daten-JSON aus Messungen erzeugen:
        StringBuilder datenJson = new StringBuilder();
        datenJson.append("{\n  \"messungen\": [\n");

        for (int i = 0; i < messungen.size(); i++) {
            Temperaturmessung m = messungen.get(i);

            datenJson.append("    {")
                     .append("\"id\": ").append(m.getId()).append(", ")
                     .append("\"temperatur\": ").append(m.getTemperatur()).append(", ")
                     .append("\"zeitstempel\": \"").append(m.getZeitstempel()).append("\", ")
                     .append("\"ort\": \"").append(m.getOrt()).append("\"")
                     .append("}");

            if (i < messungen.size() - 1) {
                datenJson.append(",");
            }
            datenJson.append("\n");
        }

        datenJson.append("  ]\n}");

        // TODO: Schreibe beide Dateien mit UTF-8 und verwende atomare Schreibweise:
        //  - Falls keine Fehler: schemaJson -> schemaPfad
        //  - Falls keine Fehler: datenJson -> datenPfad
        //  - Bei Fehler: Keine Datei überschreiben




    }
}
```

Vervollständige die Schreiblogik mit atomarer Schreibweise (Backup vor Überschreiben).

---

## Aufgabe 2: JSON-Messdaten lesen und Range-Validierung (4 Punkte)

**Thema:** Plausibilitätsprüfung geladener Messwerte

```java
public class JsonMesswertDatenbankReader {

    public List<Temperaturmessung> ladeMesswerteDatenbank(String ordner) throws IOException, IllegalArgumentException {
        Path schemaPfad = Paths.get(ordner, "messungen.schema.json");
        Path datenPfad = Paths.get(ordner, "messungen.data.json");

        // Lies beide Dateien:
        String schema = Files.readString(schemaPfad, StandardCharsets.UTF_8);  // 1 Punkt
        String daten = Files.readString(datenPfad, StandardCharsets.UTF_8);

        // Lese minValid und maxValid aus Schema:
        double minValid = extractDouble(schema, "\"minValid\": ");  // 1 Punkt
        double maxValid = extractDouble(schema, "\"maxValid\": ");

        // TODO:
        // - Prüfe, dass Daten den Container "messungen" enthaelt  (1 Punkt)
        // - Prüfe beim Laden: Jede Temperatur muss zwischen minValid und maxValid liegen (1 Punkt)
        //   Falls nicht: werfe IllegalArgumentException mit beschreibendem Text
        // - Gib die validierte List<Temperaturmessung> zurück

        return new ArrayList<>();  // Placeholder
    }

    private double extractDouble(String json, String key) {
        int start = json.indexOf(key) + key.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("\n", start);
        return Double.parseDouble(json.substring(start, end).trim());
    }
}
```

---

## Aufgabe 3: Code-Analyse – Fehler in der Serialisierung (5 Punkte)

**Thema:** Typische JSON-Strukturfehler erkennen

Gegeben ist folgender fehlerhafter Code:

```java
public class FalscheMesswertDb {

    public void speichern(List<Temperaturmessung> messungen, String datei) throws IOException {
        String json = "{\"messungen\":{"; // FEHLER: Array statt Objekt!

        for (Temperaturmessung m : messungen) {
            json += "\"" + m.getId() + "\":{" +
                    "\"temperatur\":" + m.getTemperatur() + "," +
                    "\"ort\":\"" + m.getOrt() + "\"},";
        }

        json += "}}";
        Files.writeString(Paths.get(datei), json, StandardCharsets.UTF_8);
    }
}
```

**Fragen:**

a) **Strukturfehler:** Nenne einen Fehler in der JSON-Struktur, der dazu führt, dass die Datei sich später nicht regulär lesen lässt.

___________________________________________________________________________

___________________________________________________________________________

b) **Konsequenz:** Kann man mit diesem Format später über einen Index (0, 1, 2...) auf die Messwerte zugreifen? Warum/Warum nicht?

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 4: Datenredundanz und Anomalien (5 Punkte)

**Thema:** Redundanz erkennen und Normalisierung verstehen

Gegeben ist eine redundante Speicherform. Jede Messung speichert auch die Geräte-Info:

```json
{
  "messungen": [
    {"id": 1, "temp": 22.5, "device": "DS18B20 Sensor", "unit": "Celsius", "ort": "Kueche"},
    {"id": 2, "temp": 20.1, "device": "DS18B20 Sensor", "unit": "Celsius", "ort": "Schlafzimmer"},
    {"id": 3, "temp": 18.9, "device": "DS18B20 Sensor", "unit": "Celsius", "ort": "Buero"}
  ]
}
```

**Aufgabe:**

a) Erkläre das **Anomalieproblem:** Was würde passieren, wenn man nachträglich feststellt, dass die Einheit in Kelvin statt Celsius speichern möchte?

___________________________________________________________________________

___________________________________________________________________________

b) Wie würde die **normalisierte Lösung** aussehen (Schema/Daten-Trennung)?

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Best Practices für Sensormessungen (3 Punkte)

**Thema:** Robustheit bei kontinuierlichen Messungen

Nenne drei Best Practices für JSON-Speicherung von Messdaten (insbesondere bei kontinuierlichen oder wiederholten Messungen) und begründe jeweils kurz:

1. ________________________________________________________________
2. ________________________________________________________________
3. ________________________________________________________________

---

## Aufgabe 6: Fehlerhafte Timestamps korrigieren (3 Punkte)

**Thema:** Datenqualität sicherstellen

Der folgende Code hat einen Fehler beim Schreiben von Timestamps:

```java
for (Temperaturmessung m : messungen) {
    LocalDateTime now = LocalDateTime.now();  // FEHLER!
    json += "{\"zeitstempel\": \"" + now + "\", ...}";
}
```

**Aufgabe:**
1. Beschreibe den Fehler.
2. Zeige die korrekte Lösung.

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Erwartungshorizont (Kurzüberblick)

- **Sehr gut (23-25 Punkte):** Atomare Schreibweise verstanden, Range-Validierung sauber, Redundanz/Normalisierung klar analysiert.
- **Gut (19-22 Punkte):** Trennung und Validierung grundsätzlich richtig, kleinere Ungenauigkeiten bei Normalisierung oder Fehleranalyse.
- **Ausreichend (14-18 Punkte):** Grundideen erkannt, aber Lücken bei Validierunglogik oder Datenintegrität.
- **Unter 14 Punkte:** Wichtige Konzepte (Trennung, Validierung, Normalisierung) nicht hinreichend verstanden.
