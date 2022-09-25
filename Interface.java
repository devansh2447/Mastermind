
/**
 * Write a description of class Interface here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.util.Random;
import java.awt.Desktop;
import java.net.URI;
public class Interface
{
    public static String getPegResponse(){
        System.out.println("Enter the peg response for the computer's guess");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
    
    public static String returnString(String input){
        String[] reference = new String[] {"b", "w", "bw", "bb", "ww", "bbb", "bbw", "bww", "www", "bbbb", "wwww", "bwww", "bbww"};
        for(int iter = 0; iter < reference.length; iter++){
            if(input.equals(reference[iter])){
                return input;
            }
        }
        System.out.println("Invalid input, please try again.");
        return getPegResponse();
    }
    
    public static void init() throws java.io.IOException, java.net.URISyntaxException {
        Scanner scan = new Scanner(System.in);
        String input;
        System.out.println("Welcome to Mastermind.");
        System.out.println("Press \"i\" if you would wish to view the instructions (your web browser will be opened if you choose to do so).");
        System.out.println("If you are curious about the algorithm by the computer to solve the game, you can find it on the same page.");
        input = scan.nextLine();
        if(input.equalsIgnoreCase("i")){
            launchInstructions();
        }
        System.out.println("Note that CP refers to the number of pegs in the correct place, and CR refers to the number of pegs in the correct row except those already in the correct place.");
        checkHowToPlay(scan);
    }
    
    public static void checkHowToPlay(Scanner scan){
        String input;
        System.out.println("Enter 1 to solve it yourself, enter 2 to give your own solution, and enter 3 provide input to the computer with regard to the pegs in the correct place.");
        input = scan.nextLine();
        System.out.println();
        if(input.equals("1")){
            System.out.println("Generating game...");
            System.out.println();
            scan.close();
            play();
        }
        else if(input.equals("2")){
            System.out.println("Enter your solution here: ");
            Computer.solve(new Board(Computer.interpretNumber(getInput(scan))));
        }
        else if(input.equals("3")){
            System.out.println();
            scan.close();
            System.out.println();
            System.out.println("Enter your input in the following fashion: black pegs first and represented with a \"b\" and then white pegs, represented with a \"w\".");
            System.out.println("For example, bw is valid but wb is not.");
            System.out.println();
            Computer.solveWithUser(new Board());
        }
        else{
            System.out.println("Invalid input, going with default option of playing against computer...");
            System.out.println("Generating game...");
            System.out.println();
            scan.close();
            play();
        }
    }
    
    public static void launchInstructions() throws java.net.URISyntaxException, java.io.IOException {
        Desktop desktop =Desktop.getDesktop();
        desktop.browse(new URI("https://en.wikipedia.org/wiki/Mastermind_(board_game)#Gameplay_and_rules"));
    }
    
    public static void play(){
        System.out.println("Generating board...");
        Board board = getBoard();
        int input;
        Scanner scan = new Scanner(System.in);
        while(board.state.equals("play")){
            System.out.println();
            System.out.println("Enter your input here, invalid input will be not be accepted.");
            input = getInput(scan);
            board.addGuess(Computer.interpretNumber(input));
            board.print();
            System.out.println();
        }
        scan.close();
    }
    
    public static void continuePlay(Board board){
        if(board.state.equals("vict")){
            System.out.println("Looks like you guessed the solution!");
        }
        else if(board.state.equals("loss")){
            System.out.println("Looks like you could not guess the answer!");
            System.out.print("It was ");
            for(int iter = 0; iter < board.solution.length; iter++){
                System.out.print(board.solution[iter]);
            }
            System.out.println();
        }
        System.out.println();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter \"c\" to continue, and  anything else to exit.");
        if(scan.nextLine().equalsIgnoreCase("c")){
            checkHowToPlay(new Scanner(System.in));
        }
        else{
            System.out.flush();
            System.exit(0);
        }
    }
    
    public static int getInput(Scanner scan){
        int[] list = Computer.cropList(Computer.getInitialList());
        System.out.println();
        String forReturn = scan.nextLine();
        if(contains(list, forReturn)){
            System.out.println();
            return Integer.parseInt(forReturn);
        }
        else{
            System.out.println();
            return getInput(scan);
        }
    }
    
    public static boolean contains(int[] reference, String check){
        for(int iter = 0; iter < reference.length; iter++){
            if(check.equals(reference[iter] + "")){
                return true;
            }
        }
        return false;
    }
    
    public static Board getBoard(){
        int[] list = Computer.cropList(Computer.getInitialList());
        return new Board(Computer.interpretNumber(list[random(list.length) - 1]));
    }
    
    public static int random(int max){
        Random random = new Random();
        return random.nextInt(max + 1);
    }
}
