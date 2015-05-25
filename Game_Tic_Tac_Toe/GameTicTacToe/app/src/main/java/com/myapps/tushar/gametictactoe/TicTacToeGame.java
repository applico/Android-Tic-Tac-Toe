package com.myapps.tushar.gametictactoe;

///////////////////////////////////////////////////////////////////////
// MainActivity.java - GameTicTacToe                                 //
// ver 1.0                                                           //
// Language:    Java, Android Studio 1.2.1.1                         //
// platform:    Dell Inspiron 15R, Windows 8                         //
// Author:      Tushar Bhatia                                        //
//              Graduate Student, Syracuse University                //
//              tbhatia@syr.edu                                      //
///////////////////////////////////////////////////////////////////////


/*
* This class creates the grid on which the game is being played.
* Decides the move played by the computer and records the move played
* by either of the player.
*/

import java.util.Random;



public class TicTacToeGame {


    private char mGrid[];
    public static final char USER = 'X';
    public static final char COMPUTER = 'O';
    public static final char EMPTY_HOLE = ' ';
    private final static int GRID_SIZE = 9;

    private Random mRandNumber ;

    //-------------< Initializing the game  >-------------------
    public TicTacToeGame(){

        mGrid = new char[GRID_SIZE];
        for(int i =0; i< GRID_SIZE;i++){

            mGrid[i] = EMPTY_HOLE;
        }
        mRandNumber = new Random();
    }
    //-------------< Resets the grid  >-------------------
    public void ResetGrid(){
        for(int i =0; i< GRID_SIZE;i++){
            mGrid[i] = EMPTY_HOLE;
        }

    }

    //-------------< record the move played by the user or computer  >-------------------
    public void movePlayed(char playerType, int position){

        mGrid[position]  = playerType;
    }

    //-------------< returns the grid size (here it is 3*3  )  >-------------------
    public static int getGridSize(){

        return GRID_SIZE;
    }

    //-------------< Decides Computer's Move  >-------------------
    // First, we check if we can make 3 marks to win the game.
    // Second, if winning is not possible, we try to block user's
    // move.
    // If above doesn't work simply put the mark in a random location
    // which is empty.
    public int ComputerMove(){

        int move;

        for( int position =0; position< GRID_SIZE; position++){
            if(mGrid[position]!=USER && mGrid[position]!=COMPUTER)
            {
                mGrid[position] = COMPUTER;
                if(checkForWin() == 3) {
                    movePlayed(COMPUTER, position);
                    return position;
                }
                else
                    mGrid[position] = EMPTY_HOLE;
            }
        }


        for( int position =0; position< GRID_SIZE;position++){
            if(mGrid[position]!=USER && mGrid[position]!=COMPUTER)
            {

                mGrid[position] = USER;
                if(checkForWin() == 2) {
                    movePlayed(USER, position);
                    return position;
                }
                else
                    mGrid[position] = EMPTY_HOLE;
            }
        }

        move = mRandNumber.nextInt(getGridSize());
        while(mGrid[move] == USER || mGrid[move] == COMPUTER){
            move = mRandNumber.nextInt(getGridSize());
        }

        movePlayed(COMPUTER,move);
        return move;
    }

//-------------------< Checks if any of user has won or is it tie or game still running>------------------------------------

    // returns  -->  2 - user win ;3 comp win; 1 tie; 0 still going on
    public int checkForWin(){

        //checking horizontally for user

        for(int i= 0 ; i<=6 ; i+=3 ){
            if(mGrid[i] == USER &&
               mGrid[i+1] == USER &&
               mGrid[i+2] == USER  )
                return 2;
        }
        for(int i= 0 ; i<=6 ; i+=3 ){
            if(mGrid[i] == COMPUTER &&
               mGrid[i+1] == COMPUTER &&
               mGrid[i+2] == COMPUTER  )
                return 3;

        }

        // Checking horizontally for a win for user or computer

        for(int i= 0 ; i<=6 ; i+=3 ){
            if(mGrid[i] == USER &&
               mGrid[i+1] == USER &&
               mGrid[i+2] == USER  )
                return 2;
        }
        for(int i= 0 ; i<=6 ; i+=3 ){
            if(mGrid[i] == COMPUTER &&
               mGrid[i+1] == COMPUTER &&
               mGrid[i+2] == COMPUTER  )
                return 3;

        }

        // Checking vertically for a win for user or computer

        for(int i =0 ; i<=2 ; i++){
            if(mGrid[i] == USER &&
               mGrid[i+3] == USER &&
               mGrid[i+6] == USER )
                return 2;
        }

        for(int i =0 ; i<=2 ; i++){
            if(mGrid[i] == COMPUTER &&
               mGrid[i+3] == COMPUTER &&
               mGrid[i+6] == COMPUTER )
                return 3;
        }

        // Checking Diagonally for User
        if((mGrid[0] == USER &&
            mGrid[4] == USER &&
            mGrid[8] == USER )||
                (mGrid[2] == USER &&
                 mGrid[4] == USER &&
                 mGrid[6] == USER ))
            return 2;

        // Checking Diagonally for Computer
        if((mGrid[0] == COMPUTER &&
                mGrid[4] == COMPUTER &&
                mGrid[8] == COMPUTER )||
                (mGrid[2] == COMPUTER &&
                        mGrid[4] == COMPUTER &&
                        mGrid[6] == COMPUTER ))
            return 3;

        // Checks if game is still on going

        for(int i=0;i <getGridSize();i++){
            if(mGrid[i]!= USER && mGrid[i]!= COMPUTER )
                return 0;
        }


        // if neither user wins , nor is game going on ; Its a Tie
        return 1;
    }


}
