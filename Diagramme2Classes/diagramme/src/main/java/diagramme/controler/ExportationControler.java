package diagramme.controler;

import classes.Classe;
import classes.Interface;
import diagramme.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import classes.Export;
import java.util.ArrayList;
import java.util.List;

public class ExportationControler implements EventHandler<ActionEvent> {
    private final Model model;
    private String exportType = "puml"; // Type d'export par défaut

    public ExportationControler(Model model) {
        this.model = model;
    }

    /**
     * Met à jour le type d'export.
     *
     * @param exportType Type d'export souhaité (par exemple, "puml", "jpeg").
     */
    public void setExportType(String exportType) {
        this.exportType = exportType;
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

        if (!classes.isEmpty()) {
            Export export = new Export();
            Pane diagrammePane = model.getDiagrammePane();  // Récupère le Pane contenant le diagramme

            switch (exportType.toLowerCase()) {
                case "puml":
                    String pumlFile = export.exportPuml(classes);
                    System.out.println("Exportation en PUML terminée : " + pumlFile);
                    break;

                case "png":
                    if (diagrammePane != null) {
                        export.exportPng(diagrammePane);
                        System.out.println("Exportation en PNG terminée.");
                    } else {
                        System.err.println("Erreur : le diagramme est vide.");
                    }
                    break;

                default:
                    System.err.println("Erreur : Type d'export non pris en charge.");
            }
        } else {
            System.out.println("Aucune classe à exporter.");
        }
    }
}
