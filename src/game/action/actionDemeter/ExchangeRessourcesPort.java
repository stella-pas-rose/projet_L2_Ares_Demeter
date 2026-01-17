package game.action.actionDemeter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.PlayerDemeter;
import game.action.Action;
import game.action.ActionManager;
import game.listchooser.ListChooser;
import game.tuile.Ressource;
import game.util.CantBuildException;
import game.util.NoMoreRessourcesException;
import game.util.InvalidChoiceException;

public class ExchangeRessourcesPort extends ActionManager<PlayerDemeter> implements Action<PlayerDemeter>{

    public ListChooser<Ressource> lc; 

    public ExchangeRessourcesPort(PlayerDemeter player, ListChooser<Ressource> lc){
        super(player);
        this.lc = lc; 
    }

    /**
    * @return the description of the action
    */
    public String toString(){
        return "Exchange ressources by port"; 
    }

    /**
     * return true if the player have enough ressources to make an exchange, false otherwise
     * @return boolean
     */
    public boolean canExchange(){
        for (Ressource r : Ressource.values()) {
            if (player.getRessourceAmount(r) > 1) {
                return true; 
            }
        }
        return false; 
    }

     /**
     * asks the player which ressource he wants to exchange
     * @return the ressource the player wants to exchange
     */
    public Ressource askExchangeRessources() throws InvalidChoiceException{
        List<Ressource> ressources = new ArrayList<>();
        for (Ressource r : Ressource.values()) {
            if (player.getRessourceAmount(r) > 1) {
                ressources.add(r);
            }
        }
        Ressource chosenRessource = lc.choose("What resources do you want to exchange?", ressources);
        if(chosenRessource == null){
            throw new InvalidChoiceException("Action cancelled. No ressource to exchange was selected");
        }
        return chosenRessource;
    }
    

    /**
     * asks the player which ressource he wants to receive in exchange
     * @return the ressource the player wants to receive in exchange
     */
    public Ressource askReceiveRessources() {
        Ressource chosen = lc.choose("What resource do you want in exchange?", Arrays.asList(Ressource.values()));
        if (chosen == null){
            throw new IllegalArgumentException("Action cancelled. No ressource to receive was selected");
        }
        return chosen;
    }

    /**
     * First, asks the player which ressource he wants to exchange, then ask him which one he
     * wants te receive in exhange. If all the conditions are met, the exchange takes place. 
     * The conditions are different from ExchangeRessources
     * @throws InvalidChoiceException 
     */
    public void act(PlayerDemeter player) throws NoMoreRessourcesException , CantBuildException, InvalidChoiceException{
        Ressource toExchange= askExchangeRessources();
        Ressource toReceive= askReceiveRessources(); 
    
        if (toExchange==toReceive){
            throw new  IllegalArgumentException("2 ressources being exchanged must be different");
        }
        
        if (!player.hasPort()){
            throw new CantBuildException("You don't have a port to exchange ressources"); 
        }
        
        if (player.getRessourceAmount(toExchange)<2){
           throw new NoMoreRessourcesException("Not enough Ressources to be able to exchange.");
        }

        player.removeRessource(toExchange, 2);
        player.addRessource(toReceive, 1);
    
        System.out.println("2 "+ toExchange+ " were exchanged for 1 "+ toReceive);

    }
    
}
