package game.tuile.building;
import java.util.*;

import game.Player;
import game.tuile.Earth;
import game.tuile.Ressource;
import game.util.*;


/**
 * A class to create a Building 
 */
public abstract class Building{

    public static final String SYMBOL = " B "; //each building have his own SYMBOL
    protected String symbol;
    protected int dimension;
    protected int resourceMultiplier;
    protected Earth tuile ; 
    protected HashMap<Ressource, Integer> cost;
    protected Player player;

        
           
    /** create a building on a given tile
    * @param tuile the tile where we build the building
    * @param player the player
    */
    public Building(Earth tuile, Player player){
        this.player = player;
        this.symbol = SYMBOL;
        this.dimension = 1;
        this.resourceMultiplier = 1;
        this.tuile = tuile ;
        this.cost = new HashMap<>();
    }

    public HashMap<Ressource, Integer> getCost(){
        return this.cost;
    }

     
    /** abstract method because the name of the building will be different for each subclass 
     * @return the name of the building
    */
    public abstract String getName();

    /** return the tile where the build is
     * @return the tile
     */
    public Earth getTuile(){
        return this.tuile;
    }

    /** return the player who build this buiding
     * @return the player
     */
    public Player getPlayer(){
        return this.player; 
    }

    /** return the symbol of this building
     * @return the symbol
     */
    public String getSymbol(){
        return this.symbol;
    }

    /** return the dimension of this building
     * @return the dimension
     */
    public int getDimension(){
     return this.dimension;
    }


    /**
     * return the resource multiplier for this building
     * @return the resource multiplier
     */
    public int getResourceMultiplier() {
        return this.resourceMultiplier;
    }  
    

    /** return the ressources
     * @return the ressources of the building
     */
    public Ressource getTuileRessource(){
        return this.tuile.getRessource();
    }

     /** returns the position of this building which is also the position of the tile the building is placed on
    * @return the position on which the building was placed on */
    public Position getPosition(){
        return this.tuile.getPosition();

    }
    
    /**
    * collects resources from the tile and adds them to the player's stock
    * for exemple a camp collects twice the amount of resources  and displays the resources collected from the tile's position
    * 
    * @param player the player who owns the building
    */
    public void collectRessource(Player player) {
        if (this.getTuile().haveBuild()){
            Ressource ressource = this.getTuileRessource();
            int multiplier = this.getResourceMultiplier();  
            player.addRessource(ressource, multiplier);
            int x = this.getTuile().getPosition().getX(); 
            int y = this.getTuile().getPosition().getY(); 
            System.out.println("Tile (" + x + "," + y + ") produces " + multiplier + " " + ressource);
        }
    }
   
    /** 
    * displays cost
    */
    public void displayCost() {
        HashMap<Ressource, Integer> cost = this.getCost();
        System.out.print(this.getName() + ", cost -> ");
        for (Map.Entry<Ressource, Integer> entry : cost.entrySet()) {
            Ressource ressource = entry.getKey();
            int nb = entry.getValue();
            System.out.print(ressource + ": " + nb + " ");
        }
        System.out.println();
    }

    

}











    
    





