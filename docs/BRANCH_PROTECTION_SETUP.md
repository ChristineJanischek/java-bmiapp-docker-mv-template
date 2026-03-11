# Branch Protection Rule einrichten – Schritt-für-Schritt

## 🔒 Ziel
Nach dieser Anleitung darf niemand (auch nicht Sie selbst) direkt zu `main` pushen.  
Alle Änderungen müssen über einen **Pull Request mit mindestens 1 Genehmigung** erfolgen.

---

## 📋 Schritt-für-Schritt Anleitung

### Schritt 1: Zum Repository auf GitHub gehen

1. Öffne: https://github.com/ChristineJanischek/java-bmiapp-docker-mv-template
2. Oben rechts → **Settings** (Zahnrad-Icon)

![image](https://github.com/user-attachments/assets/settings-tab.png)

---

### Schritt 2: Zu „Branches" navigieren

1. Linkes Menü unter `Code and automation`
2. Klick auf **Branches**

![image](https://github.com/user-attachments/assets/branches-menu.png)

---

### Schritt 3: Branch Protection Rule erstellen

1. Button **Add rule** (oben rechts)

![image](https://github.com/user-attachments/assets/add-rule-button.png)

---

### Schritt 4: Branch-Namen eingeben

**Branch name pattern:** `main`

(Genau so schreiben!)

![image](https://github.com/user-attachments/assets/pattern-input.png)

---

### Schritt 5: Schutzoptionen aktivieren

Scrolle herunter und aktiviere diese Kontrollkästchen:

#### ✅ **Require a pull request before merging**
- Checkbox: `Require a pull request before merging` → **AKTIVIERT**
- Unter dieser Option:
  - `Require approvals` → **AKTIVIERT**
  - `Number of approvals required:` → auf **1** setzen

![image](https://github.com/user-attachments/assets/require-pr-review.png)

#### ✅ **Dismiss stale pull request approvals when new commits are pushed**
- Optional, aber empfohlen
- Falls jemand änderungen macht und neue Commits pusht, werden alte Reviews ungültig und müssen neu erfolgen

![image](https://github.com/user-attachments/assets/stale-reviews.png)

#### ⚠️ **Weitere empfohlen Optionen** (Optional)

Falls Sie extremere Sicherheit wollen:

```
❌ NICHT NOTWENDIG für Ihre Anforderung:
- Require status checks to pass before merging
- Require branches to be up to date before merging
```

![image](https://github.com/user-attachments/assets/additional-options.png)

---

### Schritt 6: Speichern

Scrolle ganz nach unten und klick auf **Create** oder **Save changes**

![image](https://github.com/user-attachments/assets/save-button.png)

---

## ✅ Fertig!

Die Rule ist aktiv. Ab sofort:

- ❌ **Direkte Pushes zu `main` sind blockiert** – auch für Sie selbst!
- ✅ **Nur Pull Requests mit mindestens 1 Approval** können gemergt werden

---

## 🔄 Neuer Workflow

### Für Sie und Ihre Schüler:

```bash
# 1. Feature-Branch erstellen
git checkout -b feature/neue-dokumentation

# 2. Änderungen machen und committen
git add .
git commit -m "docs: neue features"

# 3. Pushen (zu Feature-Branch)
git push origin feature/neue-dokumentation
```

Danach auf GitHub:
- Ein **Pull Request (PR)** wird automatisch erstellt
- Sie reviewen den Code
- Sie geben eine Genehmigung (Approve)
- Sie mergen den PR zu `main`

---

## 📝 Beispiel: PR reviewen und genehmigen

1. GitHub zeigt automatisch eine **Notification** für den neuen PR
2. Oder Sie sehen en unter: **Pull Requests** Tab
3. Klick auf den PR
4. Reviewen Sie den Code
5. Klick **Review changes** → **Approve** → **Submit review**
6. Der „Merge" Button wird grün – klicken Sie ihn!

---

## 🛠️ Administration: Als Admin durchsetzen

Falls Sie als **Admin** trotzdem schnell etwas zu `main` pushen müssen, können Sie das Kontrollkästchen aktivieren:

**Enforce for administrators:**
- ☑️ **Include administrators** – Admins müssen auch PR-Reviews erfüllen
- ☐ (Unklar markiert) – Admins können direkt zu `main` pushen

Wir empfehlen: **☑️ AKTIVIEREN** – Das schützt vor versehentlichen Fehlern!

---

## ❓ Troubleshooting

### Problem: "Push rejected"
```
ERROR: [remote rejected] main -> main (branch is protected)
```
**Lösung:** Müssen einen Pull Request machen (siehe Workflow oben).

### Problem: "I want to push directly anyway"
**Lösung:** Feature-Branch erstellen, PR öffnen, selbst genehmigen, mergen. Dauert 2 Minuten.

---

## 📌 Zusammenfassung der Settings

| Setting | Wert |
|---------|------|
| **Branch name pattern** | `main` |
| **Require pull request reviews** | ✅ JA |
| **Required approvals** | `1` |
| **Dismiss stale reviews** | ✅ JA (empfohlen) |
| **Enforce for admins** | ✅ JA (empfohlen) |

---

## 🎯 Post-Setup Check

Nach dem Speichern, zum Testen:

```bash
# Versuchen zu main zu pushen (sollte fehlschlag sein)
git checkout main
echo "test" >> test.txt
git add test.txt
git commit -m "test"
git push origin main

# ERROR sollte erscheinen:
# [remote rejected] main -> main (branch is protected)
```

Falls der Fehler kommt: **Perfekt!** ✅ Die Rule funktioniert.

Dann `test.txt` löschen:
```bash
git reset --hard HEAD~1
```

---

Sagen Sie Bescheid wenn Sie Fragen haben! 🚀
