package analyse.loader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * La classe {@code SimpleDecompiler} permet de décompiler un fichier .class pour extraire des informations, 
 * notamment le nom de la classe contenue dans ce fichier.
 * Elle utilise la bibliothèque ASM pour analyser le bytecode Java et récupérer le nom de la classe.
 */
public class SimpleDecompiler {

    /**
     * Récupère le nom de la classe à partir d'un fichier .class en utilisant la bibliothèque ASM.
     * 
     * <p>Cette méthode ouvre le fichier .class spécifié, analyse le bytecode et extrait le nom de la classe.</p>
     *
     * @param chemin Le chemin absolu du fichier .class à analyser.
     * @return Le nom de la classe, sous forme de chaîne de caractères, en utilisant le format Java (avec des points au lieu des barres obliques).
     * @throws IOException Si une erreur survient lors de la lecture du fichier.
     */
    public static String getNomClasse(String chemin) throws IOException {
        // Charger le fichier .class
        FileInputStream fileInputStream = new FileInputStream(chemin);
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
                // Cette méthode est appelée pour chaque méthode, mais ici elle n'est pas utilisée.
                return new MethodVisitor(Opcodes.ASM9) {
                    @Override
                    public void visitInsn(int opcode) {
                        // Ne rien faire avec les instructions des méthodes pour cet exemple.
                    }
                };
            }

        }, 0);
        
        // Retourner le nom de la classe
        return className[0];
    }
}
