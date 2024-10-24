import java.io.*;
import java.net.*;
import java.util.*;

public class QuizServer {
    // List of connected clients
    private static List<ClientHandler> clients = new ArrayList<>();
    
    // List of quiz questions (18 total)
    public static List<Question> questions = Arrays.asList(
        new Question("What is the capital of France?", "Paris"),
        new Question("What is 5 + 7?", "12"),
        new Question("Which planet is known as the Red Planet?", "Mars"),
        new Question("Who wrote the play 'Romeo and Juliet'?", "Shakespeare"),
        new Question("What is the smallest ocean in the world?", "Arctic Ocean"),
        new Question("How many continents are there on Earth?", "7"),
        new Question("What is the chemical symbol for water?", "H2O"),
        new Question("Which country is home to the kangaroo?", "Australia"),
        new Question("In which year did the Titanic sink?", "1912"),
        new Question("What is the largest mammal in the world?", "Blue Whale"),
        new Question("Which organ in the human body is primarily responsible for detoxifying chemicals?", "Liver"),
        new Question("Which country is the second-largest by land area?", "Canada"),
        new Question("How many colors are there in a rainbow?", "7"),
        new Question("Which element does 'O' represent on the periodic table?", "Oxygen"),
        new Question("What is the hardest natural substance on Earth?", "Diamond"),
        new Question("Which planet is closest to the sun?", "Mercury"),
        new Question("What is the name of the longest river in the world?", "Nile"),
        new Question("Who painted the Mona Lisa?", "Leonardo da Vinci")
    );
    
    // Map to track client scores
    private static Map<String, Integer> scores = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Quiz Server started...");

        // Continuously accept clients and start a new thread for each one
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }
    }

    // Method to broadcast a message to all clients
    public static synchronized void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    // Method to update the scores for a specific client
    public static synchronized void updateScores(String username, int score) {
        scores.put(username, scores.getOrDefault(username, 0) + score);
    }

    // Method to generate the current leaderboard as a string
    public static synchronized String getLeaderboard() {
        StringBuilder leaderboard = new StringBuilder();
        leaderboard.append("Leaderboard:\n");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            leaderboard.append(entry.getKey()).append(": ").append(entry.getValue()).append(" points\n");
        }
        return leaderboard.toString();
    }

    // Method to determine and announce the winner
    public static synchronized String announceWinner() {
        String winner = "";
        int maxScore = -1;
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                winner = entry.getKey();
            }
        }
        return "The winner is " + winner + " with " + maxScore + " points!";
    }
}

