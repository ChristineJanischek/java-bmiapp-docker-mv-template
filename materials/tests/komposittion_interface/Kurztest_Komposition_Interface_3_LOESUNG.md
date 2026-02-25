# Kurztest: Komposition & Interface - Versicherungssystem - LOESUNG

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Interface für Versicherungsleistungen (4 Punkte)

**Thema:** Gemeinsame Fähigkeiten verschiedener Versicherungen

### Musterlösung:

a) **Interface-Definition (3 Punkte):**

```java
public interface Versicherung {
    double berechneBeitrag();
    void meldeSchaden(double schadenshoehe);
}
```

**Bewertung:**
- Interface korrekt deklariert (1 Punkt)
- `berechneBeitrag()` mit richtigem Rückgabetyp (1 Punkt)
- `meldeSchaden(double)` mit Parameter (1 Punkt)

b) **Zuordnung (1 Punkt - nur bei allen richtig!):**

Eine `Krankenversicherung` ist eine `Versicherung`. **(I)** ← Interface-Implementierung  
Eine `Police` hat mehrere `Versicherungen`. **(K)** ← Komposition / hat-Beziehung  
Eine `Versicherung` kann schadensfrei sein (zusätzliche Fähigkeit). **(I)** ← Zusätzliches Interface

---

## Aufgabe 2: Komposition - Police mit mehreren Versicherungen (5 Punkte)

**Thema:** Police besteht aus mehreren Versicherungsprodukten

### Musterlösung:

```java
public class Police {
    private String policeNummer;
    private List<Versicherung> versicherungen;
    private Kunde kunde;  // Komposition
    
    public Police(String policeNummer, Kunde kunde) {
        this.policeNummer = policeNummer;
        this.kunde = kunde;
        // Liste initialisieren:
        this.versicherungen = new ArrayList<>();  // 1 Punkt
    }
    
    public void addVersicherung(Versicherung v) {
        // Versicherung hinzufügen:
        versicherungen.add(v);  // 1 Punkt
    }
    
    public double getGesamtbeitrag() {
        // Summe aller Beiträge berechnen:
        double summe = 0;  // 1 Punkt
        for (Versicherung v : versicherungen) {
            summe += v.berechneBeitrag();  // 1 Punkt
        }
        return summe;
    }
}
```

**Erklärung Komposition (1 Punkt):**

"Police nutzt Komposition, weil sie Kunde und Versicherungen *enthält* (hat-Beziehung). Die Police verwaltet ihre Bestandteile und kann verschiedene Versicherungstypen kombinieren, ohne selbst eine Versicherung zu sein."

**Alternative Erklärung (auch korrekt):**
- "Hat-eine-Beziehung statt ist-eine-Beziehung"
- "Police setzt sich aus mehreren Objekten zusammen"
- "Aggregation von Versicherungen in einer Datenstruktur"

**Bewertung:**
- Liste initialisiert (1 Punkt)
- addVersicherung korrekt (1 Punkt)
- Schleife oder Stream (1 Punkt)
- berechneBeitrag() aufgerufen (1 Punkt)
- Erklärung Komposition (1 Punkt)

---

## Aufgabe 3: Interface für Schadensfreiheit (5 Punkte)

**Thema:** Bonus für schadenfreie Jahre

### Musterlösung:

```java
// Interface Schadensfrei (2 Punkte):
public interface Schadensfrei {
    int getSchadensfreieJahre();
    double getRabatt();
}
```

**Implementierung Autoversicherung (3 Punkte):**

```java
public class Autoversicherung implements Versicherung, Schadensfrei {
    private double grundbeitrag;
    private int schadensfreieJahre;
    
    @Override
    public double berechneBeitrag() {
        // Berechne Beitrag mit Rabatt (pro Jahr 5% Rabatt, max. 50%):
        double rabatt = schadensfreieJahre * 0.05;  // 1 Punkt
        if (rabatt > 0.5) {
            rabatt = 0.5;  // Maximal 50% Rabatt  // 1 Punkt
        }
        return grundbeitrag * (1 - rabatt);  // 1 Punkt
    }
    
    @Override
    public int getSchadensfreieJahre() {
        return schadensfreieJahre;
    }
    
    @Override
    public double getRabatt() {
        return Math.min(schadensfreieJahre * 0.05, 0.5);
    }
}
```

**Alternative Lösungen:**
```java
// Mit Math.min (eleganter):
double rabatt = Math.min(schadensfreieJahre * 0.05, 0.5);
return grundbeitrag * (1 - rabatt);

// Mit Ternary-Operator:
double rabatt = (schadensfreieJahre * 0.05 > 0.5) ? 0.5 : schadensfreieJahre * 0.05;
```

**Bewertung:**
- Interface mit Methoden (2 Punkte)
- Rabatt berechnet (1 Punkt)
- Maximum von 50% beachtet (1 Punkt)
- Rückgabe mit Rabatt (1 Punkt)

---

## Aufgabe 4: Code-Analyse - Polymorphe Versicherungsverwaltung (6 Punkte)

**Thema:** Verschiedene Versicherungen in einer Police

### Musterlösung:

a) **Gesamtbeitrag berechnen (3 Punkte):**

Krankenversicherung: **250,00 €** (kein Rabatt)  
Autoversicherung (180€ - 15% Rabatt): **153,00 €** (180 * 0.85)  
Haftpflicht: **80,00 €** (kein Rabatt)  
**Gesamtbeitrag: 483,00 €**

**Berechnung Autoversicherung:**
- 3 schadensfreie Jahre × 5% = 15% Rabatt
- 180 € × (1 - 0.15) = 180 € × 0.85 = 153 €

**Bewertung:**
- Krankenversicherung korrekt (1 Punkt)
- Autoversicherung mit Rabatt korrekt (1 Punkt)
- Gesamtsumme korrekt (1 Punkt)

b) **instanceof Erklärung (3 Punkte):**

"instanceof Schadensfrei ist nur bei Autoversicherung erfolgreich, weil nur diese Klasse das Interface Schadensfrei implementiert. Krankenversicherung und Haftpflicht implementieren nur das Interface Versicherung, daher gibt instanceof Schadensfrei bei diesen false zurück."

**Kernpunkte für volle Punktzahl:**
- instanceof prüft Typ zur Laufzeit (1 Punkt)
- Nur Autoversicherung implementiert Schadensfrei (1 Punkt)
- Andere Versicherungen haben diese Fähigkeit nicht (1 Punkt)

**Alternative Formulierung:**
"instanceof prüft, ob ein Objekt einen bestimmten Typ implementiert. Da nur Autoversicherung das Interface Schadensfrei implementiert, ist die Prüfung nur bei dieser erfolgreich."

---

## Aufgabe 5: Komposition - Schadenshistorie (3 Punkte)

**Thema:** Versicherung enthält Liste von Schäden

### Musterlösung:

```java
public class Versicherungspolice {
    private List<Schaden> schaeden;
    
    public boolean istSchadensfrei(int jahre) {
        // Prüfe, ob in den letzten 'jahre' Jahren ein Schaden gemeldet wurde:
        LocalDate grenzDatum = LocalDate.now().minusYears(jahre);  // 1 Punkt
        
        for (Schaden schaden : schaeden) {
            if (schaden.getDatum().isAfter(grenzDatum)) {  // 1 Punkt
                return false;  // Schaden in Zeitraum gefunden
            }
        }
        return true;  // Kein Schaden gefunden  // 1 Punkt
    }
}
```

**Alternative Lösungen:**

Mit Stream API:
```java
public boolean istSchadensfrei(int jahre) {
    LocalDate grenzDatum = LocalDate.now().minusYears(jahre);
    return schaeden.stream()
        .noneMatch(s -> s.getDatum().isAfter(grenzDatum));
}
```

Mit Jahr-Vergleich:
```java
public boolean istSchadensfrei(int jahre) {
    int aktuellesJahr = LocalDate.now().getYear();
    int grenzJahr = aktuellesJahr - jahre;
    
    for (Schaden s : schaeden) {
        if (s.getJahr() >= grenzJahr) {
            return false;
        }
    }
    return true;
}
```

**Bewertung:**
- Zeitraum berechnet (1 Punkt)
- Schäden überprüft (1 Punkt)
- Korrektes Rückgabeverhalten (1 Punkt)

**Teilpunkte:**
- Logik grundsätzlich richtig, aber kleine Fehler: 2 Punkte
- Ansatz erkennbar: 1 Punkt

---

## Aufgabe 6: Fehlersuche - Interface-Implementierung (2 Punkte)

**Thema:** Fehlende Methodenimplementierung

### Musterlösung:

a) **Fehlerbeschreibung (1 Punkt):**

"Die Klasse Krankenversicherung implementiert das Interface Versicherung nicht vollständig. Es fehlt die Implementierung der Methode meldeSchaden(double). Wenn ein Interface implementiert wird, müssen ALLE Methoden implementiert werden, sonst gibt es einen Compiler-Fehler."

**Kernpunkte:**
- Methode fehlt (0.5 Punkte)
- Interface-Vertrag nicht erfüllt (0.5 Punkte)

**Compiler-Fehler:**
```
Krankenversicherung is not abstract and does not override abstract method meldeSchaden(double) in Versicherung
```

b) **Fehlende Methode (1 Punkt):**

```java
@Override
public void meldeSchaden(double schadenshoehe) {
    // Beispiel-Implementierung:
    schaeden.add(new Schaden(schadenshoehe, LocalDate.now()));
}
```

**Minimale Lösung (auch korrekt für 1 Punkt):**
```java
@Override
public void meldeSchaden(double schadenshoehe) {
    // Schaden erfassen
}
```

**Bewertung:**
- Signatur korrekt (@Override optional) (0.5 Punkte)
- void Rückgabetyp und Parameter (0.5 Punkte)

---

## Bewertungsschema

| Punkte | Note |
|--------|------|
| 25-23  | 1 (sehr gut) |
| 22-20  | 2 (gut) |
| 19-17  | 3 (befriedigend) |
| 16-13  | 4 (ausreichend) |
| 12-0   | 5 (mangelhaft) |

---

## Häufige Fehler

### Interface vs. Klasse
- ❌ "Interface mit Konstruktor" → Interfaces haben keine Konstruktoren
- ❌ "Interface mit private Methoden" → Alle Interface-Methoden sind implizit public

### Komposition
- ❌ "Vererbung statt Komposition" → Police ist keine Versicherung!
- ✓ Police *hat* Versicherungen (Komposition)

### instanceof
- ❌ "instanceof ist nur für Klassen" → funktioniert auch mit Interfaces!
- ✓ `v instanceof Schadensfrei` prüft Interface-Implementierung

### Rabattberechnung
- ❌ Rabatt unbegrenzt: 10 Jahre = 50% Rabatt ✗ (maximal 50%!)
- ✓ Mit Math.min() oder if-Abfrage auf 50% begrenzen

---

## Didaktischer Hinweis

Dieser Test kombiniert:
1. **Interfaces:** Gemeinsame Fähigkeiten (Versicherung, Schadensfrei)
2. **Komposition:** Police besteht aus mehreren Teilen
3. **Polymorphie:** Verschiedene Versicherungstypen in einer Liste
4. **instanceof:** Typ-Prüfung zur Laufzeit

**Praxisbezug:** Versicherungssysteme sind realistische ERP-Beispiele mit:
- Verschiedenen Produkttypen (Kranken-, Auto-, Haftpflicht)
- Zusätzlichen Features (Schadensfreiheitsrabatt)
- Aggregation mehrerer Produkte in einer Police
- Unterschiedlichen Berechnungslogiken

**Typische Fehlerquellen für Schüler:**
1. Interface-Vertrag nicht vollständig implementiert
2. Komposition und Vererbung verwechselt
3. Rabattberechnung ohne Maximum
4. instanceof mit Casting vergessen
5. Liste nicht initialisiert (NullPointerException)
