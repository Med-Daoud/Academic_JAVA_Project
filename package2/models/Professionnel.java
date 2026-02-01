package projet_java.package2.models;

import java.time.LocalDate;

public final class Professionnel extends Personne {
    private Profession profession; 
    private String entreprise;
    private double salaire;

    public Professionnel(String id, String nom, String prenom, String adresse, String numTel,
                         LocalDate dateNaissance, Pays pays, Profession profession,
                         String entreprise, double salaire) {
        
       
        super(id, nom, prenom, adresse, numTel, dateNaissance, pays);
        
        this.entreprise = entreprise;
        this.salaire = salaire;
        this.profession = profession;
    }

   

     public Profession getProfession() {
        return profession;}
     
     
    public void setProfession(Profession profession) {
        this.profession = profession; }
    
    
    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

   @Override
public String toString() {
    String professionNom = (profession != null) ? profession.getNom() : "N/A";
    return super.toString() +
           "\nEntreprise: " + entreprise +
           "\nSalaire: " + salaire + " DT" +
           "\nProfession: " + professionNom;
}

}

