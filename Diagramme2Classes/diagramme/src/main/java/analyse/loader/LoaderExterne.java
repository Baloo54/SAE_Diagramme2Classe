package analyse.loader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoaderExterne extends ClassLoader {

    // Singleton pour gérer une seule instance de LoaderExterne
    private static final LoaderExterne loader = new LoaderExterne();

    // Cache pour les classes chargées
    private final Map<String, Class<?>> loadedClasses = new HashMap<>();

    /**
     * Charge une classe depuis un fichier .class en vérifiant le package et si elle est déjà chargée.
     *
     * @param filePath Chemin absolu du fichier .class
     * @return La classe chargée
     * @throws ClassNotFoundException Si la classe ne peut être trouvée ou chargée
     * @throws IOException            Si une erreur d'E/S survient
     */
    public Class<?> loadClassFromFile(String filePath) throws ClassNotFoundException, IOException {
        // Vérifier si le fichier est valide
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new ClassNotFoundException("Le fichier spécifié est introuvable : " + filePath);
        }

        // Extraire le nom de la classe à partir du chemin ou du contenu
        String className = SimpleDecompiler.getNomClasse(filePath);

        // Vérifier si la classe est déjà chargée par le parent
        try {
            return getParent().loadClass(className);
        } catch (ClassNotFoundException e) {
            // Ignorer si le parent ne trouve pas la classe
        }

        // Vérifier si la classe est déjà chargée localement
        Class<?> loadedClass = findLoadedClass(className);
        if (loadedClass != null) {
            return loadedClass;
        }

        // Lire le fichier .class en tant que tableau d'octets
        byte[] classData = readFileAsBytes(file);

        // Charger la classe principale
        loadedClass = defineClass(className, classData, 0, classData.length);

        // Ajouter la classe au cache
        loadedClasses.put(className, loadedClass);

        return loadedClass;
    }

    /**
     * Lit le fichier donné sous forme de tableau de bytes.
     *
     * @param file Fichier à lire
     * @return Tableau d'octets contenant le contenu du fichier
     * @throws IOException En cas de problème de lecture
     */
    private byte[] readFileAsBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }

    /**
     * Retourne une instance singleton de LoaderExterne.
     *
     * @return Instance unique de LoaderExterne
     */
    public static LoaderExterne getInstance() {
        return loader;
    }
}
