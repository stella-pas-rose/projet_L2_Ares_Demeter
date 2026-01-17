package game.action.actionDemeter;

import java.util.ArrayList;
import java.util.List;

import game.PlayerDemeter;
import game.action.Action;
import game.listchooser.ListChooser;
import game.tuile.Ressource;
import game.util.NoMoreRessourcesException;



/**
 * This class is used to play a thief
 * its implements Action<PlayerDemeter>
 */
public class PlayThief implements Action<PlayerDemeter> {

    private List<PlayerDemeter> players;
    public ListChooser<Ressource> lc; 
    private Ressource ressource; 

    //builds a new PlayThief action with the given players demeter on the board and the given ressource type that the thief will steal
    public PlayThief(ListChooser<Ressource> lc, List<PlayerDemeter> players) {
        this.ressource = null;
        this.players = players;
        this.lc = lc;
    }

    /**
     * asks the player which ressource he wants to receive in exchange
     * @return the ressource the player wants to receive in exchange
     */
    public Ressource askRessource(PlayerDemeter playerOfThief) throws NoMoreRessourcesException {
       
        // creete a list of available ressources to steal
        List<Ressource> available = new ArrayList<>();
        // for each ressource type
        for (Ressource r : Ressource.values()) {

            // for each player on the board
            for (PlayerDemeter p : this.players) {

                if (p != playerOfThief && p.getRessourceAmount(r) > 0) {
                    available.add(r); // add the ressource to the list of available ressources
                    break;// we can stop checking this ressource type 
                }
            }
        }
        if (available.isEmpty()) {
            throw new NoMoreRessourcesException("No available resources to steal.");
        }
        
        
        Ressource chosen = lc.choose("What resource do you want to steal?", available);
        if (chosen == null){
            throw new NoMoreRessourcesException("No ressource to steal was selected");

        }
        return chosen;
    }


    /**
    * @return the description of the action
    */
    public String toString(){
        return "Play thief" ; 
    }


    /**
     * the thief steals the type of ressource in the constructor from every player on the board and gives it the given playerOfThief
     * @param playerOfThief 
     * @throws NoMoreRessourcesException if the player doesn't have enough ressources to play the thief
     */
    @Override
    public void act(PlayerDemeter playerOfThief) throws NoMoreRessourcesException {
        this.ressource= askRessource(playerOfThief); 
        int totalStolen = 0;
        // check if the player has a thief to play
        if (playerOfThief.getNbThief() == 0) {
            throw new NoMoreRessourcesException("You don't have any thief to play");
        }

        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i) != playerOfThief) {
                int stolen = this.players.get(i).getRessourceAmount(this.ressource);
                if (stolen > 0) {
                    totalStolen += stolen;
                    this.players.get(i).removeRessource(this.ressource, stolen);
                }
            }
        }

        if (totalStolen > 0) {
            playerOfThief.addRessource(this.ressource, totalStolen);
            System.out.println(playerOfThief.getName() + " has stolen " + totalStolen + " " + this.ressource);
            playerOfThief.removeThief(); // remove a thief from the player
        } else {
            throw new NoMoreRessourcesException("There is not enough of this type of ressource : " + this.ressource + " to be able to steal");
        }

        
    }
}
