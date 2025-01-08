package diagramme.controler;

import classes.*;
import classes.Classe;
import classes.Interface;
import diagramme.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.List;

public class ExportationControler implements EventHandler<ActionEvent> {
    private final Model model;

    public ExportationControler(Model model) {
        this.model = model;
    }

    @Override
    public void handle(ActionEvent event) {
        // Liste pour collecter toutes les classes
        List<Classe> classes = new ArrayList<>();

        // Récupère toutes les classes du modèle
        for (Interface inter : model.getClasses()) {
            if (inter instanceof Classe) {
                Classe classeAnalysee = (Classe) inter;
                classes.add(classeAnalysee);
            }
        }

        // Exporte toutes les classes dans un seul fichier PlantUML
        if (!classes.isEmpty()) {
            // Appel à la classe Export pour générer le diagramme en Puml
            Export export = new Export(); // Création d'une instance de Export
            String fileName = export.exportPuml(classes); // Appel à la méthode pour exporter toutes les classes
            System.out.println("Exportation terminée : " + fileName);
        } else {
            System.out.println("Aucune classe à exporter.");
        }
    }
}