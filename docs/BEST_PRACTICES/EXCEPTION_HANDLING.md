# Exception Handling - Ausnahmebehandlung in Java

## 📚 Inhaltsverzeichnis

1. [Was sind Exceptions?](#was-sind-exceptions)
2. [Exception-Hierarchie](#exception-hierarchie)
3. [Try-Catch-Finally](#try-catch-finally)
4. [Checked vs. Unchecked Exceptions](#checked-vs-unchecked-exceptions)
5. [Eigene Exceptions erstellen](#eigene-exceptions-erstellen)
6. [Best Practices](#best-practices)
7. [Praxisbeispiele für BMI-Rechner](#praxisbeispiele-für-bmi-rechner)

---

## Was sind Exceptions?

**Exceptions** (Ausnahmen) sind Ereignisse, die während der Programmausführung auftreten und den normalen Programmablauf stören. Sie ermöglichen es, Fehler kontrolliert zu behandeln, anstatt das Programm abstürzen zu lassen.

### Warum Exception Handling?

✅ **Programm bleibt stabil** - keine plötzlichen Abstürze  
✅ **Benutzerfreundlich** - aussagekräftige Fehlermeldungen  
✅ **Wartbarkeit** - klare Trennung von Fehlerbehandlung und Programmlogik  
✅ **Debugging** - Stack Traces zeigen genau, wo der Fehler auftrat  

### Beispiel ohne Exception Handling

```java
public class BmiRechnerOhneException {
    public static void main(String[] args) {
        double gewicht = 70.0;
        double groesse = 0.0;  // Fehlerhafte Eingabe!
        
        double bmi = gewicht / (groesse * groesse);  // Division durch 0!
        System.out.println("BMI: " + bmi);  // Infinity oder NaN
    }
}
```

**Problem:** Ungültige Berechnung wird nicht abgefangen → Fehlerhafte Ergebnisse!

---

## Exception-Hierarchie

```
                    Throwable
                    /        \
              Exception      Error
              /      \          \
   RuntimeException  IOException  OutOfMemoryError
         |                |
  NullPointerException  FileNotFoundException
  ArithmeticException
  IllegalArgumentException
```

### Wichtige Exception-Typen

| Typ | Beschreibung | Beispiel |
|-----|--------------|----------|
| **ArithmeticException** | Mathematische Fehler | Division durch 0 |
| **NullPointerException** | Zugriff auf `null`-Objekt | `obj.method()` wenn `obj == null` |
| **IllegalArgumentException** | Ungültige Methodenparameter | Negative Größe beim BMI |
| **NumberFormatException** | Ungültiger String→Zahl | `Integer.parseInt("abc")` |
| **IOException** | Ein-/Ausgabefehler | Datei nicht gefunden |

---

## Try-Catch-Finally

### Grundstruktur

```java
try {
    // Code, der eine Exception werfen könnte
    riskante_operation();
} catch (ExceptionTyp e) {
    // Fehlerbehandlung
    System.err.println("Fehler: " + e.getMessage());
} finally {
    // Wird IMMER ausgeführt (optional)
    aufräumen();
}
```

### Beispiel: Division durch Null abfangen

```java
public double berechneBmi(double gewicht, double groesse) {
    try {
        if (groesse <= 0) {
            throw new IllegalArgumentException("Größe muss positiv sein!");
        }
        return gewicht / (groesse * groesse);
    } catch (IllegalArgumentException e) {
        System.err.println("Fehler: " + e.getMessage());
        return 0.0;  // Standardwert zurückgeben
    }
}
```

### Mehrere Catch-Blöcke

```java
try {
    String input = getUserInput();
    double wert = Double.parseDouble(input);
    double ergebnis = berechne(wert);
} catch (NumberFormatException e) {
    System.err.println("Ungültige Zahl eingegeben!");
} catch (IllegalArgumentException e) {
    System.err.println("Wert außerhalb des gültigen Bereichs!");
} catch (Exception e) {
    System.err.println("Ein unerwarteter Fehler ist aufgetreten!");
}
```

### Multi-Catch (ab Java 7)

```java
try {
    // ... Code ...
} catch (NumberFormatException | IllegalArgumentException e) {
    System.err.println("Eingabefehler: " + e.getMessage());
}
```

### Try-With-Resources (ab Java 7)

Für Ressourcen, die geschlossen werden müssen (Dateien, Datenbankverbindungen):

```java
try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
    String line = reader.readLine();
    // ... Verarbeitung ...
} catch (IOException e) {
    System.err.println("Dateifehler: " + e.getMessage());
}
// reader wird automatisch geschlossen, auch bei Exception!
```

---

## Checked vs. Unchecked Exceptions

### Checked Exceptions

- **Müssen** behandelt oder deklariert werden (`throws`)
- Compiler erzwingt Behandlung
- Beispiele: `IOException`, `SQLException`, `FileNotFoundException`

```java
public void leseDatei(String pfad) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(pfad));
    // ... Verarbeitung ...
}

// Aufrufer MUSS behandeln:
try {
    leseDatei("daten.txt");
} catch (IOException e) {
    e.printStackTrace();
}
```

### Unchecked Exceptions (Runtime Exceptions)

- **Können** behandelt werden, müssen aber nicht
- Erben von `RuntimeException`
- Beispiele: `NullPointerException`, `IllegalArgumentException`, `ArithmeticException`

```java
public void setGroesse(double groesse) {
    if (groesse <= 0) {
        throw new IllegalArgumentException("Größe muss positiv sein!");
    }
    this.groesse = groesse;
}

// Aufrufer kann, muss aber nicht behandeln:
setGroesse(-1.75);  // Wirft IllegalArgumentException
```

---

## Eigene Exceptions erstellen

### Einfache Custom Exception

```java
public class InvalidBmiInputException extends Exception {
    public InvalidBmiInputException(String message) {
        super(message);
    }
    
    public InvalidBmiInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Verwendung

```java
public double berechne(double gewicht, double groesse) 
        throws InvalidBmiInputException {
    if (gewicht <= 0) {
        throw new InvalidBmiInputException(
            "Gewicht muss positiv sein! Eingabe: " + gewicht
        );
    }
    if (groesse <= 0) {
        throw new InvalidBmiInputException(
            "Größe muss positiv sein! Eingabe: " + groesse
        );
    }
    return gewicht / (groesse * groesse);
}
```

### Aufrufer behandelt Exception

```java
try {
    double bmi = bmirechner.berechne(-70.0, 1.75);
} catch (InvalidBmiInputException e) {
    JOptionPane.showMessageDialog(
        null, 
        e.getMessage(), 
        "Eingabefehler", 
        JOptionPane.ERROR_MESSAGE
    );
}
```

---

## Best Practices

### ✅ DO - Gute Praktiken

**1. Spezifische Exceptions verwenden**
```java
// ✅ GUT
throw new IllegalArgumentException("Größe darf nicht negativ sein");

// ❌ SCHLECHT
throw new Exception("Fehler");
```

**2. Aussagekräftige Fehlermeldungen**
```java
// ✅ GUT
throw new IllegalArgumentException(
    "BMI-Berechnung fehlgeschlagen: Größe muss zwischen 0.5m und 3.0m liegen. " +
    "Eingabe: " + groesse + "m"
);

// ❌ SCHLECHT
throw new IllegalArgumentException("Ungültig");
```

**3. Exceptions nicht verschlucken**
```java
// ✅ GUT
try {
    riskanteOperation();
} catch (Exception e) {
    logger.error("Fehler bei Operation: " + e.getMessage(), e);
    throw e;  // Oder alternative Behandlung
}

// ❌ SCHLECHT
try {
    riskanteOperation();
} catch (Exception e) {
    // Leer - Fehler wird ignoriert!
}
```

**4. Finally für Aufräumarbeiten**
```java
Connection conn = null;
try {
    conn = getConnection();
    // ... Datenbankoperationen ...
} catch (SQLException e) {
    e.printStackTrace();
} finally {
    if (conn != null) {
        try {
            conn.close();  // Immer schließen!
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

**5. Früh validieren (Fail-Fast)**
```java
public void setBmiWerte(double gewicht, double groesse) {
    // Validierung am Anfang
    if (gewicht <= 0 || gewicht > 500) {
        throw new IllegalArgumentException("Gewicht ungültig: " + gewicht);
    }
    if (groesse <= 0 || groesse > 3.0) {
        throw new IllegalArgumentException("Größe ungültig: " + groesse);
    }
    
    // Normale Verarbeitung
    this.gewicht = gewicht;
    this.groesse = groesse;
}
```

### ❌ DON'T - Schlechte Praktiken

**1. Generische Exception werfen**
```java
// ❌ SCHLECHT
public void methode() throws Exception {
    // ...
}

// ✅ BESSER
public void methode() throws IOException, InvalidBmiInputException {
    // ...
}
```

**2. Exception für Programmflusssteuerung**
```java
// ❌ SCHLECHT
try {
    int index = 0;
    while (true) {
        System.out.println(array[index++]);
    }
} catch (ArrayIndexOutOfBoundsException e) {
    // Ende des Arrays
}

// ✅ BESSER
for (int i = 0; i < array.length; i++) {
    System.out.println(array[i]);
}
```

**3. Exception Stack Trace verlieren**
```java
// ❌ SCHLECHT
try {
    riskanteOperation();
} catch (IOException e) {
    throw new RuntimeException("Fehler");  // Original-Exception verloren!
}

// ✅ BESSER
try {
    riskanteOperation();
} catch (IOException e) {
    throw new RuntimeException("Fehler bei Operation", e);  // Cause mitgeben!
}
```

---

## Praxisbeispiele für BMI-Rechner

### Beispiel 1: Eingabevalidierung mit Exceptions

```java
public class Bmirechner {
    private double groesse;
    private double gewicht;
    
    public void setGroesse(double groesse) {
        if (groesse <= 0) {
            throw new IllegalArgumentException(
                "Größe muss positiv sein! Eingabe: " + groesse + "m"
            );
        }
        if (groesse > 3.0) {
            throw new IllegalArgumentException(
                "Größe unrealistisch hoch! Eingabe: " + groesse + "m (max: 3.0m)"
            );
        }
        this.groesse = groesse;
    }
    
    public void setGewicht(double gewicht) {
        if (gewicht <= 0) {
            throw new IllegalArgumentException(
                "Gewicht muss positiv sein! Eingabe: " + gewicht + "kg"
            );
        }
        if (gewicht > 500) {
            throw new IllegalArgumentException(
                "Gewicht unrealistisch hoch! Eingabe: " + gewicht + "kg (max: 500kg)"
            );
        }
        this.gewicht = gewicht;
    }
    
    public double berechne(double gewicht, double groesse) {
        setGewicht(gewicht);  // Validierung durch Setter
        setGroesse(groesse);  // Validierung durch Setter
        return gewicht / (groesse * groesse);
    }
}
```

### Beispiel 2: GUI mit Exception Handling

```java
public class MainWindow extends JFrame {
    private JTextField gewichtField;
    private JTextField groesseField;
    private Bmirechner rechner;
    
    private void berechneBmiButtonClicked() {
        try {
            // Eingabe parsen
            double gewicht = Double.parseDouble(gewichtField.getText());
            double groesse = Double.parseDouble(groesseField.getText());
            
            // BMI berechnen (kann IllegalArgumentException werfen)
            double bmi = rechner.berechne(gewicht, groesse);
            
            // Ergebnis anzeigen
            JOptionPane.showMessageDialog(
                this,
                "Ihr BMI: " + String.format("%.2f", bmi),
                "Ergebnis",
                JOptionPane.INFORMATION_MESSAGE
            );
            
        } catch (NumberFormatException e) {
            // Ungültige Zahl eingegeben
            JOptionPane.showMessageDialog(
                this,
                "Bitte geben Sie gültige Zahlen ein!\n" +
                "Verwenden Sie '.' als Dezimaltrennzeichen.",
                "Eingabefehler",
                JOptionPane.ERROR_MESSAGE
            );
            
        } catch (IllegalArgumentException e) {
            // Validierung fehlgeschlagen (z.B. negative Werte)
            JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Ungültige Eingabe",
                JOptionPane.WARNING_MESSAGE
            );
            
        } catch (Exception e) {
            // Unerwarteter Fehler
            JOptionPane.showMessageDialog(
                this,
                "Ein unerwarteter Fehler ist aufgetreten:\n" + e.getMessage(),
                "Fehler",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();  // Für Debugging
        }
    }
}
```

### Beispiel 3: Datenbankverbindung mit Exception Handling

```java
public class DatabaseConnector {
    private Connection connection;
    
    public void connect(String url, String user, String password) 
            throws SQLException {
        try {
            // Treiber laden
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Verbindung herstellen
            connection = DriverManager.getConnection(url, user, password);
            
            System.out.println("Datenbankverbindung erfolgreich!");
            
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "MySQL-Treiber nicht gefunden! " +
                "Bitte mysql-connector-java zur classpath hinzufügen.",
                e
            );
        }
    }
    
    public void saveBmiErgebnis(double bmi, String kategorie) 
            throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Keine aktive Datenbankverbindung!");
        }
        
        String sql = "INSERT INTO bmi_ergebnisse (bmi, kategorie, datum) VALUES (?, ?, NOW())";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, bmi);
            stmt.setString(2, kategorie);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException(
                "Fehler beim Speichern des BMI-Ergebnisses: " + e.getMessage(),
                e
            );
        }
    }
    
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Datenbankverbindung geschlossen.");
            } catch (SQLException e) {
                System.err.println("Fehler beim Schließen: " + e.getMessage());
            }
        }
    }
}
```

### Beispiel 4: Custom Exception für BMI-Rechner

```java
// Eigene Exception-Klasse
public class BmiCalculationException extends Exception {
    private double gewicht;
    private double groesse;
    
    public BmiCalculationException(String message, double gewicht, double groesse) {
        super(message);
        this.gewicht = gewicht;
        this.groesse = groesse;
    }
    
    public double getGewicht() {
        return gewicht;
    }
    
    public double getGroesse() {
        return groesse;
    }
    
    @Override
    public String getMessage() {
        return super.getMessage() + 
               " [Gewicht: " + gewicht + "kg, Größe: " + groesse + "m]";
    }
}

// Verwendung
public class BmiManager {
    public double berechneValidiert(double gewicht, double groesse) 
            throws BmiCalculationException {
        
        if (gewicht <= 0 || groesse <= 0) {
            throw new BmiCalculationException(
                "Gewicht und Größe müssen positiv sein!",
                gewicht,
                groesse
            );
        }
        
        if (gewicht > 500 || groesse > 3.0) {
            throw new BmiCalculationException(
                "Werte außerhalb realistischer Grenzen!",
                gewicht,
                groesse
            );
        }
        
        return gewicht / (groesse * groesse);
    }
}

// Aufrufer
try {
    double bmi = manager.berechneValidiert(gewicht, groesse);
} catch (BmiCalculationException e) {
    System.err.println("BMI-Berechnung fehlgeschlagen:");
    System.err.println("  Gewicht: " + e.getGewicht() + "kg");
    System.err.println("  Größe: " + e.getGroesse() + "m");
    System.err.println("  Grund: " + e.getMessage());
}
```

---

## Zusammenfassung

### Wichtigste Konzepte

1. **Try-Catch-Finally** - Struktur zur Fehlerbehandlung
2. **Checked Exceptions** - Müssen behandelt werden (IOException, SQLException)
3. **Unchecked Exceptions** - Können behandelt werden (RuntimeException)
4. **Eigene Exceptions** - Für spezifische Fehlerfälle
5. **Best Practices** - Spezifisch, aussagekräftig, nicht verschlucken

### Faustregeln

✅ **Verwende Exceptions für außergewöhnliche Situationen**  
✅ **Validiere Eingaben früh (Fail-Fast)**  
✅ **Gib aussagekräftige Fehlermeldungen**  
✅ **Schließe Ressourcen im Finally-Block**  
✅ **Logge Exceptions für Debugging**  

❌ **Verwende Exceptions nicht für normale Programmflusssteuerung**  
❌ **Verschlucke Exceptions nicht (leerer Catch-Block)**  
❌ **Werfe keine zu generischen Exceptions**  

---

## Weiterführende Ressourcen

- [Oracle Java Tutorial: Exceptions](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [Effective Java (Joshua Bloch)](https://www.oreilly.com/library/view/effective-java/9780134686097/) - Kapitel zu Exceptions
- `SECURE_CODING.md` - Sicherheitsaspekte bei Exception Handling
- `UNIT_TESTING.md` - Testen von Exception-Handling

---

**💡 Tipp:** Exceptions sind ein mächtiges Werkzeug für robuste Software. Nutze sie bewusst und konsequent, um deine Anwendung stabil und wartbar zu halten!
