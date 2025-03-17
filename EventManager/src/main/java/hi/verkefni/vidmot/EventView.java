package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * EventView is a custom component defined in the event-view.fxml file.
 * It manages the UI elements for displaying and editing an EventModel.
 */
public class EventView extends AnchorPane {
    
    @FXML
    private TextField fxEventName;
    
    @FXML
    private ComboBox<String> fxCategory;
    
    @FXML
    private DatePicker fxDate;
    
    @FXML
    private Spinner<LocalTime> fxTime;
    
    @FXML
    private MediaView fxMediaView;
    
    @FXML
    private Button fxSelectVideoButton;
    
    private EventModel eventModel;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    
    /**
     * Default constructor that loads the FXML file and initializes the component.
     */
    public EventView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/event-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        // Create a default event model
        eventModel = new EventModel("", "Concert", LocalDate.now(), LocalTime.of(12, 0), null);
        
        // Initialize the component
        initialize();
    }
    
    /**
     * Initialize the component by setting up UI controls and bindings.
     */
    private void initialize() {
        // Set up category combo box
        fxCategory.getItems().addAll("Concert", "Theater", "Sports", "Conference", "Exhibition", "Other");
        fxCategory.setValue("Concert");
        
        // Set up time spinner
        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setValue(LocalTime.of(12, 0));
            }
            
            @Override
            public void decrement(int steps) {
                LocalTime time = getValue();
                setValue(time.minusMinutes(30 * steps));
            }
            
            @Override
            public void increment(int steps) {
                LocalTime time = getValue();
                setValue(time.plusMinutes(30 * steps));
            }
        };
        
        // Set a converter for the spinner
        timeValueFactory.setConverter(new StringConverter<LocalTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            
            @Override
            public String toString(LocalTime time) {
                if (time != null) {
                    return formatter.format(time);
                }
                return "";
            }
            
            @Override
            public LocalTime fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalTime.parse(string, formatter);
                }
                return LocalTime.of(12, 0);
            }
        });
        
        fxTime.setValueFactory(timeValueFactory);
        
        // Set up button handlers
        fxSelectVideoButton.setOnAction(e -> handleSelectVideo());
        
        // Set up bindings
        setupBindings();
    }
    
    /**
     * Set up bidirectional bindings between UI controls and EventModel properties.
     */
    private void setupBindings() {
        // Bind text field to event name property
        fxEventName.textProperty().bindBidirectional(eventModel.eventNameProperty());
        
        // Bind combo box to category property
        fxCategory.valueProperty().bindBidirectional(eventModel.categoryProperty());
        
        // Bind date picker to date property
        fxDate.valueProperty().bindBidirectional(eventModel.dateProperty());
        
        // For time spinner, we need to use listeners since Spinner doesn't support direct binding
        fxTime.valueProperty().addListener((obs, oldVal, newVal) -> {
            eventModel.timeProperty().set(newVal);
        });
        
        eventModel.timeProperty().addListener((obs, oldVal, newVal) -> {
            fxTime.getValueFactory().setValue(newVal);
        });
        
        // Add listener to the media property
        eventModel.promoVideoProperty().addListener((obs, oldMedia, newMedia) -> {
            if (mediaPlayer != null) {
                mediaPlayer.dispose();
            }
            
            if (newMedia != null) {
                mediaPlayer = new MediaPlayer(newMedia);
                fxMediaView.setMediaPlayer(mediaPlayer);
                
                // Auto play the video
                mediaPlayer.play();
                isPlaying = true;
            } else {
                fxMediaView.setMediaPlayer(null);
                isPlaying = false;
            }
        });
    }
    
    /**
     * Handler for selecting a video file.
     * Opens a file chooser dialog and sets the selected file as the promotional video.
     */
    @FXML
    private void handleSelectVideo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Promotional Video");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mov", "*.avi", "*.wmv")
        );
        
        File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
        if (selectedFile != null) {
            try {
                Media media = new Media(selectedFile.toURI().toString());
                eventModel.promoVideoProperty().set(media);
            } catch (Exception e) {
                System.err.println("Error loading media file: " + e.getMessage());
            }
        }
    }
    
    /**
     * Handler for playing or pausing the video.
     * Toggles between play and pause states.
     */
    @FXML
    private void handlePlayPause() {
        if (mediaPlayer != null) {
            if (isPlaying) {
                mediaPlayer.pause();
                isPlaying = false;
            } else {
                mediaPlayer.play();
                isPlaying = true;
            }
        }
    }
    
    /**
     * Get the event model associated with this view.
     * 
     * @return The event model
     */
    public EventModel getEventModel() {
        return eventModel;
    }
    
    /**
     * Set a new event model for this view.
     * 
     * @param model The new event model
     */
    public void setEventModel(EventModel model) {
        // Stop any playing media
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        
        this.eventModel = model;
        
        // Update UI controls with model values
        fxEventName.setText(model.eventNameProperty().get());
        fxCategory.setValue(model.categoryProperty().get());
        fxDate.setValue(model.dateProperty().get());
        fxTime.getValueFactory().setValue(model.timeProperty().get());
        
        // Setup the media view if there's a promo video
        if (model.promoVideoProperty().get() != null) {
            mediaPlayer = new MediaPlayer(model.promoVideoProperty().get());
            fxMediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
            isPlaying = true;
        } else {
            fxMediaView.setMediaPlayer(null);
            isPlaying = false;
        }
        
        // Refresh bindings
        setupBindings();
    }
}
