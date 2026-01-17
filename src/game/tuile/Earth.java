package game.tuile;

import game.tuile.building.*;

/**
 * A Earth Tile
 */
public class Earth extends Tuile {

    protected Ressource ressource; // the ressource the tile produce
    protected String symbol; // the symbol of the tile
    protected Building building; //if a tile have one 
   



    /** 
     * initializes a new Earth tile with a given ressource and symbol
     * @param ressource the ressource type of this tile
     * @param symbol the symbol representing this tile
     */
    public Earth(Ressource ressource, String symbol) {
        super(symbol);
        this.ressource = ressource;
        this.symbol = symbol;
        this.building = null; // genre au debut on a aucun batiment dessus
        

        
    }

    /** 
     * returns the resource type of this tile
     * @return the resource of the tile
     */
    public Ressource getRessource() {
        return this.ressource;
    }

    /**
     * checks if this tile has a building
     * @return true if there is a building false otherwise
     */
    public boolean haveBuild() {
        return this.building != null;
    }

    /** 
     * sets the given building to this tile if no building has been constructed on it
     * @param building the building to be  place on the tile
     */
    public void setBuilding(Building building) {
        if (this.haveBuild()){
            System.out.println("This tile already has a building.");
            return;
        }
        this.building = building;
    }

    /** 
     * returns the building on this tile, or null if there is none
     * @return the building or null if no building  placed
     */
    public Building getBuilding() {
        return this.building;
    }
    
    /** 
     * removes the building from this tile
     * @return true if the building was removed, false if there was no building to remove
     */
    public boolean removeBuilding() {
        if (this.haveBuild()) {
            this.building = null;
            System.out.println("The building has been removed");
            return true;
        } else {
            System.out.println("No building to remove");
            return false;
        }
    }


}
