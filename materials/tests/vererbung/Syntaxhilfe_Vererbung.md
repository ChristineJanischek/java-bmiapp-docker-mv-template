# Syntaxhilfe: Vererbung

**Ziel:** Gemeinsame Eigenschaften und Verhalten in Oberklasse, Spezialisierung in Unterklasse.

---

## Grundstruktur

```java
public class Fahrzeug {
    protected String marke;

    public Fahrzeug(String marke) {
        this.marke = marke;
    }

    public void starte() {
        System.out.println("Fahrzeug startet");
    }
}

public class Auto extends Fahrzeug {
    private int tueren;

    public Auto(String marke, int tueren) {
        super(marke);  // Aufruf Oberklassen-Konstruktor
        this.tueren = tueren;
    }

    @Override
    public void starte() {
        System.out.println("Auto startet");
    }
}
```

---

## Polymorphie

```java
Fahrzeug f = new Auto("VW", 4);
f.starte();  // ruft Auto.starte() auf
```

---

## instanceof + Casting

```java
if (f instanceof Auto) {
    Auto a = (Auto) f;
    // Zugriff auf Auto-spezifische Methoden
}
```

---

## Abstrakte Klasse (optional)

```java
public abstract class Mitarbeiter {
    protected String name;

    public Mitarbeiter(String name) {
        this.name = name;
    }

    public abstract double berechneGehalt();
}
```

---

## Tipps

- "extends" fuer Klassenvererbung.
- "super(...)" ruft den Konstruktor der Oberklasse.
- Ueberschreiben mit @Override.
- Gemeinsame Logik gehoert in die Oberklasse.
