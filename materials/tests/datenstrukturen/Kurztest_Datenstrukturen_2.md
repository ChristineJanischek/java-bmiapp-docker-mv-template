# Kurztest: Datenstrukturen 2 - Queue, Stack, Sortierung

**Klasse:** _________________  **Datum:** _________________  **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Queue mit ArrayDeque (4 Punkte)

Vervollstaendige den Code fuer eine Queue (FIFO):

```java
Deque<String> queue = new ArrayDeque<>();

// Drei Elemente einfuegen:



// Erstes Element entfernen:
String first = __________________;
```

---

## Aufgabe 2: Stack (LIFO) (4 Punkte)

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(1);
stack.push(2);
stack.push(3);

int top = stack.pop();
```

**Fragen:**

a) Welchen Wert hat `top`? ____________

b) Was liegt danach oben auf dem Stack? ____________

---

## Aufgabe 3: Sortieren mit Comparator (5 Punkte)

Sortiere eine Liste von Benutzern nach Nachname:

```java
class User {
    String vorname;
    String nachname;
}

public void sortiere(List<User> users) {
    // TODO
}
```

---

## Aufgabe 4: Code-Analyse - Map Reihenfolge (5 Punkte)

```java
Map<String, Integer> map = new HashMap<>();
map.put("b", 2);
map.put("a", 1);
map.put("c", 3);

for (String k : map.keySet()) {
    System.out.print(k + " ");
}
```

a) Ist die Ausgabe-Reihenfolge garantiert? ____________

b) Welche Map nutzt du fuer garantierte Einfuegereihenfolge? ____________

---

## Aufgabe 5: Passende Datenstruktur waehlen (4 Punkte)

1) Schnelles Entfernen am Anfang und Ende: __________________________
2) Sortierte Schluessel-Ausgabe: ___________________________________
3) Sehr schnelle Suche nach ID: ____________________________________

---

## Aufgabe 6: Fehlersuche (3 Punkte)

```java
List<String> names = new ArrayList<>();
names.add("A");
names.add("B");

for (String n : names) {
    if (n.equals("A")) {
        names.remove(n);  // FEHLER
    }
}
```

Warum ist das problematisch, und wie loest man es?

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
