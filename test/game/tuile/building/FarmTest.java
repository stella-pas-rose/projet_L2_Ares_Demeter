package game.tuile.building;
import game.PlayerDemeter;
import game.tuile.Earth;

import game.tuile.Ressource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;



public class FarmTest{
  
    private Farm farm;
    private Earth tuile;
    
    private PlayerDemeter player;
    private Ressource ressource;
  
    @BeforeEach
    void setUp(){
      player = new PlayerDemeter("loulou");
      ressource = Ressource.SHEEP;
      tuile = new Earth(ressource,"F");
      farm = new Farm(tuile, player);
  
    }
    /*
     * Tests if a farm can not be upgraded to an exploitation if the player does not have enough ressources
     */
    @Test
    void canBeExploitationShouldReturnFalse(){
      assertFalse(farm.canBeExploitation(player));
  
    }
    /*
     * Tests if upgrading a farm to an exploitation returns null when the player lacks resources
     */
    @Test 
    void upGradeToExploitationShouldReturnNull(){
      assertEquals(farm.upGradeToExploitation(player),null);


  }


  /*
   * Tests if a farm can be upgraded when the player has enough resources
   */
  @Test
  void canBeExploitationShouldReturnTrue(){
    // providing enough resources 
    Ressource sheep =Ressource.SHEEP;
    Ressource wealth=Ressource.WEALTH;
    Ressource wood=Ressource.WOOD;

    // giving the player enough ressources to be able to upgrade a farm to an exploitation
    player.addRessource(sheep, 2);
    player.addRessource(wood, 2);
    player.addRessource(wealth, 2);
    assertTrue(farm.canBeExploitation(player));

  }

  /*
   * Tests if upgrading a farm correctly returns a new exploitation
   */
  @Test
  void  upGradeToExploitationShouldReturnNewExploitation(){
    Ressource sheep =Ressource.SHEEP;
    Ressource wealth=Ressource.WEALTH;
    Ressource wood=Ressource.WOOD;

    // giving the player enough ressources to be able to upgrade a farm to an exploitation
    player.addRessource(sheep, 2);
    player.addRessource(wood, 2);
    player.addRessource(wealth, 2);
    assertTrue(farm.upGradeToExploitation(player)!=null);
 
}


/*
 * Tests if the farm's name is correctly returned
 */
@Test 
void getNameShouldReturnFarm(){
  assertEquals(farm.getName(),"Farm");
}

/*
 * Tests if the farm's cost is correctly returned
 */
@Test
void farmCostTest(){
  assertEquals(farm.getCost().get(Ressource.WOOD),1);
  assertEquals(farm.getCost().get(Ressource.ORE),1);  
}













}