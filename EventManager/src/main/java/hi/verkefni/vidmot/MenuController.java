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
        if (mainController != null && mainController.getEventView() != null) {
            // Create a new empty event model
            EventModel newModel = new EventModel("", "Concert", LocalDate.now(), LocalTime.of(12, 0), null);
            mainController.getEventView().setEventModel(newModel);
        }
    }
    
    /**
     * Handler for opening an event file.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Event File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Event Files", "*.evt"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        
        File selectedFile = fileChooser.showOpenDialog(getWindow());
        
        if (selectedFile != null) {
            // Here you would load the event from the file
            // This is just a placeholder for now
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("File Selected");
            alert.setHeaderText(null);
            alert.setContentText("Selected file: " + selectedFile.getName());
            alert.showAndWait();
        }
    }
    
    /**
     * Handler for saving an event file.
     */
    @FXML
    private void handleSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Event File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Event Files", "*.evt"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        
        File selectedFile = fileChooser.showSaveDialog(getWindow());
        
        if (selectedFile != null) {
            // Here you would save the event to the file
            // This is just a placeholder for now
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("File Saved");
            alert.setHeaderText(null);
            alert.setContentText("Event saved to file: " + selectedFile.getName());
            alert.showAndWait();
        }
    }
    
    /**
     * Handler for editing an event.
     */
    @FXML
    private void handleEdit() {
        // This is just a placeholder for now
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Edit Event");
        alert.setHeaderText(null);
        alert.setContentText("Edit event functionality");
        alert.showAndWait();
    }
    
    /**
     * Handler for deleting an event.
     */
    @FXML
    private void handleDelete() {
        // This is just a placeholder for now
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Delete Event");
        alert.setHeaderText(null);
        alert.setContentText("Delete event functionality");
        alert.showAndWait();
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
        Stage stage = (Stage) getWindow();
        stage.close();
    }
}