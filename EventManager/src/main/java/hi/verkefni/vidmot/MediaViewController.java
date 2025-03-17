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

/**
 * Stýriklasi fyrir MediaView í EventManager forritinu.
 */
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
    
    /**
     * Upphafsstillir stýriklasann.
     */
    @FXML
    private void initialize() {
        // Upphafsstilla stjórntæki
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
        timeSlider.setDisable(true);
        
        // Setja upp hlustara fyrir tíma sleðann
        timeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updating && mediaPlayer != null) {
                try {
                    // Umbreyta sleðagildi í tíma
                    double duration = mediaPlayer.getMedia().getDuration().toSeconds();
                    double seekTime = timeSlider.getValue() * duration / 100.0;
                    mediaPlayer.seek(Duration.seconds(seekTime));
                } catch (Exception e) {
                    System.err.println("Villa við að leita: " + e.getMessage());
                }
            }
        });
        
        // Setja upp MediaView til að binda við stærð foreldris fyrir stærðarbreytingu
        StackPane mediaViewParent = (StackPane) mediaView.getParent();
        if (mediaViewParent != null) {
            // Setja MediaView til að stækka með foreldri
            mediaView.fitWidthProperty().bind(mediaViewParent.widthProperty().multiply(0.95));
            mediaView.fitHeightProperty().bind(mediaViewParent.heightProperty().multiply(0.95));
        }
    }
    
    /**
     * Setur viðburðalíkan til að birta myndmiðla þess.
     * 
     * @param model Viðburðalíkanið
     */
    public void setEventModel(EventModel model) {
        this.eventModel = model;
        
        // Bæta hlustara við myndmiðlaeiginleika
        if (model != null) {
            model.promoVideoProperty().addListener((obs, oldMedia, newMedia) -> {
                setupMediaPlayer(newMedia);
            });
            
            // Upphafsstilla ef myndmiðill er til staðar
            if (model.promoVideoProperty().get() != null) {
                setupMediaPlayer(model.promoVideoProperty().get());
            } else {
                // Endurstilla MediaView ef enginn myndmiðill er til staðar
                resetMediaView();
            }
        } else {
            resetMediaView();
        }
    }
    
    /**
     * Endurstilla MediaView þegar enginn myndmiðill er til staðar.
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
        mediaTitle.setText("Kynningarmyndband");
    }
    
    /**
     * Setur tilvísun í aðalstýriklasann.
     * 
     * @param controller Aðalstýriklasinn
     */
    public void setMainController(EventManagerController controller) {
        this.mainController = controller;
    }
    
    /**
     * Spilar myndmiðilinn.
     */
    @FXML
    private void handlePlay() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.play();
                playButton.setDisable(true);
                pauseButton.setDisable(false);
                stopButton.setDisable(false);
                System.out.println("Spila myndmiðil: " + mediaPlayer.getMedia().getSource());
                
                // Debug upplýsingar til að athuga myndmiðlaeiginleika
                mediaPlayer.setOnReady(() -> {
                    System.out.println("Myndmiðill er tilbúinn");
                    System.out.println("Myndmiðilsbreidd: " + mediaPlayer.getMedia().getWidth());
                    System.out.println("Myndmiðilshæð: " + mediaPlayer.getMedia().getHeight());
                    System.out.println("Lengd myndmiðils: " + mediaPlayer.getMedia().getDuration());
                    System.out.println("MediaView fit breidd: " + mediaView.getFitWidth());
                    System.out.println("MediaView fit hæð: " + mediaView.getFitHeight());
                });
            } catch (Exception e) {
                System.err.println("Villa við að spila myndmiðil: " + e.getMessage());
            }
        }
    }
    
    /**
     * Pásar myndmiðilinn.
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
     * Stöðvar myndmiðilinn.
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
     * Opnar skráarval til að velja myndband.
     */
    @FXML
    private void handleSelectVideo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Velja kynningarmyndband");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Myndbandsskrár", "*.mp4", "*.mov", "*.avi", "*.wmv")
        );
        
        File selectedFile = fileChooser.showOpenDialog(mediaContainer.getScene().getWindow());
        if (selectedFile != null && eventModel != null) {
            try {
                System.out.println("Hleður myndbandsskrá: " + selectedFile.getAbsolutePath());
                Media media = new Media(selectedFile.toURI().toString());
                eventModel.promoVideoProperty().set(media);
            } catch (MediaException me) {
                System.err.println("Myndmiðlavilla: " + me.getMessage());
                System.err.println("Villutegund: " + me.getType());
                if (me.getCause() != null) {
                    System.err.println("Orsök: " + me.getCause().getMessage());
                }
            } catch (Exception e) {
                System.err.println("Villa við að hlaða myndmiðilsskrá: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Setur upp MediaPlayer með gefnum myndmiðli.
     * 
     * @param media Myndmiðillinn til að spila
     */
    private void setupMediaPlayer(Media media) {
        // Hreinsa gamla MediaPlayer ef hann er til staðar
        if (mediaPlayer != null) {
            if (currentTimeListener != null) {
                mediaPlayer.currentTimeProperty().removeListener(currentTimeListener);
            }
            mediaPlayer.dispose();
        }
        
        if (media != null) {
            try {
                // Búa til nýjan MediaPlayer
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                
                // Setja viðbótareiginleika fyrir MediaView
                mediaView.setPreserveRatio(true);
                mediaView.setSmooth(true);
                
                // Tryggja að MediaView stækki með foreldri sínu
                StackPane mediaViewParent = (StackPane) mediaView.getParent();
                if (mediaViewParent != null) {
                    mediaView.fitWidthProperty().bind(mediaViewParent.widthProperty().multiply(0.95));
                    mediaView.fitHeightProperty().bind(mediaViewParent.heightProperty().multiply(0.95));
                }
                
                // Uppfæra titil ef við höfum heimild
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
                    System.out.println("Myndmiðilsheimild: " + source);
                }
                
                // Virkja stjórntæki
                timeSlider.setDisable(false);
                
                // Setja upp tímamælingu
                currentTimeListener = (obs, oldTime, newTime) -> {
                    if (!timeSlider.isValueChanging()) {
                        updating = true;
                        Duration duration = mediaPlayer.getMedia().getDuration();
                        if (duration.greaterThan(Duration.ZERO)) {
                            // Umbreyta lengd í prósentu fyrir sleðann
                            double percentage = newTime.toMillis() / duration.toMillis() * 100.0;
                            timeSlider.setValue(percentage);
                            
                            // Uppfæra tímamerki
                            updateTimeLabel(newTime, duration);
                        }
                        updating = false;
                    }
                };
                
                mediaPlayer.currentTimeProperty().addListener(currentTimeListener);
                
                // Bæta við villumeðhöndlun
                mediaPlayer.setOnError(() -> {
                    MediaException error = mediaPlayer.getError();
                    System.err.println("MediaPlayer villa: " + error.getMessage());
                    if (error.getCause() != null) {
                        System.err.println("Orsök: " + error.getCause().getMessage());
                    }
                });
                
                // Setja upp meðhöndlun fyrir lok myndmiðils
                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.stop();
                    mediaPlayer.seek(Duration.ZERO);
                    playButton.setDisable(false);
                    pauseButton.setDisable(true);
                    stopButton.setDisable(true);
                });
                
                // Sjálfvirk spilun
                mediaPlayer.play();
                playButton.setDisable(true);
                pauseButton.setDisable(false);
                stopButton.setDisable(false);
                
                // Prenta debug upplýsingar þegar myndmiðill er tilbúinn
                mediaPlayer.setOnReady(() -> {
                    System.out.println("Myndmiðill tilbúinn:");
                    System.out.println("  Breidd: " + media.getWidth());
                    System.out.println("  Hæð: " + media.getHeight());
                    System.out.println("  Lengd: " + media.getDuration());
                });
            } catch (Exception e) {
                System.err.println("Villa við að setja upp MediaPlayer: " + e.getMessage());
                e.printStackTrace();
                resetMediaView();
            }
        } else {
            // Enginn myndmiðill, endurstilla allt
            resetMediaView();
        }
    }
    
    /**
     * Uppfærir tímamerki með núverandi tíma og lengd.
     * 
     * @param currentTime Núverandi spilunartími
     * @param duration Heildarlengd
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