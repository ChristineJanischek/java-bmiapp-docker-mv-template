# Kurztest: Verzweigungen 3 - Bedingungen und Validierung - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: De-Morgan (4 Punkte)

### Aufgabenstellung

Vereinfache die Bedingung mit De-Morgan:

```java
if (!(a > 5 && b < 3)) {
    // ...
}
```

Schreibe eine aequivalente Bedingung ohne Negation:

_______________________________________________

### Musterloesung

```java
if (a <= 5 || b >= 3) {
    // ...
}
```

### Erlaeuterung

De-Morgan: `!(X && Y)` wird zu `(!X || !Y)`.

---

## Aufgabe 2: Preislogik (4 Punkte)

### Aufgabenstellung

```java
public double preis(int menge, boolean premium) {
    // premium: 10% Rabatt
    // menge >= 100: 5% Rabatt
    // Rabatte addieren sich
    
    
}
```

### Musterloesung

```java
public double preis(int menge, boolean premium) {
    double basis = menge * 10.0;
    double rabatt = 0.0;

    if (premium) {
        rabatt += 0.10;
    }
    if (menge >= 100) {
        rabatt += 0.05;
    }

    return basis * (1 - rabatt);
}
```

### Erlaeuterung

Beide Rabatte werden aufaddiert und dann einmal auf den Basispreis angewendet.

---

## Aufgabe 3: Validierung (5 Punkte)

### Aufgabenstellung

```java
public boolean gueltigeId(String id) {
    // gueltig: 3..10 Zeichen, beginnt mit "ID-"
    
    
}
```

### Musterloesung

```java
public boolean gueltigeId(String id) {
    return id != null
        && id.startsWith("ID-")
        && id.length() >= 3
        && id.length() <= 10;
}
```

### Erlaeuterung

Null-Check verhindert Fehler, danach werden Praefix und Laenge geprueft.

---

## Aufgabe 4: Code-Analyse (5 Punkte)

### Aufgabenstellung

```java
int x = 7;
if (x % 2 == 0) {
    System.out.println("gerade");
} else if (x % 3 == 0) {
    System.out.println("durch 3 teilbar");
} else {
    System.out.println("sonst");
}
```

Was wird ausgegeben? _______________________________________________

### Musterloesung

Ausgabe: `sonst`

### Erlaeuterung

`7` ist weder durch `2` noch durch `3` ohne Rest teilbar.

---

## Aufgabe 5: Guard Clause (4 Punkte)

### Aufgabenstellung

```java
public void sendeMail(String mail) {
    // Wenn mail ungueltig -> return
    // gueltig wenn enthaelt "@" und "."
    
    // TODO
    System.out.println("Sende Mail");
}
```

### Musterloesung

```java
public void sendeMail(String mail) {
    if (mail == null || !mail.contains("@") || !mail.contains(".")) {
        return;
    }
    System.out.println("Sende Mail");
}
```

### Erlaeuterung

Ungueltige Eingaben werden frueh beendet, nur gueltige Mails werden weiterverarbeitet.

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
if (a > 0 && b > 0 && c > 0) {
    System.out.println("alle positiv");
} else if (a > 0 && b > 0 || c > 0) {
    System.out.println("mindestens zwei positiv");
}
```

Warum ist die zweite Bedingung fehlerhaft? __________________________

### Musterloesung

Durch Operator-Prioritaet bedeutet die zweite Bedingung: `(a > 0 && b > 0) || c > 0`. Damit reicht schon `c > 0` alleine, obwohl „mindestens zwei“ gefordert ist.

Korrekt z. B.:

```java
else if ((a > 0 && b > 0) || (a > 0 && c > 0) || (b > 0 && c > 0)) {
    System.out.println("mindestens zwei positiv");
}
```

### Erlaeuterung

Die Logik muss explizit alle Kombinationen von jeweils zwei positiven Werten abdecken.

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
