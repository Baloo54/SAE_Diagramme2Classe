package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class VueMethode {
    private Pane root;

    public VueMethode(String definition) {
        root = new Pane();
        afficher(definition);
    }

    public void afficher(String definition) {
        Rectangle rect = new Rectangle(150, 50);
        rect.setStyle("-fx-fill: yellow; -fx-stroke: black;");
        Text label = new Text(10, 30, "Méthode : " + definition);

        root.getChildren().addAll(rect, label);
    }

    public Pane getView() {
        return root;
    }
}
