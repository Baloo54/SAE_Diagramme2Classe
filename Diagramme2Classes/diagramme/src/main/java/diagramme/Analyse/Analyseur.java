package main.java.diagramme.Analyse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class Analyseur {
    private Class analyseClasse;

    public Analyseur(String nomClasse) throws ClassNotFoundException {
        this.analyseClasse = Class.forName(nomClasse);
    }

    public void analyseNouvelleClasse(String nomClasse) throws ClassNotFoundException {
        this.analyseClasse = Class.forName(nomClasse);
    }

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

    private static String getModifierVisibilite(int i) {
        switch (i) {
            case Modifier.PUBLIC:
                return "public";
            case Modifier.PRIVATE:
                return "private";
            case Modifier.PROTECTED:
                return "protected";
            default:
                return "Erreur";
        }
    }

    private static ArrayList<String> getModifierClasse(int i) {
        ArrayList<String> modifiers = new ArrayList<>();
        modifiers.add(getModifierVisibilite(i));
        if (Modifier.isAbstract(i)) {
            modifiers.add("abstract");
        } else if (Modifier.isFinal(i)) {
            modifiers.add("final");
        }
        if (Modifier.isStrict(i)) {
            modifiers.add("strictfp");
        }
        return modifiers;
    }

    private static ArrayList<String> getModifierMethode(int i) {
        ArrayList<String> modifiers = new ArrayList<>();
        modifiers.add(getModifierVisibilite(i));
        if (Modifier.isStatic(i)) {
            modifiers.add("static");
        }
        if (Modifier.isAbstract(i)) {
            modifiers.add("abstract");
        } else {
            if (Modifier.isNative(i)) {
                modifiers.add("native");
            } else if (Modifier.isStrict(i)) {
                modifiers.add("strictfp");
            }
            if (Modifier.isFinal(i)) {
                modifiers.add("final");
            }
            if (Modifier.isSynchronized(i)) {
                modifiers.add("synchronized");
            }
        }
        return modifiers;
    }

    private static ArrayList<String> getModifierAttribut(int i) {
        ArrayList<String> modifiers = new ArrayList<>();
        modifiers.add(getModifierVisibilite(i));
        if (Modifier.isStatic(i)) {
            modifiers.add("static");
        }
        if (Modifier.isFinal(i)) {
            modifiers.add("final");
        }
        if (Modifier.isTransient(i)) {
            modifiers.add("transient");
        }
        if (Modifier.isVolatile(i)) {
            modifiers.add("volatile");
        }
        return modifiers;
}
}
