package diagramme.controler;

import classes.Interface;
import diagramme.Model;
import diagramme.Observateur;
import diagramme.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class ControlleurAfficherClasses implements EventHandler<ActionEvent>, Observateur {
    private Model model;
    private VBox sidebar;
    boolean visible = false;
    private VBox methodes;


    public ControlleurAfficherClasses(Model model, VBox sidebar,VBox methodes) {
        this.model = model;
        this.sidebar = sidebar;
        this.methodes = methodes;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        visible=!visible;
        updateSidebar(); // Remplir la sidebar à chaque clic
    }

    private void updateSidebar() {

        // Créer un Menu pour les classes

        if (visible) {
            sidebar.getChildren().clear(); // Vider la sidebar
        // Ajouter chaque classe comme MenuItem
        for (Interface classe : model.getClasses()) {
            Menu classMenu = new Menu(classe.getNom());
            if (classe.getVisible()) {
                {
                    MenuItem classItem = new MenuItem("masquer classe");
                    classItem.setOnAction(new ControlleurVisibilite(model, classe));
                    classMenu.getItems().add(classItem);
                }
                if (classe.getHeritageVisible()) {
                    MenuItem classItem = new MenuItem("masquer héritage");
                    classItem.setOnAction(new ControlleurVisibiliteHeritage(model, classe));
                    classMenu.getItems().add(classItem);
                } else {
                    MenuItem classItem = new MenuItem("afficher héritage");
                    classItem.setOnAction(new ControlleurVisibiliteHeritage(model, classe));
                    classMenu.getItems().add(classItem);
                }
                MenuItem classItem = new MenuItem("afficher méthode");
                classItem.setOnAction(new ControlleurAfficherMethodes(model,methodes,classe));
                classMenu.getItems().add(classItem);

            } else {
                MenuItem classItem = new MenuItem("afficher classe");
                classItem.setOnAction(new ControlleurVisibilite(model, classe));
                classMenu.getItems().add(classItem);
            }
            sidebar.getChildren().add(new MenuBar(classMenu));
        }
        }
        else {
            sidebar.getChildren().clear();
        }
    }

    @Override
    public void actualiser(Sujet s) {
        this.model = (Model) s;
        updateSidebar();
    }

}

