package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class VueTexte {
    private Pane root;

    public VueTexte() {
        root = new Pane();
        afficher();
    }

    public void afficher() {
        Text text = new Text(10, 20, "Affichage Texte");
        text.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        root.getChildren().add(text);
    }

    public Pane getView() {
        return root;
    }
}
