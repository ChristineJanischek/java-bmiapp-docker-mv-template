# Kurztest: Komposition & Interface - Buchungssystem für Unterkünfte

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Interface für Buchbarkeit (4 Punkte)

**Thema:** Gemeinsame Fähigkeiten definieren

In einem Buchungssystem gibt es verschiedene Unterkünfte (Hotel, Ferienwohnung, Hostel). Alle sind buchbar.

a) Definiere ein Interface `Buchbar` mit den Methoden:
- `berechnePreis(int anzahlNaechte)` → gibt den Gesamtpreis zurück
- `istVerfuegbar(LocalDate von, LocalDate bis)` → prüft Verfügbarkeit

```java
// Interface hier schreiben:




```

b) Erkläre, warum ein Interface hier besser ist als eine Oberklasse `Unterkunft`:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: Komposition - Hotel mit Ausstattung (5 Punkte)

**Thema:** Hotel besteht aus Services

Ein Hotel hat verschiedene Services: Frühstück, Parkplatz, Pool.

```java
public class Hotel implements Buchbar {
    private String name;
    private double preisProNacht;
    private Fruehstueck fruehstueck;  // Komposition
    private Parkplatz parkplatz;      // Komposition
    
    public Hotel(String name, double preisProNacht) {
        this.name = name;
        this.preisProNacht = preisProNacht;
        this.fruehstueck = new Fruehstueck(15.00);  // 15€ pro Tag
        this.parkplatz = new Parkplatz(10.00);      // 10€ pro Tag
    }
    
    @Override
    public double berechnePreis(int anzahlNaechte) {
        // Berechne Gesamtpreis (Zimmer + Frühstück + Parkplatz):
        
        
        
    }
}
```

Vervollständige die Methode `berechnePreis()`. Erkläre, warum Komposition hier sinnvoll ist:

___________________________________________________________________________

---

## Aufgabe 3: Interface für Stornierbarkeit (5 Punkte)

**Thema:** Verschiedene Stornierungsbedingungen

Nicht alle Unterkünfte sind gleich stornierbar. Definiere ein Interface `Stornierbar`:

```java
// Interface Stornierbar:



```

Implementiere `Stornierbar` für eine Klasse `Ferienwohnung` mit 50% Gebühr:

```java
public class Ferienwohnung implements Buchbar, Stornierbar {
    private double preis;
    
    @Override
    public double berechneStorniertungsgebuehr(int tageVorAnreise) {
        // Weniger als 7 Tage: 50% Gebühr, sonst kostenlos
        
        
    }
}
```

---

## Aufgabe 4: Code-Analyse - Polymorphe Buchungsliste (6 Punkte)

**Thema:** Verschiedene Unterkünfte in einer Liste

Gegeben ist folgender Code:

```java
List<Buchbar> unterkünfte = new ArrayList<>();

unterkünfte.add(new Hotel("Maritim", 120));
unterkünfte.add(new Ferienwohnung("Bergblick", 80));
unterkünfte.add(new Hostel("Backpacker", 30));

int naechte = 3;
double gesamtkosten = 0;

for (Buchbar u : unterkünfte) {
    gesamtkosten += u.berechnePreis(naechte);
}

System.out.println("Gesamtkosten für " + naechte + " Nächte: " + gesamtkosten + "€");
```

**Fragen:**

a) Berechne die Gesamtkosten (Hotel: 120€/Nacht + 25€ Services, Ferienwohnung: 80€/Nacht, Hostel: 30€/Nacht):

Hotel (3 Nächte): _____________  
Ferienwohnung (3 Nächte): _____________  
Hostel (3 Nächte): _____________  
**Gesamtkosten: _____________**

b) Warum können verschiedene Unterkunftstypen in einer Liste gespeichert werden?

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Komposition - Buchung mit mehreren Unterkünften (3 Punkte)

**Thema:** Buchung besteht aus mehreren Positionen

Schreibe eine Klasse `Buchung`, die mehrere `Buchbar`-Objekte verwaltet:

```java
public class Buchung {
    private List<Buchbar> unterkünfte;
    private int anzahlNaechte;
    
    public Buchung(int anzahlNaechte) {
        // Konstruktor:
        
    }
    
    public void addUnterkunft(Buchbar u) {
        // Unterkunft hinzufügen:
        
    }
    
    public double getGesamtpreis() {
        // Gesamtpreis aller Unterkünfte berechnen:
        
        
    }
}
```

---

## Aufgabe 6: Fehlersuche - Typprüfung (2 Punkte)

**Thema:** Instanceof bei Interfaces

Gegeben ist folgender Code:

```java
for (Buchbar u : unterkünfte) {
    if (u.istStornierbar()) {  // ← FEHLER
        System.out.println("Kann storniert werden");
    }
}
```

a) Was ist der Fehler?

___________________________________________________________________________

b) Schreibe die korrekte Version mit `instanceof`:

```java
// Korrekt:


```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Interface für Buchbarkeit | 4 | |
| 2. Komposition - Hotel mit Services | 5 | |
| 3. Interface Stornierbarkeit | 5 | |
| 4. Polymorphe Buchungsliste | 6 | |
| 5. Buchung mit mehreren Unterkünften | 3 | |
| 6. Fehlersuche instanceof | 2 | |
| **Gesamt** | **25** | |
