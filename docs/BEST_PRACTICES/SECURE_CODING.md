# Secure Coding – Grundlagen und Implementierung

Dieses Dokument fasst die wichtigsten Prinzipien und Praktiken des Secure Coding zusammen und zeigt, wie sie in Java-Anwendungen umgesetzt werden. Ziel ist es, Sicherheitslücken von Anfang an zu vermeiden und robuste, wartbare Software zu entwickeln.

## 1. Was ist Secure Coding?

Secure Coding bezeichnet die Praxis, Software so zu entwickeln, dass Sicherheitslücken und Schwachstellen systematisch vermieden werden. Es umfasst:
- Bewusstsein für häufige Sicherheitsrisiken (OWASP Top 10)
- Anwendung bewährter Designprinzipien
- Strikte Input-Validierung und Output-Encoding
- Sichere Fehlerbehandlung und Logging
- Regelmäßige Code-Reviews und Tests

## 2. Zentrale Prinzipien

### Hinweis zum Single Entry Point-Prinzip
Ein klar definierter Einstiegspunkt (siehe [SINGLE_ENTRY_POINT.md](../KONZEPTE/SINGLE_ENTRY_POINT.md)) ist ein wichtiger Baustein für sichere und wartbare Software. Er ermöglicht zentrale Initialisierung, Security-Checks und Logging und verhindert, dass das System an unerwarteten Stellen betreten wird.

### 2.1 Principle of Least Privilege (Prinzip der geringsten Berechtigung)
- Gewähre nur die minimal notwendigen Zugriffsrechte.
- Verwende `private` für Felder, `protected` oder package-private nur bei echtem Bedarf.
- Vermeide unnötige `public`-APIs.

**Beispiel:**
```java
public class BankAccount {
    private double balance;  // nicht public!
    
    public double getBalance() {
        return balance;  // kontrollierter Lesezugriff
    }
    
    // kein direkter Setter; nur über Geschäftslogik
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Betrag muss positiv sein");
        balance += amount;
    }
}
```

### 2.2 Defense in Depth (Verteidigung in der Tiefe)
- Setze mehrere Sicherheitsschichten ein (Validierung, Kapselung, Authentifizierung, Logging).
- Ein einzelner Fehler sollte nicht zum Totalausfall führen.

### 2.3 Fail-Safe Defaults
- Systeme sollten im Fehlerfall in einen sicheren Zustand übergehen.
- Default-Einstellungen restriktiv wählen (z. B. Zugriff verweigern statt erlauben).

### 2.4 Separation of Concerns
- Trenne Zuständigkeiten klar (z. B. MVC: Model, View, Controller).
- Verhindert unübersichtliche, schwer wartbare "God Classes".

## 3. Input-Validierung (Eingabeprüfung)

**Regel:** Traue keiner Eingabe – validiere alles!

### 3.1 Warum validieren?
- Verhindert Injection-Angriffe (SQL, Command, Script Injection)
- Vermeidet unerwartete Programmzustände und Abstürze
- Schützt vor Denial-of-Service (z. B. zu große Eingaben)

### 3.2 Best Practices
- **Whitelist statt Blacklist:** Erlaube nur bekannte, sichere Werte.
- **Typ- und Bereichsprüfung:** Prüfe Datentyp, Länge, Wertebereich.
- **Frühzeitig validieren:** Direkt bei Eingabe (Konstruktor, Setter, API-Einstiegspunkt).
- **Klare Fehlermeldungen:** Informiere den Nutzer, aber verrate keine Interna.

**Beispiel:**
```java
public class User {
    private String username;
    private int age;
    
    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Benutzername darf nicht leer sein");
        }
        if (username.length() > 20 || !username.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Benutzername ungültig (max. 20 Zeichen, nur a-z, A-Z, 0-9, _)");
        }
        this.username = username.trim();
    }
    
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Alter muss zwischen 0 und 150 liegen");
        }
        this.age = age;
    }
}
```

### 3.3 Häufige Fallen
- **SQL Injection:** Nutze Prepared Statements, niemals String-Konkatenation.
- **Path Traversal:** Prüfe Dateipfade, verbiete `..` oder absolute Pfade.
- **Command Injection:** Vermeide `Runtime.exec()` mit Benutzereingaben.

## 4. Output-Encoding (Ausgabebereinigung)

- Schützt vor Cross-Site Scripting (XSS) und anderen Injection-Angriffen.
- Codiere Ausgaben kontextabhängig (HTML, URL, SQL, JSON).

**Beispiel (GUI):**
```java
// Statt rohe Benutzereingabe direkt anzuzeigen:
String userInput = textField.getText();
// In Swing meist unkritisch, aber in Webanwendungen HTML-Escape nötig
label.setText("Eingabe: " + escapeHtml(userInput));
```

## 5. Fehlerbehandlung (Error Handling)

### 5.1 Prinzipien
- **Fail-Safe:** Im Fehlerfall sicher abbrechen, nicht weiterlaufen.
- **Keine sensiblen Details preisgeben:** Stack-Traces nicht an Endnutzer ausgeben.
- **Zentrale Exception-Handler:** Logging und Nutzerbenachrichtigung zentral steuern.

**Beispiel:**
```java
public double berechne(double gewicht, double groesse) {
    if (gewicht <= 0 || groesse <= 0) {
        throw new IllegalArgumentException("Gewicht und Größe müssen positiv sein");
    }
    return gewicht / (groesse * groesse);
}

// Im Controller:
try {
    double bmi = model.berechne(gewicht, groesse);
    view.zeigeErgebnis(bmi);
} catch (IllegalArgumentException e) {
    view.zeigeFehler("Ungültige Eingabe: " + e.getMessage());
    // Logging (intern):
    logger.warning("Ungültige Eingabe: " + e.getMessage());
}
```

### 5.2 Häufige Fehler
- Leere `catch`-Blöcke (Fehler werden verschluckt).
- Generische Exceptions (`catch (Exception e)`) ohne Differenzierung.
- Stack-Traces in Produktionsumgebungen sichtbar.

## 6. Logging und Monitoring

- **Was loggen?** Sicherheitsrelevante Events (Login, Fehler, Zugriffe).
- **Was nicht loggen?** Passwörter, Kreditkartennummern, andere sensible Daten.
- **Level verwenden:** DEBUG, INFO, WARNING, ERROR, CRITICAL.

**Beispiel:**
```java
import java.util.logging.Logger;

public class BmiManager {
    private static final Logger logger = Logger.getLogger(BmiManager.class.getName());
    
    public void berechneBMI(double gewicht, double groesse) {
        logger.info("BMI-Berechnung gestartet");
        try {
            // ... Berechnung
            logger.fine("BMI erfolgreich berechnet");
        } catch (IllegalArgumentException e) {
            logger.warning("Ungültige Eingabe: " + e.getMessage());
            throw e;
        }
    }
}
```

## 7. Sichere Speicherung von Daten

### 7.1 Passwörter
- **Niemals im Klartext speichern!**
- Verwende Hash-Funktionen (bcrypt, Argon2, PBKDF2).
- Nutze Salt (zufälliger Wert) pro Passwort.

**Beispiel (Konzept):**
```java
// Pseudo-Code (in Produktion Bibliotheken wie BCrypt nutzen)
String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
// Verifikation:
if (BCrypt.checkpw(inputPassword, hashedPassword)) {
    // Login erfolgreich
}
```

### 7.2 Datenbank-Zugriffe
- **Prepared Statements verwenden:**
```java
String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, username);
stmt.setString(2, passwordHash);
ResultSet rs = stmt.executeQuery();
```

## 8. Secure Coding im MVC-Kontext

| Schicht | Sicherheitsaufgaben |
|---------|---------------------|
| **Model** | Input-Validierung, Invarianten prüfen, Daten kapseln |
| **View** | Keine Geschäftslogik, Output-Encoding, keine sensiblen Daten anzeigen |
| **Controller** | Zentrale Validierung, Exception-Handling, Logging, Zugriffskontrolle |

**Beispiel (BMI-Rechner):**
- **Model (Bmirechner):** Validiert Gewicht/Größe, kapselt Attribute (`private`).
- **Controller (BmiManager):** Koordiniert, fängt Exceptions, loggt Events.
- **View (MainWindow):** Zeigt Ergebnisse, gibt keine internen Details preis.

## 9. Checkliste für Secure Coding

- [ ] Alle Eingaben validiert (Typ, Bereich, Format)?
- [ ] Kapselung konsequent umgesetzt (`private` Felder, kontrollierte APIs)?
- [ ] Exceptions sinnvoll behandelt (nicht verschluckt, keine Stack-Traces an User)?
- [ ] Logging für sicherheitsrelevante Events aktiviert?
- [ ] Sensible Daten nicht im Klartext gespeichert oder geloggt?
- [ ] Prepared Statements bei DB-Zugriffen genutzt?
- [ ] Output-Encoding bei Ausgaben angewendet?
- [ ] Code-Reviews durchgeführt?
- [ ] Tests (Unit, Integration, Security) vorhanden?

## 10. Häufige Sicherheitslücken (OWASP Top 10 – Auszug)

1. **Injection (SQL, Command, Script):** Durch fehlende Input-Validierung.
2. **Broken Authentication:** Schwache Passwörter, fehlende MFA, Session-Management.
3. **Sensitive Data Exposure:** Unverschlüsselte Übertragung/Speicherung.
4. **Broken Access Control:** Fehlende Berechtigungsprüfungen.
5. **Security Misconfiguration:** Default-Passwörter, verbose Error-Messages.
6. **Cross-Site Scripting (XSS):** Unbereinigtes Output in Webanwendungen.
7. **Insecure Deserialization:** Unsichere Objektdeserialisierung.
8. **Using Components with Known Vulnerabilities:** Veraltete Bibliotheken.
9. **Insufficient Logging & Monitoring:** Angriffe bleiben unentdeckt.
10. **Server-Side Request Forgery (SSRF):** Ungeprüfte URL-Aufrufe.

## 11. Weitere Ressourcen

- **OWASP (Open Web Application Security Project):** [owasp.org](https://owasp.org)
- **CWE (Common Weakness Enumeration):** [cwe.mitre.org](https://cwe.mitre.org)
- **Java Secure Coding Guidelines:** [Oracle Secure Coding Guidelines](https://www.oracle.com/java/technologies/javase/seccodeguide.html)
- **CERT Oracle Secure Coding Standard for Java:** [sei.cmu.edu](https://wiki.sei.cmu.edu/confluence/display/java)

## 12. Zusammenfassung

Secure Coding ist kein Add-on, sondern integraler Bestandteil der Softwareentwicklung. Durch konsequente Input-Validierung, Kapselung, sichere Fehlerbehandlung und Logging schützt du deine Anwendung vor den häufigsten Angriffen. Im MVC-Kontext sorgt die klare Trennung der Verantwortlichkeiten zusätzlich für Übersichtlichkeit und Wartbarkeit.

**Faustregel:** Denke immer aus Angreifersicht – was könnte missbraucht werden?
