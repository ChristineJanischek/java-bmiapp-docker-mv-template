# GUI Test - MainWindow

## Status: ✅ Kompilierung erfolgreich

Die `MainWindow.java` wurde erfolgreich kompiliert und ist bereit zur Ausführung.

## GUI-Komponenten

Die Benutzeroberfläche enthält folgende Elemente (GridBagLayout):

### Zeile 0: Logo Banner
- **Component**: JLabel mit ImageIcon
- **Bild**: `logo_final.png` (Banner oben)
- **Layout**: Zentriert, über 2 Spalten

### Zeile 1: Titel mit Icon
- **Component**: JLabel "BMI-Rechner"
- **Icon**: `ic_launcher.png`
- **Schriftart**: Arial, Bold, 20pt

### Zeile 2: Gewicht-Eingabe
- **Label**: "Gewicht (kg):"
- **TextField**: `tfGewicht` (10 Spalten)

### Zeile 3: Größe-Eingabe
- **Label**: "Größe (m):"
- **TextField**: `tfGroesse` (10 Spalten)

### Zeile 4: Ergebnis-Anzeige
- **TextField**: `tfErgebnis` (nicht editierbar)
- **Hintergrund**: Hellgrau
- **Schriftart**: Tahoma, 14pt
- **Breite**: Über 2 Spalten

### Zeile 5: Aktions-Buttons
- **Links**: Button "Berechne BMI" → Ruft `manager.berechneBMI()` und zeigt BMI-Wert
- **Rechts**: Button "Interpretiere BMI" → Ruft `manager.interpretiereBMI()` und zeigt Kategorie

### Zeile 6: Steuerungs-Buttons
- **Links**: Button "Leeren" → Löscht alle Eingaben
- **Rechts**: Button "Schließen" → Beendet die Anwendung

## MVC-Integration

```
View (MainWindow)
    ↓ nutzt
Controller (BmiManager)
    ↓ steuert
Model (Bmirechner)
```

### Getter-Methoden in MainWindow:
- `getManager()` → Zugriff auf den BmiManager
- `getGewichtValue()` → Liest Gewicht aus TextField
- `getGroesseValue()` → Liest Größe aus TextField

### Setter-Methoden:
- `setErgebnisText(String)` → Setzt Ergebnistext
- `clearFields()` → Leert alle Felder

### Private Helper:
- `schreibeErgebnis()` → Zeigt BMI-Wert formatiert an
- `schreibeKategorie()` → Zeigt Gewichtskategorie an

## Test auf lokalem System

Um die GUI auf einem System mit grafischer Oberfläche zu testen:

```bash
# Kompilieren
javac -d . src/start/*.java

# Ausführen
java start.MainWindow
```

## Erwartetes Verhalten

1. **Fenster öffnet sich** mit Titel "BMI-Rechner" (400x600 Pixel)
2. **Logo** wird oben angezeigt
3. **Benutzer gibt ein**: z.B. Gewicht: 75, Größe: 1.80
4. **Klick auf "Berechne BMI"**: Zeigt "BMI: 23.15"
5. **Klick auf "Interpretiere BMI"**: Zeigt "Normalgewicht"
6. **Klick auf "Leeren"**: Alle Felder werden geleert
7. **Klick auf "Schließen"**: Anwendung wird beendet

## Layout-System

✅ **GridBagLayout** (flexibles Layout)
❌ ~~setBounds()~~ (kein absolutes Layout mehr)

Das Layout passt sich automatisch an verschiedene Fenstergrößen an.
