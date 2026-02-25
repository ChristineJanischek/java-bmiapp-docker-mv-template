# Kurztest: Assoziationen & Bidirektionale Beziehungen - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: Deklaration bidirektionaler Assoziationen (5 Punkte)

**Thema:** Aufbau von Klassen mit gegenseitigen Verweisen

### Musterlösung

**a) Ergänzte Klassen (4 Punkte)**

```java
public class Arzt {
    private String name;
    private List<Patient> patienten = new ArrayList<>();  // ← Attribut

    public Arzt(String name) {
        this.name = name;
        this.patienten = new ArrayList<>();               // ← Initialisierung
    }
}

public class Patient {
    private String vorname;
    private Arzt arzt;                                    // ← Rückverweis

    public Patient(String vorname) {
        this.vorname = vorname;
    }
}
```

**Bewertung (4 Punkte):**
- **2 Punkte:** Attribut `List<Patient> patienten` in `Arzt` korrekt deklariert und initialisiert
  - 1 Punkt: Deklaration vorhanden (ohne Initialisierung im Konstruktor)
- **1 Punkt:** Initialisierung im Konstruktor (`this.patienten = new ArrayList<>()`)
  - 0,5 Punkte: Initialisierung als Inline-Zuweisung ohne Konstruktor
- **1 Punkt:** Rückverweis `Arzt arzt` in `Patient` korrekt deklariert

**Häufige Fehler:**
- `ArrayList<Patient>` statt `List<Patient>` → akzeptieren (0 Punkte Abzug)
- Fehlende Generics (`List patienten`) → 0,5 Punkte Abzug

---

**b) Erklärung (1 Punkt)**

**Musterlösung:**
```
Bei einer unidirektionalen Beziehung kennt nur eine Seite die andere (z. B. nur Arzt kennt
seine Patienten). Bei einer bidirektionalen Beziehung kennt jede Seite die andere –
der Patient kennt auch seinen Arzt.
```

**Bewertung:**
- ✓ Sinnvolle Unterscheidung (beide Richtungen erklärt) → 1 Punkt
- ~ Ansatz erkennbar → 0,5 Punkte
- ✗ Falsch → 0 Punkte

---

## Aufgabe 2: Zugriffsmethoden & Zuordnung (6 Punkte)

**Thema:** Konsistente Verwaltung beider Richtungen

### Musterlösung

**a) addPatient()-Methode (2 Punkte)**

```java
public void addPatient(Patient patient) {
    this.patienten.add(patient);      // 1. Patient zur Liste des Arztes hinzufügen
    patient.setArzt(this);            // 2. Rückverweis beim Patienten setzen
}
```

**Bewertung (2 Punkte):**
- **1 Punkt:** `this.patienten.add(patient)` oder gleichwertig
- **1 Punkt:** `patient.setArzt(this)` oder `patient.setArzt(arzt)` (wenn Arzt als Parameter)
- **0,5 Punkte je Teil:** Konzept erkannt, aber syntaktisch falsch

---

**b) Zuordnungstabelle (4 Punkte)**

| Codeschnipsel | Richtiger Buchstabe | Bedeutung |
|---------------|----------------------|-----------|
| `arzt.addPatient(patient)` | **(c)** | Fügt einen Patienten hinzu und setzt den Rückverweis |
| `patient.getArzt()` | **(a)** | Greift auf den Arzt eines Patienten zu |
| `arzt.getPatienten()` | **(b)** | Gibt alle Patienten eines Arztes zurück |
| `arzt.getPatienten().contains(patient)` | **(d)** | Prüft, ob ein Patient bereits in der Liste ist |

**Bewertung (4 Punkte):** 1 Punkt je korrekte Zuordnung

---

## Aufgabe 3: Code-Analyse (bidirektionale Beziehung) (8 Punkte)

**Thema:** Nachvollziehen von Zuständen bei bidirektionalen Assoziationen

### Musterlösungen

**a) Erste Ausgabe (1 Punkt)**

```
Anzahl Patienten: 3
```

**Bewertung:** ✓ `3` oder `Anzahl Patienten: 3` → 1 Punkt

---

**b) Zweite und dritte Ausgabe (2 Punkte)**

```
Arzt von Klaus: Dr. Weber
Arzt von Maria: Dr. Weber
```

**Bewertung:** je 1 Punkt (Rückverweis korrekt erkannt)

---

**c) Vierte Ausgabe (1 Punkt)**

```
Anzahl nach Entfernen: 2
```

**Bewertung:** ✓ `2` → 1 Punkt

---

**d) Zustand von p2.getArzt() (2 Punkte)**

**Musterlösung:**
```
p2.getArzt() gibt immer noch "Dr. Weber" zurück, weil nur die Liste des Arztes
aktualisiert wurde, nicht der Rückverweis im Patienten-Objekt.
```

**Bewertung:**
- **2 Punkte:** Korrekt erkannt (Rückverweis bleibt `Dr. Weber`) + Begründung
- **1 Punkt:** Richtige Antwort ohne Begründung
- **0 Punkte:** `null` oder falsch

---

**e) Vollständige bidirektionale Entfernung (2 Punkte)**

**Musterlösung:**
```
Zusätzlich müsste p2.setArzt(null) aufgerufen werden, um den Rückverweis
beim Patienten zu entfernen. Nur so ist die Beziehung auf beiden Seiten
konsistent aufgelöst.
```

**Bewertung:**
- **2 Punkte:** `p2.setArzt(null)` erwähnt + konsistenter Zustand erklärt
- **1 Punkt:** Grundidee (beide Seiten aktualisieren) erkannt
- **0 Punkte:** Falsch oder nicht beantwortet

---

### Subtotale Aufgabe 3: max. 8 Punkte
- a) 1 Punkt
- b) 2 Punkte
- c) 1 Punkt
- d) 2 Punkte
- e) 2 Punkte

---

## Aufgabe 4: Verschachtelte Ausgabe (6 Punkte)

**Thema:** Ausgabe aller Objekte in verschachtelten Collection-Strukturen

### Musterlösungen

**a) Verschachtelte Schleife (5 Punkte)**

```java
for (Arzt arzt : aerzte) {
    System.out.println(arzt.getName() + ":");
    for (Patient patient : arzt.getPatienten()) {
        System.out.println("  - " + patient.getVorname());
    }
}
```

**Bewertung (5 Punkte):**
- **2 Punkte:** Äußere Schleife über `aerzte` korrekt
- **2 Punkte:** Innere Schleife über `arzt.getPatienten()` korrekt
- **1 Punkt:** Ausgabe im richtigen Format (Einrückung / Arztname + Patientenliste)

**Abzüge:**
- Fehlende Einrückung oder anderes Format → 0,5 Punkte Abzug
- Schleife funktioniert, aber Methodennamen falsch (z. B. `getNamen()`) → 0,5 Punkte Abzug

---

**b) Anzahl Schleifendurchläufe (1 Punkt)**

```
Äußere Schleife: 2 Durchläufe (für a1 und a2)
Innere Schleife: 2 + 1 = 3 Durchläufe (Anna, Bernd, Clara)
Gesamt: 2 + 3 = 5 Durchläufe
```

**Bewertung:**
- ✓ `5` (mit oder ohne Erklärung) → 1 Punkt
- ✓ Korrekte Aufschlüsselung (2 äußere + 3 innere) → 1 Punkt
- ✗ andere Zahl → 0 Punkte

---

### Subtotale Aufgabe 4: max. 6 Punkte
- a) 5 Punkte
- b) 1 Punkt

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte | Gewichtung |
|---------|------------|-----------|
| 1. Deklaration bidirektionaler Assoziationen | 5 | 20% |
| 2. Zugriffsmethoden & Zuordnung | 6 | 24% |
| 3. Code-Analyse | 8 | 32% |
| 4. Verschachtelte Ausgabe | 6 | 24% |
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

### Fehler 1: Nur eine Seite in addPatient aktualisiert
```java
public void addPatient(Patient patient) {
    this.patienten.add(patient);  // ← Rückverweis vergessen
}
```
**Handling:** 1 Punkt abziehen (bidirektionale Konsistenz nicht erfüllt)

### Fehler 2: Inkonsistentes remove
```java
arzt.getPatienten().remove(p2);  // ← p2.setArzt(null) vergessen
```
**Handling:** In Aufgabe 3e als Kernfehler verwenden; Schüler, die dies erkennen: volle Punkte

### Fehler 3: Schleifenverschachtelung falsch
```java
for (Patient p : patienten) {          // ← Äußere Schleife über Patienten
    for (Arzt a : aerzte) { ... }      // ← Statt Patienten des Arztes
}
```
**Handling:** Logik falsch → max. 2 Punkte für Aufgabe 4a

---

## Tipps zur schnellen Korrektur

1. **Aufgabe 1a:** Drei Stellen prüfen: `List<Patient>`, `new ArrayList<>()` im Konstruktor, `Arzt arzt` in Patient
2. **Aufgabe 2a:** Zwei Zeilen: add() + setArzt(this)
3. **Aufgabe 3a-c:** Nur Zahlen/Namen vergleichen
4. **Aufgabe 3d-e:** Stichwort „Rückverweis nicht aktualisiert" / `setArzt(null)`
5. **Aufgabe 4:** Schleife in Schleife? Arztname + Patientennamen ausgegeben?

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Zielgruppe:** Schüler nach Lerneinheit Bidirektionale Assoziationen  
**Voraussetzungen:** 1:N-Beziehungen, ArrayList, get/set-Methoden, verschachtelte Schleifen bekannt
