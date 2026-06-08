# Changelog - Version 2: Polymorphie & Intelligente Methodenwahl

## 🎯 Überblick

Version 2 erweitert den BMI-Rechner um **alters- und geschlechtsspezifische Interpretation** basierend auf wissenschaftlichen BMI-Tabellen. Die Implementation demonstriert **Polymorphie** (Method Overloading), **intelligente Controller-Logik** und **GUI-Erweiterung** mit Swing-Komponenten.

---

## 📋 Implementierte Features

### 1. Polymorphe `interpretiere()`-Methode (Model)

**Datei:** `src/start/Bmirechner.java`

#### Variante 1: Einfache Interpretation (WHO-Standard)
```java
public void interpretiere(double pGewicht, double pGroesse)
```
- Basiert auf allgemeinen WHO-Kategorien
- Keine Berücksichtigung von Alter/Geschlecht
- Für Schnellauswertung ohne Detailangaben

#### Variante 2: Erweiterte Interpretation (Alters-/Geschlechtsspezifisch)
```java
public void interpretiere(double pGewicht, double pGroesse, int pAlter, String pGeschlecht)
```

**BMI-Kategorien nach Altersgruppen:**

| Kategorie | Jung (<25 J.) | Erwachsen (Mann) | Erwachsen (Frau) | Senior (≥65 J.) |
|-----------|---------------|------------------|------------------|-----------------|
| Untergewicht | <19 | <20 | <19 | <24 |
| Normalgewicht | 19-24 | 20-25 | 19-24 | 24-29 |
| Übergewicht | 25-30 | 26-30 | 25-30 | 30-35 |
| Adipositas | >30 | >30 | >30 | >35 |

**Alterseinteilung:**
- **Jung:** < 25 Jahre
- **Erwachsen:** 25-64 Jahre (geschlechtsspezifisch)
- **Senior:** ≥ 65 Jahre

---

### 2. Intelligente Methodenwahl (Controller)

**Datei:** `src/start/BmiManager.java`

#### Neue Methoden:

##### `interpretiereIntelligent()`
```java
public void interpretiereIntelligent(double pGewicht, double pGroesse, int pAlter, String pGeschlecht)
```

**Entscheidungslogik:**
- **Fall 1:** Alter **UND** Geschlecht vorhanden → Erweiterte Interpretation
- **Fall 2:** Alter **ODER** Geschlecht fehlt → Einfache Interpretation

**Vorteile:**
- ✅ Keine Fehler bei fehlenden Parametern
- ✅ Automatische Wahl der passenden Methode
- ✅ Nutzer muss sich nicht um Logik kümmern

##### `zeigeInterpretation()`
```java
public void zeigeInterpretation()
```
- Zeigt BMI-Kategorie in JOptionPane-Dialog
- Benutzerfreundliches Feedback
- Blockiert GUI während Anzeige

---

### 3. GUI-Erweiterung (View)

**Datei:** `src/start/MainWindow.java`

#### Neue Komponenten:

| Komponente | Typ | Zweck |
|------------|-----|-------|
| `cbAlter` | `JComboBox<String>` | Auswahl Altersgruppe (18-24 bis 75+) |
| `rbMann` | `JRadioButton` | Geschlechtsauswahl "Männlich" |
| `rbFrau` | `JRadioButton` | Geschlechtsauswahl "Weiblich" |
| `bgGeschlecht` | `ButtonGroup` | Exklusivität der RadioButtons |

#### Neue Methoden:

##### `getAlterValue()`
```java
public int getAlterValue()
```
- Liest ComboBox-Auswahl
- Berechnet Mittelwert der Altersgruppe:
  - `"18-24"` → `21`
  - `"35-44"` → `40`
  - `"75+"` → `75`
- Gibt `0` zurück bei "-- nicht angegeben --"

##### `getGeschlechtValue()`
```java
public String getGeschlechtValue()
```
- Prüft RadioButton-Status (`isSelected()`)
- Gibt `"Mann"` oder `"Frau"` zurück
- Gibt `null` zurück bei fehlender Auswahl

#### Layout-Änderungen:

**GridBagLayout-Anpassung:**
- **Alter-Label/ComboBox:** `gridy = 4`
- **Geschlecht-Label/Panel:** `gridy = 5`
- **Ergebnisfeld:** `gridy = 6` (vorher: 4)
- **Buttons (Berechne/Interpretiere):** `gridy = 7` (vorher: 5)
- **Buttons (Leeren/Schließen):** `gridy = 8` (vorher: 6)

#### Event-Handler-Änderung:

**Vorher (einfache Interpretation):**
```java
btInterpretiere.addActionListener(e -> {
    manager.berechneBMI(getGewichtValue(), getGroesseValue());
    manager.interpretiereBMI();
    schreibeKategorie();
});
```

**Nachher (intelligente Interpretation):**
```java
btInterpretiere.addActionListener(e -> {
    manager.interpretiereIntelligent(
        getGewichtValue(), 
        getGroesseValue(), 
        getAlterValue(), 
        getGeschlechtValue()
    );
    manager.zeigeInterpretation();
    schreibeKategorie();
});
```

---

### 4. Unit-Testing

**Datei:** `src/start/Main.java`

#### Test-Coverage:

| Test-Kategorie | Anzahl Tests | Status |
|----------------|--------------|--------|
| Einfache Interpretation | 4 | ✅ |
| Polymorphe Interpretation | 4 | ✅ |
| **Gesamt** | **8** | **✅** |

**Test-Strategien:**
- **AAA-Prinzip** (Arrange-Act-Assert)
- **Boundary Testing** (Grenzwerte)
- **Äquivalenzklassen** (Untergewicht, Normal, Übergewicht, Adipositas)

**Beispiel-Test:**
```java
// Test: Erwachsener Mann mit Normalgewicht
bmi.interpretiere(75, 1.75, 40, "Mann"); // BMI = 24.49
assertEquals("Normalgewicht (Mann, 25-64 Jahre)", bmi.getKategorie());
```

---

### 5. Intelligente Methodenwahl Testing

**Datei:** `src/start/TestIntelligent.java`

#### Test-Szenarien:

| Test | Alter | Geschlecht | Erwartete Methode |
|------|-------|------------|-------------------|
| 1 | 30 | "Mann" | Erweitert (4 Parameter) |
| 2 | 0 | "Frau" | Einfach (2 Parameter) |
| 3 | 40 | null | Einfach (2 Parameter) |
| 4 | 0 | null | Einfach (2 Parameter) |
| 5 | 70 | "Frau" | Erweitert (4 Parameter) |

**Test-Coverage:** 5/5 Tests bestanden ✅

---

## 📚 Dokumentation

Neue Dokumentationsdateien:

1. **`POLYMORPHIE.md`**
   - Erklärung von Method Overloading
   - Beispiele für Polymorphie
   - Best Practices
   - Unterschied zu Overriding

2. **`UNIT_TESTING.md`**
   - AAA-Prinzip (Arrange-Act-Assert)
   - Boundary Testing
   - Test-Design-Strategien
   - 8 konkrete Test-Cases mit Tabellen

3. **`INTELLIGENTE_METHODENWAHL.md`**
   - Controller-Logik Entscheidungsbaum
   - Warum Controller statt View?
   - MVC-Konformität
   - Code-Beispiele

4. **`SCHRITTE_VERSION_2.md`** (erweitert)
   - **Schritt 7:** GUI-Erweiterung (Alter & Geschlecht)
   - Code-Snippets für alle Komponenten
   - Layout-Anpassung Schritt-für-Schritt
   - Test-Anleitungen mit noVNC

---

## 🎓 Lernziele (erreicht)

- ✅ **Polymorphie:** Method Overloading mit 2 und 4 Parametern
- ✅ **MVC-Pattern:** Trennung von View, Controller, Model beibehalten
- ✅ **GUI-Komponenten:** ComboBox, RadioButton, ButtonGroup
- ✅ **Event-Handling:** ActionListener für intelligente Methodenwahl
- ✅ **Unit-Testing:** 8 Tests mit AAA-Prinzip und Boundary Testing
- ✅ **Controller-Logik:** Intelligente Entscheidung basierend auf Parametern
- ✅ **Datenfluss:** Von GUI über Controller zum Model und zurück

---

## 🧪 Testing

### GUI-Test (mit noVNC)

```bash
docker-compose -f docker-compose.novnc.yml up
```

Öffne: http://localhost:6080

**Test-Szenario 1: Einfache Interpretation**
1. Gewicht: `75` kg
2. Größe: `1.75` m
3. Alter: `-- nicht angegeben --`
4. Geschlecht: keine Auswahl
5. Klicke **"Interpretiere BMI"**
6. **Erwartetes Ergebnis:** JOptionPane zeigt allgemeine WHO-Kategorie

**Test-Szenario 2: Erweiterte Interpretation**
1. Gewicht: `75` kg
2. Größe: `1.75` m
3. Alter: `35-44` (Mittelwert: 40)
4. Geschlecht: `Männlich`
5. Klicke **"Interpretiere BMI"**
6. **Erwartetes Ergebnis:** JOptionPane zeigt "Normalgewicht (Mann, 25-64 Jahre)"

**Test-Szenario 3: Reset-Funktion**
1. Fülle alle Felder aus
2. Klicke **"Leeren"**
3. **Erwartetes Ergebnis:** 
   - TextFields leer
   - ComboBox auf "-- nicht angegeben --"
   - RadioButtons abgewählt

---

## 🔄 Datenfluss-Diagramm

```
[GUI: MainWindow]
    ↓
1. User wählt Alter "35-44" → getAlterValue() → 40
2. User wählt "Männlich" → getGeschlechtValue() → "Mann"
3. User klickt "Interpretiere BMI"
    ↓
[Controller: BmiManager]
4. interpretiereIntelligent(75, 1.75, 40, "Mann")
5. Prüfung: Alter != 0 && Geschlecht != null → true
6. Aufruf: interpretiereBMI(75, 1.75, 40, "Mann")
    ↓
[Model: Bmirechner]
7. berechneBMI(75, 1.75) → BMI = 24.49
8. interpretiere(75, 1.75, 40, "Mann")
9. Alter = 40 → erwachsen, Geschlecht = Mann
10. Kategorie: "Normalgewicht (Mann, 25-64 Jahre)"
    ↓
[Controller: BmiManager]
11. zeigeInterpretation() → JOptionPane.showMessageDialog()
    ↓
[GUI: MainWindow]
12. schreibeKategorie() → tfErgebnis.setText(...)
```

---

## 📦 Geänderte Dateien

| Datei | Änderungen | Zeilen |
|-------|-----------|--------|
| `Bmirechner.java` | Polymorphe `interpretiere()` hinzugefügt | +45 |
| `BmiManager.java` | `interpretiereIntelligent()`, `zeigeInterpretation()` | +25 |
| `MainWindow.java` | ComboBox, RadioButtons, Helper-Methoden | +95 |
| `Main.java` | 8 Unit-Tests (komplett neu) | +150 |
| `TestIntelligent.java` | 5 Tests für intelligente Auswahl | +75 |
| `POLYMORPHIE.md` | Neue Dokumentation | +120 |
| `UNIT_TESTING.md` | Neue Dokumentation | +180 |
| `INTELLIGENTE_METHODENWAHL.md` | Neue Dokumentation | +95 |
| `SCHRITTE_VERSION_2.md` | Schritt 7 ergänzt | +165 |

**Gesamt:** +950 Zeilen Code & Dokumentation

---

## 🚀 Nächste Schritte → Version 3

In **Version 3** werden folgende Features implementiert:

1. **Input-Validierung:**
   - Prüfung: Gewicht > 0, Größe > 0
   - Try-Catch für NumberFormatException
   - Fehlerbehandlung mit JOptionPane

2. **Erweiterte Fehlerbehandlung:**
   - Custom Exception-Klassen
   - Logging mit `java.util.logging`

3. **Persistenz:**
   - Speichern von Berechnungen in Datei
   - Verlaufs-Anzeige

→ Siehe [`SCHRITTE_VERSION_3.md`](../ANLEITUNGEN/SCHRITTE_VERSION_3.md)

---

## ✅ Checkliste Version 2

- [x] Polymorphe `interpretiere()`-Methode (2 + 4 Parameter)
- [x] Alters-/Geschlechtsspezifische BMI-Tabellen
- [x] Unit-Tests (8/8 Tests bestanden)
- [x] Intelligente Controller-Logik (`interpretiereIntelligent()`)
- [x] GUI-Erweiterung (ComboBox, RadioButtons)
- [x] Event-Handler-Anpassung
- [x] JOptionPane-Integration
- [x] Reset-Funktion für neue Komponenten
- [x] Dokumentation (POLYMORPHIE.md, UNIT_TESTING.md, INTELLIGENTE_METHODENWAHL.md)
- [x] Test-Coverage (Main.java, TestIntelligent.java)

---

**Version:** 2.0.0  
**Branch:** `version-2-methoden`  
**Status:** ✅ Abgeschlossen  
**Datum:** 2025

