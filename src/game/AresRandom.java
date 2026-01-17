package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.listchooser.RandomListChooser;
import game.tuile.Earth;
import game.util.CantBuildException;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;

public class AresRandom {

  public static void main(String[] args)
      throws IOException, InvalidChoiceException, CantBuildException, NoMoreRessourcesException {

    if (args.length < 3) {
      System.out.println("You have to give two positive settings and the number of player.");
      return;
    }

    int width = Integer.parseInt(args[0]);
    int height = Integer.parseInt(args[1]);
    int nbplayer = Integer.parseInt(args[2]);

    if (width < 10 || height < 10) {
      System.out.println("Width and height must be greater than 10.");
      return;
    }

    if (nbplayer < 2) {
      System.out.println("Number of players must be between 2 .");
      return;
    }

    System.out.println("--------------------");
    System.out.println("------ARES RANDOM---");
    System.out.println("--------------------");

    Board board = new Board(width, height);

    List<PlayerAres> players = new ArrayList<>();
    PlayerAres winner = null;

    // création des différents joueurs
    for (int i = 0; i < nbplayer; i++) {
      String name = "j" + (i + 1);
      players.add(new PlayerAres(name));
    }

    // Placement initail des armees
    System.out.println("Placement initial des armées (mode aléatoire) :");

    System.out.println("\n");
    System.out.println("TIME TO BUILD YOUR ARMIES !");
    System.out.println("\n");

    // premier tour : ordre croissant
    for (int i = 0; i < players.size(); i++) {
      System.out.println(players.get(i).getName() + " place your first initial farm: ");
      boolean isPlace= false; 
      while (!isPlace){
          try{
              players.get(i).placeInitialArmy(board, new RandomListChooser<Earth>(),new RandomListChooser<Integer>());
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
              players.get(i).placeInitialArmy(board, new RandomListChooser<Earth>(),new RandomListChooser<Integer>());
              isPlace= true; 
          } catch (InvalidChoiceException | NullPointerException | IllegalArgumentException e) {
                  System.out.println("Please build a army.");
          }
      }
  }

    // tir au sort un objectif pour chaque joueuer
    System.out.println("\n");
    System.out.println("Player's goals");
    for (PlayerAres p : players) {
      p.givePlayersObjective();
      System.out.println(p.getName() + " goal is to " + p.getObjective());
    }

    

    int i = 0;
    int maxRounds = 100;
    int roundCounter = 0;

    while (winner == null && roundCounter < maxRounds) {
      System.out.println("\n");
      System.out.println("ROUND " + i);
      System.out.println("\n");
      for (PlayerAres p : players) {
        System.out.println(p.getName() + " (" + p.getResources() + ") turn !");
        p.collectRessources();
        board.display();

        p.createActions(board, 1, players);
        try {
          p.act(board, 1);
        } catch (InvalidChoiceException | NullPointerException | IllegalArgumentException e) {
          System.out.println("Action cancelled due to invalid or null choice.");

        }
        if (p.isObjectiveAchieved(board)) {
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
      System.out.println("(do love, not war)");
    } else {
      System.out.println(winner.getName() + " WINS THE GAME !!");
    }

  }

}