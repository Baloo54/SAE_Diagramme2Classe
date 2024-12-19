module version1 {
    requires javafx.controls;
    requires javafx.base;
    requires transitive javafx.graphics;
    requires org.objectweb.asm; // librairie ow2.asm
    exports diagramme;
    exports diagramme.loader;
    exports app;
}