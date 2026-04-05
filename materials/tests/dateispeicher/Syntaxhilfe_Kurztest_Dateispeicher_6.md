# Syntaxhilfe: Kurztest Dateispeicher 6 (CSV mit Kopfzeile und Validierung)

**Ziel:** CSV-Dateien robust schreiben und lesen, ohne externe Bibliotheken.

---

## 1) CSV-Grundformat

- Erste Zeile: Kopfzeile (z.B. `id;vorname;nachname;alter`)
- Danach pro Datensatz genau eine Zeile
- Einheitliches Trennzeichen (hier `;`)

---

## 2) CSV schreiben

```java
StringBuilder csv = new StringBuilder();
csv.append("id;vorname;nachname;alter\n");

for (Person p : personen) {
    csv.append(p.getId()).append(";")
       .append(p.getVorname()).append(";")
       .append(p.getNachname()).append(";")
       .append(p.getAlter()).append("\n");
}

Files.writeString(Paths.get("personen.csv"), csv.toString(), StandardCharsets.UTF_8);
```

---

## 3) CSV lesen und Kopfzeile pruefen

```java
List<String> zeilen = Files.readAllLines(Paths.get("personen.csv"), StandardCharsets.UTF_8);

if (zeilen.isEmpty()) {
    throw new IOException("Datei ist leer");
}

if (!"id;vorname;nachname;alter".equals(zeilen.get(0))) {
    throw new IOException("Ungueltige Kopfzeile");
}
```

---

## 4) Zeilen sicher parsen

```java
String[] teile = zeile.split(";");
if (teile.length != 4) {
    throw new IllegalArgumentException("Falsche Feldanzahl");
}

int id = Integer.parseInt(teile[0]);
String vorname = teile[1];
String nachname = teile[2];
int alter = Integer.parseInt(teile[3]);
```

---

## 5) Backup vor Ueberschreiben

```java
Path csvPfad = Paths.get("personen.csv");

if (Files.exists(csvPfad)) {
    Files.copy(csvPfad, Paths.get("personen.csv.bak"), StandardCopyOption.REPLACE_EXISTING);
}

Files.writeString(csvPfad, neuerInhalt, StandardCharsets.UTF_8);
```

---

## 6) Typische Fehler

- Mit falschem Trenner splitten (`,` statt `;`)
- Kopfzeile vergessen
- UTF-8 nicht explizit angeben
- Feldanzahl nicht pruefen
- Ohne Backup ueberschreiben

---

## 7) Mini-Check

- Kopfzeile vorhanden
- Einheitlicher Trenner
- UTF-8 gesetzt
- `teile.length` geprueft
- Backup vorhanden
