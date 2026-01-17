package game;

import java.io.IOException;
import java.util.*;

import game.AresGameObjectives.ObjectiveType;
import game.action.*;
import game.action.actionAres.AttackNeighboor;
import game.action.actionAres.BuildArmy;
import game.action.actionAres.BuySecretWeapon;
import game.action.actionAres.BuyWarriors;
import game.action.actionAres.DisplayWarriors;
import game.action.actionAres.UpgradeWithRessources;
import game.action.actionAres.UpgradeWithWarriors;
import game.tuile.Earth;
import game.tuile.Ressource;
import game.tuile.building.Army;
import game.tuile.building.Camp;
import game.util.*;
import game.listchooser.*;

public class PlayerAres extends Player {

    private int warriors;
    private int secretWeapon;
    private List<Army> armies;
    private List<Camp> camps;
    private List<Action<PlayerAres>> actionsAres;
    private AresGameObjectives objective;

    // initializes a new PlayerAres with 30 warriors, a name, and zero secret
    // weapons
    public PlayerAres(String name) {
        super(name);
        this.warriors = 30;
        this.secretWeapon = 0;
        this.armies = new ArrayList<>();
        this.camps = new ArrayList<>();
        this.actionsAres = new ArrayList<>();
        this.objective = null;
    }

    /**
     * adds warriors to the army of this player
     * 
     * @param nb number of warriors to add
     */
    public void addWarriors(int nb) {
        this.warriors += nb;
    }

    /**
     * removes nb warriors from this player's army
     * 
     * @param nb number of warriors to remove
     * @exception NoMoreRessourcesException if you don't have enough ressources
     */
    public void removeWarriors(int nb) throws NoMoreRessourcesException {
        if ((this.warriors - nb) < 0) {
            throw new NoMoreRessourcesException("you have less than " + nb + " warriors");
        } else {
            this.warriors -= nb;
        }
    }

    /**
     * returns the number of warriors of this player
     * 
     * @return number of warriors belonging to this player
     */
    public int getWarriors() {
        return this.warriors;
    }

    /**
     * set the objective to the player 
     * @param objective the objective
     */
    public void setObjective(AresGameObjectives objective) {
        this.objective = objective;
    }

    /**
     * return the objective given to the player
     * @return the objective
     */
    public AresGameObjectives getObjectives(){
        return this.objective; 
    }

    /**
     * returns the number of secret weapons owned by this player
     * 
     * @return int
     */
    public int getNbSecretWeapon() {
        return this.secretWeapon;
    }

    /**
     * removes an army from the list of armies
     * @param army the army
     */
    public void removeArmy(Army army) {
        this.armies.remove(army);
        this.playerTiles.remove(army.getTuile());
    }

    /**
     * adds a secret weapon to this player
     */
    public void addSecretWeapon() {
        this.secretWeapon += 1;
    }

    /**
     * removes a secret weapon from this player
     */
    public void removeSecretWeapon() {
        this.secretWeapon -= 1;
    }

    /**
     * returns the list of armies of this player
     * 
     * @return the list of army
     */
    public List<Army> getArmies() {
        return this.armies;
    }

    /**
     * adds an army to the list of armies of this player
     * 
     * @param army the army
     */
    public void addArmy(Army army) {
        this.armies.add(army);
        this.playerTiles.add(army.getTuile());
    }

    /**
     * returns the list of camps of this player
     * 
     * @return the list of camp
     */
    public List<Camp> getCamps() {
        return this.camps;
    }

    /**
     * adds a camp to the list of camps of this player
     * 
     * @param camp the camp
     */
    public void addCamp(Camp camp) {
        this.camps.add(camp);
        this.playerTiles.add(camp.getTuile());
    }


    /**
     * return true if this player has envaded an entire island
     * @param board the board
     * @return true if the player invade the island, false otherwise
     */
    public boolean didEnvadeIsland(Board board) {
        for (List<Earth> island : board.getIslands()) {
            boolean allTilesOwned = true;
            for (Earth tile : island) {
                if (!this.getTiles().contains(tile)) {
                    allTilesOwned = false;
                    break;
                }
            }
            if (allTilesOwned) {
                return true;
            }
        }
        return false;
    }

    /**
     * return true if this player has conquered the values of tiles required 
     * @return true if it's the case, false otherwise
     */
    public boolean hasConqueredTiles() {
        return this.getTiles().size() >= this.objective.getValue();
    }

    /**
     * returns true if the player has enough warriors by summing the warriors in all
     * their armies and camps to meet the required objective
     * @return true if it's the case, false otherwise
     */
    public boolean didDetainWarriors() {
        int totalWarriors = 0;
        for (Army army : this.armies) {
            totalWarriors += army.getNbWarriors();
        }
        for (Camp camp : this.camps) {
            totalWarriors += camp.getNbWarriors();
        }
        return totalWarriors >= this.objective.getValue();
    }

    /**
     * return the objective of the player, exception if not set yet 
     * @return the objective
     */
    public AresGameObjectives getObjective() {
        if (this.objective == null) {
            throw new IllegalStateException("player has no objective yet");
        }
        return this.objective;
    }

    /** randomly assigns an objective to the player with a corresponding value */
    public void givePlayersObjective() {
        Random random = new Random();
        AresGameObjectives.ObjectiveType[] types = AresGameObjectives.ObjectiveType.values();
        AresGameObjectives.ObjectiveType chosenType = types[random.nextInt(types.length)];
        int value= 0; 
        if (chosenType== ObjectiveType.CONQUER_TILES){
            value= 5 + random.nextInt(6);
        }
        if (chosenType == ObjectiveType.INVADE_ISLANDS){
            value = 1; 
        }
        if (chosenType == ObjectiveType.DETAIN_WARRIORS){
            value=  20 + random.nextInt(11); 
        }
        this.objective = new AresGameObjectives(chosenType, value);
    }

    /**
     *  return true if the player's objective is achieved 
     * @param board the board
     * @return true if is the objective is complete, false otherwise
     */
    public boolean isObjectiveAchieved(Board board) {
        ObjectiveType obj= this.objective.getType(); 
        if ( obj == ObjectiveType.CONQUER_TILES){
            return this.hasConqueredTiles(); 
        }
        if (obj == ObjectiveType.INVADE_ISLANDS){
            return this.didEnvadeIsland(board);
        }
        if (obj  == ObjectiveType.DETAIN_WARRIORS){
            return this.didDetainWarriors();
        }
        return false; 
    }

    /** collect all the ressources from the differents building */
    public void collectRessources() {
        for (Army army : this.armies) {
            this.addRessource(army.getTuileRessource(), 1);
        }
        for (Camp camp : this.camps) {
            this.addRessource(camp.getTuileRessource(), 2);
        }
    }

    /**
     * fill the actionsDemeter attributes with all the actions for this game
     * if option is 0 the actions will be in interactive mode, random otherwise
     * 
     * @param board the board
     * @param option the option
     * @param players the players
     */
    public void createActions(Board board, int option, List<PlayerAres> players) {
        ListChooser<Earth> lcEarth = null;
        ListChooser<Army> lcArmy = null;
        ListChooser<Ressource> lcRessource = null;
        ListChooser<Integer> lcInt = null;
        ListChooser<String> lcString = null;
        ListChooser<Camp> lcCamp = null;
        ListChooser<PlayerAres> lcPlayer = null;
        ListChooser<Integer> lcNumber = null;

        this.actionsAres.clear();
        if (this.actionsAres.isEmpty()) {
            if (option == 0) {
                lcEarth = new InteractiveListChooser<>();
                lcArmy = new InteractiveListChooser<>();
                lcRessource = new InteractiveListChooser<>();
                lcInt = new InteractiveListChooser<>();
                lcString = new InteractiveListChooser<>();
                lcCamp = new InteractiveListChooser<>();
                lcPlayer = new InteractiveListChooser<>();
                lcNumber = new InteractiveListChooser<>();

            } else {
                lcEarth = new RandomListChooser<>();
                lcArmy = new RandomListChooser<>();
                lcRessource = new RandomListChooser<>();
                lcInt = new RandomListChooser<>();
                lcString = new RandomListChooser<>();
                lcCamp = new RandomListChooser<>();
                lcPlayer = new RandomListChooser<>();
                lcNumber = new RandomListChooser<>();
            }
            this.actionsAres = actionsPlayer(board, lcEarth, lcRessource, lcArmy, lcInt, lcString, lcCamp, lcPlayer, lcNumber, players);
        }
    }

    public List<Action<PlayerAres>> getActionsAres() {
        return this.actionsAres;
    }

    /**
     * excute the action of the demeter player
     * 
     * @param board the board
     * @param option if option is 0 then we create a interactive actions List,
     *               otherwise the actions will be automatic
     * @throws IOException exception
     * @throws InvalidChoiceException if the choice is not valid
     */
    public void act(Board board, int option) throws IOException, InvalidChoiceException {
        ListChooser<Action<PlayerAres>> lc = null;

        if (option == 0) {
            lc = new InteractiveListChooser<>();
        } else {
            lc = new RandomListChooser<>();
        }

        Action<PlayerAres> aresAction = lc.choose("Choose an action by typing its number", this.actionsAres);
        if (actionsAres != null) {
            try {
                aresAction.act(this);
            } catch (NoMoreRessourcesException | CantBuildException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("No action chosen");
        }
    }

    /**
     * returns a list of actions that the player Ares can do
     * 
     * @param board
     * @return List<Action<PlayerAres>>
     */
    private List<Action<PlayerAres>> actionsPlayer(Board board, ListChooser<Earth> lcEarth,
            ListChooser<Ressource> lcRessource, ListChooser<Army> lcArmy, ListChooser<Integer> lcInt,
            ListChooser<String> lcString, ListChooser<Camp> lcCamp, ListChooser<PlayerAres> lcPlayer, ListChooser<Integer> lcNumber, List<PlayerAres> players) {
        List<Action<PlayerAres>> aresActions = new ArrayList<>();
    
        BuildPort<PlayerAres> buildPort = new BuildPort<PlayerAres>(this, board, lcEarth);
        if (buildPort.hasEnoughRessources()) {
            aresActions.add(buildPort);
        }

        //Exchange Ressources
        ExchangeRessources<PlayerAres> exchangeRessources = new ExchangeRessources<PlayerAres>(this, lcRessource);
        if( exchangeRessources.canExchange() ){
            aresActions.add(exchangeRessources);
        }
        

        // add possible actions for player Ares

        // Build Army
        BuildArmy buildArmy = new BuildArmy(board, this, lcEarth, lcNumber);
        if (buildArmy.hasEnoughRessources()) {
            aresActions.add(buildArmy);
        }

        // upgrade to camp with ressources
        UpgradeWithRessources upgradeWithRessources = new UpgradeWithRessources(this, lcArmy);
        if (upgradeWithRessources.hasEnoughRessources() && !this.armies.isEmpty()) {
            aresActions.add(upgradeWithRessources);
        }

        // upgrade to camp with warriors
        List<Army> armiesWithFiveWarriors = new ArrayList<>();
        for (Army army : this.armies) {
            if (army.getNbWarriors() == 5) {
                armiesWithFiveWarriors.add(army);  
            }
        }
        if (!armiesWithFiveWarriors.isEmpty() && this.getWarriors() > 0) {
            UpgradeWithWarriors upgradeWithWarriors = new UpgradeWithWarriors(this, lcInt, lcArmy);
            aresActions.add(upgradeWithWarriors);
        }
        

        BuySecretWeapon buySecretWeapon = new BuySecretWeapon(this);
        if (buySecretWeapon.hasEnoughRessources()) {
            aresActions.add(buySecretWeapon);
        }

        BuyWarriors<PlayerAres> buyWarriors = new BuyWarriors<PlayerAres>(this);
        if (buyWarriors.hasEnoughRessources()) {
            aresActions.add(buyWarriors);
        }

        aresActions.add(new DisplayWarriors(this, lcInt, lcArmy));
        aresActions.add(new AttackNeighboor(this, players, lcPlayer,lcEarth));

        return aresActions;
    }

    /**
     * allow the player to build a army, it will be used to build the first two armies in the game
     * @param board the board
     * @param lc lischooser with earth
     * @param lcNumber list chooser with number
     * @throws CantBuildException if you can't build
     * @throws NoMoreRessourcesException if the choice is invalid
     * @throws InvalidChoiceException if you don't have enough ressources
     */
    public void placeInitialArmy(Board board, ListChooser<Earth> lc, ListChooser<Integer> lcNumber) throws CantBuildException, NoMoreRessourcesException, InvalidChoiceException{
        BuildArmy ba= new BuildArmy(board, this, lc , lcNumber); 
        ba.act(this);
    }


    /**
     * returns a list of enemy tiles on the same island as this player's tiles
     * 
     * @param board the game board
     * @param enemy the enemy player
     * @return the list of enemy tiles on the same island as this player's tiles
     */
    public List<Earth> getEnemyTilesOnSameIsland(Board board, PlayerAres enemy) {
        List<Earth> islandTiles = new ArrayList<>();
    
        for (Earth myTile : this.getTiles()) {
            List<Earth> island = board.getIsland(myTile);
            if (island == null) continue;
    
            for (Earth enemyTile : enemy.getTiles()) {
                if (island.contains(enemyTile) && !islandTiles.contains(enemyTile)) {
                    islandTiles.add(enemyTile);
                }
            }
        }
    
        return islandTiles;
    }
    

}
