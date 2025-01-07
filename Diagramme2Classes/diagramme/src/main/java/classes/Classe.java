package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Classe extends Interface {
    private List<Methode> methodes;
    private List<Interface> interfaces;
    private Classe classeParent;
    private HashMap<Classe, Boolean> classesFilles;

    public Classe(String type, String nom) {
        super(type, nom);
        this.methodes = new ArrayList<>();
        this.interfaces = new ArrayList<>();
    }

    public void changerVisibiliteHeritage() {
        super.changerVisibiliteHeritage();
        if (!this.getHeritageVisible()) {
            if(this.classeParent != null) {
                    this.classeParent.changerVisibiliteClasseFille(this);
                }
        }
    }
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

    public void changerVisibiliteClasseFille(Classe c) {
        this.classesFilles.put(c,!this.classesFilles.get(c));
}