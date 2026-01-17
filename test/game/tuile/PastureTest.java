package game.tuile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PastureTest {

    private Pasture pasture;

    @BeforeEach
    void setUp() {
        pasture = new Pasture();
    }
    
    // Verify that we correctly created an object of type Pasture and that it is not null
    @Test
    void testConstructor() {
        assertNotNull(pasture);
    }

    // Verify that the resource Sheep corresponds to the Pasture tile
    @Test
    void testGetRessource() {
        assertEquals(Ressource.SHEEP, pasture.getRessource());
    }
}
