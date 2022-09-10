
/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{
    public static void main(String[] args){
        System.out.println("Program written by Devansh, algorithm implemented for computer to solve is Donald Knuth's 5-move algorithm.");
        try{
            try{
                Interface.init();
            }
            catch(java.net.URISyntaxException urise){
                urise.printStackTrace();
            }
        }
        catch(java.io.IOException ioe){
            ioe.printStackTrace();
        }
    }
}
