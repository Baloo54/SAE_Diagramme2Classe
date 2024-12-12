package main.java.diagramme.Classes;

import java.util.ArrayList;

//correspond à CompositionClasse dans le diagramme de classe
public abstract class Declarable {
private String nom;
private String type;
private ArrayList<String> modificateurs;

    public ArrayList<String> getModificateurs() {
        return modificateurs;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }
}
