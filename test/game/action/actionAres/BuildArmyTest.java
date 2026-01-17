package game.action.actionAres;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import game.Board;
import game.PlayerAres;
import game.listchooser.FixedIndexChooser;
import game.tuile.Earth;
import game.tuile.Field;
import game.tuile.Ressource;
import game.tuile.Sea;
import game.tuile.building.Farm;
import game.tuile.building.Port;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;
import game.util.Position;

public class BuildArmyTest {

    private Board board;
    private PlayerAres player;

    @BeforeEach
    void setUp() throws CantBuildException {
        board = new Board(5, 5);
        player = new PlayerAres("Ares");

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                board.put(new Sea(), new Position(x, y));
            }
        }

        Position pos = new Position(2, 2);
        Earth tuile = new Field();
        board.put(tuile, pos);

        Position pos1 = new Position(2, 1);
        Earth tuile1 = new Field();
        board.put(tuile1, pos1);

        Position pos2 = new Position(1, 2);
        Earth tuile2 = new Field();
        board.put(tuile2, pos2);

        Position pos3 = new Position(3, 2);
        Earth tuile3 = new Field();
        board.put(tuile3, pos3);

        Position pos4 = new Position(2, 3);
        Earth earth = new Field();
        board.put(earth, pos4);

        Farm farm = new Farm(tuile, null);
        tuile.setBuilding(farm);

        Farm farm1 = new Farm(tuile1, null);
        tuile1.setBuilding(farm1);

        Port port = new Port(tuile3, player);
        tuile3.setBuilding(port);

        board.islands = board.findIslands();
    }

    @Test
    void BuildTest() throws NoMoreRessourcesException, CantBuildException, IOException, InvalidChoiceException {
        BuildArmy actionBuild = new BuildArmy(board, player, new FixedIndexChooser<>(0), new FixedIndexChooser<>(0));
        board.islands = board.findIslands();
        player.addRessource(Ressource.WOOD, 1);
        player.addRessource(Ressource.SHEEP, 1);
        player.addRessource(Ressource.WEALTH, 1);

        assertTrue(player.getArmies().isEmpty());
        actionBuild.act(player);
        assertTrue(!player.getArmies().isEmpty());
    }

    @Test
    void canBuildArmyTest() throws NoMoreRessourcesException {
        board = new Board(5, 5);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                board.put(new Sea(), new Position(x, y));
            }
        }

        Position pos = new Position(2, 2);
        Earth tuile = new Field();
        board.put(tuile, pos);

        Position pos1 = new Position(2, 1);
        Earth tuile1 = new Field();
        board.put(tuile1, pos1);

        Position pos2 = new Position(1, 2);
        Earth tuile2 = new Field();
        board.put(tuile2, pos2);

        Position pos3 = new Position(3, 2);
        Earth tuile3 = new Field();
        board.put(tuile3, pos3);

        Position pos4 = new Position(2, 3);
        Earth earth = new Field();
        board.put(earth, pos4);

        Farm farm = new Farm(tuile, null);
        tuile.setBuilding(farm);

        Farm farm1 = new Farm(tuile1, null);
        tuile1.setBuilding(farm1);

        Port port = new Port(tuile3, player);
        tuile3.setBuilding(port);

        board.islands = board.findIslands();

        BuildArmy buildArmy = new BuildArmy(board, player, new FixedIndexChooser<>(0), new FixedIndexChooser<>(0));
        assertTrue(buildArmy.canBuildArmy(earth, player));
    }

    @Test
    void testNoMoreRessourcesException() throws Exception {
        BuildArmy action = new BuildArmy(board, player, new FixedIndexChooser<>(0), new FixedIndexChooser<>(0));
        player.removeWarriors(player.getWarriors());

        assertThrows(NoMoreRessourcesException.class, () -> {
            action.act(player);
        });
    }
}
