package hi.verkefni.vinnsla;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.scene.media.Media;

/**
 * Í klasanum eru eigindin fyrir viðburðinn, þ.e. heiti viðburðar (strengur), flokkur (Flokkur),
 * dagsetning (LocalDate), tími (LocalTime) og kynningarmyndband (Media).
 * Skilgreindu allar tilviksbreyturnar sem property breytur, t.d. SimpleStringProperty,
 * SimpleObjectProperty. Þú mynd binda við þessar breytur í notendaviðmótinu.
 */
public class EventModel {
    private final SimpleStringProperty eventName;
    private final SimpleObjectProperty<String> category;
    private final SimpleObjectProperty<LocalDate> date;
    private final SimpleObjectProperty<LocalTime> time;
    private final SimpleObjectProperty<Media> promoVideo;

    public EventModel(String eventName, String category, LocalDate date, LocalTime time, Media promoVideo) {
        this.eventName = new SimpleStringProperty(eventName);
        this.category = new SimpleObjectProperty<>(category);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.promoVideo = new SimpleObjectProperty<>(promoVideo);
    }

    public SimpleStringProperty eventNameProperty() {
        return eventName;
    }

    public SimpleObjectProperty<String> categoryProperty() {
        return category;
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public SimpleObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public SimpleObjectProperty<Media> promoVideoProperty() {
        return promoVideo;
    }
}