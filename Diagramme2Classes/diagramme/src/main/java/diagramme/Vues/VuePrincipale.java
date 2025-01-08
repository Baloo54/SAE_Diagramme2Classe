/**
 * Classe VuePrincipale - Vue principale du diagramme.
 * 
 * Cette classe représente la vue principale d'un diagramme en tant que conteneur graphique.
 * Elle implémente l'interface Observateur pour être notifiée des changements dans le modèle.
 * 
 * @author [Nom de l'auteur]
 * @version 1.0
 * @since [Date de création]
 */
package diagramme.Vues;

import java.util.ArrayList;
import java.util.Random;

import classes.Classe;
import diagramme.Model;
import diagramme.Observateur;
import diagramme.Sujet;
import javafx.scene.layout.StackPane;

/**
 * La classe VuePrincipale est un conteneur principal pour l'affichage graphique des classes.
 * Elle étend StackPane et implémente l'interface Observateur pour mettre 
 * à jour son contenu en fonction des modifications du modèle associé.
 */
public class VuePrincipale extends StackPane implements Observateur {
    /**
     * Méthode d'actualisation appelée lorsqu'un changement est détecté dans le sujet observé.
     * Elle met à jour l'affichage en ajoutant des vues pour chaque classe du modèle.
     * 
     * @param s Le sujet observé (doit être une instance de Model).
     */
    @Override
    public void actualiser(Sujet s) {
        // Récupérer la liste des classes depuis le modèle
        ArrayList<Classe> classes = ((Model) (s)).getClasses();

        // Créer un générateur de nombres aléatoires
        Random random = new Random();

        // Ajouter chaque classe à l'affichage en tant que VueClasse avec translation aléatoire
        for (Classe classe : classes) {
            VueClasse vueClasse = new VueClasse(classe);
            getChildren().add(vueClasse);
        }
    }
}
