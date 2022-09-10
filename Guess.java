
/**
 * Write a description of class Guess here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Guess
{
    int[] pegs;
    int cp; //in correct place
    int cr; //in correct row, does not include cp

    public Guess(int[] pegs){
        this.pegs = pegs;
    }

    public Guess clone(){
        Guess forReturn = new Guess(this.pegs);
        forReturn.cp = this.cp;
        forReturn.cr = this.cr;
        return forReturn;
    }

    public void update(int[] solution){
        if(solution != null){
            int[] solDistribution = getDistribution(solution);
            int[] guessDistribution = getDistribution(this.pegs);
            int cp = 0;
            int cr = 0;
            for(int iter = 0; iter < solDistribution.length; iter++){
                cr = cr + getLower(solDistribution[iter], guessDistribution[iter]);
            }
            for(int iter = 0; iter < solution.length; iter++){
                if(solution[iter] == this.pegs[iter]){
                    cp++;
                    cr--;
                }
            }
            this.cp = cp;
            this.cr = cr; 
        }
    }

    public void print(){
        for(int iter = 0; iter < this.pegs.length; iter++){
            System.out.print(this.pegs[iter]);
        }
        System.out.println(" CP: " + this.cp + " CR: " + this.cr);
    }

    public static int getLower(int int1, int int2){
        if(int1 < int2){
            return int1;
        }
        else{
            return int2;
        }
    }

    public static int[] getDistribution(int[] reference){
        int[] forReturn = new int[7];
        for(int iter = 0; iter < reference.length; iter++){
            forReturn[reference[iter]]++;
        }
        return forReturn;
    }
}
