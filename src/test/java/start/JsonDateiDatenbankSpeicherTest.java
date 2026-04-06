package start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JSON Dateispeicher Tests")
class JsonDateiDatenbankSpeicherTest {

    @Test
    @DisplayName("Speichern und Laden erhaelt Beziehungen und Statistik")
    void testSpeichernUndLadenRoundtrip() throws Exception {
        BmiManager manager = new BmiManager();
        Person p = manager.erstellePerson("Mia", "Muster", 16, "Frau", "mia@example.de");
        manager.setAktuellePerson(p);

        Messung m1 = new Messung(1, p.getId(), 62.0, 1.68, LocalDateTime.of(2026, 4, 5, 10, 15));
        Messung m2 = new Messung(2, p.getId(), 60.0, 1.68, LocalDateTime.of(2026, 4, 6, 10, 15));
        p.addMessung(m1);
        p.addMessung(m2);

        double durchschnittVorher = p.getDurchschnittsBmi();
        double differenzVorher = p.getBmiDifferenz();

        Path tempDir = Files.createTempDirectory("bmiapp-json-test-");
        manager.speichereDaten(tempDir);

        BmiManager geladenerManager = new BmiManager();
        geladenerManager.ladeDaten(tempDir);

        assertEquals(1, geladenerManager.getAnzahlPersonen(), "Es sollte genau 1 Person geladen werden");
        Person geladen = geladenerManager.getAllePersonen().get(0);

        assertEquals("Mia", geladen.getVorname());
        assertEquals("Muster", geladen.getNachname());
        assertEquals(2, geladen.getAnzahlMessungen(), "Es sollten 2 Messungen geladen werden");

        assertEquals(durchschnittVorher, geladen.getDurchschnittsBmi(), 0.00001,
            "Durchschnitts-BMI muss nach Laden identisch sein");
        assertEquals(differenzVorher, geladen.getBmiDifferenz(), 0.00001,
            "BMI-Differenz muss nach Laden identisch sein");
    }

    @Test
    @DisplayName("Schema und Daten werden getrennt gespeichert")
    void testSchemaUndDatenSindGetrennt() throws Exception {
        BmiManager manager = new BmiManager();
        Person p = manager.erstellePerson("Noah", "Beispiel", 17, "Mann", "noah@example.de");
        manager.setAktuellePerson(p);
        manager.erstelleMessung(70.0, 1.75);

        Path tempDir = Files.createTempDirectory("bmiapp-json-files-");
        manager.speichereDaten(tempDir);

        Path schemaPfad = tempDir.resolve("bmiapp.schema.json");
        Path datenPfad = tempDir.resolve("bmiapp.data.json");

        assertTrue(Files.exists(schemaPfad), "Schema-Datei muss existieren");
        assertTrue(Files.exists(datenPfad), "Daten-Datei muss existieren");

        String schema = Files.readString(schemaPfad, StandardCharsets.UTF_8);
        String daten = Files.readString(datenPfad, StandardCharsets.UTF_8);

        assertTrue(schema.contains("\"entities\""), "Schema muss Entitaeten enthalten");
        assertTrue(schema.contains("\"personen\""), "Schema muss personen enthalten");
        assertTrue(schema.contains("\"messungen\""), "Schema muss messungen enthalten");

        assertTrue(daten.contains("\"personen\""), "Daten muessen personen enthalten");
        assertTrue(daten.contains("\"messungen\""), "Daten muessen messungen enthalten");
        assertTrue(daten.contains("\"personId\""), "Messungen muessen per personId verknuepft sein");
    }
}
