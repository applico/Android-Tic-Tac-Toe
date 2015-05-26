package com.applico.tictactoe;

import android.content.Context;
import android.content.res.Resources;

import java.util.Random;

/**
 * Created by johntzan on 5/22/15.
 */
public class Game {



    public static final int NUM_ROWS = 3;
    public static final int NUM_COLUMNS = 3;

    public static final int MARK_O = 2;
    public static final int MARK_X = 1;
    public static final int MARK_NONE = 0;

    private Context context;
    public int[][] boardArray;
    public enum GameState{
        X_TURN, O_TURN, X_WIN, O_WIN, TIE
    }

    GameState gameState;

    public Game(Context context){
        this.context = context;
        resetGame();
    }
    public void resetGame(){
        this.boardArray = new int[NUM_ROWS][NUM_COLUMNS];
        this.gameState = GameState.X_TURN;
    }
    public void setMove(int row, int column){

        /* if board position is already taken */
        if (this.boardArray[row][column] != MARK_NONE){
            return;

        }
        /* User's turn */
        if(this.gameState == GameState.X_TURN) {
            this.boardArray[row][column] = MARK_X;
            this.gameState = GameState.O_TURN;
        }
        /* Computer's Turn */
        else if(this.gameState == GameState.O_TURN){

            this.boardArray[row][column] = MARK_O;
            this.gameState = GameState.X_TURN;

            }

        checkWinner();
    }

    public void getAIMove(){
        while(this.gameState == GameState.O_TURN )
            /*Take Middle if open first always */
            if(this.boardArray[1][1] == MARK_NONE) {
                setMove(1, 1);
            }
            /*Block user from winning */
        else if(this.boardArray[0][0]==MARK_NONE &&
                ((this.boardArray[0][1]==MARK_X && this.boardArray[0][2]==MARK_X) ||
                        (this.boardArray[1][1]==MARK_X && this.boardArray[2][2]==MARK_X) ||
                        (this.boardArray[1][0]==MARK_X && this.boardArray[2][0]==MARK_X))) {
            setMove(0, 0);
        }
            else if (this.boardArray[0][1]==MARK_NONE &&
                ((this.boardArray[1][1]==MARK_X && this.boardArray[2][1]==MARK_X) ||
                        (this.boardArray[0][0]==MARK_X && this.boardArray[0][2]==MARK_X))) {
            setMove(0, 1);
        }
            else if(this.boardArray[0][2]==MARK_NONE &&
                ((this.boardArray[0][0]==MARK_X && this.boardArray[0][1]==MARK_X) ||
                        (this.boardArray[2][0]==MARK_X && this.boardArray[1][1]==MARK_X) ||
                        (this.boardArray[1][2]==MARK_X && this.boardArray[2][2]==MARK_X))) {
            setMove(0, 2);
        }
            else if(this.boardArray[1][0]==MARK_NONE &&
                ((this.boardArray[1][1]==MARK_X && this.boardArray[1][2]==MARK_X) ||
                        (this.boardArray[0][0]==MARK_X && this.boardArray[2][0]==MARK_X))){
            setMove(1, 0);
        }
            else if(this.boardArray[1][1]==MARK_NONE &&
                ((this.boardArray[0][0]==MARK_X && this.boardArray[2][2]==MARK_X) ||
                        (this.boardArray[0][1]==MARK_X && this.boardArray[2][1]==MARK_X) ||
                        (this.boardArray[2][0]==MARK_X && this.boardArray[0][2]==MARK_X) ||
                        (this.boardArray[1][0]==MARK_X && this.boardArray[1][2]==MARK_X))) {
            setMove(1, 1);
        }
            else if(this.boardArray[1][2]==MARK_NONE &&
                ((this.boardArray[1][0]==MARK_X && this.boardArray[1][1]==MARK_X) ||
                        (this.boardArray[0][2]==MARK_X && this.boardArray[2][2]==MARK_X))) {
            setMove(1, 2);
        }
            else if(this.boardArray[2][0]==MARK_NONE &&
                ((this.boardArray[0][0]==MARK_X && this.boardArray[1][0]==MARK_X) ||
                        (this.boardArray[2][1]==MARK_X && this.boardArray[2][2]==MARK_X) ||
                        (this.boardArray[1][1]==MARK_X && this.boardArray[0][2]==MARK_X))){
            setMove(2, 0);
        }
            else if(this.boardArray[2][1]==MARK_NONE &&
                ((this.boardArray[0][1]==MARK_X && this.boardArray[1][1]==MARK_X) ||
                        (this.boardArray[2][0]==MARK_X && this.boardArray[2][2]==MARK_X))) {
            setMove(2, 1);
        }
            else if( this.boardArray[2][2]==MARK_NONE &&
                ((this.boardArray[0][0]==MARK_X && this.boardArray[1][1]==MARK_X) ||
                        (this.boardArray[0][2]==MARK_X && this.boardArray[1][2]==MARK_X) ||
                        (this.boardArray[2][0]==MARK_X && this.boardArray[2][1]==MARK_X))) {
            setMove(2, 2);
        }
            else {
                /* if user isn't going to win, get random location */
            Random rand = new Random();

            int a = rand.nextInt(3);
            int b = rand.nextInt(3);
            while(a==MARK_X || b==MARK_X || this.boardArray[a][b]!=MARK_NONE) {
                a = rand.nextInt(3);
                b = rand.nextInt(3);
            }
            setMove(a, b);
        }
    }

    private boolean winner(int player){
        boolean winner = true;
        /* Check columns */
        for(int col = 0; col < NUM_COLUMNS; col++){
            winner = true;
            for(int row=0; row < NUM_ROWS; row++){
                if(this.boardArray[row][col] != player) {
                    winner = false;
                    break;
                }
            }

        if (winner) return true;
        }
        /* check rows */
        for(int row=0; row < NUM_ROWS; row++){
            winner = true;
            for(int col = 0; col<NUM_COLUMNS; col++){
                if(this.boardArray[row][col] != player){
                    winner = false;
                    break;
                }
            }
            if(winner) return true;
        }
        /* check diagonals */
     if(this.boardArray[0][0] == player && this.boardArray[1][1]==player && this.boardArray[2][2] == player) {
        return true;
     }
     if(this.boardArray[2][0] == player && this.boardArray[1][1]==player && this.boardArray[0][2] == player){
         return true;
     }
        return false;

    }
    /* check if game is a tie */
    private boolean isTie(){
        for (int row = 0; row < Game.NUM_ROWS; row++) {
            for (int col = 0; col < Game.NUM_COLUMNS; col++) {
                if(this.boardArray[row][col] == MARK_NONE)
                    return false;
            }
        }
        return true;
    }
    /* get winner and set to gamestate */
    private void checkWinner(){
        if(!(this.gameState == GameState.X_TURN || this.gameState == GameState.O_TURN))
            return;
        if(winner(MARK_X))
            this.gameState = gameState.X_WIN;
        else if(winner(MARK_O))
            this.gameState = gameState.O_WIN;
        else if (isTie())
            this.gameState = gameState.TIE;

    }

    public String setMoveText(int row, int col){
        String label = "";
        if(row>=0 && row<NUM_ROWS && col>=0 && col < NUM_COLUMNS) {

            if (this.boardArray[row][col] == MARK_X) {
                return "X";

            } else if (this.boardArray[row][col] == MARK_O) {
                return "O";
            }
        }
        return label;
    }
    public String stringForGameState(){
        String gameStateLabel = "";
        Resources r = this.context.getResources();
        switch (this.gameState){
            case X_TURN:
                gameStateLabel = r.getString(R.string.user_turn);
                break;
            case O_TURN:
                gameStateLabel = r.getString(R.string.cpu_turn);
                break;
            case X_WIN:
                gameStateLabel = r.getString(R.string.user_wins);
                break;
            case O_WIN:
                gameStateLabel = r.getString(R.string.cpu_wins);
                break;
            default:
                gameStateLabel = r.getString(R.string.tie);
                break;

        }
        return gameStateLabel;
    }



}