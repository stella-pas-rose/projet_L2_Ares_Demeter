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

public class BuySecretWeaponTest{

    private PlayerAres player; 
    private Action<PlayerAres> action; 

    @BeforeEach
    void setUp(){
        player = new PlayerAres("chouette");
        action= new BuySecretWeapon(player); 
    }

    @Test
    void actTest() throws NoMoreRessourcesException, CantBuildException, IOException, InvalidChoiceException{
        assertTrue(player.getNbSecretWeapon()==0);
        assertThrows(NoMoreRessourcesException.class, () -> action.act(player)); 
        player.addRessource(Ressource.WOOD, 1);
        player.addRessource(Ressource.ORE, 1);
        action.act(player);
        assertTrue(player.getNbSecretWeapon()==1);
    }

}