# Kurztest: Exceptions 3 - Validierung und Design - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Validierung mit Exception (4 Punkte)

### Aufgabenstellung

```java
public void setPort(int port) {
    // Ports 1..65535
    if (port < 1 || port > 65535) {
        // TODO
    }
}
```

---

## Aufgabe 2: Checked oder Unchecked? (4 Punkte)

### Aufgabenstellung

Entscheide fuer jede Situation und begruende kurz:

1) Fehlerhafte Eingabe durch Benutzer: _____________________________
2) Datei nicht gefunden: ____________________________________________

---

## Aufgabe 3: Eigene Checked Exception (5 Punkte)

### Aufgabenstellung

```java
public void ladeKonfig(String datei) throws KonfigException {
    // TODO: wenn Datei leer ist -> KonfigException
}

public class KonfigException extends Exception {
    // TODO
}
```

---

## Aufgabe 4: Code-Analyse (5 Punkte)

### Aufgabenstellung

```java
public int parse(String s) {
    try {
        return Integer.parseInt(s);
    } catch (NumberFormatException e) {
        return 0;
    }
}
```

a) Was ist Nachteil dieser Loesung? ________________________________

b) Wie koennte man es verbessern? _________________________________

---

## Aufgabe 5: Logging von Fehlern (4 Punkte)

### Aufgabenstellung

```java
public void verarbeite(List<String> daten) {
    List<String> fehler = new ArrayList<>();

    for (String d : daten) {
        try {
            Integer.parseInt(d);
        } catch (NumberFormatException e) {
            // TODO: Fehler sammeln
        }
    }

    // TODO: Fehler ausgeben
}
```

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
try {
    int[] a = new int[2];
    System.out.println(a[5]);
} catch (RuntimeException e) {
    System.out.println("Fehler");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Index");
}
```

Warum ist das falsch?

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

