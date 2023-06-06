import java.util.Scanner;
import java.util.Random;

public class GuessingGame extends Game{
  ////////////////////
  // PROPERTIES
  ////////////////////
  // ALL print statements
  private static String[] goals;
  public final static String think = "%nI'm thinking of a number between 1 and 100...%n";
  public final static String ask1 = "Your guess? ";
  public final static String ask2 = "It's lower.";
  public final static String ask3 = "It's higher.";
  public final static String right = "You got it right in %d guesses!";
  public final static String ask4 = "Do you want to play again? ";

  /** The total number of Guesses made.*/
  private int guesses = 0;
  /** Used to get Player Input.*/
  private static Scanner user = new Scanner(System.in);
  /** Used to generate a random number.*/
  private Random randy = new Random();
  
  public GuessingGame(){
    super();
    this.guesses = 0; // Total guesses
  }

  public void play(Player player){
    int goal = randy.nextInt(0,101);
    int guess = 101; // What the Player guesses 
    int count = 0; // Number of guesses
    System.out.printf(think);
    //System.out.println(goal); // FOR TESTING

    playerGuess(goal, count);
    player.plusScore();
  }

  
  // Gets a users guess and returns if it is right
  public static void playerGuess(int goal, int count){
    System.out.print(ask1);
    int guess = user.nextInt();
    
    if(guess == goal){
      System.out.printf(right, count);
      System.out.printf("%n","/n");
      return;
    }
    else if(guess < goal){
      System.out.println(ask3);
    }
    else{ //Greater
      System.out.println(ask2);
    }
    playerGuess(goal, count + 1);
  }

  //Returns guesses
  public int getGuesses(){
    return guesses;
  }
  
}