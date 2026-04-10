# Syntaxhilfe: Sichere DB-Anbindung (Version 7)

**Ziel:** Datenbankzugriffe sicher gestalten (Injection-Schutz, Rechtekonzept, Transaktionen, sichere Konfiguration).

---

## 1. PreparedStatement statt String-Konkatenation

```java
String sql = "SELECT id, vorname, nachname FROM person WHERE vorname = ?";

try (Connection conn = connector.verbinden();
     PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setString(1, input);
    try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            // auswerten
        }
    }
}
```

Unsicher (nicht verwenden):

```java
String sql = "SELECT * FROM person WHERE vorname='" + input + "'";
```

Merksatz:

- SQL-Befehl und Eingabedaten muessen getrennt bleiben.

---

## 2. Sichere DB-Konfiguration mit Umgebungsvariablen

```java
public final class DbConfig {
    private DbConfig() {}

    public static String url() {
        return required("BMI_DB_URL");
    }

    public static String user() {
        return required("BMI_DB_USER");
    }

    public static String password() {
        return required("BMI_DB_PASSWORD");
    }

    private static String required(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Fehlende Umgebungsvariable: " + key);
        }
        return value;
    }
}
```

Merksatz:

- Secrets gehoeren nie in den Quellcode und nie in Git.

---

## 3. Least-Privilege-Rechte

Sinnvolle Startrechte fuer den App-User:

- `SELECT`, `INSERT`, `UPDATE`, `DELETE` auf benoetigten Tabellen
- keine Admin-Rechte
- kein `DROP`, kein `ALTER`

Merksatz:

- Kleine Rechte begrenzen den Schaden bei Fehlern oder Angriffen.

---

## 4. Transaktionen mit Rollback

```java
conn.setAutoCommit(false);
try {
    int personId = personRepository.insert(conn, person);
    messungRepository.insert(conn, personId, messung);
    conn.commit();
} catch (SQLException ex) {
    conn.rollback();
    throw ex;
} finally {
    conn.setAutoCommit(true);
}
```

Merksatz:

- Entweder alles speichern oder nichts (atomare Einheit).

---

## 5. Sichere Fehlerkommunikation

```java
try {
    repository.savePerson(person);
} catch (SQLException e) {
    secureLogger.logError("DB_SAVE_PERSON", "U001", e);
    throw new IllegalStateException("Daten konnten nicht gespeichert werden. Bitte erneut versuchen.");
}
```

Merksatz:

- Interne Details nur ins Log, nicht in Fehlermeldungen fuer Nutzende.

---

## 6. Kurze JDBC-URL-Hinweise

Beispiel:

```text
jdbc:mysql://localhost:3306/bmiapp?useSSL=true&serverTimezone=Europe/Berlin
```

Achte auf:

- SSL/TLS-Konfiguration
- passende Zeitzone
- stabile Timeout-Werte (projektabhaengig)

---

## 7. Mini-Checkliste vor Abgabe

- [ ] Jeder SQL-Zugriff nutzt PreparedStatement
- [ ] Keine DB-Secrets im Quellcode
- [ ] Rechte fuer App-User minimiert
- [ ] Transaktionen bei zusammengehoerigen DB-Schritten
- [ ] Fehler intern detailliert, extern generisch
- [ ] Audit-Logging ohne sensible Klartextdaten
