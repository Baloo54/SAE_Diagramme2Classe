package classes;

import java.util.ArrayList;

/**
 * Classe représentant un attribut avec un nom, un type et des modificateurs.
 * Elle hérite de la classe Visible.
 */
public class Attribut extends Visible {

    // Nom de l'attribut
    private String nom;

    // Type de l'attribut (ex: int, String, etc.)
    private String type;

    // Liste des modificateurs (ex: public, private, static, etc.)
    private ArrayList<String> modificateurs;

    /**
     * Constructeur pour initialiser un attribut avec son type et son nom.
     * 
     * @param type Le type de l'attribut.
     * @param nom  Le nom de l'attribut.
     */
    public Attribut(String type, String nom) {
        super();
        this.nom = nom;
        this.type = type;
        this.modificateurs = new ArrayList<String>();
    }

    /**
     * Retourne le nom de l'attribut.
     * 
     * @return Le nom de l'attribut.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le type de l'attribut.
     * 
     * @return Le type de l'attribut.
     */
    public String getType() {
        return type;
    }

    /**
     * Ajoute un modificateur à la liste des modificateurs de l'attribut.
     * 
     * @param modificateur Le modificateur à ajouter.
     */
    public void addModificateur(String modificateur) {
        modificateurs.add(modificateur);
    }

    /**
     * Vérifie si deux attributs sont égaux en comparant leurs noms.
     * 
     * @param obj L'objet à comparer.
     * @return true si les noms sont identiques, sinon false.
     */
    @Override
    public boolean equals(Object obj) {
        return nom.equals(((Attribut) obj).getNom());
    }

    /**
     * Retourne la liste des modificateurs de l'attribut.
     * 
     * @return Une liste de modificateurs.
     */
    public ArrayList<String> getModificateurs() {
        return modificateurs;
    }
}
