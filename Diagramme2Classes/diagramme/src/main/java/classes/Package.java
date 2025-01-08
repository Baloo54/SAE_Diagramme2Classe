package classes;


import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe représentant un package
 */
public class Package {

    /**
     * Attributs de la classe
     */
    private String nom;
    private ArrayList<Interface> interfaces;
    private ArrayList<Package> packages;

    /**
     * Constructeur de la classe
     * @param nom : nom du package
     */
    public Package(String nom) {
        this.nom = nom;
        this.interfaces = new ArrayList<Interface>();
        this.packages = new ArrayList<Package>();
    }

    /**
     * Méthode permettant de récupérer le nom du package
     * @return : le nom du package
     */
    public String getNom() {
        return nom;
    }

    /**
     * Méthode permettant d'ajouter une classe au package ou de la mettre à jour si elle existe déjà
     */
    public void ajouterClasse(Interface classe) {
        if (!interfaces.contains(classe)) {
            interfaces.add(classe);
        }
        //si la classe est déjà présente on part du principe que l'utilisateur veut la mettre a jour
        else {interfaces.remove(classe);
            interfaces.add(classe);
        }
    }

    /**
     * Méthode permettant d'ajouter un package au package ou de fusionner les packages si le package existe déjà
     * @param p
     */
    public void addPackage(Package p) {
        if (!packages.contains(p)) {
            packages.add(p);
        }
        else {
            while (p != null) {
                int i = packages.indexOf(p);
                for (Interface inter : p.getInterfaces()) {
                    packages.get(i).ajouterClasse(inter);
                }
                for (Package pack : p.getPackages()) {
                    packages.get(i).addPackage(pack);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return Objects.equals(nom, aPackage.getNom());
    }

    public ArrayList<Interface> getInterfaces() {
        return interfaces;
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }
}
