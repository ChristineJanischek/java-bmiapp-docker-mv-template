# Kurztest: Assoziationen & 1:1-Beziehungen

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Datenstrukturen im Vergleich (5 Punkte)

**Thema:** Auswahl der richtigen Datenstruktur

Fülle die folgende Tabelle aus. Schreibe in jede Zelle **Ja** oder **Nein**.

| Eigenschaft | Array | ArrayList | Einzelner Objektverweis |
|-------------|-------|-----------|------------------------|
| Dynamisch erweiterbar | | | |
| Feste Größe nach Erstellung | | | |
| Geeignet für genau 1 zugeordnetes Objekt | | | |
| Benötigt Import (`java.util`) | | | |
| Direkter Zugriff über `[index]` | | | |

Begründe in 1–2 Sätzen, warum für eine **1:1-Beziehung** (z. B. eine Person hat genau einen Arzt) ein einzelner Objektverweis besser geeignet ist als eine ArrayList:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: Methodenzuordnung (5 Punkte)

**Thema:** Methoden für einfache und verkettete Assoziationen

Ordne die Methoden den Beschreibungen zu. Schreibe den Buchstaben in die Klammer.

**Beschreibungen:**

- **(a)** Setzt die Referenz auf ein anderes Objekt (Setter)
- **(b)** Gibt das referenzierte Objekt zurück (Getter)
- **(c)** Prüft, ob die Referenz gesetzt ist (nicht `null`)
- **(d)** Entfernt die Verbindung durch Setzen auf `null`
- **(e)** Gibt den Namen des referenzierten Objekts aus

| Methode | Buchstabe |
|---------|-----------|
| `getArzt()` | ( ) |
| `setArzt(Arzt a)` | ( ) |
| `person.getArzt() != null` | ( ) |
| `person.setArzt(null)` | ( ) |
| `person.getArzt().getName()` | ( ) |

---

## Aufgabe 3: Code-Analyse (1:1-Relation) (8 Punkte)

**Thema:** Praktische Anwendung der 1:1-Beziehung

Gegeben ist folgender Code:

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

**Fragen:**

a) Was gibt die **erste** `System.out.println`-Zeile aus?

Antwort: ___________________________________________________________________

b) Was gibt die **zweite** `System.out.println`-Zeile aus?

Antwort: ___________________________________________________________________

c) Was gibt die **dritte** `System.out.println`-Zeile aus?

Antwort: ___________________________________________________________________

d) Erkläre, was passiert, wenn man `person.getArzt().getName()` aufruft, nachdem `person.setArzt(null)` ausgeführt wurde:

___________________________________________________________________________

___________________________________________________________________________

e) Wie viele `Arzt`-Objekte existieren nach Ausführung des Codes insgesamt noch im Speicher?

Antwort: ___________________________________________________________________

---

## Aufgabe 4: Schleife mit 1:1-Assoziation (7 Punkte)

**Thema:** Verarbeitung von Objekten mit einfachen Assoziationen

Gegeben ist eine Liste von Personen. Jede Person kann einen Arzt zugeordnet haben (oder auch nicht – `null`).

```java
List<Person> personen = new ArrayList<>();
personen.add(new Person("Max", "Meier", 25, "Mann"));
personen.add(new Person("Lena", "Klein", 32, "Frau"));
personen.add(new Person("Tom", "Wolf", 19, "Mann"));

personen.get(0).setArzt(new Arzt("Dr. Huber"));
personen.get(2).setArzt(new Arzt("Dr. Huber"));

// Schleife hier schreiben:
```

a) Schreibe eine **for-each-Schleife**, die für jede Person ausgibt, ob sie einen Arzt hat.  
Beispiel-Ausgabe: `Max Meier - Arzt: Dr. Huber` oder `Lena Klein - kein Arzt`

```java
// Schleife:


```

b) Schreibe eine **Zählvariable** und eine **Schleife**, die ermittelt, wie viele Personen *keinen* Arzt zugeordnet haben:

```java
// Zähler und Schleife:



```

Bonus (+1 Punkt): Was könnte passieren, wenn man in der Schleife direkt `person.getArzt().getName()` aufruft, ohne vorher zu prüfen? Nenne den Fehlertyp:

___________________________________________________________________________

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Datenstrukturen im Vergleich | 5 | |
| 2. Methodenzuordnung | 5 | |
| 3. Code-Analyse | 8 | |
| 4. Schleife mit 1:1-Assoziation | 7 | |
| **Gesamt** | **25** | |
