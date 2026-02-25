# Kurztest: Persistente Datenspeicherung - Protokoll-Analyse & Security Auditing - LOESUNG

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Access-Log filtern und analysieren (5 Punkte)

**Thema:** Web-Server Zugriffe überprüfen und verdächtige Aktivität erkennen

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

**Thema:** Brute-Force Angriffe bzw. verdächtige Login-Muster erkennen

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

**Thema:** Audit-Logs mit Hashwert zur Manipulationserkennung

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

**Thema:** Sichere Speicherung von Benutzer-Daten

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

**Thema:** Dateiberechtigungen für sensible Logs setzen

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
