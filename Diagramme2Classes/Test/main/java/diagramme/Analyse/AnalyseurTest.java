package main.java.diagramme.Analyse;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AnalyseurTest {

    @Test
    void trierAttributsParModificateur() throws ClassNotFoundException {
        Analyseur a = new Analyseur("main.java.diagramme.Analyse.Analyseur");
         Map<String, List<Field>> test =a.trierAttributsParModificateur();
         for (String key : test.keySet()) {
             if(test.get(key).size() != 0) {
                 assertEquals("private", key);
             }
             for (Field f : test.get(key)) {
                    assertEquals("analyseClasse", f.getName());
                    assertEquals("java.lang.Class", f.getType().getName());
                    assertEquals(Modifier.PRIVATE, f.getModifiers());
                    assertEquals(f.getType().getSuperclass().getName(),"java.lang.Object");
             }
         }
    }
}