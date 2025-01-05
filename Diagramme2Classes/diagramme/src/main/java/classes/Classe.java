package classes;
import java.util.HashMap;

/**
 * Classe représentant une classe
 */
public class Classe extends Interface {
    /**
     * Attributs de la classe
     */
    private HashMap<Classe,Boolean> classesFilles;
    private Classe classeParent = null;

    /**
     * Constructeur de la classe
     * @param type : type de la classe
     * @param nom : nom de la classe
     */
    public Classe(String type,String nom) {
        super(type,nom);
        this.classesFilles = new HashMap<Classe,Boolean>();
    }

    /**
     * Méthode permettant de récupérer les classes filles
     * @return : les classes filles
     */
    public HashMap<Classe,Boolean> getClasseFille() {
        return classesFilles;
    }

    /**
     * Méthode permettant de récupérer la visibilité de l'héritage
     * @return : la visibilité de l'héritage
     */
    public void changerVisibiliteHeritage() {
        //TODO
    }

    /**
     * Méthode permettant de récupérer la classe parent
     * @return : la classe parent
     */
    public Classe getClasseParent() {
        //TODO
        return null;
    }
}
