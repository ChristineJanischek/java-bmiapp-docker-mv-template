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

    public BmiManager(Bmirechner pModel) {
        this.model = pModel;
    }

    public double berechneBMI(double pGewicht, double pGroesse) {
        return model.berechne(pGewicht, pGroesse);
    }

    public void interpretiereBMI() {
        model.interpretiere();
    }

    public Bmirechner getModel() {
        return model;
    }

    public void setModel(Bmirechner pModel) {
        this.model = pModel;
    }
}
