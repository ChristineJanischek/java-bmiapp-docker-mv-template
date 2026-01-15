# Assoziationen in der objektorientierten Programmierung

Diese Datei erklärt die grundlegenden Konzepte von Assoziationen zwischen Klassen und wie sie in Java implementiert werden.

---

## Was ist eine Assoziation?

Eine **Assoziation** beschreibt eine Beziehung zwischen zwei oder mehreren Klassen. Sie zeigt, dass Objekte miteinander verbunden sind und miteinander interagieren können.

### Beispiel im BMI-Rechner:
- `MainWindow` **nutzt** einen `BmiManager` (View nutzt Controller)
- `BmiManager` **verwaltet** einen `Bmirechner` (Controller verwaltet Model)

---

## Multiplizität (Kardinalität)

Die **Multiplizität** gibt an, wie viele Objekte an einer Assoziation beteiligt sind.

### Gängige Multiplizitäten:

| Notation | Bedeutung | Beispiel |
|----------|-----------|----------|
| `1` | Genau ein Objekt | Ein BmiManager hat genau ein Bmirechner-Objekt |
| `0..1` | Kein oder ein Objekt | Ein Kunde kann ein Konto haben oder nicht |
| `*` oder `0..*` | Beliebig viele (null bis unendlich) | Ein Lehrer hat viele Schüler |
| `1..*` | Mindestens ein Objekt | Eine Bestellung enthält mindestens ein Produkt |
| `n` | Genau n Objekte | Ein Würfel hat genau 6 Seiten |

---

## Arten von Assoziationen

### 1. Einfache Assoziation
Zwei Klassen sind miteinander verbunden, aber keine ist "Besitzer" der anderen.

**Beispiel:**
```
Student ←→ Kurs
(Ein Student besucht Kurse, ein Kurs hat Studenten)
```

### 2. Gerichtete Assoziation
Eine Klasse kennt die andere, aber nicht umgekehrt.

**Beispiel im BMI-Rechner:**
```
BmiManager → Bmirechner
(Der Manager kennt das Model, aber das Model kennt den Manager nicht)
```

### 3. Aggregation (hat-ein-Beziehung, schwach)
Eine Klasse "besitzt" eine andere, aber beide können unabhängig existieren.

**Symbol:** ◇─→ (leerer Diamant)

**Beispiel:**
```
Auto ◇─→ Reifen
(Ein Auto hat Reifen, aber Reifen existieren auch ohne Auto)
```

### 4. Komposition (besteht-aus-Beziehung, stark)
Eine Klasse ist Teil einer anderen und kann nicht ohne sie existieren.

**Symbol:** ◆─→ (gefüllter Diamant)

**Beispiel:**
```
Haus ◆─→ Zimmer
(Ein Haus besteht aus Zimmern, Zimmer existieren nicht ohne Haus)
```

---

## Implementierung in Java

### 1. Einfache 1:1-Assoziation (BmiManager → Bmirechner)

```java
public class BmiManager {
    private Bmirechner model;  // Attribut für die Assoziation
    
    public BmiManager() {
        this.model = new Bmirechner();  // Objekt wird erzeugt
    }
    
    public BmiManager(Bmirechner pModel) {
        this.model = pModel;  // Objekt wird übergeben
    }
    
    public Bmirechner getModel() {
        return model;  // Zugriff auf das assoziierte Objekt
    }
}
```

**Erklärung:**
- `private Bmirechner model;` → Attribut speichert die Verbindung
- Im Konstruktor wird das Objekt erzeugt oder übergeben
- Getter-Methode ermöglicht Zugriff auf das assoziierte Objekt

---

### 2. 1:n-Assoziation (Ein Lehrer hat viele Schüler)

```java
import java.util.ArrayList;
import java.util.List;

public class Lehrer {
    private String name;
    private List<Schueler> schuelerListe;  // Liste für mehrere Objekte
    
    public Lehrer(String pName) {
        this.name = pName;
        this.schuelerListe = new ArrayList<>();  // Leere Liste erzeugen
    }
    
    public void schuelerHinzufuegen(Schueler pSchueler) {
        schuelerListe.add(pSchueler);  // Schüler zur Liste hinzufügen
    }
    
    public List<Schueler> getSchuelerListe() {
        return schuelerListe;
    }
}
```

**Erklärung:**
- `List<Schueler>` → Kann mehrere Schüler-Objekte speichern
- `ArrayList` ist eine konkrete Implementierung einer Liste
- Methode `schuelerHinzufuegen()` fügt neue Assoziationen hinzu

---

### 3. Bidirektionale Assoziation (Beide Klassen kennen sich)

```java
public class Student {
    private String name;
    private Kurs kurs;
    
    public void setKurs(Kurs pKurs) {
        this.kurs = pKurs;
        pKurs.studentHinzufuegen(this);  // Bidirektional verknüpfen
    }
}

public class Kurs {
    private String titel;
    private List<Student> studenten;
    
    public Kurs(String pTitel) {
        this.titel = pTitel;
        this.studenten = new ArrayList<>();
    }
    
    public void studentHinzufuegen(Student pStudent) {
        if (!studenten.contains(pStudent)) {
            studenten.add(pStudent);
        }
    }
}
```

---

## UML-Notation

### Beispiel: BmiManager und Bmirechner

```
┌─────────────────┐         1      ┌─────────────────┐
│   BmiManager    │ ◇────────────→ │   Bmirechner    │
├─────────────────┤                ├─────────────────┤
│ - model         │                │ - gewicht       │
├─────────────────┤                │ - groesse       │
│ + berechneBMI() │                │ - ergebnis      │
│ + getModel()    │                ├─────────────────┤
└─────────────────┘                │ + berechne()    │
                                   │ + interpretiere()│
                                   └─────────────────┘
```

**Legende:**
- Pfeil von `BmiManager` zu `Bmirechner` → gerichtete Assoziation
- `1` an der Zielseite → Multiplizität (genau ein Bmirechner)
- `◇` → Aggregation (BmiManager "hat" ein Bmirechner-Objekt)

---

## Zusammenfassung

| Konzept | Bedeutung | Java-Implementierung |
|---------|-----------|---------------------|
| **Assoziation** | Beziehung zwischen Klassen | Attribut vom Typ der anderen Klasse |
| **1:1** | Ein Objekt kennt genau ein anderes | `private KlasseB objekt;` |
| **1:n** | Ein Objekt kennt mehrere andere | `private List<KlasseB> objekte;` |
| **Aggregation** | "hat-ein"-Beziehung (schwach) | Objekt wird im Konstruktor erzeugt oder übergeben |
| **Komposition** | "besteht-aus"-Beziehung (stark) | Objekt wird nur im Konstruktor erzeugt |

---

## Anwendung im BMI-Projekt

1. **MainWindow → BmiManager** (1:1, Aggregation)
   - Die View kennt ihren Controller
   - Wird im Konstruktor von MainWindow erzeugt

2. **BmiManager → Bmirechner** (1:1, Aggregation)
   - Der Controller kennt sein Model
   - Wird im Konstruktor von BmiManager erzeugt

3. **MVC-Prinzip nutzt Assoziationen**
   - View nutzt Controller
   - Controller nutzt Model
   - Klare Trennung durch gerichtete Assoziationen

---

Weitere Informationen zu objektorientierter Modellierung findest du in [info.md](./info.md).
