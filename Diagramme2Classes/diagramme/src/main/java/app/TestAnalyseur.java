package app;

import java.io.IOException;
import java.util.ArrayList;
import analyse.Analyseur;
import diagramme.Vues.*;
import classes.*;
import javafx.application.Application;
import javafx.scene.Scene;
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
            Interface classe = analyseur.analyserClasse("Diagramme2Classes/target/classes/diagramme/Vues/VuePrincipale.class");
            ArrayList<Interface> classeParent = classe.getInterfaces();
            for (Interface interface1 : classeParent) {
                System.out.println(interface1.getNom());
            }
            VueClasse vueClasse = new VueClasse(classe);
            vueClasse.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));

            // Création d'un Pane pour afficher le diagramme
            Pane p = new Pane();
            p.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
            p.getChildren().add(vueClasse);

  

            // Mise en page avec BorderPane
            BorderPane root = new BorderPane();
            root.setCenter(p);

            // Configuration de la scène
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Diagramme UML");
            primaryStage.setScene(scene);
            primaryStage.show();
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
