package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.EventModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private MediaViewController mediaViewController;
    
    @FXML
    private StackPane fxEventViews;
    
    // Current visible event in the UI
    private EventView currentView;  // núverandi viðburður í viðmótinu
    
    // List of all events (EventModel - this is the data model)
    private ObservableList<EventModel> list = // listi af viðburðum
            FXCollections.observableArrayList();
    
    // Map to keep track of which EventView is associated with which EventModel
    private Map<EventModel, EventView> viewMap = new HashMap<>();
    
    

    /**
     * Initialize the controller.
     * Sets the first EventView as the current event.
     */
    @FXML
    private void initialize() {
        // Connect the controllers
        if (menuViewController != null) {
            menuViewController.setMainController(this);
        }
        
        // Try to find the StackPane if it wasn't injected
        if (fxEventViews == null) {
            // The StackPane is in the center of the BorderPane
            if (rootPane.getCenter() instanceof StackPane) {
                fxEventViews = (StackPane) rootPane.getCenter();
            }
        }
        
        // Set the initial EventView as the current view
        if (eventView != null) {
            currentView = eventView;
            list.add(currentView.getEventModel());
            viewMap.put(currentView.getEventModel(), currentView);
            
            // Connect the MediaViewController to the current EventModel
            if (mediaViewController != null) {
                mediaViewController.setMainController(this);
                mediaViewController.setEventModel(currentView.getEventModel());
            }
        } else {
            // If no EventView was injected, create a new one
            nyr();
        }
    }
    
    /**
     * Creates a new event.
     * Creates a new EventView, makes it the current event, and adds it to fxEventViews.
     */
    public void nyr() {
        // Create a new EventView
        EventView newView = new EventView();
        currentView = newView;
        
        // Add to the list and map
        EventModel model = currentView.getEventModel();
        list.add(model);
        viewMap.put(model, currentView);
        
        // Add to UI
        if (fxEventViews != null) {
            fxEventViews.getChildren().add(currentView);
            switchView(currentView);
        }
        
        // Update the media controller with the new model
        if (mediaViewController != null) {
            mediaViewController.setEventModel(model);
        }
    }
    
    /**
     * Saves the current event to memory.
     * Takes the currentView and saves it to memory (list).
     */
    public void vista() {
        if (currentView != null) {
            EventModel model = currentView.getEventModel();
            
            // Check if this model is already in the list
            if (!list.contains(model)) {
                list.add(model);
            }
            
            // Make sure it's in the map
            viewMap.put(model, currentView);
        }
    }
    
    /**
     * Opens an existing event.
     * Asks the user for the event name, looks it up in memory.
     * If it exists, the corresponding EventView is displayed.
     * If not, a new EventView pointing to the EventModel is created.
     */
    public void opna() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Open Event");
        dialog.setHeaderText("Enter Event Name");
        dialog.setContentText("Event name:");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            String eventName = result.get();
            
            // Find the event model with the given name
            EventModel foundModel = null;
            for (EventModel model : list) {
                if (model.eventNameProperty().get().equals(eventName)) {
                    foundModel = model;
                    break;
                }
            }
            
            if (foundModel != null) {
                // Check if we already have a view for this model
                EventView view = viewMap.get(foundModel);
                
                if (view != null) {
                    // View exists, switch to it
                    currentView = view;
                    switchView(currentView);
                } else {
                    // Create a new view for this model
                    EventView newView = new EventView();
                    newView.setEventModel(foundModel);
                    viewMap.put(foundModel, newView);
                    
                    // Add to UI
                    fxEventViews.getChildren().add(newView);
                    currentView = newView;
                    switchView(currentView);
                }
                
                // Update the media controller with this model
                if (mediaViewController != null) {
                    mediaViewController.setEventModel(foundModel);
                }
            }
        }
    }
    
    /**
     * Deletes the current event.
     * Removes the current event from the StackPane children.
     * Removes the corresponding model from the list.
     * If there are remaining children, shows the last one added.
     */
    public void eyda() {
        if (currentView != null) {
            // Remove from UI
            fxEventViews.getChildren().remove(currentView);
            
            // Remove from data structures
            EventModel model = currentView.getEventModel();
            list.remove(model);
            viewMap.remove(model);
            
            // If there are any events left, show the last one
            if (!fxEventViews.getChildren().isEmpty()) {
                Node lastChild = fxEventViews.getChildren().get(fxEventViews.getChildren().size() - 1);
                if (lastChild instanceof EventView) {
                    currentView = (EventView) lastChild;
                    switchView(currentView);
                    
                    // Update the media controller
                    if (mediaViewController != null) {
                        mediaViewController.setEventModel(currentView.getEventModel());
                    }
                }
            } else {
                currentView = null;
                
                // Clear the media controller
                if (mediaViewController != null) {
                    mediaViewController.setEventModel(null);
                }
            }
        }
    }
    
    /**
     * Closes the current event view.
     * Removes currentView from StackPane.
     * If any events remain, switches to the last event in StackPane.
     */
    public void loka() {
        if (currentView != null) {
            // Remove from UI
            fxEventViews.getChildren().remove(currentView);
            
            // If there are any events left, show the last one
            if (!fxEventViews.getChildren().isEmpty()) {
                Node lastChild = fxEventViews.getChildren().get(fxEventViews.getChildren().size() - 1);
                if (lastChild instanceof EventView) {
                    currentView = (EventView) lastChild;
                    switchView(currentView);
                    
                    // Update the media controller
                    if (mediaViewController != null) {
                        mediaViewController.setEventModel(currentView.getEventModel());
                    }
                }
            } else {
                currentView = null;
                
                // Clear the media controller
                if (mediaViewController != null) {
                    mediaViewController.setEventModel(null);
                }
            }
        }
    }
    
    /**
     * Helper method to switch the visible view.
     * 
     * @param targetView The view to make visible
     */
    private void switchView(EventView targetView) {
        for (Node node : fxEventViews.getChildren()) {
            node.setVisible(false); // Hide all events
        }
        targetView.setVisible(true); // Show the target view
        targetView.setFocusTraversable(true);
    }
    
    /**
     * Gets the current EventView component.
     * @return The current EventView
     */
    public EventView getEventView() {
        return currentView;
    }
}