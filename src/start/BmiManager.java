package start;

/**
 * Controller-Klasse nach dem MVC-Prinzip.
 * Steuert die Interaktion zwischen Model (Bmirechner) und View (Main).
 */
public class BmiManager {
    // Attribut: Referenz auf das Model (Bmirechner)
    // Das Model speichert die Daten und Logik der BMI-Berechnung
    private Bmirechner model;

    /**
     * Standard-Konstruktor: Initialisiert den Manager mit einem neuen Model-Objekt.
     * Konstruktoren werden beim Erzeugen eines Objekts aufgerufen und setzen Anfangswerte.
     */
    public BmiManager() {
        this.model = new Bmirechner();
    }

    /**
     * Konstruktor mit Parameter: Ermöglicht das Setzen eines bestehenden Model-Objekts.
     */
    public BmiManager(Bmirechner pModel) {
        this.model = pModel;
    }

    /**
     * Fachmethode: Führt die BMI-Berechnung aus, indem sie die Methode des Models aufruft.
     */
    public double berechneBMI(double pGewicht, double pGroesse) {
        return model.berechne(pGewicht, pGroesse);
    }

    /**
     * Fachmethode: Interpretiert das BMI-Ergebnis über das Model.
     */
    public void interpretiereBMI() {
        model.interpretiere();
    }

    /**
     * Getter: Gibt das Model-Objekt zurück.
     * Getter ermöglichen kontrollierten Lesezugriff auf private Attribute.
     */
    public Bmirechner getModel() {
        return model;
    }

    /**
     * Setter: Setzt das Model-Objekt.
     * Setter ermöglichen kontrollierten Schreibzugriff und können Validierung enthalten.
     */
    public void setModel(Bmirechner pModel) {
        this.model = pModel;
    }
}
