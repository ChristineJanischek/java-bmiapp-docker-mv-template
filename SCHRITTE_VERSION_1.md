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
- Ergänze Methoden laut UML (siehe [MVC_KONZEPT.md](./MVC_KONZEPT.md))
- Verbinde die GUI-Events mit Methoden im Controller

---

## 4. Model (Bmirechner) nutzen
- Stelle sicher, dass die Methoden `berechne()` und `interpretiere()` korrekt funktionieren
- Übergib Werte aus der GUI an das Model

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
- [MVC_KONZEPT.md](./MVC_KONZEPT.md)
- [INFO.md](./INFO.md)
- [SECURE_CODING.md](./SECURE_CODING.md)
- [ASSOZIATIONEN.md](./ASSOZIATIONEN.md)
