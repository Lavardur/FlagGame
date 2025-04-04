package hi.verkefni.vidmot;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Handles switching between different views in the application.
 */
public class ViewSwitcher {
    private Stage stage;

    /**
     * Constructs a ViewSwitcher with the given stage.
     *
     * @param stage The stage to switch views in.
     */
    public ViewSwitcher(Stage stage) {
        this.stage = stage;
    }

    /**
     * Switches to the view specified by the fxml file path.
     *
     * @param fxmlPath The path to the FXML file for the view.
     */
    public void switchToView(String fxmlPath) {
        try {
            System.out.println("Loading FXML from: " + fxmlPath);
            
            // Create a new loader each time
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            
            // Load the FXML file
            Parent root = loader.load();
            
            // Get the controller for debugging
            Object controller = loader.getController();
            System.out.println("Controller loaded: " + (controller != null ? controller.getClass().getName() : "null"));
            
            // Create and set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error switching to view: " + fxmlPath);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error loading view: " + fxmlPath);
            e.printStackTrace();
        }
    }
}