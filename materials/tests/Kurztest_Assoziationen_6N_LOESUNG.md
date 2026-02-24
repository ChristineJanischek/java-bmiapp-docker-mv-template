# Kurztest: Assoziationen & ArrayList - Mannschaft und Spieler - LÖSUNGEN & BEPUNKTUNG

**Dokumenttyp:** Lehrerversion mit Musterlösungen und Bewertungskriterien

---

## Aufgabe 1: ArrayList-Deklaration (3 Punkte)

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
