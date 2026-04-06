# Materialien für Schritt-für-Schritt Anleitung Version 5 & Kurztests

## Übersicht

Hier findest Du alle Materialien für **Version 5 (JSON-Dateispeicher)** der BMI-App:

### 1. Schritt-für-Schritt Anleitung (Version 5)

Die komplette Anleitung ist verfügbar als:

- **Markdown:** `docs/ANLEITUNGEN/SCHRITTE_VERSION_5_JSON_DATEISPEICHER.md`
- **HTML (für PDF):** `docs/ANLEITUNGEN/SCHRITTE_VERSION_5_JSON_DATEISPEICHER.html`

**PDF generieren (Variante 1 – Browser):**
1. Öffne die HTML-Datei im Browser (Chrome, Firefox, Edge)
2. Drücke `Ctrl+P` (Windows/Linux) oder `Cmd+P` (Mac)
3. Wähle "Als PDF speichern" → Ziel auswählen → Speichern

**PDF generieren (Variante 2 – Kommandozeile):**
Falls auf Deinem System `wkhtmltopdf` installiert ist:
```bash
wkhtmltopdf docs/ANLEITUNGEN/SCHRITTE_VERSION_5_JSON_DATEISPEICHER.html \
            docs/ANLEITUNGEN/SCHRITTE_VERSION_5_JSON_DATEISPEICHER.pdf
```

### 2. Kurztests für Schüler

Die Tests sind in **zwei Varianten** verfügbar:

#### Trainingsfalls (Schüler übt daheim):
```
materials/tests/dateispeicher/
├── Kurztest_Dateispeicher_5.md           ← Mit Lösung (zum Üben)
├── Kurztest_Dateispeicher_5_LOESUNG.md   ← Beispiellösung
```

#### Prüfungsfalls (Echte Klausuren im Unterricht):
```
materials/tests/dateispeicher/
├── Kurztest_Dateispeicher_6_LOESUNG.md   ← Zweite Variante mit Lösung
├── Kurztest_Dateispeicher_7_LOESUNG.md   ← Dritte Variante mit Lösung
```

### 3. Aufbau der Kurztests

Alle drei Tests folgen der gleichen Struktur:

| Test | Szenario | Schwerpunkte | Punkte |
|------|----------|--------------|--------|
| **Kurztest 5** | Person-Datenbank | Trennung Struktur/Daten, Backup, Fehlersuche | 25 |
| **Kurztest 6** | Messungen-Datenbank (Sensorwerte) | Atomare Schreibweise, Range-Validierung, Redundanz | 25 |
| **Kurztest 7** | Schüler-Noten-Verwaltung | Mehrentität-Normalisierung, Race Conditions, Konsistenz | 25 |

### 4. Verwendung im Unterricht

**Lernphase (Vorbereitung):**
1. Schüler arbeiten mit **Kurztest_Dateispeicher_5.md** + Anleitung
2. Version 5 live im Docker testen
3. Lösung gegen **Kurztest_Dateispeicher_5_LOESUNG.md** vergleichen

**Prüfungsphase:**
- Wähle **Kurztest 6 oder Kurztest 7** als echte Klausur
- Schüler dürfen **die Anleitung Version 5 als Hilfsmittel** verwenden
- Gleische Syntaxhilfe ist zugelassen

### 5. Didaktische Differenzierung

- **Einfacher Einstieg (Test 5):** Person/Messung – 1:1 Fokus auf Struktur/Daten-Trennung
- **Mittleres Niveau (Test 6):** Messkontext + Gültigkeitsprüfung – Validierung + Atomarität
- **Erweitertes Niveau (Test 7):** Multi-Entität, Race Conditions – Normalisierung + Transaktionssicherheit

### 6. Erwartungshorizont

| Punkte | Bewertung | Anforderungen |
|--------|-----------|---------------|
| 23-25 | Sehr gut | Alle Konzepte verstanden, sichere Implementierung |
| 19-22 | Gut | Grundkonzepte sauber, kleinere Lücken |
| 14-18 | Ausreichend | Basalkonzepte erkannt, Validierung/Fehlerbehandlung unvollständig |
| < 14 | Mangelhaft | Wichtige Konzepte fehlen |

### 7. Dateien im Repository

```
materials/tests/dateispeicher/
├── Kurztest_Dateispeicher_5.md               (Original mit Lösung)
├── Kurztest_Dateispeicher_5_LOESUNG.md       (Strukturierter Lösungsschlüssel)
├── Kurztest_Dateispeicher_6_LOESUNG.md       (Variante 2 Messungen)
├── Kurztest_Dateispeicher_7_LOESUNG.md       (Variante 3 Noten/Normalisierung)
├── Kurztest_Dateispeicher_Materialliste.md   (Diese Datei)

docs/ANLEITUNGEN/
├── SCHRITTE_VERSION_5_JSON_DATEISPEICHER.md  (Markdown-Quelle)
└── SCHRITTE_VERSION_5_JSON_DATEISPEICHER.html (HTML für PDF-Druck)
```

### 8. Tipps für Lehrkräfte

1. **Differenzierung nach Leistungsstand:**
   - Schwächer: Kurztest 5 (grundlegend)
   - Mittel: Kurztest 6 (validierungsfokussiert)
   - Stark: Kurztest 7 (normalisierung+konsistenz)

2. **Gruppenarbeit möglich:**
   - Tests sind unabhängig → verschiedene Gruppen verschiedene Tests

3. **Fehleranalyse:**
   - Nutze die Aufgabe 3/4 (Code-Analyse) als Diskussionspunkt im Plenum

4. **Zeitmangement:**
   - 25 Min reicht für motivierte Schüler
   - Bei Bedarf: Teil-Bewertung (z.B. nur Aufgaben 1-3)

5. **Syntaxhilfe nutzen:**
   - Erlaube Zugriff auf die Anleitung SCHRITTE_VERSION_5
   - Erlaube Zugriff auf die Vorgänger-Tests (Kurztest 5)
   - Einschränkung: Keine externe IDE/Google während Klausur

### 9. Nächster Schritt: Version 6 (Security & Safety)

Nach erfolgreicher Abdeckung von Version 5 folgt:

**Version 6: Sichere Datenverarbeitung und verantwortliche Software**
- Eingabevalidierung & Whitelist-Ansatz
- Sichere Speicherung (Verschlüsselung, Minimalprinzip)
- Audit-Logging ohne sensible Daten
- Datenschutz (DSGVO-Grundideen)
- Fehlerbehandlung & Fail-Safe Defaults

→ Siehe: `docs/BEST_PRACTICES/SECURE_CODING.md` als Basis

### 10. Feedback & Verbesserungen

Falls Du Fehler findest oder Anmerkungen hast:
- Öffne ein Issue im Repository
- Oder füge einen Kommentar in die Test-Dateien ein

---

**Viel Erfolg beim Unterricht!**
