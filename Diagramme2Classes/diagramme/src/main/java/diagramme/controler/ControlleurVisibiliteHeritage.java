package diagramme.controler;

import classes.Interface;
import diagramme.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControlleurVisibiliteHeritage implements EventHandler<ActionEvent> {
    private Model model;
    private Interface classe;
    public ControlleurVisibiliteHeritage(Model model, Interface classe) {
        this.model = model;
        this.classe = classe;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        model.changerVisibiliteHeritage(classe);
    }
}
