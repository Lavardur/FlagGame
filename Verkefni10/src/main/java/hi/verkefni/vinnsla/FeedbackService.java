package hi.verkefni.vinnsla;

public class FeedbackService {

    /**
     * Evaluates if the user's guess matches the correct country
     *
     * @param userGuess The country guessed by the user
     * @param correctCountry The correct country name
     * @return Feedback message based on the guess
     */
    public String evaluateGuess(String userGuess, String correctCountry) {
        if (userGuess == null || userGuess.trim().isEmpty()) {
            return "Please select an answer.";
        }
        
        if (userGuess.equals(correctCountry)) {
            return "Correct! Well done!";
        } else {
            return "Incorrect. The correct answer is " + correctCountry + ".";
        }
    }
}