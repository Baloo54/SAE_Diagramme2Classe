module version1 {
    requires javafx.controls;
    requires javafx.base;
    requires transitive javafx.graphics;
    requires org.objectweb.asm;
    exports diagramme;
    exports app;
}