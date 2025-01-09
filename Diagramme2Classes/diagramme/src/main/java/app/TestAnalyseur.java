package app;

import diagramme.Model;
import diagramme.Vues.VuePrincipale;
import diagramme.controler.ModificationControler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Classe principale pour exécuter et tester l'application.
 */
public class TestAnalyseur extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        double height = Screen.getPrimary().getBounds().getHeight();
        double width = Screen.getPrimary().getBounds().getWidth();
        primaryStage.setTitle("Test Générateur de Diagrammes de Classes");

        VBox diagramArea = new VBox();
        diagramArea.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: lightgray;");

        // Modèle
        Model model = new Model();

        // Vue
        VuePrincipale principal = new VuePrincipale();
        model.ajouterObservateur(principal);
        diagramArea.getChildren().add(principal);

        // Menu principal
        MenuBar menuBar = new MenuBar();

        // Menu Modifier
        Menu editMenu = new Menu("Modifier");
        MenuItem ajoutClasse = new MenuItem("Ajouter une classe");
        MenuItem ajoutMethode = new MenuItem("Ajouter une méthode");
        MenuItem ajoutAttribut = new MenuItem("Ajouter un attribut");

        // Ajouter des contrôleurs pour les actions
        ajoutClasse.setOnAction(new ModificationControler(model, "ajoutClasse"));
        ajoutMethode.setOnAction(new ModificationControler(model, "ajoutMethode"));
        ajoutAttribut.setOnAction(new ModificationControler(model, "ajoutAttribut"));

        editMenu.getItems().addAll(ajoutClasse, ajoutMethode, ajoutAttribut);
        menuBar.getMenus().add(editMenu);

        // Layout principal
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(diagramArea);

        // Scène principale
        Scene scene = new Scene(root, width * 0.8, height * 0.8);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
