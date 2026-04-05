# Kurztest: Persistente Datenspeicherung - Schluessel=Wert-Format (Properties) robust speichern und laden - LOESUNG

**Klasse:** _________________      **Datum:** _________________      **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Konfigurationsdatei schreiben (5 Punkte)

### Musterloesung

```java
public class AppConfigWriter {

    public void speichereKonfiguration(String ordner) throws IOException {
        Path pfad = Paths.get(ordner, "app.properties");
        Files.createDirectories(Paths.get(ordner));

        StringBuilder inhalt = new StringBuilder();
        inhalt.append("db.typ=datei\n"); // 1 Punkt
        inhalt.append("db.pfad=./daten/personen.csv\n"); // 1 Punkt
        inhalt.append("autosave=true\n"); // 1 Punkt

        Files.writeString(pfad, inhalt.toString(), StandardCharsets.UTF_8); // 2 Punkte
    }
}
```

---

## Aufgabe 2: Konfiguration lesen und Pflichtschluessel pruefen (4 Punkte)

### Musterloesung

```java
public class AppConfigReader {

    public Map<String, String> ladeKonfiguration(String ordner) throws IOException {
        Path pfad = Paths.get(ordner, "app.properties");
        List<String> zeilen = Files.readAllLines(pfad, StandardCharsets.UTF_8);

        Map<String, String> config = new HashMap<>();

        for (String zeile : zeilen) {
            if (zeile.isBlank()) {
                continue; // 1 Punkt
            }

            if (!zeile.contains("=")) {
                throw new IOException("Ungueltige Konfigurationszeile: " + zeile); // 1 Punkt
            }

            String[] teile = zeile.split("=", 2);
            config.put(teile[0].trim(), teile[1].trim());
        }

        if (!config.containsKey("db.typ") ||
            !config.containsKey("db.pfad") ||
            !config.containsKey("autosave")) {
            throw new IOException("Pflichtschluessel fehlen"); // 1 Punkt
        }

        return config; // 1 Punkt
    }
}
```

---

## Aufgabe 3: Code-Analyse - Persistenzrisiko erkennen (5 Punkte)

### a) Zwei Risiken

1. Bei Absturz waehrend des Schreibens kann die Datei unvollstaendig oder leer werden.
2. Es gibt keine Rueckfallebene (kein Backup), daher droht Datenverlust.

### b) Robustere Strategie

1. Vorhandene Datei als `.bak` sichern.
2. Neuen Inhalt zuerst in eine Temp-Datei schreiben.
3. Temp-Datei mit `move` auf Zieldatei ersetzen (moeglichst atomar).
4. Bei Fehlern Backup behalten und Ausnahme behandeln.

Bewertung:
- Zwei Risiken korrekt: 3 Punkte
- Robustes Vorgehen in Schritten: 2 Punkte

---

## Aufgabe 4: Fehlersuche - Falsches Parsen von Schluessel=Wert (5 Punkte)

### Fehlererklaerung

Properties-Zeilen nutzen `=`, der Code splittet aber mit `:`. Das liefert falsche Ergebnisse oder Exceptions.

### Korrigierte Version

```java
public void parseZeile(Map<String, String> config, String zeile) {
    if (!zeile.contains("=")) { // 2 Punkte
        throw new IllegalArgumentException("Keine key=value Zeile: " + zeile);
    }

    String[] teile = zeile.split("=", 2); // 2 Punkte
    config.put(teile[0].trim(), teile[1].trim()); // 1 Punkt
}
```

---

## Aufgabe 5: Best Practices fuer Properties-Dateien (4 Punkte)

### Musterantworten

1. Einheitliches `key=value`-Format verwenden, um Parsingfehler zu vermeiden.
2. Pflichtschluessel beim Laden validieren, damit Konfiguration vollstaendig ist.
3. UTF-8 explizit nutzen, damit Inhalte systemunabhaengig lesbar bleiben.
4. Vor dem Ueberschreiben Backup und Temp-Datei verwenden, um Datenverlust zu verhindern.

Bewertung: je valider Punkt mit Begruendung 1 Punkt (max. 4)

---

## Aufgabe 6: Kurzaufgabe - Standardwert setzen (2 Punkte)

### Musterloesung

```java
public boolean liesAutoSave(Map<String, String> config) {
    return Boolean.parseBoolean(config.getOrDefault("autosave", "false")); // 2 Punkte
}
```

---

## Gesamt-Erwartungshorizont

- **Sehr gut (22-25 Punkte):** robustes Parsen, Pflichtpruefungen und sichere Speicherstrategie klar beherrscht.
- **Gut (18-21 Punkte):** gute Loesung mit kleineren technischen Ungenauigkeiten.
- **Ausreichend (13-17 Punkte):** Basisideen vorhanden, Absicherung teils lueckenhaft.
- **Unter 13 Punkte:** wesentliche Konzepte fuer verlaessliche Dateispeicherung fehlen.
