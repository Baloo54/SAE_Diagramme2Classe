package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un attribut
 */
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

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }



    @Override
    public boolean equals(Object obj) {
        return nom.equals(((Attribut) obj).getNom());
    }
}
