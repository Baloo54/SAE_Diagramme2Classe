package diagramme.Vues.arrow;

/**
 * fleche ayant une tête remplie et une ligne en pointille
 */
public class FlecheTeteRempliePointille extends FabriqueAbstraiteVueFleche{
    public FlecheTeteRempliePointille(double x1, double y1, double x2, double y2){
        super(x1, y1, x2, y2);
    }

    @Override
    protected void styleLine() {
        this.line.getStrokeDashArray().addAll(2.0,5.0);
    }

    @Override
    protected void styleArrowHead() {
        // TODO Auto-generated method stub
    }
}