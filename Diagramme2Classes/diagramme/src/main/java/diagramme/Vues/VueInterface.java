package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VueInterface {
    private VBox root;

    public VueInterface(Class<?> clazz) {
        root = new VBox(10);
        root.setStyle("-fx-border-color: black; -fx-background-color: lightyellow; -fx-padding: 10;");
        afficher(clazz);
    }

    public void afficher(Class<?> clazz) {
        Text interfaceName = new Text("Interface: " + clazz.getSimpleName());
        interfaceName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        root.getChildren().add(interfaceName);
    }

    public Pane getView() {
        return root;
    }
}
