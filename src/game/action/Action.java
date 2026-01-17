package game.action;
import game.Player;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;

/**
 * represents an action that can be done by a player
 */
public interface Action<T extends Player> {

    /**
     * executes the action for the given player
     * @param player the player
     * @throws NoMoreRessourcesException if you don't have the ressources
     * @throws CantBuildException if you can't build
     * @throws InvalidChoiceException if the choice is invalid
     */
    void act(T player) throws NoMoreRessourcesException , CantBuildException, InvalidChoiceException;
}
