package game;
import java.util.List;
import game.tuile.building.*;
import game.tuile.*;
import game.util.CantBuildException;
import game.util.Position;

public class Livrable2 {
    public static void main (String[] args) throws CantBuildException{
        if (args.length < 2) {
            System.out.println("you have to give two positive setting");
            return;
        }
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);

        if (width < 10 || height < 10) {
            System.out.println("minimum dimensions must be 10");
            return;
        }
        Board board = new Board(width, height);
        board.display(); 
        placeBuildings(board);

    }
   private static void placeBuildings(Board board) throws CantBuildException {
    PlayerAres ares = new PlayerAres("ares");
    PlayerDemeter demeter = new PlayerDemeter("demeter");

    List<Position> buildableTiles = board.getBuildablePositions();

    Earth portTile = (Earth) board.getTile(buildableTiles.get(0));
    Earth farmTile = (Earth) board.getTile(buildableTiles.get(1));
    Earth exploitationTile = (Earth) board.getTile(buildableTiles.get(2));
    Earth armyTile = (Earth) board.getTile(buildableTiles.get(3));
    Earth campTile = (Earth) board.getTile(buildableTiles.get(4));

    Port port = new Port(portTile, ares);
    Farm farm = new Farm(farmTile, demeter);
    Exploitation exploitation = new Exploitation(exploitationTile, demeter);
    Army army = new Army(armyTile, 5, ares);
    Camp camp = new Camp(campTile, 20, ares);

    portTile.setBuilding(port);
    farmTile.setBuilding(farm);
    exploitationTile.setBuilding(exploitation);
    armyTile.setBuilding(army);
    campTile.setBuilding(camp);

    port.displayCost();
    farm.displayCost();
    exploitation.displayCost();
    army.displayCost();
    camp.displayCost();

    System.out.println("--------------------------------------");

    board.displayBuildings();

    System.out.println("--------------------------------------");

    port.collectRessource(ares);
    farm.collectRessource(demeter);
    exploitation.collectRessource(demeter);
    army.collectRessource(ares);
    camp.collectRessource(ares);

    board.display(); 
}



    
}
