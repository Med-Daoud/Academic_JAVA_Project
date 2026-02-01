package projet_java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import projet_java.package1.services.QuestionnaireService;
public class Projet_Java extends Application {
    @Override
public void start(Stage primaryStage) throws Exception {
   
    Parent root = FXMLLoader.load(getClass().getResource("/projet_java/package2/views/MenuFX.fxml"));
    primaryStage.setTitle("Menu Principal");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
}

   
    public static void main(String[] args) {
        launch(args);
    }
}
