# Anleitung: KI-Bewertungsmodul fuer Webprojekte der Oberstufe

## Ziel

Diese Anleitung beschreibt, wie aus den vorhandenen Korrekturhilfen in diesem Repository ein gleichwertiges oder besseres Bewertungsmodul fuer Webprojekte von Schuelerinnen und Schuelern der Oberstufe abgeleitet werden kann.

Der Fokus liegt nicht auf einer generischen KI-Idee, sondern auf einer belastbaren Architektur:

- nachvollziehbar
- sicher gegen unsaubere Uploads
- rubrikgetrieben statt promptgetrieben
- fuer Klassenstapel automatisierbar
- fuer Lehrkraefte manuell nachpruefbar

## Analyse der bestehenden Implementierung

Das vorhandene Modul ist kein frei formulierender LLM-Korrektor, sondern eine regelbasierte Bewertungs-Pipeline mit klar getrennten Bausteinen.

### Bestehende Pipeline

1. Eine ZIP-Abgabe wird ueber ein CLI-Skript eingelesen.
2. Das Archiv wird sicher entpackt.
3. Das erwartete Quellcode-Verzeichnis wird erkannt.
4. Ein Bewertungsprofil im JSON-Format definiert die Rubrik und die Pruefregeln.
5. Der Analyzer prueft die Regeln gegen den Quellcode.
6. Aus den erreichten Punkten wird eine lineare Note berechnet.
7. Es werden Berichte als DOCX, Markdown und HTML erzeugt.
8. Im Batch-Modus werden viele Abgaben in einem Lauf verarbeitet, inklusive Uebersicht, Rangliste und Laufstatistik.

### Staerken des aktuellen Moduls

- Klare Modultrennung: Import, Analyse, Scoring, Reporting und Profile sind voneinander entkoppelt.
- Hohe Nachvollziehbarkeit: Jede Teilbewertung laesst sich auf eine Regel zurueckfuehren.
- Gute Wartbarkeit: Neue Rubriken werden ueber Profile statt ueber Code-Aenderungen angelegt.
- Gute Skalierbarkeit fuer den Schulalltag: Einzel- und Batch-Lauf sind bereits vorgesehen.
- Sichere ZIP-Verarbeitung: Path-Traversal beim Entpacken wird verhindert.
- Mehrere Ausgabeformate: DOCX fuer Lehrkraft-Workflows, Markdown und HTML fuer schnelle Kontrolle.
- Lehrerzentrierte Nutzung: automatische Vorbewertung mit ausdruecklicher Moeglichkeit zur manuellen Finalpruefung.

### Grenzen des aktuellen Moduls

- Stark auf statische Musterpruefung ausgerichtet, vor allem per Regex.
- Java-zentriert in Dateifindung und Heuristiken.
- Keine Build-, Laufzeit- oder Browserpruefung.
- Keine echte Ausfuehrung funktionaler Szenarien.
- Keine AST-basierte Analyse fuer robustere Codequalitaetspruefungen.
- Keine isolierte zweite Bewertungsstufe fuer qualitative Textbegruendungen.

## Was fuer Webprojekte uebernommen werden sollte

Fuer ein gleichwertiges Modul sollten folgende Designprinzipien direkt uebernommen werden:

### 1. Profil vor KI

Die Rubrik darf nicht nur im Prompt stehen. Sie muss als strukturierte Konfiguration vorliegen, zum Beispiel als JSON oder YAML mit:

- kriteriums-id
- titel
- beschreibung
- punkte
- prueftyp
- konfiguration
- evidenztext

Damit bleibt die Bewertung stabil, testbar und versionierbar.

### 2. Sichere Intake-Stufe

Webprojekte kommen oft als ZIP oder Repository-Export. Diese Intake-Stufe sollte zwingend koennen:

- sicheres Entpacken ohne Path-Traversal
- Groessenlimits fuer Archive und entpackte Inhalte
- Blockliste fuer problematische Dateien
- Ignorieren von build, dist, node_modules und coverage
- Normalisierung des Projekt-Roots, falls Schuelerinnen und Schueler Zusatzordner mitliefern

### 3. Klare Trennung der Bewertungsphasen

Die beste Zielarchitektur fuer Webprojekte ist mehrstufig:

1. Intake und Normalisierung
2. statische Projektanalyse
3. Build- und Laufpruefung
4. Browser-Smoketests
5. regelbasierte Punktevergabe
6. optionale KI-Zusammenfassung mit belegbaren Evidenzen
7. Berichtsgenerierung

### 4. Evidenz statt freier Behauptung

Jede Teilbewertung sollte mindestens eine konkrete Evidenz liefern:

- Dateiname
- CSS-Selektor
- Script-Name
- Build-Ausgabe
- HTTP-Status
- Screenshot-Pfad
- Ausschnitt aus DOM oder Konsole

Damit bleibt die Lehrkraft in Kontrolle.

## Zielbild: Gleichwertiges oder besseres Web-Bewertungsmodul

### Mindestziel fuer Gleichwertigkeit

Ein gleichwertiges Modul fuer Webprojekte sollte mindestens folgende Eigenschaften besitzen:

- profilgesteuerte Rubriken
- Einzel- und Batch-Verarbeitung
- nachvollziehbare Teilkriterien mit Punkten
- Markdown- und HTML-Berichte
- sichere Verarbeitung von Uploads
- klare Kennzeichnung als Vorbewertung mit manueller Endpruefung

### Verbesserungen fuer ein besseres Modul

Ein besseres Modul sollte zusaetzlich leisten:

- Browserpruefung mit Playwright oder aehnlichem Tool
- Stack-spezifische Profile fuer statische Seiten, Vite, React, Vue oder Next
- AST-basierte Analyse fuer JavaScript oder TypeScript statt nur Regex
- Accessibility-Heuristiken
- Responsive-Checks fuer typische Bildschirmbreiten
- Sicherheitschecks fuer haeufige Schuelerfehler bei Formularen und DOM-Manipulation
- JSON-Zwischenausgabe mit allen Evidenzen fuer spaetere Auswertung
- optionale KI-Stufe nur fuer Formulierung und Priorisierung, nicht fuer unkontrollierte Punktevergabe

## Empfohlene Repository-Struktur

Eine robuste Struktur fuer ein neues Repo kann so aussehen:

```text
scripts/
  grade_web_project.py
  batch_grade_web_projects.py
  grading_profiles/
    web_static_oberstufe.json
    web_react_oberstufe.json
    web_vue_oberstufe.json
  web_grader/
    archive.py
    project_detector.py
    static_analysis.py
    ast_analysis.py
    build_runner.py
    browser_checks.py
    scoring.py
    models.py
    profile_loader.py
    report_json.py
    report_text.py
    report_docx.py
    llm_review.py
tests/
  fixtures/
  test_profiles.py
  test_archive.py
  test_browser_checks.py
docs/
  WEB_BEWERTUNG/
    RUBRIKEN.md
    EVIDENZEN.md
    BETRIEB.md
```

## Empfohlene Verarbeitungspipeline fuer Webprojekte

### Phase 1: Projekt erkennen

Das Modul sollte nach dem Entpacken zuerst bestimmen, mit welcher Art Projekt es zu tun hat:

- statische HTML-CSS-JS-Seite
- Vite-Projekt
- React-Projekt
- Vue-Projekt
- Next-Projekt
- unbekanntes oder unvollstaendiges Projekt

Detektionssignale koennen sein:

- package.json
- vite.config.*
- next.config.*
- src/main.*
- public/index.html
- index.html im Root

### Phase 2: Statische Pruefungen

Diese Phase ersetzt im Webkontext die Java-Regex-Heuristiken.

Geeignete Pruefungen sind:

- wichtige Dateien vorhanden
- sinnvolle Projektstruktur vorhanden
- HTML mit title, meta viewport und semantischen Elementen
- CSS-Dateien oder CSS-Module vorhanden
- JavaScript oder TypeScript mit Event-Handling vorhanden
- Formulareingaben werden validiert
- asynchrone Fehler werden behandelt
- offensichtliche Anti-Patterns werden markiert

Beispiele fuer Anti-Patterns:

- fehlender viewport meta tag
- reine div-Struktur ohne semantische Elemente
- Formulare ohne label
- ungesicherte Nutzung von innerHTML
- fetch ohne Fehlerbehandlung
- nur Inline-CSS ohne erkennbare Struktur

### Phase 3: Build und Start

Fuer moderne Webprojekte reicht statische Analyse nicht aus. Das Modul sollte in isolierter Umgebung pruefen:

- laesst sich das Projekt installieren
- laesst es sich bauen
- startet ein lokaler Dev- oder Preview-Server
- liefert die Anwendung eine gueltige HTTP-Antwort

Wichtig:

- mit Timeouts arbeiten
- in Container oder Sandbox ausfuehren
- keine Secrets bereitstellen
- Netzwerkzugriffe nach Moeglichkeit deaktivieren oder begrenzen

### Phase 4: Browser-Smoketests

Das ist der groesste Qualitaetssprung gegenueber dem bestehenden Modul.

Der Browser-Test sollte mindestens pruefen:

- Startseite laedt ohne White Screen
- keine offensichtlichen fatalen Konsolenfehler
- Hauptnavigation vorhanden
- zentrale Interaktion funktioniert
- Layout bleibt bei Desktop und Mobile benutzbar

Je nach Aufgabentyp koennen weitere Checks folgen:

- Formular absenden
- Filter oder Suche benutzen
- Datensatz anlegen und wieder anzeigen
- Local Storage oder API-Mock korrekt verwenden

## Empfohlene Rubrik fuer Webprojekte der Oberstufe

Eine gute Oberstufen-Rubrik sollte fachlich breit, aber nicht akademisch ueberladen sein. Ein brauchbares Raster ist:

### A. Formales und Projektstruktur

- Projekt startet oder ist nachvollziehbar aufsetzbar
- sinnvolle Ordnerstruktur
- lesbare Dateibenennung
- README mit kurzem Start-Hinweis

### B. Funktionalitaet

- Kernanforderungen sind umgesetzt
- Interaktionen funktionieren stabil
- Datenfluss oder Zustand ist nachvollziehbar
- Fehlerfaelle sind sichtbar behandelt

### C. HTML und Semantik

- sinnvolle Ueberschriftenstruktur
- Formulare korrekt beschriftet
- semantische Elemente statt reiner div-Waende
- Bilder mit alt-Texten

### D. CSS und Responsive Design

- konsistentes Layout
- brauchbare Abstaende und Lesbarkeit
- mobile Darstellung benutzbar
- kein schwerer Layoutbruch bei typischen Breiten

### E. JavaScript oder TypeScript-Qualitaet

- Event-Handling nachvollziehbar
- Funktionen sind sinnvoll geschnitten
- keine uebermaessige globale Logik
- Validierung und Fehlerbehandlung vorhanden

### F. Barrierearmut und UX

- Fokuszustand erkennbar
- Kontraste ausreichend oder zumindest vertretbar
- Tastaturbedienung fuer zentrale Elemente moeglich
- Fehlermeldungen sind fuer Nutzerinnen und Nutzer verstaendlich

### G. Sicherheit und Datenverarbeitung

- keine unkontrollierte innerHTML-Nutzung
- Eingaben werden validiert
- sensible Daten werden nicht im Klartext verarbeitet
- keine offensichtlichen Geheimnisse im Repository

### H. Individualisierung und Eigenleistung

- erkennbarer eigener gestalterischer oder funktionaler Mehrwert
- Erweiterung ueber das Mindestziel hinaus
- saubere thematische Konsistenz

## Sinnvolle Regeltypen fuer das neue Modul

Die bestehenden Regeltypen sind ein guter Anfang, reichen fuer Webprojekte aber nicht aus. Das neue Modul sollte mindestens diese Typen unterstuetzen:

- file_exists
- min_files_glob
- contains_regex
- not_contains_regex
- any_of
- all_of
- package_script_exists
- dependency_present
- json_path_equals
- html_selector_exists
- html_selector_count_min
- css_rule_exists
- js_ast_pattern
- build_command_succeeds
- test_command_succeeds
- http_status_ok
- browser_text_visible
- browser_click_flow
- console_errors_max
- screenshot_diff_below

## Beispiel fuer ein Web-Profil

```json
{
  "profile_name": "Web Oberstufe 2026",
  "project_type": "web-static-or-vite",
  "expected_root": ".",
  "grade_scale": {
    "best": 1.0,
    "worst": 6.0
  },
  "rules": [
    {
      "id": "A1",
      "title": "Projekt besitzt Einstiegspunkt",
      "description": "index.html oder ein Framework-Entry ist vorhanden.",
      "points": 2,
      "kind": "any_of",
      "config": {
        "rules": [
          {
            "kind": "file_exists",
            "config": { "glob": "index.html" }
          },
          {
            "kind": "file_exists",
            "config": { "glob": "src/main.*" }
          }
        ]
      }
    },
    {
      "id": "C1",
      "title": "Viewport gesetzt",
      "description": "Die Seite ist fuer mobile Darstellung vorbereitet.",
      "points": 1,
      "kind": "contains_regex",
      "config": {
        "target_glob": "index.html",
        "regex": "<meta[^>]*name=\"viewport\""
      }
    },
    {
      "id": "E2",
      "title": "Formularvalidierung vorhanden",
      "description": "Nutzereingaben werden nicht blind verarbeitet.",
      "points": 2,
      "kind": "js_ast_pattern",
      "config": {
        "pattern": "input-validation"
      }
    },
    {
      "id": "D2",
      "title": "Responsive Layout funktioniert",
      "description": "Die Kernansicht ist bei 390px und 1280px benutzbar.",
      "points": 3,
      "kind": "browser_click_flow",
      "config": {
        "scenario": "core-user-flow",
        "viewports": [390, 1280]
      }
    }
  ]
}
```

## Wann KI sinnvoll ist und wann nicht

### KI sollte nicht die primaere Punktevergabe uebernehmen

Wenn die KI ohne harte Evidenzen direkt benotet, entstehen fuer den Schulkontext drei Probleme:

- geringe Wiederholbarkeit
- schwer erklaerbare Urteile
- zu hohes Konfliktpotenzial gegenueber Lernenden und Eltern

### KI sollte als zweite Stufe eingesetzt werden

Sinnvoll ist eine optionale KI-Stufe nach der regelbasierten Analyse. Sie bekommt nur:

- Rubrik
- strukturierte Ergebnisse
- Build- und Browser-Evidenzen
- Screenshots
- kurze Codeauszuege

Die KI darf dann zum Beispiel:

- staerkste und schwaechste Punkte formulieren
- eine priorisierte Handlungsempfehlung erzeugen
- auffaellige Design- oder UX-Probleme zusammenfassen
- einen freundlichen, schuelergerechten Feedbacktext erzeugen

Die endgueltige Punktevergabe sollte trotzdem aus dem Regelsystem oder aus explizit freigegebenen Teilstufen kommen.

## Sicherheitsanforderungen fuer ein Web-Bewertungsmodul

Webprojekte sind riskanter als reine Quellcode-Heuristiken. Das Modul sollte deshalb mindestens diese Schutzmechanismen enthalten:

- Ausfuehrung in isoliertem Container
- CPU-, RAM- und Laufzeitlimits
- keine Weitergabe von Tokens, Passwoertern oder lokalen Secrets
- no install without policy: nur definierte Build-Schritte erlauben
- optional nur lockfile-basierte Installationen
- Logging aller ausgefuehrten Kommandos
- saubere Trennung zwischen untrusted student code und dem Grader selbst

## Reporting: Was die neue Ausgabe enthalten sollte

Die bestehende Mehrfachausgabe ist ein gutes Vorbild. Fuer Webprojekte sollte sie erweitert werden.

### Pflichtausgaben

- report.json mit allen Rohdaten und Evidenzen
- report.md fuer schnelle manuelle Sichtung
- report.html fuer saubere Weitergabe
- optional report.docx oder PDF fuer schulische Dokumentationsprozesse

### Sinnvolle Berichtsteile

- Metadaten zur Abgabe
- erkannter Projekttyp
- Punktestand und Note
- Teilkriterien mit Evidenzen
- Build-Ergebnis
- Browser-Ergebnis
- priorisierte Verbesserungen
- Hinweis, welche Stellen manuell nachgeprueft werden sollen

## Teststrategie fuer das Bewertungsmodul selbst

Ein solches Modul darf nicht nur Schuelerprojekte testen. Es muss auch selbst testbar sein.

### Unbedingt abdecken

- sichere ZIP-Extraktion
- Profilvalidierung
- Regel-Engine je Regeltyp
- Projekt-Typ-Erkennung
- Build-Timeouts und Fehlerfaelle
- Browser-Szenarien gegen Beispielprojekte
- Report-Generierung
- Batch-Lauf mit fehlerhaften und gueltigen Projekten gemischt

### Empfohlene Test-Faelle

- sehr gutes Projekt
- knapp ausreichendes Projekt
- Projekt mit Build-Fehler
- Projekt mit falscher Ordnerstruktur
- Projekt mit responsivem Fehlerbild
- Projekt mit sicherheitskritischer DOM-Manipulation

## Konkreter Umsetzungsplan fuer ein neues Repo

### Phase 1: Gleichwertige Basis schaffen

Ziel: denselben Reifegrad wie das bestehende Java-Modul erreichen.

1. Sichere ZIP-Intake-Stufe bauen.
2. Profilformat fuer Web-Rubriken definieren.
3. Statische Regel-Engine fuer Dateien, HTML, CSS und JS bauen.
4. Markdown- und HTML-Reporting einfuehren.
5. Batch-Verarbeitung implementieren.

### Phase 2: Auf Webprojekte zuschneiden

Ziel: fachlich passend fuer Oberstufen-Webprojekte werden.

1. Projekttyp-Erkennung einfuehren.
2. Stack-spezifische Profile anlegen.
3. Responsive- und Accessibility-Heuristiken ergaenzen.
4. Sicherheitsregeln fuer Formulare und DOM-Manipulation aufnehmen.

### Phase 3: Qualitativ besser als das Ausgangsmodul werden

Ziel: echte funktionale Bewertung statt nur statischer Heuristik.

1. Build-Runner mit Sandbox einfuehren.
2. Browser-Smoketests mit Screenshots implementieren.
3. JSON-Rohreport als stabile Datengrundlage einfuehren.
4. Optionale KI-Stufe fuer Feedback-Texte und Priorisierung ergaenzen.

## Architekturentscheidung in einem Satz

Wenn das neue Modul fair, wiederholbar und schulisch belastbar sein soll, dann muss die Punktevergabe regel- und evidenzbasiert bleiben, waehrend KI nur die erklaerende zweite Schicht bildet.

## Kurzfassung fuer die Umsetzung

Uebernimm aus dem bestehenden Modul unbedingt:

- profilgesteuerte Rubriken
- sichere Intake-Pipeline
- modulare Trennung von Analyse, Scoring und Reporting
- Batch-Workflow
- manuell nachpruefbare Ausgaben

Erweitere fuer Webprojekte gezielt um:

- Projekttyp-Erkennung
- Build- und Startpruefung
- Browser-Smoketests
- Accessibility- und Responsive-Heuristiken
- JSON-Evidenzreport
- optionale KI-Zusammenfassung auf Basis harter Befunde

Dann entsteht kein unzuverlaessiger Chat-Korrektor, sondern ein belastbares Bewertungsmodul fuer Webprojekte im Oberstufenkontext.