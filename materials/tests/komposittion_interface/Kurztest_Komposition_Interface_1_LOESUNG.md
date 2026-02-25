# Kurztest: Komposition & Interface in Java - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: Komposition vs. Vererbung (4 Punkte)

### Aufgabenstellung

**Thema:** Beziehungen richtig modellieren

Gegeben sind folgende Szenarien. Entscheide, ob **Komposition** oder **Vererbung** besser passt. Schreibe **K** (Komposition) oder **V** (Vererbung) in die Klammer.

1) Ein `Auto` hat einen `Motor` (Motor kann ausgetauscht werden). ( )
2) Ein `Hund` ist ein `Tier`. ( )
3) Ein `Rechnung` hat mehrere `Rechnungspositionen`. ( )
4) Ein `Mitarbeiter` ist eine `Person`. ( )

Begründe in 1-2 Sätzen, warum Komposition oft flexibler ist als Vererbung:

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösung

1) Auto hat Motor → **K**
2) Hund ist Tier → **V**
3) Rechnung hat Positionen → **K**
4) Mitarbeiter ist Person → **V**

**Erklärung (2 Punkte):**
```
Komposition ist flexibler, weil Objekte zusammengesetzt und austauschbar sind.
Vererbung ist starrer, da Unterklassen an die Oberklasse gebunden sind.
```

### Bewertung (4 Punkte)

- **2 Punkte:** Alle 4 Zuordnungen korrekt
- **1 Punkt:** 3 von 4 korrekt
- **0 Punkte:** ≤ 2 korrekt

**Erklärung (2 Punkte):**
- **2 Punkte:** Flexibilität/ Austauschbarkeit genannt
- **1 Punkt:** Grundidee erkannt
- **0 Punkte:** Falsch

---

## Aufgabe 2: Interface definieren (5 Punkte)

### Aufgabenstellung

**Thema:** Gemeinsame Fähigkeiten über Interfaces

Erstelle ein Interface `Zahlbar`, das eine Methode `berechneBetrag()` vorschreibt.

```java
// Interface hier schreiben:



```

Erkläre in 1-2 Sätzen, warum ein Interface in diesem Fall sinnvoller ist als eine Oberklasse:

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösung

```java
public interface Zahlbar {
    double berechneBetrag();
}
```

**Erklärung (2 Punkte):**
```
Ein Interface erlaubt es, unterschiedliche Klassen mit der gleichen Fähigkeit
zu versehen, ohne eine gemeinsame Oberklasse zu erzwingen.
Mehrfach-Implementierung ist möglich, Vererbung nicht.
```

### Bewertung (5 Punkte)

- **3 Punkte:** Interface korrekt mit Methode
- **2 Punkte:** Erklärung sinnvoll

---

## Aufgabe 3: Komposition in der Praxis (5 Punkte)

### Aufgabenstellung

**Thema:** Bestellung mit Positionen

Vervollständige die Klasse `Bestellung`, die eine Liste von `Position`-Objekten verwaltet:

```java
public class Bestellung {
    private List<Position> positionen;

    public Bestellung() {
        // Initialisierung hier:

    }

    public void addPosition(Position p) {
        // Position hinzufügen:

    }

    public double getGesamtpreis() {
        // Summe aller Positionen berechnen:

    }
}
```

Erkläre kurz, warum hier Komposition verwendet wird:

___________________________________________________________________________

---

### Musterlösung

```java
public class Bestellung {
    private List<Position> positionen;

    public Bestellung() {
        this.positionen = new ArrayList<>();
    }

    public void addPosition(Position p) {
        this.positionen.add(p);
    }

    public double getGesamtpreis() {
        double summe = 0;
        for (Position p : positionen) {
            summe += p.getPreis();
        }
        return summe;
    }
}
```

**Erklärung (1 Punkt):**
```
Bestellung besteht aus Positionen. Positionen sind Teil der Bestellung und können
verwaltet und ausgetauscht werden.
```

### Bewertung (5 Punkte)

- **4 Punkte:** Code korrekt (Initialisierung, add, Summe)
- **1 Punkt:** Begründung korrekt

---

## Aufgabe 4: Interface nutzen (5 Punkte)

### Aufgabenstellung

**Thema:** Polymorphie mit Interfaces

Gegeben sind die Klassen `Produkt`, `Dienstleistung` und `Abo`, die alle `Zahlbar` implementieren.

```java
List<Zahlbar> posten = new ArrayList<>();
posten.add(new Produkt("Laptop", 900));
posten.add(new Dienstleistung("Beratung", 120));
posten.add(new Abo("Cloud", 15));

double summe = 0;
for (Zahlbar z : posten) {
    summe += z.berechneBetrag();
}

System.out.println("Summe: " + summe);
```

**Fragen:**

a) Warum funktioniert die for-Schleife mit Objekten unterschiedlicher Klassen?

___________________________________________________________________________

___________________________________________________________________________

b) Was wird ausgegeben, wenn die Beträge 900, 120 und 15 sind?

Antwort: ___________________________________________________________________

---

### Musterlösung

**a) Erklärung (3 Punkte):**
```
Die Liste ist vom Typ Zahlbar. Alle Objekte implementieren dieses Interface,
daher können sie polymorph verarbeitet werden.
```

**b) Ausgabe (2 Punkte):**
```
Summe: 1035
```

### Bewertung (5 Punkte)

- **3 Punkte:** Erklärung korrekt
- **2 Punkte:** Summe korrekt

---

## Aufgabe 5: Komposition + Interface kombinieren (4 Punkte)

### Aufgabenstellung

**Thema:** Zahlungsabwicklung

Ein `Warenkorb` enthält `WarenkorbPosition`-Objekte. Jede Position ist `Zahlbar`.

Schreibe eine Methode, die die Summe aller Positionen berechnet:

```java
public double berechneSumme(List<Zahlbar> positionen) {
    // Methode hier schreiben:



}
```

---

### Musterlösung

```java
public double berechneSumme(List<Zahlbar> positionen) {
    double summe = 0;
    for (Zahlbar z : positionen) {
        summe += z.berechneBetrag();
    }
    return summe;
}
```

### Bewertung (4 Punkte)

- **4 Punkte:** Schleife + Summe korrekt
- **2 Punkte:** Grundidee erkannt, kleine Fehler
- **0 Punkte:** Falsch

---

## Aufgabe 6: Fehlersuche & Analyse (2 Punkte)

### Aufgabenstellung

**Thema:** Typfehler mit Interfaces

Gegeben ist folgender Code:

```java
List<Zahlbar> posten = new ArrayList<>();
posten.add(new Produkt("Maus", 25));

for (Produkt p : posten) {  // ← FEHLER
    System.out.println(p.berechneBetrag());
}
```

a) Was ist der Fehler?

___________________________________________________________________________

b) Schreibe die korrekte Schleife:

```java
// Korrekt:

```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Komposition vs. Vererbung | 4 | |
| 2. Interface definieren | 5 | |
| 3. Komposition in der Praxis | 5 | |
| 4. Interface nutzen | 5 | |
| 5. Kombination Komposition + Interface | 4 | |
| 6. Fehlersuche | 2 | |
| **Gesamt** | **25** | |

### Musterlösung

**a) Fehler (1 Punkt):**
```
posten ist eine List<Zahlbar>, nicht List<Produkt>. Der for-each-Typ passt nicht.
```

**b) Korrekte Schleife (1 Punkt):**

```java
for (Zahlbar p : posten) {
    System.out.println(p.berechneBetrag());
}
```

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte |
|---------|------------|
| 1. Komposition vs. Vererbung | 4 |
| 2. Interface definieren | 5 |
| 3. Komposition in der Praxis | 5 |
| 4. Interface nutzen | 5 |
| 5. Kombination Komposition + Interface | 4 |
| 6. Fehlersuche | 2 |
| **Gesamt** | **25** |

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

## Häufige Fehler

### Fehler 1: Interface mit Implementierung
```java
public interface Zahlbar {
    public double berechneBetrag() { return 0; }  // ← Interface darf keine Implementierung
}
```
**Handling:** 1 Punkt Abzug in Aufgabe 2

### Fehler 2: Komposition falsch verstanden
```java
class Bestellung extends Position { }  // ← Falsch, Bestellung hat Positionen
```
**Handling:** 0 Punkte für Aufgabe 3

### Fehler 3: Falscher Schleifentyp
```java
for (Produkt p : posten) { ... }  // ← posten ist List<Zahlbar>
```
**Handling:** 0 Punkte in Aufgabe 6

---

## Lernziele

✓ **Komposition verstehen:** Objekt aus anderen Objekten zusammensetzen  
✓ **Interface nutzen:** Gemeinsame Fähigkeiten ohne Vererbung  
✓ **Polymorphie mit Interfaces:** Einheitliche Verarbeitung  
✓ **Fehlerdiagnose:** Typfehler und falsche Modellierung  

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Zielgruppe:** Schüler nach Lerneinheit Komposition & Interface  
**Voraussetzungen:** Klassen, Listen, Interfaces, Grundprinzipien OOP
