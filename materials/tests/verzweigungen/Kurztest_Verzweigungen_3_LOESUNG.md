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

---

## Aufgabe 3: Validierung (5 Punkte)

### Aufgabenstellung

```java
public boolean gueltigeId(String id) {
    // gueltig: 3..10 Zeichen, beginnt mit "ID-"
    
    
}
```

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

