package com.shengkunwei.tictactoe.tictactoe;

/**
 * Created by ShengkunWei on 4/2/15.
 */
public class Cell {
    public static final int STATE_EMPTY = 0;
    public static final int STATE_PLAYER1 = 1;
    public static final int STATE_COMPUTER = 2;

    int state = STATE_EMPTY;
    int row;
    int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
