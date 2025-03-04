package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import hi.verkefni.vinnsla.FeedbackService;

public class SvarDialogController {

    @FXML
    private TextArea answerTextArea;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Label questionLabel;

    private FeedbackService feedbackService;
    private boolean answered = false;

    public SvarDialogController() {
        feedbackService = new FeedbackService();
    }

    /**
     * Upphafsstillir gluggann.
     */
    @FXML
    private void initialize() {
        feedbackLabel.setText("");
    }

    /**
     * Setur spurninguna í gluggann.
     *
     * @param question Spurningin sem á að setja.
     */
    public void setQuestion(String question) {
        questionLabel.setText(question);
    }

    /**
     * Athugar hvort spurningin hafi verið svöruð.
     *
     * @return true ef spurningin hefur verið svöruð, annars false.
     */
    public boolean isAnswered() {
        return answered;
    }

    /**
     * Meðhöndlar innsendingu svars.
     */
    @FXML
    private void handleSubmit() {
        String answer = answerTextArea.getText();
        String feedback = feedbackService.provideFeedback(answer);
        feedbackLabel.setText(feedback);
        answered = true;
    }

    /**
     * Lokar glugganum.
     */
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}