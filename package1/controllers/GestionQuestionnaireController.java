package projet_java.package1.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import projet_java.package1.models.Question;
import projet_java.package1.services.QuestionnaireService;
import java.io.IOException;
import java.util.ArrayList;

public class GestionQuestionnaireController {
    
    @FXML private ListView<String> listQuestions;
    @FXML private ListView<String> listChoix;
    @FXML private TextField txtEnnonce;
    @FXML private TextField txtChoix;
    @FXML private TextField txtReponse;
    
    private QuestionnaireService questionnaireService = QuestionnaireService.getInstance();
    private ArrayList<String> choixTemp = new ArrayList<>();

    @FXML
    public void initialize() {
  

        rafraichirListe();
        
        listQuestions.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectionnerQuestion()
        );
    }

    private void rafraichirListe() {
        listQuestions.getItems().clear();
        for (Question q : questionnaireService.getToutesLesQuestions()) {
            listQuestions.getItems().add(q.getEnnonce());
        }
    }

    private void selectionnerQuestion() {
        int index = listQuestions.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Question q = questionnaireService.getQuestion(index);
            if (q != null) {
                txtEnnonce.setText(q.getEnnonce());
                txtReponse.setText(String.valueOf(q.getReponseCorrecte()));
                
                choixTemp.clear();
                listChoix.getItems().clear();
                choixTemp.addAll(q.getChoix());
                listChoix.getItems().addAll(q.getChoix());
            }
        }
    }
@FXML
    private void btnRetourClicked() throws Exception {
        loadWindow("/projet_java/package2/views/MenuFX.fxml", "Menu");
    }
    @FXML
    public void ajouterChoix() {
        String choix = txtChoix.getText().trim();
        if (!choix.isEmpty()) {
            choixTemp.add(choix);
            listChoix.getItems().add(choix);
            txtChoix.clear();
        }
    }

    @FXML
    public void ajouterQuestion() {
        try {
            String ennonce = txtEnnonce.getText().trim();
            if (ennonce.isEmpty()) {
                showAlert("Erreur", "Veuillez saisir un énoncé.");
                return;
            }
            
            if (choixTemp.size() < 2) {
                showAlert("Erreur", "Veuillez ajouter au moins 2 choix.");
                return;
            }
            
            int rep = Integer.parseInt(txtReponse.getText());
            if (rep < 0 || rep >= choixTemp.size()) {
                showAlert("Erreur", "L'index de réponse doit être entre 0 et " + (choixTemp.size() - 1));
                return;
            }
            
            Question q = new Question(ennonce, rep);
            q.setChoix(new ArrayList<>(choixTemp));
            questionnaireService.ajouterQuestion(q);
            
            // Réinitialiser
            reinitialiserFormulaire();
            rafraichirListe();
            showAlert("Succès", "Question ajoutée avec succès!");
            
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez saisir un nombre valide pour la réponse.");
        }
    }

    @FXML
    public void supprimerQuestion() {
        int index = listQuestions.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            questionnaireService.supprimerQuestion(index);
            reinitialiserFormulaire();
            rafraichirListe();
        } else {
            showAlert("Erreur", "Veuillez sélectionner une question à supprimer.");
        }
    }

    @FXML
    public void modifierQuestion() {
        int index = listQuestions.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showAlert("Erreur", "Veuillez sélectionner une question à modifier.");
            return;
        }
        
        try {
            String ennonce = txtEnnonce.getText().trim();
            if (ennonce.isEmpty()) {
                showAlert("Erreur", "Veuillez saisir un énoncé.");
                return;
            }
            
            int rep = Integer.parseInt(txtReponse.getText());
            if (rep < 0 || rep >= choixTemp.size()) {
                showAlert("Erreur", "L'index de réponse doit être entre 0 et " + (choixTemp.size() - 1));
                return;
            }
            
            Question newQ = new Question(ennonce, rep);
            newQ.setChoix(new ArrayList<>(choixTemp));
            questionnaireService.modifierQuestion(index, newQ);
            
            rafraichirListe();
            showAlert("Succès", "Question modifiée avec succès!");
            
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez saisir un nombre valide pour la réponse.");
        }
    }

    @FXML
    public void supprimerChoix() {
        int index = listChoix.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            choixTemp.remove(index);
            listChoix.getItems().remove(index);
        }
    }

    private void reinitialiserFormulaire() {
        txtEnnonce.clear();
        txtReponse.clear();
        txtChoix.clear();
        choixTemp.clear();
        listChoix.getItems().clear();
        listQuestions.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void ouvrirPasserQCM(ActionEvent event) {
        if (questionnaireService.getNombreQuestions() == 0) {
            showAlert("Erreur", "Veuillez ajouter des questions avant de passer le questionnaire.");
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/projet_java/package1/views/PasserQuestionnaireFX.fxml")
            );
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Passer Questionnaire");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir le questionnaire.");
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