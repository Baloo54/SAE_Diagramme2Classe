package diagramme;

import analyse.Analyseur;
import classes.Attribut;
import classes.Interface;
import classes.Methode;
import javafx.scene.layout.Pane;

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

    /**
     * change la visibilité de l'interface/classe
     * @param c
     */
    public void changerVisibilite(Interface c) {
        c.changerVisibilite();
        notifierObservateurs();
    }

    /**
     * Change la visibilité de l'héritage d'une classe/interface.
     *
     * @param c Classe ou interface.
     */
    public void changerVisibiliteHeritage(Interface c) {
        c.changerVisibiliteHeritage();
        notifierObservateurs();
    }

    /**
     * Récupère les noms des classes dans le modèle.
     *
     * @return Liste des noms des classes.
     */
    public List<String> getClassesNoms() {
        List<String> noms = new ArrayList<>();
        for (Interface c : getClasses()) {
            noms.add(c.getNom());
        }
        return noms;
    }

    /**
     * Ajoute une classe ou une interface.
     *
     * @param nom          Nom de la classe ou de l'interface.
     * @param estInterface Indique si c'est une interface.
     */
    public void ajouterClasse(String nom, boolean estInterface) {
        Interface nouvelleClasse = estInterface
                ? new Interface("interface", nom, "")
                : new Interface("class", nom, "");
        this.packages.putIfAbsent("", new ArrayList<>());
        this.packages.get("").add(nouvelleClasse);
        this.positions.put(nouvelleClasse, new Position(0, 0));
        notifierObservateurs();
    }

    /**
     * Ajoute une méthode à une classe existante.
     *
     * @param nomClasse Nom de la classe.
     * @param methode   Méthode à ajouter.
     */
    public void ajouterMethode(String nomClasse, Methode methode) {
        for (Interface c : getClasses()) {
            if (c.getNom().equals(nomClasse)) {
                c.addMethode(methode);
                notifierObservateurs();
                return;
            }
        }
    }

    /**
     * Ajoute un attribut à une classe existante.
     *
     * @param nomClasse Nom de la classe.
     * @param attribut  Attribut à ajouter.
     */
    public void ajouterAttribut(String nomClasse, Attribut attribut) {
        for (Interface c : getClasses()) {
            if (c.getNom().equals(nomClasse)) {
                c.addAttribut(attribut);
                notifierObservateurs();
                return;
            }
        }
    }

   public Pane getDiagrammePane() {
        return null;
    }
}
