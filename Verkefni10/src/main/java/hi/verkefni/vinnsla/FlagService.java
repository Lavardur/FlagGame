package hi.verkefni.vinnsla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FlagService {
    private Map<String, List<Flag>> flagsByContinent;
    private Random random;
    
    public FlagService() {
        flagsByContinent = new HashMap<>();
        random = new Random();
        initializeFlags();
    }
    
    private void initializeFlags() {
        // Initialize continent lists
        for (String continent : new String[]{"Europe", "Asia", "Africa", "Americas", "Oceania"}) {
            flagsByContinent.put(continent, new ArrayList<>());
        }
        
        // Get country data
        Map<String, String> countryData = CountryDataLoader.getCountryData();
        
        // Fill flag lists by continent
        for (Map.Entry<String, String> entry : countryData.entrySet()) {
            String code = entry.getKey();
            String name = entry.getValue();
            
            // Skip sub-regions (like US states or UK countries)
            if (code.contains("-")) {
                continue;
            }
            
            // Get continent for this country
            String continent = CountryDataLoader.getContinent(code);
            if (!continent.equals("Other")) {
                Flag flag = new Flag(code, name, continent);
                flagsByContinent.get(continent).add(flag);
            }
        }
    }
    
    public List<String> getContinents() {
        return new ArrayList<>(flagsByContinent.keySet());
    }
    
    public List<Flag> getFlagsByContinent(String continent) {
        return flagsByContinent.getOrDefault(continent, new ArrayList<>());
    }
    
    public List<String> getCountryOptionsForFlag(Flag currentFlag, int numberOfOptions) {
        List<String> options = new ArrayList<>();
        options.add(currentFlag.getCountryName()); // Add correct answer
        
        // Get flags from the same continent for more challenging options
        List<Flag> continentFlags = new ArrayList<>(getFlagsByContinent(currentFlag.getContinent()));
        
        // Remove the current flag
        continentFlags.removeIf(flag -> flag.getCountryName().equals(currentFlag.getCountryName()));
        
        // Shuffle and select random options from the same continent
        Collections.shuffle(continentFlags);
        
        // Add options from the same continent first
        for (Flag flag : continentFlags) {
            if (options.size() < numberOfOptions) {
                options.add(flag.getCountryName());
            } else {
                break;
            }
        }
        
        // If we need more options than available in the continent, add from other continents
        if (options.size() < numberOfOptions) {
            List<Flag> otherFlags = new ArrayList<>();
            for (String cont : getContinents()) {
                if (!cont.equals(currentFlag.getContinent())) {
                    otherFlags.addAll(getFlagsByContinent(cont));
                }
            }
            
            Collections.shuffle(otherFlags);
            
            for (Flag flag : otherFlags) {
                if (options.size() < numberOfOptions) {
                    options.add(flag.getCountryName());
                } else {
                    break;
                }
            }
        }
        
        // Shuffle again to randomize option order
        Collections.shuffle(options);
        return options;
    }
    
    public Flag getRandomFlag(String continent) {
        List<Flag> flags = getFlagsByContinent(continent);
        if (flags.isEmpty()) {
            return null;
        }
        return flags.get(random.nextInt(flags.size()));
    }
    
    public Flag getRandomFlag() {
        List<String> continents = getContinents();
        String randomContinent = continents.get(random.nextInt(continents.size()));
        return getRandomFlag(randomContinent);
    }
    
    public int getTotalFlagCount() {
        int total = 0;
        for (List<Flag> flags : flagsByContinent.values()) {
            total += flags.size();
        }
        return total;
    }
    
    public int getFlagCountByContinent(String continent) {
        return getFlagsByContinent(continent).size();
    }
}