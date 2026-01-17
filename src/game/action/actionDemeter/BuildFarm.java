package game.action.actionDemeter;

import java.util.List;

import game.*;
import game.action.Action;
import game.action.ActionManager;
import game.tuile.*;
import game.tuile.building.*;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;
import game.util.Position;
import game.listchooser.ListChooser;

/*
 * This class is used to build a farm on a tile
 * It extends ActionManager and implements Action<PlayerDemeter>
 */
public class BuildFarm extends ActionManager <PlayerDemeter> implements Action<PlayerDemeter> {

    private Board board; 
    public ListChooser<Earth> lc;

    public BuildFarm(Board board, PlayerDemeter player, ListChooser<Earth> lc ) {
        super(player);  
        this.board= board;
        this.cost.put(Ressource.WOOD, 1);  
        this.cost.put(Ressource.ORE, 1);   
        this.lc = lc;
    }


    /**
    * @return the description of the action
    */
    public String toString(){
        return "Build a farm => cost: " + this.cost; 
    }

    public Earth askCoordinate() throws InvalidChoiceException {
        List<Earth> buildableTiles = this.board.buildableTiles();
        if (buildableTiles.isEmpty()) {
            throw new InvalidChoiceException("There are no buildable tiles available");
        }
        if (buildableTiles.size() == 1) {
            return buildableTiles.get(0);
        }
        Earth chosenTile = lc.choose("Where do you want to build a Farm ?", buildableTiles);
    
        if (chosenTile == null) {
            throw new InvalidChoiceException("Action cancelled. No farm was selected");
        }
        if (chosenTile.haveBuild()) {
            throw new InvalidChoiceException("This tile already has a building.");
        }
        return chosenTile;
    }
    

    /**
     * builds a farm on a tile for the given player
     * @param player the player who wants to build the farm
     * @throws NoMoreRessourcesException if the player doesn't have enough ressources to build the farm
     * @throws InvalidChoiceException
    */
    public void act(PlayerDemeter player) throws NoMoreRessourcesException, InvalidChoiceException {
        Position choosenPosition= askCoordinate().getPosition();
        Earth tile= (Earth) this.board.getTile(choosenPosition); 
        int numberofBuilding= player.getFarms().size() + player.getExploitations().size() +player.getPorts().size(); 
       
       
       
        //checks if player has enough ressources to build a farm
       
        if (numberofBuilding >=2){
            if (!this.hasEnoughRessources()){
                throw new NoMoreRessourcesException("You don't have enough ressources to build a farm" + this.cost);
            }
            this.removeRessources(); // removes the ressources from the player
        }

        // builds the farm on the tile the player wants to build on
        Farm farm = new Farm((Earth) tile, player);
        // sets the farm on the tile
        tile.setBuilding(farm);
        // adds the built tile to the player's list of tiles
        player.addFarm(farm);

        farm.collectRessource(player);
        
        player.addPoints(1); 
        
        System.out.println(player.getName() +": "+player.getResources()+ " build a farm on position "+ choosenPosition);
    }





}

   
