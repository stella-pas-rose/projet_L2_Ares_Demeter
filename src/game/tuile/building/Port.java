package game.tuile.building;
import game.Board;
import game.Player;
import game.tuile.Earth;
import game.tuile.Ressource;
import game.tuile.Sea;
import game.tuile.Tuile;
import game.util.*;
import java.util.HashMap;

public class Port extends Building{
    
    private static final String SYMBOL = "p";        //" ⚓ "

    /*
     * Build a Port on the given tile
     * @param tile the tile we want to build the port on
     */
    public Port(Earth tuile, Player player){
        super(tuile, player );
        this.symbol = SYMBOL;
        this.cost= new HashMap<>(); 
        this.cost.put(Ressource.WOOD,1);
        this.cost.put(Ressource.SHEEP,2);
    }
    
    public String toString(){
        return "Port " + this.tuile.getPosition(); 
    }

    /**
     * return true if the port can be placed at the given position , false otherwise
     * @param pos the position where we want to place it on 
     * @param board the board of the game
     * @return true if the port can be placed at the given position , false otherwise
     */
    public boolean canPlacePort(Position pos, Board board) {
        if (!(board.getTile(pos) instanceof Earth)) {
            return false;
        }
        for (Direction d : Direction.values()) {
            Position neighbor = pos.next(d);
            Tuile neighborTile = board.getTile(neighbor);
            if (neighborTile instanceof Sea && board.nbSeaTiles(pos) >= 2) {
                return true;
            }
        }
        return false;
    }

    /** 
     * returns the name of the building
     */
    public String getName(){
        return "Port";
    }


}
   




    
    

    
