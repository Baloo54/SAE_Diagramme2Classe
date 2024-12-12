package diagramme;

import java.io.File;
import java.util.ArrayList;
/**
 * classe model
 */
public class Model implements Sujet{
    /**
     * Attributs
     * folder correspond au dossier contenant le package .class
     */
    private ArrayList<Observateur> observateurs;
    private File folder;
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
     * @param File folder
     */
    public void ajouterPackage(File folder){
        this.folder = folder;
        System.out.println(folder.getAbsolutePath());
    }
}
