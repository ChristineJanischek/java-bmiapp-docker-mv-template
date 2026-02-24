# Kurztest: Assoziationen & ArrayList - Bücherei und Bücher - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: ArrayList-Deklaration (3 Punkte)

### Musterlösung

```java
List<Buch> buecher = new ArrayList<Buch>();
```

oder

```java
private ArrayList<Buch> buecher = new ArrayList<>();
```

**Erklärung (1 Punkt):**
```
ArrayList wird verwendet, weil die Anzahl der Bücher nicht bekannt ist und sich 
dynamisch ändern kann. Arrays haben eine feste Größe, ArrayList ist flexibel.
```

### Bewertung (3 Punkte)

**Deklaration (2 Punkte):**
- ✓ `List<Buch> buecher = new ArrayList<Buch>();` → 2 Punkte
- ✓ `ArrayList<Buch> buecher = new ArrayList<>();` → 2 Punkte
- ✓ Ähnliche Varianten mit `private` → 2 Punkte
- ~ Fehlende Generics (`List buecher`) → 1 Punkt
- ✗ Falsch oder Array statt ArrayList → 0 Punkte

**Erklärung (1 Punkt):**
- ✓ Dynamische Größe / flexibel erwähnt → 1 Punkt
- ~ Ansatz erkennbar → 0,5 Punkte
- ✗ Falsch oder nicht beantwortet → 0 Punkte

---

## Aufgabe 2: ArrayList-Methoden (4 Punkte)

### Musterlösung (Zuordnung)

| Methode | Richtiger Buchstabe |
|---------|----------------------|
| `add(Buch)` | **(b)** |
| `get(int index)` | **(c)** |
| `remove(int index)` | **(d)** |
| `size()` | **(a)** |

### Bewertung (4 Punkte)

- **4 Punkte:** Alle 4 Zuordnungen korrekt
- **3 Punkte:** 3 von 4 richtig
- **2 Punkte:** 2 von 4 richtig
- **1 Punkt:** 1 von 4 richtig
- **0 Punkte:** Keine oder alle falsch

---

## Aufgabe 3: Code-Analyse (5 Punkte)

### Musterlösungen

**a) Ausgabe (2 Punkte)**

```
Bücher im Bestand: 3
```

**Bewertung:**
- ✓ `3` oder `Bücher im Bestand: 3` → 2 Punkte
- ✗ andere Zahl → 0 Punkte

---

**b) Zweites Buch (1,5 Punkte)**

```
1984
```

**Erklärung:** Index 1 ist das zweite Element (0-basiert): b1 (Index 0), b2 (Index 1) ← "1984"

**Bewertung:**
- ✓ `1984` oder `Zweites Buch: 1984` → 1,5 Punkte
- ✓ `Zweites Buch: 1984` → 1,5 Punkte
- ✗ anderer Titel → 0 Punkte

---

**c) Erklärung `.getBuecher().get(1)` (1,5 Punkte)**

**Musterlösung:**
```
getBuecher() gibt die Liste aller Bücher zurück. 
get(1) greift auf das Element an Index 1 zu, also das zweite Buch in der Liste.
```

**Bewertung:**
- **1,5 Punkte:** Beide Teile erklärt (Getter + Index-Zugriff)
- **1 Punkt:** Teilweise erklärt
- **0,5 Punkte:** Grundidee erkannt
- **0 Punkte:** Falsch oder nicht beantwortet

---

## Aufgabe 4: Schleife & Ausgabe (5 Punkte)

### Musterlösungen

**Grundaufgabe (4 Punkte):**

```java
List<Buch> buecher = buecherei.getBuecher();

for (Buch buch : buecher) {
    System.out.println(buch.getTitel() + " von " + buch.getAutor());
}
```

oder

```java
List<Buch> buecher = buecherei.getBuecher();

for (int i = 0; i < buecher.size(); i++) {
    System.out.println(buecher.get(i).getTitel() + " von " + buecher.get(i).getAutor());
}
```

**Bewertung (4 Punkte):**
- **4 Punkte:** Schleife korrekt (for-each oder for-Schleife), alle Bücher ausgegeben mit Titel und Autor
- **3 Punkte:** Schleife funktioniert, aber unvollständige Ausgabe
- **2 Punkte:** Grundkonzept erkannt, syntaktische Fehler
- **1 Punkt:** Versuch offensichtlich, viele Fehler
- **0 Punkte:** Falsch oder nicht beantwortet

---

**Bonus (+1 Punkt):**

```java
// Beispiel mit Index
for (int i = 0; i < buecher.size(); i++) {
    System.out.println((i + 1) + ". " + buecher.get(i).getTitel() + 
                       " von " + buecher.get(i).getAutor());
}
```

**Bewertung:**
- ✓ 1-basierte Nummerierung (`i + 1`) + Titel + Autor → +1 Punkt
- ✓ Ähnliche sinnvolle Ausgabe → +1 Punkt
- ✗ Nur Ansatz → 0 Punkte

---

## Aufgabe 5: Methode verstehen (4 Punkte)

### Musterlösung

**a) Methodensignatur (1,5 Punkte)**

```java
public int getAutorHaeufigkeit(String autor)
```

oder

```java
public int getAutorAnzahl(String autor)
```

**Bewertung:**
- ✓ `public int getAutorHaeufigkeit(String autor)` → 1,5 Punkte
- ✓ Sinnvolle Variante → 1,5 Punkte
- ~ `void` statt `int` → 0 Punkte
- ~ Parameter nicht mitgenommen → 0 Punkte

---

**b) Erklärung (2,5 Punkte)**

**Musterlösung:**
```
Die Methode iteriert durch alle Bücher in der ArrayList und zählt, wie viele 
davon den angegebenen Autor haben. Sie vergleicht jeweils buch.getAutor() mit 
dem Parameter autor und erhöht beim Treffer einen Zähler.
```

**Bewertung:**
- **2,5 Punkte:** Iteration + Vergleich + Zählung erklärt
- **1,5 Punkte:** Grundidee erkannt, aber unvollständig
- **0,5 Punkte:** Ansatz sichtbar
- **0 Punkte:** Falsch oder nicht beantwortet

---

## Aufgabe 6: Fehlersuche & Analyse (4 Punkte)

### Musterlösungen

**a) Fehler 1 - Direkter ArrayList-Zugriff (1 Punkt)**

**Musterlösung:**
```
Das Attribut 'buecher' ist private und nicht von außen erreichbar. 
Man muss die öffentliche Methode addBuch() verwenden.
```

**Bewertung:**
- ✓ Private + getter/Methode erwähnt → 1 Punkt
- ~ Encapsulation-Verletzung → 0,5 Punkte
- ✗ Falsch → 0 Punkte

---

**b) Fehler 2 - IndexOutOfBoundsException (1,5 Punkte)**

**Musterlösung:**
```
Der Index 10 existiert nicht (gültige Indizes sind 0-2 bei 3 Büchern). 
Zugriff auf einen nicht-existierenden Index wirft IndexOutOfBoundsException.
Man sollte vorher mit size() oder isEmpty() prüfen.
```

**Bewertung:**
- **1,5 Punkte:** IndexOutOfBoundsException + Begründung + Prüfempfehlung
- **1 Punkt:** Fehler erkannt, aber nicht korrekt benannt
- **0,5 Punkte:** Ansatz erkennbar
- **0 Punkte:** Falsch

---

**c) Korrekte Version (1,5 Punkte)**

**Musterlösung:**
```java
buecherei.addBuch(b1);
```

**Bewertung:**
- ✓ `buecherei.addBuch(b1)` → 1,5 Punkte
- ~ `buecherei.getBuecher().add(b1)` → 1 Punkt (funktioniert, aber nicht sauber)
- ✗ Falsch → 0 Punkte

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte |
|---------|------------|
| 1. ArrayList-Deklaration | 3 |
| 2. ArrayList-Methoden | 4 |
| 3. Code-Analyse | 5 |
| 4. Schleife & Ausgabe | 5 (+1 Bonus) |
| 5. Methode verstehen | 4 |
| 6. Fehlersuche | 4 |
| **GESAMT** | **25** |

---

## Bewertungsskala

| Prozent | Note |
|---------|------|
| 90-100% | 1 |
| 80-89% | 2 |
| 70-79% | 3 |
| 60-69% | 4 |
| 50-59% | 5 |
| < 50% | 6 |

**Notenschlüssel:**
- **ab 23 Punkte:** Note 1
- **ab 20 Punkte:** Note 2
- **ab 18 Punkte:** Note 3
- **ab 15 Punkte:** Note 4
- **ab 13 Punkte:** Note 5
- **unter 13 Punkte:** Note 6

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Zielgruppe:** Schüler nach Lerneinheit 1:N-Assoziationen (Bücherei/Buch)  
**Voraussetzungen:** ArrayList, Generics, for-Schleifen, get/set-Methoden bekannt
