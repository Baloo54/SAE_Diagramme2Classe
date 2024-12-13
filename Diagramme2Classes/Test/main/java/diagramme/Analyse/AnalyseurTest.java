package main.java.diagramme.Analyse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AnalyseurTest {


    private Analyseur analyseur;

    @BeforeEach
    void setup() throws ClassNotFoundException {
        analyseur = new Analyseur("main.java.diagramme.Analyse.Analyseur");
    }

    @Test
    void testGetNomClasse() {
        String nomClasse = analyseur.getNomClasse();
        assertEquals("main.java.diagramme.Analyse.Analyseur", nomClasse);
    }

    @Test
    void testGetClasseParent() {
        Class<?> classeParent = analyseur.getClasseParent();
        assertNotNull(classeParent);
        assertEquals(Object.class, classeParent);
    }

    @Test
    void testGetInterfaces() {
        Class<?>[] interfaces = analyseur.getInterfaces();
        assertNotNull(interfaces);
        assertEquals(0, interfaces.length);
    }

    @Test
    void trierAttributsParModificateur() throws ClassNotFoundException {
        Analyseur a = new Analyseur("main.java.diagramme.Analyse.Analyseur");
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

    @Test
    void testTrierMethodesParModificateur() {
        Method[] methods = analyseur.getClass().getDeclaredMethods();
        Map<String, List<Method>> triMethodes = analyseur.trierMethodesParModificateur(methods);
        assertNotNull(triMethodes);
        assertTrue(triMethodes.containsKey("public"));
        assertTrue(triMethodes.containsKey("protected"));
        assertTrue(triMethodes.containsKey("private"));
    }

    @Test
    void AfficherElements() throws ClassNotFoundException {
        Analyseur a = new Analyseur("main.java.diagramme.Analyse.Analyseur");
        Class<?> cl = Class.forName("main.java.diagramme.Analyse.Analyseur");
        Analyseur.afficherAttributs(cl);
        Analyseur.afficherMethodes(cl);

    }

    @Test
    void testGetPackages() {
        Package[] packages = Analyseur.getPackages();
        assertNotNull(packages);
        assertTrue(packages.length > 0);
    }
}
