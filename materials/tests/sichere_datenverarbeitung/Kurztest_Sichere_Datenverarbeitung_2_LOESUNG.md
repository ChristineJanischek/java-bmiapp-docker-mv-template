# Kurztest: Sichere Datenverarbeitung 2 - LOESUNG

**Zeit: 20 Min | Punkte: 24**

---

## Aufgabe 1: Sichere Fehlerbehandlung formulieren (8 Punkte)

### Musterloesung

```java
try {
    manager.addMeasurement(personId, height, weight);
} catch (Exception ex) {
    SecureLogger.logError("ADD_MEASUREMENT_UI", personId, ex);
    JOptionPane.showMessageDialog(this,
        "Ein Fehler ist aufgetreten. Bitte erneut versuchen.",
        "Fehler",
        JOptionPane.ERROR_MESSAGE);
}
```

Moegliche Begruendung:

Technische Details aus Exceptions koennen interne Strukturen offenlegen. Deshalb werden Details nur intern protokolliert, waehrend Nutzer eine sichere, allgemein gehaltene Meldung sehen.

### Punktehinweis

- `logError(...)` korrekt: 3 Punkte
- generische, sichere Nutzernachricht: 3 Punkte
- Begruendung zur Trennung intern/extern: 2 Punkte

---

## Aufgabe 2: Audit-Log korrekt aufbauen (8 Punkte)

### Musterloesung

```java
SecureLogger.logAction(
    "MEASUREMENT_ADDED",
    "U001",
    "Messung hinzugefuegt: id=M321"
);
```

Moegliche Angaben:

1. Aktion (z. B. `MEASUREMENT_ADDED`)
2. Zeitpunkt und/oder Status (`SUCCESS`/`ERROR`)

### Punktehinweis

- Aktionsname korrekt: 4 Punkte
- zwei sinnvolle Pflichtangaben genannt: 4 Punkte

---

## Aufgabe 3: Plausibilitaetsgrenzen pruefen (8 Punkte)

### Musterloesung

```java
@Test
void isValidWeight_checksBoundaries() {
    assertTrue(InputValidator.isValidWeight(30));
    assertTrue(InputValidator.isValidWeight(300));
    assertFalse(InputValidator.isValidWeight(29.9));
    assertFalse(InputValidator.isValidWeight(300.1));
}
```

Moegliche Erklaerung:

Gerade an Grenzen passieren in der Praxis viele Fehler. Grenzwerttests sichern, dass die erlaubten Minimal- und Maximalwerte korrekt behandelt werden.

### Punktehinweis

- gueltige Grenzwerte korrekt: 4 Punkte
- ungueltige Nachbarwerte korrekt: 2 Punkte
- Begruendung zu Grenzwerten: 2 Punkte
