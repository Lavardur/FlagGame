package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class KvedjaController {

    @FXML
    private Button closeButton;
    
    @FXML
    private ImageView flagImageView;
    
    @FXML
    public void initialize() {
        // Set a random flag
        FlagImageUtils.setRandomFlag(flagImageView);
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}