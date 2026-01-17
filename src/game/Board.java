package game;
import java.util.*;

import game.tuile.*;
import game.util.*;

/**
 * A board of a given height and width
 */
public class Board{
    private int width;
    private int height;
    private Tuile[][] grid;
    public List<List<Earth>> islands  ; 
    private static final String RESET = "\u001B[0m";


/** initialises a new board for the game  with the given height and the given width
 * then fills every case of the board with a sea tile (it is not the final board it would be modified later to add earth tiles)
 * @param width the width of the board
 * @param height the height of the board
*/
public Board(int width , int height){
    this.width=width;
    this.height=height;
    this.grid= new Tuile[width][height];
    for (int x=0; x<width; x++){
        for (int y=0; y<height; y++){
            this.grid[x][y]= new Sea();
            }
        }
    createBoard();
    this.islands = this.findIslands();
}


/** return the board
 * @return the grid of the board
 */
public Tuile[][] getGrid(){
    return this.grid;
}

/** returns the width of the board chosen by the user
 * @return the width  of the board
 */

public int getWidth(){
    return this.width;
}

/** return the height of the board chosen by the user
 * @return height of the board
 */
public int getHeight(){
    return this.height;
}

/**
 * returns the tile at the given position
 * @param pos the position
 * @return the tile at the given position
 */
public Tuile getTile(Position pos){
    if(this.isValidPosition(pos)){
        return this.grid[pos.getX()][pos.getY()];
    }
    return null ;
}



/**
 * get the list of islands on the board
 * @return the list of islands
 */
public List<List<Earth>> getIslands(){
    return this.islands;
}


/**
 * get the island of the given earth
 * @param earth the earth
 * @return the island of the given earth
 */
public List<Earth> getIsland(Earth earth){
    for (List<Earth> island : this.islands){
        if (island.contains(earth)){
            return island;
        }
    }
    return null;
}


/**
 * returns the color for the given tile
 * @param tuile the tile
 * @return the color
 */
public static String getColorForTuile(Tuile tuile) {
        if (tuile instanceof Forest) return "\u001B[48;5;22m";      
        if (tuile instanceof Mountain) return "\u001B[100m";    
        if (tuile instanceof Pasture) return "\u001B[103m";     
        if (tuile instanceof Field) return "\u001B[48;5;94m";       
        if (tuile instanceof Sea) return "\u001B[48;5;17m";         
        return "\u001B[47m";                                   
    }
    

/*** displays a  board for the game with tiles with random type placed on random positions */
public void display(){
    int w = this.getWidth();
    int h = this.getHeight();
   
    System.out.println("GAME BOARD :");
    System.out.println("\n Légende des tuiles :");


    System.out.print(getColorForTuile(new Sea()) + "     " + RESET + " : Sea     ");
    System.out.print(getColorForTuile(new Forest()) + "     " + RESET + " : Forest     ");
    System.out.print(getColorForTuile(new Pasture()) + "     " + RESET + " : Pasture     ");
    System.out.print(getColorForTuile(new Mountain()) + "     " + RESET + " : Mountain     ");
    System.out.print(getColorForTuile(new Field()) + "     " + RESET + " : Field     ");
    System.out.println();


    System.out.println("\n Légende des Batiments :");
    System.out.println(" a : Army");
    System.out.println(" c : Camp");
    System.out.println(" f : Farm");
    System.out.println(" e : Exploitation");
    System.out.println(" p : Port");

    
    System.out.println("        ");
    System.err.print("     ");
    for (int i = 0; i < w; i++) {
       
        System.out.printf("%2d | ", i);
    }
    System.out.println();

    for (int y = 0; y < h; y++) {
        System.out.printf("%2d |", y); // Affiche le numéro de la ligne
        for (int x = 0; x < w; x++) {
            Tuile t = this.grid[x][y];
            
            String symbole = "     ";
            if (t instanceof Sea) {
                symbole =" " + t.getSymbol()+" ";
            }

            if (t instanceof Earth && t.haveBuild()) {
                symbole = "  " + ((Earth)t).getBuilding().getSymbol()+"  ";
            }

            String color = getColorForTuile(t);
            System.out.print(""+color+""+symbole+""+ RESET + "");
        }
        System.out.println(); // saut de ligne à la fin de chaque rangée
    }
}

/**
 * Displays the board with symbols enclosed in brackets [ ].
 */ 
/**public void display(){
    int w = this.getWidth();
    int h = this.getHeight();
   
    System.out.println("GAME BOARD :");
    System.out.println("\n Légende des tuiles :");
    System.out.println(new Sea().getSymbol() + " : Sea");
    System.out.println(new Forest().getSymbol() + " : Forest");
    System.out.println(new Pasture().getSymbol() + " : Pasture");
    System.out.println(new Mountain().getSymbol() + " : Mountain");
    System.out.println(new Field().getSymbol() + " : Field");
    
    System.out.println("        ");
    for (int x = 0; x < w; x++) {
        if(x==0){
            System.out.print("|-----|");
        }
        else {
            System.out.print("------|");
        }
       
    } System.out.println();
    for(int  y=0; y < h ; y++){
        System.out.print("|");
        for (int x=0 ; x < w; x++){
            String symbole = grid[x][y].getSymbol() ;
            System.out.print(  symbole + " | ");
        }
        System.out.println(); 
        // We put a separating line between the lines but not the last one.
        if (y<h-1){
            System.out.print("|");
            for (int x = 0; x < w; x++) {
                if(x==0){
                System.out.print("_____|");}
                else {
                    System.out.print("______|");}
            }
            System.out.println();
        }
    }
    for (int x = 0; x < w; x++) {
            if(x==0){
                System.out.print("|-----|");
            }
            else {
                System.out.print("------|");
            }
        }
        System.out.println();
        
}*/

/** creates a new board for the game randomly */
private void createBoard(){
    this.placeInitialeTiles();
    this.placeNeighboorEarthTiles();
}

/**
 * checks if the position is within the board's boundaries
 *
 * @param pos the position to check
 * @return true if the position is inside the board, false if outside
 */
public boolean isValidPosition(Position pos) {
    return pos.getX() >= 0 && pos.getX() < this.width &&
           pos.getY() >= 0 && pos.getY() < this.height;
}

/**
 * checks if the tile at the given position is of type Earth because Earth is buildable 
 *
 * @param pos the position to check
 * @return true if the tile is Earth, false otherwise
 */
public boolean isBuildable(Position pos){
    return this.grid[pos.getX()][pos.getY()] instanceof Earth  && !((Earth)this.grid[pos.getX()][pos.getY()]).haveBuild();
}


/** return true if the given position is the sea, false otherwise
 * @param pos the position 
 * @return boolean true if the tile il the sea, false otherwise
 * */
public boolean isEmpty(Position pos) {
   /*f(!isValidPosition(pos)){
        return false;
    }
    Tuile tile = getTile(pos);
    if (tile instanceof Sea) {
        return true;
    }
    else if(isBuildable(pos)){
        Earth earthTile = (Earth) tile;
        return earthTile.getBuilding() ==null ;
    }*/
    
 return isValidPosition(pos) && !(this.grid[pos.getX()][pos.getY()] instanceof Earth)   ;
   
}


/** return true if the tile have a neighbor, false otherwise 
 * @param pos the position
 * @return boolean true if the tile have a neighbor 
*/
public boolean haveNeighbor(Position pos) {
    for (Direction d : Direction.values()) {
        Position neighbor = pos.next(d);
        if (this.isValidPosition(neighbor) && !this.isEmpty(neighbor)) {
            return true;
        }
    }
    return false;
}

/** put the tile on the given position ans set the position to the tile
 * @param t the tile
 * @param pos a position
 */
public void put(Tuile t, Position pos){
    t.setPosition(pos);
    this.grid[pos.getX()][pos.getY()]= t;

}


/** return an empty random position if found within less 100 attempts, if not found return null
* @return a random position or null if not found
*/
private Position randomPosition() {
    Random randomNumbers = new Random();
    int cpt = 0;
    Position randomPos;
    do {
        randomPos = new Position(randomNumbers.nextInt(this.width), randomNumbers.nextInt(this.height));
    } while (!this.isEmpty(randomPos) && cpt++ < 100);
    return cpt < 100 ? randomPos : null;  
}



/** returns the number of the earth tiles that we should place on the board using the width and the height
 * of the said board
 * @return number of earth tiles to be palced on the board
 * 
 */
public int tileNumber(){
    return (int)(this.getHeight() * this.getWidth() * 1.0 / 3);
}

/** places half of the 1/6 number of tiles randomly on the board */
private void placeInitialeTiles(){
    int nbTuile= this.tileNumber();
    int nbTuileInit = nbTuile/2;
    for (int i=0 ;i< nbTuileInit;i++){
        Position pos = this.randomPosition();
        Tuile tuile = this.randomTuile();
        this.put(tuile, pos);
    }
}
/// Test +
/**
 * returns the list of all the neighbors of a given position
 * @param pos the position
 * @return the list of neighbors
 */
public List<Tuile> getNeighbours(Position pos){
    List<Tuile> neighbours = new ArrayList<>();

    for (Direction d : Direction.values()) {
        Position neighbourPos = pos.next(d);
        if (this.isValidPosition(neighbourPos)) {
            neighbours.add(this.getTile(neighbourPos));
        }
    }
    return neighbours;


}


/** return the list of all the empty neighbor around a position
 * @param pos the position
 * @return the list of the empty neighbor
 */
public ArrayList<Position> haveEmptyNeighboorList(Position pos){
    ArrayList<Position> res= new ArrayList<>();
    for (Direction d: Direction.values()){
        Position neighbor= pos.next(d);
        if (this.isEmpty(neighbor)){
            res.add(neighbor) ;
        }
    }
    return res ;
}

/** place a random tile next to a tile who doesn't have a neighbor
 * for the ENTIRETY  of the board it also returns the number of remaining tiles to place
 * @return nbre of remaining tiles to place
 */
private int placeNeighboorEarthTiles(){
    int nbredetuilerestante= this.tileNumber() / 2;
    for (int x=0; x<this.getWidth(); x++){
        for (int y=0; y<this.getHeight(); y++){
            Position currentPos= new Position(x, y);
            if (!this.isEmpty(currentPos) && !this.haveNeighbor(currentPos)){
                ArrayList<Position> Npos= this.haveEmptyNeighboorList(currentPos);
                Random choiceRandom = new Random();
                int random= choiceRandom.nextInt(Npos.size());
                this.put( this.randomTuile(), Npos.get(random));
                nbredetuilerestante-=1;
            }
        }
    }
    return nbredetuilerestante;

}
/* return a random tile of a random type
 * @return a random tile 
*/
private Tuile randomTuile(){
    Map<Integer,Tuile> tuileTypes= new HashMap<Integer,Tuile>();
    tuileTypes.put(0,new Forest() );
    tuileTypes.put(1,new Mountain());
    tuileTypes.put(2,new Pasture()) ;
    tuileTypes.put(3,new Field());

    Random choiceRandom = new Random();
    int random = choiceRandom.nextInt(4);

    return tuileTypes.get(random);
}

/**
 * returns a list of all buildable positions on the board
 * A position is buildable if it is not a Sea tile
 *
 * @return a list of buildable positions
 */
public List<Position> getBuildablePositions() {
    List<Position> buildableTiles = new ArrayList<>();
    for (int x = 0; x < this.width; x++) {
        for (int y = 0; y < this.height; y++) {
            Position pos = new Position(x, y);
            if (this.isBuildable(pos)) { 
                buildableTiles.add(pos);
            }
        }
    }
    return buildableTiles;
}

/**
 * displays the buildings placed on Earth tiles in the grid with their positions
 */
public void displayBuildings() {
    for (int x = 0; x < this.width; x++) {
        for (int y = 0; y < this.height; y++) {
            Tuile tuile = this.grid[x][y];
            if (tuile instanceof Earth) {
                Earth earthTile = (Earth) tuile;
                if (earthTile.haveBuild()) {
                    System.out.println(earthTile.getBuilding().getName() + " has been set on " + tuile.getSymbol() + "(" + x + "," + y + ")");
                }
            }
        }
    }
}


/**
     * return the number of sea tiles around the port
     * @param pos the position
     * @return int the number of sea tiles around the port
     */
    public int nbSeaTiles(Position pos) {
        int nbSeaTiles = 0;
        for (Direction d : Direction.values()) {
            Position neighbor = pos.next(d);
            if (this.isValidPosition(neighbor)) {
                Tuile neighborTile = this.getTile(neighbor);
                if (neighborTile instanceof Sea) {
                    nbSeaTiles += 1;
                }
            }
        }
        return nbSeaTiles;
    }



    
    
   
    /**
     * Explores an island starting from a given Earth tile and returns a list of all connected Earth tiles.
     * This method uses a breadth-first search (BFS) algorithm to traverse the island.
     *
     * @param Start the starting Earth tile from which the exploration begins
     * @param visited a set of Earth tiles that have already been visited
     * @return a list of Earth tiles representing the explored island
     */
    public List<Earth> exploreIsland(Earth Start  , Set<Earth> visited){

        List<Earth> island = new ArrayList<>();
        Queue<Earth> queue = new LinkedList<>();
        
        
        queue.add(Start);
        visited.add(Start);

        while(!queue.isEmpty()){

            Earth current = queue.poll();
            island.add(current);

            //verifier les tuiles voisines
            for (Tuile neighbour : this.getNeighbours(current.getPosition())){
                if (neighbour instanceof Earth){
                    Earth neighbourEarth = (Earth) neighbour;

                    // ajouter a la file si elle n'a pas encore ete visitee
                    if (!visited.contains(neighbourEarth)){
                        queue.add(neighbourEarth);
                        visited.add(neighbourEarth);
                    }
                }
            }
        }
        return island;

    } 



    
    /**
     * Finds and returns a list of islands on the board. An island is defined as a 
     * contiguous group of Earth tiles. This method iterates through all the tiles 
     * on the board, and for each Earth tile that has not been visited, it explores 
     * the island starting from that tile and adds it to the list of islands.
     *
     * @return a list of islands, where each island is represented as a list of Earth tiles.
     */
    public List<List<Earth>> findIslands(){
        List<List<Earth>> islands = new ArrayList<>();
        Set<Earth> visited = new HashSet<>();

        for (int x=0 ; x<this.width; x++){
            for(int y=0; y<this.height; y++){

                Tuile tile = this.getTile(new Position(x, y));

                if(tile instanceof Earth){
                    Earth earthTile = (Earth) tile ;

                    if(!visited.contains(earthTile)){

                        List<Earth> island =exploreIsland(earthTile,  visited);
                        islands.add(island);
                    }
                }

            }

        }
        return islands;


    }
    
    /**
     * Retrieves a random buildable tile from the board.
     * A buildable tile is an instance of Earth that does not have a building on it.
     * 
     * @return a random Earth object that is buildable, or null if no buildable tiles are available.
     */
    public Earth getRandomBuildableTile(){
        Random random = new Random();
        List<Earth> buildableEarths = new ArrayList<>();

        for (List<Earth> island : this.islands ){
            for(Earth tile : island){
                if(!tile.haveBuild()){
                    buildableEarths.add(tile);
                }
            }
        }
        if (!buildableEarths.isEmpty()){
            return buildableEarths.get(random.nextInt(buildableEarths.size()));
        }
        return null;
    }

    public List<Earth> coastalTiles(){
        List<Earth> res= new ArrayList<>(); 
        for (List<Earth> t : this.islands){
            for ( Earth e : t){
                if (this.isBuildable(e.getPosition())){
                    for (Direction d : Direction.values()) {
                        Position neighbor = e.getPosition().next(d);
                        Tuile neighborTile = getTile(neighbor);
                        if (neighborTile instanceof Sea && this.nbSeaTiles(e.getPosition()) >= 2) {
                            res.add(e); 
                            break; 
                        }
                    }
                }
            }
        }
        return res; 
    }


    public List<Earth> buildableTiles(){
        List<Earth> res= new ArrayList<>(); 
        for (List<Earth> t : this.getIslands()){
            for ( Earth e : t){
                if (!e.haveBuild()){
                   res.add(e); 
                }
            }
        }
        return res; 
    }


    
    
    





}
