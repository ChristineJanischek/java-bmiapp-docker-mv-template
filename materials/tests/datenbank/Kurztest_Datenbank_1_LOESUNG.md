# Kurztest: Datenbank 1 – Verbindung aufbauen – LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: JDBC-Verbindung (5 Punkte)

### Aufgabenstellung

Vervollständige die Methode. Die Verbindung soll automatisch geschlossen werden.

### Musterlösung

```java
public boolean verbindungTesten(String url, String user, String pw) {
    try (Connection conn = DriverManager.getConnection(url, user, pw)) {   // ← 5 Punkte

        System.out.println("Verbindung hergestellt!");
        return true;

    } catch (SQLException e) {
        System.err.println("Fehler: " + e.getMessage());
        return false;
    }
}
```

### Erläuterung

`try (Connection conn = ...)` ist die **try-with-resources**-Syntax. Das `Connection`-Objekt implementiert `AutoCloseable`, daher wird `conn.close()` am Ende des Blocks automatisch aufgerufen – auch wenn eine Exception auftritt.

**Punktevergabe:**
- `Connection conn = DriverManager.getConnection(...)` korrekt: 3 Punkte
- try-with-resources (Klammern um den Ausdruck): 2 Punkte

---

## Aufgabe 2: URL zusammensetzen (4 Punkte)

### Musterlösung

```java
String url = "jdbc:mysql://localhost:3306/schule_db";
```

### Erläuterung

Das JDBC-URL-Format für MySQL:

```
jdbc:mysql://<HOST>:<PORT>/<DATENBANKNAME>
```

Optionale Parameter können mit `?` angehängt werden:
```java
"jdbc:mysql://localhost:3306/schule_db?useSSL=false&serverTimezone=Europe/Berlin"
```

**Punktevergabe:**
- `jdbc:mysql://` korrekt: 1 Punkt
- Host und Port korrekt: 1 Punkt
- Datenbankname korrekt: 1 Punkt
- Gesamtformat korrekt: 1 Punkt

---

## Aufgabe 3: Verbindung nutzen – SELECT (6 Punkte)

### Musterlösung

```java
String sql = "SELECT id, vorname, nachname FROM person";  // ← 2 Punkte

try (Statement stmt = conn.createStatement();              // ← 1 Punkt
     ResultSet rs   = stmt.executeQuery(sql)) {           // ← 1 Punkt

    while (rs.next()) {
        System.out.println(
            rs.getInt("id") + " | " +                     // ← 1 Punkt
            rs.getString("vorname") + " " +
            rs.getString("nachname")                       // ← 1 Punkt
        );
    }
}
```

### Erläuterung

- `conn.createStatement()` erzeugt ein Statement-Objekt für einfache SQL-Befehle ohne Parameter.
- `stmt.executeQuery(sql)` führt den SELECT aus und gibt ein `ResultSet` zurück.
- `rs.next()` wandert zeilenweise durch das Ergebnis – gibt `false` zurück wenn keine Zeilen mehr vorhanden.
- `rs.getInt("id")` und `rs.getString("vorname")` lesen Spaltenwerte nach Namen.

---

## Aufgabe 4: Fehleranalyse (5 Punkte)

### Fehlerhafte Code

```java
Connection conn = DriverManager.getConnection("localhost:3306/db", "root", "pw");
Statement stmt  = conn.createStatement;
ResultSet rs    = stmt.executeUpdate("SELECT * FROM person");
```

### Musterlösung

**Fehler 1** (2 Punkte): Die JDBC-URL ist ungültig – fehlendes `jdbc:mysql://`  
```java
// Falsch:
"localhost:3306/db"
// Richtig:
"jdbc:mysql://localhost:3306/db"
```

**Fehler 2** (1 Punkt): `createStatement` ist eine Methode und braucht `()`:  
```java
// Falsch:
conn.createStatement
// Richtig:
conn.createStatement()
```

**Fehler 3** (2 Punkte): `executeUpdate()` ist für INSERT/UPDATE/DELETE und gibt `int` zurück, nicht `ResultSet`. Für SELECT muss `executeQuery()` verwendet werden:  
```java
// Falsch:
ResultSet rs = stmt.executeUpdate("SELECT ...");
// Richtig:
ResultSet rs = stmt.executeQuery("SELECT ...");
```

---

## Aufgabe 5: Kurzfragen (5 Punkte)

### Musterlösungen

**1. Was bedeutet `try-with-resources`?**  
Ressourcen, die `AutoCloseable` implementieren (z.B. `Connection`, `Statement`, `ResultSet`), werden am Ende des try-Blocks automatisch geschlossen – auch bei Ausnahmen. Verhindert Ressourcen-Lecks.  
*(1 Punkt)*

**2. Was gibt `executeQuery()` zurück?**  
Ein `ResultSet`-Objekt mit den Ergebniszeilen einer SELECT-Abfrage.  
*(1 Punkt)*

**3. Was gibt `executeUpdate()` zurück?**  
Eine `int`-Zahl: die Anzahl der betroffenen Zeilen (z.B. 1 bei einem erfolgreichen INSERT).  
*(1 Punkt)*

**4. Warum darf man Passwörter nicht im Quellcode speichern?**  
Der Quellcode wird in Repositories (z.B. GitHub) hochgeladen und ist dann öffentlich lesbar. Außerdem schwer zu ändern ohne neuen Build. Besser: Konfigurationsdatei (außerhalb des Repos) oder Umgebungsvariablen.  
*(1 Punkt)*

**5. Welche Maven-Abhängigkeit wird für MySQL benötigt?**  
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```
*(1 Punkt)*
