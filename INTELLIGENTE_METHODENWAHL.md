# Intelligente Methodenwahl – Best Practice

## 🎯 Problem

In der GUI haben wir optionale Felder (Alter, Geschlecht). Je nachdem, ob diese ausgefüllt sind, soll eine andere Interpretationsmethode aufgerufen werden.

**Frage:** Wie entscheiden wir, welche Methode aufgerufen wird?

---

## ✅ Empfohlene Lösung: Intelligente Controller-Methode

### Ansatz: `interpretiereIntelligent()`

Der **Controller** (BmiManager) entscheidet automatisch, welche Model-Methode aufgerufen wird.

```java
public void interpretiereIntelligent(double pGewicht, double pGroesse, 
                                     int pAlter, String pGeschlecht) {
    // Prüfe, ob Alter und Geschlecht gültig sind
    boolean alterVorhanden = pAlter > 0;
    boolean geschlechtVorhanden = pGeschlecht != null && !pGeschlecht.trim().isEmpty();
    
    if (alterVorhanden && geschlechtVorhanden) {
        // Erweiterte Interpretation mit allen Parametern
        model.interpretiere(pGewicht, pGroesse, pAlter, pGeschlecht);
    } else {
        // Einfache Interpretation ohne Alter/Geschlecht
        model.berechne(pGewicht, pGroesse);
        model.interpretiere();
    }
}
```

### Vorteile ✅

1. **View bleibt einfach**: GUI muss nicht entscheiden, welche Methode aufgerufen wird
2. **Klare Verantwortlichkeit**: Controller steuert die Logik
3. **Flexibel**: Kann leicht um weitere Fälle erweitert werden
4. **MVC-konform**: View → Controller → Model

### Verwendung in der GUI

```java
// Im ActionListener des "Berechnen"-Buttons:
double gewicht = Double.parseDouble(txtGewicht.getText());
double groesse = Double.parseDouble(txtGroesse.getText());

// Alter aus ComboBox (0 = nicht ausgewählt)
int alter = cbAlter.getSelectedIndex();

// Geschlecht aus RadioButtons (null = nicht ausgewählt)
String geschlecht = null;
if (rbMann.isSelected()) {
    geschlecht = "männlich";
} else if (rbFrau.isSelected()) {
    geschlecht = "weiblich";
}

// Controller übernimmt die Entscheidung!
manager.interpretiereIntelligent(gewicht, groesse, alter, geschlecht);
manager.zeigeInterpretation();
```

---

## ❌ Alternative 1: View entscheidet (nicht empfohlen)

```java
// In der GUI:
if (cbAlter.getSelectedIndex() > 0 && (rbMann.isSelected() || rbFrau.isSelected())) {
    manager.interpretiereBMI(gewicht, groesse, alter, geschlecht);
} else {
    manager.berechneBMI(gewicht, groesse);
    manager.interpretiereBMI();
}
```

**Nachteil:** 
- View enthält Geschäftslogik (verletzt MVC-Prinzip)
- Code-Duplikation, wenn mehrere Views existieren
- Schwer zu testen

---

## ❌ Alternative 2: Model entscheidet (kompliziert)

```java
// Im Model:
public void interpretiereAuto(double pGewicht, double pGroesse, Integer pAlter, String pGeschlecht) {
    if (pAlter != null && pAlter > 0 && pGeschlecht != null) {
        interpretiere(pGewicht, pGroesse, pAlter, pGeschlecht);
    } else {
        berechne(pGewicht, pGroesse);
        interpretiere();
    }
}
```

**Nachteil:**
- Model übernimmt Controller-Aufgabe (verletzt Single Responsibility)
- Weniger flexibel bei komplexeren Entscheidungen

---

## 🎓 MVC-Prinzip beachten

| Schicht    | Verantwortung                                      | Beispiel                          |
|------------|----------------------------------------------------|-----------------------------------|
| **View**   | Daten sammeln und anzeigen                         | GUI-Komponenten auslesen          |
| **Controller** | Entscheidungen treffen, Ablauf steuern         | Welche Methode wird aufgerufen?   |
| **Model**  | Geschäftslogik, Berechnungen                       | BMI berechnen und interpretieren  |

**Merksatz:** 
> "Die View sieht, der Controller entscheidet, das Model rechnet."

---

## 🔍 Erweiterte Variante: Null-Object-Pattern

Falls du mit `null`-Werten arbeiten möchtest:

```java
public void interpretiereIntelligent(double pGewicht, double pGroesse, 
                                     Integer pAlter, String pGeschlecht) {
    // Prüfe, ob Alter und Geschlecht gültig sind
    boolean alterVorhanden = (pAlter != null && pAlter > 0);
    boolean geschlechtVorhanden = (pGeschlecht != null && !pGeschlecht.trim().isEmpty());
    
    if (alterVorhanden && geschlechtVorhanden) {
        model.interpretiere(pGewicht, pGroesse, pAlter, pGeschlecht);
    } else {
        model.berechne(pGewicht, pGroesse);
        model.interpretiere();
    }
}
```

**Hinweis:** Verwende `Integer` statt `int`, um `null` als "nicht gesetzt" zu erkennen.

---

## 🚀 Best Practices

### 1. **Defensive Programmierung**
```java
if (pGeschlecht != null && !pGeschlecht.trim().isEmpty()) {
    // Verarbeitung
}
```

### 2. **Klare Namensgebung**
- `interpretiereIntelligent()` → macht klar, dass automatisch entschieden wird
- `interpretiereMitAlter()` → explizit für erweiterte Interpretation

### 3. **Dokumentation**
Erkläre im JavaDoc, welche Werte "nicht gesetzt" bedeuten:
```java
/**
 * @param pAlter Alter in Jahren (0 = nicht angegeben)
 * @param pGeschlecht Geschlecht (null oder leer = nicht angegeben)
 */
```

---

## 💡 Zusammenfassung

**Deine Ursprungsfrage:**
> "Soll ich im BmiManager prüfen, ob Alter/Geschlecht gesetzt sind?"

**Antwort:** ✅ **Ja, genau dort gehört die Logik hin!**

Der Controller ist der richtige Ort für solche Entscheidungen. Das hält die View einfach und das Model fokussiert auf Berechnungen.

**Empfohlener Ablauf:**
1. View liest Werte aus (auch wenn leer/null)
2. View ruft `interpretiereIntelligent()` auf
3. Controller prüft Werte und wählt die richtige Model-Methode
4. Model führt Berechnung durch
5. Controller holt Ergebnis und gibt es an View zurück
