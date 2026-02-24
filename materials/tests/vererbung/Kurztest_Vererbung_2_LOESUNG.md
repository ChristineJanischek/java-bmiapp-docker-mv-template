# Kurztest: Vererbung in ERP-Systemen - Mitarbeiter-Hierarchie - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: Vererbungshierarchie in einem Personalsystem (4 Punkte)

### Musterlösung

```java
public class Manager extends Mitarbeiter {
    protected int anzahlUntergeordneter;
}

public class Direktor extends Manager {
    protected String abteilung;
}
```

**Erklärung (2 Punkte):**
```
Mitarbeiter ist die Basisklasse. Manager erbt von Mitarbeiter. 
Direktor erbt von Manager (nicht direkt von Mitarbeiter) und bildet die oberste Hierarchie-Stufe.
```

### Bewertung (4 Punkte)

**Hierarchie (2 Punkte):**
- ✓ Manager extends Mitarbeiter → 1 Punkt
- ✓ Direktor extends Manager → 1 Punkt
- ✗ Falsche Vererbungsrichtung → 0 Punkte

**Erklärung (2 Punkte):**
- **2 Punkte:** Drei-Stufen-Hierarchie klar erklärt
- **1 Punkt:** Grundidee erkannt
- **0 Punkte:** Falsch

---

## Aufgabe 2: Konstruktoren mit super() (5 Punkte)

### Musterlösung

```java
public class Manager extends Mitarbeiter {
    protected int anzahlUntergeordneter;
    
    public Manager(String name, String mitarbeiternummer, double gehalt, int anzahlUntergeordneter) {
        super(name, mitarbeiternummer, gehalt);
        this.anzahlUntergeordneter = anzahlUntergeordneter;
    }
}

public class Direktor extends Manager {
    protected String abteilung;
    
    public Direktor(String name, String mitarbeiternummer, double gehalt, int anzahlUntergeordneter, String abteilung) {
        super(name, mitarbeiternummer, gehalt, anzahlUntergeordneter);
        this.abteilung = abteilung;
    }
}
```

### Bewertung (3 Punkte für Code)

**Manager-Konstruktor (1,5 Punkte):**
- ✓ `super(name, mitarbeiternummer, gehalt)` korrekt → 1 Punkt
- ✓ `this.anzahlUntergeordneter` korrekt → 0,5 Punkte

**Direktor-Konstruktor (1,5 Punkte):**
- ✓ `super(name, mitarbeiternummer, gehalt, anzahlUntergeordneter)` korrekt → 1 Punkt
- ✓ `this.abteilung` korrekt → 0,5 Punkte

---

**Erklärung super()-Unterschied (2 Punkte):**

**Musterlösung:**
```
Manager ruft super() mit 3 Parametern auf (für Mitarbeiter-Attribute).
Direktor ruft super() mit 4 Parametern auf (für Manager-Attribute + Mitarbeiter-Attribute).
Jede Klasse ruft den Konstruktor ihrer direkten Oberklasse auf und übergibt 
die notwendigen Parameter für deren Initialisierung.
```

**Bewertung:**
- **2 Punkte:** Unterschied erklärt + direkte Oberklasse erwähnt
- **1 Punkt:** Grundidee erkannt ("unterschiedliche Parameter")
- **0 Punkte:** Falsch

---

## Aufgabe 3: Gehaltsboni und überschriebene Methoden (5 Punkte)

### Musterlösung

**a) Bonus-Methoden (3 Punkte):**

```java
// Manager:
@Override
public double getBonus() {
    return gehalt * 0.07;  // 7% Bonus
}

// Direktor:
@Override
public double getBonus() {
    return gehalt * 0.10;  // 10% Bonus
}
```

**Bewertung (3 Punkte):**
- **1,5 Punkte:** Manager-Bonus korrekt (7%)
- **1,5 Punkte:** Direktor-Bonus korrekt (10%)
- **0,5 Punkte Abzug:** @Override fehlt

---

**b) Methode getGesamtvergütung() (2 Punkte):**

```java
@Override
public double getGesamtvergütung() {
    return gehalt + getBonus();
}
```

oder

```java
public double getGesamtvergütung() {
    return gehalt + (gehalt * 0.07);
}
```

**Bewertung (2 Punkte):**
- **2 Punkte:** Gehalt + Bonus korrekt zurückgegeben
- **1 Punkt:** Logik richtig, aber kleine Fehler
- **0 Punkte:** Falsch

---

## Aufgabe 4: Code-Analyse - Polymorphe Mitarbeiterliste (6 Punkte)

### Musterlösungen

**a) Bonus-Berechnung (4 Punkte):**

```
Mitarbeiter Anna: 3000 * 0.05 = 150
Manager Max: 5000 * 0.07 = 350
Direktor Klaus: 8000 * 0.10 = 800
Gesamtbonus: 150 + 350 + 800 = 1300
```

**Bewertung:**
- **1 Punkt pro korrekte Bonus-Berechnung** (3 Punkte insgesamt)
- **1 Punkt für korrekte Gesamtsumme** (1 Punkt)
- ✗ Falsche Prozentsätze → 0 Punkte

---

**b) Erklärung Polymorphie (2 Punkte):**

**Musterlösung:**
```
Obwohl alle `m.getBonus()` aufgerufen wird, wird zur Laufzeit bei jedem Objekt 
seine eigene überschriebene Methode aufgerufen (dynamische Bindung).
Manager und Direktor calculateten einen höheren Bonus, weil sie getBonus() überschrieben haben.
```

**Bewertung:**
- **2 Punkte:** Polymorphie / dynamische Bindung erklärt
- **1 Punkt:** Grundidee erkannt
- **0 Punkte:** Falsch

---

## Aufgabe 5: Typprüfung und Speziallogik (3 Punkte)

### Musterlösung

```java
for (Mitarbeiter m : mitarbeiter) {
    if (m instanceof Manager && !(m instanceof Direktor)) {
        Manager manager = (Manager) m;
        if (manager.getAnzahlUntergeordneter() < 5) {
            System.out.println(manager.getName());
        }
    }
}
```

oder (eleganter mit Direktor-Ausschluss im instanceof):

```java
for (Mitarbeiter m : mitarbeiter) {
    if (m instanceof Manager && m.getClass() == Manager.class) {
        Manager manager = (Manager) m;
        if (manager.getAnzahlUntergeordneter() < 5) {
            System.out.println(m.getName());
        }
    }
}
```

### Bewertung (3 Punkte)

- **3 Punkte:** instanceof-Prüfung + Typecast + Bedingung (< 5) korrekt + keine Direktoren
- **2 Punkte:** Grundkonzept erkannt, aber Direktor-Ausschluss vergessen
- **1 Punkt:** instanceof vorhanden, aber unvollständig
- **0 Punkte:** Falsch

---

## Aufgabe 6: Fehlersuche - Gehaltssteigerung (2 Punkte)

### Musterlösung

**Erklärung (1 Punkt):**
```
Der Code funktioniert! Das Attribut 'gehalt' ist protected und kann daher 
in der Unterklasse Direktor direkt zugegriffen werden. Der Code ist syntaktisch 
und logisch korrekt.
```

**Beste Implementierungslösung (1 Punkt):**
```java
// Besser in der Oberklasse Mitarbeiter:
public void gehaltsErhoehung(double prozent) {
    this.gehalt = this.gehalt * (1 + prozent / 100);
}
```

oder mit Getter/Setter:

```java
// In Mitarbeiter:
public void gehaltsErhoehung(double prozent) {
    gehalt *= (1 + prozent / 100);
}
```

### Bewertung (2 Punkte)

**Fehleranalyse (1 Punkt):**
- ✓ "Funktioniert / kein Fehler" + Begründung (protected) → 1 Punkt
- ✗ "Ist ein Fehler" (falsch) → 0 Punkte

**Implementierung (1 Punkt):**
- **1 Punkt:** Methode in Oberklasse Mitarbeiter definiert (DRY-Prinzip)
- **0,5 Punkte:** Funktioniert, aber nicht optimal (Code-Duplikation)
- **0 Punkte:** Falsch

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte |
|---------|------------|
| 1. Vererbungshierarchie (3-stufig) | 4 |
| 2. Konstruktoren mit super() | 5 |
| 3. Überschriebene Methoden | 5 |
| 4. Code-Analyse (Polymorphie) | 6 |
| 5. Typprüfung (instanceof) | 3 |
| 6. Fehlersuche & Implementierung | 2 |
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

## Häufige Schülerfehler

### Fehler 1: Falsche Hierarchie
```java
public class Manager extends Direktor { }  // ← Umgekehrte Richtung!
public class Direktor extends Mitarbeiter { }
```
**Handling:** 0 Punkte für Aufgabe 1

### Fehler 2: super() mit falschen Parametern
```java
public Direktor(...) {
    super(name, mitarbeiternummer, gehalt);  // ← anzahlUntergeordneter fehlt!
}
```
**Handling:** 1 Punkt Abzug in Aufgabe 2

### Fehler 3: Bonus-Prozentsätze verwechselt
```
Manager: 5% statt 7% → Rechenfehler
Direktor: 7% statt 10% → Rechenfehler
```
**Handling:** Jeweils 0,5 Punkte abziehen in Aufgabe 4a

### Fehler 4: Keine Direktor-Ausschluss bei instanceof
```java
if (m instanceof Manager) {  // ← Direktoren sind auch Manager!
    // Manager-Logik
}
```
**Handling:** 1 Punkt Abzug in Aufgabe 5 (kritischer Fehler)

---

## Lernziele

✓ **Mehrschichtige Vererbung:** Drei-Stufen-Hierarchie verstehen  
✓ **Super-Aufrufe:** Kette von super()-Aufrufen  
✓ **Überschreiben:** Unterschiedliche Bonus-Logiken  
✓ **Polymorphie:** Verschiedene Behavior je Typ  
✓ **Typprüfung:** instanceof mit Ausschluss  
✓ **DRY-Prinzip:** Code-Wiederverwendung durch Oberklassen  

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Kontext:** ERP-System - Personalverwaltung  
**Schwierigkeitsgrad:** Mittelschwer  
**Praktische Relevanz:** Sehr hoch (echte ERP-Szenarien)
