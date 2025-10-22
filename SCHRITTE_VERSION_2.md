# Schritt-für-Schritt-Anleitung Version 2 (Methoden, Kontrollstrukturen, Algorithmen)

## Ziel
Erweitere die Anwendung um zusätzliche Methoden, Kontrollstrukturen und Algorithmen. Ergänze die GUI um Alter (ComboBox) und Geschlecht (RadioButtons) und verbinde alles nach dem MVC-Prinzip.

---

## 1. Branch wechseln
```bash
git checkout version-2-methoden
git pull
```

---

## 2. GUI erweitern
- Füge eine ComboBox für das Alter (`cbAlter`) hinzu
- Füge RadioButtons für das Geschlecht (`rbMann`, `rbFrau`) hinzu
- Siehe Beispiel in [SCHRITT_FUER_SCHRITT_GUI_V2.md](./SCHRITT_FUER_SCHRITT_GUI_V2.md)

---

## 3. Methoden & Algorithmen ergänzen
- Implementiere neue Methoden im Model/Controller (z.B. BMI-Verlauf, Durchschnitt)
- Nutze Schleifen und verschachtelte Bedingungen

---

## 4. MVC-Prinzip anwenden
- Verbinde die neuen GUI-Elemente mit Controller und Model
- Passe die Methode `interpretieren()` an, sodass Alter und Geschlecht berücksichtigt werden

---

## 5. Anwendung testen
```bash
./build.sh
./run.sh
```
- Teste verschiedene Kombinationen von Alter und Geschlecht

---

## 6. Änderungen committen und pushen
```bash
git add .
git commit -m "feat: GUI und Logik für Version 2 erweitert"
git push
```

---

## Weitere Hilfen
- [SCHRITT_FUER_SCHRITT_GUI_V2.md](./SCHRITT_FUER_SCHRITT_GUI_V2.md)
- [KONTROLLSTRUKTUREN.md](./KONTROLLSTRUKTUREN.md)
- [MVC_KONZEPT.md](./MVC_KONZEPT.md)
- [SECURE_CODING.md](./SECURE_CODING.md)
