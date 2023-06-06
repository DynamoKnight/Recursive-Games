
/*
In the Deck class, it creates a series of 52 unique cards. Multiple methods can be used to shuffle or take a card from the deck. 
*/
import java.util.Random;
import java.util.ArrayList;

public class Deck {
  ////////////////////
  // PROPERTIES
  ////////////////////

  private ArrayList<Card> cards = new ArrayList<Card>();
  Random rand = new Random();

  ////////////////////
  // CONSTRUCTOR
  ////////////////////

  public Deck() {
    for (int c = 0; c < 52; c++) {
      cards.add(new Card(c)); // Makes a new Card
    }
  }

  ////////////////////
  // METHODS
  ////////////////////

  // Randomizes order of deck
  public void shuffle() {
    for (int i = 0; i < cards.size(); i++) { // Goes through every card
      int shuf = rand.nextInt(cards.size()); // Gets a random pos
      Card temp = cards.get(i); // Stores the ith card
      cards.set(i,cards.get(shuf)); // Swaps ith card
      cards.set(shuf,temp); // Swaps other card
    }
  }

  // Gives player a card
  // Removes card from list
  public Card deal() {
    Card card = cards.get(0); // Temporary storage
    cards.remove(0); // Gone but not forgotten
    return card;
  }

  // GETTERS AND SETTERS

  public void getCards() {
    System.out.println(cards.toString());
  }
  
  public void getCardsLength(){
    System.out.println(cards.size());
  }
}