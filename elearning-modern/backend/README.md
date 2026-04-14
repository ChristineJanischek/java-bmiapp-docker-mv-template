# Backend Blueprint (OOP, erweiterbar, DSGVO-orientiert)

## Ziel

Dieses Blueprint beschreibt ein wartbares Backend fuer die E-Learning-Modernisierung mit Java 21 und Spring Boot.

## Architekturprinzip

Schichtenmodell mit klarer Trennung:

1. domain
2. application
3. infrastructure
4. interfaces

### Beispielstruktur

```text
backend/
  src/main/java/de/schule/elearning/
    domain/
      model/
      service/
      repository/
    application/
      usecase/
      dto/
    infrastructure/
      persistence/
      security/
      logging/
    interfaces/
      rest/
      mapper/
```

## Kern-Use-Cases

- Lesson bereitstellen (Info, Task, Steps, Code)
- Lernfortschritt speichern
- Quiz-Ergebnisse erfassen
- Audit-Events protokollieren
- Rollenbasierte Sicht (Lernende/Lehrkraft/Admin)

## Sicherheitskern

- Inputvalidierung zentral im application-layer
- rollenbasiertes Access Control
- Prepared Statements oder ORM mit Parametrisierung
- keine Klartext-Secrets im Code
- strukturierte Audit-Logs ohne sensible Klartextdaten
- standardisierte Fehlerantworten fuer Clients

## API-Design (Auszug)

- GET /api/v1/modules
- GET /api/v1/modules/{moduleId}
- POST /api/v1/progress
- POST /api/v1/quiz-results
- GET /api/v1/teacher/overview

## Datenschutzbetrieb Schule

- Datenminimierung je Rolle
- Loeschkonzept mit Fristen
- Exportfaehigkeit fuer Auskunftsanfragen
- Hosting in EU-Infrastruktur

## Qualitaetssicherung

- Unit-Tests fuer Domainlogik
- Integrationstests fuer API/Persistenz
- Security-Tests fuer Auth, Rollen, Eingabevalidierung
- CI mit Quality Gate (Build, Tests, Lint, SAST)
