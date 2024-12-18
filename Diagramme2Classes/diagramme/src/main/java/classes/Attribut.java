package classes;

import java.util.ArrayList;

public class Attribut {
    private String value;
    private String type;
    private ArrayList<String> modificateurs;

    public Attribut(String value, String type, String modificateur) {
        this.value = value;
        this.type = type;
        this.modificateurs = new ArrayList<String>();
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getModificateur() {
        return modificateurs;
    }
}
