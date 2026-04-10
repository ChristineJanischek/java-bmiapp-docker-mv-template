# Kurztest: Sichere DB-Anbindung 1 - Injection, Secrets, Transaktionen

**Klasse:** _________________  **Datum:** _________________  **Zeit: 25 Min | Punkte: 30**

> **Erlaubte Hilfsmittel:** Unterlagen zu Version 7, Datenbank-Syntaxhilfe, JDBC-Beispiele

---

## Aufgabe 1: SQL-Injection verhindern (10 Punkte)

Ersetze die unsichere SQL-Erzeugung durch ein sicheres PreparedStatement.

```java
public List<Person> findByFirstName(String input) throws SQLException {
    List<Person> result = new ArrayList<>();
    String sql = "SELECT id, vorname, nachname FROM person WHERE vorname = ______";

    try (Connection conn = connector.verbinden();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.______________(1, input);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Ergebnisverarbeitung
            }
        }
    }
    return result;
}
```

Erklaere in einem Satz, warum die Eingabe `"' OR '1'='1"` damit nicht als SQL-Code ausgefuehrt wird.

_________________________________________________________________

---

## Aufgabe 2: Secrets aus dem Code entfernen (10 Punkte)

Vervollstaendige die Konfigurationsklasse so, dass Zugangsdaten aus Umgebungsvariablen gelesen werden.

```java
public final class DbConfig {

    public static String url() {
        return required("____________");
    }

    public static String user() {
        return required("____________");
    }

    public static String password() {
        return required("____________");
    }
}
```

Nenne einen Sicherheitsvorteil dieses Vorgehens.

_________________________________________________________________

---

## Aufgabe 3: Rollback bei Fehlern begruenden (10 Punkte)

Gegeben ist ein Ablauf, bei dem zuerst eine Person und danach eine erste Messung gespeichert wird.

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

Beantworte:

1. Was verhindert `rollback()` in diesem Beispiel?
2. Warum ist das nicht nur ein Komfort-, sondern auch ein Sicherheits- und Qualitaetsthema?

_________________________________________________________________

_________________________________________________________________

_________________________________________________________________