import java.io.*;
import java.net.*;

public class QuizClient {
    public static void main(String[] args) {
        try {
            // Connect to the server on localhost, port 1234
            Socket socket = new Socket("localhost", 1234);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Read the welcome message from the server
            String serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);

            // Enter the username
            System.out.print("Enter your username: ");
            String username = userInput.readLine();
            out.println(username); // Send username to server
            System.out.println("Username sent to server: " + username);

            // Now read the server's response after sending the username
            serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);

            // Read and respond to quiz questions
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);

                // If the response contains a question, ask for user input
                if (serverResponse.contains("?")) {
                    String answer = userInput.readLine();
                    out.println(answer); // Send answer to server
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


