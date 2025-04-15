/******************************************************************************
 *  Höfundur: Anton Benediktsson
 *  Netfang : anb59@hi.is
 *  Lýsing  : Controller klasi fyrir yfirlit leikjaeinkunnar. Sér um að uppfæra viðmót
 *            með upplýsingum um rétt svör, framvindu, einstaka fána og endurgjöf.
 *****************************************************************************/

package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GameSummaryController {

    @FXML
    private ImageView flagImageView;
    
    @FXML
    private Label scoreLabel;
    
    @FXML
    private ProgressBar scoreProgressBar;
    
    @FXML
    private Label gameInfoLabel;
    
    @FXML
    private Label feedbackLabel;
    
    @FXML
    private Button playAgainButton;
    
    @FXML
    private Button homeButton;
    
    @FXML
    private Label uniqueFlagsLabel; // Add this field
    
    @FXML
    public void initialize() {
        // Set a random flag
        FlagImageUtils.setRandomFlag(flagImageView);
        
        // Get game results from GameSettings
        int correctAnswers = GameSettings.getInstance().getCorrectAnswers();
        int totalQuestions = GameSettings.getInstance().getTotalQuestions();
        String difficulty = GameSettings.getInstance().getDifficulty();
        String continent = GameSettings.getInstance().getContinent();
        int uniqueFlags = GameSettings.getInstance().getUsedFlagCount();
        
        // Set progress bar
        double percentage = totalQuestions > 0 ? (double) correctAnswers / totalQuestions : 0;
        scoreProgressBar.setProgress(percentage);
        
        // Set score text
        int percentageInt = (int) (percentage * 100);
        scoreLabel.setText(correctAnswers + "/" + totalQuestions + " Correct (" + percentageInt + "%)");
        
        // Set game info
        if ("All Continents".equals(continent)) {
            gameInfoLabel.setText("Continent: All | Difficulty: " + difficulty);
        } else {
            gameInfoLabel.setText("Continent: " + continent + " | Difficulty: " + difficulty);
        }
        
        // Set unique flags info
        uniqueFlagsLabel.setText("Unique flags seen: " + uniqueFlags);
        
        // Set feedback message based on score
        if (percentage >= 0.9) {
            feedbackLabel.setText("Excellent! You're a flag expert!");
            feedbackLabel.getStyleClass().add("feedback-correct");
        } else if (percentage >= 0.7) {
            feedbackLabel.setText("Great job! You have a good knowledge of flags.");
            feedbackLabel.getStyleClass().add("feedback-correct");
        } else if (percentage >= 0.5) {
            feedbackLabel.setText("Good effort! You're getting there.");
            feedbackLabel.getStyleClass().add("feedback-correct");
        } else if (percentage >= 0.3) {
            feedbackLabel.setText("Not bad, but you could use more practice.");
            feedbackLabel.getStyleClass().add("feedback-incorrect");
        } else {
            feedbackLabel.setText("Try again! Flags can be tricky, but you'll improve.");
            feedbackLabel.getStyleClass().add("feedback-incorrect");
        }
    }
    
    @FXML
    private void handlePlayAgain() {
        // Reset game counters but keep settings
        GameSettings.getInstance().resetGameCounters();
        
        // Switch to the game setup view
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) playAgainButton.getScene().getWindow());
        viewSwitcher.switchToView("/game-setup-view.fxml");
    }
    
    @FXML
    private void handleHome() {
        // Reset everything
        GameSettings.getInstance().resetAll();
        
        // Switch to the welcome view
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) homeButton.getScene().getWindow());
        viewSwitcher.switchToView("/velkominn-view.fxml");
    }
}