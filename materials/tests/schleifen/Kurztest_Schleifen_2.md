# Kurztest: Schleifen 2 - Verschachtelung und Steuerung

**Klasse:** _________________  **Datum:** _________________  **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Verschachtelte Schleifen (4 Punkte)

Gib folgendes Muster aus (3 Zeilen):

```
***
***
***
```

```java
// TODO
```

---

## Aufgabe 2: break/continue (4 Punkte)

```java
for (int i = 1; i <= 5; i++) {
    if (i == 2) continue;
    if (i == 4) break;
    System.out.print(i + " ");
}
```

Was wird ausgegeben? _______________________________________________

---

## Aufgabe 3: Suche in Array (5 Punkte)

```java
public boolean enthaelt(int[] arr, int ziel) {
    // TODO
}
```

---

## Aufgabe 4: while mit Abbruchbedingung (5 Punkte)

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

---

## Aufgabe 5: for-each und Index (4 Punkte)

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

---

## Aufgabe 6: Fehlersuche (3 Punkte)

```java
int i = 0;
while (i < 5) {
    System.out.println(i);
}
```

Was fehlt?

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
