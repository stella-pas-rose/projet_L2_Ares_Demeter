package game.tuile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ForestTest {

    private Forest forest;

    @BeforeEach
    void setUp() {
        forest = new Forest();
    }
    
    // Verify that we correctly created an object of type Forest and that it is not null
    @Test
    void testConstructor() {
        assertNotNull(forest);
    }
    
    // Verify that the resource Wood corresponds to the Forest tile
    @Test
    void testGetRessource() {
        assertEquals(Ressource.WOOD, forest.getRessource());
    }
}
