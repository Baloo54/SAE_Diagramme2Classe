package diagramme;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import analyse.Analyseur;
import classes.*;
/**
 * classe model
 */
public class Model implements Sujet{
    /**
     * Attributs
     * folder correspond au dossier contenant le package .class
     */
    private ArrayList<Observateur> observateurs;
    private ArrayList<Classe> classes = new ArrayList<>();
    private HashMap<Classe, Position> positions = new HashMap<>();

    /**
     * Constructeur
     */
    public Model() {
        this.observateurs = new ArrayList<Observateur>();
    }
    /**
     * ajouter observateur
     * @param observateur
     * @return void
     */
    public void ajouterObservateur(Observateur observateur) {
        this.observateurs.add(observateur);
    }
    /**
     * notifier observateurs
     * @return void
     */
    public void notifierObservateurs() {
        for (Observateur observateur : this.observateurs) {
            observateur.actualiser(this);
        }
    }
    /**
     * ajouterPackage
     * @param folder folder
     */
    public void ajouterPackage(File folder){
        ReadFile reader = new ReadFile();
        List<String> classes = reader.findClassFiles(folder);
        Analyseur analyseur = Analyseur.getInstance();
        
        for (String string : classes) {
            try {
                Classe c = analyseur.analyserClasse(string);
                this.classes.add(c);
                this.positions.put(c, new Position(0,0));
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        notifierObservateurs();
    }
    /**
     * @param Classe
     * getter getClasses
     * @return Position
     */
    public Position getPosition(Classe c) {
        return positions.get(c);
    }
    /**
     * getClasses
     * @return ArrayList<Classe>
     */
    public ArrayList<Classe> getClasses(){
        return this.classes;
    }
    /**
     * deplacement
     * @param Classe
     * @param Position
     */
    public void deplacement(Classe c, Position p){
        this.positions.put(c,p);
        notifierObservateurs();
    }
}
