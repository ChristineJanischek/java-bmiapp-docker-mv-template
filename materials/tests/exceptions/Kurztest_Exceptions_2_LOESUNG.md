# Kurztest: Exceptions 2 - Ressourcen und Weitergabe - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: try-with-resources (4 Punkte)

### Aufgabenstellung

Vervollstaendige:

```java
public int zeilenZaehlen(String datei) throws IOException {
    int count = 0;
    // TODO: try-with-resources
    
    return count;
}
```

### Musterloesung

```java
public int zeilenZaehlen(String datei) throws IOException {
    int count = 0;
    try (BufferedReader reader = Files.newBufferedReader(Paths.get(datei))) {
        while (reader.readLine() != null) {
            count++;
        }
    }
    return count;
}
```

### Erlaeuterung

Die Ressource wird automatisch geschlossen, auch wenn ein Fehler auftritt.

---

## Aufgabe 2: Multi-catch (4 Punkte)

### Aufgabenstellung

```java
try {
    int x = Integer.parseInt(input);
    int y = 10 / x;
} catch (NumberFormatException | ArithmeticException e) {
    System.out.println("Fehler");
}
```

Welche zwei Fehler werden hier abgefangen?

1) _____________________
2) _____________________

### Musterloesung

1) `NumberFormatException` bei ungueltiger Zahl in `input`
2) `ArithmeticException` bei Division durch 0

### Erlaeuterung

Multi-catch fasst mehrere passende Exception-Typen in einem Block zusammen.

---

## Aufgabe 3: Exception weitergeben (5 Punkte)

### Aufgabenstellung

```java
public void speichere(String pfad, String text) throws IOException {
    // TODO: text in Datei schreiben
}
```

### Musterloesung

```java
public void speichere(String pfad, String text) throws IOException {
    Files.writeString(Paths.get(pfad), text);
}
```

### Erlaeuterung

Die Methode delegiert das Schreiben und gibt moegliche `IOException` weiter.

---

## Aufgabe 4: RuntimeException (5 Punkte)

### Aufgabenstellung

```java
public void pruefeRolle(String rolle) {
    if (rolle == null || rolle.isBlank()) {
        // TODO: RuntimeException werfen
    }
}
```

### Musterloesung

```java
public void pruefeRolle(String rolle) {
    if (rolle == null || rolle.isBlank()) {
        throw new IllegalArgumentException("Rolle darf nicht leer sein");
    }
}
```

### Erlaeuterung

`IllegalArgumentException` ist passend fuer ungueltige Methodenparameter.

---

## Aufgabe 5: finally und return (4 Punkte)

### Aufgabenstellung

```java
public int test() {
    try {
        return 1;
    } finally {
        return 2;
    }
}
```

Welche Rueckgabe liefert die Methode? ____________

### Musterloesung

Rueckgabe: `2`

### Erlaeuterung

Ein `return` im `finally` ueberschreibt den `return` aus `try` (in der Praxis vermeiden).

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
public void lese(String datei) {
    try (BufferedReader r = new BufferedReader(new FileReader(datei))) {
        r.readLine();
    } catch (IOException e) {
        throw e;  // FEHLER
    }
}
```

Was ist falsch und wie behebst du es?

_____________________________________________________________________

### Musterloesung

Fehler: `IOException` wird als checked Exception erneut geworfen, aber die Methode deklariert kein `throws IOException`.

Korrekturmoeglichkeiten:

```java
public void lese(String datei) throws IOException {
    try (BufferedReader r = new BufferedReader(new FileReader(datei))) {
        r.readLine();
    }
}
```

oder in RuntimeException umwandeln.

### Erlaeuterung

Checked Exceptions muessen entweder behandelt oder in der Signatur deklariert werden.

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
