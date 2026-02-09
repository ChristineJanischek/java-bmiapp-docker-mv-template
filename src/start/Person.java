package start;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Person-Klasse repräsentiert eine Person im BMI-Rechner.
 * Sie speichert persönliche Informationen und ihre Messungen.
 * 
 * 1:N Beziehung: Eine Person hat mehrere Messungen.
 * 
 * @version 4.0
 * @since 2026-02-09
 */
public class Person {
    
    // Attribute
    private String vorname;
    private String nachname;
    private int alter;
    private String geschlecht; // "Mann" oder "Frau"
    private String email;
    
    // 1:N Beziehung: Eine Person hat mehrere Messungen
    private List<Messung> messungen; // ArrayList speichert alle Messungen
    
    /**
     * Konstruktor: Erstellt eine neue Person mit Grunddaten.
     * 
     * @param vorname Der Vorname der Person
     * @param nachname Der Nachname der Person
     * @param alter Das Alter der Person
     * @param geschlecht Das Geschlecht der Person ("Mann" oder "Frau")
     * @param email Die E-Mail-Adresse der Person
     */
    public Person(String vorname, String nachname, int alter, String geschlecht, String email) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.alter = alter;
        this.geschlecht = geschlecht;
        this.email = email;
        
        // 1:N Assoziation initialisieren
        this.messungen = new ArrayList<>();
    }
    
    // Getter-Methoden
    public String getVorname() {
        return vorname;
    }
    
    public String getNachname() {
        return nachname;
    }
    
    public int getAlter() {
        return alter;
    }
    
    public String getGeschlecht() {
        return geschlecht;
    }
    
    public String getEmail() {
        return email;
    }
    
    // Setter-Methoden
    public void setAlter(int alter) {
        this.alter = alter;
    }
    
    public void setEmail(String email) {
        // Optional: Validierung
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Ungültige Email-Adresse!");
        }
    }
    
    /**
     * Gibt den vollständigen Namen der Person zurück.
     * 
     * @return Name im Format "Vorname Nachname"
     */
    public String getFullName() {
        return vorname + " " + nachname;
    }
    
    // === 1:N Beziehung: Messungen verwalten ===
    
    /**
     * Fügt eine neue Messung zu dieser Person hinzu.
     * 
     * @param messung Die neue Messung
     */
    public void addMessung(Messung messung) {
        if (messung == null) {
            throw new IllegalArgumentException("Messung darf nicht null sein!");
        }
        messungen.add(messung);
    }
    
    /**
     * Gibt alle Messungen dieser Person zurück.
     * 
     * @return Liste aller Messungen (Kopie für Kapselung)
     */
    public List<Messung> getMessungen() {
        return new ArrayList<>(messungen); // Gibt eine Kopie zurück (Kapselung!)
    }
    
    /**
     * Gibt die Anzahl der Messungen zurück.
     * 
     * @return Anzahl der gespeicherten Messungen
     */
    public int getAnzahlMessungen() {
        return messungen.size();
    }
    
    /**
     * Gibt die letzte Messung zurück (falls vorhanden).
     * 
     * @return Die letzte Messung, oder null wenn keine Messungen vorhanden
     */
    public Messung getLetzteMessung() {
        if (messungen.isEmpty()) {
            return null;
        }
        return messungen.get(messungen.size() - 1);
    }
    
    /**
     * Gibt die erste Messung zurück (falls vorhanden).
     * 
     * @return Die erste Messung, oder null wenn keine Messungen vorhanden
     */
    public Messung getErsteMessung() {
        if (messungen.isEmpty()) {
            return null;
        }
        return messungen.get(0);
    }
    
    /**
     * Gibt den Durchschnitts-BMI aller Messungen zurück.
     * 
     * @return Der durchschnittliche BMI, oder 0 wenn keine Messungen vorhanden
     */
    public double getDurchschnittsBmi() {
        if (messungen.isEmpty()) {
            return 0.0;
        }
        
        double summe = 0.0;
        for (Messung m : messungen) {
            summe += m.getBmi();
        }
        
        return summe / messungen.size();
    }
    
    /**
     * Gibt die Messung mit dem niedrigsten (besten) BMI zurück.
     * 
     * @return Die Messung mit niedrigstem BMI, oder null wenn keine Messungen vorhanden
     */
    public Messung getBesteMessung() {
        if (messungen.isEmpty()) {
            return null;
        }
        
        Messung beste = messungen.get(0);
        for (Messung m : messungen) {
            if (m.getBmi() < beste.getBmi()) {
                beste = m;
            }
        }
        return beste;
    }
    
    /**
     * Gibt die Messung mit dem höchsten (schlechtesten) BMI zurück.
     * 
     * @return Die Messung mit höchstem BMI, oder null wenn keine Messungen vorhanden
     */
    public Messung getSchlechtesteMessung() {
        if (messungen.isEmpty()) {
            return null;
        }
        
        Messung schlechteste = messungen.get(0);
        for (Messung m : messungen) {
            if (m.getBmi() > schlechteste.getBmi()) {
                schlechteste = m;
            }
        }
        return schlechteste;
    }
    
    /**
     * Gibt die BMI-Differenz zwischen bester und schlechtester Messung zurück.
     * 
     * @return Die BMI-Differenz, oder 0 wenn weniger als 2 Messungen vorhanden
     */
    public double getBmiDifferenz() {
        if (messungen.size() < 2) {
            return 0.0;
        }
        
        Messung beste = getBesteMessung();
        Messung schlechteste = getSchlechtesteMessung();
        
        return schlechteste.getBmi() - beste.getBmi();
    }
    
    /**
     * Entfernt eine Messung aus der Liste.
     * 
     * @param messung Die zu entfernende Messung
     * @return true wenn erfolgreich entfernt, false sonst
     */
    public boolean removeMessung(Messung messung) {
        return messungen.remove(messung);
    }
    
    /**
     * Entfernt alle Messungen dieser Person.
     */
    public void clearMessungen() {
        messungen.clear();
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", alter=" + alter +
                ", geschlecht='" + geschlecht + '\'' +
                ", email='" + email + '\'' +
                ", messungen=" + messungen.size() + " Einträge" +
                '}';
    }
}
