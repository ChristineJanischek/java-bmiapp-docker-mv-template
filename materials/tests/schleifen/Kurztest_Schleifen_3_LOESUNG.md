# Kurztest: Schleifen 3 - Arrays und Muster - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Gerade Zahlen summieren (4 Punkte)

### Aufgabenstellung

```java
public int summeGerade(int[] arr) {
    // TODO
}
```

### Musterloesung

```java
public int summeGerade(int[] arr) {
    int summe = 0;
    for (int wert : arr) {
        if (wert % 2 == 0) {
            summe += wert;
        }
    }
    return summe;
}
```

### Erlaeuterung

Nur gerade Zahlen (`wert % 2 == 0`) werden aufsummiert.

---

## Aufgabe 2: Multiplikationstabelle (4 Punkte)

### Aufgabenstellung

Erzeuge eine 3x3 Tabelle (1..3):

```
1 2 3
2 4 6
3 6 9
```

```java
// TODO
```

### Musterloesung

```java
for (int i = 1; i <= 3; i++) {
    for (int j = 1; j <= 3; j++) {
        System.out.print((i * j) + " ");
    }
    System.out.println();
}
```

### Erlaeuterung

Jede Tabellenzelle ist das Produkt aus Zeile (`i`) und Spalte (`j`).

---

## Aufgabe 3: do-while mit Abbruchwort (5 Punkte)

### Aufgabenstellung

Schreibe eine Schleife, die Eingaben sammelt, bis der Benutzer "stop" eingibt.

```java
Scanner sc = new Scanner(System.in);
String input;
// TODO
```

### Musterloesung

```java
Scanner sc = new Scanner(System.in);
String input;

do {
    input = sc.nextLine();
    if (!input.equalsIgnoreCase("stop")) {
        System.out.println("Eingabe: " + input);
    }
} while (!input.equalsIgnoreCase("stop"));
```

### Erlaeuterung

Die Schleife wird mindestens einmal ausgefuehrt. Bei `stop` endet sie.

---

## Aufgabe 4: Code-Analyse (5 Punkte)

### Aufgabenstellung

```java
int[] a = {1, 2, 3};
int sum = 0;
for (int i = 0; i <= a.length; i++) {
    sum += a[i];
}
System.out.println(sum);
```

a) Was passiert? _____________________________________________

b) Korrigiere die Schleife. ___________________________________

### Musterloesung

a) Es entsteht eine `ArrayIndexOutOfBoundsException`, weil bei `i == a.length` auf ein ungueltiges Indexelement zugegriffen wird.

b) Korrektur:

```java
for (int i = 0; i < a.length; i++) {
    sum += a[i];
}
```

### Erlaeuterung

Gueltige Array-Indizes reichen von `0` bis `a.length - 1`.

---

## Aufgabe 5: Indexierte Ausgabe (4 Punkte)

### Aufgabenstellung

```java
List<String> liste = List.of("A", "B", "C");
// Gib Index und Wert aus (0:A, 1:B, 2:C)

```

### Musterloesung

```java
for (int i = 0; i < liste.size(); i++) {
    System.out.println(i + ":" + liste.get(i));
}
```

### Erlaeuterung

Ein klassischer `for`-Loop ist sinnvoll, wenn Index und Wert benoetigt werden.

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
for (int i = 10; i > 0; i++) {
    System.out.println(i);
}
```

Was ist hier falsch, wenn gewuenscht ist: 10 bis 1?

_____________________________________________________________________

### Musterloesung

Die Richtung ist falsch: `i++` erhoeht statt zu verringern.

Korrekt:

```java
for (int i = 10; i > 0; i--) {
    System.out.println(i);
}
```

### Erlaeuterung

Fuer eine Countdown-Schleife muss der Zaehler dekrementiert werden.

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
