# Kurztest: Persistente Datenspeicherung - Server-Monitoring & Performance-Logging - LOESUNG

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Performance-Daten speichern (5 Punkte)

### Aufgabenstellung

**Thema:** Server-Performance in strukturiertem Format persistieren

Ein Monitoring-System sammelt Performance-Daten (CPU, RAM, Disk) und muss diese speichern:

```java
public class PerformanceLogger {
    private List<PerformanceDaten> messungen;
    
    public void speichereAls_CSV(String dateiname) {
        // Format: timestamp,cpu_prozent,ram_mb,disk_prozent
        // Beispiel: 2026-02-25 14:30:15,45.2,8192,75.8
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(dateiname))) {  // Header
            writer.println("timestamp,cpu_prozent,ram_mb,disk_prozent");  
            
            // Schreibe alle Messungen:
            
            
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class PerformanceDaten {
    private LocalDateTime zeitstempel;
    private double cpuProzent;
    private long ramMB;
    private double diskProzent;
    
    // Constructor, Getter...
    public String toCSV() {
        return String.format("%s,%.1f,%d,%.1f", 
            zeitstempel, cpuProzent, ramMB, diskProzent);
    }
}
```

Vervollständige die `speichereAls_CSV()` Methode (verwende die `toCSV()` Methode):

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösung:

```java
public void speichereAls_CSV(String dateiname) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(dateiname))) {  // 1 Punkt
        // Header
        writer.println("timestamp,cpu_prozent,ram_mb,disk_prozent");
        
        // Schreibe alle Messungen:
        for (PerformanceDaten daten : messungen) {  // 1 Punkt
            writer.println(daten.toCSV());  // 1 Punkt
        }
    } catch (IOException e) {  // 1 Punkt
        e.printStackTrace();
    }
}
```

**Alternative mit StringBuilder:**
```java
public void speichereAls_CSV(String dateiname) throws IOException {
    StringBuilder csv = new StringBuilder();
    csv.append("timestamp,cpu_prozent,ram_mb,disk_prozent\n");
    
    for (PerformanceDaten daten : messungen) {
        csv.append(daten.toCSV()).append("\n");
    }
    
    Files.write(Paths.get(dateiname), csv.toString().getBytes());  // 2 Punkte
}
```

**Bewertung:**
- try-with-resources oder Files API (1 Punkt)
- Iteration über Liste (1 Punkt)
- toCSV() aufgerufen (1 Punkt)
- Korrekt geschrieben mit Newline (1 Punkt)
- Exception Handling (1 Punkt)

**Beispiel CSV-Ausgabe:**
```csv
timestamp,cpu_prozent,ram_mb,disk_prozent
2026-02-25 14:30:15,45.2,8192,75.8
2026-02-25 14:31:15,52.1,8256,76.1
2026-02-25 14:32:15,48.9,8192,75.9
```

---

## Aufgabe 2: Server-Status aus Datei laden und prüfen (4 Punkte)

### Aufgabenstellung

**Thema:** Sicherheitsstatus von Servern überprüfen

Lies eine Datei mit Server-Informationen und prüfe, welche Server Updates benötigen:

```java
public class ServerManager {
    
    public List<Server> ladeServerStatus(String dateiname) throws IOException {
        List<Server> server = new ArrayList<>();
        
        // Dateiformat:
        // hostname,ipaddresse,betriebssystem,letzte_update
        // webserver01,192.168.1.50,Ubuntu 22.04,2026-01-15
        // dbserver,192.168.1.51,CentOS 7,2025-10-20
        
        
        
        
        
        
        
        
        
        
        return server;
    }
    
    public List<Server> findeServerMitAltenUpdates(List<Server> server) {
        LocalDate heute = LocalDate.now();
        LocalDate grenzDatum = heute.minusMonths(3);  // älter als 3 Monate
        
        // Filtere Server, deren letztes Update älter als 3 Monate ist:
        
        
        
        
    }
}

class Server {
    private String hostname;
    private String ipaddresse;
    private String betriebssystem;
    private LocalDate letzteUpdate;
    
    public Server(String hostname, String ipaddresse, String betriebssystem, LocalDate letzteUpdate) {
        this.hostname = hostname;
        this.ipaddresse = ipaddresse;
        this.betriebssystem = betriebssystem;
        this.letzteUpdate = letzteUpdate;
    }
    
    // Getter...
    public LocalDate getLetzteUpdate() { return letzteUpdate; }
    public String getHostname() { return hostname; }
}
```

---

### Musterlösung Teil 1 - `ladeServerStatus()`:

```java
public List<Server> ladeServerStatus(String dateiname) throws IOException {
    List<Server> server = new ArrayList<>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(dateiname))) {  // 1 Punkt
        String zeile = reader.readLine();  // Header überspringen
        
        while ((zeile = reader.readLine()) != null) {  // 1 Punkt
            String[] teile = zeile.split(",");  // 1 Punkt
            
            if (teile.length == 4) {
                String hostname = teile[0];
                String ip = teile[1];
                String os = teile[2];
                LocalDate updateDatum = LocalDate.parse(teile[3]);  // 1 Punkt
                
                server.add(new Server(hostname, ip, os, updateDatum));
            }
        }
    }
    
    return server;
}
```

### Musterlösung Teil 2 - `findeServerMitAltenUpdates()`:

```java
public List<Server> findeServerMitAltenUpdates(List<Server> server) {
    LocalDate heute = LocalDate.now();
    LocalDate grenzDatum = heute.minusMonths(3);
    
    // Variante 1: mit Stream API
    return server.stream()
        .filter(s -> s.getLetzteUpdate().isBefore(grenzDatum))
        .collect(Collectors.toList());
    
    // Variante 2: mit for-Schleife
    List<Server> altServer = new ArrayList<>();
    for (Server s : server) {
        if (s.getLetzteUpdate().isBefore(grenzDatum)) {
            altServer.add(s);
        }
    }
    return altServer;
}
```

**Bewertung:**
- Datei gelesen (1 Punkt)
- Split und Parsing (1 Punkt)
- LocalDate.parse() verwendet (1 Punkt)
- Filter mit Stream oder for-Schleife (1 Punkt)

**Beispiel-Eingabedatei:**
```csv
hostname,ipaddresse,betriebssystem,letzte_update
webserver01,192.168.1.50,Ubuntu 22.04,2026-01-15
dbserver,192.168.1.51,CentOS 7,2025-10-20
fileserver,192.168.1.52,Ubuntu 20.04,2025-11-30
```

---

## Aufgabe 3: Performance-Anomalien erkennen und schreiben (5 Punkte)

### Aufgabenstellung

**Thema:** Automatische Zeichen setzung bei kritischen Werten

```java
public class PerformanceAnalyzer {
    
    public void erkennePeaks(String eingabeDatei, String ausgabeDatei) throws IOException {
        // Lies CSV-Datei mit Performance-Daten
        // Schreibe kritische Messungen (CPU > 80% oder RAM > 90%) in separate Datei
        // Format der Ausgabedatei: timestamp,cpu,ram,disk,status
        // Status: "CRITICAL_CPU" oder "CRITICAL_RAM" oder "OK"
        
        try (BufferedReader reader = new BufferedReader(new FileReader(eingabeDatei));
             PrintWriter writer = new PrintWriter(new FileWriter(ausgabeDatei))) {  // 1 Punkt
            
            String zeile = reader.readLine();  // Header überspringen
            
            while ((zeile = reader.readLine()) != null) {  // 1 Punkt
                String[] teile = zeile.split(",");  // 1 Punkt
                
                if (teile.length >= 4) {
                    double cpu = Double.parseDouble(teile[1]);  // 1 Punkt
                    long ram = Long.parseLong(teile[2]);
                    
                    // Bestimme Status und schreibe:
                    
                    
                    
                }
            }
        }
    }
}
```

---

### Musterlösung:

```java
public void erkennePeaks(String eingabeDatei, String ausgabeDatei) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(eingabeDatei));
         PrintWriter writer = new PrintWriter(new FileWriter(ausgabeDatei))) {  // 1 Punkt
        
        String zeile = reader.readLine();  // Header überspringen
        writer.println("timestamp,cpu,ram,disk,status");  // Neuer Header
        
        while ((zeile = reader.readLine()) != null) {  // 1 Punkt
            String[] teile = zeile.split(",");
            
            if (teile.length >= 4) {  // 1 Punkt
                double cpu = Double.parseDouble(teile[1]);  // 1 Punkt
                long ram = Long.parseLong(teile[2]);
                double disk = Double.parseDouble(teile[3]);
                String status = "OK";
                
                // Bestimme Status:  // 1 Punkt
                if (cpu > 80.0) {
                    status = "CRITICAL_CPU";
                } else if (ram > 90) {
                    status = "CRITICAL_RAM";
                }
                
                writer.println(zeile + "," + status);
            }
        }
    }
}
```

**Alternative mit mehrfachen Bedingungen:**
```java
// Oder mit mehreren kritischen Bedingungen:
String status = "OK";
if (cpu > 80.0 && ram > 90) {
    status = "CRITICAL_CPU_AND_RAM";
} else if (cpu > 80.0) {
    status = "CRITICAL_CPU";
} else if (ram > 90) {
    status = "CRITICAL_RAM";
} else if (disk > 95) {
    status = "CRITICAL_DISK";
}
```

**Bewertung:**
- try-with-resources für beide Reader/Writer (1 Punkt)
- Schleife zum Lesen (1 Punkt)
- Parsing von double/long (1 Punkt)
- Bedingungen für Schwellwerte (1 Punkt)
- Output geschrieben (1 Punkt)

**Beispiel-Verarbeitung:**

Input:
```csv
timestamp,cpu_prozent,ram_mb,disk_prozent
2026-02-25 14:30:15,45.2,8192,75.8
2026-02-25 14:31:15,85.1,9200,76.1
2026-02-25 14:32:15,48.9,9300,95.2
```

Output:
```csv
timestamp,cpu,ram,disk,status
2026-02-25 14:30:15,45.2,8192,75.8,OK
2026-02-25 14:31:15,85.1,9200,76.1,CRITICAL_CPU
2026-02-25 14:32:15,48.9,9300,95.2,CRITICAL_RAM
```

---

## Aufgabe 4: Code-Analyse - Datei-Vergleich für Backup-Verifizierung (5 Punkte)

### Aufgabenstellung

**Thema:** Backup-Integrität überprüfen

Gegeben ist folgender Code:

```java
public class BackupVerifier {
    
    public boolean istBackupAktuell(String source, String backup) throws IOException {
        List<String> sourceLines = Files.readAllLines(Paths.get(source));  // 1
        List<String> backupLines = Files.readAllLines(Paths.get(backup));  // 2
        
        if (sourceLines.size() != backupLines.size()) {
            return false;  // 3
        }
        
        for (int i = 0; i < sourceLines.size(); i++) {
            if (!sourceLines.get(i).equals(backupLines.get(i))) {
                return false;  // 4
            }
        }
        
        return true;  // 5
    }
    
    public boolean istBackupAktuellModern(String source, String backup) throws IOException {
        return Files.readAllLines(Paths.get(source))
            .equals(Files.readAllLines(Paths.get(backup)));
    }
}
```

**Fragen:**

a) Welche Zeilen (1-5) können IOException werfen und warum?

___________________________________________________________________________

___________________________________________________________________________

b) Wann würde `istBackupAktuell()` false zurückgeben?

- ❏ Backup ist leer, Source nicht
- ❏ Eine Zeile unterscheidet sich
- ❏ Source und Backup sind identisch
- ❏ Backup existiert nicht

c) Was ist der Vorteil von `istBackupAktuellModern()` gegenüber der klassischen Variante?

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösung:

a) **Welche Zeilen können IOException werfen? (2 Punkte)**

"Zeile 1 (`Files.readAllLines()` für source) und Zeile 2 (`Files.readAllLines()` für backup) können IOException werfen, falls die Dateien nicht existieren oder nicht gelesen werden dürfen (Berechtigungen)."

**Kernpunkte:**
- Files.readAllLines() wirft IOException (1 Punkt)
- Wenn Datei nicht existiert oder nicht lesbar (1 Punkt)

b) **Wann würde `istBackupAktuell()` false zurückgeben? (2 Punkte)**

Mehrfach-Antwort, alle zutreffenden Optionen:
- ✓ Backup ist leer, Source nicht (unterschiedliche Zeilenanzahl)
- ✓ Eine Zeile unterscheidet sich (nicht equal)
- ✗ Source und Backup sind identisch (return true)
- ✓ Backup existiert nicht (IOException, aber würde als Fehler behandelt)

**Erwartete Antwort:** Erste, zweite und vierte Option sind richtig.

c) **Vorteil von `istBackupAktuellModern()`? (1 Punkt)**

" `istBackupAktuellModern()` ist eleganter und kürzer, weil es direkt List.equals() nutzt. Dies vergleicht alle Zeilen in einer Operation. Die klassische Variante muss alle Zeilen manuell vergleichen."

**Alternative Formulierungen:**
- "Bessere Lesbarkeit durch funktionale API"
- "Kürzerer Code, weniger Fehleranfällig"
- "Nutzt die equals()-Methode von List-Objekten"

**Bewertung:**
- Zeilen zutreffend identifiziert (2 Punkte)
- Optionen korrekt ausgewählt (2 Punkte)
- Vorteil erklärt (1 Punkt)

---

## Aufgabe 5: Zentrale Log-Datei sammeln (4 Punkte)

### Aufgabenstellung

**Thema:** Logs von mehreren Servern zusammenfassen

Schreibe eine Methode, die Log-Dateien mehrerer Server in eine zentrale Datei zusammenführt:

```java
public class LogAggregator {
    
    public void aggregiereLogs(List<String> serverLogDateien, String zentraleDatei) throws IOException {
        // Lese alle Log-Dateien und schreibe sie in eine zentrale Datei
        // Jede Zeile soll mit Servername präfixiert werden
        // Format: [servername] original_log_eintrag
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(zentraleDatei))) {
            
            for (String logDatei : serverLogDateien) {
                // Extrahiere Servername aus Pfad (z.B. "/logs/webserver01.log" → "webserver01")
                String servername = Paths.get(logDatei).getFileName()
                    .toString()
                    .replace(".log", "");
                
                // Lese Log-Datei:
                
                
                
                
                
                
            }
        }
    }
}
```

---

### Musterlösung:

```java
public void aggregiereLogs(List<String> serverLogDateien, String zentraleDatei) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(zentraleDatei))) {  // 1 Punkt
        
        for (String logDatei : serverLogDateien) {  // 1 Punkt
            // Extrahiere Servername
            String servername = Paths.get(logDatei).getFileName()
                .toString()
                .replace(".log", "");
            
            // Lese Log-Datei:
            try (BufferedReader reader = new BufferedReader(new FileReader(logDatei))) {  // 0.5 Punkt
                String zeile;
                while ((zeile = reader.readLine()) != null) {  // 0.5 Punkt
                    writer.println("[" + servername + "] " + zeile);  // 1 Punkt
                }
            }
        }
    }
}
```

**Alternative mit Files API:**
```java
public void aggregiereLogs(List<String> serverLogDateien, String zentraleDatei) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(zentraleDatei))) {
        for (String logDatei : serverLogDateien) {
            String servername = Paths.get(logDatei).getFileName()
                .toString()
                .replace(".log", "");
            
            Files.readAllLines(Paths.get(logDatei))
                .forEach(zeile -> writer.println("[" + servername + "] " + zeile));
        }
    }
}
```

**Bewertung:**
- Äußere Schleife über Dateiliste (1 Punkt)
- Servername extrahiert (1 Punkt)
- Innere try-with-resources für Reader (1 Punkt)
- Zeilen mit Prefix geschrieben (1 Punkt)

**Beispiel-Verarbeitung:**

Input-Dateien:
- `/logs/webserver01.log`: ERROR: Connection timeout
- `/logs/dbserver.log`: WARNING: Slow query

Zentrale Ausgabe:
```
[webserver01] ERROR: Connection timeout
[dbserver] WARNING: Slow query
```

---

## Aufgabe 6: Fehlersuche - try-with-resources mit mehreren Resources (2 Punkte)

### Aufgabenstellung

**Thema:** Mehrere Dateien gleichzeitig verarbeiten

Finde den Fehler im Code:

```java
public void kopiereMitFilter(String quelle, String ziel) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(quelle));
    PrintWriter writer = new PrintWriter(new FileWriter(ziel));  // ← FEHLER!
    
    String zeile;
    while ((zeile = reader.readLine()) != null) {
        if (zeile.startsWith("ERROR")) {
            writer.println(zeile);
        }
    }
    
    reader.close();
    writer.close();
}
```

a) Was ist das Hauptproblem mit diesem Code?

___________________________________________________________________________

___________________________________________________________________________

b) Schreibe den Code mit try-with-resources korrekt neu:

```java
public void kopiereMitFilter(String quelle, String ziel) throws IOException {
    
    
    
    
    
    
    
}
```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Performance-Daten speichern | 5 | |
| 2. Server-Status laden und prüfen | 4 | |
| 3. Performance-Anomalien erkennen | 5 | |
| 4. Backup-Verifizierung Analyse | 5 | |
| 5. Zentrale Log-Datei sammeln | 4 | |
| 6. Fehlersuche try-with-resources | 2 | |
| **Gesamt** | **25** | |

---

## Praxiskontext: Server-Monitoring & Logging

Diese Aufgaben basieren auf realen Szenarien in der Systemadministration:

### 📊 Performance-Monitoring (Aufgaben 1 & 3)
- CPU, RAM, Disk-Auslastung aufzeichnen
- Zeitreihen-Daten analysieren
- Automatische Alarme bei Schwellwerten

### 🖥️ Server-Management (Aufgabe 2)
- Inventar von Servern führen
- Update-Status überwachen
- Security-Patches planen

### 💾 Backup & Disaster Recovery (Aufgabe 4)
- Backup-Integrität überprüfen
- Datenbank-Konsistenz sichern
- Automatisierte Verifizierung

### 📋 Zentrale Logs (Aufgabe 5)
- ELK-Stack ähnlich (Elasticsearch-Logstash-Kibana)
- Syslog-Server Konzepte
- Log-Aggregation für Analyse

**Typische Tools mit ähnlichen Konzepten:**
- Prometheus / Grafana (Monitoring)
- ELK-Stack (Log-Aggregation)
- Nagios / Zabbix (Performance-Überwachung)
- Logrotate / syslog-ng (Log-Management)

### 📊 Performance-Monitoring (Aufgaben 1 & 3)
- CPU, RAM, Disk-Auslastung aufzeichnen
- Zeitreihen-Daten analysieren
- Automatische Alarme bei Schwellwerten

### 🖥️ Server-Management (Aufgabe 2)
- Inventar von Servern führen
- Update-Status überwachen
- Security-Patches planen

### 💾 Backup & Disaster Recovery (Aufgabe 4)
- Backup-Integrität überprüfen
- Datenbank-Konsistenz sichern
- Automatisierte Verifizierung

### 📋 Zentrale Logs (Aufgabe 5)
- ELK-Stack ähnlich (Elasticsearch-Logstash-Kibana)
- Syslog-Server Konzepte
- Log-Aggregation für Analyse

**Typische Tools mit ähnlichen Konzepten:**
- Prometheus / Grafana (Monitoring)
- ELK-Stack (Log-Aggregation)
- Nagios / Zabbix (Performance-Überwachung)
- Logrotate / syslog-ng (Log-Management)


**Thema:** Mehrere Dateien gleichzeitig verarbeiten

### Musterlösung:

a) **Hauptproblem (1 Punkt):**

"Reader und Writer sind nicht in einem try-with-resources Block. Falls während der Verarbeitung eine Exception tritt, werden `reader.close()` und `writer.close()` nicht aufgerufen. Dies führt zu Resource Leaks (offene Dateihandles). Außerdem: Falls `reader.close()` fehlschlägt, wird `writer.close()` nicht aufgerufen."

**Kernpunkte:**
- Ressourcen nicht automatisch geschlossen bei Exception (1 Punkt)
- Resource Leak (0.5 Punkte Extra)

b) **Korrekter Code (1 Punkt):**

```java
public void kopiereMitFilter(String quelle, String ziel) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(quelle));
         PrintWriter writer = new PrintWriter(new FileWriter(ziel))) {  // 0.5 Punkte
        
        String zeile;
        while ((zeile = reader.readLine()) != null) {
            if (zeile.startsWith("ERROR")) {
                writer.println(zeile);
            }
        }
    }  // catch optional
}
```

**Alternative mit catch:**
```java
public void kopiereMitFilter(String quelle, String ziel) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(quelle));
         PrintWriter writer = new PrintWriter(new FileWriter(ziel))) {
        
        String zeile;
        while ((zeile = reader.readLine()) != null) {
            if (zeile.startsWith("ERROR")) {
                writer.println(zeile);
            }
        }
    } catch (IOException e) {
        System.err.println("Fehler: " + e.getMessage());
        throw e;
    }
}
```

**Bewertung:**
- Beide Resources in try() (0.5 Punkte)
- Semicolon zwischen Ressourcen (0.5 Punkte)

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

### Collection vs. einzelne Ressourcen
- ❌ Jede Ressource einzeln close() aufrufen
- ✓ try-with-resources mit mehreren Resources (Semicolon-trennung)

### Performance-Logging
- ❌ Alle Daten erst sammeln, dann schreiben → MemoryError
- ✓ Während Sammlung direkt schreiben (Streaming)

### Log-Aggregation
- ❌ Writer pro Server öffnen/schließen → ineffizient
- ✓ Einen Writer für alle, mehrere Reader in Schleife

### Datei-Vergleich
- ❌ Nur Zeilenanzahl vergleichen (incomplete check)
- ✓ Tatsächlichen Inhalt vergleichen mit equals()

---

## Didaktischer Hinweis

### Lernziele dieses Tests:

1. **Daten strukturiert speichern:**
   - CSV mit Header
   - Komplexe Objekte serialisieren

2. **Daten filtern und transformieren:**
   - Lesen und dabei Bedingungen prüfen
   - Anomalien erkennen

3. **Mehrere Dateien verarbeiten:**
   - Mehrere Reader/Writer gleichzeitig
   - Collection von Dateien iterieren

4. **Datenvalidierung:**
   - Integrität überprüfen
   - Backup-Vergleich

5. **Zentralisierung:**
   - Mehrere Quellen aggregieren
   - Konsistente Formate

### Typische Fehlerquellen:

1. **Resource Management:**
   - try-with-resources vergessen
   - close() nicht aufgerufen

2. **Parsing:**
   - Falsche Indizes bei split()
   - IOException bei Datei-Operationen nicht gehandhabt

3. **Logik:**
   - Header nicht berücksichtigt
   - Off-by-one Fehler bei Bedingungen

4. **Encoding:**
   - UTF-8 vs ISO-8859-1 (Umlaute)
   - Zeilenumbrüche (\\n vs \\r\\n)

### Reale Anwendungen:

- **Prometheus/Grafana:** Performance-Metriken speichern
- **ELK-Stack:** Zentrale Log-Aggregation
- **Backup-Systeme:** Integrität verifizieren
- **System-Monitoring:** Anomalien erkennen
- **Audit-Logging:** Zentrale Protokollierung

