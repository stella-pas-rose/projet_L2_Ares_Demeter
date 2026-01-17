package game.action.actionAres;

import game.*;
import game.action.Action;
import game.action.ActionManager;
import game.tuile.*;
import game.tuile.building.*;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;
import game.util.Position;
import game.listchooser.ListChooser;

import java.io.IOException;
import java.util.*;

/*
 * Builds an army on the island
 */
public class BuildArmy extends ActionManager<PlayerAres> implements Action<PlayerAres> {
    private Board board;
    public ListChooser<Earth> lc;
    public List<Earth> earthList;
    private ListChooser<Integer> lcNumber;

    public BuildArmy(Board board, PlayerAres player, ListChooser<Earth> lc, ListChooser<Integer> lcNumber) {
        super(player);
        this.board = board;
        this.cost.put(Ressource.WOOD, 1);
        this.cost.put(Ressource.SHEEP, 1);
        this.cost.put(Ressource.WEALTH, 1);
        this.lc = lc;
        this.lcNumber = lcNumber;
        earthList = new ArrayList<>();

    }

    /**
     * @return the description of the action
     */
    public String toString() {
        return "Build a army => cost: " + this.cost;
    }

    /**
     * asks the player which tile they want to build on and returns the chosen tile
     * @return the tile chosen by the player to be built on
     */
    private Earth askCoordinate() throws InvalidChoiceException {
        List<Earth> buildableTiles = this.board.buildableTiles();
        if (buildableTiles.isEmpty()) {
            throw new InvalidChoiceException("There are no buildable tiles available");
        }
        Earth chosenTile = lc.choose("Where do you want to build an Army?", buildableTiles);
        if (chosenTile == null) {
            throw new InvalidChoiceException("Action cancelled : No tile selected");
        }
        return chosenTile;
    }

    /***
     * asks the player the number of warriors they want to assign to an army
     * @return the number of warriors chosen by the player
     * @throws InvalidChoiceException 
     * @throws NoMoreRessourcesException when the player doesnt have enough ressources to assign those new warriors
     */
    private int askNumberWarriors() throws InvalidChoiceException, NoMoreRessourcesException {
        List<Integer> options = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            options.add(i);
        }
        Integer chosen = lcNumber.choose("How many warriors do you want to assign to the army?", options);
        if (chosen == null) {
            throw new InvalidChoiceException("Action cancelled: No number of warriors selected");
        }
        if (this.player.getWarriors() < chosen) {
            throw new NoMoreRessourcesException("Player has not enough warriors to deploy in the new army");
        }
        return chosen;

    }

    /**
     * Checks if the player can build an army , which means if he has at least a
     * port and two building placed on an island
     * 
     * @param earth
     * @param player
     * @return boolean
     * @throws NoMoreRessourcesException 
     */
    public boolean canBuildArmy(Earth earth, PlayerAres player) throws NoMoreRessourcesException {
        int cptBuild = 0;
        int cptPort = 0;
        if (player.getArmies().size() <= 2) {
            return true;
        }

        if (player.getWarriors() < 1) {
            throw new NoMoreRessourcesException("You need at least 1 warrior to build an Army");
        }

        if (!this.hasEnoughRessources()) {
            throw new NoMoreRessourcesException("Not enough resources to build an Army");
        }


        List<Earth> island = board.getIsland(earth);
        for (Earth tuile : island) {
            if (tuile.haveBuild()) {

                cptBuild++;
            }
            if (tuile.getBuilding() instanceof Port) {
                cptPort++;
            }

        }
        
        return cptBuild >= 2 && cptPort >= 1;
    }

    /**
     * Builds  an army on the island
     * 
     * @param player the player that wants to build an army on an island
     * @throws NoMoreRessourcesException
     * @throws CantBuildException
     * @throws IOException
     */
    public void act(PlayerAres player) throws NoMoreRessourcesException, CantBuildException, InvalidChoiceException {
        Position choosenPosition = askCoordinate().getPosition();
        Earth tile = (Earth) this.board.getTile(choosenPosition);
        Integer warriors= null; 

        if (player.getArmies().size() < 2){
            warriors= 1; 
        }
        else{
            warriors = askNumberWarriors();
        }


        if (!canBuildArmy((Earth) tile, player)) {
            throw new CantBuildException("conditions not met to build an army here");
        }

        if(player.getArmies().size() >= 2){
            this.removeRessources();
        }
    
        Army army = new Army((Earth) tile, warriors, player);

        player.addArmy(army);
        tile.setBuilding(army);

        army.collectRessource(player);

        player.removeWarriors(warriors);

        System.out.println(player.getName() + ": " + player.getResources() + " build a army with"+ warriors +" warrior on position "
                + choosenPosition);

    }
}