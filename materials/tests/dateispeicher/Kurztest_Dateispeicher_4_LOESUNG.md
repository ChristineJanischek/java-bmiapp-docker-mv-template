# Kurztest: Persistente Datenspeicherung - Protokoll-Analyse & Security Auditing - LOESUNG

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Access-Log filtern und analysieren (5 Punkte)

### Aufgabenstellung

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

### Musterlösung - Schreiblogik:

```java
// Schreibe verdächtige Zugriffe in separate Datei:
try (PrintWriter writer = new PrintWriter(new FileWriter(ausgabeDatei))) {  // 0.5 Punkt
    writer.println("timestamp|ip|endpoint|status|user_agent|grund");  // Header
    
    for (String zeile : verdaechtigeZugriffe) {  // 0.5 Punkt
        String[] teile = zeile.split("\\|");
        String status = teile.length > 3 ? teile[3] : "unbekannt";
        String grund = "Status: " + status;
        
        if (status.equals("404") || status.equals("403")) {
            grund = "HTTP-Fehler: " + status;
        } else {
            grund = "Verdächtiges Muster erkannt";
        }
        
        writer.println(zeile + "|" + grund);
    }
}
```

### Vollständige Alternative:

```java
try (PrintWriter writer = new PrintWriter(new FileWriter(ausgabeDatei))) {  // 1 Punkt
    writer.println("timestamp|ip|endpoint|status|grund");
    
    for (String zeile : verdaechtigeZugriffe) {  // 1 Punkt
        writer.println(zeile);  // einfach schreiben
    }
} catch (IOException e) {
    System.err.println("Fehler beim Schreiben: " + e.getMessage());
}
```

**Bewertung:**
- PrintWriter mit try-with-resources (1 Punkt)
- Try-catch für IOException (1 Punkt)
- Korrekt in Datei geschrieben (1 Punkt)
- Header oder Strukturierung (1 Punkt)
- Schleife über verdächtige Zugriffe (1 Punkt)

**Beispiel verdächtige Zugriffe Output:**

```
2026-02-25 14:30:15|192.168.1.100|/admin/users|403|Zugriff verweigert
2026-02-25 14:31:20|192.168.45.67|/page.php?id=1' OR '1'='1|404|SQL-Injection Versuch
2026-02-25 14:32:45|203.0.113.45|/cgi-bin/cmd.exe|404|Command-Injection Versuch
```

---

## Aufgabe 2: Fehlgeschlagene Login-Versuche erkennen (4 Punkte)

### Aufgabenstellung

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

### Erklärung der Logik (2 Punkte):

Die Methode funktioniert so:
1. Liest Log-Datei Zeile für Zeile
2. Splittet jede Zeile in `[username, timestamp, erfolg]`
3. Zählt fehlgeschlagene Login-Versuche pro Benutzer
4. Wenn die Anzahl die Schwelle überschreitet, markiert den Benutzer als verdächtig
5. Bei erfolgreichem Login wird der Fehler-Counter zurückgesetzt (erlaubt neue Versuche)

### Security-Implikationen (2 Punkte):

**Erkannte Probleme:**
- **Brute-Force Angriffe:** Viele fehlerhafte Versuche = automatisierter Angriff
- **Schwellwert-Konfiguration:** z.B. 5 Fehlversuche als Schwelle verhindert Glücksspiel-Attacken
- **Counter-Reset:** Nach erfolgreichem Login wird gezählt zurückgesetzt (nicht-böswillige Fehler erlaubt)

**Maßnahmen:**
- Account temporär sperren bei zu vielen Fehlern
- Client-IP blockieren (statt nur Username)
- Warnung/Alert an Benutzer senden
- Längere Wartezeit nach jedem Fehler (exponential backoff)

**Beispiel:**
```
Benutzer "mueller":
  Versuch 1: FAIL
  Versuch 2: FAIL
  Versuch 3: FAIL
  Versuch 4: FAIL
  Versuch 5: FAIL (Schwelle überschritten!)
  → Benutzer markiert als verdächtig
  → Mögliche Aktion: Account sperren oder weitere Logins ablehnen
```

---

## Aufgabe 3: Kryptografische Logs schreiben (5 Punkte)

### Aufgabenstellung

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

### Musterlösung (Code war teilweise vorgegeben, hier komplett):

```java
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    private String letzterHash = "";
    
    public void schreibeAuditLogMitHash(String ereignis, String auditDatei) throws IOException {
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

**Bewertung:**
- Zeitstempel mit Format (1 Punkt)
- Vorheriger Hash in Eintrag inkludiert (1 Punkt)
- SHA-256 MessageDigest (1 Punkt)
- Hex-Konvertierung (1 Punkt)
- Schreiben mit Hash (1 Punkt)

**Wie funktioniert die Blockchain-ähnliche Verkettung?**

```
Eintrag 1: "2026-02-25 14:30:00|Login: mueller|" → Hash: a1b2c3d4...
Eintrag 2: "2026-02-25 14:30:15|File accessed|a1b2c3d4..." → Hash: e5f6g7h8...
Eintrag 3: "2026-02-25 14:30:30|Data modified|e5f6g7h8..." → Hash: i9j0k1l2...

Wenn jemand Eintrag 2 ändert:
- Der Hash von Eintrag 2 ändert sich
- Aber Eintrag 3 hat immer noch den alten Hash
- → Manipulation erkannt!
```

**Security-Vorteil:**
- Verhindert Nachträgliche Manipulation
- Compliance-Anforderung für kritische Systeme
- Authentizitäts-Beweis (wer hat was wann getan)

---

## Aufgabe 4: Code-Analyse - Passwort-Sicherheit (5 Punkte)

### Aufgabenstellung

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

### Musterlösung:

a) **Identifiziere die Sicherheitsprobleme (2 Punkte):**

1. **Passwörter im Klartext speichern** (Line D)
   - Wenn Datei geleakt wird, sind alle Passwörter kompromittiert
   - Violiert OWASP Top 10 (A02:2021 – Cryptographic Failures)

2. **Datei nicht geschützt**
   - Dateiberechtigungen nicht gesetzt
   - Jeder mit Dateizugriff kann Passwörter lesen

3. **Keine Verschlüsselung der Ausgabedatei**
   - Plain-Text Speicherung ist nicht sicher

**Kernpunkte (1 Punkt je Problem, insgesamt 2):**
- Klartext-Speicherung kritisiert
- Fehlende Dateischutzmaßnahmen erkannt

b) **Drei Verbesserungen (1.5 Punkte):**

1. **Passwörter mit bcrypt oder Argon2 hashen** (statt Klartext)
   ```java
   String hashedPassword = BCrypt.hashpw(b.getPassword(), BCrypt.gensalt());
   ```

2. **Keine sensiblen Daten exportieren** (oder nur für Administratoren mit Encryption)
   ```java
   // Exportiere nur username und email, NICHT das Passwort!
   writer.println(b.getUsername() + "," + b.getEmail());
   ```

3. **Dateiberechtigungen setzen (chmod 600)**
   ```java
   Files.setPosixFilePermissions(datei, 
       PosixFilePermissions.fromString("rw-------"));
   ```

**Alternative Maßnahmen:**
- Datei verschlüsseln mit AES
- Datei signieren mit digitalem Zertifikat
- Audit-Log wer auf Datei zugegriffen hat
- Nur bei Admin-Aktion exportieren (mit Bestätigung)

c) **Wie sollte man Passwörter korrekt speichern (1.5 Punkte):**

**Anforderung 1: Hashing mit Salt**
- Nutze bcrypt, scrypt, Argon2 oder PBKDF2
- Jedes Passwort hat eindeutigen Salt
- Verhindert Rainbow-Table Attacks

**Anforderung 2: Niemals im Klartext speichern**
- Auch nicht verschlüsselt!
- Hash ist Einweg-Funktion

**Anforderung 3: Work Factor / Stretching**
- Bcrypt: Computational cost adjustable
- Macht Brute-Force langsamer
- Passwort-Prüfung: `BCrypt.checkpw(plain, hash)`

**Beispiel richtig:**
```java
String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
// Speichere nur: hashedPassword (nicht password!)

// Prüfen:
if (BCrypt.checkpw(eingabePW, hashedPassword)) {
    // Passwort korrekt
}
```

**Bewertung:**
- 3 konkrete Anforderungen (1.5 Punkte)
- Mit Beispielen oder Technologien

---

## Aufgabe 5: Datei-Zugriff mit Berechtigungen (4 Punkte)

### Aufgabenstellung

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

### Musterlösung:

a) **Was bedeutet `"rw-------"` (chmod 600)? (1 Punkt)**

"`rw-------` bedeutet:
- `rw-` (Besitzer/Owner): Lesen + Schreiben erlaubt
- `---` (Gruppe): Keine Berechtigungen
- `---` (Andere): Keine Berechtigungen

In Oktal-Notation: chmod 600
- Nur der Besitzer kann die Datei lesen und ändern
- Alle anderen haben keinen Zugriff"

**Kurz:** Nur Besitzer darf Datei lesen/schreiben.

b) **Warum ist dies wichtig für Audit-Logs? (1 Punkt)**

"Audit-Logs enthalten sensible Informationen:
- Wer hat sich wann angemeldet
- Welche Daten wurden zugegriffen/modifiziert
- Systemkonfigurationen und Fehler

Wenn Logs für alle lesbar sind (chmod 644):
- Ein normaler Benutzer könnte sensible Logs einsehen
- Compliance-Violations (DSGVO, SOC 2, etc.)
- Security durch Dunkelheit ist keine echte Security

Mit chmod 600:
- Nur Administratoren/Besitzer können Logs visuell prüfen
- Unbefugte können nicht einfach Daten stehlen
- Audit-Trail bleibt geschützt"

**Kernpunkte:**
- Vertraulichkeit (nur Befugte können lesen)
- Compliance-Anforderungen
- Verhindert Informations-Leakage

---

## Aufgabe 6: Fehlersuche - Information Leakage (2 Punkte)

### Aufgabenstellung

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


**Thema:** Versehentliche Offenlegung von Daten in Fehlermeldungen

### Musterlösung:

a) **Sicherheitsprobleme (1 Punkt):**

**Problem 1: Benutzer-ID in Fehlermeldung offenlegen**
```
"Fehler: Benutzer mit ID mueller nicht gefunden!"
```
- Ein Angreifer kann herausfinden, welche User-IDs existieren
- Ermöglicht User-Enumeration Attacken
- Ermöglicht gezielte Angriffe auf spezifische Benutzer

**Problem 2: Dateipfad offenlegen**
```
"Datei konnte nicht gelesen werden: /var/secrets/users.csv"
```
- Offenbarte interne Dateisystem-Struktur
- Hilft Angreifern bei Path-Traversal Angriffen
- Könnte System-Sicherheit compromittieren

**Problem 3: NoSuchElementException**
- Unbehandelte Exception führt zu Stack-Trace
- Stack-Trace könnte ClassNames, Methoden, interne Logik offenbaren

b) **Sichere Implementierung (1 Punkt):**

```java
public void ladeBenutzerdaten(String datei, String benutzerid) {
    try {
        List<String> zeilen = Files.readAllLines(Paths.get(datei));
        String benutzer = zeilen.stream()
            .filter(z -> z.startsWith(benutzerid))
            .findFirst()
            .orElse(null);  // Statt .get() → keine Exception
        
        if (benutzer == null) {
            // Generische Fehlermeldung (no Information Leakage):
            System.out.println("Die angeforderten Daten konnten nicht abgerufen werden.");
            // In Logs (nicht für User): Log benutzerid für Audit
            logger.warn("Benutzer nicht gefunden für ID: " + benutzerid);
        }
        
    } catch (IOException e) {
        // Generische Fehlermeldung:
        System.out.println("Ein Fehler ist aufgetreten. Bitte versuchen Sie später erneut.");
        // In Logs: technische details
        logger.error("Fehler beim Lesen der Benutzer-Datei", e);
    }
}
```

**Richtlinien für Fehlerbehandlung:**

❌ **Niemals offenlegen:**
- Dateipfade
- Benutzer-Existenz
- SQL-Queries
- Stack-Traces (an Benutzer)
- Versioning-Info (Apache 2.x etc.)

✓ **Stattdessen zeigen:**
- Generische Meldungen: "Anfrage konnte nicht verarbeitet werden"
- In Logs: Vollständige Fehlerinformationen für Admin
- Logging mit Severity (ERROR, WARN, INFO)

**Bewertung:**
- Information Leakage erkannt (0.5 Punkte × 2)
- Sichere Alternative mit generischen Meldungen (0.5 Punkte)
- Separation User-Meldung vs. Logs (0.5 Punkte)

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

### Security Mistakes
- ❌ **Passwörter im Klartext** → Fatal!
- ❌ **Fehler-Details an User** → Information Leakage
- ❌ **Keine Dateiberechtigungen** → Jedem zugänglich
- ❌ **Kein Audit-Logging** → Keine Nachverfolgung

### Logging
- ❌ Sensitive Daten in Logs schreiben
- ✓ Nur notwendige Info in Logs
- ✓ Logs selbst schützen (chmod 600)

### Exception Handling
- ❌ Stack-Traces an Benutzer zeigen
- ✓ Generische Meldung für User, Details in Logs
- ✓ Nur technische Fehler in Admin-Logs

---

## Didaktischer Hinweis

### Sicherheits-Checkliste für Datei-I/O:

✓ **Authentizität:** Nur autorisierte Benutzer dürfen lesen
✓ **Vertraulichkeit:** Verschlüsseln sensible Daten
✓ **Integrität:** Checksummen/Hashes prüfen
✓ **Verfügbarkeit:** Backups und Redundanz
✓ **Accountability:** Audit-Logs wer/was/wann

### Relevante Security-Standards:

- **OWASP Top 10:**
  - A02:2021 – Cryptographic Failures
  - A04:2021 – Insecure Design
  - A07:2021 – Identification and Authentication Failures

- **DSGVO (EU):**
  - Datenschutz by Design
  - Minimal-Prinzip (nur notwendige Daten speichern)
  - Encryption / Pseudonymisierung

- **PCI-DSS (für Payment):**
  - Keine Cleartext Passwörter
  - Starke Kryptographie
  - Audit-Logs mindestens 1 Jahr

### Reale Breach-Szenarien:

❌ **LinkedIn 2012:** 6,5 Mio. Passwörter geleakt (mit Hash, aber crackbar)
❌ **Adobe 2013:** 150 Mio. User, Passwörter im Klartext!
❌ **Facebook:** Milliarden Phone-Nummern in Logs ungeschützt
❌ **Twitter:** API-Logs mit User-Tokens geleast

✓ **Best-Practice:** Bcrypt/Argon2, Encryption, Access Control, Audit Logs

---

## Zusammenfassung: Security bei File I/O

Wenn Sie mit sensiblen Daten in Dateien arbeiten:

1. **Nie Passwörter speichern** → nur Hashes
2. **Dateibiberrechtigungen** → chmod 600 für sensible Dateien
3. **Verschlüsseln** → für Datenbank-Backups, Zertifikate, Keys
4. **Audit-Logging** → wer hat was wann gelesen/geändert
5. **Fehlerbehandlung** → keine technischen Infos für Benutzer
6. **Integrität prüfen** → Hashes/Signaturen verwenden
7. **Backups** → redundancy gegen Denverlust/Ransomware
