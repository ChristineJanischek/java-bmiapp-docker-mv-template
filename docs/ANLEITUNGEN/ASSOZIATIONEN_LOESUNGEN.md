# Lösungen: Assoziationen im BMI-Rechner (Lehrerversion)

Diese Datei enthält Musterlösungen für die Aufgaben aus `ASSOZIATIONEN_PERSON_MESSUNG.md`.

---

## Aufgabe 1: Messung-Klasse verstehen ⭐

**Frage:** Schaue dir die `Messung`-Klasse an. Was speichert die Klasse? Wie wird der BMI berechnet?

**Lösung:**

Die `Messung`-Klasse speichert:
- `gewicht` (double): Gewicht in Kilogramm
- `groesse` (double): Körpergröße in Metern
- `bmi` (double): Berechneter BMI-Wert
- `zeitstempel` (LocalDateTime): Zeitpunkt der Messung
- `kategorie` (String): BMI-Kategorie (detailliert von Bmirechner)
- `rechner` (Bmirechner): Delegiert die BMI-Berechnung und Kategorisierung

Der BMI wird mit der `Bmirechner`-Klasse berechnet:
```
BMI = Gewicht / (Größe²)

Beispiel: 80 kg, 1,80 m
BMI = 80 / (1.8 * 1.8) = 80 / 3.24 = 24,69 (Normalgewicht)
```

**Code:**
```java
public Messung(double gewicht, double groesse) {
    this.gewicht = gewicht;
    this.groesse = groesse;
    this.zeitstempel = LocalDateTime.now();
    
    // Bmirechner (DRY-Prinzip: Keine Duplikation!)
    this.rechner = new Bmirechner();
    this.bmi = rechner.berechne(gewicht, groesse);
    
    rechner.interpretiere();
    this.kategorie = rechner.getKategorie();
}
```

**Wichtig:** Statt die BMI-Berechnung selbst zu implementieren, nutzen wir die bestehende `Bmirechner`-Klasse. Das ist effizienter und konsistent mit den vorherigen Versionen!

---

## Aufgabe 2: 1:N Beziehung nutzen ⭐⭐

**Aufgabe:** Erstelle eine Person, füge 3 Messungen hinzu, gib alle aus und berechne den Durchschnitts-BMI.

**Musterlösung:**

```java
public class TestAssoziation1N {
    public static void main(String[] args) {
        
        // 1. Person erstellen
        Person person = new Person("Max", "Mustermann", 30, "Mann");
        System.out.println("Person erstellt: " + person.getFullName());
        System.out.println();
        
        // 2. Messungen erstellen und hinzufügen (1:N Assoziation)
        Messung m1 = new Messung(85.0, 1.80);
        Messung m2 = new Messung(80.0, 1.80);
        Messung m3 = new Messung(90.0, 1.80);
        
        person.addMessung(m1);
        person.addMessung(m2);
        person.addMessung(m3);
        
        System.out.println("=== Messungen für " + person.getFullName() + " ===");
        System.out.println("Anzahl Messungen: " + person.getAnzahlMessungen());
        System.out.println();
        
        // 3. Alle Messungen ausgeben
        int counter = 1;
        for (Messung m : person.getMessungen()) {
            System.out.println("Messung " + counter + ":");
            System.out.println("  Gewicht: " + m.getGewicht() + " kg");
            System.out.println("  Größe: " + m.getGroesse() + " m");
            System.out.println("  BMI: " + String.format("%.2f", m.getBmi()));
            System.out.println("  Kategorie: " + m.getKategorie());
            System.out.println("  Zeitstempel: " + m.getFormatiertesDatum());
            counter++;
        }
        
        System.out.println();
        
        // 4. Statistiken
        System.out.println("=== Statistiken ===");
        System.out.println("Durchschnitts-BMI: " + String.format("%.2f", person.getDurchschnittsBmi()));
        if (person.getLetzeMessung() != null) {
            System.out.println("Letzte Messung: " + person.getLetzeMessung().getFormatiertesDatum());
        }
    }
}
```

**Erwartete Ausgabe:**
```
Person erstellt: Max Mustermann

=== Messungen für Max Mustermann ===
Anzahl Messungen: 3

Messung 1:
  Gewicht: 85.0 kg
  Größe: 1.8 m
  BMI: 26.23
  Kategorie: Übergewicht
  Zeitstempel: [aktuelles Datum/Uhrzeit]

Messung 2:
  Gewicht: 80.0 kg
  Größe: 1.8 m
  BMI: 24.69
  Kategorie: Normalgewicht
  Zeitstempel: [aktuelles Datum/Uhrzeit]

Messung 3:
  Gewicht: 90.0 kg
  Größe: 1.8 m
  BMI: 27.78
  Kategorie: Übergewicht
  Zeitstempel: [aktuelles Datum/Uhrzeit]

=== Statistiken ===
Durchschnitts-BMI: 26.23
Letzte Messung: [aktuelles Datum/Uhrzeit]
```

---

## Aufgabe 3: Die Person-Klasse erweitern ⭐⭐

**Aufgabe:** Füge ein neues Attribut `email` hinzu, schreibe Getter und Setter, aktualisiere Konstruktor und `toString()`.

**Musterlösung:**

```java
package start;

import java.util.ArrayList;
import java.util.List;

public class Person {
    
    // Bisherige Attribute
    private String vorname;
    private String nachname;
    private int alter;
    private String geschlecht;
    private List<Messung> messungen;
    
    // NEUE Attribute
    private String email;
    
    /**
     * Konstruktor: Erstellt eine neue Person mit Grunddaten.
     * AKTUALISIERT mit email-Parameter
     */
    public Person(String vorname, String nachname, int alter, 
                  String geschlecht, String email) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
        this.geschlecht = geschlecht;
        this.email = email;
        this.messungen = new ArrayList<>();
    }
    
    // Bisherige Getter/Setter...
    
    // NEUE Getter und Setter für email
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        // Optional: Validierung
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            System.out.println("Ungültige Email-Adresse!");
        }
    }
    
    // AKTUALISIERTE toString() Methode
    @Override
    public String toString() {
        return "Person{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", alter=" + alter +
                ", geschlecht='" + geschlecht + '\'' +
                ", email='" + email + '\'' +
                ", messungen=" + messungen.size() +
                '}';
    }
    
    // Übrige Methoden bleiben gleich...
}
```

**Testbeispiel:**
```java
Person p = new Person("Anna", "Schmidt", 28, "Frau", "anna@example.com");
System.out.println(p);  // Zeigt alle Attribute inklusive email

System.out.println("Email: " + p.getEmail());  // anna@example.com
p.setEmail("anna.schmidt@company.de");
System.out.println("Neue Email: " + p.getEmail());  // anna.schmidt@company.de
```

---

## Aufgabe 4: Neue Methoden schreiben ⭐⭐

**Aufgabe:** Schreibe `getBesteMessung()` und weitere Hilfsmethoden.

**Musterlösung A: getBesteMessung()**

```java
/**
 * Gibt die Messung mit dem niedrigsten (besten) BMI zurück.
 * 
 * @return Die Messung mit niedrigstem BMI, oder null wenn keine Messungen vorhanden
 */
public Messung getBesteMessung() {
    if (messungen.isEmpty()) {
        return null;
    }
    
    Messung beste = messungen.get(0);
    for (Messung m : messungen) {
        if (m.getBmi() < beste.getBmi()) {
            beste = m;
        }
    }
    return beste;
}
```

**Musterlösung B: getSchlechtesteMessung()**

```java
/**
 * Gibt die Messung mit dem höchsten (schlechtesten) BMI zurück.
 */
public Messung getSchlechtesteMessung() {
    if (messungen.isEmpty()) {
        return null;
    }
    
    Messung schlechteste = messungen.get(0);
    for (Messung m : messungen) {
        if (m.getBmi() > schlechteste.getBmi()) {
            schlechteste = m;
        }
    }
    return schlechteste;
}
```

**Musterlösung C: getBMI-Differenz**

```java
/**
 * Gibt die Differenz zwischen beste und schlechteste Messung zurück.
 * Positive Werte bedeuten: Gewicht gestiegen
 * 
 * @return Die BMI-Differenz, oder 0 wenn weniger als 2 Messungen
 */
public double getBmiDifferenz() {
    if (messungen.size() < 2) {
        return 0;
    }
    
    Messung beste = getBesteMessung();
    Messung schlechteste = getSchlechtesteMessung();
    
    return schlechteste.getBmi() - beste.getBmi();
}
```

**Testbeispiel:**
```java
Person p = new Person("Max", "Muster", 30, "Mann", "max@example.com");
p.addMessung(new Messung(85.0, 1.80));  // BMI 26.23
p.addMessung(new Messung(80.0, 1.80));  // BMI 24.69 (beste)
p.addMessung(new Messung(90.0, 1.80));  // BMI 27.78 (schlechteste)

System.out.println("Beste Messung BMI: " + String.format("%.2f", p.getBesteMessung().getBmi())); // 24.69
System.out.println("Schlechteste Messung BMI: " + String.format("%.2f", p.getSchlechtesteMessung().getBmi())); // 27.78
System.out.println("Differenz: " + String.format("%.2f", p.getBmiDifferenz())); // 3.09
```

---

## Aufgabe 5: M:N Beziehung implementieren ⭐⭐⭐

**Aufgabe:** Implementiere `Arzt`-Klasse und M:N Beziehung.

**Musterlösung: Arzt-Klasse**

```java
package start;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Arzt-Klasse repräsentiert einen Arzt.
 * M:N Beziehung: Ein Arzt betreut mehrere Patienten (Personen).
 */
public class Arzt {
    
    private String name;
    private String spezialisierung;
    
    // M:N Beziehung
    private List<Person> patienten;
    
    /**
     * Konstruktor: Erstellt einen neuen Arzt.
     */
    public Arzt(String name, String spezialisierung) {
        this.name = name;
        this.spezialisierung = spezialisierung;
        this.patienten = new ArrayList<>();
    }
    
    // Getter
    public String getName() {
        return name;
    }
    
    public String getSpezialisierung() {
        return spezialisierung;
    }
    
    // === M:N Beziehung: Patienten verwalten ===
    
    /**
     * Fügt einen Patienten hinzu (M:N Assoziation).
     */
    public void addPatient(Person patient) {
        if (!patienten.contains(patient)) {
            patienten.add(patient);
        }
    }
    
    /**
     * Entfernt einen Patienten.
     */
    public void removePatient(Person patient) {
        patienten.remove(patient);
    }
    
    /**
     * Gibt alle Patienten zurück (Kopie für Kapselung).
     */
    public List<Person> getPatienten() {
        return new ArrayList<>(patienten);
    }
    
    /**
     * Gibt die Anzahl der Patienten zurück.
     */
    public int getAnzahlPatienten() {
        return patienten.size();
    }
    
    /**
     * Berechnet den Durchschnitts-BMI aller Patienten.
     */
    public double getDurchschnittsBmiAllerPatienten() {
        if (patienten.isEmpty()) {
            return 0;
        }
        
        double summe = 0;
        int messungen = 0;
        
        for (Person patient : patienten) {
            Messung letzte = patient.getLetzeMessung();
            if (letzte != null) {
                summe += letzte.getBmi();
                messungen++;
            }
        }
        
        return messungen > 0 ? summe / messungen : 0;
    }
    
    /**
     * Gibt alle Patienten mit Übergewicht zurück.
     */
    public List<Person> getPatienenMitUbergewicht() {
        List<Person> result = new ArrayList<>();
        for (Person p : patienten) {
            Messung m = p.getLetzeMessung();
            if (m != null && m.getKategorie().equals("Übergewicht")) {
                result.add(p);
            }
        }
        return result;
    }
    
    @Override
    public String toString() {
        return "Arzt{" +
                "name='" + name + '\'' +
                ", spezialisierung='" + spezialisierung + '\'' +
                ", patienten=" + patienten.size() +
                '}';
    }
}
```

**Testklasse:**
```java
public class TestAssoziationMN {
    public static void main(String[] args) {
        
        // Ärzte erstellen
        Arzt arzt1 = new Arzt("Dr. Schmidt", "Allgemeinmedizin");
        Arzt arzt2 = new Arzt("Dr. Müller", "Kardiologie");
        
        // Patienten erstellen
        Person p1 = new Person("Max", "Mustermann", 30, "Mann", "max@example.de");
        Person p2 = new Person("Anna", "Beispiel", 25, "Frau", "anna@example.de");
        Person p3 = new Person("Tom", "Test", 45, "Mann", "tom@example.de");
        
        // M:N Assoziation: Patienten bei Arzt registrieren
        arzt1.addPatient(p1);  // Dr. Schmidt: Max
        arzt1.addPatient(p2);  // Dr. Schmidt: Anna
        arzt1.addPatient(p3);  // Dr. Schmidt: Tom
        
        arzt2.addPatient(p1);  // Dr. Müller: Max
        arzt2.addPatient(p3);  // Dr. Müller: Tom
        
        // Ausgabe
        System.out.println("=== Dr. Schmidt Patienten ===");
        for (Person p : arzt1.getPatienten()) {
            System.out.println("  - " + p.getFullName());
        }
        
        System.out.println();
        System.out.println("=== Dr. Müller Patienten ===");
        for (Person p : arzt2.getPatienten()) {
            System.out.println("  - " + p.getFullName());
        }
        
        // Messungen hinzufügen
        p1.addMessung(new Messung(85.0, 1.80));  // BMI 26.23 (Übergewicht)
        p2.addMessung(new Messung(65.0, 1.65));  // BMI 23.88 (Normal)
        p3.addMessung(new Messung(95.0, 1.75));  // BMI 30.98 (Adipositas)
        
        System.out.println();
        System.out.println("Durchschnitts-BMI (Dr. Schmidt): " + 
                          String.format("%.2f", arzt1.getDurchschnittsBmiAllerPatienten()));
    }
}
```

---

## Aufgabe 6: Bidirektionale Beziehung ⭐⭐⭐

**Aufgabe:** Erweitere `Person` um bidirektionale M:N Beziehung mit `Arzt`.

**Musterlösung: Erweiterung der Person-Klasse**

```java
package start;

import java.util.ArrayList;
import java.util.List;

public class Person {
    
    // ... bisherige Attribute ...
    private List<Messung> messungen;
    
    // NEU: M:N Beziehung zu Ärzten
    private List<Arzt> aerzte;
    
    /**
     * Konstruktor: Initialisiert auch die Arzt-Liste.
     */
    public Person(String vorname, String nachname, int alter, 
                  String geschlecht, String email) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
        this.geschlecht = geschlecht;
        this.email = email;
        this.messungen = new ArrayList<>();
        this.aerzte = new ArrayList<>();  // NEU!
    }
    
    // === Bisherige Methoden ... ===
    
    // === NEU: Bidirektionale M:N Beziehung mit Arzt ===
    
    /**
     * Fügt einen Arzt hinzu (bidirektionale M:N Assoziation).
     * Sorgt dafür, dass die Person auch bei dem Arzt hinzugefügt wird.
     * 
     * @param arzt Der neue Arzt
     */
    public void addArzt(Arzt arzt) {
        if (!aerzte.contains(arzt)) {
            aerzte.add(arzt);
            // Bidirektionalität: Person auch beim Arzt hinzufügen
            arzt.addPatient(this);
        }
    }
    
    /**
     * Entfernt einen Arzt.
     */
    public void removeArzt(Arzt arzt) {
        if (aerzte.contains(arzt)) {
            aerzte.remove(arzt);
            // Bidirektionalität: Person auch beim Arzt entfernen
            arzt.removePatient(this);
        }
    }
    
    /**
     * Gibt alle Ärzte dieser Person zurück.
     */
    public List<Arzt> getAerzte() {
        return new ArrayList<>(aerzte);
    }
    
    /**
     * Gibt die Anzahl der Ärzte zurück.
     */
    public int getAnzahlAerzte() {
        return aerzte.size();
    }
    
    /**
     * Prüft, ob die Person von einem bestimmten Arzt betreut wird.
     */
    public boolean wurdeBetreuteVon(Arzt arzt) {
        return aerzte.contains(arzt);
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", alter=" + alter +
                ", geschlecht='" + geschlecht + '\'' +
                ", email='" + email + '\'' +
                ", messungen=" + messungen.size() +
                ", aerzte=" + aerzte.size() +  // NEU!
                '}';
    }
}
```

**Wichtig:** Auch in der `Arzt`-Klasse muss `removePatient()` implementiert sein:

```java
public class Arzt {
    // ... bisherige Code ...
    
    /**
     * Entfernt einen Patienten (für bidirektionale Beziehung).
     */
    public void removePatient(Person patient) {
        patienten.remove(patient);
    }
}
```

**Testklasse für bidirektionale Beziehung:**
```java
public class TestBidirektionaleMN {
    public static void main(String[] args) {
        
        // Ärzte erstellen
        Arzt arzt1 = new Arzt("Dr. Schmidt", "Allgemeinmedizin");
        Arzt arzt2 = new Arzt("Dr. Müller", "Kardiologie");
        
        // Patient erstellen
        Person patient = new Person("Max", "Mustermann", 30, "Mann", "max@example.de");
        
        // Bidirektionale Assoziation: Patient wird Ärzte hinzugefügt
        patient.addArzt(arzt1);  // Max wird Arzt von Dr. Schmidt
        patient.addArzt(arzt2);  // Max wird auch Arzt von Dr. Müller
        
        System.out.println("=== Von Patient aus ===");
        System.out.println("Ärzte von " + patient.getFullName() + ":");
        for (Arzt a : patient.getAerzte()) {
            System.out.println("  - " + a.getName() + " (" + a.getSpezialisierung() + ")");
        }
        
        System.out.println();
        System.out.println("=== Von Arzt aus (automatisch synchronisiert!) ===");
        System.out.println("Patienten von " + arzt1.getName() + ":");
        for (Person p : arzt1.getPatienten()) {
            System.out.println("  - " + p.getFullName());
        }
        
        System.out.println();
        System.out.println("Patienten von " + arzt2.getName() + ":");
        for (Person p : arzt2.getPatienten()) {
            System.out.println("  - " + p.getFullName());
        }
        
        // Test: Entfernen
        System.out.println();
        patient.removeArzt(arzt1);
        System.out.println("Nach Entfernen:");
        System.out.println("Ärzte von " + patient.getFullName() + ": " + patient.getAnzahlAerzte());
        System.out.println("Patienten von " + arzt1.getName() + ": " + arzt1.getAnzahlPatienten());
    }
}
```

---

## Aufgabe 7: Datenanalyse ⭐⭐⭐

**Aufgabe:** Schreibe Methoden zur Analyse von Messungs-Trends.

**Musterlösung A: Trend bestimmen**

```java
/**
 * Bestimmt den Trend der BMI-Entwicklung.
 * 
 * @return "STEIGEND", "FALLEND" oder "GLEICHBLEIBEND"
 */
public String getTrend() {
    if (messungen.size() < 2) {
        return "NICHT_GENUG_DATEN";
    }
    
    double erster = messungen.get(0).getBmi();
    double letzter = messungen.get(messungen.size() - 1).getBmi();
    
    if (letzter > erster) {
        return "STEIGEND";
    } else if (letzter < erster) {
        return "FALLEND";
    } else {
        return "GLEICHBLEIBEND";
    }
}
```

**Musterlösung B: Trend als visueller String**

```java
/**
 * Gibt den Trend als visuellen String aus.
 * 
 * @return Trend mit Symbol (z.B. "↑ BMI steigt")
 */
public String getTrendAusgabe() {
    String trend = getTrend();
    
    switch (trend) {
        case "STEIGEND":
            double diff1 = getDurchschnittsBmi() - messungen.get(0).getBmi();
            return "↑ BMI ist um " + String.format("%.2f", diff1) + " gestiegen";
            
        case "FALLEND":
            double diff2 = messungen.get(0).getBmi() - getDurchschnittsBmi();
            return "↓ BMI ist um " + String.format("%.2f", diff2) + " gefallen";
            
        case "GLEICHBLEIBEND":
            return "→ BMI ist stabil";
            
        default:
            return "⚠ Nicht genug Daten";
    }
}
```

**Musterlösung C: Detaillierte Analyse**

```java
/**
 * Gibt eine detaillierte Analyse der Messdaten zurück.
 */
public void zeigeAnalyse() {
    if (messungen.isEmpty()) {
        System.out.println("Keine Messungen vorhanden!");
        return;
    }
    
    System.out.println("=== Analyse für " + this.getFullName() + " ===");
    System.out.println("Anzahl Messungen: " + getAnzahlMessungen());
    
    if (getBesteMessung() != null) {
        System.out.println("Bester BMI: " + String.format("%.2f", getBesteMessung().getBmi()) +
                          " (" + getBesteMessung().getKategorie() + ")");
    }
    
    if (getSchlechtesteMessung() != null) {
        System.out.println("Schlechtester BMI: " + String.format("%.2f", getSchlechtesteMessung().getBmi()) +
                          " (" + getSchlechtesteMessung().getKategorie() + ")");
    }
    
    System.out.println("Durchschnitts-BMI: " + String.format("%.2f", getDurchschnittsBmi()));
    System.out.println("Trend: " + getTrendAusgabe());
}
```

**Testbeispiel:**
```java
Person p = new Person("Anna", "Schmidt", 28, "Frau", "anna@example.de");
p.addMessung(new Messung(70.0, 1.65));   // BMI 25.71
p.addMessung(new Messung(68.0, 1.65));   // BMI 25.00
p.addMessung(new Messung(65.0, 1.65));   // BMI 23.88

p.zeigeAnalyse();

// Erwartete Ausgabe:
// === Analyse für Anna Schmidt ===
// Anzahl Messungen: 3
// Bester BMI: 23.88 (Normalgewicht)
// Schlechtester BMI: 25.71 (Normalgewicht)
// Durchschnitts-BMI: 24.86
// Trend: ↓ BMI ist um 0.83 gefallen
```

---

## Bewertungskriterien für Schüler

| Kriterium | Punkte | Bemerkung |
|-----------|--------|----------|
| **Aufgabe 1** | 5 | Verständnis der Klasse |
| **Aufgabe 2** | 10 | Praktische Anwendung 1:N |
| **Aufgabe 3** | 10 | Erweiterung von Klassen |
| **Aufgabe 4** | 15 | Neue Methoden schreiben |
| **Aufgabe 5** | 15 | M:N Beziehung implementieren |
| **Aufgabe 6** | 20 | Bidirektionale Assoziation |
| **Aufgabe 7** | 10 | Datenanalyse und Logik |
| **Gesamt** | 85 | - |

---

## Häufige Fehler der Schüler und Lösungshinweise

### ❌ Fehler 1: ArrayList nicht initialisiert
```java
// FALSCH:
private List<Messung> messungen; // wird null!

// RICHTIG:
private List<Messung> messungen;
public Person(...) {
    this.messungen = new ArrayList<>();
}
```

### ❌ Fehler 2: Originalliste wird zurückgegeben
```java
// FALSCH:
public List<Messung> getMessungen() {
    return messungen; // Kapselung verletzt!
}

// RICHTIG:
public List<Messung> getMessungen() {
    return new ArrayList<>(messungen); // Kopie schützt
}
```

### ❌ Fehler 3: Bidirektionalität nicht synchronisiert
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
        arzt.addPatient(this);
    }
}
```

### ❌ Fehler 4: Endlosschleife bei bidirektionalen Operationen
```java
// FALSCH: Kann zu Stack Overflow führen!
public void addArzt(Arzt arzt) {
    aerzte.add(arzt);
    arzt.addPatient(this);  // if not checked!
}

// RICHTIG: if-Bedingung verhindert Zirkularität
public void addArzt(Arzt arzt) {
    if (!aerzte.contains(arzt)) {  // Schutz!
        aerzte.add(arzt);
        arzt.addPatient(this);
    }
}
```

### ❌ Fehler 5: Division durch Null bei leerem ArrayList
```java
// FALSCH:
public double getDurchschnittsBmi() {
    return summe / messungen.size(); // Was wenn size() == 0?
}

// RICHTIG:
public double getDurchschnittsBmi() {
    if (messungen.isEmpty()) {
        return 0;
    }
    return summe / messungen.size();
}
```

---

## Differenzierung für unterschiedliche Leistungsniveaus

### 📍 Grundniveau (E-G):
- Aufgaben 1-3 bewältigen
- Person- und Messung-Klasse verstehen
- 1:N Beziehung praktizieren

### 📍 Mittleres Niveau (C-E):
- Aufgaben 1-5 bewältigen
- M:N Beziehung implementieren
- Statistik-Methoden schreiben

### 📍 Hohes Niveau (A-C):
- Alle 7 Aufgaben bewältigen
- Bidirektionale Beziehungen verstehen
- Datenanalyse und erweiterte Logik

---

**Viel Erfolg beim Unterrichten! 🎓**
