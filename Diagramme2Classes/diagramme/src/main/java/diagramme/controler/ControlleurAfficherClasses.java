package diagramme.controler;

import classes.Interface;
import diagramme.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControlleurAfficherClasses implements EventHandler<ActionEvent> {
    private Model model;
    private VBox sidebar;

    public ControlleurAfficherClasses(Model model, VBox sidebar) {
        this.model = model;
        this.sidebar = sidebar;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        updateSidebar();  // Remplir la sidebar à chaque clic
    }

    private void updateSidebar() {
        sidebar.getChildren().clear();  // Vider la sidebar
        for (Interface classe : model.getClasses()) {
            sidebar.getChildren().add(new Label(classe.getNom()));  // Ajouter chaque classe à la sidebar
        }
    }
}
