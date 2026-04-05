# Kurztest: Persistente Datenspeicherung - CSV-Datei lesen/schreiben mit Kopfzeile und Validierung

**Klasse:** _________________      **Datum:** _________________      **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: CSV-Datei schreiben - Kopfzeile + Datensaetze (5 Punkte)

**Thema:** Personenliste als CSV-Datei exportieren

Die Klasse soll eine Datei `personen.csv` schreiben. Format:

`id;vorname;nachname;alter`

```java
public class CsvPersonenWriter {

    public void speicherePersonen(List<Person> personen, String ordner) throws IOException {
        Path dateiPfad = Paths.get(ordner, "personen.csv");
        Files.createDirectories(Paths.get(ordner));

        StringBuilder csv = new StringBuilder();

        // TODO 1: Kopfzeile schreiben



        // TODO 2: Datensaetze schreiben
        // Hinweis: pro Person eine Zeile im Format id;vorname;nachname;alter
        for (Person p : personen) {




        }

        // TODO 3: Datei mit UTF-8 schreiben



    }
}
```

---

## Aufgabe 2: CSV-Datei lesen und Mindeststruktur pruefen (4 Punkte)

**Thema:** Plausibilitaet beim Laden sicherstellen

```java
public class CsvPersonenReader {

    public List<Person> ladePersonen(String ordner) throws IOException {
        Path dateiPfad = Paths.get(ordner, "personen.csv");

        List<String> zeilen = Files.readAllLines(dateiPfad, StandardCharsets.UTF_8); // 1 Punkt

        // TODO:
        // 1) pruefen, ob Datei nicht leer ist
        // 2) pruefen, ob Kopfzeile exakt id;vorname;nachname;alter ist
        // 3) bei Fehler IOException werfen
        // 4) vorerst leere Liste zurueckgeben





    }
}
```

---

## Aufgabe 3: Code-Analyse - Wo liegt der Denkfehler? (5 Punkte)

**Thema:** Trennung von Datei- und Fachlogik

Gegeben ist folgender Code:

```java
public class UnsaubereCsvLogik {

    public void speichern(List<Person> personen, String datei) throws IOException {
        String inhalt = "";

        for (Person p : personen) {
            // Speichern + Fachentscheidung direkt gemischt
            if (p.getAlter() >= 18) {
                inhalt += p.getVorname() + ";" + p.getNachname() + "\n";
            }
        }

        Files.writeString(Paths.get(datei), inhalt);
    }
}
```

**Fragen:**

a) Nenne zwei konkrete Probleme des Codes (Wartbarkeit / Datenqualitaet).

___________________________________________________________________________

___________________________________________________________________________

b) Wie kann man den Code sauber aufteilen (mindestens zwei Klassen oder Methodenideen)?

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 4: Fehlersuche - Trennerproblem in CSV (5 Punkte)

**Thema:** Einlesefehler durch falsches Split-Muster

```java
public Person parseZeile(String zeile) {
    String[] teile = zeile.split(","); // Fehler?

    int id = Integer.parseInt(teile[0]);
    String vorname = teile[1];
    String nachname = teile[2];
    int alter = Integer.parseInt(teile[3]);

    return new Person(id, vorname, nachname, alter);
}
```

**Aufgabe:**
1. Erklaere den Fehler.
2. Zeige eine korrigierte Variante inklusive einfacher Feldanzahl-Pruefung.

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Best Practices fuer CSV-Dateispeicherung (4 Punkte)

**Thema:** Robuste Umsetzung ohne Internet-Hilfen

Nenne vier Best Practices und begruende jeweils kurz:

1. ________________________________________________________________
2. ________________________________________________________________
3. ________________________________________________________________
4. ________________________________________________________________

---

## Aufgabe 6: Kurzaufgabe - Backup vor dem Ueberschreiben (2 Punkte)

**Thema:** Sicherheitskopie bei bestehender Datei

Ergaenze die Methode so, dass vor dem Schreiben von `personen.csv` eine Kopie
`personen.csv.bak` erstellt wird (falls Datei vorhanden).

```java
public void sicherSpeichern(Path csvPfad, String neuerInhalt) throws IOException {
    // TODO:
    // 1) Falls csvPfad existiert: Backup anlegen
    // 2) neuen Inhalt in UTF-8 schreiben
}
```

---

## Erwartungshorizont (Kurzueberblick)

- **Sehr gut (22-25 Punkte):** korrekte CSV-Struktur, sinnvolle Validierung, sauberer Architekturtransfer.
- **Gut (18-21 Punkte):** weitgehend richtig, kleinere Ungenauigkeiten bei Fehlerfaellen.
- **Ausreichend (13-17 Punkte):** Grundideen da, aber Validierung/Robustheit lueckenhaft.
- **Unter 13 Punkte:** zentrale Punkte (Format, Pruefung, Sicherung) nicht sicher umgesetzt.
