package diagramme.Vues.decorateur;

import javafx.scene.control.Label;
import javafx.scene.text.FontPosture;
/**
 * Décorateur pour mettre un label en italique.
 */
public class ItaliqueDecorateur extends DecorateurLabel {
    public ItaliqueDecorateur(Label label) {
        super(label);
        this.setFont(javafx.scene.text.Font.font(getFont().getFamily(), FontPosture.ITALIC, getFont().getSize()));
    }
}