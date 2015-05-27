package com.tevinjeffrey.tictactoe.game.players.impl;

import com.tevinjeffrey.tictactoe.game.TicTacToe;
import com.tevinjeffrey.tictactoe.game.board.Board;
import static com.tevinjeffrey.tictactoe.game.board.Board.BoardUtils;

import com.tevinjeffrey.tictactoe.game.board.Cell;
import com.tevinjeffrey.tictactoe.game.board.impl.CellImpl;
import com.tevinjeffrey.tictactoe.game.players.Player;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerOneTurnState;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerTwoTurnState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AiPlayer extends Player {

    private final boolean perfectAi;
    private final Random rand = new Random();

    private Player ai;
    private Player opponent;
    private Board board;
    private int choice;


    public AiPlayer(String name, boolean perfectAi) {
        super.setPlayerName(name);
        this.perfectAi = perfectAi;
    }

    //TODO move all of AI off of the UI thread
    public int decide(TicTacToe game) {
        choice = 0;
        whoAmI(game);
        board = game.getBoard();

        //optimizations to prevent the ai for checking EVERY possible outcome from first move.

        //The bug that became a feature. On first play on the board, the ai chooses a corner cell if
        // it's not already chosen by the user, because finding every possible play on an empty board
        // uses a considerable amount of memory. The bug? Choosing the corner is not always
        // the best move on the second play of the game (opponent then ai) and can open up opportunities
        // for the user to win. The fix? Only choose the corner on the first play. The feature?
        // Disable the fix, now we have a new difficult level :D
        if (!isACornerChecked(board.getCells())
                && (isBoardEmpty(board.getCells()) || !perfectAi)) {
            int randCorner = rand.nextInt(4);
            int[] corners = {1, 3, 7, 9};
            return corners[randCorner];
        }

        //Choose center if not already chosen.
        if (!isCenterChecked(board.getCells()) && aiMadeNoMoves(board.getCells()) && isCenterChecked(board.getCells())) {
            return 5;
        }

        //Minimax algorithm to maximize the AI's winning potential.
        minimax(board.getCells(), 0, ai.getPlayerId());


        return choice;
    }

    //http://neverstopbuilding.com/minimax
    private int minimax(final List<Cell> cells, int depth, Cell.CellState state) {
        if (board.isGameOver(cells)) {
            return score(cells, depth);
        }
        List<Integer> scores = new ArrayList<>();
        List<Integer> moves = new ArrayList<>();
        depth++;

        for (Cell possibleMove : BoardUtils.getPossibleMoves(cells)) {
            //Create a new board of possible games which the minimax algorithm
            // will recursively traverse
            List<Cell> possibleGame = getNewGame(cells);
            possibleGame.get(possibleMove.getIndex() - 1).setState(state.equals(ai.getPlayerId())?
                    ai.getPlayerId() : opponent.getPlayerId());
            scores.add(minimax(possibleGame, depth, state.equals(ai.getPlayerId())?
                    opponent.getPlayerId() : ai.getPlayerId()));
            moves.add(possibleMove.getIndex());
        }


        // Do the min or the max calculation
        if (state.equals(ai.getPlayerId())) {
            //noinspection ToArrayCallWithZeroLengthArrayArgument
            int maxScoreIndex = max(scores.toArray(new Integer[0]));
            if (scores.size() > 0) {
                choice = moves.get(maxScoreIndex);
                return scores.get(maxScoreIndex);
            }
            return 0;
        } else {
            //noinspection ToArrayCallWithZeroLengthArrayArgument
            int minScoreIndex = min(scores.toArray(new Integer[0]));
            if (scores.size() > 0) {
                choice = moves.get(minScoreIndex);
                return scores.get(minScoreIndex);
            }
            return 0;
        }

    }

    private int score(List<Cell> cells, int depth) {
        Cell[] winner = board.getWinningLine(cells);
        if(winner != null) {
            if (winner[0].getState().equals(ai.getPlayerId())) {
                return 10 - depth;
            } else if (winner[0].getState().equals(opponent.getPlayerId())) {
                return depth - 10;
            }
        }
        return 0;
    }

    private boolean isBoardEmpty(List<Cell> cells) {
        boolean isBoardEmpty = true;
        for (Cell cell : cells) {
            if (!cell.isBlank()) {
                isBoardEmpty = false;
            }
        }
        return isBoardEmpty;
    }
    private boolean isACornerChecked(List<Cell> cells) {
        boolean cornerChecked = false;
        for(Cell cell : cells) {
            if (cell.getIndex() == 1 || cell.getIndex() == 3
                    || cell.getIndex() == 7 || cell.getIndex() == 9) {
                if (cell.getState() != Cell.CellState.BLANK) {
                    cornerChecked = true;
                }
            }
        }
        return cornerChecked;
    }

    private boolean aiMadeNoMoves(List<Cell> cells) {
        boolean aiMadeNoMoves = true;
        for (Cell cell : cells) {
            if (cell.getState().equals(ai.getPlayerId())) {
                aiMadeNoMoves = false;
            }
        }
        return aiMadeNoMoves;
    }

    private boolean isCenterChecked(List<Cell> cells) {
        boolean centerChecked = false;
        for (Cell cell : cells) {
            if (cell.getIndex() == 5 && cell.isTaken()) {
                centerChecked = true;
            }
        }
        return centerChecked;
    }

    private void whoAmI(TicTacToe game) {
        if (game.getGameState() instanceof PlayerOneTurnState){
            ai = game.getPlayerOne();
            opponent = game.getPlayerTwo();
        } else if (game.getGameState() instanceof PlayerTwoTurnState) {
            ai = game.getPlayerTwo();
            opponent = game.getPlayerOne();
        }
    }

    // Why this isn't built into Java, I don't know.
    private int max(Integer[] intArr) {
        int maxIndex = 0;
        for (int i = 1; i < intArr.length; i++) {
            int newNumber = intArr[i];
            if ((newNumber > intArr[maxIndex])) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }


    private int min(Integer[] intArr) {
        int minIndex = 0;
        for (int i = 1; i < intArr.length; i++) {
            int newNumber = intArr[i];
            if ((newNumber < intArr[minIndex])) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    //Helper method to create a new game/node for the algorithm to evaluate.
    private List<Cell> getNewGame(List<Cell> cells) {
        List<Cell> newGame = new ArrayList<>();
        for (Cell cell : cells) {
            newGame.add(new CellImpl(cell));
        }
        return newGame;
    }

    @Override
    public String toString() {
        return "AiPlayer{" +
                "choice=" + choice + "}";
    }

}
