import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Analyseur {
    public static void analyseClasse(String nomClasse) {
        try {
            Class<?> cl = Class.forName(nomClasse);
            System.out.println("Classe: " + cl.getName());
            afficherAttributs(cl);
            afficherMethodes(cl);
            Package p = cl.getPackage();
            System.out.println("Package: " + p.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void afficherAttributs(Class<?> cl) {
        Field[] fields = cl.getDeclaredFields();

        List<Field> publics = new ArrayList<>();
        List<Field> protecteds = new ArrayList<>();
        List<Field> privates = new ArrayList<>();
        List<Field> defaults = new ArrayList<>();

        for (Field f : fields) {
            int mod = f.getModifiers();
            if (Modifier.isPublic(mod)) {
                publics.add(f);
            } else if (Modifier.isProtected(mod)) {
                protecteds.add(f);
            } else if (Modifier.isPrivate(mod)) {
                privates.add(f);
            } else {
                defaults.add(f);
            }
        }

        System.out.println("Attributs public:");
        for (Field f : publics) {
            System.out.println(" - " + f.getName());
        }

        System.out.println("Attributs protected:");
        for (Field f : protecteds) {
            System.out.println(" - " + f.getName());
        }

        System.out.println("Attributs private:");
        for (Field f : privates) {
            System.out.println(" - " + f.getName());
        }

        System.out.println("Attributs package-private:");
        for (Field f : defaults) {
            System.out.println(" - " + f.getName());
        }
    }

    public static void afficherMethodes(Class<?> cl) {
        Method[] methods = cl.getDeclaredMethods();
        System.out.println("Méthodes:");
        for (Method m : methods) {
            System.out.println(m.getName());
        }
    }
}
