package diagramme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {


    public List<String> findClassFiles(File directory) {
        List<String> classFilePaths = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            findClassFilesRecursive(directory, classFilePaths);
        } else {
            System.out.println("Le fichier fourni n'est pas un dossier valide : " + directory.getAbsolutePath());
        }

        return classFilePaths;
    }


    private void findClassFilesRecursive(File folder, List<String> classFilePaths) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                findClassFilesRecursive(file, classFilePaths);
            } else if (file.isFile() && file.getName().endsWith(".class")) {
                classFilePaths.add(file.getAbsolutePath());
            }
        }
    }

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
