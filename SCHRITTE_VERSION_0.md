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
- Felder: gewicht, groesse, ergebnis, kategorie
- Methoden: `berechne()`, `interpretiere()`, Getter/Setter
- Siehe [GRUNDGERUEST_KLASSE.md](./GRUNDGERUEST_KLASSE.md)

---

## 4. Controller (BmiManager) implementieren
- Verbindet View (MainWindow) und Model (Bmirechner)
- Methoden: `berechneBMI()`, `interpretiereBMI()`, Getter/Setter
- Siehe UML in README oder [MVC_KONZEPT.md](./MVC_KONZEPT.md)

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
