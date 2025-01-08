package  analyse.loader;

import java.io.*;

public class LoaderExterne extends ClassLoader {
    /**
     * Charge une classe depuis un fichier .class en vérifiant le package.
     *
     * @param filePath Chemin absolu du fichier .class
     * @return La classe chargée
     * @throws ClassNotFoundException Si la classe ne peut être trouvée ou chargée
     * @throws IOException            Si une erreur d'E/S survient
     */
    public Class<?> loadClassFromFile(String filePath) throws ClassNotFoundException, IOException {
        try {
            return Class.forName(filePath);
        } catch (ClassNotFoundException e) {
            getInstance();
            File file = new File(filePath);
            if (!file.exists() || !file.isFile()) {
                throw new ClassNotFoundException("Le fichier spécifié est introuvable : " + filePath);
            }
            // Lire le fichier .class en tant que tableau de bytes
            byte[] classData = readFileAsBytes(file);

            // Extraire le nom de la classe à partir du chemin et du contenu

            String nomClass = SimpleDecompiler.getNomClasse(filePath);
            // Charger la classe
            return defineClass(nomClass, classData, 0, classData.length);

        }
    }

    /**
     * Lit le fichier donné sous forme de tableau de bytes.
     */
    private byte[] readFileAsBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return data;
        }
    }
    public static LoaderExterne getInstance() {
        return new LoaderExterne();
    }
}
