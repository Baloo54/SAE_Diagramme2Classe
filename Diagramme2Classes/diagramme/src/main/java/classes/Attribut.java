package classes;

import java.util.ArrayList;

public class Attribut extends Visible {
    private String nom;
    private String type;
    private ArrayList<String> modificateurs;

    public Attribut( String type, String nom) {
        super();
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

    public String getNom() {
        return nom;
    }

    @Override
    public boolean equals(Object obj) {
        return nom.equals(((Attribut) obj).getNom());
    }
}
