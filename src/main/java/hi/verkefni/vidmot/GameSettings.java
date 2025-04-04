package hi.verkefni.vidmot;

import java.util.HashSet;
import java.util.Set;

/**
 * Singleton class to store game settings between views
 */
public class GameSettings {
    private static GameSettings instance;
    
    private String continent = "All Continents";
    private String difficulty = "Medium";
    private int correctAnswers = 0;
    private int totalQuestions = 0;
    private int questionsPerRound = 10;
    
    // Track used flags to prevent duplicates
    private Set<String> usedFlags = new HashSet<>();
    
    private GameSettings() {
        // Private constructor to prevent instantiation
    }
    
    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }
    
    public String getContinent() {
        return continent;
    }
    
    public void setContinent(String continent) {
        this.continent = continent;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    /**
     * Get the number of options to display based on difficulty level
     * @return Number of options (3 for Easy, 4 for Medium, 5 for Hard)
     */
    public int getNumberOfOptions() {
        switch (difficulty) {
            case "Easy":
                return 3;
            case "Hard":
                return 5; // Changed from 6 to 5
            case "Medium":
            default:
                return 4;
        }
    }
    
    public boolean useTopFlags() {
        return "Easy".equals(difficulty);
    }
    
    public int getCorrectAnswers() {
        return correctAnswers;
    }
    
    public void incrementCorrectAnswers() {
        this.correctAnswers++;
    }
    
    public int getTotalQuestions() {
        return totalQuestions;
    }
    
    public void incrementTotalQuestions() {
        this.totalQuestions++;
    }
    
    public int getQuestionsPerRound() {
        return questionsPerRound;
    }
    
    public void setQuestionsPerRound(int questionsPerRound) {
        this.questionsPerRound = questionsPerRound;
    }
    
    public boolean isGameComplete() {
        return totalQuestions >= questionsPerRound;
    }
    
    /**
     * Track a flag as used in the current game session
     * @param countryCode The country code of the flag
     */
    public void markFlagAsUsed(String countryCode) {
        usedFlags.add(countryCode);
    }
    
    /**
     * Check if a flag has already been used in the current game session
     * @param countryCode The country code to check
     * @return true if the flag has already been used
     */
    public boolean isFlagUsed(String countryCode) {
        return usedFlags.contains(countryCode);
    }
    
    /**
     * Get the number of unique flags that have been used
     * @return Count of used flags
     */
    public int getUsedFlagCount() {
        return usedFlags.size();
    }
    
    public void resetGameCounters() {
        correctAnswers = 0;
        totalQuestions = 0;
        usedFlags.clear(); // Clear the set of used flags
    }
    
    public void resetAll() {
        resetGameCounters();
        continent = "All Continents";
        difficulty = "Medium";
    }
}