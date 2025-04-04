package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.FeedbackService;
import hi.verkefni.vinnsla.Flag;
import hi.verkefni.vinnsla.FlagService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

public class FlagGameController {

    @FXML
    private ImageView flagImageView;

    @FXML
    private RadioButton option1;

    @FXML
    private RadioButton option2;

    @FXML
    private RadioButton option3;

    @FXML
    private RadioButton option4;
    
    @FXML
    private RadioButton option5;
    
    @FXML
    private RadioButton option6;

    @FXML
    private ToggleGroup answerGroup;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Label scoreLabel;
    
    @FXML
    private Label gameInfoLabel;

    @FXML
    private Button submitButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    private FlagService flagService;
    private FeedbackService feedbackService;
    private Flag currentFlag;
    private int correctAnswers = 0;
    private int totalQuestions = 0;
    private RadioButton[] optionButtons;

    @FXML
    public void initialize() {
        System.out.println("Initializing FlagGameController");
        
        flagService = new FlagService();
        feedbackService = new FeedbackService();
        
        // Create array of option buttons for easier handling
        optionButtons = new RadioButton[]{option1, option2, option3, option4, option5, option6};
        
        // Initially disable the next button until submission
        nextButton.setDisable(true);
        
        // Set the game info label based on GameSettings
        updateGameInfoLabel();
        
        // Load the first flag
        loadNewFlag();
    }
    
    private void updateGameInfoLabel() {
        // Get settings from the GameSettings singleton
        String continent = GameSettings.getInstance().getContinent();
        String difficulty = GameSettings.getInstance().getDifficulty();
        
        // Update the info label
        if ("All Continents".equals(continent)) {
            gameInfoLabel.setText("Continent: All | Difficulty: " + difficulty);
        } else {
            gameInfoLabel.setText("Continent: " + continent + " | Difficulty: " + difficulty);
        }
    }

    private void loadNewFlag() {
        // Clear previous selections and feedback
        feedbackLabel.setText("");
        if (answerGroup.getSelectedToggle() != null) {
            answerGroup.getSelectedToggle().setSelected(false);
        }
        
        // Get a random flag based on selected continent from GameSettings
        String selectedContinent = GameSettings.getInstance().getContinent();
        if ("All Continents".equals(selectedContinent)) {
            currentFlag = flagService.getRandomFlag();
        } else {
            currentFlag = flagService.getRandomFlag(selectedContinent);
        }
        
        // Load the flag image
        try {
            Image flagImage = new Image(getClass().getResourceAsStream(currentFlag.getImageUrl()));
            flagImageView.setImage(flagImage);
        } catch (Exception e) {
            System.err.println("Could not load flag image: " + currentFlag.getImageUrl());
            e.printStackTrace();
            flagImageView.setImage(null);
        }
        
        // Determine number of options based on difficulty from GameSettings
        int numOptions = GameSettings.getInstance().getNumberOfOptions();
        
        // Get country options
        List<String> options = flagService.getCountryOptionsForFlag(currentFlag, numOptions);
        
        // Set the radio button text and visibility
        for (int i = 0; i < optionButtons.length; i++) {
            if (i < options.size()) {
                optionButtons[i].setText(options.get(i));
                optionButtons[i].setVisible(true);
            } else {
                optionButtons[i].setVisible(false);
            }
        }
        
        // Enable submit button, disable next button
        submitButton.setDisable(false);
        nextButton.setDisable(true);
    }

    @FXML
    private void handleSubmit() {
        RadioButton selectedOption = (RadioButton) answerGroup.getSelectedToggle();
        
        if (selectedOption == null) {
            feedbackLabel.setText("Please select an answer.");
            return;
        }
        
        // Increment total questions
        totalQuestions++;
        
        // Get the selected country name
        String selectedCountry = selectedOption.getText();
        
        // Evaluate the answer
        String feedback = feedbackService.evaluateGuess(selectedCountry, currentFlag.getCountryName());
        feedbackLabel.setText(feedback);
        
        // Update score if correct
        if (selectedCountry.equals(currentFlag.getCountryName())) {
            correctAnswers++;
            feedbackLabel.getStyleClass().clear();
            feedbackLabel.getStyleClass().add("feedback-correct");
        } else {
            feedbackLabel.getStyleClass().clear();
            feedbackLabel.getStyleClass().add("feedback-incorrect");
        }
        
        // Update score label
        if (totalQuestions > 0) {
            int percentage = (int)((double)correctAnswers/totalQuestions*100);
            scoreLabel.setText("Score: " + correctAnswers + "/" + totalQuestions + " (" + percentage + "%)");
        } else {
            scoreLabel.setText("Score: 0/0 (0%)");
        }
        
        // Disable submit button, enable next button
        submitButton.setDisable(true);
        nextButton.setDisable(false);
    }

    @FXML
    private void handleNext() {
        loadNewFlag();
    }

    @FXML
    private void handleBack() {
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) backButton.getScene().getWindow());
        viewSwitcher.switchToView("/game-setup-view.fxml");
    }
}