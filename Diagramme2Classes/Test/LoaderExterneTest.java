import diagramme.loader.LoaderExterne;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoaderExterneTest {

    @Test
    public void testChargerClasse() {
            try {
                LoaderExterne loader = LoaderExterne.getInstance();
                Class<?> c = loader.loadClassFromFile("C:\\LoaderExterne.class");
                assertEquals("diagramme.loader.LoaderExterne", c.getName());
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
}
