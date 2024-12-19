package diagramme.Vues;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class VueClasse {
    private Pane root;

    public VueClasse(Class<?> clazz) {
        root = new Pane();
        afficher(clazz);
    }

    public void afficher(Class<?> clazz) {
        // Taille totale de la boîte
        double boxWidth = 300;
        double boxHeight = 150;

        // Rectangle principal
        Rectangle box = new Rectangle(boxWidth, boxHeight);
        box.setStyle("-fx-fill: white; -fx-stroke: black;");

        // Nom de la classe
        Text className = new Text(10, 20, clazz.getSimpleName());
        className.setStyle("-fx-font-weight: bold;");

        // Ligne séparant le nom de la classe des attributs
        Line line1 = new Line(0, 30, boxWidth, 30);

        // Ajouter les attributs
        int yPosition = 50;
        for (Field field : clazz.getDeclaredFields()) {
            String visibility = getVisibility(field.getModifiers());
            Text fieldText = new Text(10, yPosition, visibility + " " + field.getName() + " : " + field.getType().getSimpleName());
            root.getChildren().add(fieldText);
            yPosition += 15;
        }

        // Ligne séparant les attributs des méthodes
        Line line2 = new Line(0, yPosition, boxWidth, yPosition);
        yPosition += 10;

        // Ajouter les méthodes
        for (Method method : clazz.getDeclaredMethods()) {
            String visibility = getVisibility(method.getModifiers());
            Text methodText = new Text(10, yPosition, visibility + " " + method.getName() + "()");
            root.getChildren().add(methodText);
            yPosition += 15;
        }

        // Ajouter les composants au conteneur
        root.getChildren().addAll(box, className, line1, line2);
    }

    private String getVisibility(int modifiers) {
        if (java.lang.reflect.Modifier.isPublic(modifiers)) return "+";
        if (java.lang.reflect.Modifier.isPrivate(modifiers)) return "-";
        if (java.lang.reflect.Modifier.isProtected(modifiers)) return "#";
        return "~"; // Default/package-private
    }

    public Pane getView() {
        return root;
    }
}
