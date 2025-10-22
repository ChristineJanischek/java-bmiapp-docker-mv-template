# Schritt-für-Schritt-Anleitung Version 0 (main)

## Ziel
Starte mit dem Grundgerüst: Implementiere das Model (`Bmirechner`) und den Controller (`BmiManager`).

---

## 1. Repository klonen und vorbereiten
```bash
git clone https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template
cd java-bmiapp-docker-mv-template
git checkout main
git pull
```

---

## 2. Fehlende Klassen anlegen
- Lege die Datei `src/start/Bmirechner.java` an (Model)
- Lege die Datei `src/start/BmiManager.java` an (Controller)

---

## 3. Model (Bmirechner) implementieren

Implementiere die Klasse gemäß diesem UML-Diagramm:

```text
┌──────────────────────────────────────────────────────────────┐
│                        Bmirechner                            │
├──────────────────────────────────────────────────────────────┤
│ - gewicht: double                                            │
│ - groesse: double                                            │
│ - ergebnis: double                                           │
│ - kategorie: String                                          │
├──────────────────────────────────────────────────────────────┤
│ + Bmirechner()                                               │
│ + setGewicht(pGewicht: double): void                         │
│ + setGroesse(pGroesse: double): void                         │
│ + getGewicht(): double                                       │
│ + getGroesse(): double                                       │
│ + getErgebnis(): double                                      │
│ + getKategorie(): String                                     │
│ + berechne(pGewicht: double, pGroesse: double): double       │
│ + interpretiere(): void                                      │
│ + toString(): String                                         │
└──────────────────────────────────────────────────────────────┘
```

### BMI-Wertetabelle für die Interpretation

| BMI-Wert         | Kategorie                |
|------------------|------------------------- |
| < 16             | Starkes Untergewicht     |
| 16 – < 17        | Mäßiges Untergewicht     |
| 17 – < 18.5      | Leichtes Untergewicht    |
| 18.5 – < 25      | Normalgewicht            |
| 25 – < 30        | Prädipositas             |
| 30 – < 35        | Adipositas Grad I        |
| 35 – < 40        | Adipositas Grad II       |
| ≥ 40             | Adipositas Grad III      |

Weitere Infos: [GRUNDGERUEST_KLASSE.md](./GRUNDGERUEST_KLASSE.md)

---

## 4. Controller (BmiManager) implementieren

Implementiere die Klasse gemäß diesem UML-Diagramm:

```text
┌─────────────────────────────────────────────┐
│                BmiManager                   │
├─────────────────────────────────────────────┤
│ - model: Bmirechner                         │
├─────────────────────────────────────────────┤
│ + BmiManager()                              │
│ + BmiManager(pModel: Bmirechner)            │
│ + berechneBMI(pGewicht: double,             │
│               pGroesse: double): double     │
│ + interpretiereBMI(): void                  │
│ + getModel(): Bmirechner                    │
│ + setModel(pModel: Bmirechner): void        │
└─────────────────────────────────────────────┘
```

Der BmiManager verbindet View (später: MainWindow) und Model (Bmirechner).

Weitere Infos: [MVC_KONZEPT.md](./MVC_KONZEPT.md)

---

## 5. Kompilieren und starten
```bash
./build.sh
./run.sh
```

---

## 6. Testen
- Starte die GUI, gib Werte ein, prüfe die Ausgabe
- Teste Buttons "Leeren" und "Schließen"

---

## 7. Änderungen committen und pushen
```bash
git add .
git commit -m "Implementiere Bmirechner und BmiManager (Version 0)"
git push
```

---

## 8. Nächste Versionen ansehen
```bash
git checkout version-1-mvc-gui   # Musterlösung
```

---

## Weitere Hilfen
- [KONTROLLSTRUKTUREN.md](./KONTROLLSTRUKTUREN.md)
- [MVC_KONZEPT.md](./MVC_KONZEPT.md)
- [SECURE_CODING.md](./SECURE_CODING.md)
- [ASSOZIATIONEN.md](./ASSOZIATIONEN.md)
- [INFO.md](./INFO.md)
