package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class VueClasse {
    private VBox root;

    public VueClasse(Class<?> cl) {
        root = new VBox(10);
        root.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-padding: 10;");
        afficher(cl);
    }

    public void afficher(Class<?> clazz) {
        // Nom de la classe
        Text className = new Text(clazz.getSimpleName());
        className.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Conteneur pour attributs et méthodes
        VBox content = new VBox(5);

        // Ajouter les attributs
        for (Field field : clazz.getDeclaredFields()) {
            Text fieldText = new Text(getVisibility(field.getModifiers()) + " " + field.getName() + " : " + field.getType().getSimpleName());
            content.getChildren().add(fieldText);
        }

        // Ajouter les méthodes
        for (Method method : clazz.getDeclaredMethods()) {
            Text methodText = new Text(getVisibility(method.getModifiers()) + " " + method.getName() + "()");
            content.getChildren().add(methodText);
        }

        root.getChildren().addAll(className, content);
    }

    private String getVisibility(int modifiers) {
        if (java.lang.reflect.Modifier.isPublic(modifiers)) return "+";
        if (java.lang.reflect.Modifier.isPrivate(modifiers)) return "-";
        if (java.lang.reflect.Modifier.isProtected(modifiers)) return "#";
        return "~";
    }

    public Pane getView() {
        return root;
    }
}
