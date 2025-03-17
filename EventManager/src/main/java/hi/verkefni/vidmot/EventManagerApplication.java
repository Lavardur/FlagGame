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
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Update the eventmanager-view.fxml to use fx:id for included FXML
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/eventmanager-view.fxml"));
        Parent root = mainLoader.load();
        
        // Get the main controller
        EventManagerController mainController = mainLoader.getController();
        
        // Set up the scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Event Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}