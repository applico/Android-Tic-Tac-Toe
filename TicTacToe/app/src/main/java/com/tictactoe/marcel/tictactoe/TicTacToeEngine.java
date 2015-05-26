package com.tictactoe.marcel.tictactoe;


import java.util.Random;

/**
 * Created by marcelino on 5/22/15.
 * TicTacToeEngine class does the following:
 * 1. Clear all players position on the game board
 * 2. Save players position on the board
 * 3. Return players position
 * 4. Find Android position
 * 5. Check the winner
 * 6. Return necessary values
 */
public class TicTacToeEngine {

    private int BOARD_SIZE;
    private int[] entryBoard;
    private Random randomAndroidPosition;

    //Reset all entry
    private final static int  RESET_EMTY=0;

    //Players
    private final static int  ME_PLAYER=1;
    private final static int  ANDROID_PLAYER=2;

    //Case for winning
    private final static int  ME_PLAYER_WIN=1;
    private final static int  ANDROID_PLAYER_WIN=2;
    private final static int  NO_WINNER_YET=3;
    private final static int  THERE_IS_TIE=4;

    //Constructor
    public TicTacToeEngine(int boardSize){

           this.BOARD_SIZE=boardSize;
           entryBoard=new int[BOARD_SIZE];
           clearBoard();
           randomAndroidPosition=new Random();

    }

    //Clear all value in Array
    public void clearBoard(){

        for(int k=0; k<BOARD_SIZE; k++){

            entryBoard[k]=RESET_EMTY;
        }
    }

    //Store players move
    public void storePlayerMove(int playerPosition,int whoIsPlayer){

        //WhoisPlayer:
        //ME_PLAYER=1
        //ANDROID_PLAYER=2

        entryBoard[playerPosition]=whoIsPlayer;


    }

    //Return all players move
    public int[] getAllEntry(){

        return entryBoard; //return all players move
    }

    //Find and return android player position
    public int findAndroidMove(){

        int androidMove;

        //Check if ANDROID_PLAYER can win the game
        for(int k=0; k<BOARD_SIZE; k++){

            //Check an entry is empty
            if(entryBoard[k]!=ME_PLAYER && entryBoard[k]!=ANDROID_PLAYER) {

                entryBoard[k]=ANDROID_PLAYER;

                //Check if Android wins
                if(checkWinner()==ANDROID_PLAYER_WIN){

                    return k;

                }else{
                    //Set entry back to empty
                    entryBoard[k]=RESET_EMTY;
                }
            }
        }

        //Check if ANDROID_PLAYER can block ME_PLAYER from winning
        for(int k=0; k<BOARD_SIZE; k++){

            //Check an entry is empty
            if(entryBoard[k]!=ME_PLAYER && entryBoard[k]!=ANDROID_PLAYER) {

                entryBoard[k]=ME_PLAYER;

                //Check if Android wins
                if(checkWinner()==ME_PLAYER_WIN){
                    storePlayerMove(k,ANDROID_PLAYER);

                    return k;

                }else{
                    //Set entry back to empty
                    entryBoard[k]=RESET_EMTY;
                }
            }
        }

        //Generate random ANDROID_PLAYER position
        //if none of the above is true.
        //do-while statement uses less code
        do {
            androidMove=randomAndroidPosition.nextInt(BOARD_SIZE);
        }while (entryBoard[androidMove]==ME_PLAYER || entryBoard[androidMove]==ANDROID_PLAYER);

        //Store Android move
        storePlayerMove(androidMove,ANDROID_PLAYER);

        return androidMove;

    }

    //Check winner and return who is the winner
    public int checkWinner(){

        //Return 1 if ME_PLAYER wins
        //Return 2 if ANDROID_PLAYER wins
        //Return 3 if no winner yet
        //Return 4 if there is a tie

        int k;

        //Check each column
        for(k=0; k<3; k++){

            //Check if ME_PLAYER wins
            if(entryBoard[k]==ME_PLAYER && entryBoard[k+3]==ME_PLAYER && entryBoard[k+6]==ME_PLAYER ){

                return ME_PLAYER_WIN;

            }

            //Check if ANDROID_PLAYER wins
            if(entryBoard[k]==ANDROID_PLAYER && entryBoard[k+3]==ANDROID_PLAYER && entryBoard[k+6]==ANDROID_PLAYER ){

                return ANDROID_PLAYER_WIN;
            }

        }

        //Check each row
        for(k=0; k<7; k+=3){

            //Check if ME_PLAYER wins
            if(entryBoard[k]==ME_PLAYER && entryBoard[k+1]==ME_PLAYER && entryBoard[k+2]==ME_PLAYER ){

                return ME_PLAYER_WIN;

            }

            //Check if ANDROID_PLAYER wins
            if(entryBoard[k]==ANDROID_PLAYER && entryBoard[k+1]==ANDROID_PLAYER && entryBoard[k+2]==ANDROID_PLAYER ){

                return ANDROID_PLAYER_WIN;
            }

        }

        //Check each diagonal
        //Check if ME_PLAYER wins
        if((entryBoard[0]==ME_PLAYER && entryBoard[4]==ME_PLAYER && entryBoard[8]==ME_PLAYER)||
                (entryBoard[2]==ME_PLAYER && entryBoard[4]==ME_PLAYER && entryBoard[6]==ME_PLAYER)){

            return ME_PLAYER_WIN;

        }

        //Check if ANDROID_PLAYER wins
        if((entryBoard[0]==ANDROID_PLAYER && entryBoard[4]==ANDROID_PLAYER && entryBoard[8]==ANDROID_PLAYER)||
                (entryBoard[2]==ANDROID_PLAYER && entryBoard[4]==ANDROID_PLAYER && entryBoard[6]==ANDROID_PLAYER)){

            return ANDROID_PLAYER_WIN;
        }

        //Check for no winner yet
        for(k=0; k<BOARD_SIZE; k++){

            if(entryBoard[k]!=ME_PLAYER && entryBoard[k]!=ANDROID_PLAYER) {

                return NO_WINNER_YET;
            }
        }

        //There is a tie game, no winner
        return THERE_IS_TIE;
    }

    public int emptyBoard(){
      return RESET_EMTY;
    }
    public int mePlayer(){
        return ME_PLAYER;
    }
    public int androidPlayer(){
        return ANDROID_PLAYER;
    }
    public int mePlayerWin(){
        return ME_PLAYER_WIN;
    }

    public int androidPlayerWin(){
        return ANDROID_PLAYER_WIN;
    }

    public int noWinnerYet(){
        return NO_WINNER_YET;
    }

    public int thereIsTie(){
        return THERE_IS_TIE;
    }

}//end class TicTacToeEngine
