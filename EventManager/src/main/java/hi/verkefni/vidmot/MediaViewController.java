package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.EventModel;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.text.DecimalFormat;

public class MediaViewController {
    
    @FXML
    private VBox mediaContainer;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Button playButton;
    
    @FXML
    private Button pauseButton;
    
    @FXML
    private Button stopButton;
    
    @FXML
    private Button selectButton;
    
    @FXML
    private Slider timeSlider;
    
    @FXML
    private Label timeLabel;
    
    @FXML
    private Label mediaTitle;
    
    private MediaPlayer mediaPlayer;
    private EventModel eventModel;
    private boolean updating = false;
    private ChangeListener<Duration> currentTimeListener;
    @SuppressWarnings("unused")
    private EventManagerController mainController;
    
    @FXML
    private void initialize() {
        // Initialize controls
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
        timeSlider.setDisable(true);
        
        // Setup time slider listener
        timeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && mediaPlayer != null) {
                try {
                    // Convert the slider value to duration
                    double duration = mediaPlayer.getMedia().getDuration().toSeconds();
                    double seekTime = timeSlider.getValue() * duration / 100.0;
                    mediaPlayer.seek(Duration.seconds(seekTime));
                } catch (Exception e) {
                    System.err.println("Error seeking: " + e.getMessage());
                }
            }
        });
        
        // Setup MediaView to bind to its parent size for scaling
        StackPane mediaViewParent = (StackPane) mediaView.getParent();
        if (mediaViewParent != null) {
            // Set MediaView to scale with its parent
            mediaView.fitWidthProperty().bind(mediaViewParent.widthProperty().multiply(0.95));
            mediaView.fitHeightProperty().bind(mediaViewParent.heightProperty().multiply(0.95));
        }
    }
    
    /**
     * Set the event model to display its media
     * 
     * @param model The event model
     */
    public void setEventModel(EventModel model) {
        this.eventModel = model;
        
        // Add a listener to the media property
        if (model != null) {
            model.promoVideoProperty().addListener((obs, oldMedia, newMedia) -> {
                setupMediaPlayer(newMedia);
            });
            
            // Initial setup if media exists
            if (model.promoVideoProperty().get() != null) {
                setupMediaPlayer(model.promoVideoProperty().get());
            } else {
                // Reset the media view if no media
                resetMediaView();
            }
        } else {
            resetMediaView();
        }
    }
    
    /**
     * Reset the media view when no media is available
     */
    private void resetMediaView() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        
        mediaView.setMediaPlayer(null);
        timeSlider.setDisable(true);
        timeSlider.setValue(0);
        timeLabel.setText("00:00 / 00:00");
        playButton.setDisable(true);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
        mediaTitle.setText("Promotional Video");
    }
    
    /**
     * Set the main controller reference
     * 
     * @param controller The main controller
     */
    public void setMainController(EventManagerController controller) {
        this.mainController = controller;
    }
    
    /**
     * Play the media
     */
    @FXML
    private void handlePlay() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.play();
                playButton.setDisable(true);
                pauseButton.setDisable(false);
                stopButton.setDisable(false);
                System.out.println("Playing media: " + mediaPlayer.getMedia().getSource());
                
                // Debug info to check video properties
                mediaPlayer.setOnReady(() -> {
                    System.out.println("Media is ready");
                    System.out.println("Media width: " + mediaPlayer.getMedia().getWidth());
                    System.out.println("Media height: " + mediaPlayer.getMedia().getHeight());
                    System.out.println("Media duration: " + mediaPlayer.getMedia().getDuration());
                    System.out.println("MediaView fit width: " + mediaView.getFitWidth());
                    System.out.println("MediaView fit height: " + mediaView.getFitHeight());
                });
            } catch (Exception e) {
                System.err.println("Error playing media: " + e.getMessage());
            }
        }
    }
    
    /**
     * Pause the media
     */
    @FXML
    private void handlePause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playButton.setDisable(false);
            pauseButton.setDisable(true);
        }
    }
    
    /**
     * Stop the media
     */
    @FXML
    private void handleStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            playButton.setDisable(false);
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
        }
    }
    
    /**
     * Open file chooser to select a video
     */
    @FXML
    private void handleSelectVideo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Promotional Video");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mov", "*.avi", "*.wmv")
        );
        
        File selectedFile = fileChooser.showOpenDialog(mediaContainer.getScene().getWindow());
        if (selectedFile != null && eventModel != null) {
            try {
                System.out.println("Loading video file: " + selectedFile.getAbsolutePath());
                Media media = new Media(selectedFile.toURI().toString());
                eventModel.promoVideoProperty().set(media);
            } catch (MediaException me) {
                System.err.println("Media error: " + me.getMessage());
                System.err.println("Error type: " + me.getType());
                if (me.getCause() != null) {
                    System.err.println("Cause: " + me.getCause().getMessage());
                }
            } catch (Exception e) {
                System.err.println("Error loading media file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Setup the media player with the given media
     * 
     * @param media The media to play
     */
    private void setupMediaPlayer(Media media) {
        // Clean up old media player if it exists
        if (mediaPlayer != null) {
            if (currentTimeListener != null) {
                mediaPlayer.currentTimeProperty().removeListener(currentTimeListener);
            }
            mediaPlayer.dispose();
        }
        
        if (media != null) {
            try {
                // Create a new media player
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                
                // Set additional properties for the MediaView
                mediaView.setPreserveRatio(true);
                mediaView.setSmooth(true);
                
                // Ensure MediaView is properly scaling with its container
                StackPane mediaViewParent = (StackPane) mediaView.getParent();
                if (mediaViewParent != null) {
                    mediaView.fitWidthProperty().bind(mediaViewParent.widthProperty().multiply(0.95));
                    mediaView.fitHeightProperty().bind(mediaViewParent.heightProperty().multiply(0.95));
                }
                
                // Update the title if we have a source
                String source = media.getSource();
                if (source != null) {
                    String fileName;
                    if (source.contains("/")) {
                        fileName = source.substring(source.lastIndexOf('/') + 1);
                    } else if (source.contains("\\")) {
                        fileName = source.substring(source.lastIndexOf('\\') + 1);
                    } else {
                        fileName = source;
                    }
                    mediaTitle.setText(fileName);
                    System.out.println("Media source: " + source);
                }
                
                // Enable controls
                timeSlider.setDisable(false);
                
                // Setup time tracking
                currentTimeListener = (obs, oldTime, newTime) -> {
                    if (!timeSlider.isValueChanging()) {
                        updating = true;
                        Duration duration = mediaPlayer.getMedia().getDuration();
                        if (duration.greaterThan(Duration.ZERO)) {
                            // Convert duration to percentage for the slider
                            double percentage = newTime.toMillis() / duration.toMillis() * 100.0;
                            timeSlider.setValue(percentage);
                            
                            // Update time label
                            updateTimeLabel(newTime, duration);
                        }
                        updating = false;
                    }
                };
                
                mediaPlayer.currentTimeProperty().addListener(currentTimeListener);
                
                // Add error handler
                mediaPlayer.setOnError(() -> {
                    MediaException error = mediaPlayer.getError();
                    System.err.println("Media player error: " + error.getMessage());
                    if (error.getCause() != null) {
                        System.err.println("Cause: " + error.getCause().getMessage());
                    }
                });
                
                // Setup end of media handler
                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.stop();
                    mediaPlayer.seek(Duration.ZERO);
                    playButton.setDisable(false);
                    pauseButton.setDisable(true);
                    stopButton.setDisable(true);
                });
                
                // Auto play
                mediaPlayer.play();
                playButton.setDisable(true);
                pauseButton.setDisable(false);
                stopButton.setDisable(false);
                
                // Print debug information when the media is ready
                mediaPlayer.setOnReady(() -> {
                    System.out.println("Media ready:");
                    System.out.println("  Width: " + media.getWidth());
                    System.out.println("  Height: " + media.getHeight());
                    System.out.println("  Duration: " + media.getDuration());
                });
            } catch (Exception e) {
                System.err.println("Error setting up media player: " + e.getMessage());
                e.printStackTrace();
                resetMediaView();
            }
        } else {
            // No media, reset everything
            resetMediaView();
        }
    }
    
    /**
     * Update the time label with current time and duration
     * 
     * @param currentTime Current playback time
     * @param duration Total duration
     */
    private void updateTimeLabel(Duration currentTime, Duration duration) {
        DecimalFormat format = new DecimalFormat("00");
        
        int currentMinutes = (int) currentTime.toMinutes();
        int currentSeconds = (int) currentTime.toSeconds() % 60;
        
        int totalMinutes = (int) duration.toMinutes();
        int totalSeconds = (int) duration.toSeconds() % 60;
        
        timeLabel.setText(
            format.format(currentMinutes) + ":" + format.format(currentSeconds) + " / " +
            format.format(totalMinutes) + ":" + format.format(totalSeconds)
        );
    }
}