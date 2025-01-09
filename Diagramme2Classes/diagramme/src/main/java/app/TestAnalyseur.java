package app;

import java.io.IOException;

import analyse.Analyseur;
import classes.Interface;
import diagramme.Vues.VueClasse;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestAnalyseur extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Initialisation de l'analyseur
        Analyseur analyseur = Analyseur.getInstance();
        BorderPane root = new BorderPane();

        try {
            // Analyse de la classe et création de la vue
            Interface classe = analyseur.analyserClasse("out/production/SAE_Diagramme2Classe/diagramme/Model.class");
            VueClasse vueClasse = new VueClasse(classe);
            vueClasse.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));

            // Création d'un Pane pour afficher le diagramme
            Pane diagramPane = new Pane();
            diagramPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
            diagramPane.getChildren().add(vueClasse);

            // Placement du diagramme au centre
            root.setCenter(diagramPane);

            // Configuration de la scène
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Diagramme UML");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Affichage des résultats d'analyse dans la console
            analyseur.afficherResultats(classe);

        } catch (ClassNotFoundException e) {
            // Gestion des erreurs pour la classe introuvable
            showError("Erreur d'analyse", "La classe spécifiée est introuvable.", e);
        } catch (IOException e) {
            // Gestion des erreurs pour les problèmes d'entrée/sortie
            showError("Erreur d'entrée/sortie", "Une erreur est survenue lors de la lecture du fichier.", e);
        }
    }

    /**
     * Affiche une alerte d'erreur avec des informations détaillées.
     *
     * @param title   Titre de l'alerte.
     * @param message Message principal de l'alerte.
     * @param e       Exception à afficher (détails dans la console).
     */
    private void showError(String title, String message, Exception e) {
        // Afficher l'exception dans la console pour débogage
        e.printStackTrace();

        // Affichage d'une boîte de dialogue d'alerte
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
