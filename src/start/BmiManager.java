package start;

/**
 * Controller-Klasse nach dem MVC-Prinzip.
 * Steuert die Interaktion zwischen Model (Bmirechner) und View (Main).
 */
public class BmiManager {
    private Bmirechner model;

    public BmiManager() {
        this.model = new Bmirechner();
    }

    public BmiManager(Bmirechner model) {
        this.model = model;
    }

    public double berechneBMI(double gewicht, double groesse) {
        return model.berechne(gewicht, groesse);
    }

    public void interpretiereBMI() {
        model.interpretiere();
    }

    public Bmirechner getModel() {
        return model;
    }

    public void setModel(Bmirechner model) {
        this.model = model;
    }
}
