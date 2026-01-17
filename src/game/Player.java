package game;

import java.util.*;
import game.tuile.building.*;
import game.util.NoMoreRessourcesException;
import game.tuile.*;

public class Player {

    private String name;
    protected HashMap<Ressource, Integer> ressources;
    protected  ArrayList<Earth> playerTiles;
    private List<Port> ports;

    /** 
     * creates a player with the given name and the initial ressources
     * @param name  name of the player
     */ 
    public Player(String name) {
        this.name = name;
        this.ports = new ArrayList<>();
        this.ressources = new HashMap<>();
        this.ressources.put(Ressource.WOOD, 0);
        this.ressources.put(Ressource.ORE, 0);
        this.ressources.put(Ressource.WEALTH, 0);
        this.ressources.put(Ressource.SHEEP, 0);
        this.playerTiles = new ArrayList<>();
    }


    /** 
     * returns the player's name
     * @return player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * gets the ports of this player
     * @return the ports of the player
     */
    public List<Port> getPorts(){
        return this.ports;
    }

    /**
     * adds the given port to this player
     * @param port the port to add
     */
    public void addPort(Port port){
        this.ports.add(port);
        this.playerTiles.add(port.getTuile()); 
    }

    /**
     * getter for playerTiles
     * @return playerTiles
     */
    public ArrayList<Earth> getTiles() {
        return this.playerTiles;
    }

    

    /**
     * adds the given tile to the player's list of tiles
     * @param tile the tile
     */
    public void addTile(Earth tile) {
        if(!this.playerTiles.contains(tile)){
            this.playerTiles.add(tile);
        }
    }
    /**
     * returns the earth at the given position
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the earth at (x,y) or null if not found
     */
    public Earth getEarth(int x,int y){
        for(Earth tuile : this.playerTiles){
            if(tuile.getPosition().getX() == x && tuile.getPosition().getY() == y){
                return tuile;
            }   
        }
        return null;
    }

    /** 
     * returns the ressources of this player
     * @return resource map
     */
    public HashMap<Ressource, Integer> getResources() {
        return this.ressources;
    }
    
    /** 
     * adds the given amount of the  given type of resource to the player's stock 
     * @param resource the resource to add
     * @param nb amount to add
     */
    public void addRessource(Ressource resource, int nb) {
        this.ressources.put(resource, ressources.getOrDefault(resource, 0) + nb);
    }

    /** 
     * removes the given amount of the give type of resource from the player's stock
     * @param ressource resource to remove
     * @param nb amount to remove
     * @exception NoMoreRessourcesException if you don't have enough ressources
     */
    public void removeRessource(Ressource ressource, int nb) throws NoMoreRessourcesException{
        if ((ressources.getOrDefault(ressource, 0) - nb)<0 ){
            throw new NoMoreRessourcesException("you have less than"+ nb + ressource);
        }
        this.ressources.put(ressource, ressources.getOrDefault(ressource, 0) - nb);
    }

    /** 
     * checks if the player has enough resources to build the given building 
     * @param building the building the player wants to build
     * @return true if the player has  enough resources false otherwise
     */
    public boolean hasEnoughRessources(Building building) {
        HashMap<Ressource, Integer> cost = building.getCost();
        for (Ressource resource : cost.keySet()) {
            if (ressources.getOrDefault(resource, 0) < cost.get(resource)) {
                return false;
            }
        }
        return true;
    }


    /**
     *  returns the amount of the given ressource the player owns
     * @param resource the ressource
     * @return the amount of the ressource
     */
    public int getRessourceAmount(Ressource resource) {
        return this.ressources.getOrDefault(resource, 0);
    }

    





    
}



