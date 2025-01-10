package diagramme;

import analyse.Analyseur;
import classes.Attribut;
import classes.Classe;
import classes.Interface;
import classes.Methode;
import diagramme.Vues.VueClasse;
import diagramme.Vues.arrow.FabriqueAbstraiteVueFleche;
import diagramme.Vues.arrow.FlechePointille;
import diagramme.Vues.arrow.FlecheTeteRemplie;
import diagramme.Vues.arrow.FlecheTeteRempliePointille;
import diagramme.controler.DeplacementControler;
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
    private HashMap<Interface, ArrayList<HashMap<String, Interface>>> relations;
    private HashMap<Interface, VueClasse> vuesClasses;
    private ArrayList<HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>>> vuesFleches;

    /**
     * Constructeur
     * Initialise les structures de données.
     */
    public Model() {
        this.observateurs = new ArrayList<>();
        this.packages = new HashMap<>();
        this.positions = new HashMap<>();
        this.relations = new HashMap<>();
        this.vuesClasses = new HashMap<>();
        this.vuesFleches = new ArrayList<>();
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
        creerVue();
        notifierObservateurs();
    }

    /**
     * Recherche des relations entre les classes en analysant attributs et méthodes.
     */
    private void rechercheRelation() {
        for (Map.Entry<String, ArrayList<Interface>> entry : packages.entrySet()) {
        ArrayList<Interface> interfaces = entry.getValue();
            for (Interface classe : interfaces) {
                // Analyse des attributs
                for (Attribut attribut : classe.getAttributs()) {
                    ajouterRelationSiExiste(classe, attribut.getNom(), "association");
                }

                // Analyse des paramètres de méthodes
                for (Methode methode : classe.getMethodes()) {
                    for (HashMap<String, String> map : methode.getParametres()) {
                        for (String typeParametre : map.values()) {
                            ajouterRelationSiExiste(classe, typeParametre.split("arg")[0], "association");
                        }
                    }
                }
                // Analyse des classes parents donc héritage
                if (classe instanceof Classe) {
                    Classe classeAnalysee = (Classe) classe; // Caster l'interface en classe
                    Classe classeParent = classeAnalysee.getClasseParent();
                    if(classeParent!=null){
                        ajouterRelationSiExiste(classe, classeParent.getNom(), "heritage");
                    }
                }
                // Analyse des classes implemente
                for(Interface i : classe.getInterfaces()){
                    ajouterRelationSiExiste(classe, i.getNom(), "implementation");
                }
            }
        }
    }
    /**
     * Ajoute une relation si la classe associée existe.
     * @param classe La classe source.
     * @param nom Le nom de la classe cible.
     */
    private void ajouterRelationSiExiste(Interface classe, String nom, String type) {
        Interface associe = existeClasse(nom);
        if (associe != null) {
            HashMap<String, Interface> relation = new HashMap<>();
            relation.put(type, associe);
            ajouterRelation(classe, relation);
        }
    }
    /**
     * Vérifie si une classe existe.
     * @param nom Le nom de la classe.
     * @return La classe si elle existe, sinon null.
     */
    private Interface existeClasse(String nom) {
        for (Map.Entry<String, ArrayList<Interface>> entry : packages.entrySet()) {
            ArrayList<Interface> interfaces = entry.getValue();
            for (Interface classe : interfaces) {
                if (nom.equals(classe.getNom())) {
                    return classe;
                }
            }
        }
        return null;
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
    /**
     * création des Vuesclasses et vueFleches
     */
    public void creerVue(){
        for (Map.Entry<String, ArrayList<Interface>> entry : packages.entrySet()) {
            ArrayList<Interface> interfaces = entry.getValue();
            for (Interface classe : interfaces) {
                Position position = getPosition(classe);
                VueClasse nouvelleVue = new VueClasse(classe);
                DeplacementControler controler = new DeplacementControler(this);
                controler.ajouterEvenements(nouvelleVue);
                nouvelleVue.setTranslateX(position.getX());
                nouvelleVue.setTranslateY(position.getY());
                vuesClasses.put(classe, nouvelleVue);
            }
        }
        for (Map.Entry<Interface, ArrayList<HashMap<String, Interface>>> entree : relations.entrySet()) {
            // récupère la classevue source
            VueClasse source = vuesClasses.get(entree.getKey());
            // Parcourt les relations associées
            for (HashMap<String, Interface> relation : entree.getValue()) {
                for (Map.Entry<String, Interface> rel : relation.entrySet()) {
                    //récupère la classvue cible
                    VueClasse cible = vuesClasses.get(rel.getValue());
                    FabriqueAbstraiteVueFleche fleche;
                    switch (rel.getKey()) {
                        case "heritage": 
                            fleche = new FlecheTeteRempliePointille(0,0,0,0);
                            break;

                        case "implementation": 
                            fleche = new FlechePointille(0,0,0,0);
                            break;
                        default:
                        fleche = new FlecheTeteRemplie(0,0,0,0);
                        break;
                    }
                    fleche.setMouseTransparent(true);
                    HashMap<VueClasse, VueClasse> relationInterne = new HashMap<>();
                    relationInterne.put(source, cible);

                    HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>> relationExterne = new HashMap<>();
                    relationExterne.put(fleche, relationInterne);
                    vuesFleches.add(relationExterne);
                }
            }
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
     * Change la visibilité de l'interface/classe.
     * @param c La classe ou l'interface.
     */
    public void changerVisibilite(Interface c) {
        c.changerVisibilite();
        int i = packages.get(c.getPackageClasse()).indexOf(c);
        packages.get(c.getPackageClasse()).set(i, c);
        vuesClasses.get(c).setClasse(c);
        vuesClasses.get(c).mettreAJourAffichage();
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
     * Retourne un panneau de diagramme (placeholder).
     * @return Panneau de diagramme.
     */
    public Pane getDiagrammePane() {
        return null;
    }
    /**
     * 
     * @return liste des packages
     */
    public HashMap<String, ArrayList<Interface>> getPackages() {
        return packages;
    }

    public ArrayList<VueClasse> getVueClasse() {
        return new ArrayList<>(vuesClasses.values());
    }

    public ArrayList<HashMap<FabriqueAbstraiteVueFleche, HashMap<VueClasse, VueClasse>>> getVueFleches() {
        return vuesFleches;
    }
    /**
     * change la visibilité des méthodes de l'interface/classe
     * @param c
     */
    public void changerVisibiliteMethode(Interface c, Methode m) {
        c.changerVisibiliteMethode(m);
        int i = packages.get(c.getPackageClasse()).indexOf(c);
        packages.get(c.getPackageClasse()).set(i, c);
        vuesClasses.get(c).setClasse(c);
        vuesClasses.get(c).mettreAJourAffichage();
        notifierObservateurs();
    }
}
