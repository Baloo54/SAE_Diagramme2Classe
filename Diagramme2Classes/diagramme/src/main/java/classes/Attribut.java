package classes;

import java.util.ArrayList;

public class Attribut extends Visible {
    String nom;
    private String type;
    private ArrayList<String> modificateurs;

    public Attribut( String type, String nom) {
        this.nom = nom;
        this.type = type;
        this.modificateurs = new ArrayList<String>();
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getModificateur() {
        return modificateurs;
    }
}
