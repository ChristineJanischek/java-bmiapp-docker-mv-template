# Kurztest: Assoziationen & ArrayList (1:N Beziehung)

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: ArrayList-Deklaration (3 Punkte)

**Thema:** Datenstruktur verstehen

Schreibe die Deklaration und Initialisierung einer ArrayList, die Messung-Objekte speichert.

```java
// Hier aufschreiben:


```

Erkläre in 1-2 Sätzen, warum man hier ArrayList statt Array verwendet.

___________________________________________________________________________

___________________________________________________________________________


---

## Aufgabe 2: ArrayList-Methoden (4 Punkte)

**Thema:** Zugriff auf Listenelemente

Ordne die ArrayList-Methoden ihren Beschreibungen zu. Schreibe den Buchstaben in die Klammer.

| Methode | Beschreibung |
|---------|-------------|
| `add(Object)` | ( ) | Gibt die Anzahl der Elemente zurück |
| `get(int index)` | ( ) | Fügt ein Element am Ende hinzu |
| `remove(int index)` | ( ) | Gibt das Element an Position index zurück |
| `size()` | ( ) | Entfernt das Element an Position index |

---

## Aufgabe 3: Code-Analyse (5 Punkte)

**Thema:** Praktische Anwendung der 1:N Beziehung

Gegeben ist folgender Code:

```java
Person person = new Person("Anna", "Schmidt", 28, "Frau");

Messung m1 = new Messung(65.0, 1.70);
Messung m2 = new Messung(67.0, 1.70);
Messung m3 = new Messung(64.0, 1.70);

person.addMessung(m1);
person.addMessung(m2);
person.addMessung(m3);

System.out.println("Anzahl Messungen: " + person.getAnzahlMessungen());
Messung letzteMessung = person.getMessungen().get(2);
System.out.println("Gewicht letzte Messung: " + letzteMessung.getGewicht());
```

**Fragen:**

a) Was wird folgende Zeile als Ausgabe produzieren?  
```java
System.out.println("Anzahl Messungen: " + person.getAnzahlMessungen());
```

Antwort: ___________________________________________________________________

b) Welches Gewicht wird ausgegeben bei der Zeile mit `get(2)`?

Antwort: ___________________________________________________________________

c) Erkläre, was `.getMessungen().get(2)` macht:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 4: Schleife & Ausgabe (5 Punkte)

**Thema:** Alle Elemente einer ArrayList verarbeiten und ausgeben

Schreibe eine vollständige **for-Schleife** (oder for-each Schleife), die alle Messungen einer Person ausgibt.

**Vorgabe:** Die ArrayList mit den Messungen heißt `messungen` und besteht aus `Messung`-Objekten.

```java
List<Messung> messungen = person.getMessungen();

// Schleife hier schreiben:


```

Bonus (+1 Punkt): Schreibe zusätzlich eine `System.out.println()` Zeile, die den Index (Nummer) und das Gewicht ausgibt.

```java
// Bonus-Ausgabe:

```

---

## Aufgabe 5: Methode verstehen (4 Punkte)

**Thema:** Methoden für 1:N Beziehungen

In der `Person`-Klasse gibt es eine Methode `getDurchschnittsBmi()`.

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

**Thema:** Häufige Fehler bei ArrayList-Nutzung

Gegeben ist folgender fehlerhafter Code:

```java
Person person = new Person("Max", "Müller", 35, "Mann");
Messung m1 = new Messung(80.0, 1.80);

person.messungen.add(m1);  // ← FEHLER 1
System.out.println(person.getMessungen().get(5));  // ← FEHLER 2
```

**Aufgaben:**

a) **Fehler 1:** `person.messungen.add(m1)` - Was ist das Problem?

___________________________________________________________________________

___________________________________________________________________________

b) **Fehler 2:** `person.getMessungen().get(5)` - Warum funktioniert das nicht?

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
