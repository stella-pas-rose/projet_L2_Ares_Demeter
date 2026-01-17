package game.listchooser;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class InteractiveListChooserTest {

    @Test
    void testChooseCorrectElement() {
        
        // genre ByteArrayInputStream ça permet de simuler le choix 2 qui est du coup army2
        String simulatedInput = "2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); 

        InteractiveListChooser<String> chooser = new InteractiveListChooser<>();
        
        List<String> options = List.of("army1", "army2", "army2");

        String result = chooser.choose("choose an army:", options);

        assertEquals("army2", result);
    }
}
