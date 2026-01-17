package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import game.AresGameObjectives.ObjectiveType;

public class AresGameObjectivesTest{





 @Test
 public void  ObjectiveGetTypeTest(){
  AresGameObjectives objective = new AresGameObjectives(
            ObjectiveType.CONQUER_TILES, 10
        );
        assertEquals(ObjectiveType.CONQUER_TILES, objective.getType());


 AresGameObjectives objective2 = new AresGameObjectives(
          ObjectiveType.DETAIN_WARRIORS, 10
      );
      assertEquals(ObjectiveType.DETAIN_WARRIORS, objective2.getType());



      AresGameObjectives objective3 = new AresGameObjectives(
        ObjectiveType.INVADE_ISLANDS, 10
    );
    assertEquals(ObjectiveType.INVADE_ISLANDS, objective3.getType());




 }

 @Test
  public void testGetValue() {
     AresGameObjectives objective = new AresGameObjectives(
        ObjectiveType.INVADE_ISLANDS, 5
     );
     assertEquals(5, objective.getValue());


     AresGameObjectives objective2 = new AresGameObjectives(
      ObjectiveType.INVADE_ISLANDS, 20
   );
   assertEquals(20, objective2.getValue());


  }

   @Test
   public void testToString() {
       AresGameObjectives objective = new AresGameObjectives(
          ObjectiveType.CONQUER_TILES, 20 );
       assertEquals("Conquer 20 tiles", objective.toString());




    AresGameObjectives objective2 = new AresGameObjectives(
       ObjectiveType.INVADE_ISLANDS, 15);
    assertEquals("Invade 15 islands", objective2.toString());

    AresGameObjectives objective3 = new AresGameObjectives(
      ObjectiveType.DETAIN_WARRIORS, 7
  );
  assertEquals("Detain 7 warriors", objective3.toString());
}


  }
   


   
 




