public class Question {
    private String question;
    private String correctAnswer;

    public Question(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isCorrect(String answer) {
        return answer.equalsIgnoreCase(correctAnswer);
    }

    // This method returns the correct answer for when a client gets it wrong
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
