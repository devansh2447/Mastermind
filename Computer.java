import java.util.Scanner;

/**
 * Write a description of class Computer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Computer
{
    public static void solve(Board board){
        int[] list = cropList(getInitialList());
        String response;
        int guess = 1122;
        int[] pegs = interpretNumber(guess);
        board.addGuess(pegs);
        board.print();
        System.out.println();
        response = generateString(board.guesses[board.guesses.length - 1]);
        list = purge(pegs, list, response);
        while(board.state.equals("play")){
            guess = minimax(list);
            pegs = interpretNumber(guess);
            board.addGuess(pegs);
            board.print();
            response = generateString(board.guesses[board.guesses.length - 1]);
            list = purge(pegs, list, response);
        }
        System.out.println("The computer found the solution in " + board.guesses.length + " guesses.");
        board.state = "play";
        Interface.continuePlay(board);
    }

    public static void invalid(Board board){
        System.out.println("Looks like you entered something invalid!");
        System.out.println("Enter your actual solution here.");
        int[] solution = convert(Interface.getInput(new Scanner(System.in)));
        int incorrect = getIncorrect(board, solution) + 1;
        System.out.println("Move number " + incorrect + " had incorrect information.");
        Interface.continuePlay(board);
    }
    
    public static int[] convert(int integer){
        String toConvert = integer + "";
        int[] forReturn = new int[toConvert.length()];
        for(int iter = 0; iter < forReturn.length; iter++){
            forReturn[iter] = Integer.parseInt(toConvert.substring(iter, iter + 1));
        }
        return forReturn;
    }

    public static void solveWithUser(Board board){
        int[] list = cropList(getInitialList());
        String response;
        int guess = 1122;
        System.out.println("Guess: " + guess);
        int[] pegs = interpretNumber(guess);
        board.addGuess(pegs);
        System.out.println();
        response = Interface.getPegResponse();
        board.guesses[board.guesses.length - 1].cp = getBlack(response);
        board.guesses[board.guesses.length - 1].cr = getWhite(response);
        board.print();
        list = purge(pegs, list, response);
        int iter = 0;
        while(board.state.equals("play") && iter < 6 && !response.equals("bbbb")){
            guess = minimax(list);
            if(guess == 0){
                invalid(board);
            }
            pegs = interpretNumber(guess);
            board.addGuess(pegs);
            System.out.println("Guess: " + guess);
            response = Interface.getPegResponse();
            board.guesses[board.guesses.length - 1].cp = getBlack(response);
            board.guesses[board.guesses.length - 1].cr = getWhite(response);
            board.print();
            list = purge(pegs, list, response);
            iter++;
        }
        if(iter > 6){
            invalid(board);
        }
    }
    
    public static int getIncorrect(Board board, int[] solution){
        String original;
        String testResponse;
        for(int iter = 0; iter < board.guesses.length; iter++){
            original = generateString(board.guesses[iter]);
            board.guesses[iter].update(solution);
            testResponse = generateString(board.guesses[iter]);
            if(!original.equals(testResponse)){
                return iter;
            }
        }
        return -1;
    }

    public static int getBlack(String reference){
        int forReturn = 0;
        for(int iter = 0; iter < reference.length(); iter++){
            if(reference.charAt(iter) == 'b'){
                forReturn++;
            }
        }
        return forReturn;
    }

    public static int getWhite(String reference){
        int forReturn = 0;
        for(int iter = 0; iter < reference.length(); iter++){
            if(reference.charAt(iter) == 'w'){
                forReturn++;
            }
        }
        return forReturn;
    }

    public static void test(){
        int[] list = cropList(getInitialList());
        Board board;
        int highest = 0;
        for(int iter = 0; iter < list.length; iter++){
            board = new Board(interpretNumber(list[iter]));
            solve(board);
            if(board.guesses.length > highest){
                highest = board.guesses.length;
            }
        }
        System.out.println(highest);
    }

    public static int minimax(int[] reference){
        if(reference.length == 0){
            return 0;
        }
        int forReturn = reference[0];
        int least = reference.length;
        int score;
        String[] responseList = new String[reference.length];
        for(int iter = 0; iter < reference.length; iter++){
            score = getScore(reference, reference[iter]);
            if(score < least){
                least = score;
                forReturn =  reference[iter];
            }
        }
        return forReturn;
    }

    public static String[] getResponseList(int[] reference, int check){
        String[] responseList = new String[reference.length];
        Guess guess;
        for(int iter = 0; iter < responseList.length; iter++){
            guess = new Guess(interpretNumber(check));
            guess.update(interpretNumber(reference[iter]));
            responseList[iter] = generateString(guess);
        }
        return responseList;
    }

    public static int getScore(int[] reference, int check){
        String[] numReference = new String[] {"b", "w", "bw", "bb", "ww", "bbb", "bbw", "bww", "www", "bbbb", "wwww", "bwww", "bbww"};
        int[] distribution = new int[numReference.length];
        String[] responseList = getResponseList(reference, check);
        for(int iter = 0; iter < responseList.length; iter++){
            distribution[getIndex(numReference, responseList[iter])]++;
        }
        return getLargest(distribution);
    }

    public static int getLargest(int[] reference){
        int largest = reference[0];
        for(int iter = 0; iter < reference.length; iter++){
            if(reference[iter] > largest){
                largest = reference[iter];
            }
        }
        return largest;
    }

    public static int getIndex(String[] reference, String check){
        for(int iter = 0; iter < reference.length; iter++){
            if(check.equals(reference[iter])){
                return iter;
            }
        }
        return 0;
    }

    public static int[] purge(int[] guess, int[] reference, String ignore){
        String response;
        Guess check;
        for(int iter = 0; iter < reference.length; iter++){
            check = new Guess(guess);
            check.update(interpretNumber(reference[iter]));
            response = generateString(check);
            if(!response.equals(ignore)){
                reference = removeElement(reference, iter);
                iter--;
            }
        }
        return reference;
    }

    public static String generateString(Guess guess){
        String forReturn = "";
        for(int iter = 0; iter < guess.cp; iter++){
            forReturn = forReturn + "b";
        }
        for(int iter = 0; iter < guess.cr; iter++){
            forReturn = forReturn + "w";
        }
        return forReturn;
    }

    public static int[] removeElement(int[] array, int remove){
        int[] forReturn = new int[array.length - 1];
        int subtract = 0;
        for(int iter = 0; iter < array.length; iter++){
            if(iter != remove){ 
                forReturn[iter - subtract] = array[iter];
            }
            else{
                subtract = 1;
            }
        }
        return forReturn;
    }

    public static int[] interpretNumber(int interpretInt){
        String interpret = interpretInt + "";
        int[] forReturn = new int[interpret.length()];
        for(int iter = 0; iter < forReturn.length; iter++){
            forReturn[iter] = Integer.parseInt(interpret.charAt(iter) + "");
        }
        return forReturn;
    }

    public static int[] add(int[] array, int add){
        int[] forReturn = new int[array.length + 1];
        for(int iter = 0; iter < array.length; iter++){
            forReturn[iter] = array[iter];
        }
        forReturn[array.length] = add;
        return forReturn;
    }

    public static int[] getInitialList(){
        int[] forReturn = new int[7778];
        for(int iter = 0; iter < forReturn.length; iter++){
            forReturn[iter] = 1111 + iter;
        }
        return forReturn;
    }

    public static int[] cropList(int[] crop){
        for(int iter = 0; iter < crop.length; iter++){
            if(!isValid(crop[iter])){
                crop = removeElement(crop, iter);
                iter--;
            }
        }
        return crop;
    }

    public static boolean isValid(int check){
        String checkString = check + "";
        if(checkString.contains("0") || checkString.contains("9") || checkString.contains("7") || checkString.contains("8")){
            return false;
        }
        else{
            return true;
        }
    }
}
