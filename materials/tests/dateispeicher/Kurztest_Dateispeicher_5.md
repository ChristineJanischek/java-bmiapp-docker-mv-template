# Kurztest: Persistente Datenspeicherung - JSON-Datei lesen/schreiben mit Strukturtrennung

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: JSON-Datenbank schreiben - Struktur und Daten trennen (5 Punkte)

**Thema:** Trenne Datenbankschema und Nutzdaten in zwei Dateien

Ein Team speichert bisher alles in eine Datei. Das soll verbessert werden.

Es sollen zwei Dateien geschrieben werden:
- `personen.schema.json` (Struktur)
- `personen.data.json` (Daten)

```java
public class JsonDateiDatenbankWriter {

    public void speicherePersonenDatenbank(List<Person> personen, String ordner) throws IOException {
        Path schemaPfad = Paths.get(ordner, "personen.schema.json");
        Path datenPfad = Paths.get(ordner, "personen.data.json");

        Files.createDirectories(Paths.get(ordner));

        // 1) Schema-JSON (Struktur) erstellen:
        String schemaJson = """
            {
              "entity": "person",
              "version": 1,
              "fields": [
                {"name": "id", "type": "int", "required": true},
                {"name": "vorname", "type": "string", "required": true},
                {"name": "nachname", "type": "string", "required": true},
                {"name": "alter", "type": "int", "required": false}
              ]
            }
            """;

        // 2) Daten-JSON aus personen erzeugen:
        StringBuilder datenJson = new StringBuilder();
        datenJson.append("{\n  \"personen\": [\n");

        for (int i = 0; i < personen.size(); i++) {
            Person p = personen.get(i);

            datenJson.append("    {")
                     .append("\"id\": ").append(p.getId()).append(", ")
                     .append("\"vorname\": \"").append(p.getVorname()).append("\", ")
                     .append("\"nachname\": \"").append(p.getNachname()).append("\", ")
                     .append("\"alter\": ").append(p.getAlter())
                     .append("}");

            if (i < personen.size() - 1) {
                datenJson.append(",");
            }
            datenJson.append("\n");
        }

        datenJson.append("  ]\n}");

        // TODO: Schreibe beide Dateien mit UTF-8:
        //  - schemaJson -> schemaPfad
        //  - datenJson  -> datenPfad




    }
}
```

Vervollständige die Schreiblogik.

---

## Aufgabe 2: JSON-Datenbank lesen und gegen Struktur prüfen (4 Punkte)

**Thema:** Datenqualität beim Laden sicherstellen

```java
public class JsonDateiDatenbankReader {

    public List<Person> ladePersonenDatenbank(String ordner) throws IOException {
        Path schemaPfad = Paths.get(ordner, "personen.schema.json");
        Path datenPfad = Paths.get(ordner, "personen.data.json");

        // Lies beide Dateien:
        String schema = Files.readString(schemaPfad, StandardCharsets.UTF_8);  // 1 Punkt
        String daten = Files.readString(datenPfad, StandardCharsets.UTF_8);    // 1 Punkt

        // Einfache Strukturpruefung (ohne externe JSON-Library):
        if (!schema.contains("\"fields\"") || !schema.contains("\"entity\"")) {
            throw new IOException("Schema-Datei ungueltig");  // 1 Punkt
        }

        // TODO:
        // - pruefe, ob daten den Container "personen" enthaelt
        // - pruefe, ob Pflichtfelder id, vorname, nachname als Keys vorhanden sind
        // - gib vorerst eine leere Liste zurueck





    }
}
```

---

## Aufgabe 3: Code-Analyse - Trennung verletzt? (5 Punkte)

**Thema:** Architekturfehler erkennen

Gegeben ist folgender Code:

```java
public class FalscheJsonDb {

    public void speichern(List<Person> personen, String datei) throws IOException {
        String json = "{" +
            "\"schema\": {\"fields\": [\"id\",\"vorname\",\"nachname\"]}," +
            "\"daten\": [";

        for (Person p : personen) {
            json += "{\"id\":" + p.getId() + ",\"vorname\":\"" + p.getVorname() +
                    "\",\"nachname\":\"" + p.getNachname() + "\"},";
        }

        json += "]}";
        Files.writeString(Paths.get(datei), json, StandardCharsets.UTF_8);
    }
}
```

**Fragen:**

a) Nenne zwei Probleme dieses Ansatzes bezogen auf Wartbarkeit und Datenintegritaet.

___________________________________________________________________________

___________________________________________________________________________

b) Wie sollte die Trennung von Struktur und Daten stattdessen konkret aussehen?

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 4: Fehlersuche - JSON kaputt geschrieben (5 Punkte)

**Thema:** Typische Schreibfehler bei Dateispeicherung

```java
public void schreibeMesswerte(List<Double> bmiWerte, String datei) throws IOException {
    StringBuilder json = new StringBuilder();
    json.append("{\"messwerte\":[");

    for (int i = 0; i < bmiWerte.size(); i++) {
        json.append("{\"bmi\":").append(bmiWerte.get(i)).append("}");
        json.append(","); // Fehler?
    }

    json.append("]}");
    Files.writeString(Paths.get(datei), json.toString(), StandardCharsets.UTF_8);
}
```

**Aufgabe:**
1. Erklaere den Fehler.
2. Zeige eine korrigierte Schleife.

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Best Practices fuer JSON-Datei-Datenbanken (4 Punkte)

**Thema:** Robuste Umsetzung im Projekt

Nenne vier Best Practices und begruende jeweils kurz:

1. ________________________________________________________________
2. ________________________________________________________________
3. ________________________________________________________________
4. ________________________________________________________________

---

## Aufgabe 6: Kurzaufgabe - Backup vor dem Schreiben (2 Punkte)

**Thema:** Sicherheitskopie vor Ueberschreiben

Ergaenze die Methode so, dass vor dem Ueberschreiben von `personen.data.json`
eine Kopie `personen.data.json.bak` erstellt wird (falls Datei existiert).

```java
public void sicherSpeichern(Path datenPfad, String neuerInhalt) throws IOException {
    // TODO:
    // 1) Falls datenPfad existiert: Backup anlegen
    // 2) neuen Inhalt schreiben
}
```

---

## Erwartungshorizont (Kurzueberblick)

- **Sehr gut (22-25 Punkte):** Saubere Trennung von Struktur/Daten, robuste Dateioperationen,
  korrekte Fehleranalyse und klare Best-Practice-Argumentation.
- **Gut (18-21 Punkte):** Trennung verstanden, kleinere technische Ungenauigkeiten,
  aber funktional richtige Loesungen.
- **Ausreichend (13-17 Punkte):** Grundidee erkennbar, Luecken bei Validierung/Fehlerfaellen.
- **Unter 13 Punkte:** Wichtige Konzepte (Trennung, gueltiges JSON, Dateisicherheit) fehlen.