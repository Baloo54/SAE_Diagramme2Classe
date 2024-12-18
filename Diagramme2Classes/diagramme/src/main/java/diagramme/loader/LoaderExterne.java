package main.java.diagramme.loader;

import main.java.diagramme.exception.FichierIllisibleException;
import main.java.diagramme.exception.FichierIncompatibleException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoaderExterne extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
        LoaderExterne a = new LoaderExterne();
            Class c = a.findClass("C:/Users/Username/Desktop/7.docx");
        System.out.println(c.getName());
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        if (!name.endsWith(".class")) {
            throw new FichierIncompatibleException();
        }
        return findClass(name);
    }

    //trouve le package
//lecture du fichier obligatoire ?
    //nom dossier d'un package toujours en entier
    //. peut être sur une autre ligne
    //commence par package toujours écrit en entier
    //fini par ;
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Path path = Paths.get(name);
        byte[] b;
        System.out.println(path);
        try {
        BufferedReader reader = new BufferedReader(new FileReader(name));
        String filename = "";
        while (!filename.endsWith(";")){
            reader.readLine();

        }
            File f = new File(name);
            b = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new FichierIllisibleException();
        }

        return defineClass("", b, 0, b.length);
    }
}