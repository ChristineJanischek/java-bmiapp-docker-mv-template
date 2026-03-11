# Dateien lesen und schreiben in Java

Java bietet mehrere Möglichkeiten, Daten in Dateien zu speichern und aus Dateien zu lesen.  
Diese Datei erklärt die wichtigsten Methoden, die in Schul- und Abschlussprojekten verwendet werden.

---

## Überblick: Welche Klasse wofür?

| Klasse | Package | Zweck |
|--------|---------|-------|
| `FileWriter` / `FileReader` | `java.io` | Einfaches Lesen/Schreiben zeichenweise |
| `BufferedWriter` / `BufferedReader` | `java.io` | Zeilenweises Lesen/Schreiben (empfohlen) |
| `PrintWriter` | `java.io` | Schreiben mit `println()` (bequem) |
| `Files` | `java.nio.file` | Moderne API, komfortabel (Java 7+) |
| `Path` / `Paths` | `java.nio.file` | Dateipfade plattformunabhängig angeben |

---

## 1. Text in eine Datei schreiben

### Methode 1: BufferedWriter (Standard)

```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public void inDateiSchreiben(String dateiPfad, String inhalt) {
    // try-with-resources: Datei wird automatisch geschlossen!
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(dateiPfad))) {

        writer.write(inhalt);
        writer.newLine();          // Zeilenumbruch einfügen
        System.out.println("Datei geschrieben: " + dateiPfad);

    } catch (IOException e) {
        System.err.println("Fehler beim Schreiben: " + e.getMessage());
    }
}
```

### Methode 2: PrintWriter (bequemer für mehrere Zeilen)

```java
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public void messungenSpeichern(String dateiPfad, List<String> zeilen) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(dateiPfad))) {

        for (String zeile : zeilen) {
            writer.println(zeile);   // Schreibt Zeile + Zeilenumbruch
        }

    } catch (IOException e) {
        System.err.println("Fehler beim Schreiben: " + e.getMessage());
    }
}
```

### Methode 3: Datei ergänzen statt überschreiben (append)

```java
// Zweitem Parameter: true = anhängen, false = überschreiben
try (PrintWriter writer = new PrintWriter(new FileWriter(dateiPfad, true))) {
    writer.println("Neue Zeile wird angehängt");
}
```

---

## 2. Text aus einer Datei lesen

### Methode 1: BufferedReader (Standard)

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public List<String> ausDateiLesen(String dateiPfad) {
    List<String> zeilen = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(dateiPfad))) {

        String zeile;
        while ((zeile = reader.readLine()) != null) {   // null = Dateiende
            zeilen.add(zeile);
        }

    } catch (IOException e) {
        System.err.println("Fehler beim Lesen: " + e.getMessage());
    }

    return zeilen;
}
```

### Methode 2: java.nio.file.Files (modern, kompakt)

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

// Alle Zeilen auf einmal einlesen
public List<String> alleLesen(String dateiPfad) {
    try {
        Path pfad = Paths.get(dateiPfad);
        return Files.readAllLines(pfad);          // gibt List<String> zurück

    } catch (IOException e) {
        System.err.println("Fehler: " + e.getMessage());
        return new ArrayList<>();
    }
}

// Gesamten Inhalt als einzelnen String
public String inhaltAlsText(String dateiPfad) {
    try {
        return Files.readString(Paths.get(dateiPfad));   // Java 11+
    } catch (IOException e) {
        return "";
    }
}
```

---

## 3. Praxisbeispiel: BMI-Messungen speichern und laden

### Messungen in CSV-Datei schreiben

CSV (Comma-Separated Values) ist ein einfaches Format, das von Excel und anderen Tools gelesen werden kann.

```java
public class MessungDateiSpeicher {

    private static final String DATEI = "messungen.csv";

    /**
     * Schreibt eine Liste von Messungen im CSV-Format in eine Datei.
     * Format: vorname;nachname;gewicht;groesse;bmi;datum
     */
    public void messungenSpeichern(List<Messung> messungen, String vorname, String nachname) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATEI, true))) {

            for (Messung m : messungen) {
                // Semikolon als Trennzeichen (kein Komma wegen Dezimalzahlen!)
                String zeile = vorname + ";" + nachname + ";"
                             + m.getGewicht() + ";"
                             + m.getGroesse() + ";"
                             + String.format("%.2f", m.getBmi()) + ";"
                             + m.getFormatiertesDatum();
                writer.println(zeile);
            }

        } catch (IOException e) {
            System.err.println("Speichern fehlgeschlagen: " + e.getMessage());
        }
    }

    /**
     * Liest Messungen aus der CSV-Datei und gibt sie als Liste zurück.
     * Überspringt fehlerhafte Zeilen.
     */
    public List<String[]> messungenLaden() {
        List<String[]> ergebnisse = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATEI))) {

            String zeile;
            while ((zeile = reader.readLine()) != null) {
                if (zeile.isBlank()) continue;   // leere Zeilen überspringen

                String[] felder = zeile.split(";");
                if (felder.length == 6) {
                    ergebnisse.add(felder);
                }
            }

        } catch (IOException e) {
            System.err.println("Laden fehlgeschlagen: " + e.getMessage());
        }

        return ergebnisse;
    }
}
```

### Verwendung:

```java
MessungDateiSpeicher speicher = new MessungDateiSpeicher();

// Messungen einer Person speichern
speicher.messungenSpeichern(person.getMessungen(), "Max", "Mustermann");

// Messungen wieder laden und ausgeben
List<String[]> daten = speicher.messungenLaden();
for (String[] felder : daten) {
    System.out.printf("%-10s %-10s  Gewicht: %s  BMI: %s  Datum: %s%n",
        felder[0], felder[1], felder[2], felder[4], felder[5]);
}
```

---

## 4. Datei-Existenz prüfen und Verzeichnisse anlegen

```java
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Datei-Existenz prüfen
File datei = new File("messungen.csv");
if (datei.exists()) {
    System.out.println("Datei vorhanden, Größe: " + datei.length() + " Bytes");
} else {
    System.out.println("Datei existiert noch nicht");
}

// Verzeichnis anlegen (falls nicht vorhanden)
Path verzeichnis = Paths.get("daten/messungen");
if (!Files.exists(verzeichnis)) {
    Files.createDirectories(verzeichnis);   // Erstellt auch Zwischenverzeichnisse
}

// Datei löschen
Files.deleteIfExists(Paths.get("alte_messungen.csv"));
```

---

## 5. Plattformunabhängige Pfade

Pfadtrennzeichen sind unter Windows `\`, unter Linux und macOS `/`.  
`Path` und `Paths.get()` handeln das automatisch:

```java
// FALSCH (nur Windows):
String pfad = "C:\\Users\\Max\\daten\\messungen.csv";

// RICHTIG (plattformunabhängig):
Path pfad = Paths.get(System.getProperty("user.home"), "daten", "messungen.csv");
// → Windows: C:\Users\Max\daten\messungen.csv
// → Linux:   /home/max/daten/messungen.csv

// Aus relativen Teilen zusammensetzen
Path pfad2 = Paths.get("daten", "export", "ergebnis.txt");
```

---

## 6. Exceptions beim Dateizugriff

`IOException` ist eine **Checked Exception** – sie muss behandelt werden:

```java
// Möglichkeit 1: try-catch (Exception behandeln)
try (BufferedReader r = new BufferedReader(new FileReader("daten.txt"))) {
    String zeile = r.readLine();
} catch (IOException e) {
    System.err.println("Datei nicht lesbar: " + e.getMessage());
}

// Möglichkeit 2: throws (Exception weitergeben)
public List<String> lesen(String pfad) throws IOException {
    // Aufrufer muss die Exception behandeln
    return Files.readAllLines(Paths.get(pfad));
}
```

**Wichtige IOException-Ursachen:**

| Situation | Exception / Ursache |
|-----------|---------------------|
| Datei existiert nicht | `FileNotFoundException` (Unterklasse von IOException) |
| Keine Leseberechtigung | `IOException: Permission denied` |
| Festplatte voll | `IOException: No space left on device` |
| Pfad ungültig | `IOException: Invalid argument` |

---

## 7. Dateien und MVC

Im MVC-Muster gehört das Lesen/Schreiben von Dateien in das **Model** oder in eine eigene **Persistenz-Klasse**:

```
MainWindow (View)
    │
    ▼
BmiManager (Controller)
    │
    ├──► Bmirechner (Model: Berechnung)
    │
    └──► MessungDateiSpeicher (Persistenz: Speichern/Laden)
```

```java
// Im BmiManager (Controller):
public class BmiManager {
    private Bmirechner         model;
    private MessungDateiSpeicher speicher;

    public BmiManager() {
        this.model    = new Bmirechner();
        this.speicher = new MessungDateiSpeicher();
    }

    public void messungenExportieren(Person person) {
        speicher.messungenSpeichern(person.getMessungen(),
            person.getVorname(), person.getNachname());
    }
}
```

---

## Zusammenfassung

| Aufgabe | Empfohlene Klassen |
|---------|-------------------|
| Text schreiben | `PrintWriter` + `FileWriter` |
| Text lesen (zeilenweise) | `BufferedReader` + `FileReader` |
| Alles auf einmal lesen | `Files.readAllLines()` |
| Datei ergänzen | `FileWriter(pfad, true)` |
| Pfade plattformunabhängig | `Paths.get(...)` |
| Verzeichnis erstellen | `Files.createDirectories()` |
| Datei prüfen | `Files.exists()` / `datei.exists()` |

**Merksätze:**
- `try-with-resources` verwenden → Datei wird automatisch geschlossen
- `IOException` ist Checked Exception → muss behandelt werden
- CSV mit `;` statt `,` trennen → kein Konflikt mit Dezimalzahlen
- Pfade mit `Paths.get()` zusammenbauen → läuft auf Windows und Linux
