/*
In the Game class, it takes a deck and simulates the game.

BLACK JACK RULES:
1) Dealer deals 2 cards per person, everyone shows one of his cards.
2) If you Hit: Draw a card that must not go over 21, but get closest.
3) If you Stand: You are confident with deck.
4) Always be able to bet.
5) After everyone Stands, reveal cards and find winner.
6) If Dealer has best deck, they win. If you have best deck, you win. If you push(tie), then dealer wins. 
7) If Dealer busts(goes over), everyone wins. Dealer has to hit when below 16.
8) Aces are worth 1 or 11, All face cards are worth 10
*/
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Blackjack extends Game{
  ////////////////////
  // PROPERTIES
  ////////////////////
  private ArrayList<Player> players = new ArrayList<Player>(); 
  private ArrayList<Integer> moneys = new ArrayList<Integer>(); 
  private ArrayList<Hand> hands = new ArrayList<Hand>(); 
  private Scanner user = new Scanner(System.in);
  private Deck deck;
  private Hand dealer;
  
  ////////////////////
  // CONSTRUCTOR
  ////////////////////

  public Blackjack(Deck deck, ArrayList<Player> players, ArrayList<Integer> moneys) {
    super();
    this.deck = deck;
    this.players = players;
    this.moneys = moneys;

    // Creates a hand for each player
    for (int h = 0; h < players.size(); h++) {
      hands.add(new Hand(players.get(h))); // Searches for data with the same name

      
      
      // Takes wager of each Player
      System.out.print(Color.RESET + "How much money is " + hands.get(h).getName() + " betting?: " + Color.GREEN);
      moneys.set(h,user.nextInt());
    }
    // The dealer is born!
    dealer = new Hand(new Player("The Dealer"));
    dealer.setDealer(true); //Signifies the hand is a dealer
  }

  ////////////////////
  // METHODS
  ////////////////////
  
  /** Goes through every step and plays the game.*/
  public void play(){
    clearScreen(); // Just google it
    dealer.hit(deck.deal()); //Revealed dealer card
    
    // Deals 2 cards for each Player
    for (int h = 0; h < hands.size(); h++) {
      System.out.println(Color.BLUE + "\nIt is " + hands.get(h).getName() + "'s turn." + Color.RESET);
      System.out.print("\nREADY? "); // To reveal cards privately
      user.next(); // Doesn't matter what player types

      hands.get(h).hit(deck.deal());
      hands.get(h).hit(deck.deal());
      hands.get(h).printHand(); // Shows avaliable cards

      System.out.print("\nCONTINUE? ");
      user.next();
      clearScreen();
    }

    // Gets Input of each player to commence turn
    for (int h = 0; h < hands.size(); h++) {
      playerTurn(hands.get(h));
      clearScreen();
    }
    
    // Check for Aces
    // It would first find Ace through iteration, and if the sum is less than 11, than it would add 10.
    for (int h = 0; h < hands.size(); h++){
      if (hands.get(h).getTotal() <= 11){
        hands.get(h).checkForAce();
      }
    }
    
    // Compares everyones sums
    int bestPos = bestHand();
    Hand bestHand;
    if(bestPos == -1){
      bestHand = null;
    }
    else{
      bestHand = hands.get(bestPos);
    }
    
    
    // Time to compare with the DEALER
    // Dealer has to keep hitting
    while (dealer.getTotal() < 16){
      dealer.hit(deck.deal());
      dealer.bust(); // Checks if they went over
    }
    // Outputs final results
    int win = printResults(bestHand, bestPos); // Does comparisons
    earnings(win);

    System.out.print("\nCONTINUE? ");
    user.next();
  }

  // Gets choice from User to continue
  public void playerTurn(Hand guy){
    dealer.printHand();
    guy.printHand();
    int choice = 0;
    
    boolean over = false; // Represents a Bust
    
    while(choice != 2 && over == false){
      // Keep going until Player stands or busts
      System.out.println("\nAlright " + guy.getName() + ", what would you like to do?");
      System.out.print("1) Hit \n2) Stand \n3) Cry :( \nChoice: ");
      choice = user.nextInt();
  
      if(choice == 1){ // Hit
        guy.hit(deck.deal());
        guy.printHand();
        over = guy.bust();
      }
      // Little Cheat command
      else if (choice == 357){
        deck.getCards();
      }
      else if (choice == 10){
        deck.getCardsLength();
      }
      // Every other choice is Stand
    }
    guy.bust(); // Calculates total
    System.out.print("\nCONTINUE? ");
    user.next();
    clearScreen();
    System.out.println("OVER HERE!");
  }

  /**
  * Finds the highest totaled hand from the Players.
  * @return The index position of the best Hand.
  */
  public int bestHand(){
    Hand guy = hands.get(0); // Starts at 0th
    int pos = 0;
    for (int h = 1; h < hands.size(); h++) {
      //Greatest hand but not over 21
      if(hands.get(h).getTotal() > guy.getTotal() && hands.get(h).getTotal() <= 21 || guy.getTotal() > 21){
        guy = hands.get(h);
        pos = h;
      }
    }
    // Makes sure there isn't a tie
    for (int i = 0; i < hands.size(); i++){
      if(hands.get(i).getTotal() == guy.getTotal() && i != pos){
        pos = -1;
        break;
      }
    }
    return pos;
  }

  /**
  * Prints out winner and returns to calculate their winnings.
  * @param bestHand is the object of the highest totaled player. 
  * @param bestPos is the position of that Hand in the list.
  * @return The Index position indicating either a winning player, dealer, or everyone. 
  */
  public int printResults(Hand bestHand, int bestPos){
    // Outputs the total points of everyone
    for (int h = 0; h < hands.size(); h++){
      Hand hand = hands.get(h); // For simplicity
      if(hand.getTotal() > 21){
        System.out.println(Color.red + hand.getName() + " has a total of " + hand.getTotal() + ". They BUSTED!" + Color.RESET);
      }
      else if (hand.getTotal() == 21){
        System.out.println(Color.GREEN + hand.getName() + " has a total of " + hand.getTotal() + ". They have a BLACKJACK!" + Color.RESET);
      }
      else {
        System.out.println(hand.getName() + " has a total of " + hand.getTotal() + ".");
      }
      System.out.println(hand.getName() + " has " + hand.getCards() + "\n");
    }
    // Dealers outcome
    dealer.printHand();
    System.out.println("\n" + dealer.getName() + " has a total of " + dealer.getTotal() + ".");

    // Final Results
    if(dealer.getTotal() > 21){
      System.out.println(Color.GREEN + "\nEverybody wins! Take your money back" + Color.RESET);
      return -1;
    }
    // Tie means Dealer wins
    else if (bestPos == -1){
      System.out.println(Color.GREEN + "\nNobody wins! There was a tie" + Color.RESET);
      return -1;
    }
    // If everyone is over 21, it will default to hand 0, so also check again
    else if(dealer.getTotal() > bestHand.getTotal() || bestHand.getTotal() > 21){
      System.out.println(Color.RED + "\nThe Dealer wins..." + Color.RESET);
      return -2;
    }
    else{
      System.out.println(Color.BLUE + "\n" + bestHand.getName() + " wins!" + Color.RESET);
      players.get(bestPos).plusScore(); // Increases win count
      return bestPos; 
    }
  }

  /**
  * Summarizes up earnings for winner.
  * @param winPos is the Index position of the best Hand in the list.
  */
  public void earnings(int winPos){
    // Everyone wins
    if(winPos == -1){
      for(int p = 0; p < hands.size(); p++){
        moneys.set(p,0);
      }
      return; // No loss no win
    }
    // Dealer dub
    else if(winPos == -2){
      for(int p = 0; p < players.size(); p++){
        players.get(p).setMoney(players.get(p).getMoney() - moneys.get(p));
        System.out.println(players.get(p).getMoney());
        moneys.set(p,0); // Adds the money from each Hand to the dealer
      }
    }
    // Player victory royale
    else{
      Player winner = players.get(winPos);
      int earnings = 0;
      for(int p = 0; p < players.size(); p++){
        // Dont add player's own bet
        if(p != winPos){
          earnings += moneys.get(p); // Adds up all the earnings
          players.get(p).setMoney(players.get(p).getMoney() - moneys.get(p)); // Lost how much they put in
          System.out.println(players.get(p).getMoney());
          moneys.set(p,0); // Resets money for each player
        }
      }
      System.out.println(winner.getName() + " gains a total of " + earnings);
      winner.setMoney(winner.getMoney() + earnings); // Adds their earnings
    }
  }
  
  /** Clears console lines.*/
  public static void clearScreen() {
    System.out.print("\033[H\033[2J"); // ANSI escape code
    System.out.flush();
  }

  /** Super method.*/
  public boolean didWin(){
    System.out.println("Why so Serious?");
    return super.didWin();
  }

  // GETTERS AND SETTERS

}