package diagramme.Vues.decorateur;

import javafx.scene.control.Label;

/**
 * Décorateur pour ajouter le texte <<final>> au début d'un label.
 */
public class FinalDecorateur extends DecorateurLabel {
    
    public FinalDecorateur(Label label) {
        super(label);
        this.setText("<<final>> " + label.getText());
    }
}
