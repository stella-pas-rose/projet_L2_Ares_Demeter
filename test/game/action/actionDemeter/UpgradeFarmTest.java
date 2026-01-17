package game.action.actionDemeter;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.PlayerDemeter;
import game.action.Action;
import game.listchooser.FixedIndexChooser;

import game.tuile.Ressource;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;

public class UpgradeFarmTest{

    private Board board; 
    private PlayerDemeter player; 
    private Action<PlayerDemeter> build; 
    private Action<PlayerDemeter> upgrade; 

    @BeforeEach
    void setUp() throws NoMoreRessourcesException, CantBuildException, IOException, InvalidChoiceException{
        player = new PlayerDemeter("kiwi");
        board= new Board(5, 5); 
        build= new BuildFarm(board, player, new FixedIndexChooser<>(0)); 
        upgrade= new UpgradeFarm(player, new FixedIndexChooser<>(0)); 
        player.addRessource(Ressource.WOOD, 1);
        player.addRessource(Ressource.ORE, 1);
        build.act(player);
    }

    @Test
    void actTest() throws NoMoreRessourcesException, CantBuildException, IOException, InvalidChoiceException {
        assertTrue(player.getExploitations().isEmpty());
        assertThrows(NoMoreRessourcesException.class, () -> upgrade.act(player)); 
        assertTrue(! player.getFarms().isEmpty());

        player.addRessource(Ressource.WOOD, 2);
        player.addRessource(Ressource.WEALTH, 1);
        player.addRessource(Ressource.SHEEP, 1);
        
        upgrade.act(player);
        
        assertTrue(! player.getExploitations().isEmpty());
    }

    
}