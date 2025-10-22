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

## 5. MVC-Prinzip anwenden
- Verbinde die neuen GUI-Elemente (ComboBox für Alter, RadioButtons für Geschlecht) mit Controller und Model
- Rufe die polymorphe Methode `interpretiere(gewicht, groesse, alter, geschlecht)` im Controller auf
- Nutze `JOptionPane` zur Anzeige des Ergebnisses

---

## 6. Anwendung testen
```bash
./build.sh
./run.sh
```
- Teste verschiedene Kombinationen von Alter und Geschlecht

---

## 6. Änderungen committen und pushen
```bash
git add .
git commit -m "feat: GUI und Logik für Version 2 erweitert"
git push
```

---

## Weitere Hilfen
- [POLYMORPHIE.md](./POLYMORPHIE.md) – Polymorphie verstehen und anwenden
- [SCHRITT_FUER_SCHRITT_GUI_V2.md](./SCHRITT_FUER_SCHRITT_GUI_V2.md)
- [KONTROLLSTRUKTUREN.md](./KONTROLLSTRUKTUREN.md)
- [MVC_KONZEPT.md](./MVC_KONZEPT.md)
- [SECURE_CODING.md](./SECURE_CODING.md)
