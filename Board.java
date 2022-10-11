
/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Board
{
    Guess[] guesses;
    int[] solution;
    String state;
    
    //constructor to get Board object with solution array
    public Board(int[] solution){
        this.solution = solution;
        this.guesses = new Guess[0];
        this.state = "play";
    }
    
    //constructor for board without solution array
    public Board(){
        this.guesses = new Guess[0];
        this.state = "play";
    }
    
    //adds guess to a board, updates state of required
    public void addGuess(int[] add){
        Guess guess = new Guess(add);
        guess.update(this.solution);
        this.guesses = add(guess, guesses);
        if(this.guesses.length == 12){
            this.state = "loss";
        }
        if(guess.cp == 4){
            this.state = "vict";
        }
    }
    
    //adds guess to a full guess array
    public static Guess[] add(Guess add, Guess[] addTo){
        Guess[] forReturn = new Guess[addTo.length + 1];
        for(int iter = 0; iter < addTo.length; iter++){
            forReturn[iter] = addTo[iter].clone();
        }
        forReturn[addTo.length] = add;
        return forReturn;
    }
    
    //prints a board object
    public void print(){
        System.out.println();
        for(int iter = 0; iter < 12; iter++){
            if(iter < this.guesses.length){
                this.guesses[iter].print();
            }
            else{
                System.out.println("____");
            }
        }
        System.out.println();
    }
}
