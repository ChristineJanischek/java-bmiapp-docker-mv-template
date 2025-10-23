package start;

import javax.swing.JOptionPane;

/**
 * Controller-Klasse nach dem MVC-Prinzip.
 * Steuert die Interaktion zwischen Model (Bmirechner) und View (Main/MainWindow).
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

    /**
     * Polymorphe Fachmethode: Interpretiert BMI unter Berücksichtigung von Alter und Geschlecht.
     * Nutzt die überladene interpretiere()-Methode des Models.
     * 
     * @param pGewicht Körpergewicht in kg
     * @param pGroesse Körpergröße in Metern
     * @param pAlter Alter der Person in Jahren
     * @param pGeschlecht Geschlecht ("männlich" oder "weiblich")
     */
    public void interpretiereBMI(double pGewicht, double pGroesse, int pAlter, String pGeschlecht) {
        model.interpretiere(pGewicht, pGroesse, pAlter, pGeschlecht);
    }

    /**
     * Intelligente Interpretationsmethode: Entscheidet automatisch, welche Interpretationsmethode
     * verwendet werden soll, basierend auf den übergebenen Parametern.
     * 
     * @param pGewicht Körpergewicht in kg
     * @param pGroesse Körpergröße in Metern
     * @param pAlter Alter der Person in Jahren (0 = nicht angegeben)
     * @param pGeschlecht Geschlecht ("männlich", "weiblich" oder null/leer = nicht angegeben)
     */
    public void interpretiereIntelligent(double pGewicht, double pGroesse, int pAlter, String pGeschlecht) {
        // Prüfe, ob Alter und Geschlecht gültig sind
        boolean alterVorhanden = pAlter > 0;
        boolean geschlechtVorhanden = pGeschlecht != null && !pGeschlecht.trim().isEmpty();
        
        if (alterVorhanden && geschlechtVorhanden) {
            // Erweiterte Interpretation mit allen Parametern
            model.interpretiere(pGewicht, pGroesse, pAlter, pGeschlecht);
        } else {
            // Einfache Interpretation ohne Alter/Geschlecht
            model.berechne(pGewicht, pGroesse);
            model.interpretiere();
        }
    }

    /**
     * Zeigt die BMI-Interpretation in einem Dialog-Fenster an.
     * Nutzt JOptionPane für eine benutzerfreundliche Darstellung.
     */
    public void zeigeInterpretation() {
        String kategorie = model.getKategorie();
        double bmi = model.getErgebnis();
        
        String nachricht = String.format(
            "Ihr BMI: %.2f\nKategorie: %s",
            bmi, 
            kategorie
        );
        
        JOptionPane.showMessageDialog(
            null,                           // Kein Parent-Frame
            nachricht,                      // Nachricht
            "BMI-Interpretation",           // Titel
            JOptionPane.INFORMATION_MESSAGE // Icon-Typ
        );
    }

    @Override
    public String toString() {
        return "BmiManager{model=" + (model != null ? model.toString() : "null") + "}";
    }
}
