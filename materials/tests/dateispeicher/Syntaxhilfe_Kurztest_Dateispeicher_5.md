# Syntaxhilfe: Kurztest Dateispeicher 5 (JSON-Struktur + JSON-Daten)

**Ziel:** JSON-Dateien als kleine Datenbank nutzen und dabei Struktur und Daten sauber trennen.

---

## 1) Grundprinzip

- `personen.schema.json` = Struktur (Felder, Typen, Pflichtfelder)
- `personen.data.json` = Daten (konkrete Eintraege)

**Vorteile:** weniger Redundanz, bessere Wartbarkeit, einfachere Validierung, leichtere Erweiterbarkeit.

---

## 2) Schema schreiben

```java
String schemaJson = """
{
  "entity": "person",
  "version": 1,
  "fields": [
    {"name":"id","type":"int","required":true},
    {"name":"vorname","type":"string","required":true},
    {"name":"nachname","type":"string","required":true},
    {"name":"alter","type":"int","required":false}
  ]
}
""";

Files.writeString(Paths.get("personen.schema.json"), schemaJson, StandardCharsets.UTF_8);
```

---

## 3) Daten schreiben (ohne Trailing-Komma)

```java
StringBuilder daten = new StringBuilder();
daten.append("{\n  \"personen\": [\n");

for (int i = 0; i < personen.size(); i++) {
    Person p = personen.get(i);

    daten.append("    {")
         .append("\"id\": ").append(p.getId()).append(", ")
         .append("\"vorname\": \"").append(p.getVorname()).append("\", ")
         .append("\"nachname\": \"").append(p.getNachname()).append("\", ")
         .append("\"alter\": ").append(p.getAlter())
         .append("}");

    if (i < personen.size() - 1) {
        daten.append(",");
    }
    daten.append("\n");
}

daten.append("  ]\n}");
Files.writeString(Paths.get("personen.data.json"), daten.toString(), StandardCharsets.UTF_8);
```

---

## 4) Minimal validieren beim Lesen

```java
String schema = Files.readString(Paths.get("personen.schema.json"), StandardCharsets.UTF_8);
String daten = Files.readString(Paths.get("personen.data.json"), StandardCharsets.UTF_8);

if (!schema.contains("\"entity\"") || !schema.contains("\"fields\"")) {
    throw new IOException("Schema ungueltig");
}

if (!daten.contains("\"personen\"") ||
    !daten.contains("\"id\"") ||
    !daten.contains("\"vorname\"") ||
    !daten.contains("\"nachname\"")) {
    throw new IOException("Daten ungueltig");
}
```

---

## 5) Sicher speichern (Backup + atomar)

```java
Path datenPfad = Paths.get("personen.data.json");

if (Files.exists(datenPfad)) {
    Files.copy(
        datenPfad,
        Paths.get("personen.data.json.bak"),
        StandardCopyOption.REPLACE_EXISTING
    );
}

Path tmpPfad = Paths.get("personen.data.json.tmp");
Files.writeString(tmpPfad, neuerInhalt, StandardCharsets.UTF_8);
Files.move(
    tmpPfad,
    datenPfad,
    StandardCopyOption.REPLACE_EXISTING,
    StandardCopyOption.ATOMIC_MOVE
);
```

---

## 6) Typische Fehler

- Struktur und Daten in einer Datei vermischt
- Komma hinter letztem Element (ungueltiges JSON)
- Zeichenkodierung nicht explizit (Sonderzeichen-Probleme)
- Keine Validierung beim Laden
- Kein Backup vor Ueberschreiben

---

## 7) Schnell-Checkliste

- Zwei Dateien vorhanden (`schema`, `data`)
- UTF-8 explizit gesetzt
- Kein Trailing-Komma
- Pflichtfelder geprueft (`id`, `vorname`, `nachname`)
- Backup und atomisches Schreiben vorhanden
