package projet_java.package2.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import projet_java.package2.models.Migration;
import projet_java.package2.models.Pays;
import projet_java.package2.models.Professionnel;

import java.util.List;
import java.util.Optional;

public class MigrationService {

    private Migration migration;
     private final String FILE_PAYS = "data/pays.txt";
    private final String FILE_PERSONNEL = "data/personnel.txt";

    public MigrationService() {
        this.migration = new Migration();
         chargerPays();
        chargerPersonnel();
    }

    public MigrationService(Migration migration) {
        this.migration = migration;
    }

    
    public void addPays(Pays pays) {
        List<Pays> paysList = migration.getPaysList();
        if (paysList.stream().noneMatch(p -> p.equals(pays))) {
            paysList = new java.util.ArrayList<>(paysList); 
            paysList.add(pays);
            migration.setPaysList(paysList);
             sauvegarderPays();
            System.out.println("Pays ajouté avec succès !");
        } else {
            System.out.println("Ce pays existe déjà !");
        }
    }

    
    public boolean removePays(Pays pays) {
        List<Pays> paysList = new java.util.ArrayList<>(migration.getPaysList());
        boolean removed = paysList.removeIf(p -> p.equals(pays));
        migration.setPaysList(paysList);
         sauvegarderPays();
        if (removed) {
            System.out.println("Pays supprimé avec succès !");
        } else {
            System.out.println("Ce pays n'existe pas !");
        }
        return removed;
    }

    
    public boolean containsPays(Pays pays) {
        return migration.getPaysList().stream()
                .anyMatch(p -> p.equals(pays));
    }

   
    public List<Pays> getAllPays() {
        return migration.getPaysList();
    }

    
    public void addProfessionnel(Professionnel p) {
        List<Professionnel> list = new java.util.ArrayList<>(migration.getProfessionnelsList());
        list.add(p);
        migration.setProfessionnelsList(list);
        sauvegarderPersonnel();
        System.out.println("Professionnel ajouté !");
    }

    
    public boolean removeProfessionnelById(String id) {
        List<Professionnel> list = new java.util.ArrayList<>(migration.getProfessionnelsList());
        boolean removed = list.removeIf(p -> p.getId().equals(id));
        migration.setProfessionnelsList(list);
        sauvegarderPersonnel();
        if (removed) {
            System.out.println("Professionnel supprimé !");
        } else {
            System.out.println("Professionnel avec ID " + id + " introuvable.");
        }
        return removed;
    }

    
    public Optional<Professionnel> getProfessionnelById(String id) {
        return migration.getProfessionnelsList()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

  
    public List<Professionnel> getAllProfessionnels() {
        return migration.getProfessionnelsList();
    }

    public void afficherMigration() {
        System.out.println(migration);
    }

    private void sauvegarderPays() {
        try {
            File file = new File(FILE_PAYS);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Pays p : migration.getPaysList()) {
                    writer.write(p.name() + "|" + p.codeIso());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargerPays() {
        migration.setPaysList(new ArrayList<>());
        File file = new File(FILE_PAYS);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    Pays p = new Pays(parts[0], parts[1]);
                    List<Pays> temp = new ArrayList<>(migration.getPaysList());
                    temp.add(p);
                    migration.setPaysList(temp);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sauvegarderPersonnel() {
        try {
            File file = new File(FILE_PERSONNEL);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Professionnel p : migration.getProfessionnelsList()) {
                    writer.write(
                            p.getId() + "|" +
                            p.getNom() + "|" +
                            p.getPrenom() + "|" +
                            p.getAdresse() + "|" +
                            p.getNumTel() + "|" +
                            (p.getDateNaissance() != null ? p.getDateNaissance() : "") + "|" +
                            (p.getPays() != null ? p.getPays().codeIso() : "") + "|" +
                            (p.getProfession() != null ? p.getProfession().getNom() : "") + "|" +
                            p.getEntreprise() + "|" +
                            p.getSalaire()
                    );
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargerPersonnel() {
         List<Professionnel> persList = new ArrayList<>();
        File file = new File(FILE_PERSONNEL);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 10) {
                    Professionnel p = new Professionnel(
                            parts[0], 
                            parts[1], 
                            parts[2], 
                            parts[3], 
                            parts[4], 
                            parts[5].isEmpty() ? null : LocalDate.parse(parts[5]), 
                            migration.getPaysList().stream().filter(pa -> pa.codeIso().equals(parts[6])).findFirst().orElse(null), 
                            new projet_java.package2.models.Profession(parts[7], LocalDate.now(), "N/A", 0), 
                            parts[8], 
                            Double.parseDouble(parts[9]) 
                    );
                    
                    persList.add(p);
                    
                }
            }
             migration.setProfessionnelsList(persList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
