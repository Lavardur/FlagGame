package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.FlagService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GameSetupController {

    @FXML
    private ComboBox<String> continentComboBox;
    
    @FXML
    private ComboBox<String> difficultyComboBox;
    
    @FXML
    private Label flagCountLabel;
    
    @FXML
    private Label difficultyDescriptionLabel;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button startButton;
    
    @FXML
    private ImageView flagImageView;
    
    private FlagService flagService;
    
    @FXML
    public void initialize() {
        flagService = new FlagService();
        
        // Set a random flag
        FlagImageUtils.setRandomFlag(flagImageView);
        
        // Set up continent selection
        continentComboBox.getItems().add("All Continents");
        continentComboBox.getItems().addAll(flagService.getContinents());
        continentComboBox.setValue("All Continents");
        
        // Set up difficulty levels
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.setValue("Medium");
        
        // Update labels when selections change
        continentComboBox.setOnAction(event -> updateFlagCount());
        difficultyComboBox.setOnAction(event -> updateDifficultyDescription());
        
        // Initialize labels
        updateFlagCount();
        updateDifficultyDescription();
    }
    
    private void updateFlagCount() {
        String selectedContinent = continentComboBox.getValue();
        int flagCount;
        
        if ("All Continents".equals(selectedContinent)) {
            flagCount = flagService.getTotalFlagCount();
            flagCountLabel.setText("Available flags: " + flagCount);
        } else {
            flagCount = flagService.getFlagCountByContinent(selectedContinent);
            flagCountLabel.setText(selectedContinent + " flags: " + flagCount);
        }
    }
    
    private void updateDifficultyDescription() {
        String difficulty = difficultyComboBox.getValue();
        switch (difficulty) {
            case "Easy":
                difficultyDescriptionLabel.setText("Description: 3 options (more relaxed)");
                break;
            case "Hard":
                difficultyDescriptionLabel.setText("Description: 6 options (more challenging)");
                break;
            case "Medium":
            default:
                difficultyDescriptionLabel.setText("Description: Standard 4-option quiz");
                break;
        }
    }
    
    @FXML
    private void handleBack() {
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) backButton.getScene().getWindow());
        viewSwitcher.switchToView("/velkominn-view.fxml");
    }
    
    @FXML
    private void handleStart() {
        // Store the selected options in GameSettings
        GameSettings.getInstance().setContinent(continentComboBox.getValue());
        GameSettings.getInstance().setDifficulty(difficultyComboBox.getValue());
        
        // Switch to the game view
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) startButton.getScene().getWindow());
        viewSwitcher.switchToView("/flag-game-view.fxml");
    }
}