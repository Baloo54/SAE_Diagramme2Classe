package diagramme.Vues.decorateur;

import javafx.scene.control.Label;

/**
 * Décorateur pour ajouter le texte <<interface>> au début d'un label.
 */
public class InterfaceDecorateur extends DecorateurLabel {
    public InterfaceDecorateur(Label label) {
        super(label);
        this.setText("<<interface>> " + label.getText());
    }
}
