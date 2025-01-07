
import static org.junit.Assert.assertTrue;

import analyse.Analyseur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Classe de test pour la classe Analyseur
 */

class AnalyseurTest {

    /**
     * Analyseur à tester
     */
    private Analyseur analyseur;


    /**
     * Initialisation de l'analyseur avant chaque test
     *
     * @throws ClassNotFoundException
     */
    @BeforeEach
    void setup() throws ClassNotFoundException {
        Analyseur analyseur = Analyseur.getInstance();
    }

    /**
     * Test de la méthode getNomClasse, retourne le nom de la classe
     */
    @Test
    void testGetNomClasse() {
        String nomClasse = analyseur.getNomClasse();
        assertEquals("analyse.Analyseur", nomClasse);
    }

    /**
     * Test de la méthode getClasseParent, retourne la classe parent
     */
    @Test
    void testGetClasseParent() {
        Class<?> classeParent = analyseur.getClasseParent();
        assertNotNull(classeParent);
        assertEquals(Object.class, classeParent);
    }

    /**
     * Test de la méthode getInterfaces, retourne les interfaces implémentées
     */
    @Test
    void testGetInterfaces() {
        Class<?>[] interfaces = analyseur.getInterfaces();
        assertNotNull(interfaces);
        assertEquals(0, interfaces.length);
    }

}
