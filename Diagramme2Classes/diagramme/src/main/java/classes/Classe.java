package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe représentant une classe dans un modèle orienté objet.
 * Elle hérite de la classe Interface.
 */
public class Classe extends Interface {

    // Liste des méthodes définies dans la classe
    private List<Methode> methodes;

    // Liste des interfaces implémentées par la classe
    private List<Interface> interfaces;

    // Classe parent dans une relation d'héritage
    private Classe classeParent;

    // Mappage des classes filles et de leur visibilité (true ou false)
    private HashMap<Classe, Boolean> classesFilles;

    /**
     * Constructeur pour initialiser une classe avec un type et un nom.
     *
     * @param type Le type de la classe.
     * @param nom  Le nom de la classe.
     */
    public Classe(String type, String nom) {
        super(type, nom);
        this.methodes = new ArrayList<>();
        this.interfaces = new ArrayList<>();
    }

    /**
     * Change la visibilité de l'héritage pour la classe.
     * Si l'héritage devient invisible, met à jour la visibilité de la classe parent.
     */
    public void changerVisibiliteHeritage() {
        super.changerVisibiliteHeritage();
        if (!this.getHeritageVisible()) {
            if (this.classeParent != null) {
                this.classeParent.changerVisibiliteClasseFille(this);
            }
        }
    }

    /**
     * Change la visibilité de la classe.
     * Si la classe devient invisible, toutes ses classes filles deviennent également invisibles.
     */
    @Override
    public void changerVisibilite() {
        super.changerVisibilite();
        if (!this.getVisible()) {
            for (Classe c : classesFilles.keySet()) {
                if (classesFilles.get(c)) {
                    classesFilles.put(c, false);
                }
            }
        }
    }

    /**
     * Change la visibilité d'une classe fille spécifique.
     *
     * @param c La classe fille dont la visibilité doit être changée.
     */
    public void changerVisibiliteClasseFille(Classe c) {
        this.classesFilles.put(c, !this.classesFilles.get(c));
    }

    public void setClasseParent(Classe classeParent) {
        this.classeParent = classeParent;
    }
}
