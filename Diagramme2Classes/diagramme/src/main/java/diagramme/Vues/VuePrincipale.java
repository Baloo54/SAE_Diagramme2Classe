package diagramme.Vues;

import classes.Classe;
import classes.Interface;
import diagramme.Model;
import diagramme.Observateur;
import diagramme.Position;
import diagramme.Sujet;
import diagramme.controler.DeplacementControler;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VuePrincipale extends StackPane implements Observateur {

    // Stocke les classes affichées avec leur vue correspondante
    private HashMap<Interface, VueClasse> vues = new HashMap<>();

    @Override
    public void actualiser(Sujet s) {
        Model model = (Model) s;

        // Marquer toutes les vues comme "non vérifiées" pour supprimer ensuite celles qui ne sont plus nécessaires
        HashMap<Interface, Boolean> marqueurs = new HashMap<>();
        for (Interface classe : vues.keySet()) {
            marqueurs.put(classe, false);
        }

        // Mettre à jour ou ajouter de nouvelles classes
        for (Interface classe : model.getClasses()) {
            Position position = model.getPosition(classe);

            if (vues.containsKey(classe)) {
                // Si la classe existe déjà, mettre à jour sa position
                VueClasse vueClasse = vues.get(classe);
                vueClasse.setTranslateX(position.getX());
                vueClasse.setTranslateY(position.getY());
                vueClasse.update(classe);
            } else {
                // Si la classe est nouvelle, créer une vue et l'ajouter
                VueClasse nouvelleVue = new VueClasse(classe);
                DeplacementControler controler = new DeplacementControler(model);
                controler.ajouterEvenements(nouvelleVue);

                nouvelleVue.setTranslateX(position.getX());
                nouvelleVue.setTranslateY(position.getY());
                getChildren().add(nouvelleVue);
                vues.put(classe, nouvelleVue); // Ajouter dans la liste
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
    }
}
