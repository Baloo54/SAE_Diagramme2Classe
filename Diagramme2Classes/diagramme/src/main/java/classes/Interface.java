package classes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant une interface dans un modèle orienté objet.
 * Une interface peut contenir des méthodes, des attributs et peut hériter d'autres interfaces.
 */
public class Interface extends Attribut {

    // Liste des interfaces héritées par cette interface
    private ArrayList<Interface> interfaces;

    // Liste des méthodes définies dans cette interface
    private ArrayList<Methode> methodes;

    // Liste des attributs définis dans cette interface
    private ArrayList<Attribut> attributs;

    // Mappage des interfaces filles et de leur visibilité (true ou false)
    private HashMap<Interface, Boolean> interfacesFilles;


    // Indique si l'héritage est visible
    private boolean heritageVisible = true;

    // Nom du package de la classe
    private String packageClasse;

    /**
     * Constructeur pour initialiser une interface avec un type et un nom.
     *
     * @param type Le type de l'interface.
     * @param nom  Le nom de l'interface.
     */
    public Interface(String type, String nom, String packageClasse) {
        super(type, nom);
        this.interfaces = new ArrayList<>();
        this.interfacesFilles = new HashMap<>();
        this.methodes = new ArrayList<>();
        this.attributs = new ArrayList<>();
        this.packageClasse = packageClasse;
    }

    /**
     * Change la visibilité de l'interface.
     * Si l'interface devient invisible, ses relations avec d'autres interfaces deviennent également invisibles.
     */
    @Override
    public void changerVisibilite() {
        super.changerVisibilite();
        if (!this.getVisible()) {
            for (Interface i : interfaces) {
                if (i.interfacesFilles.get(this)) {
                    i.interfacesFilles.put(this, false);
                }
            }
            for (Interface i : interfacesFilles.keySet()) {
                if (interfacesFilles.get(i)) {
                    interfacesFilles.put(i, false);
                }
            }
        }
    }

    /**
     * Change la visibilité d'une interface fille.
     *
     * @param i L'interface fille dont la visibilité doit être changée.
     */
    public void changerVisibiliteInterfaceFille(Interface i) {
        if (this.getVisible()) {
            interfacesFilles.put(i, !interfacesFilles.get(i));
        } else {
            this.changerVisibilite();
            interfacesFilles.put(i, !interfacesFilles.get(i));
        }
    }

    /**
     * Vérifie si une interface fille est visible.
     *
     * @param i L'interface fille à vérifier.
     * @return true si l'interface fille est visible, false sinon.
     */
    public boolean etreVisibleFille(Interface i) {
        return interfacesFilles.get(i);
    }

    /**
     * Change la visibilité de l'héritage de l'interface.
     * Met à jour la visibilité des relations d'héritage avec les interfaces parentes.
     */
    public void changerVisibiliteHeritage() {
        heritageVisible = !heritageVisible;
        if (!heritageVisible) {
            for (Interface i : interfaces) {
                if (i.etreVisibleFille(this)) {
                    i.changerVisibiliteInterfaceFille(this);
                }
            }
        } else {
            for (Interface i : interfaces) {
                if (!i.etreVisibleFille(this)) {
                    i.changerVisibiliteInterfaceFille(this);
                }
            }
        }
    }

    /**
     * Change la visibilité d'une méthode spécifique.
     *
     * @param m La méthode dont la visibilité doit être changée.
     */
    public void changerVisibiliteMethode(Methode m) {
        m.changerVisibilite();
    }

    /**
     * Vérifie si deux interfaces sont égales en fonction de leur type et attributs.
     *
     * @param obj L'objet à comparer.
     * @return true si les interfaces sont égales, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        return getNom().equals(((Interface) obj).getNom());
    }

    /**
     * Retourne la liste des méthodes de l'interface.
     *
     * @return Liste des méthodes.
     */
    public ArrayList<Methode> getMethodes() {
        return methodes;
    }

    /**
     * Retourne la visibilité de l'héritage.
     *
     * @return true si l'héritage est visible, false sinon.
     */
    public boolean getHeritageVisible() {
        return heritageVisible;
    }

    /**
     * Ajoute une méthode à l'interface.
     *
     * @param methode La méthode à ajouter.
     */
    public void addMethode(Methode methode) {
        methodes.add(methode);
    }

    /**
     * Ajoute une interface implémenté.
     * 
     * @param inter L'interface à ajouter.
     */
    public void addInterface(Interface inter) {
        interfaces.add(inter);
        interfacesFilles.put(inter, true);
    }

    /**
     * Ajoute un attribut à l'interface.
     *
     * @param attribut L'attribut à ajouter.
     */
    public void addAttribut(Attribut attribut) {
        attributs.add(attribut);
    }

    /**
     * Retourne la liste des interfaces héritées.
     *
     * @return Liste des interfaces.
     */
    public ArrayList<Interface> getInterfaces() {
        return interfaces;
    }

    /**
     * Retourne la liste des attributs
     *
     * @return liste des attributs
     */
    public ArrayList<Attribut> getAttributs() {
        return attributs;
    }
    public HashMap<Interface, Boolean> getInterfacesFilles() {
        return interfacesFilles;
    }

    public String getPackageClasse() {
        return packageClasse;
    }
}
