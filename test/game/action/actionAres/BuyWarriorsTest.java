package game.action.actionAres;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

import game.PlayerAres;
import game.action.Action;
import game.tuile.Ressource;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;
public class BuyWarriorsTest{

    private PlayerAres player; 
    private Action<PlayerAres> action; 

    @BeforeEach
    void setUp(){
        player = new PlayerAres("cool");
        action = new BuyWarriors<>(player);
    }

    @Test
    void actTest() throws NoMoreRessourcesException, CantBuildException, IOException, InvalidChoiceException{
        //the players have initialy 30 warriors each
        assertTrue(player.getWarriors()==30);
        // the player has no ressources , so if he attempts to buy additionnal warriors , it should throw an exception
        assertThrows(NoMoreRessourcesException.class, () -> action.act(player)); 
        //giving the player enough ressources to buy 5 warriors
        player.addRessource(Ressource.WEALTH, 2);
        player.addRessource(Ressource.SHEEP, 2);
        player.addRessource(Ressource.ORE, 1);
        action.act(player);
        //after buying 5 warriors , the player's inventory of warriors should increase by 5
        assertTrue(player.getWarriors()==35);
    }

}