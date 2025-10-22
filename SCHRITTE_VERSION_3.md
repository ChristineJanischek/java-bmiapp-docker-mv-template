# Schritt-für-Schritt-Anleitung Version 3 (Validierung & Fehlerbehandlung)

## Ziel
Erweitere die Anwendung um Eingabevalidierung, Fehlerbehandlung und Usability-Verbesserungen.

---

## 1. Branch wechseln
```bash
git checkout version-3-validation
git pull
```

---

## 2. Eingabevalidierung ergänzen
- Prüfe alle Eingaben auf sinnvolle Werte (z.B. Gewicht > 0, Größe > 0.5)
- Markiere fehlerhafte Felder farblich
- Zeige hilfreiche Fehlermeldungen an

---

## 3. Exception Handling
- Fange NumberFormatException und andere Fehler ab
- Verhindere Abstürze durch ungültige Eingaben

---

## 4. Usability verbessern
- Hilfetexte für Eingabefelder
- Fokus auf das erste fehlerhafte Feld setzen

---

## 5. Anwendung testen
```bash
./build.sh
./run.sh
```
- Teste alle Fehlerfälle und prüfe die Benutzerführung

---

## 6. Änderungen committen und pushen
```bash
git add .
git commit -m "feat: Validierung und Fehlerbehandlung (Version 3)"
git push
```

---

## Weitere Hilfen
- [SECURE_CODING.md](./SECURE_CODING.md)
- [KONTROLLSTRUKTUREN.md](./KONTROLLSTRUKTUREN.md)
- [MVC_KONZEPT.md](./MVC_KONZEPT.md)
