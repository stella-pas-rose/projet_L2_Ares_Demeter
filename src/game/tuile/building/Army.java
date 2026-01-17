package game.tuile.building;

import game.PlayerAres;
import game.tuile.Earth;
import game.tuile.Ressource;
import game.util.CantBuildException;
import game.util.NoMoreRessourcesException;

/*
 * A class to create a Army
 */
public class Army extends Building{

    protected int nbWarriors  ; //the number of warriors in the army
    private static final String SYMBOL = "a";
    private static int nbWarriorsMax = 5; //the max number of warriors in a army 
    protected PlayerAres player;

    /** create an army on a given tile
    * @param tuile the tile where we build the building
    * @param nbWarriors the number of warriors
    * @param player the player
    * @throws CantBuildException if you can't build
    */
    public Army(Earth tuile, int nbWarriors, PlayerAres player) throws CantBuildException{
        super(tuile, player); 
        if (nbWarriors > nbWarriorsMax){
            throw new CantBuildException("You cant build Army with warriors more then 5");
        }
        else{
            this.nbWarriors = nbWarriors;
        }
        this.dimension = this.nbWarriors;
        this.symbol = SYMBOL;
        
        this.cost.put(Ressource.WOOD,1);
        this.cost.put(Ressource.SHEEP, 1);
        this.cost.put(Ressource.WEALTH, 1);


        this.player = player;

    }

    public String toString(){
        return "Army ("+ this.getNbWarriors()+ " warriors) " + this.tuile.getPosition(); 
    }
    
    public String getName(){
        return "Army";
    }
    /**
     * return the number of warriors in the army
     * @return int
     */
    public int getNbWarriors(){
        return this.nbWarriors;
    }
    
    public PlayerAres getPlayerAres(){
        return this.player;
    }     

    /**
     * add warriors to the army
     * @param nb the number of warriors
     * @throws NoMoreRessourcesException if you don't have enough ressources
     */
    public void addWarriors(int nb) throws NoMoreRessourcesException{
        if (this.player.getWarriors() < nb){
            throw new NoMoreRessourcesException("You dont have enough warriors");
        }
        this.nbWarriors += nb;
    }
    
    /**
     * return true if the army can be a camp by already having 5 warriors
     * @param player the person who play
     * @return boolean
     */
    public boolean canBeCampWarriors(PlayerAres player){
        return this.getNbWarriors() >= 5 ;
    }

    /**
     * return true if the army can be a camp if player has enough ressources
     * @param player the person who play
     * @return boolean
     * @throws CantBuildException if you can't build
     */
    public boolean canBeCampRessources(PlayerAres player) throws CantBuildException{
        return this.player.hasEnoughRessources(new Camp(tuile, nbWarriors, player));
    }

    
    /** evolves the army into a camp
    * @param player the player who wants to upgrade the army
    * @return the new camp if the army can be upgraded null otherwise
    * @throws CantBuildException if you can't build
    */
    public Camp upGradeToCampWithWarriors(PlayerAres player) throws CantBuildException {
        if (this.canBeCampWarriors(player)) {
            return new Camp(this.getTuile(), this.getNbWarriors(), this.getPlayerAres());
        } else {
            throw new CantBuildException("Not enough warriors to upgrade to a camp");
        }
    }

    /**
     * evolves the army into a camp
     * @param player the player who wants to upgrade the army
     * @return the new camp if the army can be upgraded null otherwise
     * @throws CantBuildException if you can't build
     */
    public Camp upGradeToCampWithRessources(PlayerAres player) throws CantBuildException {
        if (this.canBeCampRessources(player)) {
            return new Camp(this.getTuile(), this.getNbWarriors(), this.getPlayerAres());
        } else {
            throw new CantBuildException("Not enough resources to upgrade to a camp");
        }
    }
    



}

