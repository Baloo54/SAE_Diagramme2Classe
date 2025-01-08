package app;

import diagramme.Model;
import diagramme.Vues.VuePrincipale;
import diagramme.controler.ImportationControler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * Classe principale qui exécute l'application.
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //récupération des dimensions de l'écran de l'utilisateur
        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();
        primaryStage.setTitle("Générateur de diagrammes de classes");
        VBox diagramArea = new VBox();
        diagramArea.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgray;");
        //model
        Model model = new Model();
        //controler
        ImportationControler importationControler = new ImportationControler(model,primaryStage);
        //vue
        VuePrincipale vuePrincipale = new VuePrincipale();
        model.ajouterObservateur(vuePrincipale);
        diagramArea.getChildren().add(vuePrincipale);
        //boutons
        Button fichierButton = new Button("Fichier");
        fichierButton.setOnAction(importationControler);


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(fichierButton);


        BorderPane root = new BorderPane();
        root.setTop(null);
        root.setCenter(diagramArea);
        root.setBottom(buttonBox);

        
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
