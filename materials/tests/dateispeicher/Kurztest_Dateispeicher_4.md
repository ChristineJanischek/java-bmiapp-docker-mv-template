# Kurztest: Persistente Datenspeicherung - Protokoll-Analyse & Security Auditing

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Access-Log filtern und analysieren (5 Punkte)

**Thema:** Web-Server Zugriffe überprüfen und verdächtige Aktivität erkennen

Ein Web-Server erzeugt Access-Logs im Format: `timestamp|ip|endpoint|status_code|user_agent`

```java
public class AccessLogAnalyzer {
    
    public void findeUnerlaubteZugriffe(String logDatei, String ausgabeDatei) throws IOException {
        // Lese Log-Datei und filtere:
        // - HTTP Status 404 (nicht gefunden) 
        // - oder 403 (Zugriff verweigert)
        // - oder verdächtige Patterns wie SQL-Injection-Versuche
        
        List<String> verdaechtigeZugriffe = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(logDatei))) {  // 1 Punkt
            String zeile;
            
            while ((zeile = reader.readLine()) != null) {  // 1 Punkt
                String[] teile = zeile.split("\\|");  // 1 Punkt (escape regex)
                
                if (teile.length >= 4) {
                    String status = teile[3];
                    String endpoint = teile[2];
                    
                    // Prüfe auf verdächtige Muster:  // 1 Punkt
                    if (status.equals("404") || status.equals("403") || 
                        endpoint.contains("'") || endpoint.contains("--") ||  // SQL-Injection Pattern
                        endpoint.contains("exec") || endpoint.contains("cmd")) {  // Command-Injection
                        
                        verdaechtigeZugriffe.add(zeile);
                    }
                }
            }
        }
        
        // Schreibe verdächtige Zugriffe in separate Datei:
        
        
        
        
    }
}
```

Vervollständige die Schreiblogik für verdächtige Zugriffe:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: Fehlgeschlagene Login-Versuche erkennen (4 Punkte)

**Thema:** Brute-Force Angriffe bzw. verdächtige Login-Muster erkennen

```java
public class LoginAuditor {
    private Map<String, Integer> loginVersuche = new HashMap<>();  // username -> Anzahl Fehlversuche
    private List<String> verdaechtigeBenutzer = new ArrayList<>();
    
    public void analysiereLoginLog(String logDatei, int verdaechtigeSchwelle) throws IOException {
        // Log-Format: timestamp,username,login_erfolgreich (true/false)
        // mueller,2026-02-25 14:30:15,false
        // mueller,2026-02-25 14:30:30,false
        // mueller,2026-02-25 14:30:45,true
        
        try (BufferedReader reader = new BufferedReader(new FileReader(logDatei))) {  // 1 Punkt
            String zeile;
            
            while ((zeile = reader.readLine()) != null) {  // 1 Punkt
                String[] teile = zeile.split(",");
                
                if (teile.length >= 3) {
                    String username = teile[0];  // 1 Punkt
                    boolean erfolg = Boolean.parseBoolean(teile[2]);
                    
                    if (!erfolg) {
                        // Zähle fehlgeschlagene Versuche:
                        int count = loginVersuche.getOrDefault(username, 0);
                        loginVersuche.put(username, count + 1);  // 1 Punkt
                        
                        // Wenn zu viele Fehlversuche, als verdächtig markieren:
                        if (count + 1 >= verdaechtigeSchwelle) {
                            if (!verdaechtigeBenutzer.contains(username)) {
                                verdaechtigeBenutzer.add(username);
                            }
                        }
                    } else {
                        // Erfolgreicher Login: Reset Fehler-Counter
                        loginVersuche.put(username, 0);
                    }
                }
            }
        }
    }
    
    public List<String> getVerdaechtigeBenutzer() {
        return verdaechtigeBenutzer;
    }
}
```

Der Code ist bereits vorgegeben. **Aufgabe:** Erklären Sie die Logik und die Sicherheits-Implikationen.

---

## Aufgabe 3: Kryptografische Logs schreiben (5 Punkte)

**Thema:** Audit-Logs mit Hashwert zur Manipulationserkennung

Schreibe eine Methode, die Audit-Logs mit Hash-Verkettung speichert (ähnlich Blockchain):

```java
import java.security.MessageDigest;

public class AuditLogger {
    private String letzterHash = "";  // Hash des vorherigen Eintrags
    
    public void schreib AuditLogMitHash(String ereignis, String auditDatei) throws IOException {
        try {
            // Erstelle neuen Log-Eintrag mit Zeitstempel:
            String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));  // 1 Punkt
            
            // Kombiniere vorherigen Hash mit neuem Ereignis für Verkettung:
            String logEintrag = timestamp + "|" + ereignis + "|" + letzterHash;  // 1 Punkt
            
            // Berechne Hash dieses Eintrags:
            MessageDigest digest = MessageDigest.getInstance("SHA-256");  // 1 Punkt
            byte[] hashBytes = digest.digest(logEintrag.getBytes());
            
            // Konvertiere zu Hex:
            StringBuilder hexHash = new StringBuilder();  // 1 Punkt
            for (byte b : hashBytes) {
                hexHash.append(String.format("%02x", b));
            }
            letzterHash = hexHash.toString();
            
            // Schreibe Log-Eintrag mit Hash:
            try (PrintWriter writer = new PrintWriter(new FileWriter(auditDatei, true))) {  
                writer.println(logEintrag + "|" + letzterHash);  // 1 Punkt
            }
            
        } catch (Exception e) {
            throw new IOException("Audit-Log Fehler: " + e.getMessage());
        }
    }
}
```

---

## Aufgabe 4: Code-Analyse - Passwort-Sicherheit (5 Punkte)

**Thema:** Sichere Speicherung von Benutzer-Daten

Gegeben ist dieser Code:

```java
public class UserSecurityManager {
    
    public void exportiereBenutzermitPasswoerter(List<Benutzer> benutzer, String datei) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(datei))) {  // Line A
            writer.println("username,email,password");  // Line B
            
            for (Benutzer b : benutzer) {  // Line C
                String zeile = b.getUsername() + "," + 
                               b.getEmail() + "," + 
                               b.getPassword();  // Line D - SICHERHEITSPROBLEM!!!
                writer.println(zeile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**Fragen:**

a) Identifiziere die Sicherheitsprobleme in diesem Code:

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

b) Welche drei Maßnahmen würden die Sicherheit verbessern?

1. ________________________________________________________________

2. ________________________________________________________________

3. ________________________________________________________________

c) Wie sollte man Passwörter korrekt speichern? (3 Anforderungen)

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Datei-Zugriff mit Berechtigungen (4 Punkte)

**Thema:** Dateiberechtigungen für sensible Logs setzen

```java
public class SecureFileManager {
    
    public void erstelleSichereDatei(String dateiname, String inhalt) throws IOException {
        // Erstelle Datei und setze restriktive Berechtigungen
        Path datei = Paths.get(dateiname);
        
        // Schreibe Inhalt:
        Files.write(datei, inhalt.getBytes(StandardCharsets.UTF_8));  // Datei erstellt
        
        // Setze Berechtigungen: nur Besitzer kann lesen/schreiben (chmod 600):
        Set<PosixFilePermission> berechtigungen = PosixFilePermissions.fromString("rw-------");  // 2 Punkte
        
        Files.setPosixFilePermissions(datei, berechtigungen);  // 2 Punkte
    }
}
```

**Fragen:**

a) Was bedeutet `"rw-------"` (chmod 600)?

___________________________________________________________________________

b) Warum ist dies besonders wichtig für Audit-Logs und Benutzerdaten?

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 6: Fehlersuche - Information Leakage (2 Punkte)

**Thema:** Versehentliche Offenlegung von Daten in Fehlermeldungen

Gegeben ist fehlerhafte Code:

```java
public void ladeBenutzerdaten(String datei, String benutzerid) {
    try {
        List<String> zeilen = Files.readAllLines(Paths.get(datei));
        String benutzer = zeilen.stream()
            .filter(z -> z.startsWith(benutzerid))
            .findFirst()
            .get();  // ← FEHLER!
    } catch (NoSuchElementException e) {
        System.out.println("Fehler: Benutzer mit ID " + benutzerid + " nicht gefunden!");  // ← FEHLER!
    } catch (IOException e) {
        System.out.println("Datei konnte nicht gelesen werden: " + datei);  // ← FEHLER!
    }
}
```

a) Was sind die Sicherheitsprobleme in den Fehlermeldungen?

___________________________________________________________________________

___________________________________________________________________________

b) Schreibe den Code sicherer neu:

```java
public void ladeBenutzerdaten(String datei, String benutzerid) {
    try {
        // Sichere Implementierung:
        
        
        
        
    } catch (Exception e) {
        
        
    }
}
```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Access-Log filtern | 5 | |
| 2. Login-Versuche erkennen | 4 | |
| 3. Kryptografische Logs | 5 | |
| 4. Passwort-Sicherheit Analyse | 5 | |
| 5. Dateiberechtigungen | 4 | |
| 6. Fehlersuche Information Leakage | 2 | |
| **Gesamt** | **25** | |

---

## Praxiskontext: Security & Compliance

### 🔍 Threat Detection (Aufgabe 1 & 2)
- Verdächtige Aktivitäten erkennen
- Brute-Force Angriffe aufdecken
- SQL-Injection Versuche blockieren

### 📋 Audit-Trails (Aufgabe 3)
- Wer hat was getan? (Accountability)
- Manipulations-Erkennung (Hash-Verkettung)
- Compliance-Anforderungen (DSGVO, Logging)

### 🔒 Passwort-Sicherheit (Aufgabe 4)
- Niemals im Klartext speichern!
- Hashing mit Salt
- PBKDF2, bcrypt, Argon2

### 🛡️ Datenschutz (Aufgabe 5 & 6)
- Dateiberechtigungen (chmod)
- Minimale Fehlerinformation
- Keine sensiblen Daten in Logs

**Real-World Standards:**
- OWASP Top 10 (Security Best Practices)
- DSGVO (Datenschutz-Grundverordnung)
- PCI-DSS (Payment Card Industry)
- ISO 27001 (Information Security Management)

**Tools:**
- SIEM-Systeme (Splunk, ELK-Stack)
- WAF (Web Application Firewall)
- Log Aggregation (Logstash, Fluentd)
- Password Hashing: bcrypt, Argon2
