# Grundgerüst einer Java-Klasse

Dieses Dokument beschreibt die grundlegenden Bausteine zur Implementierung einer Java-Klasse. Es dient als Leitfaden für Schüler, um die wichtigsten Bestandteile einer Klasse zu verstehen und korrekt umzusetzen.

## 0. Deklaration der Klasse
- Die Deklaration legt den Namen und die Sichtbarkeit der Klasse fest.
- Sie steht immer am Anfang des Quellcodes.

**Beispiel:**
```java
public class Bmirechner {
    // ...
}
```

## 1. Attribute
- Attribute sind Variablen, die den Zustand (Daten) eines Objekts speichern.
- Sie werden meist als `private` deklariert, um das Prinzip der Datenkapselung zu wahren.

**Beispiel:**
```java
private double gewicht;
private double groesse;
```

## 2. Konstruktoren
- Konstruktoren sind spezielle Methoden, die beim Erzeugen eines Objekts aufgerufen werden.
- Sie initialisieren die Attribute der Klasse.

**Beispiel:**
```java
public Bmirechner(double gewicht, double groesse) {
    this.gewicht = gewicht;
    this.groesse = groesse;
}
```

## 3. Getter und Setter
- Getter-Methoden geben den Wert eines Attributs zurück.
- Setter-Methoden setzen den Wert eines Attributs.
- Sie ermöglichen kontrollierten Zugriff auf die Attribute.

**Beispiel:**
```java
public double getGewicht() {
    return gewicht;
}

public void setGewicht(double gewicht) {
    this.gewicht = gewicht;
}
```

## 4. Sonstige Methoden
- Methoden wie `toString()`, Berechnungen oder Logikfunktionen gehören ebenfalls zum Grundgerüst.
- Die Methode `toString()` gibt eine textuelle Repräsentation des Objekts zurück.

**Beispiel:**
```java
@Override
public String toString() {
    return "Bmirechner [gewicht=" + gewicht + ", groesse=" + groesse + "]";
}
```

## 5. Beispiel für eine vollständige Klasse
```java
public class Bmirechner {
    private double gewicht;
    private double groesse;

    public Bmirechner(double gewicht, double groesse) {
        this.gewicht = gewicht;
        this.groesse = groesse;
    }

    public double getGewicht() {
        return gewicht;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    public double getGroesse() {
        return groesse;
    }

    public void setGroesse(double groesse) {
        this.groesse = groesse;
    }

    @Override
    public String toString() {
        return "Bmirechner [gewicht=" + gewicht + ", groesse=" + groesse + "]";
    }
}
```

## 6. Zusammenfassung
- Eine Klasse besteht aus Attributen, Konstruktor(en), Getter/Setter und weiteren Methoden.
- Die Einhaltung dieser Struktur erleichtert das Verständnis und die Wartung des Codes.
