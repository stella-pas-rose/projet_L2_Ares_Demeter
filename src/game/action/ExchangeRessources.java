package game.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.Player;
import game.listchooser.ListChooser;
import game.tuile.Ressource;
import game.util.NoMoreRessourcesException;
import game.util.InvalidChoiceException;

public class ExchangeRessources <T extends Player> extends ActionManager<T> implements Action<T> {

    private ListChooser<Ressource> lc; 

    /**
     *  creates an exchange action between two resources
     * @param player the player who proceed the exchange
     * @param lc the ressource lisChooser
     */
    public ExchangeRessources(T player, ListChooser<Ressource> lc) {
        super(player); 
        this.lc= lc; 
    }


    /**
    * @return the description of the action
    */
    public String toString(){
        return "Exchange ressources"; 
    }

    /**
     * return true if the player have enough ressources to make an exchange, false otherwise
     * @return boolean
     */
    public boolean canExchange(){
        for (Ressource r : Ressource.values()) {
            if (player.getRessourceAmount(r) > 2) {
                return true; 
            }
        }
        return false; 
    }

    /**
     * asks the player what type of  ressource he wants to exchange
     * @return the ressource
     * @throws InvalidChoiceException if the choice is invalid
     */
    public Ressource askExchangeRessources() throws InvalidChoiceException {
        List<Ressource> ressources = new ArrayList<>();
        for (Ressource r : Ressource.values()) {
            if (player.getRessourceAmount(r) > 2) {
                ressources.add(r);
            }
        }
        if (ressources.isEmpty()) {
            throw new InvalidChoiceException("No ressources available to exchange.");
        }
        Ressource chosen = lc.choose("What ressource do you want to exchange?", ressources);
        if (chosen == null) {
            throw new InvalidChoiceException("No ressource was selected");
        }
        return chosen;
    }
    
    /**
     * asks the player which ressource he wants to receive in exchange
     * @return the ressource
     */
    public Ressource askReceiveRessources() {
        List<Ressource> all = Arrays.asList(Ressource.values());
        Ressource chosen = lc.choose("What ressource do you want in exchange?", all);
        while (chosen == null) {
            chosen = lc.choose("What ressource do you want in exchange?", all);
        }
        return chosen;
    }
    


    /**
     * first, asks the player which ressource he wants to exchange, then asks  which one he
     * wants te receive in exchange. If all the conditions are met, the exchange takes place.  
     * @throws InvalidChoiceException if the choice is invalid
     */
    @Override
    public void act(T player) throws NoMoreRessourcesException, InvalidChoiceException {
        Ressource toExchange= askExchangeRessources();
        Ressource toReceive= askReceiveRessources();

        if ( toExchange == toReceive) {
            throw new IllegalArgumentException("You cannot exchange a ressource for itself");
        }

        if (player.getRessourceAmount(toExchange) < 3) {
            throw new NoMoreRessourcesException("Not enough " + toExchange + " to exchange you need at least 3");
        }
        player.removeRessource(toExchange,3);
        player.addRessource(toReceive, 1);
        System.out.println("3 "+ toExchange+ " were exchanged for 1 "+ toReceive);
    }
}
