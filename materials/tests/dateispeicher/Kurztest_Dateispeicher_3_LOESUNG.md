# Kurztest: Persistente Datenspeicherung - Datenmigration & Validierung - LOESUNG

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Benutzerdaten migrieren CSV → JSON (5 Punkte)

**Thema:** Datenformat konvertieren beim Systemwechsel

### Musterlösung (JSON-Schreiblogik):

```java
// Schreibe JSON:
Map<String, List<Map<String, String>>> root = new LinkedHashMap<>();
root.put("users", benutzer);

// Konvertiere zu JSON-String und schreibe Datei (einfache Variante):
StringBuilder json = new StringBuilder();  // 1 Punkt
json.append("{\"users\":[");

for (int i = 0; i < benutzer.size(); i++) {  // 1 Punkt
    Map<String, String> user = benutzer.get(i);
    json.append("{");
    json.append("\"username\":\"").append(user.get("username")).append("\",");
    json.append("\"email\":\"").append(user.get("email")).append("\",");
    json.append("\"abteilung\":\"").append(user.get("abteilung")).append("\"");
    json.append("}");
    
    if (i < benutzer.size() - 1) {  // 1 Punkt
        json.append(",");
    }
}

json.append("]}");

// Schreibe in Datei:
Files.write(Paths.get(jsonDatei), json.toString().getBytes(StandardCharsets.UTF_8));  // 2 Punkte
```

### Musterlösung 2 (mit Gson oder JSON-Library):

```java
// Mit Gson (besser in der Praxis):
Gson gson = new GsonBuilder().setPrettyPrinting().create();
String jsonString = gson.toJson(root);

Files.write(Paths.get(jsonDatei), jsonString.getBytes(StandardCharsets.UTF_8));
```

### Musterlösung 3 (mit Stream API):

```java
StringBuilder json = new StringBuilder();
json.append("{\"users\":[");

json.append(benutzer.stream()
    .map(user -> String.format(
        "{\"username\":\"%s\",\"email\":\"%s\",\"abteilung\":\"%s\"}",
        user.get("username"), user.get("email"), user.get("abteilung")))
    .collect(Collectors.joining(",")));

json.append("]}");

Files.write(Paths.get(jsonDatei), json.toString().getBytes(StandardCharsets.UTF_8));
```

**Bewertung:**
- LinkedHashMap für geordnete Daten (1 Punkt)
- CSV gelesen und in Maps konvertiert (1 Punkt)
- JSON-Struktur korrekt (1 Punkt)
- Trennzeichen/Kommas (1 Punkt)
- UTF-8 Encoding (1 Punkt)

**Beispiel CSV→JSON:**

Input CSV:
```csv
username,email,abteilung
mueller,mueller@firma.de,IT
schmidt,schmidt@firma.de,Vertrieb
```

Output JSON:
```json
{
  "users": [
    {"username":"mueller","email":"mueller@firma.de","abteilung":"IT"},
    {"username":"schmidt","email":"schmidt@firma.de","abteilung":"Vertrieb"}
  ]
}
```

---

## Aufgabe 2: Eingaber-Validierung beim Einlesen (4 Punkte)

**Thema:** Fehlerhafte Daten erkennen und sammeln

### Musterlösung für `schreibeFehler()`:

```java
public void schreibeFehler(String ausgabeDatei) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(ausgabeDatei))) {  // 1 Punkt
        if (fehlerhafte_zeilen.isEmpty()) {  // 1 Punkt
            writer.println("Keine Fehler gefunden.");
        } else {
            for (String fehler : fehlerhafte_zeilen) {  // 1 Punkt
                writer.println(fehler);
            }
        }
    }
}
```

### Alternative mit Stream API:

```java
public void schreibeFehler(String ausgabeDatei) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(ausgabeDatei))) {
        if (fehlerhafte_zeilen.isEmpty()) {
            writer.println("Keine Fehler gefunden.");
        } else {
            fehlerhafte_zeilen.stream()
                .forEach(writer::println);
        }
    }
}
```

**Beispiel Fehler-Output:**
```
Zeile 2: Falsche Feldanzahl
Zeile 5: Negative Werte
Zeile 8: java.lang.NumberFormatException: For input string: "abc"
```

**Bewertung:**
- try-with-resources (1 Punkt)
- Fehler iteriert (1 Punkt)
- In Datei geschrieben (1 Punkt)
- Korrekte Fehlerbehandlung (1 Punkt)

---

## Aufgabe 3: Datei-Vergleich mit Checksumme (5 Punkte)

**Thema:** Integrität von Dateien überprüfen

### Musterlösung für `istBackupKorrekt()`:

```java
public boolean istBackupKorrekt(String quelle, String backup) throws IOException {
    // Berechne Checksummen beider Dateien:
    String checksumQuelle = berechneChecksum(quelle);  // 1 Punkt
    String checksumBackup = berechneChecksum(backup);  // 1 Punkt
    
    // Vergleiche:
    boolean istIdentisch = checksumQuelle.equals(checksumBackup);  // 1 Punkt
    
    // Optional: Ausgabe für Debugging
    if (istIdentisch) {
        System.out.println("✓ Backup-Integrität OK");
    } else {
        System.err.println("✗ Backup korrupt!");
        System.err.println("Quelle:  " + checksumQuelle);
        System.err.println("Backup:  " + checksumBackup);
    }
    
    return istIdentisch;  // 1 Punkt
}
```

**Alternative mit try-catch:**

```java
public boolean istBackupKorrekt(String quelle, String backup) {
    try {
        return berechneChecksum(quelle)
            .equals(berechneChecksum(backup));
    } catch (IOException e) {
        System.err.println("Fehler beim Checksum-Vergleich: " + e.getMessage());
        return false;  // oder throw
    }
}
```

**Bewertung der Checksum-Berechnung:**
- Files.readAllBytes() verwendet (1 Punkt)
- MessageDigest.getInstance("SHA-256") (1 Punkt)
- digest() aufgerufen (1 Punkt)
- Schleife für Hex-Konvertierung (1 Punkt)
- Rückgabe des Hex-Strings (1 Punkt)

**Beispiel Checksummen:**

Datei 1: `users.csv`
```
a7b5f2e1c3d9...
```

Datei 2: `users.csv.backup`
```
a7b5f2e1c3d9...  (identisch = Backup OK!)
```

**Beispiel bei Corruption:**
```
Quelle:  a7b5f2e1c3d9...
Backup:  b8c6g3f2d4e0...  (unterschiedlich!)
```

---

## Aufgabe 4: Code-Analyse - Datenexport mit Fehlertoleranz (5 Punkte)

**Thema:** Robuste Datenbearbeitung trotz fehlerhafter Einträge

### Musterlösung:

a) **Was zeigt Zeile D? (1 Punkt)**

"Zeile D gibt eine Fehlernachricht auf System.err (Error Stream) aus für jeden Benutzer, der keine gültige Email-Adresse hat. Diese Meldung wird nicht in die CSV-Datei geschrieben, sondern nur auf der Konsole angezeigt."

**Kernpunkt:**
- Fehlerausgabe auf stderr statt stdout (0.5 Punkte)
- Benutzer haben keine oder leere Email (0.5 Punkte)

b) **Welche Benutzer werden NICHT geschrieben? (2 Punkte - nur bei allen richtig!)**

Korrekte Antworten:
- ✓ Benutzer mit leerer Email (wenn `isEmpty()`)
- ✓ Benutzer mit null als Email (wenn `null`)
- ✗ Inaktive Benutzer (werden geschrieben mit Status "INACTIVE")
- ✓ **Alle der beiden obigen** (aber nicht die dritten)

**Erklärung:**
Die Bedingung `if (b.getEmail() != null && !b.getEmail().isEmpty())` prüft, dass Email nicht null UND nicht leer ist. Benutzer ohne gültige Email werden übersprungen.

c) **Ist dieser Ansatz für große Datenmengen geeignet? (1 Punkt)**

"Ja, dieser Ansatz ist geeignet, weil:
- Benutzer einzeln verarbeitet werden (kein vollständiges In-Memory Laden)
- Fehler werden protokolliert statt das Programm zu stoppen (robust)
- Datei wird sequenziell geschrieben (speichereffizient)

Nachteil: System.err für großer Mengen (Mio. Benutzer) könnte Performance-Problem sein."

**Alternative Formulierung:**
- "Ja, Streaming ist effizienter als alle Daten zu Laden"
- "Nein, wenn die Fehlerausgabe zu viele Zeilen sind" (auch akzeptabel)

d) **Wie könnte man fehlende Werte besser verarbeiten? (1 Punkt)**

"Man könnte fehlende Werte durch Defaults ersetzen, z.B.:
- `String email = b.getEmail() != null ? b.getEmail() : "no-email@firma.de";`
- Oder leere Email mit Platzhalter: `"-"`
- Oder in separater Fehler-Datei sammeln statt auf stderr ausgeben"

**Weitere Optionen:**
- Validiera auf Konstruktor-Ebene (nicht null möglich)
- Nullable-Wrapper verwenden (`Optional<String>`)
- Fehler-Datei anstatt stderr
- Fehler-Counter zur Statistik

**Bewertung:**
- a) Zeile D erklärt (1 Punkt)
- b) Alle drei Optionen richtig bewertet (2 Punkte)
- c) Große Datenmengen bewertet (1 Punkt)
- d) Alternative vorgeschlagen (1 Punkt)

---

## Aufgabe 5: Logdatei-Archivierung (4 Punkte)

**Thema:** Alte Logs komprimieren und verschieben

### Code ist bereits komplett, hier die Bewertung:

```java
public void archiviereLogs(String logVerzeichnis, String archivVerzeichnis) throws IOException {
    LocalDate heute = LocalDate.now();
    LocalDate grenzDatum = heute.minusDays(30);
    
    // listFiles() mit Filter  // 1 Punkt
    File[] logDateien = new File(logVerzeichnis)
        .listFiles((dir, name) -> name.endsWith(".log"));
    
    if (logDateien == null) {
        throw new IOException("Verzeichnis nicht gefunden");
    }
    
    // Iteriere über alle Log-Dateien  // 1 Punkt
    for (File logDatei : logDateien) {
        // Berechne Änderungsdatum  // 1 Punkt
        LocalDate letzteAenderung = Instant.ofEpochMilli(logDatei.lastModified())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        
        // Verschiebe alte Logs  // 1 Punkt
        if (letzteAenderung.isBefore(grenzDatum)) {
            Path quelle = logDatei.toPath();
            Path ziel = Paths.get(archivVerzeichnis, logDatei.getName() + ".archived");
            
            Files.move(quelle, ziel, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
```

**Bewertung Komponenten:**
- `listFiles()` mit Filter nutzt (1 Punkt)
- Schleife über Dateien (1 Punkt)
- lastModified() → LocalDate konvertiert (1 Punkt)
- Files.move() mit Ziel-Pfad (1 Punkt)

**Beispiel-Szenario:**

Vor Archivierung:
```
/logs/
  app-2026-02-01.log (alt)
  app-2026-02-20.log (neu)
  app-2026-02-25.log (aktuell)
```

Nach Archivierung (mit Grenz-Datum 30 Tage):
```
/logs/
  app-2026-02-20.log
  app-2026-02-25.log

/archive/
  app-2026-02-01.log.archived
```

---

## Aufgabe 6: Fehlersuche - Encoding-Problem (2 Punkte)

**Thema:** Umlaute und Sonderzeichen richtig speichern

### Musterlösung:

a) **Wo liegt das Encoding-Problem? (1 Punkt)**

"Das Problem liegt bei `.getBytes()` ohne Encoding-Angabe. Die Methode nutzt das Standard-Encoding der JVM (oft ISO-8859-1), das Umlaute nicht korrekt speichert. UTF-8 ist für internationale Zeichen erforderlich."

**Kernpunkte:**
- getBytes() nutzt Default-Encoding (0.5 Punkte)
- UTF-8 ist notwendig für Umlaute (0.5 Punkte)

**Erklärung des Problems:**
```
"Müller".getBytes()           // ISO-8859-1 → M?ller (falsch!)
"Müller".getBytes("UTF-8")    // UTF-8 → Müller (korrekt)
"Müller".getBytes(StandardCharsets.UTF_8)  // auch korrekt
```

b) **Korrekter Code (1 Punkt):**

```java
public void speichereBenutzerId(List<Benutzer> benutzer, String datei) throws IOException {
    Files.write(Paths.get(datei),              // 0.5 Punkte
        benutzer.stream()
        .map(b -> b.getName())
        .collect(Collectors.joining("\n"))
        .getBytes(StandardCharsets.UTF_8));    // 0.5 Punkte - UTF-8 Encoding
}
```

**Alternative Variante:**

```java
public void speichereBenutzerId(List<Benutzer> benutzer, String datei) throws IOException {
    List<String> namen = benutzer.stream()
        .map(Benutzer::getName)
        .collect(Collectors.toList());
    
    Files.write(Paths.get(datei), namen, StandardCharsets.UTF_8);
}
```

**Oder mit expliziter Charset:**

```java
public void speichereBenutzerId(List<Benutzer> benutzer, String datei) throws IOException {
    PrintWriter writer = new PrintWriter(
        new OutputStreamWriter(
            new FileOutputStream(datei),
            StandardCharsets.UTF_8));  // Explizites UTF-8
    
    for (Benutzer b : benutzer) {
        writer.println(b.getName());
    }
    writer.close();
}
```

**Bewertung:**
- StandardCharsets.UTF_8 verwendet (1 Punkt)
- Oder ".getBytes("UTF-8")" (auch akzeptabel)

---

## Bewertungsschema

| Punkte | Note |
|--------|------|
| 25-23  | 1 (sehr gut) |
| 22-20  | 2 (gut) |
| 19-17  | 3 (befriedigend) |
| 16-13  | 4 (ausreichend) |
| 12-0   | 5 (mangelhaft) |

---

## Häufige Fehler

### JSON-Generierung
- ❌ Keine Escape-Sequenzen für Sonderzeichen
- ❌ Kommas vergessen zwischen Objekten
- ✓ StringBuilder oder externe Library (Gson, Jackson)

### Validierung
- ❌ Fehler stoppen Verarbeitung (statt zu sammeln)
- ✓ Fehler sammeln, in separater Datei schreiben

### Checksummen
- ❌ Datei mehrfach lesen
- ✓ Einmal lesen, Checksum speichern

### Encoding
- ❌ getBytes() ohne Encoding
- ✓ StandardCharsets.UTF_8
- ✓ Files.write() nutzt UTF-8 per Default

### Datei-Operationen
- ❌ Dateihandles nicht geschlossen
- ✓ try-with-resources verwenden

---

## Didaktischer Hinweis

### Lernziele Test 3:

1. **Datenformat-Konvertierung:**
   - CSV → JSON
   - Strukturierte vs. flache Daten

2. **Fehlertoleranz:**
   - Validierung ohne Programmabsturz
   - Error Logging

3. **Datenintegrität:**
   - Checksummen/Hashing
   - Backup-Verifizierung

4. **Robuste Verarbeitung:**
   - Partielle Erfolge
   - Fehlerberichte

5. **Character Encoding:**
   - UTF-8 vs. ISO-8859-1
   - Internationalisierung

### Reale Anwendungen:

- **ETL-Tools:** Datenformat-Konvertierung
- **CI/CD Pipelines:** Datamigration
- **Backup-Systeme:** Checksummen-Verifizierung
- **Internationalisierte Apps:** Encoding-Handling
- **Data Quality:** Validierung & Error Logging

### Typische Fehlerquellen:

1. **JSON-Parsing:** Ungültige Struktur
2. **Encoding:** Umlaute/Sonderzeichen verloren
3. **Fehlerbehandlung:** zu streng (abbrechen) oder zu locker (ignorieren)
4. **Performance:** Alle Daten in RAM laden
5. **Ressourcen:** Streams nicht geschlossen

