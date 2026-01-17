package game.tuile;

/**
 * a Earth tile Pasture type
 */
public class Pasture extends Earth{
   private static final String SYMBOL = " P ";
/** initialise a new tile of the type  pasture */

 public Pasture(){
    super(Ressource.SHEEP,SYMBOL);
 }

 public String toString(){
   return "Pasture "; 
}

}