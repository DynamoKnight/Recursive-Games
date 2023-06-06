import java.io.*;
import java.util.*;

public class PlayerReader implements PersistenceAPI{
  //////////////////
  // PROPERTIES
  /////////////////
  
  /*** The File that is being read from */
  private File playersFile;
  /*** Used to iterate over the file */
  private Scanner reader;
  /*** Scanner to get Player input*/
  private Scanner scn;
  /*** Keeps track of all players */
  private ArrayList<Player> players;
  /*** Used to write into the file */
  private PrintStream printer;
  /*** */
  private static final String HEADER = "uuid,name,password,email,score,money";
  
  //////////////////
  // CONSTRUCTOR
  /////////////////
  
  /*** Constructs the PlayerReader Object, which will assign the default file to be read from.*/
  public PlayerReader() {
    this.players = new ArrayList<Player>();
    this.playersFile = new File("Players.csv");
    init();
  }
  
  /*** Constructs the PlayerReader Object, which will assign the given file to be read from.*/
  public PlayerReader(String path) {
    this.players = new ArrayList<Player>();
    this.playersFile = new File(path);
    init();
  }
  
  //////////////////
  // METHODS
  /////////////////
  
  /**
  * Trys and Catches if the file given is found
  * @throws FileNotFoundException
  */
  public void init(){
    
    // Try this if File is Found
    try{
      this.reader = new Scanner(playersFile);
      //this.printer = new PrintStream(playersFile);
      this.read();
    }
    // Catch File Not Found and Throw it back
    catch(Exception e){
      System.out.println("I CAN THROW! " + e);
    }
  }
  
  /*** Reads the entire file and prints it out*/
  public void read(){
    
    // Initialized to skip first line
    String lineData = reader.nextLine();
    
    while(reader.hasNextLine()){
      lineData = reader.nextLine(); // Stores entire line
      String[] elements = lineData.split(",");
      players.add(new Player(elements[0], elements[1], elements[2], elements[3], elements[4], elements[5]));
    }
    reader.close();
  }

  /**Uses a PrintStream to update the csv file*/
  private void write(){
    // Attempts to print the new data into the file
    try{
      printer = new PrintStream(playersFile);
      // First line 
      printer.println(HEADER);
      for(Player p : players){
        printer.print(p.toString());
      }
    }
    catch(Exception e){
      System.out.println("I CAN THROW! " + e);
    }
  }

  /**
  * Sets the File and makes sure it exists.
  * @param path the name of the file.
  * @return the file extracted from the path.
  */
  public File setFile(String path){
    this.playersFile = new File(path);
    
    try{
      this.reader = new Scanner(playersFile);
    }
    // Catch File Not Found and Throw it back
    catch(Exception e){
      System.out.println("I CAN THROW! " + e);
    }
    
    return playersFile;
  }
  
  /**
  * Gets a specfic Player from the list of players.
  * @param id represents either the unique email or uuid of the Player.
  * @return Returns a Player object based on the id.
  */
  public Player getPlayer(String id){
    
    for(Player player : players){
      // Checks id the uuid matches
      if(player.getUUID().equals(id) || player.getEmail().equals(id)){
         return player;
      }
    }
    return null;
  }

  /**
  * Gets a specfic Player that either .
  * @param name represents the name of the Player.
  * @return Returns a Player object based on the id given.
  */
  public Player createPlayer(String name){
    scn = new Scanner(System.in);
    // Asks for id and password
    System.out.print("What is your ID?(UUID/Email): ");
    String id = scn.next();
    System.out.print("What is your password: ");
    String password = scn.next();

    // Checks if the id matches a Player and the password matches the id
    if(getPlayer(id) != null){
      Player player = getPlayer(id);
      if(password.equals(player.getPassword())){
        System.out.println(Color.green + "Successfully Logged in to " + name + "'s account! " + Color.RESET);
        scn.close();
        return player;
      }
      else{
        System.out.println(Color.red + "Incorrect id or password. " + Color.RESET);
        scn.close();
        return createPlayer(name); // Reattempts login
      }
    }
    // Asks to create a new Player or retry since the Player entered doesn't exist
    else{
      System.out.println(Color.red + "The id you entered does not exist." + Color.RESET);
      System.out.print("Would you like to Create a New Account?(y/n): ");
      if(scn.next().charAt(0) == 'y'){
        // A New Player is added to the list
        scn.close();
        return addPlayer(name); 
      }
      else{
        scn.close();
        return createPlayer(name);
      }
    }
    // Shouldn't reach here
  }
  
  /**
  * Creates a Player Object and adds it to the List. Gets player input for the email and password.
  * @param name is the name of the Player.
  * @return Returns the Player object created.
  */
  private Player addPlayer(String name){
    
    System.out.print("\n" + name + " is being created!");
    Player player = new Player(name);
    initPlayer(player);
    players.add(player);
    
    return player;
  }
  
  /**
  * Calls the different signature with the Player Object
  * @param id is either the email or uuid of the Player.
  */
  private void initPlayer(String id){
    initPlayer(getPlayer(id));
  }

  /**
  * Assigns an email and password to the Player.
  * @param player is the Player object.
  */
  private void initPlayer(Player player){
    Scanner userInput = new Scanner(System.in);
    
    System.out.print("\nEmail: ");
    player.setEmail(userInput.next());
    System.out.print("Password: ");
    player.setPassword(userInput.next());
    // Once the Player is edited, all the data is ready to be written into the file
    write();
  }

  /**
  * Updates the data of the Player.
  * @param player is the Player object.
  */
  public void editPlayer(Player player){
    write();
  }

  /**
  * Removes the Player from the list.
  * @param id represents either the email or uuid of the Player to remove.
  * @return Returns the removed Player just in case.
  */
  public void deletePlayer(String id){
    Player player = getPlayer(id);
    for(int p = 0; p < players.size(); p++){
      // Checks id the uuid matches
      if(player.getUUID().equals(id) || player.getEmail().equals(id)){
         players.remove(p);
        return;
      }
    }
  }

  /**
  * Gets the List of all the Players.
  * @return Returns the player list players.
  */
  public ArrayList<Player> getPlayers(){
    return players;
  }
  
  /**
  * Gets all the Players with the same name.
  * @param name is the common name of the Players.
  * @return An ArrayList of the Players that match.
  */
  public ArrayList<Player> getPlayersByName(String name){
    ArrayList<Player> namePlayers = new ArrayList<Player>();
    for(Player player : players){
      // Checks id the uuid matches
      if(player.getName().equals(name)){
        namePlayers.add(player);
      }
    }
    return namePlayers;
  }

  /*** Prints out the list of all Players*/
  public void printToScreen(){
    
    System.out.println();
    for(Player player : players){
      System.out.println(player.getName());
    }
  }
}