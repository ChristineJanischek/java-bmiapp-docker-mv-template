

package start;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BMI-Rechner Unit-Tests (Version 2) ===\n");

        // Test-Zähler
        int testsPassed = 0;
        int testsFailed = 0;
        
        // ========================================
        // Tests: Einfache Interpretation (ohne Alter/Geschlecht)
        // ========================================
        
        // Test 1: Normalfall - Normalgewicht
        System.out.println("Test 1: Normalgewicht (70kg, 1.75m)");
        Bmirechner test1 = new Bmirechner();
        test1.berechne(70, 1.75);
        test1.interpretiere();
        
        if (test1.getKategorie().equals("Normalgewicht")) {
            System.out.println("  ✅ BESTANDEN - " + test1.toString() + "\n");
            testsPassed++;
        } else {
            System.out.println("  ❌ FEHLGESCHLAGEN - Erwartet: Normalgewicht, Erhalten: " 
                + test1.getKategorie() + " -> " + test1.toString() + "\n");
            testsFailed++;
        }
        
        // Test 2: Grenzwert - Normalgewicht beginnt bei 18.5
        System.out.println("Test 2: Grenzwert Normalgewicht (53.5kg, 1.70m = BMI 18.5)");
        Bmirechner test2 = new Bmirechner();
        test2.berechne(53.5, 1.70);
        test2.interpretiere();
        
        if (test2.getKategorie().equals("Normalgewicht") && 
            Math.abs(test2.getErgebnis() - 18.5) < 0.1) {
            System.out.println("  ✅ BESTANDEN - " + test2.toString() + "\n");
            testsPassed++;
        } else {
            System.out.println("  ❌ FEHLGESCHLAGEN - Erwartet: Normalgewicht (BMI 18.5), Erhalten: " 
                + test2.getKategorie() + " -> " + test2.toString() + "\n");
            testsFailed++;
        }
        
        // Test 3: Grenzwert - Prädipositas beginnt bei 25.0
        System.out.println("Test 3: Grenzwert Prädipositas (72.25kg, 1.70m = BMI 25.0)");
        Bmirechner test3 = new Bmirechner();
        test3.berechne(72.25, 1.70);
        test3.interpretiere();
        
        if (test3.getKategorie().equals("Prädipositas") && 
            Math.abs(test3.getErgebnis() - 25.0) < 0.1) {
            System.out.println("  ✅ BESTANDEN - " + test3.toString() + "\n");
            testsPassed++;
        } else {
            System.out.println("  ❌ FEHLGESCHLAGEN - Erwartet: Prädipositas (BMI 25.0), Erhalten: " 
                + test3.getKategorie() + " -> " + test3.toString() + "\n");
            testsFailed++;
        }
        
        // Test 4: Leichtes Untergewicht
        System.out.println("Test 4: Leichtes Untergewicht (50kg, 1.70m)");
        Bmirechner test4 = new Bmirechner();
        test4.berechne(50, 1.70);
        test4.interpretiere();
        
        if (test4.getKategorie().equals("Leichtes Untergewicht")) {
            System.out.println("  ✅ BESTANDEN - " + test4.toString() + "\n");
            testsPassed++;
        } else {
            System.out.println("  ❌ FEHLGESCHLAGEN - Erwartet: Leichtes Untergewicht, Erhalten: " 
                + test4.getKategorie() + " -> " + test4.toString() + "\n");
            testsFailed++;
        }
        
        // ========================================
        // Tests: Polymorphe Interpretation (mit Alter + Geschlecht)
        // ========================================
        
        System.out.println("--- Polymorphie-Tests (erweiterte Interpretation) ---\n");
        
        // Test 5: Junger Mann (< 25 Jahre)
        System.out.println("Test 5: Polymorphie - 22-jähriger Mann (60kg, 1.70m)");
        Bmirechner test5 = new Bmirechner();
        test5.interpretiere(60, 1.70, 22, "männlich");
        
        if (test5.getKategorie().equals("Normalgewicht")) {
            System.out.println("  ✅ BESTANDEN - " + test5.toString() + "\n");
            testsPassed++;
        } else {
            System.out.println("  ❌ FEHLGESCHLAGEN - Erwartet: Normalgewicht, Erhalten: " 
                + test5.getKategorie() + "\n");
            testsFailed++;
        }
        
        // Test 6: Erwachsener Mann (25-64 Jahre)
        System.out.println("Test 6: Polymorphie - 35-jähriger Mann (73kg, 1.75m)");
        Bmirechner test6 = new Bmirechner();
        test6.interpretiere(73, 1.75, 35, "männlich");
        
        if (test6.getKategorie().equals("Normalgewicht")) {
            System.out.println("  ✅ BESTANDEN - " + test6.toString() + "\n");
            testsPassed++;
        } else {
            System.out.println("  ❌ FEHLGESCHLAGEN - Erwartet: Normalgewicht, Erhalten: " 
                + test6.getKategorie() + "\n");
            testsFailed++;
        }
        
        // Test 7: Erwachsene Frau (25-64 Jahre)
        System.out.println("Test 7: Polymorphie - 35-jährige Frau (65kg, 1.65m)");
        Bmirechner test7 = new Bmirechner();
        test7.interpretiere(65, 1.65, 35, "weiblich");
        
        if (test7.getKategorie().equals("Normalgewicht")) {
            System.out.println("  ✅ BESTANDEN - " + test7.toString() + "\n");
            testsPassed++;
        } else {
            System.out.println("  ❌ FEHLGESCHLAGEN - Erwartet: Normalgewicht, Erhalten: " 
                + test7.getKategorie() + "\n");
            testsFailed++;
        }
        
        // Test 8: Senior (≥ 65 Jahre)
        System.out.println("Test 8: Polymorphie - 70-jähriger Mann (80kg, 1.70m)");
        Bmirechner test8 = new Bmirechner();
        test8.interpretiere(80, 1.70, 70, "männlich");
        
        if (test8.getKategorie().equals("Normalgewicht")) {
            System.out.println("  ✅ BESTANDEN - " + test8.toString() + "\n");
            testsPassed++;
        } else {
            System.out.println("  ❌ FEHLGESCHLAGEN - Erwartet: Normalgewicht, Erhalten: " 
                + test8.getKategorie() + "\n");
            testsFailed++;
        }
        
        // ========================================
        // Zusammenfassung
        // ========================================
        
        System.out.println("==========================================");
        System.out.println("Testergebnisse:");
        System.out.println("  Bestanden: " + testsPassed + " / " + (testsPassed + testsFailed));
        System.out.println("  Fehlgeschlagen: " + testsFailed + " / " + (testsPassed + testsFailed));
        
        if (testsFailed == 0) {
            System.out.println("\n🎉 Alle Tests erfolgreich!");
            System.out.println("✅ Polymorphie funktioniert korrekt!");
        } else {
            System.out.println("\n⚠️ Einige Tests sind fehlgeschlagen. Bitte Code überprüfen.");
        }
        
        System.out.println("\n💡 Hinweis: Für GUI-Tests siehe ./run.sh");
    }
}

