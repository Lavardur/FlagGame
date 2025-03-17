package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * EventView er sérsniðinn hluti skilgreindur í event-view.fxml skránni.
 * Hann sér um viðmótshluta fyrir að sýna og breyta EventModel.
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
    
    private EventModel eventModel;
    
    /**
     * Sjálfgefinn smiður sem hleður FXML skránni og frumstillir hlutinn.
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
        
        // Býr til sjálfgefið viðburðarlíkan
        eventModel = new EventModel("", "Concert", LocalDate.now(), LocalTime.of(12, 0), null);
        
        // Frumstillir hlutinn
        initialize();
    }
    
    /**
     * Frumstillir hlutinn með því að setja upp viðmótshluta og bindingar.
     */
    private void initialize() {
        // Setur upp flokkavalmynd
        fxCategory.getItems().addAll("Concert", "Theater", "Sports", "Conference", "Exhibition", "Other");
        fxCategory.setValue("Concert");
        
        // Setur upp tíma spinner
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
        
        // Setur upp umbreytingu fyrir spinner
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
        
        // Setur upp bindingar
        setupBindings();
    }
    
    /**
     * Setur upp tvíátta bindingar milli viðmótshluta og eiginleika EventModel.
     */
    private void setupBindings() {
        // Bindur textareit við nafn eiginleika viðburðar
        fxEventName.textProperty().bindBidirectional(eventModel.eventNameProperty());
        
        // Bindur valmynd við flokkseiginleika
        fxCategory.valueProperty().bindBidirectional(eventModel.categoryProperty());
        
        // Bindur dagatalsval við dagseiginleika
        fxDate.valueProperty().bindBidirectional(eventModel.dateProperty());
        
        // Fyrir tíma spinner, notum við hlustara þar sem Spinner styður ekki beinar bindingar
        fxTime.valueProperty().addListener((obs, oldVal, newVal) -> {
            eventModel.timeProperty().set(newVal);
        });
        
        eventModel.timeProperty().addListener((obs, oldVal, newVal) -> {
            fxTime.getValueFactory().setValue(newVal);
        });
    }
    
    /**
     * Nær í viðburðarlíkanið sem tengist þessari sýn.
     * 
     * @return Viðburðarlíkanið
     */
    public EventModel getEventModel() {
        return eventModel;
    }
    
    /**
     * Setur nýtt viðburðarlíkan fyrir þessa sýn.
     * 
     * @param model Nýja viðburðarlíkanið
     */
    public void setEventModel(EventModel model) {
        this.eventModel = model;
        
        // Uppfærir viðmótshluta með gildum úr líkaninu
        fxEventName.setText(model.eventNameProperty().get());
        fxCategory.setValue(model.categoryProperty().get());
        fxDate.setValue(model.dateProperty().get());
        fxTime.getValueFactory().setValue(model.timeProperty().get());
        
        // Endurnýjar bindingar
        setupBindings();
    }
}
