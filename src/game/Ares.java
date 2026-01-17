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

public class Ares {

    public static void main(String[] args)
            throws IOException, InvalidChoiceException, CantBuildException, NoMoreRessourcesException {
        if (args.length < 3) {
            System.out.println("You have to give two positive settings and the number of player.");
            return;
        }

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int nbPlayer = Integer.parseInt(args[2]);

        if (width < 10 || height < 10) {
            System.out.println("minimum dimensions must be 10");
            return;
        }

        if (nbPlayer < 2) {
            System.out.println("You should play with at least two player");
            return;
        }

        System.out.println("--------------------");
        System.out.println("--------ARES--------");
        System.out.println("--------------------");
        System.out.println("\n");

        Board board = new Board(width, height);

        List<PlayerAres> players = new ArrayList<>();
        PlayerAres winner = null;

        // création des différents joueurs
        for (int i = 0; i < nbPlayer; i++) {
            System.out.println("j" + (i + 1) + " name:");
            String name = Input.readString();
            players.add(new PlayerAres(name));

        }

        // tir au sort un objectif pour chaque joueuer
        System.out.println("\n");
        System.out.println("Player's goals");
        for (PlayerAres p : players) {
            p.givePlayersObjective();
            System.out.println(p.getName() + " goal is to " + p.getObjective());
        }

        for (PlayerAres p : players) {
            p.createActions(board, 0, players);
        }

        // ici on vas laisser les joueurs construire leurs deux armées
        System.out.println("\n");
        System.out.println("TIME TO BUILD YOUR ARMIES !");
        System.out.println("\n");

        // premier tour : ordre croissant
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getName() + " place your first initial farm: ");
            boolean isPlace= false; 
            while (!isPlace){
                try{
                    players.get(i).placeInitialArmy(board, new InteractiveListChooser<Earth>(),new InteractiveListChooser<Integer>());
                    isPlace= true; 
                } catch (InvalidChoiceException | NullPointerException | IllegalArgumentException e) {
                        System.out.println("Please build a army.");
                }
            }
            
        }

        // deuxieme tour : ordre decroissant
        for (int i = players.size() - 1; i >= 0; i--) {
            System.out.println(players.get(i).getName() + " (" + players.get(i).getResources() + "/ nb warriors: "
                    + players.get(i).getWarriors() + ") build your second army : ");
            boolean isPlace= false; 
            while (!isPlace){
                try{
                    players.get(i).placeInitialArmy(board, new InteractiveListChooser<Earth>(),new InteractiveListChooser<Integer>());
                    isPlace= true; 
                } catch (InvalidChoiceException | NullPointerException | IllegalArgumentException e) {
                        System.out.println("Please build a army.");
                }
            }
        }

        System.out.println("\n");
        System.out.println("THE GAME START");
        System.out.println("\n");

        int i = 0;
        int maxRounds = 100;
        int roundCounter = 0;

        while (winner == null && roundCounter < maxRounds) {
            System.out.println("ROUND " + i);
            for (PlayerAres p : players) {
                System.out.println(
                        p.getName() + " (" + p.getResources() + "/ nb warriors: " + p.getWarriors() + ") turn!!");
                p.collectRessources();
                board.display();
                p.createActions(board, 0, players);// changer la liste des actions a chaque tour
                try {
                    p.act(board, 0);
                } catch (InvalidChoiceException | NullPointerException | IllegalArgumentException e) {
                    System.out.println("Action cancelled due to invalid or null choice.");
                }
                if (isWinner(p, board)) {
                    winner = p;
                    break;
                }
            }
            i++;
            roundCounter++;
            System.out.println("\n");
        }

        if (roundCounter >= maxRounds) {
            System.out.println("GAME OVER. No winner after " + maxRounds + " rounds.");
        } else {
            System.out.println(winner.getName() + " WINS THE GAME !!");
        }

    }

    private static boolean isWinner(PlayerAres player, Board board) {
        return player.isObjectiveAchieved(board);
    }

}
