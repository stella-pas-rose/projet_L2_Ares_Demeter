package game.tuile;

/**
 * a Earth tile Forest type
 */
public class Forest extends Earth{
    private static final String SYMBOL = " F ";
/** initialises  a new tile of the type forest */
    public Forest(){
       super(Ressource.WOOD,SYMBOL); 
    }

    public String toString(){
        return "Forest "; 
    }

}