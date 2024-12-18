package analyse;

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
     * @throws ClassNotFoundException
     */
    @BeforeEach
    void setup() throws ClassNotFoundException {
        analyseur = new Analyseur("diagramme.analyse.Analyseur");
    }

    /**
     * Test de la méthode getNomClasse, retourne le nom de la classe
     */
    @Test
    void testGetNomClasse() {
        String nomClasse = analyseur.getNomClasse();
        assertEquals("diagramme.analyse.Analyseur", nomClasse);
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

    /**
     * Test de la méthode trierAttributsParModificateur, retourne les attributs triés par modificateur
     * tri les attributs par modificateur dans une map
     * @throws ClassNotFoundException
     */
    @Test
    void trierAttributsParModificateur() throws ClassNotFoundException {
        Analyseur a = new Analyseur("diagramme.analyse.Analyseur");
        Map<String, List<Field>> test = a.trierAttributsParModificateur();
        for (String key : test.keySet()) {
            if (!test.get(key).isEmpty()) {
                assertEquals("private", key);
            }
            for (Field f : test.get(key)) {
                assertEquals("analyseClasse", f.getName());
                assertEquals("java.lang.Class", f.getType().getName());
                assertEquals(Modifier.PRIVATE, f.getModifiers());
                assertEquals(f.getType().getSuperclass().getName(), "java.lang.Object");
            }
        }
    }

    /**
     * Test de la méthode trierMethodesParModificate
     * Tri les méthodes par modificateur dans une map
     */
    @Test
    void testTrierMethodesParModificateur() {
        Method[] methods = analyseur.getClass().getDeclaredMethods();
        Map<String, List<Method>> triMethodes = analyseur.trierMethodesParModificateur(methods);
        assertNotNull(triMethodes);
        assertTrue(triMethodes.containsKey("public"));
        assertTrue(triMethodes.containsKey("protected"));
        assertTrue(triMethodes.containsKey("private"));
    }


    /**
     * Test de la méthode afficherAttributs et afficherMethodes
     * Affiche les attributs et les méthodes de la classe
     */
    @Test
    void AfficherElements() throws ClassNotFoundException {
        @SuppressWarnings("unused")
        Analyseur a = new Analyseur("diagramme.analyse.Analyseur");
        Class<?> cl = Class.forName("diagramme.analyse.Analyseur");
        Analyseur.afficherAttributs(cl);
        Analyseur.afficherMethodes(cl);

    }

    /**
     * Test de la méthode getPackages
     * Retourne les packages de la classe
     */
    @Test
    void testGetPackages() {
        Package[] packages = Analyseur.getPackages();
        assertNotNull(packages);
        assertTrue(packages.length > 0);
    }
}
