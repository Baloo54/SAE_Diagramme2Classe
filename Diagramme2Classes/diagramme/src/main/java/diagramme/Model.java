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
    private ArrayList<Interface> classes = new ArrayList<>();
    private HashMap<Interface, Position> positions = new HashMap<>();
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
                Interface c = analyseur.analyserClasse(string);
                if (!this.classes.contains(c))
                {
                    this.classes.add(c);
                    this.positions.put(c, new Position(0,0));
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        notifierObservateurs();
    }
    /**
     * getter getClasses
     * @return ArrayList<Classe>
     */
    public ArrayList<Interface> getClasses() {
        return classes;
    }
    public Position getPosition(Interface c) {
        return positions.get(c);
    }
    public void deplacement(Interface c, Position p){
        this.positions.put(c,p);
        notifierObservateurs();
    }
}
