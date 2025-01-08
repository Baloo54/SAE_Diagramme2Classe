package analyse;

import classes.*;
import classes.Package;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;

import analyse.loader.LoaderExterne;

/**
 * Classe réalisant l'analyse d'une classe Java.
 * Cette classe permet d'examiner dynamiquement la structure d'une classe, y compris ses attributs,
 * ses méthodes, ses modificateurs et ses interfaces implémentées.
 */
public class Analyseur {

    // Instance unique de l'analyseur (Singleton)
    private static Analyseur INSTANCE;

    // Classe en cours d'analyse
    private Class analyseClasse;

    /**
     * Constructeur privé pour implémenter le patron Singleton.
     */
    private Analyseur() {
        this.analyseClasse = null;
    }

    /**
     * Retourne l'instance unique de l'analyseur.
     * 
     * @return L'instance de l'analyseur.
     */
    public static Analyseur getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Analyseur();
        }
        return INSTANCE;
    }

    /**
     * Définit la classe à analyser.
     * 
     * @param classe Classe Java à analyser.
     */
    private void setClasseAnalyse(Class classe) {
        this.analyseClasse = classe;
    }   

    /**
     * Analyse une classe à partir de son chemin sous forme de chaîne de caractères.
     * 
     * @param chemin Chemin vers la classe à analyser.
     * @return Un objet Classe contenant les résultats de l'analyse.
     * @throws ClassNotFoundException Si la classe n'est pas trouvée.
     * @throws IOException 
     */
    public Classe analyserClasse(String chemin) throws ClassNotFoundException, IOException  {
        Class classe = LoaderExterne.getInstance().loadClassFromFile(chemin);
        setClasseAnalyse(classe);
        String packageNom = classe.getPackage().getName();
        Package p = null;
        if(packageNom == null) {
            packageNom = "";
        }else {
            for (String s : packageNom.split("\\.")) {
                packageNom = s;
            }
        }

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

        // Analyse des interfaces implémentées
        for (Class<?> interfaceClass : classe.getInterfaces()) {
            Interface inter = new Interface("interface", interfaceClass.getSimpleName());
            classeAnalysee.addInterface(inter);
        }
        return classeAnalysee;
    }

    /**
     * Analyse un attribut d'une classe.
     * 
     * @param field Attribut à analyser.
     * @return Un objet Attribut représentant l'attribut analysé.
     */
    private Attribut analyserAttribut(Field field) {
        Attribut attribut = new Attribut(field.getType().getSimpleName(), field.getName());
        attribut.addModificateur(Modifier.toString(field.getModifiers()));
        return attribut;
    }

    /**
     * Analyse une méthode d'une classe.
     * 
     * @param method Méthode à analyser.
     * @return Un objet Methode représentant la méthode analysée.
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
     * Retourne les modificateurs d'une classe sous forme de chaîne de caractères.
     * 
     * @param c Classe à analyser.
     * @return Liste des modificateurs.
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
     * Retourne la visibilité d'un modificateur.
     * 
     * @param modifiers Modificateurs sous forme d'entier.
     * @return La visibilité (public, private, protected ou package).
     */
    private static String getModifierVisibilite(int modifiers) {
        if (Modifier.isPublic(modifiers)) return "public";
        if (Modifier.isPrivate(modifiers)) return "private";
        if (Modifier.isProtected(modifiers)) return "protected";
        return "package";
    }

    /**
     * Récupère les modificateurs d'une méthode sous forme de liste de chaînes de caractères.
     *
     * @param m La méthode à analyser.
     * @return Une liste des modificateurs sous forme de chaînes (par exemple : "public", "static", "final").
     */
    private static ArrayList<String> getModifierMethode(Method m) {
        int modifiers = m.getModifiers();
        ArrayList<String> result = new ArrayList<>();
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
     * Retourne la classe parente.
     * 
     * @return La classe parente de la classe analysée.
     */
    public Class getClasseParent() {
        return this.analyseClasse.getSuperclass();
    }

    /**
     * Retourne les interfaces implémentées.
     * 
     * @return Tableau des interfaces implémentées.
     */
    public Class[] getInterfaces() {
        return this.analyseClasse.getInterfaces();
    }

    /**
     * Retourne le nom complet de la classe.
     * 
     * @return Nom complet de la classe analysée.
     */
    public String getNomClasse() {
        return this.analyseClasse.getName();
    }

    /**
     * Affiche les résultats de l'analyse dans la console.
     */
    public void afficherResultats() {
        System.out.println("Nom de la classe : " + this.analyseClasse.getName().replaceAll(".*\\.",""));
        System.out.println("Classe parente : " + this.analyseClasse.getSuperclass().getName().replaceAll(".*\\.",""));
        System.out.println("Attributs :");
        for (Field field : this.analyseClasse.getDeclaredFields()) {
            System.out.println(" - " + Modifier.toString(field.getModifiers()) + " " + field.getName());
        }
        System.out.println("Méthodes :");
        for (Method method : this.analyseClasse.getDeclaredMethods()) {
            System.out.println(" - " + Modifier.toString(method.getModifiers()) + " " + method.getName());
        }
    }
}
