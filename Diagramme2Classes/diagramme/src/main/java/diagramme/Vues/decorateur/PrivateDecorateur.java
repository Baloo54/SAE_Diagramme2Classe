package diagramme.Vues.decorateur;

import javafx.scene.control.Label;

/**
 * Décorateur pour ajouter le symbole - private au début d'un label.
 */
public class PrivateDecorateur extends DecorateurLabel {
    public PrivateDecorateur(Label label) {
        super(label);
        this.setText("- " + label.getText());
    }
}
