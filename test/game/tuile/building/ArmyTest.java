package game.tuile.building;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;
import game.tuile.*;
import game.util.CantBuildException;
import game.util.NoMoreRessourcesException;

import static org.junit.jupiter.api.Assertions.*;

public class ArmyTest {

    private Forest tuile;
    private Army army;
    private PlayerAres playerAres;


    @BeforeEach
    void setUp() throws CantBuildException {
        tuile = new Forest();
        playerAres=new PlayerAres("emilie");
        army = new Army(tuile, 0, playerAres);
        
    }

    /**
     * Ensures the number of warriors does not exceed the maximum limit
          * @throws CantBuildException 
          */
         @Test
         void armyMaxWarriorsTest() throws CantBuildException {
                assertThrows(CantBuildException.class, () -> {
                    new Army(tuile, 10, playerAres);
                });
            }
        

    /**
     * Test if the army can become a camp based on warrior count
     */
    @Test
    void canBeCampWithWarriorsTest() throws NoMoreRessourcesException {
        assertFalse(army.canBeCampWarriors(playerAres));
        playerAres.addWarriors(5);
        army.addWarriors(5);
        assertTrue(army.canBeCampWarriors(playerAres));
    }

    /**
     * Test if the army can become a camp based on resources
     * @throws CantBuildException 
     */
    @Test
    void canBeCampWithRessourcesTest() throws CantBuildException {
        assertFalse(army.canBeCampRessources(playerAres));
        playerAres.addRessource(Ressource.WOOD, 2);
        playerAres.addRessource(Ressource.ORE, 3);
        assertTrue(army.canBeCampRessources(playerAres));
    }

    /**
     * Test if the number of warriors is correctly added
     */
    @Test
    void addWarriorsTest() throws NoMoreRessourcesException {
        playerAres.addWarriors(3);
        assertEquals(0, army.getNbWarriors());
        army.addWarriors(3);
        assertEquals(3, army.getNbWarriors());
    }

    /**
     * Ensures that an army cannot add more warriors than available in stock */
    @Test 
    void addMoreWarriorsThanPossibleTest() throws NoMoreRessourcesException {
        playerAres.removeWarriors(30); // On met à zéro les warriors du joueur
        assertThrows(NoMoreRessourcesException.class, () -> army.addWarriors(6));
        
    }
    

    /**
     * Checks if an army can be upgraded to a camp when conditions are met
     * @throws CantBuildException 
     */
    @Test
    void upGradeToCampTest() throws NoMoreRessourcesException, CantBuildException {
        playerAres.addWarriors(5);
        army.addWarriors(5);
        assertTrue(army.canBeCampWarriors(playerAres));
        assertNotNull(army.upGradeToCampWithWarriors(playerAres));
        assertTrue(army.upGradeToCampWithWarriors(playerAres) instanceof Camp);
    }

    /**
     * Ensures that upgrading to a camp fails when a player does not have enough resources
     * @throws CantBuildException 
     */
    @Test
    void upgradeToCampFailedTest() throws CantBuildException {
    assertFalse(army.canBeCampRessources(playerAres));
    assertThrows(CantBuildException.class, () -> army.upGradeToCampWithRessources(playerAres));
}


}