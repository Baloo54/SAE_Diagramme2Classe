package diagramme.Vues.decorateur;

import javafx.scene.control.Label;

/**
 * Décorateur pour ajouter le texte <<abstract>> au début d'un label.
 */
public class AbstractDecorateur extends DecorateurLabel {
    public AbstractDecorateur(Label label) {
        super(label);
        this.setText("<<abstract>> " + label.getText());
    }
}
