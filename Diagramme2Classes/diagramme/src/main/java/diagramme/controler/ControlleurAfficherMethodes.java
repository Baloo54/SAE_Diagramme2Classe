package diagramme.controler;

import classes.Interface;
import classes.Methode;
import diagramme.Model;
import diagramme.Observateur;
import diagramme.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class ControlleurAfficherMethodes implements EventHandler<ActionEvent>, Observateur {

    private Model model;
    private VBox sidebar;
    boolean visible = false;
    Interface classe;

    public ControlleurAfficherMethodes(Model model, VBox sidebar,Interface classe) {
        this.model = model;
        this.sidebar = sidebar;
        this.classe = classe;
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
            for (Methode m : classe.getMethodes()) {
                Menu classMenu = new Menu(m.getNom());
                if (m.getVisible()) {
                    {
                        MenuItem classItem = new MenuItem("masquer méthode");
                        classItem.setOnAction(new ControlleurVisibiliteMethode(model, classe, m));
                        classMenu.getItems().add(classItem);
                    }
                } else {
                    MenuItem classItem = new MenuItem("afficher méthode");
                    classItem.setOnAction(new ControlleurVisibiliteMethode(model, classe, m));
                    classMenu.getItems().add(classItem);
                }
                sidebar.getChildren().add(new MenuBar(classMenu));
            }
        } else {
            sidebar.getChildren().clear();
        }
    }

    @Override
    public void actualiser(Sujet s) {
        this.model = (Model) s;
        updateSidebar();
    }

}
