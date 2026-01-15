# Schritt-für-Schritt-Anleitung Version 1 (MVC + GUI)

## Ziel
Verstehe und nutze die vorgegebene grafische Oberfläche (MainWindow). Implementiere und verbinde Controller (BmiManager) und Model (Bmirechner) nach dem MVC-Prinzip.

---

## 1. Branch wechseln
```bash
git checkout version-1-mvc-gui
git pull
```

---

## 2. Überblick verschaffen
- Sieh dir die Datei `src/start/MainWindow.java` an (GUI ist vorgegeben)
- Lies die Kommentare und prüfe, wie die Buttons und Felder funktionieren

---

## 3. Controller (BmiManager) implementieren

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

- Ergänze die Methoden laut UML-Diagramm
- Verbinde die GUI-Events mit Methoden im Controller
- Weitere Infos: [MVC_KONZEPT.md](../KONZEPTE/MVC_KONZEPT.md)

---

## 4. Model (Bmirechner) nutzen

Das Model sollte bereits implementiert sein (siehe Version 0):

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

- Stelle sicher, dass die Methoden `berechne()` und `interpretiere()` korrekt funktionieren
- Übergib Werte aus der GUI an das Model

### Hinweis: `Object`-API und `toString()`

Alle Java-Klassen erben von `java.lang.Object`. Überschreibe `toString()` in `Bmirechner`
und ggf. `BmiManager`, damit Objekte eine aussagekräftige Textdarstellung liefern. Tests
sollten bevorzugt `rechner.toString()` verwenden, z.B. `System.out.println(rechner);`.

---

## 5. Anwendung testen
```bash
./build.sh
./run.sh
```
- Teste die GUI: Werte eingeben, Buttons nutzen, Ausgaben prüfen

---

## 6. Änderungen committen und pushen
```bash
git add .
git commit -m "feat: Controller und Model nach MVC angebunden (Version 1)"
git push
```

---

## Weitere Hilfen
- [MVC_KONZEPT.md](../KONZEPTE/MVC_KONZEPT.md)
- [INFO.md](./INFO.md)
- [SECURE_CODING.md](../BEST_PRACTICES/SECURE_CODING.md)
- [ASSOZIATIONEN.md](../JAVA_PROGRAMMIERUNG/ASSOZIATIONEN.md)
