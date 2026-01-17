package game.action.actionAres;

import game.PlayerAres;
import game.listchooser.FixedIndexChooser;

import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AttackNeighboorTest {

    private PlayerAres defender;
    private List<PlayerAres> enemies;

    @BeforeEach
    void setUp() {

        defender = new PlayerAres("Ares2");
        enemies = new ArrayList<>();
        enemies.add(defender);
    }

    @Test
    void testHowMuchDice_WithDifferentWarriorCounts() throws NoMoreRessourcesException {
        PlayerAres player = new PlayerAres("Ares");
        List<PlayerAres> enemies = new ArrayList<>();
        enemies.add(new PlayerAres("Ares2"));

        AttackNeighboor attack = new AttackNeighboor(player, enemies, new FixedIndexChooser<>(2),
                new FixedIndexChooser<>(2));

        // Cas 1 :
        player.removeWarriors(player.getWarriors() - 2); // reste 2
        assertEquals(1, attack.howMuchDice(player));

        // Cas 2 :
        player.addWarriors(3);
        assertEquals(2, attack.howMuchDice(player));

        // Cas 3 :
        player.addWarriors(5);
        assertEquals(3, attack.howMuchDice(player));

        // Cas 4 :
        player.removeWarriors(player.getWarriors());
        player.addSecretWeapon();
        assertEquals(3, attack.howMuchDice(player));
    }

    @Test
    void testDicesResult() {
        AttackNeighboor action = new AttackNeighboor(new PlayerAres("Ares"), enemies, new FixedIndexChooser<>(2),
                new FixedIndexChooser<>(2));

        int numberOfDices = 10;
        int result = action.dicesResult(numberOfDices);

        // Le résultat doit être entre 10 (1x10) et 60 (6x10)
        assertTrue(result >= 10 && result <= 60, "Le résultat devrait être entre 10 et 60");
    }

    @Test
    void testDicesResulZero() {
        AttackNeighboor action = new AttackNeighboor(new PlayerAres("Ares"), enemies, new FixedIndexChooser<>(2),
                new FixedIndexChooser<>(2));

        int result = action.dicesResult(0);

        // Aucun dé = somme 0
        assertEquals(0, result);
    }

    @Test
    void testAskNeighborThrowsWhenNoEnemies() {
        PlayerAres player = new PlayerAres("Attacker");
        List<PlayerAres> enemies = new ArrayList<>(); // vide

        AttackNeighboor action = new AttackNeighboor(player, enemies, new FixedIndexChooser<>(2),
                new FixedIndexChooser<>(2));

        assertThrows(InvalidChoiceException.class, () -> action.askNeighbor());
    }

    @Test
    void createEnnemiesTes() {
        List<PlayerAres> players = new ArrayList<>();
        PlayerAres player1 = new PlayerAres("toto");
        PlayerAres player2 = new PlayerAres("totpo");
        PlayerAres player = new PlayerAres("totpo");

        players.add(player1);
        players.add(player2);
        players.add(player);

        AttackNeighboor action = new AttackNeighboor(player, players, new FixedIndexChooser<>(0),
                new FixedIndexChooser<>(0));

        assertTrue(action.getEnemies().size() == 0);
        action.createEnnemies();
        assertFalse(action.getEnemies().size() == 0);
    }

}
