package hi.verkefni.vidmot;

/**
 * Singleton class to store game settings between views
 */
public class GameSettings {
    private static GameSettings instance;
    
    private String continent = "All Continents";
    private String difficulty = "Medium";
    
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
    
    public int getNumberOfOptions() {
        switch (difficulty) {
            case "Easy":
                return 3;
            case "Hard":
                return 6;
            case "Medium":
            default:
                return 4;
        }
    }
}