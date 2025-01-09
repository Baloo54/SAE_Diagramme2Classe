package diagramme.Vues.arrow;
/**
 * fleche ayant une ligne avec en pointillé et une tête vide
 */
public class FlechePointille extends FabriqueAbstraiteVueFleche{
    public FlechePointille(double x1, double y1, double x2, double y2){
        super(x1, y1, x2, y2);
    }

    @Override
    protected void styleLine() {
        this.line.getStrokeDashArray().addAll(2.0,5.0);
    }

    @Override
    protected void styleArrowHead() {
        this.arrowHead.setFill(null);
    }
}