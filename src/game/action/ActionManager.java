package game.action;
import java.util.HashMap;

import game.Player;
import game.tuile.Ressource;
import game.util.NoMoreRessourcesException;

public abstract class ActionManager <T extends Player>{

    protected HashMap<Ressource, Integer> cost;
    protected T player;
    
    /**
     * creates an ActionManager for the given player
     * @param player the  player
     */
        public ActionManager(T player){
            this.cost = new HashMap<>();
            this.player = player;
    }

    /**
     * checks if the the player has enough ressources in his inventory
     * @return true if he have enough, false otherwise
     */
    public boolean hasEnoughRessources(){
        for (Ressource ressource : cost.keySet()){
            if (player.getRessourceAmount(ressource) < cost.get(ressource)){
                return false;
            }
        }
        return true;
    }

    /**
     * removes the ressource from the player's inventary
     * @throws NoMoreRessourcesException if you don't have enough ressources
     */
    protected void removeRessources() throws NoMoreRessourcesException{
        for (Ressource ressource : cost.keySet()){
            player.removeRessource(ressource, cost.get(ressource));
        }
    }




}