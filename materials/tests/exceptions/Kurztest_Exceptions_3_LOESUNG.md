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

### Musterloesung

```java
public void setPort(int port) {
    if (port < 1 || port > 65535) {
        throw new IllegalArgumentException("Port muss zwischen 1 und 65535 liegen");
    }
}
```

### Erlaeuterung

Ungueltige Parameter werden sofort mit einer klaren Fehlermeldung abgewiesen.

---

## Aufgabe 2: Checked oder Unchecked? (4 Punkte)

### Aufgabenstellung

Entscheide fuer jede Situation und begruende kurz:

1) Fehlerhafte Eingabe durch Benutzer: _____________________________
2) Datei nicht gefunden: ____________________________________________

### Musterloesung

1) Eher **unchecked** (z. B. `IllegalArgumentException`), weil es meist ein Validierungs-/Nutzungsfehler ist.
2) **checked** (`FileNotFoundException`/`IOException`), weil externer I/O-Fehler behandelt werden sollte.

### Erlaeuterung

Anwendungslogikfehler und Umgebungs-/I/O-Fehler werden bewusst unterschiedlich modelliert.

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

### Musterloesung

```java
public void ladeKonfig(String datei) throws KonfigException {
    if (datei == null || datei.isBlank()) {
        throw new KonfigException("Konfigurationsdatei ist leer");
    }
}

public class KonfigException extends Exception {
    public KonfigException(String message) {
        super(message);
    }
}
```

### Erlaeuterung

Eine eigene checked Exception macht den fachlichen Fehlerfall explizit fuer Aufrufer.

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

### Musterloesung

a) `0` kann sowohl gueltiger Wert als auch Fehlerersatz sein; Fehler werden dadurch verschleiert.

b) Verbesserung: Exception weitergeben, oder z. B. `OptionalInt`/separate Fehlerbehandlung verwenden.

### Erlaeuterung

Fehler und fachlich gueltige Werte sollten klar unterscheidbar bleiben.

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

### Musterloesung

```java
public void verarbeite(List<String> daten) {
    List<String> fehler = new ArrayList<>();

    for (String d : daten) {
        try {
            Integer.parseInt(d);
        } catch (NumberFormatException e) {
            fehler.add("Ungueltige Zahl: " + d);
        }
    }

    for (String eintrag : fehler) {
        System.out.println(eintrag);
    }
}
```

### Erlaeuterung

Fehler werden gesammelt und gebuendelt ausgegeben, statt den Ablauf sofort abzubrechen.

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

### Musterloesung

`ArrayIndexOutOfBoundsException` ist Unterklasse von `RuntimeException`. Nach dem allgemeinen `catch (RuntimeException ...)` ist der spezifische `catch` unerreichbar.

Korrektur: spezifisch zuerst.

```java
try {
    int[] a = new int[2];
    System.out.println(a[5]);
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Index");
} catch (RuntimeException e) {
    System.out.println("Fehler");
}
```

### Erlaeuterung

Catch-Reihenfolge immer von spezifisch nach allgemein.

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
