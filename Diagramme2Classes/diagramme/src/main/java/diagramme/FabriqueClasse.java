package diagramme;

import diagramme.ReadFile;
import javafx.scene.layout.Pane;
import diagramme.Vues.*;

import java.io.File;
import java.util.List;

public class FabriqueClasse {

    public Pane createViewsFromFile(File file) {
        ReadFile reader = new ReadFile();
        List<String> classDefinitions = reader.findClassFiles(file);

        Pane root = new Pane();
        int yPosition = 20;

        for (String definition : classDefinitions) {
            if (definition.contains("class")) {
                VueClasse vueClasse = new VueClasse(definition);
                vueClasse.getView().setLayoutY(yPosition);
                root.getChildren().add(vueClasse.getView());
                yPosition += 120;
            } else if (definition.contains("method")) {
                VueMethode vueMethode = new VueMethode(definition);
                vueMethode.getView().setLayoutY(yPosition);
                root.getChildren().add(vueMethode.getView());
                yPosition += 80;
            } else if (definition.contains("interface")) {
                VueInterface vueInterface = new VueInterface(definition);
                vueInterface.getView().setLayoutY(yPosition);
                root.getChildren().add(vueInterface.getView());
                yPosition += 100;
            }
        }
        return root;
    }
}
