package classes;
import java.util.HashMap;

public class Classe extends Interface {
    private HashMap<Classe,Boolean> classesFilles;
    private Classe classeParent = null;

    public Classe(String type,String nom) {
        super(type,nom);
        this.classesFilles = new HashMap<Classe,Boolean>();
    }

    public HashMap<Classe,Boolean> getClasseFille() {
        return classesFilles;
    }

    public void changerVisibiliteHeritage() {
        //TODO
    }

    public Classe getClasseParent() {
        //TODO
        return null;
    }
}
