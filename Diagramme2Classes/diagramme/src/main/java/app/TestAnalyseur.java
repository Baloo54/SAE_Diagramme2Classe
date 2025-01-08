package app;

import java.io.IOException;
import analyse.Analyseur;
import diagramme.Vues.*;
import classes.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestAnalyseur extends Application {
    public void start(Stage primaryStage) {
        Analyseur analyseur = Analyseur.getInstance();
        try {
            // Analyse de la classe et création de la vue
            Interface classe = analyseur.analyserClasse("out/production/SAE_Diagramme2Classe/diagramme/Model.class");
            VueClasse vueClasse = new VueClasse(classe);
            vueClasse.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));

            // Création d'un Pane pour afficher le diagramme
            Pane p = new Pane();
            p.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
            p.getChildren().add(vueClasse);

            // Bouton pour exporter le diagramme en PNG
            Button exportButton = new Button("Exporter en PNG");
            exportButton.setOnAction(event -> {
                analyseur.exporterDiagrammeEnPNG(p, "diagramme.png");
                System.out.println("Diagramme exporté avec succès !");
            });

            // Mise en page avec BorderPane
            BorderPane root = new BorderPane();
            root.setCenter(p);
            root.setBottom(exportButton);

            // Configuration de la scène
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Diagramme UML");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Affichage des résultats et exportation PlantUML
            analyseur.afficherResultats(classe);
            String puml = analyseur.exportPuml((Classe) classe);
            System.out.println(puml);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
