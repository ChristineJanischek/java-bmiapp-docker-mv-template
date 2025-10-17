
package start;


public class Bmirechner {
    // Attribute
    private double groesse;
    private double gewicht;
    private double ergebnis;
    private String kategorie;

    /**
     * Standardkonstruktor
     */
    public Bmirechner() {
    }

    /**
     * Konstruktor mit Initialwerten
     */
    public Bmirechner(double gewicht, double groesse) {
        this.gewicht = gewicht;
        this.groesse = groesse;
    }

    // Getter
    public double getGroesse() {
        return groesse;
    }

    public double getGewicht() {
        return gewicht;
    }

    public double getErgebnis() {
        return ergebnis;
    }

    public String getKategorie() {
        return this.kategorie;
    }

    // Setter
    public void setGroesse(double groesse) {
        this.groesse = groesse;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    /**
     * Berechnet den BMI und speichert das Ergebnis im Attribut ergebnis.
     * @return berechneter BMI
     */
    public double berechne(double pGewicht, double pGroesse) {
        if (pGroesse > 0) {
            this.ergebnis = pGewicht / (pGroesse * pGroesse);
        } else {
            this.ergebnis = 0;
        }
        return this.ergebnis;
    }

    /**
     * Interpretiert das BMI-Ergebnis und gibt die Kategorie zurück.
     * @return Kategorie als String
     */
    public String interpretiere() {
        if (this.ergebnis >= 40) {
            this.kategorie = "Adipositas Grad III";
        } else if (this.ergebnis >= 35) {
            this.kategorie = "Adipositas Grad II";
        } else if (this.ergebnis >= 30) {
            this.kategorie = "Adipositas Grad I";
        } else if (this.ergebnis >= 25) {
            this.kategorie = "Prädipositas";
        } else if (this.ergebnis >= 18.5) {
            this.kategorie = "Normalgewicht";
        } else if (this.ergebnis >= 17) {
            this.kategorie = "Leichtes Untergewicht";
        } else if (this.ergebnis >= 16) {
            this.kategorie = "Mäßiges Untergewicht";
        } else {
            this.kategorie = "Starkes Untergewicht";
        }
        return this.kategorie;
    }

    /**
     * Gibt alle Attributwerte des Objekts als formatierten String zurück.
     * @return String-Repräsentation des Objekts
     */
    @Override
    public String toString() {
        return "Bmirechner{" +
                "gewicht=" + gewicht + " kg" +
                ", groesse=" + groesse + " m" +
                ", ergebnis=" + String.format("%.2f", ergebnis) +
                ", kategorie='" + (kategorie != null ? kategorie : "noch nicht berechnet") + "'" +
                '}';
    }
}

