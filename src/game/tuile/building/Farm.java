package game.tuile.building;
import game.PlayerDemeter;
import game.tuile.Earth;
import game.tuile.Ressource;

/*
 * A class to create a Farm
 */
public class Farm extends Building{

    private static final String SYMBOL= "f";
    protected PlayerDemeter player;

    /** Builds a Farm
     * @param tuile the tile where we place the farm
     * @param player the player
     */
    public Farm(Earth tuile, PlayerDemeter player) {
        super(tuile, player);
        this.dimension = 1;
        this.cost.put(Ressource.WOOD, 1);
        this.cost.put(Ressource.ORE, 1);
        this.symbol= SYMBOL; 
        this.player = player;
    }

    public String toString(){
        return "Farm " + this.tuile.getPosition(); 
    }


    /** 
     * returns the name of the building
     */
    public String getName(){
        return "Farm";
    }


    /** returns the player of this farm
     * @return the player of this farm
     */
    public PlayerDemeter getPlayerDemeter(){
        return this.player;
    }
    

    /**
     * returns true if the farm can be a exploitation
     * @param player2 the player that wants to upgrade his farm
     * @return boolean
     */
    public boolean canBeExploitation(PlayerDemeter player2){
        return player2.hasEnoughRessources(new Exploitation(tuile, player2));
    }
    
    /** evolves the farm into a exploitation
     * @param player the player 
     * @return the new exploitation if the farm can be upgraded null otherwise
    */
    public Exploitation upGradeToExploitation(PlayerDemeter player ) {
        if (this.canBeExploitation(player)) {
            //player.removeFarm(this);
            //this.getTuile().removeBuilding();
            Exploitation exploitation = new Exploitation(this.getTuile(), player );
            //this.getTuile().setBuilding(exploitation);
            //player.addExploitation(exploitation);
            return exploitation;
        } else {
            System.out.println("Not enough resources");
        }
        return null;
    }
}
