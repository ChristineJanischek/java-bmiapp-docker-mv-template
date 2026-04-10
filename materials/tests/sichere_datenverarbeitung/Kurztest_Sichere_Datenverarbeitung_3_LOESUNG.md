# Kurztest: Sichere Datenverarbeitung 3 - LOESUNG

**Zeit: 20 Min | Punkte: 24**

---

## Aufgabe 1: Datenminimierung bewerten (8 Punkte)

### Musterloesung

Notwendig:

- Vorname
- Nachname
- Groesse
- Gewicht

Nicht notwendig:

- Lieblingsfarbe
- Kontonummer

Moegliche Begruendung:

Die Kontonummer ist fuer BMI-Berechnungen fachlich unnoetig und besonders sensibel. Solche Daten sollen ohne echten Zweck nicht erhoben oder gespeichert werden.

### Punktehinweis

- sinnvolle Zuordnung notwendig/nicht notwendig: 6 Punkte
- tragfaehige Begruendung: 2 Punkte

---

## Aufgabe 2: Loeschfunktion absichern (8 Punkte)

### Musterloesung

```java
public boolean deletePersonData(String personId) {
    if (personId == null || personId.isBlank()) {
        return false;
    }

    boolean removed = manager.removePersonById(personId);
    if (removed) {
        SecureLogger.logAction("PERSON_DELETED", personId, "Personendaten geloescht");
    } else {
        SecureLogger.logSecurityEvent("DELETE_FAILED", personId, "personId nicht gefunden");
    }
    return removed;
}
```

### Punktehinweis

- Aktion bei erfolgreichem Loeschen passend: 4 Punkte
- Sicherheitsereignis bei Fehlschlag sinnvoll: 4 Punkte

---

## Aufgabe 3: Datenschutzgerechte Meldung formulieren (8 Punkte)

### Musterloesung

Moegliche Nutzerinformation:

"Die Daten konnten derzeit nicht gespeichert werden. Bitte versuche es spaeter erneut oder melde dich bei der Lehrkraft."

Moegliche interne Log-Infos:

1. Exception-Typ und Stacktrace
2. technische Kontextdaten (z. B. Methode, Timestamp, Nutzer-ID)

### Punktehinweis

- sichere Nutzerinformation ohne Interna: 4 Punkte
- zwei passende interne Log-Informationen: 4 Punkte
