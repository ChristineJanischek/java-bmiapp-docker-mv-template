
package start;

public class Bmirechner {
  // Deklaration der Eigenschaften (Attribute)
  // Attribute speichern den Zustand des Objekts (z.B. Gewicht, Größe, Ergebnis, Kategorie)
  private double groesse;
  private double gewicht;
  private double ergebnis;
  private String kategorie;
  
  // Standard (Default) Konstruktor
    /**
     * Standard-Konstruktor: Initialisiert ein neues Objekt ohne Werte.
     * Konstruktoren werden beim Erzeugen eines Objekts aufgerufen und setzen die Anfangswerte der Attribute.
     */
    public Bmirechner(){
      // Standardwerte können hier gesetzt werden
    }
  
  // Getter: Ermittelt Eigenschaftswert eines Objektes
    /**
     * Getter-Methoden geben den Wert eines privaten Attributs zurück.
     * Sie ermöglichen kontrollierten Lesezugriff von außen.
     */
    public double getGroesse(){
      return groesse;
    }
    public double getGewicht(){
      return gewicht;
    }
    public double getErgebnis(){
      return ergebnis;
    }
    public String getKategorie(){
      return this.kategorie;
    }
  
  // Setter: Übermittelt Eigenschaftswert an das Attribut des Objektes
    /**
     * Setter-Methoden setzen den Wert eines privaten Attributs.
     * Sie ermöglichen kontrollierten Schreibzugriff von außen und können Validierung enthalten.
     */
    public void setGroesse(double pGroesse){
      // Beispiel für Validierung: Größe muss positiv sein
      this.groesse = pGroesse;
    }
    public void setGewicht(double pGewicht){
      this.gewicht = pGewicht;
    }
    public void setErgebnis(double pErgebnis){
      this.ergebnis = pErgebnis;
    }  
    public void setKategorie(String pKategorie){
      this.kategorie = pKategorie;
    }
  
  // Sonstige Methoden: Methoden die mehr können als nur er- und übermitteln
    /**
     * Fachmethode: Setzt Gewicht und Größe und berechnet den BMI.
     * Methoden wie diese führen Logik aus und verändern den Zustand des Objekts.
     */
    public double berechne(double pGewicht, double pGroesse) {
      this.gewicht = pGewicht;
      this.groesse = pGroesse;
      if (pGroesse > 0) {
        this.ergebnis = pGewicht / (pGroesse * pGroesse);
      } else {
        this.ergebnis = 0;
      }
      return this.ergebnis;
    }

    /**
     * Alte Methode bleibt zur Kompatibilität erhalten.
     */
    public void berechnen(){
      this.ergebnis = this.gewicht / (this.groesse * this.groesse);
      System.out.println("Ergebnis:"+ this.ergebnis );
    }

    /**
     * Interpretiert das Ergebnis und setzt die Kategorie entsprechend dem BMI-Wert.
     * Zeigt, wie Methoden komplexere Logik umsetzen können.
     */
    public void interpretiere(){
      if (this.ergebnis >= 40) {
        this.kategorie = "Adipositas Grad III";
      }else if(this.ergebnis >= 35 && this.ergebnis < 40){
        this.kategorie = "Adipositas Grad II";
      }else if(this.ergebnis >= 30 && this.ergebnis < 35){
        this.kategorie = "Adipositas Grad I";
      } else if(this.ergebnis >= 25 && this.ergebnis < 30){
        this.kategorie = "Prädipositas";
      } else if(this.ergebnis >= 18.5 && this.ergebnis < 25){
        this.kategorie = "Normalgewicht";
      } else if(this.ergebnis >= 17 && this.ergebnis < 18.5){
        this.kategorie = "Leichtes Untergewicht";
      } else if(this.ergebnis >= 16 && this.ergebnis < 17){
        this.kategorie = "Mäßiges Untergewicht";
      } else{
        this.kategorie = "Starkes Untergewicht";
      }
      System.out.println("Ergebnis:"+ this.kategorie);
    }

    /**
     * Polymorphe Methode (Überladung): Interpretiert BMI unter Berücksichtigung von Alter und Geschlecht.
     * Diese Methode erweitert die einfache interpretiere()-Methode um zwei zusätzliche Parameter.
     * 
     * @param pGewicht Körpergewicht in kg
     * @param pGroesse Körpergröße in Metern
     * @param pAlter Alter der Person in Jahren
     * @param pGeschlecht Geschlecht ("männlich" oder "weiblich")
     */
    public void interpretiere(double pGewicht, double pGroesse, int pAlter, String pGeschlecht) {
      // Zuerst BMI berechnen
      berechne(pGewicht, pGroesse);
      
      // Alters- und geschlechtsspezifische Interpretation
      if (pAlter < 25) {
        // Junge Erwachsene (< 25 Jahre)
        if (this.ergebnis < 19) {
          this.kategorie = "Untergewicht";
        } else if (this.ergebnis < 24) {
          this.kategorie = "Normalgewicht";
        } else {
          this.kategorie = "Übergewicht";
        }
      } else if (pAlter >= 65) {
        // Senioren (≥ 65 Jahre)
        if (this.ergebnis < 22) {
          this.kategorie = "Untergewicht";
        } else if (this.ergebnis < 28) {
          this.kategorie = "Normalgewicht";
        } else {
          this.kategorie = "Übergewicht";
        }
      } else {
        // Erwachsene (25-64 Jahre) - geschlechtsspezifisch
        if (pGeschlecht.equalsIgnoreCase("männlich")) {
          if (this.ergebnis < 20) {
            this.kategorie = "Untergewicht";
          } else if (this.ergebnis < 25) {
            this.kategorie = "Normalgewicht";
          } else {
            this.kategorie = "Übergewicht";
          }
        } else {
          // Weiblich
          if (this.ergebnis < 19) {
            this.kategorie = "Untergewicht";
          } else if (this.ergebnis < 24) {
            this.kategorie = "Normalgewicht";
          } else {
            this.kategorie = "Übergewicht";
          }
        }
      }
      /*Testausgabe: */
      System.out.println("Erweiterte Interpretation (Alter: " + pAlter + ", Geschlecht: " + pGeschlecht + "): " + this.kategorie);
    }
}

