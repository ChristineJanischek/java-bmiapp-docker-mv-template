# Kontrollstrukturen in Java: Schleifen und Alternativen

Dieses Dokument erklärt die grundlegenden Kontrollstrukturen in Java, wie sie in Methoden (z. B. `berechne`, `interpretiere`) verwendet werden, und gibt Beispiele für typische Anwendungsfälle.

## 1. Was sind Kontrollstrukturen?
- Kontrollstrukturen steuern den Ablauf eines Programms.
- Sie bestimmen, ob und wie oft Anweisungen ausgeführt werden.
- Die wichtigsten Typen sind Alternativen (if/else, switch) und Schleifen (for, while, do-while).

## 2. Alternativen (Verzweigungen)

### 2.1 if-else
- Mit `if` und `else` kann das Programm je nach Bedingung unterschiedliche Wege gehen.

**Beispiel:**
```java
if (bmi >= 25) {
    System.out.println("Übergewicht");
} else {
    System.out.println("Normalgewicht oder Untergewicht");
}
```

### 2.2 else if-Kaskade
- Mehrere Bedingungen können nacheinander geprüft werden.

**Beispiel (aus interpretiere):**
```java
if (bmi >= 40) {
    kategorie = "Adipositas Grad III";
} else if (bmi >= 35) {
    kategorie = "Adipositas Grad II";
} else if (bmi >= 30) {
    kategorie = "Adipositas Grad I";
} else if (bmi >= 25) {
    kategorie = "Prädipositas";
} else if (bmi >= 18.5) {
    kategorie = "Normalgewicht";
} else if (bmi >= 17) {
    kategorie = "Leichtes Untergewicht";
} else if (bmi >= 16) {
    kategorie = "Mäßiges Untergewicht";
} else {
    kategorie = "Starkes Untergewicht";
}
```

### 2.3 switch-case
- Für feste Werte oder Enum-Typen kann `switch` verwendet werden.

**Beispiel:**
```java
switch (wochentag) {
    case 1: System.out.println("Montag"); break;
    case 2: System.out.println("Dienstag"); break;
    // ...
    default: System.out.println("Unbekannt");
}
```

## 3. Schleifen

### 3.1 for-Schleife
- Wiederholt Anweisungen eine feste Anzahl von Malen.

**Beispiel:**
```java
for (int i = 0; i < 5; i++) {
    System.out.println("Durchlauf: " + i);
}
```

### 3.2 while-Schleife
- Wiederholt Anweisungen, solange eine Bedingung erfüllt ist.

**Beispiel:**
```java
int i = 0;
while (i < 5) {
    System.out.println("Durchlauf: " + i);
    i++;
}
```

### 3.3 do-while-Schleife
- Führt Anweisungen mindestens einmal aus, dann solange die Bedingung gilt.

**Beispiel:**
```java
int i = 0;
do {
    System.out.println("Durchlauf: " + i);
    i++;
} while (i < 5);
```

## 4. Bezug zu Methoden
- In der Methode `berechne` wird meist keine Schleife, sondern eine Berechnung ausgeführt.
- In der Methode `interpretiere` werden Alternativen genutzt, um die BMI-Kategorie zu bestimmen.
- Schleifen sind nützlich, wenn z. B. mehrere Werte verarbeitet oder wiederholt abgefragt werden sollen.

## 5. Best Practices
- Bedingungen klar und verständlich formulieren.
- else if-Kaskaden für Bereichsprüfungen (wie BMI) nutzen.
- Schleifen nur verwenden, wenn wirklich Wiederholungen nötig sind.
- Endlosschleifen vermeiden (z. B. while(true) ohne Abbruch).

## 6. Übungsaufgabe
- Schreibe eine Methode, die alle BMI-Werte von 15 bis 45 in 1er-Schritten durchläuft und für jeden Wert die Kategorie ausgibt (for-Schleife + if-else).

**Beispiel:**
```java
for (int bmi = 15; bmi <= 45; bmi++) {
    String kategorie = interpretiereBmi(bmi);
    System.out.println("BMI: " + bmi + " -> " + kategorie);
}
```

## 7. Zusammenfassung
- Kontrollstrukturen sind das Fundament für die Logik in Methoden.
- Alternativen (if/else) steuern Entscheidungen, Schleifen Wiederholungen.
- Die BMI-Interpretation ist ein typisches Beispiel für Alternativen in der Praxis.
