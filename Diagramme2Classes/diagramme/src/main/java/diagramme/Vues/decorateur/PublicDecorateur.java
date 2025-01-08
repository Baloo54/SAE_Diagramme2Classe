package diagramme.Vues.decorateur;

import javafx.scene.control.Label;

/**
 * Décorateur pour ajouter le symbole + public au début d'un label.
 */
public class PublicDecorateur extends DecorateurLabel {
    public PublicDecorateur(Label label) {
        super(label);
        this.setText("+ " + label.getText());
    }
}