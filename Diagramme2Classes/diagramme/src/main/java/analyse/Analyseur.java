package analyse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Analyseur {
    private Class analyseClasse;
    public Analyseur instance = new Analyseur();

    public Analyseur() throws ClassNotFoundException {
       // this.analyseClasse = LoaderExterne.getInstance().loadClass(chemin);
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

    private static String getModifierVisibilite(int i) {
        switch (i) {
            case Modifier.PUBLIC:
                return "public";
            case Modifier.PRIVATE:
                return "private";
            default:
                return "protected";
        }
    }

    private static ArrayList<String> getModifierClasse(Class c) {
        int i = c.getModifiers();
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

    private static ArrayList<String> getModifierMethode(Method m) {
        int i = m.getModifiers();
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

    private static ArrayList<String> getModifierAttribut(Field f) {
        int i = f.getModifiers();
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
        if(f.getType().isArray()){
            modifiers.add("array");
        }
        return modifiers;
    }
    public Class getClasseParent() {
        return this.analyseClasse.getSuperclass();
    }

    public Class[] getInterfaces() {
        return this.analyseClasse.getInterfaces();
    }

    public String getNomClasse() {
        return this.analyseClasse.getName();
    }

    public  Map<String, List<Field>> trierAttributsParModificateur() {
        Field[] fields = this.analyseClasse.getDeclaredFields();
        Map<String, List<Field>> tri = new HashMap<>();
        tri.put("public", new ArrayList<>());
        tri.put("protected", new ArrayList<>());
        tri.put("private", new ArrayList<>());

        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isPublic(mod)) {
                tri.get("public").add(field);
            } else if (Modifier.isProtected(mod)) {
                tri.get("protected").add(field);
            } else{
                tri.get("private").add(field);
            }
        }

        return tri;
    }

    public  Map<String, List<Method>> trierMethodesParModificateur(Method[] methods) {
        Map<String, List<Method>> tri = new HashMap<>();
        tri.put("public", new ArrayList<>());
        tri.put("protected", new ArrayList<>());
        tri.put("private", new ArrayList<>());
        for (Method method : methods) {
            int mod = method.getModifiers();
            if (Modifier.isPublic(mod)) {
                tri.get("public").add(method);
            } else if (Modifier.isProtected(mod)) {
                tri.get("protected").add(method);
            } else{
                tri.get("private").add(method);
            }
        }
        return tri;
    }

    public  void afficherTriAttributs(Map<String, List<Field>> tri) {
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

    public Analyseur getInstance() {
        return instance;
    }

    public static Package[] getPackages() {
        return Package.getPackages();
    }
}
