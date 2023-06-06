
/*
In the Hand class, the maximum hand size possible is 11. A House rule is if you get 5 cards without going over, you automatically get a blackjack. The reason the hand and player is seperate is because they hand is only for blackjack. The Player can play several other games, and if they decide on blackjack, a hand will be created representing them. 
*/
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Hand {
  ////////////////////
  // PROPERTIES
  ////////////////////
  private boolean isDealer;
  private ArrayList<Card> cards = new ArrayList<Card>(); 
  private String name; // Based on player
  private Player player; // The player of the hand
  private int total;

  ////////////////////
  // CONSTRUCTOR
  ////////////////////

  public Hand(Player player) {
    this.player = player;
    this.name = player.getName();
    this.total = 0;
    // The cards in the hand 
    // Since we are now using ArrayList, we don't need to initialize the size
  }
  
  ////////////////////
  // METHODS
  ////////////////////
  
  /** Prints out all cards in the hand.*/
  public void printHand(){
    if(isDealer){
      System.out.println(Color.RED + "\nThe Dealer has " + Color.RESET);
    }
    else{
      System.out.println(Color.BLUE + "\nHere are your cards:" + Color.RESET);
    }
    for (int c = 0; c < cards.size(); c++) {
      System.out.println(cards.get(c));
    }
  }

  /** Adds a card to the hand, would need a deck to hit from.*/
  public void hit(Card dealt) {
    cards.add(dealt);
  }

  /**
  * Sums up the hand to check if it busts by going over 21. :(
  * @return Boolean indictaing whether the hand busted.
  */
  public boolean bust() {
    int tempTotal = 0;
    // Adds together all cards to get total
    for (int t = 0; t < cards.size(); t++){
      int value = cards.get(t).getNum();
      // Face cards are worth 10
      if(value > 10){ 
        value = 10;
      }
      tempTotal += value;
    }
    total = tempTotal;
    //Bad news
    if (tempTotal > 21){
      if(isDealer()){
        System.out.println(Color.RED + "The Dealer BUSTED!" + Color.RESET);
      }
      else{
        System.out.println(Color.BLUE + "You BUSTED!" + Color.RESET);
      }
      return true; // Indicates Bust
    }
    else{
      return false;
    }
  }

  /** Searches for an Ace and will increase its value.*/
  public void checkForAce(){
    for (int c = 0; c < cards.size(); c++){
      // Increases value of 1 to 11 if possible
      if (cards.get(c).getNum() == 1){
        cards.get(c).setNum(11); // Increases total
        total += 10; 
      }
    }
  }
  
  // GETTERS AND SETTERS
  public boolean isDealer() {
    return isDealer;
  }

  public void setDealer(boolean isDealer) {
    this.isDealer = isDealer;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void setCards(ArrayList<Card> cards) {
    this.cards = cards;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

}