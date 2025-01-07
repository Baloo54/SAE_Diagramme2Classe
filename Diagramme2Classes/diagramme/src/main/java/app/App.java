package app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        primaryStage.setTitle("Générateur de diagrammes de classes");
        VBox diagramArea = new VBox();
        diagramArea.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgray;");
        diagramArea.setPrefSize(800, 400);

        Button fichierButton = new Button("Fichier");
        Button modifierButton = new Button("Modifier");
        Button exporterButton = new Button("Exporter");


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(fichierButton, modifierButton, exporterButton);


        BorderPane root = new BorderPane();
        root.setTop(null);
        root.setCenter(diagramArea);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 960, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
