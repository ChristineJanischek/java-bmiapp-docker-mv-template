# Kurztest: Vererbung in ERP-Systemen - Zahlungssysteme

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Zahlungsmethoden in einem E-Commerce-System (4 Punkte)

**Thema:** Unterschiedliche Zahlungsarten mit gemeinsamen Attributes

In einem E-Commerce-System gibt es eine Zahlungs-Hierarchie mit unterschiedlichen Gebührenmodellen:

```java
public class Zahlung {
    protected String zahlungsId;
    protected double betrag;
    protected String status;  // "AUSSTEHEND", "BEZAHLT", "ABGELEHNT"
    
    public Zahlung(String zahlungsId, double betrag) {
        this.zahlungsId = zahlungsId;
        this.betrag = betrag;
        this.status = "AUSSTEHEND";
    }
}

// Unterklasse 1: Banküberweisung (1,5% Gebühren, max. 3€)
public class Bankueberweisung __________________ {
    protected String iban;
    protected String bic;
}

// Unterklasse 2: Kreditkarte (2,5% Gebühren)
public class Kreditkarte __________________ {
    protected String kartennummer;
    protected String gueltigBis;  // "MM/YY"
}

// Unterklasse 3: PayPal (2,9% + 0,30€)
public class PayPal __________________ {
    protected String email;
}
```

Erkläre, waarum alle drei Klassen von Zahlung erben, obwohl sie unterschiedliche Gebühren haben:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: Konstruktoren mit unterschiedlichen Parametern (5 Punkte)

**Thema:** super() mit variierenden Unterklassen-Attributen

Vervollständige die Konstruktoren:

```java
public class Bankueberweisung extends Zahlung {
    protected String iban;
    protected String bic;
    
    public Bankueberweisung(String zahlungsId, double betrag, String iban, String bic) {
        // super() Aufruf:
        
        // Eigene Attribute:
        
    }
}

public class Kreditkarte extends Zahlung {
    protected String kartennummer;
    protected String gueltigBis;
    
    public Kreditkarte(String zahlungsId, double betrag, String kartennummer, String gueltigBis) {
        // super() Aufruf:
        
        // Eigene Attribute (mit Validierung):
        this.kartennummer = kartennummer.substring(kartennummer.length() - 4);
        
    }
}

public class PayPal extends Zahlung {
    protected String email;
    
    public PayPal(String zahlungsId, double betrag, String email) {
        // super() Aufruf:
        
        // Eigene Attribute:
        
    }
}
```

---

## Aufgabe 3: Gebührenberechnung durch Überschreiben (5 Punkte)

**Thema:** Polymorphe Gebührenlogik

In der Basisklasse:

```java
public double getGebuehren() {
    return 0.0;  // Standardgebühren
}
```

a) Implementiere getGebuehren() für alle drei Zahlungsarten:

```java
// Bankueberweisung (1,5%, max. 3€):


// Kreditkarte (2,5%):


// PayPal (2,9% + 0,30€):

```

b) Schreibe eine Methode `getZahlbetrag()`, die Betrag + Gebühren zurückgibt:

```java
// Methode:

```

---

## Aufgabe 4: Code-Analyse - Rechnungsabschluss (6 Punkte)

**Thema:** Polymorphie bei der Finanzabrechnung

Gegeben ist folgender Code:

```java
List<Zahlung> zahlungen = new ArrayList<>();

zahlungen.add(new Bankueberweisung("Z001", 1000, "DE21500...", "DRESDEFF"));
zahlungen.add(new Kreditkarte("Z002", 500, "4532123456789999", "12/25"));
zahlungen.add(new PayPal("Z003", 200, "user@example.com"));

double gesamtGebühren = 0;
for (Zahlung z : zahlungen) {
    gesamtGebühren += z.getGebuehren();
}

System.out.println("Gesamte Transaktionsgebühren: " + gesamtGebühren + "€");
```

**Fragen:**

a) Berechne die Gebühren für jede Zahlungsart:

Antwort:  
Z001 (Banküberweisung 1000€): _____________ (1,5%, max. 3€)  
Z002 (Kreditkarte 500€): _____________ (2,5%)  
Z003 (PayPal 200€): _____________ (2,9% + 0,30€)  
**Gesamtgebühren: _____________**

b) Warum ist Polymorphie hier besonders wertvoll für ein Zahlungssystem?

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Zahlungsstatus und Transaktionslogik (3 Punkte)

**Thema:** Typgebundene Operationen

Schreibe eine Methode `verarbeiteZahlung()`, die eine Zahlung verarbeitet und den Status aktualisiert:

```java
public void verarbeiteZahlung(Zahlung zahlung) {
    // Prüfe Zahlungsart und verarbeite entsprechend
    // (z.B. Banküberweisung braucht längeres Verarbeiten)
    
    
    
}
```

---

## Aufgabe 6: Fehlersuche - Sicherheit (2 Punkte)

**Thema:** Häufige Sicherheitsfehler bei Zahlungsdaten

Gegeben ist folgender fehlerhafter Code:

```java
public void zeigeKartendaten(Kreditkarte karte) {
    System.out.println("Kartennummer: " + karte.kartennummer);  // ← SICHERHEITSFEHLER
}
```

Erkläre das Sicherheitsproblem und schreibe eine sichere Version:

**Sicherheitsproblem:**

___________________________________________________________________________

___________________________________________________________________________

**Sichere Methode:**

```java
// Besser so (maskieren):

```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Zahlungsmethoden-Hierarchie | 4 | |
| 2. Unterschiedliche Konstruktoren | 5 | |
| 3. Gebührenberechnung (Überschreiben) | 5 | |
| 4. Polymorphie in Finanzabrechnung | 6 | |
| 5. Transaktionslogik | 3 | |
| 6. Fehlersuche & Sicherheit | 2 | |
| **Gesamt** | **25** | |
