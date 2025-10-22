

package start;

public class Main {
    public static void main(String[] args) {
        // View: Main
        System.out.println("=== BMI-Rechner (MVC mit Polymorphie) ===\n");

        // Controller: BmiManager
        BmiManager manager = new BmiManager();

        // Beispiel-Eingaben (könnten später von der View kommen)
        double gewicht = 75.0;
        double groesse = 1.75;

        // Test 1: Einfache Interpretation (ohne Alter/Geschlecht)
        System.out.println("--- Test 1: Einfache Interpretation ---");
        manager.berechneBMI(gewicht, groesse);
        manager.interpretiereBMI();
        
        Bmirechner rechner = manager.getModel();
        System.out.println("BMI: " + rechner.getErgebnis());
        System.out.println("Kategorie: " + rechner.getKategorie());
        
        // Test 2: Erweiterte Interpretation (mit Alter/Geschlecht) - Polymorphie!
        System.out.println("\n--- Test 2: Erweiterte Interpretation (Polymorphie) ---");
        
        // Junger Mann, 22 Jahre
        manager.interpretiereBMI(75.0, 1.75, 22, "männlich");
        System.out.println("22-jähriger Mann - BMI: " + rechner.getErgebnis() + ", Kategorie: " + rechner.getKategorie());
        
        // Erwachsene Frau, 35 Jahre
        manager.interpretiereBMI(65.0, 1.65, 35, "weiblich");
        System.out.println("35-jährige Frau - BMI: " + rechner.getErgebnis() + ", Kategorie: " + rechner.getKategorie());
        
        // Senior, 70 Jahre
        manager.interpretiereBMI(80.0, 1.70, 70, "männlich");
        System.out.println("70-jähriger Mann - BMI: " + rechner.getErgebnis() + ", Kategorie: " + rechner.getKategorie());
        
        // Test 3: JOptionPane-Dialog (kommentiert aus, da in Terminal nicht sichtbar)
        // manager.zeigeInterpretation();
        
        System.out.println("\n✅ Polymorphie erfolgreich demonstriert!");
        System.out.println("Die Methode interpretiere() existiert in zwei Varianten:");
        System.out.println("  1. interpretiere() - ohne Parameter");
        System.out.println("  2. interpretiere(gewicht, groesse, alter, geschlecht) - mit 4 Parametern");
    }
}

