package main.java.diagramme.Analyse;

import java.lang.reflect.*;

public class Analyseur {
    public static void analyseClasse(String nomClasse) {
        try {
            Class cl = Class.forName(nomClasse);
            System.out.println("Classe: " + cl.getName());
            afficherAttributs(cl);
            afficherMethodes(cl);
            Package p = cl.getPackage();
            System.out.println("Package: " + p.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    

    public static void afficherAttributs(Class cl) {
        Field[] fields = cl.getDeclaredFields();
        System.out.println("Attributs:");
        for (Field f : fields) {
            System.out.println(f.getName());
        }
    }

    public static void afficherMethodes(Class cl) {
        Method[] methods = cl.getDeclaredMethods();
        System.out.println("Méthodes:");
        for (Method m : methods) {
            System.out.println(m.getName());
        }
    }

    public static void main(String[] args) {
        analyseClasse("main.java.diagramme.Analyse.Analyseur");
    }
}
