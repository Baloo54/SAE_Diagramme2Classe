/**
 * @author Gabriel Comte 
 */
package diagramme.Vues.arrow;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public abstract class FabriqueAbstraiteVueFleche extends Pane {

    /**
     * Ligne principale de la flèche.
     */
    protected Line line;

    /**
     * Tête de la flèche.
     */
    protected Polygon arrowHead;

    /**
     * Taille de la tête de flèche.
     */
    private double arrowHeadSize = 25;

    /**
     * Angle de la tête de flèche en radians.
     */
    private double arrowAngle = Math.toRadians(30);

    /**
     * Constructeur pour créer une flèche avec des coordonnées de départ et d'arrivée.
     * 
     * @param x1 Coordonnée X du point de départ.
     * @param y1 Coordonnée Y du point de départ.
     * @param x2 Coordonnée X du point d'arrivée.
     * @param y2 Coordonnée Y du point d'arrivée.
     */
    public FabriqueAbstraiteVueFleche(double x1, double y1, double x2, double y2) {
        drawArrow(x1, y1, x2, y2);
    }

    /**
     * Dessine ou redessine la flèche avec de nouvelles coordonnées.
     * 
     * @param x1 Nouvelle coordonnée X du point de départ.
     * @param y1 Nouvelle coordonnée Y du point de départ.
     * @param x2 Nouvelle coordonnée X du point d'arrivée.
     * @param y2 Nouvelle coordonnée Y du point d'arrivée.
     */
    private void drawArrow(double x1, double y1, double x2, double y2) {
        this.getChildren().clear();

        // Calcul des vecteurs directionnels
        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.sqrt(dx * dx + dy * dy);
        double ux = dx / length;
        double uy = dy / length;

        // Calcul des coordonnées de la ligne sans chevauchement avec la tête
        double lineEndX = x2 - arrowHeadSize * ux;
        double lineEndY = y2 - arrowHeadSize * uy;

        // Ligne principale
        this.line = new Line(x1, y1, lineEndX, lineEndY);
        //style par défaut
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        //puis style que les classes filles implémenterons
        styleLine();

        // Points de la tête de flèche
        double Hx1 = x2 - arrowHeadSize * (ux * Math.cos(arrowAngle) - uy * Math.sin(arrowAngle));
        double Hy1 = y2 - arrowHeadSize * (uy * Math.cos(arrowAngle) + ux * Math.sin(arrowAngle));

        double Hx2 = x2 - arrowHeadSize * (ux * Math.cos(-arrowAngle) - uy * Math.sin(-arrowAngle));
        double Hy2 = y2 - arrowHeadSize * (uy * Math.cos(-arrowAngle) + ux * Math.sin(-arrowAngle));

        this.arrowHead = new Polygon();
        this.arrowHead.getPoints().addAll(
            x2, y2,
            Hx1, Hy1,
            Hx2, Hy2
        );
        arrowHead.setStrokeWidth(2);
        arrowHead.setStroke(Color.BLACK);
        //puis style que les classes filles implémenterons
        styleArrowHead();

        this.getChildren().addAll(this.line, this.arrowHead);
    }

    /**
     * Modifie la position de départ de la flèche.
     * 
     * @param x1 Nouvelle coordonnée X du point de départ.
     * @param y1 Nouvelle coordonnée Y du point de départ.
     */
    public void setStartPosition(double x1, double y1) {
        double x2 = this.line.getEndX();
        double y2 = this.line.getEndY();
        drawArrow(x1, y1, x2, y2);
    }

/**
 * Modifie la position de la flèche (départ ou arrivée) en redessinant.
 * 
 * @param x1 Nouvelle coordonnée X du point de départ.
 * @param y1 Nouvelle coordonnée Y du point de départ.
 * @param x2 Nouvelle coordonnée X du point d'arrivée.
 * @param y2 Nouvelle coordonnée Y du point d'arrivée.
 * @param isStartPosition Si true, met à jour la position de départ, sinon celle d'arrivée.
 */
public void setPosition(double x1, double y1, double x2, double y2) {
    drawArrow(x1, y1, x2, y2);
}


    /**
     * Méthode abstraite pour styliser la ligne.
     */
    protected abstract void styleLine();

    /**
     * Méthode abstraite pour styliser la tête de flèche.
     */
    protected abstract void styleArrowHead();
}
