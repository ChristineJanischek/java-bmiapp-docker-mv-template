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

### Musterloesung

```java
public boolean istGueltig(int alter) {
    return alter >= 0 && alter <= 120;
}
```

### Erlaeuterung

Nur Werte innerhalb des Intervalls inklusive Grenzen sind gueltig.

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

### Musterloesung

```java
public void verarbeite(String input) {
    if (input == null || input.isBlank()) {
        return;
    }
    System.out.println(input.trim());
}
```

### Erlaeuterung

Guard Clauses beenden ungueltige Faelle frueh und reduzieren Verschachtelung.

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

### Musterloesung

```java
public String gruppe(int note) {
    switch (note) {
        case 1:
        case 2:
            return "gut";
        case 3:
            return "mittel";
        case 4:
        case 5:
            return "schwach";
        default:
            return "ungueltig";
    }
}
```

### Erlaeuterung

Mehrere `case`-Labels ohne Code dazwischen nutzen bewusst Fall-Through.

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

### Musterloesung

Ausgabe: `Zugriff`

### Erlaeuterung

Bei `||` reicht eine wahre Teilbedingung; `aktiv` ist `true`.

---

## Aufgabe 5: Ternary vs if (4 Punkte)

### Aufgabenstellung

Schreibe als if/else:

```java
String r = (x > 0) ? "positiv" : "nicht positiv";
```

### Musterloesung

```java
String r;
if (x > 0) {
    r = "positiv";
} else {
    r = "nicht positiv";
}
```

### Erlaeuterung

Funktional identisch zum ternary Ausdruck, aber laenger ausgeschrieben.

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

### Musterloesung

Ohne Klammern ist die Lesbarkeit schlecht; `&&` wird zwar vor `||` ausgewertet, aber das ist fehleranfaellig.

Eindeutig z. B.:

```java
if ((x > 5 && x < 10) || x == 20) {
    // ...
}
```

### Erlaeuterung

Klammern dokumentieren die gewollte Logik explizit und verhindern Missverstaendnisse.

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
