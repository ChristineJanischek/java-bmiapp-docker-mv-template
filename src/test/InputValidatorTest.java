package test;

import start.InputValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * InputValidatorTest: Unit-Tests für die Whitelist-Validierung.
 * 
 * Testet alle Validierungsmethoden mit beiden Positive (Valid) und
 * Negative (Invalid) Testfällen.
 * 
 * @author BmiApp-Team
 * @version 6.0
 */
public class InputValidatorTest {
    
    // ===== Tests für Namen-Validierung =====
    
    @Test
    public void testValidNameWithNormalNames() {
        // Positive Tests
        assertTrue(InputValidator.isValidName("Max"));
        assertTrue(InputValidator.isValidName("Maria"));
        assertTrue(InputValidator.isValidName("Max Müller"));
        assertTrue(InputValidator.isValidName("Anna-Maria"));
        assertTrue(InputValidator.isValidName("Müller"));
        assertTrue(InputValidator.isValidName("Schmidt-König"));
    }
    
    @Test
    public void testInvalidNameWithNumbers() {
        // Negative Tests: Zahlen nicht erlaubt
        assertFalse(InputValidator.isValidName("Max123"));
        assertFalse(InputValidator.isValidName("123Max"));
        assertFalse(InputValidator.isValidName("Max 2024"));
    }
    
    @Test
    public void testInvalidNameWithSpecialChars() {
        // Negative Tests: Sonderzeichen nicht erlaubt
        assertFalse(InputValidator.isValidName("Max@Mueller"));
        assertFalse(InputValidator.isValidName("Anna#Maria"));
        assertFalse(InputValidator.isValidName("Max$"));
        assertFalse(InputValidator.isValidName("Max_Mueller"));
        assertFalse(InputValidator.isValidName("Max.Mueller"));
    }
    
    @Test
    public void testInvalidNameEmptyOrNull() {
        // Negative Tests: Empty/Null
        assertFalse(InputValidator.isValidName(""));
        assertFalse(InputValidator.isValidName("   "));
        assertFalse(InputValidator.isValidName(null));
    }
    
    @Test
    public void testInvalidNameTooLong() {
        // Negative Tests: Länge > 50 Zeichen
        String tooLongName = "a".repeat(51);
        assertFalse(InputValidator.isValidName(tooLongName));
        
        String exactlyLong = "a".repeat(50);
        assertTrue(InputValidator.isValidName(exactlyLong));
    }
    
    // ===== Tests für Alter-Validierung =====
    
    @Test
    public void testValidAge() {
        // Positive Tests
        assertTrue(InputValidator.isValidAge(18));
        assertTrue(InputValidator.isValidAge(50));
        assertTrue(InputValidator.isValidAge(120));
        assertTrue(InputValidator.isValidAge(25));
    }
    
    @Test
    public void testInvalidAgeTooYoung() {
        // Negative Tests: Unter 18
        assertFalse(InputValidator.isValidAge(17));
        assertFalse(InputValidator.isValidAge(0));
        assertFalse(InputValidator.isValidAge(-1));
    }
    
    @Test
    public void testInvalidAgeTooOld() {
        // Negative Tests: Über 120
        assertFalse(InputValidator.isValidAge(121));
        assertFalse(InputValidator.isValidAge(200));
    }
    
    // ===== Tests für Körpergröße-Validierung =====
    
    @Test
    public void testValidHeight() {
        // Positive Tests
        assertTrue(InputValidator.isValidHeight(100));
        assertTrue(InputValidator.isValidHeight(170.5));
        assertTrue(InputValidator.isValidHeight(200));
        assertTrue(InputValidator.isValidHeight(250));
    }
    
    @Test
    public void testInvalidHeightTooSmall() {
        // Negative Tests: Unter 100 cm
        assertFalse(InputValidator.isValidHeight(99.9));
        assertFalse(InputValidator.isValidHeight(50));
    }
    
    @Test
    public void testInvalidHeightTooLarge() {
        // Negative Tests: Über 250 cm
        assertFalse(InputValidator.isValidHeight(250.1));
        assertFalse(InputValidator.isValidHeight(300));
    }
    
    // ===== Tests für Gewicht-Validierung =====
    
    @Test
    public void testValidWeight() {
        // Positive Tests
        assertTrue(InputValidator.isValidWeight(30));
        assertTrue(InputValidator.isValidWeight(75.5));
        assertTrue(InputValidator.isValidWeight(150));
        assertTrue(InputValidator.isValidWeight(300));
    }
    
    @Test
    public void testInvalidWeightTooSmall() {
        // Negative Tests: Unter 30 kg
        assertFalse(InputValidator.isValidWeight(29.9));
        assertFalse(InputValidator.isValidWeight(0));
    }
    
    @Test
    public void testInvalidWeightTooLarge() {
        // Negative Tests: Über 300 kg
        assertFalse(InputValidator.isValidWeight(300.1));
        assertFalse(InputValidator.isValidWeight(500));
    }
}
