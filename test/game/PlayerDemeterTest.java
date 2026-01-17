package game;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.action.Action;
import game.action.ExchangeRessources;
import game.action.actionDemeter.BuildFarm;
import game.action.actionDemeter.BuyThief;
import game.action.actionDemeter.ExchangeRessourcesPort;
import game.action.actionDemeter.PlayThief;
import game.action.actionDemeter.UpgradeFarm;
import game.listchooser.FixedIndexChooser;
import game.tuile.Earth;
import game.tuile.Field;
import game.tuile.Forest;
import game.tuile.Mountain;
import game.tuile.Ressource;
import game.tuile.building.Exploitation;
import game.tuile.building.Farm;
import game.tuile.building.Port;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;
import game.util.Position;

public class PlayerDemeterTest {

    private PlayerDemeter player;
    private Board board; 

    @BeforeEach
    // Create a player and a field tile
    void setUp() {
        player = new PlayerDemeter("TestPlayer");
        board= new Board(5, 5); 
    }

    // Test the player's initialization
    @Test
    void testPlayerInit() {
        assertEquals("TestPlayer", player.getName());
        assertEquals(0, player.getPoints());
        assertEquals(0, player.getNbThief()); 
        assertTrue(player.getFarms().isEmpty());
        assertTrue(player.getExploitations().isEmpty()); 
    }

    @Test
    void pointTest(){
        player.addPoints(5);
        assertEquals(player.getPoints(), 5);
    }

    @Test
    void thiefTest(){
        player.addThief();
        assertEquals(player.getNbThief(), 1);
    }

    @Test
    void testBuild() throws NoMoreRessourcesException, IOException, InvalidChoiceException{
        BuildFarm buildfarm=new BuildFarm(board, player, new FixedIndexChooser<>(1));
        // a player trying to build with enough resources should return True
        player.addRessource(Ressource.WOOD, 1);
        player.addRessource(Ressource.ORE, 1);
        assertTrue(player.getFarms().isEmpty());
        buildfarm.act(player);
        buildfarm.act(player);
        Integer nbWoods= player.getResources().get(Ressource.WOOD);
        Integer nbOre= player.getResources().get(Ressource.ORE);
        buildfarm.act(player);
        assertEquals( player.getFarms().size(),3);
        // after building a farm  using  wood and one ore , the player's ressources should diminish
        assertTrue(player.getResources().get(Ressource.WOOD)<= nbWoods);
        assertTrue(player.getResources().get(Ressource.ORE) <= nbOre);
        }

    @Test
    void testAddAndRemoveFarm() {
        Earth earth = new Earth(Ressource.SHEEP, null);
        Farm farm = new Farm(earth, player);
        player.addFarm(farm);
        assertTrue(player.getFarms().contains(farm));

        player.removeFarm(farm);
        assertFalse(player.getFarms().contains(farm));
        assertFalse(player.getTiles().contains(farm.getTuile()));
    }



    @Test
    void testAddExploitation() {
        Earth earth = new Earth(Ressource.SHEEP, null);
        Exploitation ex = new Exploitation(earth, player);
        player.addExploitation(ex);

        assertTrue(player.getExploitations().contains(ex));
        assertTrue(player.getTiles().contains(ex.getTuile()));
    }

    @Test
    void testHasPort() {
        Earth earth = new Earth(Ressource.SHEEP, null);
        earth.setPosition(new game.util.Position(1, 1));
        Port port = new Port(earth, player);
        earth.setBuilding(port);

        player.addPort(port); 

        assertTrue(player.hasPort());
    }


    @Test
    void collectRessourcesOfThePlayerTest(){
    Forest forest=new Forest();
    Mountain mountain=new Mountain();
    Farm farm=new Farm(forest, player);
    Exploitation exp=new Exploitation(mountain, player);
    
    player.addFarm(farm);
    player.addExploitation(exp);

    // before collecting the player's ressources that he has on tiles that have 
    //building , the player has no ressources in his stock for the moment
    assertTrue(player.getRessourceAmount(farm.getTuileRessource())==0);
    assertTrue(player.getRessourceAmount(exp.getTuileRessource())==0);

    // collecting the player's ressources
    player.collectRessources();
    //after collecting the player's ressouces of tiles that has building ,
    //now the player got said ressources in his inventory
    assertTrue(player.getRessourceAmount(farm.getTuileRessource())==1);
    assertTrue(player.getRessourceAmount(exp.getTuileRessource())==2);

    }

    @Test 
    void placeInitialFarmRandomTest() throws NoMoreRessourcesException, InvalidChoiceException , CantBuildException{
    //before placing a farm randomly on the board for the player , the player has no farms
    assertTrue(player.getFarms().isEmpty());
    player.placeInitialFarm(board, new FixedIndexChooser<>(2));
    //after placing a farm randomly on the board , the player posesses one farm
    assertTrue(!player.getFarms().isEmpty());
    }

    @Test
    void testCreateAllActionsAvailable() throws Exception {
        PlayerDemeter player = new PlayerDemeter("Demeter");
        Board board = new Board(5, 5);
        List<PlayerDemeter> players = new ArrayList<>();
        players.add(player);

       
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board.put(new Field(), new Position(i, j));
            }
        }

        
        Earth tile =(Earth) board.getTile(new Position(1, 1));
        Farm farm = new Farm(tile, player);
        tile.setBuilding(farm);
        player.addFarm(farm);

       
        Port port = new Port((Earth)board.getTile(new Position(2, 2)), player);
        Earth e = (Earth) board.getTile(new Position(2, 2));
        e.setBuilding(port);
        player.addPort(port);

      
        player.addThief();

        
        player.addRessource(Ressource.WOOD, 10);
        player.addRessource(Ressource.ORE, 10);
        player.addRessource(Ressource.WEALTH, 10);
        player.addRessource(Ressource.SHEEP, 10);

        
        player.createActions(board, 1, players);

        
        List<Action<PlayerDemeter>> actions = player.getActionsDemeter();

        
        
        boolean hasBuildFarm = false;
        boolean hasBuyThief = false;
        boolean hasPlayThief = false;
        boolean hasExchange = false;
        boolean hasExchangePort = false;
        boolean hasUpgradeFarm = false;

        for (Action<PlayerDemeter> action : actions) {
            if (action instanceof BuildFarm) hasBuildFarm = true;
            if (action instanceof BuyThief) hasBuyThief = true;
            if (action instanceof PlayThief) hasPlayThief = true;
            if (action instanceof ExchangeRessources) hasExchange = true;
            if (action instanceof ExchangeRessourcesPort) hasExchangePort = true;
            if (action instanceof UpgradeFarm) hasUpgradeFarm = true;
        }

       
        assertTrue(hasBuildFarm);
        assertTrue(hasBuyThief);
        assertTrue(hasPlayThief);
        assertTrue(hasExchange);
        assertTrue(hasExchangePort);
        assertTrue(hasUpgradeFarm);
    }


    @Test
    void testRemoveThief() {

        player.addThief();
        assertEquals(1, player.getNbThief());
        player.removeThief();
        assertEquals(0, player.getNbThief());

        
        player.removeThief();
        assertEquals(0, player.getNbThief());
    }


    @Test
    void testActChoosesAndExecutesAction() throws Exception {
        List<PlayerDemeter> players = new ArrayList<>();
        players.add(player);
        player.addThief();
        player.addRessource(Ressource.WOOD, 10);
        player.addRessource(Ressource.ORE, 10);

        player.createActions(board, 1, players); 
        assertDoesNotThrow(() -> player.act(board, 1));
    }


    
    @Test
    void testGetActionsDemeterReturnsList() {
        List<Action<PlayerDemeter>> actions = player.getActionsDemeter();
        assertNotNull(actions);
    }


    @Test
    void testUpdateNbIsland() {
        assertEquals(0, player.getNbIsland());
        player.updateNbIsland(3);
        assertEquals(3, player.getNbIsland());
    }

    @Test
    void testHasPortWhenEmpty() {
        assertFalse(player.hasPort());
    }

    @Test
    void testCreateActionsAddsActionsWhenResourcesPresent() {
        Board board = new Board(5, 5);
        List<PlayerDemeter> players = List.of(player);

        player.addRessource(Ressource.WOOD, 5);
        player.addRessource(Ressource.ORE, 5);
        player.addRessource(Ressource.SHEEP, 5);
        player.addRessource(Ressource.WEALTH, 5);

        player.createActions(board, 1, players);
        assertFalse(player.getActionsDemeter().isEmpty());
    }














    








    
}
