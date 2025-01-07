package classes;

import java.util.List;

/**
 * Classe représentant un attribut
 */
public class Attribut extends Visible {
    private String nom;
    private String type;
    private List<String> modificateurs;

    public Attribut(String nom, String type, List<String> modificateurs) {
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



    @Override
    public boolean equals(Object obj) {
        return nom.equals(((Attribut) obj).getNom());
    }
}
