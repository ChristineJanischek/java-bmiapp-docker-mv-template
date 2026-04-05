# Kurztest: Persistente Datenspeicherung - JSON-Datei lesen/schreiben mit Strukturtrennung - LOESUNG

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: JSON-Datenbank schreiben - Struktur und Daten trennen (5 Punkte)

### Aufgabenstellung

**Thema:** Trenne Datenbankschema und Nutzdaten in zwei Dateien

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
    }
}
```

### Musterloesung - Schreiblogik:

```java
// Beide Dateien in UTF-8 schreiben:
Files.writeString(schemaPfad, schemaJson, StandardCharsets.UTF_8); // 2 Punkte
Files.writeString(datenPfad, datenJson.toString(), StandardCharsets.UTF_8); // 2 Punkte

// Optional: Erfolgsmeldung (nicht bewertet)
System.out.println("Schema und Daten erfolgreich gespeichert.");
```

### Alternative (mit byte[] API):

```java
Files.write(schemaPfad, schemaJson.getBytes(StandardCharsets.UTF_8));
Files.write(datenPfad, datenJson.toString().getBytes(StandardCharsets.UTF_8));
```

**Bewertung:**
- Schema-Datei korrekt geschrieben (2 Punkte)
- Daten-Datei korrekt geschrieben (2 Punkte)
- UTF-8 genutzt (1 Punkt)

---

## Aufgabe 2: JSON-Datenbank lesen und gegen Struktur pruefen (4 Punkte)

### Musterloesung:

```java
// Daten-Container pruefen
if (!daten.contains("\"personen\"")) { // 1 Punkt
    throw new IOException("Daten-Datei ungueltig: Container 'personen' fehlt");
}

// Pflichtfelder als Keys pruefen (einfache Plausibilitaetspruefung)
if (!daten.contains("\"id\"") ||
    !daten.contains("\"vorname\"") ||
    !daten.contains("\"nachname\"")) { // 1 Punkt
    throw new IOException("Daten-Datei ungueltig: Pflichtfelder fehlen");
}

return new ArrayList<>(); // 0 Punkte (vorgegeben)
```

**Bewertung:**
- Containerpruefung vorhanden (1 Punkt)
- Pflichtfelder geprueft (1 Punkt)
- Sinnvolle Fehlermeldung/Exception (1 Punkt)
- Strukturgedanke korrekt erklaert/umgesetzt (1 Punkt)

---

## Aufgabe 3: Code-Analyse - Trennung verletzt? (5 Punkte)

### a) Zwei Probleme

1. **Schema und Daten sind in einer Datei vermischt.**
   Jede Struktur-Aenderung erzwingt ein Neuschreiben der gesamten Datei.

2. **String-Konkatenation ist fehleranfaellig.**
   Es entstehen leicht ungueltige JSONs (z.B. Trailing-Komma, fehlendes Escaping).

### b) Korrekte Trennung

- Datei 1: `personen.schema.json` mit Feldern, Datentypen, Required-Informationen.
- Datei 2: `personen.data.json` mit reinen Datensaetzen.
- Beim Lesen zuerst Schema laden/pruefen, danach Daten dagegen validieren.

**Beispiel:**

```json
// personen.schema.json
{
  "entity": "person",
  "fields": [
    {"name":"id","type":"int","required":true},
    {"name":"vorname","type":"string","required":true},
    {"name":"nachname","type":"string","required":true}
  ]
}
```

```json
// personen.data.json
{
  "personen": [
    {"id":1,"vorname":"Mia","nachname":"Muster"}
  ]
}
```

**Bewertung:**
- Problem 1 korrekt (2 Punkte)
- Problem 2 korrekt (1 Punkt)
- Trennkonzept fachlich richtig (2 Punkte)

---

## Aufgabe 4: Fehlersuche - JSON kaputt geschrieben (5 Punkte)

### Fehlererklaerung:

Nach **jedem** Element wird ein Komma angehaengt, also auch nach dem letzten.
Das erzeugt ein ungueltiges JSON (Trailing-Komma).

### Korrigierte Schleife:

```java
for (int i = 0; i < bmiWerte.size(); i++) {
    json.append("{\"bmi\":").append(bmiWerte.get(i)).append("}"); // 2 Punkte

    if (i < bmiWerte.size() - 1) { // 2 Punkte
        json.append(",");
    }
}
```

**Bewertung:**
- Fehler korrekt identifiziert (2 Punkte)
- Schleife korrekt ohne Trailing-Komma (3 Punkte)

---

## Aufgabe 5: Best Practices fuer JSON-Datei-Datenbanken (4 Punkte)

### Musterantworten:

1. **Schema und Daten in getrennten Dateien halten.**
   Struktur ist stabil, Daten aendern sich haeufiger.

2. **Immer UTF-8 explizit angeben.**
   Sonderzeichen (Umlaute) bleiben plattformunabhaengig korrekt.

3. **Temp-Datei + atomisches Move beim Schreiben nutzen.**
   Verhindert kaputte Dateien bei Absturz waehrend des Schreibens.

4. **Vor dem Schreiben Backup erstellen.**
   Ermoeglicht schnelles Restore bei fehlerhaften Updates.

**Bewertung:**
- Pro valider Best Practice mit Begruendung: 1 Punkt (max. 4)

---

## Aufgabe 6: Kurzaufgabe - Backup vor dem Schreiben (2 Punkte)

### Musterloesung:

```java
public void sicherSpeichern(Path datenPfad, String neuerInhalt) throws IOException {
    if (Files.exists(datenPfad)) { // 1 Punkt
        Path backupPfad = Paths.get(datenPfad.toString() + ".bak");
        Files.copy(datenPfad, backupPfad, StandardCopyOption.REPLACE_EXISTING);
    }

    Files.writeString(datenPfad, neuerInhalt, StandardCharsets.UTF_8); // 1 Punkt
}
```

---

## Gesamt-Erwartungshorizont

- **Sehr gut (22-25 Punkte):** Trennung Struktur/Daten sicher umgesetzt, Fehler robust behandelt,
  Best Practices fachlich korrekt begruendet.
- **Gut (18-21 Punkte):** Gute technische Loesung mit kleineren Luecken.
- **Ausreichend (13-17 Punkte):** Grundprinzip verstanden, aber unvollstaendige Absicherung.
- **Unter 13 Punkte:** JSON-Grundlagen und Trennprinzip nicht ausreichend sicher umgesetzt.