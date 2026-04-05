# Vererbung in Java

Vererbung ist eines der vier Grundprinzipien der objektorientierten Programmierung.  
Sie ermöglicht es, eine neue Klasse auf Basis einer bestehenden Klasse aufzubauen und deren Eigenschaften und Methoden zu übernehmen, zu erweitern oder anzupassen.

---

## Was ist Vererbung?

Wenn eine Klasse **B** von einer Klasse **A** erbt, dann:
- besitzt **B** alle Attribute und Methoden von **A** automatisch
- kann **B** eigene Attribute und Methoden hinzufügen
- kann **B** geerbte Methoden überschreiben (`@Override`)

Die erbende Klasse heißt **Unterklasse** (engl. subclass / child class).  
Die Klasse, von der geerbt wird, heißt **Oberklasse** (engl. superclass / parent class).

```
         Fahrzeug          ← Oberklasse
        /        \
    Auto       Fahrrad     ← Unterklassen
```

---

## Schlüsselwort: extends

```java
public class Auto extends Fahrzeug {
    // Auto erbt alles von Fahrzeug
}
```

---

## Vollständiges Beispiel: BMI-Rechner mit Vererbung

### Oberklasse: Person

```java
public class Person {
    // Attribute der Oberklasse
    protected String vorname;      // protected: sichtbar für Unterklassen
    protected String nachname;
    protected int    alter;

    // Konstruktor
    public Person(String vorname, String nachname, int alter) {
        this.vorname  = vorname;
        this.nachname = nachname;
        this.alter    = alter;
    }

    // Methoden
    public String getFullName() {
        return vorname + " " + nachname;
    }

    public int getAlter() {
        return alter;
    }

    @Override
    public String toString() {
        return "Person{" + vorname + " " + nachname + ", Alter: " + alter + "}";
    }
}
```

### Unterklasse: Patient (erbt von Person)

```java
public class Patient extends Person {
    // Eigene zusätzliche Attribute
    private String krankenkasse;
    private String versicherungsNummer;

    // Konstruktor: ruft den Konstruktor der Oberklasse mit super() auf
    public Patient(String vorname, String nachname, int alter,
                   String krankenkasse, String versicherungsNummer) {
        super(vorname, nachname, alter);  // ← super() MUSS als erstes stehen!
        this.krankenkasse        = krankenkasse;
        this.versicherungsNummer = versicherungsNummer;
    }

    // Eigene neue Methode
    public String getKrankenkasse() {
        return krankenkasse;
    }

    // Geerbte Methode überschreiben (@Override)
    @Override
    public String toString() {
        // super.toString() ruft die Methode der Oberklasse auf
        return super.toString() + ", KK: " + krankenkasse;
    }
}
```

### Unterklasse: Sportler (erbt von Person)

```java
public class Sportler extends Person {
    private String sportart;
    private int    trainingsStundenProWoche;

    public Sportler(String vorname, String nachname, int alter,
                    String sportart, int stunden) {
        super(vorname, nachname, alter);
        this.sportart                 = sportart;
        this.trainingsStundenProWoche = stunden;
    }

    // Eigene Methode: BMI-Grenzwerte für Sportler sind anders
    public String getBmiKategorieSportler(double bmi) {
        // Sportler haben mehr Muskelmasse → andere Grenzwerte
        if (bmi < 17.5) return "Untergewicht";
        if (bmi < 27.0) return "Normalgewicht (Sportler)";
        if (bmi < 32.0) return "Muskulös";
        return "Übergewicht";
    }

    @Override
    public String toString() {
        return super.toString() + ", Sport: " + sportart
               + " (" + trainingsStundenProWoche + "h/Woche)";
    }
}
```

---

## Vererbungshierarchie und UML

```
┌──────────────────────┐
│       Person         │   Oberklasse
├──────────────────────┤
│ # vorname            │   # = protected
│ # nachname           │
│ # alter              │
├──────────────────────┤
│ + getFullName()      │
│ + getAlter()         │
│ + toString()         │
└──────────┬───────────┘
           │  extends (Vererbung)
     ┌─────┴──────┐
     ▼            ▼
┌──────────┐  ┌────────────┐
│ Patient  │  │  Sportler  │   Unterklassen
├──────────┤  ├────────────┤
│-krankenkasse│  │-sportart   │
├──────────┤  ├────────────┤
│+toString()│  │+toString() │
└──────────┘  └────────────┘
```

---

## super – die Oberklasse ansprechen

`super` hat zwei Verwendungszwecke:

### 1. Konstruktor der Oberklasse aufrufen

```java
public Patient(String vorname, String nachname, int alter, String kk) {
    super(vorname, nachname, alter);  // Muss erste Zeile im Konstruktor sein!
    this.krankenkasse = kk;
}
```

### 2. Methode der Oberklasse aufrufen

```java
@Override
public String toString() {
    return super.toString() + ", KK: " + krankenkasse;
    // super.toString() → gibt "Person{Max Muster, Alter: 30}" zurück
    // Ergebnis: "Person{Max Muster, Alter: 30}, KK: AOK"
}
```

---

## @Override – Methoden überschreiben

Wenn eine Unterklasse eine geerbte Methode neu implementiert, wird `@Override` verwendet:

```java
public class Sportler extends Person {

    @Override
    public String toString() {
        // Diese Methode ersetzt toString() aus Person
        return "Sportler: " + getFullName() + " (" + sportart + ")";
    }
}
```

### Warum `@Override` schreiben?

- Schützt vor Tippfehlern: Der Compiler meldet einen Fehler, wenn die Methode **nicht** in der Oberklasse existiert
- Macht den Code lesbarer: Lesende wissen sofort, dass hier eine Methode überschrieben wird

```java
// Mit Tippfehler – OHNE @Override kein Compilerfehler, ABER Methode wird nicht überschrieben!
public String toStirng() { ... }   // toBug – wird nie aufgerufen!

// MIT @Override – Compiler meldet sofort: "Method does not override"
@Override
public String toStirng() { ... }   // → Compilerfehler: Tippfehler entdeckt!
```

---

## Sichtbarkeit (Zugriffsmodifikatoren bei Vererbung)

| Modifikator | Gleiche Klasse | Unterklasse | Anderes Package |
|-------------|:--------------:|:-----------:|:---------------:|
| `private`   | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ❌ |
| (kein)      | ✅ | ✅ (gleich. Pkg) | ❌ |
| `public`    | ✅ | ✅ | ✅ |

**Empfehlung:** Attribute in der Oberklasse `protected` (nicht `private`) wenn Unterklassen sie direkt nutzen sollen.

---

## Polymorphie durch Vererbung

Da `Patient` und `Sportler` beide von `Person` erben, kann eine Variable vom Typ `Person` auch ein `Patient`- oder `Sportler`-Objekt speichern:

```java
// Polymorphie: Oberklassenreferenz hält Unterklassenobjekt
Person p1 = new Patient("Anna", "Schmidt", 28, "AOK", "12345");
Person p2 = new Sportler("Tom", "Müller", 35, "Fußball", 10);

// toString() der jeweiligen Unterklasse wird aufgerufen (dynamisches Binden)
System.out.println(p1.toString()); // → Patient-toString()
System.out.println(p2.toString()); // → Sportler-toString()

// Liste mit verschiedenen Person-Typen
List<Person> personen = new ArrayList<>();
personen.add(new Patient("Eva", "Huber", 42, "TK", "67890"));
personen.add(new Sportler("Jan", "Bauer", 25, "Tennis", 8));

for (Person p : personen) {
    System.out.println(p.getFullName()); // funktioniert für alle!
}
```

---

## instanceof – Typ prüfen

Falls du zur Laufzeit wissen musst, welcher Typ ein Objekt wirklich ist:

```java
Person p = new Patient("Max", "Muster", 30, "AOK", "123");

if (p instanceof Patient) {
    Patient patient = (Patient) p;    // Cast: Person → Patient
    System.out.println(patient.getKrankenkasse());
}

// Java 16+: Pattern Matching (kürzer)
if (p instanceof Patient patient) {
    System.out.println(patient.getKrankenkasse());
}
```

---

## Abstrakte Klassen

Eine **abstrakte Klasse** kann nicht direkt instanziiert werden. Sie dient als Vorlage für Unterklassen und kann abstrakte Methoden definieren, die jede Unterklasse implementieren **muss**:

```java
// Abstrakte Oberklasse
public abstract class Fahrzeug {
    protected String modell;
    protected int    baujahr;

    public Fahrzeug(String modell, int baujahr) {
        this.modell  = modell;
        this.baujahr = baujahr;
    }

    // Normale Methode (geerbt)
    public String getModell() {
        return modell;
    }

    // Abstrakte Methode: MUSS in Unterklassen implementiert werden
    public abstract double getMaxGeschwindigkeit();
}

// Unterklasse MUSS abstrakte Methode implementieren
public class Auto extends Fahrzeug {
    private double maxGeschwindigkeit;

    public Auto(String modell, int baujahr, double maxV) {
        super(modell, baujahr);
        this.maxGeschwindigkeit = maxV;
    }

    @Override
    public double getMaxGeschwindigkeit() {    // Pflicht!
        return maxGeschwindigkeit;
    }
}

// FEHLER: Fahrzeug f = new Fahrzeug(...); → nicht erlaubt, da abstrakt!
// KORREKT:
Fahrzeug f = new Auto("VW Golf", 2022, 200.0);
```

---

## Wann Vererbung einsetzen?

| Situation | Empfehlung |
|-----------|-----------|
| Klasse B **ist eine Art von** Klasse A | ✅ Vererbung (`extends`) |
| Klasse B **hat eine** Klasse A | ❌ Assoziation (Attribut) |
| Gemeinsames Verhalten, aber unterschiedliche Details | ✅ Abstrakte Oberklasse |
| Gemeinsame Schnittstelle ohne Code | ✅ Interface (`implements`) |

**Merksatz:** Vererbung = „**ist-ein**"-Beziehung, Assoziation = „**hat-ein**"-Beziehung.

```
Patient   IST EINE  Person          → extends Person     ✅
BmiManager HAT EINEN Bmirechner     → Attribut           ✅
Patient   HAT EINE  Krankenakte     → Attribut/Assoziation ✅
```

---

## Häufige Fehler

### Fehler 1: super() wird vergessen

```java
// FALSCH – Compiler-Fehler wenn Oberklasse keinen Leer-Konstruktor hat
public Patient(String vorname, String kk) {
    this.krankenkasse = kk;  // ERROR: kein super()-Aufruf
}

// RICHTIG
public Patient(String vorname, String nachname, int alter, String kk) {
    super(vorname, nachname, alter);   // ← erste Anweisung!
    this.krankenkasse = kk;
}
```

### Fehler 2: private statt protected

```java
// FALSCH – Unterklasse kann nicht auf 'name' zugreifen
public class Person {
    private String name;          // private: nur in Person sichtbar
}
public class Patient extends Person {
    public String info() {
        return name;              // FEHLER: name ist privat!
    }
}

// RICHTIG
public class Person {
    protected String name;        // protected: auch in Unterklassen sichtbar
}
```

### Fehler 3: @Override vergessen

```java
// FALSCH – kein Override, Methode wird nie aufgerufen
public class Patient extends Person {
    public String tostring() { ... }   // Tippfehler! kein Override!
}

// RICHTIG – Compiler prüft den Methodennamen
public class Patient extends Person {
    @Override
    public String toString() { ... }   // korrekt
}
```

---

## Zusammenfassung

| Konzept | Schlüsselwort | Bedeutung |
|---------|--------------|-----------|
| Erben | `extends` | Unterklasse übernimmt alles von Oberklasse |
| Oberklasse aufrufen | `super()` | Konstruktor/Methode der Oberklasse |
| Methode neu implementieren | `@Override` | Geerbte Methode ersetzen |
| Abstrakte Klasse | `abstract` | Kann nicht instanziiert werden, erzwingt Implementation |
| Typprüfung | `instanceof` | Prüft, welchen Typ ein Objekt hat |
| Sichtbarkeit | `protected` | In Unterklassen zugänglich, außen verborgen |
