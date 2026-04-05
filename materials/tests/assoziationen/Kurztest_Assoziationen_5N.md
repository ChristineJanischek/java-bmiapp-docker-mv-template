# Kurztest: Assoziationen & ArrayList - Bücherei und Bücher (1:N Beziehung)

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: ArrayList-Deklaration (3 Punkte)

**Thema:** Datenstruktur für eine Buchsammlung

Schreibe die Deklaration und Initialisierung einer ArrayList, die Buch-Objekte speichert.

```java
// Hier aufschreiben:


```

Erkläre in 1-2 Sätzen, warum man hier ArrayList statt Array verwendet.

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: ArrayList-Methoden (4 Punkte)

**Thema:** Verwaltung von Büchern in einer Bücherei

Ordne die ArrayList-Methoden ihren Beschreibungen zu. Schreibe den Buchstabe in die Klammer.

**Beschreibungen:**

- **(a)** Gibt die Gesamtzahl der Bücher zurück
- **(b)** Fügt ein Buch zur Sammlung hinzu
- **(c)** Greift auf ein Buch an einer bestimmten Position zu
- **(d)** Entfernt ein Buch aus der Sammlung (z. B. aussortierte Bücher)

| Methode | Buchstabe |
|---------|-----------|
| `add(Buch)` | ( ) |
| `get(int index)` | ( ) |
| `remove(int index)` | ( ) |
| `size()` | ( ) |

---

## Aufgabe 3: Code-Analyse (5 Punkte)

**Thema:** 1:N Beziehung zwischen Bücherei und Büchern

Gegeben ist folgender Code:

```java
Buecherei buecherei = new Buecherei("Stadtbücherei");

Buch b1 = new Buch("Der Hobbit", "J.R.R. Tolkien", 2024);
Buch b2 = new Buch("1984", "George Orwell", 2023);
Buch b3 = new Buch("Herr der Ringe", "J.R.R. Tolkien", 2024);

buecherei.addBuch(b1);
buecherei.addBuch(b2);
buecherei.addBuch(b3);

System.out.println("Bücher im Bestand: " + buecherei.getAnzahlBuecher());
Buch zweitesBuch = buecherei.getBuecher().get(1);
System.out.println("Zweites Buch: " + zweitesBuch.getTitel());
```

**Fragen:**

a) Was wird folgende Zeile als Ausgabe produzieren?  
```java
System.out.println("Bücher im Bestand: " + buecherei.getAnzahlBuecher());
```

Antwort: ___________________________________________________________________

b) Welcher Titel wird ausgegeben bei der Zeile mit `get(1)`?

Antwort: ___________________________________________________________________

c) Erkläre, was `.getBuecher().get(1)` macht:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 4: Schleife & Ausgabe (5 Punkte)

**Thema:** Katalog aller Bücher anzeigen

Schreibe eine vollständige **for-Schleife** (oder for-each Schleife), die alle Bücher einer Bücherei ausgibt.

**Vorgabe:** Die ArrayList mit den Büchern heißt `buecher` und besteht aus `Buch`-Objekten mit den Attributen `getTitel()` und `getAutor()`.

```java
List<Buch> buecher = buecherei.getBuecher();

// Schleife hier schreiben:


```

Bonus (+1 Punkt): Schreibe zusätzlich eine `System.out.println()` Zeile, die die laufende Nummer (1, 2, 3...), Titel und Autor ausgibt.

```java
// Bonus-Ausgabe:

```

---

## Aufgabe 5: Methode verstehen (4 Punkte)

**Thema:** Methoden für 1:N Beziehungen in einer Bücherei

In der `Buecherei`-Klasse gibt es eine Methode `getAutorHaeufigkeit(String autor)`, die zählt, wie viele Bücher eines bestimmten Autors vorhanden sind.

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

**Thema:** Häufige Fehler bei ArrayList-Nutzung in einer Bücherei

Gegeben ist folgender fehlerhafter Code:

```java
Buecherei buecherei = new Buecherei("Bücherei XYZ");
Buch b1 = new Buch("Faust", "Goethe", 2024);

buecherei.buecher.add(b1);  // ← FEHLER 1
System.out.println(buecherei.getBuecher().get(10));  // ← FEHLER 2
```

**Aufgaben:**

a) **Fehler 1:** `buecherei.buecher.add(b1)` - Was ist das Problem?

___________________________________________________________________________

___________________________________________________________________________

b) **Fehler 2:** `buecherei.getBuecher().get(10)` - Warum funktioniert das nicht (wenn die Bücherei nur 3 Bücher hat)?

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
