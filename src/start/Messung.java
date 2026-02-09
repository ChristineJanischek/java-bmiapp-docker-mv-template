package start;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Die Messung-Klasse repräsentiert eine einzelne BMI-Messung.
 * Sie speichert Gewicht, Größe, BMI und den Messzeitpunkt.
 * 
 * Diese Klasse wird in einer 1:N Beziehung mit der Person-Klasse verwendet.
 * 
 * @version 4.0
 * @since 2026-02-09
 */
public class Messung {
    
    // Attribute
    private double gewicht;           // in kg
    private double groesse;           // in m
    private LocalDateTime zeitstempel; // Zeitpunkt der Messung
    private double bmi;               // Berechneter BMI
    private String kategorie;         // BMI-Kategorie (z.B. "Übergewicht")
    
    /**
     * Konstruktor: Erstellt eine neue Messung.
     * Der BMI wird automatisch berechnet.
     * 
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Größe in m
     * @throws IllegalArgumentException wenn Gewicht oder Größe ungültig sind
     */
    public Messung(double gewicht, double groesse) {
        // Validierung
        if (gewicht <= 0) {
            throw new IllegalArgumentException("Gewicht muss größer als 0 sein!");
        }
        if (groesse <= 0) {
            throw new IllegalArgumentException("Größe muss größer als 0 sein!");
        }
        
        this.gewicht = gewicht;
        this.groesse = groesse;
        this.zeitstempel = LocalDateTime.now();
        this.bmi = berechneBMI(gewicht, groesse);
        this.kategorie = bestimmeKategorie(this.bmi);
    }
    
    /**
     * Konstruktor mit spezifischem Zeitstempel (für Tests oder Import).
     * 
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Größe in m
     * @param zeitstempel Der Zeitstempel der Messung
     */
    public Messung(double gewicht, double groesse, LocalDateTime zeitstempel) {
        if (gewicht <= 0) {
            throw new IllegalArgumentException("Gewicht muss größer als 0 sein!");
        }
        if (groesse <= 0) {
            throw new IllegalArgumentException("Größe muss größer als 0 sein!");
        }
        if (zeitstempel == null) {
            throw new IllegalArgumentException("Zeitstempel darf nicht null sein!");
        }
        
        this.gewicht = gewicht;
        this.groesse = groesse;
        this.zeitstempel = zeitstempel;
        this.bmi = berechneBMI(gewicht, groesse);
        this.kategorie = bestimmeKategorie(this.bmi);
    }
    
    /**
     * Berechnet den BMI aus Gewicht und Größe.
     * Formel: BMI = Gewicht / (Größe²)
     * 
     * @param gewicht Das Gewicht in kg
     * @param groesse Die Größe in m
     * @return Der berechnete BMI
     */
    private double berechneBMI(double gewicht, double groesse) {
        return gewicht / (groesse * groesse);
    }
    
    /**
     * Bestimmt die BMI-Kategorie basierend auf dem BMI-Wert.
     * 
     * Kategorien nach WHO-Standard:
     * - < 18.5: Untergewicht
     * - 18.5 - 24.9: Normalgewicht
     * - 25.0 - 29.9: Übergewicht
     * - >= 30.0: Adipositas
     * 
     * @param bmi Der BMI-Wert
     * @return Die BMI-Kategorie als String
     */
    private String bestimmeKategorie(double bmi) {
        if (bmi < 18.5) return "Untergewicht";
        else if (bmi < 25) return "Normalgewicht";
        else if (bmi < 30) return "Übergewicht";
        else return "Adipositas";
    }
    
    // Getter-Methoden
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
            case "Untergewicht": return "#FFD700"; // Gold
            case "Normalgewicht": return "#90EE90"; // LightGreen
            case "Übergewicht": return "#FFA500"; // Orange
            case "Adipositas": return "#FF6347"; // Tomato
            default: return "#FFFFFF"; // White
        }
    }
    
    @Override
    public String toString() {
        return "Messung{" +
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
