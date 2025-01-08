module version1 {
    requires javafx.controls;
    requires javafx.base;
    requires transitive javafx.graphics;
    requires org.objectweb.asm;
    requires java.desktop; // librairie ow2.asm
    exports diagramme;
    exports diagramme.Vues.decorateur;
    exports analyse.loader;
    exports app;
    exports classes;
    exports diagramme.Vues;
    exports diagramme.controler;
}