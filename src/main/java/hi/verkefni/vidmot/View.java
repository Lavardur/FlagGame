package hi.verkefni.vidmot;

import javafx.scene.Parent;

public abstract class View {
    protected Parent root;

    public abstract Parent getRoot();

    public void setRoot(Parent root) {
        this.root = root;
    }
}