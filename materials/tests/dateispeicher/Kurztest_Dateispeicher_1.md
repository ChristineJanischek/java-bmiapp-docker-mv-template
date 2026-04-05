# Kurztest: Persistente Datenspeicherung - IT-Systemverwaltung

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Benutzerverwaltung - CSV-Export (5 Punkte)

**Thema:** Benutzerdaten in CSV-Datei speichern

Als IT-Administrator sollst du Benutzerkonten in eine CSV-Datei exportieren, um sie später wiederherzustellen.

```java
public class BenutzerManager {
    private List<Benutzer> benutzer;
    
    public void exportiereBenutzer(String dateiname) {
        // Schreibe alle Benutzer in CSV-Format (username,email,rolle)
        // Beispiel: "max.mueller,max@firma.de,Admin"
        
        
        
        
        
        
        
        
        
    }
}
```

**Anforderungen:**
- Nutze try-with-resources für automatisches Schließen
- Format: `username,email,rolle` pro Zeile
- Verwende BufferedWriter oder Files.write()

---

## Aufgabe 2: System-Log schreiben (4 Punkte)

**Thema:** Ereignisse in eine Log-Datei anhängen

Schreibe eine Methode, die System-Ereignisse an eine bestehende Log-Datei **anhängt** (nicht überschreibt):

```java
public class SystemLogger {
    private static final String LOG_FILE = "system.log";
    
    public void logeEreignis(String ereignis) {
        // Hänge Ereignis mit Zeitstempel an Log-Datei an
        // Format: "[2026-02-25 14:30:15] Ereignis-Text"
        
        
        
        
        
        
        
        
    }
}
```

**Hinweis:** Nutze FileWriter mit append-Parameter oder Files.write() mit StandardOpenOption.

---

## Aufgabe 3: Konfigurationsdatei lesen (5 Punkte)

**Thema:** Server-Konfiguration aus Datei laden

Lies eine Konfigurationsdatei im Format `key=value` und speichere die Werte in einer Map:

```java
public class KonfigManager {
    private Map<String, String> config;
    
    public void ladeKonfiguration(String dateiname) throws IOException {
        config = new HashMap<>();
        
        // Lies Datei Zeile für Zeile
        // Beispiel-Zeilen:
        // server.host=192.168.1.100
        // server.port=8080
        // database.name=users_db
        
        
        
        
        
        
        
        
        
    }
    
    public String getWert(String key) {
        return config.get(key);
    }
}
```

**Anforderungen:**
- Ignoriere leere Zeilen und Kommentare (beginnen mit #)
- Splitte jede Zeile am `=` Zeichen

---

## Aufgabe 4: Code-Analyse - Backup-System (5 Punkte)

**Thema:** Dateien kopieren für Backup

Gegeben ist folgender Code:

```java
public class BackupManager {
    public void erstelleBackup(String quellDatei, String zielDatei) throws IOException {
        Path quelle = Paths.get(quellDatei);
        Path ziel = Paths.get(zielDatei);
        
        Files.copy(quelle, ziel, StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("Backup erstellt: " + ziel.getFileName());
    }
    
    public void erstelleBackupMitZeitstempel(String datei) throws IOException {
        LocalDateTime jetzt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String zeitstempel = jetzt.format(formatter);
        
        String backupDatei = datei + "." + zeitstempel + ".backup";
        erstelleBackup(datei, backupDatei);
    }
}
```

**Fragen:**

a) Was bewirkt `StandardCopyOption.REPLACE_EXISTING`?

___________________________________________________________________________

___________________________________________________________________________

b) Wie lautet der Backup-Dateiname für `users.csv` am 25.02.2026 um 14:30:15?

___________________________________________________________________________

c) Welcher Vorteil besteht bei Verwendung von `Path` statt `String` für Dateipfade?

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: CSV-Benutzer importieren (4 Punkte)

**Thema:** Benutzer aus CSV-Datei einlesen

Schreibe die passende Import-Methode zur Aufgabe 1:

```java
public class BenutzerManager {
    private List<Benutzer> benutzer = new ArrayList<>();
    
    public void importiereBenutzer(String dateiname) throws IOException {
        // Lies CSV-Datei und erstelle Benutzer-Objekte
        // Format: username,email,rolle
        
        
        
        
        
        
        
        
        
        
    }
}

// Verwende diese Benutzer-Klasse:
class Benutzer {
    private String username;
    private String email;
    private String rolle;
    
    public Benutzer(String username, String email, String rolle) {
        this.username = username;
        this.email = email;
        this.rolle = rolle;
    }
}
```

---

## Aufgabe 6: Fehlersuche - Exception Handling (2 Punkte)

**Thema:** Fehlerhafte Ressourcenverwaltung

Finde die Fehler im folgenden Code:

```java
public void speichereInventar(List<Artikel> artikel, String datei) {
    FileWriter writer = new FileWriter(datei);  // ← FEHLER 1
    
    for (Artikel a : artikel) {
        writer.write(a.getId() + "," + a.getName() + "\n");
    }
    
    writer.close();  // ← FEHLER 2
}
```

a) Welche zwei Hauptfehler enthält dieser Code?

**Fehler 1:** _______________________________________________________________

**Fehler 2:** _______________________________________________________________

b) Schreibe den Code korrekt mit try-with-resources neu:

```java
public void speichereInventar(List<Artikel> artikel, String datei) {
    
    
    
    
    
    
}
```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Benutzerverwaltung - Export | 5 | |
| 2. System-Log schreiben | 4 | |
| 3. Konfigurationsdatei lesen | 5 | |
| 4. Backup-System Analyse | 5 | |
| 5. CSV-Benutzer importieren | 4 | |
| 6. Fehlersuche Exception Handling | 2 | |
| **Gesamt** | **25** | |

---

## Wichtige Konzepte für IT-Systemverwaltung

### File I/O Best Practices:
- ✓ **try-with-resources** für automatisches Schließen
- ✓ **BufferedReader/Writer** für effizientes Lesen/Schreiben
- ✓ **Files & Path API** (java.nio.file) für moderne Dateioperationen
- ✓ **Exception Handling** mit IOException

### Typische Anwendungsfälle:
- 📁 Benutzerdaten exportieren/importieren (CSV)
- 📝 System-Logs schreiben
- ⚙️ Konfigurationsdateien lesen
- 💾 Backup-Funktionen
- 📊 Inventar-Verwaltung
