package diagramme.autoload;

public class Autoloader extends ClassLoader {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class loadClass(String name) throws ClassNotFoundException {

        return findClass(name);
    }
}
