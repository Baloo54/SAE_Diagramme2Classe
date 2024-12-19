package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class VueMethode {
    private Pane root;

    public VueMethode(Method method) {
        root = new Pane();
        afficher(method);
    }

    public void afficher(Method method) {
        // Texte représentant la méthode
        String visibility = getVisibility(method.getModifiers());
        String methodSignature = visibility + " " + method.getName() + "()";

        Text methodText = new Text(10, 20, methodSignature);
        root.getChildren().add(methodText);
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
