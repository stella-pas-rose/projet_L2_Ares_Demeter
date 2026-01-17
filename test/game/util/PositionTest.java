package game.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void testGetX() {
        Position position = new Position(1, 2);
        assertEquals(1, position.getX());
    }

    @Test
    public void testGetY() {
        Position position = new Position(1, 2);
        assertEquals(2, position.getY());
    }

    @Test
    public void testNext() {
        Position position = new Position(1, 2);
        // Test the movement to the North (N)
        Position next = position.next(Direction.N);
        assertEquals(new Position(2, 2), next);

        // Test the movement to the South (S)
        next = position.next(Direction.S);
        assertEquals(new Position(0, 2), next);

        // Test the movement to the West (O)
        next = position.next(Direction.O);
        assertEquals(new Position(1, 1), next);

        // Test the movement to the East (E)
        next = position.next(Direction.E);
        assertEquals(new Position(1, 3), next);
    }

    @Test
    public void testToString() {
        Position position = new Position(1, 2);
        assertEquals("(1,2)", position.toString());
    }
}
