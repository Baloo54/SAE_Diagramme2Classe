package classes;


import java.util.ArrayList;

/**
 * Classe représentant un package
 */
public class Package {

    /**
     * Attributs de la classe
     */
    private String nom;
    private ArrayList<Interface> interfaces;

    /**
     * Constructeur de la classe
     * @param nom : nom du package
     */
    public Package(String nom) {
        this.nom = nom;
        this.interfaces = new ArrayList<Interface>();
    }

    /**
     * Méthode permettant de récupérer le nom du package
     * @return : le nom du package
     */
    public String getNom() {
        return nom;
    }

    /**
     * Méthode permettant d'ajouter une classe au package
     */
    public void ajouterClasse(Interface classe) {

    }


}
