package game.action.actionDemeter;

import game.Board;
import game.PlayerDemeter;
import game.listchooser.FixedIndexChooser;
import game.tuile.Earth;
import game.tuile.Ressource;
import game.tuile.Sea;
import game.tuile.building.Port;
import game.util.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRessourcesPortTest{

    private PlayerDemeter player;
    private ExchangeRessourcesPort exchangeRessourcesPortAction;
    private Board board;
        

    @BeforeEach
    public void setUp(){
        player = new PlayerDemeter("test");
        exchangeRessourcesPortAction = new ExchangeRessourcesPort(player, new FixedIndexChooser<>(0));
    }


    @Test
    void testExchangeFailsWithoutPort() {
        player.addRessource(Ressource.WOOD, 2);
        player.addRessource(Ressource.ORE, 1);

        assertThrows(IllegalArgumentException.class, () -> {exchangeRessourcesPortAction.act(player);});
    }

    @Test
    void testExchageWithSameRessource() {
    
        ExchangeRessourcesPort exchangeRessourcesPortAction = new ExchangeRessourcesPort(player, new FixedIndexChooser<>(1)){
            @Override
            public Ressource askExchangeRessources() {
                return Ressource.WOOD;
            }
    
            @Override
            public Ressource askReceiveRessources() {
                return Ressource.WOOD; // exchanging the same ressource should throw an error
            }

        };
        
        assertThrows(IllegalArgumentException.class, () -> {exchangeRessourcesPortAction.act(player);});
    }

    /**
     * Tests a successful ressource exchange using a port 
     * @throws Exception
     */
    @Test 
    void testExchange() throws Exception{
        board = new Board(5, 5);
        for (int x=0; x<5; x++){
            for (int y=0; y<5; y++){
                board.put(new Sea(), new Position(x, y));
                }
            }
        //mettre une tuile Earth au centre de 4 tuiles Earth
        Position pos = new Position(2, 2);
        board.put(new  Earth(Ressource.ORE,null), pos);

        Position pos1 = new Position(2, 1);
        Earth portTile =  new Earth(Ressource.ORE, null);
        board.put(portTile, pos1);
        
        Port port = new Port(portTile, player);
        portTile.setBuilding(port);
        player.addPort(port);
        player.addTile(portTile);
    
        player.addRessource(Ressource.WOOD, 3);
        player.addRessource(Ressource.ORE, 1);

        int beforeWood =player.getRessourceAmount(Ressource.WOOD);
        int beforeOre = player.getRessourceAmount(Ressource.ORE);

        ExchangeRessourcesPort testAction = new ExchangeRessourcesPort(player, new FixedIndexChooser<>(1)){
            @Override
            public Ressource askExchangeRessources() {
                return Ressource.WOOD;
            }

            @Override
            public Ressource askReceiveRessources() {
                return Ressource.ORE;
            }

        };
        testAction.act(player);
        assertEquals(beforeWood-2, player.getRessourceAmount(Ressource.WOOD));
        assertEquals(beforeOre+1, player.getRessourceAmount(Ressource.ORE));
    }

}
    



    
