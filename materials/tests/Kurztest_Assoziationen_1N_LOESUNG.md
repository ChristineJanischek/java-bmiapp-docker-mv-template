# Kurztest: Assoziationen & ArrayList (1:N Beziehung) - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: ArrayList-Deklaration (3 Punkte)

### Musterlösung

```java
List<Messung> messungen = new ArrayList<Messung>();

// Oder die moderne Variante:
List<Messung> messungen = new ArrayList<>();
```

### Erklärung

ArrayList wird verwendet, da die Anzahl der Messungen vorher nicht bekannt ist. Arrays haben eine feste Größe, ArrayList passt sich dynamisch an.

### Bewertung (3 Punkte)

- **3 Punkte:** Korrekte Deklaration mit `List<Messung>` UND `new ArrayList<>()` sowie sinnvolle Erklärung
- **2 Punkte:** ArrayList ist deklariert/initialisiert, aber:
  - Nur ArrayList ohne Interface, ODER
  - Erklärung ist unvollständig
- **1 Punkt:** Ansatz erkennbar, aber gravierendes Problem (z.B. `ArrayList messung` oder falsche Deklaration)
- **0 Punkte:** Nicht beantwortet oder völlig falsch

**Häufige Schülerfehler:**
- `ArrayList<Messung> messungen = new ArrayList<Messung>();` ✓ (akzeptieren)
- `List messungen = new ArrayList();` (Punkt abziehen wegen fehlender Generics)
- `Messung[] messungen = new ArrayList();` ✗ (falsch - nicht als Lösung akzeptieren)

---

## Aufgabe 2: ArrayList-Methoden (4 Punkte)

### Musterlösung (Zuordnung)

| Methode | Beschreibung |
|---------|-------------|
| `add(Object)` | **(b)** | Fügt ein Element am Ende hinzu |
| `get(int index)` | **(c)** | Gibt das Element an Position index zurück |
| `remove(int index)` | **(d)** | Entfernt das Element an Position index |
| `size()` | **(a)** | Gibt die Anzahl der Elemente zurück |

### Bewertung (4 Punkte)

- **4 Punkte:** Alle 4 Zuordnungen korrekt
- **3 Punkte:** 3 von 4 richtig
- **2 Punkte:** 2 von 4 richtig
- **1 Punkt:** 1 von 4 richtig
- **0 Punkte:** Keine oder alle falsch

**Lösung in Kurzform für schnelle Korrektur:**
```
add → Einfügen
get → Lesen
remove → Löschen
size → Anzahl
```

---

## Aufgabe 3: Code-Analyse (5 Punkte)

### Musterlösungen

**a) Ausgabe der Anzahl Messungen (1 Punkt)**

```
Anzahl Messungen: 3
```

**Bewertung (1 Punkt):**
- ✓ Akzeptabel: `3`, `Anzahl Messungen: 3`, `3 Messungen`
- ✗ Falsch: Andere Zahlen, oder komplette falsche Erklärung

---

**b) Welches Gewicht bei get(2)? (1,5 Punkte)**

```
64.0
```

**Erklärung:** Index 2 bedeutet das 3. Element (0-indexierung: 0=m1, 1=m2, 2=m3).
m3 hat 64.0 kg.

**Bewertung (1,5 Punkte):**
- ✓ `64.0` oder `64` → 1,5 Punkte
- ✓ `64.0 kg` → 1,5 Punkte
- ~ `m3` oder nur Erklärung ohne Zahl → 0,5 Punkte
- ✗ `67.0` oder `65.0` → 0 Punkte (falsche Indexierung)

---

**c) Erklärung getMessungen().get(2) (2,5 Punkte)**

**Musterlösung:**
```
getMessungen() gibt die ArrayList aller Messungen zurück.
.get(2) greift auf das Element mit Index 2 zu (das 3. Element).
Zusammen wird die dritte Messung (m3) der Person zurückgegeben.
```

**Bewertung (2,5 Punkte):**
- **2,5 Punkte:** Beide Teile (getMessungen + get(2)) erklärt, Index/Position auch erwähnt
- **2 Punkte:** Grundsätzliche Idee richtig, aber unvollständig oder leicht unklar
- **1 Punkt:** Nur teilweise richtig (z.B. nur getMessungen erklärt)
- **0 Punkte:** Falsch oder nicht beantwortet

---

### Subtotale Aufgabe 3: max. 5 Punkte
- a) 1 Punkt
- b) 1,5 Punkte
- c) 2,5 Punkte

---

## Aufgabe 4: Schleife & Ausgabe (5 Punkte)

### Musterlösungen

**Option 1: for-Schleife (empfohlen)**
```java
for (int i = 0; i < messungen.size(); i++) {
    Messung messung = messungen.get(i);
    System.out.println("Gewicht: " + messung.getGewicht());
}
```

**Option 2: for-each Schleife (auch gut)**
```java
for (Messung messung : messungen) {
    System.out.println("Gewicht: " + messung.getGewicht());
}
```

**Option 3: while-Schleife (auch akzeptabel)**
```java
int i = 0;
while (i < messungen.size()) {
    Messung messung = messungen.get(i);
    System.out.println("Gewicht: " + messung.getGewicht());
    i++;
}
```

### Bewertung Hauptaufgabe (4 Punkte)

- **4 Punkte:** 
  - Schleife iteriert über alle Elemente korrekt
  - Zugriff mit get() oder for-each
  - Ausgabe mit Gewicht und sinnvoll strukturiert
  
- **3 Punkte:**
  - Schleife funktioniert grundsätzlich, aber:
    - Kleine Fehler (z.B. off-by-one Error, aber logisch erkennbar)
    - Oder: getGewicht() vergessen, aber Konzept verstanden
  
- **2 Punkte:**
  - Schelfen-Konzept erkannt, aber:
    - Syntax-Fehler
    - Oder: unvollständige Implementierung
  
- **1 Punkt:** Grundidee vorhanden, aber viele Fehler
- **0 Punkte:** Nicht beantwortet oder völlig falsch

### Bewertung Bonus (max. +1 Punkt)

```java
System.out.println((i+1) + ". Messung - Gewicht: " + messung.getGewicht());
```

**Bonus-Kriterien:**
- ✓ **+1 Punkt:** Augabe mit Index/Nummer und Gewicht kombiniert
- ✓ **+0,5 Punkte:** Nur teilweise (z.B. nur Index ohne Gewicht)
- ✗ **+0 Punkte:** Unkorrekt oder vergessen

---

## Aufgabe 5: Methode verstehen (4 Punkte)

### Musterlösung

**a) Methodensignatur (1 Punkt)**

```java
public double getDurchschnittsBmi()
```

**Erklärung:** 
- `public`: Die Methode ist von außen erreichbar
- `double`: Rückgabewert ist eine Dezimalzahl (BMI)
- `getDurchschnittsBmi`: Aussagekräftiger Name mit Getter-Konvention
- `()`: Keine Parameter nötig (Daten sind in der ArrayList vorhanden)

**Bewertung (1 Punkt):**
- ✓ `public double getDurchschnittsBmi()` → 1 Punkt
- ✓ `double getDurchschnittsBmi()` (public nicht geschrieben) → 0,5 Punkte
- ✗ `void getDurchschnittsBmi()` → 0 Punkte (falscher Rückgabewert)
- ✗ `double durchschnittsBmi` → 0 Punkte (ist Methode nicht erkannt)

---

**b) Erklärung (3 Punkte)**

**Musterlösung:**
```
Die Methode iteriert über alle Messungen der ArrayList.
Sie addiert alle BMI-Werte auf und teilt sie durch die Anzahl der Messungen.
Das Ergebnis ist der durchschnittliche BMI der Person über alle Messzeitpunkte.
```

**Bewertung (3 Punkte):**
- **3 Punkte:** 
  - Iteration über ListView erkannt
  - Summe/Addition erwähnt
  - Division durch Anzahl erwähnt
  - Oder: Logischer Algorithmus beschrieben
  
- **2 Punkte:**
  - Grundidee richtig, aber:
    - Nicht alle Schritte erklären, oder
    - Etwas unklar formuliert
  
- **1 Punkt:** Grundidee vorhanden, aber mangelhaft
- **0 Punkte:** Falsch oder nicht beantwortet

---

### Subtotale Aufgabe 5: max. 4 Punkte

---

## Aufgabe 6: Fehlersuche & Analyse (4 Punkte)

### Musterlösungen

**a) Fehler 1: person.messungen.add(m1) (1,5 Punkte)**

**Problem:**
```
Das Attribut 'messungen' ist private und nicht direkt von außen erreichbar.
Man muss die öffentliche Methode addMessung() verwenden.
```

**Bewertung (1,5 Punkte):**
- ✓ `private ist nicht von außen erreichbar` → 1,5 Punkte
- ✓ `Muss Getter/Setter oder Methode addMessung() verwenden` → 1,5 Punkte
- ✓ `Kapselung/Sichtbarkeitsproblem erkannt` → 1,5 Punkte
- ~ `ArrayList nicht direkt nutzbar` → 0,5 Punkte (ungenau, aber Ansatz erkannt)
- ✗ `ArrayList hat keine messungen` → 0 Punkte (falsch)

---

**b) Fehler 2: person.getMessungen().get(5) (1 Punkt)**

**Problem:**
```
Es gibt nur 1 Messung (m1), die beim Index 0 ist.
Index 5 existiert nicht → IndexOutOfBoundsException.
Man darf nur auf Index 0 zugreifen oder vorher prüfen.
```

**Bewertung (1 Punkt):**
- ✓ `Index 5 existiert nicht / zu groß` → 1 Punkt
- ✓ `In Java nur 0-1 vorhanden, aber 5 angefordert` → 1 Punkt
- ✓ `IndexOutOfBoundsException` → 1 Punkt
- ~ `Zu viele Elemente` → 0,5 Punkte (ungenau)
- ✗ `ArrayList ist zu klein` → keine Punkte (Schreibweise ist vage)

---

**c) Korrekte Version von Fehler 1 (1,5 Punkte)**

**Musterlösung:**
```java
person.addMessung(m1);
```

**Bewertung (1,5 Punkte):**
- ✓ `person.addMessung(m1);` → 1,5 Punkte
- ✓ `person.getMessungen().add(m1);` (auch akzeptabel) → 1,5 Punkte
- ~ `person.messungen = m1;` (wenn private gewesen wäre, aber trotzdem unkorrekt) → 0 Punkte
- ✗ `person.add(m1);` → 0 Punkte (falsch)

---

### Subtotale Aufgabe 6: max. 4 Punkte
- a) 1,5 Punkte
- b) 1 Punkt
- c) 1,5 Punkte

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte | Gewichtung |
|---------|------------|-----------|
| 1. ArrayList-Deklaration | 3 | 12% |
| 2. ArrayList-Methoden | 4 | 16% |
| 3. Code-Analyse | 5 | 20% |
| 4. Schleife & Ausgabe | 5 | 20% |
| 5. Methode verstehen | 4 | 16% |
| 6. Fehlersuche | 4 | 16% |
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

## Läufige Schülerfehler & Wertungen

### Fehler 1: ArrayList ohne Generics
```java
ArrayList messungen = new ArrayList();  // Ohne <Messung>
```
**Handling:** Punkt abziehen, kann aber akzeptiert werden mit Note -0,5 Punkte

### Fehler 2: Array statt ArrayList
```java
Messung[] messungen = new Messung[10];
```
**Handling:** Nicht als Lösung für ArrayList-Aufgaben akzeptieren → 0 Punkte für die Aufgabe

### Fehler 3: get(index) vergessen
```java
for (int i = 0; i < messungen.size(); i++) {
    System.out.println(messungen);  // ← Ganze Liste, nicht einzelnes Element
}
```
**Handling:** 1 Punkt abziehen (Konzept da, aber Umsetzung falsch)

### Fehler 4: Off-by-One Error
```java
for (int i = 1; i <= messungen.size(); i++) {  // Startet bei 1 statt 0
    System.out.println(messungen.get(i));      // ← Index zu groß
```
**Handling:** 1 Punkt abziehen (Code funktioniert nicht)

### Fehler 5: Falsches Index-Verständnis
```
"Index 2 ist die 2. Messung" ← FALSCH, ist die 3. (0-basiert)
```
**Handling:** Concept falsch verstanden → weniger Punkte (0,5)

---

## Tipps zur schnellen Korrektur

### Korrektur-Workflow (ca. 2-3 Min pro Test)

1. **Aufgabe 1:** Schneller Check - ArrayList mit <Messung>? JA (+3) NEIN (→ 0-2)
2. **Aufgabe 2:** 4 Zuordnungen zählen - schnell verifizierbar
3. **Aufgabe 3a-b:** Nur Zahlen: `3` und `64.0` prüfen
4. **Aufgabe 3c:** Stichwort "Assoziation" oder "get" + "Index" → Punkt gegeben
5. **Aufgabe 4:** Schleife läuft? `.get()` oder `for-each`? Ausgabe sinnvoll?
6. **Aufgabe 5a:** Nur Methodensignatur prüfen (public double ...)
7. **Aufgabe 5b:** Stichwörter: "Iteration", "Summe", "Division" erkennen
8. **Aufgabe 6a:** Stichwort "private" oder "addMessung-Methode" → Punkt
9. **Aufgabe 6b:** Stichwort "Index 5 illegal" oder "zu groß" → Punkt
10. **Aufgabe 6c:** Nur `person.addMessung(m1)` prüfen

### Checklisten für Korrektor

#### Aufgabe 1 Kurzcheck:
- [ ] `List<Messung>` oder `ArrayList<Messung>`?
- [ ] `new ArrayList<>()`?
- [ ] Erklärung erkennbar?

#### Aufgabe 4 Kurzcheck:
- [ ] Schleife über `.size()` oder `for-each`?
- [ ] `.get(i)` verwendet?
- [ ] `System.out.println()` mit Gewicht?
- [ ] Bonus: Index/Nummer ausgegeben?

**Geschätzte Korrektur:** ~2:30 Min pro Test × Klassengröße

---

## Differenzierungsvorschläge für Lehrer

### Zusatzaufgaben für schnelle Schüler (+5 Bonuspunkte)

**Aufgabe Z1: Filterung**
```
Schreibe Code (Pseudocode ist OK), der nur Messungen mit 
Kategorie "Normalgewicht" ausgibt.
```

**Aufgabe Z2: Fehlerbehandlung**
```
Wie kann man verhindern, dass get(100) einen Fehler verursacht?
Schreibe eine Sicherheitsabfrage.
```

**Aufgabe Z3: M:N Beziehung**
```
Erkläre in 2-3 Sätzen, wie eine M:N Beziehung (Ärzte ↔ Patienten) 
mit zwei ArrayLists funktioniert.
```

### Vereinfachte Version für schwäche Schüler (Basis 15 Punkte)

Entferne Aufgaben 5 und 6, konzentriere dich auf:
- Deklaration (Aufgabe 1)
- Methoden (Aufgabe 2)
- Praktische Nutzung (Aufgaben 3 & 4)

---

## Tipps für die nächste Testversion

1. **Code-Modus:** Schüler dürfen auf Papier Pseudocode schreiben
2. **Multiple Choice:** Für Aufgabe 2 & 3 noch einfacher korrigierbar
3. **Halboffene Fragen:** Mit Auswahl-Elementen (Zahl + Text)
4. **Testdauer:** Kann auf 20 Min reduziert werden mit Vereinfachung
5. **Gewichtung:** Java-Klassen mehr Gewicht, weniger Theorie

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Zielgruppe:** Schüler nach Lerneinheit Version 3+ Assoziationen  
**Voraussetzungen:** ArrayList, 1:N Beziehungen, Person/Messung-Klassen bekannt
