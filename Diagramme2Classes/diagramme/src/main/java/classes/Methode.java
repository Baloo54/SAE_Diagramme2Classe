package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe représentant une méthode dans un modèle orienté objet.
 * Une méthode possède un nom, un type de retour, des paramètres et des modificateurs d'accès.
 */
public class Methode extends Visible {

    // Nom de la méthode
    private String nom;

    // Type de retour de la méthode
    private String typeRetour;

    // Liste des paramètres de la méthode (nom et type)
    private List<HashMap<String, String>> parametres;

    // Liste des modificateurs d'accès (ex. public, private, static)
    private ArrayList<String> modificateurs;

    /**
     * Constructeur pour initialiser une méthode avec ses attributs principaux.
     * 
     * @param nom           Nom de la méthode.
     * @param typeRetour    Type de retour de la méthode.
     * @param parametres    Liste des paramètres de la méthode sous forme de paires clé-valeur (type et nom).
     * @param modificateurs Liste des modificateurs d'accès.
     */
    public Methode(String nom, String typeRetour, List<HashMap<String, String>> parametres, ArrayList<String> modificateurs) {
        this.nom = nom;
        this.typeRetour = typeRetour;
        this.parametres = parametres;
        this.modificateurs = modificateurs;
    }

    /**
     * Retourne le nom de la méthode.
     * 
     * @return Nom de la méthode.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le type de retour de la méthode.
     * 
     * @return Type de retour.
     */
    public String getTypeRetour() {
        return typeRetour;
    }

    /**
     * Retourne la liste des paramètres de la méthode.
     * 
     * @return Liste des paramètres sous forme de paires clé-valeur (type et nom).
     */
    public List<HashMap<String, String>> getParametres() {
        return parametres;
    }

    /**
     * Compare cette méthode avec un autre objet pour vérifier leur égalité.
     * Deux méthodes sont considérées comme égales si elles ont le même nom et la même signature.
     * 
     * @param obj Objet à comparer.
     * @return true si les méthodes sont égales, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        boolean signature = false;
        if (parametres.size() == ((Methode) obj).getParametres().size()) {
            int i = 0;
            ArrayList<Boolean> b = new ArrayList<>();
            while (i < parametres.size()) {
                b.add(parametres.get(i).equals(((Methode) obj).getParametres().get(i)));
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

    /**
     * Retourne la liste des modificateurs d'accès de la méthode.
     * 
     * @return Liste des modificateurs.
     */
    public ArrayList<String> getModificateurs() {
        return modificateurs;
    }
}
