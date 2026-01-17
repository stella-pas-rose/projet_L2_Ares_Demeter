package game.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DirectionTest {

    @Test
    public void testGetDx() {
        // To verify the x movements for each direction
        assertEquals(1, Direction.N.getDx());
        assertEquals(-1, Direction.S.getDx());
        assertEquals(0, Direction.O.getDx());
        assertEquals(0, Direction.E.getDx());
    }

    @Test
    public void testGetDy() {
        // To verify the y movements for each direction
        assertEquals(0, Direction.N.getDy());
        assertEquals(0, Direction.S.getDy());
        assertEquals(-1, Direction.O.getDy());
        assertEquals(1, Direction.E.getDy());
    }
}
