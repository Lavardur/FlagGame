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
}