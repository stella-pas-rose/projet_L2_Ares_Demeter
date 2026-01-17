package game.action;

import java.util.List;
import java.util.Random;

import game.NoMoreRessourcesException;
import game.PlayerAres;
import game.listchooser.RandomListChooser;
import game.tuile.Earth;
import game.tuile.building.Army;

public class AttackNeighboor extends ActionManager implements Action<PlayerAres>{

    public RandomListChooser<PlayerAres> lc;
    public PlayerAres  player ;
    public List<PlayerAres> enemies;
    public Earth tile;
   
    
        /**
         * constructor of AttackNeighboor
         * @param player the player who wants to attack
         * @param enemies the list of all the neighboring enemies
         */ 
        public AttackNeighboor(PlayerAres player, List<PlayerAres> enemies){
            super(player); 

            this.enemies= enemies;
            lc= new RandomListChooser<PlayerAres>(); 
        }
    
        /**
         * asks the player which neighbor he wants to attack
         * @return the player  to be attacked
         */
        public PlayerAres askNeighbor() {
            if (this.enemies.isEmpty()) {
                System.out.println("No enemies available to attack.");
                return null;
            }
            return lc.choose("Who do you want to attack", this.enemies);
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
    

    public void act(PlayerAres player) throws NoMoreRessourcesException {
        PlayerAres ennemy= askNeighbor();
        // la somme des dés de chaque joueur
        System.out.println(player.getName() + " VS " + ennemy.getName());
        Integer ennemyRes= dicesResult(howMuchDice(ennemy));
        Integer playerRes= dicesResult(howMuchDice(player));
        PlayerAres loser;
        if (ennemyRes< playerRes){
            loser = ennemy;
        }
        else{
            loser= player;
        }
        //je sais pas comment on va faire pour savoir quel tuile on attaque je pense que 
        // mon code est confus et à revoir 

        if (loser.getWarriors()>=1){
            
          loser.removeWarriors(1);
        }
        else if (loser.getArmies().size()>=1){
            List<Army> listarmies=loser.getArmies();
            loser.removeArmy(listarmies.get(0));

        }
    
    
}
}
