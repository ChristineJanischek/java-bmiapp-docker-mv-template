# Kurztest: Datenbank 3 – Projekt-Integration (JOIN, 1:N, Vorlage adaptieren)

**Klasse:** _________________  **Datum:** _________________  **Zeit: 35 Min | Punkte: 35**

> **Erlaubte Hilfsmittel:** Syntaxhilfe_Datenbank.md, Vorlage_DatabaseConnector.java  
> **Datenbankschema (gegeben):**
> ```sql
> CREATE TABLE person (
>     id         INT AUTO_INCREMENT PRIMARY KEY,
>     vorname    VARCHAR(100) NOT NULL,
>     nachname   VARCHAR(100) NOT NULL,
>     alter      INT          NOT NULL,
>     geschlecht ENUM('Mann','Frau','Divers') NOT NULL
> );
>
> CREATE TABLE messung (
>     id          INT AUTO_INCREMENT PRIMARY KEY,
>     person_id   INT           NOT NULL,    -- Fremdschlüssel
>     gewicht     DECIMAL(5,2)  NOT NULL,
>     groesse     DECIMAL(4,2)  NOT NULL,
>     bmi         DECIMAL(5,2)  NOT NULL,
>     zeitstempel DATETIME DEFAULT CURRENT_TIMESTAMP,
>     FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
> );
> ```

---

## Aufgabe 1: 1:N Beziehung – Messung speichern (8 Punkte)

Vervollständige die Methode, die eine neue Messung für eine existierende Person speichert:

```java
public int messungSpeichern(Connection conn,
                             int personId, double gewicht,
                             double groesse, double bmi) throws SQLException {

    String sql = "INSERT INTO _______ (_________, _______, _______, ___) "
               + "VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql, ___________________________)) {

        pstmt.setInt(___,   personId);
        pstmt.setDouble(___, gewicht);
        pstmt.setDouble(___, groesse);
        pstmt.setDouble(___, bmi);
        pstmt.______________();

        ResultSet keys = pstmt.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);
        }
    }
    return -1;
}
```

---

## Aufgabe 2: JOIN – Messungen einer Person abfragen (10 Punkte)

Schreibe **vollständig** eine Methode `messungenNachPerson(Connection conn, int personId)`, die alle Messungen einer Person per JOIN zurückgibt.  
Rückgabe: `List<String>` mit je einem Eintrag pro Messung im Format:  
`"Max Mustermann | 80.0 kg | BMI: 24.69 | 2026-03-10 09:15:00"`

```java
public List<String> messungenNachPerson(Connection conn, int personId)
        throws SQLException {

    List<String> ergebnisse = new ArrayList<>();

    // TODO: SQL mit JOIN schreiben und Ergebnisse aufbauen
    // Hinweis: Benötigte Spalten:
    //   p.vorname, p.nachname, m.gewicht, m.bmi, m.zeitstempel

}
```

---

## Aufgabe 3: Vorlage adaptieren (10 Punkte)

Du arbeitest an einem **Bibliotheks-Verwaltungssystem** mit folgender Datenbankstruktur:

```sql
CREATE TABLE buch (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    titel    VARCHAR(200) NOT NULL,
    autor    VARCHAR(100) NOT NULL,
    isbn     VARCHAR(13)  UNIQUE NOT NULL,
    bestand  INT DEFAULT 1
);
```

Adaptiere die Vorlage `Vorlage_DatabaseConnector.java` und schreibe eine Klasse `BibliothekConnector` mit:

a) Einem Konstruktor der Benutzername und Passwort entgegennimmt und die URL für die Datenbank `bibliothek_db` aufbaut. *(2 Punkte)*

b) Eine Methode `buchEinfuegen(Connection conn, String titel, String autor, String isbn)` die ein neues Buch einfügt und die neue ID zurückgibt. *(4 Punkte)*

c) Eine Methode `bestandAendern(Connection conn, int buchId, int neuerBestand)` die den Bestand ändert und `true` zurückgibt wenn erfolgreich. *(4 Punkte)*

```java
public class BibliothekConnector {

    private static final String URL = "jdbc:mysql://___________/___________";
    private final String benutzer;
    private final String passwort;

    // a) Konstruktor
    public BibliothekConnector(String benutzer, String passwort) {
        // TODO
    }

    // b) buchEinfuegen
    public int buchEinfuegen(Connection conn, String titel, String autor, String isbn)
            throws SQLException {
        // TODO
    }

    // c) bestandAendern
    public boolean bestandAendern(Connection conn, int buchId, int neuerBestand)
            throws SQLException {
        // TODO
    }
}
```

---

## Aufgabe 4: Kurzfragen (7 Punkte)

1. Was versteht man unter **3NF** (Dritte Normalform) und warum ist sie wichtig?  *(2 Punkte)*

   _________________________________________________________________
   _________________________________________________________________

2. Was ist ein **Fremdschlüssel** (`FOREIGN KEY`) und welchen Vorteil bietet `ON DELETE CASCADE`?  *(2 Punkte)*

   _________________________________________________________________
   _________________________________________________________________

3. Erkläre den Unterschied zwischen `Statement` und `PreparedStatement` in zwei Sätzen.  *(2 Punkte)*

   _________________________________________________________________
   _________________________________________________________________

4. Was ist **SQL-Injection** und wie wird sie verhindert?  *(1 Punkt)*

   _________________________________________________________________
