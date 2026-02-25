# Syntaxhilfe: Assoziationen (1:N)

**Ziel:** Eine Klasse verwaltet mehrere Objekte einer anderen Klasse (z.B. Mannschaft -> Spieler).

---

## Grundstruktur (1:N)

```java
import java.util.ArrayList;
import java.util.List;

public class Mannschaft {
    private String name;
    private List<Spieler> spielerListe;

    public Mannschaft(String name) {
        this.name = name;
        this.spielerListe = new ArrayList<>();
    }

    public void addSpieler(Spieler spieler) {
        spielerListe.add(spieler);
    }

    public void removeSpieler(Spieler spieler) {
        spielerListe.remove(spieler);
    }

    public List<Spieler> getSpieler() {
        return spielerListe;
    }
}

class Spieler {
    private String name;
    private int nummer;

    public Spieler(String name, int nummer) {
        this.name = name;
        this.nummer = nummer;
    }
}
```

---

## Typische Methoden

```java
public int getAnzahlSpieler() {
    return spielerListe.size();
}

public Spieler findeSpieler(String name) {
    for (Spieler s : spielerListe) {
        if (s.getName().equals(name)) {
            return s;
        }
    }
    return null;
}
```

---

## Hinweise

- Assoziation = "hat"-Beziehung.
- Liste immer im Konstruktor initialisieren.
- Getter kann unmodifizierbare Liste zurueckgeben:

```java
return Collections.unmodifiableList(spielerListe);
```

---

## Mini-Beispiel (Nutzung)

```java
Mannschaft team = new Mannschaft("Red Tigers");
team.addSpieler(new Spieler("Max", 7));
team.addSpieler(new Spieler("Lena", 10));
```
