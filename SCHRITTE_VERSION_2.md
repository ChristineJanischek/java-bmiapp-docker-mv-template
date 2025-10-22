# Schritt-für-Schritt-Anleitung Version 2 (Methoden, Kontrollstrukturen, Algorithmen)

## Ziel
Erweitere die Anwendung um zusätzliche Methoden, Kontrollstrukturen und Algorithmen. Ergänze die GUI um Alter (ComboBox) und Geschlecht (RadioButtons) und verbinde alles nach dem MVC-Prinzip.

---

## 1. Branch wechseln
```bash
git checkout version-2-methoden
git pull
```

---

## 2. GUI erweitern
- Füge eine ComboBox für das Alter (`cbAlter`) hinzu
- Füge RadioButtons für das Geschlecht (`rbMann`, `rbFrau`) hinzu
- Siehe Beispiel in [SCHRITT_FUER_SCHRITT_GUI_V2.md](./SCHRITT_FUER_SCHRITT_GUI_V2.md)

---

## 3. Polymorphe Methode implementieren

Erweitere die Klasse `Bmirechner` um eine **überladene** `interpretiere()`-Methode:

```java
// Neue, überladene Methode mit 4 Parametern
public void interpretiere(double pGewicht, double pGroesse, int pAlter, String pGeschlecht) {
    berechne(pGewicht, pGroesse);
    // Alters- und geschlechtsspezifische Interpretation (siehe Wertetabelle unten)
}
```

**Was ist Polymorphie?** 
Mehrere Methoden mit gleichem Namen, aber unterschiedlichen Parametern. Siehe [POLYMORPHIE.md](./POLYMORPHIE.md) für Details und Beispiele.

### Erweiterte BMI-Wertetabelle (Alter + Geschlecht)

Die Interpretation berücksichtigt jetzt Alter und Geschlecht:

#### Junge Erwachsene (< 25 Jahre)
| BMI-Wert    | Kategorie       |
|-------------|-----------------|
| < 19        | Untergewicht    |
| 19 – < 24   | Normalgewicht   |
| ≥ 24        | Übergewicht     |

#### Erwachsene (25 – 64 Jahre) – Männlich
| BMI-Wert    | Kategorie       |
|-------------|-----------------|
| < 20        | Untergewicht    |
| 20 – < 25   | Normalgewicht   |
| ≥ 25        | Übergewicht     |

#### Erwachsene (25 – 64 Jahre) – Weiblich
| BMI-Wert    | Kategorie       |
|-------------|-----------------|
| < 19        | Untergewicht    |
| 19 – < 24   | Normalgewicht   |
| ≥ 24        | Übergewicht     |

#### Senioren (≥ 65 Jahre)
| BMI-Wert    | Kategorie       |
|-------------|-----------------|
| < 22        | Untergewicht    |
| 22 – < 28   | Normalgewicht   |
| ≥ 28        | Übergewicht     |

**Hinweis:** Diese Werte sind vereinfacht für das Lernprojekt. Medizinisch korrekte Werte können abweichen.

---

## 4. Ergebnis mit JOptionPane anzeigen

Zeige die Interpretation in einem Dialog-Fenster an:

```java
import javax.swing.JOptionPane;

// Im Controller (BmiManager):
public void zeigeInterpretation() {
    String ergebnis = model.getKategorie();
    double bmi = model.getErgebnis();
    
    String nachricht = String.format(
        "Ihr BMI: %.2f\nKategorie: %s",
        bmi, ergebnis
    );
    
    JOptionPane.showMessageDialog(
        null,                           // Kein Parent-Frame
        nachricht,                      // Nachricht
        "BMI-Interpretation",           // Titel
        JOptionPane.INFORMATION_MESSAGE // Icon-Typ
    );
}
```

---

## 5. Unit-Tests implementieren

Bevor du die GUI programmierst, teste deine Implementierung mit Unit-Tests in `Main.java`.

### Testfälle für die einfache Interpretation

Implementiere mindestens diese Grenzwert-Tests:

| Test | Gewicht (kg) | Größe (m) | Erwarteter BMI | Erwartete Kategorie    |
|------|--------------|-----------|----------------|------------------------|
| 1    | 70           | 1.75      | 22.9           | Normalgewicht          |
| 2    | 53.5         | 1.70      | 18.5           | Normalgewicht          |
| 3    | 72.25        | 1.70      | 25.0           | Prädipositas           |
| 4    | 50           | 1.70      | 17.3           | Leichtes Untergewicht  |

### Testfälle für die polymorphe Interpretation (Alter + Geschlecht)

| Test | Gewicht | Größe | Alter | Geschlecht | Erwartete Kategorie |
|------|---------|-------|-------|------------|---------------------|
| 5    | 60      | 1.70  | 22    | männlich   | Normalgewicht       |
| 6    | 73      | 1.75  | 35    | männlich   | Normalgewicht       |
| 7    | 65      | 1.65  | 35    | weiblich   | Normalgewicht       |
| 8    | 80      | 1.70  | 70    | männlich   | Normalgewicht       |

### Beispiel-Implementierung in Main.java

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Unit-Tests BMI-Rechner Version 2 ===\n");
        
        int testsPassed = 0;
        int testsFailed = 0;
        
        // Test 1: Normalfall
        System.out.println("Test 1: Normalgewicht (70kg, 1.75m)");
        Bmirechner test1 = new Bmirechner();
        test1.berechne(70, 1.75);
        test1.interpretiere();
        
        if (test1.getKategorie().equals("Normalgewicht")) {
            System.out.println("✅ BESTANDEN\n");
            testsPassed++;
        } else {
            System.out.println("❌ FEHLGESCHLAGEN - Erwartet: Normalgewicht, Erhalten: " 
                + test1.getKategorie() + "\n");
            testsFailed++;
        }
        
        // Test 2: Polymorphie - Junge Erwachsene
        System.out.println("Test 2: Polymorphie - 22-jähriger Mann (60kg, 1.70m)");
        Bmirechner test2 = new Bmirechner();
        test2.interpretiere(60, 1.70, 22, "männlich");
        
        if (test2.getKategorie().equals("Normalgewicht")) {
            System.out.println("✅ BESTANDEN\n");
            testsPassed++;
        } else {
            System.out.println("❌ FEHLGESCHLAGEN - Erwartet: Normalgewicht, Erhalten: " 
                + test2.getKategorie() + "\n");
            testsFailed++;
        }
        
        // Weitere Tests hier hinzufügen...
        
        // Zusammenfassung
        System.out.println("==================");
        System.out.println("Tests bestanden: " + testsPassed);
        System.out.println("Tests fehlgeschlagen: " + testsFailed);
        if (testsFailed == 0) {
            System.out.println("🎉 Alle Tests erfolgreich!");
        }
    }
}
```

**Weitere Infos:** Siehe [UNIT_TESTING.md](./UNIT_TESTING.md) für Best Practices und erweiterte Testmethoden.

---

## 6. MVC-Prinzip anwenden
- Verbinde die neuen GUI-Elemente (ComboBox für Alter, RadioButtons für Geschlecht) mit Controller und Model
- Rufe die polymorphe Methode `interpretiere(gewicht, groesse, alter, geschlecht)` im Controller auf
- Nutze `JOptionPane` zur Anzeige des Ergebnisses

---

## 7. Anwendung testen
```bash
./build.sh
./run.sh
```
- Teste verschiedene Kombinationen von Alter und Geschlecht

---

## 8. Änderungen committen und pushen
```bash
git add .
git commit -m "feat: GUI und Logik für Version 2 erweitert"
git push
```

---

## Weitere Hilfen
- [UNIT_TESTING.md](./UNIT_TESTING.md) – Unit-Tests verstehen und implementieren
- [POLYMORPHIE.md](./POLYMORPHIE.md) – Polymorphie verstehen und anwenden
- [SCHRITT_FUER_SCHRITT_GUI_V2.md](./SCHRITT_FUER_SCHRITT_GUI_V2.md)
- [KONTROLLSTRUKTUREN.md](./KONTROLLSTRUKTUREN.md)
- [MVC_KONZEPT.md](./MVC_KONZEPT.md)
- [SECURE_CODING.md](./SECURE_CODING.md)
