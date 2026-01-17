package game.tuile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class RessourceTest {

    // Create an array that contains all the values of the enum: first, check if the list is not empty with assertNotNull
    // Then, check if the values are in the correct order with assertTrue
    @Test
    public void testEnumValues() {
        Ressource[] ressources = Ressource.values();
        assertNotNull(ressources);
        assertEquals(4, ressources.length);
        assertTrue(ressources[0] == Ressource.WOOD);
        assertTrue(ressources[1] == Ressource.WEALTH);
        assertTrue(ressources[2] == Ressource.ORE);
        assertTrue(ressources[3] == Ressource.SHEEP);
    }
}
