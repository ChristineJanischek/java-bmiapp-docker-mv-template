# Kurztest: Komposition & Interface in Java

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Komposition vs. Vererbung (4 Punkte)

**Thema:** Beziehungen richtig modellieren

Gegeben sind folgende Szenarien. Entscheide, ob **Komposition** oder **Vererbung** besser passt. Schreibe **K** (Komposition) oder **V** (Vererbung) in die Klammer.

1) Ein `Auto` hat einen `Motor` (Motor kann ausgetauscht werden). ( )
2) Ein `Hund` ist ein `Tier`. ( )
3) Ein `Rechnung` hat mehrere `Rechnungspositionen`. ( )
4) Ein `Mitarbeiter` ist eine `Person`. ( )

Begründe in 1-2 Sätzen, warum Komposition oft flexibler ist als Vererbung:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: Interface definieren (5 Punkte)

**Thema:** Gemeinsame Fähigkeiten über Interfaces

Erstelle ein Interface `Zahlbar`, das eine Methode `berechneBetrag()` vorschreibt.

```java
// Interface hier schreiben:



```

Erkläre in 1-2 Sätzen, warum ein Interface in diesem Fall sinnvoller ist als eine Oberklasse:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 3: Komposition in der Praxis (5 Punkte)

**Thema:** Bestellung mit Positionen

Vervollständige die Klasse `Bestellung`, die eine Liste von `Position`-Objekten verwaltet:

```java
public class Bestellung {
    private List<Position> positionen;

    public Bestellung() {
        // Initialisierung hier:

    }

    public void addPosition(Position p) {
        // Position hinzufügen:

    }

    public double getGesamtpreis() {
        // Summe aller Positionen berechnen:

    }
}
```

Erkläre kurz, warum hier Komposition verwendet wird:

___________________________________________________________________________

---

## Aufgabe 4: Interface nutzen (5 Punkte)

**Thema:** Polymorphie mit Interfaces

Gegeben sind die Klassen `Produkt`, `Dienstleistung` und `Abo`, die alle `Zahlbar` implementieren.

```java
List<Zahlbar> posten = new ArrayList<>();
posten.add(new Produkt("Laptop", 900));
posten.add(new Dienstleistung("Beratung", 120));
posten.add(new Abo("Cloud", 15));

double summe = 0;
for (Zahlbar z : posten) {
    summe += z.berechneBetrag();
}

System.out.println("Summe: " + summe);
```

**Fragen:**

a) Warum funktioniert die for-Schleife mit Objekten unterschiedlicher Klassen?

___________________________________________________________________________

___________________________________________________________________________

b) Was wird ausgegeben, wenn die Beträge 900, 120 und 15 sind?

Antwort: ___________________________________________________________________

---

## Aufgabe 5: Komposition + Interface kombinieren (4 Punkte)

**Thema:** Zahlungsabwicklung

Ein `Warenkorb` enthält `WarenkorbPosition`-Objekte. Jede Position ist `Zahlbar`.

Schreibe eine Methode, die die Summe aller Positionen berechnet:

```java
public double berechneSumme(List<Zahlbar> positionen) {
    // Methode hier schreiben:



}
```

---

## Aufgabe 6: Fehlersuche & Analyse (2 Punkte)

**Thema:** Typfehler mit Interfaces

Gegeben ist folgender Code:

```java
List<Zahlbar> posten = new ArrayList<>();
posten.add(new Produkt("Maus", 25));

for (Produkt p : posten) {  // ← FEHLER
    System.out.println(p.berechneBetrag());
}
```

a) Was ist der Fehler?

___________________________________________________________________________

b) Schreibe die korrekte Schleife:

```java
// Korrekt:

```

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Komposition vs. Vererbung | 4 | |
| 2. Interface definieren | 5 | |
| 3. Komposition in der Praxis | 5 | |
| 4. Interface nutzen | 5 | |
| 5. Kombination Komposition + Interface | 4 | |
| 6. Fehlersuche | 2 | |
| **Gesamt** | **25** | |
