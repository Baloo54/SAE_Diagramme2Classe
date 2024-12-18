package diagramme.diagramme;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ImportationControler implements EventHandler<ActionEvent> {
    /**
     * attribut de la classe
     */
    private Model model;
    private Stage primaryStage;
    String userHome;
    /**
     * Constructeur
     * @param model
     */
    public ImportationControler(Model model, Stage primaryStage) {
        this.model = model;
        this.primaryStage = primaryStage;
        //répertoir principale de l'utilisateur 
        this.userHome = System.getProperty("user.home");
    }
    /**
     * permet de récupérer le package de l'utilisateur
     * @param event
     * @return void
     */
    public void handle (ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(this.userHome));
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        model.ajouterPackage(selectedDirectory);
    }
}
