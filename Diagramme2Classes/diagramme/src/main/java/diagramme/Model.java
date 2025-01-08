package diagramme;

import analyse.Analyseur;
import classes.Interface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * classe model
 */
public class Model implements Sujet {
    /**
     * Attributs
     * folder correspond au dossier contenant le package .class
     */
    private ArrayList<Observateur> observateurs;
    private HashMap<String, ArrayList<Interface>> packages = new HashMap<>();
    private HashMap<Interface, Position> positions = new HashMap<>();

    /**
     * Constructeur
     */
    public Model() {
        this.observateurs = new ArrayList<Observateur>();
    }

    /**
     * ajouter observateur
     *
     * @param observateur
     * @return void
     */
    public void ajouterObservateur(Observateur observateur) {
        this.observateurs.add(observateur);
    }

    /**
     * notifier observateurs
     *
     * @return void
     */
    public void notifierObservateurs() {
        for (Observateur observateur : this.observateurs) {
            observateur.actualiser(this);
        }
    }

    /**
     * ajouterPackage
     *
     * @param folder folder
     */
    public void ajouterPackage(File folder) {
        ReadFile reader = new ReadFile();
        List<String> chemin = reader.findClassFiles(folder);
        Analyseur analyseur = Analyseur.getInstance();

        for (String string : chemin) {
            try {
                Interface c = analyseur.analyserClasse(string);
                if (!this.packages.containsKey(c.getPackageClasse())) {
                    this.packages.put(c.getPackageClasse(), new ArrayList<Interface>());
                    this.packages.get(c.getPackageClasse()).add(c);
                } else {
                    if (!this.packages.get(c.getPackageClasse()).contains(c)) {
                        this.packages.get(c.getPackageClasse()).add(c);
                    }
                }
                this.positions.put(c, new Position(0, 0));
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
     *
     * @return ArrayList<Classe>
     */

    public Position getPosition(Interface c) {
        return positions.get(c);
    }

    public ArrayList<Interface> getClasses() {
        ArrayList<Interface> classes = new ArrayList<>();
        for (String key : this.packages.keySet()) {
            classes.addAll(this.packages.get(key));
        }
        return classes;
    }

    public void deplacement(Interface c, Position p) {
        this.positions.put(c, p);
        notifierObservateurs();
    }
}
