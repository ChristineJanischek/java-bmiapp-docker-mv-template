# Syntaxhilfe: Kurztest Dateispeicher 7 (Properties-Datei key=value)

**Ziel:** Konfigurationsdaten als `key=value` robust speichern und laden.

---

## 1) Grundformat

- Eine Zeile pro Eintrag: `schluessel=wert`
- Beispiel:
  - `db.typ=datei`
  - `db.pfad=./daten/personen.csv`
  - `autosave=true`

---

## 2) Schreiben in Datei

```java
StringBuilder inhalt = new StringBuilder();
inhalt.append("db.typ=datei\n");
inhalt.append("db.pfad=./daten/personen.csv\n");
inhalt.append("autosave=true\n");

Files.writeString(Paths.get("app.properties"), inhalt.toString(), StandardCharsets.UTF_8);
```

---

## 3) Lesen und Parsen

```java
List<String> zeilen = Files.readAllLines(Paths.get("app.properties"), StandardCharsets.UTF_8);
Map<String, String> config = new HashMap<>();

for (String zeile : zeilen) {
    if (zeile.isBlank()) {
        continue;
    }

    if (!zeile.contains("=")) {
        throw new IOException("Ungueltige Zeile: " + zeile);
    }

    String[] teile = zeile.split("=", 2);
    config.put(teile[0].trim(), teile[1].trim());
}
```

---

## 4) Pflichtschluessel pruefen

```java
if (!config.containsKey("db.typ") ||
    !config.containsKey("db.pfad") ||
    !config.containsKey("autosave")) {
    throw new IOException("Pflichtschluessel fehlen");
}
```

---

## 5) Standardwert nutzen

```java
boolean autosave = Boolean.parseBoolean(config.getOrDefault("autosave", "false"));
```

---

## 6) Sicher speichern (Backup + Temp-Datei)

```java
Path ziel = Paths.get("app.properties");
Path backup = Paths.get("app.properties.bak");
Path temp = Paths.get("app.properties.tmp");

if (Files.exists(ziel)) {
    Files.copy(ziel, backup, StandardCopyOption.REPLACE_EXISTING);
}

Files.writeString(temp, neuerInhalt, StandardCharsets.UTF_8);
Files.move(temp, ziel, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
```

---

## 7) Typische Fehler

- Mit `:` statt `=` splitten
- Ohne `split("=", 2)` parsen (Werte mit `=` gehen kaputt)
- Pflichtschluessel nicht pruefen
- Ohne Backup direkt ueberschreiben
- Keine explizite UTF-8-Kodierung
