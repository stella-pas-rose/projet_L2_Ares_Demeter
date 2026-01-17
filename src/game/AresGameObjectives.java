package game;

/** 
* represents the objective of a player in the Ares game
 * each objective has a type and a value to reach
 */
public class AresGameObjectives {

    public enum ObjectiveType {
        CONQUER_TILES,
        INVADE_ISLANDS,
        DETAIN_WARRIORS
    }

    private ObjectiveType type;
    private int value;

    public AresGameObjectives(ObjectiveType type, int value) {
        this.type = type;
        this.value = value;
    }

    public ObjectiveType getType() {
        return this.type;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        if (this.type == ObjectiveType.CONQUER_TILES){
            return "Conquer " + value + " tiles";
        }
        if (this.type == ObjectiveType.INVADE_ISLANDS){
            return "Invade " + value + " islands";
        }
        if ( this.type == ObjectiveType.DETAIN_WARRIORS){
            return "Detain " + value + " warriors";
        }
        return null; 
    }
}
