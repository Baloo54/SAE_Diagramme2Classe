module version1 {
    requires javafx.controls;
    requires javafx.base;
    requires transitive javafx.graphics;
    requires org.objectweb.asm;// librairie ow2.asm
    requires java.desktop;
    exports diagramme;
    requires org.apache.pdfbox;//org.apache.pdfbox:pdfbox
    exports diagramme.Vues.decorateur;
    exports analyse.loader;
    exports app;
    exports classes;
    exports diagramme.Vues;
    exports diagramme.controler;
}