import java.util.ArrayList;
import java.util.Scanner;

public class Game{
  //////////////////
  // PROPERTIES
  /////////////////
  private ArrayList<Turn> turns;
  private int MAX = 5;
  /** In order to access the file*/
  private PlayerReader pr;
  /** List of current players*/
  private ArrayList<Player> players;
  
  //////////////////
  // CONSTRUCTOR
  /////////////////
  Game(){
    this.turns = new ArrayList<Turn>();
    this.pr = new PlayerReader();
    this.players = new ArrayList<Player>();
  }
  
  //////////////////
  // METHODS
  /////////////////
  
  public void play(){
    Turn t = new Turn();
    turns.add(t);
    if(didWin() 
    || didTie()){
      return;
    }
    else{
      play();
    }
  }

  // Checks if the Player Won
  public boolean didWin(){
    char win = move();
    // yes means end, no means continue
    return win == 'y'; 
  }
  
  // Checks if the Player reached the max turns
  public boolean didTie(){
    return turns.size() > MAX;
  }

  // Simulates one move (Move to Turn)
  public char move(){
    System.out.print("Did you Win?: ");
    char ans = new Scanner(System.in).next().charAt(0);
    return ans;
  }
  
  
}