# Kurztest: Datenstrukturen 1 - Grundlagen

**Klasse:** _________________  **Datum:** _________________  **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: ArrayList anlegen (4 Punkte)

Erstelle eine Liste fuer Benutzernamen und fuege drei Werte hinzu.

```java
// Liste deklarieren und initialisieren:



// Drei Namen hinzufuegen:



```

---

## Aufgabe 2: Set und Duplikate (4 Punkte)

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

---

## Aufgabe 3: Map - Zaehlen von Rollen (5 Punkte)

Schreibe eine Methode, die Rollennamen zaehlt.

```java
public Map<String, Integer> zaehleRollen(List<String> rollen) {
    Map<String, Integer> counts = new HashMap<>();
    
    // TODO: rollen zaehlen
    
    return counts;
}
```

---

## Aufgabe 4: Code-Analyse (5 Punkte)

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

---

## Aufgabe 5: Passende Datenstruktur waehlen (4 Punkte)

Ordne die passende Datenstruktur zu:

1) Schneller Zugriff per Schluessel (IP -> Hostname): ________________
2) Keine Duplikate, Reihenfolge egal: _______________________________
3) Reihenfolge wichtig, Duplikate erlaubt: ___________________________

---

## Aufgabe 6: Fehlersuche (3 Punkte)

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
