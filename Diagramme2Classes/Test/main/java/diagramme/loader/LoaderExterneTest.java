package main.java.diagramme.loader;

import main.java.diagramme.exception.FichierIllisibleException;
import main.java.diagramme.exception.FichierIncompatibleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoaderExterneTest {

    @Test
    void findClass() {
        LoaderExterne a = new LoaderExterne();
        try {
            Class c = a.findClass("C:/LoaderExterne.class");
            assertEquals(c.getPackage().getName(), "main.java.diagramme.loader.LoaderExterne");
        } catch (ClassNotFoundException e) {
            if (e instanceof FichierIllisibleException) {
                assertEquals(false, false);
            } else if (e instanceof FichierIncompatibleException) {
                assertEquals(true, false);
            }
            fail();
        }
    }
}