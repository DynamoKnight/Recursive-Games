/**
* The TTT class creates a game board that can be placed and played on for Tic-Tac-Toe. 
* The Size is able to change, it uses recursive methods to get the player input, it uses the best win checking method, applies javadoc comments, and has colors!
*/
// Epic: https://pokemon-cards.nakulkumar3.repl.co/CYO.html
import java.util.Scanner;
import java.util.ArrayList;
public class TTT extends Game{
  /////////////////
  // PROPERTIES
  /////////////////
  
  /*** The Dimension of the Tic-Tac-Toe Board. */
  private final int SIZE = 4;
  /*** The state of the TTT board. Follows the convention [Row][Column].*/
  private char state[][];
  /*** Keeps track of the number of total turns.*/
  private int turns; 
  /*** The User Input. */
  private Scanner user = new Scanner(System.in);

  /////////////////
  // CONSTRUCTOR
  /////////////////
  
  /**
  * @author Nakul Kumar
  * @author Aidan Coyne
  * @author Apurv Srirangapatnam
  */
  public TTT(){
    super();
    this.state = new char[SIZE][SIZE];
    this.turns = 0;
  }
  ///////////////
  // METHODS
  //////////////
  
  /*** The initial state of the board, using ASCII values to represent spots. */
  public void fill() {
    int count = 0; // The true position
    for (int i = 0; i < SIZE; i++) {
      for(int j = 0; j < SIZE; j++){
        state[i][j] = (char)(count+'0'); // ASCII VALUE
        count++;
      }
    }
  }
  
  /**
  * Starts a game of Tic-Tac-Toe with Players, Running the game until a Player wins or draws. 
  * @param player1 is the Player object that represents a player. 
  * @param player2 is also is a Player object that represents a player.
  */
  public void play(Player player1, Player player2) {
    // ASCII character order
    fill();    
    // Assigns the Players their token and color
    player1.setToken('X');
    player2.setToken('O');
    //player1.setColor(BLUE);
    //player2.setColor(RED);
    
    // The game begins until it ends
    boolean gameEnd = false;
    while (gameEnd == false) {
      gameEnd = move(player1);
      if(gameEnd == false){
        gameEnd = move(player2);
      }
    }
    draw(); // Final completed board
  }

  /**
  * Displays the game Board in a clear way for the Humans to see. 
  * Uses fancy coloring to enhance the experience!
  */
  public void draw() {
    System.out.println();
    for (char[] rows : state) {
      for (char x : rows) {
        if(x == 'X'){
          System.out.printf(" [" + Color.BLUE + " %c " + Color.RESET + "]", x);
        }
        else if(x == 'O'){
          System.out.printf("[" + Color.RED + " %c " + Color.RESET + "]", x);
        }
        else{
          System.out.printf("[ %c ]", x);
        }
        
      }
      System.out.println();
    }
  }

  /**
  * Gets the player input for an available spot and places it onto the Board. 
  * @param player is the Player object that represents a player. 
  * @return False if the didWin() method fails, True if the didWin() method is true. 
  */
  public boolean move(Player player) {
    // Ends the game if Draw...
    turns++;
    if(checkTie()){return true;}

    // Otherwise continues with game
    draw();
    int number = getInput(player);
    int row = number / SIZE;
    int column = number % SIZE;
    // Recursion
    if(number >= SIZE * SIZE || number < 0){
      System.out.println("Out of Bounds! Pick another Spot.");
      return move(player);
    }
    else if (state[row][column] == 'X' || state[row][column] == 'O') {
      System.out.println("Pick another spot.");
      return move(player);
    } 
    else {
      state[row][column] = player.getToken();
      return didWin(player, row, column);
    }
  }

  /**
  * Gets the ASCII value given by the Player and converts it into a number.
  * @return The number position of the ASCII value. 
  */
  public int getInput(Player player){
    // Player 1
    if(player.getToken() == 'X'){
      System.out.println(Color.BLUE + "\nWhich box do you want your token in? ("+ player.getToken() + "): " + Color.RESET);
    }
    // Player 2
    else{
      System.out.println(Color.RED + "\nWhich box do you want your token in? ("+ player.getToken() + "): " + Color.RESET);
    }
    
    char pos = user.next().charAt(0);
    int number = (int) pos - 48; // The ASCII position of numbers starts at 48 for 0
    return number;
  } 
  
  /**
  * Determines if the entire board has been filled.
  * @return True if the board is complete.
  */
  public boolean checkTie(){
    if(turns > SIZE * SIZE){
      System.out.println("\nThe Game ended in a Draw!");
      return true;
    }
    return false;
  }
  
  /**
  * Determines if the player won based on their last move.
  * @param player is the Player that is being checked.
  * @param rowIndex is the y position of the last move.
  * @param columnIndex is the x position of the last move.
  * @return False if neither works, True if either check is complete.
  */
  public boolean didWin(Player player, int rowIndex, int columnIndex) {
    char token = player.getToken();
    if (
      checkRow(token, rowIndex) || 
      checkColumn(token, columnIndex) || 
      checkDiagonals(token, rowIndex, columnIndex)){
      System.out.println("\nPlayer " + token + " wins!");
      player.plusScore(); // Win
      return true;
    }
    return false;
  }
  
  /**
  * Checks within the given row for a complete row or a breakage.
  * @return False if there is a breakage, True if the entire row never breaks.
  */
  private boolean checkRow(char token, int rowIndex) {
    for (int t = 0; t < SIZE; t++) {
      // Looks for first case where the token streak breaks
      if (state[rowIndex][t] != token) {
        return false;
      }
    }
    return true;
  }
  /**
  * Checks within the given column for a complete column or a breakage.
  * @return False if there is a breakage, True if the entire column never breaks.
  */
  private boolean checkColumn(char token, int columnIndex){
    for (int t = 0; t < SIZE; t++) {
      // Looks for first case where the token streak breaks
      if (state[t][columnIndex] != token) {
        return false;
      }
    }
    return true;
  }

  /**
  * Checks if either the sum of the last moves are SIZE - 1, or both moves are the same.
  * @return False if it is not on a diagonal, True only if either diagonal is controlled.
  */
  // Checks Both the Diagonals if they follow a mathematical pattern
  public boolean checkDiagonals(char token, int rowIndex, int columnIndex){
    if (rowIndex + columnIndex == SIZE - 1) {
      if (checkDiagonalRight(token)){
        return true;
      }
    }
    else if (rowIndex == columnIndex){
      if (checkDiagonalLeft(token)){
        return true;
      }
    }
    return false;
  }
  
  /**
  * Checks within the diagonal if it is complete. Checks from the Top Left to the Bottom Right, increases x and y by one until it reaches Bottom Right.
  * @return False if there is a breakage, True if the entire diagonal never breaks
  */
  private boolean checkDiagonalLeft(char token) {
    for (int t = 0; t < SIZE; t++) {
      if (state[t][t] != token) {
        return false;
      } 
    }
    return true; // Appeared all times
  }

  /**
  * Checks within the diagonal if it is complete. Checks from the Top Right to the Bottom Left, decreases x but increases y by one until it reaches Bottom Left.
  * @return False if there is a breakage, True if the entire diagonal never breaks
  */
  private boolean checkDiagonalRight(char token) {
    for (int t = 1; t <= SIZE; t++) {
      if (state[SIZE - t][t - 1] != token) {
        return false;
      }
    }
    return true;
  }
  
  //@Override
  public boolean didWin(){
    System.out.println("TTTTTTTTTTT");
    return super.didWin();
  }
  
}