# Model-View-Controller (MVC) - Konzept und Implementierung

Diese Datei erklärt das Model-View-Controller-Prinzip und wie es im BMI-Rechner-Projekt umgesetzt wird.

---

## Was ist MVC?

**MVC (Model-View-Controller)** ist ein Architekturmuster in der Softwareentwicklung, das die Anwendung in drei unabhängige Komponenten aufteilt:

1. **Model (Modell)** - Daten und Geschäftslogik
2. **View (Ansicht)** - Benutzeroberfläche und Präsentation
3. **Controller (Steuerung)** - Vermittler zwischen Model und View

---

## Die drei Komponenten im Detail

### 1. Model (Modell)

**Aufgaben:**
- Verwaltung der Daten und des Zustands
- Implementierung der Geschäftslogik
- Berechnung und Verarbeitung von Daten
- Benachrichtigung bei Änderungen

**Eigenschaften:**
- Kennt weder View noch Controller
- Ist unabhängig von der Darstellung
- Kann wiederverwendet werden

**Im BMI-Projekt: `Bmirechner`**
```java
public class Bmirechner {
    // Daten
    private double gewicht;
    private double groesse;
    private double ergebnis;
    private String kategorie;
    
    // Geschäftslogik
    public double berechne(double pGewicht, double pGroesse) {
        this.gewicht = pGewicht;
        this.groesse = pGroesse;
        this.ergebnis = pGewicht / (pGroesse * pGroesse);
        return this.ergebnis;
    }
    
    public void interpretiere() {
        // Logik zur Kategorisierung
        if (this.ergebnis >= 40) {
            this.kategorie = "Adipositas Grad III";
        }
        // ... weitere Kategorien
    }
}
```

---

### 2. View (Ansicht)

**Aufgaben:**
- Darstellung der Benutzeroberfläche
- Anzeige der Daten aus dem Model
- Entgegennahme von Benutzereingaben
- Weiterleitung von Aktionen an den Controller

**Eigenschaften:**
- Kennt den Controller
- Kennt das Model nicht direkt (nur über Controller)
- Ist zuständig für die Präsentation

**Im BMI-Projekt: `MainWindow`**
```java
public class MainWindow extends JFrame {
    private BmiManager manager;  // Kennt den Controller
    private JTextField tfGewicht;
    private JTextField tfGroesse;
    private JTextField tfErgebnis;
    
    public MainWindow() {
        manager = new BmiManager();  // Erzeugt Controller
        // ... GUI-Aufbau
    }
    
    // Button-Event: Leitet Aktion an Controller weiter
    btBerechne.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            manager.berechneBMI(getGewichtValue(), getGroesseValue());
            schreibeErgebnis();
        }
    });
}
```

---

### 3. Controller (Steuerung)

**Aufgaben:**
- Vermittlung zwischen Model und View
- Verarbeitung von Benutzereingaben
- Steuerung des Programmablaufs
- Aktualisierung von Model und View

**Eigenschaften:**
- Kennt sowohl Model als auch View
- Koordiniert die Zusammenarbeit
- Enthält keine Geschäftslogik (nur Steuerungslogik)

**Analogie:**
Der Controller funktioniert wie eine **Theke in einem Restaurant**: 
- Die Bestellung (Daten vom Benutzer) wird über die Theke zum Koch (Model) gereicht
- Die Speisen (Ergebnis) werden über die Theke zum Gast (View) gereicht
- Die Theke selbst kocht nicht und isst nicht - sie ist nur die **Durchreiche**!
- So vermittelt der Controller zwischen View und Model, ohne selbst die Geschäftslogik zu enthalten

**Im BMI-Projekt: `BmiManager`**
```java
public class BmiManager {
    private Bmirechner model;  // Kennt das Model
    
    public BmiManager() {
        this.model = new Bmirechner();
    }
    
    // Steuerungsmethode: Ruft Model-Methoden auf
    public double berechneBMI(double pGewicht, double pGroesse) {
        return model.berechne(pGewicht, pGroesse);
    }
    
    public void interpretiereBMI() {
        model.interpretiere();
    }
    
    // Zugriff auf das Model für die View
    public Bmirechner getModel() {
        return model;
    }
}
```

---

## Zusammenspiel der Komponenten

```
┌──────────────────────────────────────────────────────────┐
│                    MVC-Ablauf                            │
└──────────────────────────────────────────────────────────┘

    Benutzer
       │
       │ (1) Eingabe
       ▼
   ┌──────┐
   │ VIEW │ (MainWindow)
   └──────┘
       │
       │ (2) Aktion weiterleiten
       ▼
┌──────────────┐
│ CONTROLLER   │ (BmiManager)
└──────────────┘
       │
       │ (3) Daten verarbeiten
       ▼
   ┌───────┐
   │ MODEL │ (Bmirechner)
   └───────┘
       │
       │ (4) Ergebnis zurück
       ▼
┌──────────────┐
│ CONTROLLER   │
└──────────────┘
       │
       │ (5) View aktualisieren
       ▼
   ┌──────┐
   │ VIEW │
   └──────┘
       │
       │ (6) Anzeige
       ▼
    Benutzer
```

---

## 📝 Arbeitsauftrag: eEPK erstellen (für Schüler)

### Aufgabenstellung:
Erstelle eine **erweiterte ereignisgesteuerte Prozesskette (eEPK)** für den Anwendungsfall "BMI berechnen" im BMI-Rechner-System.

### Anwendungsfall: BMI berechnen

**Beschreibung des Ablaufs:**

1. **Start:** Der Benutzer öffnet die Anwendung und möchte seinen BMI berechnen.

2. **Eingabe:** Der Benutzer gibt sein Gewicht (z.B. 75 kg) und seine Größe (z.B. 1.80 m) in die entsprechenden Eingabefelder der grafischen Oberfläche (MainWindow) ein.

3. **Aktion auslösen:** Der Benutzer klickt auf den Button "Berechne BMI".

4. **Weiterleitung:** Die View (MainWindow) nimmt das Button-Klick-Ereignis entgegen und leitet die eingegebenen Werte (Gewicht und Größe) an den Controller (BmiManager) weiter, indem sie die Methode `berechneBMI(gewicht, groesse)` aufruft.

5. **Steuerung:** Der Controller (BmiManager) erhält die Werte und gibt sie an das Model (Bmirechner) weiter, indem er die Methode `berechne(pGewicht, pGroesse)` aufruft.

6. **Verarbeitung:** Das Model (Bmirechner) berechnet den BMI nach der Formel: BMI = Gewicht / (Größe × Größe). Das Ergebnis wird im Attribut `ergebnis` gespeichert.

7. **Rückgabe:** Das Model gibt das Berechnungsergebnis an den Controller zurück.

8. **Aktualisierung:** Der Controller stellt das Ergebnis der View zur Verfügung.

9. **Anzeige:** Die View (MainWindow) holt sich das Ergebnis vom Model (über `getModel().getErgebnis()`) und zeigt es im Ergebnisfeld an: "BMI: 23.15".

10. **Ende:** Der Benutzer sieht das Ergebnis und kann weitere Aktionen durchführen oder die Anwendung beenden.

### Elemente für deine eEPK:

**Ereignisse (Sechsecke):**
- Anwendung gestartet
- Gewicht und Größe eingegeben
- Button "Berechne BMI" geklickt
- Daten an Controller übergeben
- Model hat BMI berechnet
- Ergebnis zurückgegeben
- View aktualisiert
- BMI-Wert angezeigt

**Funktionen (abgerundete Rechtecke):**
- Gewicht und Größe eingeben
- Button-Klick-Event verarbeiten
- Daten an Controller weiterleiten
- BMI berechnen
- Ergebnis an Controller zurückgeben
- Ergebnis von Model holen
- Ergebnis im Textfeld anzeigen

**Organisationseinheiten (Ellipsen):**
- Benutzer
- MainWindow (View)
- BmiManager (Controller)
- Bmirechner (Model)

**Informationsobjekte (Rechtecke mit geknickter Ecke):**
- Gewicht (75 kg)
- Größe (1.80 m)
- BMI-Wert (23.15)

### Hinweise:
- Verwende XOR-Konnektoren (×), wenn es alternative Wege gibt
- Verwende AND-Konnektoren (∧), wenn Prozesse parallel ablaufen
- Ordne die Organisationseinheiten (MainWindow, BmiManager, Bmirechner) den entsprechenden Funktionen zu
- Zeige den Datenfluss mit Informationsobjekten

### Ziel:
Die eEPK soll den gesamten Prozess von der Benutzereingabe bis zur Anzeige des Ergebnisses darstellen und dabei die MVC-Architektur deutlich machen.

---

## Vorteile von MVC

### 1. Trennung der Verantwortlichkeiten (Separation of Concerns)
- Jede Komponente hat eine klar definierte Aufgabe
- Änderungen in einer Komponente betreffen nicht die anderen
- Code ist leichter zu verstehen und zu warten

### 2. Wiederverwendbarkeit
- Das Model kann mit verschiedenen Views verwendet werden
- Beispiel: Konsolen-View und GUI-View nutzen dasselbe Model

### 3. Testbarkeit
- Komponenten können unabhängig getestet werden
- Model-Logik kann ohne GUI getestet werden

### 4. Parallele Entwicklung
- Verschiedene Entwickler können gleichzeitig an verschiedenen Komponenten arbeiten

### 5. Flexibilität
- Einfacher Austausch von Komponenten (z.B. andere GUI)
- Verschiedene Views für dasselbe Model möglich

---

## MVC im BMI-Rechner-Projekt

### Struktur

```
┌─────────────────────────────────────────────────┐
│              MainWindow (View)                  │
│  - Zeigt GUI-Elemente                           │
│  - Nimmt Benutzereingaben entgegen              │
│  - Ruft Controller-Methoden auf                 │
└─────────────────────────────────────────────────┘
                      │
                      │ nutzt
                      ▼
┌─────────────────────────────────────────────────┐
│            BmiManager (Controller)              │
│  - Vermittelt zwischen View und Model           │
│  - Steuert den Programmablauf                   │
│  - Keine Geschäftslogik                         │
└─────────────────────────────────────────────────┘
                      │
                      │ verwaltet
                      ▼
┌─────────────────────────────────────────────────┐
│            Bmirechner (Model)                   │
│  - Speichert Daten (Gewicht, Größe, BMI)       │
│  - Berechnet BMI (berechne())                   │
│  - Interpretiert BMI (interpretiere())          │
└─────────────────────────────────────────────────┘
```

### Beispiel-Ablauf: BMI berechnen

1. **Benutzer** gibt Gewicht (75 kg) und Größe (1.80 m) ein und klickt auf "Berechne BMI"
2. **View (MainWindow)** fängt das Button-Event ab und ruft `manager.berechneBMI(75, 1.80)` auf
3. **Controller (BmiManager)** erhält die Werte und ruft `model.berechne(75, 1.80)` auf
4. **Model (Bmirechner)** berechnet den BMI: 75 / (1.80 * 1.80) = 23.15
5. **Controller** gibt das Ergebnis an die View zurück
6. **View** zeigt das Ergebnis im Textfeld an: "BMI: 23.15"

---

## Implementierungsregeln

### ✅ DO (Machen)

1. **Model:**
   - Geschäftslogik und Datenverarbeitung
   - Getter und Setter für Attribute
   - Keine GUI-Elemente (kein `JFrame`, `JButton`, etc.)

2. **View:**
   - GUI-Aufbau und Event-Handling
   - Ruft Controller-Methoden auf
   - Zeigt Daten aus dem Model an (über Controller)

3. **Controller:**
   - Erzeugt das Model
   - Bietet Methoden für die View an
   - Ruft Model-Methoden auf
   - Gibt Model-Daten an die View weiter

### ❌ DON'T (Nicht machen)

1. **Model:**
   - ❌ Keine GUI-Elemente
   - ❌ Keine direkte Kommunikation mit der View
   - ❌ Keine System.out.println() für Benutzerausgaben

2. **View:**
   - ❌ Keine Geschäftslogik (Berechnungen)
   - ❌ Keine direkte Manipulation des Models
   - ❌ Keine Datenhaltung (nur Anzeige)

3. **Controller:**
   - ❌ Keine Geschäftslogik (nur Steuerung)
   - ❌ Keine GUI-Elemente
   - ❌ Keine komplexen Berechnungen

---

## Checkliste für MVC-Implementierung

- [ ] **Model ist unabhängig**: Keine Abhängigkeiten zu View oder Controller
- [ ] **View kennt nur Controller**: Keine direkte Kommunikation mit dem Model
- [ ] **Controller vermittelt**: Verbindet Model und View
- [ ] **Klare Verantwortlichkeiten**: Jede Komponente macht nur ihre Aufgabe
- [ ] **Testbarkeit**: Model kann ohne GUI getestet werden
- [ ] **Wiederverwendbarkeit**: Model kann mit verschiedenen Views genutzt werden

---

## Häufige Fehler

### Fehler 1: Model kennt die View
```java
// ❌ FALSCH
public class Bmirechner {
    private MainWindow view;  // Model sollte View nicht kennen!
}
```

### Fehler 2: Geschäftslogik in der View
```java
// ❌ FALSCH
public void actionPerformed(ActionEvent e) {
    double bmi = gewicht / (groesse * groesse);  // Logik gehört ins Model!
}
```

### Fehler 3: GUI-Elemente im Model
```java
// ❌ FALSCH
public class Bmirechner {
    private JTextField tfErgebnis;  // GUI gehört in die View!
}
```

---

## Zusammenfassung

| Komponente | Aufgabe | Kennt | Beispiel im Projekt |
|------------|---------|-------|---------------------|
| **Model** | Daten & Logik | Niemanden | `Bmirechner` |
| **View** | Darstellung | Controller | `MainWindow` |
| **Controller** | Vermittlung | Model & View | `BmiManager` |

---

## Weiterführende Links

- [Schritt-für-Schritt-Anleitung: MVC implementieren](../KONZEPTE/MVC_ANLEITUNG.md)
- [Assoziationen zwischen Klassen](../JAVA_PROGRAMMIERUNG/ASSOZIATIONEN.md)
- [Weitere Konzepte und Prompts](../../INFO.md)

---

**Tipp:** Beginne immer mit dem Model, dann dem Controller und zuletzt der View!
