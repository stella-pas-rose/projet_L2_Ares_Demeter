package game.tuile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MountainTest {

    private Mountain mountain;

    @BeforeEach
    void setUp() {
        mountain = new Mountain();
    }

    // Verify that we correctly created an object of type Mountain and that it is not null
    @Test
    void testConstructor() {
        assertNotNull(mountain);
    }
    
    // Verify that the resource Ore corresponds to the Mountain tile
    @Test
    void testGetRessource() {
        assertEquals(Ressource.ORE, mountain.getRessource());
    }
}
