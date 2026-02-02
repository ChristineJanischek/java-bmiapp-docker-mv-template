# Quick Reference: Assoziationen im BMI-Rechner

Eine schnelle Übersicht aller Klassen für die Arbeit mit Assoziationen.

---

## 📋 Schnellübersicht

```
Person (1:N) Messung
  ↓
Person kann mehrere Messungen haben

Arzt (M:N) Person
  ↓
Ein Arzt betreut viele Personen
Eine Person wird von mehreren Ärzten betreut
```

---

## 1️⃣ Die Person-Klasse (vereinfacht)

```java
public class Person {
    private String vorname, nachname;
    private int alter;
    private String geschlecht;
    private List<Messung> messungen = new ArrayList<>();
    
    // Konstruktor
    public Person(String v, String n, int a, String g) {
        this.vorname = v;
        this.nachname = n;
        this.alter = a;
        this.geschlecht = g;
    }
    
    // 1:N Operationen
    public void addMessung(Messung m) { messungen.add(m); }
    public List<Messung> getMessungen() { return new ArrayList<>(messungen); }
    public double getDurchschnittsBmi() {
        double sum = 0;
        for (Messung m : messungen) sum += m.getBmi();
        return sum / messungen.size();
    }
}
```

---

## 2️⃣ Die Messung-Klasse (vereinfacht)

```java
public class Messung {
    private double gewicht, groesse;
    private double bmi;
    private LocalDateTime zeitstempel;
    
    public Messung(double g, double gr) {
        this.gewicht = g;
        this.groesse = gr;
        this.bmi = g / (gr * gr);
        this.zeitstempel = LocalDateTime.now();
    }
    
    public double getBmi() { return bmi; }
    public double getGewicht() { return gewicht; }
    public double getGroesse() { return groesse; }
}
```

---

## 3️⃣ Die Arzt-Klasse (vereinfacht) - M:N Beziehung

```java
public class Arzt {
    private String name, spezialisierung;
    private List<Person> patienten = new ArrayList<>();
    
    public Arzt(String n, String s) {
        this.name = n;
        this.spezialisierung = s;
    }
    
    // M:N Operationen
    public void addPatient(Person p) {
        if (!patienten.contains(p)) patienten.add(p);
    }
    
    public List<Person> getPatienten() { return new ArrayList<>(patienten); }
    public int getAnzahlPatienten() { return patienten.size(); }
}
```

---

## 💻 Verwendungsbeispiele

### Beispiel 1: Eine Person mit mehreren Messungen

```java
// Person erstellen
Person person = new Person("Anna", "Schmidt", 28, "Frau");

// Messungen hinzufügen (1:N Beziehung)
person.addMessung(new Messung(70.0, 1.65)); // BMI = 25.7
person.addMessung(new Messung(72.0, 1.65)); // BMI = 26.4
person.addMessung(new Messung(68.0, 1.65)); // BMI = 25.0

// Zugriff auf Daten
System.out.println("Anzahl Messungen: " + person.getAnzahlMessungen()); // 3
System.out.println("Durchschnitts-BMI: " + person.getDurchschnittsBmi()); // ~25.7
```

### Beispiel 2: Mehrere Ärzte mit gemeinsamen Patienten (M:N Beziehung)

```java
// Ärzte erstellen
Arzt arzt1 = new Arzt("Dr. Schmidt", "Kardiologie");
Arzt arzt2 = new Arzt("Dr. Müller", "Allgemeinmedizin");

// Patienten erstellen
Person patient1 = new Person("Max", "Muster", 45, "Mann");
Person patient2 = new Person("Lisa", "Test", 32, "Frau");

// Patienten bei Ärzten registrieren (M:N Beziehung)
arzt1.addPatient(patient1);  // Dr. Schmidt betreut Max
arzt1.addPatient(patient2);  // Dr. Schmidt betreut Lisa
arzt2.addPatient(patient1);  // Dr. Müller betreut auch Max

// Zugriff auf Daten
System.out.println("Patienten von Dr. Schmidt: " + arzt1.getAnzahlPatienten()); // 2
System.out.println("Patienten von Dr. Müller: " + arzt2.getAnzahlPatienten()); // 1
```

---

## 🔑 Wichtige Konzepte

| Konzept | Bedeutung | Beispiel |
|---------|----------|---------|
| **Assoziation** | Verbindung zwischen Klassen | Person hat Messung |
| **1:1** | Eins-zu-Eins | Person ↔ Pass |
| **1:N** | Eins-zu-Viele | Person ↔ Messungen |
| **M:N** | Viele-zu-Viele | Arzt ↔ Patienten |
| **ArrayList** | Dynamische Liste | `List<Messung>` |
| **Kapselung** | Daten schützen | `new ArrayList<>(...)` zurückgeben |

---

## ⚡ Häufige Operationen

### Liste durchlaufen (Iteration)
```java
for (Messung m : person.getMessungen()) {
    System.out.println("BMI: " + m.getBmi());
}
```

### Element hinzufügen
```java
person.addMessung(new Messung(75.0, 1.80));
```

### Anzahl der Elemente
```java
int count = person.getAnzahlMessungen();
```

### Durchschnittswert berechnen
```java
double durchschnitt = person.getDurchschnittsBmi();
```

### Bedingte Ausgabe
```java
if (person.getAnzahlMessungen() > 0) {
    System.out.println("Messungen vorhanden");
}
```

---

## ❓ Häufige Fragen

**F: Was ist der Unterschied zwischen `List<Messung>` und `Messung[]`?**  
A: ArrayList ist dynamisch (Größe kann sich ändern), Array ist statisch (feste Größe).

**F: Warum geben wir `new ArrayList<>(messungen)` zurück und nicht `messungen`?**  
A: Um Kapselung zu gewährleisten - die Original-Liste bleibt geschützt.

**F: Können wir `messungen.clear()` von außen aufrufen?**  
A: Nur wenn wir die Originalliste zurückgeben. Mit Kopie ist es sicher.

**F: Was passiert wenn die ArrayList leer ist?**  
A: Wir sollten mit `isEmpty()` prüfen oder 0 zurückgeben.

---

## 🚀 Erweiterte Szenarien

### Szenario 1: Messung suchen
```java
public Messung findeMessungVonDatum(LocalDateTime datum) {
    for (Messung m : messungen) {
        if (m.getZeitstempel().equals(datum)) {
            return m;
        }
    }
    return null;
}
```

### Szenario 2: Beste Messung finden
```java
public Messung getBesteMessung() {
    if (messungen.isEmpty()) return null;
    Messung beste = messungen.get(0);
    for (Messung m : messungen) {
        if (m.getBmi() < beste.getBmi()) {
            beste = m;
        }
    }
    return beste;
}
```

### Szenario 3: Filter nach Kategorie
```java
public List<Messung> getNormalgewicht() {
    List<Messung> result = new ArrayList<>();
    for (Messung m : messungen) {
        if (m.getKategorie().equals("Normalgewicht")) {
            result.add(m);
        }
    }
    return result;
}
```

---

## 🎯 Checkliste für die Implementierung

- [ ] Person-Klasse erstellt mit ArrayList<Messung>
- [ ] Messung-Klasse erstellt mit BMI-Berechnung
- [ ] addMessung() Methode funktioniert
- [ ] getMessungen() gibt Kopie zurück (Kapselung)
- [ ] Durchschnitts-BMI wird korrekt berechnet
- [ ] Arzt-Klasse erstellt mit ArrayList<Person>
- [ ] M:N Beziehung getestet
- [ ] Alle Getter/Setter vorhanden
- [ ] toString() Methoden aussagekräftig
- [ ] Code getestet mit Testklassen

---

## 📝 Testvorlage

```java
public class TestAssoziationen {
    public static void main(String[] args) {
        // Test 1: 1:N Beziehung
        Person p = new Person("Test", "Mensch", 30, "Mann");
        p.addMessung(new Messung(80, 1.80));
        System.out.println("✓ Messung hinzugefügt");
        
        // Test 2: ArrayList-Operationen
        System.out.println("Anzahl: " + p.getAnzahlMessungen());
        System.out.println("BMI: " + p.getLetzeMessung().getBmi());
        
        // Test 3: M:N Beziehung
        Arzt a = new Arzt("Dr. Test", "Allgem.");
        a.addPatient(p);
        System.out.println("✓ Patient registriert");
        System.out.println("Patienten: " + a.getAnzahlPatienten());
    }
}
```

---

## 📚 Zusätzliche Ressourcen

```
Ordnerstruktur:
/docs/ANLEITUNGEN/
├── ASSOZIATIONEN_PERSON_MESSUNG.md (Hauptlernmaterial)
├── ASSOZIATIONEN_QUICK_REFERENCE.md (Diese Datei)
└── JAVA_PROGRAMMIERUNG/
    ├── ASSOZIATIONEN.md
    └── KAPSELUNG.md
```

---

**Viel Erfolg beim Lernen! 🎓**
