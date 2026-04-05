# Kurztest: Persistente Datenspeicherung - CSV-Datei lesen/schreiben mit Kopfzeile und Validierung - LOESUNG

**Klasse:** _________________      **Datum:** _________________      **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: CSV-Datei schreiben - Kopfzeile + Datensaetze (5 Punkte)

### Musterloesung

```java
public class CsvPersonenWriter {

    public void speicherePersonen(List<Person> personen, String ordner) throws IOException {
        Path dateiPfad = Paths.get(ordner, "personen.csv");
        Files.createDirectories(Paths.get(ordner));

        StringBuilder csv = new StringBuilder();

        csv.append("id;vorname;nachname;alter\n"); // 1 Punkt

        for (Person p : personen) {
            csv.append(p.getId()).append(";")
               .append(p.getVorname()).append(";")
               .append(p.getNachname()).append(";")
               .append(p.getAlter()).append("\n"); // 2 Punkte
        }

        Files.writeString(dateiPfad, csv.toString(), StandardCharsets.UTF_8); // 2 Punkte
    }
}
```

---

## Aufgabe 2: CSV-Datei lesen und Mindeststruktur pruefen (4 Punkte)

### Musterloesung

```java
public class CsvPersonenReader {

    public List<Person> ladePersonen(String ordner) throws IOException {
        Path dateiPfad = Paths.get(ordner, "personen.csv");

        List<String> zeilen = Files.readAllLines(dateiPfad, StandardCharsets.UTF_8);

        if (zeilen.isEmpty()) { // 1 Punkt
            throw new IOException("CSV-Datei ist leer");
        }

        if (!"id;vorname;nachname;alter".equals(zeilen.get(0))) { // 2 Punkte
            throw new IOException("CSV-Kopfzeile ungueltig");
        }

        return new ArrayList<>(); // 1 Punkt
    }
}
```

---

## Aufgabe 3: Code-Analyse - Wo liegt der Denkfehler? (5 Punkte)

### a) Zwei Probleme

1. Fachlogik (nur Volljaehrige) wird beim Speichern versteckt eingebaut. Das veraendert Daten ohne klaren Auftrag.
2. Kein Kopfzeilenformat, keine feste Struktur, keine Kodierung angegeben. Datei ist uneinheitlich und fehleranfaellig.

### b) Saubere Aufteilung

- `PersonenFilterService`: entscheidet fachlich, welche Personen exportiert werden.
- `CsvPersonenWriter`: schreibt nur das CSV-Format.
- Optional `CsvPersonenValidator`: prueft Zeilen/Kopfzeile vor dem Einlesen.

Bewertung:
- Zwei sinnvolle Probleme: 3 Punkte
- Klares Trennkonzept: 2 Punkte

---

## Aufgabe 4: Fehlersuche - Trennerproblem in CSV (5 Punkte)

### Fehlererklaerung

Die Datei nutzt `;` als Trenner, aber der Code splittet mit `,`. Dadurch entstehen falsche Felder.

### Korrigierte Variante

```java
public Person parseZeile(String zeile) {
    String[] teile = zeile.split(";"); // 2 Punkte

    if (teile.length != 4) { // 2 Punkte
        throw new IllegalArgumentException("CSV-Zeile hat nicht 4 Felder: " + zeile);
    }

    int id = Integer.parseInt(teile[0]);
    String vorname = teile[1];
    String nachname = teile[2];
    int alter = Integer.parseInt(teile[3]);

    return new Person(id, vorname, nachname, alter); // 1 Punkt
}
```

---

## Aufgabe 5: Best Practices fuer CSV-Dateispeicherung (4 Punkte)

### Musterantworten

1. Trennzeichen und Kopfzeile verbindlich festlegen, damit Lesen und Schreiben konsistent bleiben.
2. UTF-8 immer explizit nutzen, damit Umlaute stabil bleiben.
3. Vor dem Parsen Feldanzahl pruefen, um Laufzeitfehler frueh abzufangen.
4. Vor Ueberschreiben Backup anlegen, um Datenverlust zu vermeiden.

Bewertung: je valider Punkt mit kurzer Begruendung 1 Punkt (max. 4)

---

## Aufgabe 6: Kurzaufgabe - Backup vor dem Ueberschreiben (2 Punkte)

### Musterloesung

```java
public void sicherSpeichern(Path csvPfad, String neuerInhalt) throws IOException {
    if (Files.exists(csvPfad)) { // 1 Punkt
        Path backupPfad = Paths.get(csvPfad.toString() + ".bak");
        Files.copy(csvPfad, backupPfad, StandardCopyOption.REPLACE_EXISTING);
    }

    Files.writeString(csvPfad, neuerInhalt, StandardCharsets.UTF_8); // 1 Punkt
}
```

---

## Gesamt-Erwartungshorizont

- **Sehr gut (22-25 Punkte):** CSV sicher aufgebaut, valide geprueft, gute Transferantworten.
- **Gut (18-21 Punkte):** fachlich korrekt mit kleineren technischen Luecken.
- **Ausreichend (13-17 Punkte):** Grundprinzip verstanden, aber unvollstaendig abgesichert.
- **Unter 13 Punkte:** wesentliche Dateispeicher- und Validierungskonzepte fehlen.
