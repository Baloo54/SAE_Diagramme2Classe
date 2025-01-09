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
import java.util.Map;

/**
 * Classe Model
 * Représente le modèle pour l'analyse des classes et des relations.
 * Implémente le motif Observateur.
 */
public class Model implements Sujet{
    /**
     * Attributs
     * folder correspond au dossier contenant le package .class
     */
    private ArrayList<Observateur> observateurs;
    private HashMap<String, ArrayList<Interface>> packages = new HashMap<>();
    private HashMap<Interface, Position> positions = new HashMap<>();
    private ArrayList<Interface> classes;
    private HashMap<Interface, ArrayList<HashMap<String, Interface>>> relations;


    /**
     * Constructeur
     * Initialise les structures de données.
     */
    public Model() {
        this.observateurs = new ArrayList<>();
        this.classes = new ArrayList<>();
        this.positions = new HashMap<>();
        this.relations = new HashMap<>();
    }
    /**
     * Ajoute un observateur à la liste.
     * @param observateur L'observateur à ajouter.
     */
    public void ajouterObservateur(Observateur observateur) {
        this.observateurs.add(observateur);
    }

    /**
     * Notifie tous les observateurs.
     */
    public void notifierObservateurs() {
        for (Observateur observateur : this.observateurs) {
            observateur.actualiser(this);
        }
    }

    // Gestion des classes et packages

    /**
     * Ajoute un package et analyse ses classes.
     * @param folder Le dossier contenant les fichiers .class.
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
        rechercheRelation();
        notifierObservateurs();
    }


    /**
     * Recherche des relations entre les classes en analysant attributs et méthodes.
     */
    private void rechercheRelation() {
        for (Interface classe : this.classes) {
            // Analyse des attributs
            for (Attribut attribut : classe.getAttributs()) {
                ajouterRelationSiExiste(classe, attribut.getNom());
            }

            // Analyse des paramètres de méthodes
            for (Methode methode : classe.getMethodes()) {
                List<HashMap<String, String>> hash = methode.getParametres();
                for (HashMap<String, String> map : hash) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String typeParametre = entry.getValue().split("arg")[0];
                        ajouterRelationSiExiste(classe, typeParametre);
                    }
                }
            }
        }
    }

    /**
     * Ajoute une relation si la classe associée existe.
     * @param classe La classe source.
     * @param nom Le nom de la classe cible.
     */
    private void ajouterRelationSiExiste(Interface classe, String nom) {
        Interface associe = existeClasse(nom);
        if (associe != null) {
            HashMap<String, Interface> relation = new HashMap<>();
            relation.put("association", associe);
            ajouterRelation(classe, relation);
        }
    }

    /**
     * Ajoute une relation à la liste des relations.
     * @param cle La classe source.
     * @param nouvelElement La relation à ajouter.
     */
    public void ajouterRelation(Interface cle, HashMap<String, Interface> nouvelElement) {
        ArrayList<HashMap<String, Interface>> liste = relations.getOrDefault(cle, new ArrayList<>());
        if (!liste.contains(nouvelElement)) {
            liste.add(nouvelElement);
            relations.put(cle, liste);
        }
    }

    // Gestion des positions et déplacements

    /**
     * Obtient la position d'une classe.
     * @param c La classe.
     * @return La position de la classe.
     */
    public Position getPosition(Interface c) {
        return positions.get(c);
    }

    /**
     * Déplace une classe vers une nouvelle position.
     * @param c La classe.
     * @param p La nouvelle position.
     */
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

    public ArrayList<Interface> getClasses() {
        ArrayList<Interface> classes = new ArrayList<>();
        for (String key : this.packages.keySet()) {
            classes.addAll(this.packages.get(key));
        }
        return classes;
    }
        /**
     * Vérifie si une classe existe.
     * @param nom Le nom de la classe.
     * @return La classe si elle existe, sinon null.
     */
    private Interface existeClasse(String nom) {
        for (Interface classe : this.classes) {
            if (nom.equals(classe.getNom())) {
                return classe;
            }
        }
        return null;
    }
}
