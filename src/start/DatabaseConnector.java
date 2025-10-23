package start;

import java.sql.*;

// Diese Klasse verwaltet die Verbindung zur MySQL-Datenbank
public class DatabaseConnector {
    private final String url;
    private final String user;
    private final String password;

    // Konstruktor: übergibt Verbindungsdaten beim Erstellen des Objekts
    public DatabaseConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // Diese Methode stellt die Verbindung her und führt eine einfache Abfrage aus
    public void connectAndQuery() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Datenbankverbindung erfolgreich!");

            // SQL-Anweisung erstellen und ausführen
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOW()");

            // Ergebnis anzeigen
            while (rs.next()) {
                System.out.println("Aktuelle Zeit in der DB: " + rs.getString(1));
            }
        } catch (Exception e) {
            // Fehlerbehandlung
            System.err.println("Fehler bei der DB-Verbindung:");
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        // Passwort wird aus Sicherheitsgründen nicht ausgegeben
        return "DatabaseConnector{url='" + url + "', user='" + user + "'}";
    }

}

