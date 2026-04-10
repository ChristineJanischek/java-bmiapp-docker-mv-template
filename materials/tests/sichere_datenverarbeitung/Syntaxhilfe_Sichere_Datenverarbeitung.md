# Syntaxhilfe: Sichere Datenverarbeitung (Version 6)

**Ziel:** Eingaben sicher verarbeiten, Fehler sauber behandeln und sensible Daten schuetzen.

---

## 1. Whitelist-Validierung

```java
public static boolean isValidName(String name) {
    if (name == null || name.trim().isEmpty()) {
        return false;
    }
    if (name.length() > 50) {
        return false;
    }
    return name.matches("^[a-zA-ZaeoeuessAEOEUE\\-\\s]+$");
}
```

Merksatz:

- Whitelist bedeutet: Nur erlaubte Muster akzeptieren.
- Blacklist ist unsicherer, weil nie alle Angriffsvarianten bekannt sind.

---

## 2. Plausibilitaetspruefungen fuer Zahlen

```java
public static boolean isValidAge(int age) {
    return age >= 18 && age <= 120;
}

public static boolean isValidHeight(double height) {
    return height >= 100 && height <= 250;
}

public static boolean isValidWeight(double weight) {
    return weight >= 30 && weight <= 300;
}
```

Merksatz:

- Grenzwerte immer explizit testen (min, max, knapp darunter, knapp darueber).

---

## 3. Sichere Fehlerbehandlung

```java
try {
    service.save(data);
} catch (Exception ex) {
    SecureLogger.logError("SAVE_DATA", "U001", ex); // intern detailliert
    showError("Daten konnten nicht gespeichert werden. Bitte erneut versuchen."); // extern generisch
}
```

Merksatz:

- Intern: Details ins Log.
- Extern: Keine Systeminterna anzeigen.

---

## 4. Audit-Logging

```java
SecureLogger.logAction("MEASUREMENT_ADDED", "U001", "Messung gespeichert");
SecureLogger.logSecurityEvent("INVALID_INPUT", "U001", "weight ausserhalb Bereich");
```

Ein sicherer Audit-Eintrag enthaelt:

- Aktion
- Zeitstempel
- technische Nutzerkennung
- Status

Nicht loggen:

- Klartext-Passwoerter
- unnötige sensible Personendaten

---

## 5. Sensible Daten im Log maskieren

```java
private static String sanitizeLogMessage(String message) {
    if (message == null) return "";
    message = message.replaceAll("(?i)password[\\s]*=[\\s]*[^\\s|]+", "password=***");
    message = message.replaceAll("(?i)card[\\s]*=[\\s]*[0-9]{4}", "card=****");
    return message;
}
```

---

## 6. Mini-Checkliste vor Abgabe

- [ ] Alle Eingaben validiert (String + Zahlenwerte)
- [ ] Keine internen Fehlerdetails in der GUI
- [ ] Audit-Logs fuer sicherheitsrelevante Aktionen vorhanden
- [ ] Sensible Daten werden nicht im Klartext geloggt
- [ ] Grenzwerttests durchgefuehrt
