# Bridge-Klasse: Arzt ↔ Behandlung ↔ Patient – Praktische Implementierung

## 🎯 Ziel dieser Anleitung

Sie werden darum gebeten, die **direkte M:N Beziehung** zwischen Arzt und Patient zu verbessern, indem Sie eine **Bridge-Klasse `Behandlung`** dazwischen einführen, die relevant Daten speichert.

---

## 📋 Vorher ↔ Nachher Vergleich

### ❌ VORHER (Problematisch):
```java
// Arzt kennt direkt alle Patienten
public class Arzt {
    private List<Person> patienten; // M:N direkt
    // PROBLEM: Wie speichern wir: Seit wann? Welche Ausgabe? Status?
}

// Patient kennt direkt alle Ärzte
public class Person {
    private List<Arzt> aerzte; // M:N direkt
    // PROBLEM: Wie speichern wir: Seit wann? Welche Diagnose?
}
```

### ✅ NACHHER (Best Practice):
```java
// Behandlung ist die Bridge
public class Behandlung {
    private Arzt arzt;              // N:1 zum Arzt
    private Person patient;         // N:1 zum Patienten
    private LocalDate beginnDatum;  // DATEN DER ASSOZIATION!
    private String diagnose;        // DATEN DER ASSOZIATION!
    private String status;          // DATEN DER ASSOZIATION!
}

// Arzt kennt seine Behandlungen, nicht direkt Patienten
public class Arzt {
    private List<Behandlung> behandlungen; // 1:N
}

// Patient kennt seine Behandlungen, nicht direkt Ärzte
public class Person {
    private List<Behandlung> behandlungen; // 1:N
}

// UML:
// Arzt (1) ──┐
//            │──►Behandlung◄──│ (N)
// Person (1)─┘                └─ (N)
```

---

## 📝 Schritt 1: Die Behandlung-Klasse erstellen

Erstelle eine neue Datei: `src/start/Behandlung.java`

```java
package start;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Die Behandlung-Klasse ist die Bridge zwischen Arzt und Patient.
 * Sie speichert alle Informationen zur Behandlungsbeziehung.
 * 
 * UML:
 *   Arzt (1) ──┐
 *              ├──► Behandlung ◄──┐ Patient
 *   Periode, Diagnose             │
 */
public class Behandlung {
    
    // === Assoziationen zu anderen Objekten ===
    private Arzt arzt;              // N:1 zum Arzt
    private Person patient;         // N:1 zum Patienten
    
    // === Daten der Assoziation selbst ===
    private LocalDate beginnDatum;  // Wann wurde die Behandlung gestartet?
    private LocalDate endDatum;     // Wann wurde sie beendet? (null = laufend)
    private String diagnose;        // Was ist die Diagnose?
    private String behandlungsStatus; // "Aktiv", "Abgeschlossen", "Pausiert"
    private String noizen;          // Behandlungsnotizen
    
    /**
     * Konstruktor: Erstellt eine neue Behandlung.
     * 
     * @param arzt Der behandelnde Arzt
     * @param patient Der Patient (eine Person)
     * @param diagnose Die Diagnose
     */
    public Behandlung(Arzt arzt, Person patient, String diagnose) {
        this.arzt = arzt;
        this.patient = patient;
        this.diagnose = diagnose;
        this.beginnDatum = LocalDate.now();
        this.endDatum = null; // Noch nicht beendet
        this.behandlungsStatus = "Aktiv";
        this.noizen = "";
    }
    
    // === Getter-Methoden ===
    
    public Arzt getArzt() {
        return arzt;
    }
    
    public Person getPatient() {
        return patient;
    }
    
    public LocalDate getBeginnDatum() {
        return beginnDatum;
    }
    
    public LocalDate getEndDatum() {
        return endDatum;
    }
    
    public String getDiagnose() {
        return diagnose;
    }
    
    public String getBehandlungsStatus() {
        return behandlungsStatus;
    }
    
    public String getNotizen() {
        return noizen;
    }
    
    // === Setter-Methoden ===
    
    public void setBehandlungsStatus(String status) {
        this.behandlungsStatus = status;
        // Wenn Status auf "Abgeschlossen" gesetzt wird, speichern wir das Enddatum
        if ("Abgeschlossen".equals(status)) {
            this.endDatum = LocalDate.now();
        }
    }
    
    public void addNotiz(String notiz) {
        this.noizen += notiz + " [" + LocalDate.now() + "]\n";
    }
    
    /**
     * Gibt die Dauer der Behandlung in Tagen zurück.
     * 
     * @return Die Anzahl der Tage (von Beginn bis Heute oder bis Ende)
     */
    public long getDauerInTagen() {
        LocalDate bis = endDatum != null ? endDatum : LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(beginnDatum, bis);
    }
    
    /**
     * Gibt formatierte Darstellung zurück.
     */
    public String getFormatiertesDatum() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String von = beginnDatum.format(fmt);
        String bis = endDatum != null ? endDatum.format(fmt) : "Laufend";
        return von + " bis " + bis;
    }
    
    /**
     * Gibt eine Zusammenfassung der Behandlung aus.
     */
    @Override
    public String toString() {
        return "Behandlung{" +
                "arzt=" + arzt.getName() +
                ", patient=" + patient.getFullName() +
                ", diagnose='" + diagnose + '\'' +
                ", status='" + behandlungsStatus + '\'' +
                ", dauer=" + getDauerInTagen() + " Tage" +
                '}';
    }
}
```

---

## 🔗 Schritt 2: Arzt-Klasse anpassen

Bearbeite `src/start/Arzt.java`, um mit der Behandlung-Klasse zu arbeiten:

```java
package start;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Die Arzt-Klasse repräsentiert einen Behandelnden Arzt.
 * Der Arzt kennt seine Behandlungen, nicht direkt seine Patienten.
 * 
 * Beziehung: Arzt (1) ──► (N) Behandlung
 */
public class Arzt {
    
    private String name;
    private String spezialisierung; // z.B. "Allgemeinmedizin", "Kardiologie"
    
    // 1:N Beziehung: Ein Arzt hat mehrere Behandlungen
    private List<Behandlung> behandlungen;
    
    /**
     * Konstruktor: Erstellt einen neuen Arzt.
     * 
     * @param name Der Name des Arztes
     * @param spezialisierung Die Spezialisierung (z.B. "Kardiologie")
     */
    public Arzt(String name, String spezialisierung) {
        this.name = name;
        this.spezialisierung = spezialisierung;
        this.behandlungen = new ArrayList<>();
    }
    
    // === Getter-Methoden ===
    
    public String getName() {
        return name;
    }
    
    public String getSpezialisierung() {
        return spezialisierung;
    }
    
    // === Behandlungen verwalten (1:N Beziehung) ===
    
    /**
     * Startet eine neue Behandlung für einen Patienten.
     * 
     * @param patient Der Patient (Person)
     * @param diagnose Die Diagnose
     * @return Die neu erstellte Behandlung
     */
    public Behandlung startBehandlung(Person patient, String diagnose) {
        Behandlung behandlung = new Behandlung(this, patient, diagnose);
        behandlungen.add(behandlung);
        return behandlung;
    }
    
    /**
     * Gibt alle Behandlungen dieses Arztes zurück.
     * 
     * @return Liste aller Behandlungen
     */
    public List<Behandlung> getBehandlungen() {
        return new ArrayList<>(behandlungen);
    }
    
    /**
     * Gibt nur die aktiven Behandlungen zurück.
     * 
     * @return Liste der aktiven Behandlungen
     */
    public List<Behandlung> getAktiveBehandlungen() {
        return behandlungen.stream()
            .filter(b -> "Aktiv".equals(b.getBehandlungsStatus()))
            .collect(Collectors.toList());
    }
    
    /**
     * Gibt die Patienten mit aktiven Behandlungen zurück.
     * (Leitet die Patienten aus den Behandlungen ab)
     * 
     * @return Liste der Patienten mit aktiven Behandlungen
     */
    public List<Person> getAktuellePatienten() {
        return getAktiveBehandlungen().stream()
            .map(Behandlung::getPatient)
            .collect(Collectors.toList());
    }
    
    /**
     * Gibt die Anzahl der aktuellen Patienten zurück.
     */
    public int getAnzahlPatienten() {
        return getAktuellePatienten().size();
    }
    
    /**
     * Sucht eine Behandlung für einen bestimmten Patienten.
     * 
     * @param patient Der gesuchte Patient
     * @return Die Behandlung, oder null wenn nicht vorhanden
     */
    public Behandlung getBehandlungFuer(Person patient) {
        return behandlungen.stream()
            .filter(b -> b.getPatient().equals(patient))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Gibt alle Diagnosen dieses Arztes aus.
     * 
     * @return Liste aller unterschiedlichen Diagnosen
     */
    public List<String> getAlleDiagnosen() {
        return behandlungen.stream()
            .map(Behandlung::getDiagnose)
            .distinct()
            .collect(Collectors.toList());
    }
    
    /**
     * Gibt die durchschnittliche Behandlungsdauer aus.
     */
    public double getDurchschnittlicheBehandlungsdauer() {
        if (behandlungen.isEmpty()) return 0;
        
        long summe = behandlungen.stream()
            .mapToLong(Behandlung::getDauerInTagen)
            .sum();
        
        return (double) summe / behandlungen.size();
    }
    
    /**
     * Beendet eine Behandlung.
     * 
     * @param behandlung Die zu beendende Behandlung
     */
    public void beendBehandlung(Behandlung behandlung) {
        behandlung.setBehandlungsStatus("Abgeschlossen");
    }
    
    @Override
    public String toString() {
        return "Arzt{" +
                "name='" + name + '\'' +
                ", spezialisierung='" + spezialisierung + '\'' +
                ", behandlungen=" + behandlungen.size() +
                ", aktive Patienten=" + getAnzahlPatienten() +
                '}';
    }
}
```

---

## 👤 Schritt 3: Person-Klasse erweitern

Bearbeite `src/start/Person.java`, um auch die Behandlungen zu verwalten:

```java
package start;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Die Person-Klasse (Patient) kennt ihre Behandlungen.
 * 
 * Struktur:
 *   Person (1) ──► (N) Behandlung
 *   Person (1) ──► (N) Messung
 */
public class Person {
    
    // === Persönliche Daten ===
    private String vorname;
    private String nachname;
    private int alter;
    private String geschlecht;
    
    // === Assoziationen ===
    private List<Messung> messungen;      // 1:N zu Messungen
    private List<Behandlung> behandlungen; // 1:N zu Behandlungen
    
    /**
     * Erweiterte Konstruktor-Signatur (wenn nötig).
     */
    public Person(String vorname, String nachname, int alter, String geschlecht) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
        this.geschlecht = geschlecht;
        
        // Initialisiere beide Listen
        this.messungen = new ArrayList<>();
        this.behandlungen = new ArrayList<>();
    }
    
    // === Getter für persönliche Daten ===
    
    public String getVorname() { return vorname; }
    public String getNachname() { return nachname; }
    public int getAlter() { return alter; }
    public String getGeschlecht() { return geschlecht; }
    public String getFullName() { return vorname + " " + nachname; }
    
    // === 1:N Beziehung: Messungen ===
    
    public void addMessung(Messung messung) {
        messungen.add(messung);
    }
    
    public List<Messung> getMessungen() {
        return new ArrayList<>(messungen);
    }
    
    public int getAnzahlMessungen() {
        return messungen.size();
    }
    
    public double getDurchschnittsBmi() {
        if (messungen.isEmpty()) return 0;
        return messungen.stream()
            .mapToDouble(Messung::getBmi)
            .average()
            .orElse(0);
    }
    
    // === 1:N Beziehung: Behandlungen ===
    
    /**
     * Fügt eine Behandlung hinzu.
     * (Wird normalerweise vom Arzt aufgerufen)
     */
    public void addBehandlung(Behandlung behandlung) {
        if (!behandlungen.contains(behandlung)) {
            behandlungen.add(behandlung);
        }
    }
    
    /**
     * Gibt alle Behandlungen dieser Person zurück.
     */
    public List<Behandlung> getBehandlungen() {
        return new ArrayList<>(behandlungen);
    }
    
    /**
     * Gibt nur die aktiven Behandlungen zurück.
     */
    public List<Behandlung> getAktiveBehandlungen() {
        return behandlungen.stream()
            .filter(b -> "Aktiv".equals(b.getBehandlungsStatus()))
            .collect(Collectors.toList());
    }
    
    /**
     * Gibt die Ärzte mit aktiven Behandlungen zurück.
     * (Abgeleitet aus den Behandlungen)
     */
    public List<Arzt> getMeineAerzte() {
        return getAktiveBehandlungen().stream()
            .map(Behandlung::getArzt)
            .collect(Collectors.toList());
    }
    
    /**
     * Gibt die Anzahl der Ärzte mit aktiven Behandlungen zurück.
     */
    public int getAnzahlAerzte() {
        return getMeineAerzte().size();
    }
    
    /**
     * Gibt alle Diagnosen dieser Person zurück.
     */
    public List<String> getAlleDiagnosen() {
        return behandlungen.stream()
            .map(Behandlung::getDiagnose)
            .distinct()
            .collect(Collectors.toList());
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", alter=" + alter +
                ", messungen=" + messungen.size() +
                ", behandlungen=" + behandlungen.size() +
                '}';
    }
}
```

---

## 🧪 Schritt 4: Test der Bridge-Klasse

Erstelle eine neue Testklasse: `src/start/TestBehandlung.java`

```java
package start;

/**
 * Test der M:N Beziehung mit Bridge-Klasse (Behandlung).
 * 
 * Struktur:
 *   Arzt (1) ──┐
 *              ├──► Behandlung ◄──┐ (N) Person
 *   Diagnose    │
 */
public class TestBehandlung {
    
    public static void main(String[] args) {
        System.out.println("=== M:N Beziehung mit Bridge-Klasse ===\n");
        
        // === Ärzte erstellen ===
        Arzt arzt1 = new Arzt("Dr. Schmidt", "Allgemeinmedizin");
        Arzt arzt2 = new Arzt("Dr. Müller", "Kardiologie");
        System.out.println("✓ Ärzte erstellt:");
        System.out.println("  - " + arzt1.getName() + " (" + arzt1.getSpezialisierung() + ")");
        System.out.println("  - " + arzt2.getName() + " (" + arzt2.getSpezialisierung() + ")");
        System.out.println();
        
        // === Patienten (Personen) erstellen ===
        Person patient1 = new Person("Max", "Mustermann", 35, "Mann");
        Person patient2 = new Person("Anna", "Beispiel", 42, "Frau");
        Person patient3 = new Person("Tom", "Test", 58, "Mann");
        System.out.println("✓ Patienten erstellt:");
        System.out.println("  - " + patient1.getFullName());
        System.out.println("  - " + patient2.getFullName());
        System.out.println("  - " + patient3.getFullName());
        System.out.println();
        
        // === Behandlungen starten (Bridge-Klasse verwenden) ===
        System.out.println("=== Behandlungen starten ===\n");
        
        // Dr. Schmidt behandelt patient1 und patient2
        Behandlung b1 = arzt1.startBehandlung(patient1, "Hypertonie");
        patient1.addBehandlung(b1); // Bidirektionale Assoziation halten
        
        Behandlung b2 = arzt1.startBehandlung(patient2, "Diabetes");
        patient2.addBehandlung(b2);
        
        // Dr. Müller behandelt patient1 und patient3 (patient1 hat zwei Ärzte!)
        Behandlung b3 = arzt2.startBehandlung(patient1, "Herzrhythmusstörung");
        patient1.addBehandlung(b3);
        
        Behandlung b4 = arzt2.startBehandlung(patient3, "Koronare Herzkrankheit");
        patient3.addBehandlung(b4);
        
        System.out.println("✓ " + arzt1.getName() + " hat " + arzt1.getAnzahlPatienten() + " Patienten");
        System.out.println("✓ " + arzt2.getName() + " hat " + arzt2.getAnzahlPatienten() + " Patienten");
        System.out.println();
        
        // === Informationen ausgeben ===
        System.out.println("=== Patientenübersicht ===\n");
        
        System.out.println(patient1.getFullName() + ":");
        System.out.println("  Behandlungen:");
        for (Behandlung b : patient1.getBehandlungen()) {
            System.out.println("    - " + b.getArzt().getName() + ": " + b.getDiagnose() + 
                             " (" + b.getBehandlungsStatus() + ")");
        }
        System.out.println("  Meine Ärzte: " + patient1.getAnzahlAerzte());
        System.out.println();
        
        System.out.println(patient2.getFullName() + ":");
        System.out.println("  Behandlungen:");
        for (Behandlung b : patient2.getBehandlungen()) {
            System.out.println("    - " + b.getArzt().getName() + ": " + b.getDiagnose());
        }
        System.out.println();
        
        // === Notizen hinzufügen ===
        System.out.println("=== Notizen hinzufügen ===\n");
        b1.addNotiz("Blutdruck gemessen: 160/100");
        b1.addNotiz("Antihypertensivum verschrieben");
        
        System.out.println("Behandlung " + b1.getDiagnose() + ":");
        System.out.println(b1.getNotizen());
        System.out.println();
        
        // === Statistiken ===
        System.out.println("=== Statistiken ===\n");
        
        System.out.println(arzt1.getName() + ":");
        System.out.println("  Gesamte Behandlungen: " + arzt1.getBehandlungen().size());
        System.out.println("  Aktive Behandlungen: " + arzt1.getAktiveBehandlungen().size());
        System.out.println("  Diagnosen: " + arzt1.getAlleDiagnosen());
        System.out.println();
        
        // === Behandlung beenden ===
        System.out.println("=== Behandlung beenden ===\n");
        arzt1.beendBehandlung(b1);
        
        System.out.println("Behandlung \"" + b1.getDiagnose() + "\" Status: " + 
                          b1.getBehandlungsStatus());
        System.out.println("Dauer: " + b1.getDauerInTagen() + " Tage");
        System.out.println("Zeitraum: " + b1.getFormatiertesDatum());
    }
}
```

---

## 📊 UML-Diagramm der neuen Struktur

```
┌──────────┐           1         ┌──────────────┐           1   ┌──────────┐
│  Arzt    │◄──────────────────┤ Behandlung   ├─────────────►│ Person   │
│          │   behandelt           │            │   Patient      │          │
│ - name   │   behandlungen        │ - arzt     │ von            │ - Name   │
│ - spezia │──────────(N)──────■   │ - patient  │ - patient      │ - Alter  │
└──────────┘                   ◄───│ - diagnose │───(N)──────────│ - Messung│
                                   │ - status   │   werden behandelt
                                   │ - beginn   │
                                   └────────────┘
```

---

## 🎓 Lernziele dieser Anleitung

Nach dieser Anleitung verstehen Sie:

✅ Wie man M:N Beziehungen mit einer Bridge-Klasse auflöst  
✅ Wie man Daten speichert, die zur Assoziation gehören (nicht zu den Objekten)  
✅ Wie man bidirektionale Beziehungen korrekt implementiert  
✅ Wie man komplexe Abfragen mit Streams durchführt (`filter`, `map`, `collect`)  
✅ Wie man Datenbanken-Konzepte in Java-Code umsetzt  
✅ Best Practice für Kapselung und Konsistenz  

---

## 💡 Häufige Fragen

**F: Warum brauchen wir die Bridge-Klasse?**  
A: Sie speichert Daten, die zur Beziehung, nicht zu den Objekten gehören (Diagnose, Zeitstempel, Status).

**F: Kann ich immer noch direkte M:N verwenden?**  
A: Ja, wenn es **KEINE** zusätzlichen Daten zur Beziehung gibt. Sonst ist die Bridge besser.

**F: Wie halte ich die bidirektionale Konsistenz?**  
A: Nutze die `add`-Methoden konsistent. Wenn `Arzt.startBehandlung()` aufgerufen wird, muss auch `Patient.addBehandlung()` aufgerufen werden.

**F: Gibt es eine bessere Lösung?**  
A: Mit JPA/Hibernate können Sie Annotationen verwenden, die Bridge-Klassen automatisch handhaben. Das ist aber ein fortgeschrittenes Thema.

---

## 📚 Aufgaben

### Aufgabe 1: Bridge-Klasse testen  ⭐
- Führe `TestBehandlung.java` aus
- Beobachte, wie die M:N Beziehung funktioniert
- Erkläre, warum die Bridge-Klasse nützlich ist

### Aufgabe 2: Neue Daten hinzufügen  ⭐⭐
- Füge zu `Behandlung` ein neues Attribut `medikament` hinzu
- Schreibe Getter/Setter
- Aktualisiere den Konstruktor
- Teste die Änderung

### Aufgabe 3: Abfragen schreiben  ⭐⭐
- Schreibe eine Methode in `Arzt`: `findPatientMitDiagnose(String diagnose)`
- Schreibe eine Methode in `Person`: `wurdBehandeltVon(Arzt arzt)`
- Teste beide Methoden

### Aufgabe 4: Erweiterte Statistiken  ⭐⭐⭐
- Schreibe in `Arzt`: `getMeistBehandelteDiagnose()` – gibt die häufigste Diagnose zurück
- Schreibe in `Person`: `getAezteMitAktivBehanlungen()` – gibt nur Ärzte mit aktiven Behandlungen zurück
- Schreibe in `Behandlung`: `istAktiv()` – gibt true zurück wenn Status aktiv ist

### Aufgabe 5: Datenbank-Denken  ⭐⭐⭐
- Überlegen Sie: Wie würde diese Struktur in einer SQL-Datenbank aussehen?
  - Tabelle `arzt`
  - Tabelle `person`
  - Tabelle `behandlung` (Join-Tabelle mit Fremdschlüsseln und Daten)
- Schreibe die SQL CREATE TABLE Befehle auf

---

## 🚀 Zusammenfassung

Die **Bridge-Klasse (Behandlung)** ist eine elegante Lösung für M:N Beziehungen, die:

1. **Zusätzliche Daten speichert**: Diagnose, Status, Zeitstempel
2. **Klare Struktur bietet**: Zwei 1:N Beziehungen statt einer komplexen M:N
3. **Flexibel und wartbar** ist: Neue Daten lassen sich einfach hinzufügen
4. **Der Datenbank entspricht**: Join-Tabellen können direkt implementiert werden
5. **Best Practice ist**: Wird in professionellen Systemen so gemacht

