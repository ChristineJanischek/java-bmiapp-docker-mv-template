# Template Quick Reference - MindLink Setup

## ⚡ Schnellstart (3 Schritte)

### 1️⃣ Template aktivieren (einmalig, auf GitHub)
```
https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template/settings
→ General → Template repository ☑ aktivieren
```

### 2️⃣ MindLink Repository erstellen
```
https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template
→ "Use this template" → "Create a new repository"
→ Name: "mindlink"
→ ☑ "Include all branches" aktivieren!
→ Create
```

### 3️⃣ Klonen und Template-Remote hinzufügen
```bash
git clone https://github.com/ChristineJanischek/mindlink.git
cd mindlink
git remote add template https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template.git
```

---

## 🔄 Updates vom Template holen

```bash
cd mindlink
git fetch template
git merge template/main --allow-unrelated-histories
git push origin main
```

---

## 🛠 Für MindLink anpassen

### Packages umbenennen (start → mindlink)
```bash
mkdir -p src/mindlink src/test/java/mindlink
mv src/start/* src/mindlink/
mv src/test/java/start/* src/test/java/mindlink/
rm -rf src/start src/test/java/start

find src -name "*.java" -exec sed -i 's/package start;/package mindlink;/g' {} +
find src -name "*.java" -exec sed -i 's/import start\./import mindlink./g' {} +

sed -i 's/<mainClass>start\.MainWindow/<mainClass>mindlink.MainWindow/g' pom.xml
```

### pom.xml anpassen
```xml
<groupId>com.mindlink</groupId>
<artifactId>mindlink-app</artifactId>
<name>MindLink Application</name>
<description>MindLink - Cognitive Training Application</description>
```

### Klassen umbenennen (Beispiel)
```bash
mv src/mindlink/Bmirechner.java src/mindlink/MindLinkCore.java
sed -i 's/public class Bmirechner/public class MindLinkCore/g' src/mindlink/MindLinkCore.java
grep -rl "Bmirechner" src/ | xargs sed -i 's/Bmirechner/MindLinkCore/g'
```

---

## ✅ Testen & Bauen

```bash
# Build
mvn clean compile

# Tests
mvn test

# GUI starten
java -cp build mindlink.MainWindow

# Docker GUI (Browser)
docker-compose -f docker-compose.novnc.yml up
# → http://localhost:6080/vnc.html
```

---

## 📚 Dokumentation

- **Vollständige Anleitung:** `TEMPLATE_GUIDE.md`
- **Script ausführen:** `bash scripts/prepare_template.sh`

---

## 🎯 Was ist enthalten?

✅ Java 21 + Maven  
✅ MVC-Architektur  
✅ Swing GUI + Docker noVNC  
✅ JUnit 5 Tests (15+ Tests)  
✅ Git Hooks (Auto-Push, Pre-Push Tests)  
✅ 5 Branches (main, version-0 bis version-3)  
✅ GitHub Classroom Ready  

---

**Viel Erfolg mit MindLink! 🚀**
