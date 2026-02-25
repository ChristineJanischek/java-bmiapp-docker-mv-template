# Kurztest: Datenstrukturen 3 - Auswahl und Gruppierung

**Klasse:** _________________  **Datum:** _________________  **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Datenstruktur waehlen (4 Punkte)

Waehle die passende Struktur:

1) Duplikate entfernen, Reihenfolge behalten: ______________________
2) Schnellstes contains() fuer viele IDs: __________________________
3) Mehrere Werte pro Schluessel (Abteilung -> Mitarbeiterliste): ____

---

## Aufgabe 2: Maximum in Liste (4 Punkte)

```java
public int maxWert(List<Integer> werte) {
    // TODO
}
```

---

## Aufgabe 3: Gruppieren mit Map (5 Punkte)

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

---

## Aufgabe 4: Code-Analyse - TreeSet (5 Punkte)

```java
Set<Integer> set = new TreeSet<>();
set.add(5);
set.add(1);
set.add(3);
System.out.println(set);
```

a) Welche Ausgabe erwartest du? ______________________

b) Warum ist die Reihenfolge so? ______________________

---

## Aufgabe 5: Duplikate entfernen (4 Punkte)

Vervollstaendige:

```java
List<String> namen = List.of("A", "B", "A", "C");

// Entferne Duplikate:
Set<String> unique = ________________________________;
```

---

## Aufgabe 6: Fehlersuche (3 Punkte)

```java
List zahlen = new ArrayList();
zahlen.add(1);
zahlen.add("zwei");  // FEHLER
```

Was ist das Problem, und wie loest du es?

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
