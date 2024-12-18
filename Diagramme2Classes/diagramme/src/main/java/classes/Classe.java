package main.java.classes;

import classes.Interface;

import java.util.HashMap;

public class Classe extends Interface {
    private HashMap<Classe,Boolean> classeFille;

    public Classe(String value, String type, String modificateur) {
        super(value, type, modificateur);
        this.classeFille = new HashMap<Classe,Boolean>();
    }

    public HashMap<Classe,Boolean> getClasseFille() {
        return classeFille;
    }

    public void changerVisibiliteHeritage() {
        //TODO
    }

    public Classe getClasseParent() {
        //TODO
        return null;

    }
}
