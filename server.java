import java.io.*;
import java.net.*;
import java.util.*;

class statistics implements Serializable{
    Integer I;
    String S;
    public statistics(){}
   public statistics(String str,Integer i){
    I=i;
    S=str;}
}

//this class extends thread class to enable multithreading
class ClientThread extends Thread {
    
    private Socket clientSocket;
     public static int counteroneoperation = 0;
        public static int countertwooperation = 0;
        public static int counterthreeoperation = 0;
        public static int counterfouroperation = 0;
        public static int conut0TO25 = 0;
        public static int conut25TO50 = 0;
        public static int conut50TO75 = 0;
        public static int conut75TO100 = 0;
        public static int conutof100 = 0;
        public static int sum = conut0TO25+conut25TO50+conut50TO75+conut75TO100+conutof100;
    
    //public constructor
    public ClientThread(Socket socket) {
        this.clientSocket = socket;
    }
    public static void Serialization (int conut0TO25,int conut25TO50,int conut50TO75,int conut75TO100,int conutof100 ,int sum ,int counteroneoperation,int countertwooperation,int counterthreeoperation,int counterfouroperation){
        try{
            FileOutputStream Fout=new FileOutputStream("STATISTICS.out");
            ObjectOutputStream oout = new ObjectOutputStream(Fout);
            //this file updates when any student terminated 
            oout.writeObject(new statistics("Number of Students who achieved [0 -25%) are :" , conut0TO25 ));
            oout.writeObject(new statistics("Number of Students who achieved [25-50%) are :" , conut25TO50));
            oout.writeObject(new statistics("Number of Students who achieved [50-75%) are :" , conut50TO75));
            oout.writeObject(new statistics("Number of Students who achieved [75-100%) are :" , conut75TO100));
            oout.writeObject(new statistics("Number of Students who achieved 100% are :" , conutof100 ));
            oout.writeObject(new statistics("Total number of quizzes performed by all students: ", sum));
            oout.writeObject(new statistics("The numbers of Questions performed that included one operation : " , counteroneoperation));
            oout.writeObject(new statistics("The numbers of Questions performed that included two operation : " , countertwooperation));
            oout.writeObject(new statistics("The numbers of Questions performed that included three operation : " , counterthreeoperation));
            oout.writeObject(new statistics("The numbers of Questions performed that included four operation : " , counterfouroperation));
        }
        catch (IOException i){}
    }
    public synchronized static void Deserialization (int v){
        statistics x;
        try {
            FileInputStream Fin=new FileInputStream("STATISTICS.out");
            ObjectInputStream oin = new ObjectInputStream(Fin);
            for(int i=1;i<=10;i++){
            try{
                    // Read the statistics object back from the file
                    Object obj = oin.readObject();
                    // Cast back to a Integer
                    x = (statistics) obj;
                   switch (i){
                        case 1: conut0TO25=x.I;
                            break;
                        case 2: conut25TO50=x.I;
                            break;
                        case 3: conut50TO75=x.I;
                            break;
                        case 4: conut75TO100=x.I;
                            break;
                        case 5: conutof100=x.I;
                            break;
                        case 6 : sum=x.I;
                            break;
                        case 7 : 
                            if (counteroneoperation==0)
                                counteroneoperation=x.I;
                            else 
                                    break;
                            break;
                        case 8 : if (countertwooperation==0)
                            countertwooperation=x.I;
                            else 
                                    break;
                            break;
                        case 9 : if (counterthreeoperation==0)
                            counterthreeoperation=x.I;
                            else 
                                    break;
                            break;
                        case 10 : if (counterfouroperation==0)
                            counterfouroperation=x.I;
                            else 
                                    break;
                            break;
                    } 
                }
                catch (ClassCastException | ClassNotFoundException   cce){
                    System.out.println("There is no statistics to serialized");
                }
            }}
                    catch(FileNotFoundException  io){
                    System.out.println("There is no statistics to serialized");}
                    catch(IOException io){
                    System.out.println("There is no statistics to serialized");}
        switch (v){
            case 1 :break;
            case 2 :
            System.out.println("Number of Students who achieved [0-25%) are :" + conut0TO25 );
            System.out.println("Number of Students who achieved [25-50%) are :" + conut25TO50 );
            System.out.println("Number of Students who achieved [50-75%) are :" + conut50TO75 );
            System.out.println("Number of Students who achieved [75-100%) are :" + conut75TO100 );
            System.out.println("Number of Students who achieved 100% are :" + conutof100 );
            System.out.println("Total number of quizzes performed by all students: "+sum);
            System.out.println("The numbers of quizzes performed that included one operation : " +counteroneoperation);
            System.out.println("The numbers of quizzes performed that included two operation : "+countertwooperation);
            System.out.println("The numbers of quizzes performed that included three operation : "+counterthreeoperation);
            System.out.println("The numbers of quizzes performed that included four operation : "+counterfouroperation);
                break;
        }}
    public synchronized static void NUMOFOPRATIONS(int opnum){
//        Deserialization (1);
        switch (opnum){
                case 1 :counteroneoperation+=1;break;
                case 2 :countertwooperation+=1;break;
                case 3 :counterthreeoperation+=1;break;
                case 4 :counterfouroperation+=1;break;
        }            
    }
    public synchronized static void FINALSTATISTIC(double FinalGrade){
        // assume the final grade was from 100%
            if (FinalGrade < 25)
                conut0TO25+=1;
            else if (FinalGrade >= 25 && FinalGrade < 50)
                conut25TO50+=1;
            else if (FinalGrade >= 50 && FinalGrade < 75)
                conut50TO75+=1;
            else if (FinalGrade >= 75 && FinalGrade < 100)
                conut75TO100+=1;
            else 
                conutof100+=1;
            sum = conut0TO25+conut25TO50+conut50TO75+conut75TO100+conutof100;
            Serialization ( conut0TO25, conut25TO50, conut50TO75, conut75TO100, conutof100 , sum , counteroneoperation, countertwooperation, counterthreeoperation, counterfouroperation);
}

    
    
    
    //run method ovveriden from thread class to serve our project
    @Override
    public void run() {
    	double finalgrade; 
        try {
            Deserialization (1);
            int counterofattempts=0;
            
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String start;
            //if user typed start the Quiz session will start
            while ((start = in.readLine()) != null) {
                if (start.equals("start")) {
                	finalgrade=Quiz( in, out);
                	FINALSTATISTIC(finalgrade);
                } 
            } 
            
            if (start=="start")
               counterofattempts=+1;
           else {
                       System.out.println("");}
            
        } catch (IOException e) {
            System.out.println("i/o error");
        }
        
    }
    
    public double Quiz(BufferedReader in,PrintWriter out) throws IOException {
            int numQuestions = Integer.parseInt(in.readLine());
            int timeout = Integer.parseInt(in.readLine());
            int maxOp = Integer.parseInt(in.readLine());
            int maxDigits = Integer.parseInt(in.readLine());
            //for the delay between each Question
            Thread t1=new Thread();
            //these arraylists for storing each Question and result and the key answer for results displaying
             ArrayList<String> Questions= new ArrayList<String>();
            ArrayList<Double> StudentAnswer= new ArrayList<Double>();
            ArrayList<Double> Key= new ArrayList<Double>();
            //to measure timeout
            StopWatch stopWatch = new StopWatch();
            int totalGrade = 0;
            
            for (int i = 0; i < numQuestions; i++) {
                //generate Questions according to user choices
                String question = generateQuestion( maxOp, maxDigits);
                out.println(question);
                //start the timer after sending the Question 
                stopWatch.start();
                Questions.add(question);
                
                    
                    String answer = in.readLine();
                    //stop timer after receivivg the answer
                    stopWatch.stop();
                    
                    //if timeout specified and time greater than timeout then the answer will not be taken and will be replaced by zero in the arraylist
                    if(timeout<=stopWatch.getElapsedTimeSecs() && timeout!=0){
                        answer="0"; 
                     double d=Double.parseDouble(answer);
                    StudentAnswer.add(d);
                    
                    //to find the key answer for each Question
                     double correctAnswer = keyAnswer(question);
                    Key.add(correctAnswer);
                    }
                    
                    else{
                     
                     double d=Double.parseDouble(answer);
                    StudentAnswer.add(d);
                    double correctAnswer = keyAnswer(question);
                    Key.add(correctAnswer);
                    if (d == correctAnswer) {
                                totalGrade++;
                    }
                    }
                    //delay between each Question 2sec
                try {
					t1.sleep(1000*2);
				} catch (InterruptedException e) {
				}
            }
            
            
    //sending report for user to include his/her performance + total grade 
            report( Key, StudentAnswer, Questions, numQuestions, totalGrade,  out);
        return ((double)totalGrade/numQuestions)*100;
    }

    //report Fuction takes the arraylists and compare the key answer with user answer and send them to client code
        public static void report(ArrayList<Double> key,ArrayList<Double> StudentAnswer,ArrayList<String> Questions, int numOfQuestions,int totalGrade, PrintWriter out){
            for(int i=0; i<numOfQuestions;i++){
                out.println("Question-"+(i+1)+"- :"+Questions.get(i));
                double x=key.get(i);
                double y=StudentAnswer.get(i);
                if(x==y){
                    out.println("your answer is correct :"+StudentAnswer.get(i));
                }
                else{
                    out.println("wrong answer ==>"+" true answer is : "+key.get(i)+ " your answer: "+StudentAnswer.get(i));
                }
            }
           out.println("Your total grade: " + ((double)totalGrade/numOfQuestions)*100+"%"); 
        }
        
        
       public static String generateQuestion(int maxOp, int maxDigits) {
        Random random = new Random();
            int x = random.nextInt(maxOp) + 1;
            //using string Builder here instead of String because it is mutable
            StringBuilder question = new StringBuilder();
            //generating numbers according to requested number of digits but we excluded ZERO to avoid division by zero error
            for (int i = 0; i <= x; i++) {
            	int max=(int) Math.pow(10, maxDigits)-1;
                int y = random.nextInt((max+1)-1)+1;
                question.append(y);
             //to assign operator before the last digit  
                if (i <= x - 1) {
                    char operator = "+-*/".charAt(random.nextInt(4));
                    question.append(operator);
                }
            }
            question.append('=');
            NUMOFOPRATIONS(x);
            return question.toString();
        }
//this function is taken from Stack overflow
        public static double keyAnswer(String expression) 
    { 
        char[] tokens = expression.toCharArray(); 
  
        // Stacks to store operands and operators 
        Stack<Double> values = new Stack<>(); 
        Stack<Character> operators = new Stack<>(); 
  
        // Iterate through each character in the expression 
        for (int i = 0; i < tokens.length; i++) { 
            if (tokens[i] == ' ') 
                continue; 
  
            // If the character is a digit or a decimal 
            // point, parse the number 
            if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.') { 
                
                StringBuilder sb = new StringBuilder(); 
                // Continue collecting digits and the 
                // decimal point to form a number 
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.')) { 
                           
                       
                    sb.append(tokens[i]); 
                    i++; 
                } 
                // Parse the collected number and push it to 
                // the values stack 
                values.push( Double.parseDouble(sb.toString())); 
                i--; // Decrement i to account for the extra 
                     // increment in the loop 
            } 
          
            
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') { 
                    
                    
                // If the character is an operator, pop and 
                // apply operators with higher precedence 
                while (!operators.isEmpty() && hasPrecedence(tokens[i], operators.peek())) { 
                       
                                        
                    values.push(applyOperator( operators.pop(), values.pop(),values.pop())); 
                         
                        
                } 
                // Push the current operator to the 
                // operators stack 
                operators.push(tokens[i]); 
            } 
        } 
  
        // Process any remaining operators in the stack 
        while (!operators.isEmpty()) { 
            values.push(applyOperator(operators.pop(),  values.pop(), values.pop())); 
                                     
                                      
        } 
  
        // The result is the only remaining element in the 
        // values stack 
        double va=values.pop();
        double result=Math.round(va*10.0)/10.0;
        return result ;
    } 
  
    // Function to check if operator1 has higher precedence 
    // than operator2 
    private static boolean hasPrecedence(char operator1, char operator2)
                                          
    { 
        if (operator2 == '(' || operator2 == ')') 
            return false; 
        return (operator1 != '*' && operator1 != '/') || (operator2 != '+' && operator2 != '-');
             
    } 
  
    // Function to apply the operator to two operands 
    private static double applyOperator(char operator,double b, double a) 
                                         
    { 
        switch (operator) { 
        case '+': 
            return a + b; 
        case '-': 
            return a - b; 
        case '*': 
            return a * b; 
        case '/': 
            if (b == 0) 
                throw new ArithmeticException( 
                    "Cannot divide by zero"); 
            return a / b; 
        } 
        return 0; 
    } 
  
    
}



//server code
public class server {
	public static void main(String[] args) {
		 Scanner s = new Scanner(System.in);
	
    try{
        //server socket
        ServerSocket socket = new  ServerSocket(18);
         System.out.print("Server Started");
         for(;;) {
        	 try {
                     //if there is no connection with server after 10m it displays this messege
        	 socket.setSoTimeout(1000*10*60);
                 //Multithreading
        	 Socket nextClient = socket.accept();
        	 System.out.print("Recieved request from "+nextClient.getInetAddress()+" "+nextClient.getPort());
                 //creating thread for each accepted client
        	 ClientThread CT= new ClientThread(nextClient);
        	 CT.start();
        	 
        	 }	
        	 catch (SocketTimeoutException ioe){
        			//timeoutinterr=true;
        			System.out.println("DO YOU WANT TO : "+"\n"+"1.Continue waiting"+"\n"+"2.Display statistics and terminate");
        			int c=s.nextInt();  
        			if(c==1) {
        				continue;
        			}
        			else if(c==2) {
        				//display statistcs (returning to the statistics method)
                                    ClientThread.Deserialization (2);
                                    System.out.println("The Server Terminated Successfully");
        				System.exit(0);
        			}
        			}
         }
}
    catch(BindException be){
   	 System.out.print("Unable to bind ports");
} 
    

    catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	} 
}
