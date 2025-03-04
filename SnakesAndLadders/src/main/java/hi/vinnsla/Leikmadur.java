package hi.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Leikmadur {
    private final IntegerProperty reitur = new SimpleIntegerProperty();
    private String nafn;

    public int getReitur() {
        return reitur.get();
    }

    public IntegerProperty reiturProperty() {
        return reitur;
    }

    public void setReitur(int reitur) {
        this.reitur.set(reitur);
    }

    public String getNafn() {
        return nafn;
    }

    public void setNafn(String nafn) {
        this.nafn = nafn;
    }
}
