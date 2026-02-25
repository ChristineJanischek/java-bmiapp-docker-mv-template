# Kurztest: Persistente Datenspeicherung - Server-Monitoring & Performance-Logging

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Performance-Daten speichern (5 Punkte)

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

## Aufgabe 2: Server-Status aus Datei laden und prüfen (4 Punkte)

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

## Aufgabe 3: Performance-Anomalien erkennen und schreiben (5 Punkte)

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

## Aufgabe 4: Code-Analyse - Datei-Vergleich für Backup-Verifizierung (5 Punkte)

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

## Aufgabe 5: Zentrale Log-Datei sammeln (4 Punkte)

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

## Aufgabe 6: Fehlersuche - try-with-resources mit mehreren Resources (2 Punkte)

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
