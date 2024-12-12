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

    public static Map<String, List<Field>> trierAttributsParModificateur(Field[] fields) {
        Map<String, List<Field>> tri = new HashMap<>();
        tri.put("public", new ArrayList<>());
        tri.put("protected", new ArrayList<>());
        tri.put("private", new ArrayList<>());
        tri.put("package-private", new ArrayList<>());

        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isPublic(mod)) {
                tri.get("public").add(field);
            } else if (Modifier.isProtected(mod)) {
                tri.get("protected").add(field);
            } else if (Modifier.isPrivate(mod)) {
                tri.get("private").add(field);
            } else {
                tri.get("package-private").add(field);
            }
        }

        return tri;
    }

    public static Map<String, List<Method>> trierMethodesParModificateur(Method[] methods) {
        Map<String, List<Method>> tri = new HashMap<>();
        tri.put("public", new ArrayList<>());
        tri.put("protected", new ArrayList<>());
        tri.put("private", new ArrayList<>());
        tri.put("package-private", new ArrayList<>());

        for (Method method : methods) {
            int mod = method.getModifiers();
            if (Modifier.isPublic(mod)) {
                tri.get("public").add(method);
            } else if (Modifier.isProtected(mod)) {
                tri.get("protected").add(method);
            } else if (Modifier.isPrivate(mod)) {
                tri.get("private").add(method);
            } else {
                tri.get("package-private").add(method);
            }
        }

        return tri;
    }

    public static void afficherTriAttributs(Map<String, List<Field>> tri) {
        for (Map.Entry<String, List<Field>> entry : tri.entrySet()) {
            System.out.println("Attributs " + entry.getKey() + ":");
            for (Field field : entry.getValue()) {
                System.out.println(" - " + field.getName());
            }
        }
    }

    public static void afficherTriMethodes(Map<String, List<Method>> tri) {
        for (Map.Entry<String, List<Method>> entry : tri.entrySet()) {
            System.out.println("Méthodes " + entry.getKey() + ":");
            for (Method method : entry.getValue()) {
                System.out.println(" - " + method.getName());
            }
        }
    }

    public static List<Package> getPackages() {

        Package[] packages = Package.getPackages();
        return Arrays.asList(packages);
    }
}
