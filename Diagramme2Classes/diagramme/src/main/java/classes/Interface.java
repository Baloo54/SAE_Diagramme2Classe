package classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Interface extends Attribut {
    private ArrayList<Interface> interfaces;
    private ArrayList<Methode> methodes;
    private ArrayList<Attribut> attributs;
    private HashMap<Interface, Boolean> interfacesFilles;
    private boolean heritageVisible = true;

    public Interface(String type, String nom) {
        super(type, nom);
        this.interfaces = new ArrayList<Interface>();
        this.methodes = new ArrayList<Methode>();
        this.attributs = new ArrayList<Attribut>();
    }

    /**
     * méthode pour masquer complètement l'interface
     */
    @Override
    public void changerVisibilite() {
        //masque ou affiche l'interface
        super.changerVisibilite();
        //masque tous les interfaces filles et méthodes si l'interface est masquée
        if (!this.getVisible()) {
            for (Interface i : interfaces) {
                //si l'interface parent est visible, on masque la relation de cette interface pour l'interface parente
                if (i.interfacesFilles.get(this)) {
                    i.interfacesFilles.put(this, false);
                }
            }
            for (Interface i : interfacesFilles.keySet()) {
                //si l'interface fille est visible, on masque la relation de l'interface fille pour cette interface
                if (interfacesFilles.get(i)) {
                    interfacesFilles.put(i, false);
                }
            }
        }
    }

    /**
     * change la visibilité d'une interfaceFille lors du changement de visibilité de l'héritage
     * @param i
     */
    public void changerVisibiliteInterfaceFille(Interface i) {
        if (this.getVisible()) {
            //si l'interface est visible, on change la visibilité de l'interface fille
            interfacesFilles.put(i, !interfacesFilles.get(i));
        } else {
            //en théorie l'interface est masquée que lorsque qu'il n'y a plus de relations visibles
            //donc si l'interface est masquée, elle devient visible et l'interface fille concerné également
            this.changerVisibilite();
            interfacesFilles.put(i, !interfacesFilles.get(i));
        }
    }

    public boolean etreVisibleFille(Interface i) {
        return interfacesFilles.get(i);
    }



    /**
     * change la visibilité de l'héritage
     */
    public void changerVisibiliteHeritage() {
        heritageVisible = !heritageVisible;
        if(!heritageVisible){
            for (Interface i : interfaces) {
                //si l'interface parent est masquée, on masque la relation de cette interface pour l'interface fille
                if (i.etreVisibleFille(this)) {
                    i.changerVisibiliteInterfaceFille(this);
                }
            }
        }
        else {
            for (Interface i : interfaces) {
                //si l'interface parent est visible, on affiche la relation de cette interface pour l'interface fille
                if (!i.etreVisibleFille(this)) {
                    i.changerVisibiliteInterfaceFille(this);
                }
            }
        }
    }

    /**
     * change la visiblité d'un Méthode
     *
     * @param m
     */
    public void changerVisibiliteMethode(Methode m) {
        m.changerVisibilite();
    }

    public void ajouterMethode(Methode m) {
        methodes.add(m);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && getType().equals(((Interface) obj).getType());
    }

    public ArrayList<Methode> getMethodes() {
        return methodes;
    }

    public boolean getHeritageVisible(){
        return heritageVisible;
    }

    public ArrayList<Interface> getInterfaces() {
        return interfaces;
    }
}
