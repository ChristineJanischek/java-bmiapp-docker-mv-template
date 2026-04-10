# Kurztest: Sichere DB-Anbindung 3 - LOESUNG

**Zeit: 25 Min | Punkte: 30**

---

## Aufgabe 1: Transaktion korrekt abschliessen (10 Punkte)

### Musterloesung

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

Moegliches Problem ohne diesen Mechanismus:

Die Person wird gespeichert, die Messung aber nicht. Dadurch entsteht ein inkonsistenter Teilzustand.

### Punktehinweis

- `commit`, `rollback`, `setAutoCommit(true)` korrekt: 6 Punkte
- plausibles Konsistenzproblem genannt: 4 Punkte

---

## Aufgabe 2: Sichere Fehlerkommunikation in der UI (10 Punkte)

### Musterloesung

Sicherere Meldung: **B**

Moegliche Begruendung:

Meldung B gibt keine internen Details ueber Tabellen, IDs oder Implementierung preis. Solche Details koennen Angreifern helfen und gehoeren nur ins interne Logging.

### Punktehinweis

- richtige Auswahl: 4 Punkte
- Sicherheitsbegruendung ohne Interna: 6 Punkte

---

## Aufgabe 3: Audit-Eintrag bewerten (10 Punkte)

### Musterloesung

a) Sicherere Logzeile: **2**

b) Moegliche Gruende:

1. Es werden keine Geheimnisse (z. B. Passwort) im Klartext gespeichert.
2. Die Zeile ist trotzdem nachvollziehbar (Aktion, Status, Zeitstempel).

c) Moegliche Ergaenzung:

`REQUEST_ID=R-2026-04-10-091500-17`

### Punktehinweis

- richtige Auswahl: 3 Punkte
- zwei tragfaehige Gruende: 4 Punkte
- sinnvolle Zusatzinformation: 3 Punkte
