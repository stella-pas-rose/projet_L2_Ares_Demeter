package game;

import java.lang.Integer;

/**
 * print a board of the size to the given parameters
 */
public class Livrable1 {

  /**
   * take 2 arguments for the size of the board and print it. 
   * @param args the size
   */
  public static void main(String[] args) {
    try { 
      
      if (args.length < 2) {
        System.out.println("you have to give positive setting");
        return;
      }

      int height = Integer.parseUnsignedInt(args[0]);
      int width = Integer.parseUnsignedInt(args[1]);

      if (height <= 0 || width <= 0) {
        System.out.println("dimensions must be positive");
        return;
      }

      Board b = new Board(height, width);
      b.display();

    } catch (NumberFormatException e) {
      System.out.println("");
    }
  }
}
