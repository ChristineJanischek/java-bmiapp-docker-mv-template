# Kurztest: Datenstrukturen 2 - Queue, Stack, Sortierung - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Queue mit ArrayDeque (4 Punkte)

### Aufgabenstellung

Vervollstaendige den Code fuer eine Queue (FIFO):

```java
Deque<String> queue = new ArrayDeque<>();

// Drei Elemente einfuegen:



// Erstes Element entfernen:
String first = __________________;
```

### Musterloesung

```java
queue.offer("A");
queue.offer("B");
queue.offer("C");

String first = queue.poll();
```

### Erlaeuterung

Queue arbeitet nach FIFO: zuerst eingefuegt, zuerst entfernt. `poll()` liefert und entfernt das erste Element.

---

## Aufgabe 2: Stack (4 Punkte)

### Aufgabenstellung

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

### Musterloesung

a) `top = 3`

b) Danach liegt `2` oben.

### Erlaeuterung

Stack arbeitet nach LIFO: zuletzt eingelegt, zuerst entnommen.

---

## Aufgabe 3: Sortieren mit Comparator (5 Punkte)

### Aufgabenstellung

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

### Musterloesung

```java
public void sortiere(List<User> users) {
    users.sort(Comparator.comparing(u -> u.nachname));
}
```

### Erlaeuterung

`Comparator.comparing(...)` erstellt eine Sortierregel anhand des Nachnamens.

---

## Aufgabe 4: Map Reihenfolge (5 Punkte)

### Aufgabenstellung

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

### Musterloesung

a) Nein, bei `HashMap` ist die Iterationsreihenfolge nicht garantiert.

b) `LinkedHashMap`

### Erlaeuterung

`LinkedHashMap` behaelt die Einfuegereihenfolge der Schluessel bei.

---

## Aufgabe 5: Passende Datenstruktur (4 Punkte)

### Aufgabenstellung

1) Schnelles Entfernen am Anfang und Ende: __________________________
2) Sortierte Schluessel-Ausgabe: ___________________________________
3) Sehr schnelle Suche nach ID: ____________________________________

### Musterloesung

1) `ArrayDeque`
2) `TreeMap`
3) `HashSet` (bei reiner Existenzpruefung) oder `HashMap` (mit Zusatzdaten)

### Erlaeuterung

Deque fuer Enden-Operationen, TreeMap fuer sortierte Schluessel, Hash-Strukturen fuer schnelle Suche.

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

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

### Musterloesung

Beim Entfernen in einer for-each-Schleife droht `ConcurrentModificationException`.

Korrekt z. B. mit Iterator:

```java
Iterator<String> it = names.iterator();
while (it.hasNext()) {
    if (it.next().equals("A")) {
        it.remove();
    }
}
```

### Erlaeuterung

Strukturveraenderungen waehrend for-each sind nicht erlaubt, ausser ueber den zugehoerigen Iterator.

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
