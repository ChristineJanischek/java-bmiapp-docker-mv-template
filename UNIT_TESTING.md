# Unit-Testing in Java – Praktischer Leitfaden

## 🎯 Was ist ein Unit-Test?

Ein **Unit-Test** (Modultest) prüft, ob eine einzelne Methode oder Klasse korrekt funktioniert.

**Ziel:** Fehler früh erkennen und sicherstellen, dass der Code wie erwartet funktioniert.

---

## 📋 Best Practices für Unit-Tests

### 1. **AAA-Prinzip** (Arrange-Act-Assert)

```java
// ARRANGE: Vorbereitung - Objekte erstellen, Werte setzen
Bmirechner rechner = new Bmirechner();

// ACT: Aktion - Methode ausführen
double ergebnis = rechner.berechne(70, 1.75);

// ASSERT: Überprüfung - Ergebnis testen
if (ergebnis == 22.86) {
    System.out.println("✅ Test bestanden");
} else {
    System.out.println("❌ Test fehlgeschlagen");
}
```

### Ausgabe in Tests: toString() verwenden

Anstatt mehrere Getter manuell zusammenzusetzen, ist es praktisch, das Objekt über
`toString()` auszugeben. Beispiel:

```java
Bmirechner rechner = new Bmirechner();
rechner.berechne(70, 1.75);
rechner.interpretiere();
System.out.println(rechner); // ruft rechner.toString() auf
```

Stelle sicher, dass `toString()` eine kurze, gut lesbare Zusammenfassung (z.B. "BMI: 22.9, Kategorie: Normalgewicht")
liefert, damit Testergebnisse leicht lesbar sind.

### 2. **Grenzwerte testen** (Boundary Testing)

Teste immer die **Grenzen** der Kategorien:
- Werte **knapp unter** dem Grenzwert
- Werte **genau auf** dem Grenzwert
- Werte **knapp über** dem Grenzwert

**Beispiel:** Normalgewicht (18.5 – < 25)
- 18.4 → Untergewicht ✅
- 18.5 → Normalgewicht ✅
- 24.9 → Normalgewicht ✅
- 25.0 → Übergewicht ✅

### 3. **Typische Testfälle**

1. **Normalfall:** Durchschnittliche, realistische Werte
2. **Grenzfälle:** Werte an den Kategorie-Grenzen
3. **Fehlerfälle:** Ungültige Eingaben (z.B. Größe = 0, negative Werte)
4. **Extremfälle:** Sehr kleine/große Werte

---

## 🧪 Testfälle für BMI-Rechner (Version 2)

### Testfälle: Einfache Interpretation (ohne Alter/Geschlecht)

| Gewicht (kg) | Größe (m) | Erwarteter BMI | Erwartete Kategorie    | Testtyp     |
|--------------|-----------|----------------|------------------------|-------------|
| 50           | 1.70      | 17.3           | Leichtes Untergewicht  | Normalfall  |
| 70           | 1.75      | 22.9           | Normalgewicht          | Normalfall  |
| 90           | 1.75      | 29.4           | Prädipositas           | Normalfall  |
| 53.5         | 1.70      | 18.5           | Normalgewicht          | Grenzwert ⚠️ |
| 72.2         | 1.70      | 25.0           | Prädipositas           | Grenzwert ⚠️ |
| 0            | 1.70      | 0              | (Fehlerfall)           | Fehlerfall  |
| 70           | 0         | (Fehler)       | (Fehlerfall)           | Fehlerfall  |

### Testfälle: Erweiterte Interpretation (mit Alter/Geschlecht)

| Gewicht | Größe | Alter | Geschlecht | BMI  | Kategorie      | Grund                        |
|---------|-------|-------|------------|------|----------------|------------------------------|
| 60      | 1.70  | 22    | männlich   | 20.8 | Normalgewicht  | Jung (19-24)                 |
| 56      | 1.70  | 22    | weiblich   | 19.4 | Normalgewicht  | Jung (19-24)                 |
| 55      | 1.70  | 22    | männlich   | 19.0 | **Grenzwert**  | Genau 19 (Grenze jung)       |
| 73      | 1.75  | 35    | männlich   | 23.8 | Normalgewicht  | Erwachsen männlich (20-25)   |
| 65      | 1.65  | 35    | weiblich   | 23.9 | Normalgewicht  | Erwachsen weiblich (19-24)   |
| 80      | 1.70  | 70    | männlich   | 27.7 | Normalgewicht  | Senior (22-28)               |
| 64      | 1.70  | 70    | weiblich   | 22.1 | **Grenzwert**  | Genau 22 (Grenze Senior)     |

---

## 💻 Implementierung eines Unit-Tests

### Einfacher Unit-Test in Main.java

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Unit-Tests für BMI-Rechner ===\n");
        
        // Test-Zähler
        int testsPassed = 0;
        int testsFailed = 0;
        
        // Test 1: Normalfall - Normalgewicht
        System.out.println("Test 1: Normalgewicht (70kg, 1.75m)");
        Bmirechner test1 = new Bmirechner();
        double ergebnis1 = test1.berechne(70, 1.75);
        test1.interpretiere();
        
        if (ergebnis1 >= 22.8 && ergebnis1 <= 22.9 && 
            test1.getKategorie().equals("Normalgewicht")) {
            System.out.println("✅ BESTANDEN\n");
            testsPassed++;
        } else {
            System.out.println("❌ FEHLGESCHLAGEN\n");
            testsFailed++;
        }
        
        // Test 2: Grenzwert - Übergang Normalgewicht → Prädipositas
        System.out.println("Test 2: Grenzwert (72.25kg, 1.70m = BMI 25.0)");
        Bmirechner test2 = new Bmirechner();
        double ergebnis2 = test2.berechne(72.25, 1.70);
        test2.interpretiere();
        
        if (ergebnis2 == 25.0 && test2.getKategorie().equals("Prädipositas")) {
            System.out.println("✅ BESTANDEN\n");
            testsPassed++;
        } else {
            System.out.println("❌ FEHLGESCHLAGEN\n");
            testsFailed++;
        }
        
        // Test 3: Polymorphie - Junge Erwachsene
        System.out.println("Test 3: Polymorphie - 22-jähriger (60kg, 1.70m)");
        Bmirechner test3 = new Bmirechner();
        test3.interpretiere(60, 1.70, 22, "männlich");
        
        if (test3.getKategorie().equals("Normalgewicht")) {
            System.out.println("✅ BESTANDEN\n");
            testsPassed++;
        } else {
            System.out.println("❌ FEHLGESCHLAGEN\n");
            testsFailed++;
        }
        
        // Zusammenfassung
        System.out.println("==================");
        System.out.println("Ergebnis: " + testsPassed + " bestanden, " + testsFailed + " fehlgeschlagen");
        if (testsFailed == 0) {
            System.out.println("🎉 Alle Tests erfolgreich!");
        }
    }
}
```

---

## 🔍 Erweiterte Test-Methode (Hilfsfunktion)

Um Tests übersichtlicher zu gestalten, kann man eine Hilfsmethode erstellen:

```java
public static void testBMI(String testName, double gewicht, double groesse, 
                           double erwarteterBMI, String erwarteteKategorie) {
    Bmirechner rechner = new Bmirechner();
    double ergebnis = rechner.berechne(gewicht, groesse);
    rechner.interpretiere();
    
    boolean bmiKorrekt = Math.abs(ergebnis - erwarteterBMI) < 0.1;
    boolean kategorieKorrekt = rechner.getKategorie().equals(erwarteteKategorie);
    
    System.out.println(testName);
    System.out.println("  BMI: " + ergebnis + " (erwartet: " + erwarteterBMI + ")");
    System.out.println("  Kategorie: " + rechner.getKategorie() + " (erwartet: " + erwarteteKategorie + ")");
    
    if (bmiKorrekt && kategorieKorrekt) {
        System.out.println("  ✅ BESTANDEN\n");
    } else {
        System.out.println("  ❌ FEHLGESCHLAGEN\n");
    }
}

// Aufruf:
testBMI("Test 1: Normalgewicht", 70, 1.75, 22.9, "Normalgewicht");
```

---

## 📊 Checkliste für gute Unit-Tests

- [ ] **Aussagekräftiger Name**: Test beschreibt, was getestet wird
- [ ] **Unabhängigkeit**: Jeder Test läuft eigenständig
- [ ] **Reproduzierbarkeit**: Gleiches Ergebnis bei jedem Durchlauf
- [ ] **Schnell**: Tests laufen in Sekunden, nicht Minuten
- [ ] **Klare Ausgabe**: ✅/❌ und Begründung bei Fehlschlag
- [ ] **Grenzwerte testen**: Besonders wichtig bei if-Bedingungen

---

## 🎓 Testgetriebene Entwicklung (TDD)

**Red-Green-Refactor-Zyklus:**

1. **Red** 🔴: Test schreiben (schlägt fehl, da Code noch fehlt)
2. **Green** 🟢: Minimalen Code schreiben, damit Test besteht
3. **Refactor** 🔵: Code verbessern, Tests bleiben grün

**Vorteil:** Code ist von Anfang an getestet!

---

## 💡 Merksatz

> **"Teste das, was schiefgehen kann – nicht das, was funktionieren muss!"**

Fokussiere dich auf:
- Grenzwerte
- Ungültige Eingaben
- Spezialfälle (z.B. Größe = 0)
- Verschiedene Parameterkombinationen

---

## 🚀 Weiterführende Konzepte

- **JUnit**: Professionelles Test-Framework für Java
- **Mocking**: Simulieren von Abhängigkeiten
- **Code Coverage**: Wie viel Prozent des Codes sind getestet?
- **Integration Tests**: Testen des Zusammenspiels mehrerer Komponenten
