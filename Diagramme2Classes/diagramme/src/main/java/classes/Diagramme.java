package classes;

import diagramme.loader.LoaderExterne;

import java.nio.file.Path;
import java.util.ArrayList;

public class Diagramme {
    private ArrayList<Package> classes;
    

    public Diagramme() {
        this.classes = new ArrayList<>();
    }

    /**
     *
     * @param chemin chemin absolue vers un fichier .class
     * @throws ClassNotFoundException
     */
    public void ajouterClasse(String chemin) throws ClassNotFoundException {
        Class classe = LoaderExterne.getInstance().loadClass(chemin);
        String pack = classe.getName().replace(classe.getSimpleName(), "");
        Package p = new Package(pack);
        Classe c = new Classe("Object",classe.getSimpleName());

        this.classes.add(p);
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
