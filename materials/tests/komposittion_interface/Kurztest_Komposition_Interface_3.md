# Kurztest: Komposition & Interface - Versicherungssystem

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Interface für Versicherungsleistungen (4 Punkte)

**Thema:** Gemeinsame Fähigkeiten verschiedener Versicherungen

In einem Versicherungssystem gibt es verschiedene Versicherungstypen (Krankenversicherung, Autoversicherung, Haftpflicht). Alle können Beiträge berechnen und Schäden melden.

a) Definiere ein Interface `Versicherung` mit den Methoden:
- `berechneBeitrag()` → gibt den monatlichen Beitrag zurück
- `meldeSchaden(double schadenshoehe)` → meldet einen Schaden

```java
// Interface hier schreiben:




```

b) Ordne zu (K = Komposition, V = Vererbung, I = Interface):

Eine `Krankenversicherung` ist eine `Versicherung`. ( )  
Eine `Police` hat mehrere `Versicherungen`. ( )  
Eine `Versicherung` kann schadensfrei sein (zusätzliche Fähigkeit). ( )

---

## Aufgabe 2: Komposition - Police mit mehreren Versicherungen (5 Punkte)

**Thema:** Police besteht aus mehreren Versicherungsprodukten

Eine Versicherungspolice kann mehrere Versicherungen enthalten (z.B. Kranken + Haftpflicht).

```java
public class Police {
    private String policeNummer;
    private List<Versicherung> versicherungen;
    private Kunde kunde;  // Komposition
    
    public Police(String policeNummer, Kunde kunde) {
        this.policeNummer = policeNummer;
        this.kunde = kunde;
        // Liste initialisieren:
        
    }
    
    public void addVersicherung(Versicherung v) {
        // Versicherung hinzufügen:
        
    }
    
    public double getGesamtbeitrag() {
        // Summe aller Beiträge berechnen:
        
        
        
    }
}
```

Vervollständige die Methoden und erkläre, warum Police Komposition nutzt:

___________________________________________________________________________

---

## Aufgabe 3: Interface für Schadensfreiheit (5 Punkte)

**Thema:** Bonus für schadenfreie Jahre

Nicht alle Versicherungen bieten Schadensfreiheitsrabatt. Definiere ein Interface `Schadensfrei`:

```java
// Interface Schadensfrei:



```

Implementiere eine Klasse `Autoversicherung`, die `Schadensfrei` unterstützt:

```java
public class Autoversicherung implements Versicherung, Schadensfrei {
    private double grundbeitrag;
    private int schadensfreieJahre;
    
    @Override
    public double berechneBeitrag() {
        // Berechne Beitrag mit Rabatt (pro Jahr 5% Rabatt, max. 50%):
        
        
        
    }
}
```

---

## Aufgabe 4: Code-Analyse - Polymorphe Versicherungsverwaltung (6 Punkte)

**Thema:** Verschiedene Versicherungen in einer Police

Gegeben ist folgender Code:

```java
Police police = new Police("P123", new Kunde("Max Müller"));

police.addVersicherung(new Krankenversicherung(250.00));
police.addVersicherung(new Autoversicherung(180.00, 3));  // 3 schadensfreie Jahre
police.addVersicherung(new Haftpflicht(80.00));

System.out.println("Monatlicher Gesamtbeitrag: " + police.getGesamtbeitrag() + "€");

// Schadensfreiheitsrabatte anzeigen:
for (Versicherung v : police.getVersicherungen()) {
    if (v instanceof Schadensfrei) {
        Schadensfrei sf = (Schadensfrei) v;
        System.out.println("Schadensfreie Jahre: " + sf.getSchadensfreieJahre());
    }
}
```

**Fragen:**

a) Berechne den Gesamtbeitrag (Autoversicherung mit 5% Rabatt pro Jahr):

Krankenversicherung: _____________  
Autoversicherung (180€ - 15% Rabatt): _____________  
Haftpflicht: _____________  
**Gesamtbeitrag: _____________**

b) Warum wird instanceof nur bei einigen Versicherungen erfolgreich sein?

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Komposition - Schadenshistorie (3 Punkte)

**Thema:** Versicherung enthält Liste von Schäden

Schreibe eine Methode, die überprüft, ob eine Versicherung in den letzten 3 Jahren schadensfrei war:

```java
public class Versicherungspolice {
    private List<Schaden> schaeden;
    
    public boolean istSchadensfrei(int jahre) {
        // Prüfe, ob in den letzten 'jahre' Jahren ein Schaden gemeldet wurde:
        
        
        
    }
}
```

---

## Aufgabe 6: Fehlersuche - Interface-Implementierung (2 Punkte)

**Thema:** Fehlende Methodenimplementierung

Gegeben ist folgender fehlerhafter Code:

```java
public class Krankenversicherung implements Versicherung {
    private double beitrag;
    
    @Override
    public double berechneBeitrag() {
        return beitrag;
    }
    
    // meldeSchaden() fehlt!  ← FEHLER
}
```

a) Was ist der Fehler und warum kompiliert der Code nicht?

___________________________________________________________________________

___________________________________________________________________________

b) Ergänze die fehlende Methode:

```java
// Fehlende Methode:


```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Interface Versicherungsleistungen | 4 | |
| 2. Komposition - Police | 5 | |
| 3. Interface Schadensfreiheit | 5 | |
| 4. Polymorphe Versicherungsverwaltung | 6 | |
| 5. Schadenshistorie | 3 | |
| 6. Fehlersuche Implementierung | 2 | |
| **Gesamt** | **25** | |
