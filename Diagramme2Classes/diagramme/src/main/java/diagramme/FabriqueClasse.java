package diagramme;

import diagramme.Vues.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;

public class FabriqueClasse {

    public Pane creerVue(File file) {
        ReadFile reader = new ReadFile();
        List<String> classDefinitions = reader.findClassFiles(file);

        VBox root = new VBox(20); // Utilisation d'un VBox pour gérer les positions
        root.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

        for (String definition : classDefinitions) {
            if (definition.contains("class")) {
                VueClasse vueClasse = new VueClasse(definition.getClass());
                root.getChildren().add(vueClasse.getView());
            } else if (definition.contains("interface")) {
                VueInterface vueInterface = new VueInterface(definition.getClass());
                root.getChildren().add(vueInterface.getView());
            } else if (definition.contains("method")) {
                VueMethode vueMethode = new VueMethode(definition.getClass().getEnclosingMethod());
                root.getChildren().add(vueMethode.getView());
            }
        }

        return root;
    }
}
