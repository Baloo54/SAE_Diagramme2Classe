package diagramme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * classe model
 */
public class Model implements Sujet{
    /**
     * Attributs
     * folder correspond au dossier contenant le package .class
     */
    private ArrayList<Observateur> observateurs;
    @SuppressWarnings("unused")
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
     * @param folder folder
     */
    public void ajouterPackage(File folder){
        this.folder = folder;
    }
    /**
     *giveListeClasses
     */
    public List<String> giveListeClasse(){
        ReadFile reader = new ReadFile();
        return reader.findClassFiles(this.folder);
    }

}
