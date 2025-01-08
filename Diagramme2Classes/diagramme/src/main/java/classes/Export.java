package classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Export {

    /**
     * Exporte toutes les classes vers un diagramme UML en format Puml
     *
     * @param classes : liste des classes à exporter
     * @return le nom du fichier généré
     */
    public String exportPuml(List<Classe> classes) {
        StringBuilder puml = new StringBuilder();
        puml.append("@startuml\n");

        // Exportation de toutes les classes
        for (Classe classe : classes) {
            puml.append("class ").append(classe.getNom()).append(" {\n");

            // Exportation des attributs
            for (Attribut attribut : classe.getAttributs()) {
                ArrayList<String> modificateurs = (ArrayList<String>) attribut.getModificateurs();
                String visibility = getPumlModificateur(modificateurs);
                puml.append("    ").append(visibility)
                        .append(" ").append(attribut.getNom())
                        .append(" : ").append(attribut.getType()).append("\n");
            }

            // Exportation des méthodes
            for (Methode methode : classe.getMethodes()) {
                String visibility = getPumlModificateur(methode.getModificateurs());
                puml.append("    ").append(visibility)
                        .append(" ").append(methode.getNom()).append("(");

                List<HashMap<String, String>> parametres = methode.getParametres();
                for (int i = 0; i < parametres.size(); i++) {
                    HashMap<String, String> param = parametres.get(i);
                    puml.append(param.get("nom")).append(" : ").append(param.get("type"));
                    if (i < parametres.size() - 1) {
                        puml.append(", ");
                    }
                }

                puml.append(") : ").append(methode.getTypeRetour()).append("\n");
            }
            puml.append("}\n");

            // Relations avec la classe parent (si applicable)
            if (classe.getClasseParent() != null) {
                puml.append(classe.getClasseParent().getNom())
                        .append(" <|-- ").append(classe.getNom()).append("\n");
            }

            // Exportation des interfaces
            for (Interface inter : classe.getInterfaces()) {
                puml.append("interface ").append(inter.getNom()).append(" {\n");
                for (Methode methode : inter.getMethodes()) {
                    puml.append("    + ").append(methode.getNom()).append("()\n");
                }
                puml.append("}\n");
                puml.append(inter.getNom()).append(" <|.. ").append(classe.getNom()).append("\n");
            }
        }

        puml.append("@enduml\n");

        // Écriture dans un fichier
        String fileName = "diagramme2Classe.puml";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(puml.toString());
            System.out.println("Fichier généré : " + fileName);
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
        }

        return fileName;
    }

    /**
     * Méthode permettant de traduire les modificateurs au format Puml
     *
     * @param modificateurs : liste des modificateurs
     * @return modificateurs au format Puml
     */
    private String getPumlModificateur(List<String> modificateurs) {
        if (modificateurs.contains("private")) return "-";
        if (modificateurs.contains("protected")) return "#";
        return "+";
    }
}
    