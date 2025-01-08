package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe représentant une méthode
 */
public class Methode extends Visible {
    private String nom;
    private String typeRetour;
    private List<HashMap<String, String>> parametres;
    private List<String> modificateurs;

    public Methode(String nom, String typeRetour, List<HashMap<String, String>> parametres, List<String> modificateurs) {
        this.nom = nom;
        this.typeRetour = typeRetour;
        this.parametres = parametres;
        this.modificateurs = modificateurs;
    }

    public String getNom() {
        return nom;
    }

    public String getTypeRetour() {
        return typeRetour;
    }

    public List<HashMap<String, String>> getParametres() {
        return parametres;
    }


    @Override
    public boolean equals(Object obj) {
        boolean signature = false;
        if (parametres.size() == ((Methode) obj).getParametres().size()) {
            int i = 0;
            ArrayList<Boolean> b = new ArrayList<>();
            while (i < parametres.size()) {
                b.add(parametres.get(i) == ((Methode) obj).getParametres().get(i));
                i++;
            }
            if (i == 0) {
                signature = true;
            } else if (!b.contains(false)) {
                signature = true;
            }
        }
        return getNom().equals(((Methode) obj).getNom()) && signature;
    }

    public List<String> getModificateurs() {
        return modificateurs;
    }


}
