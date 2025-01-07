package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import diagramme.*;

/**
 * classe principale 
 * qui run l'application
 */
public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        //titre de l'application
        primaryStage.setTitle("Diagramme");
        //le modèle
        Model model = new Model();
        //les différents controleurs
        ImportationControler importationControler = new ImportationControler(model, primaryStage);
      
        //les différents boutton
        Button button = new Button("Select Package");
        button.setOnAction(importationControler);

        VBox vBox = new VBox(button);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

