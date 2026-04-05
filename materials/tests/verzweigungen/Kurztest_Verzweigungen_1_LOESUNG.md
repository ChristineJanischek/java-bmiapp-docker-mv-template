# Kurztest: Verzweigungen 1 - if/else und switch - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: if/else (4 Punkte)

### Aufgabenstellung

```java
public String status(int punkte) {
    // ab 50: bestanden
    // sonst: nicht bestanden
    
    
}
```

### Musterloesung

```java
public String status(int punkte) {
    if (punkte >= 50) {
        return "bestanden";
    } else {
        return "nicht bestanden";
    }
}
```

### Erlaeuterung

Ab 50 Punkten gilt die Bedingung als wahr, sonst wird der `else`-Zweig ausgefuehrt.

---

## Aufgabe 2: else-if Kette (4 Punkte)

### Aufgabenstellung

Ergaenze:

```java
if (note == 1) {
    return "sehr gut";
} else if (note == 2) {
    return "gut";
} else if (note == 3) {
    // TODO
} else {
    // TODO
}
```

### Musterloesung

```java
if (note == 1) {
    return "sehr gut";
} else if (note == 2) {
    return "gut";
} else if (note == 3) {
    return "befriedigend";
} else {
    return "ausreichend oder schlechter";
}
```

### Erlaeuterung

Die Bedingungen werden von oben nach unten geprueft; der erste passende Zweig gewinnt.

---

## Aufgabe 3: switch (5 Punkte)

### Aufgabenstellung

```java
public String tagName(String code) {
    switch (code) {
        case "MO": return "Montag";
        case "DI": return "Dienstag";
        // TODO: MI, DO, FR
        default: return "unbekannt";
    }
}
```

### Musterloesung

```java
public String tagName(String code) {
    switch (code) {
        case "MO": return "Montag";
        case "DI": return "Dienstag";
        case "MI": return "Mittwoch";
        case "DO": return "Donnerstag";
        case "FR": return "Freitag";
        default: return "unbekannt";
    }
}
```

### Erlaeuterung

`switch` ist gut fuer mehrere feste Vergleichswerte derselben Variablen.

---

## Aufgabe 4: Code-Analyse (5 Punkte)

### Aufgabenstellung

```java
int x = 10;
if (x > 5 && x < 20) {
    System.out.println("OK");
} else {
    System.out.println("NO");
}
```

Was wird ausgegeben? _______________________________________________

### Musterloesung

Ausgabe: `OK`

### Erlaeuterung

Beide Teilbedingungen sind wahr (`10 > 5` und `10 < 20`).

---

## Aufgabe 5: Ternary (4 Punkte)

### Aufgabenstellung

```java
int alter = 17;
String status = ___________________________;
```

Erstelle den Ausdruck fuer "volljaehrig" oder "minderjaehrig".

### Musterloesung

```java
String status = (alter >= 18) ? "volljaehrig" : "minderjaehrig";
```

### Erlaeuterung

Der ternary Operator schreibt ein kurzes if/else als Ausdruck.

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
if (a = b) {
    System.out.println("gleich");
}
```

Was ist falsch und wie lautet die korrekte Version?

_____________________________________________________________________

### Musterloesung

`=` ist Zuweisung, nicht Vergleich.

Korrekt:

```java
if (a == b) {
    System.out.println("gleich");
}
```

### Erlaeuterung

Fuer Wertvergleich von primitiven Typen wird `==` verwendet.

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
