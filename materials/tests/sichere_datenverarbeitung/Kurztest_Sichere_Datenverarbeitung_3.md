# Kurztest: Sichere Datenverarbeitung 3 - Datenschutz, Datenminimierung, Loeschbarkeit

**Klasse:** _________________  **Datum:** _________________  **Zeit: 20 Min | Punkte: 24**

> **Erlaubte Hilfsmittel:** Unterlagen zu Version 6, DSGVO-Notizen aus dem Unterricht

---

## Aufgabe 1: Datenminimierung bewerten (8 Punkte)

Ordne die Felder in "notwendig" oder "nicht notwendig" fuer die BMI-Funktion ein.

- Vorname
- Nachname
- Groesse
- Gewicht
- Lieblingsfarbe
- Kontonummer

Notwendig:

_________________________________________________________________

Nicht notwendig:

_________________________________________________________________

Begruende kurz eure Entscheidung fuer ein Feld aus "nicht notwendig".

_________________________________________________________________

---

## Aufgabe 2: Loeschfunktion absichern (8 Punkte)

Vervollstaendige den Code so, dass eine Person und deren Daten sicher geloescht werden koennen.

```java
public boolean deletePersonData(String personId) {
    if (personId == null || personId.isBlank()) {
        return false;
    }

    boolean removed = manager.removePersonById(personId);
    if (removed) {
        SecureLogger.logAction("__________________", personId, "Personendaten geloescht");
    } else {
        SecureLogger.logSecurityEvent("DELETE_FAILED", personId, "__________________");
    }
    return removed;
}
```

---

## Aufgabe 3: Datenschutzgerechte Meldung formulieren (8 Punkte)

Formuliere eine geeignete Nutzerinformation fuer einen unerwarteten Fehler beim Speichern.

Vorgabe:

- keine internen Systemdetails
- klar fuer Lernende verstaendlich
- max. 2 Saetze

_________________________________________________________________

_________________________________________________________________

Nenne danach zwei Informationen, die nur ins interne Log gehoeren.

1. _______________________________________________________________
2. _______________________________________________________________
