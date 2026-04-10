# Kurztest: Sichere DB-Anbindung 2 - LOESUNG

**Zeit: 25 Min | Punkte: 30**

---

## Aufgabe 1: Least-Privilege begruenden (10 Punkte)

### Musterloesung

a) Moegliche Risiken:

1. Bei kompromittiertem Account koennen Tabellen geloescht oder stark veraendert werden.
2. Ein Fehler in der Anwendung hat durch hohe Rechte einen viel groesseren Schaden.

b) Moegliches Minimal-Rechtepaket:

`SELECT, INSERT, UPDATE, DELETE` nur auf benoetigten Tabellen (`person`, `messung`).

c) Moegliche Begruendung:

Weniger Rechte reduzieren die Angriffsoberflaeche und begrenzen den maximalen Schaden im Fehler- oder Angriffsfall.

### Punktehinweis

- zwei passende Risiken: 4 Punkte
- sinnvolles Minimal-Rechtepaket: 3 Punkte
- Sicherheitsbegruendung: 3 Punkte

---

## Aufgabe 2: Unsichere SQL-Aenderung absichern (10 Punkte)

### Musterloesung

```java
public boolean updateEmail(Connection conn, int personId, String newEmail) throws SQLException {
    String sql = "UPDATE person SET email = ? WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, newEmail);
        ps.setInt(2, personId);
        return ps.executeUpdate() > 0;
    }
}
```

Moegliche Erklaerung:

Auch bei UPDATE koennen manipulierte Eingaben in den SQL-Befehl eingeschleust werden, wenn Werte unsicher konkatenert werden.

### Punktehinweis

- SQL mit Platzhaltern korrekt: 4 Punkte
- Setter-Aufrufe korrekt: 4 Punkte
- Injection-Erklaerung passend: 2 Punkte

---

## Aufgabe 3: Verbindungsaufbau robuster machen (10 Punkte)

### Musterloesung

Moegliches Beispiel:

```text
jdbc:mysql://localhost:3306/bmiapp?useSSL=true&serverTimezone=Europe/Berlin
```

Moegliche Risiken:

1. Ohne passende SSL-Konfiguration koennen Daten auf dem Transportweg leichter abgegriffen werden.
2. Falsche Zeitzonen-/Timeout-Konfiguration kann zu inkonsistenten Daten oder instabilen Verbindungen fuehren.

### Punktehinweis

- zwei sinnvolle URL-Parameter: 6 Punkte
- zwei fachlich passende Risiken: 4 Punkte
