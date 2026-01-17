package game.tuile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.tuile.building.*;
import game.util.CantBuildException;

import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.*;


class EarthTest{
    private Army army ;
    private Earth tuile1;
    private Earth tuile2;
    private Ressource ressource1;
    private Ressource ressource2;

    @BeforeEach
    void setUp() throws CantBuildException {
      ressource1= Ressource.WOOD;
      ressource2=Ressource.WOOD;

      tuile1= new Earth(ressource1,"F");
      tuile2=new Earth(ressource2,"F");
      
        army = new Army(tuile1 ,0, null);   
        
    }

    @Test
    void TestAddingABuildingToATile(){
      
      tuile1.setBuilding(army);
      assertEquals(army , tuile1.getBuilding());

    }



    @Test 
    void TestGetBuildingOnATileWithoutABuilding(){
        
       assertTrue( tuile2.getBuilding()==null);
    }

    @Test
    void TestHaveBuild(){
      
      tuile1.setBuilding(army);
      assertTrue(tuile1.haveBuild());
  }

    @Test
    void TestGetRessource(){
      Ressource ressource = Ressource.SHEEP;
      Earth tuile= new Earth(ressource,"F");
      assertEquals(tuile.getRessource(),ressource);
    }

     @Test 
      void TestGetSymbol(){
      Ressource ressource = Ressource.SHEEP;
      Earth tuile= new Earth(ressource,"F");
      assertTrue(tuile.getSymbol()=="F");


    }

  
    }



    

