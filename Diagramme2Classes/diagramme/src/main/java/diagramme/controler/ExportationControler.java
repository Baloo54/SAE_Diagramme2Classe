package diagramme.controler;

import analyse.Analyseur;
import classes.Classe;
import classes.Interface;
import diagramme.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExportationControler implements EventHandler<ActionEvent> {
    private final Model model;

    public ExportationControler(Model model) {
        this.model = model;
    }
    @Override
    public void handle(ActionEvent event) {
        Analyseur analyseur = Analyseur.getInstance();
        for (Interface classe : model.getClasses()) {
            if (classe instanceof Classe) {
                Classe classeAnalysee = (Classe) classe;
                String fileName = analyseur.exportPuml(classeAnalysee);
                System.out.println("Exportation terminée : " + fileName);
            }
        }
    }
}