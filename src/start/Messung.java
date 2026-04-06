package start;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Die Messung-Klasse repräsentiert eine einzelne BMI-Messung.
 * Sie speichert Gewicht, Größe, BMI und den Messzeitpunkt.
 * 
 * Die Berechnung nutzt die bestehende Bmirechner-Klasse (DRY-Prinzip).
 * 
 * @version 4.0
 * @since 2026-02-09
 */
public class Messung {
    
    // Attribute
    private int id;
    private int personId;
    private double gewicht;           // in kg
    private double groesse;           // in m
    private LocalDateTime zeitstempel; // Zeitpunkt der Messung
    private double bmi;               // Berechneter BMI
    private String kategorie;         // BMI-Kategorie (z.B. "Übergewicht")
    
    // Nutzen der bestehenden Bmirechner-Klasse
    private Bmirechner rechner;       // Delegiert die BMI-Berechnung
    
    /**
     * Konstruktor: Erstellt eine neue Messung.
     * Der BMI wird automatisch mittels Bmirechner berechnet.
     * 
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Größe in m
     * @throws IllegalArgumentException wenn Gewicht oder Größe ungültig sind
     */
    public Messung(double gewicht, double groesse) {
        this(0, 0, gewicht, groesse, LocalDateTime.now());
    }

    /**
     * Konstruktor mit IDs fuer Persistenz.
     *
     * @param id Eindeutige Messungs-ID
     * @param personId Zugehoerige Personen-ID
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Groesse in m
     */
    public Messung(int id, int personId, double gewicht, double groesse) {
        this(id, personId, gewicht, groesse, LocalDateTime.now());
    }

    public Messung(double gewicht, double groesse, LocalDateTime zeitstempel) {
        this(0, 0, gewicht, groesse, zeitstempel);
    }

    /**
     * Vollstaendiger Konstruktor mit IDs und Zeitstempel.
     *
     * @param id Eindeutige Messungs-ID
     * @param personId Zugehoerige Personen-ID
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Groesse in m
     * @param zeitstempel Der Zeitstempel der Messung
     */
    public Messung(int id, int personId, double gewicht, double groesse, LocalDateTime zeitstempel) {
        // Validierung
        if (gewicht <= 0) {
            throw new IllegalArgumentException("Gewicht muss größer als 0 sein!");
        }
        if (groesse <= 0) {
            throw new IllegalArgumentException("Größe muss größer als 0 sein!");
        }
        
        if (zeitstempel == null) {
            throw new IllegalArgumentException("Zeitstempel darf nicht null sein!");
        }
        
        this.id = id;
        this.personId = personId;
        this.gewicht = gewicht;
        this.groesse = groesse;
        this.zeitstempel = zeitstempel;
        
        // Bmirechner nutzen zur Berechnung
        this.rechner = new Bmirechner();
        this.bmi = rechner.berechne(gewicht, groesse);
        
        // Kategorisierung mit Bmirechner
        rechner.interpretiere();
        this.kategorie = rechner.getKategorie();
    }
    
    // Getter-Methoden
    public int getId() {
        return id;
    }

    public int getPersonId() {
        return personId;
    }

    public double getGewicht() {
        return gewicht;
    }
    
    public double getGroesse() {
        return groesse;
    }
    
    public LocalDateTime getZeitstempel() {
        return zeitstempel;
    }
    
    public double getBmi() {
        return bmi;
    }
    
    public String getKategorie() {
        return kategorie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
    
    /**
     * Gibt das Messdatum im Format "dd.MM.yyyy HH:mm" zurück.
     * 
     * @return Formatierter Zeitstempel
     */
    public String getFormatiertesDatum() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return zeitstempel.format(formatter);
    }
    
    /**
     * Gibt das Messdatum im Format "dd.MM.yyyy" zurück (nur Datum).
     * 
     * @return Formatiertes Datum
     */
    public String getFormatiertesKurzesDatum() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return zeitstempel.format(formatter);
    }
    
    /**
     * Gibt die Uhrzeit im Format "HH:mm" zurück.
     * 
     * @return Formatierte Uhrzeit
     */
    public String getFormatierteUhrzeit() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return zeitstempel.format(formatter);
    }
    
    /**
     * Gibt den BMI formatiert auf 2 Dezimalstellen zurück.
     * 
     * @return Formatierter BMI-String
     */
    public String getFormatierteBMI() {
        return String.format("%.2f", bmi);
    }
    
    /**
     * Gibt eine Farbe basierend auf der BMI-Kategorie zurück (für GUI).
     * 
     * @return Farb-String (#RRGGBB Format)
     */
    public String getKategorieColor() {
        switch (kategorie) {
            case "Leichtes Untergewicht": 
            case "Mäßiges Untergewicht":
            case "Starkes Untergewicht":
                return "#FFD700"; // Gold
            case "Normalgewicht": 
                return "#90EE90"; // LightGreen
            case "Prädipositas":
                return "#FFA500"; // Orange
            case "Adipositas Grad I":
            case "Adipositas Grad II":
            case "Adipositas Grad III":
                return "#FF6347"; // Tomato
            default: 
                return "#FFFFFF"; // White
        }
    }
    
    @Override
    public String toString() {
        return "Messung{" +
            "id=" + id +
            ", personId=" + personId +
            ", " +
                "gewicht=" + gewicht + " kg, " +
                "groesse=" + groesse + " m, " +
                "bmi=" + String.format("%.2f", bmi) + ", " +
                "kategorie='" + kategorie + "', " +
                "zeitstempel=" + getFormatiertesDatum() +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Messung messung = (Messung) obj;
        return Double.compare(messung.gewicht, gewicht) == 0 &&
               Double.compare(messung.groesse, groesse) == 0 &&
               zeitstempel.equals(messung.zeitstempel);
    }
    
    @Override
    public int hashCode() {
        int result = Double.hashCode(gewicht);
        result = 31 * result + Double.hashCode(groesse);
        result = 31 * result + zeitstempel.hashCode();
        return result;
    }
}
