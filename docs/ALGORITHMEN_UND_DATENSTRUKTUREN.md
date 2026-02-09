# 📚 Algorithmen und Datenstrukturen - Lernmodul für Schüler

## Überblick
Dieses Lernmodul führt Schüler in **Algorithmen** und **Datenstrukturen** ein, mit Fokus auf:
- **Listen und Arrays** verstehen und manipulieren
- **Suchalgorithmen** implementieren (Linear Search, Binary Search)
- **Sortieralgorithmen** verstehen und programmieren (Bubble Sort, Selection Sort, Insertion Sort)
- **Struktogramme** zur Algorithmen-Visualisierung nutzen
- Algorithmische **Komplexität** (Big-O-Notation) einführen

---

## 1. Lernziele

Nach Abschluss dieses Moduls können Schüler:

✅ **Datenstrukturen verstehen:**
- Unterschied zwischen Array und ArrayList kennen
- Listen iterieren und manipulieren
- Indizierung und Grenzen beachten

✅ **Algorithmen analysieren und implementieren:**
- Struktogramme in Java-Code übersetzen
- Basis-Suchalgorithmen programmieren
- Basis-Sortieralgorithmen programmieren
- Algorithmen-Effizienz vergleichen

✅ **Code mit Tests validieren:**
- JUnit-Tests schreiben und verstehen
- Edge-Cases testen (leere Listen, Ein-Element, gleiche Werte)
- Test-driven Development (TDD) praktizieren

---

## 2. Niveau-Definition

### Zielgruppe
- **Klassenstufe:** 11-12 (Sekundarstufe II / Gymnasium)
- **Vorwissen:** Java-Grundlagen (Variablen, Kontrollstrukturen, Methoden)
- **Lernstand:** Nach Version 2 des BMI-Rechner-Projekts

### Komplexität
- **Einstieg:** Sehr einfach (Lineare Suche, Bubble Sort)
- **Fortgeschrittene:** Mittelschwer (Binary Search, verschiedene Sortieralgorithmen)
- **Challenge:** Schwer (Komplexität analysieren, Hybrid-Algorithmen)

---

## 3. Struktur des Lernmoduls

### Phase 1: Struktogramme lesen und verstehen
**Dauer:** 1-2 Unterrichtsstunden

**Ziele:**
- Verstehen der Notationen in Struktogrammen
- Umwandlung Struktogramm → Pseudocode → Java-Code
- Erkennen von Mustern (Schleifen, Bedingungen, Funktionsaufrufe)

**Material:**
- Operatorenliste für Struktogramme
- Beispiel-Struktogramme (einfach → komplex)
- Leerstellen-Aufgaben (Code-Lücken)

---

### Phase 2: Lineare Datenstrukturen
**Dauer:** 2 Unterrichtsstunden

**Inhalte:**
- **Arrays vs. ArrayList**
- **Iteration über Listen** (for, for-each, while)
- **Grundoperationen:** Hinzufügen, Entfernen, Zugriff

**Praktische Aufgaben:**
- Aufgabe 1.1: Array iterieren und Werte ausgeben
- Aufgabe 1.2: ArrayList manipulieren
- Aufgabe 1.3: Duplikate finden/entfernen

---

### Phase 3: Suchalgorithmen
**Dauer:** 2-3 Unterrichtsstunden

**Inhalte:**
- **Lineare Suche:** Einfach, aber O(n)
- **Binäre Suche:** Schneller, aber sortiertes Array erforderlich
- **Komplexität vergleichen:** O(n) vs O(log n)

**Praktische Aufgaben:**
- Aufgabe 2.1: Lineare Suche implementieren (Struktogramm → Code)
- Aufgabe 2.2: Binäre Suche implementieren
- Aufgabe 2.3: Suchzeiten vergleichen (große Arrays)

---

### Phase 4: Sortieralgorithmen
**Dauer:** 3-4 Unterrichtsstunden

**Inhalte:**
- **Bubble Sort:** Einfach zu verstehen, O(n²)
- **Selection Sort:** Optimierte Bubble Sort, O(n²)
- **Insertion Sort:** Praktischer, O(n²)
- **Quicksort/Mergesort:** Schneller, O(n log n) (Challenge)

**Praktische Aufgaben:**
- Aufgabe 3.1: Bubble Sort implementieren (Struktogramm → Code)
- Aufgabe 3.2: Selection Sort implementieren
- Aufgabe 3.3: Insertion Sort implementieren
- Aufgabe 3.4 (Challenge): Quicksort oder Mergesort

---

## 4. Operatorenliste für Struktogramme

Schüler dürfen **NUR** diese Notationen verwenden:

### Grundstruktur
```
┌─────────────────────┐
│  Prozessblock       │  → Zuweisung: a = 5
│  (Rechteck)         │  → Methodenaufruf: print(x)
└─────────────────────┘
```

### Alternativen
```
      ┌────┐
      │ B? │  → if (Bedingung)
    J ┌────┐ N
      │    │
    ┌─▼─┐┌─▼─┐
    │ A ││ C │  → if-Block | else-Block
    └───┘└───┘
```

### Schleifen
```
    ┌────────┐
    │ i<10?  │  → while (i < 10)
   N│        │J
  ┌─▼──┐ ┌──▼─┐
  │Exit│ │i++ │  → Schleife, dann Counter
  └────┘ └────┘
```

### Funktionsaufruf
```
┌───────────────┐
│ result =      │  → Zuweisung mit Funktionsaufruf
│ search(arr,x) │
└───────────────┘
```

### Array/List Zugriff
```
┌──────────────┐
│ arr[i] = 99  │  → Array-Element setzen
│ x = arr[i]   │  → Array-Element auslesen
└──────────────┘
```

---

## 5. Erste Aufgaben-Serie: Struktogramme → Code

### Aufgabe 1.0 (Einstieg): Array durchlaufen und ausgeben

**Struktogramm:**
```
┌──────────────────────────┐
│ i = 0                    │
└──────────────────────────┘
         ↓
      ┌──────────┐
      │ i < 5?   │
     N│          │J
    ┌─▼──┐    ┌──▼───────────────────┐
    │Exit│    │ print(arr[i])        │
    └────┘    │ i = i + 1            │
              └──────────────────────┘
                      ↓ (zurück zu if)
```

**Aufgabe:** Implementiere dieses Struktogramm in Java!

**Lösung (Muster):**
```java
public static void printArray(int[] arr) {
    int i = 0;
    while (i < arr.length) {
        System.out.println(arr[i]);
        i = i + 1;
    }
}
```

**Variante mit for-Schleife:**
```java
public static void printArray(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        System.out.println(arr[i]);
    }
}
```

---

### Aufgabe 1.1: Maximales Element finden

**Struktogramm:**
```
┌──────────────────────────┐
│ max = arr[0]             │
│ i = 1                    │
└──────────────────────────┘
         ↓
      ┌──────────┐
      │ i < n?   │
     N│          │J
    ┌─▼──┐    ┌──▼──────────────────────┐
    │max │    │ arr[i] > max?           │
    └────┘    └────┬───────────────────┬─┘
                  J│                   │N
              ┌────▼───┐           ┌───▼────┐
              │max =   │           │ i=i+1  │
              │arr[i]  │           │        │
              └────┬───┘           └───┬────┘
                   │                   │
                   └──────┬────────────┘
                          ↓ (zurück zu if)
```

**Aufgabe:**
- Implementiere die Methode nach dem Struktogramm
- Schreibe Tests für: leere Arrays, Ein-Element-Arrays, negative Zahlen

**Template:**
```java
public static int findMax(int[] arr) {
    // TODO: Implementiere nach Struktogramm
    return 0;
}

// Tests
public static void main(String[] args) {
    int[] test1 = {3, 1, 4, 1, 5, 9};
    System.out.println("Max: " + findMax(test1)); // Erwartet: 9
    
    int[] test2 = {-5, -2, -10};
    System.out.println("Max: " + findMax(test2)); // Erwartet: -2
}
```

---

### Aufgabe 2.0: Lineare Suche

**Struktogramm:**
```
┌──────────────────────────┐
│ i = 0                    │
│ found = false            │
└──────────────────────────┘
         ↓
      ┌───────────────────┐
      │ i<n AND NOT found?│
     N│                   │Y
    ┌─▼──┐    ┌───────────▼────────────┐
    │ret │    │ arr[i] == target?      │
    │-1  │    └────┬──────────────┬────┘
    └────┘        Y│              │N
              ┌────▼──┐    ┌──────▼──┐
              │return │    │ i=i+1   │
              │i      │    └────┬────┘
              └───────┘         │
                                │ (zurück zu while)
```

**Aufgabe:**
- Implementiere die lineare Suche
- Gib den Index zurück oder -1, wenn nicht gefunden
- Tests für: vorhandene Werte, nicht vorhandene, erste Element, letzte Element

---

### Aufgabe 3.0: Bubble Sort

**Struktogramm:**
```
┌──────────────┐
│ i = 0        │
└──────────────┘
      ↓
   ┌──────────┐
   │ i < n-1? │
  N│          │Y
┌──▼─┐    ┌───▼───────────────────┐
│Exit│    │ j = 0                 │
└────┘    │ swapped = false       │
          └───────┬───────────────┘
                  ↓
             ┌──────────────┐
             │ j < n-1-i?   │
            N│              │Y
          ┌──▼──┐    ┌──────▼────────────────┐
          │i++  │    │ arr[j] > arr[j+1]?   │
          │(z.u)│    └────┬──────────┬───────┘
          └────┘        Y│           │N
                    ┌────▼─────┐┌────▼─────┐
                    │swap()    ││j=j+1     │
                    │swapped=T ││          │
                    └────┬─────┘└────┬─────┘
                         └────┬──────┘
                              ↓
                         (zurück while j)
```

**Aufgabe:**
- Implementiere Bubble Sort
- Optimiere: `swapped`-Flag nutzen
- Tests für: sortierte Arrays, reverse-sortierte, Duplikate

---

## 6. Test-Framework und Beispiele

### Einfache Test-Klasse (ohne JUnit)

```java
public class AlgorithmusTests {
    
    private static int testCount = 0;
    private static int passedTests = 0;
    
    public static void test(String name, boolean bedingung) {
        testCount++;
        if (bedingung) {
            passedTests++;
            System.out.println("✓ " + name);
        } else {
            System.out.println("✗ " + name);
        }
    }
    
    public static void summary() {
        System.out.println("\n" + passedTests + "/" + testCount + " Tests bestanden");
    }
    
    // Beispiel-Tests
    public static void main(String[] args) {
        // Test 1: Lineare Suche
        int[] arr = {3, 1, 4, 1, 5};
        test("Suche 4 findet Index 2", linearSearch(arr, 4) == 2);
        test("Suche 10 findet -1", linearSearch(arr, 10) == -1);
        
        // Test 2: Bubble Sort
        int[] unsorted = {5, 2, 8, 1};
        bubbleSort(unsorted);
        test("Bubble Sort funktioniert", 
             unsorted[0] == 1 && unsorted[3] == 8);
        
        summary();
    }
}
```

### JUnit 5 Test-Beispiel (erweitert)

```java
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Algorithmus-Tests")
public class AlgorithmusJUnitTest {
    
    @Test
    @DisplayName("Lineare Suche findet vorhandene Werte")
    void testLinearSearchFound() {
        int[] arr = {3, 1, 4, 1, 5};
        assertEquals(2, linearSearch(arr, 4));
    }
    
    @Test
    @DisplayName("Lineare Suche gibt -1 für nicht gefundene Werte")
    void testLinearSearchNotFound() {
        int[] arr = {3, 1, 4};
        assertEquals(-1, linearSearch(arr, 10));
    }
    
    @Test
    @DisplayName("Bubble Sort sortiert Array aufsteigend")
    void testBubbleSort() {
        int[] arr = {5, 2, 8, 1};
        bubbleSort(arr);
        assertArrayEquals(new int[]{1, 2, 5, 8}, arr);
    }
    
    // Edge Cases
    @Test
    @DisplayName("Bubble Sort mit leerem Array")
    void testBubbleSortEmpty() {
        int[] arr = {};
        bubbleSort(arr);
        assertEquals(0, arr.length);
    }
}
```

---

## 7. Lehrkraft-Hinweise

### Differenzierung

**Anfänger:**
- Aufgaben 1.0 - 1.1
- Mit Strukto­gramm-Template arbeiten
- Einfache Tests

**Standard:**
- Aufgaben 1.1 - 3.1
- Tests selbst schreiben
- Komplexität vergleichen

**Fortgeschrittene:**
- Aufgaben 3.2 - 3.4 (Challenge)
- Schnellere Algorithmen (Quicksort)
- Komplexität analysieren und beweisen

### Unterrichtsmaterial
- PowerPoint-Folien zum Zeigen von Struktogrammen
- Interactive Visualisierung (z.B. VisuAlgo)
- Live-Coding-Session mit Studierende

### Häufige Fehler
1. **Off-by-One-Fehler:** `i <= arr.length` statt `i < arr.length`
2. **Swap-Fehler:** Temporäre Variable vergessen
3. **Sortierungslogik:** `>` vs `<` verwechseln
4. **Endlosschleifen:** `while(true)` ohne Exit-Bedingung

---

## 8. Fortgeschrittene Themen

### Komplexität (Big-O)
- **O(1):** Konstant (Array-Zugriff)
- **O(log n):** Logarithmisch (Binary Search)
- **O(n):** Linear (Linear Search, Bubble Sort worst case)
- **O(n²):** Quadratisch (Nested Loops)
- **O(n log n):** Linearithmisch (Quicksort average, Mergesort)

### Visualisierung
```
Wieviele Operationen für n=1000?
O(1):       1 Operation
O(log n):   ~10 Operationen
O(n):       1.000 Operationen
O(n²):      1.000.000 Operationen  ❌ Sehr langsam!
O(n log n): ~10.000 Operationen
```

---

## 9. Ressourcen für Lehrkräfte

- **VisuAlgo:** https://visualgo.net/en (Algorithmen visualisieren)
- **Big-O CheatSheet:** https://www.bigocheatsheet.com/
- **Sorting Visualizer:** https://www.youtube.com/results?search_query=sorting+algorithms+visualization
- **GeeksforGeeks:** https://www.geeksforgeeks.org/fundamentals-of-algorithms/ (Referenz)

---

## Version-Historie

| Version | Datum | Änderungen |
|---------|-------|-----------|
| 1.0 | 2026-02-03 | Initiales Modul mit Aufgaben 1-3 |
|  |  | Operatorenliste und Test-Beispiele |

