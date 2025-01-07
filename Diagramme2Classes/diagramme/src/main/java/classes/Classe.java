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
        super.changerVisibiliteHeritage();
        if (!this.getHeritageVisible()) {
            if(this.classeParent != null) {
                    this.classeParent.changerVisibiliteClasseFille(this);
                }
        }
    }
    public void changerVisibiliteClasseFille(Classe c) {
        this.classesFilles.put(c,!this.classesFilles.get(c));
    }
}
