package game.listchooser;
import java.util.List;

import game.Player;
import game.listchooser.util.Input;

/** 
 * Allow the player to make choices by himself 
*/
public class InteractiveListChooser<T> implements ListChooser<T>{

    /**
    * prompts the user to choose an item from a list of type T
    * if the list is empty or the user chooses not to make a selection, null is returned
    * 
    * @param msg the prompt message
    * @param list the list of items to choose from
    * @return the selected item, or null if no valid choice is made
    */
    
    public T choose(String msg, List<? extends T> list) {
        if (list.isEmpty()) {
            return null;
        }
/** 
        if (list.size() == 1) {
            return list.get(0);
        }
        */
        int choice = -1;
        while ((choice < 0) || (choice > list.size())) {
            System.out.println(msg);
            System.out.println("      0 - none");
            int index = 1;
            for (T element : list) {
                if (element instanceof Player){
                    System.out.println("      " + (index++) + " - " + ((Player) element).getName());
                }

                else{
                System.out.println("      " + (index++) + " - " + element);
                }
            }
            System.out.println("            choice ?");
            try {
                choice = Input.readInt(); 
            } catch (java.io.IOException e) {
                System.out.println("Please, enter a number between 0 and " + (index-1));
            }
        }
        if (choice == 0) {
            return null;
        }
        return list.get(choice - 1);
    }
}
