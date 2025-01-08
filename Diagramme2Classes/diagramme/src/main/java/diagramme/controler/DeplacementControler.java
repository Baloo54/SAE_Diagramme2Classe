package diagramme.controler;

import classes.Classe;
import diagramme.Model;
import diagramme.Position;
import diagramme.Vues.VueClasse;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DeplacementControler {
    private Model model;

    public DeplacementControler(Model model) {
        this.model = model;
    }

    // Méthode pour attacher les événements à une VueClasse
    public void ajouterEvenements(VueClasse vueClasse) {
        // Position initiale pour calculer les décalages
        final double[] offsetX = {0};
        final double[] offsetY = {0};

        // Événement lors du clic initial
        vueClasse.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Calculer l'écart entre la position actuelle et la position de la souris
                offsetX[0] = event.getSceneX() - vueClasse.getTranslateX();
                offsetY[0] = event.getSceneY() - vueClasse.getTranslateY();
            }
        });

        // Événement lors du déplacement
        vueClasse.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Mettre à jour la position en fonction du décalage
                double newX = event.getSceneX() - offsetX[0];
                double newY = event.getSceneY() - offsetY[0];

                // Déplacer visuellement la vue
                vueClasse.setTranslateX(newX);
                vueClasse.setTranslateY(newY);

                // Mettre à jour la position dans le modèle
                Classe c = vueClasse.getClasse();
                model.deplacement(c, new Position(newX, newY));
            }
        });
    }
}
