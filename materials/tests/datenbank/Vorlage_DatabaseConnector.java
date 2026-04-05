package start;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * =====================================================================
 * VORLAGE: DatabaseConnector – Best Practice für Java-MySQL-Verbindung
 * =====================================================================
 *
 * ANLEITUNG FÜR SCHÜLER:
 *   1. Kopiere diese Klasse in dein Projekt.
 *   2. Passe DATENBANKNAME, BENUTZER und PASSWORT in deiner eigenen
 *      Klasse an (NICHT hier direkt hardcoden!).
 *   3. Ersetze "Person" und "Messung" durch deine eigenen Klassen.
 *   4. Erweitere die CRUD-Methoden für deine Tabellen.
 *
 * SICHERHEITSHINWEISE:
 *   - Passwörter NIEMALS im Quellcode speichern.
 *   - IMMER PreparedStatement verwenden (kein Statement + String!).
 *   - Verbindungen IMMER mit try-with-resources schließen.
 *
 * VORAUSSETZUNGEN (pom.xml):
 *   <dependency>
 *       <groupId>com.mysql</groupId>
 *       <artifactId>mysql-connector-j</artifactId>
 *       <version>8.3.0</version>
 *   </dependency>
 * =====================================================================
 */
public class DatabaseConnector {

    // ─── Konfiguration ────────────────────────────────────────────────
    // TODO: Passe diese Werte für dein Projekt an!
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "bmi_datenbank";  // ← anpassen

    // Verbindungs-URL (wird aus den Teilen zusammengebaut)
    private static final String URL =
        "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
        + "?useSSL=false&serverTimezone=Europe/Berlin&allowPublicKeyRetrieval=true";

    // Zugangsdaten – in Produktionssystemen aus Konfigurationsdatei lesen!
    private final String benutzer;
    private final String passwort;

    // Logger statt System.out.println (professionelles Logging)
    private static final Logger LOG = Logger.getLogger(DatabaseConnector.class.getName());

    // ─── Konstruktor ──────────────────────────────────────────────────

    /**
     * Erstellt einen neuen DatabaseConnector.
     *
     * @param benutzer   MySQL-Benutzername (z.B. "root")
     * @param passwort   MySQL-Passwort
     */
    public DatabaseConnector(String benutzer, String passwort) {
        this.benutzer = benutzer;
        this.passwort = passwort;
    }

    // ─── Verbindung herstellen ────────────────────────────────────────

    /**
     * Stellt eine Verbindung zur Datenbank her.
     * MUSS nach Benutzung geschlossen werden – am besten mit try-with-resources!
     *
     * Verwendung:
     *   try (Connection conn = connector.verbinden()) {
     *       // ... SQL-Operationen ...
     *   }
     *
     * @return Connection-Objekt, oder null bei Fehler
     * @throws SQLException bei Verbindungsfehler
     */
    public Connection verbinden() throws SQLException {
        return DriverManager.getConnection(URL, benutzer, passwort);
    }

    /**
     * Prüft, ob die Datenbankverbindung funktioniert.
     *
     * @return true wenn erfolgreich, false bei Fehler
     */
    public boolean verbindungTesten() {
        try (Connection conn = verbinden()) {
            LOG.info("Datenbankverbindung erfolgreich: " + URL);
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "Verbindung fehlgeschlagen: " + e.getMessage(), e);
            return false;
        }
    }

    // ─── CREATE: Datensatz einfügen ───────────────────────────────────

    /**
     * Fügt eine neue Person in die Datenbank ein.
     *
     * TODO: Passe die Tabellen- und Spaltennamen für dein Projekt an!
     *
     * @param vorname    Vorname der Person
     * @param nachname   Nachname der Person
     * @param alter      Alter der Person
     * @param geschlecht Geschlecht ("Mann", "Frau", "Divers")
     * @return die automatisch generierte ID, oder -1 bei Fehler
     */
    public int personEinfuegen(String vorname, String nachname, int alter, String geschlecht) {
        // PreparedStatement: PFLICHT bei Benutzereingaben!
        String sql = "INSERT INTO person (vorname, nachname, alter, geschlecht) VALUES (?, ?, ?, ?)";

        try (Connection conn        = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Parameter setzen – jedes ? wird durch den Wert ersetzt
            pstmt.setString(1, vorname);
            pstmt.setString(2, nachname);
            pstmt.setInt(3, alter);
            pstmt.setString(4, geschlecht);

            // SQL ausführen
            int betroffeneZeilen = pstmt.executeUpdate();
            LOG.info("Person eingefügt: " + betroffeneZeilen + " Zeile(n)");

            // Auto-generierte ID zurückgeben
            ResultSet generierteKeys = pstmt.getGeneratedKeys();
            if (generierteKeys.next()) {
                return generierteKeys.getInt(1);
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Einfügen der Person: " + e.getMessage(), e);
        }
        return -1; // Fehlerfall
    }

    // ─── READ: Datensätze lesen ───────────────────────────────────────

    /**
     * Liest alle Personen aus der Datenbank.
     *
     * TODO: Passe den Rückgabetyp auf deine eigene Klasse an!
     *
     * @return Liste aller Personen als String-Array [vorname, nachname, alter, geschlecht]
     */
    public List<String[]> allePersonenLesen() {
        List<String[]> ergebnisse = new ArrayList<>();
        String sql = "SELECT id, vorname, nachname, alter, geschlecht FROM person ORDER BY nachname";

        try (Connection conn   = verbinden();
             Statement stmt    = conn.createStatement();
             ResultSet rs      = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] zeile = {
                    String.valueOf(rs.getInt("id")),
                    rs.getString("vorname"),
                    rs.getString("nachname"),
                    String.valueOf(rs.getInt("alter")),
                    rs.getString("geschlecht")
                };
                ergebnisse.add(zeile);
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Lesen der Personen: " + e.getMessage(), e);
        }
        return ergebnisse;
    }

    /**
     * Sucht eine Person anhand ihrer ID.
     *
     * @param id  die gesuchte Personen-ID
     * @return String-Array mit Personendaten, oder null wenn nicht gefunden
     */
    public String[] personNachIdSuchen(int id) {
        String sql = "SELECT id, vorname, nachname, alter, geschlecht FROM person WHERE id = ?";

        try (Connection conn        = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("vorname"),
                    rs.getString("nachname"),
                    String.valueOf(rs.getInt("alter")),
                    rs.getString("geschlecht")
                };
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler bei Personensuche (ID=" + id + "): " + e.getMessage(), e);
        }
        return null; // nicht gefunden
    }

    // ─── UPDATE: Datensatz ändern ─────────────────────────────────────

    /**
     * Ändert das Alter einer Person.
     *
     * TODO: Passe für deine eigenen Felder an!
     *
     * @param personId  die ID der Person
     * @param neuesAlter  das neue Alter
     * @return true wenn erfolgreich geändert, false sonst
     */
    public boolean personAlterAendern(int personId, int neuesAlter) {
        String sql = "UPDATE person SET alter = ? WHERE id = ?";

        try (Connection conn        = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, neuesAlter);
            pstmt.setInt(2, personId);

            int betroffeneZeilen = pstmt.executeUpdate();
            LOG.info("Person geändert: " + betroffeneZeilen + " Zeile(n)");
            return betroffeneZeilen > 0;

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Ändern der Person: " + e.getMessage(), e);
        }
        return false;
    }

    // ─── DELETE: Datensatz löschen ────────────────────────────────────

    /**
     * Löscht eine Person anhand ihrer ID.
     * Verknüpfte Messungen werden durch ON DELETE CASCADE automatisch mitgelöscht!
     *
     * @param personId  die ID der zu löschenden Person
     * @return true wenn erfolgreich gelöscht, false sonst
     */
    public boolean personLoeschen(int personId) {
        String sql = "DELETE FROM person WHERE id = ?";

        try (Connection conn        = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, personId);

            int betroffeneZeilen = pstmt.executeUpdate();
            LOG.info("Person gelöscht: " + betroffeneZeilen + " Zeile(n)");
            return betroffeneZeilen > 0;

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Löschen der Person: " + e.getMessage(), e);
        }
        return false;
    }

    // ─── 1:N Beziehung: Messungen ─────────────────────────────────────

    /**
     * Fügt eine neue Messung für eine Person ein (1:N Beziehung).
     *
     * @param personId  Fremdschlüssel zur Person
     * @param gewicht   Gewicht in kg
     * @param groesse   Größe in m
     * @param bmi       berechneter BMI-Wert
     * @return ID der neuen Messung, oder -1 bei Fehler
     */
    public int messungEinfuegen(int personId, double gewicht, double groesse, double bmi) {
        String sql = "INSERT INTO messung (person_id, gewicht, groesse, bmi) VALUES (?, ?, ?, ?)";

        try (Connection conn        = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, personId);
            pstmt.setDouble(2, gewicht);
            pstmt.setDouble(3, groesse);
            pstmt.setDouble(4, bmi);

            pstmt.executeUpdate();

            ResultSet generierteKeys = pstmt.getGeneratedKeys();
            if (generierteKeys.next()) {
                return generierteKeys.getInt(1);
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Einfügen der Messung: " + e.getMessage(), e);
        }
        return -1;
    }

    /**
     * Liest alle Messungen einer bestimmten Person (JOIN über Fremdschlüssel).
     *
     * @param personId  die ID der Person
     * @return Liste aller Messungen als String-Array [gewicht, groesse, bmi, zeitstempel]
     */
    public List<String[]> messungenNachPerson(int personId) {
        List<String[]> ergebnisse = new ArrayList<>();
        String sql = """
                SELECT m.id, m.gewicht, m.groesse, m.bmi, m.zeitstempel
                FROM   messung m
                WHERE  m.person_id = ?
                ORDER BY m.zeitstempel DESC
                """;

        try (Connection conn        = verbinden();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, personId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String[] zeile = {
                    String.valueOf(rs.getInt("id")),
                    String.valueOf(rs.getDouble("gewicht")),
                    String.valueOf(rs.getDouble("groesse")),
                    String.format("%.2f", rs.getDouble("bmi")),
                    rs.getString("zeitstempel")
                };
                ergebnisse.add(zeile);
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Fehler beim Lesen der Messungen: " + e.getMessage(), e);
        }
        return ergebnisse;
    }

    // ─── toString ─────────────────────────────────────────────────────

    @Override
    public String toString() {
        // Passwort wird NICHT ausgegeben (Sicherheit!)
        return "DatabaseConnector{url='" + URL + "', benutzer='" + benutzer + "'}";
    }

    // ─── Hauptmethode zum Testen ──────────────────────────────────────

    /**
     * TODO: Entferne diese main()-Methode oder ersetze sie durch einen Unit Test
     *       bevor du das Projekt abgibst!
     */
    public static void main(String[] args) {
        // Connector erstellen
        DatabaseConnector db = new DatabaseConnector("root", "mein_passwort"); // ← anpassen!

        // 1. Verbindung testen
        System.out.println("Verbindungstest: " + db.verbindungTesten());

        // 2. Person einfügen
        int neueId = db.personEinfuegen("Max", "Mustermann", 30, "Mann");
        System.out.println("Neue Person-ID: " + neueId);

        // 3. Alle Personen anzeigen
        List<String[]> personen = db.allePersonenLesen();
        System.out.println("Alle Personen:");
        for (String[] p : personen) {
            System.out.println("  [" + p[0] + "] " + p[1] + " " + p[2] + ", " + p[3] + " Jahre");
        }

        // 4. Messung hinzufügen
        double gewicht = 80.0;
        double groesse = 1.80;
        double bmi     = gewicht / (groesse * groesse);
        int messungId  = db.messungEinfuegen(neueId, gewicht, groesse, bmi);
        System.out.println("Neue Messung-ID: " + messungId);

        // 5. Alter ändern
        boolean geaendert = db.personAlterAendern(neueId, 31);
        System.out.println("Alter geändert: " + geaendert);

        // 6. Person löschen (löscht auch Messungen durch ON DELETE CASCADE)
        boolean geloescht = db.personLoeschen(neueId);
        System.out.println("Person gelöscht: " + geloescht);
    }
}
