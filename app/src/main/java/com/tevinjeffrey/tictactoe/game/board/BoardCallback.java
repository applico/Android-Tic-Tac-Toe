package com.tevinjeffrey.tictactoe.game.board;

import com.tevinjeffrey.tictactoe.game.cell.Cell;
import com.tevinjeffrey.tictactoe.game.cell.CellState;

/**
 * The interface Board callback.
 */
public interface BoardCallback {
    /**
     * onPlayerPick is called when the user or ai chooses a cell.
     *
     * @param threeBoard the three board
     * @param pickIndex the pick index
     */
    void onPlayerPick(Board threeBoard, int pickIndex);

    /**
     * onCellInvoke is called when the state of a cell has been changed.
     *
     * @param player the player
     */
    void onCellInvoke(CellState player);

    /**
     * onWinner is called when the board determines that a player has won the game.
     *
     *
     * @param winningCells the winning cells
     */
    void onWinner(Cell[] winningCells);

    /**
     * Called when the board determines the players are at a stalemate
     */
    void onDraw();

    /**
     * Requests that the next player make a move on the board.
     */
    void nextPlayer();

    /**
     * onClear is called when the board on done clearing the cells
     */
    void onClear();

}
