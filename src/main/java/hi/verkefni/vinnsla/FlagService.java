package hi.verkefni.vinnsla;

import hi.verkefni.vidmot.GameSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
    
    /**
     * Get only the most recognizable flags from a specific continent
     *
     * @param continent The continent to get flags from
     * @return List of the most recognizable flags from that continent
     */
    public List<Flag> getTopFlagsByContinent(String continent) {
        List<String> topCodes = TopFlagsService.getTopFlagsForContinent(continent);
        List<Flag> flags = getFlagsByContinent(continent);
        
        return flags.stream()
                .filter(flag -> topCodes.contains(flag.getCountryCode()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get a combined list of the most recognizable flags from all continents
     *
     * @return List of the most recognizable flags from all continents
     */
    public List<Flag> getAllTopFlags() {
        List<Flag> result = new ArrayList<>();
        
        for (String continent : getContinents()) {
            result.addAll(getTopFlagsByContinent(continent));
        }
        
        return result;
    }
    
    /**
     * Gets unused flags from a list of flags
     * 
     * @param flags The list of flags to filter
     * @return A new list containing only unused flags
     */
    private List<Flag> getUnusedFlags(List<Flag> flags) {
        return flags.stream()
                .filter(flag -> !GameSettings.getInstance().isFlagUsed(flag.getCountryCode()))
                .collect(Collectors.toList());
    }
    
    /**
     * Generates a list of country name options for a given flag, including the correct answer
     * and additional options from the same continent or other continents if necessary.
     *
     * @param currentFlag      The flag for which the country options are being generated.
     * @param numberOfOptions  The total number of country options to generate.
     * @return A list of country names, including the correct answer and other randomized options.
     *         The list is shuffled to randomize the order of the options.
     */
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
    
    /**
     * Gets a random flag from a specific continent that hasn't been used yet
     * If using easy mode, returns one of the most recognizable flags
     *
     * @param continent The continent to get a flag from
     * @param useTopFlags Whether to only use the most recognizable flags
     * @return A randomly selected unused flag, or null if all flags have been used
     */
    public Flag getRandomFlag(String continent, boolean useTopFlags) {
        List<Flag> flags;
        
        if (useTopFlags) {
            flags = getTopFlagsByContinent(continent);
            // If no top flags found for this continent, fall back to all flags
            if (flags.isEmpty()) {
                flags = getFlagsByContinent(continent);
            }
        } else {
            flags = getFlagsByContinent(continent);
        }
        
        // Get only unused flags
        List<Flag> unusedFlags = getUnusedFlags(flags);
        
        // If all flags have been used, we might need to allow repeats
        if (unusedFlags.isEmpty()) {
            // But first, let's check if we've used all flags or are close to the limit
            int usedCount = GameSettings.getInstance().getUsedFlagCount();
            int totalFlags = flags.size();
            
            // If we're approaching the number of available flags for this filter, allow repeats
            if (usedCount >= totalFlags * 0.8) {
                System.out.println("Warning: Almost all flags have been used, allowing repeats.");
                return flags.get(random.nextInt(flags.size()));
            }
            
            // Otherwise, if we've filtered too aggressively, try less filtering
            if (useTopFlags) {
                // Try all flags from this continent instead of just top flags
                flags = getFlagsByContinent(continent);
                unusedFlags = getUnusedFlags(flags);
                
                if (!unusedFlags.isEmpty()) {
                    System.out.println("Falling back to all continent flags");
                    return unusedFlags.get(random.nextInt(unusedFlags.size()));
                }
            }
            
            // If still no flags, try all continents
            if (!"All Continents".equals(continent)) {
                System.out.println("Falling back to all continents");
                return getRandomFlag(useTopFlags);
            }
            
            // If we've really used all flags, allow repeats as a last resort
            System.out.println("Warning: All flags have been used, allowing repeats.");
            return flags.get(random.nextInt(flags.size()));
        }
        
        return unusedFlags.get(random.nextInt(unusedFlags.size()));
    }
    
    /**
     * Gets a random flag from any continent that hasn't been used yet
     * If using easy mode, returns one of the most recognizable flags
     *
     * @param useTopFlags Whether to only use the most recognizable flags
     * @return A randomly selected unused flag
     */
    public Flag getRandomFlag(boolean useTopFlags) {
        if (useTopFlags) {
            List<Flag> topFlags = getAllTopFlags();
            List<Flag> unusedTopFlags = getUnusedFlags(topFlags);
            
            if (!unusedTopFlags.isEmpty()) {
                return unusedTopFlags.get(random.nextInt(unusedTopFlags.size()));
            }
            
            // If all top flags used but we still need more, try using all flags
            if (GameSettings.getInstance().getUsedFlagCount() < getTotalFlagCount()) {
                useTopFlags = false; // Fall back to all flags
            } else {
                // We've used all flags, so repeat a top flag
                return topFlags.get(random.nextInt(topFlags.size()));
            }
        }
        
        // Use flags from all continents
        List<Flag> allFlags = new ArrayList<>();
        for (String continent : getContinents()) {
            allFlags.addAll(getFlagsByContinent(continent));
        }
        
        List<Flag> unusedFlags = getUnusedFlags(allFlags);
        
        if (unusedFlags.isEmpty()) {
            // If all flags have been used, pick a random one
            return allFlags.get(random.nextInt(allFlags.size()));
        }
        
        return unusedFlags.get(random.nextInt(unusedFlags.size()));
    }
    
    public int getTotalFlagCount() {
        int total = 0;
        for (List<Flag> flags : flagsByContinent.values()) {
            total += flags.size();
        }
        return total;
    }
    
    public int getTopFlagsCount() {
        return getAllTopFlags().size();
    }
    
    public int getFlagCountByContinent(String continent) {
        return getFlagsByContinent(continent).size();
    }
    
    public int getTopFlagCountByContinent(String continent) {
        return getTopFlagsByContinent(continent).size();
    }
}