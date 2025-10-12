
package start;

public class Bmirechner {
  // Deklaration der Eigenschaften (Attribute)
  private double groesse;
  private double gewicht;
  private double ergebnis;
  private String kategorie;
  
  // Standard (Default) Konstruktor
  public Bmirechner(){
    
  }
  
  // Getter: Ermittelt Eigenschaftswert eines Objektes
  
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
  
  public void setGroesse(double pGroesse){
    
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
  
  public void berechnen(){
    
    this.ergebnis = this.gewicht / (this.groesse * this.groesse);
    System.out.println("Ergebnis:"+ this.ergebnis );
  }
  
  
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
      
      this.kategorie = "Leichtes Untergwicht";
    } else if(this.ergebnis >= 16 && this.ergebnis < 17){
      this.kategorie = "Mäßiges Untergewicht";
      
    } else{
      this.kategorie = "Starkes Untergewicht";
    }
    
    System.out.println("Ergebnis:"+ this.kategorie);
  }
}


