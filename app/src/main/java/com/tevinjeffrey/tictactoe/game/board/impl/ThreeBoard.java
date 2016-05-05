package com.tevinjeffrey.tictactoe.game.board.impl;

import android.view.View;

import com.tevinjeffrey.tictactoe.R;
import com.tevinjeffrey.tictactoe.game.board.Board;
import com.tevinjeffrey.tictactoe.game.board.BoardCallback;
import com.tevinjeffrey.tictactoe.game.cell.Cell;
import com.tevinjeffrey.tictactoe.game.cell.CellState;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectViews;
import butterknife.OnClick;

import static com.tevinjeffrey.tictactoe.game.cell.CellState.BLANK;
import static com.tevinjeffrey.tictactoe.game.cell.CellState.PLAYER_ONE;
import static com.tevinjeffrey.tictactoe.game.cell.CellState.PLAYER_TWO;

//An implementation of a 3x3 board.
public class ThreeBoard implements Board {

    private final BoardCallback mBoardCallback;
    @InjectViews({R.id.cell_1, R.id.cell_2, R.id.cell_3, R.id.cell_4, R.id.cell_5, R.id.cell_6, R.id.cell_7, R.id.cell_8, R.id.cell_9})
    List<Cell> cells;

    public List<Cell> getCells() {
        return cells;
    }

    public ThreeBoard(BoardCallback callback, View boardLayout) {
        this.mBoardCallback = callback;
        setupCells(boardLayout);
    }

    private void setupCells(View boardLayout) {
        ButterKnife.inject(this, boardLayout);

        int i = 1;
        for (Cell cell : getCells()) {
            cell.setIndex(i);
            int value;
            switch(i) {
                case 1:
                    value = 8;
                    break;
                case 2:
                    value = 1;
                    break;
                case 3:
                    value = 6;
                    break;
                case 4:
                    value = 3;
                    break;
                case 5:
                    value = 5;
                    break;
                case 6:
                    value = 7;
                    break;
                case 7:
                    value = 4;
                    break;
                case 8:
                    value = 9;
                    break;
                case 9:
                    value = 2;
                    break;
                default:
                    value = -1;
                    break;
            }
            i++;
            cell.setValue(value);

        }
    }

    //Fires the click on the current state state of the game. From there the game will
    // determine what to do.
    @OnClick({R.id.cell_1, R.id.cell_2, R.id.cell_3, R.id.cell_4, R.id.cell_5,
            R.id.cell_6, R.id.cell_7, R.id.cell_8, R.id.cell_9})
    public void checkPlayerPick(Cell cell) {
        mBoardCallback.onPlayerPick(this, cell.getIndex() - 1);
    }

    @Override
    public void invokeCell(CellState who, int pickIndex) {
        if (who.equals(PLAYER_ONE)) {
            cells.get(pickIndex).setState(PLAYER_ONE);
            mBoardCallback.onCellInvoke(who);
        } else if (who.equals(PLAYER_TWO)) {
            cells.get(pickIndex).setState(PLAYER_TWO);
            mBoardCallback.onCellInvoke(who);
        }

    }

    public boolean isGameOver(List<Cell> cells) {
        return BoardUtils.isCellsFull(cells) || getWinner(getWinningLine(cells)) != null;
    }

    @Override
    public void evaluate(boolean resumedGame) {
        CellState winner = getWinner();

        if(winner != null) {
            mBoardCallback.onWinner(getWinningLine(getCells()));
        } else if (isDraw()) {
            mBoardCallback.onDraw();
        } else if (!resumedGame){
            mBoardCallback.nextPlayer();
        }
    }

    @Override
    public void clear() {
        for (Cell cell : getCells()) {
            cell.setState(BLANK);
        }
        mBoardCallback.onClear();
    }

    public CellState getWinner() {
        Cell[] winningTriple = getWinningLine(getCells());
        if (winningTriple != null) {
            return winningTriple[0].getState();
        }
        return null;
    }

    @Override
    public boolean isDraw() {
        return BoardUtils.isCellsFull(getCells()) &&
                getWinner(getWinningLine(getCells())) == null;
    }

    private static CellState getWinner(Cell[] winningTriple) {
        if (winningTriple != null) {
            return winningTriple[0].getState();
        }
        return null;
    }

    public Cell[] getWinningLine(List<Cell> cells) {
        Cell[] a = new Cell[]{cells.get(0), cells.get(1), cells.get(2)};
        Cell[] b = new Cell[]{cells.get(3), cells.get(4), cells.get(5)};
        Cell[] c = new Cell[]{cells.get(6), cells.get(7), cells.get(8)};
        Cell[] d = new Cell[]{cells.get(0), cells.get(3), cells.get(6)};
        Cell[] e = new Cell[]{cells.get(1), cells.get(4), cells.get(7)};
        Cell[] f = new Cell[]{cells.get(2), cells.get(5), cells.get(8)};
        Cell[] g = new Cell[]{cells.get(0), cells.get(4), cells.get(8)};
        Cell[] h = new Cell[]{cells.get(2), cells.get(4), cells.get(6)};
        List<Cell[]> winningConditions = Arrays.asList(a, b, c, d, e, f, g, h);
        for (int i = 0; i < winningConditions.size(); i++) {
            Cell[] condition = winningConditions.get(i);
            if (isWinningTriple(condition)) {
                return condition;
            }
        }
        return null;
    }


    //http://rowdy.msudenver.edu/~gordona/cs1050/progs/
    /* -------------|
    *   8 |  1 |  6 |
    * ----|----|----|
    *   3 |  5 |  7 |
    * ----|----|----|
    *   4 |  9 |  2 |
    * --------------| All rows, columns and diagonals must equal 15
    * */
    private static boolean isWinningTriple(Cell[] cells) {
        int score = 0;
        CellState temp = cells[0].getState();

        for (Cell c : cells) {
            if (c.isTaken()) {
                if (c.getState() == temp) {
                    score += c.getValue();
                }
            }
        }
        return score == 15;
    }
}
