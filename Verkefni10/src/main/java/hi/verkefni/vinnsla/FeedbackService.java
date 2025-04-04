package hi.verkefni.vinnsla;

public class FeedbackService {

    /**
     * Gefur endurgjöf byggt á svari notanda.
     *
     * @param userResponse Svar notanda.
     * @return Endurgjöf byggt á svari notanda.
     */
    public String provideFeedback(String userResponse) {
        if (userResponse == null || userResponse.trim().isEmpty()) {
            return "Vinsamlegast gefðu svar.";
        } else if (userResponse.length() < 20) {
            return "Svarið þitt er of stutt. Reyndu að útskýra betur.";
        } else {
            return "Gott svar! Prófaðu að bæta við fleiri smáatriðum til að styrkja svarið þitt.";
        }
    }

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