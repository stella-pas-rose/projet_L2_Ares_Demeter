package game.action.actionDemeter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.PlayerDemeter;
import game.action.Action;
import game.tuile.Ressource;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class BuyThiefTest{

    private PlayerDemeter player; 
    private Action<PlayerDemeter> action; 

    @BeforeEach
    void setUp(){
        player = new PlayerDemeter("elijah");
        action= new BuyThief(player); 
    }

    @Test
    void actTest() throws NoMoreRessourcesException, CantBuildException, IOException, InvalidChoiceException{
        // before buying a thief, the player has no thieves
        assertTrue(player.getNbThief()==0);
        // the player has no ressources ,so if he attempts to buy a thief , it should throw an exception
        assertThrows(NoMoreRessourcesException.class, () -> action.act(player));
        //giving the player enough ressources so he could buy a thief 
        player.addRessource(Ressource.WOOD, 1);
        player.addRessource(Ressource.ORE, 1);
        player.addRessource(Ressource.WEALTH, 1);
        action.act(player);
        // after buying a thief , the player has one thief in his inventory of thieves
        assertTrue(player.getNbThief()==1);
    }


    
}