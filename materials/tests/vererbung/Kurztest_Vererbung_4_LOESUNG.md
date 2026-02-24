# Kurztest: Vererbung in ERP-Systemen - Zahlungssysteme - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: Zahlungsmethoden in einem E-Commerce-System (4 Punkte)

### Musterlösung

```java
public class Bankueberweisung extends Zahlung { }
public class Kreditkarte extends Zahlung { }
public class PayPal extends Zahlung { }
```

**Erklärung (2 Punkte):**

```
Alle Zahlungsarten teilen grundlegende Eigenschaften:
Zahlungs-ID, Betrag, Status. Jede Zahlungsart hat aber unterschiedliche Gebührenmodelle,
die durch Überschreiben von getGebuehren() implementiert werden.
Dadurch kann Code wiederverwendet und polymorphe Verarbeitung ermöglicht werden.
```

### Bewertung (4 Punkte)

**Hierarchie (2 Punkte):**
- ✓ Alle drei `extends Zahlung` → 2 Punkte
- ✗ Falsche Vererbung → 0 Punkte

**Erklärung (2 Punkte):**
- **2 Punkte:** Gemeinsame Attribute + unterschiedliche Gebühren + Polymorphie
- **1 Punkt:** Grundidee erkannt
- **0 Punkte:** Falsch

---

## Aufgabe 2: Konstruktoren mit unterschiedlichen Parametern (5 Punkte)

### Musterlösung

**Banküberweisung:**
```java
public Bankueberweisung(String zahlungsId, double betrag, String iban, String bic) {
    super(zahlungsId, betrag);
    this.iban = iban;
    this.bic = bic;
}
```

**Kreditkarte:**
```java
public Kreditkarte(String zahlungsId, double betrag, String kartennummer, String gueltigBis) {
    super(zahlungsId, betrag);
    this.kartennummer = kartennummer.substring(kartennummer.length() - 4);
    this.gueltigBis = gueltigBis;
}
```

**PayPal:**
```java
public PayPal(String zahlungsId, double betrag, String email) {
    super(zahlungsId, betrag);
    this.email = email;
}
```

### Bewertung (5 Punkte)

**Jeder Konstruktor (1,5 Punkte):**
- ✓ super(zahlungsId, betrag) korrekt → 1 Punkt
- ✓ Eigene Attribute gesetzt → 0,5 Punkte

**Masking bei Kreditkarte (0,5 Punkte):**
- ✓ kartennummer wird gekürzt → +0,5 Punkte Bonus

---

## Aufgabe 3: Gebührenberechnung durch Überschreiben (5 Punkte)

### Musterlösung

**a) getGebuehren() (3 Punkte):**

```java
// Bankueberweisung (1,5%, max. 3€):
@Override
public double getGebuehren() {
    double gebuehr = betrag * 0.015;
    return Math.min(gebuehr, 3.0);  // Maximum 3€
}

// Kreditkarte (2,5%):
@Override
public double getGebuehren() {
    return betrag * 0.025;
}

// PayPal (2,9% + 0,30€):
@Override
public double getGebuehren() {
    return (betrag * 0.029) + 0.30;
}
```

**Bewertung (3 Punkte):**
- **1 Punkt:** Banküberweisung mit Math.min() oder max()-Logik
- **1 Punkt:** Kreditkarte mit 2,5% korrekt
- **1 Punkt:** PayPal mit 2,9% + 0,30€ korrekt

---

**b) getZahlbetrag() (2 Punkte):**

```java
public double getZahlbetrag() {
    return betrag + getGebuehren();
}
```

**Bewertung (2 Punkte):**
- **2 Punkte:** Betrag + Gebühren korrekt
- **1 Punkt:** Logik richtig, aber kleine Fehler

---

## Aufgabe 4: Code-Analyse - Rechnungsabschluss (6 Punkte)

### Musterlösungen

**a) Gebührenberechnung (4 Punkte):**

```
Z001 (Banküberweisung 1000€): 1000 * 0.015 = 15€, max. 3€ → 3€
Z002 (Kreditkarte 500€): 500 * 0.025 = 12,50€
Z003 (PayPal 200€): (200 * 0.029) + 0,30 = 5,80 + 0,30 = 6,10€

Gesamtgebühren: 3 + 12,50 + 6,10 = 21,60€
```

**Bewertung (4 Punkte):**
- **1 Punkt:** Z001 korrekt (mit max. 3€-Regel)
- **1 Punkt:** Z002 korrekt
- **1 Punkt:** Z003 korrekt (mit Festbetrag)
- **1 Punkt:** Summe korrekt

**Häufige Fehler:**
- Banküberweisung: 15€ statt 3€ (max.-Regel übersehen) → -1 Punkt
- PayPal: 5,80€ statt 6,10€ (Festbetrag vergessen) → -1 Punkt

---

**b) Wert der Polymorphie (2 Punkte):**

**Musterlösung:**
```
Polymorphie ermöglicht es, verschiedene Zahlungsarten in einer List zu speichern
und einheitlich zu verarbeiten, ohne den Typ zu prüfen.
Die getGebuehren()-Methode wird automatisch korrekt aufgerufen.
Neue Zahlungsarten können hinzugefügt werden, ohne bestehenden Code zu ändern (Open-Closed-Prinzip).
```

**Bewertung:**
- **2 Punkte:** Polymorphie-Wert erklärt + Erweiterbarkeit
- **1 Punkt:** Grundidee erkannt

---

## Aufgabe 5: Zahlungsstatus und Transaktionslogik (3 Punkte)

### Musterlösung

```java
public void verarbeiteZahlung(Zahlung zahlung) {
    if (zahlung instanceof Bankueberweisung) {
        System.out.println("Banküberweisung wird verarbeitet... (2-3 Tage)");
        zahlung.setStatus("VERARBEITUNG");
    } else if (zahlung instanceof Kreditkarte) {
        System.out.println("Kreditkarte wird validiert...");
        zahlung.setStatus("VALIDIERUNG");
    } else if (zahlung instanceof PayPal) {
        System.out.println("PayPal-Zahlungsanforderung wird gesendet...");
        zahlung.setStatus("AUSSTEHENDE_BESTÄTIGUNG");
    }
}
```

oder eleganter mit Strategy-Pattern:

```java
public void verarbeiteZahlung(Zahlung zahlung) {
    zahlung.verarbeite();  // Jede Klasse implementiert ihre Logik
}
```

### Bewertung (3 Punkte)

- **3 Punkte:** instanceof mit unterschiedlicher Behandlung + Status-Update
- **2 Punkte:** Grundkonzept erkannt, aber unvollständig
- **1 Punkt:** Ansatz vorhanden

---

## Aufgabe 6: Fehlersuche - Sicherheit (2 Punkte)

### Musterlösung

**Sicherheitsproblem (1 Punkt):**

```
Die vollständige Kartennummer wird ausgegeben/geloggt.
Das ist ein Sicherheitsrisiko und verstößt gegen Zahlungsstandards (PCI-DSS).
Kartendaten sollten niemals vollständig sichtbar sein - höchstens die letzten 4 Ziffern.
```

**Sichere Methode (1 Punkt):**

```java
public String getMaskedKartennummer() {
    String masked = "****";
    if (kartennummer != null && kartennummer.length() >= 4) {
        masked += kartennummer.substring(kartennummer.length() - 4);
    }
    return masked;  // Ausgabe: **** 9999
}
```

oder:

```java
public void zeigeKartendaten(Kreditkarte karte) {
    System.out.println("Kartennummer: ****" + karte.getLastFourDigits());
}
```

### Bewertung (2 Punkte)

**Sicherheitsproblem:**
- ✓ PCI-DSS oder Zahlungsstandards erwähnt → 1 Punkt
- ✓ "Daten sollten nicht sichtbar sein" → 1 Punkt

**Sichere Implementierung:**
- ✓ Masking (****) + letzte 4 Ziffern → 1 Punkt

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte |
|---------|------------|
| 1. Zahlungsmethoden-Hierarchie | 4 |
| 2. Unterschiedliche Konstruktoren | 5 |
| 3. Gebührenberechnung | 5 |
| 4. Polymorphie in Finanzabrechnung | 6 |
| 5. Transaktionslogik | 3 |
| 6. Fehlersuche & Sicherheit | 2 |
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

### Fehler 1: Banküberweisung-Gebühren falsch
```java
return betrag * 0.015;  // ← Vergisst das 3€-Maximum!
```
**Handling:** -1 Punkt in Aufgabe 3a/4a

### Fehler 2: PayPal-Festbetrag vergessen
```java
return betrag * 0.029;  // ← 0,30€ vergessen!
```
**Handling:** -1 Punkt in Aufgabe 3a/4a

### Fehler 3: Kartennummer nicht maskieren
```java
this.kartennummer = kartennummer;  // ← Volle Nummer gespeichert!
```
**Handling:** -0,5 Punkte in Aufgabe 2, -1 Punkt in Aufgabe 6

### Fehler 4: Keine Typprüfung bei spezialisierter Logik
```java
public void verarbeiteZahlung(Zahlung zahlung) {
    // Keine instanceof-Prüfung
}
```
**Handling:** 1 Punkt Abzug in Aufgabe 5

---

## Lernziele

✓ **Mehrere Unterklassen:** Parallele Spezialisierung  
✓ **Gebührenlogik:** Unterschiedliche mathematische Berechnungen  
✓ **Polymorphe Zusammenfassung:** Einheitliche Verarbeitung verschiedener Typen  
✓ **Finanzielle Genauigkeit:** Math.min(), Dezimalzahlen  
✓ **Sicherheit:** Datenmasking, PCI-DSS-Konzepte  
✓ **Praktisches ERP-Szenario:** Zahlungsabwicklung in echten Systemen  

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Kontext:** ERP-System - E-Commerce Zahlungsabwicklung  
**Schwierigkeitsgrad:** Mittelschwer bis schwer  
**Praktische Relevanz:** Sehr hoch (kritisches Banking/FinTech-Szenario)  
**Sicherheitsfokus:** Ja (Kartendaten, PCI-DSS)
