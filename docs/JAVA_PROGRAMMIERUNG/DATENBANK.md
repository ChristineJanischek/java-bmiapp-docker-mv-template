# Datenbankanbindung in Java (JDBC + MySQL)

Diese Datei erklärt, wie du eine MySQL-Datenbank mit deiner Java-Anwendung verbindest und die grundlegenden Datenbankoperationen (Einfügen, Lesen, Ändern, Löschen) umsetzt.

---

## Voraussetzungen

### 1. MySQL-Treiber in pom.xml eintragen

```xml
<!-- pom.xml – im <dependencies>-Block einfügen -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```

### 2. Datenbankschema anlegen (SQL)

```sql
-- Datenbank erstellen
CREATE DATABASE IF NOT EXISTS mein_projekt;
USE mein_projekt;

-- Beispiel: Tabelle person (3NF)
CREATE TABLE person (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    vorname    VARCHAR(100) NOT NULL,
    nachname   VARCHAR(100) NOT NULL,
    alter      INT          NOT NULL,
    geschlecht ENUM('Mann','Frau','Divers') NOT NULL
);

-- Tabelle messung – Fremdschlüssel auf person (1:N Beziehung)
CREATE TABLE messung (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    person_id   INT           NOT NULL,
    gewicht     DECIMAL(5,2)  NOT NULL,
    groesse     DECIMAL(4,2)  NOT NULL,
    bmi         DECIMAL(5,2)  NOT NULL,
    zeitstempel DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);
```

---

## Die DatabaseConnector-Klasse (Best Practice)

Alle Datenbankzugriffe werden in einer eigenen Klasse gebündelt. Diese Klasse übernimmst du als **Vorlage** und passt Tabellen- und Spaltennamen für dein Projekt an.

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnector {

    // ── Konfiguration ─────────────────────────────────────────────────
    private static final String URL =
        "jdbc:mysql://localhost:3306/mein_projekt"
        + "?useSSL=false&serverTimezone=Europe/Berlin&allowPublicKeyRetrieval=true";

    private final String benutzer;
    private final String passwort;   // ← NIEMALS hardcoded im Quellcode!

    private static final Logger LOG =
        Logger.getLogger(DatabaseConnector.class.getName());

    // ── Konstruktor ───────────────────────────────────────────────────
    public DatabaseConnector(String benutzer, String passwort) {
        this.benutzer = benutzer;
        this.passwort = passwort;
    }

    // ── Verbindung aufbauen ───────────────────────────────────────────
    /**
     * Stellt eine Verbindung her. Verwendung immer mit try-with-resources!
     *   try (Connection conn = db.verbinden()) { ... }
     */
    public Connection verbinden() throws SQLException {
        return DriverManager.getConnection(URL, benutzer, passwort);
    }

    /** Schnelltest: Gibt true zurück wenn die Verbindung funktioniert. */
    public boolean verbindungTesten() {
        try (Connection conn = verbinden()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            LOG.warning("Verbindung fehlgeschlagen: " + e.getMessage());
            return false;
        }
    }
```

---

## CREATE – Datensatz einfügen (INSERT)

```java
    /**
     * Fügt eine neue Person ein und gibt die generierte ID zurück.
     * Rückgabe -1 = Fehler.
     */
    public int personEinfuegen(String vorname, String nachname,
                                int alter, String geschlecht) {
        String sql =
            "INSERT INTO person (vorname, nachname, alter, geschlecht) "
          + "VALUES (?, ?, ?, ?)";

        // PreparedStatement: PFLICHT bei Benutzereingaben (verhindert SQL-Injection!)
        try (Connection conn  = verbinden();
             PreparedStatement pstmt =
                 conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, vorname);
            pstmt.setString(2, nachname);
            pstmt.setInt(3,    alter);
            pstmt.setString(4, geschlecht);

            pstmt.executeUpdate();   // Führt INSERT aus

            // Automatisch generierte ID auslesen
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Einfügen: " + e.getMessage(), e);
        }
        return -1;
    }
```

**Wichtig:**
- `?` sind Platzhalter – Werte werden mit `setString()`, `setInt()`, `setDouble()` gesetzt
- Die `?` werden ab Index **1** (nicht 0!) gezählt
- `Statement.RETURN_GENERATED_KEYS` → danach `getGeneratedKeys()` für die neue ID

---

## READ – Daten lesen (SELECT)

### Alle Zeilen einer Tabelle lesen

```java
    /**
     * Gibt alle Personen als Liste zurück.
     * Jedes String-Array: [id, vorname, nachname, alter, geschlecht]
     */
    public List<String[]> allePersonenLesen() {
        List<String[]> ergebnisse = new ArrayList<>();
        String sql = "SELECT id, vorname, nachname, alter, geschlecht "
                   + "FROM person ORDER BY nachname";

        try (Connection conn = verbinden();
             Statement  stmt = conn.createStatement();
             ResultSet  rs   = stmt.executeQuery(sql)) {   // executeQuery für SELECT!

            while (rs.next()) {   // rs.next() = eine Zeile weiter; false = Ende
                ergebnisse.add(new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("vorname"),
                    rs.getString("nachname"),
                    String.valueOf(rs.getInt("alter")),
                    rs.getString("geschlecht")
                });
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Lesen: " + e.getMessage(), e);
        }
        return ergebnisse;
    }
```

### Suche mit Parameter (PreparedStatement)

```java
    /** Sucht eine Person nach ihrer ID. Gibt null zurück wenn nicht gefunden. */
    public String[] personNachId(int id) {
        String sql = "SELECT id, vorname, nachname, alter, geschlecht "
                   + "FROM person WHERE id = ?";

        try (Connection conn  = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("vorname"),
                    rs.getString("nachname"),
                    String.valueOf(rs.getInt("alter")),
                    rs.getString("geschlecht")
                };
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler bei Suche: " + e.getMessage(), e);
        }
        return null;
    }
```

### JOIN – Daten aus verknüpften Tabellen (1:N)

```java
    /**
     * Gibt alle Messungen einer Person zurück (JOIN über Fremdschlüssel).
     * Format: [gewicht, groesse, bmi, zeitstempel]
     */
    public List<String[]> messungenVonPerson(int personId) {
        List<String[]> ergebnisse = new ArrayList<>();
        String sql = """
                SELECT p.vorname, p.nachname,
                       m.gewicht, m.groesse, m.bmi, m.zeitstempel
                FROM   messung m
                JOIN   person p ON m.person_id = p.id
                WHERE  m.person_id = ?
                ORDER BY m.zeitstempel DESC
                """;

        try (Connection conn  = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, personId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ergebnisse.add(new String[]{
                    rs.getString("vorname") + " " + rs.getString("nachname"),
                    String.valueOf(rs.getDouble("gewicht")) + " kg",
                    String.format("%.2f", rs.getDouble("bmi")),
                    rs.getString("zeitstempel")
                });
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler bei JOIN-Abfrage: " + e.getMessage(), e);
        }
        return ergebnisse;
    }
```

---

## UPDATE – Datensatz ändern

```java
    /**
     * Ändert das Alter einer Person.
     * Gibt true zurück wenn mindestens eine Zeile geändert wurde.
     */
    public boolean alterAendern(int personId, int neuesAlter) {
        String sql = "UPDATE person SET alter = ? WHERE id = ?";

        try (Connection conn  = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, neuesAlter);
            pstmt.setInt(2, personId);

            int betroffeneZeilen = pstmt.executeUpdate();  // gibt Anzahl geänderter Zeilen
            return betroffeneZeilen > 0;

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Ändern: " + e.getMessage(), e);
        }
        return false;
    }
```

---

## DELETE – Datensatz löschen

```java
    /**
     * Löscht eine Person (und durch ON DELETE CASCADE alle ihre Messungen).
     * Gibt true zurück wenn gelöscht wurde.
     */
    public boolean personLoeschen(int personId) {
        String sql = "DELETE FROM person WHERE id = ?";

        try (Connection conn  = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, personId);

            int betroffeneZeilen = pstmt.executeUpdate();
            return betroffeneZeilen > 0;

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Löschen: " + e.getMessage(), e);
        }
        return false;
    }
}  // Ende DatabaseConnector
```

---

## Verwendung im Projekt

```java
// Connector erstellen (Passwort aus Konfiguration lesen, NICHT hardcoden!)
DatabaseConnector db = new DatabaseConnector("root", "mein_passwort");

// Verbindung testen
if (!db.verbindungTesten()) {
    System.err.println("Keine Datenbankverbindung! Programm beendet.");
    return;
}

// Person einfügen
int neueId = db.personEinfuegen("Max", "Mustermann", 30, "Mann");
System.out.println("Person gespeichert mit ID: " + neueId);

// Alle Personen ausgeben
List<String[]> personen = db.allePersonenLesen();
for (String[] p : personen) {
    System.out.printf("[%s] %s %s, %s Jahre%n", p[0], p[1], p[2], p[3]);
}

// Alter ändern
db.alterAendern(neueId, 31);

// Person löschen (löscht auch Messungen automatisch)
db.personLoeschen(neueId);
```

---

## Integration in das MVC-Muster

Datenbankzugriffe gehören in das **Model** oder eine eigene **Persistenz-Klasse**. Der Controller ruft diese auf:

```
MainWindow  (View)
    │
    ▼
BmiManager  (Controller)
    │
    ├──► Bmirechner         (Model: Berechnung)
    │
    └──► DatabaseConnector  (Persistenz: Datenbank)
```

```java
public class BmiManager {
    private Bmirechner        model;
    private DatabaseConnector db;

    public BmiManager() {
        this.model = new Bmirechner();
        this.db    = new DatabaseConnector("root", System.getenv("DB_PASSWORT"));
    }

    public void berechneBMIUndSpeichern(Person person, double gewicht, double groesse) {
        // Berechnen
        model.berechne(gewicht, groesse);

        // Messung in DB speichern
        String sql = "INSERT INTO messung (person_id, gewicht, groesse, bmi) "
                   + "VALUES (?, ?, ?, ?)";
        // ... (PreparedStatement, s.o.)
    }
}
```

---

## Sicherheit: SQL-Injection verhindern

**SQL-Injection** ist ein Angriff, bei dem ein Benutzer SQL-Code als Eingabe einschleust:

```java
// ❌ GEFÄHRLICH – NIEMALS so!
String name = userEingabe;  // z.B. "' OR '1'='1"
stmt.executeQuery("SELECT * FROM person WHERE nachname = '" + name + "'");
// → Erzeugt: SELECT * FROM person WHERE nachname = '' OR '1'='1'
// → Gibt ALLE Zeilen zurück!

// ✅ SICHER – immer PreparedStatement verwenden
PreparedStatement pstmt = conn.prepareStatement(
    "SELECT * FROM person WHERE nachname = ?");
pstmt.setString(1, userEingabe);   // Eingabe wird als Datenwert behandelt, nicht als SQL
```

**Regel:** Jede Benutzereingabe die in eine SQL-Abfrage fließt **muss** über `PreparedStatement` laufen.

---

## try-with-resources – Verbindungen sicher schließen

Datenbankverbindungen, Statements und ResultSets sind **endliche Ressourcen**. Sie müssen nach der Verwendung geschlossen werden, sonst entstehen Ressourcenlecks:

```java
// ✅ RICHTIG: try-with-resources schließt automatisch (auch bei Exception!)
try (Connection conn       = verbinden();
     PreparedStatement ps  = conn.prepareStatement(sql);
     ResultSet rs          = ps.executeQuery()) {

    while (rs.next()) { /* ... */ }

}  // ← conn, ps, rs werden hier automatisch geschlossen

// ❌ FALSCH: manuelles Schließen – wird bei Exception vergessen!
Connection conn = verbinden();
Statement stmt  = conn.createStatement();
ResultSet rs    = stmt.executeQuery(sql);
// ... (Exception hier → conn wird NIE geschlossen!)
conn.close();
```

---

## Zusammenfassung: Methoden im Vergleich

| Operation | SQL-Befehl | Java-Methode | Rückgabe |
|-----------|-----------|--------------|---------|
| Einfügen | `INSERT` | `executeUpdate()` | `int` (Anzahl Zeilen) |
| Lesen | `SELECT` | `executeQuery()` | `ResultSet` |
| Ändern | `UPDATE` | `executeUpdate()` | `int` (Anzahl Zeilen) |
| Löschen | `DELETE` | `executeUpdate()` | `int` (Anzahl Zeilen) |

**Merksätze:**
- `executeQuery()` → nur für `SELECT` → gibt `ResultSet` zurück
- `executeUpdate()` → für `INSERT`, `UPDATE`, `DELETE` → gibt `int` zurück
- `PreparedStatement` → immer bei Benutzereingaben (Security!)
- `try-with-resources` → Verbindung wird automatisch geschlossen
- Passwörter nie hardcoden → Umgebungsvariablen oder Konfigurationsdatei nutzen
