package projet_java.package2.controllers;

import projet_java.package2.models.Pays;
import projet_java.package2.models.Professionnel;
import projet_java.package2.models.Profession;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import projet_java.package2.services.MigrationService;

import java.time.LocalDate;

public class MigrationController {

    // ----------------------- PAYS -----------------------
    @FXML private TextField tfPaysName;
    @FXML private TextField tfPaysCode;
    @FXML private ListView<Pays> lvPays;

    // ------------------- PERSONNEL ----------------------
    @FXML private TextField tfPersId;
    @FXML private TextField tfPersNom;
    @FXML private TextField tfPersPrenom;
    @FXML private TextField tfPersAdresse;
    @FXML private TextField tfPersTel;
    @FXML private DatePicker dpPersDateNaissance;
    @FXML private ComboBox<Pays> cbPersPays;
    @FXML private TextField tfPersProfession;
    @FXML private TextField tfPersEntreprise;
    @FXML private TextField tfPersSalaire;
    @FXML private ListView<Professionnel> lvPersonnel;

   
    private MigrationService migrationService;

    @FXML
    public void initialize() {
        migrationService = new MigrationService();

     
        lvPays.setItems(FXCollections.observableArrayList(migrationService.getAllPays()));
        lvPersonnel.setItems(FXCollections.observableArrayList(migrationService.getAllProfessionnels()));
        cbPersPays.setItems(FXCollections.observableArrayList(migrationService.getAllPays()));
    }

    // ------------------- GESTION PAYS -------------------
    @FXML
    private void ajouterPays() {
        String name = tfPaysName.getText();
        String code = tfPaysCode.getText();
        if (!name.isEmpty() && !code.isEmpty()) {
            Pays p = new Pays(name, code);
            migrationService.addPays(p);
            lvPays.getItems().setAll(migrationService.getAllPays());
            cbPersPays.getItems().setAll(migrationService.getAllPays());
            tfPaysName.clear();
            tfPaysCode.clear();
        }
    }
@FXML
    private void btnRetourClicked() throws Exception {
        loadWindow("/projet_java/package2/views/MenuFX.fxml", "Menu");
    }

    @FXML
    private void supprimerPays() {
        Pays selected = lvPays.getSelectionModel().getSelectedItem();
        if (selected != null) {
            migrationService.removePays(selected);
            lvPays.getItems().setAll(migrationService.getAllPays());
            cbPersPays.getItems().setAll(migrationService.getAllPays());
        }
    }

    // ----------------- GESTION PERSONNEL ----------------
    @FXML
    private void ajouterPersonnel() {
        try {
            Professionnel p = new Professionnel(
                    tfPersId.getText(),
                    tfPersNom.getText(),
                    tfPersPrenom.getText(),
                    tfPersAdresse.getText(),
                    tfPersTel.getText(),
                    dpPersDateNaissance.getValue(),
                    cbPersPays.getValue(),
                    new Profession(tfPersProfession.getText(), LocalDate.now(), "N/A", 0), // exemple
                    tfPersEntreprise.getText(),
                    Double.parseDouble(tfPersSalaire.getText())
            );

            migrationService.addProfessionnel(p);
            lvPersonnel.getItems().setAll(migrationService.getAllProfessionnels());

            
            tfPersId.clear();
            tfPersNom.clear();
            tfPersPrenom.clear();
            tfPersAdresse.clear();
            tfPersTel.clear();
            dpPersDateNaissance.setValue(null);
            cbPersPays.getSelectionModel().clearSelection();
            tfPersProfession.clear();
            tfPersEntreprise.clear();
            tfPersSalaire.clear();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du personnel : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void supprimerPersonnel() {
        Professionnel selected = lvPersonnel.getSelectionModel().getSelectedItem();
        if (selected != null) {
            migrationService.removeProfessionnelById(selected.getId());
            lvPersonnel.getItems().setAll(migrationService.getAllProfessionnels());
        }
    }

   private void loadWindow(String path, String title) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(path));
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.setTitle(title);
    stage.show();
}

}
