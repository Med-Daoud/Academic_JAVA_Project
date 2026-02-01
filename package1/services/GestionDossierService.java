package projet_java.package1.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import projet_java.package1.models.DossierMigration;
import projet_java.package1.models.GestionDossier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import projet_java.package2.models.Professionnel;

public class GestionDossierService {

    private final GestionDossier gestionDossier;
    private static final String FILE_DOSSIERS = "data/dossiers.txt";

   
      public GestionDossierService() {
        this.gestionDossier = new GestionDossier();
    }
      
    public GestionDossierService(GestionDossier gestionDossier) {
        this.gestionDossier = gestionDossier;
    }

    public void ajouterDossier(DossierMigration dossier) {
        gestionDossier.getDossiers().add(dossier);
        sauvegarderDossiers(); 
    }

        public boolean supprimerDossier(DossierMigration dossier) {
        boolean removed = gestionDossier.getDossiers().remove(dossier);
        sauvegarderDossiers();
        return removed;
    }


    public List<DossierMigration> getTousLesDossiers() {
        return gestionDossier.getDossiers();
    }

    

        public boolean existe(int id) {
        return gestionDossier.getDossiers()
                .stream()
                .anyMatch(d -> d.getNumDossier() == id);
    }

    
    public int count() {
        return gestionDossier.getDossiers().size();
    }

    public DossierMigration chercherParNumero(int numero) {
    return gestionDossier.getDossiers()
            .stream()
            .filter(d -> d.getNumDossier() == numero)
            .findFirst()
            .orElse(null);
}
    
    public void sauvegarderDossiers() {
    File file = new File("data/dossiers.txt");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (DossierMigration dossier : gestionDossier.getDossiers()) {
            writer.write(String.format("%d|%s|%s|%s|%b",
                    dossier.getNumDossier(),
                    dossier.getDateDepot() != null ? dossier.getDateDepot() : "",
                    dossier.getDateTraitement() != null ? dossier.getDateTraitement() : "",
                    dossier.getProfessionnel() != null ? dossier.getProfessionnel().getId() : "",
                    dossier.isResultatTraitement()));
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public void chargerDossiers(List<Professionnel> persList) {
    File file = new File("data/dossiers.txt");
    if (!file.exists()) return;

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                DossierMigration dossier = new DossierMigration();
                dossier.setNumDossier(Integer.parseInt(parts[0]));
                dossier.setDateDepot(parts[1].isEmpty() ? null : LocalDate.parse(parts[1]));
                dossier.setDateTraitement(parts[2].isEmpty() ? null : LocalDate.parse(parts[2]));
                Professionnel prof = persList.stream()
                        .filter(p -> p.getId().equals(parts[3]))
                        .findFirst()
                        .orElse(null);
                dossier.setProfessionnel(prof);
                dossier.setResultatTraitement(Boolean.parseBoolean(parts[4]));
                gestionDossier.getDossiers().add(dossier);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


}

