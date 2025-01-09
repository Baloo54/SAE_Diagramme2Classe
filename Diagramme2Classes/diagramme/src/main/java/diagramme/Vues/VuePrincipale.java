package diagramme.Vues;

import classes.Interface;
import diagramme.Model;
import diagramme.Observateur;
import diagramme.Position;
import diagramme.Sujet;
import diagramme.Vues.arrow.FabriqueAbstraiteVueFleche;
import diagramme.Vues.arrow.FlecheTeteRemplie;
import diagramme.controler.DeplacementControler;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VuePrincipale extends StackPane implements Observateur {

    // Stocke les classes affichées avec leur vue correspondante
    private HashMap<Interface, VueClasse> vues = new HashMap<>();
    //stock les différents flèches de relations
    private ArrayList<HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>>> fleches = new ArrayList<>();

    @Override
    public void actualiser(Sujet s) {
        Model model = (Model) s;
        //vérification si les relations sont générées.
        boolean verificationRelation = true;
        // Marquer toutes les vues comme "non vérifiées" pour supprimer ensuite celles qui ne sont plus nécessaires
        HashMap<Interface, Boolean> marqueurs = new HashMap<>();
        for (Interface classe : vues.keySet()) {
            marqueurs.put(classe, false);
        }

        // Mettre à jour ou ajouter de nouvelles classes
        for (Interface classe : model.getClasses()) {
            Position position = model.getPosition(classe);

            if (vues.containsKey(classe)) {
                // Si la classe existe déjà, mettre à jour sa position et son contenu
                VueClasse vueClasse = vues.get(classe);
                vueClasse.setTranslateX(position.getX());
                vueClasse.setTranslateY(position.getY());
                vueClasse.mettreAJourAffichage(); // Mise à jour des méthodes et attributs
            } else {
                //donc les relations n'ont pas été ajoutée
                verificationRelation = false;
                // Si la classe est nouvelle, créer une vue et l'ajouter
                VueClasse nouvelleVue = new VueClasse(classe);
                DeplacementControler controler = new DeplacementControler(model);
                controler.ajouterEvenements(nouvelleVue);

                nouvelleVue.setTranslateX(position.getX());
                nouvelleVue.setTranslateY(position.getY());
                getChildren().add(nouvelleVue);
                vues.put(classe, nouvelleVue);
            }
            // Marquer la classe comme vérifiée
            marqueurs.put(classe, true);
        }
        // Supprimer les classes qui ne sont plus dans le modèle
        Iterator<Map.Entry<Interface, VueClasse>> iterator = vues.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Interface, VueClasse> entry = iterator.next();
            if (!marqueurs.get(entry.getKey())) {
                getChildren().remove(entry.getValue()); // Retirer du visuel
                iterator.remove(); // Retirer de la HashMap
            }
        }
        if(!verificationRelation){
            HashMap<Interface, ArrayList<HashMap<String, Interface>>> relations = ((Model)s).getRelations();
            for (Map.Entry<Interface, ArrayList<HashMap<String, Interface>>> entry : relations.entrySet()) {
                // récupère la classevue source
                VueClasse source = vues.get(entry.getKey());
                // Parcourt les relations associées
                for (HashMap<String, Interface> relation : entry.getValue()) {
                    for (Map.Entry<String, Interface> rel : relation.entrySet()) {
                        //récupère la classvue cible
                        VueClasse cible = vues.get(rel.getValue());
                        FabriqueAbstraiteVueFleche fleche = new FlecheTeteRemplie(0,0,0,0);
                        getChildren().add(fleche);
                        fleche.setMouseTransparent(true);
                        ajouterRelationComplexe(fleche, source, cible);
                    }
                }
            }
        }else{
             for (HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>> map : fleches) {
                FabriqueAbstraiteVueFleche fabrique = map.keySet().iterator().next();
                for (HashMap<VueClasse, VueClasse> innerMap : map.values()) {
                    for (VueClasse source : innerMap.keySet()) {
                        VueClasse cible = innerMap.get(source);
                         // Récupération des coordonnées actuelles des vues
                        // Calcul des coordonnées de source et cible dans la scène
                        double x1 = source.localToScene(source.getWidth() / 2, 0).getX(); // Centre horizontal de source
                        double y1 = source.localToScene(0, 0).getY(); // Position Y de source

                        double x2 = cible.localToScene(cible.getWidth() / 2, 0).getX(); // Centre horizontal de cible
                        double y2 = cible.localToScene(0, cible.getHeight()).getY(); // Bas de cible
                        fabrique.setPosition(x1, y1, x2, y2);
                    }
                }
            }


        }
    }
    /**
     * Ajoute une relation complexe en vérifiant qu'elle n'existe pas déjà.
     *
     * @param fleche La clé FabriqueAbstraiteVueFleche
     * @param source VueClasse source
     * @param cible VueClasse cible
     */
    private void ajouterRelationComplexe(FabriqueAbstraiteVueFleche fleche, VueClasse source, VueClasse cible) {
        // Création d'une nouvelle relation
        HashMap<VueClasse, VueClasse> relationInterne = new HashMap<>();
        relationInterne.put(source, cible);

        HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>> relationExterne = new HashMap<>();
        relationExterne.put(fleche, relationInterne);

        // Vérifie si la relation existe déjà
        if (!fleches.contains(relationExterne)) {
            fleches.add(relationExterne); // Ajout de la relation si elle est unique
        }
    }

}
