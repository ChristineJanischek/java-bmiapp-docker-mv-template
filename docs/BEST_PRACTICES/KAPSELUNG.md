# Kapselung in Java (Encapsulation) und Zugriffsmodifikatoren

Dieses Dokument fasst die grundlegenden Fakten zur Kapselung (Encapsulation) in Java zusammen und zeigt, wie Zugriffsmodifikatoren korrekt angewendet werden. Ziel ist es, Daten zu schützen, Implementierungsdetails zu verbergen und nur notwendige Schnittstellen freizugeben (Prinzip der geringsten Berechtigung).

## 1) Was bedeutet Kapselung?
- Trennung zwischen interner Repräsentation (Attribute, Implementierungsdetails) und externer Sicht (öffentliche API).
- Schutz vor unkontrolliertem Zugriff und unerwarteten Zustandsänderungen.
- Erhöht Verständlichkeit, Wartbarkeit, Testbarkeit und Sicherheit.

## 2) Zugriffsmodifikatoren im Überblick
- public: Überall sichtbar (innerhalb und außerhalb des Pakets).
- protected: Sichtbar in derselben Klasse, im selben Paket und in Subklassen (auch aus anderen Paketen).
- (kein Modifikator) „package-private“: Sichtbar nur innerhalb desselben Pakets.
- private: Sichtbar nur innerhalb der Klasse.

Richtschnur: So restriktiv wie möglich, so offen wie nötig.

## 3) Typische Verwendungsmuster
- Attribute fast immer private deklarieren.
- Zugriff über Getter/Setter oder explizite Methoden (Intent ausdrücken, z. B. increaseQuantity()).
- In Settern/Konstruktoren validieren (keine ungültigen Zustände zulassen).
- Unveränderliche (immutable) Objekte bevorzugen: final-Attribute, keine Setter, nur lesende Getter.
- Sammlungen kapseln: Unmodifiable-View oder defensive Kopien zurückgeben.

## 4) Beispiele

### 4.1) Einfache Klasse mit Kapselung und Validierung
```java
public class Person {
    private String name;      // gekapselt
    private int alter;        // gekapselt

    public Person(String name, int alter) {
        setName(name);        // Validierung zentral nutzen
        setAlter(alter);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name darf nicht leer sein");
        }
        this.name = name.trim();
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        if (alter < 0) {
            throw new IllegalArgumentException("Alter darf nicht negativ sein");
        }
        this.alter = alter;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', alter=" + alter + "}";
    }
}
```

### 4.2) Immutable Variante
```java
public final class Adresse {
    private final String strasse;
    private final String stadt;

    public Adresse(String strasse, String stadt) {
        if (strasse == null || strasse.isBlank()) throw new IllegalArgumentException("Straße erforderlich");
        if (stadt == null || stadt.isBlank())     throw new IllegalArgumentException("Stadt erforderlich");
        this.strasse = strasse.trim();
        this.stadt = stadt.trim();
    }

    public String getStrasse() { return strasse; }
    public String getStadt()   { return stadt; }

    @Override
    public String toString() {
        return strasse + ", " + stadt;
    }
}
```

### 4.3) Sammlungen kapseln (defensive Kopien / unmodifiable)
```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kurs {
    private final List<String> teilnehmer = new ArrayList<>();

    public void fuegeTeilnehmerHinzu(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name erforderlich");
        teilnehmer.add(name.trim());
    }

    // Nur lesender Zugriff nach außen
    public List<String> getTeilnehmer() {
        return Collections.unmodifiableList(teilnehmer);
    }
}
```

### 4.4) package-private und protected gezielt nutzen
```java
// package-private Klasse (kein Modifikator): nur im Paket sichtbar
class InternerHelper {
    static int add(int a, int b) { return a + b; }
}

public class Basis {
    protected void nurFuerSubklassen() {
        // für Vererbung gedacht
    }
}
```

## 5) Best Practices (Kurzcheck)
- Felder private, Methoden so restriktiv wie möglich.
- Nur notwendige API als public offenlegen.
- Invariante in Konstruktoren/Settern validieren; fehlerhafte Zustände verhindern.
- Für unveränderliche Objekte: final-Felder, keine Setter.
- Bei Sammlungen: unmodifiable oder Kopien zurückgeben.
- Keine Interna (mutable Referenzen) nach außen durchreichen.
- Schnittstellen klar benennen: intent-revealing methods statt beliebiger Setter.

## 6) Häufige Fehler
- public-Attribute: umgehen die Kapselung komplett.
- Getter geben veränderbare interne Objekte direkt zurück (List, Date, Arrays).
- Zu breite public-APIs, die Erweiterung/Wartung erschweren.
- protected ohne echten Vererbungsbedarf (führt zu enger Kopplung). 

## 7) Zusammenhang mit MVC und Secure Coding
- Model: Zustand kapseln, nur klar definierte Operationen erlauben; Validierung und Invarianten einhalten.
- View: Kein direkter Zugriff auf Model-Felder; kommuniziert über Controller/öffentliche API.
- Controller: Orchestriert Logik, validiert Eingaben, setzt nur erlaubte Änderungen um.
- Secure Coding: Principle of Least Privilege konsequent umsetzen; Angriffsfläche reduzieren.

## 8) Mini-Referenz der Modifikatoren
- class: public oder package-private
- Feld/Attribut: private (Standard), ggf. protected/public falls API nötig
- Methode: public für API, protected für Vererbung, package-private für Paket-intern, private für intern
- Konstruktor: je nach Fabrik-/Erzeugungsmuster (z. B. private bei Factory-Methoden)

## 9) Lernaufgaben (optional)
- Mache aus einer bestehenden Klasse eine immutable Variante.
- Kapsle eine List<E> korrekt (unmodifiable + Methoden zum Hinzufügen/Entfernen).
- Reduziere die Sichtbarkeit einer Klasse von public auf package-private: Was ändert sich?

## 10) Quiz (5 Fragen)
Beantworte kurz. Markiere jeweils die richtige(n) Antwort(en).

1. Welcher Modifikator ist am restriktivsten?
    - a) public
    - b) protected
    - c) (kein Modifikator) package-private
    - d) private

2. Wofür steht „package-private“?
    - a) Sichtbar in derselben Klasse und in Subklassen anderer Pakete
    - b) Nur im selben Paket sichtbar
    - c) Überall sichtbar
    - d) Nur innerhalb der Klasse sichtbar

3. Welche Aussage ist korrekt? (Mehrfachauswahl möglich)
    - a) Attribute sollten in der Regel private sein
    - b) Getter dürfen niemals verwendet werden
    - c) Setter können Validierung enthalten
    - d) Kapselung erhöht die Wartbarkeit des Codes

4. Welche Rückgabe ist bei Sammlungen aus Kapselungssicht vorzuziehen?
    - a) Die interne, veränderbare Liste
    - b) Eine unveränderliche Sicht (unmodifiable)
    - c) Immer null
    - d) Eine defensive Kopie

5. Wofür eignet sich protected?
    - a) Für API-Methoden, die von jedem aufgerufen werden sollen
    - b) Für Methoden, die nur Subklassen (und Paket-Mitglieder) sehen dürfen
    - c) Für komplett interne Hilfsmethoden
    - d) Für konstante Werte

Lösungsschlüssel: 1-d, 2-b, 3-a/c/d, 4-b/d, 5-b

## 11) Mini-Übungsaufgabe: Kapselung refactoren

Gegeben ist folgende (absichtlich schlechte) Klasse:

```java
public class Konto {
     public String inhaber;
     public double saldo;
}
```

Aufgabe:
- Kapsle die Felder (private) und füge sinnvolle Getter/Setter hinzu.
- Validiere, dass saldo nie negativ gesetzt werden kann.
- Füge eine Methode `einzahlen(double betrag)` hinzu, die validiert und das Saldo erhöht.
- Füge eine Methode `abheben(double betrag)` hinzu, die nur abhebt, wenn genügend Guthaben vorhanden ist.
- Optional: Mache `Konto` teilweise oder vollständig immutable (z. B. kein Setter für inhaber, nur im Konstruktor setzen).

Zielkriterien (Akzeptanz):
- Kein direkter Zugriff von außen auf Felder möglich.
- Ungültige Zustände (negatives Saldo) werden verhindert.
- Intention der API klar: Einzahlen/Abheben statt willkürlichem Setzen.

## 12) Musterlösung: Konto (eine mögliche Umsetzung)

```java
public class Konto {
    private String inhaber;    // gekapselt
    private double saldo;      // gekapselt, nie negativ

    public Konto(String inhaber, double startSaldo) {
        setInhaber(inhaber);   // Validierung wiederverwenden
        if (startSaldo < 0) {
            throw new IllegalArgumentException("Startsaldo darf nicht negativ sein");
        }
        this.saldo = startSaldo;
    }

    public String getInhaber() {
        return inhaber;
    }

    public void setInhaber(String inhaber) {
        if (inhaber == null || inhaber.isBlank()) {
            throw new IllegalArgumentException("Inhaber darf nicht leer sein");
        }
        this.inhaber = inhaber.trim();
    }

    public double getSaldo() {
        return saldo;
    }

    // kein setSaldo(): Saldo wird nur über fachliche Methoden verändert

    public void einzahlen(double betrag) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Einzahlungsbetrag muss > 0 sein");
        }
        saldo += betrag;
    }

    public boolean abheben(double betrag) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Abhebebetrag muss > 0 sein");
        }
        if (saldo >= betrag) {
            saldo -= betrag;
            return true;
        }
        return false; // nicht genug Guthaben
    }

    @Override
    public String toString() {
        return "Konto{" +
               "inhaber='" + inhaber + '\'' +
               ", saldo=" + saldo +
               '}';
    }
}
```

Optional (immutable Variante):
- `inhaber` als `final` setzen und nur im Konstruktor setzen, keinen Setter anbieten.
- Änderungen am Saldo weiterhin nur über `einzahlen`/`abheben` zulassen (kontrollierte Mutation). Eine vollständig immutable Konto-Klasse ist wegen der Semantik „Zustandsänderung“ unüblich; hier wäre dann ein neues Objekt mit geändertem Saldo zurückzugeben.

---

Hinweis: Das Quiz gibt es auch als PDF (zum Ausfüllen am Rechner oder Ausdrucken):
- Datei: [materials/quiz_kapselung.pdf](./materials/quiz_kapselung.pdf)
