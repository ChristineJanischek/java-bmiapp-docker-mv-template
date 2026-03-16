# Pflichtenheft

# Projekt: Intelligentes webbasiertes eLearning-Kurseditor-System für Informatik

Geplant Repositories:

1. edu-code-lab-core
2. edu-code-lab-courses

Version: 1.0  
Status: Konzept / Architekturentwurf  
Datum: 2026

---

# 1 Projektvision

Ziel des Projekts ist die Entwicklung eines webbasierten intelligenten eLearning-Kurseditor-Systems speziell für Informatikunterricht.

Das System soll kein klassisches Learning Management System wie Moodle sein.

Stattdessen soll es ein pädagogisch intelligenter Kurseditor sein, der Lehrern und Schülern ermöglicht:

- Informatik-E-Learnings zu erstellen
- Codeaufgaben direkt im System zu bearbeiten
- Modellierungsaufgaben zu integrieren
- automatisierte Qualitätsprüfung zu nutzen
- eine kontinuierlich wachsende Wissensdatenbank aufzubauen
- Lernpfade intelligent zu strukturieren

Der Fokus liegt auf praktischem Informatikunterricht.

---

# 2 Projektziele

## Pädagogische Ziele

Das System soll:

- Informatik handlungsorientiert vermitteln
- selbstgesteuertes Lernen ermöglichen
- strukturierte Lernpfade anbieten
- praktische Programmieraufgaben integrieren
- moderne Themen wie KI und Machine Learning, Software Engineering integrieren

---

## Technische Ziele

Das System muss folgende Qualitätsmerkmale erfüllen:

- Erweiterbarkeit
- Wartbarkeit
- Wiederverwendbarkeit
- Sicherheit
- hohe Usability
- klare Struktur
- vollständige Dokumentation
- keine Redundanzen
- Online Examen (Prüfungen)

---

# 3 Zielgruppe

Primäre Zielgruppe:

- Informatiklehrer
- Schüler Sekundarstufe II
- Berufskollegs
- Berufliche Gymnasien, Schulen

Sekundäre Zielgruppe:

- Informatik-Fachschaften
- Schulen mit GitHub Classroom
- Entwickler von Bildungssoftware

---

# 4 Einsatzbereiche

Das System soll Kurse ermöglichen für:

- Programmierung
- Softwareentwicklung/-Management (Git und Github)
- BPMN / Geschäftsprozessmodellierung
- Mockups / UI-Design Integration
- Künstliche Intelligenz
- Machine Learning
- Datenbanken,
- eERM
- Webentwicklung
- OOP
- UML Klassendiagramme erstellen
- Struktogramme erstellen
- Code Generierung Bidirektional Modellierung/UI Design

---

# 5 Machbarkeitsanalyse

## Technische Machbarkeit

Die Umsetzung ist realistisch, da folgende Technologien bereits existieren:ö

| Bereich         | Technologie      |
| --------------- | ---------------- |
| Repository      | GitHub           |
| Kollaboration   | GitHub Classroom |
| Codeeditor      | VS Code          |
| Dokumentation   | Markdown         |
| Diagramme       | Draw.io          |
| Automatisierung | GitHub Actions   |
| KI-Integration  | LLM APIs         |
| Export          | Pandoc           |

Diese Technologien ermöglichen eine skalierbare Architektur.

---

# 6 Bedarfsanalyse

Probleme im Informatikunterricht heute:

- Materialien sind unstrukturiert
- Aufgaben existieren redundant
- Materialien sind schwer wiederverwendbar
- unterschiedliche Dokumentformate
- wenig Automatisierung
- hoher manueller Aufwand

---

# 7 Konkurrenzanalyse

| System           | Bewertung                     |
| ---------------- | ----------------------------- |
| Moodle           | LMS, kein Kurseditor          |
| GitHub Classroom | Codeplattform, keine Didaktik |
| CodeRunner       | nur Aufgabenprüfung           |
| Jupyter          | gut für Data Science          |

Ergebnis:

Es existiert derzeit kein System, das Kurseditor, Codeplattform, Wissensdatenbank, KI-Assistenz und GitHub-Integration kombiniert.

---

# 8 Systemarchitektur

Die Architektur besteht aus fünf Ebenen.

Frontend  
↓  
Kurseditor  
↓  
Content Engine  
↓  
Wissensdatenbank  
↓  
GitHub Repository

---

# 9 Kernfunktionen

## Kurseditor

Lehrer und Schüler können:

- Kurse erstellen
- Aufgaben definieren
- Lösungen hinterlegen
- Lernpfade strukturieren
- Gleichwertige Aufgaben mit Lösungen generieren und im System integrieren lassen

---

## Code Integration

Unterstützte Sprachen:

- Python
- Java
- HTML
- CSS
- JavaScript
- PHP

---

## Modellierung

Unterstützung für:

- Struktogramme
- BPMN -> Mockups -> Quellcode
- UML

Integration über Draw.io.

---

# 10 Wissensdatenbank

Alle Inhalte werden zentral gespeichert.

Eigenschaften:

- versioniert
- strukturiert
- wiederverwendbar
- keine Redundanzen

---

# 11 Aufgabenstruktur

Jede Aufgabe nutzt ein Template.

Titel  
Thema  
Beschreibung  
Aufgabe  
Codebox  
Bewertung  
Häufige Fehler  
Lösung

Metadaten:

- Schwierigkeit
- Lernziel
- Punkte
- Erwartungshorizont
- Dauer

---

# 12 Bewertungssystem

Jede Aufgabe enthält:

Bewertung:

- Punkteverteilung
- Bewertungskriterien
- Teilpunkte

Häufige Fehler:

Liste typischer Schülerfehler.

Nutzen:

- schnellere Korrektur
- gerechte Bewertung

---

# 13 KI Unterstützung

Das System soll KI nutzen für:

- Aufgabenvariationen
- Qualitätsprüfung
- Codeoptimierung
- Lernpfadempfehlungen
- Generierung neuer Aufgaben

---

# 14 Qualitätssicherung

Automatische Prüfungen:

- Codequalität
- Duplikate
- Redundanzen
- Architekturprüfung
- Sicherheitsprüfung

Diese Prüfungen laufen über GitHub Actions.

Verbindliche CI-Prüfungen umfassen zusätzlich:

- Exam-Validierung (Variantenanzahl, Konsistenz Aufgabe/Lösung, Duplikatprüfung je Aufgabe, Punktelogik)
- Navigationsprüfung der Grundlagen-Dokumentation (zentrale Regelkonfiguration)
- Namenskonventionen und Strukturvalidierung
- Drift-Prüfung der VS-Code-Extension-Empfehlungen gegen zentrales Manifest
- Accessibility- und Lighthouse-Qualitätschecks für Web-Projekte

Lokale Qualitätssicherung:

- Pre-Commit-Hooks müssen für prüfungsrelevante Inhalte aktivierbar sein
- Wissensdatenbank/Fingerprints für Aufgabenvarianten müssen automatisiert aktualisierbar sein

---

# 15 Exportfunktionen

Aufgaben können exportiert werden als:

- PDF
- DOCX
- HTML
- XML
- Markdown

Nutzen:

- Offline Prüfungen
- Ausdrucke
- Archivierung

---

# 16 GitHub Repository Strategie

Empfohlene Struktur:

elearning-core  
│  
├── platform  
├── templates  
├── knowledge-base  
├── courses  
└── tools

Template-Update-Strategie:

- Updates aus dem Template müssen selektiv übernehmbar sein (z. B. Dokumentation, Workflows, neue Versionen)
- Eigene Schülerarbeiten in `version*/aufgabe/` dürfen durch Template-Sync nicht automatisch überschrieben werden
- Update-Prozess soll semi-automatisch mit Benachrichtigung und manueller Freigabe erfolgen

---

# 17 Branch Strategie

main  
development  
content  
experiment

Zusätzlich thematische Branches:

core-programming  
core-ai  
core-software-engineering

---

# 18 Repository Governance

Nur der Projektverantwortliche darf:

- pushen
- committen
- mergen

Branch Protection Regeln:

- Pull Requests erforderlich
- CI Checks erforderlich
- Reviews verpflichtend

Erweiterte Governance-Anforderungen:

- `main` ist als geschützter Branch zu führen; direkte Push-Rechte sind auf die Projektadministration zu begrenzen
- Code-Owner-Review muss für Merge nach `main` verpflichtend sein
- Offene Review-Konversationen müssen vor Merge aufgelöst sein
- Governance-Einstellungen sind regelmäßig gegen die dokumentierte Zielkonfiguration zu prüfen

---

# 19 Meilensteinplan

Phase 1 – Konzept (1–2 Monate)

- Architekturdefinition
- Repositorystruktur
- Template-System

Phase 2 – Minimalversion (3–4 Monate)

- funktionaler Kurseditor
- Markdown Aufgaben
- GitHub Integration

Phase 3 – Wissensdatenbank (6 Monate)

- Aufgabenbibliothek
- automatische Strukturprüfung
- Versionierung

Phase 4 – KI Integration (9 Monate)

- Aufgabenvariationen
- Empfehlungssystem
- Qualitätsprüfung

---

# 20 Start ToDo Liste

- Repository erstellen
- Struktur definieren
- Templates definieren
- GitHub Actions einrichten
- Wissensschema entwickeln
- Kurseditor Konzept erstellen
- Exam-CI einrichten (Pre-Commit, Remote-Validierung, Knowledge-Base-Update)
- Backup-Workflow aktivieren und regelmäßige Restore-Tests planen
- Docs-Navigation-Regeln zentral konfigurieren und automatisiert prüfen
- VS-Code-Extension-Manifest synchronisieren und CI-Check aktivieren
- Template-Update-Benachrichtigung und Release-Kommunikation etablieren

---

# 21 Langfristige Vision

Ein offenes System für Schulen:

- offene Wissensdatenbank
- automatisierte Kursentwicklung
- intelligente Lernpfade
- KI-gestützte Informatikdidaktik

Das System soll langfristig eine Plattform für moderne Informatiklehre werden.

---

# 22 Repositories

Trennung System und Inhalt

Zu 1. edu-code-lab-core.
Hier gehört alles hinein, was das System technisch bereitstellt.

Also zum Beispiel:

- Kurseditor
- Renderer
- Import-/Export-Logik
- Template-Engine
- GUI-Komponenten
- Rollen-/Rechtestruktur
- Codeboxen
- Prüf- und Validierungsroutinen
- KI-gestützte Qualitätschecks
- Synchronisationslogik
- Duplikatserkennung
- Modellierungsfunktionen
- Struktogramm-Generatoren
- Draw.io-/XML-/SVG-Schnittstellen
- Code-Generatoren aus Modellen
- Code-Analyse und Best-Practice-Checks
- GitHub-Anbindung
- Automatisierungen / CI / Prüfjobs

Zu 2. edu-code-lab-courses. 
Hier gehört alles hinein, was fachlicher Inhalt ist.

Also:

- Kurse
- Module
- Lernpfade
- Aufgaben
- Lösungen
- Prüfungen
- Varianten
- Bewertungsschemata
- häufige Fehler
- Lehrerhinweise

Schülerhinweise

- fachliche Dokumentation
- kursbezogene Medien
- kursbezogene Diagramme
- kursbezogene Beispiele
- Markdown-Dateien mit Inhalten

Kurz: alles, was didaktischer oder fachlicher Content ist, gehört in das Courses-Repo.
