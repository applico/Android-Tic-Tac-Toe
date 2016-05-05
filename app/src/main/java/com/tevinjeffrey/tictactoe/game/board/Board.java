package com.tevinjeffrey.tictactoe.game.board;

import com.tevinjeffrey.tictactoe.game.cell.Cell;
import com.tevinjeffrey.tictactoe.game.cell.CellState;

import java.util.ArrayList;
import java.util.List;

//An interface for the game to support different sizes of boards.
// 4x4 and 6x6
public interface Board {

    void invokeCell(CellState who, int pickIndex);

    CellState getWinner();

    boolean isDraw();

    //No static interface method in java 7 :(
    Cell[] getWinningLine(List<Cell> cells);

    List<Cell> getCells();

    boolean isGameOver(List<Cell> cells);

    void evaluate(boolean resumedGame);

    void clear();

    void checkPlayerPick(Cell cell);

    abstract class BoardUtils {
        public static List<Cell> getPossibleMoves(List<Cell> cells) {
            List<Cell> possibleMoves = new ArrayList<>();
            for(Cell cell : cells) {
                if (cell.isBlank()) {
                    possibleMoves.add(cell);
                }
            }
            return possibleMoves;
        }

        public static boolean isCellsFull(List<Cell> cells) {
            boolean isFilled = true;
            for (Cell cell : cells) {
                if(!cell.isTaken()) {
                    isFilled = false;
                }
            }
            return isFilled;
        }
    }
}
