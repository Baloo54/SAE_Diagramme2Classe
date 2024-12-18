package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class VuePackage {
    private Pane root;

    public VuePackage() {
        root = new Pane();
        afficher();
    }

    public void afficher() {
        Rectangle rect = new Rectangle(200, 75);
        rect.setStyle("-fx-fill: pink; -fx-stroke: black;");
        Text label = new Text(10, 40, "Vue Package");

        root.getChildren().addAll(rect, label);
    }

    public Pane getView() {
        return root;
    }
}
