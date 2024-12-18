package main.java.classes;

public abstract class Visible {
    private boolean visible;

    public Visible() {
        this.visible = true;
    }

    public void changerVisibilite() {
        this.visible = !this.visible;
    }

    public boolean getVisible() {
        return visible;
    }
}
