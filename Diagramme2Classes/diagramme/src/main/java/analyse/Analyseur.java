package analyse;

import analyse.loader.LoaderExterne;
import classes.Attribut;
import classes.Classe;
import classes.Interface;
import classes.Methode;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe réalisant l'analyse d'une classe Java.
 * Cette classe permet d'examiner dynamiquement la structure d'une classe, y compris ses attributs,
 * ses méthodes, ses modificateurs et ses interfaces implémentées.
 */
public class Analyseur {

    // Instance unique de l'analyseur (Singleton)
    private static Analyseur instance;

    /**
     * Retourne l'instance unique de l'analyseur.
     *
     * @return L'instance de l'analyseur.
     */
    public static Analyseur getInstance() {
        if (instance == null) {
            instance = new Analyseur();
        }
        return instance;
    }

    /**
     * Analyse une classe à partir de son chemin sous forme de chaîne de caractères.
     *
     * @param chemin Chemin vers la classe à analyser.
     * @return Un objet Classe contenant les résultats de l'analyse.
     * @throws ClassNotFoundException Si la classe n'est pas trouvée.
     * @throws IOException
     */
    public Interface analyserClasse(String chemin) throws ClassNotFoundException, IOException {
        Class<?> classe = LoaderExterne.getInstance().loadClassFromFile(chemin);
        String type = classe.isInterface() ? "interface" : "classe";
        Interface classeAnalysee;
        if (type.equals("classe")) {
            classeAnalysee = new Classe(type, classe.getSimpleName(), classe.getPackageName());
        } else {
            classeAnalysee = new Interface(type, classe.getSimpleName(), classe.getPackageName());
        }

        // Analyse des modificateurs
        ArrayList<String> modifiers = getModifierClasse(classe);
        for (String modifier : modifiers) {
            classeAnalysee.addModificateur(modifier);
        }

        try {
            // Analyse des attributs
            for (Field field : classe.getDeclaredFields()) {
                Attribut attribut = analyserAttribut(field);
                classeAnalysee.addAttribut(attribut);
            }
        } catch (java.lang.NoClassDefFoundError e) {
            // Capture d'autres exceptions si nécessaire, mais on ne fait rien
        }
        try {
            // Analyse des méthodes
            for (Method method : classe.getDeclaredMethods()) {
                Methode methode = analyserMethode(method);
                classeAnalysee.addMethode(methode);
            }
        } catch (java.lang.NoClassDefFoundError e) {
            // Capture d'autres exceptions si nécessaire, mais on ne fait rien
        }
        
        // Analyse des interfaces implémentées
        for (Class<?> interfaceClass : classe.getInterfaces()) {
            Interface inter = new Interface("interface", interfaceClass.getSimpleName(), interfaceClass.getPackageName());
            classeAnalysee.addInterface(inter);
        }
        if (type.equals("classe") && classe.getSuperclass() != null) {
            ((Classe) classeAnalysee).setClasseParent(new Classe("classe", classe.getSuperclass().getSimpleName(), classe.getSuperclass().getPackageName()));
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
    private static ArrayList<String> getModifierClasse(Class<?> c) {
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
        else if (Modifier.isPrivate(modifiers)) result.add("private");
        else if (Modifier.isProtected(modifiers)) result.add("protected");
        if (Modifier.isStatic(modifiers)) result.add("static");
        if (Modifier.isAbstract(modifiers)) result.add("abstract");
        if (Modifier.isFinal(modifiers)) result.add("final");
        if (Modifier.isSynchronized(modifiers)) result.add("synchronized");
        return result;
    }


    /**
     * Affiche les résultats de l'analyse dans la console.
     */
    public void afficherResultats(Interface analyseClasse) {
        System.out.println("Nom de la classe : " + analyseClasse.getNom());
        if (analyseClasse instanceof Classe) {
            System.out.println("Classe parente : " + ((Classe) analyseClasse).getClasseParent().getNom());
        }
        System.out.println("Attributs :");
        for (Attribut field : analyseClasse.getAttributs()) {
            System.out.println(" - " + field.getModificateurs() + " " + field.getNom());
        }
        System.out.println("Méthodes :");
        for (Methode method : analyseClasse.getMethodes()) {
            System.out.println(" - " + method.getModificateurs() + " " + method.getNom());
        }
    }

}