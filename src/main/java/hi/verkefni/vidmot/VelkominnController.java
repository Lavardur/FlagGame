package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class VelkominnController {

    @FXML
    private Button spurningarButton;

    @FXML
    private Button haettaButton;
    
    @FXML
    private ImageView flagImageView; // Add this field to match the fx:id we'll add

    /**
     * Initialize method called after FXML is loaded
     */
    @FXML
    public void initialize() {
        // Set a random flag to the image view
        FlagImageUtils.setRandomFlag(flagImageView);
    }

    /**
     * Handles the action when the start game button is clicked.
     */
    @FXML
    private void handleSpurningarButtonAction() {
        System.out.println("Start game button pressed");
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) spurningarButton.getScene().getWindow());
        viewSwitcher.switchToView("/game-setup-view.fxml");
    }

    /**
     * Handles the action when the exit button is clicked.
     */
    @FXML
    private void handleHaettaButtonAction() {
        System.out.println("Exit button pressed");
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) haettaButton.getScene().getWindow());
        viewSwitcher.switchToView("/kvedja-view.fxml");
    }
}