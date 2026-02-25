# Kurztest: Exceptions 2 - Ressourcen und Weitergabe

**Klasse:** _________________  **Datum:** _________________  **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: try-with-resources (4 Punkte)

Vervollstaendige:

```java
public int zeilenZaehlen(String datei) throws IOException {
    int count = 0;
    // TODO: try-with-resources
    
    return count;
}
```

---

## Aufgabe 2: Multi-catch (4 Punkte)

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

---

## Aufgabe 3: Exception weitergeben (5 Punkte)

```java
public void speichere(String pfad, String text) throws IOException {
    // TODO: text in Datei schreiben
}
```

---

## Aufgabe 4: RuntimeException (5 Punkte)

```java
public void pruefeRolle(String rolle) {
    if (rolle == null || rolle.isBlank()) {
        // TODO: RuntimeException werfen
    }
}
```

---

## Aufgabe 5: finally und return (4 Punkte)

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

---

## Aufgabe 6: Fehlersuche (3 Punkte)

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
