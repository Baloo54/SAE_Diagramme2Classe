package diagramme.Vues;

import diagramme.Model;
import diagramme.Observateur;
import diagramme.Position;
import diagramme.Sujet;
import diagramme.Vues.arrow.FabriqueAbstraiteVueFleche;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VuePrincipale extends StackPane implements Observateur {

    // Stocke les classes affichées avec leur vue correspondante
    private ArrayList<VueClasse> vues = new ArrayList<>();
    //stock les différents flèches de relations
    private ArrayList<HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>>> fleches = new ArrayList<>();

    @Override
    public void actualiser(Sujet s) {
        Model model = (Model) s;        
        // Mettre à jour ou ajouter de nouvelles classes
        //partie1
        for (VueClasse classe : model.getVueClasse()) {
            Position position = model.getPosition(classe.getClasse());
            classe.setTranslateX(position.getX());
            classe.setTranslateY(position.getY());
            //vueClasse.mettreAJourAffichage(); // Mise à jour des méthodes et attributs
            if (!this.vues.contains(classe)) {
                getChildren().add(classe);
                vues.add(classe);
                
            }
        }
        // Supprimer les classes qui ne sont plus dans le modèle
        for (VueClasse vue : this.vues) {
            if(!model.getVueClasse().contains(vue))getChildren().remove(vue);
        }
        //mettre à jour ou ajouter les flèches
        ArrayList<HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>>> vuefleches = model.getVueFleches();
        for (HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>> map : vuefleches) {
            // Récupérer la première clé et ses valeurs associées
            Map.Entry<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>> entry = map.entrySet().iterator().next();
            FabriqueAbstraiteVueFleche fabrique = entry.getKey();
            HashMap<VueClasse, VueClasse> innerMap = entry.getValue();

            if (!this.fleches.contains(map)) {
                getChildren().add(fabrique);
                this.fleches.add(map);
            } else {
                for (Map.Entry<VueClasse, VueClasse> innerEntry : innerMap.entrySet()) {
                    VueClasse source = innerEntry.getKey();
                    VueClasse cible = innerEntry.getValue();

                    // Récupération des coordonnées actuelles des vues
                    double x1, y1, x2, y2;
                    switch (fabrique.getClass().getSimpleName()) {
                        case "FlecheTeteRempliePointille":
                        case "FlechePointille":
                            x1 = source.localToScene(source.getWidth() / 2, 0).getX();
                            y1 = source.localToScene(0, 0).getY();
                            x2 = cible.localToScene(cible.getWidth() / 2, 0).getX();
                            y2 = cible.localToScene(0, cible.getHeight()).getY();
                            break;

                        case "FlecheTeteRemplie":
                            x1 = source.localToScene(source.getWidth(), 0).getX();
                            y1 = source.localToScene(0, source.getHeight() * 0.1).getY();
                            x2 = cible.localToScene(0, 0).getX();
                            y2 = cible.localToScene(0,cible.getHeight() * 0.1).getY();
                            break;

                        default:
                            throw new IllegalArgumentException("Type de flèche inconnu : " + fabrique.getClass().getSimpleName());
                    }

                    fabrique.setPosition(x1, y1, x2, y2);
                }
            }
        }
        //supprimer les flèches qui ne sont plus dans le modèle
        for(HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>> map : fleches){
            if(!model.getVueFleches().contains(map)){
                getChildren().remove(map.keySet().iterator().next());
            }
        }
    }
}