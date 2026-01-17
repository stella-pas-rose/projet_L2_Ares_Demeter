package game.action.actionAres;

import game.PlayerAres;
import game.action.Action;
import game.action.ActionManager;
import game.tuile.Ressource;
import game.util.NoMoreRessourcesException;

/*
 * buys a secret weapon
 * it extends ActionManager and implements Action<PlayerAres>
 */
public class BuySecretWeapon extends ActionManager<PlayerAres> implements Action<PlayerAres>{


    public BuySecretWeapon(PlayerAres player){
        super(player);
        this.cost.put(Ressource.ORE, 1);
        this.cost.put(Ressource.WOOD, 1);
    }


    /**
    * @return the description of the action
    */
    public String toString(){
        return "Buy a secret weapon => cost: " + this.cost; 
    }


    /**
     * buys a secret weapon if the player has enough ressources for it
     * @param player the player who wants to buy
     * @throws NoMoreRessourcesException if the player doesn't have enough ressources to buy
     */
    @Override
    public void act(PlayerAres player) throws NoMoreRessourcesException {

        // checks if the player has enough ressources to buy a secret weapon
        if (! this.hasEnoughRessources() ){
            throw new NoMoreRessourcesException("Not enough ressources to buy a secret weapon");
        }
        this.removeRessources();
        // adds a secret weapon to the player's weapons stock
        player.addSecretWeapon();
        System.out.println(player.getName()+ " "+ player.getResources()+" (" +player.getWarriors()+ " warriors) now have a secret weapon");
    }


}