package game.action.actionAres;

import game.PlayerAres;
import game.action.Action;
import game.action.ActionManager;
import game.listchooser.ListChooser;
import game.tuile.building.Army;


import java.util.ArrayList;
import java.util.List;

import game.tuile.Earth;
import game.tuile.building.Camp;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;

public class UpgradeWithWarriors extends ActionManager<PlayerAres> implements Action<PlayerAres> {

  private ListChooser<Integer> lnumb;
  private ListChooser<Army> lc;
  private Earth tuile;

  // army is the army that the given player player wants to upgrade
  public UpgradeWithWarriors(PlayerAres player, ListChooser<Integer> lnumb, ListChooser<Army> lc) {
    super(player);
    this.lnumb = lnumb;
    this.lc = lc;

  }


    /**
    * @return the description of the action
    */
    public String toString(){
      return "Upgrade with warriors"; 
  }

  /**2
   * 
   * Asks the player which army he wants to upgrade
   * 
   * @return the army the player wants to upgrade
   */
  /**
 * Asks the player which army they want to upgrade (only those with exactly 5 warriors)
 * 
 * @return the army the player wants to upgrade
 * @throws InvalidChoiceException if no valid army is available or the choice is cancelled
 */
public Army askArmy() throws InvalidChoiceException {
  List<Army> eligibleArmies = new ArrayList<>();
  for (Army army : this.player.getArmies()) {
      if (army.getNbWarriors() == 5) {
          eligibleArmies.add(army);
      }
  }
  if (eligibleArmies.isEmpty()) {
      throw new InvalidChoiceException("No eligible armies available (the army must have exactly 5 warriors to upgrade)");
  }
  Army chosenArmy = lc.choose("Which army do you want to upgrade?", eligibleArmies);

  if (chosenArmy == null) {
      throw new InvalidChoiceException("Action cancelled. No army was selected");
  }

  return chosenArmy;
}


  /**
   * asks the player how many warriors ther want to add
   * 
   * @param max the maximum number of warriors the player can add
   * @return the number of warriors to add
   * @throws InvalidChoiceException 
   */
  public int askNumberOfWarriors() throws InvalidChoiceException {
    List<Integer> options = new ArrayList<>();
    for (int i = 1; i <= (this.player).getWarriors(); i++) {
      options.add(i);
    }
    Integer add = lnumb.choose("How many warriors do you want to add?", options);
    if (add == null) {
      throw new InvalidChoiceException("Action cancelled. No warriors selected to upgrade");
    }
    return add;
  }

  /** upgrades the army of the given player by adding more warriors to the warrios they alreadly have
   * to create an army
   * @player the player that wants to upgrade his army using more warriors
   */
  public void act(PlayerAres player) throws NoMoreRessourcesException, CantBuildException,InvalidChoiceException{
    Army chosenArmy = askArmy();

    // asks the player how he wants to upgrade the army

    // checks if the player has an army to upgrade
    if (chosenArmy == null) {
      throw new InvalidChoiceException("No army selected");
    }

    // check if the army has 5 warriors to upgrade
    if (chosenArmy.getNbWarriors() < 5) {
      throw new CantBuildException("To upgrade an army to a camp, the army must have 5 warriors");
    }
    int add = askNumberOfWarriors();

    if (player.getWarriors() < add) {
      throw new CantBuildException("To upgrade an army to a camp, you must have enough warriors in stock");
    }

    this.tuile = chosenArmy.getTuile();

    // destroy army
    this.tuile.removeBuilding();
    player.removeArmy(chosenArmy);

    Camp camp = chosenArmy.upGradeToCampWithWarriors(player);
    camp.addWarriors(add);
    player.removeWarriors(add);

    // build camp
    this.tuile.setBuilding(camp);
    player.addCamp(camp);

    camp.collectRessource(player);

    System.out.println("The army evolved into a camp (" + camp.getNbWarriors() + " warriors)");

  }
}