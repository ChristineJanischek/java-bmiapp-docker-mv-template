````markdown
# Methoden überschreiben in Java (Kurzguide)

Diese Datei erklärt kurz, wie man Methoden in Java überschreibt (override), warum das wichtig ist
und zeigt konkrete Beispiele — mit Fokus auf `toString()` und einer Verbindung zur Polymorphie.

---

## 1) Warum überschreiben?

- In Java erben alle Klassen von `java.lang.Object`. Manche Methoden (z. B. `toString()`, `equals()`, `hashCode()`) sind
  allgemeine Helfer, die für aussagekräftige Debug- und Testausgaben angepasst werden sollten.
- Überschreiben (`override`) ermöglicht es, die Standard-Implementierung durch eine projektspezifische
  Ausgabe zu ersetzen (z. B. statt `start.Bmirechner@1a2b3c` eine lesbare Form wie `BMI: 22.9, Kategorie: Normalgewicht`).

---

## 2) Die @Override-Annotation

Die Annotation `@Override` ist optional, aber empfohlen: sie macht klar, dass die Methode eine Basisklassen-Methode
überschreibt und löst einen Compilerfehler aus, wenn Signatur oder Name nicht zu einer Basismethode passen.

Beispiel:

```java
@Override
public String toString() {
    return "BMI: " + this.ergebnis + ", Kategorie: " + this.kategorie;
}
```

Wenn die Signatur versehentlich nicht mit der Basismethode übereinstimmt, meldet der Compiler einen Fehler — das
hilft Tippfehler oder falsche Methodensignaturen zu vermeiden.

---

## 3) Beispiel: `Bmirechner.toString()`

Vollständiges Beispiel (aus dem Projekt):

```java
public class Bmirechner {
    private double ergebnis;
    private String kategorie;

    @Override
    public String toString(){
      return String.format("BMI: %.2f, Kategorie: %s", this.ergebnis, this.kategorie);
    }
}
```

Hinweis: Bei der Formatierung mit `String.format` lässt sich die Ausgabe auf sinnvolle Nachkommastellen begrenzen.

---

## 4) Verwendung in Tests

Tests und Debug-Ausgaben sollten bevorzugt `toString()` verwenden, weil:

- Die Ausgabe einheitlich und lesbar ist.
- Änderungen an der Darstellung zentral in `toString()` erfolgen und nicht in jedem einzelnen Test.

Beispiel in einem Testprogramm:

```java
Bmirechner r = new Bmirechner();
r.berechne(70, 1.75);
r.interpretiere();
System.out.println(r); // ruft r.toString() auf
```

---

## 5) Kurzer Exkurs: Polymorphie und Überschreiben

Polymorphie bedeutet in Java, dass ein Methodenaufruf zur Laufzeit die passende Implementierung verwendet —
insbesondere bei Vererbung und Interfaces. Überschreiben ist eine Form der Polymorphie:

- Beispiel: Eine Basisklasse `Tier` hat `public String gibLaut()`; `Hund` und `Katze` überschreiben `gibLaut()`.
- Wenn du `Tier t = new Hund(); t.gibLaut();` aufrufst, wird zur Laufzeit die `Hund`-Implementierung ausgeführt.

Für dieses Projekt: `toString()` ist in `Object` definiert und wird polymorphisch auf dem tatsächlichen Objekt aufgerufen —
deshalb ist `System.out.println(obj)` immer sicher und liefert die passende Darstellung für jedes Objekt, das `toString()` überschreibt.

---

## 6) Best Practices

- Überschreibe nur das, was sinnvoll ist (keine sensiblen Daten wie Passwörter im toString()).
- Nutze `@Override` zur Sicherheit.
- Verwende `String.format` für kontrollierte Formatierung.
- Dokumentiere besondere Darstellungsentscheidungen in einer kurzen Kommentarzeile in der Klasse.

---

### Weiterführende Links
- Polymorphie: [POLYMORPHIE.md](./POLYMORPHIE.md)
- MVC-Konzept: [MVC_KONZEPT.md](./MVC_KONZEPT.md)
- Unit Testing: [UNIT_TESTING.md](./UNIT_TESTING.md)

````
