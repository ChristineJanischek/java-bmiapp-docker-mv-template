# 🧑‍💻 Aufgabe: Java Hallo-Welt in Docker mit eigener Ausgabe

## 🎯 Ziel

Schreibe ein eigenes Java-Programm, das in der Konsole eine Begrüßung ausgibt.

## 📄 Deine Aufgabe

Öffne die Datei `Main.java` im Ordner `src/start`. Ergänze die `main()`-Methode so, dass:

- ein Objekt der Klasse `HalloWelt` erstellt wird
- eine Methode `begruessung()` aufgerufen wird
- im Terminal erscheint z. B.: `Hallo Christine!`

Nutze dazu die vorhandene Klasse `HalloWelt.java`.

### 📄 Hilfe um Änderungen in die .class Datei zu schreiben:

1. Kompiliere alle java-Dateien:

```bash (Terminal)
 javac src/start/*.java
```

2. Wechsle ins src-Verzeichnis
```bash (Terminal)
cd src
```

3. Teste vor Ort
```bash (Terminal)
java start.Main 
```

## ▶️ Ausführen im Codespace

1. Wechsele ins main-Verzeichnis:
```bash (Terminal)
cd ..
```

2. Erzeuge ein Docker image
```bash (Terminal)
docker-compose up --build
```

3. Laufender Docker Compose-Prozess beenden
```bash (Terminal)
STRG+C auf der Tastatur drücken
```



## ✅ Test / Feedback

Wenn du richtig gearbeitet hast, bekommst du automatisches Feedback:
- Gibt dein Programm den Text „Hallo“ aus? ✅

## ✅ Abgeben: Lösung übermitteln
So gehst du vor:

1.Änderungen speichern und alle Dateien zum Commit vormerken:
```bash (Terminal)
git add .
```

2. Commit mit einer Nachricht erstellen:
```bash (Terminal)
git commit -m "Lösung Aufgabe HalloWelt"
```

3. Änderungen ins GitHub-Repository hochladen (pushen):
```bash (Terminal)
git push
```

Danach werden die Tests (sofern eingerichtet) meist automatisch ausgeführt und die Lehrkraft sieht deine Lösung.
