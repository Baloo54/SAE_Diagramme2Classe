package analyse;

import analyse.loader.LoaderExterne;
import classes.Attribut;
import classes.Classe;
import classes.Interface;
import classes.Methode;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe réalisant l'analyse d'une classe Java.
 * Cette classe permet d'examiner dynamiquement la structure d'une classe, y compris ses attributs,
 * ses méthodes, ses modificateurs et ses interfaces implémentées.
 */
public class Analyseur {

    // Instance unique de l'analyseur (Singleton)
    private static Analyseur INSTANCE;

    /**
     * Retourne l'instance unique de l'analyseur.
     *
     * @return L'instance de l'analyseur.
     */
    public static Analyseur getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Analyseur();
        }
        return INSTANCE;
    }

    /**
     * Analyse une classe à partir de son chemin sous forme de chaîne de caractères.
     *
     * @param chemin Chemin vers la classe à analyser.
     * @return Un objet Classe contenant les résultats de l'analyse.
     * @throws ClassNotFoundException Si la classe n'est pas trouvée.
     * @throws IOException
     */
    public Interface analyserClasse(String chemin) throws ClassNotFoundException, IOException {
        Class classe = LoaderExterne.getInstance().loadClassFromFile(chemin);
        String type = classe.isInterface() ? "interface" : "classe";
        Interface classeAnalysee;
        if (type.equals("classe")) {
            classeAnalysee = new Classe(type, classe.getSimpleName(), classe.getPackageName());
        } else {
            classeAnalysee = new Interface(type, classe.getSimpleName(), classe.getPackageName());
        }

        // Analyse des modificateurs
        ArrayList<String> modifiers = getModifierClasse(classe);
        for (String modifier : modifiers) {
            classeAnalysee.addModificateur(modifier);
        }

        // Analyse des attributs
        for (Field field : classe.getDeclaredFields()) {
            Attribut attribut = analyserAttribut(field);
            classeAnalysee.addAttribut(attribut);
        }

        // Analyse des méthodes
        for (Method method : classe.getDeclaredMethods()) {
            Methode methode = analyserMethode(method);
            classeAnalysee.addMethode(methode);
        }

        // Analyse des interfaces implémentées
        for (Class<?> interfaceClass : classe.getInterfaces()) {
            Interface inter = new Interface("interface", interfaceClass.getSimpleName(), interfaceClass.getPackageName());
            classeAnalysee.addInterface(inter);
        }
        if (type.equals("classe") && classe.getSuperclass() != null) {
            ((Classe) classeAnalysee).setClasseParent(new Classe("classe", classe.getSuperclass().getSimpleName(), classe.getSuperclass().getPackageName()));
        }

        return classeAnalysee;
    }

    /**
     * Analyse un attribut d'une classe.
     *
     * @param field Attribut à analyser.
     * @return Un objet Attribut représentant l'attribut analysé.
     */
    private Attribut analyserAttribut(Field field) {
        Attribut attribut = new Attribut(field.getType().getSimpleName(), field.getName());
        attribut.addModificateur(Modifier.toString(field.getModifiers()));
        return attribut;
    }

    /**
     * Analyse une méthode d'une classe.
     *
     * @param method Méthode à analyser.
     * @return Un objet Methode représentant la méthode analysée.
     */
    private Methode analyserMethode(Method method) {
        ArrayList<HashMap<String, String>> parametres = new ArrayList<>();
        for (Parameter param : method.getParameters()) {
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("type", param.getType().getSimpleName());
            paramMap.put("nom", param.getName());
            parametres.add(paramMap);
        }

        ArrayList<String> modifiers = getModifierMethode(method);
        return new Methode(method.getName(), method.getReturnType().getSimpleName(), parametres, modifiers);
    }

    /**
     * Retourne les modificateurs d'une classe sous forme de chaîne de caractères.
     *
     * @param c Classe à analyser.
     * @return Liste des modificateurs.
     */
    private static ArrayList<String> getModifierClasse(Class c) {
        int modifiers = c.getModifiers();
        ArrayList<String> result = new ArrayList<>();
        result.add(getModifierVisibilite(modifiers));
        if (Modifier.isAbstract(modifiers)) result.add("abstract");
        if (Modifier.isFinal(modifiers)) result.add("final");
        return result;
    }

    /**
     * Retourne la visibilité d'un modificateur.
     *
     * @param modifiers Modificateurs sous forme d'entier.
     * @return La visibilité (public, private, protected ou package).
     */
    private static String getModifierVisibilite(int modifiers) {
        if (Modifier.isPublic(modifiers)) return "public";
        if (Modifier.isPrivate(modifiers)) return "private";
        if (Modifier.isProtected(modifiers)) return "protected";
        return "package";
    }

    /**
     * Récupère les modificateurs d'une méthode sous forme de liste de chaînes de caractères.
     *
     * @param m La méthode à analyser.
     * @return Une liste des modificateurs sous forme de chaînes (par exemple : "public", "static", "final").
     */
    private static ArrayList<String> getModifierMethode(Method m) {
        int modifiers = m.getModifiers();
        ArrayList<String> result = new ArrayList<>();
        if (Modifier.isPublic(modifiers)) result.add("public");
        else if (Modifier.isPrivate(modifiers)) result.add("private");
        else if (Modifier.isProtected(modifiers)) result.add("protected");
        if (Modifier.isStatic(modifiers)) result.add("static");
        if (Modifier.isAbstract(modifiers)) result.add("abstract");
        if (Modifier.isFinal(modifiers)) result.add("final");
        if (Modifier.isSynchronized(modifiers)) result.add("synchronized");
        return result;
    }


    /**
     * Affiche les résultats de l'analyse dans la console.
     */
    public void afficherResultats(Interface analyseClasse) {
        System.out.println("Nom de la classe : " + analyseClasse.getNom());
        if (analyseClasse instanceof Classe) {
            System.out.println("Classe parente : " + ((Classe) analyseClasse).getClasseParent().getNom());
        }
        System.out.println("Attributs :");
        for (Attribut field : analyseClasse.getAttributs()) {
            System.out.println(" - " + field.getModificateurs() + " " + field.getNom());
        }
        System.out.println("Méthodes :");
        for (Methode method : analyseClasse.getMethodes()) {
            System.out.println(" - " + method.getModificateurs() + " " + method.getNom());
        }
    }

    /**
     * Méthode permettant d'exporter au format Puml
     *
     * @param classeAnalysee : l'objet Classe analysé
     * @return code Puml
     */
    public String exportPuml(Classe classeAnalysee) {
        StringBuilder puml = new StringBuilder();
        puml.append("@startuml\n");

        for (Attribut attribut : classeAnalysee.getAttributs()) {
            ArrayList<String> modificateurs = (ArrayList<String>) attribut.getModificateurs();
            String visibility = getPumlModificateur(modificateurs);
            puml.append("    ")
                    .append(visibility)
                    .append(" ")
                    .append(attribut.getNom())
                    .append(" : ")
                    .append(attribut.getType())
                    .append("\n");
        }

        for (Methode methode : classeAnalysee.getMethodes()) {
            String visibility = getPumlModificateur(methode.getModificateurs());
            puml.append("    ")
                    .append(visibility)
                    .append(" ")
                    .append(methode.getNom())
                    .append("(");

            List<HashMap<String, String>> parametres = methode.getParametres();
            for (int i = 0; i < parametres.size(); i++) {
                HashMap<String, String> param = parametres.get(i);
                puml.append(param.get("nom"))
                        .append(" : ")
                        .append(param.get("type"));
                if (i < parametres.size() - 1) {
                    puml.append(", ");
                }
            }

            puml.append(") : ").append(methode.getTypeRetour()).append("\n");
        }
        puml.append("}\n");

        if (classeAnalysee instanceof Classe) {
            Classe classe = (Classe) classeAnalysee;
            if (classe.getClasseParent() != null) {
                puml.append(classe.getClasseParent().getNom())
                        .append(" <|-- ")
                        .append(classeAnalysee.getNom())
                        .append("\n");
            }
        }

        for (Interface inter : classeAnalysee.getInterfaces()) {
            puml.append("interface ").append(inter.getNom()).append(" {\n");

            for (Methode methode : inter.getMethodes()) {
                puml.append("    + ").append(methode.getNom()).append("()\n");
            }
            puml.append("}\n");

            puml.append(inter.getNom()).append(" <|.. ").append(classeAnalysee.getNom()).append("\n");
        }
        puml.append("@enduml\n");

        // Écriture dans un fichier
        String fileName = classeAnalysee.getNom() + ".puml";
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
     *
     * @param diagrammePane le Pane contenant le diagramme à exporter.
     * @param cheminFichier le chemin de sortie du fichier PNG.
     */
    public void exporterDiagrammeEnPNG(Pane diagrammePane, String cheminFichier) {
        try {
            // Vérifie si le Pane est non nul
            if (diagrammePane == null) {
                throw new IllegalArgumentException("Le Pane contenant le diagramme ne peut pas être null.");
            }

            // Capture le contenu du Pane dans une image
            WritableImage image = new WritableImage((int) diagrammePane.getWidth(), (int) diagrammePane.getHeight());
            diagrammePane.snapshot(new SnapshotParameters(), image);

            // Prépare l'image pour l'écriture dans un fichier
            File fichierSortie = new File(cheminFichier);
            ImageIO.write(toBufferedImage(image), "png", fichierSortie);

            System.out.println("Diagramme exporté avec succès : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'exportation du diagramme : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur d'argument : " + e.getMessage());
        }
    }

    /**
     * Convertit une WritableImage en BufferedImage
     *
     * @param writableImage L'image capturée.
     * @return Un BufferedImage prêt pour l'écriture.
     */
    private static java.awt.image.BufferedImage toBufferedImage(WritableImage writableImage) {
        int largeur = (int) writableImage.getWidth();
        int hauteur = (int) writableImage.getHeight();
        java.awt.image.BufferedImage bufferedImage = new java.awt.image.BufferedImage(largeur, hauteur, java.awt.image.BufferedImage.TYPE_INT_ARGB);

        PixelReader pixelReader = writableImage.getPixelReader();
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
            }
        }
        return bufferedImage;
    }

    /**
     * Exporte le diagramme affiché dans un Pane JavaFX sous forme de fichier PDF.
     * Utilise Apache PDFBox pour générer le fichier PDF.
     *
     * @param diagrammePane Le Pane contenant le diagramme.
     * @param cheminFichier Le chemin de sortie du fichier PDF.
     */
    public void exporterDiagrammeEnPDF(Pane diagrammePane, String cheminFichier) {
        try {
            // Capture le contenu du Pane dans une image
            WritableImage image = new WritableImage((int) diagrammePane.getWidth(), (int) diagrammePane.getHeight());
            diagrammePane.snapshot(new SnapshotParameters(), image);

            // Convertit l'image en BufferedImage
            BufferedImage bufferedImage = toBufferedImage(image);

            // Sauvegarde temporairement l'image comme fichier PNG
            File tempFile = File.createTempFile("diagramme", ".png");
            ImageIO.write(bufferedImage, "png", tempFile);

            // Création du document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Ajout de l'image au document PDF
            PDImageXObject pdImage = PDImageXObject.createFromFile(tempFile.getAbsolutePath(), document);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(pdImage, 100, 500, 400, 300); // Position et taille
            contentStream.close();

            // Sauvegarde du document PDF
            document.save(cheminFichier);
            document.close();

            System.out.println("Diagramme exporté avec succès en PDF : " + cheminFichier);

            // Suppression du fichier temporaire
            tempFile.delete();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exportation en PDF : " + e.getMessage());
        }
    }
}