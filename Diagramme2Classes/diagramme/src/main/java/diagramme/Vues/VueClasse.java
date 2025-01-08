package diagramme.Vues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import classes.*;
import diagramme.Vues.decorateur.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * La classe VueClasse permet de représenter graphiquement une classe UML dans un diagramme de classe en utilisant JavaFX.
 * Elle gère l'affichage des attributs, des méthodes et du titre de la classe, ainsi que l'application de différents modificateurs visuels.
 * Cette classe hérite de {@link javafx.scene.layout.StackPane} pour disposer les éléments graphiques.
 * 
 * @package Diagramme
 */
public class VueClasse extends StackPane{
    
    private Classe classe; // Instance de la classe représentée

    /**
     * Constructeur de la classe VueClasse.
     * Ce constructeur initialise la classe représentée et crée la structure graphique pour afficher la classe UML
     * avec ses attributs, méthodes et titre, en prenant en compte les modificateurs de visibilité.
     * 
     * @param classe La classe UML à représenter.
     */
    public VueClasse(Classe classe){
        this.classe = classe;
        ArrayList<DecorateurLabel> methodes = new ArrayList<>();
        ArrayList<DecorateurLabel> attributs = new ArrayList<>();
        
        // Création du titre avec modificateurs.
        DecorateurLabel title = getModificateurClasse(classe.getModificateurs(), new Label(classe.getNom()));

        // Traitement des méthodes visibles.
        for (Methode methode : classe.getMethodes()) {
            if(methode.getVisible()){
                List<HashMap<String,String>> hash = methode.getParametres();
                String signature = methode.getNom() + "(";
                int index = hash.size();
                for (HashMap<String, String> map : hash) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        index--;
                        signature += entry.getValue().split("arg")[0];
                        signature += index > 0 ? ",": "";
                    }
                }
                signature += "): " + methode.getTypeRetour();
                methodes.add(getModificateur(methode.getModificateurs(), new Label(signature)));
            }
        }

        // Traitement des attributs visibles.
        for(Attribut attribut : classe.getAttributs()){
            if(attribut.getVisible()){
                attributs.add(getModificateur(attribut.getModificateurs(), new Label(attribut.getNom())));
            }
        }

        // Calcul de la largeur maximale pour l'affichage.
        ArrayList<DecorateurLabel> testTaille = new ArrayList<>();
        testTaille.add(title);
        testTaille.addAll(methodes);
        testTaille.addAll(attributs);

        double largeur = 0;
        double hauteur = 0;

        for (DecorateurLabel label : testTaille) {
            if(label.getLabelWidth() > largeur) largeur = label.getLabelWidth();
            if(label.getLabelHeight() > hauteur) hauteur = label.getLabelHeight();
        }
        largeur += 5;
        hauteur *= testTaille.size();

        // Création de la structure graphique pour le diagramme de classe.
        VBox vbox = new VBox();
        //fixer la largeur de vBox
        vbox.setMaxWidth(largeur);
        vbox.setMaxHeight(hauteur);
        this.setMaxWidth(largeur);
        this.setMaxHeight(hauteur);
        //centré le titre
        StackPane panecenter = new StackPane();
        panecenter.getChildren().add(title);
        panecenter.setAlignment(Pos.CENTER);
        vbox.getChildren().add(panecenter);

        vbox.getChildren().add(new Rectangle(largeur, 1)); // Ligne de séparation
        for (DecorateurLabel attr : attributs) {
            vbox.getChildren().add(attr);
        }

        vbox.getChildren().add(new Rectangle(largeur, 1)); // Ligne de séparation
        for (DecorateurLabel meth : methodes) {
            vbox.getChildren().add(meth);
        }
        //bordure de la vbox
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 1;"); // Bordure noire de 1px
        getChildren().addAll(vbox);
    }

    /**
     * Applique les modificateurs à un label représentant le titre de la classe UML.
     * 
     * @param modificateur Le modificateur de la classe (par exemple "interface", "abstract").
     * @param label Le label à décorer.
     * @return Le label décoré avec le modificateur spécifié.
     */
    private DecorateurLabel getModificateurClasse(ArrayList<String> modificateurs, Label label){
        DecorateurLabel result = new DecorateurLabel(label);
        for (String modificateur : modificateurs) {
            switch(modificateur){
                case "interface": 
                    result = new InterfaceDecorateur(result);
                    break;
                case "abstract":
                    result = new AbstractDecorateur(new ItaliqueDecorateur(result));
                    break;
            }
        }
        return result;
    }

    /**
     * Applique les modificateurs de visibilité (public, private, protected) à un label représentant un attribut ou une méthode.
     * 
     * @param modificateur Le modificateur de visibilité (par exemple "public", "private", "protected").
     * @param label Le label à décorer.
     * @return Le label décoré avec le modificateur de visibilité spécifié.
     */
    private DecorateurLabel getModificateur(ArrayList<String> modificateurs, Label label){
        DecorateurLabel result = new DecorateurLabel(label);
        for (String modificateur : modificateurs) {
            switch (modificateur) {
                case "public":
                    result = new PublicDecorateur(result);
                    break;
                case "private":
                    result = new PrivateDecorateur(result);
                    break;
                case "protected":
                    result = new ProtectedDecorateur(result);
                    break;
                case "static":
                    result = new SoulignementDecorateur(result);
                    break;
                case "abstract":
                    result = new ItaliqueDecorateur(result);
            }
        }
        return result;
    }
    public Classe getClasse(){
        return this.classe;
    }
}
