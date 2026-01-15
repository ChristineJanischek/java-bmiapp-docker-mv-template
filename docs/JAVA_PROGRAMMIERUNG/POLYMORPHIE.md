# Polymorphie in Java – Praktischer Leitfaden

## 🎯 Was ist Polymorphie?

**Polymorphie** (griechisch: "Vielgestaltigkeit") bedeutet, dass eine Methode in verschiedenen Formen (mit unterschiedlichen Parametern) existieren kann.

In Java gibt es zwei Arten:
1. **Methodenüberladung (Overloading)** – Gleicher Name, unterschiedliche Parameter
2. **Methodenüberschreibung (Overriding)** – Subklasse überschreibt Methode der Superklasse

---

## 🔄 Methodenüberladung (Method Overloading)

### Definition
Mehrere Methoden mit **gleichem Namen**, aber **unterschiedlichen Parameterlisten** in derselben Klasse.

### Wann unterscheiden sich Methoden?
- ✅ Anzahl der Parameter
- ✅ Datentypen der Parameter
- ✅ Reihenfolge der Parameter
- ❌ **NICHT** der Rückgabetyp allein!

### Beispiel: BMI-Interpretation

```java
public class Bmirechner {
    
    // Version 1: Nur BMI-Wert
    public void interpretiere() {
        if (ergebnis < 18.5) {
            kategorie = "Untergewicht";
        } else if (ergebnis < 25) {
            kategorie = "Normalgewicht";
        } else {
            kategorie = "Übergewicht";
        }
    }
    
    // Version 2: BMI + Alter + Geschlecht (überladene Methode!)
    public void interpretiere(double pGewicht, double pGroesse, int pAlter, String pGeschlecht) {
        // Erweiterte Interpretation mit alters- und geschlechtsspezifischen Grenzwerten
        berechne(pGewicht, pGroesse);
        
        // Alters- und geschlechtsspezifische Auswertung
        if (pAlter < 25) {
            // Jüngere Menschen: andere Grenzwerte
            if (ergebnis < 19) kategorie = "Untergewicht";
            else if (ergebnis < 24) kategorie = "Normalgewicht";
            else kategorie = "Übergewicht";
        } else if (pAlter >= 65) {
            // Ältere Menschen: angepasste Grenzwerte
            if (ergebnis < 22) kategorie = "Untergewicht";
            else if (ergebnis < 28) kategorie = "Normalgewicht";
            else kategorie = "Übergewicht";
        } else {
            // Erwachsene (25-64 Jahre)
            if (pGeschlecht.equalsIgnoreCase("männlich")) {
                if (ergebnis < 20) kategorie = "Untergewicht";
                else if (ergebnis < 25) kategorie = "Normalgewicht";
                else kategorie = "Übergewicht";
            } else {
                if (ergebnis < 19) kategorie = "Untergewicht";
                else if (ergebnis < 24) kategorie = "Normalgewicht";
                else kategorie = "Übergewicht";
            }
        }
    }
}
```

---

## ✅ Vorteile der Polymorphie

1. **Lesbarkeit**: Gleicher Methodenname für ähnliche Funktionen
2. **Flexibilität**: Verschiedene Aufrufmöglichkeiten
3. **Erweiterbarkeit**: Neue Varianten ohne Umbenennung hinzufügen

---

## 🎮 Verwendung

```java
Bmirechner rechner = new Bmirechner();

// Aufruf 1: Einfache Interpretation (nur BMI)
rechner.berechne(70, 1.75);
rechner.interpretiere();  // Nutzt die parameterlose Version

// Aufruf 2: Erweiterte Interpretation (BMI + Alter + Geschlecht)
rechner.interpretiere(70, 1.75, 30, "männlich");  // Nutzt die überladene Version
```

---

## 🚨 Häufige Fehler

### ❌ Falsch: Nur Rückgabetyp unterschiedlich
```java
public String interpretiere() { ... }
public int interpretiere() { ... }  // FEHLER: Compiler-Error!
```

### ✅ Richtig: Parameter unterschiedlich
```java
public void interpretiere() { ... }
public void interpretiere(int pAlter) { ... }
public void interpretiere(double pGewicht, double pGroesse, int pAlter, String pGeschlecht) { ... }
```

---

## 📋 Checkliste für Polymorphie

- [ ] Methodenname ist identisch
- [ ] Parameterliste ist unterschiedlich (Anzahl, Typ oder Reihenfolge)
- [ ] Jede Methode hat eine klare, spezifische Aufgabe
- [ ] Dokumentation erklärt den Unterschied zwischen den Varianten

---

## 🔗 Weiterführende Konzepte

- **Überladung von Konstruktoren**: `BmiManager()` vs. `BmiManager(Bmirechner pModel)`
- **Überschreibung (@Override)**: In Vererbungshierarchien (wird später behandelt)
- **Generics**: Typsichere Polymorphie (fortgeschrittenes Thema)

---

## 💡 Merksatz

> **"Gleicher Name, verschiedene Signaturen – das ist Polymorphie durch Überladung!"**

Die **Signatur** einer Methode = Name + Parameterliste (ohne Rückgabetyp)
