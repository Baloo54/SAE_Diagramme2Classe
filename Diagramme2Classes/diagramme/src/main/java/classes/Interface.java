package classes;

/**
 * Classe représentant une interface
 */
public class Interface {
    private String type;
    private String nom;

    public Interface(String type, String nom) {
        this.type = type;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
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


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)&&getType().equals(((Interface)obj).getType());
    }
}
