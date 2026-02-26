# Kurztest: Schleifen 2 - Verschachtelung und Steuerung - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Verschachtelte Schleifen (4 Punkte)

### Aufgabenstellung

Gib folgendes Muster aus (3 Zeilen):

```
***
***
***
```

```java
// TODO
```

### Musterloesung

```java
for (int zeile = 0; zeile < 3; zeile++) {
    for (int spalte = 0; spalte < 3; spalte++) {
        System.out.print("*");
    }
    System.out.println();
}
```

### Erlaeuterung

Die aeussere Schleife steuert die Zeilen, die innere die Anzahl Sterne pro Zeile.

---

## Aufgabe 2: break/continue (4 Punkte)

### Aufgabenstellung

```java
for (int i = 1; i <= 5; i++) {
    if (i == 2) continue;
    if (i == 4) break;
    System.out.print(i + " ");
}
```

Was wird ausgegeben? _______________________________________________

### Musterloesung

Ausgabe: `1 3 `

### Erlaeuterung

Bei `2` wird der Durchlauf uebersprungen (`continue`), bei `4` wird die Schleife beendet (`break`).

---

## Aufgabe 3: Suche in Array (5 Punkte)

### Aufgabenstellung

```java
public boolean enthaelt(int[] arr, int ziel) {
    // TODO
}
```

### Musterloesung

```java
public boolean enthaelt(int[] arr, int ziel) {
    for (int wert : arr) {
        if (wert == ziel) {
            return true;
        }
    }
    return false;
}
```

### Erlaeuterung

Sobald der gesuchte Wert gefunden wird, kann sofort `true` zurueckgegeben werden.

---

## Aufgabe 4: while mit Abbruchbedingung (5 Punkte)

### Aufgabenstellung

```java
int sum = 0;
int n = 1;
while (sum < 10) {
    sum += n;
    n++;
}
System.out.println(n);
```

a) Was wird ausgegeben? ____________

b) Erklaere kurz. _________________________________________________

### Musterloesung

a) Ausgabe: `5`

b) `sum` wird zu `1, 3, 6, 10`; danach wurde `n` bereits auf `5` erhoeht.

### Erlaeuterung

Die Abbruchbedingung prueft erst am Schleifenkopf des naechsten Durchlaufs.

---

## Aufgabe 5: for-each und Index (4 Punkte)

### Aufgabenstellung

Warum ist dieser Code falsch?

```java
for (String s : liste) {
    int i = liste.indexOf(s);
    if (i % 2 == 0) {
        liste.remove(s);
    }
}
```

_____________________________________________________________________

### Musterloesung

Der Code ist problematisch, weil waehrend einer for-each-Iteration aus derselben Liste entfernt wird (`ConcurrentModificationException`). Zusaetzlich ist `indexOf` bei Duplikaten unzuverlaessig.

Korrekt z. B. rueckwaerts mit Index:

```java
for (int i = liste.size() - 1; i >= 0; i--) {
    if (i % 2 == 0) {
        liste.remove(i);
    }
}
```

### Erlaeuterung

Rueckwaerts entfernen verschiebt keine noch zu verarbeitenden Indizes nach vorne.

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
int i = 0;
while (i < 5) {
    System.out.println(i);
}
```

Was fehlt?

_____________________________________________________________________

### Musterloesung

Es fehlt die Zaehleraenderung, z. B.:

```java
int i = 0;
while (i < 5) {
    System.out.println(i);
    i++;
}
```

### Erlaeuterung

Ohne `i++` bleibt `i` immer `0` und die Schleife endet nie.

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
