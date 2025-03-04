package hi.vinnsla;

import java.util.HashMap;
import java.util.Map;

public class SlongurStigar {
    private final Map<Integer, Integer> slongurStigarMap = new HashMap<>();

    public void setSlangaEðaStigi(int reitur, int lendingarReitur) {
        slongurStigarMap.put(reitur, lendingarReitur);
    }

    public Integer getLendingarReitur(int reitur) {
        return slongurStigarMap.get(reitur);
    }
}
