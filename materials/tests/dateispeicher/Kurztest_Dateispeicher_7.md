# Kurztest: Persistente Datenspeicherung - Schluessel=Wert-Format (Properties) robust speichern und laden

**Klasse:** _________________      **Datum:** _________________      **Zeit: 25 Min | Punkte: 25**

---

## Aufgabe 1: Konfigurationsdatei schreiben (5 Punkte)

**Thema:** Einstellungen in `app.properties` speichern

Die Datei soll folgende Struktur haben:

- `db.typ=datei`
- `db.pfad=./daten/personen.csv`
- `autosave=true`

```java
public class AppConfigWriter {

    public void speichereKonfiguration(String ordner) throws IOException {
        Path pfad = Paths.get(ordner, "app.properties");
        Files.createDirectories(Paths.get(ordner));

        StringBuilder inhalt = new StringBuilder();

        // TODO 1: drei Schluessel=Wert-Zeilen schreiben




        // TODO 2: Datei in UTF-8 schreiben



    }
}
```

---

## Aufgabe 2: Konfiguration lesen und Pflichtschluessel pruefen (4 Punkte)

**Thema:** Dateiqualitaet sichern

```java
public class AppConfigReader {

    public Map<String, String> ladeKonfiguration(String ordner) throws IOException {
        Path pfad = Paths.get(ordner, "app.properties");
        List<String> zeilen = Files.readAllLines(pfad, StandardCharsets.UTF_8); // 1 Punkt

        Map<String, String> config = new HashMap<>();

        // TODO:
        // 1) Leere Zeilen ignorieren
        // 2) Nur Zeilen mit '=' akzeptieren
        // 3) key und value in Map ablegen
        // 4) pruefen, ob db.typ, db.pfad, autosave vorhanden sind
        // 5) bei Fehler IOException werfen






        return config;
    }
}
```

---

## Aufgabe 3: Code-Analyse - Persistenzrisiko erkennen (5 Punkte)

**Thema:** Warum ist dieser Code im Projektbetrieb riskant?

```java
public class UnsicheresSpeichern {

    public void speichern(String datei, String inhalt) throws IOException {
        Files.writeString(Paths.get(datei), inhalt); // direktes Ueberschreiben
    }
}
```

**Fragen:**

a) Nenne zwei Risiken beim direkten Ueberschreiben.

___________________________________________________________________________

___________________________________________________________________________

b) Beschreibe eine robustere Strategie in sinnvollen Schritten.

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 4: Fehlersuche - Falsches Parsen von Schluessel=Wert (5 Punkte)

**Thema:** Trennzeichen korrekt behandeln

```java
public void parseZeile(Map<String, String> config, String zeile) {
    String[] teile = zeile.split(":"); // Fehler?
    config.put(teile[0], teile[1]);
}
```

**Aufgabe:**
1. Erklaere den Fehler.
2. Zeige eine korrigierte Version mit Sicherheitspruefung.

___________________________________________________________________________

___________________________________________________________________________

___________________________________________________________________________

---

## Aufgabe 5: Best Practices fuer Properties-Dateien (4 Punkte)

**Thema:** Verlaessliche Dateispeicherung

Nenne vier Best Practices und begruende jeweils kurz:

1. ________________________________________________________________
2. ________________________________________________________________
3. ________________________________________________________________
4. ________________________________________________________________

---

## Aufgabe 6: Kurzaufgabe - Standardwert setzen (2 Punkte)

**Thema:** Fehlenden Eintrag absichern

Ergaenze die Methode so, dass bei fehlendem `autosave` automatisch `false`
verwendet wird.

```java
public boolean liesAutoSave(Map<String, String> config) {
    // TODO: Standardwert false, falls Schluessel fehlt
}
```

---

## Erwartungshorizont (Kurzueberblick)

- **Sehr gut (22-25 Punkte):** korrektes Format, sichere Pruefungen, praxisnahe Robustheitsideen.
- **Gut (18-21 Punkte):** fachlich richtig mit kleineren Luecken in Randfaellen.
- **Ausreichend (13-17 Punkte):** Grundprinzip vorhanden, aber unsaubere Absicherung.
- **Unter 13 Punkte:** wichtige Dateispeicher-Grundlagen nicht sicher angewendet.
