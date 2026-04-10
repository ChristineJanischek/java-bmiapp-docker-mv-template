# Kurztest: Sichere Datenverarbeitung 1 - LOESUNG

**Zeit: 20 Min | Punkte: 24**

---

## Aufgabe 1: Whitelist-Validierung anwenden (8 Punkte)

### Musterloesung

```java
public static boolean isValidName(String name) {
    if (name == null || name.trim().isEmpty()) {
        return false;
    }
    if (name.length() > 50) {
        return false;
    }
    return name.matches("^[a-zA-ZaeoeuessAEOEUE\\-\\s]+$");
}
```

Moegliche Erklaerung:

Ein Whitelist-Ansatz erlaubt nur Zeichen, die fachlich wirklich benoetigt werden. Dadurch werden unerwartete oder schaedliche Eingaben grundsaetzlich ausgeschlossen, statt nur bekannte Problemfaelle zu verbieten.

### Punktehinweis

- Laengenpruefung korrekt: 2 Punkte
- regulaerer Ausdruck als Whitelist: 4 Punkte
- fachliche Begruendung: 2 Punkte

---

## Aufgabe 2: Log-Nachricht absichern (8 Punkte)

### Musterloesung

Bereinigte Nachricht:

```text
ACTION=LOGIN | user=max | password=*** | STATUS=ERROR
```

Moegliche Begruendung:

Logs muessen fuer Fehlersuche nutzbar bleiben, duerfen aber keine Geheimnisse preisgeben. Ein Klartext-Passwort waere selbst dann kritisch, wenn es nur intern protokolliert wird.

### Punktehinweis

- Passwort korrekt maskiert: 4 Punkte
- Begruendung Datenschutz/Sicherheit: 4 Punkte

---

## Aufgabe 3: Ungueltige Daten vor dem Speichern erkennen (8 Punkte)

### Musterloesung

```java
@Test
void saveToFile_rejectsInvalidPersonData() {
    BmiManager manager = new BmiManager();
    Person invalidPerson = new Person("Max123", "Muster", 25, "Mann", "max@example.de");
    manager.getPeople().add(invalidPerson);

    assertThrows(IllegalStateException.class, () ->
        manager.saveToFile("test.json")
    );
}
```

Moegliche Erklaerung:

Sicherheit darf nicht nur an der Oberflaeche haengen, weil Daten auch aus anderen Quellen stammen koennen. Vor dem dauerhaften Speichern muss deshalb nochmals fachlich geprueft werden.

### Punktehinweis

- ungueltiger Testdatensatz passend gewaehlt: 3 Punkte
- erwartete Exception korrekt: 3 Punkte
- Begruendung der zweiten Sicherheitspruefung: 2 Punkte