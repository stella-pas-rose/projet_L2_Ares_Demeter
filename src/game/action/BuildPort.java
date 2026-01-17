package game.action;


import java.util.List;

import game.Board;
import game.Player;
import game.listchooser.ListChooser;
import game.tuile.*;
import game.tuile.building.*;
import game.util.*;

/*
 * 
 * builds a port on a tile
 * It extends ActionManager and implements Action<Player>
 */
public class BuildPort <T extends Player > extends ActionManager<T> implements Action<T>{

    private Board board; 
    public ListChooser<Earth> lc;

    public BuildPort(T player, Board board, ListChooser<Earth> lc){
        super(player); 
        this.board= board; 
        this.cost.put(Ressource.WOOD,1);
        this.cost.put(Ressource.SHEEP,2);
        this.lc= lc; 
    }


    /**
    * @return the description of the action
    */
    public String toString(){
        return "Build a port => cost: " + this.cost; 
    }

    /**
     * asks the player on which tile they want to build the port on
     * @return the tile chosen play the player to build the port on
     * @throws InvalidChoiceException if the choice is invalid
     */
    public Earth askCoordinate() throws InvalidChoiceException {
    List<Earth> options = this.board.coastalTiles();
    if (options.isEmpty()){
        throw new InvalidChoiceException("No tiles to build a port");
    }
    Earth chosen = lc.choose("Where do you want to build a Port?", options);
    if(chosen == null) {
        throw new InvalidChoiceException("Action cancelled. No port was selected");
    }
    if(chosen.haveBuild()){
        throw new InvalidChoiceException("This tile already has a building.");
    }
    return chosen;
}


    /**
     * returns true if the port can be placed on the given position , false otherwise
     * @param pos the position where we want to place the port on 
     * @param board the board of the game
     * @return true if the port can be placed on the given position , false otherwise
     */
    public boolean canPlacePort(Position pos, Board board) {
        if (!(board.getTile(pos) instanceof Earth)) {
            return false;
        }
        return board.nbSeaTiles(pos) >= 2;
    }

    /** builds a port for the given player player
     * @param player the player that wants to build a port
     * @throws NoMoreRessourcesException if the player doesnt have enough ressources to build a port
     */
    public void act(T player) throws NoMoreRessourcesException, InvalidChoiceException , CantBuildException{
        Earth choosenTile= askCoordinate();

        if (! this.hasEnoughRessources()) {
            throw new NoMoreRessourcesException("Not enough ressources to build a port.\n cost: "+this.cost);
        }
        if(!canPlacePort(choosenTile.getPosition(), board)){
            throw new CantBuildException("There should be at least two neighboring sea tiles.");
        }

        this.removeRessources();
        Port port = new Port(choosenTile, player);
        choosenTile.setBuilding(port);
        player.addPort(port);
        port.collectRessource(player);

        System.out.println(player.getName() +": "+player.getResources()+ " build a port on the position "+ choosenTile.getPosition());
        
    }
    
}
