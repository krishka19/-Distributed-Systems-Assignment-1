import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Get the username from the client
            out.println("Enter your username:");
            username = in.readLine();
            System.out.println("Received username: " + username);

            if (username == null || username.isEmpty()) {
                socket.close();
                return;
            }

            out.println("Welcome to the quiz, " + username + "!");

            // Iterate through the quiz questions
            for (Question q : QuizServer.questions) {
                out.println(q.getQuestion());
                String answer = in.readLine();

                // Check the answer and update the score
                if (q.isCorrect(answer)) {
                    QuizServer.updateScores(username, 1);
                    out.println("Correct! You got 1 point.");
                } else {
                    out.println("Incorrect. The correct answer was: " + q.getCorrectAnswer());
                }

                // Send updated leaderboard after each question
                out.println("Updated Leaderboard:\n" + QuizServer.getLeaderboard());
            }

            // Notify that the quiz is finished
            out.println("Quiz finished!");

            // Announce the winner
            String winnerAnnouncement = QuizServer.announceWinner();
            QuizServer.broadcast(winnerAnnouncement);

            // Send the final leaderboard and winner to the client
            out.println("Final Leaderboard:\n" + QuizServer.getLeaderboard());
            out.println(winnerAnnouncement);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}

