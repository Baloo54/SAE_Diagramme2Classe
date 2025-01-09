package classes;

import javafx.scene.SnapshotParameters;
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
     * Exporte le diagramme affiché dans un Pane JavaFX sous forme de fichier PNG.
     * @param diagrammePane Le Pane contenant le diagramme à exporter.
     */
    public void exportPng(Pane diagrammePane) {
        // Chemin fixe où le fichier PNG sera sauvegardé
        String cheminFichier = "Diagramme2Classes/diagramme/src/main/java/Export/diagramme.png";

        try {
            // Vérifie si le Pane est non nul
            if (diagrammePane == null) {
                throw new IllegalArgumentException("Le Pane contenant le diagramme ne peut pas être null.");
            }

            // Capture le contenu du Pane dans une image
            WritableImage image = new WritableImage((int) diagrammePane.getWidth(), (int) diagrammePane.getHeight());
            SnapshotParameters snapshotParams = new SnapshotParameters();
            diagrammePane.snapshot(snapshotParams, image);

            // Convertit l'image en BufferedImage pour pouvoir l'écrire avec ImageIO
            BufferedImage bufferedImage = toBufferedImage(image);

            // Crée le fichier et le répertoire si nécessaire
            File fichierSortie = new File(cheminFichier);
            File parentDir = fichierSortie.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs(); // Crée les répertoires parents si nécessaire
            }

            // Écrit l'image dans le fichier
            ImageIO.write(bufferedImage, "png", fichierSortie);

            System.out.println("Diagramme exporté avec succès : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exportation du diagramme : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur d'argument : " + e.getMessage());
        }
    }

    /**
     * Convertit une WritableImage en BufferedImage.
     *
     * @param writableImage L'image capturée.
     * @return Un BufferedImage prêt pour l'écriture.
     */
    private static BufferedImage toBufferedImage(WritableImage writableImage) {
        int largeur = (int) writableImage.getWidth();
        int hauteur = (int) writableImage.getHeight();
        BufferedImage bufferedImage = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
        javafx.scene.image.PixelReader pixelReader = writableImage.getPixelReader();
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
            }
        }
        return bufferedImage;
    }
}