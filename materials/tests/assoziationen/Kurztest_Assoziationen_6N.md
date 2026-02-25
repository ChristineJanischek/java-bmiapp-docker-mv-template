# Kurztest: Assoziationen & ArrayList - Mannschaft und Spieler (1:N Beziehung)

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: ArrayList-Deklaration (3 Punkte)

**Thema:** Datenstruktur für eine Fußball-Mannschaft

Schreibe die Deklaration und Initialisierung einer ArrayList, die Spieler-Objekte speichert.

```java
// Hier aufschreiben:


```

Erkläre in 1-2 Sätzen, warum man hier ArrayList statt Array verwendet.

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: ArrayList-Methoden (4 Punkte)

**Thema:** Verwaltung von Spielern in einer Mannschaft

Ordne die ArrayList-Methoden ihren Beschreibungen zu. Schreibe den Buchstaben in die Klammer.

**Beschreibungen:**

- **(a)** Gibt die Gesamtzahl der Spieler zurück
- **(b)** Registriert einen neuen Spieler in der Mannschaft
- **(c)** Greift auf einen Spieler an einer bestimmten Position zu
- **(d)** Entfernt einen Spieler aus der Mannschaft (z. B. bei Verletzung)

| Methode | Buchstabe |
|---------|-----------|
| `add(Spieler)` | ( ) |
| `get(int index)` | ( ) |
| `remove(int index)` | ( ) |
| `size()` | ( ) |

---

## Aufgabe 3: Code-Analyse (5 Punkte)

**Thema:** 1:N Beziehung zwischen Mannschaft und Spielern

Gegeben ist folgender Code:

```java
Mannschaft mannschaft = new Mannschaft("FC Bayern");

Spieler s1 = new Spieler("Müller", 9, "Stürmer");
Spieler s2 = new Spieler("Neuer", 1, "Torwart");
Spieler s3 = new Spieler("Kimmich", 32, "Abwehr");

mannschaft.addSpieler(s1);
mannschaft.addSpieler(s2);
mannschaft.addSpieler(s3);

System.out.println("Spieler im Kader: " + mannschaft.getAnzahlSpieler());
Spieler ersterSpieler = mannschaft.getSpieler().get(0);
System.out.println("Spieler 1: " + ersterSpieler.getName());
```

**Fragen:**

a) Was wird folgende Zeile als Ausgabe produzieren?  
```java
System.out.println("Spieler im Kader: " + mannschaft.getAnzahlSpieler());
```

Antwort: ___________________________________________________________________

b) Welcher Name wird ausgegeben bei der Zeile mit `get(0)`?

Antwort: ___________________________________________________________________

c) Erkläre, was `.getSpieler().get(0)` macht:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 4: Schleife & Ausgabe (5 Punkte)

**Thema:** Kompletter Spielerkader ausgeben

Schreibe eine vollständige **for-Schleife** (oder for-each Schleife), die alle Spieler einer Mannschaft ausgibt.

**Vorgabe:** Die ArrayList mit den Spielern heißt `spieler` und besteht aus `Spieler`-Objekten mit den Attributen `getName()`, `getNummer()` und `getPosition()`.

```java
List<Spieler> spieler = mannschaft.getSpieler();

// Schleife hier schreiben:


```

Bonus (+1 Punkt): Schreibe zusätzlich eine `System.out.println()` Zeile, die die Nummer, Name und Position ausgibt.

```java
// Bonus-Ausgabe:

```

---

## Aufgabe 5: Methode verstehen (4 Punkte)

**Thema:** Methoden für 1:N Beziehungen in einer Mannschaft

In der `Mannschaft`-Klasse gibt es eine Methode `getSpielerMitPosition(String position)`, die alle Spieler einer bestimmten Position zurückgibt.

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

**Thema:** Häufige Fehler bei ArrayList-Nutzung in einer Mannschaft

Gegeben ist folgender fehlerhafter Code:

```java
Mannschaft mannschaft = new Mannschaft("FC Dortmund");
Spieler s1 = new Spieler("Haaland", 9, "Stürmer");

mannschaft.spieler.add(s1);  // ← FEHLER 1
System.out.println(mannschaft.getSpieler().get(15));  // ← FEHLER 2
```

**Aufgaben:**

a) **Fehler 1:** `mannschaft.spieler.add(s1)` - Was ist das Problem?

___________________________________________________________________________

___________________________________________________________________________

b) **Fehler 2:** `mannschaft.getSpieler().get(15)` - Warum funktioniert das nicht (wenn die Mannschaft nur 11 Spieler hat)?

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
