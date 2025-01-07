package classes;

/**
 *
 * Classe abstraite Visible permettant de gérer la visibilité des éléments masquables dans le diagramme
 */
public abstract class Visible {
    private boolean visible;

    public Visible() {
        this.visible = true;
    }

    public void changerVisibilite() {
        this.visible = !this.visible;
    }

    public boolean getVisible() {
        return visible;
    }
}
