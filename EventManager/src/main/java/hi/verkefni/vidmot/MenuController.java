package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Stýriklasi fyrir valmyndastiku í EventManager forritinu.
 */
public class MenuController {
    @FXML
    private MenuBar menuBar;
    
    @FXML
    private MenuItem exitMenuItem;
    
    @FXML
    private MenuItem newMenuItem;
    
    @FXML
    private MenuItem openMenuItem;
    
    @FXML
    private MenuItem saveMenuItem;
    
    @FXML
    private MenuItem editMenuItem;
    
    @FXML
    private MenuItem deleteMenuItem;
    
    @FXML
    private MenuItem aboutMenuItem;
    
    /**
     * Tilvísun í aðalstýriklasann til að fá aðgang að EventView.
     */
    private EventManagerController mainController;
    
    /**
     * Setur tilvísun í aðalstýriklasann.
     * @param controller Aðalstýriklasinn EventManagerController
     */
    public void setMainController(EventManagerController controller) {
        this.mainController = controller;
    }
    
    /**
     * Nær í gluggann sem inniheldur valmyndastikuna.
     * @return Glugginn sem inniheldur valmyndastikuna
     */
    private Window getWindow() {
        return menuBar.getScene().getWindow();
    }

    /**
     * Handler fyrir að búa til nýjan viðburð.
     */
    @FXML
    private void handleNew() {
        if (mainController != null) {
            mainController.nyr();
        }
    }
    
    /**
     * Handler fyrir að opna viðburðarskrá.
     */
    @FXML
    private void handleOpen() {
        if (mainController != null) {
            mainController.opna();
        }
    }
    
    /**
     * Handler fyrir að vista viðburðarskrá.
     */
    @FXML
    private void handleSave() {
        if (mainController != null) {
            mainController.vista();
        }
    }
    
    /**
     * Handler fyrir að breyta viðburði.
     */
    @FXML
    private void handleEdit() {
        // Þetta er bara staðgengill, gæti verið útfært ef þörf er á
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Breyta viðburði");
        alert.setHeaderText(null);
        alert.setContentText("Breytingar á viðburði eru framkvæmdar beint í formi.");
        alert.showAndWait();
    }
    
    /**
     * Handler fyrir að eyða viðburði.
     */
    @FXML
    private void handleDelete() {
        if (mainController != null) {
            mainController.eyda();
        }
    }
    
    /**
     * Handler fyrir að sýna upplýsingar um forritið.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Um Viðburðarstjóra");
        alert.setHeaderText("Viðburðarstjóri");
        alert.setContentText("Þetta forrit leyfir notanda að skrá viðburði, heiti þeirra, tíma og dagsetningu, flokk og kynningarmyndband." +
                            "Hægt er að stofna nýjan viðburð, opna viðburð, loka viðburði, breyta þeim, vista og eyða.");
        alert.showAndWait();
    }
    
    /**
     * Handler fyrir að loka forritinu.
     */
    @FXML
    private void handleClose() {
        if (mainController != null) {
            mainController.loka();
        } else {
            // Ef við getum ekki fengið aðgang að aðalstýriklasanum, loka bara glugganum
            Stage stage = (Stage) getWindow();
            stage.close();
        }
    }
}