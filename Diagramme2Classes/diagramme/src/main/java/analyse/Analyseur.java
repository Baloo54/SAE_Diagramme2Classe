package analyse;

import classes.Diagramme;
import classes.Package;
import diagramme.loader.LoaderExterne;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Classe réalisant l'analyse d'une classe Java
 */
public class Analyseur {

    /**
     * Attribut contenant la classe à analyser
     */
    private Class analyseClasse;

    /**
     * Singleton de la classe créant une instance unique
     */
    public static Analyseur instance = new Analyseur();

    /**
     * Méthode permettant récupérer grâce à un chemin le package associé
     * @param chemin : chemin du package
     * @param d : diagramme associé
     * @return : le package créé
     */
    public Package construireClasse(String chemin, Diagramme d) throws ClassNotFoundException {
        Class classe = LoaderExterne.getInstance().loadClass(chemin);
        String pack = classe.getName().replace(classe.getSimpleName(), "");
        Package p = new Package(pack);
        return p;
    }

    /**
     * Méthodes permettant d'analyser une classe
     */

    /**
     * Méthode permettant d'analyser une classe
     * @param classe : classe à analyser
     */
    public static void afficherAttributs(Class cl) {
        Field[] fields = cl.getDeclaredFields();
        System.out.println("Attributs:");
        for (Field f : fields) {
            System.out.println(f.getName());
        }

    }

    /**
     * Méthode permettant d'afficher les méthodes d'une classe
     * @param cl : classe à analyser
     */
    public static void afficherMethodes(Class cl) {
        Method[] methods = cl.getDeclaredMethods();
        System.out.println("Méthodes:");
        for (Method m : methods) {
            System.out.println(m.getName());
        }
    }

    /**
     * Méthode permettant de traduire les modificateurs en string
     * @param i : modificateur
     * @return : le modificateur en string
     */
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

    /**
     * Méthode permettant de récupérer les modificateurs d'une classe
     * @param c : classe à analyser
     * @return : les modificateurs de la classe en int
     */
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

    /**
     * Méthode permettant de récupérer les modificateurs d'une méthode
     * @param m : méthode à analyser
     * @return : les modificateurs de la méthode en int
     */
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

    /**
     * Méthode permettant de récupérer les modificateurs d'un attribut
     * @param f : attribut à analyser
     * @return : les modificateurs de l'attribut en int
     */
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
J
    /**
     * Méthode permettant de récupérer les classes Parents
     * @return : Classes Parents
     */
    public Class getClasseParent() {
        return this.analyseClasse.getSuperclass();
    }

    /**
     * Méthode permettant de récupérer les Interfaces
     * @return : Interfaces
     */
    public Class[] getInterfaces() {
        return this.analyseClasse.getInterfaces();
    }

    /**
     * Méthode permettant de récupérer le nom de la classe
     * @return : Nom de la classe
     */
    public String getNomClasse() {
        return this.analyseClasse.getName();
    }

    /**
     * Méthode permettant de trier les attributs par modificateur dans une map
     * @return : Map triée contenant les attributs triés
     */
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

    /**
     * Méthode permettant de trier les méthodes par modificateur dans une map
     * @return : Map triée contenant les méthodes triées
     */
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


    /**
     * Méthode permettant d'afficher les attributs de la classe triés
     */
    public  void afficherTriAttributs(Map<String, List<Field>> tri) {
        for (Map.Entry<String, List<Field>> entry : tri.entrySet()) {
            System.out.println("Attributs " + entry.getKey() + ":");
            for (Field field : entry.getValue()) {
                System.out.println(" - " + field.getName());
            }
        }
    }

    /**
     * Méthode permettant d'afficher les méthodes de la classe triées
     */
    public static void afficherTriMethodes(Map<String, List<Method>> tri) {
        for (Map.Entry<String, List<Method>> entry : tri.entrySet()) {
            System.out.println("Méthodes " + entry.getKey() + ":");
            for (Method method : entry.getValue()) {
                System.out.println(" - " + method.getName());
            }
        }
    }

    /**
     * Méthode permettant de récupérer l'instance
     */
    public static Analyseur getInstance() {
        return instance;
    }
}
