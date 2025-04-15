/******************************************************************************
 *  Nafn    : Anton Benediktsson
 *  T-póstur: anb59@hi.is
 *  Lýsing  : Hjálparklasi fyrir vinnslu og birtingu á fána myndum í notendaviðmóti.
 *            Klasi sem inniheldur aðferðir til að velja handahófskennda fána og 
 *            birta þá í ImageView hluti.
 *****************************************************************************/
package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.CountryDataLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class for handling flag images in the UI
 */
public class FlagImageUtils {
    private static final Random random = new Random();
    
    /**
     * Sets a random flag image to the given ImageView
     *
     * @param imageView The ImageView to set the random flag to
     */
    public static void setRandomFlag(ImageView imageView) {
        String code = getRandomCountryCode();
        String flagPath = "/flags/" + code + ".png";
        
        try {
            Image flagImage = new Image(FlagImageUtils.class.getResourceAsStream(flagPath));
            imageView.setImage(flagImage);
        } catch (Exception e) {
            System.err.println("Could not load flag image: " + flagPath);
            e.printStackTrace();
        }
    }
    
    /**
     * Gets a random country code from the available country data
     *
     * @return A random two-letter country code
     */
    public static String getRandomCountryCode() {
        List<String> codes = new ArrayList<>(CountryDataLoader.getCountryData().keySet());
        
        // Filter out non-standard codes like us-xx or gb-xx
        codes.removeIf(code -> code.contains("-"));
        
        if (codes.isEmpty()) {
            return "is"; // Default to Iceland if no valid codes
        }
        
        return codes.get(random.nextInt(codes.size()));
    }
}