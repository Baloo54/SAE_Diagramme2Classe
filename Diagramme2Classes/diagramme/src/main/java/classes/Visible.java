package classes;

/**
 * Classe représentant un élément visible
 */
public abstract class Visible {
    /**
     * Attributs de la classe
     */
    private boolean visible;

    /**
     * Constructeur de la classe
     */

    public Visible() {
        this.visible = true;
    }

    /**
     * Méthode permettant de changer la visibilité
     */

    public void changerVisibilite() {
        this.visible = !this.visible;
    }

    /**
     * Méthode permettant de récupérer la visibilité
     * @return : la visibilité
     */

    public boolean getVisible() {
        return visible;
    }
}
