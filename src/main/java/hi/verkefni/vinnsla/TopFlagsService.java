package hi.verkefni.vinnsla;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class providing access to the most recognizable flags by continent
 */
public class TopFlagsService {

    private static final Map<String, List<String>> topFlagsByContinentMap = new HashMap<>();
    
    static {
        // Initialize the top flags for each continent
        
        // Africa
        topFlagsByContinentMap.put("Africa", Arrays.asList(
            "za", // South Africa
            "eg", // Egypt
            "ke", // Kenya
            "ng", // Nigeria
            "et", // Ethiopia
            "gh", // Ghana
            "ma", // Morocco
            "tz", // Tanzania
            "zm", // Zambia
            "dz"  // Algeria
        ));
        
        // North America (part of Americas in our app)
        List<String> northAmerica = Arrays.asList(
            "us", // United States
            "ca", // Canada
            "mx", // Mexico
            "cu", // Cuba
            "jm", // Jamaica
            "ht", // Haiti
            "pa", // Panama
            "cr", // Costa Rica
            "do", // Dominican Republic
            "tt"  // Trinidad & Tobago
        );
        
        // South America (part of Americas in our app)
        List<String> southAmerica = Arrays.asList(
            "br", // Brazil
            "ar", // Argentina
            "co", // Colombia
            "cl", // Chile
            "pe", // Peru
            "ve", // Venezuela
            "ec", // Ecuador
            "uy", // Uruguay
            "py", // Paraguay
            "bo"  // Bolivia
        );
        
        // Combine North and South America for our "Americas" continent
        topFlagsByContinentMap.put("Americas", northAmerica);
        
        topFlagsByContinentMap.put("Americas", southAmerica);
        
        // Asia
        topFlagsByContinentMap.put("Asia", Arrays.asList(
            "cn", // China
            "jp", // Japan
            "in", // India
            "sa", // Saudi Arabia
            "kr", // South Korea
            "id", // Indonesia
            "ph", // Philippines
            "th", // Thailand
            "vn", // Vietnam
            "ir"  // Iran
        ));
        
        // Oceania
        topFlagsByContinentMap.put("Oceania", Arrays.asList(
            "au", // Australia
            "nz", // New Zealand
            "fj", // Fiji
            "pg", // Papua New Guinea
            "vu", // Vanuatu
            "to", // Tonga
            "ws", // Samoa
            "ki", // Kiribati
            "nr", // Nauru
            "sb"  // Solomon Islands
        ));
        
        // Europe
        topFlagsByContinentMap.put("Europe", Arrays.asList(
            "gb", // United Kingdom
            "fr", // France
            "de", // Germany
            "it", // Italy
            "es", // Spain
            "ru", // Russia
            "se", // Sweden
            "no", // Norway
            "ch", // Switzerland
            "gr"  // Greece
        ));
    }
    
    /**
     * Get the list of country codes for the most recognizable flags in a continent
     *
     * @param continent The continent name
     * @return List of country codes for the top flags
     */
    public static List<String> getTopFlagsForContinent(String continent) {
        return topFlagsByContinentMap.getOrDefault(continent, Arrays.asList());
    }
    
    /**
     * Checks if the given country code is among the most recognizable flags of its continent
     *
     * @param countryCode The country code to check
     * @return true if the country is among the most recognizable flags
     */
    public static boolean isTopFlag(String countryCode) {
        if (countryCode == null) return false;
        
        String continent = CountryDataLoader.getContinent(countryCode);
        List<String> topFlags = getTopFlagsForContinent(continent);
        
        return topFlags.contains(countryCode);
    }
}