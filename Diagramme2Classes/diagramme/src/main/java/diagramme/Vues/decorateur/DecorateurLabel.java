package diagramme.Vues.decorateur;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * Classe  servant de décorateur pour un objet label.
 * Cette classe suit le motif de conception Décorateur, permettant d'ajouter dynamiquement
 * des fonctionnalités à un objet Label sans modifier sa structure sous-jacente.
 */
public class DecorateurLabel extends Label {
    /**
     * Constructeur par copie.
     * Crée un nouveau DecorateurLabel en copiant toutes les propriétés d'un autre DecorateurLabel.
     *
     * @param original Le DecorateurLabel à copier.
     */
    public DecorateurLabel(Label original) {
        super(original.getText());
        
        // Copie des propriétés principales
        this.setStyle(original.getStyle());
        this.setFont(original.getFont());
        this.setTextFill(original.getTextFill());
        this.setAlignment(original.getAlignment());
        this.setWrapText(original.isWrapText());
        this.setGraphic(original.getGraphic());
        this.setContentDisplay(original.getContentDisplay());
        this.setGraphicTextGap(original.getGraphicTextGap());
        this.setEllipsisString(original.getEllipsisString());
        this.setUnderline(original.isUnderline());
        this.setLineSpacing(original.getLineSpacing());
        this.setMnemonicParsing(original.isMnemonicParsing());

        // Ajout des nouvelles propriétés
        this.setFont(new Font("Arial", 16));
    }
    /**
     * Retourne la largeur en pixels du texte contenu dans le label.
     *
     * @return La largeur en pixels du texte affiché dans le label.
     */
    public double getLabelWidth() {
        Text text = new Text(this.getText());
        text.setFont(this.getFont());
        return text.getLayoutBounds().getWidth();
    }
    /**
     * Retourne la hauteur en pixels du texte contenu dans le label.
     *
     * @return La hauteur en pixels du texte affiché dans le label.
     */
    public double getLabelHeight() {
        Text text = new Text(this.getText());
        text.setFont(this.getFont());
        text.setWrappingWidth(this.getWidth()); // Gère le retour à la ligne si activé
        return text.getLayoutBounds().getHeight();
    }
}