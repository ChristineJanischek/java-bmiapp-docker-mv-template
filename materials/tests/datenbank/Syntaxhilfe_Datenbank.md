# Syntaxhilfe: Datenbank-Verbindung mit Java (JDBC + MySQL)

**Ziel:** MySQL-Datenbank aus Java heraus verbinden und CRUD-Operationen durchführen.

---

## 1. JDBC-Verbindung aufbauen

```java
import java.sql.*;

String url  = "jdbc:mysql://localhost:3306/meine_datenbank";
String user = "root";
String pw   = "mein_passwort";

try (Connection conn = DriverManager.getConnection(url, user, pw)) {
    System.out.println("Verbindung hergestellt!");
} catch (SQLException e) {
    System.err.println("Fehler: " + e.getMessage());
}
```

> **try-with-resources** schließt die Verbindung automatisch – kein `conn.close()` nötig!

---

## 2. Einfache Abfrage (SELECT)

```java
String sql = "SELECT id, vorname, nachname FROM person";

try (Statement stmt = conn.createStatement();
     ResultSet rs   = stmt.executeQuery(sql)) {

    while (rs.next()) {
        int    id      = rs.getInt("id");
        String vorname = rs.getString("vorname");
        String nachname = rs.getString("nachname");
        System.out.println(id + " | " + vorname + " " + nachname);
    }
}
```

---

## 3. PreparedStatement – PFLICHT bei Benutzereingaben!

```java
// ✅ SICHER – verhindert SQL-Injection
String sql = "SELECT * FROM person WHERE nachname = ?";

try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setString(1, "Mustermann");       // ? wird ersetzt
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) { /* ... */ }
}
```

```java
// ❌ UNSICHER – NIEMALS so!
String name = eingabe;
Statement stmt = conn.createStatement();
stmt.executeQuery("SELECT * FROM person WHERE nachname = '" + name + "'");
```

---

## 4. INSERT – Datensatz einfügen

```java
String sql = "INSERT INTO person (vorname, nachname, alter, geschlecht) VALUES (?, ?, ?, ?)";

try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setString(1, "Anna");
    pstmt.setString(2, "Schmidt");
    pstmt.setInt(3, 28);
    pstmt.setString(4, "Frau");
    int betroffeneZeilen = pstmt.executeUpdate();
    System.out.println("Eingefügt: " + betroffeneZeilen + " Zeile(n)");
}
```

---

## 5. UPDATE – Datensatz ändern

```java
String sql = "UPDATE person SET alter = ? WHERE id = ?";

try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setInt(1, 30);
    pstmt.setInt(2, 5);
    int betroffeneZeilen = pstmt.executeUpdate();
    System.out.println("Geändert: " + betroffeneZeilen + " Zeile(n)");
}
```

---

## 6. DELETE – Datensatz löschen

```java
String sql = "DELETE FROM person WHERE id = ?";

try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setInt(1, 5);
    int betroffeneZeilen = pstmt.executeUpdate();
    System.out.println("Gelöscht: " + betroffeneZeilen + " Zeile(n)");
}
```

---

## 7. Auto-generierte ID nach INSERT lesen

```java
String sql = "INSERT INTO person (vorname, nachname) VALUES (?, ?)";

try (PreparedStatement pstmt = conn.prepareStatement(sql,
        Statement.RETURN_GENERATED_KEYS)) {
    pstmt.setString(1, "Max");
    pstmt.setString(2, "Muster");
    pstmt.executeUpdate();

    ResultSet keys = pstmt.getGeneratedKeys();
    if (keys.next()) {
        int neueId = keys.getInt(1);
        System.out.println("Neue ID: " + neueId);
    }
}
```

---

## 8. JOIN – Tabellen verknüpfen

```java
// Alle Messungen einer Person abfragen (1:N Beziehung)
String sql = """
    SELECT p.vorname, p.nachname, m.gewicht, m.groesse, m.bmi
    FROM   person p
    JOIN   messung m ON m.person_id = p.id
    WHERE  p.id = ?
    ORDER BY m.zeitstempel DESC
    """;

try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setInt(1, personId);
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) {
        System.out.printf("%s %s – BMI: %.2f%n",
            rs.getString("vorname"),
            rs.getString("nachname"),
            rs.getDouble("bmi"));
    }
}
```

---

## 9. Maven-Abhängigkeit (pom.xml)

```xml
<!-- MySQL JDBC-Treiber -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```

---

## 10. Datenbankschema BMI-Rechner (3NF-Beispiel)

```sql
-- Tabelle: person
CREATE TABLE person (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    vorname   VARCHAR(100) NOT NULL,
    nachname  VARCHAR(100) NOT NULL,
    alter     INT          NOT NULL,
    geschlecht ENUM('Mann','Frau','Divers') NOT NULL
);

-- Tabelle: messung (1:N zu person)
CREATE TABLE messung (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    person_id  INT            NOT NULL,
    gewicht    DECIMAL(5,2)   NOT NULL,
    groesse    DECIMAL(4,2)   NOT NULL,
    bmi        DECIMAL(5,2)   NOT NULL,
    zeitstempel DATETIME      DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

-- Tabelle: arzt
CREATE TABLE arzt (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    spezialisierung VARCHAR(100)
);

-- Bridge-Tabelle: behandlung (M:N zwischen arzt und person)
CREATE TABLE behandlung (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    arzt_id      INT          NOT NULL,
    person_id    INT          NOT NULL,
    diagnose     VARCHAR(255),
    beginn_datum DATE         DEFAULT (CURRENT_DATE),
    status       ENUM('Aktiv','Abgeschlossen','Pausiert') DEFAULT 'Aktiv',
    FOREIGN KEY (arzt_id)   REFERENCES arzt(id),
    FOREIGN KEY (person_id) REFERENCES person(id)
);
```

---

## Tipps & Merksätze

| Thema | Merksatz |
|-------|---------|
| **PreparedStatement** | Pflicht bei Benutzereingaben – verhindert SQL-Injection |
| **try-with-resources** | Verbindung/Statement/ResultSet werden automatisch geschlossen |
| **executeQuery()** | Für SELECT – gibt `ResultSet` zurück |
| **executeUpdate()** | Für INSERT, UPDATE, DELETE – gibt Anzahl betroffener Zeilen zurück |
| **rs.next()** | Iteriert über Ergebniszeilen – `true` solange Zeilen vorhanden |
| **Passwort** | Niemals im Quellcode – nutze Konfigurationsdateien oder Umgebungsvariablen |
