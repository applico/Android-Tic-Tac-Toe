package com.tevinjeffrey.tictactoe.game.board;

import com.tevinjeffrey.tictactoe.game.players.Player;

import java.util.ArrayList;
import java.util.List;

//An interface for the game to support different sizes of boards.
// 4x4 and 6x6
public interface Board {

    void pick(int pickIndex);

    void clickCell(Cell cell);

    Player getWinner();

    boolean isDraw();

    Cell[] getWinningLine(List<Cell> cells);

    List<Cell> getCells();

    boolean isGameOver(List<Cell> cells);

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
