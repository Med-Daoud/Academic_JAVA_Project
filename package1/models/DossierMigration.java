
package projet_java.package1.models;
import java.time.LocalDate;
import projet_java.package2.models.Professionnel;
public final  class DossierMigration extends Dossier {

    private Professionnel professionnel;
    private boolean resultatTraitement;

    
    public DossierMigration(int numDossier, 
                            LocalDate dateDepot, 
                            LocalDate dateTraitement,
                            Professionnel professionnel,
                            boolean resultatTraitement,LocalDate rendezVous) {

        super(numDossier, dateDepot, dateTraitement , rendezVous); // appel au parent
        this.professionnel = professionnel;
        this.resultatTraitement = resultatTraitement;
    }
    
    public DossierMigration() {
        super();
        this.professionnel = null; 
        this.resultatTraitement = false;}

    
    public Professionnel getProfessionnel() {
        return professionnel;
    }

    public void setProfessionnel(Professionnel professionnel) {
        this.professionnel = professionnel;
    }

    public boolean isResultatTraitement() {
        return resultatTraitement;
    }

    public void setResultatTraitement(boolean resultatTraitement) {
        this.resultatTraitement = resultatTraitement;
    }

    @Override
    public String toString() {
        return "DossierMigration{" +super.toString() +
                ", professionnel=" + professionnel +
                ", resultatTraitement=" + resultatTraitement +
                '}';
    }

   }