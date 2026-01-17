package game.action.actionDemeter;

import java.util.List;

import game.*;
import game.action.Action;
import game.action.ActionManager;
import game.listchooser.ListChooser;
import game.tuile.*;
import game.tuile.building.*;
import game.util.NoMoreRessourcesException;
import game.util.InvalidChoiceException;

public class UpgradeFarm extends ActionManager<PlayerDemeter> implements Action<PlayerDemeter> {

    private ListChooser<Farm> lc;
    private Earth tuile;

    public UpgradeFarm(PlayerDemeter player, ListChooser<Farm> lc) {
        super(player);
        this.cost.put(Ressource.WOOD, 2);
        this.cost.put(Ressource.WEALTH, 1);
        this.cost.put(Ressource.SHEEP, 1);
        this.lc =lc;
    }


    /**
    * @return the description of the action
    */
    public String toString(){
        return "Upgrade farm => cost: " + this.cost; 
    }

    public Farm ask() throws InvalidChoiceException {
        List<Farm> farms = ((PlayerDemeter) this.player).getFarms();
        if (farms.isEmpty()) {
            throw new InvalidChoiceException("No farms available to upgrade.");
        }
        Farm chosen = lc.choose("Which farm do you want to upgrade?", farms);
        if (chosen == null) {
            throw new InvalidChoiceException("Action cancelled. No farm to upgrade was selected");
        }
        return chosen;
    }

    @Override
    public void act(PlayerDemeter player) throws NoMoreRessourcesException, InvalidChoiceException {
        Farm chosenFarm = ask();
        
        if (!this.hasEnoughRessources()) {
            throw new NoMoreRessourcesException("Not enough resources to upgrade the farm \n cost: "+this.cost);
        }
        
        Exploitation exploitation = chosenFarm.upGradeToExploitation(player);
        if (exploitation == null){
            return;
        }
        this.removeRessources();
        this.tuile = chosenFarm.getTuile();
        this.tuile.removeBuilding(); 
        player.removeFarm(chosenFarm);

        this.tuile.setBuilding(exploitation);
        player.addExploitation(exploitation);

        exploitation.collectRessource(player);
 
        player.addPoints(2); 
        System.out.println("The farm evolved into a exploitation");


    }
}
