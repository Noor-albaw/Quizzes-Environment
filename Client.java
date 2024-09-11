import java.io.*;
import java.util.*;
import java.net.*;
public class Client {
    public static final int SERVICE_PORT = 18;
    public static void main(String args[]) {
    try {
            //creating tcp socket 
            Socket clientSocket = new Socket(InetAddress.getLoopbackAddress(), SERVICE_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //creating printWriter to write the answer on socket and enabels auto flushing 
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            //to read from consol
            Scanner scanner = new Scanner(System.in);
            String userInput;
            while (true) {
                System.out.println("Enter '1' to start a new quiz or '2' to terminate:");
                userInput = scanner.nextLine();
        
                if (userInput.equals("1")) {
                    out.println("start");
                    //invoking start function to send data to the server
                    startQuiz( scanner, out, in);
                    
                    
                    
                } else if (userInput.equals("2")) {
                    out.println("terminate");
                    break;
                
                }
            }
            //terminate client socket after choosing option 2
            
            System.out.println("Connection terminated.");
            clientSocket.close();
    }
    catch (IOException ioe) {
            System.err.println("Error " + ioe);
        }
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
   public static void startQuiz(Scanner scanner,PrintWriter out,BufferedReader in) throws IOException {
       //this object used to count the timeout to display notification messege to user
       StopWatch stopwatch=new StopWatch();
       //sending number of questions to server
        System.out.print("Enter the number of questions: ");
        int numQuestions = scanner.nextInt();
        out.println(numQuestions);
        
       //sending timeout to server
        System.out.print("Specify a timeout for questions in seconds select 0 for no timeout: ");
        int timeout = scanner.nextInt();
        out.println(timeout); 
        

       
        
        //sending operations in questions to server and limiting the client with for operations +,-,*,/
        int maxOperations;
        while(true){
           System.out.print("Enter the maximum number of operations in questions (1-4): ");
                maxOperations = scanner.nextInt();
                if(maxOperations>=1 && maxOperations<=4){
                    out.println(maxOperations);  
                    break;
        }
                else{
                    System.out.println("INVALID");
                }
        }
   
        
        //sending maximum number of digit to server
        System.out.print("Enter the maximum number of digits in numbers : ");
        int maxDigits = scanner.nextInt();
        out.println(maxDigits);
        
        System.out.println("Please answer each question with one decimal digit (round if nessecary )");
        
        //iterating over questions to display them for user and then take the answer from the consol and send it back to server
        for (int i = 0; i < numQuestions; i++) {
            String question = in.readLine();
            System.out.println("Question " + (i + 1) + ": " + question);
            //start the stopwatch here to count timeout
            stopwatch.start();
            String answer = scanner.next();
            
             out.println(answer);
            stopwatch.stop();
            
            //check if timeout reached or not to notify the user
             if(timeout<=stopwatch.getElapsedTimeSecs() && timeout!=0){
                 System.out.println("the timeout is Reached");
             }
             
       
        }
        
        //receiving the results for each Question from server
        for (int i = 0; i < numQuestions; i++){
            //to receive Question
           String Q= in.readLine();
            System.out.println(Q);
            //to receive the results
           String overview= in.readLine();
            System.out.println(overview);
           
           
        }
        //receive user total grade out of 100%
        String result= in.readLine();
        System.out.println(result);
    
    }
   }

    


        
    

