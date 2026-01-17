package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.listchooser.InteractiveListChooser;
import game.listchooser.util.Input;
import game.tuile.Earth;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;

public class Demeter {

    public static void main(String[] args) throws IOException, InvalidChoiceException, CantBuildException, NoMoreRessourcesException{
        if (args.length < 3) {
            System.out.println("You have to give two positive settings and the number of player.");
            return;
        }

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int nbPlayer= Integer.parseInt(args[2]); 

        if (width < 10 || height < 10 ) {
            System.out.println("minimum dimensions must be 10");
            return;
        }

        if (nbPlayer < 2 ) {
            System.out.println("You should play with at least two player");
            return;
        }

        System.out.println("---------------------");
        System.out.println("-------DEMETER-------");
        System.out.println("---------------------");
        System.out.println("\n"); 

        Board board = new Board(width, height);

        List<PlayerDemeter> players= new ArrayList<>(); 
        PlayerDemeter winner= null; 

        //création des différents joueurs
        for (int i=0; i<nbPlayer; i++){
            System.out.println("j"+(i+1)+" name:");
            String name= Input.readString(); 
            players.add(new PlayerDemeter(name)); 
        }

        //on initialise la liste des actions pour chaque joueurs
        for (PlayerDemeter p: players){
            p.createActions(board, 0, players);
        }

        System.out.println("\n");
        System.out.println("TIME TO BUILD YOUR FARMS !");
        System.out.println("\n");


        // premier tour : ordre croissant
        for (int i=0 ;i<players.size();i++){
            System.out.println(players.get(i).getName()+"( " + players.get(i).getResources() + ") place your first initial farm: ");
            boolean isPlace= false; 
            while (!isPlace){
                try {
                    players.get(i).placeInitialFarm(board, new InteractiveListChooser<Earth>()); 
                    isPlace= true; 
                } catch (InvalidChoiceException | NullPointerException | IllegalArgumentException e) {
                    System.out.println("Please build a farm.");
                }
            }
            
        }


        // deuxieme tour : ordre decroissant
        for (int i=players.size()-1 ;i>=0;i--){
            System.out.println(players.get(i).getName()+"( " + players.get(i).getResources() + ")  place your second initial farm:");
            boolean isPlace= false; 
            while (!isPlace){
                try {
                    players.get(i).placeInitialFarm(board, new InteractiveListChooser<Earth>()); 
                    isPlace= true; 
                } catch (InvalidChoiceException | NullPointerException | IllegalArgumentException e) {
                    System.out.println("Please build a farm.");
                }
            }
        }

        System.out.println("\n");
        System.out.println("THE GAME START");
        System.out.println("\n");

        int maxRounds = 100;  
        int roundCounter = 0;
        
        while (winner==null && roundCounter < maxRounds){
            System.out.println("\n");
            System.out.println("ROUND "+ roundCounter );
            System.out.println("\n");

            for (PlayerDemeter p: players){
                
                p.collectRessources();
                System.out.println("\n");
                System.out.println(p.getName()+" ("+p.getPoints()+" points, "+p.getResources()+ ") turn!!"); 
                board.display();

                //on propose au joueur des actions
                p.createActions(board,0, players); // on propose les actions possibles

                if ((p.getNbIsland()!= p.nbIslands(board)) && (p.nbIslands(board)<4) ){
                    int newIsland= p.nbIslands(board); 
                    if (newIsland ==2){
                        p.addPoints(1);
                        System.out.println("You settled on 2 islands! (+1 bonus point)");
                        
                    }
                    else{
                        if (newIsland >=2){
                            p.addPoints(2);
                            System.out.println("You settled on more than two islands! (+2 bonus points) ");
                        }
                    }
                    p.updateNbIsland(newIsland);
                }

                try {
                    p.act(board, 0);
                } catch (InvalidChoiceException | NullPointerException | IllegalArgumentException e) {
                    System.out.println("Action cancelled due to invalid or null choice.");
                }
                //on vérifie si le joueur gagne
                if (p.getPoints() >= 12){
                    winner=p; 
                    break; 
                }
            }
            roundCounter++;
            System.out.println("\n");
        }

        if (roundCounter >= maxRounds) {
            System.out.println("GAME OVER. No winner after " + maxRounds + " rounds.");
        } else {
            System.out.println(winner.getName() + " WINS THE GAME !!");
        }

    }
    
}
