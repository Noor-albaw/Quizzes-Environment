# Network Quizzes Environment

## Programming of Networks Protocol - Final Project  
**The University of Jordan**  
**Faculty of Engineering and Technology**  
**Department of Computer Engineering**  

Instructor: Eng. Asma Abdel Karim

### Project Overview
This project is a client/server network application implemented in Java using the TCP API. It provides a quiz environment for elementary school students to practice basic math operations (+, -, *, /). The server handles multiple client connections, allowing each student to complete a quiz. The difficulty level and number of questions are customizable by the student.

### Features
- **Client-Server Architecture**: Supports multiple clients, but only one quiz session per client at a time.
- **Math Quizzes**: The quiz consists of random math problems involving addition, subtraction, multiplication, and division. The student can set:
  - Number of questions.
  - Difficulty level, defined by the number of operations and number of digits in the numbers.
  - Optional timeout for each question.
- **Quiz Results**: The server sends the quiz result, showing correct and incorrect answers along with the total score.
- **Statistics Tracking**: The server keeps track of:
  1. Total quizzes performed.
  2. Distribution of quizzes based on the number of operations (1, 2, 3, or 4 operations).
  3. Student performance in ranges: [0-25%), [25%-50%), [50%-75%), [75%-100%), and 100%.
- **Idle Server Behavior**: If the server is idle (no client activity) for 10 minutes, the admin is prompted to either continue waiting or display statistics and terminate.

### How It Works

#### 1. **Server Side:**
- Listens for incoming client connections.
- For each client:
  - Handles the quiz process (question generation, answer validation).
  - Sends the quiz result to the client.
  - Updates the statistics.
- Idle for 10 minutes:
  - Prompts the admin to either continue or terminate and display statistics.

#### 2. **Client Side:**
- The client can:
  - Start a new quiz.
  - Terminate the connection.
- When starting a quiz:
  - The client specifies the number of questions.
  - Sets a timeout (optional) for answering questions.
  - Sets the difficulty level (number of operations and number of digits).
- Receives quiz results after completion.

