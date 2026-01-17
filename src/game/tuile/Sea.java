package game.tuile;

public class Sea extends Tuile{
    public static final String BLUE = "\u001B[34m";  // the color of the tile
    private static final String SYMBOL = " ~ ";
    /** initiales a new tile of type sea */
    public Sea(){
        super(SYMBOL);
    }
}