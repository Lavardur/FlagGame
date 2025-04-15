/******************************************************************************
 *  Nafn    : Anton Benediktsson
 *  T-póstur: anb59@hi.is
 *  Lýsing  : Application klasi sem les inn notendaviðmótslýsingu úr .fxml og ræsir gluggann
 *
 *
 *****************************************************************************/

package hi.verkefni.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FlagGameApplication extends Application {

    /**
     * Starts the application.
     *
     * @param primaryStage The primary stage.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/velkominn-view.fxml"));
            primaryStage.setTitle("Flag Guessing Game");
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