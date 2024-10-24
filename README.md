# -Distributed-Systems-Assignment-1

How to Run
Compile the Java Files: Open a terminal in the project directory and compile all the .java files:
javac QuizServer.java ClientHandler.java QuizClient.java Question.java

Run the Server: In the terminal, start the server:
java QuizServer

Run the Clients: Open separate terminal windows for each client, and run the following command in each terminal to connect a client to the server:
java QuizClient

Gameplay:
Each client will be prompted to enter a username.
Once the quiz starts, clients will receive a series of quiz questions.
After answering a question, clients will see immediate feedback (Correct/Incorrect).
After each round, the leaderboard will be updated and displayed to all clients.
Once all questions are answered, the final leaderboard and the winner will be announced.
