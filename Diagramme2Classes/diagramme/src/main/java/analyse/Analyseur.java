package analyse;

import classes.Diagramme;
import classes.Package;
import classes.Visible;
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

    /**
     * Attribut contenant la classe à analyser
     */
    private Class analyseClasse;
    private static final Analyseur instance = new Analyseur();

    private Analyseur() {
    }

    /**
     * Singleton de la classe créant une instance unique
     */
    public static Analyseur instance = new Analyseur();

    /**
     * Méthode permettant d'analyser une classe
     *
     * @param chemin : classe à analyser
     */
    public Classe analyserClasse(String chemin) throws ClassNotFoundException {
        Class classe = LoaderExterne.getInstance().loadClass(chemin);
        setClasseAnalyse(classe);

        Classe classeAnalysee = new Classe("class", classe.getSimpleName());
        classeAnalysee.addPackage(new Package(classe.getPackage().getName()));

        ArrayList<String> modifiers = getModifierClasse(classe);
        for (String modifier : modifiers) {
            classeAnalysee.addModificateur(modifier);
        }
        for (Field field : classe.getDeclaredFields()) {
            Attribut attribut = analyserAttribut(field);
            classeAnalysee.addAttribut(attribut);
            System.out.println(" Attribut : " + attribut.getNom() + " : " + attribut.getType());
        }

        for (Method method : classe.getDeclaredMethods()) {
            Methode methode = analyserMethode(method);
            classeAnalysee.addMethode(methode);
            System.out.println("Méthode : " + methode.getNom());
        }

        /**
         * Méthode permettant d'analyser une classe
         * @param classe : classe à analyser
         */
        public static void afficherAttributs (Class cl){
            Field[] fields = cl.getDeclaredFields();
            System.out.println("Attributs:");
            for (Field f : fields) {
                System.out.println(f.getName());
            }
            for (Class<?> interfaceClass : classe.getInterfaces()) {
                Interface interface_ = new Interface("interface", interfaceClass.getSimpleName());
                classeAnalysee.addInterface(interface_);
                System.out.println("Interface : " + interface_.getNom());
            }
        }

        /**
         * Méthode permettant d'analyser les attributs d'une classe
         * @param field : attribut à analyser
         */
        private Attribut analyserAttribut (Field field){
            Attribut attribut = new Attribut(field.getType().getSimpleName(), field.getName());

            ArrayList<String> modifiers = getModifierAttribut(field);
            for (String modifier : modifiers) {
                attribut.addModificateur(modifier);
            }
            return new Attribute(field.getName(), field.getType().getSimpleName(), modifiers);
        }

        /**
         * Méthode permettant d'afficher les méthodes d'une classe
         * @param cl : classe à analyser
         */
        public static void afficherMethodes (Class cl){
            Method[] methods = cl.getDeclaredMethods();
            System.out.println("Méthodes:");
            for (Method m : methods) {
                System.out.println(m.getName());
            }
        }


        /**
         * Méthode permettant d'analyser une méthode
         * @param method : méthode à analyser
         */
        private Methode analyserMethode (Method method){
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
         * Méthode permetant de récupérer les modificateurs d'un attribut
         * @param modifiers : modifiers en int
         */
        private static String getModifierVisibilite ( int modifiers){
            if (Modifier.isPublic(modifiers)) return "public";
            if (Modifier.isPrivate(modifiers)) return "private";
            if (Modifier.isProtected(modifiers)) return "protected";
            return "package";

            /**
             * Méthode permettant de traduire les modificateurs en string
             * @param i : modificateur
             * @return : le modificateur en string
             */
            private static String getModifierVisibilite ( int i){
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
            private static ArrayList<String> getModifierClasse (Class c){
                int modifiers = c.getModifiers();
                ArrayList<String> result = new ArrayList<>();
                result.add(getModifierVisibilite(modifiers));
                if (Modifier.isPublic(modifiers)) result.add("public");
                if (Modifier.isPrivate(modifiers)) result.add("private");
                if (Modifier.isProtected(modifiers)) result.add("protected");
                if (Modifier.isAbstract(modifiers)) result.add("abstract");
                return result;
            }

            /**
             * Méthode permettant de récupérer les modificateurs d'une méthode
             * @param m : méthode à analyser
             * @return : les modificateurs de la méthode en int
             */
            private static ArrayList<String> getModifierMethode (Method m){
                int modifiers = m.getModifiers();
                ArrayList<String> result = new ArrayList<>();
                result.add(getModifierVisibilite(modifiers));
                if (Modifier.isPublic(modifiers)) result.add("public");
                if (Modifier.isPrivate(modifiers)) result.add("private");
                if (Modifier.isProtected(modifiers)) result.add("protected");
                return result;
            }

            /**
             * Méthode permettant de récupérer les modificateurs d'un attribut
             * @param f : attribut à analyser
             * @return : les modificateurs de l'attribut en int
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
             * Méthode permettant de récupérer les classes Parents
             *
             * @return : Classes Parents
             */
            public Class getClasseParent() {
                return this.analyseClasse.getSuperclass();
            }

            /**
             * Méthode permettant de récupérer les Interfaces
             *
             * @return : Interfaces
             */
            public Class[] getInterfaces() {
                return this.analyseClasse.getInterfaces();
            }

            /**
             * Méthode permettant de récupérer le nom de la classe
             *
             * @return : Nom de la classe
             */
            public String getNomClasse() {
                return this.analyseClasse.getName();
            }

            /**
             * Méthode permettant de trier les attributs par modificateur dans une map
             *
             * @return : Map triée contenant les attributs triés
             */
            public Map<String, List<Field>> trierAttributsParModificateur() {
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
                    } else {
                        tri.get("private").add(field);
                    }
                }

                return tri;
            }

            /**
             * Méthode permettant de trier les méthodes par modificateur dans une map
             *
             * @return : Map triée contenant les méthodes triées
             */
            public Map<String, List<Method>> trierMethodesParModificateur(Method[] methods) {
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
                    } else {
                        tri.get("private").add(method);
                    }
                }
                return tri;
            }


            /**
             * Méthode permettant d'afficher les attributs de la classe triés
             */
            public void afficherTriAttributs(Map<String, List<Field>> tri) {
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
    }
