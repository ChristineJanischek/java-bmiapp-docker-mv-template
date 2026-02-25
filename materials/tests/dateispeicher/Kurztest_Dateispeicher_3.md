# Kurztest: Persistente Datenspeicherung - Datenmigration & Validierung

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Benutzerdaten migrieren CSV → JSON (5 Punkte)

**Thema:** Datenformat konvertieren beim Systemwechsel

Ein altes System speichert Benutzer als CSV, das neue System braucht JSON:

```java
public class DataMigration {
    
    public void migriereCsvZuJson(String csvDatei, String jsonDatei) throws IOException {
        // Lese CSV-Datei:
        // username,email,abteilung
        // mueller,mueller@firma.de,IT
        // schmidt,schmidt@firma.de,Vertrieb
        
        // Schreibe ins JSON-Format:
        // {"users":[{"username":"mueller","email":"mueller@firma.de","abteilung":"IT"}, ...]}
        
        List<Map<String, String>> benutzer = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csvDatei))) {  // 1 Punkt
            String zeile = reader.readLine();  // Header
            
            while ((zeile = reader.readLine()) != null) {  // 1 Punkt
                String[] teile = zeile.split(",");
                
                if (teile.length == 3) {
                    Map<String, String> benutzer_map = new LinkedHashMap<>();  // 1 Punkt
                    benutzer_map.put("username", teile[0]);
                    benutzer_map.put("email", teile[1]);
                    benutzer_map.put("abteilung", teile[2]);
                    benutzer.add(benutzer_map);
                }
            }
        }
        
        // Schreibe JSON:
        Map<String, List<Map<String, String>>> root = new LinkedHashMap<>();  // 1 Punkt
        root.put("users", benutzer);
        
        // Konvertiere zu JSON-String und schreibe Datei:
        
        
        
    }
}
```

Vervollständige die JSON-Schreiblogik (pseudo-JSON ohne externe Library):

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: Eingaber-Validierung beim Einlesen (4 Punkte)

**Thema:** Fehlerhafte Daten erkennen und sammeln

```java
public class DataValidator {
    private List<String> fehlerhafte_zeilen = new ArrayList<>();
    
    public List<Inventar> validiereInventar(String csvDatei) throws IOException {
        List<Inventar> artikel = new ArrayList<>();
        int zeilenNummer = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csvDatei))) {
            String zeile;
            reader.readLine();  // Header überspringen
            
            while ((zeile = reader.readLine()) != null) {  // 1 Punkt
                zeilenNummer++;
                String[] teile = zeile.split(",");
                
                // Validiere Format: id,name,menge,preis  // 1 Punkt
                if (teile.length != 4) {
                    fehlerhafte_zeilen.add("Zeile " + zeilenNummer + ": Falsche Feldanzahl");
                    continue;
                }
                
                try {
                    int id = Integer.parseInt(teile[0]);  // 1 Punkt
                    String name = teile[1];
                    int menge = Integer.parseInt(teile[2]);
                    double preis = Double.parseDouble(teile[3]);
                    
                    if (menge < 0 || preis < 0) {  // 1 Punkt
                        fehlerhafte_zeilen.add("Zeile " + zeilenNummer + ": Negative Werte");
                        continue;
                    }
                    
                    artikel.add(new Inventar(id, name, menge, preis));
                    
                } catch (NumberFormatException e) {
                    fehlerhafte_zeilen.add("Zeile " + zeilenNummer + ": " + e.getMessage());
                }
            }
        }
        
        return artikel;
    }
    
    public void schreibeFehler(String ausgabeDatei) throws IOException {
        // Schreibe alle gesammelten Fehler in separate Datei:
        
        
        
        
    }
}
```

---

## Aufgabe 3: Datei-Vergleich mit Checksumme (5 Punkte)

**Thema:** Integrität von Dateien überprüfen

Schreibe eine Methode, die Checksummen (Hash) zur Verifizierung nutzt:

```java
import java.security.MessageDigest;

public class FileIntegrity {
    
    public String berechneChecksum(String dateiname) throws IOException {
        // Berechne SHA-256 Checksum einer Datei
        // Nutze Files.readAllBytes() um Inhalt zu lesen
        
        try {
            byte[] dateiInhalt = Files.readAllBytes(Paths.get(dateiname));  // 1 Punkt
            MessageDigest digest = MessageDigest.getInstance("SHA-256");  // 1 Punkt
            byte[] hashBytes = digest.digest(dateiInhalt);  // 1 Punkt
            
            // Konvertiere Bytes zu Hex-String:
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {  // 1 Punkt
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append("0");
                hexString.append(hex);
            }
            
            return hexString.toString();  // 1 Punkt
            
        } catch (Exception e) {
            throw new IOException("Checksum-Fehler: " + e.getMessage());
        }
    }
    
    public boolean istBackupKorrekt(String quelle, String backup) throws IOException {
        // Vergleiche Checksummen:
        
        
        
        
    }
}
```

---

## Aufgabe 4: Code-Analyse - Datenexport mit Fehlertoleranz (5 Punkte)

**Thema:** Robuste Datenbearbeitung trotz fehlenhafter Einträge

Gegeben ist dieser Code:

```java
public class RobustExporter {
    
    public void exportiereBenutzer(List<Benutzer> benutzer, String datei) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(datei))) {
            writer.println("id,username,email,status");
            
            for (Benutzer b : benutzer) {  // A: Schleife
                if (b.getEmail() != null && !b.getEmail().isEmpty()) {  // B: Validierung
                    writer.println(String.format("%d,%s,%s,%s",
                        b.getId(),
                        b.getUsername(),
                        b.getEmail(),
                        b.istAktiv() ? "ACTIVE" : "INACTIVE"));  // C: Format
                } else {
                    System.err.println("Benutzer " + b.getUsername() + " hat keine Email");  // D: Fehlerlog
                }
            }
        }
    }
}
```

**Fragen:**

a) Was zeigt Zeile D (Fehlerlog) auf System.err?

___________________________________________________________________________

b) Welche Benutzer werden **nicht** in die CSV-Datei geschrieben?

- ❏ Benutzer mit leerer Email
- ❏ Benutzer mit null als Email
- ❏ Inaktive Benutzer
- ❏ Alle der obigen

c) Ist dieser Ansatz für große Datenmengen geeignet (z.B. 1 Mio. Benutzer)? Erkläre:

___________________________________________________________________________

___________________________________________________________________________

d) Wie könnte man fehlende Werte besser verarbeiten?

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Logdatei-Archivierung (4 Punkte)

**Thema:** Alte Logs komprimieren und verschieben

```java
public class LogArchiver {
    
    public void archiviereLogs(String logVerzeichnis, String archivVerzeichnis) throws IOException {
        LocalDate heute = LocalDate.now();
        LocalDate grenzDatum = heute.minusDays(30);  // Älter als 30 Tage
        
        // 1. Finde alle .log Dateien im Verzeichnis:
        File logDir = new File(logVerzeichnis);
        File[] logDateien = logDir.listFiles((dir, name) -> name.endsWith(".log"));  // 1 Punkt
        
        if (logDateien == null) {
            throw new IOException("Verzeichnis nicht gefunden");
        }
        
        for (File logDatei : logDateien) {  // 1 Punkt
            // 2. Prüfe Änderungsdatum:
            LocalDate letzteAenderung = Instant.ofEpochMilli(logDatei.lastModified())  // 1 Punkt
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            
            // 3. Verschiebe alte Logs ins Archiv:
            if (letzteAenderung.isBefore(grenzDatum)) {
                Path quelle = logDatei.toPath();
                Path ziel = Paths.get(archivVerzeichnis, logDatei.getName() + ".archived");  // 1 Punkt
                
                Files.move(quelle, ziel, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
```

---

## Aufgabe 6: Fehlersuche - Encoding-Problem (2 Punkte)

**Thema:** Umlaute und Sonderzeichen richtig speichern

Gegeben ist fehlerhafter Code:

```java
public void speichereBenutzerId(List<Benutzer> benutzer, String datei) throws IOException {
    Files.write(Paths.get(datei),              // ← FEHLER!
        benutzer.stream()
        .map(b -> b.getName())
        .collect(Collectors.joining("\n"))
        .getBytes());
}
```

**Problem:** Eine Benutzerin heißt "Müller", wird aber als "M?ller" gespeichert.

a) Wo liegt das Encoding-Problem?

___________________________________________________________________________

___________________________________________________________________________

b) Schreibe den Code korrekt:

```java
public void speichereBenutzerId(List<Benutzer> benutzer, String datei) throws IOException {
    
    
    
}
```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Datenmigration CSV→JSON | 5 | |
| 2. Eingabe-Validierung | 4 | |
| 3. Datei-Integrität mit Checksum | 5 | |
| 4. Robuster Datenexport | 5 | |
| 5. Logdatei-Archivierung | 4 | |
| 6. Fehlersuche Encoding | 2 | |
| **Gesamt** | **25** | |

---

## Praxiskontext: Datenmigration & Validierung

### 🔄 Systemwechsel & Migration (Aufgabe 1)
- Altes System → neues System
- Format-Konvertierungen (CSV, JSON, XML)
- Batch-Processing großer Datenmengen

### ✅ Datenqualität (Aufgabe 2)
- Eingabe-Validierung
- Fehlerberichte
- Exception Handling ohne Programmabsturz

### 🔒 Datenintegrität (Aufgabe 3)
- Checksum / Hash-Vergleich
- Corruptibility Detection
- Backup-Verifizierung

### 📦 Datenmigration (Aufgabe 4)
- Fehlertolerante Datenverarbeitung
- Partial Success Szenarien
- Audit Logging

### 📋 Archivierung (Aufgabe 5)
- Automatische Log-Verwaltung
- Retention Policies
- Storage-Optimierung

**Real-World Tools:**
- Apache Kafka (Datenmigration)
- Great Expectations (Datenvalidierung)
- OpenSSL / md5sum (Checksummen)
- Logrotate (Log-Archivierung)
