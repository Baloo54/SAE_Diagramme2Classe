package classes;

import java.util.ArrayList;

public class Interface extends Attribut {
   private ArrayList<Interface> interfaces;
   private ArrayList<Methode> methodes;
   private ArrayList<Attribut> attributs;


    public Interface(String value, String type, String modificateur) {
        super(value, type, modificateur);
    }
}
