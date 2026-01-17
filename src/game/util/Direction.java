package game.util;

/**
 * Enum representing the possible directions for checking the borders of tiles.
 */
public enum Direction {
    N(1, 0),   // north (up)
    S(-1, 0),  // south (down)
    O(0, -1),  // west (left)
    E(0, 1);   // east (right)

    private final int dx;
    private final int dy;

    /**
     * Constructor to define the movement in x and y directions.
     *
     * @param dx the movement in the x direction
     * @param dy the movement in the y direction
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the movement in the x direction for this direction.
     *
     * @return dx the movement in the x direction
     */
    public int getDx() {
        return this.dx;
    }

    /**
     * Returns the movement in the y direction for this direction.
     *
     * @return dy the movement in the y direction
     */
    public int getDy() {
        return this.dy;
    }
}
