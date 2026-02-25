# Kurztest: Exceptions 1 - Grundlagen - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: try-catch (4 Punkte)

### Aufgabenstellung

Vervollstaendige:

```java
public int parseZahl(String input) {
    try {
        // TODO
        
    } catch (NumberFormatException e) {
        return -1;
    }
}
```

---

## Aufgabe 2: Checked vs. Unchecked (4 Punkte)

### Aufgabenstellung

Markiere richtig (C = checked, U = unchecked):

- IOException: ( )
- NullPointerException: ( )
- FileNotFoundException: ( )
- IllegalArgumentException: ( )

---

## Aufgabe 3: throws (5 Punkte)

### Aufgabenstellung

Schreibe eine Methode, die eine Datei einliest und IOException weitergibt:

```java
public List<String> ladeDatei(String datei) throws IOException {
    // TODO
}
```

---

## Aufgabe 4: Eigene Exception (5 Punkte)

### Aufgabenstellung

```java
public void pruefeBestand(int menge) throws BestandException {
    if (menge < 0) {
        // TODO: eigene Exception werfen
    }
}
```

Schreibe auch die Exception-Klasse:

```java
public class BestandException extends Exception {
    // TODO
}
```

---

## Aufgabe 5: finally (4 Punkte)

### Aufgabenstellung

```java
try {
    int x = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Fehler");
} finally {
    System.out.println("Cleanup");
}
```

Was wird ausgegeben?

_____________________________________________________________________

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
try {
    Files.readAllLines(Paths.get("daten.txt"));
} catch (Exception e) {
    // ...
} catch (IOException e) {
    // ...
}
```

Was ist der Fehler und wie behebst du ihn?

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

