package diagramme.controler;

import classes.Attribut;
import classes.Methode;
import diagramme.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Contrôleur pour gérer les modifications dans le modèle.
 * Permet d'ajouter des classes, des méthodes et des attributs via des fenêtres.
 */
public class ModificationControler implements EventHandler<ActionEvent> {
    private final Model model;
    private final String actionType;

    /**
     * Constructeur du contrôleur de modification.
     *
     * @param model      Le modèle à modifier.
     * @param actionType Le type d'action à réaliser (ajoutClasse, ajoutMethode, ajoutAttribut).
     */
    public ModificationControler(Model model, String actionType) {
        this.model = model;
        this.actionType = actionType;
    }

    /**
     * Gère les événements liés aux actions de modification.
     *
     * @param event L'événement déclenché.
     */
    @Override
    public void handle(ActionEvent event) {
        switch (actionType) {
            case "ajoutClasse":
                ouvrirFenetreClasse();
                break;
            case "ajoutMethode":
                ouvrirFenetreMethode();
                break;
            case "ajoutAttribut":
                ouvrirFenetreAttribut();
                break;
            default:
                System.out.println("Action inconnue");
        }
    }

    /**
     * Ouvre une fenêtre pour ajouter une classe ou une interface.
     */
    private void ouvrirFenetreClasse() {
        Stage stage = new Stage();
        stage.setTitle("Ajouter une classe");

        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);

        TextField textFieldNom = new TextField();
        CheckBox checkBoxInterface = new CheckBox("Est une interface");
        Button btnAjouter = new Button("Ajouter");

        btnAjouter.setOnAction(e -> {
            String nom = textFieldNom.getText();
            boolean estInterface = checkBoxInterface.isSelected();
            model.ajouterClasse(nom, estInterface);
            stage.close();
        });

        pane.add(new Label("Nom de la classe :"), 0, 0);
        pane.add(textFieldNom, 1, 0);
        pane.add(checkBoxInterface, 1, 1);
        pane.add(btnAjouter, 1, 2);

        stage.setScene(new Scene(pane, 300, 200));
        stage.show();
    }

    /**
     * Ouvre une fenêtre pour ajouter une méthode à une classe.
     */
    private void ouvrirFenetreMethode() {
        Stage stage = new Stage();
        stage.setTitle("Ajouter une méthode");

        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);

        ComboBox<String> comboBoxClasse = new ComboBox<>();
        comboBoxClasse.getItems().addAll(model.getClassesNoms());
        TextField textFieldNom = new TextField();
        ComboBox<String> comboBoxVisibilite = new ComboBox<>();
        comboBoxVisibilite.getItems().addAll("Public", "Privé", "Protégé");

        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> {
            String nomClasse = comboBoxClasse.getValue();
            String nomMethode = textFieldNom.getText();
            ArrayList<String> modificateurs = new ArrayList<>();
            modificateurs.add(comboBoxVisibilite.getValue().toLowerCase());
            model.ajouterMethode(nomClasse, new Methode(nomMethode, "void", new ArrayList<>(), modificateurs));
            stage.close();
        });

        pane.add(new Label("Classe :"), 0, 0);
        pane.add(comboBoxClasse, 1, 0);
        pane.add(new Label("Nom de la méthode :"), 0, 1);
        pane.add(textFieldNom, 1, 1);
        pane.add(new Label("Visibilité :"), 0, 2);
        pane.add(comboBoxVisibilite, 1, 2);
        pane.add(btnAjouter, 1, 3);

        stage.setScene(new Scene(pane, 300, 250));
        stage.show();
    }

    /**
     * Ouvre une fenêtre pour ajouter un attribut à une classe.
     */
    private void ouvrirFenetreAttribut() {
        Stage stage = new Stage();
        stage.setTitle("Ajouter un attribut");

        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);

        ComboBox<String> comboBoxClasse = new ComboBox<>();
        comboBoxClasse.getItems().addAll(model.getClassesNoms());
        TextField textFieldNom = new TextField();
        TextField textFieldType = new TextField();
        ComboBox<String> comboBoxVisibilite = new ComboBox<>();
        comboBoxVisibilite.getItems().addAll("Public", "Privé", "Protégé");

        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> {
            String nomClasse = comboBoxClasse.getValue();
            String nomAttribut = textFieldNom.getText();
            String type = textFieldType.getText();
            model.ajouterAttribut(nomClasse, new Attribut(type, nomAttribut));
            stage.close();
        });

        pane.add(new Label("Classe :"), 0, 0);
        pane.add(comboBoxClasse, 1, 0);
        pane.add(new Label("Nom de l'attribut :"), 0, 1);
        pane.add(textFieldNom, 1, 1);
        pane.add(new Label("Type :"), 0, 2);
        pane.add(textFieldType, 1, 2);
        pane.add(new Label("Visibilité :"), 0, 3);
        pane.add(comboBoxVisibilite, 1, 3);
        pane.add(btnAjouter, 1, 4);

        stage.setScene(new Scene(pane, 300, 300));
        stage.show();
    }
}
