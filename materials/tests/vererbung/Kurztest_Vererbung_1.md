# Kurztest: Vererbung in Java

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Vererbungssyntax (4 Punkte)

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

## Aufgabe 2: Konstruktor mit super() (5 Punkte)

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

## Aufgabe 3: Methoden überschreiben (5 Punkte)

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

## Aufgabe 4: Code-Analyse (5 Punkte)

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

## Aufgabe 5: instanceof und Typprüfung (3 Punkte)

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

## Aufgabe 6: Fehlersuche & Analyse (3 Punkte)

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
