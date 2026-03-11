# Kurztest: Datenbank 3 – Projekt-Integration – LOESUNG

**Zeit: 35 Min | Punkte: 35**

---

## Aufgabe 1: 1:N Beziehung – Messung speichern (8 Punkte)

### Musterlösung

```java
public int messungSpeichern(Connection conn,
                             int personId, double gewicht,
                             double groesse, double bmi) throws SQLException {

    String sql = "INSERT INTO messung (person_id, gewicht, groesse, bmi) "    // 3 Pkt
               + "VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql,
            Statement.RETURN_GENERATED_KEYS)) {                                // 1 Pkt

        pstmt.setInt(1,    personId);                                          // je 0,5 Pkt
        pstmt.setDouble(2, gewicht);
        pstmt.setDouble(3, groesse);
        pstmt.setDouble(4, bmi);
        pstmt.executeUpdate();                                                 // 1 Pkt

        ResultSet keys = pstmt.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);                                             // 1 Pkt
        }
    }
    return -1;
}
```

### Erläuterung

- `person_id` ist der **Fremdschlüssel** – er verbindet die Messung mit einer Person (1:N Beziehung in der Datenbank).
- `zeitstempel` wird weggelassen: MySQL setzt ihn automatisch auf `CURRENT_TIMESTAMP` (Default-Wert in der Tabellendefinition).
- `setDouble()` wird für `DECIMAL`-Spalten verwendet.

---

## Aufgabe 2: JOIN – Messungen einer Person abfragen (10 Punkte)

### Musterlösung

```java
public List<String> messungenNachPerson(Connection conn, int personId)
        throws SQLException {

    List<String> ergebnisse = new ArrayList<>();

    // SQL mit JOIN (4 Punkte)
    String sql = """
            SELECT p.vorname, p.nachname, m.gewicht, m.bmi, m.zeitstempel
            FROM   messung m
            JOIN   person p ON m.person_id = p.id
            WHERE  m.person_id = ?
            ORDER BY m.zeitstempel DESC
            """;

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {               // 2 Punkte

        pstmt.setInt(1, personId);                                             // 1 Punkt
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {                                                    // 2 Punkte
            String eintrag = String.format("%s %s | %.1f kg | BMI: %.2f | %s",
                rs.getString("vorname"),
                rs.getString("nachname"),
                rs.getDouble("gewicht"),
                rs.getDouble("bmi"),
                rs.getString("zeitstempel")
            );
            ergebnisse.add(eintrag);
        }
    }

    return ergebnisse;                                                         // 1 Punkt
}
```

### Erläuterung

**JOIN** verbindet zwei Tabellen über eine gemeinsame Spalte:
```sql
JOIN person p ON m.person_id = p.id
```
- `m.person_id` ist der Fremdschlüssel in `messung`
- `p.id` ist der Primärschlüssel in `person`
- `ON` beschreibt die Verknüpfungsbedingung

`ORDER BY m.zeitstempel DESC` – neueste Messung kommt zuerst.

`String.format("%.1f", wert)` – 1 Nachkommastelle; `"%.2f"` – 2 Nachkommastellen.

---

## Aufgabe 3: Vorlage adaptieren (10 Punkte)

### Musterlösung

```java
public class BibliothekConnector {

    // a) URL für bibliothek_db (2 Punkte)
    private static final String URL =
        "jdbc:mysql://localhost:3306/bibliothek_db"
        + "?useSSL=false&serverTimezone=Europe/Berlin";

    private final String benutzer;
    private final String passwort;

    // a) Konstruktor (im selben Bereich – keine eigenen Punkte)
    public BibliothekConnector(String benutzer, String passwort) {
        this.benutzer = benutzer;
        this.passwort = passwort;
    }

    // Hilfsmethode zum Verbinden (Übernahme aus Vorlage)
    public Connection verbinden() throws SQLException {
        return DriverManager.getConnection(URL, benutzer, passwort);
    }

    // b) buchEinfuegen (4 Punkte)
    public int buchEinfuegen(Connection conn, String titel, String autor, String isbn)
            throws SQLException {

        String sql = "INSERT INTO buch (titel, autor, isbn) VALUES (?, ?, ?)"; // 1 Pkt

        try (PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {                            // 1 Pkt

            pstmt.setString(1, titel);                                         // 0,5 Pkt
            pstmt.setString(2, autor);                                         // 0,5 Pkt
            pstmt.setString(3, isbn);                                          // 0,5 Pkt
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);                                         // 0,5 Pkt
            }
        }
        return -1;
    }

    // c) bestandAendern (4 Punkte)
    public boolean bestandAendern(Connection conn, int buchId, int neuerBestand)
            throws SQLException {

        String sql = "UPDATE buch SET bestand = ? WHERE id = ?";              // 1 Pkt

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {          // 1 Pkt

            pstmt.setInt(1, neuerBestand);                                    // 0,5 Pkt
            pstmt.setInt(2, buchId);                                          // 0,5 Pkt

            int betroffeneZeilen = pstmt.executeUpdate();                     // 0,5 Pkt
            return betroffeneZeilen > 0;                                      // 0,5 Pkt
        }
    }
}
```

### Erläuterung: Vorlage adaptieren – Checkliste

Beim Adaptieren der `Vorlage_DatabaseConnector.java` auf ein neues Projekt änderst du:

| Was ändern? | Beispiel |
|-------------|---------|
| `DB_NAME` / URL | `"bibliothek_db"` statt `"bmi_datenbank"` |
| Tabellennamen im SQL | `buch` statt `person` |
| Spaltennamen im SQL | `titel, autor, isbn` statt `vorname, nachname, ...` |
| `setString()` / `setInt()` / `setDouble()` | passend zum Datentyp der Spalte |
| Rückgabetypen | passend zu deinen eigenen Klassen |

Alles andere (Verbindungsaufbau, try-with-resources, PreparedStatement-Muster) bleibt **gleich**!

---

## Aufgabe 4: Kurzfragen (7 Punkte)

### Musterlösungen

**1. Was ist 3NF (Dritte Normalform)?** *(2 Punkte)*

3NF ist eine Stufe der Datenbankmodellierung, bei der:
- Jede Tabelle nur Daten eines Themas enthält (z.B. `person` nur Personendaten, `messung` nur Messdaten).
- Keine Spalte von einer Nicht-Schlüssel-Spalte abhängt (keine transitive Abhängigkeit).

Vorteil: Keine Redundanz (Daten werden nicht mehrfach gespeichert), einfachere Aktualisierung, weniger Fehleranfälligkeit.

*(1 Punkt Erklärung, 1 Punkt Vorteil)*

---

**2. Was ist ein Fremdschlüssel und was bewirkt ON DELETE CASCADE?** *(2 Punkte)*

Ein **Fremdschlüssel** (`FOREIGN KEY`) ist eine Spalte, die auf den Primärschlüssel einer anderen Tabelle verweist und so die Verknüpfung zwischen Tabellen herstellt (z.B. `messung.person_id` → `person.id`).

`ON DELETE CASCADE` bedeutet: Wenn eine Person gelöscht wird, werden automatisch alle zugehörigen Messungen ebenfalls gelöscht. Verhindert verwaiste Datensätze (sog. „orphaned records").

*(1 Punkt Fremdschlüssel, 1 Punkt CASCADE)*

---

**3. Unterschied Statement vs. PreparedStatement:** *(2 Punkte)*

`Statement` wird für einfache SQL-Befehle ohne Parameter verwendet – der SQL-String wird direkt übergeben. `PreparedStatement` verwendet `?`-Platzhalter für Parameter, die sicher gesetzt werden; es schützt vor SQL-Injection, ist wiederverwendbar und klar strukturiert.

*(1 Punkt Statement, 1 Punkt PreparedStatement)*

---

**4. Was ist SQL-Injection und wie wird sie verhindert?** *(1 Punkt)*

SQL-Injection ist ein Angriff, bei dem ein Benutzer SQL-Code als Eingabe einschleust, der vom Datenbankserver als Befehl ausgeführt wird (z.B. `' OR '1'='1`). Verhindert wird es durch `PreparedStatement` mit `?`-Platzhaltern – Eingaben werden als Datenwerte behandelt, nicht als SQL-Code.
