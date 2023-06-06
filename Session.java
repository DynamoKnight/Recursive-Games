import java.util.ArrayList;
import java.util.Scanner;

public class Session{
  //////////////////
  // PROPERTIES
  /////////////////
  /** List of the Games in a Session*/
  private ArrayList<Game> games;
  /** In order to access the file*/
  private PlayerReader pr;
  /** Scanner*/
  private Scanner scn = new Scanner(System.in);
  /** List of the Players who are currently playing in the game. Gets reset for every new game*/
  ArrayList<Player> players;

  //////////////////
  // CONSTRUCTOR
  /////////////////
  // Unrecorded game (Yet to be implemented)
  Session(){
    this.scn = new Scanner(System.in);
  }
  
  Session(PlayerReader pr){
    this.games = new ArrayList<Game>();
    this.pr = pr;

    System.out.println(Color.GREEN + "WELCOME TO YOUR SESSION, Where you can play any game you like!" + Color.RESET);
    play();
  }
  //////////////////
  // METHODS
  /////////////////

  /** A recursive method that keeps playing the game until the session ends*/
  public void play(){
    players = new ArrayList<Player>();
    if(sessionEnd()){
      return;
    }
    // Edits all the players who have played
    for(Player player : players){
      pr.editPlayer(player); // Updates the File
    }
    play();
  }

  /**
  * Checks the choice of the Players. Adds a game to the list if played. 
  * @return Boolean indicating if the game is ending.
  */
  public boolean sessionEnd(){
    gameChoices();
    char ans = scn.next().charAt(0);
    if(ans == '1'){
      games.add(playBlackjack());
    }
    else if (ans == '2'){
      games.add(playTTT());
    }
    else if (ans == '3'){
      games.add(playGG());
    }
    else{
      return true;
    }
    return false;
  }

  /** 
  * Gets setup for playing Blackjack
  * @return The object of the Blackjack game played. 
  */
  
  public Game playBlackjack(){
    /** List of the money belonging to the Players*/
    ArrayList<Integer> moneys = new ArrayList<Integer>();
    
    Deck cards = new Deck(); //Entire deck of cards
    cards.shuffle();
    //cards.getCards(); //Prints all cards

    System.out.print("How many Players: ");
    int numPlayers = scn.nextInt();
    // Creates and sets Name of each Player
    for(int p = 0; p < numPlayers; p++){
      System.out.print(Color.RESET + "What is name of Player " + (p+1) + ": ");
      players.add(pr.createPlayer(scn.next()));
      moneys.add(0);
    }
    Blackjack bj = new Blackjack(cards, players, moneys);
    bj.play();
    return bj;
  }

  /** 
  * Gets setup for playing Tic-Tac-Toe.
  * @return The object of the Tic-tac-Toe game played. 
  */
  public Game playTTT(){
    TTT t = new TTT();
    System.out.print("Who is Player 1: ");
    Player player1 = pr.createPlayer(scn.next());
    System.out.print("Who is Player 2: ");
    Player player2 = pr.createPlayer(scn.next());
    players.add(player1);
    players.add(player2);
    
    t.play(player1, player2);
    return t;
  }

  /** 
  * Gets setup for playing the Guessing Game
  * @return The object of the GuessingGame game played. 
  */
  public Game playGG(){
    GuessingGame gg = new GuessingGame();
    System.out.print("Who is Player 1: ");
    Player player = pr.createPlayer(scn.next());
    players.add(player);
    gg.play(player);
    return gg;
  }

  /** Prints out the available game choices*/
  public void gameChoices(){
    System.out.println(Color.RESET + "\nWhat would you like to play?");
    System.out.println("1) Play Blackjack");
    System.out.println("2) Tic-Tac-Toe");
    System.out.println("3) Guessing Game");
    System.out.println(Color.red + "4) Superhero Bakesale" + Color.RESET);
    System.out.println(Color.yellow + "5) Done With Session" + Color.RESET);
    System.out.print("\nCHOICE: ");
  }
}