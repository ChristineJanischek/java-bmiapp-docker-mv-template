# BMI-Rechner Version 3+: Assoziationen & Objektbeziehungen

## 📚 Lernmaterialien zur Erweiterung des BMI-Rechners

Willkommen zu Version 3+ des BMI-Rechners! In dieser Version erweitern wir die Anwendung um **Assoziationen** und die Verwaltung von **Benutzerprofilen**.

---

## 🎯 Lernziele

Nach dieser Lerneinheit können die Schüler:

✅ Die drei Assoziationstypen (1:1, 1:N, M:N) verstehen und unterscheiden  
✅ 1:N Beziehungen mit `ArrayList` implementieren  
✅ M:N Beziehungen zwischen mehreren Klassen aufbauen  
✅ Bidirektionale Assoziationen synchronisieren  
✅ Die Kapselung von Sammlungen gewährleisten  
✅ Praktische Datenstrukturen für reale Szenarien entwerfen

---

## 📖 Lernmaterialien

### 1. **Hauptanleitung: Schritt-für-Schritt Lernmaterial**
   📄 [ASSOZIATIONEN_PERSON_MESSUNG.md](ASSOZIATIONEN_PERSON_MESSUNG.md)
   
   **Inhalte:**
   - Grundkonzepte der Assoziationen
   - Schritt-für-Schritt Implementierung der Person-Klasse
   - Die Messung-Klasse und ihre Struktur
   - 1:N Beziehung: Person ↔ Messungen
   - M:N Beziehung: Arzt ↔ Patienten
   - Integration in den BMI-Rechner
   - 7 praktische Aufgaben mit unterschiedlichen Schwierigkeitsgraden
   
   **Zielgruppe:** Schüler  
   **Dauer:** 3-4 Schulstunden

---

### 2. **Quick Reference: Schnelle Übersicht**
   📄 [ASSOZIATIONEN_QUICK_REFERENCE.md](ASSOZIATIONEN_QUICK_REFERENCE.md)
   
   **Inhalte:**
   - Kompakte Code-Beispiele aller Klassen
   - Häufige Operationen
   - FAQ - Häufig gestellte Fragen
   - Erweiterte Szenarien
   - Implementierungs-Checkliste
   
   **Zielgruppe:** Schüler (als Nachschlagewerk)  
   **Format:** Kurz und prägnant

---

### 3. **Lösungen: Musterlösungen für Lehrer**
   📄 [ASSOZIATIONEN_LOESUNGEN.md](ASSOZIATIONEN_LOESUNGEN.md)
   
   **Inhalte:**
   - Detaillierte Lösungen für alle 7 Aufgaben
   - Erläuterungen und Begründungen
   - Testbeispiele mit erwarteter Ausgabe
   - Häufige Fehler der Schüler
   - Differenzierungsvorschläge
   - Bewertungskriterien
   
   **Zielgruppe:** Lehrer  
   **Format:** Komplett mit Code und Erklärungen

---

## 🏗️ Neue Klassen

```
Person (Version 3+)
├── Attribute: vorname, nachname, alter, geschlecht, email
├── 1:N zu Messung: List<Messung> messungen
├── M:N zu Arzt: List<Arzt> aerzte
└── Methoden: addMessung(), getDurchschnittsBmi(), getTrend(), etc.

Messung (Neu)
├── Attribute: gewicht, groesse, bmi, zeitstempel, kategorie
└── Methoden: getBmi(), getKategorie(), getFormatiertesDatum()

Arzt (Neu - Optional)
├── Attribute: name, spezialisierung
├── M:N zu Person: List<Person> patienten
└── Methoden: addPatient(), getPatienten(), getDurchschnittsBmiAllerPatienten()

Krankenakte (Optional - 1:1 Beispiel)
├── Attribute: aktenNummer, person
└── Methoden: getPerson()
```

---

## 📊 Assoziationen im Überblick

### 1:1 Beziehung
```
Person ←→ Krankenakte
```
- Eine Person hat GENAU EINE Krankenakte
- Eine Krankenakte gehört zu GENAU EINER Person
- **Implementierung:** `Krankenakte { Person person; }`

### 1:N Beziehung
```
Person ←→ Messung
```
- Eine Person kann MEHRERE Messungen haben
- Eine Messung gehört zu genau EINER Person
- **Implementierung:** `Person { List<Messung> messungen; }`

### M:N Beziehung
```
Arzt ←→ Patient (Person)
```
- Ein Arzt betreut MEHRERE Patienten
- Ein Patient wird von MEHREREN Ärzten betreut
- **Implementierung (unidirektional):** `Arzt { List<Person> patienten; }`
- **Implementierung (bidirektional):** + `Person { List<Arzt> aerzte; }`

---

## 🚀 Implementierungs-Leitfaden

### Phase 1: Grundlagen (Lektion 1)
1. Person-Klasse implementieren
2. Messung-Klasse implementieren
3. 1:N Beziehung testen (TestAssoziation1N.java)

### Phase 2: Erweiterte Konzepte (Lektion 2)
4. Arzt-Klasse implementieren
5. M:N Beziehung testen (TestAssoziationMN.java)
6. Bidirektionale Synchronisation

### Phase 3: Integration (Lektion 3)
7. Bmirechner-Klasse anpassen
8. BmiManager erweitern
9. MainWindow-Integration

---

## ✅ Checkliste für Schüler

### Nach Lektion 1:
- [ ] Person-Klasse mit ArrayList<Messung> erstellt
- [ ] Messung-Klasse mit BMI-Berechnung erstellt
- [ ] addMessung(), getMessungen(), getDurchschnittsBmi() funktionieren
- [ ] TestAssoziation1N läuft ohne Fehler

### Nach Lektion 2:
- [ ] Arzt-Klasse mit ArrayList<Person> erstellt
- [ ] M:N Beziehung implementiert
- [ ] addPatient(), getPatienten(), getAnzahlPatienten() funktionieren
- [ ] TestAssoziationMN läuft ohne Fehler

### Nach Lektion 3:
- [ ] Person mit email-Attribut erweitert
- [ ] getBesteMessung() implementiert
- [ ] getTrend() zeigt Entwicklung
- [ ] Bidirektionale Beziehung funktioniert

---

## 🎓 Aufgaben nach Schwierigkeit

| # | Aufgabe | Typ | Zeit | Punkte |
|---|---------|-----|------|--------|
| 1 | Messung-Klasse verstehen | Verständnis | 15 min | 5 |
| 2 | 1:N Beziehung nutzen | Praktisch | 30 min | 10 |
| 3 | Person-Klasse erweitern | Implementierung | 25 min | 10 |
| 4 | Neue Methoden schreiben | Logik | 35 min | 15 |
| 5 | M:N Beziehung implementieren | Komplexität | 45 min | 15 |
| 6 | Bidirektionale Beziehung | Fortgeschrittene | 40 min | 20 |
| 7 | Datenanalyse | Synthese | 30 min | 10 |
| **Summe** | | | **3,5 h** | **85** |

---

## 💡 Tipps für Lehrer

### Unterrichtsgestaltung
- **Lektion 1-2:** Konzepte erklären, dann Schüler implementieren lassen
- **Lektion 3:** Schüler präsentieren ihre Lösungen
- **Pair Programming:** Schüler arbeiten zu zweit an M:N Beziehung
- **Code Review:** Andere Gruppen testen die Implementierungen

### Häufige Probleme
- **ArrayList nicht initialisiert:** Im Konstruktor zeigen!
- **Endlosschleife bei bidirektionalen Operationen:** `contains()` prüfen!
- **Original-Liste wird zurückgegeben:** Immer Kopie machen!
- **NullPointerException:** Immer `isEmpty()` prüfen!

### Differenzierung
- **Schwache Schüler:** Aufgaben 1-3, vorgefertigte Testklassen
- **Mittlere Schüler:** Aufgaben 1-5, eigene Testklassen
- **Starke Schüler:** Aufgaben 1-7 + Zusatzaufgaben (Datenbank, GUI)

---

## 🔗 Verknüpfung mit anderen Inhalten

| Konzept | Datei | Verwandlung |
|---------|-------|------------|
| ArrayList | Java docs | Datenstruktur |
| Kapselung | KAPSELUNG.md | Schutz von Daten |
| MVC-Muster | MVC_ANLEITUNG.md | Architektur |
| Polymorphie | POLYMORPHIE.md | Vererbung |
| Exceptions | EXCEPTION_HANDLING.md | Fehlerbehandlung |

---

## 📈 Lernfortschritt

```
Phase 1: Grundlagen
└─ Verstehen: Was ist eine Assoziation?
└─ Wissen: 1:N und M:N Typen
└─ Anwendung: Person + Messung implementieren

Phase 2: Vertiefung
└─ Verstehen: Bidirektionalität
└─ Wissen: ArrayList best practices
└─ Anwendung: M:N mit Arzt + Patient

Phase 3: Integration
└─ Synthese: Zusammenbinden aller Konzepte
└─ Evaluation: Eigene Szenarien erfinden
└─ Transfer: Auf andere Projekte anwenden
```

---

## 🎁 Bonusmaterial

### Zusätzliche Aufgaben

**Aufgabe 8: Datenbank-Integration (Optionial)**
- Speichere Personen und Messungen in einer Datei
- Lade sie beim Programmstart
- Implementiere DatabaseConnector-Klasse

**Aufgabe 9: GUI-Integration (Optional)**
- Erweitere MainWindow um Patientenverwaltung
- Zeige Messungshistorie an
- Füge Arzt-Selector hinzu

**Aufgabe 10: Statistik-Modul (Optional)**
- Berechne Statistiken über alle Patienten
- Exportiere Daten als CSV
- Erstelle einfache Diagramme

---

## 📞 Support für Fragen

Bei Fragen zu dieser Anleitung:

1. Schau in die [ASSOZIATIONEN_LOESUNGEN.md](ASSOZIATIONEN_LOESUNGEN.md) unter "Häufige Fehler"
2. Nutze die Testklassen zum Debuggen
3. Vergleiche mit den Quick Reference Beispielen
4. Frage die Mitschüler oder den Lehrer

---

## 🎯 Erfolgskriterien

Ein Schüler hat das Thema verstanden, wenn er/sie:

✅ Eine 1:N Beziehung erklären und implementieren kann  
✅ Den Unterschied zwischen 1:N und M:N versteht  
✅ ArrayList richtig mit Kapselung nutzt  
✅ Bidirektionale Beziehungen ohne Endlosschleife implementiert  
✅ Praktische Probleme mit Assoziationen lösen kann  
✅ Eigene Klassen mit komplexen Beziehungen entwirft

---

## 📝 Version

- **Erstellung:** 2026-02-02
- **Material für:** Java 21, Schüler Klasse 10-13
- **Format:** Markdown mit Code-Beispielen
- **Bearbeitungsstand:** Version 3+

---

**Viel Erfolg beim Lernen und Lehren! 🚀**

Für Fragen oder Verbesserungsvorschläge: Siehe README.md
