package start;

/**
 * Model-Klasse für BMI-Berechnungen (Version 0 - Stub/Template).
 * 
 * AUFGABE FÜR SCHÜLER:
 * Implementiere die TODO-markierten Methoden gemäß den Anforderungen in SCHRITTE_VERSION_0.md
 */
public class Bmirechner {
    // Attribute
    private double gewicht;
    private double groesse;
    private double ergebnis;
    private String kategorie;

    /**
     * Konstruktor: Initialisiert ein neues Bmirechner-Objekt
     */
    public Bmirechner() {
        this.kategorie = "Noch nicht berechnet";
        this.ergebnis = 0.0;
    }

    // ==================== GETTER/SETTER ====================
    
    public double getGewicht() {
        return gewicht;
    }

    public void setGewicht(double pGewicht) {
        this.gewicht = pGewicht;
    }

    public double getGroesse() {
        return groesse;
    }

    public void setGroesse(double pGroesse) {
        this.groesse = pGroesse;
    }

    public double getErgebnis() {
        return ergebnis;
    }

    public String getKategorie() {
        return kategorie;
    }

    // ==================== METHODEN (ZU IMPLEMENTIEREN) ====================

    /**
     * TODO: Implementiere diese Methode!
     * 
     * Berechnet den BMI (Body Mass Index) nach der Formel:
     * BMI = Gewicht / (Größe * Größe)
     * 
     * Beispiel: 75 kg bei 1.75 m → BMI = 75 / (1.75 * 1.75) = 24.49
     * 
     * @param pGewicht Körpergewicht in Kilogramm (z.B. 75.0)
     * @param pGroesse Körpergröße in Metern (z.B. 1.75)
     * @return Der berechnete BMI-Wert
     */
    public double berechne(double pGewicht, double pGroesse) {
        // TODO: Implementiere die BMI-Berechnung
        // Speichere gewicht, groesse und ergebnis in den Attributen
        
        // Dummy-Implementierung (entfernen und ersetzen!):
        this.gewicht = pGewicht;
        this.groesse = pGroesse;
        this.ergebnis = 0.0; // TODO: Berechne den echten BMI-Wert!
        
        return this.ergebnis;
    }

    /**
     * TODO: Implementiere diese Methode!
     * 
     * Interpretiert den BMI-Wert und ordnet ihn einer Kategorie zu.
     * 
     * BMI-Kategorien (WHO-Standard):
     * - < 16          : "Starkes Untergewicht"
     * - 16 - < 17     : "Mäßiges Untergewicht"
     * - 17 - < 18.5   : "Leichtes Untergewicht"
     * - 18.5 - < 25   : "Normalgewicht"
     * - 25 - < 30     : "Prädipositas"
     * - 30 - < 35     : "Adipositas Grad I"
     * - 35 - < 40     : "Adipositas Grad II"
     * - >= 40         : "Adipositas Grad III"
     * 
     * @param pGewicht Körpergewicht in kg
     * @param pGroesse Körpergröße in m
     */
    public void interpretiere(double pGewicht, double pGroesse) {
        // TODO: Implementiere die BMI-Interpretation
        // 1. Berechne zuerst den BMI (oder nutze bereits berechneten Wert)
        // 2. Ordne den BMI-Wert der passenden Kategorie zu
        // 3. Speichere die Kategorie im Attribut 'kategorie'
        
        // Dummy-Implementierung (entfernen und ersetzen!):
        this.kategorie = "TODO: Implementiere die BMI-Kategorien!";
    }

    /**
     * TODO: (Optional) Überschreibe toString() für lesbare Ausgabe
     * 
     * Gibt eine lesbare String-Repräsentation des Objekts zurück.
     * 
     * Beispiel-Ausgabe:
     * "BMI: 24.49 (Normalgewicht)"
     * 
     * @return String-Repräsentation des BMI-Rechners
     */
    @Override
    public String toString() {
        // TODO: (Optional) Implementiere eine aussagekräftige toString()-Methode
        return String.format("BMI: %.2f (%s)", ergebnis, kategorie);
    }
}
