package diagramme.loader;

import org.objectweb.asm.*;

import java.io.FileInputStream;
import java.io.IOException;

public class SimpleDecompiler {

    public static String getNomClasse(String chemin) throws IOException {
        // Vérifiez si un fichier .class a été fourni en argument

        // Le chemin du fichier .class à analyser (en utilisant l'argument de ligne de commande)
        String classFilePath = "C:\\LoaderExterne.class";

        // Charger le fichier .class
        FileInputStream fileInputStream = new FileInputStream(classFilePath);
        ClassReader classReader = new ClassReader(fileInputStream);
        final String[] className = new String[1];
        // Analyser la classe
        classReader.accept(new ClassVisitor(Opcodes.ASM9) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                // Convertir le nom de la classe et la signature en format lisible
                // Remplacer les "/" par "." pour obtenir le nom complet de la classe
                className[0] = name.replace("/", ".");
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                // Afficher les méthodes dans la classe
                return new MethodVisitor(Opcodes.ASM9) {
                    @Override
                    public void visitInsn(int opcode) {
                    }
                };
            }

        }, 0);
        return className[0];
    }
}
