/*
In the Card class, it contains the unique properties of the card, to be created in a deck. When constructed, it can come with a unique id, which can be used to find its suite and number. 
*/
public class Card {
  ////////////////////
  // PROPERTIES
  ////////////////////
  private int num;
  private int suite;
  private String type; //suite name
  private String value; //number name
  private boolean color; //black or red
  private String art;
  private static String[] suites = {"Spades","Hearts","Diamonds","Clubs"};
  private static String[] specials = {"Ace","Jack","Queen","King"};
  ////////////////////
  // CONSTRUCTOR
  ////////////////////
  public Card() {

  }

  // Clever and Important
  public Card(int id) {
    this.suite = id / 13; // 0/1/2/3 (4 suites)
    this.num = (id % 13) + 1;  //1-13 (13 cards)
  }

  public Card(int num, int suite) {
    this.num = num;
    this.suite = suite;
  }

  ////////////////////
  // METHODS
  ////////////////////

  // GETTERS AND SETTERS
  /** Readable for whoever is looking.*/
  public String toString() {
    return getValue(num) + " of " + getType(suite); //Returns card name
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public int getSuite() {
    return suite;
  }

  public void setSuite(int suite) {
    this.suite = suite;
  }

  //Gets the actual suite name
  public String getType(int s) {
    type = suites[s];
    return type;
  }
  
  /** Gets the actual value of card.*/
  public String getValue(int n) {
    //Proper name of card
    if(n == 1){
      value = specials[0]; // Ace
    }
    else if(n > 10){ //12-10=2, index at 2 is Queen
      value = specials[n-10];
    }
    else{
      value = Integer.toString(n); //Converts int to String
    }
    return value;
  }

  public boolean isColor() {
    return color;
  }

  public void setColor(boolean color) {
    this.color = color;
  }

  public String getArt() {
    return art;
  }

  public void setArt(String art) {
    this.art = art;
  }
}