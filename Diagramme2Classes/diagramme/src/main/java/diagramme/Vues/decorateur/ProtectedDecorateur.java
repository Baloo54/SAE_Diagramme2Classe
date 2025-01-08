package diagramme.Vues.decorateur;

import javafx.scene.control.Label;

/**
 * Décorateur pour ajouter le symbole # protected au début d'un label.
 */
public class ProtectedDecorateur extends DecorateurLabel {
    public ProtectedDecorateur(Label label) {
        super(label);
        this.setText("# " + label.getText());
    }
}
