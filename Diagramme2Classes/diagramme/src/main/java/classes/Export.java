package classes;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Export {

    /**
     * Exporte toutes les classes au format puml
     *
     * @param classes : liste des classes
     * @return le nom du fichier .puml
     */
    public String exportPuml(List<Classe> classes) {
        StringBuilder puml = new StringBuilder();
        puml.append("@startuml\n");

        // Exportation des classes
        for (Classe classe : classes) {
            puml.append("class ").append(classe.getNom()).append(" {\n");

            // Exportation attributs
            for (Attribut attribut : classe.getAttributs()) {
                ArrayList<String> modificateurs = (ArrayList<String>) attribut.getModificateurs();
                String visibility = getPumlModificateur(modificateurs);
                puml.append("    ").append(visibility)
                        .append(" ").append(attribut.getNom())
                        .append(" : ").append(attribut.getType()).append("\n");
            }

            // Exportation méthodes
            for (Methode methode : classe.getMethodes()) {
                String visibility = getPumlModificateur(methode.getModificateurs());
                puml.append("    ").append(visibility)
                        .append(" ").append(methode.getNom()).append("(");

                List<HashMap<String, String>> parametres = methode.getParametres();
                for (int i = 0; i < parametres.size(); i++) {
                    HashMap<String, String> param = parametres.get(i);
                    puml.append(param.get("nom")).append(" : ").append(param.get("type"));
                    if (i < parametres.size() - 1) {
                        puml.append(", ");
                    }
                }

                puml.append(") : ").append(methode.getTypeRetour()).append("\n");
            }
            puml.append("}\n");

            // Relations avec classe parent
            if (classe.getClasseParent() != null) {
                puml.append(classe.getClasseParent().getNom())
                        .append(" <|-- ").append(classe.getNom()).append("\n");
            }

            // Exportation interfaces
            for (Interface inter : classe.getInterfaces()) {
                puml.append("interface ").append(inter.getNom()).append(" {\n");
                for (Methode methode : inter.getMethodes()) {
                    puml.append("    + ").append(methode.getNom()).append("()\n");
                }
                puml.append("}\n");
                puml.append(inter.getNom()).append(" <|.. ").append(classe.getNom()).append("\n");
            }
        }

        puml.append("@enduml\n");
        String doss = "Diagramme2Classes/diagramme/src/main/java/Export/";
        File dossier = new File(doss);
        if (!dossier.exists()) {
            System.out.println("Dossier introuvable");
        }
        // Écriture dans un fichier
        String fileName = doss + "diagramme.puml";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(puml.toString());
            System.out.println("Fichier généré : " + fileName);
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
        }

        return fileName;
    }

    /**
     * Méthode permettant de traduire les modificateurs au format Puml
     *
     * @param modificateurs : liste des modificateurs
     * @return modificateurs au format Puml
     */
    private String getPumlModificateur(List<String> modificateurs) {
        if (modificateurs.contains("private")) return "-";
        if (modificateurs.contains("protected")) return "#";
        return "+";
    }

    /**
     * Exporte un diagramme basé sur une liste de classes sous forme de fichier PNG.
     *
     * @param classes Liste des classes à inclure dans le diagramme.
     * @return
     */
    public String exportPng(List<Classe> classes) {
        // Chemin du fichier de sortie
        String cheminFichier = "Diagramme2Classes/diagramme/src/main/java/Export/diagramme.png";

        try {
            // Vérifie si la liste de classes est vide
            if (classes == null || classes.isEmpty()) {
                throw new IllegalArgumentException("La liste de classes ne peut pas être vide.");
            }

            // Création d'un Pane pour dessiner le diagramme
            Pane diagrammePane = new Pane();
            diagrammePane.setPrefSize(800, 600);

            // Ajoute les classes au diagramme (représentation graphique basique)
            double yPosition = 20;
            for (Classe classe : classes) {
                javafx.scene.text.Text text = new javafx.scene.text.Text(20, yPosition, "Classe : " + classe.getNom());
                diagrammePane.getChildren().add(text);
                yPosition += 30;
            }

            // Capture le contenu du Pane dans une image
            WritableImage image = new WritableImage((int) diagrammePane.getPrefWidth(), (int) diagrammePane.getPrefHeight());
            diagrammePane.snapshot(new SnapshotParameters(), image);

            // Prépare l'image pour l'écriture dans un fichier
            File fichierSortie = new File(cheminFichier);
            fichierSortie.getParentFile().mkdirs(); // Crée les dossiers nécessaires si inexistants
            ImageIO.write(toBufferedImage(image), "png", fichierSortie);

            System.out.println("Diagramme exporté avec succès : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exportation du diagramme : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur d'argument : " + e.getMessage());
        }
        return cheminFichier;
    }

    /**
     * Convertit une WritableImage en BufferedImage.
     *
     * @param writableImage L'image JavaFX à convertir.
     * @return Une instance de BufferedImage.
     */
    private BufferedImage toBufferedImage(WritableImage writableImage) {
        BufferedImage bufferedImage = new BufferedImage(
                (int) writableImage.getWidth(),
                (int) writableImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        PixelReader pixelReader = writableImage.getPixelReader();
        for (int x = 0; x < writableImage.getWidth(); x++) {
            for (int y = 0; y < writableImage.getHeight(); y++) {
                int argb = pixelReader.getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }

        return bufferedImage;
    }

}