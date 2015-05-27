package com.tevinjeffrey.tictactoe.game.board.impl;

import android.view.View;

import com.tevinjeffrey.tictactoe.R;
import com.tevinjeffrey.tictactoe.game.TicTacToe;
import com.tevinjeffrey.tictactoe.game.board.Board;
import com.tevinjeffrey.tictactoe.game.board.Cell;
import com.tevinjeffrey.tictactoe.customviews.ImageCellView;
import com.tevinjeffrey.tictactoe.game.players.Player;
import com.tevinjeffrey.tictactoe.game.states.impl.EndGameState;
import com.tevinjeffrey.tictactoe.game.states.impl.NewGameState;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerOneTurnState;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerTwoTurnState;
import static com.tevinjeffrey.tictactoe.game.board.Cell.CellState.BLANK;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectViews;
import butterknife.OnClick;

//An implementation of a 3x3 board.
public class ThreeBoard implements Board {

    @InjectViews({R.id.cell_1, R.id.cell_2, R.id.cell_3, R.id.cell_4, R.id.cell_5, R.id.cell_6, R.id.cell_7, R.id.cell_8, R.id.cell_9})
    List<Cell> cells;

    public List<Cell> getCells() {
        return cells;
    }


    private final TicTacToe tictactoe;

    public ThreeBoard(TicTacToe ticTacToe, View boardLayout) {
        this.tictactoe = ticTacToe;
        setupCells(boardLayout);
    }

    private void setupCells(View boardLayout) {
        ButterKnife.inject(this, boardLayout);

        int i = 1;
        for (Cell cell : getCells()) {
            if (tictactoe.getGameState() instanceof NewGameState || tictactoe
                    .getGameState() instanceof EndGameState) {
                cell.setState(BLANK);
            }
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


    @OnClick({R.id.cell_1, R.id.cell_2, R.id.cell_3, R.id.cell_4, R.id.cell_5,
            R.id.cell_6, R.id.cell_7, R.id.cell_8, R.id.cell_9})
    public void onClick(Cell cell) {
        clickCell(cell);
    }

    @Override
    public void pick(int pickIndex) {
        if (tictactoe.getGameState() instanceof PlayerOneTurnState) {
            cells.get(pickIndex).setState(ImageCellView.CellState.PLAYER_ONE);
        } else if (tictactoe.getGameState() instanceof PlayerTwoTurnState) {
            cells.get(pickIndex).setState(ImageCellView.CellState.PLAYER_TWO);
        }
    }

    @Override
    public void clickCell(Cell cell) {
        if (tictactoe.getGameState() instanceof PlayerOneTurnState)
            tictactoe.getGameState().playerOnePick(this, cell.getIndex() - 1);
        else if (tictactoe.getGameState() instanceof  PlayerTwoTurnState){
            tictactoe.getGameState().playerTwoPick(this, cell.getIndex() - 1);
        }
    }

    public boolean isGameOver(List<Cell> cells) {
        return BoardUtils.isCellsFull(cells) || getWinner(getWinningLine(cells)) != null;
    }


    public Player getWinner() {
        Cell[] winningTriple = getWinningLine(getCells());
        if (winningTriple != null) {
            Cell winningCell = winningTriple[0];
            if (winningCell.isPlayerOne()) {
                return tictactoe.getPlayerOne();
            } else if (winningCell.isPlayerTwo()){
                return tictactoe.getPlayerTwo();
            }
        }
        return null;
    }

    @Override
    public boolean isDraw() {
        return BoardUtils.isCellsFull(getCells()) &&
                getWinner(getWinningLine(getCells())) == null;
    }

    private Player getWinner(Cell[] winningTriple) {
        if (winningTriple != null) {
            Cell winningCell = winningTriple[0];
            if (winningCell.isPlayerOne()) {
                return tictactoe.getPlayerOne();
            } else if (winningCell.isPlayerTwo()){
                return tictactoe.getPlayerTwo();
            }
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


    //http://rowdy.msudenver.edu/~gordona/cs1050/progs/tictactoermccsc.pdf
    /* -------------|
    *   8 |  1 |  6 |
    * ----|----|----|
    *   3 |  5 |  7 |
    * ----|----|----|
    *   4 |  9 |  2 |
    * --------------| All rows, columns and diagonals must equal 15
    * */
    private boolean isWinningTriple(Cell[] cells) {
        int score = 0;
        Cell.CellState temp = cells[0].getState();

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
