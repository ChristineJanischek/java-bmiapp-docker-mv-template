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

### Musterloesung

```java
public int parseZahl(String input) {
    try {
        return Integer.parseInt(input);
    } catch (NumberFormatException e) {
        return -1;
    }
}
```

### Erlaeuterung

Ungueltige Zahltexte loesen `NumberFormatException` aus und werden hier mit `-1` abgefangen.

---

## Aufgabe 2: Checked vs. Unchecked (4 Punkte)

### Aufgabenstellung

Markiere richtig (C = checked, U = unchecked):

- IOException: ( )
- NullPointerException: ( )
- FileNotFoundException: ( )
- IllegalArgumentException: ( )

### Musterloesung

- IOException: (C)
- NullPointerException: (U)
- FileNotFoundException: (C)
- IllegalArgumentException: (U)

### Erlaeuterung

Checked Exceptions muessen behandelt oder mit `throws` deklariert werden; RuntimeExceptions nicht.

---

## Aufgabe 3: throws (5 Punkte)

### Aufgabenstellung

Schreibe eine Methode, die eine Datei einliest und IOException weitergibt:

```java
public List<String> ladeDatei(String datei) throws IOException {
    // TODO
}
```

### Musterloesung

```java
public List<String> ladeDatei(String datei) throws IOException {
    return Files.readAllLines(Paths.get(datei));
}
```

### Erlaeuterung

Die Methode faengt nicht selbst ab, sondern gibt `IOException` an den Aufrufer weiter.

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

### Musterloesung

```java
public void pruefeBestand(int menge) throws BestandException {
    if (menge < 0) {
        throw new BestandException("Bestand darf nicht negativ sein");
    }
}

public class BestandException extends Exception {
    public BestandException(String message) {
        super(message);
    }
}
```

### Erlaeuterung

Eine eigene Checked Exception transportiert fachliche Fehler eindeutig.

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

### Musterloesung

Ausgabe:

```text
Fehler
Cleanup
```

### Erlaeuterung

Nach dem `catch` wird `finally` immer ausgefuehrt (ausser bei hartem JVM-Abbruch).

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

### Musterloesung

`IOException` ist Unterklasse von `Exception` und wird durch den ersten `catch` bereits abgefangen. Der zweite `catch` ist dadurch unerreichbar.

Korrektur: spezifischen `catch` zuerst.

```java
try {
    Files.readAllLines(Paths.get("daten.txt"));
} catch (IOException e) {
    // spezifisch
} catch (Exception e) {
    // allgemein
}
```

### Erlaeuterung

Catch-Bloecke muessen von spezifisch nach allgemein sortiert sein.

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
