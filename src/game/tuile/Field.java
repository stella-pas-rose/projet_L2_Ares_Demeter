package game.tuile;

/**
 * A Earth tile Field type
 */
public class Field extends Earth {
    private static final String SYMBOL = " C ";

    /** initializes a new tile of the type field */

    public Field(){
        super(Ressource.WEALTH,SYMBOL);
    }

    public String toString(){
        return "Field "; 
    }

}