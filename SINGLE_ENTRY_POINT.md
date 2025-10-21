# Single Entry Point-Prinzip (SEP)

Das Single Entry Point-Prinzip (SEP) ist ein grundlegendes Architekturprinzip für Softwareanwendungen. Es besagt, dass ein Programm oder ein Subsystem genau einen definierten Einstiegspunkt für die Ausführung haben sollte.

## 1. Definition
- **Single Entry Point**: Es gibt genau eine Stelle, an der die Ausführung des Programms beginnt.
- In Java ist dies typischerweise die Methode `public static void main(String[] args)`.
- Für größere Systeme gilt das Prinzip auch für Subsysteme, Module oder Services (z. B. ein zentrales API-Gateway).

## 2. Nutzen und Vorteile
- **Übersichtlichkeit**: Der Startpunkt ist für alle Entwickler klar ersichtlich.
- **Wartbarkeit**: Initialisierungen, Konfigurationen und Sicherheitsprüfungen werden zentral durchgeführt.
- **Sicherheit**: Es wird verhindert, dass das System an unerwarteten Stellen betreten wird (z. B. durch direkte Aufrufe von Hilfsmethoden oder internen Klassen).
- **Testbarkeit**: Der Einstiegspunkt kann gezielt getestet und überwacht werden.

## 3. Umsetzung in Java
- Die Klasse mit der `main`-Methode ist der einzige Einstiegspunkt für die Anwendung.
- Initialisierungen (z. B. Konfiguration, Logging, Security-Checks) werden dort zentral vorgenommen.
- Keine anderen Klassen sollten direkt von außen instanziiert oder aufgerufen werden, ohne über den Einstiegspunkt zu gehen.

**Beispiel:**
```java
public class Main {
    public static void main(String[] args) {
        // Zentrale Initialisierung
        System.out.println("BMI-App startet...");
        BmiManager manager = new BmiManager();
        MainWindow gui = new MainWindow(manager);
        gui.setVisible(true);
    }
}
```

## 4. Best Practices
- Nur die Main-Klasse (bzw. der Einstiegspunkt) ist von außen sichtbar (public).
- Initialisiere alle globalen Ressourcen (z. B. Datenbank, Logging) im Einstiegspunkt.
- Führe Security-Checks (z. B. Rechteprüfung, Input-Validierung) möglichst früh aus.
- Vermeide mehrere Einstiegspunkte (z. B. mehrere `main`-Methoden in verschiedenen Klassen).
- Für Webanwendungen: Ein zentrales Servlet, Controller oder API-Gateway als Einstiegspunkt.

## 5. Bezug zu Secure Coding
- Das SEP verhindert, dass Angreifer oder Nutzer das System an unerwarteten Stellen betreten können.
- Es erleichtert die Durchsetzung von Sicherheitsmaßnahmen (z. B. Authentifizierung, Logging) an einer zentralen Stelle.
- In Kombination mit Kapselung und Zugriffsmodifikatoren wird die Angriffsfläche minimiert.

## 6. Zusammenfassung
- Ein klar definierter Einstiegspunkt erhöht Übersicht, Wartbarkeit und Sicherheit.
- In Java ist dies die `main`-Methode; in Webanwendungen ein zentrales Servlet oder Controller.
- Das Prinzip ist ein wichtiger Baustein für sichere und wartbare Softwarearchitektur.
