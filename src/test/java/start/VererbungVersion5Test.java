package start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Version 5 Vererbung Tests")
class VererbungVersion5Test {

    @Test
    @DisplayName("Person und Messung erben von BaseEntity")
    void testKlassenHierarchie() {
        Person person = new Person(10, "Lena", "Beispiel", 18, "Frau", "lena@example.de");
        Messung messung = new Messung(11, 10, 65.0, 1.72);

        assertInstanceOf(BaseEntity.class, person);
        assertInstanceOf(BaseEntity.class, messung);
        assertEquals(10, person.getId());
        assertEquals(11, messung.getId());
    }

    @Test
    @DisplayName("Negative IDs werden zentral abgefangen")
    void testIdValidierungInBasisklasse() {
        IllegalArgumentException personException = assertThrows(
            IllegalArgumentException.class,
            () -> new Person(-1, "Max", "Test", 20, "Mann", "max@test.de")
        );

        IllegalArgumentException messungException = assertThrows(
            IllegalArgumentException.class,
            () -> new Messung(-2, 1, 70.0, 1.80)
        );

        assertEquals("ID darf nicht negativ sein!", personException.getMessage());
        assertEquals("ID darf nicht negativ sein!", messungException.getMessage());
    }
}
