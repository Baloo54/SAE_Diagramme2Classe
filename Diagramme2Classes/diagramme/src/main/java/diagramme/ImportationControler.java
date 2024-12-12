package diagramme;

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
    /**
     * Constructeur
     * @param model
     */
    public ImportationControler(Model model, Stage primaryStage) {
        this.model = model;
        this.primaryStage = primaryStage;
    }
    /**
     * handle
     * @param event
     * @return void
     */
    public void handle (ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("/home/nightmarev2/"));
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        model.ajouterPackage(selectedDirectory);
    }
}
