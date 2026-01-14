# GUI in Eclipse mit WindowBuilder (Swing)

Dieses Tutorial zeigt dir Schritt für Schritt, wie du mit dem Eclipse-Plugin WindowBuilder eine Swing-Oberfläche für den BMI‑Rechner erstellst – zunächst mit absolutem Layout (null‑Layout), anschließend mit einem flexiblen Layout (GridLayout). Am Ende bindest du die Logik über den `BmiManager` an.

Voraussetzungen
- Eclipse IDE (2023‑xx oder neuer)
- WindowBuilder installiert (Eclipse Marketplace → „WindowBuilder“)
- Dieses Repository als Maven‑Projekt importiert

Hinweis zur Projektstruktur
- Einstiegspunkt (Main‑Klasse): `start.MainWindow`
- Bilder/Icons: `src/start/images` und zur Laufzeit unter `/start/images/...`
- Model/Controller: `start.Bmirechner`, `start.BmiManager`

## 1) Neues JFrame mit WindowBuilder anlegen/öffnen
- Rechtsklick auf `src/start` → New → Other → WindowBuilder → Swing Designer → JFrame.
- Name: `MainWindow` (Package: `start`).
- Oder bestehende Datei öffnen: `src/start/MainWindow.java` und im Tab „Design“ arbeiten.

## 2) Phase A – Absolutes Layout (null‑Layout)
Ziel: Komponenten frei platzieren und Bounds (x, y, Breite, Höhe) setzen.

1. Wähle im Designer das `contentPane` aus und setze im Properties‑Fenster `layout` auf `null`.
2. Füge ein `JPanel` (oben) ein für Bilder/Banner. Setze `setBounds(10, 11, 386, 156)`.
   - Setze für dieses Panel ein `GridLayout(2, 1, 5, 5)`.
   - Füge zwei `JLabel` hinzu:
     - Label 1: Icon → `/start/images/logo_final.png`.
     - Label 2: Text „BMI‑Rechner“, Icon `/start/images/ic_launcher.png`, Font „Bold 18“.
3. Füge ein `JPanel` für Eingaben ein. Setze `setBounds(10, 189, 386, 128)`.
   - Layout: `GridLayout(0, 2, 2, 2)` (zwei Spalten, beliebig viele Zeilen).
   - Komponenten hinzufügen (linke Spalte Label, rechte Spalte Eingabe):
     - `JLabel` „Gewicht (kg):“ + `JTextField` (z. B. `tfGewicht`).
     - `JLabel` "Größe (m):" + `JTextField` (z. B. `tfGroesse`).
     - `JLabel` „Alter (Jahre):“ + `JComboBox` mit Werten: `-- nicht angegeben --`, `18-25`, `25-34`, `35-44`, `45-54`, `55-64`, `65-74`, `75+`.
     - `JLabel` „Geschlecht:“ + Panel mit zwei `JRadioButton` („Männlich“, „Weiblich“) in einer `ButtonGroup`.
4. Ergebnisbereich: `JScrollPane` unten hinzufügen (`setBounds(10, 328, 386, 51)`) und darin ein Panel mit `GridLayout(1,1)`, das eine `JTextArea` enthält (z. B. `taErgebnis`).
5. Aktionsbereich: weiteres `JPanel` unten (`setBounds(10, 449, 386, 62)`) mit `GridLayout(2,2,5,5)` und vier Buttons:
   - `Berechne BMI`, `Interpretiere BMI`, `Leeren`, `Schließen`.

Damit hast du eine funktionierende Oberfläche im absoluten Layout, bei der die inneren Bereiche bereits einfache Grids verwenden.

## 3) Phase B – Flexibles Layout mit GridLayout
Ziel: Statt absoluter Positionierung das Hauptfenster flexibler machen. Dazu ersetzen wir das `null`‑Layout des `contentPane` durch ein `GridLayout` und strukturieren die Bereiche in Zeilen.

Empfohlene Struktur (je 1 Zeile pro Bereich):
- Zeile 1: Bilder/Banner‑Panel
- Zeile 2: Eingabe‑Panel (Grid mit 2 Spalten)
- Zeile 3: Ergebnis‑Panel (ScrollPane + TextArea)
- Zeile 4: Aktions‑Panel (Buttons in Grid 2x2)

So gehst du vor:
1. Wähle das `contentPane` → `layout` → `GridLayout(4, 1, 5, 5)`.
2. Entferne die manuell gesetzten `setBounds(...)` der Kinder und lasse sie durch das Grid anordnen.
3. Prüfe die Panels:
   - Bilder‑Panel bleibt `GridLayout(2,1,5,5)`.
   - Eingabe‑Panel bleibt `GridLayout(0,2,2,2)`.
   - Ergebnis‑Panel bleibt `GridLayout(1,1)` innerhalb einer `JScrollPane`.
   - Aktions‑Panel bleibt `GridLayout(2,2,5,5)`.
4. Passe die Mindestgröße des Fensters an (z. B. `setMinimumSize(new Dimension(420, 560))`) und nutze `setLocationRelativeTo(null)` zum Zentrieren.

Hinweis: Für besonders feine Steuerung von Ausrichtung und Größen eignet sich alternativ `GridBagLayout`. In diesem Projekt verwenden wir bewusst einfaches `GridLayout` mit verschachtelten Panels.

## 4) Logik anbinden (BmiManager)
Falls noch nicht vorhanden, ergänze Felder für Eingaben/Ausgaben und Events (in `MainWindow`):

- Felder: `tfGewicht`, `tfGroesse`, `cbAlter`, `rbMaennlich`, `rbWeiblich`, `taErgebnis`, `ButtonGroup bgGeschlecht`.
- Controller: `private BmiManager manager = new BmiManager();`
- Action‑Listener:
  - Button „Berechne BMI“: Eingaben parsen, `manager.berechneBMI(...)` aufrufen, BMI in `taErgebnis` anzeigen.
  - Button „Interpretiere BMI“: Altersgruppe auf Mittelwert abbilden (z. B. 18‑25 → 22), Geschlecht ermitteln, `manager.interpretiereIntelligent(...)` aufrufen, Kategorie anzeigen. Optional zusätzlich `manager.zeigeInterpretation()` (Dialog).
  - Button „Leeren“: Textfelder und Auswahl zurücksetzen, Ergebnis leeren.
  - Button „Schließen“: `System.exit(0)`.

Beispiel (verkürzt, wie in der bereitgestellten Lösung in `start.MainWindow` umgesetzt):
```java
btBerechneBmi.addActionListener(e -> {
    double gewicht = Double.parseDouble(tfGewicht.getText().trim());
    double groesse = Double.parseDouble(tfGroesse.getText().trim());
    double bmi = manager.berechneBMI(gewicht, groesse);
    taErgebnis.setText(String.format("BMI: %.2f", bmi));
});

btInterpretiereBmi.addActionListener(e -> {
    double gewicht = Double.parseDouble(tfGewicht.getText().trim());
    double groesse = Double.parseDouble(tfGroesse.getText().trim());
    int alter = mapAlterToMittelwert((String) cbAlter.getSelectedItem());
    String geschlecht = rbMaennlich.isSelected() ? "männlich" : (rbWeiblich.isSelected() ? "weiblich" : null);
    manager.interpretiereIntelligent(gewicht, groesse, alter, geschlecht);
    manager.zeigeInterpretation();
    taErgebnis.setText(manager.getModel().getKategorie());
});
```

## 5) Ressourcen/Icons korrekt laden
- Verwende für Icons immer den Klassenpfad: `MainWindow.class.getResource("/start/images/ic_launcher.png")`.
- Die POM kopiert die Bilder zur Laufzeit nach `build/start/images`. Pfade ohne `/start/...` führen sonst zu „resource not found“.

## 6) Testen & Starten
- Lokaler Start (Konsole):
```bash
mvn -q -DskipTests package
java -cp build start.MainWindow
```
- In Eclipse: `MainWindow` als Java Application starten.

## 7) Häufige Stolpersteine
- „mainClass“ in `pom.xml` ist `start.MainWindow`. Stelle sicher, dass das Package deiner Klasse `start` ist.
- Bei `null`‑Layout müssen Bounds gesetzt werden. Wechselst du zu `GridLayout`, entferne die Bounds.
- NumberFormatException: Nur gültige Zahlen für Gewicht/Größe erlauben (z. B. `Double.parseDouble`).

Viel Erfolg – und probiere beide Varianten (absolut und Grid) aus, um die Unterschiede zu verstehen!
