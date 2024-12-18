package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class VueClasse {
    private Pane root;

    public VueClasse(String definition) {
        root = new Pane();
        afficher(definition);
    }

    public void afficher(String definition) {
        Rectangle box = new Rectangle(200, 100);
        box.setStyle("-fx-fill: lightblue; -fx-stroke: black;");
        Text label = new Text(10, 50, "Classe : " + definition);

        root.getChildren().addAll(box, label);
    }

    public Pane getView() {
        return root;
    }
}
