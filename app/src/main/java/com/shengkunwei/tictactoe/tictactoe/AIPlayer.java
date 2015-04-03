package com.shengkunwei.tictactoe.tictactoe;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ShengkunWei on 4/2/15.
 */
public class AIPlayer {
    private static final String TAG = AIPlayer.class.getSimpleName();
    private TicTacToeView mGameView;
    private Cell[][] mCells;

    private final int SCORE_ONE_IN_LINE = 1;
    private final int SCORE_TWO_IN_LINE = 10;

    private int IQ = 4;

    public AIPlayer(TicTacToeView gameView, int iQ) {
        this.mGameView = gameView;
        this.mCells = gameView.mCells;
        this.IQ = iQ;
    }



    public int[] getNextStep(){
        int[] nextStep = minimaxStrategy(IQ, Cell.STATE_COMPUTER);
        return new int[]{nextStep[0], nextStep[1]};
    }

    private int[] minimaxStrategy(int depth, int whichPlayer){
        int currScore = 0;
        int bestScore = (whichPlayer == Cell.STATE_COMPUTER ? Integer.MIN_VALUE : Integer.MAX_VALUE);
        int bestRow = -1;
        int bestCol = -1;

        ArrayList<Cell> emptyCells = getEmptyCells();
        if(emptyCells.isEmpty() || depth == 0){
            bestScore = calculateBestScore();
        } else {
            for(Cell cell : emptyCells){
                cell.state = whichPlayer;
                if(whichPlayer == Cell.STATE_COMPUTER){
                    int[] tmp = minimaxStrategy(depth - 1, Cell.STATE_PLAYER1);
                    currScore = tmp[2];
                    if(bestScore < currScore){
                        bestScore = currScore;
                        bestRow = cell.row;
                        bestCol = cell.col;

                    }
                } else {
                    int[] tmp = minimaxStrategy(depth - 1, Cell.STATE_COMPUTER);
                    currScore = tmp[2];
                    if(bestScore > currScore){
                        bestScore = currScore;
                        bestRow = cell.row;
                        bestCol = cell.col;
                    }
                }

                cell.state = Cell.STATE_EMPTY;
            }
        }

        return new int[]{bestRow, bestCol, bestScore};
    }

    private int calculateBestScore() {
        int score = 0;
        score += evaluateLine(mCells[0][0], mCells[0][1], mCells[0][2]);
        score += evaluateLine(mCells[1][0], mCells[1][1], mCells[1][2]);
        score += evaluateLine(mCells[2][0], mCells[2][1], mCells[2][2]);
        score += evaluateLine(mCells[0][0], mCells[1][0], mCells[2][0]);
        score += evaluateLine(mCells[0][1], mCells[1][1], mCells[2][1]);
        score += evaluateLine(mCells[0][2], mCells[1][2], mCells[2][2]);
        score += evaluateLine(mCells[0][0], mCells[1][1], mCells[2][2]);
        score += evaluateLine(mCells[0][2], mCells[1][1], mCells[2][0]);
//        Log.i(TAG, "score==" + score);
        return score;
    }

    private int evaluateLine(Cell c1, Cell c2, Cell c3){
        int score = 0;
        //cell1
        if(c1.state == Cell.STATE_COMPUTER){
            score = SCORE_ONE_IN_LINE;
        } else if(c1.state == Cell.STATE_PLAYER1) {
            score = -SCORE_ONE_IN_LINE;
        }


        //cell2
        if(c2.state == Cell.STATE_COMPUTER){
            if(score == SCORE_ONE_IN_LINE){
                score = SCORE_TWO_IN_LINE;
            } else if(score == -SCORE_ONE_IN_LINE){
                return 0;
            } else {
                score = SCORE_ONE_IN_LINE;
            }
        } else if (c2.state == Cell.STATE_PLAYER1){
            if(score == SCORE_ONE_IN_LINE){
                return 0;
            } else if(score == -SCORE_ONE_IN_LINE){
                score = -SCORE_TWO_IN_LINE;
            } else {
                score = -SCORE_ONE_IN_LINE;
            }
        }


        //cell3
        if(c3.state == Cell.STATE_COMPUTER){
            if(score > 0){
                score *= 10;
            } else if(score < 0){
                return 0;
            } else {
                score = SCORE_ONE_IN_LINE;
            }
        } else if (c3.state == Cell.STATE_PLAYER1){
            if(score > 0){
                return 0;
            } else if(score < 0){
                score *= 10;
            } else {
                score = -SCORE_ONE_IN_LINE;
            }
        }

        return score;
    }

    ArrayList<Cell> getEmptyCells(){
        ArrayList<Cell> emptyCells = new ArrayList<>();

        for(int i = 0; i < mCells.length; i++){
            for (int j = 0; j < mCells[i].length; j++) {
                if(mCells[i][j].state == Cell.STATE_EMPTY){
                    emptyCells.add(mCells[i][j]);
                }
            }
        }
        return emptyCells;
    }


}
