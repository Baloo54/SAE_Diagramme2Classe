package diagramme;


/**
 * Classe Position - Représente une position avec des coordonnées x et y.
 */
public class Position {
    private double x;
    private double y;

    /**
     * Constructeur de Position.
     * @param x Coordonnée en X.
     * @param y Coordonnée en Y.
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtient la coordonnée X.
     * @return La coordonnée X.
     */
    public double getX() {
        return x;
    }

    /**
     * Obtient la coordonnée Y.
     * @return La coordonnée Y.
     */
    public double getY() {
        return y;
    }

    /**
     * Définit la coordonnée X.
     * @param x Nouvelle valeur pour X.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Définit la coordonnée Y.
     * @param y Nouvelle valeur pour Y.
     */
    public void setY(double y) {
        this.y = y;
    }
}