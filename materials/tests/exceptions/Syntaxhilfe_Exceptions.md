# Syntaxhilfe: Exceptions (Java)

**Ziel:** Fehler sicher behandeln.

---

## try-catch

```java
try {
    int x = Integer.parseInt("42");
} catch (NumberFormatException e) {
    System.out.println("Falsche Zahl");
}
```

---

## try-with-resources

```java
try (BufferedReader reader = new BufferedReader(new FileReader("daten.txt"))) {
    String zeile = reader.readLine();
}
```

---

## throw / throws

```java
public void pruefeAlter(int alter) throws IllegalArgumentException {
    if (alter < 0) {
        throw new IllegalArgumentException("Alter ungueltig");
    }
}
```

---

## Eigene Exception

```java
public class DatenFehlerException extends Exception {
    public DatenFehlerException(String msg) {
        super(msg);
    }
}
```

---

## Tipps

- Checked Exceptions: muessen behandelt oder weitergegeben werden.
- Unchecked Exceptions: RuntimeException.
- finally laeuft immer (auch bei Exception).
