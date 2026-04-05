# Syntaxhilfe: Datenstrukturen (Java)

**Ziel:** Grundsaetzliche Nutzung der Collection-API.

---

## Liste (ArrayList)

```java
List<String> namen = new ArrayList<>();

namen.add("Max");
namen.add("Lena");

for (String n : namen) {
    System.out.println(n);
}
```

---

## Set (HashSet) - keine Duplikate

```java
Set<String> codes = new HashSet<>();
codes.add("A1");
codes.add("A1"); // wird ignoriert
```

---

## Map (HashMap) - Key/Value

```java
Map<String, Integer> punkte = new HashMap<>();
punkte.put("Max", 10);

int p = punkte.get("Max");
```

---

## Queue / Deque

```java
Deque<String> queue = new ArrayDeque<>();
queue.addLast("A");
queue.addLast("B");

String erstes = queue.removeFirst();
```

---

## Tipps

- List: geordnete Sammlung, Duplikate erlaubt.
- Set: keine Duplikate.
- Map: Schluessel -> Wert.
- Collections.sort(list) fuer Sortierung.
