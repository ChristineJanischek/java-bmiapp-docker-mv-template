# Versionsfahrplan und Branch-Strategie

Dieses Dokument beschreibt die didaktischen Versions-Branches des Repositories und ihre empfohlene Reihenfolge.

## Überblick

| Branch | Rolle | Inhalt |
|---|---|---|
| `main` | Einstiegspunkt | Version 0 für den Unterricht plus integrierter Referenzstand des Repos |
| `version-0-grundgeruest` | Alternative Basis | bewusst reduziertes Schüler-Grundgerüst |
| `version-1-mvc-gui` | Lernversion | MVC + GUI |
| `version-2-methoden` | Lernversion | Methoden, Kontrollstrukturen, Algorithmen |
| `version-3-validation` | Lernversion | Validierung und Fehlerbehandlung |
| `version-4-assoziationen` | Lernversion | Assoziationen, Personen, Messungen |
| `version-5-dateispeicher` | Lernversion | JSON-Dateispeicher und Persistenz |
| `version-6-sichere-datenverarbeitung` | Lernversion | Sichere Datenverarbeitung, Logging, Datenschutzprinzipien |
| `version-7-sichere-db-anbindung` | Lernversion | Sicherheit in DB-Anbindung, Risiken der Datenhaltung, Schutzmechanismen |

## Empfohlene Reihenfolge

1. `main`
2. `version-1-mvc-gui`
3. `version-2-methoden`
4. `version-3-validation`
5. `version-4-assoziationen`
6. `version-5-dateispeicher`
7. `version-6-sichere-datenverarbeitung`
8. `version-7-sichere-db-anbindung`

Wenn ein noch stärker reduzierter Startpunkt gebraucht wird, kann alternativ `version-0-grundgeruest` vor `version-1-mvc-gui` verwendet werden.

## Passende Anleitungen

- Version 0: [../ANLEITUNGEN/ANLEITUNG_VERSION_0_GRUNDGERUEST.md](../ANLEITUNGEN/ANLEITUNG_VERSION_0_GRUNDGERUEST.md)
- Version 1: [../ANLEITUNGEN/ANLEITUNG_VERSION_1_MVC_GUI.md](../ANLEITUNGEN/ANLEITUNG_VERSION_1_MVC_GUI.md)
- Version 2: [../ANLEITUNGEN/ANLEITUNG_VERSION_2_METHODEN.md](../ANLEITUNGEN/ANLEITUNG_VERSION_2_METHODEN.md)
- Version 3: [../ANLEITUNGEN/ANLEITUNG_VERSION_3_VALIDIERUNG.md](../ANLEITUNGEN/ANLEITUNG_VERSION_3_VALIDIERUNG.md)
- Version 4: [../ANLEITUNGEN/ANLEITUNG_VERSION_4_ASSOZIATIONEN.md](../ANLEITUNGEN/ANLEITUNG_VERSION_4_ASSOZIATIONEN.md)
- Version 5: [../ANLEITUNGEN/ANLEITUNG_VERSION_5_JSON_DATEISPEICHER.md](../ANLEITUNGEN/ANLEITUNG_VERSION_5_JSON_DATEISPEICHER.md)
- Version 6: [../ANLEITUNGEN/ANLEITUNG_VERSION_6_SICHERE_DATENVERARBEITUNG.md](../ANLEITUNGEN/ANLEITUNG_VERSION_6_SICHERE_DATENVERARBEITUNG.md)
- Version 7: [../ANLEITUNGEN/ANLEITUNG_VERSION_7_SICHERE_DB_ANBINDUNG.md](../ANLEITUNGEN/ANLEITUNG_VERSION_7_SICHERE_DB_ANBINDUNG.md)

## Hinweise zur Pflege

- Neue Unterrichtsversionen erhalten möglichst einen eigenen, klar benannten Branch.
- Die README bleibt der zentrale Navigationspunkt für Lernende und Lehrkräfte.
- Dieses Dokument ergänzt die README um eine kompakte Branch-Referenz und sollte bei neuen Versionen mitgezogen werden.