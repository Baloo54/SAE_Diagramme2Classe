package diagramme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe ReadFile fournit des méthodes pour trouver et lister tous les fichiers .class dans un répertoire donné.
 */
public class ReadFile {

    /**
     * Trouve tous les fichiers .class dans le répertoire spécifié et ses sous-répertoires.
     *
     * @param directory le répertoire dans lequel rechercher les fichiers .class
     * @return une liste des chemins vers les fichiers .class trouvés
     */
    public List<String> findClassFiles(File directory) {
        List<String> classFilePaths = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            findClassFilesRecursive(directory, classFilePaths);
        } else {
            System.out.println("Le fichier fourni n'est pas un dossier valide : " + directory.getAbsolutePath());
        }

        return classFilePaths;
    }

    /**
     * Recherche récursivement les fichiers .class dans le dossier donné et ajoute leurs chemins à la liste fournie.
     *
     * @param folder le dossier dans lequel rechercher les fichiers .class
     * @param classFilePaths la liste pour stocker les chemins des fichiers .class trouvés
     */
    private void findClassFilesRecursive(File folder, List<String> classFilePaths) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                findClassFilesRecursive(file, classFilePaths);
            } else if (file.isFile() && file.getName().endsWith(".class")) {
                classFilePaths.add(file.getAbsolutePath());
            }
        }
    }

    /**
     * La méthode principale pour tester la fonctionnalité de la classe ReadFile.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        ReadFile finder = new ReadFile();
        File directory = new File("C:/Users/Valentino/IdeaProjects");

        List<String> classFiles = finder.findClassFiles(directory);

        if (!classFiles.isEmpty()) {
            System.out.println("Fichiers .class trouvés :");
            for (String filePath : classFiles) {
                System.out.println(filePath);
            }
        } else {
            System.out.println("Aucun fichier .class trouvé dans le dossier spécifié.");
        }
    }
}