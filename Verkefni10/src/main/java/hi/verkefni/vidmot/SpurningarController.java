package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import hi.verkefni.vinnsla.Spurningar;

import java.io.IOException;

public class SpurningarController {

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private ListView<String> questionListView;

    @FXML
    private Label answeredQuestionsLabel;

    @FXML
    private Button backButton;

    @FXML
    private ScrollPane answeredQuestionsScrollPane;

    @FXML
    private TextArea answeredQuestionsLog;

    private Spurningar spurningar;
    private int answeredCount = 0;

    /**
     * Upphafsstillir forritið.
     */
    @FXML
    public void initialize() {
        spurningar = new Spurningar();
        setupCategoryList();
        updateAnsweredQuestionsLabel();
    }

    /**
     * Upphafsstillir flokkalista.
     */
    private void setupCategoryList() {
        categoryListView.getItems().addAll("Tæknispurningar", "Færnispurningar");
        categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadQuestions(newValue);
        });
    }

    /**
     * Hleður spurningum fyrir valinn flokk.
     *
     * @param category Flokkurinn sem á að hlaða spurningum fyrir.
     */

    private void loadQuestions(String category) {
        questionListView.getItems().clear();
        questionListView.getItems().addAll(spurningar.getSpurningaflokkar(category));
    }

    /**
     * Opnar glugga til að svara spurningu.
     *
     * @param question Spurningin sem á að svara.
     */
    private void openAnswerDialog(String question) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/svar-view.fxml"));
            Parent root = loader.load();

            SvarDialogController controller = loader.getController();
            controller.setQuestion(question);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Svar við spurningu");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isAnswered()) {
                incrementAnsweredCount();
                logAnsweredQuestion(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Skráir svaraða spurningu í logg.
     *
     * @param question Spurningin sem var svarað.
     */
    private void logAnsweredQuestion(String question) {
        answeredQuestionsLog.appendText(question + "\n");
    }

    /**
     * Meðhöndlar aðgerð þegar svara hnappur er ýttur.
     */
    @FXML
    private void handleAnswerButtonAction() {
        String selectedQuestion = questionListView.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            openAnswerDialog(selectedQuestion);
        }
    }

    /**
     * Meðhöndlar aðgerð þegar hætta hnappur er ýttur.
     */
    @FXML
    private void handleExitButtonAction() {
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) backButton.getScene().getWindow());
        viewSwitcher.switchToView("/kvedja-view.fxml");
    }

    /**
     * Meðhöndlar aðgerð þegar til baka hnappur er ýttur.
     */
    @FXML
    private void handleBackButtonAction() {
        ViewSwitcher viewSwitcher = new ViewSwitcher((Stage) backButton.getScene().getWindow());
        viewSwitcher.switchToView("/velkominn-view.fxml");
    }

    /**
     * Uppfærir texta sem sýnir fjölda svaraðra spurninga.
     */
    private void updateAnsweredQuestionsLabel() {
        answeredQuestionsLabel.setText("Fjöldi svaraðra spurninga: " + answeredCount);
    }

    /**
     * Eykur fjölda svaraðra spurninga um einn.
     */
    public void incrementAnsweredCount() {
        answeredCount++;
        updateAnsweredQuestionsLabel();
    }
}