# Syntaxhilfe: Dateispeicher (Lesen & Schreiben)

**Ziel:** Daten dauerhaft in Dateien speichern und wieder laden.

---

## Schreiben mit BufferedWriter

```java
try (BufferedWriter writer = new BufferedWriter(new FileWriter("daten.txt"))) {
    writer.write("Hallo Welt");
    writer.newLine();
    writer.write("Zweite Zeile");
} catch (IOException e) {
    e.printStackTrace();
}
```

---

## Schreiben mit Files.write()

```java
List<String> zeilen = List.of("Zeile 1", "Zeile 2");
Files.write(Paths.get("daten.txt"), zeilen, StandardCharsets.UTF_8);
```

---

## Lesen mit BufferedReader

```java
try (BufferedReader reader = new BufferedReader(new FileReader("daten.txt"))) {
    String zeile;
    while ((zeile = reader.readLine()) != null) {
        System.out.println(zeile);
    }
}
```

---

## Lesen mit Files.readAllLines()

```java
List<String> zeilen = Files.readAllLines(Paths.get("daten.txt"), StandardCharsets.UTF_8);
for (String z : zeilen) {
    System.out.println(z);
}
```

---

## Append (an Datei anhaengen)

```java
try (FileWriter writer = new FileWriter("log.txt", true)) {
    writer.write("Neuer Eintrag\n");
}
```

---

## CSV parsen (einfach)

```java
String[] teile = zeile.split(",");
String name = teile[0];
int menge = Integer.parseInt(teile[1]);
```

---

## Tipps

- Immer try-with-resources nutzen.
- UTF-8 bei Sonderzeichen verwenden.
- Bei CSV: split(",", 3) fuer feste Anzahl Spalten.
