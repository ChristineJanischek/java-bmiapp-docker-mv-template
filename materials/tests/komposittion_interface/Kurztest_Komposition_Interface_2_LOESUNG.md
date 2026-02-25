# Kurztest: Komposition & Interface - Buchungssystem - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: Interface für Buchbarkeit (4 Punkte)

### Aufgabenstellung

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

### Musterlösung

**a) Interface Definition (2 Punkte):**

```java
public interface Buchbar {
    double berechnePreis(int anzahlNaechte);
    boolean istVerfuegbar(LocalDate von, LocalDate bis);
}
```

**b) Erklärung (2 Punkte):**

```
Ein Interface erlaubt es, verschiedene Unterkunftstypen ohne gemeinsame
Oberklasse zu vereinen. Hotels, Ferienwohnungen und Hostels können
unterschiedlich implementiert sein, teilen aber die Buchbarkeit.
```

### Bewertung (4 Punkte)

- **2 Punkte:** Interface mit beiden Methoden korrekt
- **2 Punkte:** Erklärung sinnvoll (Flexibilität ohne Vererbung)

---

## Aufgabe 2: Komposition - Hotel mit Ausstattung (5 Punkte)

### Aufgabenstellung

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

### Musterlösung

```java
@Override
public double berechnePreis(int anzahlNaechte) {
    double zimmerPreis = preisProNacht * anzahlNaechte;
    double fruehstueckPreis = fruehstueck.getPreis() * anzahlNaechte;
    double parkplatzPreis = parkplatz.getPreis() * anzahlNaechte;
    return zimmerPreis + fruehstueckPreis + parkplatzPreis;
}
```

**Erklärung (1 Punkt):**
```
Komposition ist sinnvoll, weil Services austauschbar sind (z. B. verschiedene
Frühstücksarten) und ein Hotel aus verschiedenen Komponenten besteht.
```

### Bewertung (5 Punkte)

- **4 Punkte:** Preisberechnung korrekt (Zimmer + Services)
- **1 Punkt:** Erklärung sinnvoll

---

## Aufgabe 3: Interface für Stornierbarkeit (5 Punkte)

### Aufgabenstellung

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

### Musterlösung

**Interface (2 Punkte):**

```java
public interface Stornierbar {
    double berechneStorniertungsgebuehr(int tageVorAnreise);
}
```

**Implementierung (3 Punkte):**

```java
public class Ferienwohnung implements Buchbar, Stornierbar {
    private double preis;
    
    @Override
    public double berechneStorniertungsgebuehr(int tageVorAnreise) {
        if (tageVorAnreise < 7) {
            return preis * 0.5;  // 50% Gebühr
        }
        return 0.0;  // Kostenlos stornierbar
    }
}
```

### Bewertung (5 Punkte)

- **2 Punkte:** Interface korrekt
- **3 Punkte:** Implementierung mit Bedingung korrekt

---

## Aufgabe 4: Code-Analyse - Polymorphe Buchungsliste (6 Punkte)

### Aufgabenstellung

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

### Musterlösungen

**a) Preisberechnung (4 Punkte):**

```
Hotel (3 Nächte): (120 + 15 + 10) * 3 = 145 * 3 = 435€
Ferienwohnung (3 Nächte): 80 * 3 = 240€
Hostel (3 Nächte): 30 * 3 = 90€

Gesamtkosten: 435 + 240 + 90 = 765€
```

**Bewertung:**
- **1,5 Punkte:** Hotel korrekt (mit Services)
- **1 Punkt:** Ferienwohnung korrekt
- **1 Punkt:** Hostel korrekt
- **0,5 Punkte:** Summe korrekt

---

**b) Erklärung Polymorphie (2 Punkte):**

```
Alle Unterkünfte implementieren das Interface Buchbar.
Dadurch können sie polymorph in einer List<Buchbar> gespeichert werden.
Jede ruft ihre eigene berechnePreis()-Implementierung auf.
```

**Bewertung:**
- **2 Punkte:** Interface + Polymorphie erklärt
- **1 Punkt:** Grundidee erkannt

---

## Aufgabe 5: Komposition - Buchung mit mehreren Unterkünften (3 Punkte)

### Aufgabenstellung

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

### Musterlösung

```java
public class Buchung {
    private List<Buchbar> unterkünfte;
    private int anzahlNaechte;
    
    public Buchung(int anzahlNaechte) {
        this.anzahlNaechte = anzahlNaechte;
        this.unterkünfte = new ArrayList<>();
    }
    
    public void addUnterkunft(Buchbar u) {
        unterkünfte.add(u);
    }
    
    public double getGesamtpreis() {
        double summe = 0;
        for (Buchbar u : unterkünfte) {
            summe += u.berechnePreis(anzahlNaechte);
        }
        return summe;
    }
}
```

### Bewertung (3 Punkte)

- **1 Punkt:** Konstruktor mit Initialisierung
- **0,5 Punkte:** addUnterkunft korrekt
- **1,5 Punkte:** getGesamtpreis mit Schleife korrekt

---

## Aufgabe 6: Fehlersuche - Typprüfung (2 Punkte)

### Aufgabenstellung

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

### Musterlösung

**a) Fehler (1 Punkt):**

```
Buchbar hat keine Methode istStornierbar().
Nur Klassen, die Stornierbar implementieren, haben diese Fähigkeit.
```

**b) Korrekte Version (1 Punkt):**

```java
for (Buchbar u : unterkünfte) {
    if (u instanceof Stornierbar) {
        Stornierbar s = (Stornierbar) u;
        System.out.println("Stornierungsgebühr: " + s.berechneStorniertungsgebuehr(7));
    }
}
```

oder mit Pattern Matching (Java 16+):

```java
for (Buchbar u : unterkünfte) {
    if (u instanceof Stornierbar s) {
        System.out.println("Stornierungsgebühr: " + s.berechneStorniertungsgebuehr(7));
    }
}
```

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte |
|---------|------------|
| 1. Interface für Buchbarkeit | 4 |
| 2. Komposition - Hotel mit Services | 5 |
| 3. Interface Stornierbarkeit | 5 |
| 4. Polymorphe Buchungsliste | 6 |
| 5. Buchung mit mehreren Unterkünften | 3 |
| 6. Fehlersuche instanceof | 2 |
| **GESAMT** | **25** |

---

## Bewertungsskala

| Prozent | Note |
|---------|------|
| 90-100% | 1 |
| 80-89% | 2 |
| 70-79% | 3 |
| 60-69% | 4 |
| 50-59% | 5 |
| < 50% | 6 |

**Notenschlüssel:**
- **ab 23 Punkte:** Note 1
- **ab 20 Punkte:** Note 2
- **ab 18 Punkte:** Note 3
- **ab 15 Punkte:** Note 4
- **ab 13 Punkte:** Note 5
- **unter 13 Punkte:** Note 6

---

## Häufige Fehler

### Fehler 1: Interface mit Implementierung
```java
public interface Buchbar {
    double berechnePreis(int n) { return 0; }  // ← Falsch
}
```
**Handling:** 1 Punkt Abzug in Aufgabe 1

### Fehler 2: Services-Preise vergessen
```java
return preisProNacht * anzahlNaechte;  // ← Frühstück/Parkplatz fehlt
```
**Handling:** 2 Punkte Abzug in Aufgabe 2

### Fehler 3: Keine instanceof-Prüfung
```java
Stornierbar s = (Stornierbar) u;  // ← Ohne Prüfung = ClassCastException!
```
**Handling:** 0 Punkte in Aufgabe 6

---

## Lernziele

✓ **Interface als Vertrag:** Gemeinsame Fähigkeiten definieren  
✓ **Komposition in Geschäftslogik:** Hotel mit Services  
✓ **Multiple Interfaces:** Buchbar + Stornierbar  
✓ **Polymorphie mit Interfaces:** Unterschiedliche Unterkünfte einheitlich verarbeiten  
✓ **Typprüfung:** instanceof bei Interfaces  

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Kontext:** Buchungssystem für Unterkünfte  
**Schwierigkeitsgrad:** Mittel  
**Praktische Relevanz:** Sehr hoch (Booking.com, Airbnb, etc.)
