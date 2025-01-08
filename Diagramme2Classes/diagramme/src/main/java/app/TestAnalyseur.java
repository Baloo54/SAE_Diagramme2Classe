package app;

import analyse.Analyseur;
import classes.Classe;

public class TestAnalyseur {

    public static void main (String[] args) {
        Analyseur analyseur = Analyseur.getInstance();
        try {
            Classe classe = analyseur.analyserClasse("classes.Classe");

            // affichage de la classe
            System.out.println(classe);

            // resultats de l'analyse
            analyseur.afficherResultats();
            String puml = analyseur.exportPuml(classe);

            // affichage du puml
            System.out.println(puml);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
