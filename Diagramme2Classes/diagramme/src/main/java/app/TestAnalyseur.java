package app;

import java.io.IOException;
import analyse.Analyseur;
import diagramme.Vues.*;
import classes.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestAnalyseur extends Application{
    public void start(Stage primaryStage){
        Analyseur analyseur = Analyseur.getInstance();
        try {
            Classe classe = analyseur.analyserClasse("/home/nightmarev2/Documents/SAE_Diagramme2Classe/Diagramme2Classes/target/classes/classes/Interface.class");
            VueClasse vueClasse = new VueClasse(classe);
            vueClasse.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
            Pane p = new Pane();
            p.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
            p.getChildren().add(vueClasse);
            Scene scene = new Scene(p);
            primaryStage.setTitle("Diagramme UML");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (ClassNotFoundException e) {
           System.out.println(e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void main (String[] args) {
        launch(args);
    }
}