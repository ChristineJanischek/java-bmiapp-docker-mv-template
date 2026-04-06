package start;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Persistenzklasse fuer Version 5.
 * Trennt Struktur (Schema) und Nutzdaten in getrennte JSON-Dateien.
 */
public class JsonDateiDatenbankSpeicher {

    private static final String SCHEMA_DATEI = "bmiapp.schema.json";
    private static final String DATEN_DATEI = "bmiapp.data.json";

    public void speichern(List<Person> personen, Path ordnerPfad) throws IOException {
        if (personen == null) {
            throw new IllegalArgumentException("Personenliste darf nicht null sein");
        }
        if (ordnerPfad == null) {
            throw new IllegalArgumentException("Ordnerpfad darf nicht null sein");
        }

        Files.createDirectories(ordnerPfad);

        Path schemaPfad = ordnerPfad.resolve(SCHEMA_DATEI);
        Path datenPfad = ordnerPfad.resolve(DATEN_DATEI);

        atomarSchreiben(schemaPfad, erzeugeSchemaJson());
        atomarSchreiben(datenPfad, erzeugeDatenJson(personen));
    }

    public List<Person> laden(Path ordnerPfad) throws IOException {
        if (ordnerPfad == null) {
            throw new IllegalArgumentException("Ordnerpfad darf nicht null sein");
        }

        Path schemaPfad = ordnerPfad.resolve(SCHEMA_DATEI);
        Path datenPfad = ordnerPfad.resolve(DATEN_DATEI);

        if (!Files.exists(schemaPfad) || !Files.exists(datenPfad)) {
            throw new IOException("Schema- oder Datendatei fehlen im Ordner: " + ordnerPfad);
        }

        String schema = Files.readString(schemaPfad, StandardCharsets.UTF_8);
        String daten = Files.readString(datenPfad, StandardCharsets.UTF_8);

        pruefeSchemaMinimal(schema);
        pruefeDatenMinimal(daten);

        return parseDaten(daten);
    }

    private String erzeugeSchemaJson() {
        return """
            {
              "database": "bmiapp",
              "version": 5,
              "entities": [
                {
                  "name": "personen",
                  "primaryKey": "id",
                  "fields": [
                    {"name":"id","type":"int","required":true},
                    {"name":"vorname","type":"string","required":true},
                    {"name":"nachname","type":"string","required":true},
                    {"name":"alter","type":"int","required":true},
                    {"name":"geschlecht","type":"string","required":true},
                    {"name":"email","type":"string","required":true}
                  ]
                },
                {
                  "name": "messungen",
                  "primaryKey": "id",
                  "foreignKey": "personId",
                  "fields": [
                    {"name":"id","type":"int","required":true},
                    {"name":"personId","type":"int","required":true},
                    {"name":"gewicht","type":"double","required":true},
                    {"name":"groesse","type":"double","required":true},
                    {"name":"zeitstempel","type":"string","required":true}
                  ]
                }
              ]
            }
            """;
    }

    private String erzeugeDatenJson(List<Person> personen) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");

        json.append("  \"personen\": [\n");
        for (int i = 0; i < personen.size(); i++) {
            Person p = personen.get(i);
            json.append("    {")
                .append("\"id\": ").append(p.getId()).append(", ")
                .append("\"vorname\": \"").append(escapeJson(p.getVorname())).append("\", ")
                .append("\"nachname\": \"").append(escapeJson(p.getNachname())).append("\", ")
                .append("\"alter\": ").append(p.getAlter()).append(", ")
                .append("\"geschlecht\": \"").append(escapeJson(p.getGeschlecht())).append("\", ")
                .append("\"email\": \"").append(escapeJson(p.getEmail())).append("\"")
                .append("}");
            if (i < personen.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ],\n");

        json.append("  \"messungen\": [\n");
        List<Messung> alleMessungen = new ArrayList<>();
        for (Person p : personen) {
            alleMessungen.addAll(p.getMessungen());
        }

        for (int i = 0; i < alleMessungen.size(); i++) {
            Messung m = alleMessungen.get(i);
            json.append("    {")
                .append("\"id\": ").append(m.getId()).append(", ")
                .append("\"personId\": ").append(m.getPersonId()).append(", ")
                .append("\"gewicht\": ").append(m.getGewicht()).append(", ")
                .append("\"groesse\": ").append(m.getGroesse()).append(", ")
                .append("\"zeitstempel\": \"").append(m.getZeitstempel()).append("\"")
                .append("}");
            if (i < alleMessungen.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ]\n");

        json.append("}\n");
        return json.toString();
    }

    private List<Person> parseDaten(String daten) throws IOException {
        String personenArray = extrahiereArrayInhalt(daten, "personen");
        String messungenArray = extrahiereArrayInhalt(daten, "messungen");

        List<String> personenObjekte = splitJsonObjekte(personenArray);
        List<String> messungsObjekte = splitJsonObjekte(messungenArray);

        List<Person> personen = new ArrayList<>();
        Map<Integer, Person> personenNachId = new HashMap<>();

        for (String pObj : personenObjekte) {
            if (pObj.isBlank()) {
                continue;
            }
            Map<String, String> werte = parseObjektFelder(pObj);
            int id = parseInt(werte, "id");
            Person p = new Person(
                id,
                parseString(werte, "vorname"),
                parseString(werte, "nachname"),
                parseInt(werte, "alter"),
                parseString(werte, "geschlecht"),
                parseString(werte, "email")
            );
            personen.add(p);
            personenNachId.put(id, p);
        }

        for (String mObj : messungsObjekte) {
            if (mObj.isBlank()) {
                continue;
            }
            Map<String, String> werte = parseObjektFelder(mObj);
            int personId = parseInt(werte, "personId");
            Person person = personenNachId.get(personId);
            if (person == null) {
                throw new IOException("Messung verweist auf unbekannte personId: " + personId);
            }

            int id = parseInt(werte, "id");
            double gewicht = parseDouble(werte, "gewicht");
            double groesse = parseDouble(werte, "groesse");
            LocalDateTime zeitstempel = LocalDateTime.parse(parseString(werte, "zeitstempel"));

            Messung m = new Messung(id, personId, gewicht, groesse, zeitstempel);
            person.addMessung(m);
        }

        return personen;
    }

    private String extrahiereArrayInhalt(String json, String arrayName) throws IOException {
        String marker = "\"" + arrayName + "\"";
        int markerIndex = json.indexOf(marker);
        if (markerIndex < 0) {
            throw new IOException("Array fehlt: " + arrayName);
        }
        int start = json.indexOf('[', markerIndex);
        if (start < 0) {
            throw new IOException("Ungueltiges JSON: '[' fehlt fuer " + arrayName);
        }

        int tiefe = 0;
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') {
                tiefe++;
            } else if (c == ']') {
                tiefe--;
                if (tiefe == 0) {
                    return json.substring(start + 1, i);
                }
            }
        }
        throw new IOException("Ungueltiges JSON: ']' fehlt fuer " + arrayName);
    }

    private List<String> splitJsonObjekte(String arrayInhalt) throws IOException {
        List<String> objekte = new ArrayList<>();
        int objektStart = -1;
        int tiefe = 0;

        for (int i = 0; i < arrayInhalt.length(); i++) {
            char c = arrayInhalt.charAt(i);
            if (c == '{') {
                if (tiefe == 0) {
                    objektStart = i;
                }
                tiefe++;
            } else if (c == '}') {
                tiefe--;
                if (tiefe < 0) {
                    throw new IOException("Ungueltiges JSON: zu viele '}'");
                }
                if (tiefe == 0 && objektStart >= 0) {
                    objekte.add(arrayInhalt.substring(objektStart, i + 1));
                    objektStart = -1;
                }
            }
        }

        if (tiefe != 0) {
            throw new IOException("Ungueltiges JSON: unausgeglichene Objekte");
        }

        return objekte;
    }

    private Map<String, String> parseObjektFelder(String objektJson) throws IOException {
        Map<String, String> werte = new HashMap<>();
        String inhalt = objektJson.trim();
        if (!inhalt.startsWith("{") || !inhalt.endsWith("}")) {
            throw new IOException("Ungueltiges Objekt-JSON: " + objektJson);
        }

        inhalt = inhalt.substring(1, inhalt.length() - 1).trim();
        if (inhalt.isEmpty()) {
            return werte;
        }

        List<String> paare = splitTopLevel(inhalt);
        for (String paar : paare) {
            int doppelpunktIndex = indexOfTopLevelColon(paar);
            if (doppelpunktIndex < 0) {
                throw new IOException("Ungueltiges Schluessel-Wert-Paar: " + paar);
            }
            String keyRaw = paar.substring(0, doppelpunktIndex).trim();
            String valueRaw = paar.substring(doppelpunktIndex + 1).trim();

            String key = unquote(keyRaw);
            String value = valueRaw;
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = unescapeJson(unquote(value));
            }
            werte.put(key, value);
        }

        return werte;
    }

    private List<String> splitTopLevel(String text) {
        List<String> teile = new ArrayList<>();
        int start = 0;
        boolean inString = false;
        boolean escaped = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (c == '"') {
                inString = !inString;
                continue;
            }
            if (c == ',' && !inString) {
                teile.add(text.substring(start, i).trim());
                start = i + 1;
            }
        }

        teile.add(text.substring(start).trim());
        return teile;
    }

    private int indexOfTopLevelColon(String text) {
        boolean inString = false;
        boolean escaped = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (escaped) {
                escaped = false;
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (c == '"') {
                inString = !inString;
                continue;
            }
            if (c == ':' && !inString) {
                return i;
            }
        }

        return -1;
    }

    private String unquote(String text) throws IOException {
        String trimmed = text.trim();
        if (!trimmed.startsWith("\"") || !trimmed.endsWith("\"")) {
            throw new IOException("Erwarteter JSON-String, erhalten: " + text);
        }
        return trimmed.substring(1, trimmed.length() - 1);
    }

    private int parseInt(Map<String, String> werte, String key) throws IOException {
        String wert = werte.get(key);
        if (wert == null) {
            throw new IOException("Pflichtfeld fehlt: " + key);
        }
        try {
            return Integer.parseInt(wert);
        } catch (NumberFormatException e) {
            throw new IOException("Ungueltige int-Zahl in Feld " + key + ": " + wert, e);
        }
    }

    private double parseDouble(Map<String, String> werte, String key) throws IOException {
        String wert = werte.get(key);
        if (wert == null) {
            throw new IOException("Pflichtfeld fehlt: " + key);
        }
        try {
            return Double.parseDouble(wert);
        } catch (NumberFormatException e) {
            throw new IOException("Ungueltige double-Zahl in Feld " + key + ": " + wert, e);
        }
    }

    private String parseString(Map<String, String> werte, String key) throws IOException {
        String wert = werte.get(key);
        if (wert == null) {
            throw new IOException("Pflichtfeld fehlt: " + key);
        }
        return wert;
    }

    private void pruefeSchemaMinimal(String schema) throws IOException {
        if (!schema.contains("\"database\"") || !schema.contains("\"entities\"")) {
            throw new IOException("Schema-Datei ungueltig: database/entities fehlen");
        }
        if (!schema.contains("\"personen\"") || !schema.contains("\"messungen\"")) {
            throw new IOException("Schema-Datei ungueltig: Entitaeten personen/messungen fehlen");
        }
        if (!schema.contains("\"personId\"")) {
            throw new IOException("Schema-Datei ungueltig: foreign key personId fehlt");
        }
    }

    private void pruefeDatenMinimal(String daten) throws IOException {
        if (!daten.contains("\"personen\"")) {
            throw new IOException("Daten-Datei ungueltig: Container 'personen' fehlt");
        }
        if (!daten.contains("\"messungen\"")) {
            throw new IOException("Daten-Datei ungueltig: Container 'messungen' fehlt");
        }
        if (!daten.contains("\"id\"")) {
            throw new IOException("Daten-Datei ungueltig: Pflichtfeld 'id' fehlt");
        }
    }

    private void atomarSchreiben(Path zielDatei, String inhalt) throws IOException {
        Path tempDatei = Path.of(zielDatei.toString() + ".tmp");
        Files.writeString(tempDatei, inhalt, StandardCharsets.UTF_8);
        Files.move(tempDatei, zielDatei,
            StandardCopyOption.REPLACE_EXISTING,
            StandardCopyOption.ATOMIC_MOVE);
    }

    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    private String unescapeJson(String text) {
        StringBuilder result = new StringBuilder();
        boolean escaped = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!escaped) {
                if (c == '\\') {
                    escaped = true;
                } else {
                    result.append(c);
                }
            } else {
                switch (c) {
                    case 'n' -> result.append('\n');
                    case 'r' -> result.append('\r');
                    case 't' -> result.append('\t');
                    case '"' -> result.append('"');
                    case '\\' -> result.append('\\');
                    default -> result.append(c);
                }
                escaped = false;
            }
        }

        if (escaped) {
            result.append('\\');
        }

        return result.toString();
    }
}
