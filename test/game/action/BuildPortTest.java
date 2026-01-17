package game.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.PlayerAres;
import game.listchooser.FixedIndexChooser;

import game.tuile.Earth;
import game.tuile.Ressource;
import game.tuile.Sea;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;
import game.util.Position;

public class  BuildPortTest{

    private Board board; 
    private PlayerAres player; 
    private Action<PlayerAres> action; 

    @BeforeEach
    void setUp(){
        player = new PlayerAres("Marco");
        board= new Board(5, 5); 
        action= new BuildPort<PlayerAres>(player, board, new FixedIndexChooser<>(3)); 
       
       // giving the player enough ressources to be able to build a port
        player.addRessource(Ressource.WOOD, 1);
        player.addRessource(Ressource.SHEEP, 2);
    }

    @Test
    /**
     * tests if the position is valid to build a port on it
     */
    void isValidPositionTest(){

        //mettre une position dans board ,entouree de tuiles non-mer 
        board = new Board(5, 5);
        for (int x=0; x<5; x++){
            for (int y=0; y<5; y++){
                board.put(new Sea(), new Position(x, y));
                }
            }
        //mettre une tuile Earth au centre de 4 tuiles Earth
        Position pos = new Position(2, 2);
        board.put(new  Earth(Ressource.ORE,null), pos);

        Position pos1 = new Position(2, 1);
        board.put(new  Earth(Ressource.ORE,null), pos1);

        Position pos2 = new Position(1, 2);
        board.put(new  Earth(Ressource.ORE,null), pos2);

        Position pos3 = new Position(3, 2);
        board.put(new  Earth(Ressource.ORE,null), pos3);

        Position pos4 = new Position(2, 3);
        board.put(new  Earth(Ressource.ORE,null), pos4);

        assertFalse(((BuildPort<PlayerAres>) action).canPlacePort(pos, board));
        assertTrue(((BuildPort<PlayerAres>) action).canPlacePort(pos1, board));
        assertTrue(((BuildPort<PlayerAres>) action).canPlacePort(pos2, board));

       
        assertThrows(NullPointerException.class, () -> ((BuildPort<PlayerAres>) action).canPlacePort(null, board));
    }

    @Test
    void BuildTest() throws NoMoreRessourcesException, CantBuildException, IOException, InvalidChoiceException{
        assertTrue(player.getPorts().isEmpty());
        action.act(player);
        assertTrue(! player.getPorts().isEmpty());
    }





}