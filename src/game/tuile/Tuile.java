package game.tuile;
import game.util.*;

/**
 * a tile
 */
public abstract class Tuile {
    public String symbol ;// symbol of the tile
   
    protected Position pos;// to be able to get the position of the tile


    /**
     * Constructor of Tuile.
     *
     * @param symbol The symbol representing this tile.
     */    
    public Tuile(String symbol) {
        this.symbol =symbol ;
        this.pos = null;

    }
    /**
     * Gets the symbol of the tile.
     *
     * @return the symbol of the tile.
     */
    public String getSymbol() {
        return symbol;
    }   

    /**
     * return the position of the tile
     * @return the position
     */
    public Position getPosition(){
        return this.pos;

    }

     /**
      * set the position of the tile
      * @param position the position
      */
    public void setPosition(Position position) {
        this.pos = position;
    }

    /*
     * return if the tile have a building on it
     * @return true if it have a buiding, false otherwise
     */
    public boolean haveBuild(){
        return false;
    }


}
