package hi.verkefni.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EventManagerApplication extends Application {

    /**
     * Ræsir forritið.
     *
     * @param primaryStage Aðalsvið forritsins.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/eventmanager-view.fxml"));
            primaryStage.setTitle("Event Manager");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    public static void main(String[] args) {
        launch(args);
    }
}