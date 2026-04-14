# E-Learning OOP Modernisierung (Oberstufe Informatik/Wirtschaftsinformatik)

## 1. Zielbild

Dieses Dokument modernisiert das bestehende E-Learning aus
[downloads/eLearning_oop/extracted/ObjektorientierteSoftwareentwicklung/index.html](downloads/eLearning_oop/extracted/ObjektorientierteSoftwareentwicklung/index.html)
auf eine didaktisch zeitgemaesse, webbasierte Lernplattform.

Ziel ist eine modulare Lernumgebung mit:

- modernem React-Frontend (responsive, mobil, schulkonfigurierbar)
- objektorientiertem, erweiterbarem Backend
- DSGVO-konformer Datenverarbeitung fuer den Einsatz im staatlichen Schulwesen in Deutschland/EU
- klaren Lernpfaden: Info, Task, Step-by-Step, Praxis-Transfer

## 2. Analyse des bestehenden E-Learnings

### 2.1 Inhaltliche Staerken (beibehalten)

- Breite OOP-Abdeckung: Klassen/Objekte, MVC, Vererbung, Assoziation, Debugging, DB
- viele Fallbeispiele und Aufgabenstellungen
- vorhandene Bilder, UML-Skizzen, Musterloesungen

### 2.2 Schwachstellen (zu modernisieren)

- technischer Stand alt (eXeLearning 2.1.1, statische Seiten)
- didaktisch stark lehrerzentriert, wenig adaptive Lernpfade
- gemischte Sprachen/Stacks (Java, PHP, JSP) ohne klaren Kompetenzpfad
- Sicherheits- und Datenschutzinhalte nicht systematisch durchgaengig in jedem Modul
- geringe Interaktivitaet fuer heutige Lerngewohnheiten der Oberstufe

### 2.3 Relevante Quellmodule (Altmaterial)

- Einstieg/OOP-Basics: index, klassen_objekte, grundgeruest
- MVC: view/controller Inhalte
- Algorithmen und Kontrollstrukturen
- Assoziationen/Vererbung
- Persistenz: Datei + Datenbank
- Debugging und Musterloesungen

## 3. Fusion mit aktuellen Repo-Inhalten

### 3.1 Didaktische Fusion Alt -> Neu

| Altmodul | Neue Modulspur | Repo-Quelle fuer Upgrade |
|---|---|---|
| Klassen/Objekte | Modul A: OOP-Basis und Modellierung | docs/JAVA_PROGRAMMIERUNG/GRUNDGERUEST_KLASSE.md |
| MVC/GUI | Modul B: MVC + Eventsteuerung | docs/KONZEPTE/MVC_KONZEPT.md, docs/GUI_DEVELOPMENT/Ereignissteuerung_und_Controller.md |
| Methoden/Kontrollstrukturen | Modul C: Loesungsstrategien und Algorithmen | docs/JAVA_PROGRAMMIERUNG/KONTROLLSTRUKTUREN.md, docs/ALGORITHMEN_UND_DATENSTRUKTUREN.md |
| Vererbung/Polymorphie | Modul D: Wiederverwendung und Erweiterbarkeit | docs/JAVA_PROGRAMMIERUNG/VERERBUNG.md, docs/JAVA_PROGRAMMIERUNG/POLYMORPHIE.md |
| Assoziationen | Modul E: Fachmodellierung in Projekten | docs/JAVA_PROGRAMMIERUNG/ASSOZIATIONEN.md |
| Dateispeicher | Modul F: Persistenz lokal und sicher | docs/JAVA_PROGRAMMIERUNG/DATEIEN_LESEN_SCHREIBEN.md |
| Datenbank | Modul G: Sichere DB-Anbindung | docs/JAVA_PROGRAMMIERUNG/DATENBANK.md, docs/ANLEITUNGEN/ANLEITUNG_VERSION_7_SICHERE_DB_ANBINDUNG.md |
| Fehler/Debugging | Modul H: Qualitaet, Testing, Security | docs/BEST_PRACTICES/UNIT_TESTING.md, docs/BEST_PRACTICES/SECURE_CODING.md |

### 3.2 Neuer roter Faden fuer Oberstufe

- Fachlich: Von Objektmodellierung zu sicheren, wartbaren Softwaresystemen
- Methodisch: Analyse -> Entwurf -> Implementierung -> Test -> Reflexion
- Berufsbezug WI: Rollen, Datenverantwortung, Audits, Compliance

## 4. Zielarchitektur (Best Practice)

### 4.1 Frontend (React)

Technologieempfehlung:

- React + TypeScript + Vite
- UI-System mit Design Tokens (Farben, Typografie, Abstaende)
- Zustand: React Query + lokalem State (Formen/Interaktion)
- Routing nach Lernmodulen und Steps

Fachliche Frontend-Bausteine:

- LessonPage mit Bereichen:
  - Info (Konzept, Visualisierung)
  - Task (Auftrag)
  - Step-by-Step (Leitfragen + Teilziele)
  - CodeBox (loesungsnaher Beispielcode)
  - Check (Selbsttest)
- Teacher Mode:
  - Hinweise ein/aus
  - differenzierte Aufgabenlevel
  - Export von Lernfortschritt
- School Branding:
  - Banner/Logo/Titel/Farbschema als Admin-Konfiguration

UX-Richtlinien:

- mobile-first
- klare Kontraste und grosse Touch-Targets
- progressive Offenlegung (nicht alles auf einmal)
- unmittelbares Feedback bei Eingaben und Quiz

### 4.2 Backend (objektorientiert, effizient, wartbar)

Empfohlene Architektur:

- Java 21 + Spring Boot (REST)
- Schichten:
  - domain (Entities, Value Objects, Domain Services)
  - application (Use Cases)
  - infrastructure (DB, Files, Messaging, Security Adapter)
  - interfaces (REST Controller, DTO Mapper)

Designprinzipien:

- SOLID, DRY, Single Responsibility
- klare Schnittstellen (Repository Interfaces)
- Wiederverwendung ueber Services/UseCases statt Copy/Paste
- Teststrategie: Unit + Integration + Security Tests

Skalierbarkeit:

- modulare Bounded Contexts (Lernen, Aufgaben, Nutzer, Analytics)
- Ereignisprotokoll fuer Lernereignisse
- API-Versionierung

### 4.3 Sicherheit und Datenschutz (Deutschland/EU)

Verpflichtende Sicherheitsanforderungen:

- Data Minimization: nur notwendige personenbezogene Daten
- Rollen/Rechte (Lehrkraft, Lernende, Admin)
- Verschluesselung:
  - in Transit: TLS
  - at Rest: DB-Verschluesselung, secrets nicht im Code
- Eingabevalidierung (Whitelist), Output-Encoding
- Audit-Logging ohne sensible Klartextdaten
- Loeschkonzepte und Exportfaehigkeit (Betroffenenrechte)
- Backup + Restore-Routinen + Notfallhandbuch

Normative Leitlinien (Umsetzungsebene):

- DSGVO Prinzipien (Art. 5)
- TOMs organisatorisch und technisch
- Hosting in EU oder mit rechtskonformen Garantien

## 5. Konkrete didaktische Beispiele (praxisnah)

### 5.1 Unterrichtsformat pro Einheit (90 Minuten)

1. Einstieg (10 min): Problem aus Schulpraxis oder Wirtschaft
2. Konzept (20 min): OOP-Prinzip visualisieren
3. Guided Coding (25 min): CodeBox Schrittfolge
4. Team-Task (25 min): Transferaufgabe
5. Exit Check (10 min): Reflexion + Mini-Quiz

### 5.2 Beispielmodul: Sichere BMI-Messung

Lernziel:

- Lernende modellieren Personen und Messungen als Assoziation
- validieren Eingaben und protokollieren sicherheitsrelevante Ereignisse

CodeBox Beispiel (Java):

```java
public record Measurement(double heightCm, double weightKg) {
    public Measurement {
        if (heightCm < 100 || heightCm > 250) {
            throw new IllegalArgumentException("heightCm out of range");
        }
        if (weightKg < 30 || weightKg > 300) {
            throw new IllegalArgumentException("weightKg out of range");
        }
    }

    public double bmi() {
        double m = heightCm / 100.0;
        return weightKg / (m * m);
    }
}
```

Step-by-Step fuer Lernende:

1. Klasse/Record erstellen
2. Grenzwerte festlegen
3. Testfaelle definieren
4. Berechnung implementieren
5. Ergebnis fachlich interpretieren

Transferaufgabe WI:

- Erweitere das Modell fuer ein Gesundheitsstudio mit Rollen und Audit-Trail.

### 5.3 Beispielmodul: SQL-Injection didaktisch sichtbar machen

Info:

- Unsicheres SQL-Muster vs. Prepared Statement

Task:

- Stelle den Angriff reproduzierbar in einer isolierten Demo dar.

Step-by-Step:

1. unsicheres Statement markieren
2. Angriffsstring testen
3. Prepared Statement einsetzen
4. Regression-Test schreiben

## 6. Marschplan mit Meilensteinen

### 6.1 Machbarkeit und Umfang

- Machbarkeit: hoch, da Fachinhalte bereits vorliegen
- Hauptrisiko: Content-Migration und Qualitaetskontrolle bei grossem Altbestand
- Zeitrahmen (realistisch): 8-12 Wochen fuer produktionsreife erste Version

### 6.2 Meilensteine

1. M0 Sicherung und Baseline (1-2 Tage)
2. M1 Content-Audit und Mapping (1 Woche)
3. M2 Informationsarchitektur + UI-Prototyp (1-2 Wochen)
4. M3 React-Frontend MVP (2 Wochen)
5. M4 Backend API und Sicherheitskern (2-3 Wochen)
6. M5 Didaktische Feinschliffe + Lehrkraefte-Features (1-2 Wochen)
7. M6 Pilotklasse, Evaluation, Nachschaerfung (1 Woche)

### 6.3 ToDo-Liste (operativ)

- [x] Backtrack-Branch und Snapshot-Tag erstellt
- [x] Altmaterial analysiert
- [x] Fusionsmatrix Altmaterial/Repo erstellt
- [x] Zielarchitektur Frontend/Backend/Security festgelegt
- [ ] React-Projektgeruest initialisieren
- [ ] Design-System und modulare Lesson-Komponenten umsetzen
- [ ] API-Vertrag (OpenAPI) und Domain-Model finalisieren
- [ ] Security-Baseline (Auth, Logging, Secrets, Rollen) implementieren
- [ ] Didaktische Module A-H in neue Struktur migrieren
- [ ] Lehrkraft-Konfiguration (Branding, Aufgabenlevel) umsetzen
- [ ] Pilottest mit Lerngruppe durchfuehren

## 7. Branch-Strategie fuer sicheren Backtrack

Bereits umgesetzt:

- Backup-Branch: backup/pre-elearning-modernisierung-20260411-214520
- Snapshot-Tag: snapshot-pre-elearning-modernisierung-20260411-214520
- Basis-Commit: 84a064b

Ruecksprung:

```bash
git fetch origin --tags
git checkout backup/pre-elearning-modernisierung-20260411-214520
# oder
git checkout -b restore/snapshot-1 snapshot-pre-elearning-modernisierung-20260411-214520
```

Empfohlene Arbeitsbranches fuer die Modernisierung:

- feat/elearning-react-frontend
- feat/elearning-backend-architecture
- feat/elearning-content-migration
- feat/elearning-teacher-admin
- hardening/security-dsgvo

Merge-Regel:

- Keine direkten Pushes auf main
- PR + Review + Tests als Pflicht

## 8. Lueckenlose automatische Dokumentation

Die laufende Protokollierung erfolgt in:

- docs/ELEARNING_MODERNISIERUNG/MIGRATIONS_PROTOKOLL.md

Automatisches Logging-Skript:

- scripts/log_elearning_step.sh

Beispiel:

```bash
./scripts/log_elearning_step.sh \
  --milestone M2 \
  --step "LessonCard-Komponente erstellt" \
  --status done \
  --artifact "frontend/src/components/LessonCard.tsx" \
  --note "Code Review mit Fokus auf Responsiveness"
```

## 9. Naechste konkrete Umsetzungsschritte

1. React-Struktur im neuen Unterprojekt initialisieren
2. Modul A und Modul B als klickbaren MVP-Lernpfad bauen
3. Backend API fuer Lessons, Tasks, Progress und Audit starten
4. Pilotlauf mit 1-2 Klassen und strukturiertem Feedback
