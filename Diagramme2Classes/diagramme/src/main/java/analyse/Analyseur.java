package analyse;

import classes.*;
import classes.Package;
import diagramme.loader.LoaderExterne;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Classe réalisant l'analyse d'une classe Java
 */
public class Analyseur {

    // Instance
    private static Analyseur INSTANCE;


    // Classe à analyser
    private Class analyseClasse;

    /**
     * Constructeur privé pour le patron singleton
     */
    private Analyseur() {
        this.analyseClasse = null;
    }

    /**
     * Méthode permettant de récupèrer l'instance
     *
     * @return l'instance
     */
    public static Analyseur getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Analyseur();
        }
        return INSTANCE;
    }


    /**
     * Méthode permettant de définire la classe à analyser
     *
     * @param classe : la classe à analyser
     */
    private void setClasseAnalyse(Class classe) {
        this.analyseClasse = classe;
    }

    /**
     * Analyse une classe à partir de son chemin en string
     *
     * @param chemin : chemin vers la classe à analyser
     * @return l'objet Classe contenant l'analyse
     * @throws ClassNotFoundException si la classe n'est pas trouvée
     */
    public Classe analyserClasse(String chemin) throws ClassNotFoundException {
        Class classe = LoaderExterne.getInstance().loadClass(chemin);
        setClasseAnalyse(classe);
        Package p = new Package(classe.getPackage().getName());
        Classe classeAnalysee = new Classe("class", classe.getSimpleName());
        p.ajouterClasse(classeAnalysee);

        // Analyse des modificateurs
        ArrayList<String> modifiers = getModifierClasse(classe);
        for (String modifier : modifiers) {
            classeAnalysee.addModificateur(modifier);

        }

        // Analyse des attributs
        for (Field field : classe.getDeclaredFields()) {
            Attribut attribut = analyserAttribut(field);
            classeAnalysee.addAttribut(attribut);
        }

        // Analyse des méthodes
        for (Method method : classe.getDeclaredMethods()) {
            Methode methode = analyserMethode(method);
            classeAnalysee.addMethode(methode);
        }

        // Analyse des interfaces
        for (Class<?> interfaceClass : classe.getInterfaces()) {
            Interface inter = new Interface("interface", interfaceClass.getSimpleName());
            classeAnalysee.addInterface(inter);
        }
        return classeAnalysee;
    }

    /**
     * Méthode permettant d'analyser un attribut
     *
     * @param field : attribut à analyser
     */
    private Attribut analyserAttribut(Field field) {
        ArrayList<String> modifiers = getModifierAttribut(field);
        return new Attribut(field.getType().getSimpleName(), field.getName());
    }

    /**
     * Méthode permettant d'analyser une méthode
     *
     * @param method : méthode à analyser
     */
    private Methode analyserMethode(Method method) {
        ArrayList<HashMap<String, String>> parametres = new ArrayList<>();
        for (Parameter param : method.getParameters()) {
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("type", param.getType().getSimpleName());
            paramMap.put("nom", param.getName());
            parametres.add(paramMap);
        }

        ArrayList<String> modifiers = getModifierMethode(method);
        return new Methode(method.getName(), method.getReturnType().getSimpleName(), parametres, modifiers);
    }

    /**
     * Méthode permettant de récupèrer les modificateurs sous forme de String
     *
     * @param modifiers : modificateurs sous forme d'entier
     */
    private static String getModifierVisibilite(int modifiers) {
        if (Modifier.isPublic(modifiers)) return "public";
        if (Modifier.isPrivate(modifiers)) return "private";
        if (Modifier.isProtected(modifiers)) return "protected";
        return "package";
    }

    /**
     * Méthode permettant de récupèrer les modificateurs d'une classe
     *
     * @param c : classe à analyser
     */
    private static ArrayList<String> getModifierClasse(Class c) {
        int modifiers = c.getModifiers();
        ArrayList<String> result = new ArrayList<>();
        result.add(getModifierVisibilite(modifiers));
        if (Modifier.isAbstract(modifiers)) result.add("abstract");
        if (Modifier.isFinal(modifiers)) result.add("final");
        return result;
    }

    /**
     * Méthode permettant de récupèrer les modificateurs d'une méthode
     *
     * @param m : méthode à analyser
     */
    private static ArrayList<String> getModifierMethode(Method m) {
        int modifiers = m.getModifiers();
        ArrayList<String> result = new ArrayList<>();
        result.add(getModifierVisibilite(modifiers));
        if (Modifier.isPublic(modifiers)) result.add("public");
        if (Modifier.isPrivate(modifiers)) result.add("private");
        if (Modifier.isProtected(modifiers)) result.add("protected");
        if (Modifier.isStatic(modifiers)) result.add("static");
        if (Modifier.isAbstract(modifiers)) result.add("abstract");
        if (Modifier.isFinal(modifiers)) result.add("final");
        if (Modifier.isSynchronized(modifiers)) result.add("synchronized");
        return result;
    }

    /**
     * Méthode permettant de Récupèrer les modificateurs d'un attribut
     *
     * @param f : attribut à analyser
     */
    private static ArrayList<String> getModifierAttribut(Field f) {
        int modifiers = f.getModifiers();
        ArrayList<String> result = new ArrayList<>();
        result.add(getModifierVisibilite(modifiers));
        if (Modifier.isPublic(modifiers)) result.add("public");
        if (Modifier.isPrivate(modifiers)) result.add("private");
        if (Modifier.isProtected(modifiers)) result.add("protected");
        if (Modifier.isStatic(modifiers)) result.add("static");
        if (Modifier.isFinal(modifiers)) result.add("final");
        if (Modifier.isTransient(modifiers)) result.add("transient");
        if (Modifier.isVolatile(modifiers)) result.add("volatile");
        if (f.getType().isArray()) result.add("array");
        return result;
    }

    /**
     * Méthode permettant de récupèrer la classe parente
     */
    public Class getClasseParent() {
        return this.analyseClasse.getSuperclass();
    }

    /**
     * Méthode permettant de récupèrer les interfaces
     */
    public Class[] getInterfaces() {
        return this.analyseClasse.getInterfaces();
    }

    /**
     * Méthode permettant de récupèrer le nom de la classe
     */
    public String getNomClasse() {
        return this.analyseClasse.getName();
    }

    /**
     * Méthode permettant d'afficher les resultats de l'analyse
     */
    public void afficherResultats() {
        System.out.println("Nom de la classe : " + this.analyseClasse.getName());
        System.out.println("Classe parente : " + this.analyseClasse.getSuperclass().getName());

        System.out.println("Attributs :");
        for (Field field : this.analyseClasse.getDeclaredFields()) {
            System.out.println(" - " + field.getName() + " : " + Modifier.toString(field.getModifiers()));
        }

        System.out.println("Méthodes :");
        for (Method method : this.analyseClasse.getDeclaredMethods()) {
            System.out.println(" - " + method.getName() + " : " + Modifier.toString(method.getModifiers()));
        }
    }
}


