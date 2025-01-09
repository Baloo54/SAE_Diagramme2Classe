package diagramme.Vues.arrow;
/**
 * fleche ayant une tête vide
 */
public class FlecheNormal extends FabriqueAbstraiteVueFleche{
    public FlecheNormal(double x1, double y1, double x2, double y2){
        super(x1, y1, x2, y2);
    }

    @Override
    protected void styleLine() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void styleArrowHead() {
        // TODO Auto-generated method stub
        this.arrowHead.setFill(null);
    }
}
