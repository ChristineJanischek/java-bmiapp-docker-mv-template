# Kurztest: Datenbank 2 – CRUD-Operationen

**Klasse:** _________________  **Datum:** _________________  **Zeit: 30 Min | Punkte: 30**

> **Erlaubte Hilfsmittel:** Syntaxhilfe_Datenbank.md  
> **Datenbankschema (gegeben):**
> ```sql
> CREATE TABLE person (
>     id         INT AUTO_INCREMENT PRIMARY KEY,
>     vorname    VARCHAR(100) NOT NULL,
>     nachname   VARCHAR(100) NOT NULL,
>     alter      INT          NOT NULL,
>     geschlecht ENUM('Mann','Frau','Divers') NOT NULL
> );
> ```

---

## Aufgabe 1: INSERT – Person einfügen (8 Punkte)

Vervollständige die Methode. Die automatisch generierte ID soll zurückgegeben werden:

```java
public int personEinfuegen(Connection conn,
                            String vorname, String nachname,
                            int alter, String geschlecht) throws SQLException {

    String sql = "INSERT INTO person (_______, ________, _____, __________) "
               + "VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql, ___________________________)) {

        pstmt._________(1, vorname);
        pstmt._________(2, nachname);
        pstmt._________(3, alter);
        pstmt._________(4, geschlecht);

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

## Aufgabe 2: UPDATE – Alter ändern (7 Punkte)

Schreibe **vollständig** eine Methode `alterAendern(Connection conn, int personId, int neuesAlter)`, die das Alter einer Person in der Datenbank ändert und zurückgibt, ob die Änderung erfolgreich war (`true`/`false`):

```java
public boolean alterAendern(Connection conn, int personId, int neuesAlter)
        throws SQLException {

    // TODO: vollständig implementieren

}
```

---

## Aufgabe 3: DELETE – Person löschen (7 Punkte)

Schreibe **vollständig** eine Methode `personLoeschen(Connection conn, int id)`.  
Sie soll die Person mit der angegebenen ID löschen und `true` zurückgeben, wenn mindestens eine Zeile gelöscht wurde:

```java
public boolean personLoeschen(Connection conn, int id) throws SQLException {

    // TODO: vollständig implementieren

}
```

---

## Aufgabe 4: Fehler finden (4 Punkte)

Erkläre, warum folgender Code **problematisch** ist, und schreibe die korrekte Version:

```java
// Version A (problematisch):
public void loeschen(Connection conn, String eingabeId) throws SQLException {
    Statement stmt = conn.createStatement();
    stmt.executeUpdate("DELETE FROM person WHERE id = " + eingabeId);
}
```

**Problem:** _______________________________________________

**Korrekte Version:**
```java
public void loeschen(Connection conn, String eingabeId) throws SQLException {
    // TODO: korrekt implementieren

}
```

---

## Aufgabe 5: Wahr oder Falsch? (4 Punkte, je 1 Punkt)

Markiere mit **W** (wahr) oder **F** (falsch):

| Aussage | W / F |
|---------|-------|
| `executeUpdate()` gibt ein `ResultSet` zurück. | |
| Bei `PreparedStatement` werden `?`-Platzhalter ab Index **1** gezählt. | |
| `Statement.RETURN_GENERATED_KEYS` wird benötigt, um die neue ID nach einem INSERT zu erhalten. | |
| `pstmt.setString(1, eingabe)` schützt automatisch vor SQL-Injection. | |
