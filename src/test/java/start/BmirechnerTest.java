package start;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 Test-Klasse für die Bmirechner-Klasse.
 * Testet die BMI-Berechnungs- und Interpretationslogik.
 */
@DisplayName("BMI Rechner Tests")
class BmirechnerTest {

    private Bmirechner rechner;

    @BeforeEach
    void setUp() {
        rechner = new Bmirechner();
    }

    @Test
    @DisplayName("BMI-Berechnung für Normalgewicht (70kg, 1.75m)")
    void testBerechneNormalgewicht() {
        double bmi = rechner.berechne(70.0, 1.75);
        assertEquals(22.86, bmi, 0.01, "BMI sollte ca. 22.86 sein");
        assertEquals(70.0, rechner.getGewicht(), "Gewicht sollte gesetzt sein");
        assertEquals(1.75, rechner.getGroesse(), "Größe sollte gesetzt sein");
    }

    @Test
    @DisplayName("BMI-Berechnung für Untergewicht (50kg, 1.70m)")
    void testBerechneUntergewicht() {
        double bmi = rechner.berechne(50.0, 1.70);
        assertEquals(17.30, bmi, 0.01, "BMI sollte ca. 17.30 sein");
    }

    @Test
    @DisplayName("BMI-Berechnung für Übergewicht (90kg, 1.75m)")
    void testBerechneUebergewicht() {
        double bmi = rechner.berechne(90.0, 1.75);
        assertEquals(29.39, bmi, 0.01, "BMI sollte ca. 29.39 sein");
    }

    @Test
    @DisplayName("BMI-Berechnung mit Größe 0 sollte 0 ergeben")
    void testBerechneUngueltigeGroesse() {
        double bmi = rechner.berechne(70.0, 0.0);
        assertEquals(0.0, bmi, "BMI sollte 0 sein bei ungültiger Größe");
    }

    @Test
    @DisplayName("Interpretation: Normalgewicht (BMI 22.86)")
    void testInterpretiereNormalgewicht() {
        rechner.berechne(70.0, 1.75);
        rechner.interpretiere();
        assertEquals("Normalgewicht", rechner.getKategorie(), 
            "Kategorie sollte Normalgewicht sein");
    }

    @Test
    @DisplayName("Interpretation: Leichtes Untergewicht (BMI 17.30)")
    void testInterpretiereLeichtesUntergewicht() {
        rechner.berechne(50.0, 1.70);
        rechner.interpretiere();
        assertEquals("Leichtes Untergewicht", rechner.getKategorie(),
            "Kategorie sollte Leichtes Untergewicht sein");
    }

    @Test
    @DisplayName("Interpretation: Prädipositas (BMI 29.39)")
    void testInterpretierePraedipositas() {
        rechner.berechne(90.0, 1.75);
        rechner.interpretiere();
        assertEquals("Prädipositas", rechner.getKategorie(),
            "Kategorie sollte Prädipositas sein");
    }

    @Test
    @DisplayName("Interpretation: Adipositas Grad III (BMI 40+)")
    void testInterpretiereAdipositas3() {
        rechner.berechne(130.0, 1.75);
        rechner.interpretiere();
        assertEquals("Adipositas Grad III", rechner.getKategorie(),
            "Kategorie sollte Adipositas Grad III sein");
    }

    @Test
    @DisplayName("Intelligente Interpretation: Junge Erwachsene < 25 Jahre")
    void testInterpretiereIntelligentJung() {
        rechner.interpretiere(70.0, 1.75, 22, "männlich");
        assertEquals(22.86, rechner.getErgebnis(), 0.01, "BMI sollte berechnet sein");
        assertEquals("Normalgewicht", rechner.getKategorie(),
            "Kategorie sollte Normalgewicht sein für junge Erwachsene");
    }

    @Test
    @DisplayName("Intelligente Interpretation: Senioren ≥ 65 Jahre")
    void testInterpretiereIntelligentSenior() {
        rechner.interpretiere(75.0, 1.70, 70, "männlich");
        double bmi = rechner.getErgebnis();
        assertTrue(bmi > 25 && bmi < 28, "BMI sollte im Senior-Normalbereich liegen");
        assertEquals("Normalgewicht", rechner.getKategorie(),
            "Kategorie sollte Normalgewicht sein für Senioren");
    }

    @Test
    @DisplayName("Intelligente Interpretation: Erwachsene männlich (25-64)")
    void testInterpretiereIntelligentErwachsenMaennlich() {
        rechner.interpretiere(85.0, 1.80, 40, "männlich");
        double bmi = rechner.getErgebnis();
        assertTrue(bmi >= 25, "BMI sollte ≥ 25 sein");
        assertEquals("Übergewicht", rechner.getKategorie(),
            "Kategorie sollte Übergewicht sein für erwachsene Männer mit BMI ≥ 25");
    }

    @Test
    @DisplayName("Intelligente Interpretation: Erwachsene weiblich (25-64)")
    void testInterpretiereIntelligentErwachsenWeiblich() {
        rechner.interpretiere(60.0, 1.65, 35, "weiblich");
        double bmi = rechner.getErgebnis();
        assertTrue(bmi >= 19 && bmi < 24, "BMI sollte im weiblichen Normalbereich liegen");
        assertEquals("Normalgewicht", rechner.getKategorie(),
            "Kategorie sollte Normalgewicht sein für erwachsene Frauen");
    }

    @Test
    @DisplayName("toString() gibt formatierte Ausgabe zurück")
    void testToString() {
        rechner.berechne(70.0, 1.75);
        rechner.interpretiere();
        String output = rechner.toString();
        assertTrue(output.contains("BMI:"), "Ausgabe sollte 'BMI:' enthalten");
        assertTrue(output.contains("Kategorie:"), "Ausgabe sollte 'Kategorie:' enthalten");
        assertTrue(output.contains("Normalgewicht"), "Ausgabe sollte Kategorie enthalten");
    }

    @Test
    @DisplayName("Getter und Setter funktionieren korrekt")
    void testGetterSetter() {
        rechner.setGewicht(80.0);
        rechner.setGroesse(1.80);
        rechner.setErgebnis(24.69);
        rechner.setKategorie("Normalgewicht");

        assertEquals(80.0, rechner.getGewicht(), "Gewicht-Getter sollte gesetzten Wert zurückgeben");
        assertEquals(1.80, rechner.getGroesse(), "Größe-Getter sollte gesetzten Wert zurückgeben");
        assertEquals(24.69, rechner.getErgebnis(), 0.01, "Ergebnis-Getter sollte gesetzten Wert zurückgeben");
        assertEquals("Normalgewicht", rechner.getKategorie(), "Kategorie-Getter sollte gesetzten Wert zurückgeben");
    }

    @Test
    @DisplayName("Alte berechnen()-Methode funktioniert noch")
    void testAlteBerechnenMethode() {
        rechner.setGewicht(70.0);
        rechner.setGroesse(1.75);
        rechner.berechnen();
        assertEquals(22.86, rechner.getErgebnis(), 0.01, 
            "Alte berechnen()-Methode sollte BMI korrekt berechnen");
    }
}
