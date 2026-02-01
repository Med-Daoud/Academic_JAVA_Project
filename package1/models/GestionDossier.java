package projet_java.package1.models;

import java.util.ArrayList;
import java.util.List;

public class GestionDossier {

    private List<DossierMigration> dossiers;

    public GestionDossier() {
        this.dossiers = new ArrayList<>();
    }

    public List<DossierMigration> getDossiers() {
        return dossiers;
    }

    public void setDossiers(List<DossierMigration> dossiers) {
        this.dossiers = dossiers;
    }

    @Override
    public String toString() {
        return "GestionDossier{" +
                "dossiers=" + dossiers +
                '}';
    }
}
