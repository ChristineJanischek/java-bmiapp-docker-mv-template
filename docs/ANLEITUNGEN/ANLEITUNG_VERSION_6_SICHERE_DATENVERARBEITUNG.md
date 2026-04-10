# Version 6: Sichere Datenverarbeitung

## Überblick

Version 6 implementiert Sicherheitsmaßnahmen für die Datenverarbeitung in der BMI-App:

- **Eingabevalidierung** mit Whitelist-Ansatz
- **Sichere Fehlerbehandlung** (aussagekräftige Logs, generische Meldungen)
- **Audit-Logging** für Nachvollziehbarkeit
- **DSGVO-Grundprinzipien**: Minimalprinzip, Datenschutz, Löschbarkeit

Diese Version baut auf Version 5 (JSON-Dateispeicher) auf.

---

## Best Practices: Sichere Datenverarbeitung

### 1. Eingabevalidierung (Whitelist-Ansatz)

**Prinzip:** Nur explizit erlaubte Daten akzeptieren.

```java
// ✗ FALSCH: Blacklist (fehleranfällig)
if (!input.contains("<script>")) { // Was ist mit anderen Angriffsversuchen?
    processInput(input);
}

// ✓ RICHTIG: Whitelist
public boolean isValidName(String name) {
    if (name == null || name.trim().isEmpty()) return false;
    if (name.length() > 50) return false;
    // Nur Buchstaben, Umlaute, Bindestrich, Leerzeichen
    return name.matches("^[a-zA-ZäöüßÄÖÜ\\-\\s]+$");
}
```

### 2. Sichere Fehlerbehandlung

**Intern (Log):** Alle Details für Debugging.  
**Extern (UI):** Generische Meldungen für Sicherheit.

```java
try {
    saveData(userData);
} catch (IOException e) {
    // Intern: Vollständiger Stack Trace
    logger.error("Fehler beim Speichern von Nutzer " + userId, e);
    
    // Extern: Generische Meldung
    showUserMessage("Daten konnten nicht gespeichert werden. Bitte versuchen Sie es später erneut.");
}
```

### 3. Audit-Logging

**Was:** Wer, Wann, Was, Ergebnis.  
**Nicht:** Passwörter, vollständige Namen (gekürzt/anonymisiert).

```java
auditLogger.log("ACTION=MEASUREMENT_ADDED | USER=U001 | TIMESTAMP=2026-04-06T10:30:00Z | MEASUREMENT_ID=M123 | STATUS=SUCCESS");
```

### 4. Minimalprinzip (Data Minimization)

- Speichere nur, was absolut nötig ist.
- Lösche alte Daten nach festgelegtem Zeitraum.
- Biete Nutzer die Möglichkeit, ihre Daten zu löschen.

---

## Schrittweise Implementierung

### Schritt 1: InputValidator-Klasse erstellen

Alle Validierungslogik zentral an **einer** Stelle sammeln.

**Datei:** `src/start/InputValidator.java`

```java
package start;

public class InputValidator {
    
    /**
     * Validiert einen Namen nach Whitelist-Kriterium.
     * Erlaubt: Buchstaben (auch Umlaute), Bindestrich, Leerzeichen.
     * Nicht erlaubt: Sonderzeichen, Zahlen, Länge > 50.
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (name.length() > 50) {
            return false;
        }
        return name.matches("^[a-zA-ZäöüßÄÖÜ\\-\\s]+$");
    }
    
    /**
     * Validiert ein Alter (18-120 Jahre sinnvoll für App).
     */
    public static boolean isValidAge(int age) {
        return age >= 18 && age <= 120;
    }
    
    /**
     * Validiert eine Körpergröße in cm (100-250 cm sinnvoll).
     */
    public static boolean isValidHeight(double height) {
        return height >= 100 && height <= 250;
    }
    
    /**
     * Validiert ein Gewicht in kg (30-300 kg sinnvoll).
     */
    public static boolean isValidWeight(double weight) {
        return weight >= 30 && weight <= 300;
    }
}
```

### Schritt 2: SecureLogger-Klasse für Audit-Logging

Zentrale Stelle für alle Sicherheits-relevanten Logs.

**Datei:** `src/start/SecureLogger.java`

```java
package start;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SecureLogger {
    
    private static final Logger logger = Logger.getLogger(SecureLogger.class.getName());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    
    /**
     * Loggt eine erfolgreiche Aktion (z.B. Daten gespeichert, Messung hinzugefügt).
     * Format: ACTION=... | TIMESTAMP=... | USER_ID=... | DETAILS=... (ohne sensible Daten)
     */
    public static void logAction(String action, String userId, String details) {
        String timestamp = LocalDateTime.now().format(formatter);
        String auditEntry = String.format(
            "ACTION=%s | TIMESTAMP=%s | USER_ID=%s | DETAILS=%s | STATUS=SUCCESS",
            action, timestamp, userId, sanitizeLogMessage(details)
        );
        logger.log(Level.INFO, auditEntry);
    }
    
    /**
     * Loggt einen Fehler mit vollständigem Stack Trace (intern für Admin).
     */
    public static void logError(String action, String userId, Exception e) {
        String timestamp = LocalDateTime.now().format(formatter);
        String auditEntry = String.format(
            "ACTION=%s | TIMESTAMP=%s | USER_ID=%s | STATUS=ERROR | EXCEPTION=%s",
            action, timestamp, userId, e.getMessage()
        );
        logger.log(Level.SEVERE, auditEntry, e);
    }
    
    /**
     * Loggt einen Sicherheitsvorfall (z.B. ungültige Eingabe, Validierung fehlgeschlagen).
     */
    public static void logSecurityEvent(String event, String userId, String reason) {
        String timestamp = LocalDateTime.now().format(formatter);
        String securityEntry = String.format(
            "SECURITY_EVENT=%s | TIMESTAMP=%s | USER_ID=%s | REASON=%s",
            event, timestamp, userId, sanitizeLogMessage(reason)
        );
        logger.log(Level.WARNING, securityEntry);
    }
    
    /**
     * Entfernt sensible Daten aus Log-Nachrichten (z.B. Passwörter, Kreditkartennummern).
     */
    private static String sanitizeLogMessage(String message) {
        if (message == null) return "";
        // Entferne Strings, die wie Passwörter aussehen (vereinfacht)
        message = message.replaceAll("(?i)password[\\s]*=[\\s]*[^\\s|]+", "password=***");
        message = message.replaceAll("(?i)card[\\s]*=[\\s]*[0-9]{4}", "card=****");
        return message;
    }
}
```

### Schritt 3: BmiManager mit Validierung erweitern

Die Eingaben von Nutzer**innen validieren, bevor sie verarbeitet werden.

**Änderungen in BmiManager.java:**

```java
// Am Anfang der Klasse
public void addPerson(String firstName, String lastName, int age) throws IllegalArgumentException {
    // Eingabevalidierung
    if (!InputValidator.isValidName(firstName)) {
        SecureLogger.logSecurityEvent("INVALID_INPUT", "UNKNOWN", "firstName ungültig: " + firstName);
        throw new IllegalArgumentException("Firstname muss 1-50 Buchstaben enthalten (nur Buchstaben, Umlaute, Bindestrich).");
    }
    if (!InputValidator.isValidName(lastName)) {
        SecureLogger.logSecurityEvent("INVALID_INPUT", "UNKNOWN", "lastName ungültig: " + lastName);
        throw new IllegalArgumentException("Lastname muss 1-50 Buchstaben enthalten (nur Buchstaben, Umlaute, Bindestrich).");
    }
    if (!InputValidator.isValidAge(age)) {
        SecureLogger.logSecurityEvent("INVALID_INPUT", "UNKNOWN", "age ungültig: " + age);
        throw new IllegalArgumentException("Alter muss zwischen 18 und 120 Jahren liegen.");
    }
    
    // Wenn Validierung erfolgreich: Aktion durchführen
    Person person = new Person(firstName, lastName, age);
    people.add(person);
    
    SecureLogger.logAction("PERSON_ADDED", "UNKNOWN", 
        "Person hinzugefügt: ID=" + person.getId());
}

// Ähnlich für addMeasurement()
public void addMeasurement(String personId, double height, double weight) throws IllegalArgumentException {
    if (!InputValidator.isValidHeight(height)) {
        SecureLogger.logSecurityEvent("INVALID_INPUT", personId, "height ungültig: " + height);
        throw new IllegalArgumentException("Körpergröße muss zwischen 100 und 250 cm liegen.");
    }
    if (!InputValidator.isValidWeight(weight)) {
        SecureLogger.logSecurityEvent("INVALID_INPUT", personId, "weight ungültig: " + weight);
        throw new IllegalArgumentException("Gewicht muss zwischen 30 und 300 kg liegen.");
    }
    
    // Wenn Validierung erfolgreich: Messung hinzufügen
    // ... (bestehende Logik)
    
    SecureLogger.logAction("MEASUREMENT_ADDED", personId, 
        "Messung hinzugefügt: height=" + height + ", weight=" + weight);
}
```

### Schritt 4: GUI mit Fehlerbehandlung erweitern

Zeige generische Fehlermeldungen für Nutzer**innen, aber logge Fehler zur Diagnose.

**Änderungen in MainWindow.java (Action Listener):**

```java
addPersonButton.addActionListener(e -> {
    try {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());
        
        manager.addPerson(firstName, lastName, age);
        
        // Erfolg
        JOptionPane.showMessageDialog(MainWindow.this, 
            "Person erfolgreich hinzugefügt!", 
            "Erfolg", 
            JOptionPane.INFORMATION_MESSAGE);
        
        clearPersonFields();
        refreshPersonList();
        
    } catch (IllegalArgumentException ex) {
        // Validierungsfehler: Nutzer**in erhält generische Meldung
        JOptionPane.showMessageDialog(MainWindow.this, 
            "Bitte überprüfen Sie Ihre Eingaben: " + ex.getMessage(), 
            "Eingabefehler", 
            JOptionPane.WARNING_MESSAGE);
        
    } catch (NumberFormatException ex) {
        // Typ-Fehler
        JOptionPane.showMessageDialog(MainWindow.this, 
            "Bitte geben Sie für das Alter eine Zahl ein.", 
            "Eingabefehler", 
            JOptionPane.WARNING_MESSAGE);
        
    } catch (Exception ex) {
        // Unerwarteter Fehler
        SecureLogger.logError("ADD_PERSON_UI", "UNKNOWN", ex);
        JOptionPane.showMessageDialog(MainWindow.this, 
            "Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.", 
            "Fehler", 
            JOptionPane.ERROR_MESSAGE);
    }
});
```

### Schritt 5: Datenspeicherung mit Validierung

Bevor Daten in die JSON-Datei geschrieben werden, Validierung durchführen.

**In BmiManager.saveToFile():**

```java
public void saveToFile(String filename) throws IOException {
    try {
        // Validierung: Stelle sicher, dass alle Personen valide Daten haben
        for (Person person : people) {
            if (!InputValidator.isValidName(person.getFirstName()) || 
                !InputValidator.isValidName(person.getLastName())) {
                throw new IllegalStateException("Person mit ungültigen Daten gefunden: " + person);
            }
        }
        
        // Wenn alles valide: Speichern
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(
            new File(filename), 
            people
        );
        
        SecureLogger.logAction("DATA_SAVED", "SYSTEM", "Daten in " + filename + " gespeichert");
        
    } catch (IOException ex) {
        SecureLogger.logError("SAVE_DATA", "SYSTEM", ex);
        throw ex;  // Weiterwerfen, damit GUI-Ebene reagieren kann
    }
}
```

---

## Testing

### Unit Tests für InputValidator

**Datei:** `src/test/InputValidatorTest.java`

```java
package test;

import start.InputValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {
    
    @Test
    public void testValidName() {
        assertTrue(InputValidator.isValidName("Max Müller"));
        assertTrue(InputValidator.isValidName("Anna-Maria"));
        assertFalse(InputValidator.isValidName("Max123"));
        assertFalse(InputValidator.isValidName("Max@Mueller"));
        assertFalse(InputValidator.isValidName(""));
        assertFalse(InputValidator.isValidName(null));
    }
    
    @Test
    public void testValidAge() {
        assertTrue(InputValidator.isValidAge(18));
        assertTrue(InputValidator.isValidAge(50));
        assertTrue(InputValidator.isValidAge(120));
        assertFalse(InputValidator.isValidAge(17));
        assertFalse(InputValidator.isValidAge(121));
    }
    
    @Test
    public void testValidHeight() {
        assertTrue(InputValidator.isValidHeight(170.5));
        assertTrue(InputValidator.isValidHeight(100));
        assertTrue(InputValidator.isValidHeight(250));
        assertFalse(InputValidator.isValidHeight(99.9));
        assertFalse(InputValidator.isValidHeight(250.1));
    }
    
    @Test
    public void testValidWeight() {
        assertTrue(InputValidator.isValidWeight(75.5));
        assertTrue(InputValidator.isValidWeight(30));
        assertTrue(InputValidator.isValidWeight(300));
        assertFalse(InputValidator.isValidWeight(29.9));
        assertFalse(InputValidator.isValidWeight(300.1));
    }
}
```

### 3 Tests mit Musterloesungen

Die drei passenden Kurztest-Aufgaben fuer Version 6 liegen jetzt gesammelt im Materialbereich:

- Testblatt 1: [../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_1.md](../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_1.md)
- Musterloesung 1: [../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_1_LOESUNG.md](../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_1_LOESUNG.md)
- Testblatt 2: [../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_2.md](../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_2.md)
- Musterloesung 2: [../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_2_LOESUNG.md](../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_2_LOESUNG.md)
- Testblatt 3: [../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_3.md](../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_3.md)
- Musterloesung 3: [../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_3_LOESUNG.md](../../materials/tests/sichere_datenverarbeitung/Kurztest_Sichere_Datenverarbeitung_3_LOESUNG.md)

Die Aufgaben decken diese Schwerpunkte ab:

- Whitelist-Validierung
- Maskierung sensibler Logdaten
- Validierung vor dem dauerhaften Speichern
- sichere Fehlerbehandlung und Trennung intern/extern
- Datenschutz, Datenminimierung und Loeschkonzepte

Zusaetzliche Syntaxhilfe fuer Schueler:

- [../../materials/tests/sichere_datenverarbeitung/Syntaxhilfe_Sichere_Datenverarbeitung.md](../../materials/tests/sichere_datenverarbeitung/Syntaxhilfe_Sichere_Datenverarbeitung.md)

---

## Checkliste für Implementierung

- [ ] `InputValidator.java` erstellt und getestet
- [ ] `SecureLogger.java` erstellt
- [ ] `BmiManager.addPerson()` mit Validierung erweitert
- [ ] `BmiManager.addMeasurement()` mit Validierung erweitert
- [ ] `MainWindow` mit Fehlerbehandlung erweitert
- [ ] `BmiManager.saveToFile()` mit Validierung erweitert
- [ ] Unit Tests für `InputValidator` geschrieben und alle Tests grün
- [ ] Manuelle Tests durchgeführt (ungültige Eingaben, Logs prüfen)
- [ ] Commit mit aussagekräftiger Nachricht

---

## DSGVO-Compliance (vereinfacht)

Für eine Schulanwendung genügt eine kurze Dokumentation:

```java
/**
 * DATENSCHUTZ-HINWEIS:
 * Diese Anwendung speichert folgende personenbezogene Daten lokal (auf dem Rechner der Nutzer**in):
 * - Name (Vorname, Nachname)
 * - Alter
 * - Körpergröße und Gewicht (zu Analysezwecken)
 * 
 * RECHTE DER NUTZER**IN:
 * - Daten exportieren: Datei > Export (JSON)
 * - Daten löschen: Pro Person im Menü "Löschen" oder Gesamtdaten > Verzeichnis löschen
 * - Keine Weitergabe an Dritte
 */
```

---

## Zusammenfassung

Version 6 macht die BMI-App **sicherer**:

✓ Eingaben werden vor Verarbeitung validiert (Whitelist)  
✓ Fehlerbehandlung ist robust und aussagekräftig  
✓ Audit-Logs ermöglichen Nachverfolgung von Problemen  
✓ Daten werden minimiert und sind löschbar  

**Next Step:** Implementierung Schritt für Schritt durchgehen.
