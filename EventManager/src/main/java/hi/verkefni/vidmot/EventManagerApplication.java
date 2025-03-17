package hi.verkefni.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aðalklasi fyrir EventManager forritið.
 */
public class EventManagerApplication extends Application {
    
    // Lágmarksstærðir til að tryggja að allt efni sé sýnilegt
    private static final double MIN_WIDTH = 800;
    private static final double MIN_HEIGHT = 750;
    
    /**
     * Aðferð sem keyrir þegar forritið er ræst.
     * 
     * @param primaryStage Aðalsvið forritsins.
     * @throws Exception Ef villa kemur upp við hleðslu FXML skrárinnar.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Uppfærir eventmanager-view.fxml til að nota fx:id fyrir innifalið FXML
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/eventmanager-view.fxml"));
        Parent root = mainLoader.load();

        // Setur upp sviðið
        Scene scene = new Scene(root, 800, 750);
        
        // Setur lágmarksstærðir gluggans
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        
        primaryStage.setTitle("Viðburðarstjóri");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Aðal aðferð sem ræst forritið.
     * 
     * @param args Rök sem eru send til forritsins.
     */
    public static void main(String[] args) {
        launch(args);
    }
}