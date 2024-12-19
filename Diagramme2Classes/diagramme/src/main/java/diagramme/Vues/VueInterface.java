package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class VueInterface {
    private Pane root;

    public VueInterface(Class<?> clazz) {
        root = new Pane();
        afficher(clazz);
    }

    public void afficher(Class<?> clazz) {
        // Rectangle pour l'interface
        Rectangle box = new Rectangle(200, 100);
        box.setStyle("-fx-fill: lightyellow; -fx-stroke: black;");

        // Nom de l'interface
        Text interfaceName = new Text(10, 20, "Interface: " + clazz.getSimpleName());
        root.getChildren().addAll(box, interfaceName);
    }

    public Pane getView() {
        return root;
    }
}
