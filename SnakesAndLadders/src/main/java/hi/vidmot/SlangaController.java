package hi.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SlangaController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Slönguspil!");
    }
}