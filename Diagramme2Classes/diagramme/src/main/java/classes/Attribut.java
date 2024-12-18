package classes;

public class Attribut {
    String value;
    String type;
    String modificateur;

    public Attribut(String value, String type, String modificateur) {
        this.value = value;
        this.type = type;
        this.modificateur = modificateur;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getModificateur() {
        return modificateur;
    }
}
