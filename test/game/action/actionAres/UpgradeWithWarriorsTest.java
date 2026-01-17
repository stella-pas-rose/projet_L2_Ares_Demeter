package game.action.actionAres;


import game.PlayerAres;
import game.listchooser.FixedIndexChooser;

import game.tuile.Earth;
import game.tuile.Forest;
import game.tuile.building.Army;
import game.tuile.building.Camp;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;
import game.util.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class UpgradeWithWarriorsTest {

  private PlayerAres player;
  private Earth tuile;
  private Army army;

  @BeforeEach
  void setUp() throws CantBuildException {
    player = new PlayerAres("Player1");
    player.addWarriors(5);
    tuile = new Forest();
    tuile.setPosition(new Position(1, 1));
    army = new Army(tuile, 5, player);
    tuile.setBuilding(army);
    army.collectRessource(player);
    player.addArmy(army);
  }

  @Test
  void testUpgradeArmyToCampSuccessfully() throws Exception {
    UpgradeWithWarriors action = new UpgradeWithWarriors(
        player,
        new FixedIndexChooser<>(0),
        new FixedIndexChooser<>(0)
    );

    action.act(player);

    assertTrue(tuile.getBuilding() instanceof Camp);
    assertEquals(0, player.getArmies().size());
    assertEquals(1, player.getCamps().size());

    assertEquals(33, 33);

    Camp camp = (Camp) tuile.getBuilding();
    assertEquals(6, camp.getNbWarriors());
  }

  @Test
  void testFailsIfNoEligibleArmy() throws CantBuildException, NoMoreRessourcesException, InvalidChoiceException {
    Army weakArmy = new Army(new Forest(), 3, player);
    player.addArmy(weakArmy);

    UpgradeWithWarriors action = new UpgradeWithWarriors(
        player,
        new FixedIndexChooser<>(0),
        new FixedIndexChooser<>(0)
    );
    action.act(player);
    assertEquals(1, player.getCamps().size());
  }
}
