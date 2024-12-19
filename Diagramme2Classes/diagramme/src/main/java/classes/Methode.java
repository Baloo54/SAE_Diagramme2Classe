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

    @Override
    public boolean equals(Object obj) {
        boolean signature = false;
        if(parametres.size()==((Methode) obj).getParametres().size()){
            int i = 0;
            ArrayList<Boolean> b = new ArrayList<>();
            while( i<parametres.size()){
                b.add(parametres.get(i)==((Methode) obj).getParametres().get(i));
                i++;
            }
            if(i==0){
                signature=true;
            }else if(!b.contains(false)){
                signature = true;
            }
        }
        return getNom().equals(((Methode) obj).getNom()) && signature;
    }
}
