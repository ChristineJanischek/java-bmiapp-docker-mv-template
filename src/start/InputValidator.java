package start;

/**
 * InputValidator: Zentrale Validierungslogik
 * 
 * Nutzt Whitelist-Ansatz: Nur explizit erlaubte Eingaben werden akzeptiert.
 * 
 * @author BmiApp-Team
 * @version 6.0
 */
public class InputValidator {
    
    /**
     * Validiert einen Namen nach Whitelist-Kriterium.
     * 
     * Erlaubt: Buchstaben (auch Umlaute), Bindestrich, Leerzeichen.
     * Nicht erlaubt: Sonderzeichen, Zahlen, Länge > 50.
     * 
     * @param name Der zu validierende Name.
     * @return true, wenn Name valide; sonst false.
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (name.length() > 50) {
            return false;
        }
        // Regex: Nur Buchstaben (ASCII + Umlaute), Bindestrich, Leerzeichen am Anfang/Ende getrimmte Version
        return name.matches("^[a-zA-ZäöüßÄÖÜ\\-\\s]+$");
    }
    
    /**
     * Validiert ein Alter für die Anwendung.
     * 
     * Gesunde Range: 18-120 Jahre (medizinisch sinnvoll).
     * 
     * @param age Das zu validierende Alter in Jahren.
     * @return true, wenn Alter valide; sonst false.
     */
    public static boolean isValidAge(int age) {
        return age >= 18 && age <= 120;
    }
    
    /**
     * Validiert eine Körpergröße in cm.
     * 
     * Gesunde Range: 100-250 cm (realistische Grenzen).
     * 
     * @param height Körpergröße in Zentimetern.
     * @return true, wenn Höhe valide; sonst false.
     */
    public static boolean isValidHeight(double height) {
        return height >= 100 && height <= 250;
    }
    
    /**
     * Validiert ein Gewicht in kg.
     * 
     * Gesunde Range: 30-300 kg (realistische Grenzen für Erwachsene).
     * 
     * @param weight Gewicht in Kilogramm.
     * @return true, wenn Gewicht valide; sonst false.
     */
    public static boolean isValidWeight(double weight) {
        return weight >= 30 && weight <= 300;
    }
}
