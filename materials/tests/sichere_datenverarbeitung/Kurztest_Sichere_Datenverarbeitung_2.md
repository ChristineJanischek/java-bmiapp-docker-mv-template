# Kurztest: Sichere Datenverarbeitung 2 - Fehlerbehandlung, Audit-Logging, Validierung

**Klasse:** _________________  **Datum:** _________________  **Zeit: 20 Min | Punkte: 24**

> **Erlaubte Hilfsmittel:** Unterlagen zu Version 6, Exception-Handling-Merkblatt

---

## Aufgabe 1: Sichere Fehlerbehandlung formulieren (8 Punkte)

Vervollstaendige den Catch-Block so, dass intern detailliert geloggt wird und extern nur eine generische Meldung erscheint.

```java
try {
    manager.addMeasurement(personId, height, weight);
} catch (Exception ex) {
    SecureLogger.______________("ADD_MEASUREMENT_UI", personId, ex);
    JOptionPane.showMessageDialog(this,
        "_______________________________________________",
        "Fehler",
        JOptionPane.ERROR_MESSAGE);
}
```

Begruende in 1 bis 2 Saetzen, warum diese Trennung sicherheitsrelevant ist.

_________________________________________________________________

_________________________________________________________________

---

## Aufgabe 2: Audit-Log korrekt aufbauen (8 Punkte)

Ergaenze den Aufruf fuer ein erfolgreiches Hinzufuegen einer Messung.

```java
SecureLogger.logAction(
    "___________________",
    "U001",
    "Messung hinzugefuegt: id=M321"
);
```

Nenne zwei Angaben, die laut Version-6-Thema in einem Audit-Log enthalten sein sollen.

1. _______________________________________________________________
2. _______________________________________________________________

---

## Aufgabe 3: Plausibilitaetsgrenzen pruefen (8 Punkte)

Vervollstaendige den Test fuer die Gewichtsvalidierung.

```java
@Test
void isValidWeight_checksBoundaries() {
    assertTrue(InputValidator.isValidWeight(___));
    assertTrue(InputValidator.isValidWeight(___));
    assertFalse(InputValidator.isValidWeight(___));
    assertFalse(InputValidator.isValidWeight(___));
}
```

Erklaere kurz, warum Grenzwerttests bei Sicherheitsvalidierung wichtig sind.

_________________________________________________________________
