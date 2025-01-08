package diagramme.Vues.decorateur;

import javafx.scene.control.Label;

/**
 * Décorateur pour souligner un label en noir.
 */
public class SoulignementDecorateur extends DecorateurLabel {
    public SoulignementDecorateur(Label label) {
        super(label);
        this.setStyle("-fx-underline: true; -fx-text-fill: black;");
    }
}
