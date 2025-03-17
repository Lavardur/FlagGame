package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.EventModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Aðalstýring fyrir EventManager forritið.
 */
public class EventManagerController {
    
    @FXML
    private EventView eventView;
    
    @FXML
    private BorderPane rootPane;
    
    @FXML
    private MenuController menuViewController;
    
    @FXML
    private MediaViewController mediaViewController;
    
    @FXML
    private StackPane fxEventViews;
    
    // Núverandi viðburður í viðmótinu
    private EventView currentView;  // núverandi viðburður í viðmótinu
    
    // Listi af viðburðum
    private ObservableList<EventModel> list = 
            FXCollections.observableArrayList();
    
    // Kort til að halda utan um hvaða EventView tengist hvaða EventModel
    private Map<EventModel, EventView> viewMap = new HashMap<>();
    
    

    /**
     * Frumstillir stýringuna.
     * Setur fyrsta EventView sem núverandi viðburð.
     */
    @FXML
    private void initialize() {
        // Tengir stýringar
        if (menuViewController != null) {
            menuViewController.setMainController(this);
        }
        
        // Reynir að finna StackPane ef það var ekki sprautað inn
        if (fxEventViews == null) {
            // StackPane er í miðju BorderPane
            if (rootPane.getCenter() instanceof StackPane) {
                fxEventViews = (StackPane) rootPane.getCenter();
            }
        }
        
        // Setur upphaflegt EventView sem núverandi sýn
        if (eventView != null) {
            currentView = eventView;
            list.add(currentView.getEventModel());
            viewMap.put(currentView.getEventModel(), currentView);
            
            // Tengir MediaViewController við núverandi EventModel
            if (mediaViewController != null) {
                mediaViewController.setMainController(this);
                mediaViewController.setEventModel(currentView.getEventModel());
            }
        } else {
            // Ef ekkert EventView var sprautað inn, býr til nýtt
            nyr();
        }
    }
    
    /**
     * Býr til nýjan viðburð.
     * Býr til nýtt EventView, gerir það að núverandi viðburði og bætir því við fxEventViews.
     */
    public void nyr() {
        // Býr til nýtt EventView
        EventView newView = new EventView();
        currentView = newView;
        
        // Bætir við lista og kort
        EventModel model = currentView.getEventModel();
        list.add(model);
        viewMap.put(model, currentView);
        
        // Bætir við viðmót
        if (fxEventViews != null) {
            fxEventViews.getChildren().add(currentView);
            switchView(currentView);
        }
        
        // Uppfærir media stýringuna með nýja líkaninu
        if (mediaViewController != null) {
            mediaViewController.setEventModel(model);
        }
    }
    
    /**
     * Vista núverandi viðburð í minni.
     * Tekur núverandi EventView og vistar það í minni (listi).
     */
    public void vista() {
        if (currentView != null) {
            EventModel model = currentView.getEventModel();
            
            // Athugar hvort þetta líkan sé þegar í listanum
            if (!list.contains(model)) {
                list.add(model);
            }
            
            // Gæti þess að það sé í kortinu
            viewMap.put(model, currentView);
        }
    }
    
    /**
     * Opnar núverandi viðburð.
     * Spyr notandann um nafn viðburðarins, leitar að því í minni.
     * Ef það finnst, birtir samsvarandi EventView.
     * Ef ekki, býr til nýtt EventView sem bendir á EventModel.
     */
    public void opna() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Open Event");
        dialog.setHeaderText("Enter Event Name");
        dialog.setContentText("Event name:");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            String eventName = result.get();
            
            // Finnur viðburðarlíkanið með gefnu nafni
            EventModel foundModel = null;
            for (EventModel model : list) {
                if (model.eventNameProperty().get().equals(eventName)) {
                    foundModel = model;
                    break;
                }
            }
            
            if (foundModel != null) {
                // Athugar hvort við höfum þegar sýn fyrir þetta líkan
                EventView view = viewMap.get(foundModel);
                
                if (view != null) {
                    // Sýn til, skiptir yfir á hana
                    currentView = view;
                    switchView(currentView);
                } else {
                    // Býr til nýja sýn fyrir þetta líkan
                    EventView newView = new EventView();
                    newView.setEventModel(foundModel);
                    viewMap.put(foundModel, newView);
                    
                    // Bætir við viðmót
                    fxEventViews.getChildren().add(newView);
                    currentView = newView;
                    switchView(currentView);
                }
                
                // Uppfærir media stýringuna með þessu líkani
                if (mediaViewController != null) {
                    mediaViewController.setEventModel(foundModel);
                }
            }
        }
    }
    
    /**
     * Eyðir núverandi viðburði.
     * Fjarlægir núverandi viðburð úr StackPane börnum.
     * Fjarlægir samsvarandi líkan úr listanum.
     * Ef einhver börn eru eftir, birtir síðasta viðburðinn sem var bætt við.
     */
    public void eyda() {
        if (currentView != null) {
            // Fjarlægir úr viðmóti
            fxEventViews.getChildren().remove(currentView);
            
            // Fjarlægir úr gagnastrúktúrum
            EventModel model = currentView.getEventModel();
            list.remove(model);
            viewMap.remove(model);
            
            // Ef einhver viðburðir eru eftir, birtir síðasta viðburðinn
            if (!fxEventViews.getChildren().isEmpty()) {
                Node lastChild = fxEventViews.getChildren().get(fxEventViews.getChildren().size() - 1);
                if (lastChild instanceof EventView) {
                    currentView = (EventView) lastChild;
                    switchView(currentView);
                    
                    // Uppfærir media stýringuna
                    if (mediaViewController != null) {
                        mediaViewController.setEventModel(currentView.getEventModel());
                    }
                }
            } else {
                currentView = null;
                
                // Hreinsar media stýringuna
                if (mediaViewController != null) {
                    mediaViewController.setEventModel(null);
                }
            }
        }
    }
    
    /**
     * Lokar núverandi viðburðarsýn.
     * Fjarlægir currentView úr StackPane.
     * Ef einhver viðburðir eru eftir, skiptir yfir á síðasta viðburð í StackPane.
     */
    public void loka() {
        if (currentView != null) {
            // Fjarlægir úr viðmóti
            fxEventViews.getChildren().remove(currentView);
            
            // Ef einhver viðburðir eru eftir, birtir síðasta viðburðinn
            if (!fxEventViews.getChildren().isEmpty()) {
                Node lastChild = fxEventViews.getChildren().get(fxEventViews.getChildren().size() - 1);
                if (lastChild instanceof EventView) {
                    currentView = (EventView) lastChild;
                    switchView(currentView);
                    
                    // Uppfærir media stýringuna
                    if (mediaViewController != null) {
                        mediaViewController.setEventModel(currentView.getEventModel());
                    }
                }
            } else {
                currentView = null;
                
                // Hreinsar media stýringuna
                if (mediaViewController != null) {
                    mediaViewController.setEventModel(null);
                }
            }
        }
    }
    
    /**
     * Hjálparaðferð til að skipta um sýnilega sýn.
     * 
     * @param targetView Sýnin sem á að gera sýnilega
     */
    private void switchView(EventView targetView) {
        for (Node node : fxEventViews.getChildren()) {
            node.setVisible(false); 
        }
        targetView.setVisible(true); 
        targetView.setFocusTraversable(true);
    }
    
    /**
     * Nær í núverandi EventView hluta.
     * @return Núverandi EventView
     */
    public EventView getEventView() {
        return currentView;
    }
}