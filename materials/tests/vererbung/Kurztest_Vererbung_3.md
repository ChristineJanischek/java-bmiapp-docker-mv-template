# Kurztest: Vererbung in ERP-Systemen - Artikelverwaltung

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Produkttypen in einem Lagerverwaltungssystem (4 Punkte)

**Thema:** Physische und digitale Produkte

In einem ERP-System gibt es verschiedene Artikeltypen. Zeichne die Vererbungshierarchie auf:

Basisklasse: **Artikel**  
Unterklassen: **PhysischerArtikel**, **DigitalesProdukt**

```java
public class Artikel {
    protected String artikelNummer;
    protected String bezeichnung;
    protected double preis;
    
    public Artikel(String artikelNummer, String bezeichnung, double preis) {
        this.artikelNummer = artikelNummer;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
    }
}

// Unterklasse 1:
public class PhysischerArtikel __________________ {
    protected double gewicht;  // in kg
    protected int lagerbestand;
    
    // Konstruktor
}

// Unterklasse 2:
public class DigitalesProdukt __________________ {
    protected String downloadLink;
    protected int maxDownloads;
    
    // Konstruktor
}
```

Erkläre, warum zwei unterschiedliche Unterklassen nötig sind:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: Konstruktoren für verschiedene Artikeltypen (5 Punkte)

**Thema:** Unterschiedliche Attribute, ein super()-Aufruf

Vervollständige die Konstruktoren:

```java
public class PhysischerArtikel extends Artikel {
    protected double gewicht;
    protected int lagerbestand;
    
    public PhysischerArtikel(String artikelNummer, String bezeichnung, double preis,
                             double gewicht, int lagerbestand) {
        // super() Aufruf:
        
        // Eigene Attribute setzen:
        
    }
}

public class DigitalesProdukt extends Artikel {
    protected String downloadLink;
    protected int maxDownloads;
    
    public DigitalesProdukt(String artikelNummer, String bezeichnung, double preis,
                            String downloadLink, int maxDownloads) {
        // super() Aufruf:
        
        // Eigene Attribute setzen:
        
    }
}
```

**Bonus (+1 Punkt):** Was ist der Unterschied bei der Initialisierung zwischen beiden Unterklassen?

___________________________________________________________________________

---

## Aufgabe 3: Versandkosten und überschriebene Methoden (5 Punkte)

**Thema:** Unterschiedliche Geschäftslogik je Produkttyp

In der Basisklasse gibt es:

```java
public double getVersandkosten() {
    return 0;  // Basisimplementierung
}
```

a) Implementiere getVersandkosten() für beide Unterklassen:

```java
// PhysischerArtikel (Versandkosten: 5€ + 0,50€ pro kg):


// DigitalesProdukt (keine Versandkosten):

```

b) Schreibe eine Methode `getGesamtpreis()`, die Preis + Versandkosten zurückgibt:

```java
// Methode für Basisklasse Artikel:

```

---

## Aufgabe 4: Code-Analyse - Bestellabwicklung (6 Punkte)

**Thema:** Polymorphie bei Rechnungsstellung

Gegeben ist folgender Code:

```java
List<Artikel> bestellung = new ArrayList<>();

bestellung.add(new Artikel("A001", "USB-Kabel", 10.00));
bestellung.add(new PhysischerArtikel("A002", "Laptop", 800.00, 2.5, 5));
bestellung.add(new DigitalesProdukt("A003", "E-Book", 15.00, "https://...", 999));

double gesamtBetrag = 0;
for (Artikel artikel : bestellung) {
    gesamtBetrag += artikel.getGesamtpreis();
}

System.out.println("Rechnungsbetrag: " + gesamtBetrag + "€");
```

**Fragen:**

a) Berechne die Gesamtpreise für jeden Artikel (PhysischerArtikel mit Versandkosten!):

Antwort:  
A001 (Artikel): _____________  
A002 (PhysischerArtikel): _____________ (+ 5€ + 2,5*0,50€ Versand)  
A003 (DigitalesProdukt): _____________  
**Gesamtrechnungsbetrag: _____________**

b) Welche Rolle spielt Polymorphie hier? Erkläre in 1-2 Sätzen:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Lagerverwaltung und Verfügbarkeitsprüfung (3 Punkte)

**Thema:** Unterschiedliche Verfügbarkeitsprüfung nach Produkttyp

Schreibe eine Methode oder Schleife, die prüft, ob ein PhysischerArtikel verfügbar ist (Lagerbestand > 0):

```java
// Methode oder Schleife:




```

Können DigitalProdukte "nicht verfügbar" sein? Begründe kurz:

___________________________________________________________________________

---

## Aufgabe 6: Fehlersuche - Automatische Verwaltung (2 Punkte)

**Thema:** Häufige Fehler bei polymorphen Operationen

Gegeben ist folgender fehlerhafter Code:

```java
public void reduziertLagerbestand(Artikel artikel, int menge) {
    artikel.lagerbestand -= menge;  // ← FEHLER
}
```

Erkläre das Problem und schreibe eine korrigierte Version:

**Problem:**

___________________________________________________________________________

___________________________________________________________________________

**Korrigierte Version:**

```java
// Besser so:

```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Produkttypen-Hierarchie | 4 | |
| 2. Unterschiedliche Konstruktoren | 5 | |
| 3. Überschriebene Geschäftslogik | 5 | |
| 4. Polymorphie in Rechnungsstellung | 6 | |
| 5. Verfügbarkeitsprüfung | 3 | |
| 6. Fehlersuche & Korrektur | 2 | |
| **Gesamt** | **25** | |
