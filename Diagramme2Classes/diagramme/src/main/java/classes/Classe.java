package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Classe {
    private String type;
    private String nom;
    private List<String> modificateurs;
    private List<Attribut> attributs;
    private List<Methode> methodes;
    private List<Interface> interfaces;
    private Package pack;

    public Classe(String type, String nom) {
        this.type = type;
        this.nom = nom;
        this.modificateurs = new ArrayList<>();
        this.attributs = new ArrayList<>();
        this.methodes = new ArrayList<>();
        this.interfaces = new ArrayList<>();
    }

    public void addModificateur(String mod) {
        this.modificateurs.add(mod);
    }

    public void addAttribut(Attribut attr) {
        this.attributs.add(attr);
    }

    public void addMethode(Methode methode) {
        this.methodes.add(methode);
    }

    public void addInterface(Interface interf) {
        this.interfaces.add(interf);
    }

    public void addPackage(Package pack) {
        this.pack = pack;
    }
}