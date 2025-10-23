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

### Ergänzung: toString() & Tests

Für Debugging und Tests sollten Modelle eine `toString()`-Methode implementieren. Die
Unit-Tests in `Main.java` bzw. Testprogrammen dürfen die Objekte mit `System.out.println(obj)`
ausgeben — das ruft `toString()` auf. Das macht Testausgaben kompakter und stabiler.

---

## 6. MVC-Prinzip anwenden

### Controller erweitern (BmiManager)

Füge eine intelligente Methode hinzu, die automatisch entscheidet, welche Interpretation verwendet wird:

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

**Warum im Controller?** Siehe [INTELLIGENTE_METHODENWAHL.md](./INTELLIGENTE_METHODENWAHL.md)

### GUI-Integration (MainWindow)

Im ActionListener des "Berechnen"-Buttons:

```java
// Werte aus GUI auslesen
double gewicht = Double.parseDouble(txtGewicht.getText());
double groesse = Double.parseDouble(txtGroesse.getText());
int alter = cbAlter.getSelectedIndex();  // 0 = nicht ausgewählt

String geschlecht = null;
if (rbMann.isSelected()) {
    geschlecht = "männlich";
} else if (rbFrau.isSelected()) {
    geschlecht = "weiblich";
}

// Controller entscheidet automatisch, welche Methode aufgerufen wird!
manager.interpretiereIntelligent(gewicht, groesse, alter, geschlecht);
manager.zeigeInterpretation();
```

---

## 7. GUI-Erweiterung: Alter & Geschlecht

### 🎯 Lernziel
Erweitere die grafische Benutzeroberfläche um Eingabefelder für Alter und Geschlecht und verknüpfe diese mit der intelligenten Methodenwahl im Controller.

### Schritt 7.1: GUI-Komponenten deklarieren

Füge in `MainWindow.java` folgende Felder nach den bestehenden TextFields hinzu:

```java
private JComboBox<String> cbAlter;      // ComboBox für Altersgruppen
private JRadioButton rbMann;             // RadioButton für "Männlich"
private JRadioButton rbFrau;             // RadioButton für "Weiblich"
private ButtonGroup bgGeschlecht;        // ButtonGroup für RadioButtons
```

**Imports ergänzen:**
```java
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
```

### Schritt 7.2: ComboBox für Alter erstellen

Füge im Konstruktor nach dem Größe-Feld (nach `gridy=3`) folgende Komponenten ein:

```java
// Alter (Version 2) - ComboBox
gbc.gridx = 0; gbc.gridy = 4;
gbc.weightx = 0.3;
gbc.anchor = GridBagConstraints.EAST;
JLabel lbAlter = new JLabel("Alter (Jahre):");
contentPane.add(lbAlter, gbc);
gbc.gridx = 1;
gbc.weightx = 0.7;
gbc.anchor = GridBagConstraints.WEST;

// ComboBox mit Altersgruppen
String[] altersGruppen = {
    "-- nicht angegeben --",
    "18-24", "25-34", "35-44", "45-54", 
    "55-64", "65-74", "75+"
};
cbAlter = new JComboBox<>(altersGruppen);
contentPane.add(cbAlter, gbc);
```

### Schritt 7.3: RadioButtons für Geschlecht erstellen

```java
// Geschlecht (Version 2) - RadioButtons
gbc.gridx = 0; gbc.gridy = 5;
gbc.weightx = 0.3;
gbc.anchor = GridBagConstraints.EAST;
JLabel lbGeschlecht = new JLabel("Geschlecht:");
contentPane.add(lbGeschlecht, gbc);

gbc.gridx = 1;
gbc.weightx = 0.7;
gbc.anchor = GridBagConstraints.WEST;

// Panel für RadioButtons
JPanel panelGeschlecht = new JPanel();
panelGeschlecht.setBackground(Color.WHITE);
rbMann = new JRadioButton("Männlich");
rbMann.setBackground(Color.WHITE);
rbFrau = new JRadioButton("Weiblich");
rbFrau.setBackground(Color.WHITE);

// ButtonGroup sorgt dafür, dass nur ein RadioButton ausgewählt werden kann
bgGeschlecht = new ButtonGroup();
bgGeschlecht.add(rbMann);
bgGeschlecht.add(rbFrau);

panelGeschlecht.add(rbMann);
panelGeschlecht.add(rbFrau);
contentPane.add(panelGeschlecht, gbc);
```

**⚠️ Wichtig:** Passe alle nachfolgenden `gridy`-Werte an:
- Ergebnisfeld: `gridy = 6` (statt 4)
- Buttons (Berechne/Interpretiere): `gridy = 7` (statt 5)
- Buttons (Leeren/Schließen): `gridy = 8` (statt 6)

### Schritt 7.4: Hilfsmethoden für Alter & Geschlecht

Erstelle zwei neue Methoden in `MainWindow.java`:

```java
/**
 * Liest das Alter aus dem ComboBox (Version 2).
 * Berechnet den Mittelwert der ausgewählten Altersgruppe.
 * @return Alter als int (0 wenn keine Gruppe gewählt)
 */
public int getAlterValue() {
    String auswahl = (String) cbAlter.getSelectedItem();
    if (auswahl == null || auswahl.equals("-- nicht angegeben --")) {
        return 0;
    }
    
    // Berechne Mittelwert der Altersgruppe
    switch (auswahl) {
        case "18-24": return 21;
        case "25-34": return 30;
        case "35-44": return 40;
        case "45-54": return 50;
        case "55-64": return 60;
        case "65-74": return 70;
        case "75+": return 75;
        default: return 0;
    }
}

/**
 * Liest das Geschlecht aus den RadioButtons (Version 2).
 * @return "Mann", "Frau" oder null (wenn nichts ausgewählt)
 */
public String getGeschlechtValue() {
    if (rbMann.isSelected()) {
        return "Mann";
    } else if (rbFrau.isSelected()) {
        return "Frau";
    }
    return null;
}
```

### Schritt 7.5: Event-Handler anpassen

**Ändere den ActionListener für `btInterpretiere`:**

```java
// Button: BMI interpretieren
JButton btInterpretiere = new JButton("Interpretiere BMI");
btInterpretiere.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        // Version 2: Nutzt intelligente Methodenwahl mit Alter & Geschlecht
        manager.interpretiereIntelligent(
            getGewichtValue(), 
            getGroesseValue(), 
            getAlterValue(), 
            getGeschlechtValue()
        );
        manager.zeigeInterpretation();
        schreibeKategorie();
    }
});
```

### Schritt 7.6: `clearFields()` erweitern

```java
public void clearFields() {
    tfGewicht.setText("");
    tfGroesse.setText("");
    tfErgebnis.setText("");
    cbAlter.setSelectedIndex(0);      // Zurück auf "-- nicht angegeben --"
    bgGeschlecht.clearSelection();     // Keine Auswahl bei RadioButtons
}
```

### 🧪 GUI-Test (mit noVNC)

```bash
docker-compose -f docker-compose.novnc.yml up
```

Öffne http://localhost:6080 und teste:

1. **Einfache Interpretation** (ohne Alter/Geschlecht):
   - Gewicht: `75` kg, Größe: `1.75` m
   - Klicke "Interpretiere BMI" → Allgemeine WHO-Kategorie

2. **Erweiterte Interpretation** (mit Alter/Geschlecht):
   - Gewicht: `75` kg, Größe: `1.75` m
   - Alter: `35-44`, Geschlecht: `Männlich`
   - Klicke "Interpretiere BMI" → Geschlechtsspezifische Kategorie

3. **Reset**: Klicke "Leeren" → Alle Felder zurückgesetzt

---

## 8. Anwendung testen
```bash
./build.sh
./run.sh  # Oder mit noVNC: docker-compose -f docker-compose.novnc.yml up
```
- Teste verschiedene Kombinationen von Alter und Geschlecht
- Prüfe, ob ohne Alter/Geschlecht die einfache Methode verwendet wird

---

## 9. Änderungen committen und pushen
```bash
git add .
git commit -m "feat: GUI mit Alter & Geschlecht erweitert, intelligente Methodenwahl implementiert"
git push
```

---

## Weitere Hilfen
- [INTELLIGENTE_METHODENWAHL.md](./INTELLIGENTE_METHODENWAHL.md) – Entscheidungslogik im Controller
- [UNIT_TESTING.md](./UNIT_TESTING.md) – Unit-Tests verstehen und implementieren
- [POLYMORPHIE.md](./POLYMORPHIE.md) – Polymorphie verstehen und anwenden
- [KONTROLLSTRUKTUREN.md](./KONTROLLSTRUKTUREN.md)
- [MVC_KONZEPT.md](./MVC_KONZEPT.md)
- [SECURE_CODING.md](./SECURE_CODING.md)
