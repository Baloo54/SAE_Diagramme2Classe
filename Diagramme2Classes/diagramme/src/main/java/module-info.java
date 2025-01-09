module version1 {
    requires javafx.base;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires org.objectweb.asm;
    requires java.desktop;

    // Exporter uniquement les packages nécessaires
    exports app to javafx.graphics;
    exports diagramme;
    exports diagramme.Vues;
    exports diagramme.Vues.decorateur;
    exports analyse.loader;
    exports classes;

    // Les packages restants peuvent être ouverts pour JavaFX uniquement si nécessaire
    opens diagramme.Vues to javafx.graphics;
    opens diagramme.controler to javafx.graphics;
}
