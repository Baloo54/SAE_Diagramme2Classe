package classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Interface extends Attribut {
   private ArrayList<Interface> interfaces;
   private ArrayList<Methode> methodes;
   private ArrayList<Attribut> attributs;
   HashMap<Interface,Boolean> interfacesFilles;


    public Interface(String type,String nom) {
        super(type,nom);
        this.interfaces = new ArrayList<Interface>();
        this.methodes = new ArrayList<Methode>();
        this.attributs = new ArrayList<Attribut>();
    }

    @Override
    public void changerVisibilite() {
        if(!interfacesFilles.containsValue(true)) {
            super.changerVisibilite();
            for (Interface i : interfaces) {
                if(interfacesFilles.get(i)) {
                    i.changerVisibilite();
                }
            }
            for (Methode m : methodes) {
                if(m.getVisible()) {
                    m.changerVisibilite();
                }
            }
        }
    }

    public void changerVisibiliteInterfaceFille(Interface i) {
        interfacesFilles.put(i, !interfacesFilles.get(i));
    }
    public void changerVisibiliteHeritage() {
        for (Interface i : interfaces) {
            i.changerVisibilite();
        }
    }
    public void changerVisibiliteMethode(Methode m) {
        m.changerVisibilite();
    }

    public void ajouterMethode(Methode m) {
        methodes.add(m);
    }
}
