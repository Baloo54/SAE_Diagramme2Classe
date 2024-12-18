package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class VueInterface {
    private Pane root;

    public VueInterface(String definition) {
        root = new Pane();
        afficher(definition);
    }

    public void afficher(String definition) {
        Circle circle = new Circle(50, 50, 40);
        circle.setStyle("-fx-fill: lightgreen; -fx-stroke: black;");
        Text label = new Text(35, 55, "Interface : " + definition);

        root.getChildren().addAll(circle, label);
    }

    public Pane getView() {
        return root;
    }
}
