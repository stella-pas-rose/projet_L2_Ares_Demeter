package game.action.actionDemeter;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.PlayerDemeter;
import game.action.Action;
import game.listchooser.FixedIndexChooser;
import game.tuile.Forest;
import game.tuile.Ressource;
import game.tuile.building.Farm;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;

public class BuildFarmTest {

    private Board board;
    private PlayerDemeter player;
    private Action<PlayerDemeter> action;

    @BeforeEach
    void setUp() {
        player = new PlayerDemeter("kiwi");
        board = new Board(5, 5);
        action = new BuildFarm(board, player, new FixedIndexChooser<>(2));
    }

    @Test
    void testBuildFarmWithInsufficientResources() throws NoMoreRessourcesException, CantBuildException, IOException, InvalidChoiceException {

        assertTrue(player.getFarms().isEmpty());

        player.addFarm(new Farm(new Forest(), player)); 
        player.addFarm(new Farm(new Forest(), player)); 

        assertThrows(NoMoreRessourcesException.class, () -> action.act(player));

        player.addRessource(Ressource.WOOD, 1);
        player.addRessource(Ressource.ORE, 1);

        action.act(player);

        assertFalse(player.getFarms().isEmpty(), "player should have at least one farm after building");

        assertEquals(0, player.getResources().get(Ressource.WOOD), "player should have no wood left");
        assertEquals(0, player.getResources().get(Ressource.ORE), "player should have no ore left");
    }




}