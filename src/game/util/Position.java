package game.util;
/**
 * A class to create a position on a grid
 */
public class Position{
    private int x;
    private int y;

    /**
     * build the position
     * @param x int
     * @param y int
     */
    public Position(int x, int y){
        this.x= x;
        this.y= y;
    }

    /**
     * return the value of x
     * @return int
     */
    public int getX(){
        return this.x;
    }

    /**
     * return the value of y 
     * @return int
     */
    public int getY(){
        return this.y;
    }

    /**
     * add the the value of the direction of to x and y
     * @param d, the direction we add
     * @return Position , the previous position plus the direction 
     */
    public Position next(Direction d){
        return new Position((this.x + d.getDx()),(this.y + d.getDy()) );
    }


    /**
    * this method checks if two position objects are equal
    * @param o the object to compare with the current Position object
    * @return true if the two positions have the same x and y
    *         false if they are different or if the object is not a Position
    */
    public boolean equals(Object o) {
        if (!(o instanceof Position)) {
            return false; 
        }
        Position other = (Position) o;
        return (this.x == other.x) && (this.y == other.y);
    }

    /**
     * return a description of the position
     * @return String, the description 
     */
    public String toString(){
        return "("+this.x+","+this.y+")";
    }

}