# Kurztest: Datenstrukturen 1 - Grundlagen - LOESUNG

**Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: ArrayList anlegen (4 Punkte)

### Aufgabenstellung

Erstelle eine Liste fuer Benutzernamen und fuege drei Werte hinzu.

```java
// Liste deklarieren und initialisieren:



// Drei Namen hinzufuegen:



```

### Musterloesung

```java
List<String> benutzernamen = new ArrayList<>();
benutzernamen.add("anna");
benutzernamen.add("ben");
benutzernamen.add("chris");
```

### Erlaeuterung

`ArrayList` speichert Elemente in Reihenfolge und erlaubt Duplikate. Mit `add(...)` werden die Namen nacheinander eingefuegt.

---

## Aufgabe 2: Set und Duplikate (4 Punkte)

### Aufgabenstellung

Gegeben:

```java
Set<String> ids = new HashSet<>();
ids.add("A1");
ids.add("A1");
ids.add("B2");
```

**Fragen:**

a) Wie viele Elemente enthaelt das Set? ____________

b) Warum? ___________________________________________________________

### Musterloesung

a) `2`

b) Ein `Set` speichert jeden Wert nur einmal. Das zweite `"A1"` wird als Duplikat ignoriert.

### Erlaeuterung

`HashSet` garantiert Eindeutigkeit der Elemente, nicht aber eine feste Reihenfolge.

---

## Aufgabe 3: Map - Zaehlen von Rollen (5 Punkte)

### Aufgabenstellung

Schreibe eine Methode, die Rollennamen zaehlt.

```java
public Map<String, Integer> zaehleRollen(List<String> rollen) {
    Map<String, Integer> counts = new HashMap<>();
    
    // TODO: rollen zaehlen
    
    return counts;
}
```

### Musterloesung

```java
public Map<String, Integer> zaehleRollen(List<String> rollen) {
    Map<String, Integer> counts = new HashMap<>();

    for (String rolle : rollen) {
        counts.put(rolle, counts.getOrDefault(rolle, 0) + 1);
    }

    return counts;
}
```

### Erlaeuterung

Die Rolle ist der Schluessel der Map, der Wert ist die Anzahl. `getOrDefault` startet bei `0`, falls ein Schluessel noch nicht existiert.

---

## Aufgabe 4: Code-Analyse (5 Punkte)

### Aufgabenstellung

```java
List<Integer> zahlen = new ArrayList<>();
zahlen.add(10);
zahlen.add(20);
zahlen.add(30);

zahlen.remove(1);
System.out.println(zahlen);
```

a) Was wird ausgegeben? _____________________________________________

b) Warum wurde genau dieses Element entfernt? ________________________

### Musterloesung

a) Ausgabe: `[10, 30]`

b) `remove(1)` entfernt bei `List<Integer>` das Element am Index `1` (also `20`), nicht den Wert `1`.

### Erlaeuterung

Listen arbeiten positionsbasiert. Der zweite Eintrag (Index 1) wird entfernt.

---

## Aufgabe 5: Passende Datenstruktur waehlen (4 Punkte)

### Aufgabenstellung

Ordne die passende Datenstruktur zu:

1) Schneller Zugriff per Schluessel (IP -> Hostname): ________________
2) Keine Duplikate, Reihenfolge egal: _______________________________
3) Reihenfolge wichtig, Duplikate erlaubt: ___________________________

### Musterloesung

1) `HashMap`
2) `HashSet`
3) `ArrayList`

### Erlaeuterung

`Map` fuer Schluessel/Wert, `Set` fuer Eindeutigkeit, `List` fuer geordnete Eintraege mit moeglichen Duplikaten.

---

## Aufgabe 6: Fehlersuche (3 Punkte)

### Aufgabenstellung

```java
public class Team {
    private List<String> mitglieder;

    public void addMitglied(String name) {
        mitglieder.add(name);  // FEHLER
    }
}
```

**Frage:** Was fehlt und wie behebst du es?

_____________________________________________________________________

### Musterloesung

Die Liste wird nicht initialisiert. Beispiel:

```java
public class Team {
    private List<String> mitglieder = new ArrayList<>();

    public void addMitglied(String name) {
        mitglieder.add(name);
    }
}
```

### Erlaeuterung

Ohne Initialisierung ist `mitglieder` `null`, dadurch entsteht eine `NullPointerException` beim `add`.

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
