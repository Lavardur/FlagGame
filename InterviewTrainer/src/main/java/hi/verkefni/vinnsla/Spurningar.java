package hi.verkefni.vinnsla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Spurningar {
    private Map<String, List<String>> spurningaflokkar;

    public Spurningar() {
        spurningaflokkar = new HashMap<>();
        initializeSpurningaflokkar();
    }

    private void initializeSpurningaflokkar() {
        List<String> taeknispurningar = new ArrayList<>();
        taeknispurningar.add("Hvernig myndir þú leysa vandamál í forritun?");
        taeknispurningar.add("Hvað er OOP?");
        taeknispurningar.add("Hvernig geturðu bætt árangur forrits?");
        
        List<String> faernispurningar = new ArrayList<>();
        faernispurningar.add("Hvernig myndir þú lýsa þínum hæfileikum?");
        faernispurningar.add("Hvað gerir þig að góðum liðsfélaga?");
        faernispurningar.add("Hvernig hefur þú leyst deilur í liðinu þínu?");
        
        spurningaflokkar.put("Tæknispurningar", taeknispurningar);
        spurningaflokkar.put("Færnispurningar", faernispurningar);
    }

    public List<String> getSpurningar(String flokkur) {
        return spurningaflokkar.getOrDefault(flokkur, new ArrayList<>());
    }

    public List<String> getSpurningaflokkar(String category) {
        return spurningaflokkar.getOrDefault(category, new ArrayList<>());
    }
}