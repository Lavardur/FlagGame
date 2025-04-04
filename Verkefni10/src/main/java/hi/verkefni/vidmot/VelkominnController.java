package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VelkominnController {

    @FXML
    private Button spurningarButton;

    @FXML
    private Button haettaButton;

    /**
     * Handles the action when the start game button is clicked.
     */
    @FXML
    private void handleSpurningarButtonAction() {
        System.out.println("Start game button pressed");
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) spurningarButton.getScene().getWindow());
        viewSwitcher.switchToView("/flag-game-view.fxml");
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