package game.action.actionDemeter;

import game.PlayerDemeter;
import game.listchooser.FixedIndexChooser;
import game.tuile.Ressource;
import game.util.NoMoreRessourcesException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class PlayThiefTest {

    private PlayerDemeter player1;
    private PlayerDemeter player2;
    private PlayThief actionVoler;

    @BeforeEach
    void setUp() {
        player1 = new PlayerDemeter("lucie");
        player2 = new PlayerDemeter("luca");
    }

    @Test
    void shouldThrowExceptionWhenNoRessources() throws NoMoreRessourcesException {
        List<PlayerDemeter> listPlayers = new ArrayList<>();
        listPlayers.add(player1);
        listPlayers.add(player2);

        actionVoler = new PlayThief(new FixedIndexChooser<>(0), listPlayers);

        assertThrows(NoMoreRessourcesException.class, () -> actionVoler.act(player2));
    }

    @Test
    void shouldStealRessourcesSuccessfully() throws NoMoreRessourcesException {
        player1.addRessource(Ressource.WOOD, 10);
        player2.addThief();

        List<PlayerDemeter> listPlayers = new ArrayList<>();
        listPlayers.add(player1);
        listPlayers.add(player2);

        actionVoler = new PlayThief(new FixedIndexChooser<>(0), listPlayers);

        actionVoler.act(player2);

        assertEquals(0, player1.getRessourceAmount(Ressource.WOOD));
        assertEquals(10, player2.getRessourceAmount(Ressource.WOOD));
    }

    @Test
    void shouldThrowExceptionWhenPlayerHasNoThief() {
        player1.addRessource(Ressource.WOOD, 10);

        List<PlayerDemeter> listPlayers = new ArrayList<>();
        listPlayers.add(player1);
        listPlayers.add(player2);

        actionVoler = new PlayThief(new FixedIndexChooser<>(0), listPlayers);

        assertThrows(NoMoreRessourcesException.class, () -> actionVoler.act(player1));
    }
}
