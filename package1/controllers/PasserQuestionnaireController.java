package projet_java.package1.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import projet_java.package1.models.Question;
import projet_java.package1.services.QuestionnaireService;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projet_java.package1.models.DossierMigration;

public class PasserQuestionnaireController {
    
    @FXML private Label lblEnnonce;
    @FXML private VBox boxChoix;
    
    private QuestionnaireService questionnaireService = QuestionnaireService.getInstance();
    private ArrayList<Integer> reponsesUtilisateur = new ArrayList<>();
    private int index = 0;

    @FXML
    public void initialize() {
         if (questionnaireService.getNombreQuestions() == 0) {
            showAlert("Erreur", "Veuillez ajouter des questions avant de passer le questionnaire.");
            return;
        }

        System.out.println("=== INITIALISATION QUESTIONNAIRE ===");
        System.out.println("Nombre de questions: " + questionnaireService.getNombreQuestions());
        
        for (int i = 0; i < questionnaireService.getNombreQuestions(); i++) {
            reponsesUtilisateur.add(-1);
        }
        
        afficherQuestion();
    }

    private void afficherQuestion() {
        System.out.println("Affichage question " + index);
        
        if (questionnaireService.getNombreQuestions() == 0) {
            lblEnnonce.setText("Aucune question disponible");
            boxChoix.getChildren().clear();
            return;
        }
        
        if (index >= questionnaireService.getNombreQuestions()) {
            afficherResultats();
            return;
        }
        
        Question q = questionnaireService.getQuestion(index);
        if (q != null) {
            lblEnnonce.setText("Question " + (index + 1) + ": " + q.getEnnonce());
            boxChoix.getChildren().clear();
            
            ToggleGroup groupChoix = new ToggleGroup();
            
            for (int i = 0; i < q.getChoix().size(); i++) {
                String choixText = q.getChoix().get(i);
                RadioButton rb = new RadioButton(choixText);
                rb.setToggleGroup(groupChoix);
                
                final int choixIndex = i;
                
               
                if (reponsesUtilisateur.get(index) == choixIndex) {
                    rb.setSelected(true);
                }
                
                rb.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        reponsesUtilisateur.set(index, choixIndex);
                        System.out.println("Question " + (index + 1) + " - Réponse enregistrée: " + choixIndex);
                    }
                });
                
                boxChoix.getChildren().add(rb);
            }
        }
    }
@FXML
    private void btnRetourClicked() throws Exception {
        loadWindow("/projet_java/package2/views/MenuFX.fxml", "Menu");
    }
    @FXML
    public void suivant() {
        System.out.println("Bouton Suivant cliqué");
        if (index < questionnaireService.getNombreQuestions() - 1) {
            index++;
            afficherQuestion();
        } else {
            afficherResultats();
        }
    }
    
    @FXML
    public void precedent() {
        System.out.println("Bouton Précédent cliqué");
        if (index > 0) {
            index--;
            afficherQuestion();
        }
    }
    
    @FXML
    public void terminer() {
        System.out.println("=== BOUTON TERMINER CLIQUE ===");
        System.out.println("Réponses enregistrées: " + reponsesUtilisateur);
        afficherResultats();
       // sauvegarderResultatsDansFichier();
    }

    private void afficherResultats() {
        System.out.println("=== AFFICHAGE DES RESULTATS ===");
        
        if (questionnaireService.getNombreQuestions() == 0) {
            System.out.println("Aucune question disponible");
            return;
        }
        
        int score = 0;
        int total = questionnaireService.getNombreQuestions();
        
      
        for (int i = 0; i < total; i++) {
            int reponseUser = reponsesUtilisateur.get(i);
            int reponseCorrecte = (int) questionnaireService.getQuestion(i).getReponseCorrecte();
            
            System.out.println("Question " + i + " - User: " + reponseUser + " Correcte: " + reponseCorrecte);
            
            if (reponseUser == reponseCorrecte) {
                score++;
            }
        }
        
        System.out.println("Score calculé: " + score + "/" + total);
        
       
        StringBuilder message = new StringBuilder();
        message.append("QUESTIONNAIRE TERMINÉ!\n\n");
        message.append("Votre score: ").append(score).append("/").append(total).append("\n\n");
        message.append("DÉTAIL DES RÉPONSES:\n\n");
        
        for (int i = 0; i < total; i++) {
            Question q = questionnaireService.getQuestion(i);
            int reponseUser = reponsesUtilisateur.get(i);
            int reponseCorrecte = (int) q.getReponseCorrecte();
            
            message.append("Question ").append(i + 1).append(": ").append(q.getEnnonce()).append("\n");
            
            if (reponseUser == -1) {
                message.append("❌ NON RÉPONDU\n");
            } else if (reponseUser == reponseCorrecte) {
                message.append("✅ CORRECT\n");
            } else {
                message.append("❌ INCORRECT\n");
            }
            
            message.append("Votre réponse: ");
            if (reponseUser == -1) {
                message.append("Aucune\n");
            } else {
                message.append(q.getChoix().get(reponseUser)).append("\n");
            }
            
            message.append("Bonne réponse: ").append(q.getChoix().get(reponseCorrecte)).append("\n");
            message.append("────────────────────────────\n");
        }
        
        message.append("\nListe numérique des réponses: ").append(reponsesUtilisateur);
        

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Résultats du Questionnaire");
        alert.setHeaderText("Questionnaire terminé !");
        alert.setContentText(message.toString());
        
        System.out.println("Affichage de l'alerte...");
        alert.showAndWait();
        System.out.println("Alerte fermée");
        
       
        lblEnnonce.setText("Questionnaire terminé !\nScore: " + score + "/" + total);
        boxChoix.getChildren().clear();
        
        Label scoreLabel = new Label("Score final: " + score + "/" + total);
        scoreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2E8B57;");
        
        Label merciLabel = new Label("Merci d'avoir participé !");
        merciLabel.setStyle("-fx-font-size: 16px;");
        
        boxChoix.getChildren().addAll(scoreLabel, merciLabel);
        
        System.out.println("=== RÉSULTATS FINAUX ===");
        System.out.println("Score: " + score + "/" + total);
        System.out.println("Liste des réponses: " + reponsesUtilisateur);
    }
       private void loadWindow(String path, String title) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource(path));
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.setTitle(title);
    stage.show();
}

    private void showAlert(String erreur, String veuillez_ajouter_des_questions_avant_de_p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}