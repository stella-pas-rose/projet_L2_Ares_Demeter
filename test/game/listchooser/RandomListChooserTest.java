package game.listchooser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class RandomListChooserTest {

    @Test
    void testChooseRandomElement() {
        RandomListChooser<String> chooser = new RandomListChooser<>();
        
        List<String> options = List.of("army1", "army2", "army3");

        String result = chooser.choose("choose an army:", options);
        
        assertTrue(options.contains(result), "the chosen element should be in the list");
    }

    @Test
    void testChooseNoneIfListIsEmpty() {
        RandomListChooser<String> chooser = new RandomListChooser<>();

        List<String> emptyList = List.of();

        String result = chooser.choose("choose an army", emptyList);

        assertNull(result, "the result should be null if the list is empty");
    }
}
