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
        difficultyComboBox.setOnAction(event -> {
            updateFlagCount();
            updateDifficultyDescription();
        });
        
        // Initialize labels
        updateFlagCount();
        updateDifficultyDescription();
    }
    
    private void updateFlagCount() {
        String selectedContinent = continentComboBox.getValue();
        String difficulty = difficultyComboBox.getValue();
        boolean isEasyMode = "Easy".equals(difficulty);
        
        int flagCount;
        
        if ("All Continents".equals(selectedContinent)) {
            if (isEasyMode) {
                flagCount = flagService.getTopFlagsCount();
                flagCountLabel.setText("Top recognizable flags: " + flagCount);
            } else {
                flagCount = flagService.getTotalFlagCount();
                flagCountLabel.setText("Available flags: " + flagCount);
            }
        } else {
            if (isEasyMode) {
                flagCount = flagService.getTopFlagCountByContinent(selectedContinent);
                flagCountLabel.setText("Top " + selectedContinent + " flags: " + flagCount);
            } else {
                flagCount = flagService.getFlagCountByContinent(selectedContinent);
                flagCountLabel.setText(selectedContinent + " flags: " + flagCount);
            }
        }
    }
    
    private void updateDifficultyDescription() {
        String difficulty = difficultyComboBox.getValue();
        switch (difficulty) {
            case "Easy":
                difficultyDescriptionLabel.setText("Most recognizable flags with 3 options");
                break;
            case "Hard":
                difficultyDescriptionLabel.setText("All flags with 5 options (challenging)");
                break;
            case "Medium":
            default:
                difficultyDescriptionLabel.setText("All flags with 4 options (standard)");
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
        
        // Reset game counters in case there was a previous game
        GameSettings.getInstance().resetGameCounters();
        
        // Switch to the game view
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) startButton.getScene().getWindow());
        viewSwitcher.switchToView("/flag-game-view.fxml");
    }
}