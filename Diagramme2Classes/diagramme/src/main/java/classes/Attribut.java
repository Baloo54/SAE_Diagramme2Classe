package classes;

import java.util.ArrayList;

/**
 * Classe représentant un attribut
 */
public class Attribut extends Visible {

    /**
     * Attributs de la classe
     */
    private String nom;
    private String type;
    private ArrayList<String> modificateurs;

    /**
     * Constructeur de la classe
     * @param type : type de l'attribut
     * @param nom : nom de l'attribut
     */
    public Attribut( String type, String nom) {
        super();
        this.nom = nom;
        this.type = type;
        this.modificateurs = new ArrayList<String>();
    }

    /**
     * Méthode permettant de récupérer le type de l'attribut
     * @return : le type de l'attribut
     */
    public String getType() {
        return type;
    }

    /**
     * Méthode permettant de récupérer les modificateurs de l'attribut
     * @return : liste de Modificateurs
     */
    public ArrayList<String> getModificateur() {
        return modificateurs;
    }

    /**
     * Méthode permettant de récupérer le nom de l'attribut
     * @return : le nom de l'attribut
     */
    public String getNom() {
        return nom;
    }

    @Override
    public boolean equals(Object obj) {
        return nom.equals(((Attribut) obj).getNom());
    }
}
