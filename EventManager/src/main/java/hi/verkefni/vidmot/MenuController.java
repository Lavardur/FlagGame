package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.EventModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Controller for the menu bar in the EventManager application.
 */
public class MenuController {
    @FXML
    private MenuBar menuBar;
    
    @FXML
    private MenuItem exitMenuItem;
    
    @FXML
    private MenuItem newMenuItem;
    
    @FXML
    private MenuItem openMenuItem;
    
    @FXML
    private MenuItem saveMenuItem;
    
    @FXML
    private MenuItem editMenuItem;
    
    @FXML
    private MenuItem deleteMenuItem;
    
    @FXML
    private MenuItem aboutMenuItem;
    
    /**
     * Reference to the main controller to access the EventView
     */
    private EventManagerController mainController;
    
    /**
     * Set the main controller reference
     * @param controller The main EventManagerController
     */
    public void setMainController(EventManagerController controller) {
        this.mainController = controller;
    }
    
    /**
     * Get the window from the menu bar
     * @return The window containing the menu bar
     */
    private Window getWindow() {
        return menuBar.getScene().getWindow();
    }

    /**
     * Handler for creating a new event.
     */
    @FXML
    private void handleNew() {
        if (mainController != null) {
            mainController.nyr();
        }
    }
    
    /**
     * Handler for opening an event file.
     */
    @FXML
    private void handleOpen() {
        if (mainController != null) {
            mainController.opna();
        }
    }
    
    /**
     * Handler for saving an event file.
     */
    @FXML
    private void handleSave() {
        if (mainController != null) {
            mainController.vista();
        }
    }
    
    /**
     * Handler for editing an event.
     */
    @FXML
    private void handleEdit() {
        // This is just a placeholder, could be implemented if needed
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Edit Event");
        alert.setHeaderText(null);
        alert.setContentText("Edit event functionality is handled directly in the form.");
        alert.showAndWait();
    }
    
    /**
     * Handler for deleting an event.
     */
    @FXML
    private void handleDelete() {
        if (mainController != null) {
            mainController.eyda();
        }
    }
    
    /**
     * Handler for showing the about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About Event Manager");
        alert.setHeaderText("Event Manager Application");
        alert.setContentText("This application was created as a project for the HÍ Viðmótsforritun course. " +
                            "It allows you to create and manage events with promotional videos.");
        alert.showAndWait();
    }
    
    /**
     * Handler for closing the application.
     */
    @FXML
    private void handleClose() {
        if (mainController != null) {
            mainController.loka();
        } else {
            // If we can't access the main controller, just close the window
            Stage stage = (Stage) getWindow();
            stage.close();
        }
    }
}