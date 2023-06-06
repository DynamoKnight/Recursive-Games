public class Main {
  public static void main(String[] args) {

    PlayerReader pr = new PlayerReader();
    new Session(pr);

    // Gets Player information from the file
    Player me = pr.getPlayer("1"); // Uses UUID
    System.out.println("Me's data: " + me.toString());

    // Gets Player information if in the file
    Player j = pr.getPlayer("john@email.com"); // Uses Email
    System.out.println("John's data: " + j.toString());
    
    // Prints all Players
    pr.printToScreen();
    
    //System.out.println("Hello world!");
  }
}