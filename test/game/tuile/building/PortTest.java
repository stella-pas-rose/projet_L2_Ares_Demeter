package game.tuile.building;
import game.Board;
import game.tuile.Earth;
import game.tuile.Forest;
import game.tuile.Sea;
import game.util.*;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




public class PortTest{

  private Port port;
  private Board board;
  private Earth forest;
  private Position pos;
  private Position posbis;
  private Earth forestbis;



  @BeforeEach
  void setUp(){
    board= new Board(4,4);
    port = new Port(forest, null); 

    pos= new Position(0,0);
    posbis=new Position(2,2);
    
    forest= new Forest();
    forestbis=new Forest();
    
    board.put(forestbis,posbis);
    board.put(forest,pos);
}
 
  /*
   * Tests if a port can be placed when adjacent to at least 2 sea tiles
   */
  @Test
  void canPlacePortShoudReturnTrue(){
    board = new Board(4, 4);
        for (int x=0; x<4; x++){
            for (int y=0; y<4; y++){
                board.put(new Sea(), new Position(x, y));
                }
            }
    // port shoulf be placed at position (0,0) because it is surrounded by 2 sea tiles
    board.put(forest, pos);
    assertTrue(port.canPlacePort(pos, board));
    // port shoulf be placed at position (2,2) because it is surrounded by 4 sea tiles

    board.put(forestbis, posbis);
    assertTrue(port.canPlacePort(posbis, board));
  }


  
  /*
   * test if a port can not be placed on a tile when completly surrounded by earth tiles
   */
  @Test
  void canPlacePortShoudReturnFalse(){
    
    // placing earth tiles around the position (0,0) , 
    //so now the method canplaceport for this position should return false
    Forest forest2= new Forest();
    Forest forest3= new Forest();
    Forest forest4= new Forest();
    Position pos2 =new Position(0,1);
    Position pos3=new Position(1,0);
    Position pos4 =new Position(1,1);
    board.put(forest2,pos2);
    board.put(forest3,pos3);
    board.put(forest4,pos4);

    // port should not be allowed to be placed at position (0,0) because it is surrounded by 4 earth tiles
    assertFalse(port.canPlacePort(pos, board));




  }

}