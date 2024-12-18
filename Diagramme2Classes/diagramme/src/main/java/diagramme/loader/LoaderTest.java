package main.java.diagramme.loader;

import java.io.*;
import java.nio.file.*;

public class LoaderTest extends ClassLoader {

    // Méthode pour charger une classe depuis un fichier avec un chemin absolu
    @Override
    protected Class<?> findClass(String filePath) throws ClassNotFoundException {
        try {
            // Lire les octets du fichier .class
            Path path = Paths.get(filePath);
            byte[] classBytes = Files.readAllBytes(path);

            // Extraire le nom de la classe (ici, basé uniquement sur le nom du fichier sans extension)
            String className = extractClassName(filePath);

            // Définir la classe en utilisant les octets
            return defineClass("main.java.diagramme.loader.LoaderExterne", classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Impossible de charger la classe depuis le fichier : " + filePath, e);
        }
    }

    // Extraire un nom de classe basique à partir du chemin (sans les packages)
    private String extractClassName(String filePath) {
        String fileName = Paths.get(filePath).getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf('.')); // Retire l'extension .class
    }

    public static void main(String[] args) {
        try {
            // Création de l'instance du chargeur
            LoaderTest loader = new LoaderTest();

            // Chemin du fichier à charger
            String classFilePath = "C:/LoaderExterne.class"; // Remplacez par votre chemin exact

            // Charger la classe depuis le fichier
            Class<?> c = loader.findClass(classFilePath);

            // Afficher le nom de la classe chargée
            System.out.println("Classe chargée : " + c.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}