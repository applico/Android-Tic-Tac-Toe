package kumarankit.applicotic_tac_toe;

import java.util.Random;

/**
 * Created by Ankit on 4/17/2015.
 */




/*
*
*   Tic Tac Toe Game Logic
*
*   X  |  O  |  X
* -----|-----|-----
*      |  X  |  O
* -----|-----|-----
*   0  |     |  X
*
*
* */

public class TicTacToe {

    private char mBoard[];
    private final static int BOARD_SIZE =9;

    public static final char USER_ = 'X';
    public static final char AI ='O';
    public static final char EMPTY =' ';

    private Random mRand;

    //-----------<Getter for board size>-------------------
    public static int getBoardSize()
    {
       return BOARD_SIZE;
    }

    //-----------<Setup Board in constructor>-------------------
    public TicTacToe()
    {
        mBoard = new char[BOARD_SIZE];

        for (int i=0; i<BOARD_SIZE; i++)
            mBoard[i]=EMPTY;

        mRand = new Random();

    }

    //-----------<Clear board>-------------------
    public void clearBoard()
    {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i]= EMPTY;
        }
    }

    //-----------<Setter for moves of player>-------------------
    public void setMove(char player, int location)
    {
        mBoard[location]=player;
    }


    //-----------<Get AI move and checks for win position>-------------------
    public int getAIMove() {
        int move;

        for (int i = 0; i < getBoardSize(); i++)
        {
            if (mBoard[i] != USER_ && mBoard[i] != AI)
            {
                char curr = mBoard[i];
                mBoard[i] = USER_;
                if (checkWinner() == 3)
                {
                    setMove(AI, i);
                    return i;
                }
                else
                    mBoard[i] = curr;
            }
        }

        for (int i = 0; i < getBoardSize(); i++)
        {
            if (mBoard[i] != USER_ && mBoard[i] != AI)
            {
                char curr = mBoard[i];
                mBoard[i] = USER_;
                if (checkWinner() == 2)
                {
                    setMove(AI, i);
                    return i;
                }
                else
                    mBoard[i] = curr;
            }
        }

        do
        {
            move = mRand.nextInt(getBoardSize());
        } while (mBoard[move] == USER_ || mBoard[move] == AI);

        setMove(AI, move);
        return move;
    }

    //-----------<Helper function to check for winner>-------------------
    public int checkWinner() {

        //For horizontal check
        for (int i = 0; i <=6 ; i+=3) {
            if(mBoard[i] == USER_ && mBoard[i+1] == USER_ && mBoard[i+2] == USER_)
                return 2;

            if(mBoard[i] == AI && mBoard[i+1] == AI && mBoard[i+2] == AI)
                return 3;
        }

        //For vertical check
        for (int i = 0; i <=2 ; i++) {
            if(mBoard[i] == USER_ && mBoard[i+3] == USER_ && mBoard[i+6] == USER_)
                return 2;
            if(mBoard[i] == AI && mBoard[i+3] == AI && mBoard[i+6] == AI)
                return 3;
        }

        //For diagonal
        if((mBoard[0] == USER_ && mBoard[4] == USER_ && mBoard[8] == USER_)||
                (mBoard[2] == USER_ && mBoard[4] == USER_ && mBoard[6] == USER_))
            return 2;
        if((mBoard[0] == AI && mBoard[4] == AI && mBoard[8] == AI)||
                (mBoard[2] == AI && mBoard[4] == AI && mBoard[6] == AI))
            return 3;

        //For no winner condition
        for (int i = 0; i < getBoardSize(); i++) {
            if(mBoard[i] != USER_ && mBoard[i] != AI)
            return 0;
        }
        //For tie
        return 1;
    }

}