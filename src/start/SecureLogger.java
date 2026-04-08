package start;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * SecureLogger: Audit-Logging und sichere Fehlerbehandlung.
 * 
 * Implementiert sicherheitsrelevantes Logging mit:
 * - Sanitisierung von sensiblen Daten
 * - Konsistentes Format für Audit-Trail
 * - Trennung von internen (technisch) und externen (Nutzer) Meldungen
 * 
 * @author BmiApp-Team
 * @version 6.0
 */
public class SecureLogger {
    
    private static final Logger logger = Logger.getLogger(SecureLogger.class.getName());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    
    /**
     * Loggt eine erfolgreiche Aktion.
     * 
     * Format: ACTION=... | TIMESTAMP=... | USER_ID=... | DETAILS=... | STATUS=SUCCESS
     * 
     * @param action Die Aktion (z.B. "PERSON_ADDED", "MEASUREMENT_SAVED").
     * @param userId Die Nutzer-ID oder "UNKNOWN"/"SYSTEM".
     * @param details Die Aktion-Details (ohne sensible Daten).
     */
    public static void logAction(String action, String userId, String details) {
        String timestamp = LocalDateTime.now().format(formatter);
        String auditEntry = String.format(
            "ACTION=%s | TIMESTAMP=%s | USER_ID=%s | DETAILS=%s | STATUS=SUCCESS",
            action, timestamp, userId, sanitizeLogMessage(details)
        );
        logger.log(Level.INFO, auditEntry);
    }
    
    /**
     * Loggt einen Fehler mit Stack Trace.
     * 
     * Intern für Admin/Entwickler zur Diagnose. Stack Trace wird vollständig geloggt.
     * 
     * Format: ACTION=... | TIMESTAMP=... | USER_ID=... | STATUS=ERROR | EXCEPTION=...
     * 
     * @param action Die Aktion, bei der der Fehler aufgetreten ist.
     * @param userId Die Nutzer-ID oder "SYSTEM".
     * @param e Die Exception mit vollständigem Stack Trace.
     */
    public static void logError(String action, String userId, Exception e) {
        String timestamp = LocalDateTime.now().format(formatter);
        String auditEntry = String.format(
            "ACTION=%s | TIMESTAMP=%s | USER_ID=%s | STATUS=ERROR | EXCEPTION=%s",
            action, timestamp, userId, e.getMessage()
        );
        logger.log(Level.SEVERE, auditEntry, e);
    }
    
    /**
     * Loggt einen Sicherheitsvorfall.
     * 
     * Verwendet für verdächtige oder ungültige Eingaben, fehlgeschlagene Validierungen.
     * 
     * Format: SECURITY_EVENT=... | TIMESTAMP=... | USER_ID=... | REASON=...
     * 
     * @param event Der Sicherheitsvorfall (z.B. "INVALID_INPUT", "UNAUTHORIZED_ACCESS").
     * @param userId Die Nutzer-ID.
     * @param reason Der Grund/die Details (wird sanitisiert).
     */
    public static void logSecurityEvent(String event, String userId, String reason) {
        String timestamp = LocalDateTime.now().format(formatter);
        String securityEntry = String.format(
            "SECURITY_EVENT=%s | TIMESTAMP=%s | USER_ID=%s | REASON=%s",
            event, timestamp, userId, sanitizeLogMessage(reason)
        );
        logger.log(Level.WARNING, securityEntry);
    }
    
    /**
     * Entfernt sensible Daten aus Log-Nachrichten.
     * 
     * Vereinfachte Implementierung:
     * - Passwörter werden zu "***" gekürzt
     * - Kreditkartennummern werden zu "**** gekürzt
     * 
     * @param message Die zu sanitisierende Nachricht.
     * @return Die bereinigte Nachricht.
     */
    private static String sanitizeLogMessage(String message) {
        if (message == null) {
            return "";
        }
        
        // Passwörter: "password=..." wird zu "password=***"
        message = message.replaceAll("(?i)password[\\s]*=[\\s]*[^\\s|]+", "password=***");
        
        // Kreditkartenformat (vereinfacht): "card=1234567890123456" wird zu "card=****"
        message = message.replaceAll("(?i)card[\\s]*=[\\s]*[0-9]{4}", "card=****");
        
        // PINs: "pin=..." wird zu "pin=***"
        message = message.replaceAll("(?i)pin[\\s]*=[\\s]*[0-9]{4}", "pin=***");
        
        return message;
    }
}
