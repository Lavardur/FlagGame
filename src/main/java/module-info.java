module hi.verkefni {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    exports hi.verkefni.vidmot to javafx.graphics, javafx.fxml;
    opens hi.verkefni.vidmot to javafx.fxml;
    
    exports hi.verkefni.vinnsla;
    opens hi.verkefni.vinnsla to javafx.fxml;
}