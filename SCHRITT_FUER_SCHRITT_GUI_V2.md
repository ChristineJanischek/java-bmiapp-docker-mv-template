# Schritt-für-Schritt-Anleitung: GUI-Erweiterung Version 2

## Ziel
Die grafische Oberfläche (MainWindow) wird um die Eingabemöglichkeiten für **Alter** (ComboBox) und **Geschlecht** (RadioButtons) erweitert. Anschließend wird das Ereignis "Interpretieren" (Button) mit der Methode `interpretieren()` im Model (Bmirechner) nach dem MVC-Prinzip verbunden und getestet.

---

## 1. Vorbereitung
- Stelle sicher, dass du im Branch `version-2-methoden` arbeitest.
- Baue das Projekt und öffne die Datei `src/start/MainWindow.java`.

---

## 2. GUI um Alter und Geschlecht erweitern

### a) ComboBox für Alter hinzufügen
1. Füge im Attribut-Bereich von `MainWindow` eine neue JComboBox<String> namens `cbAlter` hinzu.
2. Initialisiere die ComboBox im Konstruktor mit sinnvollen Alterswerten (z.B. 10 bis 100 in 5er-Schritten).
3. Füge die ComboBox im Layout an eine passende Stelle (z.B. unter die Eingabefelder für Gewicht/Größe).

### b) RadioButtons für Geschlecht hinzufügen
1. Füge zwei JRadioButtons (`rbMann`, `rbFrau`) und eine ButtonGroup (`bgGeschlecht`) als Attribute hinzu.
2. Initialisiere die RadioButtons im Konstruktor (Beschriftung: "Mann", "Frau").
3. Füge die RadioButtons und die ButtonGroup dem Layout hinzu (z.B. unter die ComboBox für Alter).

---

## 3. MVC: Ereignis "Interpretieren" verbinden

### a) View → Controller
1. Im ActionListener des Buttons `btInterpretieren` rufst du eine Methode im Controller (`BmiManager`) auf, z.B. `interpretiereBMI()`.
2. Übergebe die aktuellen Werte für Gewicht, Größe, Alter und Geschlecht an den Controller.

### b) Controller → Model
1. Im Controller (`BmiManager`) rufst du die Methode `interpretieren()` des Models (`Bmirechner`) auf.
2. Stelle sicher, dass das Model die Werte für Alter und Geschlecht kennt (ggf. neue Felder/Methoden ergänzen).

### c) Model → View
1. Nach dem Aufruf von `interpretieren()` gibst du die interpretierte Kategorie im GUI aus (z.B. in einem Label oder Textfeld).

---

## 4. Testen
- Starte die Anwendung (`./run.sh` oder im Docker-Browser).
- Gib verschiedene Werte für Gewicht, Größe, Alter und Geschlecht ein.
- Klicke auf "Interpretieren" und prüfe, ob die richtige Kategorie angezeigt wird.

---

## 5. Tipps & Hinweise
- Nutze das MVC-Prinzip: View (GUI) → Controller (BmiManager) → Model (Bmirechner) → View.
- Kommentiere deinen Code und halte die Methoden kurz und verständlich.
- Teste verschiedene Kombinationen von Alter und Geschlecht.
- Speichere und committe regelmäßig deine Änderungen!

---

## 6. Beispiel-Code (Ausschnitt)

```java
// ... im Attribut-Bereich
private JComboBox<String> cbAlter;
private JRadioButton rbMann, rbFrau;
private ButtonGroup bgGeschlecht;

// ... im Konstruktor
cbAlter = new JComboBox<>();
for (int i = 10; i <= 100; i += 5) cbAlter.addItem(i + " Jahre");
add(cbAlter); // Layout anpassen!

rbMann = new JRadioButton("Mann");
rbFrau = new JRadioButton("Frau");
bgGeschlecht = new ButtonGroup();
bgGeschlecht.add(rbMann);
bgGeschlecht.add(rbFrau);
add(rbMann); add(rbFrau); // Layout anpassen!

// ... im ActionListener von btInterpretieren
String alter = (String) cbAlter.getSelectedItem();
String geschlecht = rbMann.isSelected() ? "Mann" : "Frau";
manager.interpretiereBMI(gewicht, groesse, alter, geschlecht);
```

---

## 7. Abgabe
- Prüfe, ob alle Funktionen wie gewünscht laufen.
- Committe und pushe deine Änderungen:
```bash
git add .
git commit -m "feat: GUI um Alter und Geschlecht erweitert, MVC-Interpretation angebunden"
git push
```

Viel Erfolg beim Umsetzen! Bei Fragen: README und die .md-Dateien im Projekt helfen weiter.