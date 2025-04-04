package hi.verkefni.vinnsla;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CountryDataLoader {
    
    private static Map<String, String> countryMap; // code -> name
    private static Map<String, String> continentMap; // code -> continent
    
    static {
        // Initialize continent mapping
        continentMap = new HashMap<>();
        
        // Europe
        String[] europeCodes = {"ad", "al", "at", "ba", "be", "bg", "by", "ch", "cy", "cz", "de", "dk", "ee", "es", 
                               "fi", "fr", "gb", "gb-eng", "gb-nir", "gb-sct", "gb-wls", "gr", "hr", "hu", "ie", "is", "it", "li", 
                               "lt", "lu", "lv", "mc", "md", "me", "mk", "mt", "nl", "no", "pl", "pt", "ro", "rs", 
                               "ru", "se", "si", "sk", "sm", "ua", "va", "xk"};
        
        // Asia
        String[] asiaCodes = {"ae", "af", "am", "az", "bd", "bh", "bn", "bt", "cn", "cy", "ge", "hk", "id", "il", 
                             "in", "iq", "ir", "jo", "jp", "kg", "kh", "kp", "kr", "kw", "kz", "la", "lb", "lk", 
                             "mm", "mn", "mo", "mv", "my", "np", "om", "ph", "pk", "ps", "qa", "sa", "sg", "sy", 
                             "th", "tj", "tl", "tm", "tr", "tw", "uz", "vn", "ye"};
        
        // Africa
        String[] africaCodes = {"ao", "bf", "bi", "bj", "bw", "cd", "cf", "cg", "ci", "cm", "cv", "dj", "dz", "eg", 
                               "eh", "er", "et", "ga", "gh", "gm", "gn", "gq", "gw", "ke", "km", "lr", "ls", "ly", 
                               "ma", "mg", "ml", "mr", "mu", "mw", "mz", "na", "ne", "ng", "rw", "sc", "sd", "sl", 
                               "sn", "so", "ss", "st", "sz", "td", "tg", "tn", "tz", "ug", "za", "zm", "zw"};
        
        // Americas
        String[] americasCodes = {"ag", "ar", "bb", "bo", "br", "bs", "bz", "ca", "cl", "co", "cr", "cu", "dm", "do", 
                                 "ec", "gd", "gt", "gy", "hn", "ht", "jm", "kn", "lc", "mx", "ni", "pa", "pe", "py", 
                                 "sr", "sv", "tt", "us", "uy", "vc", "ve"};
        
        // Oceania
        String[] oceaniaCodes = {"as", "au", "fj", "fm", "ki", "mh", "nr", "nu", "nz", "pg", "pw", "sb", "to", "tv", 
                                "vu", "ws"};
        
        // Add all countries to their continents
        for (String code : europeCodes) {
            continentMap.put(code, "Europe");
        }
        
        for (String code : asiaCodes) {
            continentMap.put(code, "Asia");
        }
        
        for (String code : africaCodes) {
            continentMap.put(code, "Africa");
        }
        
        for (String code : americasCodes) {
            continentMap.put(code, "Americas");
        }
        
        for (String code : oceaniaCodes) {
            continentMap.put(code, "Oceania");
        }
        
        // Load country data
        countryMap = loadCountryData();
    }
    
    private static Map<String, String> loadCountryData() {
        Map<String, String> result = new HashMap<>();
        JSONParser parser = new JSONParser();
        
        try (InputStream in = CountryDataLoader.class.getResourceAsStream("/codes.json")) {
            if (in == null) {
                System.err.println("Cannot find codes.json resource");
                return result;
            }
            
            String jsonContent = new BufferedReader(new InputStreamReader(in))
                    .lines().collect(Collectors.joining("\n"));
            
            JSONObject jsonObject = (JSONObject) parser.parse(jsonContent);
            
            for (Object key : jsonObject.keySet()) {
                String code = (String) key;
                String name = (String) jsonObject.get(key);
                result.put(code, name);
            }
            
        } catch (IOException | ParseException e) {
            System.err.println("Error loading country data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static Map<String, String> getCountryData() {
        return countryMap;
    }
    
    public static String getCountryName(String code) {
        return countryMap.getOrDefault(code, "Unknown");
    }
    
    public static String getContinent(String code) {
        return continentMap.getOrDefault(code, "Other");
    }
    
    public static boolean isCountryCode(String code) {
        return countryMap.containsKey(code);
    }
}