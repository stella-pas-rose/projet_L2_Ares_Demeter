package game.tuile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field();    
    }
    
    // Verify that we correctly created an object of type Field and that it is not null
    @Test
    void testConstructor() {
        assertNotNull(field);
    }

    // Verify that the resource Wheat corresponds to the Field tile
    @Test
    void testGetRessource() {
        assertEquals(Ressource.WEALTH, field.getRessource());
    }
}
