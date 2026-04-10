# Kurztest: Sichere DB-Anbindung 1 - LOESUNG

**Zeit: 25 Min | Punkte: 30**

---

## Aufgabe 1: SQL-Injection verhindern (10 Punkte)

### Musterloesung

```java
public List<Person> findByFirstName(String input) throws SQLException {
    List<Person> result = new ArrayList<>();
    String sql = "SELECT id, vorname, nachname FROM person WHERE vorname = ?";

    try (Connection conn = connector.verbinden();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, input);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Ergebnisverarbeitung
            }
        }
    }
    return result;
}
```

Moegliche Erklaerung:

Die Eingabe wird als Datenwert an den Platzhalter gebunden und nicht in den SQL-Befehl hineinkonkatenert. Dadurch behandelt die Datenbank auch ein Payload wie `' OR '1'='1` nur als Textinhalt.

### Punktehinweis

- Platzhalter `?` korrekt: 3 Punkte
- `setString(1, input)` korrekt: 3 Punkte
- Erklaerung Trennung von Befehl und Daten: 4 Punkte

---

## Aufgabe 2: Secrets aus dem Code entfernen (10 Punkte)

### Musterloesung

```java
public final class DbConfig {

    public static String url() {
        return required("BMI_DB_URL");
    }

    public static String user() {
        return required("BMI_DB_USER");
    }

    public static String password() {
        return required("BMI_DB_PASSWORD");
    }
}
```

Moeglicher Sicherheitsvorteil:

Zugangsdaten landen nicht im Repository und muessen nicht mit dem Quellcode verteilt werden. Das reduziert das Risiko von Secret-Leaks deutlich.

### Punktehinweis

- drei Variablennamen korrekt: 6 Punkte
- Sicherheitsvorteil fachlich passend: 4 Punkte

---

## Aufgabe 3: Rollback bei Fehlern begruenden (10 Punkte)

### Musterloesung

1. `rollback()` verhindert, dass die Person bereits gespeichert bleibt, obwohl das Speichern der Messung gescheitert ist.
2. Ohne Rollback entstehen inkonsistente Teilzustaende. Das ist ein Qualitaetsproblem, weil Daten fachlich unvollstaendig sind, und ein Sicherheitsthema, weil fehlerhafte oder nicht nachvollziehbare Datenbestaende weitere Prozesse falsch beeinflussen koennen.

### Punktehinweis

- Wirkung von Rollback korrekt: 5 Punkte
- Begruendung mit Konsistenz/Qualitaet/Sicherheit: 5 Punkte