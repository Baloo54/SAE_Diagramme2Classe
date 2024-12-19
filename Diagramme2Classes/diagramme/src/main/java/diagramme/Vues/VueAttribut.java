package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class VueAttribut {
    private Pane root;

    public VueAttribut(Field field) {
        root = new Pane();
        afficher(field);
    }

    public void afficher(Field field) {
        // Texte représentant l'attribut
        String visibility = getVisibility(field.getModifiers());
        String fieldSignature = visibility + " " + field.getName() + " : " + field.getType().getSimpleName();

        Text fieldText = new Text(10, 20, fieldSignature);
        root.getChildren().add(fieldText);
    }

    public Pane getView() {
        return root;
    }

    private String getVisibility(int modifiers) {
        if (Modifier.isPublic(modifiers)) return "+";
        if (Modifier.isPrivate(modifiers)) return "-";
        if (Modifier.isProtected(modifiers)) return "#";
        return "~"; // Default/package-private
    }
}
