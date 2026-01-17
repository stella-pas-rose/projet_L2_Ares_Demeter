package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.tuile.Earth;
import game.tuile.Field;
import game.tuile.Ressource;
import game.tuile.building.Exploitation;
import game.tuile.building.Farm;
import game.util.NoMoreRessourcesException;
import game.util.Position;




public class PlayerTest {


    private Player player;
    private Earth earth;



    @BeforeEach
    // Create a player and a field tile
    void setUp() {
        player = new Player("TestPlayer");
        earth = new Field();
    }




    // Test the player's initialization
    @Test
    void testPlayerInit() {
        assertEquals("TestPlayer", player.getName());
        assertEquals(0, player.getResources().get(Ressource.WOOD)); // puisque la tuile est une field
    }




    // test the player adding resources
    @Test
    void testPlayerAddResources() {
        player.addRessource(Ressource.WOOD, 10);
        assertEquals(10, player.getResources().get(Ressource.WOOD));
    }





    // test the player removing resources
    @Test
    void testPlayerRemoveResources() throws NoMoreRessourcesException {
        player.addRessource(Ressource.WOOD, 10);
        player.removeRessource(Ressource.WOOD, 5);
        assertEquals(5, player.getResources().get(Ressource.WOOD));
    }




    // test the player has enough resources
    @Test
    void testHasEnoughRessources() {
        player.addRessource(Ressource.WOOD, 2);
        player.addRessource(Ressource.ORE, 1);
        Farm farm = new Farm(earth, null);
        assertTrue(player.hasEnoughRessources(farm));
    }



    // test the player does not have enough resources
    @Test
    void testHasEnoughRessourcesFail() {
        player.addRessource(Ressource.WOOD, 2);
        player.addRessource(Ressource.ORE, 1);
        Exploitation exploitation = new Exploitation(earth, null);
        assertFalse(player.hasEnoughRessources(exploitation));
    }



    @Test
    void testAddTile() {
        player.addTile(earth);
        assertTrue(player.getTiles().contains(earth));
    }





    @Test
    void testGetEarthByPosition() {
        earth.setPosition(new Position(2, 3));
        player.addTile(earth);
        Earth result = player.getEarth(2, 3);
        assertEquals(earth, result);
    }




    @Test
    void testAddPort() {
        earth.setPosition(new game.util.Position(0, 0));
        game.tuile.building.Port port = new game.tuile.building.Port(earth, player);
        player.addPort(port);

        assertTrue(player.getPorts().contains(port)); 
        assertTrue(player.getTiles().contains(earth)); 
    }

    @Test
    void testRemoveRessourceThrowsExceptionIfNotEnough() {
        assertThrows(NoMoreRessourcesException.class, () -> {player.removeRessource(Ressource.WOOD, 1);});
    }

    @Test
    void testGetRessourceAmountReturnsZeroIfMissing() {
        assertEquals(0, player.getRessourceAmount(Ressource.SHEEP)); // par defaut ,0
    }


    @Test
    void testAddTileDoesNotDuplicate() {
        player.addTile(earth);
        player.addTile(earth);
        assertEquals(1, player.getTiles().size()); // doit rester à 1
    }

    @Test
    void testGetEarthReturnsNullWhenNotFound() {
        Earth result = player.getEarth(99, 99); // position absente
        assertNull(result);
    }


    



































}