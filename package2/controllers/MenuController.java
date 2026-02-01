package projet_java.package2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;

public class MenuController {

    @FXML
    private void btnMigrationClicked() throws IOException {
        loadWindow("/projet_java/package2/views/MigrationFX.fxml", "Gestion Migration");
    }

    @FXML
    private void btnQuestionnaireClicked() throws IOException {
        loadWindow("/projet_java/package1/views/GestionQuestionnaireFX.fxml", "Gestion Questionnaire");
    }

    @FXML
    private void btnPasserClicked() throws IOException {
        loadWindow("/projet_java/package1/views/PasserQuestionnaireFX.fxml", "Passer un Questionnaire");
    }
    @FXML
private void btnDossierClicked(ActionEvent event) throws IOException {
    loadWindow("/projet_java/package1/views/GestionDossierFX.fxml", "Gestion Dossier");
}


    private void loadWindow(String path, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
