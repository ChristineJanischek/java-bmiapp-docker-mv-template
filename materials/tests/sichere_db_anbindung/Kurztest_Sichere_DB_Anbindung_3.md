# Kurztest: Sichere DB-Anbindung 3 - Transaktionen, Fehlerkommunikation, Audit

**Klasse:** _________________  **Datum:** _________________  **Zeit: 25 Min | Punkte: 30**

> **Erlaubte Hilfsmittel:** Unterlagen zu Version 7, Transaktionsbeispiele

---

## Aufgabe 1: Transaktion korrekt abschliessen (10 Punkte)

Vervollstaendige den Ablauf so, dass ein konsistenter Zustand garantiert wird.

```java
conn.setAutoCommit(false);
try {
    int personId = personRepository.insert(conn, person);
    messungRepository.insert(conn, personId, messung);
    conn.___________();
} catch (SQLException ex) {
    conn.___________();
    throw ex;
} finally {
    conn.____________________(true);
}
```

Nenne ein konkretes Problem, das ohne diesen Mechanismus entstehen kann.

_________________________________________________________________

---

## Aufgabe 2: Sichere Fehlerkommunikation in der UI (10 Punkte)

Welche der beiden Meldungen ist fuer Endnutzer sicherer? Begruende kurz.

A) "SQLException in DBConnector.save(): duplicate key on person.id=42 in schema bmiapp_dev"

B) "Daten konnten nicht gespeichert werden. Bitte erneut versuchen."

Sicherere Meldung: ______

Begruendung:

_________________________________________________________________

_________________________________________________________________

---

## Aufgabe 3: Audit-Eintrag bewerten (10 Punkte)

Gegeben sind zwei Logzeilen:

1. `ACTION=DB_LOGIN_FAILED | USER=app | password=TopSecret123 | STATUS=ERROR`
2. `ACTION=DB_LOGIN_FAILED | USER=app | STATUS=ERROR | TIMESTAMP=2026-04-10T09:15:00Z`

a) Welche Logzeile ist sicherer? ______ (3 Punkte)

b) Nenne zwei Gruende. (4 Punkte)

1. _______________________________________________________________
2. _______________________________________________________________

c) Ergaenze eine weitere sinnvolle Information fuer die sichere Logzeile (z. B. Korrelations-ID). (3 Punkte)

_________________________________________________________________
