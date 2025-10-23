package start;

import javax.swing.JOptionPane;

/**
 * Controller-Klasse für BMI-Verwaltung (Version 0 - Stub/Template).
 * 
 * Der BmiManager verbindet View (GUI/MainWindow) und Model (Bmirechner).
 * Er steuert die Berechnungs- und Interpretationslogik.
 * 
 * AUFGABE FÜR SCHÜLER:
 * Implementiere die TODO-markierten Methoden gemäß MVC-Prinzip
 */
public class BmiManager {
    // Attribut: Das Model (Bmirechner-Objekt)
    private Bmirechner model;

    /**
     * Konstruktor: Erstellt einen neuen BmiManager mit leerem Model
     */
    public BmiManager() {
        this.model = new Bmirechner();
    }

    /**
     * Konstruktor mit vorhandenem Model (für fortgeschrittene Nutzung)
     * 
     * @param pModel Ein bereits existierendes Bmirechner-Objekt
     */
    public BmiManager(Bmirechner pModel) {
        this.model = pModel;
    }

    // ==================== GETTER/SETTER ====================

    /**
     * Gibt das Model (Bmirechner) zurück
     * 
     * @return Das Bmirechner-Objekt
     */
    public Bmirechner getModel() {
        return model;
    }

    /**
     * Setzt ein neues Model
     * 
     * @param pModel Das neue Bmirechner-Objekt
     */
    public void setModel(Bmirechner pModel) {
        this.model = pModel;
    }

    // ==================== CONTROLLER-METHODEN (ZU IMPLEMENTIEREN) ====================

    /**
     * TODO: Implementiere diese Methode!
     * 
     * Steuert die BMI-Berechnung im Model.
     * 
     * Diese Methode wird von der GUI aufgerufen, wenn der Benutzer
     * auf "Berechne BMI" klickt.
     * 
     * @param pGewicht Gewicht in kg (z.B. 75.0)
     * @param pGroesse Größe in m (z.B. 1.75)
     * @return Der berechnete BMI-Wert
     */
    public double berechneBMI(double pGewicht, double pGroesse) {
        // TODO: Implementiere die Controller-Logik
        // 1. Rufe die berechne()-Methode des Models auf
        // 2. Gib den BMI-Wert zurück
        
        // Dummy-Implementierung (entfernen und ersetzen!):
        return model.berechne(pGewicht, pGroesse);
    }

    /**
     * TODO: Implementiere diese Methode!
     * 
     * Steuert die BMI-Interpretation im Model.
     * 
     * Diese Methode wird von der GUI aufgerufen, wenn der Benutzer
     * auf "Interpretiere BMI" klickt.
     */
    public void interpretiereBMI() {
        // TODO: Implementiere die Controller-Logik
        // 1. Hole Gewicht und Größe aus dem Model
        // 2. Rufe die interpretiere()-Methode des Models auf
        
        // Dummy-Implementierung (entfernen und ersetzen!):
        double gewicht = model.getGewicht();
        double groesse = model.getGroesse();
        model.interpretiere(gewicht, groesse);
    }

    /**
     * TODO: (Version 2) Erweiterte Methode mit Alter & Geschlecht
     * 
     * Diese Methode wird in Version 2 benötigt und ist hier als
     * Vorbereitung bereits vorhanden.
     * 
     * @param pGewicht Gewicht in kg
     * @param pGroesse Größe in m
     * @param pAlter Alter in Jahren (0 = nicht angegeben)
     * @param pGeschlecht "Mann", "Frau" oder null
     */
    public void interpretiereIntelligent(double pGewicht, double pGroesse, int pAlter, String pGeschlecht) {
        // Version 0: Nutze einfache Interpretation
        berechneBMI(pGewicht, pGroesse);
        interpretiereBMI();
    }

    /**
     * TODO: (Optional) Zeigt die Interpretation in einem Dialog
     * 
     * Diese Methode wird in Version 1+ verwendet, um die BMI-Kategorie
     * in einem Popup-Fenster anzuzeigen.
     */
    public void zeigeInterpretation() {
        String kategorie = model.getKategorie();
        JOptionPane.showMessageDialog(null, 
            "BMI-Kategorie: " + kategorie,
            "BMI-Interpretation",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * TODO: (Optional) Überschreibe toString() für Debugging
     * 
     * @return String-Repräsentation des Controllers
     */
    @Override
    public String toString() {
        return "BmiManager [model=" + model.toString() + "]";
    }
}
