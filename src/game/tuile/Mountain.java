package game.tuile;

/**
 * a Earth tile Mountain type
 */
public class Mountain extends Earth{
    private static final String SYMBOL = " M ";
    /** initialises a new tile of the type Mountain */
    public Mountain(){
        super(Ressource.ORE, SYMBOL);

    }

    public String toString(){
        return "Mountain "; 
    }

}