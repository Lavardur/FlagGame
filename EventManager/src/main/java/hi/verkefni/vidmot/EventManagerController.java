package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Main controller for the EventManager application.
 */
public class EventManagerController {
    
    @FXML
    private EventView eventView;
    
    @FXML
    private BorderPane rootPane;
    
    @FXML
    private MenuController menuViewController;
    
    @FXML
    private void initialize() {
        // Connect the controllers
        if (menuViewController != null) {
            menuViewController.setMainController(this);
        }
    }
    
    /**
     * Gets the EventView component.
     * @return The EventView component
     */
    public EventView getEventView() {
        return eventView;
    }
}