# Kurztest: Datenstrukturen 3 - Auswahl und Gruppierung - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Datenstruktur waehlen (4 Punkte)

### Aufgabenstellung

Waehle die passende Struktur:

1) Duplikate entfernen, Reihenfolge behalten: ______________________
2) Schnellstes contains() fuer viele IDs: __________________________
3) Mehrere Werte pro Schluessel (Abteilung -> Mitarbeiterliste): ____

### Musterloesung

1) `LinkedHashSet`
2) `HashSet`
3) `Map<String, List<Mitarbeiter>>`

### Erlaeuterung

`LinkedHashSet` kombiniert Eindeutigkeit und Einfuegereihenfolge. `HashSet` ist sehr schnell fuer `contains`.

---

## Aufgabe 2: Maximum in Liste (4 Punkte)

### Aufgabenstellung

```java
public int maxWert(List<Integer> werte) {
    // TODO
}
```

### Musterloesung

```java
public int maxWert(List<Integer> werte) {
    if (werte == null || werte.isEmpty()) {
        throw new IllegalArgumentException("Liste darf nicht leer sein");
    }

    int max = werte.get(0);
    for (int wert : werte) {
        if (wert > max) {
            max = wert;
        }
    }
    return max;
}
```

### Erlaeuterung

Es wird linear durchlaufen und das aktuell groesste Element gespeichert.

---

## Aufgabe 3: Gruppieren mit Map (5 Punkte)

### Aufgabenstellung

```java
class Mitarbeiter {
    String name;
    String abteilung;
}

public Map<String, List<Mitarbeiter>> gruppiere(List<Mitarbeiter> liste) {
    Map<String, List<Mitarbeiter>> map = new HashMap<>();
    
    // TODO: Mitarbeiter nach Abteilung gruppieren
    
    return map;
}
```

### Musterloesung

```java
public Map<String, List<Mitarbeiter>> gruppiere(List<Mitarbeiter> liste) {
    Map<String, List<Mitarbeiter>> map = new HashMap<>();

    for (Mitarbeiter m : liste) {
        map.computeIfAbsent(m.abteilung, k -> new ArrayList<>()).add(m);
    }

    return map;
}
```

### Erlaeuterung

`computeIfAbsent` erstellt bei Bedarf die Liste pro Abteilung und fuegt dann den Mitarbeiter hinzu.

---

## Aufgabe 4: Code-Analyse - TreeSet (5 Punkte)

### Aufgabenstellung

```java
Set<Integer> set = new TreeSet<>();
set.add(5);
set.add(1);
set.add(3);
System.out.println(set);
```

a) Welche Ausgabe erwartest du? ______________________

b) Warum ist die Reihenfolge so? ______________________

### Musterloesung

a) `[1, 3, 5]`

b) `TreeSet` sortiert Elemente automatisch nach natuerlicher Ordnung.

### Erlaeuterung

Im Gegensatz zu `HashSet` ist die Reihenfolge bei `TreeSet` sortiert.

---

## Aufgabe 5: Duplikate entfernen (4 Punkte)

### Aufgabenstellung

Vervollstaendige:

```java
List<String> namen = List.of("A", "B", "A", "C");

// Entferne Duplikate:
Set<String> unique = ________________________________;
```

### Musterloesung

```java
Set<String> unique = new LinkedHashSet<>(namen);
```

### Erlaeuterung

`LinkedHashSet` entfernt Duplikate und behaelt die erste Reihenfolge (`A, B, C`).

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
List zahlen = new ArrayList();
zahlen.add(1);
zahlen.add("zwei");  // FEHLER
```

Was ist das Problem, und wie loest du es?

_____________________________________________________________________

### Musterloesung

Problem: Rohtypen (`List` ohne Generics) erlauben gemischte Typen und fuehren spaeter zu Laufzeitfehlern.

Korrekt:

```java
List<Integer> zahlen = new ArrayList<>();
zahlen.add(1);
// zahlen.add("zwei"); // Compilerfehler, daher sicherer
```

### Erlaeuterung

Generics sorgen fuer Typsicherheit bereits zur Compile-Zeit.

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
