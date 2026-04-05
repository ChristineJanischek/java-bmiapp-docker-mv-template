# Kurztest: Assoziationen & ArrayList – Erweiterte 1:N-Szenarien

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Sortierung in einer ArrayList (5 Punkte)

**Thema:** Erweiterte Funktionen – Sortieren von Objekten

Gegeben ist eine ArrayList mit Messung-Objekten. Jede Messung hat ein Attribut `gewicht` (double).

a) Ergänze die Lücken, um die Messungen **aufsteigend nach Gewicht** zu sortieren:

```java
List<Messung> messungen = person.getMessungen();

Collections.sort(messungen, new Comparator<Messung>() {
    @Override
    public int compare(Messung m1, Messung m2) {
        return _______________________________________________
    }
});
```

b) Was muss zusätzlich importiert werden, damit `Collections` und `Comparator` verwendet werden können?

```java
import _______________________________________________
import _______________________________________________
```

c) Erkläre in 1 Satz, was `compare()` zurückgeben muss, damit m1 **vor** m2 einsortiert wird:

___________________________________________________________________________

---

## Aufgabe 2: ArrayList-API – Methoden `contains` und `subList` (6 Punkte)

**Thema:** Vertiefung der API-Methoden

Ordne die Methodenaufrufe den Beschreibungen zu. Schreibe den Buchstaben in die Klammer.

**Beschreibungen:**
- **(a)** Gibt `true` zurück, wenn das Objekt in der Liste vorhanden ist
- **(b)** Gibt eine Teilliste von Index 1 bis 3 (exklusiv) zurück
- **(c)** Ersetzt das Element an Index 2 durch ein neues Objekt
- **(d)** Gibt den Index des ersten Vorkommens zurück (oder -1)
- **(e)** Fügt ein Element an einer bestimmten Position ein
- **(f)** Prüft, ob die Liste leer ist

| Methodenaufruf | Buchstabe |
|----------------|-----------|
| `messungen.contains(m1)` | ( ) |
| `messungen.subList(1, 3)` | ( ) |
| `messungen.set(2, neueMessung)` | ( ) |
| `messungen.indexOf(m1)` | ( ) |
| `messungen.add(1, neueMessung)` | ( ) |
| `messungen.isEmpty()` | ( ) |

---

## Aufgabe 3: Komplexe Code-Analyse (Iteration & Modifikation) (8 Punkte)

**Thema:** Zugriffen und Manipulationen in einer ArrayList

Gegeben ist folgender Code:

```java
Person person = new Person("Anna", "Bauer", 30, "Frau");

person.addMessung(new Messung(80.0, 1.70));
person.addMessung(new Messung(75.0, 1.70));
person.addMessung(new Messung(90.0, 1.70));
person.addMessung(new Messung(72.0, 1.70));

List<Messung> messungen = person.getMessungen();

int count = 0;
for (int i = 0; i < messungen.size(); i++) {
    if (messungen.get(i).getGewicht() > 77.0) {
        count++;
    }
}

System.out.println("Anzahl über 77 kg: " + count);
System.out.println("Erste Messung: " + messungen.get(0).getGewicht());
System.out.println("Letzte Messung: " + messungen.get(messungen.size() - 1).getGewicht());
```

**Fragen:**

a) Was gibt die **erste** Ausgabezeile aus?

Antwort: ___________________________________________________________________

b) Was gibt die **zweite** Ausgabezeile aus?

Antwort: ___________________________________________________________________

c) Was gibt die **dritte** Ausgabezeile aus?

Antwort: ___________________________________________________________________

d) Erkläre, warum `messungen.get(messungen.size() - 1)` das **letzte** Element zurückgibt:

___________________________________________________________________________

___________________________________________________________________________

e) Was würde passieren, wenn die ArrayList leer wäre und `messungen.get(0)` aufgerufen wird?

___________________________________________________________________________

---

## Aufgabe 4: Dynamische Testdaten mit for-Schleife (6 Punkte)

**Thema:** Generierung und Ausgabe von Testdaten

a) Schreibe eine **for-Schleife**, die 5 Messung-Objekte erstellt und sie einer Person hinzufügt.  
Die Gewichte sollen 60.0, 62.0, 64.0, 66.0, 68.0 betragen (jeweils +2.0 pro Iteration).  
Die Größe beträgt immer 1.75.

```java
Person person = new Person("Test", "Person", 25, "Mann");

// for-Schleife hier schreiben:



```

b) Schreibe anschließend eine **for-Schleife**, die alle Messungen mit ihrer **Nummer** (1-basiert) und ihrem **Gewicht** ausgibt.  
Beispiel-Ausgabe: `Messung 1: 60.0 kg`

```java
// Ausgabeschleife:



```

c) Wie viele `Messung`-Objekte wurden insgesamt erstellt?

Antwort: ___________________________________________________________________

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Sortierung in einer ArrayList | 5 | |
| 2. ArrayList-API (contains, subList) | 6 | |
| 3. Komplexe Code-Analyse | 8 | |
| 4. Dynamische Testdaten | 6 | |
| **Gesamt** | **25** | |
