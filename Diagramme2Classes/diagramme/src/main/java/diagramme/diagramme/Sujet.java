package main.java.diagramme.diagramme;
/**
 * interface sujet
 */
public interface Sujet {
    public void ajouterObservateur(Observateur o);
    public void notifierObservateurs();
}