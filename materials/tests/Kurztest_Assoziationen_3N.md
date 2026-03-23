# Kurztest: Assoziationen & Bidirektionale Beziehungen

**Klasse:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Datum:** _________________ &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Deklaration bidirektionaler Assoziationen (5 Punkte)

**Thema:** Aufbau von Klassen mit gegenseitigen Verweisen

Gegeben sind zwei Klassen `Arzt` und `Patient`. Ein Arzt kann viele Patienten haben, und jeder Patient kennt seinen Arzt (bidirektionale 1:N-Beziehung).

a) Ergänze die fehlenden Attribut-Deklarationen in beiden Klassen:

```java
public class Arzt {
    private String name;
    // Attribut für die Liste aller zugeordneten Patienten:
    _______________________________________________

    public Arzt(String name) {
        this.name = name;
        _______________________________________________
    }
}

public class Patient {
    private String vorname;
    // Attribut für den zugeordneten Arzt (Rückverweis):
    _______________________________________________

    public Patient(String vorname) {
        this.vorname = vorname;
    }
}
```

b) Erkläre in 1–2 Sätzen, was den Unterschied zu einer **unidirektionalen** Beziehung ausmacht:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 2: Zugriffsmethoden & Zuordnung (6 Punkte)

**Thema:** Konsistente Verwaltung beider Richtungen

Bei bidirektionalen Beziehungen muss beim Hinzufügen immer **beide Seiten** aktualisiert werden.

a) Vervollständige die Methode `addPatient()` in der Klasse `Arzt`:

```java
public void addPatient(Patient patient) {
    // 1. Patient zur Liste des Arztes hinzufügen:
    _______________________________________________

    // 2. Rückverweis beim Patienten setzen:
    _______________________________________________
}
```

b) Ordne die Beschreibungen den Codeschnipseln zu. Schreibe den Buchstaben in die Klammer.

**Beschreibungen:**
- **(a)** Greift auf den Arzt eines Patienten zu
- **(b)** Gibt alle Patienten eines Arztes zurück
- **(c)** Fügt einen Patienten hinzu und setzt den Rückverweis
- **(d)** Prüft, ob ein Patient bereits in der Liste ist

| Codeschnipsel | Buchstabe |
|---------------|-----------|
| `arzt.addPatient(patient)` | ( ) |
| `patient.getArzt()` | ( ) |
| `arzt.getPatienten()` | ( ) |
| `arzt.getPatienten().contains(patient)` | ( ) |

---

## Aufgabe 3: Code-Analyse (bidirektionale Beziehung) (8 Punkte)

**Thema:** Nachvollziehen von Zuständen bei bidirektionalen Assoziationen

Gegeben ist folgender Code:

```java
Arzt arzt = new Arzt("Dr. Weber");
Patient p1 = new Patient("Klaus");
Patient p2 = new Patient("Maria");
Patient p3 = new Patient("Stefan");

arzt.addPatient(p1);
arzt.addPatient(p2);
arzt.addPatient(p3);

System.out.println("Anzahl Patienten: " + arzt.getPatienten().size());
System.out.println("Arzt von Klaus: " + p1.getArzt().getName());
System.out.println("Arzt von Maria: " + p2.getArzt().getName());

arzt.getPatienten().remove(p2);
System.out.println("Anzahl nach Entfernen: " + arzt.getPatienten().size());
```

**Fragen:**

a) Was gibt die **erste** Ausgabe aus?

Antwort: ___________________________________________________________________

b) Was geben die **zweite** und **dritte** Ausgabe aus?

Antwort 2: __________________________________________________________________

Antwort 3: __________________________________________________________________

c) Was gibt die **vierte** Ausgabe aus?

Antwort: ___________________________________________________________________

d) Was ist nach `arzt.getPatienten().remove(p2)` der Wert von `p2.getArzt()`?  
(Beachte: nur die Arzt-Liste wurde aktualisiert, nicht der Rückverweis von p2.)

Antwort: ___________________________________________________________________

e) Erkläre in 1–2 Sätzen, was bei einer **vollständigen** bidirektionalen Entfernung zusätzlich gemacht werden müsste:

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 4: Verschachtelte Ausgabe (6 Punkte)

**Thema:** Ausgabe aller Objekte in verschachtelten Collection-Strukturen

Gegeben ist eine Liste von Ärzten, jeder mit einer Liste von Patienten:

```java
List<Arzt> aerzte = new ArrayList<>();

Arzt a1 = new Arzt("Dr. Huber");
Arzt a2 = new Arzt("Dr. Klein");

a1.addPatient(new Patient("Anna"));
a1.addPatient(new Patient("Bernd"));
a2.addPatient(new Patient("Clara"));

aerzte.add(a1);
aerzte.add(a2);

// Ausgabe hier schreiben:
```

a) Schreibe eine **verschachtelte Schleife** (Schleife in Schleife), die folgende Ausgabe erzeugt:

```
Dr. Huber:
  - Anna
  - Bernd
Dr. Klein:
  - Clara
```

```java
// Verschachtelte Schleife:




```

b) Wie viele Schleifendurchläufe gibt es insgesamt (äußere + innere)?

Antwort: ___________________________________________________________________

---

**Viel Erfolg! ✓**

_Tabelle zur Eigenkontrolle (für den Schüler):_

| Aufgabe | Punkte | ✓ |
|---------|--------|---|
| 1. Deklaration bidirektionaler Assoziationen | 5 | |
| 2. Zugriffsmethoden & Zuordnung | 6 | |
| 3. Code-Analyse | 8 | |
| 4. Verschachtelte Ausgabe | 6 | |
| **Gesamt** | **25** | |
