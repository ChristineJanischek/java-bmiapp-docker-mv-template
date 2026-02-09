package start;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller-Klasse nach dem MVC-Prinzip.
 * Steuert die Interaktion zwischen Model (Bmirechner, Person, Messung) und View (MainWindow).
 * 
 * Version 4: Erweitert um Person- und Messung-Verwaltung
 * 
 * @version 4.0
 * @since 2026-02-09
 */
public class BmiManager {
    // Attribut: Referenz auf das Model (Bmirechner)
    // Das Model speichert die Daten und Logik der BMI-Berechnung
    private Bmirechner model;
    
    // Version 4: Verwaltung von Personen und Messungen
    private Person aktuellePerson;              // Die aktuell ausgewählte Person
    private List<Person> personenListe;         // Liste aller Personen im System
    
    /**
     * Standard-Konstruktor: Initialisiert den Manager mit einem neuen Model-Objekt.
     * Konstruktoren werden beim Erzeugen eines Objekts aufgerufen und setzen Anfangswerte.
     */
    public BmiManager() {
        this.model = new Bmirechner();
        this.personenListe = new ArrayList<>();
        this.aktuellePerson = null;
    }

    /**
     * Konstruktor mit Parameter: Ermöglicht das Setzen eines bestehenden Model-Objekts.
     */
    public BmiManager(Bmirechner pModel) {
        this.model = pModel;
        this.personenListe = new ArrayList<>();
        this.aktuellePerson = null;
    }

    /**
     * Fachmethode: Führt die BMI-Berechnung aus, indem sie die Methode des Models aufruft.
     * Kompatibel mit Version 3 (ohne Person).
     */
    public double berechneBMI(double pGewicht, double pGroesse) {
        return model.berechne(pGewicht, pGroesse);
    }

    /**
     * Fachmethode: Interpretiert das BMI-Ergebnis über das Model.
     */
    public void interpretiereBMI() {
        model.interpretiere();
    }

    /**
     * Getter: Gibt das Model-Objekt zurück.
     * Getter ermöglichen kontrollierten Lesezugriff auf private Attribute.
     */
    public Bmirechner getModel() {
        return model;
    }

    /**
     * Setter: Setzt das Model-Objekt.
     * Setter ermöglichen kontrollierten Schreibzugriff und können Validierung enthalten.
     */
    public void setModel(Bmirechner pModel) {
        this.model = pModel;
    }
    
    // ========== Version 4: Person-Verwaltung ==========
    
    /**
     * Erstellt eine neue Person und fügt sie zur Liste hinzu.
     * 
     * @param vorname Vorname der Person
     * @param nachname Nachname der Person
     * @param alter Alter der Person
     * @param geschlecht Geschlecht der Person
     * @param email E-Mail-Adresse der Person
     * @return Die erstellte Person
     * @throws IllegalArgumentException bei ungültigen Eingaben
     */
    public Person erstellePerson(String vorname, String nachname, int alter, 
                                  String geschlecht, String email) {
        // Validierung
        if (vorname == null || vorname.trim().isEmpty()) {
            throw new IllegalArgumentException("Vorname darf nicht leer sein!");
        }
        if (nachname == null || nachname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nachname darf nicht leer sein!");
        }
        if (alter < 1 || alter > 150) {
            throw new IllegalArgumentException("Alter muss zwischen 1 und 150 liegen!");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Ungültige E-Mail-Adresse!");
        }
        
        Person person = new Person(vorname, nachname, alter, geschlecht, email);
        personenListe.add(person);
        return person;
    }
    
    /**
     * Setzt die aktuelle Person.
     * 
     * @param person Die Person, die aktiv sein soll
     */
    public void setAktuellePerson(Person person) {
        this.aktuellePerson = person;
    }
    
    /**
     * Gibt die aktuelle Person zurück.
     * 
     * @return Die aktuelle Person oder null
     */
    public Person getAktuellePerson() {
        return aktuellePerson;
    }
    
    /**
     * Gibt alle Personen zurück.
     * 
     * @return Liste aller Personen (Kopie)
     */
    public List<Person> getAllePersonen() {
        return new ArrayList<>(personenListe);
    }
    
    /**
     * Entfernt eine Person aus der Liste.
     * 
     * @param person Die zu entfernende Person
     * @return true wenn erfolgreich entfernt
     */
    public boolean entfernePerson(Person person) {
        if (person == aktuellePerson) {
            aktuellePerson = null;
        }
        return personenListe.remove(person);
    }
    
    // ========== Version 4: Messungs-Verwaltung ==========
    
    /**
     * Erstellt eine neue Messung für die aktuelle Person.
     * 
     * @param gewicht Gewicht in kg
     * @param groesse Größe in m
     * @return Die erstellte Messung
     * @throws IllegalStateException wenn keine Person ausgewählt ist
     * @throws IllegalArgumentException bei ungültigen Eingaben
     */
    public Messung erstelleMessung(double gewicht, double groesse) {
        if (aktuellePerson == null) {
            throw new IllegalStateException("Keine Person ausgewählt! Bitte zuerst eine Person erstellen oder auswählen.");
        }
        
        // Validierung
        if (gewicht <= 0 || gewicht > 500) {
            throw new IllegalArgumentException("Gewicht muss zwischen 0 und 500 kg liegen!");
        }
        if (groesse <= 0 || groesse > 3.0) {
            throw new IllegalArgumentException("Größe muss zwischen 0 und 3.0 m liegen!");
        }
        
        Messung messung = new Messung(gewicht, groesse);
        aktuellePerson.addMessung(messung);
        
        return messung;
    }
    
    /**
     * Gibt alle Messungen der aktuellen Person zurück.
     * 
     * @return Liste der Messungen, oder leere Liste wenn keine Person ausgewählt
     */
    public List<Messung> getAlleMessungen() {
        if (aktuellePerson == null) {
            return new ArrayList<>();
        }
        return aktuellePerson.getMessungen();
    }
    
    /**
     * Gibt die letzte Messung der aktuellen Person zurück.
     * 
     * @return Die letzte Messung oder null
     */
    public Messung getLetzteMessung() {
        if (aktuellePerson == null) {
            return null;
        }
        return aktuellePerson.getLetzteMessung();
    }
    
    /**
     * Berechnet den Durchschnitts-BMI der aktuellen Person.
     * 
     * @return Durchschnitts-BMI oder 0.0 wenn keine Person/Messungen
     */
    public double getDurchschnittsBmi() {
        if (aktuellePerson == null) {
            return 0.0;
        }
        return aktuellePerson.getDurchschnittsBmi();
    }
    
    /**
     * Gibt die Anzahl der Messungen der aktuellen Person zurück.
     * 
     * @return Anzahl der Messungen
     */
    public int getAnzahlMessungen() {
        if (aktuellePerson == null) {
            return 0;
        }
        return aktuellePerson.getAnzahlMessungen();
    }
    
    /**
     * Gibt die Anzahl aller Personen zurück.
     * 
     * @return Anzahl der Personen
     */
    public int getAnzahlPersonen() {
        return personenListe.size();
    }
    
    /**
     * Sucht eine Person nach Vor- und Nachname.
     * 
     * @param vorname Vorname der Person
     * @param nachname Nachname der Person
     * @return Die gefundene Person oder null
     */
    public Person findePerson(String vorname, String nachname) {
        for (Person p : personenListe) {
            if (p.getVorname().equalsIgnoreCase(vorname) && 
                p.getNachname().equalsIgnoreCase(nachname)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Löscht alle Daten (Personen und Messungen).
     */
    public void allesDatenLoeschen() {
        personenListe.clear();
        aktuellePerson = null;
    }
}
