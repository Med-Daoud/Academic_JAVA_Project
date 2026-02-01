package projet_java.package1.models;

import java.time.LocalDate;


public  sealed class Dossier permits DossierMigration{

    private int numDossier;
    private LocalDate dateDepot;
    private LocalDate dateTraitement;
    private LocalDate rendezVous;

    public Dossier(int numDossier, LocalDate dateDepot, LocalDate dateTraitement, LocalDate rendezVous) {
        this.numDossier = numDossier;
        this.dateDepot = dateDepot;
        this.dateTraitement = dateTraitement;
        this.rendezVous = rendezVous;
    }

  public Dossier() {
  this.numDossier = 0; 
    this.dateDepot = LocalDate.now();  
    this.dateTraitement = null;  
    this.rendezVous = null;  
}

    public int getNumDossier() { return numDossier; }
    public LocalDate getDateDepot() { return dateDepot; }
    public LocalDate getDateTraitement() { return dateTraitement; }
    public LocalDate getRendezVous() { return rendezVous; }

    public void setNumDossier(int numDossier) { this.numDossier = numDossier; }
    public void setDateDepot(LocalDate dateDepot) { this.dateDepot = dateDepot; }
    public void setDateTraitement(LocalDate dateTraitement) { this.dateTraitement = dateTraitement; }
    public void setRendezVous(LocalDate rendezVous) { this.rendezVous = rendezVous; }

    @Override
    public String toString() {
        return "Dossier{" +
                "num=" + numDossier +
                ", depot=" + dateDepot +
                ", traitement=" + dateTraitement +
                ", rdv=" + rendezVous +
                '}';
    }
}
