package diagramme.controler;

import classes.Interface;
import classes.Methode;
import diagramme.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControlleurVisibiliteMethode implements EventHandler<ActionEvent> {
    private Model model;
    private Interface classe;
    private Methode methode;
    public ControlleurVisibiliteMethode(Model model, Interface classe, Methode m) {
        this.model = model;
        this.classe = classe;
        this.methode = m;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        model.changerVisibiliteMethode(classe, methode);
    }
}
