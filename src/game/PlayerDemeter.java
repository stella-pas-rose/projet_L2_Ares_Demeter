package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.tuile.Earth;
import game.tuile.Ressource;
import game.tuile.building.*;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;
import game.listchooser.InteractiveListChooser;
import game.listchooser.ListChooser;
import game.listchooser.RandomListChooser; 
import game.action.*;
import game.action.actionDemeter.BuildFarm;
import game.action.actionDemeter.BuyThief;
import game.action.actionDemeter.ExchangeRessourcesPort;
import game.action.actionDemeter.PlayThief;
import game.action.actionDemeter.UpgradeFarm;

public class PlayerDemeter extends Player{

    private int points;
    private int nbThief;
    private List<Farm> farms;
    private List<Exploitation> exploitations;
    private List<Action<PlayerDemeter>> actionsDemeter;
    private int implementedIsland; 


    /** 
     * creates a demeter player with a name and number of points and number of thiefs that he has
     * @param name  name of the player
     */ 

    public PlayerDemeter(String name) {
            super(name);
            this.points = 0;
            this.nbThief = 0; 
            this.farms = new ArrayList<>();
            this.exploitations = new ArrayList<>();
            this.actionsDemeter = new ArrayList<>();
            this.implementedIsland= 0; 
    }


    /**
     * return the number of settled Islands
     * @return the number of settled Islands
     */
    public int getNbIsland(){
        return this.implementedIsland; 
    }

    /**
     * update the number of settled islands
     * @param n the number of island implemented
     */
    public void updateNbIsland(int n){
        this.implementedIsland = n; 
    }

    /**
     * gets the number of points that the demeter player has
     * @return the number of points
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * gets the number of thief that the demeter player has
     * @return the number of thief
     */
    public int getNbThief(){
        return this.nbThief;
    }

    /**
     * adds the given number of points to the demeter player
     * @param nb int number of points to add
     */
    public void addPoints(int nb){
        this.points += nb;
    }
    /**
     * adds a  thief to the  demeter player
     */
    public void addThief(){
        this.nbThief += 1 ;
    }

    public List<Action<PlayerDemeter>> getActionsDemeter() {
        return this.actionsDemeter;
    }
    

    /**
     * gets the farms of the demeter player
     * @return the farms of the demeter player
     */
    public List<Farm> getFarms(){
        return this.farms;
    }


    /**
     * remove a thief from the demeter player
     * 
     */
    public void removeThief(){
        if(this.nbThief > 0){
            this.nbThief -= 1; 
        }
        
    }

    /**
     * adds  a farm to the this demeter player
     * @param farm the farm to add
     */
    public void addFarm(Farm farm){
        this.farms.add(farm);
        this.playerTiles.add(farm.getTuile()); 
    }

    /**
     * removes a farm from the this player
     * @param farm the farm to remove
     */
    public void removeFarm(Farm farm){
        this.farms.remove(farm);
        this.playerTiles.remove(farm.getTuile()); 
    }


    /**
     * checks if the demeter player has a port in his tiles
     * @return boolean true if the this player has a port in his tiles
     */
    public boolean hasPort(){
        return !this.getPorts().isEmpty();
    }


    /**
     * gets the exploitations of the this  demeter player
     * @return the exploitations of the demeter player
     */
    public List<Exploitation> getExploitations(){
        return this.exploitations;
    }

    /**
     * adds an exploitation to the demeter player
     * @param exploitation the exploitation to add
     */
    public void addExploitation(Exploitation exploitation){
        this.exploitations.add(exploitation);
        this.playerTiles.add(exploitation.getTuile()); 
    }

    /**
     * collects all the ressources from the different buildings of this player 
     */
    public void collectRessources(){
        for ( Farm farm : this.farms){
            this.addRessource(farm.getTuileRessource(), 1);
        }
        for (Exploitation exploitation: this.exploitations){
            this.addRessource(exploitation.getTuileRessource(), 2);
        }
    }

    /**
     * fills the actionsDemeter attributes with all the actions for this game
     * if option is 0 the actions will be in interactive mode, random otherwise
     * @param board the board
     * @param option 0 for interactive, whatever bit zero for random
     * @param players the players
     */
    public void createActions(Board board, int option, List<PlayerDemeter> players){
        ListChooser<Earth> lcEarth= null; 
        ListChooser<Farm> lcFarm=null; 
        ListChooser<Ressource> lcRessource=null;  

        this.actionsDemeter.clear();
        if (this.actionsDemeter.isEmpty()) { 
            if (option==0){
                lcEarth= new InteractiveListChooser<>(); 
                lcFarm= new InteractiveListChooser<>();
                lcRessource= new InteractiveListChooser<>(); 
            }
            else{
                lcEarth= new RandomListChooser<>(); 
                lcFarm= new RandomListChooser<>();
                lcRessource= new RandomListChooser<>(); 
            }
            this.actionsDemeter = actionsPlayer(board, lcEarth, lcRessource, lcFarm, players);
        }

    }

    /**
     * excute the action of the demeter player
     * @param board the board
     * @param option if option is 0 then we create a interactive actions List, otherwise the actions will be automatic
     * @throws IOException exception
     * @throws InvalidChoiceException if the choice is not valid
     */
    public void act(Board board, int option) throws IOException, InvalidChoiceException {
        ListChooser<Action<PlayerDemeter>> lc= null;

        if (option==0){
            lc= new InteractiveListChooser<>(); 
        }
        else{
            lc= new RandomListChooser<>(); 
        }

        Action<PlayerDemeter> demeterAction = lc.choose("Choose an action", this.actionsDemeter);
        if (demeterAction != null) {
            try {
                demeterAction.act(this);
            } catch (NoMoreRessourcesException | CantBuildException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("No action chosen");
        }
    }

    /**
     * returns a list of actions that the demeter player can do
     * @param board
     * @return List<Action<PlayerDemeter>> the list of actions that the demeter player can do
     */
    private List<Action<PlayerDemeter>> actionsPlayer(Board board, ListChooser<Earth> lcEarth, ListChooser<Ressource> lcRessource, ListChooser<Farm> lcFarm, List<PlayerDemeter> players){
        List<Action<PlayerDemeter>> actionsDemeter = new ArrayList<>();

        BuildPort<PlayerDemeter> buildPort = new BuildPort<>(this, board, lcEarth);
        if (buildPort.hasEnoughRessources()) {
            actionsDemeter.add(buildPort);
        }

        // add possibles actions for player Demeter
        UpgradeFarm upgradeFarm = new UpgradeFarm(this, lcFarm);
        if (!this.getFarms().isEmpty() && upgradeFarm.hasEnoughRessources()) {
            actionsDemeter.add(upgradeFarm);
        }        

        ExchangeRessourcesPort exchangeRessourcesPort= new ExchangeRessourcesPort(this, lcRessource); 
        if (exchangeRessourcesPort.canExchange() && !this.getPorts().isEmpty()){
            actionsDemeter.add(exchangeRessourcesPort);
        }

        BuyThief buyThief = new BuyThief(this);
        if (buyThief.hasEnoughRessources()){
            actionsDemeter.add(buyThief);
        }

        if (this.getNbThief()  > 0){
            actionsDemeter.add(new PlayThief(lcRessource, players));
        } 

        BuildFarm buildFarm= new BuildFarm(board, this, lcEarth); 
        if (buildFarm.hasEnoughRessources()){
            actionsDemeter.add(buildFarm);
        }

        ExchangeRessources<PlayerDemeter> exchangeRessources= new ExchangeRessources<PlayerDemeter>(this, lcRessource); 
        if (exchangeRessources.canExchange()){
            actionsDemeter.add(exchangeRessources); 
        }
        
        return actionsDemeter;
    }

    /**
     * places a farm for this player on a random tile
     * @param board the board
     * @param lc the listchooser
     * @throws CantBuildException if you can't build
     * @throws NoMoreRessourcesException if you don't have enough ressources
     * @throws InvalidChoiceException if the choice is invalid
     */
    public void placeInitialFarm(Board board, ListChooser<Earth> lc) throws CantBuildException, NoMoreRessourcesException, InvalidChoiceException{
        BuildFarm bf= new BuildFarm(board, this, lc ); 
        bf.act(this);
    }


    /**
     * return the number of island where the player is implemented
     * @param board the board
     * @return the number of island where the player have at least one building
     */
    public int nbIslands(Board board) {
        int nbIsland= 0; 
        for (List<Earth> island : board.getIslands()) {
            boolean conqueer = false; 
            for (Earth tile : island) {
                if (this.getTiles().contains(tile) && !conqueer) {
                    nbIsland++ ; 
                    conqueer=true; 
                    break; 
                }
            }
        }
        return nbIsland;
    }



}