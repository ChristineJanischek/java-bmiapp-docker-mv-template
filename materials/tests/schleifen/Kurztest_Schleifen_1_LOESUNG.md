# Kurztest: Schleifen 1 - Grundlagen - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: for-Schleife (4 Punkte)

### Aufgabenstellung

Schreibe eine for-Schleife, die die Zahlen 1..5 ausgibt.

```java
// TODO
```

### Musterloesung

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

### Erlaeuterung

Start bei `1`, solange `i <= 5`, nach jedem Durchlauf wird `i` um 1 erhoeht.

---

## Aufgabe 2: while-Schleife (4 Punkte)

### Aufgabenstellung

Vervollstaendige:

```java
int i = 5;
while (__________________) {
    System.out.println(i);
    __________________;
}
```

### Musterloesung

```java
int i = 5;
while (i > 0) {
    System.out.println(i);
    i--;
}
```

### Erlaeuterung

Die Schleife laeuft rueckwaerts von 5 bis 1. Mit `i--` wird ein Endlosschleifenfehler vermieden.

---

## Aufgabe 3: Summe berechnen (5 Punkte)

### Aufgabenstellung

```java
public int summeBis(int n) {
    // Summe 1..n
    
    
}
```

### Musterloesung

```java
public int summeBis(int n) {
    int summe = 0;
    for (int i = 1; i <= n; i++) {
        summe += i;
    }
    return summe;
}
```

### Erlaeuterung

In jeder Iteration wird die aktuelle Zahl zur Summe addiert.

---

## Aufgabe 4: do-while (5 Punkte)

### Aufgabenstellung

```java
int x = 0;
do {
    x++;
} while (x < 3);
System.out.println(x);
```

a) Was wird ausgegeben? ____________

b) Warum ist do-while hier sinnvoll? ________________________________

### Musterloesung

a) Ausgabe: `3`

b) Bei `do-while` wird der Block mindestens einmal ausgefuehrt, danach wird die Bedingung geprueft.

### Erlaeuterung

Die Wertefolge von `x` ist 1, 2, 3; dann ist `x < 3` falsch und die Schleife endet.

---

## Aufgabe 5: for-each (4 Punkte)

### Aufgabenstellung

```java
List<String> namen = List.of("A", "B", "C");
// Gib alle Namen aus:

```

### Musterloesung

```java
for (String name : namen) {
    System.out.println(name);
}
```

### Erlaeuterung

for-each ist geeignet, wenn alle Elemente einer Sammlung gelesen werden sollen.

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
for (int i = 0; i <= 5; i--) {
    System.out.println(i);
}
```

Was ist der Fehler?

_____________________________________________________________________

### Musterloesung

Der Zaehler laeuft in die falsche Richtung (`i--`), waehrend die Bedingung `i <= 5` lautet. So entsteht eine Endlosschleife.

Korrektur:

```java
for (int i = 0; i <= 5; i++) {
    System.out.println(i);
}
```

### Erlaeuterung

Bedingung und Schritt muessen zusammenpassen: bei aufsteigender Grenze muss auch aufsteigend gezaehlt werden.

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
