# Kurztest: Sichere DB-Anbindung 2 - Rollen, Rechte, Parametrisierung

**Klasse:** _________________  **Datum:** _________________  **Zeit: 25 Min | Punkte: 30**

> **Erlaubte Hilfsmittel:** Unterlagen zu Version 7, JDBC- und SQL-Notizen

---

## Aufgabe 1: Least-Privilege begruenden (10 Punkte)

Ein Team vergibt dem App-User Admin-Rechte auf der Datenbank.

a) Nenne zwei konkrete Risiken dieser Entscheidung. (4 Punkte)

1. _______________________________________________________________
2. _______________________________________________________________

b) Nenne ein sinnvolles Minimal-Rechtepaket fuer die BMI-App. (3 Punkte)

_________________________________________________________________

c) Begruende in 1 bis 2 Saetzen, warum das ein Sicherheitsgewinn ist. (3 Punkte)

_________________________________________________________________

---

## Aufgabe 2: Unsichere SQL-Aenderung absichern (10 Punkte)

Ersetze die String-Konkatenation durch ein PreparedStatement.

```java
public boolean updateEmail(Connection conn, int personId, String newEmail) throws SQLException {
    String sql = "UPDATE person SET email = ______________________________";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.______________(1, newEmail);
        ps.______________(2, personId);
        return ps.executeUpdate() > 0;
    }
}
```

Schreibe in einem Satz, warum auch UPDATE-Befehle von SQL-Injection betroffen sein koennen.

_________________________________________________________________

---

## Aufgabe 3: Verbindungsaufbau robuster machen (10 Punkte)

Ergaenze eine JDBC-URL um zwei sinnvolle Parameter fuer Betriebssicherheit.

```text
jdbc:mysql://localhost:3306/bmiapp?________________________
```

Nenne zwei Risiken, die durch schlechte Verbindungs- oder Konfigurationswerte entstehen koennen.

1. _______________________________________________________________
2. _______________________________________________________________
