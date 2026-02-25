# Kurztest: Assoziationen & ArrayList - Kurs und Personen (1:N Beziehung)

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: ArrayList-Deklaration (3 Punkte)

**Thema:** Datenstruktur für eine Kursverwaltung

Schreibe die Deklaration und Initialisierung einer ArrayList, die Person-Objekte (Kursteilnehmer) speichert.

```java
// Hier aufschreiben:


```

Erkläre in 1-2 Sätzen, warum man hier ArrayList statt Array verwendet.

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: ArrayList-Methoden (4 Punkte)

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

## Aufgabe 3: Code-Analyse (5 Punkte)

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

## Aufgabe 4: Schleife & Ausgabe (5 Punkte)

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

## Aufgabe 5: Methode verstehen (4 Punkte)

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

## Aufgabe 6: Fehlersuche & Analyse (4 Punkte)

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
