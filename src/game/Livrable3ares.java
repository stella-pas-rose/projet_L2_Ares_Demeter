package game;

import java.io.IOException;

import game.tuile.Earth;
import game.tuile.Ressource;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;
import game.action.*;
import game.action.actionAres.BuildArmy;
import game.action.actionAres.BuySecretWeapon;
import game.action.actionAres.BuyWarriors;
import game.action.actionAres.DisplayWarriors;
import game.action.actionAres.UpgradeWithRessources;
import game.listchooser.RandomListChooser;

public class Livrable3ares {

    
    public static void main(String[] args) throws IOException, NoMoreRessourcesException, InvalidChoiceException, CantBuildException {
        if (args.length < 2) {
            System.out.println("You have to give two positive settings.");
            return;
        }
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);

        if (width < 10 || height < 10) {
            System.out.println("minimum dimensions must be 10");
            return;
        }
        
        System.out.println("--------------------");
        System.out.println("--------ARES--------");
        System.out.println("--------------------");
        System.out.println("\n"); 

        Board board = new Board(width, height);


        PlayerAres ares = new PlayerAres("ares");

        ares.addRessource(Ressource.WOOD, 10);
        ares.addRessource(Ressource.ORE, 10);
        ares.addRessource(Ressource.WEALTH, 10);
        ares.addRessource(Ressource.SHEEP, 10);

        /*
        1- construit une armée avec 1 guerrier
        2- ajoute des guerriers à son armée pour arriver à 5
        3- fait évoluer son armée en un camp
        4- achète 5 guerriers
        5- construit un port
        6- échange 3 ressources contre une
        7- achète une arme secrète
         */


        // 1. Build an army with 1 warrior
        System.out.println("===> ares "+ ares.getResources()+ " ("+ ares.getWarriors()+ " warriors)  veut construire une armee avec 1 guerrier");
        BuildArmy buildArmyAction = new BuildArmy(board, ares, new RandomListChooser<>(), new RandomListChooser<>());

        try {
            buildArmyAction.act(ares);

        } catch (CantBuildException e) {
           throw new CantBuildException("An error occurred while building the army: " + e.getMessage());
           
        }
        System.out.println("\n"); 

        //2. add warriors 
        System.out.println("===> ares "+ ares.getResources()+ " ("+ ares.getWarriors()+ " warriors)  ajoute des guerriers jusqu'à 5");

        DisplayWarriors displayWarriorsAction= new DisplayWarriors(ares, new RandomListChooser<>(),new RandomListChooser<>()); 
        try {
            displayWarriorsAction.act(ares);
        } catch (Exception e) {
            System.out.println("An error occurred while adding warriors: " + e.getMessage());
           
        }
        System.out.println("\n"); 

        //3. upgradeArmy 
        System.out.println("===> ares "+ ares.getResources()+ " ("+ ares.getWarriors()+ " warriors)  fait évoluer son armée en camp");
        UpgradeWithRessources upgradeArmy = new UpgradeWithRessources(ares, new RandomListChooser<>());
        try {
            upgradeArmy.act(ares);
            
        } catch (Exception e) {
            System.out.println("An error occurred while upgrading the army to camp: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n"); 

        //4. acheter 5 guerriers 
        System.out.println("===> ares "+ ares.getResources()+ " ("+ ares.getWarriors()+ " warriors)  achète 5 guerriers");
        BuyWarriors<PlayerAres> buyWarriorsAction= new BuyWarriors<PlayerAres>(ares); 
        try {
            buyWarriorsAction.act(ares);
        } catch (Exception e) {
            System.out.println("An error occurred while buying warriors: " + e.getMessage()); 
        }

        System.out.println("\n"); 
    
        //5. BuildPort
        System.out.println("===> ares "+ ares.getResources()+ " ("+ ares.getWarriors()+ " warriors)  construit un port");
        BuildPort<PlayerAres> buildPort = new BuildPort<PlayerAres>(ares, board, new RandomListChooser<>());
        try {
            buildPort.act(ares);            
        } catch (CantBuildException e) {
            throw new CantBuildException("An error occurred while building a Port: " + e.getMessage());
        } 

        System.out.println("\n"); 

        //6. échange 3 ressources contre une
        System.out.println("===> ares "+ ares.getResources()+ " ("+ ares.getWarriors()+ " warriors)  échange 3 WOOD contre 1 ORE");
        ExchangeRessources<PlayerAres> exchange = new ExchangeRessources<>(ares, new RandomListChooser<>());
        try {
            exchange.act(ares);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'échange : " + e.getMessage());
        }

        System.out.println("\n"); 

        //7. achète une arme secrète
        System.out.println("===> ares "+ ares.getResources()+ " ("+ ares.getWarriors()+ " warriors)  achète une arme secrète");
        BuySecretWeapon buy = new BuySecretWeapon(ares);
        try {
            buy.act(ares);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'achat : " + e.getMessage());
        }

        System.out.println("\n"); 

        // affichage des bâtiments et des tuiles du joueurs

        System.out.println("===> liste des bâtiments \n" + //
                        "Port(s): "+ ares.getPorts()+ "\n"+ //
                        "Army(ies):"+ ares.getArmies()+"\n"+//
                        "Camp(s):" +ares.getCamps());

        System.out.println("\n"); 

       System.out.println("===> liste des tuiles ");
        for(Earth t: ares.getTiles()){ 
            System.out.println(t.getPosition()); 
        }

        System.out.println("\n"); 

        board.display();  
    }
}
