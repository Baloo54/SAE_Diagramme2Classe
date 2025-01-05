package classes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant une interface
 */
public class Interface extends Attribut {
   private ArrayList<Interface> interfaces;
   private ArrayList<Methode> methodes;
   private ArrayList<Attribut> attributs;
   private HashMap<Interface,Boolean> interfacesFilles;


    /**
     * Constructeur de la classe
     * @param type : type de l'interface
     * @param nom : nom de l'interface
     */
    public Interface(String type,String nom) {
        super(type,nom);
        this.interfaces = new ArrayList<Interface>();
        this.methodes = new ArrayList<Methode>();
        this.attributs = new ArrayList<Attribut>();
    }

    /**
     * Méthode permettant de changer la visibilité de l'interface
     */
    @Override
    public void changerVisibilite() {
            super.changerVisibilite();
            for (Interface i : interfaces) {
                if(interfacesFilles.get(i)) {
                    i.changerVisibilite();
                    interfacesFilles.put(i, i.getVisible());
                }
            }
            for (Methode m : methodes) {
                if(m.getVisible()) {
                    m.changerVisibilite();
                }
            }
    }

    public void changerVisibilite(Interface i) {
        if(this.getVisible()){
            interfacesFilles.put(i, true);
        } else {
            interfacesFilles.put(i, false);
        }
    }


    public void changerVisibiliteInterfaceFille(Interface i) {
        interfacesFilles.put(i, !interfacesFilles.get(i));
    }

    public void changerVisibiliteHeritage() {
        for (Interface i : interfaces) {
            i.changerVisibilite(this);
        }
    }
    public void changerVisibiliteMethode(Methode m) {
        m.changerVisibilite();
    }

    public void ajouterMethode(Methode m) {
        methodes.add(m);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)&&getType().equals(((Interface)obj).getType());
    }
}
