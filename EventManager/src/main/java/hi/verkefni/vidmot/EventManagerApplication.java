package hi.verkefni.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the EventManager application.
 */
public class EventManagerApplication extends Application {
    
    // Minimum dimensions to ensure all content is visible
    private static final double MIN_WIDTH = 800;
    private static final double MIN_HEIGHT = 750;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Update the eventmanager-view.fxml to use fx:id for included FXML
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/eventmanager-view.fxml"));
        Parent root = mainLoader.load();
        
        // Get the main controller
        EventManagerController mainController = mainLoader.getController();
        
        // Set up the scene
        Scene scene = new Scene(root, 800, 750);
        
        // Set minimum window size constraints
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        
        primaryStage.setTitle("Event Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}