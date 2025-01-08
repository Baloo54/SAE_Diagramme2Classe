package app;

import diagramme.ImportationControler;
import diagramme.Model;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
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

        ContextMenu fichierMenu = new ContextMenu(
                new MenuItem("Choisir un fichier") {{
                    setOnAction(event -> {
                        Model model = new Model();
                        ImportationControler importationControler = new ImportationControler(model, primaryStage);
                        importationControler.handle(event);});
                }},
                new MenuItem("Annuler")
        );

        ContextMenu modifierMenu = new ContextMenu(
                new MenuItem("Ajouter un élément"),
                new MenuItem("Afficher"),
                new MenuItem("Masquer"),
                new MenuItem("Annuler")
        );

        ContextMenu exporterMenu = new ContextMenu(
                new MenuItem(".PDF"),
                new MenuItem(".PNG"),
                new MenuItem(".PUML"),
                new MenuItem("Annuler")
        );


        fichierButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            fichierMenu.show(fichierButton, event.getScreenX(), event.getScreenY());
        });

        modifierButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            modifierMenu.show(modifierButton, event.getScreenX(), event.getScreenY());
        });

        exporterButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            exporterMenu.show(exporterButton, event.getScreenX(), event.getScreenY());
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(fichierButton, modifierButton, exporterButton);

        BorderPane root = new BorderPane();
        root.setCenter(diagramArea);
        root.setBottom(buttonBox);


        Scene scene = new Scene(root, 960, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
