package classes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class CompositeClasse {
    String nom;
    String type;
    ArrayList<String> modificateurs;

    public CompositeClasse(String nom, String type, ArrayList<String> modificateurs) {
        this.nom = nom;
        this.type = type;
        this.modificateurs = modificateurs;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getModificateurs() {
        return modificateurs;
    }
}
