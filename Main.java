import java.util.Scanner;
import java.io.*;
class Main {
  public static void main(String[] args) throws IOException{
    File file = new File("Scenarios.txt");
    Scanner reader = new Scanner(file);
    String sSituation;
    sSituation = reader.nextLine();
    System.out.println(sSituation);
    Frame frame = new Frame();
  }
}