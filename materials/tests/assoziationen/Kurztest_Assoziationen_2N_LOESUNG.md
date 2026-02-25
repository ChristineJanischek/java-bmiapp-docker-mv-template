# Kurztest: Assoziationen & 1:1-Beziehungen - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: Datenstrukturen im Vergleich (5 Punkte)

**Thema:** Auswahl der richtigen Datenstruktur

### Musterlösung

| Eigenschaft | Array | ArrayList | Einzelner Objektverweis |
|-------------|-------|-----------|------------------------|
| Dynamisch erweiterbar | **Nein** | **Ja** | **Nein** |
| Feste Größe nach Erstellung | **Ja** | **Nein** | **Nein** |
| Geeignet für genau 1 zugeordnetes Objekt | **Nein** | **Nein** | **Ja** |
| Benötigt Import (`java.util`) | **Nein** | **Ja** | **Nein** |
| Direkter Zugriff über `[index]` | **Ja** | **Nein** | **Nein** |

**Erklärung (Musterlösung):**
```
Bei einer 1:1-Beziehung ist einem Objekt genau ein anderes Objekt zugeordnet.
Ein einfacher Objektverweis (Attribut vom Typ der anderen Klasse) reicht aus –
ArrayList oder Array wären überdimensioniert und würden eine nicht vorhandene
Menge implizieren.
```

### Bewertung (5 Punkte)

**Tabelle (4 Punkte):** 1 Punkt pro korrekte Zeile (5 Zeilen = max. 4 Punkte, d. h. 1 Fehler erlaubt)
- **4 Punkte:** 5 Zeilen korrekt
- **3 Punkte:** 4 Zeilen korrekt
- **2 Punkte:** 3 Zeilen korrekt
- **1 Punkt:** 2 Zeilen korrekt
- **0 Punkte:** ≤ 1 Zeile korrekt

**Begründung (1 Punkt):**
- ✓ Sinnvolle Erklärung (1:1 → ein Verweis reicht) → 1 Punkt
- ~ Ansatz erkennbar, aber unvollständig → 0,5 Punkte
- ✗ Falsch oder nicht beantwortet → 0 Punkte

---

## Aufgabe 2: Methodenzuordnung (5 Punkte)

**Thema:** Methoden für einfache und verkettete Assoziationen

### Musterlösung (Zuordnung)

| Methode | Richtiger Buchstabe | Bedeutung |
|---------|----------------------|-----------|
| `getArzt()` | **(b)** | Gibt das referenzierte Objekt zurück (Getter) |
| `setArzt(Arzt a)` | **(a)** | Setzt die Referenz auf ein anderes Objekt (Setter) |
| `person.getArzt() != null` | **(c)** | Prüft, ob die Referenz gesetzt ist (nicht `null`) |
| `person.setArzt(null)` | **(d)** | Entfernt die Verbindung durch Setzen auf `null` |
| `person.getArzt().getName()` | **(e)** | Gibt den Namen des referenzierten Objekts aus |

### Bewertung (5 Punkte)

- **5 Punkte:** Alle 5 Zuordnungen korrekt
- **4 Punkte:** 4 von 5 richtig
- **3 Punkte:** 3 von 5 richtig
- **2 Punkte:** 2 von 5 richtig
- **1 Punkt:** 1 von 5 richtig
- **0 Punkte:** Keine oder alle falsch

---

## Aufgabe 3: Code-Analyse (1:1-Relation) (8 Punkte)

**Thema:** Praktische Anwendung der 1:1-Beziehung

### Aufgabenstellung (Code)

```java
Arzt arzt1 = new Arzt("Dr. Müller");
Arzt arzt2 = new Arzt("Dr. Schmidt");

Person person = new Person("Anna", "Bauer", 30, "Frau");
person.setArzt(arzt1);

System.out.println("Arzt: " + person.getArzt().getName());

person.setArzt(arzt2);

System.out.println("Arzt: " + person.getArzt().getName());

person.setArzt(null);
System.out.println("Hat Arzt: " + (person.getArzt() != null));
```

### Musterlösungen

**a) Erste Ausgabe (1,5 Punkte)**

```
Arzt: Dr. Müller
```

**Bewertung:**
- ✓ `Arzt: Dr. Müller` → 1,5 Punkte
- ✓ `Dr. Müller` → 1 Punkt (korrekt, aber unvollständig)
- ✗ anderer Name → 0 Punkte

---

**b) Zweite Ausgabe (1,5 Punkte)**

```
Arzt: Dr. Schmidt
```

**Bewertung:**
- ✓ `Arzt: Dr. Schmidt` → 1,5 Punkte
- ✓ `Dr. Schmidt` → 1 Punkt
- ✗ `Dr. Müller` (Verwechslung mit vorher) → 0 Punkte

---

**c) Dritte Ausgabe (1 Punkt)**

```
Hat Arzt: false
```

**Bewertung:**
- ✓ `Hat Arzt: false` oder `false` → 1 Punkt
- ✗ `true` → 0 Punkte

---

**d) NullPointerException-Erklärung (2 Punkte)**

**Musterlösung:**
```
Wenn getArzt() null zurückgibt und man .getName() darauf aufruft,
wird eine NullPointerException ausgelöst, weil auf ein nicht-existierendes
Objekt zugegriffen wird. Man muss zuerst prüfen, ob getArzt() != null.
```

**Bewertung:**
- **2 Punkte:** NullPointerException genannt + Erklärung (null-Zugriff)
- **1 Punkt:** Fehler erkannt, aber nicht korrekt benannt
- **0 Punkte:** Falsch oder nicht beantwortet

---

**e) Anzahl Objekte im Speicher (2 Punkte)**

```
2 (arzt1 und arzt2 sind noch durch Variablen referenziert)
```

**Bewertung:**
- ✓ `2` mit sinnvoller Begründung → 2 Punkte
- ✓ `2` ohne Begründung → 1,5 Punkte
- ~ `1` (arzt1 nicht mehr über person erreichbar, aber Variable noch da) → 0,5 Punkte
- ✗ andere Zahl → 0 Punkte

---

### Subtotale Aufgabe 3: max. 8 Punkte
- a) 1,5 Punkte
- b) 1,5 Punkte
- c) 1 Punkt
- d) 2 Punkte
- e) 2 Punkte

---

## Aufgabe 4: Schleife mit 1:1-Assoziation (7 Punkte)

**Thema:** Verarbeitung von Objekten mit einfachen Assoziationen

### Musterlösungen

**a) for-each mit null-Prüfung (4 Punkte)**

```java
for (Person person : personen) {
    if (person.getArzt() != null) {
        System.out.println(person.getVorname() + " " + person.getNachname()
            + " - Arzt: " + person.getArzt().getName());
    } else {
        System.out.println(person.getVorname() + " " + person.getNachname()
            + " - kein Arzt");
    }
}
```

**Bewertung (4 Punkte):**
- **4 Punkte:** Schleife korrekt, null-Prüfung vorhanden, Ausgabe sinnvoll
- **3 Punkte:** Schleife korrekt, aber null-Prüfung fehlt oder Ausgabe unvollständig
- **2 Punkte:** Konzept erkannt, syntaktische oder logische Fehler
- **1 Punkt:** Grundidee vorhanden, viele Fehler
- **0 Punkte:** Nicht beantwortet oder völlig falsch

---

**b) Zähler für Personen ohne Arzt (2 Punkte)**

```java
int ohneArzt = 0;
for (Person person : personen) {
    if (person.getArzt() == null) {
        ohneArzt++;
    }
}
System.out.println("Personen ohne Arzt: " + ohneArzt);
```

**Bewertung (2 Punkte):**
- **2 Punkte:** Zähler korrekt initialisiert, Schleife mit null-Prüfung und Increment
- **1 Punkt:** Konzept erkannt, aber kleiner Fehler (z. B. kein Inkrement oder falsche Bedingung)
- **0 Punkte:** Falsch oder nicht beantwortet

---

**Bonus: Fehlertyp bei fehlendem null-Check (max. +1 Punkt)**

```
NullPointerException
```

**Bonus-Kriterien:**
- ✓ `NullPointerException` → +1 Punkt
- ✓ `NullPointer` oder `NPE` → +0,5 Punkte
- ✗ anderer Fehlertyp → +0 Punkte

---

### Subtotale Aufgabe 4: max. 7 Punkte (+1 Bonus)
- a) 4 Punkte
- b) 2 Punkte
- Bonus) +1 Punkt

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte | Gewichtung |
|---------|------------|-----------|
| 1. Datenstrukturen im Vergleich | 5 | 20% |
| 2. Methodenzuordnung | 5 | 20% |
| 3. Code-Analyse | 8 | 32% |
| 4. Schleife mit 1:1-Assoziation | 7 | 28% |
| **GESAMT** | **25** | **100%** |

---

## Bewertungsskala

| Prozent | Note | Bedeutung |
|---------|------|-----------|
| 90-100% | 1 | Sehr gut |
| 80-89% | 2 | Gut |
| 70-79% | 3 | Befriedigend |
| 60-69% | 4 | Ausreichend |
| 50-59% | 5 | Mangelhaft |
| < 50% | 6 | Ungenügend |

**Notenschlüssel für diesen Test:**
- **ab 23 Punkte (92%):** Note 1
- **ab 20 Punkte (80%):** Note 2
- **ab 18 Punkte (72%):** Note 3
- **ab 15 Punkte (60%):** Note 4
- **ab 13 Punkte (52%):** Note 5
- **unter 13 Punkte:** Note 6

---

## Häufige Schülerfehler & Wertungen

### Fehler 1: Kein null-Check vor Zugriff
```java
person.getArzt().getName()  // ← ohne if (person.getArzt() != null)
```
**Handling:** In Aufgabe 4a 1 Punkt abziehen; als Fehlerquelle in Bonusaufgabe akzeptieren

### Fehler 2: Verwechslung Setter/Getter
```java
person.getArzt(arzt1);  // ← falsch, get hat keine Parameter
```
**Handling:** Aufgabe 2 – falscher Buchstabe, kein Punkt für diese Zeile

### Fehler 3: ArrayList statt Objektverweis für 1:1
```java
List<Arzt> aerzte = new ArrayList<>();  // ← für 1:1 falsch
```
**Handling:** In Aufgabe 1 Begründung nicht akzeptieren → 0 Punkte Begründung

---

## Tipps zur schnellen Korrektur

1. **Aufgabe 1:** Tabelle zeilenweise prüfen (je 0,8 Punkte), dann Erklärung
2. **Aufgabe 2:** 5 Buchstaben mit Musterlösung abgleichen
3. **Aufgabe 3a-c:** Nur Ausgabetexte vergleichen
4. **Aufgabe 3d:** Stichwort „NullPointerException"
5. **Aufgabe 4:** Null-Check vorhanden? Schleife läuft über alle Elemente?

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Zielgruppe:** Schüler nach Lerneinheit 1:1-Assoziationen  
**Voraussetzungen:** Objektreferenzen, null-Werte, get/set-Methoden bekannt
