package hi.verkefni.vinnsla;

public class FeedbackService {

    public String provideFeedback(String userResponse) {
        // Basic feedback logic based on user response
        if (userResponse == null || userResponse.trim().isEmpty()) {
            return "Please provide an answer.";
        } else if (userResponse.length() < 20) {
            return "Your answer is too short. Try to elaborate more.";
        } else {
            return "Good job! Consider adding more details to strengthen your answer.";
        }
    }
}