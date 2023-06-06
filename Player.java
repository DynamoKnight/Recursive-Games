import java.util.UUID;
import java.util.Scanner;
public class Player{
  //////////////////
  // PROPERTIES
  /////////////////

  // Stored Data
  private String uuid;
  private String name;
  private String password; // Bad Idea
  private String email;
  private int score;
  private int money;

  // Extraneous Data
  /** Represents the value of a Player in a game, such as Player 1 or Player X.*/
  private char token;
  /** Keeps track of the number of turns a Player has made.*/
  private int turns; 
  
  //////////////////
  // CONSTRUCTOR
  /////////////////
  
  Player(){
    this.uuid = UUID.randomUUID().toString();
  }
  
  Player(String name){
    this.name = name;
    this.password = "reset123";
    this.email = "";
    this.score = 0;
    this.uuid = UUID.randomUUID().toString();
    this.money = 0;
  }
  
  Player(String uuid, String name, String password, String email, String score, String money){
    this.uuid = uuid; // Creates a UUID object out of the string
    this.name = name;
    this.password = password;
    this.email = email;
    this.score = Integer.parseInt(score); // Converts to int
    this.money = Integer.parseInt(money);
  }
  //////////////////
  // METHODS
  /////////////////
  
  // GETTERS & SETTERS
  public String getUUID() {
  	return uuid;
  }
  
  public void setUUID(String uuid) {
  	this.uuid = uuid;
  }
  
  public String getName() {
  	return name;
  }
  
  public void setName(String name) {
  	this.name = name;
  }
  
  public String getPassword() {
  	return password;
  }
  
  public void setPassword(String password) {
  	this.password = password;
  }
  
  public String getEmail() {
  	return email;
  }
  
  public void setEmail(String email) {
  	this.email = email;
  }
  
  public int getScore() {
  	return score;
  }
  
  public void setScore(int score) {
  	this.score = score;
  }

  public void plusScore(){
    this.score += 1;
  }
  
  public int getMoney() {
  	return money;
  }
  
  public void setMoney(int money) {
  	this.money = money;
  }

  public char getToken() {
	 return token;
  }

  public void setToken(char token) {
  	this.token = token;
  }

  public int getTurns() {
	 return turns;
  }

  public void setTurns(int turns) {
  	this.turns = turns;
  }
  
  /**
  * The csv formatted information of a Player.
  * @return Returns the String containing player data
  */
  public String toString(){
    return uuid + "," + name + "," + password + "," + email + "," + score + "," + money + "\n";
  }
  
}