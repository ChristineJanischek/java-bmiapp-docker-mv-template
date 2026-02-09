# 📋 Version 4: Assoziationen - Personen & Messungen

## 🎯 Überblick Version 4

**Version 4** ist eine Musterlösung, die zeigt, wie man **Assoziationen in Java** implementiert:

- ✅ **Person-Klasse**: Speichert Personen mit Grunddaten
- ✅ **Messung-Klasse**: Speichert einzelne BMI-Messungen mit Zeitstempel
- ✅ **1:N Beziehung**: Eine Person hat mehrere Messungen
- ✅ **GUI erweitert**: Personen-Verwaltung, Messungs-Historie, Statistik
- ✅ **Vollständig funktionsfähig**: Alle Features integriert und getestet

---

## 📦 Was ist neu in Version 4?

| Feature | Version 3 | Version 4 | Beschreibung |
|---------|----------|----------|-------------|
| **Person-Verwaltung** | ❌ | ✅ | Mehrere Personen erstellen und verwalten |
| **Messungs-Speicherung** | Nur aktuell | ✅ | Alle Messungen gespeichert |
| **Statistik** | Keine | ✅ | Durchschnitt, beste/schlechteste Messung |
| **Historie** | Keine | ✅ | Alle Messungen mit Datum anzeigen |
| **1:N Assoziation** | Nein | ✅ | Person ↔ Messungen verknüpft |
| **Komplexität** | Basis | Erweitert | Objekt-orientiert mit Assoziationen |

---

## 🚀 Schnellstart Version 4

### 1. Branch auschecken
```bash
git checkout version-4-assoziationen
```

### 2. Projekt bauen
```bash
./build.sh
```

### 3. Anwendung starten
```bash
./run.sh
```

---

## 📚 Architektur Version 4

### Klassen-Struktur

```
┌─────────────────────────────────────┐
│         MAIN-FENSTER (View)         │
│      MainWindow erweitert v4        │
│  - Person-Verwaltung (GUI)          │
│  - Messung-Erfassung                │
│  - Historie & Statistik             │
└────────────┬────────────────────────┘
             ↓
┌─────────────────────────────────────┐
│      CONTROLLER (Business Logic)    │
│      BmiManager erweitert v4        │
│  - Person-Management                │
│  - Messung-Management               │
└────────┬────────────────────────────┘
         ↓
┌─────────────────────────────────────┐
│        MODEL (Datenstrukturen)      │
│  1. Bmirechner (Berechnung)         │
│  2. Person (mit Messungen)          │
│  3. Messung (einzelne Wert)         │
└─────────────────────────────────────┘
```

### 1:N Beziehung visualisiert

```
┌─────────────┐      1:N       ┌──────────────┐
│   Person    │ ─────────────→ │   Messung    │
│             │  hat multiple  │              │
│ - vorname   │                │ - gewicht    │
│ - messungen*│ (ArrayList)    │ - groesse    │
└─────────────┘                │ - bmi        │
                               │ - zeitstempel│
                               └──────────────┘
```

---

## 📖 Detaillierte Schritte

### Schritt 1: Person-Klasse verstehen

Die **Person-Klasse** ist das Zentrum von Version 4:

```java
public class Person {
    // Attribute
    private String vorname;
    private String nachname;
    private int alter;
    private String geschlecht;
    private String email;
    
    // 1:N Beziehung: Eine Person hat MEHRERE Messungen
    private List<Messung> messungen; // ← Die Assoziation!
    
    // Konstruktor
    public Person(String vorname, String nachname, int alter, 
                  String geschlecht, String email) {
        // Messung-Liste initialisieren
        this.messungen = new ArrayList<>();
    }
}
```

**Wichtig**: Die `List<Messung> messungen;` ist die **1:N Assoziation**!

### Schritt 2: Messung-Klasse verstehen

Die **Messung-Klasse** speichert ein einzelnes Messergebnis:

```java
public class Messung {
    private double gewicht;        // in kg
    private double groesse;         // in m
    private LocalDateTime zeitstempel; // WANN gemessen?
    private double bmi;            // Berechnet
    private String kategorie;      // BMI-Kategorie
    
    // Konstruktor
    public Messung(double gewicht, double groesse) {
        this.gewicht = gewicht;
        this.groesse = groesse;
        this.zeitstempel = LocalDateTime.now(); // Aktuelles Datum/Zeit
        this.bmi = berechneBMI(gewicht, groesse);
        this.kategorie = bestimmeKategorie(this.bmi);
    }
}
```

### Schritt 3: So funktioniert die 1:N Beziehung

```java
// 1. Person erstellen
Person max = new Person("Max", "Mustermann", 30, "Mann", "max@example.de");

// 2. Messungen erstellen
Messung messung1 = new Messung(85.0, 1.80);  // 26.23 BMI
Messung messung2 = new Messung(80.0, 1.80);  // 24.69 BMI
Messung messung3 = new Messung(90.0, 1.80);  // 27.78 BMI

// 3. Messungen der Person ZUORDNEN (Assoziation herstellen!)
max.addMessung(messung1);
max.addMessung(messung2);
max.addMessung(messung3);

// 4. Auf die Messungen zugreifen
for (Messung m : max.getMessungen()) {
    System.out.println(m.getBmi() + " - " + m.getKategorie());
}

// 5. Statistiken berechnen
System.out.println("Anzahl: " + max.getAnzahlMessungen());           // 3
System.out.println("Durchschnitt: " + max.getDurchschnittsBmi());    // 26.23
System.out.println("Beste: " + max.getBesteMessung().getBmi());      // 24.69
```

---

## 🖥️ Die GUI in Version 4

### Bereich 1: Personen-Verwaltung

```
┌─────────────────────────────────────────┐
│ 1. PERSONEN-VERWALTUNG                  │
├─────────────────────────────────────────┤
│ Vorname:     [Max           ]           │
│ Nachname:    [Mustermann    ]           │
│ Alter:       [30            ]           │
│ Geschlecht:  [Mann         ▼]           │
│ E-Mail:      [max@example.de]           │
│                                          │
│ [Person erstellen] [Felder leeren]      │
│                                          │
│ Person auswählen: [Max Mustermann ...▼] │
└─────────────────────────────────────────┘
```

**Funktionalität:**
- Neue Personen erstellen
- Bestandene Personen aus Dropdown auswählen
- Alle Personendaten speichern

### Bereich 2: BMI-Berechnung & Messung

```
┌─────────────────────────────────────────┐
│ 2. BMI-BERECHNUNG & MESSUNG             │
├─────────────────────────────────────────┤
│ Gewicht (kg): [85.0        ]            │
│ Größe (m):    [1.80        ]            │
│                                          │
│ ┌─────────────────────────────────────┐ │
│ │ BMI: 26.23 - Übergewicht            │ │
│ └─────────────────────────────────────┘ │
│                                          │
│ [Messung erstellen] [Nur berechnen]     │
│ [Leeren]                                 │
└─────────────────────────────────────────┘
```

**Funktionalität:**
- Gewicht und Größe erfassen
- BMI berechnen und Kategorie anzeigen
- **Messung speichern**: Fügt zu Person hinzu!

### Bereich 3: Messungs-Historie

```
┌──────────────────────────────────────────────┐
│ 3. MESSUNGS-HISTORIE                         │
├──────────────────────────────────────────────┤
│ Messungen für: Max Mustermann                │
│ =============================================│
│                                               │
│ 1. 09.02.2026 14:30                          │
│    Gewicht: 85.0 kg | Größe: 1.80 m         │
│    BMI: 26.23 (Übergewicht)                  │
│                                               │
│ 2. 09.02.2026 15:15                          │
│    Gewicht: 80.0 kg | Größe: 1.80 m         │
│    BMI: 24.69 (Normalgewicht)                │
│                                               │
│ 3. 09.02.2026 16:00                          │
│    Gewicht: 90.0 kg | Größe: 1.80 m         │
│    BMI: 27.78 (Übergewicht)                  │
│                                               │
└──────────────────────────────────────────────┘
```

**Funktionalität:**
- Zeigt alle Messungen mit Datum/Zeit
- Sortiert vom Neuesten zum Ältesten

### Bereich 4: Statistik

```
┌───────────────────────────────────┐
│ 4. STATISTIK                      │
├───────────────────────────────────┤
│ STATISTIK                          │
│ ===================               │
│                                    │
│ Person:                            │
│ Max Mustermann                     │
│ 30 Jahre, Mann                     │
│                                    │
│ Anzahl Messungen:                  │
│ 3                                  │
│                                    │
│ Durchschnitts-BMI:                 │
│ 26.23                              │
│                                    │
│ Letzte Messung:                    │
│ BMI: 27.78                         │
│ Übergewicht                        │
│                                    │
│ Bester BMI:                        │
│ 24.69                              │
│                                    │
│ Schlechtester BMI:                 │
│ 27.78                              │
│                                    │
│ Differenz:                         │
│ 3.09                               │
│                                    │
└───────────────────────────────────┘
```

**Statistiken die berechnet werden:**
- Anzahl der Messungen
- Durchschnitts-BMI
- Letzte Messung
- Beste & schlechteste Messung (wenn >= 2 Messungen)
- BMI-Differenz (Fortschritt/Rückgang)

---

## 💻 Programmierkonzepte in Version 4

### 1. Assoziationen (1:N)

Ein Container (Person) enthält mehrere Elemente (Messungen):

```java
private List<Messung> messungen;  // 1:N Assoziation

// Hinzufügen
public void addMessung(Messung messung) {
    messungen.add(messung);
}

// Alle abrufen
public List<Messung> getMessungen() {
    return new ArrayList<>(messungen);  // Kopie!
}
```

### 2. Kapselung

Die `getMessungen()`-Methode gibt eine **Kopie** zurück:

```java
// RICHTIG - Kopie zurückgeben
public List<Messung> getMessungen() {
    return new ArrayList<>(messungen);  // ← Neue Liste!
}

// FALSCH - Original zurückgeben (Sicherheitsrisiko)
public List<Messung> getMessungen() {
    return messungen;  // ← Jeder kann die Original-Liste ändern!
}
```

### 3. LocalDateTime für Zeitstempel

```java
// Aktuelle Zeit erfassen
private LocalDateTime zeitstempel = LocalDateTime.now();

// Formatiert ausgeben
public String getFormatiertesDatum() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    return zeitstempel.format(formatter);
}
```

### 4. Controller-Pattern (BmiManager)

```java
public class BmiManager {
    private BmiRechner model;              // Alte Logik
    private Person aktuellePerson;         // Neu: aktuelle Person
    private List<Person> personenListe;    // Neu: alle Personen
    
    public Messung erstelleMessung(double gewicht, double groesse) {
        if (aktuellePerson == null) {
            throw new IllegalStateException("Keine Person ausgewählt!");
        }
        Messung messung = new Messung(gewicht, groesse);
        aktuellePerson.addMessung(messung);  // ← Assoziation nutzen!
        return messung;
    }
}
```

---

## ✅ Aufgaben & Übungen

### Aufgabe 4.1: Messung hinzufügen

**Ziel:** Eine Person erstellen und 3 Messungen hinzufügen.

```java
public void aufgabe41() {
    // 1. Person erstellen
    Person anna = new Person("Anna", "Schmidt", 28, "Frau", "anna@example.de");
    
    // 2. Drei Messungen erstellen
    Messung m1 = new Messung(70, 1.65);
    Messung m2 = new Messung(68, 1.65);
    Messung m3 = new Messung(72, 1.65);
    
    // 3. Messungen hinzufügen
    anna.addMessung(m1);
    anna.addMessung(m2);
    anna.addMessung(m3);
    
    // 4. Ausgeben
    System.out.println("Durchschnittli-BMI: " + anna.getDurchschnittsBmi());
    System.out.println("Differenz: " + anna.getBmiDifferenz());
}
```

**Erwartete Ausgabe:**
```
Durchschnitts-BMI: 25.05
Differenz: 1.76
```

### Aufgabe 4.2: Statistik berechnen

**Ziel:** Für mehrere Personen die beste Messung finden.

```java
public void aufgabe42() {
    List<Person> personen = new ArrayList<>();
    
    // Personen erstellen mit Messungen
    Person max = new Person("Max", "Mustermann", 30, "Mann", "max@example.de");
    max.addMessung(new Messung(85, 1.80));
    max.addMessung(new Messung(80, 1.80));
    
    Person anna = new Person("Anna", "Schmidt", 28, "Frau", "anna@example.de");
    anna.addMessung(new Messung(70, 1.65));
    anna.addMessung(new Messung(68, 1.65));
    
    personen.add(max);
    personen.add(anna);
    
    // Beste Messung pro Person ausgeben
    for (Person p : personen) {
        Messung beste = p.getBesteMessung();
        System.out.printf("%s: bester BMI = %.2f\n", p.getFullName(), beste.getBmi());
    }
}
```

### Aufgabe 4.3: GUI testen

**Ziel:** Die GUI-Version 4 nutzen und verschiedene Szenarien testen.

1. Person erstellen (Anna, 28 Jahre, Frau)
2. 3 verschiedene Messungen erfassen
3. Statistik-Bereich überprüfen
4. Messung mit neuem Gewicht = beste BMI?

---

## 🧪 Tests für Version 4

Mit JUnit 5 können Sie die Funktionen testen:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Version4Tests {
    
    @Test
    public void testPersonErstellung() {
        Person p = new Person("Max", "Mustermann", 30, "Mann", "max@example.de");
        assertEquals("Max", p.getVorname());
        assertEquals(0, p.getAnzahlMessungen());
    }
    
    @Test
    public void testMessungHinzufügen() {
        Person p = new Person("Max", "Mustermann", 30, "Mann", "max@example.de");
        Messung m = new Messung(80, 1.80);
        p.addMessung(m);
        
        assertEquals(1, p.getAnzahlMessungen());
        assertEquals(m, p.getMessungen().get(0));
    }
    
    @Test
    public void testDurchschnittsBMI() {
        Person p = new Person("Max", "Mustermann", 30, "Mann", "max@example.de");
        p.addMessung(new Messung(80, 1.80));  // 24.69
        p.addMessung(new Messung(90, 1.80));  // 27.78
        
        double average = p.getDurchschnittsBmi();
        assertTrue(average > 26 && average < 27);
    }
    
    @Test
    public void testBesteMessung() {
        Person p = new Person("Max", "Mustermann", 30, "Mann", "max@example.de");
        p.addMessung(new Messung(85, 1.80));  // 26.23
        p.addMessung(new Messung(80, 1.80));  // 24.69 ← Beste
        
        Messung beste = p.getBesteMessung();
        assertEquals(24.69, beste.getBmi(), 0.01);
    }
}
```

---

## 📋 Checkliste für Version 4

### Umgesetzte Features

- ✅ Person-Klasse mit Messung-Liste
- ✅ Messung-Klasse mit BMI-Berechnung
- ✅ 1:N Assoziation funktioniert
- ✅ BmiManager erweitert
- ✅ GUI: Person-Verwaltung
- ✅ GUI: Messung-Erfassung
- ✅ GUI: Messungs-Historie
- ✅ GUI: Statistik-Bereich
- ✅ LocalDateTime für Zeitstempel
- ✅ Kapselung beachtet
- ✅ Validierung implementiert
- ✅ Alle Klassen dokumentiert

### Testen

- ✅ Projekt kompiliert fehlerfrei
- ✅ GUI startet ohne Fehler
- ✅ Person erstellen funktioniert
- ✅ Messungen speichern funktioniert
- ✅ Statistik wird korrekt berechnet

---

## 🚀 Nächste Schritte

Nach Version 4 könnten Sie:

1. **Version 5 - Datenbank**: Messungen in einer Datenbank speichern (SQL)
2. **Version 6 - Ärzte**: M:N Beziehung (Ärzte ↔ Patienten)
3. **Version 7 - Export**: Messdaten als PDF/CSV exportieren
4. **Version 8 - Graphik**: Trend-Visualisierung der Messungen

---

## 📚 Ressourcen & Links

- [Person.java](../../src/start/Person.java) - Quellcode
- [Messung.java](../../src/start/Messung.java) - Quellcode
- [BmiManager.java](../../src/start/BmiManager.java) - Quellcode
- [MainWindow.java](../../src/start/MainWindow.java) - GUI
- [ASSOZIATIONEN_PERSON_MESSUNG.md](./ASSOZIATIONEN_PERSON_MESSUNG.md) - Theorie
- [ASSOZIATIONEN_LOESUNGEN.md](./ASSOZIATIONEN_LOESUNGEN.md) - Musterlösungen

---

**Version 4 zeigt professionelle Java-Programmierung mit Assoziationen, Kapselung und klarem MVC-Pattern!** 🎉
