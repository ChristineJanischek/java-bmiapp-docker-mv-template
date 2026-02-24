# Kurztest: Vererbung in ERP-Systemen - Artikelverwaltung - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: Produkttypen in einem Lagerverwaltungssystem (4 Punkte)

### Musterlösung

```java
public class PhysischerArtikel extends Artikel {
    protected double gewicht;
    protected int lagerbestand;
}

public class DigitalesProdukt extends Artikel {
    protected String downloadLink;
    protected int maxDownloads;
}
```

**Erklärung (2 Punkte):**

```
Physische und digitale Produkte haben unterschiedliche Attribute und 
unterschiedliche Geschäftslogik (Versand, Lagerverwaltung, Downloads).
Durch separate Unterklassen können diese Unterschiede abgebildet werden, 
während gemeinsame Attribute (Artikelnummer, Preis) in der Basisklasse bleiben.
```

### Bewertung (4 Punkte)

**Hierarchie (2 Punkte):**
- ✓ Beide `extends Artikel` → 2 Punkte
- ✗ Falsche Vererbungsrichtung → 0 Punkte

**Erklärung (2 Punkte):**
- **2 Punkte:** Unterschiedliche Logik + separate Attribute erwähnt
- **1 Punkt:** Grundidee erkannt
- **0 Punkte:** Falsch

---

## Aufgabe 2: Konstruktoren für verschiedene Artikeltypen (5 Punkte)

### Musterlösung

**PhysischerArtikel:**
```java
public PhysischerArtikel(String artikelNummer, String bezeichnung, double preis,
                         double gewicht, int lagerbestand) {
    super(artikelNummer, bezeichnung, preis);
    this.gewicht = gewicht;
    this.lagerbestand = lagerbestand;
}
```

**DigitalesProdukt:**
```java
public DigitalesProdukt(String artikelNummer, String bezeichnung, double preis,
                        String downloadLink, int maxDownloads) {
    super(artikelNummer, bezeichnung, preis);
    this.downloadLink = downloadLink;
    this.maxDownloads = maxDownloads;
}
```

### Bewertung (4 Punkte)

**Konstruktor 1 (2 Punkte):**
- ✓ super(artikelNummer, bezeichnung, preis) → 1 Punkt
- ✓ Attribute setzen → 1 Punkt

**Konstruktor 2 (2 Punkte):**
- ✓ super(artikelNummer, bezeichnung, preis) → 1 Punkt
- ✓ Attribute setzen → 1 Punkt

---

**Bonus-Frage (+1 Punkt):**

**Musterlösung:**
```
Physische Artikel benötigen Gewicht und Lagerbestand.
Digitale Produkte benötigen Download-Link und maximale Downloads.
Der super()-Aufruf ist identisch, aber die Eigenattribute unterscheiden sich.
```

**Bewertung:**
- ✓ Unterschiedliche Attribute erkannt → +1 Punkt

---

## Aufgabe 3: Versandkosten und überschriebene Methoden (5 Punkte)

### Musterlösung

**a) getVersandkosten() (3 Punkte):**

```java
// PhysischerArtikel:
@Override
public double getVersandkosten() {
    return 5.00 + (gewicht * 0.50);
}

// DigitalesProdukt:
@Override
public double getVersandkosten() {
    return 0.0;
}
```

**Bewertung (3 Punkte):**
- **1,5 Punkte:** PhysischerArtikel-Berechnung korrekt (5€ + 0,50€/kg)
- **1,5 Punkte:** DigitalesProdukt korrekt (0€)
- **0,5 Punkte Abzug:** @Override fehlt

---

**b) getGesamtpreis() (2 Punkte):**

```java
public double getGesamtpreis() {
    return preis + getVersandkosten();
}
```

**Bewertung (2 Punkte):**
- **2 Punkte:** Preis + Versandkosten korrekt
- **1 Punkt:** Logik richtig, aber kleine Fehler

---

## Aufgabe 4: Code-Analyse - Bestellabwicklung (6 Punkte)

### Musterlösungen

**a) Preisberechnung (4 Punkte):**

```
A001 (Artikel): 10.00€ (keine Versandkosten)
A002 (PhysischerArtikel): 800.00 + 5.00 + (2.5 * 0.50) = 800.00 + 5.00 + 1.25 = 806.25€
A003 (DigitalesProdukt): 15.00€ (keine Versandkosten)

Gesamtrechnungsbetrag: 10.00 + 806.25 + 15.00 = 831.25€
```

**Bewertung (4 Punkte):**
- **1 Punkt:** A001 korrekt
- **2 Punkte:** A002 mit Versandkosten-Berechnung korrekt
- **1 Punkt:** A003 korrekt
- **könnte 0,5 Punkte Abzug pro Rechenfehler)

---

**b) Rolle der Polymorphie (2 Punkte):**

**Musterlösung:**
```
Durch Polymorphie können alle Artikel in einer Liste gespeichert werden.
Jeder Artikel ruft seine eigene getGesamtpreis()-Methode auf - PhysischerArtikel 
addiert Versandkosten, DigitalesProdukt nicht. Die for-Schleife muss nicht 
zwischen Typen unterscheiden.
```

**Bewertung:**
- **2 Punkte:** Polymorphie erklärt + Vereinfachung der Schleife
- **1 Punkt:** Grundidee erkannt

---

## Aufgabe 5: Lagerverwaltung und Verfügbarkeitsprüfung (3 Punkte)

### Musterlösung

**Methode/Schleife (2 Punkte):**

```java
for (Artikel artikel : bestellung) {
    if (artikel instanceof PhysischerArtikel) {
        PhysischerArtikel physical = (PhysischerArtikel) artikel;
        if (physical.getLagerbestand() > 0) {
            System.out.println(physical.getBezeichnung() + " verfügbar");
        }
    }
}
```

oder (als Methode):

```java
public boolean istVerfügbar(PhysischerArtikel artikel) {
    return artikel.getLagerbestand() > 0;
}
```

**Bewertung (2 Punkte):**
- **2 Punkte:** instanceof + Typecast + Lagerbestand-Prüfung
- **1 Punkt:** Grundkonzept erkannt

---

**Frage DigitalProdukte (1 Punkt):**

**Musterlösung:**
```
Nein, DigitaleProdukte können (in diesem Modell) nicht "nicht verfügbar" sein.
Sie haben keine Lagerbeschränkung - eine unbegrenzte Anzahl kann heruntergeladen werden
(oder maxDownloads prüfen ist alternative Logik).
```

**Bewertung:**
- ✓ Richtige Begründung → 1 Punkt

---

## Aufgabe 6: Fehlersuche - Automatische Verwaltung (2 Punkte)

### Musterlösung

**Problem (1 Punkt):**

```
Das Attribut 'lagerbestand' ist protected und nur in PhysischerArtikel vorhanden.
Der Parameter artikel ist vom Typ Artikel - man kann nicht auf 
artkel.lagerbestand zugreifen. Außerdem: nicht alle Artikel haben einen Lagerbestand.
```

**Korrigierte Version (1 Punkt):**

```java
public void reduziertLagerbestand(PhysischerArtikel artikel, int menge) {
    if (artikel.getLagerbestand() >= menge) {
        artikel.setLagerbestand(artikel.getLagerbestand() - menge);
    }
}
```

oder mit Typprüfung:

```java
public void reduziertLagerbestand(Artikel artikel, int menge) {
    if (artikel instanceof PhysischerArtikel) {
        PhysischerArtikel physical = (PhysischerArtikel) artikel;
        physical.setLagerbestand(physical.getLagerbestand() - menge);
    }
}
```

**Bewertung:**
- **1 Punkt:** Problem erklärt (Typprobleme + nicht alle Artikel)
- **1 Punkt:** Korrigierte Implementierung mit richtigem Typ oder instanceof

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte |
|---------|------------|
| 1. Produkttypen-Hierarchie | 4 |
| 2. Unterschiedliche Konstruktoren | 5 (+1 Bonus) |
| 3. Überschriebene Geschäftslogik | 5 |
| 4. Polymorphie in Rechnungsstellung | 6 |
| 5. Verfügbarkeitsprüfung | 3 |
| 6. Fehlersuche & Korrektur | 2 |
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

## Häufige Schülerfehler

### Fehler 1: Lagerbestand in Basisklasse
```java
public class Artikel {
    protected int lagerbestand;  // ← Nicht alle Artikel haben Lagerbestand!
}
```
**Handling:** Schlechtes Design - 1 Punkt Abzug in Aufgabe 1

### Fehler 2: Versandkosten-Berechnung
```java
return 5 + gewicht * 0.5;  // ← Korrekt, aber oft vergessen
return 5;  // ← Gewicht-Anteil vergessen
```
**Handling:** 1 Punkt Abzug in Aufgabe 3a

### Fehler 3: Falsche Preisberechnung
```
A002: 800 + 6.25 = 806.25  // ← Falsch: 5.00 + (2.5*0.5) ≠ 6.25
Korrekt: 5.00 + 1.25 = 6.25, also 806.25
```
**Handling:** Rechenfehler → 0,5 Punkte abziehen

### Fehler 4: Direkt auf lagerbestand zugreifen
```java
public void reduziertLagerbestand(Artikel artikel, int menge) {
    artikel.lagerbestand -= menge;  // ← Kompilierungsfehler!
}
```
**Handling:** 0 Punkte in Aufgabe 6 (kritischer Fehler)

---

## Lernziele

✓ **Parallele Vererbung:** Mehrere Unterklassen einer Basis  
✓ **Unterschiedliche Attribute:** Spezialisierte Eigenschaften  
✓ **Überschriebene Logik:** verschiedene Versandkosten  
✓ **Polymorphie in der Praxis:** Rechnungsabwicklung  
✓ **Typprüfung:** instanceof für spezialisierte Operationen  
✓ **Geschäftslogik-Design:** Welche Attribute gehören wohin?  

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Kontext:** ERP-System - Lagerverwaltung & Rechnungsstellung  
**Schwierigkeitsgrad:** Mittelschwer bis schwer  
**Praktische Relevanz:** Sehr hoch (reale e-commerce/ERP-Szenarien)
