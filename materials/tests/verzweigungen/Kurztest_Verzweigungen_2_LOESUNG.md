# Kurztest: Verzweigungen 2 - Logik und Guard Clauses - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Bereichspruefung (4 Punkte)

### Aufgabenstellung

```java
public boolean istGueltig(int alter) {
    // gueltig: 0..120
    
    
}
```

---

## Aufgabe 2: Guard Clause (4 Punkte)

### Aufgabenstellung

Vervollstaendige:

```java
public void verarbeite(String input) {
    // Wenn input null oder leer -> sofort return
    
    
    System.out.println(input.trim());
}
```

---

## Aufgabe 3: switch mit Fall-Through (5 Punkte)

### Aufgabenstellung

```java
public String gruppe(int note) {
    switch (note) {
        case 1:
        case 2:
            return "gut";
        case 3:
            return "mittel";
        // TODO: 4 und 5 -> "schwach"
        default:
            return "ungueltig";
    }
}
```

---

## Aufgabe 4: Code-Analyse (5 Punkte)

### Aufgabenstellung

```java
boolean admin = false;
boolean aktiv = true;

if (admin || aktiv) {
    System.out.println("Zugriff");
} else {
    System.out.println("Kein Zugriff");
}
```

Was wird ausgegeben? _______________________________________________

---

## Aufgabe 5: Ternary vs if (4 Punkte)

### Aufgabenstellung

Schreibe als if/else:

```java
String r = (x > 0) ? "positiv" : "nicht positiv";
```

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
if (x > 5 && x < 10 || x == 20) {
    // ...
}
```

Warum kann die Prioritaet hier problematisch sein, und wie machst du es eindeutig?

_____________________________________________________________________

---

**Viel Erfolg!**

| Aufgabe | Punkte |
|---------|--------|
| 1 | 4 |
| 2 | 4 |
| 3 | 5 |
| 4 | 5 |
| 5 | 4 |
| 6 | 3 |
| **Gesamt** | **25** |

