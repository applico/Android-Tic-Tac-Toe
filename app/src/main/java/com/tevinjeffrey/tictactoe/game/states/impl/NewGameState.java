package com.tevinjeffrey.tictactoe.game.states.impl;


import com.tevinjeffrey.tictactoe.game.TicTacToe;
import com.tevinjeffrey.tictactoe.game.board.Board;
import com.tevinjeffrey.tictactoe.game.states.GameState;

public class NewGameState implements GameState {

    private final TicTacToe ticTacToe;

    public NewGameState(TicTacToe ticTacToe) {
        this.ticTacToe = ticTacToe;
    }

    @Override
    public void playerOneNewGame() {
        //From a new game start, player 1 goes first.
        ticTacToe.setGameState(ticTacToe.getPlayerOneTurnState());
    }

    @Override
    public void playerTwoNewGame() {
        //From a new game start, player 2 goes first.
        ticTacToe.setGameState(ticTacToe.getPlayerTwoTurnState());
    }

    @Override
    public void playerOnePick(Board board, int pickIndex) {
        //Do nothing
    }

    @Override
    public void playerTwoPick(Board board, int pickIndex) {
        //Do nothing
    }

    @Override
    public String toString() {
        return "NewGameState";
    }
}
