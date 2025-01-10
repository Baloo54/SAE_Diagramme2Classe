package classes;


/**
 * Classe représentant une classe dans un modèle orienté objet.
 * Elle hérite de la classe Interface.
 */
public class Classe extends Interface {

    // Classe parent dans une relation d'héritage
    private Classe classeParent;

    /**
     * Constructeur pour initialiser une classe avec un type et un nom.
     *
     * @param type Le type de la classe.
     * @param nom  Le nom de la classe.
     */
    public Classe(String type, String nom, String packageClasse) {
        super(type, nom, packageClasse);
        classeParent = null;
    }


    public void setClasseParent(Classe classeParent) {
        this.classeParent = classeParent;
    }

    public Classe getClasseParent() {
        return classeParent;
    }
}
