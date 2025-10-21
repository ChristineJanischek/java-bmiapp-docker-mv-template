
package start;

public class Main {
    public static void main(String[] args) {
        // View: Main
        System.out.println("=== BMI-Rechner (MVC) ===\n");

        // Controller: BmiManager
        BmiManager manager = new BmiManager();

        // Beispiel-Eingaben (könnten später von der View kommen)
        double gewicht = 150.0;
        double groesse = 1.80;

        // Model-Steuerung über Controller
        manager.berechneBMI(gewicht, groesse);
        manager.interpretiereBMI();

        // Ausgabe (View)
        Bmirechner rechner = manager.getModel();
        System.out.println("BMI: " + rechner.getErgebnis());
        System.out.println("Kategorie: " + rechner.getKategorie());
    }
}

