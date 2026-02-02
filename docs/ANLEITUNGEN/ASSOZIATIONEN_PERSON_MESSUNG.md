# BMI-Rechner Version 3+: Assoziationen & die Person-Klasse

## Überblick

In **Version 2** hatte der BMI-Rechner ein einfaches MVC-Modell:
- Eine **BMI-Berechnung** pro Eingabe
- Keine Speicherung von Messungen
- Keine Benutzerprofile

In dieser Erweiterung erweitern wir den BMI-Rechner um:
- Eine neue `Person`-Klasse
- Eine neue `Messung`-Klasse
- **Assoziationen** zwischen den Klassen
- Die Fähigkeit, mehrere Messungen pro Person zu speichern

Dies ist eine perfekte Gelegenheit, **Assoziationen** in der objektorientierten Programmierung zu verstehen.

---

## Was sind Assoziationen?

Eine **Assoziation** ist eine Beziehung zwischen zwei Klassen. Es gibt drei Typen:

| Typ | Notation | Beschreibung | Beispiel |
|-----|----------|-------------|---------|
| **1:1** | Eine-zu-Eine | Ein Objekt der Klasse A steht mit genau einem Objekt der Klasse B in Beziehung | Eine Person hat einen Pass |
| **1:N** | Eine-zu-Viele | Ein Objekt der Klasse A steht mit mehreren Objekten der Klasse B in Beziehung | Eine Person hat mehrere Messungen |
| **M:N** | Viele-zu-Viele | Mehrere Objekte der Klasse A stehen mit mehreren Objekten der Klasse B in Beziehung | Ärzte behandeln viele Patienten, Patienten besuchen viele Ärzte |

---

## Schritt 1: Die Person-Klasse erstellen (1:1 Beziehung - Demonstration)

### Ziel
Wir erstellen eine `Person`-Klasse, die persönliche Daten speichert.

### 1.1: Grundstruktur der Person-Klasse

Erstelle eine neue Datei: `src/start/Person.java`

```java
package start;

/**
 * Die Person-Klasse repräsentiert eine Person im BMI-Rechner.
 * Sie speichert persönliche Informationen.
 */
public class Person {
    
    // Attribute
    private String vorname;
    private String nachname;
    private int alter;
    private String geschlecht; // "Mann" oder "Frau"
    
    /**
     * Konstruktor: Erstellt eine neue Person mit Grunddaten.
     * 
     * @param vorname Der Vorname der Person
     * @param nachname Der Nachname der Person
     * @param alter Das Alter der Person
     * @param geschlecht Das Geschlecht der Person ("Mann" oder "Frau")
     */
    public Person(String vorname, String nachname, int alter, String geschlecht) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
        this.geschlecht = geschlecht;
    }
    
    // Getter-Methoden
    public String getVorname() {
        return vorname;
    }
    
    public String getNachname() {
        return nachname;
    }
    
    public int getAlter() {
        return alter;
    }
    
    public String getGeschlecht() {
        return geschlecht;
    }
    
    // Setter-Methoden (falls nötig)
    public void setAlter(int alter) {
        this.alter = alter;
    }
    
    /**
     * Gibt den vollständigen Namen der Person zurück.
     * 
     * @return Name im Format "Vorname Nachname"
     */
    public String getFullName() {
        return vorname + " " + nachname;
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", alter=" + alter +
                ", geschlecht='" + geschlecht + '\'' +
                '}';
    }
}
```

### 1.2: Beispiel einer 1:1 Beziehung (Optional, zur Veranschaulichung)

Eine **1:1 Beziehung** könnte eine `Krankenakte` sein, die eine Person hat:

```java
package start;

/**
 * Die Krankenakte ist mit einer Person verbunden (1:1 Beziehung).
 * Eine Person hat GENAU EINE Krankenakte.
 */
public class Krankenakte {
    
    private String aktenNummer;
    private Person person; // 1:1 Assoziation: Eine Krankenakte gehört zu einer Person
    private String arzt;
    
    public Krankenakte(String aktenNummer, Person person, String arzt) {
        this.aktenNummer = aktenNummer;
        this.person = person; // Die Assoziation wird hier hergestellt
        this.arzt = arzt;
    }
    
    public Person getPerson() {
        return person;
    }
    
    public String getAktenNummer() {
        return aktenNummer;
    }
    
    @Override
    public String toString() {
        return "Krankenakte{" +
                "aktenNummer='" + aktenNummer + '\'' +
                ", person=" + person.getFullName() +
                ", arzt='" + arzt + '\'' +
                '}';
    }
}
```

**UML-Diagramm der 1:1 Beziehung:**
```
┌─────────────┐         1:1         ┌──────────────┐
│   Person    │ ◄──────────────────► │  Krankenakte │
│             │                      │              │
│ - vorname   │                      │ - aktenNr    │
│ - nachname  │                      │ - person *   │
└─────────────┘                      └──────────────┘
```

---

## Schritt 2: Die Messung-Klasse erstellen

### Ziel
Wir erstellen eine `Messung`-Klasse, die ein BMI-Messergebnis mit Datum speichert.

### 2.1: Grundstruktur der Messung-Klasse

Erstelle eine neue Datei: `src/start/Messung.java`

```java
package start;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Die Messung-Klasse repräsentiert eine einzelne BMI-Messung.
 * Sie speichert Gewicht, Größe, BMI und den Messzeitpunkt.
 */
public class Messung {
    
    // Attribute
    private double gewicht;           // in kg
    private double groesse;           // in m
    private LocalDateTime zeitstempel; // Zeitpunkt der Messung
    private double bmi;               // Berechneter BMI
    private String kategorie;         // BMI-Kategorie (z.B. "Übergewicht")
    
    /**
     * Konstruktor: Erstellt eine neue Messung.
     * Der BMI wird automatisch berechnet.
     * 
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Größe in m
     */
    public Messung(double gewicht, double groesse) {
        this.gewicht = gewicht;
        this.groesse = groesse;
        this.zeitstempel = LocalDateTime.now();
        this.bmi = berechneBMI(gewicht, groesse);
        this.kategorie = bestimmeKategorie(this.bmi);
    }
    
    /**
     * Berechnet den BMI aus Gewicht und Größe.
     * Formel: BMI = Gewicht / (Größe²)
     * 
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Größe in m
     * @return Der berechnete BMI
     */
    private double berechneBMI(double gewicht, double groesse) {
        return gewicht / (groesse * groesse);
    }
    
    /**
     * Bestimmt die BMI-Kategorie basierend auf dem BMI-Wert.
     * 
     * @param bmi Der BMI-Wert
     * @return Die BMI-Kategorie als String
     */
    private String bestimmeKategorie(double bmi) {
        if (bmi < 18.5) return "Untergewicht";
        else if (bmi < 25) return "Normalgewicht";
        else if (bmi < 30) return "Übergewicht";
        else return "Adipositas";
    }
    
    // Getter-Methoden
    public double getGewicht() {
        return gewicht;
    }
    
    public double getGroesse() {
        return groesse;
    }
    
    public LocalDateTime getZeitstempel() {
        return zeitstempel;
    }
    
    public double getBmi() {
        return bmi;
    }
    
    public String getKategorie() {
        return kategorie;
    }
    
    /**
     * Gibt das Messdatum im Format "dd.MM.yyyy HH:mm" zurück.
     */
    public String getFormatiertesDatum() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return zeitstempel.format(formatter);
    }
    
    @Override
    public String toString() {
        return "Messung{" +
                "gewicht=" + gewicht + " kg" +
                ", groesse=" + groesse + " m" +
                ", bmi=" + String.format("%.2f", bmi) +
                ", kategorie='" + kategorie + '\'' +
                ", zeitstempel=" + getFormatiertesDatum() +
                '}';
    }
}
```

---

## Schritt 3: Die 1:N Beziehung implementieren

### Ziel
Wir erweitern die `Person`-Klasse so, dass eine Person **mehrere Messungen** haben kann.

Das ist eine **1:N Beziehung**:
- **1** Person
- **N** (viele) Messungen

### 3.1: Person-Klasse mit 1:N Beziehung erweitern

Bearbeite `src/start/Person.java`:

```java
package start;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Person-Klasse repräsentiert eine Person im BMI-Rechner.
 * Sie speichert persönliche Informationen und ihre Messungen.
 */
public class Person {
    
    // Attribute
    private String vorname;
    private String nachname;
    private int alter;
    private String geschlecht; // "Mann" oder "Frau"
    
    // 1:N Beziehung: Eine Person hat mehrere Messungen
    private List<Messung> messungen; // ArrayList speichert alle Messungen
    
    /**
     * Konstruktor: Erstellt eine neue Person mit Grunddaten.
     * 
     * @param vorname Der Vorname der Person
     * @param nachname Der Nachname der Person
     * @param alter Das Alter der Person
     * @param geschlecht Das Geschlecht der Person ("Mann" oder "Frau")
     */
    public Person(String vorname, String nachname, int alter, String geschlecht) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
        this.geschlecht = geschlecht;
        
        // 1:N Assoziation initialisieren
        this.messungen = new ArrayList<>();
    }
    
    // Getter-Methoden
    public String getVorname() {
        return vorname;
    }
    
    public String getNachname() {
        return nachname;
    }
    
    public int getAlter() {
        return alter;
    }
    
    public String getGeschlecht() {
        return geschlecht;
    }
    
    // Setter-Methoden
    public void setAlter(int alter) {
        this.alter = alter;
    }
    
    /**
     * Gibt den vollständigen Namen der Person zurück.
     * 
     * @return Name im Format "Vorname Nachname"
     */
    public String getFullName() {
        return vorname + " " + nachname;
    }
    
    // === 1:N Beziehung: Messungen verwalten ===
    
    /**
     * Fügt eine neue Messung zu dieser Person hinzu.
     * 
     * @param messung Die neue Messung
     */
    public void addMessung(Messung messung) {
        messungen.add(messung);
    }
    
    /**
     * Gibt alle Messungen dieser Person zurück.
     * 
     * @return Liste aller Messungen
     */
    public List<Messung> getMessungen() {
        return new ArrayList<>(messungen); // Gibt eine Kopie zurück (Kapselung!)
    }
    
    /**
     * Gibt die Anzahl der Messungen zurück.
     * 
     * @return Anzahl der gespeicherten Messungen
     */
    public int getAnzahlMessungen() {
        return messungen.size();
    }
    
    /**
     * Gibt die letzte Messung zurück (falls vorhanden).
     * 
     * @return Die letzte Messung, oder null wenn keine Messungen vorhanden
     */
    public Messung getLetzeMessung() {
        if (messungen.isEmpty()) {
            return null;
        }
        return messungen.get(messungen.size() - 1);
    }
    
    /**
     * Gibt den Durchschnitts-BMI aller Messungen zurück.
     * 
     * @return Der durchschnittliche BMI, oder 0 wenn keine Messungen vorhanden
     */
    public double getDurchschnittsBmi() {
        if (messungen.isEmpty()) {
            return 0;
        }
        
        double summe = 0;
        for (Messung m : messungen) {
            summe += m.getBmi();
        }
        
        return summe / messungen.size();
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", alter=" + alter +
                ", geschlecht='" + geschlecht + '\'' +
                ", messungen=" + messungen.size() + " Einträge" +
                '}';
    }
}
```

**UML-Diagramm der 1:N Beziehung:**
```
┌─────────────┐          1:N         ┌──────────────┐
│   Person    │ ─────────────────────► │   Messung    │
│             │  hat mehrere           │              │
│ - vorname   │                        │ - gewicht    │
│ - nachname  │                        │ - groesse    │
│ - messungen*│                        │ - bmi        │
└─────────────┘                        └──────────────┘
      │
      │ 1:N Assoziation in Code:
      │ List<Messung> messungen;
      │
```

### 3.2: Test der 1:N Beziehung

Erstelle eine Testklasse: `src/start/TestAssoziation1N.java`

```java
package start;

/**
 * Test der 1:N Beziehung zwischen Person und Messung.
 */
public class TestAssoziation1N {
    
    public static void main(String[] args) {
        
        // Erstelle eine Person
        Person person = new Person("Max", "Mustermann", 30, "Mann");
        System.out.println("Person erstellt: " + person.getFullName());
        System.out.println();
        
        // Erstelle mehrere Messungen (1:N Assoziation)
        Messung m1 = new Messung(85.0, 1.80); // BMI ≈ 26.2 (Übergewicht)
        Messung m2 = new Messung(80.0, 1.80); // BMI ≈ 24.7 (Normalgewicht)
        Messung m3 = new Messung(90.0, 1.80); // BMI ≈ 27.8 (Übergewicht)
        
        // Füge Messungen zur Person hinzu
        person.addMessung(m1);
        person.addMessung(m2);
        person.addMessung(m3);
        
        System.out.println("=== Messungen für " + person.getFullName() + " ===");
        System.out.println("Anzahl Messungen: " + person.getAnzahlMessungen());
        
        // Zeige alle Messungen
        for (int i = 0; i < person.getMessungen().size(); i++) {
            Messung m = person.getMessungen().get(i);
            System.out.println("  Messung " + (i+1) + ": " + m);
        }
        
        // Weitere Informationen
        System.out.println();
        System.out.println("Letzte Messung: " + person.getLetzeMessung());
        System.out.println("Durchschnitts-BMI: " + String.format("%.2f", person.getDurchschnittsBmi()));
    }
}
```

---

## Schritt 4: Die M:N Beziehung implementieren (OPTIONAL - Erweiterte Version)

### Ziel
Wir implementieren eine **M:N Beziehung** zwischen `Arzt` und `Patient`.

Das bedeutet:
- **M** (viele) Ärzte
- **N** (viele) Patienten
- Ein Arzt betreut viele Patienten, ein Patient wird von mehreren Ärzten betreut

### 4.1: Die Arzt-Klasse erstellen

Erstelle: `src/start/Arzt.java`

```java
package start;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Arzt-Klasse repräsentiert einen Arzt im System.
 * Ein Arzt kann mehrere Patienten (Personen) betreuen.
 * M:N Beziehung: Viele Ärzte betreuen viele Patienten.
 */
public class Arzt {
    
    private String name;
    private String spezialisierung; // z.B. "Allgemeinmedizin", "Kardiologie"
    
    // M:N Beziehung: Ein Arzt betreut mehrere Patienten
    private List<Person> patienten; // ArrayList speichert alle Patienten
    
    /**
     * Konstruktor: Erstellt einen neuen Arzt.
     * 
     * @param name Der Name des Arztes
     * @param spezialisierung Die Spezialisierung des Arztes
     */
    public Arzt(String name, String spezialisierung) {
        this.name = name;
        this.spezialisierung = spezialisierung;
        this.patienten = new ArrayList<>();
    }
    
    // Getter-Methoden
    public String getName() {
        return name;
    }
    
    public String getSpezialisierung() {
        return spezialisierung;
    }
    
    // === M:N Beziehung: Patienten verwalten ===
    
    /**
     * Fügt einen Patienten zur Liste dieses Arztes hinzu.
     * (Unidirektionale M:N Assoziation)
     * 
     * @param patient Der neue Patient
     */
    public void addPatient(Person patient) {
        if (!patienten.contains(patient)) {
            patienten.add(patient);
        }
    }
    
    /**
     * Gibt alle Patienten dieses Arztes zurück.
     * 
     * @return Liste aller Patienten
     */
    public List<Person> getPatienten() {
        return new ArrayList<>(patienten); // Kopie zurückgeben (Kapselung)
    }
    
    /**
     * Gibt die Anzahl der Patienten dieses Arztes zurück.
     * 
     * @return Anzahl der Patienten
     */
    public int getAnzahlPatienten() {
        return patienten.size();
    }
    
    /**
     * Berechnet den Durchschnitts-BMI aller Patienten.
     * 
     * @return Der durchschnittliche BMI aller Patienten
     */
    public double getDurchschnittsBmiAllerPatienten() {
        if (patienten.isEmpty()) {
            return 0;
        }
        
        double summe = 0;
        for (Person patient : patienten) {
            Messung letzte = patient.getLetzeMessung();
            if (letzte != null) {
                summe += letzte.getBmi();
            }
        }
        
        return summe / patienten.size();
    }
    
    @Override
    public String toString() {
        return "Arzt{" +
                "name='" + name + '\'' +
                ", spezialisierung='" + spezialisierung + '\'' +
                ", patienten=" + patienten.size() + " Einträge" +
                '}';
    }
}
```

### 4.2: Bidirektionale M:N Beziehung (ADVANCED)

Für eine vollständige M:N Beziehung können wir auch die `Person`-Klasse um die Liste der Ärzte erweitern:

```java
// In der Person-Klasse hinzufügen:

    // M:N Beziehung: Eine Person wird von mehreren Ärzten betreut
    private List<Arzt> aerzte;
    
    // Im Konstruktor:
    public Person(String vorname, String nachname, int alter, String geschlecht) {
        // ... bisheriger Code ...
        this.messungen = new ArrayList<>();
        this.aerzte = new ArrayList<>(); // M:N Assoziation initialisieren
    }
    
    // Neue Methoden:
    
    /**
     * Fügt einen Arzt zur Liste hinzu.
     * (Bidirektionale M:N Assoziation)
     * 
     * @param arzt Der neue Arzt
     */
    public void addArzt(Arzt arzt) {
        if (!aerzte.contains(arzt)) {
            aerzte.add(arzt);
            // Bidirektionalität: Auch beim Arzt hinzufügen
            arzt.addPatient(this);
        }
    }
    
    /**
     * Gibt alle Ärzte dieser Person zurück.
     * 
     * @return Liste aller Ärzte
     */
    public List<Arzt> getAerzte() {
        return new ArrayList<>(aerzte);
    }
```

**UML-Diagramm der M:N Beziehung:**
```
┌─────────────┐          M:N         ┌────────────┐
│   Person    │ ◄────────────────────► │   Arzt     │
│ (Patient)   │  wird betreut von     │            │
│             │  betreut              │ - name     │
│ - vorname   │                       │ - spezia.. │
│ - aerzte *  │                       │ - patienten│
└─────────────┘                       └────────────┘
```

### 4.3: Test der M:N Beziehung

Erstelle: `src/start/TestAssoziationMN.java`

```java
package start;

/**
 * Test der M:N Beziehung zwischen Arzt und Patient (Person).
 */
public class TestAssoziationMN {
    
    public static void main(String[] args) {
        
        // Erstelle Ärzte
        Arzt arzt1 = new Arzt("Dr. Schmidt", "Allgemeinmedizin");
        Arzt arzt2 = new Arzt("Dr. Müller", "Kardiologie");
        System.out.println("Ärzte erstellt: " + arzt1.getName() + ", " + arzt2.getName());
        System.out.println();
        
        // Erstelle Patienten
        Person patient1 = new Person("Max", "Mustermann", 30, "Mann");
        Person patient2 = new Person("Anna", "Beispiel", 25, "Frau");
        Person patient3 = new Person("Tom", "Test", 45, "Mann");
        
        // M:N Assoziation: Füge Patienten zu Ärzten hinzu
        arzt1.addPatient(patient1);
        arzt1.addPatient(patient2);
        arzt1.addPatient(patient3);
        
        arzt2.addPatient(patient1);
        arzt2.addPatient(patient3);
        
        System.out.println("=== Patienten von " + arzt1.getName() + " ===");
        for (Person p : arzt1.getPatienten()) {
            System.out.println("  - " + p.getFullName());
        }
        
        System.out.println();
        System.out.println("=== Patienten von " + arzt2.getName() + " ===");
        for (Person p : arzt2.getPatienten()) {
            System.out.println("  - " + p.getFullName());
        }
        
        // Messungen hinzufügen
        System.out.println();
        System.out.println("=== Messungen hinzufügen ===");
        patient1.addMessung(new Messung(85.0, 1.80));
        patient2.addMessung(new Messung(65.0, 1.65));
        patient3.addMessung(new Messung(95.0, 1.75));
        
        System.out.println("Durchschnitts-BMI aller Patienten von " + 
                          arzt1.getName() + ": " + 
                          String.format("%.2f", arzt1.getDurchschnittsBmiAllerPatienten()));
    }
}
```

---

## Schritt 5: Integration in die Bmirechner-Klasse

### Ziel
Wir passen die `Bmirechner`-Klasse an, um mit `Person` und `Messung` zu arbeiten.

### 5.1: Bmirechner-Klasse anpassen

Bearbeite `src/start/Bmirechner.java`:

```java
package start;

/**
 * Bmirechner-Klasse: Das Modell (Model) des BMI-Rechners.
 * Version 3: Arbeitet mit Person und Messung.
 */
public class Bmirechner {
    
    private Person person;    // Die aktuelle Person
    private Messung messung;  // Die aktuelle Messung
    
    /**
     * Konstruktor: Initialisiert den Rechner mit einer Person.
     * 
     * @param person Die Person, für die der BMI berechnet werden soll
     */
    public Bmirechner(Person person) {
        this.person = person;
        this.messung = null;
    }
    
    /**
     * Berechnet den BMI und speichert die Messung in der Person.
     * 
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Größe in m
     */
    public void berechneBmi(double gewicht, double groesse) {
        // Erstelle eine neue Messung
        messung = new Messung(gewicht, groesse);
        
        // Speichere die Messung in der Person (1:N Assoziation)
        person.addMessung(messung);
    }
    
    /**
     * Gibt den BMI der aktuellen Messung zurück.
     * 
     * @return Der BMI, oder 0 wenn keine Messung vorhanden
     */
    public double getBmi() {
        if (messung == null) {
            return 0;
        }
        return messung.getBmi();
    }
    
    /**
     * Gibt die Kategorie der aktuellen Messung zurück.
     * 
     * @return Die BMI-Kategorie (z.B. "Normalgewicht")
     */
    public String getKategorie() {
        if (messung == null) {
            return "Keine Messung vorhanden";
        }
        return messung.getKategorie();
    }
    
    /**
     * Gibt die aktuelle Person zurück.
     * 
     * @return Die Person
     */
    public Person getPerson() {
        return person;
    }
    
    /**
     * Gibt die aktuelle Messung zurück.
     * 
     * @return Die Messung, oder null wenn keine vorhanden
     */
    public Messung getMessung() {
        return messung;
    }
}
```

---

## Schritt 6: Aktualisierung des BmiManager-Controllers

### Ziel
Wir passen den `BmiManager` an, um die neuen Klassen zu nutzen.

### 6.1: BmiManager anpassen

```java
package start;

/**
 * BmiManager-Klasse: Der Controller des BMI-Rechners (Version 3+).
 * Verbindet View (MainWindow) und Model (Bmirechner, Person, Messung).
 */
public class BmiManager {
    
    private Bmirechner model;     // Das Modell
    private Person person;        // Die aktuelle Person
    
    /**
     * Konstruktor: Initialisiert den Manager.
     * Eine Standard-Person wird erstellt.
     */
    public BmiManager() {
        // Erstelle eine Standard-Person
        this.person = new Person("Benutzer", "Standard", 30, "Mann");
        this.model = new Bmirechner(person);
    }
    
    /**
     * Konstruktor mit benutzerdefiner Person.
     * 
     * @param person Die Person für die Berechnungen
     */
    public BmiManager(Person person) {
        this.person = person;
        this.model = new Bmirechner(person);
    }
    
    /**
     * Berechnet den BMI basierend auf Gewicht und Größe.
     * 
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Größe in m
     */
    public void berechneBMI(double gewicht, double groesse) {
        model.berechneBmi(gewicht, groesse);
    }
    
    /**
     * Interpretiert den BMI intelligent basierend auf Alter und Geschlecht.
     * 
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Größe in m
     * @param alter Das Alter in Jahren
     * @param geschlecht Das Geschlecht
     */
    public void interpretiereIntelligent(double gewicht, double groesse, 
                                        int alter, String geschlecht) {
        // Berechne BMI
        model.berechneBmi(gewicht, groesse);
        // Die Interpretation kann später spezialisiert werden
    }
    
    /**
     * Zeigt die Interpretation an (Platzhalter).
     */
    public void zeigeInterpretation() {
        // Kann mit zusätzlicher Logik erweitert werden
    }
    
    /**
     * Gibt das Modell zurück.
     * 
     * @return Das Bmirechner-Modell
     */
    public Bmirechner getModel() {
        return model;
    }
    
    /**
     * Gibt die aktuelle Person zurück.
     * 
     * @return Die Person
     */
    public Person getPerson() {
        return person;
    }
}
```

---

## Zusammenfassung: Assoziationen in der Praxis

| Assoziation | Beispiel | Implementierung | Besonderheit |
|------------|----------|-----------------|--------------|
| **1:1** | Person ↔ Krankenakte | `Krankenakte {Person person;}` | Genau ein Objekt |
| **1:N** | Person → Messungen | `List<Messung> messungen;` | Eine zu viele |
| **M:N** | Arzt ↔ Patienten | `List<Person> patienten;` in Arzt und `List<Arzt> aerzte;` in Person | Viele zu viele |

---

## Aufgaben für Schüler

### Aufgabe 1: Messung-Klasse verstehen ⭐
- Schaue dir die `Messung`-Klasse an
- Was speichert die Klasse?
- Wie wird der BMI berechnet?
- **Lösung:** `berechneBMI()` nutzt die Formel: BMI = Gewicht / (Größe²)

### Aufgabe 2: 1:N Beziehung nutzen ⭐⭐
- Erstelle eine Person mit `new Person("Max", "Muster", 30, "Mann")`
- Füge 3 Messungen hinzu mit `person.addMessung(new Messung(...))`
- Gib alle Messungen aus
- Berechne und zeige den Durchschnitts-BMI
- **Hinweis:** Nutze die Methode `getDurchschnittsBmi()`

### Aufgabe 3: Die Person-Klasse erweitern ⭐⭐
- Füge ein neues Attribut hinzu: `String email`
- Schreibe Getter und Setter
- Aktualisiere den Konstruktor
- Aktualisiere `toString()`
- **Tipp:** Folge dem gleichen Muster wie bei `vorname`, `nachname`, etc.

### Aufgabe 4: Neue Methoden schreiben ⭐⭐
- Schreibe in `Person`: `Messung getBesteMessung()` - gibt die Messung mit dem niedrigsten BMI zurück
- Schreibe in `Person`: `int getDurchschnittsalter()` - berechnet aus allen Messungen
- **Hinweis:** Verwende `Collections.min()` für die beste Messung

### Aufgabe 5: M:N Beziehung implementieren ⭐⭐⭐
- Erstelle die `Arzt`-Klasse nach Vorlage
- Implementiere die M:N Beziehung
- Teste mit mehreren Ärzten und Patienten
- Berechne Statistiken pro Arzt

### Aufgabe 6: Bidirektionale Beziehung ⭐⭐⭐
- Erweitere `Person` um eine Liste `List<Arzt> aerzte`
- Implementiere `addArzt(Arzt arzt)` mit bidirektionaler Synchronisation
- Sorge dafür, dass wenn eine Person einen Arzt bekommt, wird die Person auch bei dem Arzt hinzugefügt
- **Wichtig:** Verhindere Endlosschleifenprobleme!

### Aufgabe 7: Datenanalyse ⭐⭐⭐
- Schreibe eine Methode `getAenderndeMessungen()` in `Person`, die zeigt ob der BMI zunimmt oder abnimmt
- Implementiere `getTrendAusgabe()` die einen String wie "↑ BMI steigt" oder "↓ BMI sinkt" zurückgibt
- **Hinweis:** Vergleiche die erste und letzte Messung

---

## Wiederholung: Wichtige Konzepte

✅ **Assoziation**: Eine Beziehung zwischen zwei Klassen  
✅ **1:1 Beziehung**: Ein Objekt der Klasse A mit genau einem Objekt der Klasse B  
✅ **1:N Beziehung**: Ein Objekt der Klasse A mit mehreren Objekten der Klasse B  
✅ **M:N Beziehung**: Mehrere Objekte der Klasse A mit mehreren Objekten der Klasse B  
✅ **Kapselung**: Verwendung von `ArrayList<>` und Rückgabe von Kopien  
✅ **Bidirektionalität**: Beide Klassen kennen sich gegenseitig  
✅ **ArrayList**: Speichert dynamisch eine variable Anzahl von Objekten  
✅ **Polymorphie**: Objekte können in Listen gespeichert werden

---

## Häufige Fehler und Lösungen

### ❌ Fehler: Rückgabe der Original-Liste statt Kopie
```java
// FALSCH:
public List<Messung> getMessungen() {
    return messungen; // Außen können die Daten verändert werden!
}

// RICHTIG:
public List<Messung> getMessungen() {
    return new ArrayList<>(messungen); // Kopie schützt die Daten
}
```

### ❌ Fehler: ArrayList nicht initialisiert
```java
// FALSCH:
private List<Messung> messungen; // null!

// RICHTIG:
private List<Messung> messungen;

public Person(...) {
    this.messungen = new ArrayList<>(); // Im Konstruktor initialisieren!
}
```

### ❌ Fehler: Bidirektionalität nicht synchronisiert
```java
// FALSCH:
public void addArzt(Arzt arzt) {
    aerzte.add(arzt);
    // arzt.addPatient(this); <- FEHLT!
}

// RICHTIG:
public void addArzt(Arzt arzt) {
    if (!aerzte.contains(arzt)) {
        aerzte.add(arzt);
        arzt.addPatient(this); // Bidirektionalität!
    }
}
```

---

## Ressourcen & Weitere Links

- [Java ArrayList API](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)
- [UML Association Diagrams](https://en.wikipedia.org/wiki/Class_diagram)
- [Design Patterns: Composite](https://refactoring.guru/design-patterns/composite)
- [Kapselung in Java](https://docs.oracle.com/javase/tutorial/java/concepts/object.html)
