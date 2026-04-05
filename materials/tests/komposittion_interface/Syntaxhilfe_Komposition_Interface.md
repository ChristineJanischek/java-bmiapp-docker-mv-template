# Syntaxhilfe: Komposition & Interface

**Ziel:** Komposition = "hat"-Beziehung, Interface = Vertrag/Faehigkeit.

---

## Interface definieren

```java
public interface Buchbar {
    double berechnePreis(int naechte);
    void buche(String kunde);
}
```

## Interface implementieren

```java
public class Hotel implements Buchbar {
    private String name;
    private double preisProNacht;

    public Hotel(String name, double preisProNacht) {
        this.name = name;
        this.preisProNacht = preisProNacht;
    }

    @Override
    public double berechnePreis(int naechte) {
        return preisProNacht * naechte;
    }

    @Override
    public void buche(String kunde) {
        System.out.println("Buchung fuer " + kunde);
    }
}
```

---

## Komposition (hat-Beziehung)

```java
public class Buchung {
    private String buchungsNr;
    private Buchbar objekt;  // Komposition

    public Buchung(String buchungsNr, Buchbar objekt) {
        this.buchungsNr = buchungsNr;
        this.objekt = objekt;
    }
}
```

---

## Polymorphe Liste

```java
List<Buchbar> angebote = new ArrayList<>();
angebote.add(new Hotel("CityHotel", 95));
angebote.add(new Ferienwohnung("Zentral", 120));
```

---

## instanceof (Interface)

```java
if (objekt instanceof Buchbar) {
    Buchbar b = (Buchbar) objekt;
    b.buche("Max");
}
```

---

## Tipps

- Interfaces enthalten keine Konstruktoren.
- Komposition = Objekt wird in anderem Objekt verwaltet.
- Interface = gemeinsame Methoden fuer verschiedene Klassen.
