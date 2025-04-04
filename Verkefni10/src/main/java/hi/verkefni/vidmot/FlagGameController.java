package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.FeedbackService;
import hi.verkefni.vinnsla.Flag;
import hi.verkefni.vinnsla.FlagService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ToggleGroup answerGroup;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Label scoreLabel;
    
    @FXML
    private Label statsLabel;

    @FXML
    private Button submitButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;
    
    @FXML
    private ComboBox<String> continentComboBox;
    
    @FXML
    private ComboBox<String> difficultyComboBox;

    private FlagService flagService;
    private FeedbackService feedbackService;
    private Flag currentFlag;
    private int correctAnswers = 0;
    private int totalQuestions = 0;

    @FXML
    public void initialize() {
        flagService = new FlagService();
        feedbackService = new FeedbackService();
        
        // Initially disable the next button until submission
        nextButton.setDisable(true);
        
        // Set up continent selection
        continentComboBox.getItems().add("All Continents");
        continentComboBox.getItems().addAll(flagService.getContinents());
        continentComboBox.setValue("All Continents");
        
        // Set up difficulty levels
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.setValue("Medium");
        
        // Add listener for selection changes
        continentComboBox.setOnAction(event -> loadNewFlag());
        difficultyComboBox.setOnAction(event -> loadNewFlag());
        
        // Update stats
        updateStats();
        
        // Load the first flag
        loadNewFlag();
    }
    
    private void updateStats() {
        String selectedContinent = continentComboBox.getValue();
        int flagCount;
        
        if ("All Continents".equals(selectedContinent)) {
            flagCount = flagService.getTotalFlagCount();
            statsLabel.setText("Total flags: " + flagCount);
        } else {
            flagCount = flagService.getFlagCountByContinent(selectedContinent);
            statsLabel.setText(selectedContinent + " flags: " + flagCount);
        }
    }

    private void loadNewFlag() {
        // Clear previous selections and feedback
        feedbackLabel.setText("");
        if (answerGroup.getSelectedToggle() != null) {
            answerGroup.getSelectedToggle().setSelected(false);
        }
        
        // Update stats
        updateStats();
        
        // Get a random flag based on selected continent
        String selectedContinent = continentComboBox.getValue();
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
        
        // Determine number of options based on difficulty
        int numOptions;
        String difficulty = difficultyComboBox.getValue();
        switch (difficulty) {
            case "Easy":
                numOptions = 3;
                break;
            case "Hard":
                numOptions = 6;
                break;
            case "Medium":
            default:
                numOptions = 4;
                break;
        }
        
        // Get country options
        List<String> options = flagService.getCountryOptionsForFlag(currentFlag, numOptions);
        
        // Set the radio button text and visibility
        option1.setText(options.get(0));
        option2.setText(options.get(1));
        
        if (options.size() > 2) {
            option3.setText(options.get(2));
            option3.setVisible(true);
        } else {
            option3.setVisible(false);
        }
        
        if (options.size() > 3) {
            option4.setText(options.get(3));
            option4.setVisible(true);
        } else {
            option4.setVisible(false);
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
        }
        
        // Update score label
        scoreLabel.setText("Score: " + correctAnswers + "/" + totalQuestions + 
                " (" + (int)((double)correctAnswers/totalQuestions*100) + "%)");
        
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
        viewSwitcher.switchToView("/velkominn-view.fxml");
    }
}