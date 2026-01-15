# Ereignissteuerung und Controller-Integration

Diese Anleitung zeigt dir Schritt für Schritt, wie du den **Controller (BmiManager.java)** implementierst und ihn über **Event-Listener (Ereignissteuerung)** in der **MainWindow.java** mit der GUI verbindest.

## Übersicht: Wie funktioniert die Ereignissteuerung?

```
Benutzer klickt Button
    ↓
ActionListener wird ausgelöst
    ↓
Controller (BmiManager) wird aufgerufen
    ↓
Model (Bmirechner) führt Logik durch
    ↓
Ergebnis wird in GUI angezeigt
```

---

## 1. Controller implementieren (BmiManager.java)

Der **Controller** ist die Schnittstelle zwischen GUI und Model. Er koordiniert die Zusammenarbeit.

### 1.1 Klasse anlegen

Erstelle eine neue Datei `src/start/BmiManager.java`:

```java
package start;

public class BmiManager {
    private Bmirechner model;

    // Konstruktor
    public BmiManager() {
        this.model = new Bmirechner();
    }

    // Getter für Model
    public Bmirechner getModel() {
        return model;
    }

    // Hilfsmethode: BMI berechnen
    public double berechneBMI(double gewicht, double groesse) {
        return model.berechne(gewicht, groesse);
    }

    // Hilfsmethode: BMI interpretieren
    public String interpretiereIntelligent(double gewicht, double groesse, int alter, String geschlecht) {
        double bmi = model.berechne(gewicht, groesse);
        String kategorie = model.interpretiere(bmi, alter, geschlecht);
        return kategorie;
    }

    // Weitere Hilfsmethoden nach Bedarf
    public String getModel().getKategorie() {
        return model.getKategorie();
    }

    public void zeigeInterpretation() {
        System.out.println("Interpretation: " + model.getKategorie());
    }
}
```

### 1.2 Model-Klasse überprüfen

Stelle sicher, dass deine `Bmirechner.java` folgende Methoden hat:

```java
public class Bmirechner {
    // Berechnet BMI aus Gewicht (kg) und Größe (m)
    public double berechne(double gewicht, double groesse) {
        return gewicht / (groesse * groesse);
    }

    // Interpretiert BMI basierend auf Alter und Geschlecht
    public String interpretiere(double bmi, int alter, String geschlecht) {
        // Logik zur Kategorisierung (z.B. "Normal", "Übergewicht", etc.)
        // ...
    }

    // Gibt die aktuelle Kategorie aus
    public String getKategorie() {
        return this.kategorie; // oder ähnliches
    }
}
```

---

## 2. Ereignissteuerung in MainWindow.java

Die **MainWindow.java** ist deine GUI. Sie enthält die Button-Listener und ruft den Controller auf.

### 2.1 Controller-Instanz in MainWindow

```java
public class MainWindow extends JFrame {
    private BmiManager manager;  // ← WICHTIG: Controller als Feld
    
    private JTextField tfGewicht;
    private JTextField tfGroesse;
    private JComboBox<String> cbAlter;
    private JRadioButton rbMaennlich;
    private JRadioButton rbWeiblich;
    private JTextArea taErgebnis;
    
    private JButton btBerechneBmi;
    private JButton btInterpretiereBmi;
    private JButton btLeeren;
    private JButton btSchliessen;

    public MainWindow() {
        super("BMI-Rechner");
        this.manager = new BmiManager();  // ← Controller initialisieren
        
        // ... GUI-Komponenten initialisieren ...
        
        addEventListeners();  // ← Listener registrieren
    }
```

### 2.2 Event-Listener für Buttons

```java
private void addEventListeners() {
    // Button: "Berechne BMI"
    btBerechneBmi.addActionListener(e -> {
        try {
            // 1. Eingaben auslesen
            double gewicht = Double.parseDouble(tfGewicht.getText().trim());
            double groesse = Double.parseDouble(tfGroesse.getText().trim());
            
            // 2. Controller aufrufen
            double bmi = manager.berechneBMI(gewicht, groesse);
            
            // 3. Ergebnis anzeigen
            taErgebnis.setText(String.format("BMI: %.2f", bmi));
            
        } catch (NumberFormatException ex) {
            taErgebnis.setText("Fehler: Bitte nur Zahlen eingeben!");
        }
    });

    // Button: "Interpretiere BMI"
    btInterpretiereBmi.addActionListener(e -> {
        try {
            // 1. Eingaben auslesen
            double gewicht = Double.parseDouble(tfGewicht.getText().trim());
            double groesse = Double.parseDouble(tfGroesse.getText().trim());
            
            // 2. Alter aus ComboBox ermitteln
            String alterString = (String) cbAlter.getSelectedItem();
            int alter = mapAlterToMittelwert(alterString);
            
            // 3. Geschlecht ermitteln
            String geschlecht = rbMaennlich.isSelected() ? "männlich" : 
                               (rbWeiblich.isSelected() ? "weiblich" : null);
            
            // 4. Controller aufrufen
            String kategorie = manager.interpretiereIntelligent(gewicht, groesse, alter, geschlecht);
            
            // 5. Ergebnis anzeigen
            taErgebnis.setText(kategorie);
            
        } catch (NumberFormatException ex) {
            taErgebnis.setText("Fehler: Ungültige Eingaben!");
        }
    });

    // Button: "Leeren"
    btLeeren.addActionListener(e -> {
        tfGewicht.setText("");
        tfGroesse.setText("");
        cbAlter.setSelectedIndex(0);
        rbMaennlich.setSelected(false);
        rbWeiblich.setSelected(false);
        taErgebnis.setText("");
    });

    // Button: "Schließen"
    btSchliessen.addActionListener(e -> {
        System.exit(0);
    });
}
```

### 2.3 Hilfsmethode: Alter abbilden

```java
private int mapAlterToMittelwert(String altersgruppe) {
    return switch (altersgruppe) {
        case "18-25" -> 22;
        case "25-34" -> 30;
        case "35-44" -> 40;
        case "45-54" -> 50;
        case "55-64" -> 60;
        case "65-74" -> 70;
        case "75+" -> 80;
        default -> 30; // Fallback
    };
}
```

---

## 3. Komplett-Beispiel: MainWindow mit Ereignissteuerung

Hier ist ein vereinfachtes Beispiel der ganzen `MainWindow.java`:

```java
package start;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private BmiManager manager;
    
    private JTextField tfGewicht;
    private JTextField tfGroesse;
    private JComboBox<String> cbAlter;
    private JRadioButton rbMaennlich;
    private JRadioButton rbWeiblich;
    private JTextArea taErgebnis;
    
    private JButton btBerechneBmi;
    private JButton btInterpretiereBmi;
    private JButton btLeeren;
    private JButton btSchliessen;

    public MainWindow() {
        super("BMI-Rechner");
        this.manager = new BmiManager();
        
        // GUI initialisieren (mit WindowBuilder oder manuell)
        initializeGUI();
        
        // Event-Listener registrieren
        addEventListeners();
        
        // Fenster sichtbar
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initializeGUI() {
        JPanel contentPane = new JPanel(new GridLayout(4, 1, 5, 5));
        
        // Banner/Logo (vereinfacht)
        JLabel lbTitle = new JLabel("BMI-Rechner");
        contentPane.add(lbTitle);
        
        // Eingabe-Panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 2, 2));
        inputPanel.add(new JLabel("Gewicht (kg):"));
        tfGewicht = new JTextField();
        inputPanel.add(tfGewicht);
        
        inputPanel.add(new JLabel("Größe (m):"));
        tfGroesse = new JTextField();
        inputPanel.add(tfGroesse);
        
        inputPanel.add(new JLabel("Alter (Jahre):"));
        cbAlter = new JComboBox<>(new String[]{"-- nicht angegeben --", "18-25", "25-34", "35-44", "45-54", "55-64", "65-74", "75+"});
        inputPanel.add(cbAlter);
        
        inputPanel.add(new JLabel("Geschlecht:"));
        JPanel genderPanel = new JPanel();
        ButtonGroup bg = new ButtonGroup();
        rbMaennlich = new JRadioButton("Männlich");
        rbWeiblich = new JRadioButton("Weiblich");
        bg.add(rbMaennlich);
        bg.add(rbWeiblich);
        genderPanel.add(rbMaennlich);
        genderPanel.add(rbWeiblich);
        inputPanel.add(genderPanel);
        
        contentPane.add(inputPanel);
        
        // Ergebnis-Panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        taErgebnis = new JTextArea();
        taErgebnis.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taErgebnis);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(resultPanel);
        
        // Button-Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        btBerechneBmi = new JButton("Berechne BMI");
        btInterpretiereBmi = new JButton("Interpretiere BMI");
        btLeeren = new JButton("Leeren");
        btSchliessen = new JButton("Schließen");
        buttonPanel.add(btBerechneBmi);
        buttonPanel.add(btInterpretiereBmi);
        buttonPanel.add(btLeeren);
        buttonPanel.add(btSchliessen);
        contentPane.add(buttonPanel);
        
        this.add(contentPane);
    }

    private void addEventListeners() {
        btBerechneBmi.addActionListener(e -> {
            try {
                double gewicht = Double.parseDouble(tfGewicht.getText().trim());
                double groesse = Double.parseDouble(tfGroesse.getText().trim());
                double bmi = manager.berechneBMI(gewicht, groesse);
                taErgebnis.setText(String.format("BMI: %.2f", bmi));
            } catch (NumberFormatException ex) {
                taErgebnis.setText("Fehler: Bitte gültige Zahlen eingeben!");
            }
        });

        btInterpretiereBmi.addActionListener(e -> {
            try {
                double gewicht = Double.parseDouble(tfGewicht.getText().trim());
                double groesse = Double.parseDouble(tfGroesse.getText().trim());
                String altersgruppe = (String) cbAlter.getSelectedItem();
                int alter = mapAlterToMittelwert(altersgruppe);
                String geschlecht = rbMaennlich.isSelected() ? "männlich" : 
                                   (rbWeiblich.isSelected() ? "weiblich" : null);
                String kategorie = manager.interpretiereIntelligent(gewicht, groesse, alter, geschlecht);
                taErgebnis.setText(kategorie);
            } catch (NumberFormatException ex) {
                taErgebnis.setText("Fehler: Ungültige Eingaben!");
            }
        });

        btLeeren.addActionListener(e -> {
            tfGewicht.setText("");
            tfGroesse.setText("");
            cbAlter.setSelectedIndex(0);
            rbMaennlich.setSelected(false);
            rbWeiblich.setSelected(false);
            taErgebnis.setText("");
        });

        btSchliessen.addActionListener(e -> {
            System.exit(0);
        });
    }

    private int mapAlterToMittelwert(String altersgruppe) {
        return switch (altersgruppe) {
            case "18-25" -> 22;
            case "25-34" -> 30;
            case "35-44" -> 40;
            case "45-54" -> 50;
            case "55-64" -> 60;
            case "65-74" -> 70;
            case "75+" -> 80;
            default -> 30;
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}
```

---

## 4. Fehlerbehandlung & Best Practices

### 4.1 Try-Catch für Eingabevalidierung

```java
try {
    double gewicht = Double.parseDouble(tfGewicht.getText().trim());
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Ungültige Eingabe!", "Fehler", JOptionPane.ERROR_MESSAGE);
}
```

### 4.2 Null-Checks

```java
if (rbMaennlich.isSelected() || rbWeiblich.isSelected()) {
    String geschlecht = rbMaennlich.isSelected() ? "männlich" : "weiblich";
    // Weitermachen...
} else {
    taErgebnis.setText("Fehler: Geschlecht auswählen!");
}
```

### 4.3 Eingabefelder validieren

```java
private boolean validateInputs() {
    try {
        Double.parseDouble(tfGewicht.getText().trim());
        Double.parseDouble(tfGroesse.getText().trim());
        return true;
    } catch (NumberFormatException e) {
        taErgebnis.setText("Fehler: Gewicht und Größe müssen Zahlen sein!");
        return false;
    }
}
```

---

## 5. Testen der Ereignissteuerung

### 5.1 Lokal testen

```bash
# Kompilieren
javac -d build src/start/*.java

# Starten
java -cp build start.MainWindow
```

### 5.2 Mit Docker und noVNC testen

```bash
# Docker starten
docker compose -f docker-compose.novnc.yml up --build -d

# Im Browser öffnen: http://localhost:6080/vnc.html
```

---

## 6. Häufige Fehler und Lösungen

| Problem | Ursache | Lösung |
|---------|--------|--------|
| Button reagiert nicht | Listener nicht registriert | `addEventListeners()` im Konstruktor aufrufen |
| NumberFormatException | Benutzer gibt Text ein | Try-Catch verwenden, Eingabe validieren |
| NullPointerException bei `manager` | Controller nicht initialisiert | `this.manager = new BmiManager();` im Konstruktor |
| Ergebnis wird nicht angezeigt | `taErgebnis` ist null | Feld initialisieren und zur GUI hinzufügen |
| Geschlecht ist null | Keine RadioButton ausgewählt | Null-Check durchführen |

---

## 7. Zusammenfassung

Die **Ereignissteuerung** verbindet GUI und Logik:

1. **Button wird geklickt** → ActionListener wird ausgelöst
2. **Eingaben auslesen** → aus Textfeldern & ComboBox
3. **Controller aufrufen** → `manager.berechneBMI(...)`
4. **Model arbeitet** → Logik wird durchgeführt
5. **Ergebnis anzeigen** → in TextArea eintragen

Mit dieser Struktur hast du eine vollständige **MVC-Architektur** mit korrekter **Ereignissteuerung**! 🎉

---

## Weiterführende Ressourcen

- [MVC_KONZEPT.md](../KONZEPTE/MVC_KONZEPT.md) – MVC-Prinzip verstehen
- [GUI_ECLIPSE_WINDOWSBUILDER.md](./GUI_ECLIPSE_WINDOWSBUILDER.md) – GUI mit WindowBuilder erstellen
- [EXCEPTION_HANDLING.md](../BEST_PRACTICES/EXCEPTION_HANDLING.md) – Fehlerbehandlung
- [SECURE_CODING.md](../BEST_PRACTICES/SECURE_CODING.md) – Eingabevalidierung
