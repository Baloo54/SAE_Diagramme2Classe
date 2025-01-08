package classes;

import java.util.ArrayList;

/**
 * Classe représentant un diagramme
 */
public class Diagramme {
    private ArrayList<Package> classes;
    

    /**
     * Constructeur de la classe
     */
    public Diagramme() {
        this.classes = new ArrayList<>();
    }

    /**
     *
     * @param chemin chemin absolue vers un fichier .class
     * @throws ClassNotFoundException
     */
    public void ajouterClasse(String chemin) throws ClassNotFoundException {
        Package pack = new Package(chemin);
        if (this.posPackage(pack) == -1){
            this.classes.add(pack);
        }
    }
    public int posPackage(Package pack){
        int i = 0;
        for (Package p : this.classes){
            if (p.equals(pack)){
                return i;
            }
            i++;
        }
        return -1;
    }
}
