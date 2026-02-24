# Kurztest: Vererbung in ERP-Systemen - Mitarbeiter-Hierarchie

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Vererbungshierarchie in einem Personalsystem (4 Punkte)

**Thema:** Mitarbeiter, Manager, Direktor

In einem ERP-System gibt es eine Mitarbeiter-Hierarchie. Ergänze die Klassendefinitionen:

```java
public class Mitarbeiter {
    protected String name;
    protected String mitarbeiternummer;
    protected double gehalt;
    
    public Mitarbeiter(String name, String mitarbeiternummer, double gehalt) {
        this.name = name;
        this.mitarbeiternummer = mitarbeiternummer;
        this.gehalt = gehalt;
    }
}

public class Manager __________________ {
    protected int anzahlUntergeordneter;
    
    // Konstruktor hier
}

public class Direktor __________________ {
    protected String abteilung;
    
    // Konstruktor hier
}
```

a) Welche Klasse erbt von welcher? Erkläre die Hierarchie in einem Satz:

___________________________________________________________________________

---

## Aufgabe 2: Konstruktoren mit super() (5 Punkte)

**Thema:** Korrekte Initialisierung in einer dreistufigen Hierarchie

Vervollständige die Konstruktoren für Manager und Direktor:

```java
public class Manager extends Mitarbeiter {
    protected int anzahlUntergeordneter;
    
    public Manager(String name, String mitarbeiternummer, double gehalt, int anzahlUntergeordneter) {
        // Aufruf des Mitarbeiter-Konstruktors:
        
        
        // Initialisierung des Manager-Attributes:
        
    }
}

public class Direktor extends Manager {
    protected String abteilung;
    
    public Direktor(String name, String mitarbeiternummer, double gehalt, int anzahlUntergeordneter, String abteilung) {
        // Aufruf des Manager-Konstruktors (mit allen Parametern):
        
        
        // Initialisierung des Direktor-Attributes:
        
    }
}
```

Erkläre in 1-2 Sätzen, warum super() in Aufgabe 2 zweimal unterschiedlich aussieht:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 3: Gehaltsboni und überschriebene Methoden (5 Punkte)

**Thema:** Bonus-Berechnung nach Funktion

In der Basisklasse `Mitarbeiter` gibt es eine Methode zur Bonus-Berechnung:

```java
public double getBonus() {
    return gehalt * 0.05;  // 5% Basis-Bonus
}
```

a) Schreibe überschriebene Versionen für Manager (7% Bonus) und Direktor (10% Bonus):

```java
// Manager:


// Direktor:

```

b) Schreibe eine Methode `getGesamtvergütung()` für die Klasse Manager, die Gehalt + Bonus zurückgibt:

```java
// Methode:

```

---

## Aufgabe 4: Code-Analyse - Polymorphe Mitarbeiterliste (6 Punkte)

**Thema:** Polymorphie in der Gehaltsabrechnung

Gegeben ist folgender Code:

```java
List<Mitarbeiter> mitarbeiter = new ArrayList<>();

mitarbeiter.add(new Mitarbeiter("Anna Schmidt", "M001", 3000));
mitarbeiter.add(new Manager("Max Müller", "M002", 5000, 3));
mitarbeiter.add(new Direktor("Klaus Weber", "M003", 8000, 10, "IT"));

double gesamtBonus = 0;
for (Mitarbeiter m : mitarbeiter) {
    gesamtBonus += m.getBonus();
}

System.out.println("Gesamtbonus: " + gesamtBonus);
```

**Fragen:**

a) Welche Bonus-Werte werden berechnet? (5% für Mitarbeiter, 7% für Manager, 10% für Direktor)

Antwort:  
Mitarbeiter Anna: _____________  
Manager Max: _____________  
Direktor Klaus: _____________  
**Gesamtbonus: _____________**

b) Erkläre, warum verschiedene Bonus-Prozentsätze berechnet werden, obwohl alle `getBonus()` aufrufen:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Typprüfung und Speziallogik (3 Punkte)

**Thema:** Unterschiedliche Behandlung nach Typ (instanceof)

Schreibe eine Methode, die nur Manager ausgibt, die weniger als 5 Untergeordnete haben:

```java
// Methode (oder Schleife):




```

---

## Aufgabe 6: Fehlersuche - Gehaltssteigerung (2 Punkte)

**Thema:** Häufige Fehler bei Vererbung

Gegeben ist folgender fehlerhafter Code in der Klasse Direktor:

```java
public void gehaltsErhoehung(double prozent) {
    gehalt = gehalt * (1 + prozent / 100);  // ← FEHLER
}
```

Erkläre, warum dieser Code ein Problem darstellt (oder ob er funktioniert):

___________________________________________________________________________

___________________________________________________________________________

Wie würdest du die Methode für alle drei Klassen (Mitarbeiter, Manager, Direktor) korrekt implementieren?

```java
// Beste Lösung:

```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Vererbungshierarchie (3-stufig) | 4 | |
| 2. Konstruktoren mit super() | 5 | |
| 3. Überschriebene Methoden (Bonus) | 5 | |
| 4. Code-Analyse (Polymorphie) | 6 | |
| 5. Typprüfung (instanceof) | 3 | |
| 6. Fehlersuche & Implementierung | 2 | |
| **Gesamt** | **25** | |
