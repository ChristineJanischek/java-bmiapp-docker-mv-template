package start;

public class Main {
    public static void main(String[] args) {
    //Erzeuge ein Objekt der Klasse 
    Bmirechner rechner1 = new Bmirechner();
    
    //##### IF-Fall: bmi > 40 testen
    
    //Eingabe: Wert an Objekt der Fachklasse übermitteln
    rechner1.setGroesse(1.80);
    rechner1.setGewicht(150.0); 
    
    //Verarbeitung: Wert verarbeiten z.b. berechnen, prüfen
    rechner1.berechnen();
    rechner1.interpretiere();
    
    //Ausgabe: Erzeut eine Ausgabe des Ergebnisses auf der Konsole
    System.out.println("BMI: " + rechner1.getErgebnis());
    System.out.println("Kategorie: " + rechner1.getKategorie()); 


        // Verbindungsdaten für die Datenbank (müssen zu docker-compose.yml passen)
    String url = "jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC";
        String user = "user";
        String password = "password";

        // Datenbankverbindung testen
        DatabaseConnector connector = new DatabaseConnector(url, user, password);
        connector.connectAndQuery();
    }
}

