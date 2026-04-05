# Kurztest: Assoziationen & ArrayList - Mannschaft und Spieler - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: ArrayList-Deklaration (3 Punkte)

### Aufgabenstellung

**Thema:** Datenstruktur für eine Fußball-Mannschaft

Schreibe die Deklaration und Initialisierung einer ArrayList, die Spieler-Objekte speichert.

```java
// Hier aufschreiben:


```

Erkläre in 1-2 Sätzen, warum man hier ArrayList statt Array verwendet.

___________________________________________________________________________

___________________________________________________________________________

---

### Musterlösung

```java
List<Spieler> spieler = new ArrayList<Spieler>();
```

oder

```java
private ArrayList<Spieler> spieler = new ArrayList<>();
```

**Erklärung (1 Punkt):**
```
ArrayList wird verwendet, weil die Anzahl der Spieler nicht bekannt ist und sich 
dynamisch ändern kann (Verletzungen, Transfers, etc.). Arrays haben eine feste Größe.
```

### Bewertung (3 Punkte)

**Deklaration (2 Punkte):**
- ✓ `List<Spieler> spieler = new ArrayList<Spieler>();` → 2 Punkte
- ✓ `ArrayList<Spieler> spieler = new ArrayList<>();` → 2 Punkte
- ~ Fehlende Generics → 1 Punkt
- ✗ Array statt ArrayList → 0 Punkte

**Erklärung (1 Punkt):**
- ✓ Dynamische Größe oder flexible Anzahl erwähnt → 1 Punkt
- ~ Ansatz erkennbar → 0,5 Punkte
- ✗ Falsch → 0 Punkte

---

## Aufgabe 2: ArrayList-Methoden (4 Punkte)

### Aufgabenstellung

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

### Musterlösung (Zuordnung)

| Methode | Richtiger Buchstabe |
|---------|----------------------|
| `add(Spieler)` | **(b)** |
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

### Musterlösungen

**a) Ausgabe (2 Punkte)**

```
Spieler im Kader: 3
```

**Bewertung:**
- ✓ `3` oder `Spieler im Kader: 3` → 2 Punkte
- ✗ andere Zahl → 0 Punkte

---

**b) Erster Spieler (1,5 Punkte)**

```
Müller
```

**Erklärung:** Index 0 ist das erste Element: s1 = "Müller"

**Bewertung:**
- ✓ `Müller` oder `Spieler 1: Müller` → 1,5 Punkte
- ✗ anderer Name → 0 Punkte

---

**c) Erklärung `.getSpieler().get(0)` (1,5 Punkte)**

**Musterlösung:**
```
getSpieler() gibt die Liste aller Spieler zurück. 
get(0) greift auf das Element an Index 0 zu, also den ersten Spieler in der Liste.
```

**Bewertung:**
- **1,5 Punkte:** Beide Teile erklärt (Getter + Index-Zugriff)
- **1 Punkt:** Teilweise erklärt
- **0,5 Punkte:** Grundidee erkannt
- **0 Punkte:** Falsch oder nicht beantwortet

---

## Aufgabe 4: Schleife & Ausgabe (5 Punkte)

### Aufgabenstellung

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

### Musterlösungen

**Grundaufgabe (4 Punkte):**

```java
List<Spieler> spieler = mannschaft.getSpieler();

for (Spieler s : spieler) {
    System.out.println(s.getName() + " - " + s.getPosition());
}
```

oder

```java
List<Spieler> spieler = mannschaft.getSpieler();

for (int i = 0; i < spieler.size(); i++) {
    Spieler s = spieler.get(i);
    System.out.println(s.getName() + " - " + s.getPosition());
}
```

**Bewertung (4 Punkte):**
- **4 Punkte:** Schleife korrekt, alle Spieler ausgegeben mit Name und Position
- **3 Punkte:** Schleife funktioniert, aber unvollständige Ausgabe
- **2 Punkte:** Grundkonzept erkannt, syntaktische Fehler
- **1 Punkt:** Versuch offensichtlich, viele Fehler
- **0 Punkte:** Falsch oder nicht beantwortet

---

**Bonus (+1 Punkt):**

```java
for (int i = 0; i < spieler.size(); i++) {
    System.out.println(spieler.get(i).getNummer() + ". " + 
                       spieler.get(i).getName() + " (" + 
                       spieler.get(i).getPosition() + ")");
}
```

**Bewertung:**
- ✓ Nummer + Name + Position ausgegeben → +1 Punkt
- ~ Ähnliche sinnvolle Ausgabe → +1 Punkt

---

## Aufgabe 5: Methode verstehen (4 Punkte)

### Aufgabenstellung

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

### Musterlösung

**a) Methodensignatur (1,5 Punkte)**

```java
public List<Spieler> getSpielerMitPosition(String position)
```

oder

```java
public ArrayList<Spieler> getSpielerMitPosition(String position)
```

**Bewertung:**
- ✓ Korrekte Signatur mit List/ArrayList und Parameter → 1,5 Punkte
- ~ `void` statt List → 0 Punkte
- ~ Parameter fehlt → 0 Punkte

---

**b) Erklärung (2,5 Punkte)**

**Musterlösung:**
```
Die Methode iteriert durch alle Spieler und prüft, ob ihre Position dem 
angegebenen Parameter entspricht. Alle passenden Spieler werden in einer 
neuen Liste gesammelt und zurückgegeben.
```

**Bewertung:**
- **2,5 Punkte:** Iteration + Vergleich + Rückgabe erklärt
- **1,5 Punkte:** Grundidee erkannt, aber unvollständig
- **0,5 Punkte:** Ansatz sichtbar
- **0 Punkte:** Falsch

---

## Aufgabe 6: Fehlersuche & Analyse (4 Punkte)

### Aufgabenstellung

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

### Musterlösungen

**a) Fehler 1 - Direkter ArrayList-Zugriff (1 Punkt)**

**Musterlösung:**
```
Das Attribut 'spieler' ist private und nicht von außen erreichbar. 
Man muss die öffentliche Methode addSpieler() verwenden.
```

**Bewertung:**
- ✓ Private + getter/Methode erwähnt → 1 Punkt
- ~ Encapsulation-Verletzung → 0,5 Punkte
- ✗ Falsch → 0 Punkte

---

**b) Fehler 2 - IndexOutOfBoundsException (1,5 Punkte)**

**Musterlösung:**
```
Der Index 15 existiert nicht (bei 11 Spielern sind gültige Indizes 0-10). 
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
mannschaft.addSpieler(s1);
```

**Bewertung:**
- ✓ `mannschaft.addSpieler(s1)` → 1,5 Punkte
- ~ `mannschaft.getSpieler().add(s1)` → 1 Punkt
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

**Test erstellt:** Version 1.0 (Feb 2026)  
**Zielgruppe:** Schüler nach Lerneinheit 1:N-Assoziationen (Mannschaft/Spieler)  
**Voraussetzungen:** ArrayList, Generics, for-Schleifen, Objektverwaltung bekannt
