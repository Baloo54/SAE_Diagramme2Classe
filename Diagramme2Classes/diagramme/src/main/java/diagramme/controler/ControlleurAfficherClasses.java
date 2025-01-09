package diagramme.controler;

import classes.Interface;
import diagramme.Model;
import diagramme.Observateur;
import diagramme.Sujet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControlleurAfficherClasses  implements EventHandler<ActionEvent>,Observateur {
    private Model model;
    private Stage stage;
    public ControlleurAfficherClasses(Model model) {
        this.model = model;
        stage = new Stage();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        // Créer une nouvelle fenêtre (Stage)
        if(actionEvent == null){
            if(stage.isShowing()){
                //stage.close();
                stage.setScene(creerScene());
                stage.show();
            }
        }else {
            if(stage.isShowing()){
                stage.close();
            }else {
                stage.setScene(creerScene());
                stage.show();
            }
        }
    }

    @Override
    public void actualiser(Sujet s) {
        handle(null);
    }

    public Scene creerScene(){
        VBox root = new VBox();
        for(Interface classe : model.getClasses()){
            Label label = new Label(classe.getNom());
            root.getChildren().add(label);
        }
        Scene scene = new Scene(root);
        return scene;
    }
}
