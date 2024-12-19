package classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Methode extends Visible {
    private String nom;
    private String retour;
    private ArrayList<HashMap<String, String>> parametres;
    private ArrayList<String> modificateurs;


    public Methode(String nom, String retour, ArrayList<HashMap<String, String>> parametres, ArrayList<String> modificateurs) {
        this.nom = nom;
        this.retour = retour;
        this.parametres = parametres;
        this.modificateurs = modificateurs;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<HashMap<String, String>> getParametres() {
        return parametres;
    }

    public ArrayList<String> getModificateurs() {
        return modificateurs;
    }

    public String getRetour() {
        return retour;
    }
}
