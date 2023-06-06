import java.util.*;
import java.io.*;
public interface PersistenceAPI{

  // Could have multiple players of the same name
  public Player getPlayer(String id);
  public File setFile(String path);
  public void printToScreen();
  public Player createPlayer(String name);
  public void editPlayer(Player player);
  public void deletePlayer(String id);

  public ArrayList<Player> getPlayers();
  public ArrayList<Player> getPlayersByName(String name);
  
}