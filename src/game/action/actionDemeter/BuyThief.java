package game.action.actionDemeter;
import game.PlayerDemeter;
import game.action.Action;
import game.action.ActionManager;
import game.tuile.Ressource;
import game.util.NoMoreRessourcesException;

/*
 * This class is used to buy a thief
 * it extends ActionManager and implements Action<PlayerDemeter> 
 */
public class BuyThief extends ActionManager<PlayerDemeter> implements Action<PlayerDemeter>{

    public BuyThief(PlayerDemeter player){
        super(player);
        this.cost.put(Ressource.WOOD, 1);
        this.cost.put(Ressource.ORE, 1);
        this.cost.put(Ressource.WEALTH, 1);

    }


    /**
    * @return the description of the action
    */
    public String toString(){
        return "Buy a thief => cost: " + this.cost; 
    }

    /**
     * buys a thief if the player has enough ressources
     * @param the player who wants to buy a thief
     */
    public void act(PlayerDemeter player) throws NoMoreRessourcesException{

        // checks if player has enough ressources to buy a thief
        if (! this.hasEnoughRessources()) {
            throw new NoMoreRessourcesException("Not enough ressources to buy a thief \n cost: "+this.cost);
        }
        // if the player has enough ressources , removes the thief's cost from the player
        this.removeRessources();
        player.addThief();

        System.out.println(player.getName()+ " "+ player.getResources()+ " now have a thief");
        
    }
    
    
    
}
