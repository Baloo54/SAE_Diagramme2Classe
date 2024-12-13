package main.java.diagramme.autoload;

public class Autoloader extends ClassLoader {
    public Class loadClass(String name) throws ClassNotFoundException {
        return findClass(name);
    }
}
