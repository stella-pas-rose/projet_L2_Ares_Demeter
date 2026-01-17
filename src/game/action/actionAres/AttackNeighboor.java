package game.action.actionAres;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import game.PlayerAres;
import game.action.Action;
import game.action.ActionManager;
import game.listchooser.ListChooser;
import game.tuile.Earth;
import game.tuile.building.Army;
import game.util.InvalidChoiceException;
import game.util.NoMoreRessourcesException;

public class AttackNeighboor extends ActionManager<PlayerAres> implements Action<PlayerAres>{

    public ListChooser<PlayerAres> lc;
    public ListChooser<Earth> lcEarth;

    public PlayerAres  player ;
    public List<PlayerAres> enemies;
    public Earth tile;
    private List<PlayerAres> players; 
    
   
    



    /**
     * constructor of AttackNeighboor
     * @param player the player who wants to attack
     * @param enemies the list of all the neighboring enemies
     * @param tile the tile that the player wants to attack // on en a besoin car un joueur dans la plus part du temps a plusiers tuile et il faut savoir laquelle attaquer
     */ 
    public AttackNeighboor(PlayerAres player, List<PlayerAres> players, ListChooser<PlayerAres> lc,ListChooser<Earth> lcEarth) {
        super(player); 
        this.enemies= new ArrayList<>();
        this.lc= lc; 
        this.lcEarth= lcEarth;
        this.player= player;
        this.players= players;
           
    }



    /**
     * @return the description of the action
     */
    public String toString(){
            return "Attack neighboor"; 
    }

    /**
     * iterates on the the list this.player and adds all this player's enemies to the list this.enemies
     * 
     */
    public void  createEnnemies(){
        
        for (PlayerAres p : this.players){
            if (p!= this.player){
                this.enemies.add(p); 
            }
        }
     
    }
    public List<PlayerAres> getEnemies(){
        return this.enemies;
    }



    /**
     * ask the player which neighbor he wants to attack , returns null if there are no enemies to attack
     * @param ennemies
     * @return the player  to be attacked
     */
    public PlayerAres askNeighbor() throws InvalidChoiceException {
        
        this.createEnnemies();
        PlayerAres enemie=null;
        if (this.enemies.isEmpty()) {
            throw new InvalidChoiceException("No enemies to attack");
        }
        else if(this.enemies.size()==1){
            enemie=enemies.get(0);
        }
        
        else if (this.enemies.size()>1){
     
        enemie = lc.choose("Who do you want to attack", enemies);
        if (enemie == null) {
          System.out.println("Action cancelled :  No enemies to attack");
          throw new InvalidChoiceException("action cancelled !!!");
            
        }
       
    }
       
    return enemie;
    }

    /**
     * asks the player which tile he wants to attack
     * @param enemie
     * @return the tile to be attacked
     * @throws InvalidChoiceException
     */
    public Earth askTile(PlayerAres enemie) throws InvalidChoiceException {

        List<Earth> tiles = enemie.getTiles();
        if(tiles.isEmpty()) {
            throw new InvalidChoiceException("No tiles to attack");
        }
       
        Earth tile = lcEarth.choose("Which tile do you want to attack", tiles);
        if (tile == null) {
            System.out.println("Action cancelled :  No tiles to attack");
            throw new InvalidChoiceException("No tile was selected !!!");
        }
        return tile;

    }


        
    /**
     * returns how many dices a player can throw based on the number of warriors he has 
     * and wether or not  he has a secret weapon
     * @param player
     * @return the number of dice
     */
    public int howMuchDice(PlayerAres player) {

        int nbWarriors = player.getWarriors();
        int res = 0;
        if (player.getNbSecretWeapon() > 0) {
            player.removeSecretWeapon();
            res += 1;
        }
        if (nbWarriors >= 1 && nbWarriors < 4) {
            res += 1;
        } else if (nbWarriors < 8) {
            res += 2;
        } else {
            res += 3;
        }
        return res;
    }
    

    /**
     * returns the sum of the dices thrown by the player
     * @param numberDices
     * @return the result of the dices
     */
    public Integer dicesResult(Integer numberDices){
        
        Random dice = new Random();
        int result = 0;
        for (int i = 0; i < numberDices; i++) {
            result += dice.nextInt(6) + 1;
        }
        return result;
    }

    private void transferTile(Earth tile, PlayerAres player, PlayerAres ennemy) {
        // retirer la tuile de l'ancien proprietaire
        ennemy.getTiles().remove(tile);
        // ajouter la tuile au nouveau proprietaire 
        player.getTiles().add(tile);
    }


    
    /** 
     * attacks the tile of the neighbor  chosen by this player 
     *@param player the player that wants to attack a neighbor

    */
    public void act(PlayerAres player) throws NoMoreRessourcesException , InvalidChoiceException {
         
       PlayerAres ennemy=this.askNeighbor();
        Earth tile = askTile(ennemy);

        // la somme des dés de chaque joueur
        System.out.println(player.getName() + " VS " + ennemy.getName());
        
        Integer ennemyRes= dicesResult(howMuchDice(ennemy));
        Integer playerRes= dicesResult(howMuchDice(player));
        
        System.out.println(ennemy.getName() + " a obtenu : " + ennemyRes);
        System.out.println(player.getName() + " a obtenu : " + playerRes);
        
        
        PlayerAres loser;
        if (ennemyRes< playerRes){
            loser = ennemy;
        }
        else{
            loser= player;
        }

        System.out.println(loser.getName() + " a perdu ! ");
        
        try {
            if (loser.getWarriors()>=1){
            loser.removeWarriors(1);
            }
        } catch (NoMoreRessourcesException e) {
            System.out.println("");
        }

        if (!loser.getArmies().isEmpty()){
            List<Army> listarmies=loser.getArmies();
            Army army= listarmies.get(0);
            loser.removeArmy(army);

        }

        transferTile(tile, player, ennemy);

        
            
        }
    
        
}

