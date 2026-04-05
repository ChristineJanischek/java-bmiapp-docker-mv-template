# Kurztest: Assoziationen & ArrayList - Kurs und Personen - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: ArrayList-Deklaration (3 Punkte)

### Aufgabenstellung

**Thema:** Datenstruktur für eine Kursverwaltung

Schreibe die Deklaration und Initialisierung einer ArrayList, die Person-Objekte (Kursteilnehmer) speichert.

```java
// Hier aufschreiben:


```

Erkläre in 1-2 Sätzen, warum man hier ArrayList statt Array verwendet.

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösung

```java
List<Person> personen = new ArrayList<Person>();
```

oder

```java
private ArrayList<Person> personen = new ArrayList<>();
```

**Erklärung (1 Punkt):**
```
ArrayList wird verwendet, weil die Anzahl der Kursteilnehmer nicht vorab bekannt ist 
und sich dynamisch ändern kann (An- und Abmeldungen). Arrays haben eine feste Größe.
```

### Bewertung (3 Punkte)

**Deklaration (2 Punkte):**
- ✓ `List<Person> personen = new ArrayList<Person>();` → 2 Punkte
- ✓ `ArrayList<Person> personen = new ArrayList<>();` → 2 Punkte
- ~ Fehlende Generics (`List personen`) → 1 Punkt
- ✗ Array statt ArrayList → 0 Punkte

**Erklärung (1 Punkt):**
- ✓ Dynamische Größe oder variable Anzahl erwähnt → 1 Punkt
- ~ Ansatz erkennbar → 0,5 Punkte
- ✗ Falsch → 0 Punkte

---

## Aufgabe 2: ArrayList-Methoden (4 Punkte)

### Aufgabenstellung

**Thema:** Verwaltung von Kursteilnehmern

Ordne die ArrayList-Methoden ihren Beschreibungen zu. Schreibe den Buchstaben in die Klammer.

**Beschreibungen:**

- **(a)** Gibt die Gesamtzahl der angemeldeten Personen zurück
- **(b)** Meldet eine neue Person zum Kurs an
- **(c)** Greift auf eine bestimmte Person zu (z. B. für Noten)
- **(d)** Entfernt eine Person aus dem Kurs (z. B. Abmeldung)

| Methode | Buchstabe |
|---------|-----------|
| `add(Person)` | ( ) |
| `get(int index)` | ( ) |
| `remove(int index)` | ( ) |
| `size()` | ( ) |

---

### Musterlösung (Zuordnung)

| Methode | Richtiger Buchstabe |
|---------|----------------------|
| `add(Person)` | **(b)** |
| `get(int index)` | **(c)** |
| `remove(int index)` | **(d)** |
| `size()` | **(a)** |

### Bewertung (4 Punkte)

- **4 Punkte:** Alle 4 Zuordnungen korrekt
- **3 Punkte:** 3 von 4 richtig
- **2 Punkte:** 2 von 4 richtig
- **1 Punkt:** 1 von 4 richtig
- **0 Punkte:** Keine oder alle falsch

---

## Aufgabe 3: Code-Analyse (5 Punkte)

### Aufgabenstellung

**Thema:** 1:N Beziehung zwischen Kurs und Personen

Gegeben ist folgender Code:

```java
Kurs kurs = new Kurs("Informatik Grundlagen");

Person p1 = new Person("Anna", "Schmidt", 20, "Frau");
Person p2 = new Person("Max", "Müller", 21, "Mann");
Person p3 = new Person("Leon", "Weber", 20, "Mann");

kurs.addPerson(p1);
kurs.addPerson(p2);
kurs.addPerson(p3);

System.out.println("Teilnehmerzahl: " + kurs.getAnzahlPersonen());
Person zweiterTeilnehmer = kurs.getPersonen().get(1);
System.out.println("2. Teilnehmer: " + zweiterTeilnehmer.getVorname());
```

**Fragen:**

a) Was wird folgende Zeile als Ausgabe produzieren?  
```java
System.out.println("Teilnehmerzahl: " + kurs.getAnzahlPersonen());
```

Antwort: ___________________________________________________________________

b) Welcher Vorname wird ausgegeben bei der Zeile mit `get(1)`?

Antwort: ___________________________________________________________________

c) Erkläre, was `.getPersonen().get(1)` macht:

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösungen

**a) Ausgabe (2 Punkte)**

```
Teilnehmerzahl: 3
```

**Bewertung:**
- ✓ `3` oder `Teilnehmerzahl: 3` → 2 Punkte
- ✗ andere Zahl → 0 Punkte

---

**b) Zweiter Teilnehmer (1,5 Punkte)**

```
Max
```

**Erklärung:** Index 1 ist das zweite Element: p1 (Anna, Index 0), p2 (Max, Index 1)

**Bewertung:**
- ✓ `Max` oder `2. Teilnehmer: Max` → 1,5 Punkte
- ✗ anderer Name → 0 Punkte

---

**c) Erklärung `.getPersonen().get(1)` (1,5 Punkte)**

**Musterlösung:**
```
getPersonen() gibt die Liste aller angemeldeten Personen zurück.
get(1) greift auf das Element an Index 1 zu, also die zweite Person in der Liste.
```

**Bewertung:**
- **1,5 Punkte:** Beide Teile erklärt (Getter + Index-Zugriff)
- **1 Punkt:** Teilweise erklärt
- **0,5 Punkte:** Grundidee erkannt
- **0 Punkte:** Falsch oder nicht beantwortet

---

## Aufgabe 4: Schleife & Ausgabe (5 Punkte)

### Aufgabenstellung

**Thema:** Teilnehmerliste eines Kurses ausgeben

Schreibe eine vollständige **for-Schleife** (oder for-each Schleife), die alle Personen eines Kurses ausgibt.

**Vorgabe:** Die ArrayList mit den Personen heißt `personen` und besteht aus `Person`-Objekten mit den Attributen `getVorname()`, `getNachname()` und `getAlter()`.

```java
List<Person> personen = kurs.getPersonen();

// Schleife hier schreiben:


```

Bonus (+1 Punkt): Schreibe zusätzlich eine `System.out.println()` Zeile, die die laufende Nummer (1-basiert), Vorname, Nachname und Alter ausgibt.

```java
// Bonus-Ausgabe:

```

---

### Musterlösungen

**Grundaufgabe (4 Punkte):**

```java
List<Person> personen = kurs.getPersonen();

for (Person person : personen) {
    System.out.println(person.getVorname() + " " + person.getNachname() + 
                       " (" + person.getAlter() + " Jahre)");
}
```

oder

```java
List<Person> personen = kurs.getPersonen();

for (int i = 0; i < personen.size(); i++) {
    Person p = personen.get(i);
    System.out.println(p.getVorname() + " " + p.getNachname() + " - Alter: " + p.getAlter());
}
```

**Bewertung (4 Punkte):**
- **4 Punkte:** Schleife korrekt, alle Personen ausgegeben mit Vorname, Nachname, Alter
- **3 Punkte:** Schleife funktioniert, aber unvollständige Ausgabe
- **2 Punkte:** Grundkonzept erkannt, syntaktische Fehler
- **1 Punkt:** Versuch offensichtlich, viele Fehler
- **0 Punkte:** Falsch oder nicht beantwortet

---

**Bonus (+1 Punkt):**

```java
for (int i = 0; i < personen.size(); i++) {
    System.out.println((i + 1) + ". " + personen.get(i).getVorname() + " " + 
                       personen.get(i).getNachname() + " (" + 
                       personen.get(i).getAlter() + " Jahre)");
}
```

**Bewertung:**
- ✓ Laufende Nummer (1-basiert) + Vorname + Nachname + Alter → +1 Punkt
- ~ Ähnliche sinnvolle Ausgabe → +1 Punkt

---

## Aufgabe 5: Methode verstehen (4 Punkte)

### Aufgabenstellung

**Thema:** Methoden für 1:N Beziehungen in einem Kurs

In der `Kurs`-Klasse gibt es eine Methode `getPersonenAnDatum(String geburtstag)`, die alle Personen mit einem bestimmten Geburtstag findet.

a) Schreibe die **Methodensignatur** (ohne Implementierung):

```java
// Methodensignatur:

```

b) Erkläre in 2-3 Sätzen, wie diese Methode funktionieren würde:

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösung

**a) Methodensignatur (1,5 Punkte)**

```java
public List<Person> getPersonenAnDatum(String geburtstag)
```

oder

```java
public ArrayList<Person> getPersonenMitGeburtstag(String geburtstag)
```

**Bewertung:**
- ✓ Korrekte Signatur mit List/ArrayList und String-Parameter → 1,5 Punkte
- ~ `void` statt List → 0 Punkte
- ~ Parameter nicht mitgenommen → 0 Punkte

---

**b) Erklärung (2,5 Punkte)**

**Musterlösung:**
```
Die Methode iteriert durch alle Personen im Kurs und vergleicht ihren Geburtstag 
mit dem angegebenen Parameter. Alle Personen mit dem gleichen Geburtstag werden 
in einer neuen Liste gesammelt und zurückgegeben.
```

**Bewertung:**
- **2,5 Punkte:** Iteration + Vergleich + Rückgabe erklärt
- **1,5 Punkte:** Grundidee erkannt, aber unvollständig
- **0,5 Punkte:** Ansatz sichtbar
- **0 Punkte:** Falsch

---

## Aufgabe 6: Fehlersuche & Analyse (4 Punkte)

### Aufgabenstellung

**Thema:** Häufige Fehler bei ArrayList-Nutzung in einer Kursverwaltung

Gegeben ist folgender fehlerhafter Code:

```java
Kurs kurs = new Kurs("Java Programmierung");
Person person = new Person("Petra", "Klein", 19, "Frau");

kurs.personen.add(person);  // ← FEHLER 1
System.out.println(kurs.getPersonen().get(20));  // ← FEHLER 2 (bei max. 15 Personen)
```

**Aufgaben:**

a) **Fehler 1:** `kurs.personen.add(person)` - Was ist das Problem?

___________________________________________________________________________

___________________________________________________________________________

b) **Fehler 2:** `kurs.getPersonen().get(20)` - Warum funktioniert das nicht (wenn der Kurs nur 15 Personen hat)?

___________________________________________________________________________

___________________________________________________________________________

c) Schreibe die **korrekte Version** von Fehler 1:

```java
// Korrekt:

```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. ArrayList-Deklaration | 3 | |
| 2. ArrayList-Methoden | 4 | |
| 3. Code-Analyse | 5 | |
| 4. Schleife & Ausgabe | 5 | |
| 5. Methode verstehen | 4 | |
| 6. Fehlersuche | 4 | |
| **Gesamt** | **25** | |

### Musterlösungen

**a) Fehler 1 - Direkter ArrayList-Zugriff (1 Punkt)**

**Musterlösung:**
```
Das Attribut 'personen' ist private und nicht von außen erreichbar. 
Man muss die öffentliche Methode addPerson() verwenden.
```

**Bewertung:**
- ✓ Private + getter/Methode erwähnt → 1 Punkt
- ~ Encapsulation-Verletzung → 0,5 Punkte
- ✗ Falsch → 0 Punkte

---

**b) Fehler 2 - IndexOutOfBoundsException (1,5 Punkte)**

**Musterlösung:**
```
Der Index 20 existiert nicht (bei 15 Personen sind gültige Indizes 0-14).
Zugriff auf einen nicht-existierenden Index wirft IndexOutOfBoundsException.
Man sollte vorher mit size() oder isEmpty() prüfen.
```

**Bewertung:**
- **1,5 Punkte:** IndexOutOfBoundsException + Begründung + Prüfempfehlung
- **1 Punkt:** Fehler erkannt, aber nicht korrekt benannt
- **0,5 Punkte:** Ansatz erkennbar
- **0 Punkte:** Falsch

---

**c) Korrekte Version (1,5 Punkte)**

**Musterlösung:**
```java
kurs.addPerson(person);
```

**Bewertung:**
- ✓ `kurs.addPerson(person)` → 1,5 Punkte
- ~ `kurs.getPersonen().add(person)` → 1 Punkt
- ✗ Falsch → 0 Punkte

---

## GESAMTBEWERTUNG

| Aufgabe | Max. Punkte |
|---------|------------|
| 1. ArrayList-Deklaration | 3 |
| 2. ArrayList-Methoden | 4 |
| 3. Code-Analyse | 5 |
| 4. Schleife & Ausgabe | 5 (+1 Bonus) |
| 5. Methode verstehen | 4 |
| 6. Fehlersuche | 4 |
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

## Häufige Fehler & Wertungen

### Fehler 1: Direkter Attributzugriff (Encapsulation-Verletzung)
```java
kurs.personen.add(...)  // ← private Attribut direkt zugegriffen
```
**Handling:** In Aufgabe 6a 0,5 Punkte abziehen

### Fehler 2: Off-by-One bei Index-Zugriff
```java
personen.get(15)  // ← Bei 15 Personen gültig: Index 0-14
```
**Handling:** In Aufgabe 3b/c klar erklärt zurückweisen

### Fehler 3: Fehlende null-Prüfung
```java
for (Person p : personen) {
    System.out.println(p.getAlter());
}
// Was wenn p == null?
```
**Handling:** Bonus-Punkte nur, wenn Schleife strukturell korrekt ist

---

## Tipps zur schnellen Korrektur

1. **Aufgabe 1:** ArrayList<Person> + Generics? Erklärung: "dynamisch"?
2. **Aufgabe 2:** 4 Zuordnungen schnell mit Musterlösung abgleichen
3. **Aufgabe 3a:** Zähle selbst: 3 Personen hinzugefügt
4. **Aufgabe 3b:** Index 1 = 2. Element = "Max"
5. **Aufgabe 4:** for-each oder for-Schleife? Vorenamen + Nachnamen + Alter?
6. **Aufgabe 6:** private + addPerson() / IndexOutOfBoundsException?

---

**Test erstellt:** Version 1.0 (Feb 2026)  
**Zielgruppe:** Schüler nach Lerneinheit 1:N-Assoziationen (Kurs/Person)  
**Voraussetzungen:** ArrayList, Generics, for-Schleifen, Kursverwaltung bekannt
