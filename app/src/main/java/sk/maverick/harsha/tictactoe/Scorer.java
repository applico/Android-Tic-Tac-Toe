package sk.maverick.harsha.tictactoe;

/**
 * Created by Harsha on 4/8/2015.
 */
public class Scorer {

    private int player;
    private int AI;

    public Scorer(){
        this.player = 0;
        this.AI = 0;
    }
    
    public int getPlayerScore(){
        return this.player;
    }
    
    public int getAIScore(){
        return this.AI;
    }

}
