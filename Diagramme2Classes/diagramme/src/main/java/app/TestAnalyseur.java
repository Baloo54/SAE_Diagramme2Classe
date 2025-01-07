package app;

import analyse.Analyseur;
import classes.Classe;

public class TestAnalyseur {

    public static void main (String[] args) {
        Analyseur analyseur = Analyseur.getInstance();
        try {
            Classe classe = analyseur.analyserClasse("classes.Classe");
            analyseur.afficherResultats();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
