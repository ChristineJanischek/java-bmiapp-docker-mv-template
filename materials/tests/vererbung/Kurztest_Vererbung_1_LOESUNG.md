# Kurztest: Vererbung in Java - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: Vererbungssyntax (4 Punkte)

### Aufgabenstellung

**Thema:** Grundlagen der Vererbung

Gegeben ist eine Oberklasse `Fahrzeug` und eine Unterklasse `Auto`.

a) Ergänze die Klassendefinition, damit `Auto` von `Fahrzeug` erbt:

```java
public class Fahrzeug {
    protected String marke;
    protected int baujahr;
    
    public Fahrzeug(String marke, int baujahr) {
        this.marke = marke;
        this.baujahr = baujahr;
    }
}

public class Auto __________________ {
    private int anzahlTueren;
    
    // Konstruktor hier
}
```

b) Erkläre in 1-2 Sätzen, was das Schlüsselwort `protected` bedeutet:

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösung

**a) Klassendefinition (2 Punkte)**

```java
public class Auto extends Fahrzeug {
    private int anzahlTueren;
    
    public Auto(String marke, int baujahr, int anzahlTueren) {
        super(marke, baujahr);
        this.anzahlTueren = anzahlTueren;
    }
}
```

**Bewertung (2 Punkte):**
- ✓ `extends Fahrzeug` korrekt → 2 Punkte
- ✗ Syntax-Fehler oder falsches Schlüsselwort → 0 Punkte

---

**b) Erklärung protected (2 Punkte)**

**Musterlösung:**
```
protected bedeutet, dass das Attribut/Methode in der eigenen Klasse, allen Unterklassen 
und im gleichen Package sichtbar ist. Es ist nicht public (für alle), aber auch nicht 
private (nur eigene Klasse).
```

**Bewertung:**
- **2 Punkte:** Zugriff in Unterklassen + eigene Klasse erwähnt
- **1 Punkt:** Teilweise richtig (z. B. nur "Unterklassen können zugreifen")
- **0 Punkte:** Falsch oder nicht beantwortet

---

## Aufgabe 2: Konstruktor mit super() (5 Punkte)

### Aufgabenstellung

**Thema:** Aufruf des Oberkonstruktors

Vervollständige den Konstruktor der Klasse `Auto`, sodass die Attribute der Oberklasse `Fahrzeug` korrekt initialisiert werden:

```java
public class Auto extends Fahrzeug {
    private int anzahlTueren;
    
    public Auto(String marke, int baujahr, int anzahlTueren) {
        // Hier den super()-Aufruf ergänzen:
        
        
        // Hier das eigene Attribut setzen:
        
    }
}
```

Erkläre in 1-2 Sätzen, warum `super()` als erste Anweisung im Konstruktor stehen muss:

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösung

```java
public class Auto extends Fahrzeug {
    private int anzahlTueren;
    
    public Auto(String marke, int baujahr, int anzahlTueren) {
        super(marke, baujahr);              // 2 Punkte
        this.anzahlTueren = anzahlTueren;   // 1 Punkt
    }
}
```

**Bewertung (3 Punkte für Code):**
- **2 Punkte:** `super(marke, baujahr)` korrekt aufgerufen
- **1 Punkt:** `this.anzahlTueren = anzahlTueren` korrekt gesetzt
- **0,5 Punkte Abzug:** super() nicht an erster Stelle

---

**Erklärung super() Position (2 Punkte):**

**Musterlösung:**
```
super() muss an erster Stelle stehen, weil zuerst die Oberklasse initialisiert 
werden muss, bevor die Unterklasse auf deren Attribute/Methoden zugreifen kann. 
Java erzwingt dies, um sicherzustellen, dass das Objekt vollständig konstruiert ist.
```

**Bewertung:**
- **2 Punkte:** Initialisierungsreihenfolge + Grund erklärt
- **1 Punkt:** Grundidee erkannt ("Oberklasse zuerst")
- **0 Punkte:** Falsch

---

## Aufgabe 3: Methoden überschreiben (5 Punkte)

### Aufgabenstellung

**Thema:** Überschreiben (Override) von Methoden

Gegeben ist folgende Methode in der Oberklasse `Fahrzeug`:

```java
public String getBeschreibung() {
    return "Fahrzeug: " + marke + " (" + baujahr + ")";
}
```

a) Schreibe eine überschriebene Version dieser Methode für die Klasse `Auto`, die zusätzlich die Anzahl der Türen ausgibt:

```java
// Überschriebene Methode:


```

b) Ordne die Begriffe den Beschreibungen zu. Schreibe den Buchstaben in die Klammer.

**Beschreibungen:**
- **(a)** Annotation, die das Überschreiben einer Methode kennzeichnet
- **(b)** Aufruf der Methode aus der Oberklasse
- **(c)** Methode in Unterklasse ersetzt die Methode der Oberklasse
- **(d)** Methode hat gleichen Namen aber andere Parameter

| Begriff | Buchstabe |
|---------|-----------|
| `@Override` | ( ) |
| `super.methode()` | ( ) |
| Überschreiben | ( ) |
| Überladen | ( ) |

---

### Musterlösung

**a) Überschriebene Methode (3 Punkte)**

```java
@Override
public String getBeschreibung() {
    return super.getBeschreibung() + ", Türen: " + anzahlTueren;
}
```

oder

```java
@Override
public String getBeschreibung() {
    return "Fahrzeug: " + marke + " (" + baujahr + "), Türen: " + anzahlTueren;
}
```

**Bewertung (3 Punkte):**
- **3 Punkte:** Methode korrekt überschrieben, Türen-Info hinzugefügt, @Override vorhanden
- **2 Punkte:** Methode funktioniert, aber @Override fehlt oder super.getBeschreibung() nicht genutzt
- **1 Punkt:** Grundkonzept erkannt, aber Fehler in Implementierung
- **0 Punkte:** Falsch

**Häufige Varianten:**
- Mit `super.getBeschreibung()` → bevorzugt (3 Punkte)
- Ohne `super.getBeschreibung()`, aber vollständig → akzeptabel (2,5 Punkte)

---

**b) Zuordnung (2 Punkte)**

| Begriff | Richtiger Buchstabe |
|---------|----------------------|
| `@Override` | **(a)** |
| `super.methode()` | **(b)** |
| Überschreiben | **(c)** |
| Überladen | **(d)** |

**Bewertung (2 Punkte):** 0,5 Punkte pro korrekter Zuordnung

---

## Aufgabe 4: Code-Analyse (5 Punkte)

### Aufgabenstellung

**Thema:** Polymorphie und Methodenaufruf

Gegeben ist folgender Code:

```java
Fahrzeug f1 = new Fahrzeug("Mercedes", 2020);
Auto a1 = new Auto("BMW", 2022, 4);
Fahrzeug f2 = new Auto("Audi", 2023, 2);

System.out.println(f1.getBeschreibung());
System.out.println(a1.getBeschreibung());
System.out.println(f2.getBeschreibung());
```

**Fragen:**

a) Was gibt die **erste** `System.out.println`-Zeile aus?

Antwort: ___________________________________________________________________

b) Was gibt die **zweite** `System.out.println`-Zeile aus (Auto überschreibt getBeschreibung)?

Antwort: ___________________________________________________________________

c) Was gibt die **dritte** `System.out.println`-Zeile aus? Welche Methode wird aufgerufen?

Antwort: ___________________________________________________________________

___________________________________________________________________________

d) Erkläre in 1-2 Sätzen, warum bei `f2` die Methode von `Auto` aufgerufen wird, obwohl der Typ `Fahrzeug` ist:

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösungen

**a) Erste Ausgabe (1 Punkt)**

```
Fahrzeug: Mercedes (2020)
```

**Bewertung:**
- ✓ Korrekte Ausgabe → 1 Punkt
- ✗ Andere Ausgabe → 0 Punkte

---

**b) Zweite Ausgabe (1 Punkt)**

```
Fahrzeug: BMW (2022), Türen: 4
```

**Erklärung:** Auto überschreibt getBeschreibung() und erweitert die Ausgabe

**Bewertung:**
- ✓ Korrekte Ausgabe mit Türen → 1 Punkt
- ~ Ohne Türen → 0 Punkte

---

**c) Dritte Ausgabe (1 Punkt)**

```
Fahrzeug: Audi (2023), Türen: 2
```

**Erklärung:** Es wird die überschriebene Methode von Auto aufgerufen (Polymorphie)

**Bewertung:**
- ✓ Korrekte Ausgabe → 1 Punkt
- ✓ Zusatz "Methode von Auto" erwähnt → 1 Punkt

---

**d) Erklärung Polymorphie (2 Punkte)**

**Musterlösung:**
```
Obwohl f2 als Typ Fahrzeug deklariert ist, enthält es tatsächlich ein Auto-Objekt.
Zur Laufzeit wird die tatsächliche Klasse (Auto) erkannt und deren überschriebene
Methode aufgerufen. Das nennt man Polymorphie oder dynamische Bindung.
```

**Bewertung:**
- **2 Punkte:** Polymorphie erklärt + tatsächlicher Typ zur Laufzeit erwähnt
- **1 Punkt:** Grundidee erkannt ("Auto-Methode wird aufgerufen")
- **0 Punkte:** Falsch

---

## Aufgabe 5: instanceof und Typprüfung (3 Punkte)

### Aufgabenstellung

**Thema:** Prüfung von Objekttypen

Gegeben ist eine Liste von Fahrzeugen:

```java
List<Fahrzeug> fahrzeuge = new ArrayList<>();
fahrzeuge.add(new Fahrzeug("VW", 2019));
fahrzeuge.add(new Auto("Ford", 2021, 5));
fahrzeuge.add(new Auto("Opel", 2020, 3));
```

Schreibe eine **for-Schleife**, die durch alle Fahrzeuge iteriert und nur die Autos ausgibt:

```java
// Schleife hier schreiben (mit instanceof-Prüfung):




```

---

### Musterlösung

```java
for (Fahrzeug fahrzeug : fahrzeuge) {
    if (fahrzeug instanceof Auto) {
        System.out.println(((Auto) fahrzeug).getBeschreibung());
    }
}
```

oder (ohne Cast, wenn nur Ausgabe gewünscht):

```java
for (Fahrzeug fahrzeug : fahrzeuge) {
    if (fahrzeug instanceof Auto) {
        System.out.println("Auto gefunden: " + fahrzeug.getBeschreibung());
    }
}
```

**Bewertung (3 Punkte):**
- **3 Punkte:** Schleife korrekt + instanceof-Prüfung vorhanden + sinnvolle Ausgabe
- **2 Punkte:** instanceof verwendet, aber Cast fehlt (wenn nötig) oder kleine Fehler
- **1 Punkt:** Grundkonzept erkannt, aber mehrere Fehler
- **0 Punkte:** Falsch oder nicht beantwortet

**Häufige Fehler:**
- Kein instanceof → 0 Punkte
- instanceof vorhanden, aber keine Ausgabe → 2 Punkte

---

## Aufgabe 6: Fehlersuche & Analyse (3 Punkte)

### Aufgabenstellung

**Thema:** Häufige Fehler bei Vererbung

Gegeben ist folgender fehlerhafter Code:

```java
public class Motorrad extends Fahrzeug {
    private int hubraum;
    
    public Motorrad(String marke, int baujahr, int hubraum) {
        this.marke = marke;        // ← FEHLER 1
        this.baujahr = baujahr;
        this.hubraum = hubraum;
    }
}
```

**Aufgaben:**

a) **Fehler 1:** Was ist das Problem mit der Initialisierung ohne `super()`?

___________________________________________________________________________

___________________________________________________________________________

b) Schreibe die **korrekte Version** des Konstruktors:

```java
// Korrekt:




```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Vererbungssyntax | 4 | |
| 2. Konstruktor mit super() | 5 | |
| 3. Methoden überschreiben | 5 | |
| 4. Code-Analyse (Polymorphie) | 5 | |
| 5. instanceof und Typprüfung | 3 | |
| 6. Fehlersuche | 3 | |
| **Gesamt** | **25** | |

### Musterlösungen

**a) Fehler ohne super() (1,5 Punkte)**

**Musterlösung:**
```
Der Konstruktor der Oberklasse Fahrzeug wird nicht aufgerufen. Die Attribute marke 
und baujahr der Oberklasse werden nicht initialisiert. Java erwartet, dass super() 
explizit aufgerufen wird, wenn die Oberklasse einen parametrisierten Konstruktor hat.
```

**Bewertung:**
- **1,5 Punkte:** super() fehlt + Oberklassen-Konstruktor muss aufgerufen werden
- **1 Punkt:** Grundidee erkannt
- **0 Punkte:** Falsch

---

**b) Korrekte Version (1,5 Punkte)**

**Musterlösung:**
```java
public Motorrad(String marke, int baujahr, int hubraum) {
    super(marke, baujahr);
    this.hubraum = hubraum;
}
```

**Bewertung:**
- **1,5 Punkte:** super() korrekt aufgerufen als erste Anweisung
- **1 Punkt:** super() vorhanden, aber Position oder Parameter falsch
- **0 Punkte:** Falsch

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte |
|---------|------------|
| 1. Vererbungssyntax | 4 |
| 2. Konstruktor mit super() | 5 |
| 3. Methoden überschreiben | 5 |
| 4. Code-Analyse (Polymorphie) | 5 |
| 5. instanceof und Typprüfung | 3 |
| 6. Fehlersuche | 3 |
| **GESAMT** | **25** |

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

### Fehler 1: Verwechslung extends/implements
```java
public class Auto implements Fahrzeug { }  // ← implements ist für Interfaces
```
**Handling:** 0 Punkte für Aufgabe 1a

### Fehler 2: super() vergessen oder falsch platziert
```java
public Auto(String marke, int baujahr, int anzahlTueren) {
    this.anzahlTueren = anzahlTueren;
    super(marke, baujahr);  // ← muss an erster Stelle stehen
}
```
**Handling:** 0,5 Punkte Abzug in Aufgabe 2

### Fehler 3: @Override vergessen
```java
public String getBeschreibung() {  // ← @Override fehlt
    return ...;
}
```
**Handling:** 0,5 Punkte Abzug in Aufgabe 3a (akzeptabel, aber nicht optimal)

### Fehler 4: Kein instanceof vor Cast
```java
for (Fahrzeug f : fahrzeuge) {
    Auto a = (Auto) f;  // ← ClassCastException möglich!
}
```
**Handling:** Max. 1 Punkt in Aufgabe 5 (Fehler kritisch)

### Fehler 5: Polymorphie nicht verstanden
- Schüler denken, f2 ruft Fahrzeug-Methode auf (nicht Auto)
**Handling:** 0 Punkte für Aufgabe 4d (Kernkonzept nicht verstanden)

---

## Tipps zur schnellen Korrektur

1. **Aufgabe 1:** `extends` vorhanden? protected erklärt?
2. **Aufgabe 2:** `super()` korrekt? Als erste Anweisung?
3. **Aufgabe 3a:** `@Override` vorhanden? Methode erweitert?
4. **Aufgabe 3b:** 4 Zuordnungen abgleichen
5. **Aufgabe 4d:** Stichwort "Polymorphie" oder "dynamische Bindung"?
6. **Aufgabe 5:** `instanceof` verwendet?
7. **Aufgabe 6:** `super()` im korrigierten Code?

---

## Lernziele

Dieser Test prüft folgende zentrale Konzepte der Vererbung:

✓ **Syntax:** extends, super(), protected  
✓ **Konstruktoren:** Aufruf von Oberkonstruktoren  
✓ **Überschreiben:** @Override, super.methode()  
✓ **Polymorphie:** Dynamische Bindung, Typprüfung  
✓ **Typ-Casting:** instanceof, explizites Casting  
✓ **Häufige Fehler:** super() vergessen, falsche Position  

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Zielgruppe:** Schüler nach Lerneinheit Vererbung  
**Voraussetzungen:** Klassen, Konstruktoren, Methoden, Objektorientierung bekannt  
**Geschätzter Schwierigkeitsgrad:** Mittel bis anspruchsvoll
