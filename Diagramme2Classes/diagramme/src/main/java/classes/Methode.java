package classes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant une méthode
 */
public class Methode extends Visible {
    /**
     * Attributs de la classe
     */
    private String nom;
    private String retour;
    private ArrayList<HashMap<String, String>> parametres;
    private ArrayList<String> modificateurs;


    /**
     * Constructeur de la classe
     * @param nom : nom de la méthode
     * @param retour : type de retour de la méthode
     * @param parametres : liste des paramètres de la méthode
     * @param modificateurs : liste des modificateurs de la méthode
     */
    public Methode(String nom, String retour, ArrayList<HashMap<String, String>> parametres, ArrayList<String> modificateurs) {
        this.nom = nom;
        this.retour = retour;
        this.parametres = parametres;
        this.modificateurs = modificateurs;
    }

    /**
     * Méthode permettant de récupérer le nom de la méthode
     * @return : le nom de la méthode
     */
    public String getNom() {
        return nom;
    }

    /**
     * Méthode permettant de récupérer les paramètres de la méthode
     * @return : liste des paramètres
     */
    public ArrayList<HashMap<String, String>> getParametres() {
        return parametres;
    }

    /**
     * Méthode permettant de récupérer les modificateurs de la méthode
     * @return : liste des modificateurs
     */
    public ArrayList<String> getModificateurs() {
        return modificateurs;
    }

    /**
     * Méthode permettant de récupérer le type de retour de la méthode
     * @return : le type de retour
     */
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
