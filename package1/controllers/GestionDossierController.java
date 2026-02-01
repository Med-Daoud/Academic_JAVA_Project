package projet_java.package1.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projet_java.package1.models.DossierMigration;
import projet_java.package1.services.GestionDossierService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projet_java.package2.models.Professionnel;
import projet_java.package2.services.PersonnelService;

public class GestionDossierController {

    @FXML private TableView<DossierMigration> tableDossiers;
    @FXML private TableColumn<DossierMigration, Integer> colNum;
    @FXML private TableColumn<DossierMigration, String> colDateDepot;
    @FXML private TableColumn<DossierMigration, String> colDateTraitement;
    @FXML private TableColumn<DossierMigration, String> colProfessionnel;
    @FXML private TableColumn<DossierMigration, Boolean> colResultat;
    @FXML private ComboBox<Professionnel> cbProfessionnel;
    @FXML private TextField txtNumDossier;
    @FXML private DatePicker dpDateDepot;
    @FXML private DatePicker dpDateTraitement;
    @FXML private CheckBox chkResultat;

    @FXML private Button btnAjouter;
    @FXML private Button btnSupprimer;
    @FXML private Button btnActualiser;

    private GestionDossierService service;

    @FXML
    public void initialize() {
        PersonnelService personnelService = new PersonnelService();
        List<Professionnel> persList = personnelService.chargerProfessionnels();
        cbProfessionnel.setItems(FXCollections.observableArrayList(persList));

        service = new GestionDossierService();
        service.chargerDossiers(persList);

        refreshTable();

        colNum.setCellValueFactory(new PropertyValueFactory<>("numDossier"));
        colDateDepot.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDateDepot() != null ? cell.getValue().getDateDepot().toString() : ""));
        colDateTraitement.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDateTraitement() != null ? cell.getValue().getDateTraitement().toString() : ""));
        colProfessionnel.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getProfessionnel() != null ?
                        cell.getValue().getProfessionnel().getNom() : "N/A"));
        colResultat.setCellValueFactory(new PropertyValueFactory<>("resultatTraitement"));

       
                
       List<Professionnel> persFromFile = chargerProfessionnelsDepuisFichier();
    ObservableList<Professionnel> professionnels = FXCollections.observableArrayList(persFromFile);
    cbProfessionnel.setItems(professionnels);
        refreshTable();

        
        btnAjouter.setOnAction(e -> ajouterDossier());
        btnSupprimer.setOnAction(e -> supprimerDossier());
        btnActualiser.setOnAction(e -> refreshTable());
    }

    private void refreshTable() {
        ObservableList<DossierMigration> data =
                FXCollections.observableArrayList(service.getTousLesDossiers());
        tableDossiers.setItems(data);
    }
@FXML
    private void btnRetourClicked() throws Exception {
        loadWindow("/projet_java/package2/views/MenuFX.fxml", "Menu");
    }
    
    private void ajouterDossier() {
        try {
            int num = Integer.parseInt(txtNumDossier.getText());
            LocalDate depot = dpDateDepot.getValue();
            LocalDate traitement = dpDateTraitement.getValue();
            boolean resultat = chkResultat.isSelected();
            Professionnel prof = cbProfessionnel.getValue();

            if (prof == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Sélectionnez un professionnel !");
                alert.showAndWait();
                return;
            }

            DossierMigration dossier = new DossierMigration();
            dossier.setNumDossier(num);
            dossier.setDateDepot(depot);
            dossier.setDateTraitement(traitement);
            dossier.setResultatTraitement(resultat);
            dossier.setProfessionnel(prof); // ✅ set selected professional

            service.ajouterDossier(dossier);
            refreshTable();
            clearForm();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Numéro de dossier invalide !");
            alert.showAndWait();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    private void supprimerDossier() {
        DossierMigration selected = tableDossiers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.supprimerDossier(selected);
            refreshTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Sélectionnez un dossier à supprimer !");
            alert.showAndWait();
        }
    }

    private void clearForm() {
        txtNumDossier.clear();
        dpDateDepot.setValue(null);
        dpDateTraitement.setValue(null);
        chkResultat.setSelected(false);
        cbProfessionnel.getSelectionModel().clearSelection(); // ✅ clear ComboBox
    }
       private void loadWindow(String path, String title) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(path));
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.setTitle(title);
    stage.show();
}
       private List<Professionnel> chargerProfessionnelsDepuisFichier() {
    List<Professionnel> persList = new ArrayList<>();
    File file = new File("data/personnel.txt"); 
    if (!file.exists()) {
        System.out.println("Fichier introuvable : " + file.getAbsolutePath());
        return persList;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length >= 10) {
                Professionnel p = new Professionnel(
                        parts[0],                
                        parts[1],               
                        parts[2],                
                        parts[3],                
                        parts[4],               
                        parts[5].isEmpty() ? null : LocalDate.parse(parts[5]), 
                        null,      
                        null,                    
                        parts[8],               
                        Double.parseDouble(parts[9]) 
                );
                persList.add(p);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return persList;
}
}
