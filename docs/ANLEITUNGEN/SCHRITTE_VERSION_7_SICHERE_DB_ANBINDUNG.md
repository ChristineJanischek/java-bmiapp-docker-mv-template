# Schritt-fuer-Schritt-Anleitung Version 7

## Thema

Sicherheit in DB-Anbindung - Risiken der Datenhaltung und Schutzmechanismen

Diese Version baut didaktisch auf der Musterloesung aus Version 5 auf:

- Version 5 hat Persistenz eingefuehrt (JSON-Dateispeicher, Strukturdenken, ID-Beziehungen).
- Version 7 erweitert das auf professionelle Datenbank-Sicherheit bei JDBC-Anbindung.

Die Lernenden gehen damit den naechsten realistischen Schritt:
von "Daten bleiben erhalten" zu "Daten bleiben sicher, korrekt und nachvollziehbar erhalten".

---

## Lernziele

Nach Version 7 sollen die Lernenden:

- konkrete Risiken bei DB-Anbindung benennen koennen,
- SQL-Injection verstehen und mit Prepared Statements verhindern,
- Zugangsdaten sicher verwalten (keine Secrets im Quellcode),
- Rollen und Rechte nach Least-Privilege-Prinzip einsetzen,
- Fehler sicher behandeln (intern detailliert, extern knapp),
- sicherheitsrelevante Aktionen nachvollziehbar protokollieren,
- zentrale Schutzmechanismen mit Tests belegen.

---

## Vorwissen und Werkzeuge

### Fachliches Vorwissen

- Version 5 abgeschlossen: Persistenz, IDs, Datenmodell
- Grundlagen MVC und Exception Handling
- SQL-Basics (SELECT, INSERT, UPDATE, DELETE)

### Technische Werkzeuge

- Java 21 und Maven
- JDBC-Treiber (MySQL Connector)
- Laufende MySQL/MariaDB-Instanz
- Testframework JUnit 5

### Pflicht-Lektuere (an passender Stelle nutzen)

- Datenbankgrundlagen und JDBC: [../JAVA_PROGRAMMIERUNG/DATENBANK.md](../JAVA_PROGRAMMIERUNG/DATENBANK.md)
- Secure Coding Grundprinzipien: [../BEST_PRACTICES/SECURE_CODING.md](../BEST_PRACTICES/SECURE_CODING.md)
- Exception Handling: [../BEST_PRACTICES/EXCEPTION_HANDLING.md](../BEST_PRACTICES/EXCEPTION_HANDLING.md)
- Unit-Testing: [../BEST_PRACTICES/UNIT_TESTING.md](../BEST_PRACTICES/UNIT_TESTING.md)
- Rueckbezug Version 5: [./SCHRITTE_VERSION_5_JSON_DATEISPEICHER.md](./SCHRITTE_VERSION_5_JSON_DATEISPEICHER.md)
- Rueckbezug Version 6: [./SCHRITTE_VERSION_6_SICHERE_DATENVERARBEITUNG.md](./SCHRITTE_VERSION_6_SICHERE_DATENVERARBEITUNG.md)

---

## Didaktischer Leitgedanke

Wie beim Uebergang von Version 4 auf 5 wird nicht alles neu gebaut.
Die vorhandene Loesung wird gezielt erweitert.

In Version 7 ist die Leitfrage:

"Welche Stellen in der Datenbankanbindung koennen unsicher werden, und wie haerten wir sie schrittweise?"

Die Schritte unten sind so aufgebaut, dass die Lernenden selbststaendig arbeiten koennen,
aber zu jedem Schritt eine klare fachliche Begruendung erhalten.

### Paedagogische Leitlinien dieser Version

- praktisch: Jede Schutzmassnahme wird sofort am eigenen Code angewendet.
- didaktisch klug: Erst Risiko verstehen, dann gezielt absichern.
- motivierend: Die Lernenden "hacken" ihre eigene Teststrecke kontrolliert und machen Sicherheitsfortschritt sichtbar.
- erkundend: Jede Gruppe prueft mindestens ein eigenes Angriffsszenario zusaetzlich zu den Pflichttests.
- best-practice-orientiert: Industriestandards (Prepared Statements, Least Privilege, Logging, Transaktionen) werden begruendet eingefuehrt.

---

## Schritt 1: Risikomodell der aktuellen Loesung erstellen

### Auftrag

Analysiert eure Version-5-Anwendung und markiert alle Stellen, an denen Daten in Richtung Persistenz fliessen:

1. Eingabe in GUI
2. Controller/Service
3. SQL-Statement bzw. Datenbankzugriff
4. Speicherung/Logging

### Typische Risiken

- SQL-Injection durch String-Konkatenation
- Klartext-Secrets im Code oder in Git
- zu breite DB-Rechte (z. B. DROP erlaubt)
- sensible Daten im Log
- Fehlertexte mit internen Details in der GUI

### Ergebnis

Erstellt eine kleine Tabelle "Risiko -> Schutzmassnahme".
Diese Tabelle wird in den folgenden Schritten systematisch abgearbeitet.

---

## Schritt 2: Sichere DB-Konfiguration einziehen

### Auftrag

Ersetzt harte Zugangsdaten im Code durch Umgebungsvariablen.

### Warum?

- Zugangsdaten im Repository sind ein reales Sicherheitsrisiko.
- Konfiguration muss von der Anwendungslogik getrennt bleiben.

### Umsetzung (Beispiel)

```java
public final class DbConfig {
    private DbConfig() {}

    public static String url() {
        return required("BMI_DB_URL");
    }

    public static String user() {
        return required("BMI_DB_USER");
    }

    public static String password() {
        return required("BMI_DB_PASSWORD");
    }

    private static String required(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Fehlende Umgebungsvariable: " + key);
        }
        return value;
    }
}
```

### Hinweis fuer Lernende

Wenn ihr lokal entwickelt, koennt ihr Variablen im Terminal setzen,
ohne sie in Dateien einzuchecken.

---

## Schritt 3: SQL-Injection Schutz mit Prepared Statements

### Auftrag

Ersetzt unsichere SQL-Erzeugung durch parametrisierte Statements.

### Unsicheres Muster (nicht verwenden)

```java
String sql = "SELECT * FROM person WHERE vorname = '" + input + "'";
```

### Sicheres Muster

```java
String sql = "SELECT id, vorname, nachname FROM person WHERE vorname = ?";
try (Connection conn = connector.verbinden();
     PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setString(1, input);
    try (ResultSet rs = ps.executeQuery()) {
        // Verarbeitung
    }
}
```

### Didaktischer Fokus

Lernende sollen verstehen:

- Trennung von Befehl und Daten
- Warum Escaping allein nicht ausreicht
- Warum dieses Muster Standard in professionellen Projekten ist

Vertiefung: [../JAVA_PROGRAMMIERUNG/DATENBANK.md](../JAVA_PROGRAMMIERUNG/DATENBANK.md)

---

## Schritt 4: Rechtekonzept (Least Privilege)

### Auftrag

Legt einen eigenen App-DB-User an, der nur notwendige Rechte besitzt.

### Beispielidee

- `SELECT`, `INSERT`, `UPDATE`, `DELETE` auf benoetigten Tabellen
- keine Admin-Rechte
- kein `DROP`, kein `ALTER`

### Warum?

Wenn ein Angriff gelingt, begrenzen geringe Rechte den Schaden.

### Reflexionsfrage

"Was waere das Worst-Case-Szenario, wenn der App-User Admin-Rechte hat?"

---

## Schritt 5: Eingabevalidierung nah an der Fachlogik

### Auftrag

Validiert Nutzereingaben vor dem DB-Zugriff konsequent mit Whitelist-Regeln.

### Minimalbeispiele

- Name: Zeichenklasse und Laenge
- Alter: Wertebereich
- Groesse/Gewicht: plausible Grenzen

### Warum doppelt denken?

- GUI-Validierung verbessert Usability
- Service/Controller-Validierung ist Sicherheitsgrenze

Rueckbezug: [./SCHRITTE_VERSION_6_SICHERE_DATENVERARBEITUNG.md](./SCHRITTE_VERSION_6_SICHERE_DATENVERARBEITUNG.md)

---

## Schritt 6: Sichere Fehlerbehandlung bei DB-Operationen

### Auftrag

Fuehrt ein einheitliches Fehlerkonzept ein:

- intern: detailliertes Logging inkl. Stacktrace
- extern: generische, lernfreundliche Meldung ohne DB-Interna

### Beispielmuster

```java
try {
    repository.savePerson(person);
} catch (SQLException e) {
    secureLogger.logError("DB_SAVE_PERSON", currentUserId, e);
    throw new IllegalStateException("Daten konnten nicht gespeichert werden. Bitte erneut versuchen.");
}
```

### Didaktischer Fokus

Lernende erleben den Unterschied zwischen:

- technische Ursache (für Entwickler)
- sichere Nutzerkommunikation (für Endnutzer)

Vertiefung: [../BEST_PRACTICES/EXCEPTION_HANDLING.md](../BEST_PRACTICES/EXCEPTION_HANDLING.md)

---

## Schritt 7: Audit Logging fuer Nachvollziehbarkeit

### Auftrag

Protokolliert sicherheitsrelevante Aktionen standardisiert.

### Loggt mindestens

- Aktion (`PERSON_CREATED`, `MEASUREMENT_ADDED`, `DB_LOGIN_FAILED`)
- Zeitpunkt
- technische Benutzerkennung
- Status (`SUCCESS`/`ERROR`)

### Nicht loggen

- Klartext-Passwoerter
- vollstaendige sensible personenbezogene Inhalte

### Warum?

Audit-Logs helfen bei Fehlersuche und Sicherheitsanalyse,
ohne Datenschutzprinzipien zu verletzen.

---

## Schritt 8: Transaktionen bewusst einsetzen

### Auftrag

Koppelt zusammengehoerige DB-Aktionen in einer Transaktion.

### Beispiel

Person anlegen und erste Messung speichern:

```java
conn.setAutoCommit(false);
try {
    int personId = personRepository.insert(conn, person);
    messungRepository.insert(conn, personId, messung);
    conn.commit();
} catch (SQLException ex) {
    conn.rollback();
    throw ex;
} finally {
    conn.setAutoCommit(true);
}
```

### Didaktischer Fokus

Lernende verstehen, warum Konsistenz ein Sicherheits- und Qualitaetsthema ist.

---

## Schritt 9: Datenschutz in der Datenhaltung konkretisieren

### Auftrag

Formuliert und implementiert fuer eure App:

- Datensparsamkeit: Nur notwendige Felder speichern
- Loeschkonzept: Daten pro Person loeschbar
- Exportkonzept: Transparenz fuer Betroffene

### Unterrichtsimpuls

"Welche Daten brauchen wir wirklich fuer die BMI-Funktion und welche nicht?"

---

## Schritt 10: Sicherheits-Checkliste als Abnahme

Eine Version-7-Loesung gilt als "fertig", wenn folgende Punkte erfuellt sind:

- [ ] Keine DB-Secrets im Quellcode
- [ ] Alle SQL-Zugriffe mit Prepared Statements
- [ ] DB-User mit minimalen Rechten
- [ ] Validierung in Service/Controller vorhanden
- [ ] Fehler intern detailliert, extern generisch
- [ ] Audit-Logging implementiert ohne sensible Inhalte
- [ ] Mindestens 3 Sicherheits-Tests erfolgreich

---

## 3 Tests mit Musterloesungen

Die folgenden Tests sind so formuliert, dass Lernende zuerst selbst loesen koennen.
Die Musterloesung kommt jeweils direkt danach.

### Test 1: SQL-Injection abwehren

#### Aufgabe

Schreibt einen Test, der prueft, dass ein Eingabewert wie
`' OR '1'='1` nicht zu einem unkontrollierten Treffer fuehrt.

#### Musterloesung (JUnit 5, Beispiel)

```java
@Test
void searchByFirstName_blocksInjectionPayload() {
    String payload = "' OR '1'='1";

    List<Person> result = personRepository.findByFirstName(payload);

    assertNotNull(result);
    assertEquals(0, result.size(), "Injection-Payload darf keine Treffer erzeugen");
}
```

#### Auswertungshinweis

Wenn der Test fehlschlaegt, wird sehr wahrscheinlich SQL noch per String-Konkatenation gebaut.

---

### Test 2: Keine Secrets im Log

#### Aufgabe

Prueft, dass beim Fehlschlag einer DB-Anmeldung das Passwort nicht im Log erscheint.

#### Musterloesung (prinzipiell)

```java
@Test
void logOnDbFailure_masksSensitiveValues() {
    String rawMessage = "DB login failed user=app_user password=TopSecret123";

    String sanitized = SecureLogger.sanitizeLogMessage(rawMessage);

    assertFalse(sanitized.contains("TopSecret123"));
    assertTrue(sanitized.contains("password=***"));
}
```

#### Auswertungshinweis

Die zentrale Lernidee: Fehlerdiagnose ja, Geheimnisse nein.

---

### Test 3: Rollback bei Teilfehler

#### Aufgabe

Prueft, dass bei einem Fehler in Schritt 2 einer Transaktion
nicht nur der zweite Insert scheitert, sondern auch der erste zurueckgerollt wird.

#### Musterloesung (Integrationsnahes Schema)

```java
@Test
void createPersonAndMeasurement_rollsBackCompletelyOnSecondInsertFailure() {
    Person person = new Person("Mia", "Muster", 16, "Frau", "mia@example.de");
    Messung invalidMessung = new Messung(-1.0, 1.68); // provoziert SQL/Validierungsfehler

    assertThrows(Exception.class, () ->
        service.createPersonWithInitialMeasurement(person, invalidMessung)
    );

    List<Person> persons = personRepository.findByLastName("Muster");
    assertEquals(0, persons.size(), "Bei Rollback darf keine Teiloperation verbleiben");
}
```

#### Auswertungshinweis

Wenn nach Fehlern Teilzustaende bleiben, fehlt ein sauberes Transaktionskonzept.

---

## Praktischer Umsetzungsfahrplan (fuer Schuelergruppen)

### Phase 1: Analyse

- Risiken der aktuellen Loesung dokumentieren
- Schutzmassnahmen priorisieren

### Phase 2: Technische Absicherung

- Konfiguration auf Umgebungsvariablen umstellen
- Prepared Statements in allen Repositories
- Rollen/Rechte am DB-User anpassen

### Phase 3: Qualitaet und Nachweis

- Fehlerbehandlung und Audit-Logging vereinheitlichen
- 3 Sicherheitstests gruen bekommen
- Kurzreflexion schreiben: "Welche Massnahme hatte den groessten Effekt?"

---

## Typische Fehlerbilder (ohne Redundanz, direkt als Diagnosehilfe)

1. SQL wird an einzelnen Stellen immer noch per String zusammengesetzt.
2. Passwort steht in `application.properties` und ist mit committed.
3. Fehlerdialog zeigt `SQLException` inklusive DB-URL.
4. Logs enthalten personenbezogene Volltexte.
5. Bei Teilfehlern bleiben Datensaetze halb gespeichert.

---

## Erwartung an eine gute Version-7-Schuelerloesung

Eine didaktisch und fachlich starke Loesung erkennt man daran, dass sie:

- nachvollziehbar vom Version-5-Stand weiterentwickelt wurde,
- Schutzmassnahmen begruendet statt nur eingebaut hat,
- Sicherheitsanforderungen in Code und Tests sichtbar macht,
- technische Sicherheit und Datenschutz zusammen denkt,
- eine reale, wartbare DB-Anbindung als Muster fuer Folgeprojekte liefert.

---

## Kurz-Zusammenfassung fuer Lernende

Version 7 bedeutet nicht "mehr Features", sondern "mehr Professionalitaet".

Das zentrale Ziel lautet:

Nicht nur Daten speichern,
sondern Datenhaltung verantwortungsvoll absichern.

Wenn eure Loesung Angriffe erschwert, Fehler kontrolliert behandelt,
und ihr das mit Tests nachweisen koennt,
dann habt ihr das Lernziel von Version 7 erreicht.