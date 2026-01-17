package game.tuile.building;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.PlayerAres;
import game.tuile.Field;
import game.util.CantBuildException;
import game.util.NoMoreRessourcesException;

import static org.junit.jupiter.api.Assertions.*;


class CampTest{

  private Camp camp;
  private Field tuile;
  private Army army; 
  private PlayerAres player; 

 @BeforeEach
 void setUp() throws CantBuildException, NoMoreRessourcesException{
  player= new PlayerAres("c"); 
  tuile = new Field();
  army = new Army(tuile ,5, player);
  camp = army.upGradeToCampWithWarriors(player); 
  camp.addWarriors(3);
 }
/*
 * test the getAdditionalWarriors method : 
 * Tests whether the number of additional warriors beyond the Army limit is calculated correctly.
 */
@Test 
void getAdditionalWarriorTest(){
  assertEquals(3, camp.getAdditionalWarriors());
}




@Test
void getNameTest(){
  assertEquals("Camp", camp.getName());



}





}