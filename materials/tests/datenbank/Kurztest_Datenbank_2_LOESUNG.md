# Kurztest: Datenbank 2 – CRUD-Operationen – LOESUNG

**Zeit: 30 Min | Punkte: 30**

---

## Aufgabe 1: INSERT – Person einfügen (8 Punkte)

### Musterlösung

```java
public int personEinfuegen(Connection conn,
                            String vorname, String nachname,
                            int alter, String geschlecht) throws SQLException {

    String sql = "INSERT INTO person (vorname, nachname, alter, geschlecht) "  // 2 Pkt
               + "VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql,
            Statement.RETURN_GENERATED_KEYS)) {                                 // 2 Pkt

        pstmt.setString(1, vorname);    // 0,5 Pkt
        pstmt.setString(2, nachname);   // 0,5 Pkt
        pstmt.setInt(3, alter);         // 0,5 Pkt
        pstmt.setString(4, geschlecht); // 0,5 Pkt

        pstmt.executeUpdate();                                                  // 1 Pkt

        ResultSet keys = pstmt.getGeneratedKeys();
        if (keys.next()) {
            return keys.getInt(1);                                              // 1 Pkt
        }
    }
    return -1;
}
```

### Erläuterung

- **Spaltennamen** im INSERT müssen mit den Tabellenspalten übereinstimmen. `id` wird weggelassen – MySQL vergibt sie automatisch (`AUTO_INCREMENT`).
- `Statement.RETURN_GENERATED_KEYS` teilt JDBC mit, dass die automatisch generierten Schlüssel zurückgegeben werden sollen.
- `pstmt.getGeneratedKeys()` gibt ein `ResultSet` zurück, dessen erste Spalte die neue ID enthält.
- `setInt` für Integer-Werte, `setString` für Zeichenketten.

---

## Aufgabe 2: UPDATE – Alter ändern (7 Punkte)

### Musterlösung

```java
public boolean alterAendern(Connection conn, int personId, int neuesAlter)
        throws SQLException {

    String sql = "UPDATE person SET alter = ? WHERE id = ?";                   // 2 Pkt

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {               // 1 Pkt

        pstmt.setInt(1, neuesAlter);                                           // 1 Pkt
        pstmt.setInt(2, personId);                                             // 1 Pkt

        int betroffeneZeilen = pstmt.executeUpdate();                          // 1 Pkt
        return betroffeneZeilen > 0;                                           // 1 Pkt
    }
}
```

### Erläuterung

- Die Reihenfolge der `?`-Parameter entspricht ihrer Reihenfolge im SQL-String: `alter = ?` ist Parameter 1, `id = ?` ist Parameter 2.
- `executeUpdate()` gibt die Anzahl der geänderten Zeilen zurück (`0` = kein Datensatz mit dieser ID gefunden; `1` = erfolgreich geändert).
- `return betroffeneZeilen > 0` gibt `true` zurück, wenn mindestens eine Zeile geändert wurde.

---

## Aufgabe 3: DELETE – Person löschen (7 Punkte)

### Musterlösung

```java
public boolean personLoeschen(Connection conn, int id) throws SQLException {

    String sql = "DELETE FROM person WHERE id = ?";                            // 2 Pkt

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {               // 1 Pkt

        pstmt.setInt(1, id);                                                   // 2 Pkt

        int betroffeneZeilen = pstmt.executeUpdate();                          // 1 Pkt
        return betroffeneZeilen > 0;                                           // 1 Pkt
    }
}
```

### Erläuterung

- Das Muster ist identisch mit UPDATE: SQL-String vorbereiten, Parameter setzen, `executeUpdate()` aufrufen.
- Falls die Tabelle `messung` eine Fremdschlüsselbeziehung mit `ON DELETE CASCADE` hat, werden alle Messungen der gelöschten Person automatisch mitgelöscht.
- Ohne `CASCADE` würde der DELETE mit einem Fremdschlüssel-Fehler scheitern.

---

## Aufgabe 4: Fehler finden (4 Punkte)

### Problem (2 Punkte)

Der Code ist anfällig für **SQL-Injection**!

Wenn ein Angreifer als `eingabeId` den Wert `"1 OR 1=1"` eingibt, erzeugt das:
```sql
DELETE FROM person WHERE id = 1 OR 1=1
```
Das löscht **alle** Zeilen in der Tabelle!

### Korrekte Version (2 Punkte)

```java
public void loeschen(Connection conn, String eingabeId) throws SQLException {
    String sql = "DELETE FROM person WHERE id = ?";               // PreparedStatement!
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, eingabeId);                            // sicher: kein SQL-Einbetten
        pstmt.executeUpdate();
    }
}
```

### Erläuterung

`PreparedStatement` behandelt den eingegebenen Wert immer als reinen **Datenwert**, nicht als SQL-Befehlsteil. SQL-Injection wird dadurch vollständig verhindert.

---

## Aufgabe 5: Wahr oder Falsch? (4 Punkte)

| Aussage | Lösung | Erläuterung |
|---------|--------|-------------|
| `executeUpdate()` gibt ein `ResultSet` zurück. | **F** | Gibt `int` zurück (Anzahl betroffener Zeilen). `ResultSet` kommt von `executeQuery()`. |
| Bei `PreparedStatement` werden `?`-Platzhalter ab Index **1** gezählt. | **W** | Java-JDBC zählt ab 1 (nicht 0 wie Arrays!). |
| `Statement.RETURN_GENERATED_KEYS` wird benötigt, um die neue ID nach einem INSERT zu erhalten. | **W** | Ohne diese Konstante gibt `getGeneratedKeys()` ein leeres ResultSet zurück. |
| `pstmt.setString(1, eingabe)` schützt automatisch vor SQL-Injection. | **W** | Korrekter Zweck von PreparedStatement: Eingaben werden escaped, nicht als SQL interpretiert. |
