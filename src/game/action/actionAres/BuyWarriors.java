package game.action.actionAres;
import game.PlayerAres;
import game.action.Action;
import game.action.ActionManager;
import game.tuile.Ressource;
import game.util.NoMoreRessourcesException;

/**
 * This class is used to buy warriors
 * it extends ActionManager and implements Action<PlayerAres>
 */
public class BuyWarriors<T extends PlayerAres> extends ActionManager<T> implements Action<T> {

    public BuyWarriors(T player) {
        super(player);
        this.cost.put(Ressource.WEALTH, 2);
        this.cost.put(Ressource.SHEEP, 2);
        this.cost.put(Ressource.ORE, 1);
    }


    /**
    * @return the description of the action
    */
    public String toString(){
        return "Buy warriors => cost: " + this.cost; 
    }

    /**
     * Buys 5 warriors if the player has enough resources
     * @param player the player who wants to buy ressources    
     */
    public void act(PlayerAres player) throws NoMoreRessourcesException {

        // Checks if player has enough ressources to buy 5 warriors
        if (!this.hasEnoughRessources()) {
            throw new NoMoreRessourcesException("Not enough resources to add 5 warriors");
        }
        this.removeRessources();
        
        // if the player has enough ressources ,adds 5 warriors to the player's warriors stock
        player.addWarriors(5);
        System.out.println("4 warriors were purchased"); 
    }
}

