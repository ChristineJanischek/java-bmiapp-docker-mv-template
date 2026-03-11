# Kurztest: Datenbank 1 – Verbindung aufbauen

**Klasse:** _________________  **Datum:** _________________  **Zeit: 25 Min | Punkte: 25**

> **Erlaubte Hilfsmittel:** Syntaxhilfe_Datenbank.md  
> **Voraussetzung:** MySQL-Datenbank `bmi_datenbank` läuft lokal (localhost:3306)

---

## Aufgabe 1: JDBC-Verbindung (5 Punkte)

Vervollständige die Methode. Die Verbindung soll automatisch geschlossen werden:

```java
public boolean verbindungTesten(String url, String user, String pw) {
    try (______________________________________) {
        
        System.out.println("Verbindung hergestellt!");
        return true;

    } catch (SQLException e) {
        System.err.println("Fehler: " + e.getMessage());
        return false;
    }
}
```

---

## Aufgabe 2: URL zusammensetzen (4 Punkte)

Schreibe die korrekte JDBC-URL für folgende Daten:

| Angabe | Wert |
|--------|------|
| Host | `localhost` |
| Port | `3306` |
| Datenbankname | `schule_db` |

```java
String url = "____________________________________";
```

---

## Aufgabe 3: Verbindung nutzen – SELECT (6 Punkte)

Gegeben ist eine offene `Connection conn`. Schreibe den Code, um alle Zeilen der Tabelle `person` auszulesen und auf der Konsole auszugeben (Spalten: `id`, `vorname`, `nachname`):

```java
// SQL-Abfrage formulieren
String sql = "________________________________";

try (Statement stmt = ____________________;
     ResultSet rs   = ____________________) {

    while (rs.next()) {
        System.out.println(
            rs.getInt("___") + " | " +
            rs.getString("_______") + " " +
            rs.getString("________")
        );
    }
}
```

---

## Aufgabe 4: Fehleranalyse (5 Punkte)

Der folgende Code enthält **drei Fehler**. Benenne und korrigiere jeden Fehler:

```java
Connection conn = DriverManager.getConnection("localhost:3306/db", "root", "pw");
Statement stmt  = conn.createStatement;
ResultSet rs    = stmt.executeUpdate("SELECT * FROM person");
```

**Fehler 1:** _______________________________________________

**Fehler 2:** _______________________________________________

**Fehler 3:** _______________________________________________

---

## Aufgabe 5: Kurzfragen (5 Punkte, je 1 Punkt)

1. Was bedeutet `try-with-resources` und warum ist es bei Datenbankverbindungen wichtig?

   _________________________________________________________________

2. Was gibt `executeQuery()` zurück?

   _________________________________________________________________

3. Was gibt `executeUpdate()` zurück?

   _________________________________________________________________

4. Warum darf man Zugangsdaten (Passwort) nicht direkt im Quellcode speichern?

   _________________________________________________________________

5. Welche Maven-Abhängigkeit wird für MySQL in Java benötigt?

   _________________________________________________________________
