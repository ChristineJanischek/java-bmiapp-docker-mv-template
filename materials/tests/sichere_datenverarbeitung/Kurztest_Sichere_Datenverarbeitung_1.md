# Kurztest: Sichere Datenverarbeitung 1 - Validierung, Logging, Datenschutz

**Klasse:** _________________  **Datum:** _________________  **Zeit: 20 Min | Punkte: 24**

> **Erlaubte Hilfsmittel:** Unterlagen zu Version 6, Secure-Coding-Merkblatt

---

## Aufgabe 1: Whitelist-Validierung anwenden (8 Punkte)

Ergaenze die Pruefung so, dass nur fachlich zulaessige Namen akzeptiert werden.

```java
public static boolean isValidName(String name) {
    if (name == null || name.trim().isEmpty()) {
        return false;
    }
    if (name.length() > ___) {
        return false;
    }
    return name.matches("_______________________________");
}
```

Erklaere anschliessend in 1 bis 2 Saetzen, warum hier ein Whitelist-Ansatz sicherer ist als eine Blacklist.

_________________________________________________________________

_________________________________________________________________

---

## Aufgabe 2: Log-Nachricht absichern (8 Punkte)

Eine Log-Nachricht enthaelt sensible Daten und soll vor dem Schreiben bereinigt werden.

Gegeben:

```java
String raw = "ACTION=LOGIN | user=max | password=TopSecret123 | STATUS=ERROR";
```

Schreibe die erwartete bereinigte Log-Nachricht auf und begruende kurz, warum das wichtig ist.

Bereinigte Nachricht:

_________________________________________________________________

Begruendung:

_________________________________________________________________

_________________________________________________________________

---

## Aufgabe 3: Ungueltige Daten vor dem Speichern erkennen (8 Punkte)

Vervollstaendige den Test so, dass er prueft, ob ungueltige Personendaten vor dem Speichern erkannt werden.

```java
@Test
void saveToFile_rejectsInvalidPersonData() {
    BmiManager manager = new BmiManager();
    Person invalidPerson = new Person("________", "Muster", 25, "Mann", "max@example.de");
    manager.getPeople().add(invalidPerson);

    assertThrows(____________________.class, () ->
        manager.saveToFile("test.json")
    );
}
```

Erklaere in einem Satz, warum diese Pruefung nicht nur bei der GUI-Eingabe stattfinden darf.

_________________________________________________________________