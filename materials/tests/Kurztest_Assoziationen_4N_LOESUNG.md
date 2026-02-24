# Kurztest: Assoziationen & ArrayList – Erweiterte 1:N-Szenarien - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: Sortierung in einer ArrayList (5 Punkte)

**Thema:** Erweiterte Funktionen – Sortieren von Objekten

### Musterlösung

**a) Comparator-Implementierung (2 Punkte)**

```java
Collections.sort(messungen, new Comparator<Messung>() {
    @Override
    public int compare(Messung m1, Messung m2) {
        return Double.compare(m1.getGewicht(), m2.getGewicht());
    }
});
```

**Alternativ (ebenfalls akzeptabel):**
```java
return (int)(m1.getGewicht() - m2.getGewicht());
// Hinweis: bei double-Differenzen können Rundungsfehler auftreten → Double.compare bevorzugt
```

**Bewertung (2 Punkte):**
- **2 Punkte:** `Double.compare(m1.getGewicht(), m2.getGewicht())` oder gleichwertige korrekte Rückgabe
- **1 Punkt:** Grundstruktur vorhanden, aber Rückgabewert unvollständig oder leicht fehlerhaft
- **0 Punkte:** Falsch oder nicht beantwortet

---

**b) Import-Anweisungen (2 Punkte)**

```java
import java.util.Collections;
import java.util.Comparator;
```

**Bewertung (2 Punkte):** je 1 Punkt pro korrektem Import

**Häufige Fehler:**
- `import java.util.*` → akzeptieren (1,5 Punkte, da beide abgedeckt)
- Nur ein Import → 1 Punkt

---

**c) Erklärung compare()-Rückgabe (1 Punkt)**

**Musterlösung:**
```
compare() muss einen negativen Wert zurückgeben (z. B. -1), damit m1 vor m2 einsortiert wird.
```

**Bewertung:**
- ✓ Negativer Wert (kleiner als 0) erwähnt → 1 Punkt
- ✓ „m1.getGewicht() < m2.getGewicht()" als Bedingung beschrieben → 1 Punkt
- ✗ Falsch oder nicht beantwortet → 0 Punkte

---

### Subtotale Aufgabe 1: max. 5 Punkte
- a) 2 Punkte
- b) 2 Punkte
- c) 1 Punkt

---

## Aufgabe 2: ArrayList-API – Methoden `contains` und `subList` (6 Punkte)

**Thema:** Vertiefung der API-Methoden

### Musterlösung (Zuordnung)

| Methodenaufruf | Richtiger Buchstabe | Bedeutung |
|----------------|----------------------|-----------|
| `messungen.contains(m1)` | **(a)** | Gibt `true` zurück, wenn das Objekt in der Liste vorhanden ist |
| `messungen.subList(1, 3)` | **(b)** | Gibt eine Teilliste von Index 1 bis 3 (exklusiv) zurück |
| `messungen.set(2, neueMessung)` | **(c)** | Ersetzt das Element an Index 2 durch ein neues Objekt |
| `messungen.indexOf(m1)` | **(d)** | Gibt den Index des ersten Vorkommens zurück (oder -1) |
| `messungen.add(1, neueMessung)` | **(e)** | Fügt ein Element an einer bestimmten Position ein |
| `messungen.isEmpty()` | **(f)** | Prüft, ob die Liste leer ist |

### Bewertung (6 Punkte)

- **6 Punkte:** Alle 6 Zuordnungen korrekt
- **5 Punkte:** 5 von 6 richtig
- **4 Punkte:** 4 von 6 richtig
- **3 Punkte:** 3 von 6 richtig
- **2 Punkte:** 2 von 6 richtig
- **1 Punkt:** 1 von 6 richtig
- **0 Punkte:** Keine oder alle falsch

---

## Aufgabe 3: Komplexe Code-Analyse (Iteration & Modifikation) (8 Punkte)

**Thema:** Zugriffen und Manipulationen in einer ArrayList

### Musterlösungen

**a) Erste Ausgabe (2 Punkte)**

```
Anzahl über 77 kg: 2
```

**Erklärung:** 80.0 > 77 ✓, 75.0 > 77 ✗, 90.0 > 77 ✓, 72.0 > 77 ✗ → count = 2

**Bewertung:**
- ✓ `2` oder `Anzahl über 77 kg: 2` → 2 Punkte
- ✓ korrekte Antwort mit Begründung → 2 Punkte
- ~ `Anzahl über 77 kg: 3` (häufiger Fehler durch Verwechslung ≥ statt >) → 0 Punkte
- ✗ andere Zahl → 0 Punkte

---

**b) Zweite Ausgabe (1 Punkt)**

```
Erste Messung: 80.0
```

**Bewertung:**
- ✓ `80.0` oder `Erste Messung: 80.0` → 1 Punkt
- ✗ andere Zahl → 0 Punkte

---

**c) Dritte Ausgabe (1 Punkt)**

```
Letzte Messung: 72.0
```

**Bewertung:**
- ✓ `72.0` oder `Letzte Messung: 72.0` → 1 Punkt
- ✗ andere Zahl → 0 Punkte

---

**d) Erklärung messungen.size() - 1 (2 Punkte)**

**Musterlösung:**
```
ArrayList-Indizes beginnen bei 0. Bei einer Liste mit n Elementen hat das letzte Element
den Index n-1, also messungen.size()-1. Das ist der höchste gültige Index.
```

**Bewertung:**
- **2 Punkte:** 0-basierte Indizierung erklärt + size()-1 als letzter gültiger Index
- **1 Punkt:** Grundidee erkannt (z. B. „letztes Element hat Index n-1")
- **0 Punkte:** Falsch oder nicht beantwortet

---

**e) Leere Liste bei get(0) (2 Punkte)**

**Musterlösung:**
```
Es würde eine IndexOutOfBoundsException ausgelöst, weil Index 0 bei einer leeren
Liste nicht existiert. Man sollte vorher mit isEmpty() oder size() > 0 prüfen.
```

**Bewertung:**
- **2 Punkte:** `IndexOutOfBoundsException` genannt + Empfehlung zur Prüfung
- **1 Punkt:** Fehler erkannt, aber nicht korrekt benannt oder keine Prüfempfehlung
- **0 Punkte:** Falsch oder nicht beantwortet

---

### Subtotale Aufgabe 3: max. 8 Punkte
- a) 2 Punkte
- b) 1 Punkt
- c) 1 Punkt
- d) 2 Punkte
- e) 2 Punkte

---

## Aufgabe 4: Dynamische Testdaten mit for-Schleife (6 Punkte)

**Thema:** Generierung und Ausgabe von Testdaten

### Musterlösungen

**a) Erstell-Schleife (3 Punkte)**

```java
Person person = new Person("Test", "Person", 25, "Mann");

for (int i = 0; i < 5; i++) {
    double gewicht = 60.0 + (i * 2.0);
    person.addMessung(new Messung(gewicht, 1.75));
}
```

**Alternativ:**
```java
for (double gewicht = 60.0; gewicht <= 68.0; gewicht += 2.0) {
    person.addMessung(new Messung(gewicht, 1.75));
}
```

**Bewertung (3 Punkte):**
- **3 Punkte:** Schleife über 5 Iterationen, Gewicht korrekt berechnet (+2.0 je Durchlauf), addMessung() aufgerufen
- **2 Punkte:** Schleife grundsätzlich richtig, aber Gewichtsformel oder Anzahl leicht falsch
- **1 Punkt:** Grundidee vorhanden, mehrere Fehler
- **0 Punkte:** Falsch oder nicht beantwortet

---

**b) Ausgabeschleife (2 Punkte)**

```java
List<Messung> messungen = person.getMessungen();
for (int i = 0; i < messungen.size(); i++) {
    System.out.println("Messung " + (i + 1) + ": " + messungen.get(i).getGewicht() + " kg");
}
```

**Bewertung (2 Punkte):**
- **2 Punkte:** Schleife mit 1-basierter Nummerierung (`i + 1`), Gewicht über `getGewicht()` ausgegeben
- **1 Punkt:** Ausgabe vorhanden, aber Index-Offset falsch (0-basiert statt 1-basiert)
- **0 Punkte:** Falsch oder nicht beantwortet

**Häufige Fehler:**
- `System.out.println("Messung " + i + ": ...")` (0-basiert statt 1-basiert) → 1 Punkt
- `for (Messung m : messungen)` (for-each, kein Index) → 1 Punkt (kein i+1 möglich)

---

**c) Anzahl erstellter Objekte (1 Punkt)**

```
5
```

**Bewertung:**
- ✓ `5` → 1 Punkt
- ✗ andere Zahl → 0 Punkte

---

### Subtotale Aufgabe 4: max. 6 Punkte
- a) 3 Punkte
- b) 2 Punkte
- c) 1 Punkt

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte | Gewichtung |
|---------|------------|-----------|
| 1. Sortierung in einer ArrayList | 5 | 20% |
| 2. ArrayList-API (contains, subList) | 6 | 24% |
| 3. Komplexe Code-Analyse | 8 | 32% |
| 4. Dynamische Testdaten | 6 | 24% |
| **GESAMT** | **25** | **100%** |

---

## Bewertungsskala

| Prozent | Note | Bedeutung |
|---------|------|-----------|
| 90-100% | 1 | Sehr gut |
| 80-89% | 2 | Gut |
| 70-79% | 3 | Befriedigend |
| 60-69% | 4 | Ausreichend |
| 50-59% | 5 | Mangelhaft |
| < 50% | 6 | Ungenügend |

**Notenschlüssel für diesen Test:**
- **ab 23 Punkte (92%):** Note 1
- **ab 20 Punkte (80%):** Note 2
- **ab 18 Punkte (72%):** Note 3
- **ab 15 Punkte (60%):** Note 4
- **ab 13 Punkte (52%):** Note 5
- **unter 13 Punkte:** Note 6

---

## Häufige Schülerfehler & Wertungen

### Fehler 1: Falsche Comparator-Rückgabe
```java
return m1.getGewicht() - m2.getGewicht();  // ← Rückgabetyp int, aber double-Differenz
```
**Handling:** Konzept richtig, aber Typproblem → 1 Punkt abziehen

### Fehler 2: Off-by-One bei subList
```java
messungen.subList(1, 3)  // Gibt Elemente an Index 1 und 2 zurück (nicht 3!)
```
**Handling:** Als korrekte Erklärung akzeptieren, wenn explizit „exklusiv" oder „nicht 3" erwähnt

### Fehler 3: 0-basierte Ausgabe in Aufgabe 4b
```java
System.out.println("Messung " + i + ": ...");  // Startet bei 0
```
**Handling:** 1 Punkt abziehen (1-basierte Nummerierung gefordert)

### Fehler 4: count-Bedingung mit >=
```java
if (messungen.get(i).getGewicht() >= 77.0) { count++; }  // Anstatt >
```
**Handling:** Führt zu count=2 vs count=2 (gleich, da kein Wert exakt 77.0) – kein Abzug nötig

---

## Tipps zur schnellen Korrektur

1. **Aufgabe 1a:** `Double.compare()` oder sinnvolle Differenz? Import `java.util.Collections` + `Comparator`?
2. **Aufgabe 2:** 6 Buchstaben schnell mit Musterlösung abgleichen
3. **Aufgabe 3a:** Zähle selbst: 80 > 77 ✓, 75 ✗, 90 ✓, 72 ✗ → 2
4. **Aufgabe 3b-c:** Gewichte aus Code ablesen (80.0 und 72.0)
5. **Aufgabe 4a:** Schleife 5× mit +2.0? `addMessung()` aufgerufen?
6. **Aufgabe 4b:** `(i + 1)` in der Ausgabe vorhanden?

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Zielgruppe:** Schüler nach Lerneinheit erweiterte ArrayList-Operationen  
**Voraussetzungen:** 1:N-Beziehungen, ArrayList-Grundlagen, Comparator, for-Schleifen bekannt
