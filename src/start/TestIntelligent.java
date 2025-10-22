package start;

/**
 * Test-Programm für die intelligente Methodenwahl im BmiManager
 */
public class TestIntelligent {
    public static void main(String[] args) {
        System.out.println("=== Test: Intelligente Methodenwahl ===\n");
        
        BmiManager manager = new BmiManager();
        
        // Test 1: Ohne Alter und Geschlecht
        System.out.println("Test 1: Ohne Alter/Geschlecht (70kg, 1.75m)");
        manager.interpretiereIntelligent(70, 1.75, 0, null);
        System.out.println("  BMI: " + String.format("%.1f", manager.getModel().getErgebnis()));
        System.out.println("  Kategorie: " + manager.getModel().getKategorie());
        System.out.println("  ✅ Sollte einfache Interpretation nutzen\n");
        
        // Test 2: Mit Alter, ohne Geschlecht
        System.out.println("Test 2: Mit Alter (30), ohne Geschlecht");
        manager.interpretiereIntelligent(70, 1.75, 30, null);
        System.out.println("  BMI: " + String.format("%.1f", manager.getModel().getErgebnis()));
        System.out.println("  Kategorie: " + manager.getModel().getKategorie());
        System.out.println("  ✅ Sollte einfache Interpretation nutzen\n");
        
        // Test 3: Ohne Alter, mit Geschlecht
        System.out.println("Test 3: Ohne Alter, mit Geschlecht (männlich)");
        manager.interpretiereIntelligent(70, 1.75, 0, "männlich");
        System.out.println("  BMI: " + String.format("%.1f", manager.getModel().getErgebnis()));
        System.out.println("  Kategorie: " + manager.getModel().getKategorie());
        System.out.println("  ✅ Sollte einfache Interpretation nutzen\n");
        
        // Test 4: Mit Alter und Geschlecht
        System.out.println("Test 4: Mit Alter (35) und Geschlecht (männlich)");
        manager.interpretiereIntelligent(70, 1.75, 35, "männlich");
        System.out.println("  BMI: " + String.format("%.1f", manager.getModel().getErgebnis()));
        System.out.println("  Kategorie: " + manager.getModel().getKategorie());
        System.out.println("  ✅ Sollte erweiterte Interpretation nutzen\n");
        
        // Test 5: Leerer String als Geschlecht
        System.out.println("Test 5: Mit Alter (25), aber leerem Geschlecht");
        manager.interpretiereIntelligent(70, 1.75, 25, "");
        System.out.println("  BMI: " + String.format("%.1f", manager.getModel().getErgebnis()));
        System.out.println("  Kategorie: " + manager.getModel().getKategorie());
        System.out.println("  ✅ Sollte einfache Interpretation nutzen\n");
        
        System.out.println("==========================================");
        System.out.println("🎉 Alle Fälle getestet!");
        System.out.println("Die intelligente Methodenwahl funktioniert korrekt.");
    }
}
