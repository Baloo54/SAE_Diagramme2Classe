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
public class Model implements Sujet {

    // Attributs
    private ArrayList<Observateur> observateurs;
    private HashMap<String, ArrayList<Interface>> packages;
    private HashMap<Interface, Position> positions;
    private ArrayList<Interface> classes;
    private HashMap<Interface, ArrayList<HashMap<String, Interface>>> relations;

    /**
     * Constructeur
     * Initialise les structures de données.
     */
    public Model() {
        this.observateurs = new ArrayList<>();
        this.packages = new HashMap<>();
        this.positions = new HashMap<>();
        this.classes = new ArrayList<>();
        this.relations = new HashMap<>();
    }

    // Gestion des observateurs

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
                this.packages.computeIfAbsent(c.getPackageClasse(), k -> new ArrayList<>()).add(c);
                this.positions.put(c, new Position(0, 0));
            } catch (ClassNotFoundException | IOException e) {
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
                for (HashMap<String, String> map : methode.getParametres()) {
                    for (String typeParametre : map.values()) {
                        ajouterRelationSiExiste(classe, typeParametre.split("arg")[0]);
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
            ajouterRelation(classe, Map.of("association", associe));
        }
    }

    /**
     * Ajoute une relation à la liste des relations.
     * @param cle La classe source.
     * @param nouvelElement La relation à ajouter.
     */
    public void ajouterRelation(Interface cle, Map<String, Interface> nouvelElement) {
        relations.computeIfAbsent(cle, k -> new ArrayList<>()).add(new HashMap<>(nouvelElement));
    }

    /**
     * Vérifie si une classe existe.
     * @param nom Le nom de la classe.
     * @return La classe si elle existe, sinon null.
     */
    private Interface existeClasse(String nom) {
        return this.classes.stream().filter(c -> c.getNom().equals(nom)).findFirst().orElse(null);
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
     * Change la visibilité de l'interface/classe.
     * @param c La classe ou l'interface.
     */
    public void changerVisibilite(Interface c) {
        c.changerVisibilite();
        notifierObservateurs();
    }

    /**
     * Change la visibilité de l'héritage d'une classe/interface.
     * @param c Classe ou interface.
     */
    public void changerVisibiliteHeritage(Interface c) {
        c.changerVisibiliteHeritage();
        notifierObservateurs();
    }

    // Gestion des ajouts

    /**
     * Ajoute une classe ou une interface.
     * @param nom Nom de la classe ou de l'interface.
     * @param estInterface Indique si c'est une interface.
     */
    public void ajouterClasse(String nom, boolean estInterface) {
        Interface nouvelleClasse = estInterface
                ? new Interface("interface", nom, "")
                : new Interface("class", nom, "");
        this.packages.computeIfAbsent("", k -> new ArrayList<>()).add(nouvelleClasse);
        this.positions.put(nouvelleClasse, new Position(0, 0));
        notifierObservateurs();
    }

    /**
     * Ajoute une méthode à une classe existante.
     * @param nomClasse Nom de la classe.
     * @param methode Méthode à ajouter.
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
     * @param nomClasse Nom de la classe.
     * @param attribut Attribut à ajouter.
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

    // Accesseurs

    /**
     * Récupère les noms des classes dans le modèle.
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
     * Récupère toutes les classes du modèle.
     * @return Liste des classes.
     */
    public ArrayList<Interface> getClasses() {
        ArrayList<Interface> classes = new ArrayList<>();
        for (ArrayList<Interface> cls : this.packages.values()) {
            classes.addAll(cls);
        }
        return classes;
    }

    /**
     * Récupère les relations entre les classes.
     * @return Les relations.
     */
    public HashMap<Interface, ArrayList<HashMap<String, Interface>>> getRelations() {
        return relations;
    }

    /**
     * Retourne un panneau de diagramme (placeholder).
     * @return Panneau de diagramme.
     */
    public Pane getDiagrammePane() {
        return null;
    }
}
