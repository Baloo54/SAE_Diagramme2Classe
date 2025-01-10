package diagramme.Vues;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

/**
 * La classe VuePackage permet de représenter graphiquement un package UML dans un diagramme de classe en utilisant JavaFX.
 * Elle gère l'affichage du nom du package et regroupe plusieurs instances de VueClasse dans un grand rectangle.
 * Cette classe hérite de {@link javafx.scene.layout.StackPane} pour disposer les éléments graphiques.
 * 
 * @package Diagramme
 */
public class VuePackage extends StackPane {
    /**
     * Constructeur de la classe VuePackage.
     * Ce constructeur initialise le nom du package et la liste des classes à afficher.
     *
     * @param nomPackage Le nom du package UML.
     * @param classes La liste des VueClasse contenues dans le package.
     */
    public VuePackage(String nomPackage, ArrayList<VueClasse> classes) {
        // Crée un label pour le nom du package
        Label title = new Label(nomPackage);
        title.setStyle("-fx-font-weight: bold;");
        //obtenir les dimensions
        double hauteur = 0, largeur = 0;
        for (VueClasse vueClasse : classes) {
            hauteur += vueClasse.getMaxHeight();
            largeur += vueClasse.getMaxWidth();
        }
        // Ajoute un rectangle pour délimiter la bordure du package
        Rectangle cadre = new Rectangle(hauteur, largeur);
        cadre.setStyle("-fx-stroke: black; -fx-fill: transparent; -fx-stroke-width: 3; -fx-arc-width: 20; -fx-arc-height: 20;");

        // Ajoute un grand rectangle noir entourant tout
        StackPane cadreExterieur = new StackPane();
        cadreExterieur.setPrefSize(largeur+10, hauteur+10);

        // Ajoute les éléments dans le StackPane
        cadreExterieur.getChildren().add(cadre);
        getChildren().add(title);
        getChildren().add(cadreExterieur);
        StackPane.setAlignment(title, Pos.TOP_LEFT);

        for (VueClasse vueClasse : classes) {
            double randomX = Math.random() * (largeur / 2);
            double randomY =    Math.random() * (hauteur / 2);
            vueClasse.setTranslateX(randomX);
            vueClasse.setTranslateY(randomY);
            getChildren().add(vueClasse);
        }
    }
}
