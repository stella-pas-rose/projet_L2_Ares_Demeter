package game.tuile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SeaTest {

    private Sea sea;

    @BeforeEach
    void setUp() {
        sea = new Sea();
    }
    
    // Verify that we correctly constructed a Sea tile
    @Test
    void testConstructor() {
        assertNotNull(sea);
    }
}
