package com.tevinjeffrey.tictactoe.game.states.impl;


import com.tevinjeffrey.tictactoe.game.TicTacToe;
import com.tevinjeffrey.tictactoe.game.board.Board;
import com.tevinjeffrey.tictactoe.game.states.GameState;

// Typically when the the game has the won, lost or draw will this state be activated.
public class EndGameState implements GameState {

    final TicTacToe ticTacToe;

    public EndGameState(TicTacToe ticTacToe) {
        this.ticTacToe = ticTacToe;
    }

    @Override
    public void playerOneNewGame() {
        // When the game ended and playerOne starts a new game.
        ticTacToe.setGameState(ticTacToe.getNewGameState());
    }

    @Override
    public void playerTwoNewGame() {
        // When the game ended and playerOne starts a new game.
        ticTacToe.setGameState(ticTacToe.getNewGameState());
    }

    @Override
    public void playerOnePick(Board board, int pickIndex) {
        //Do Nothing
        //TODO disable the cells when end state is reached.
    }

    @Override
    public void playerTwoPick(Board board, int pickIndex) {
        //Do Nothing
        //TODO disable the cells when end state is reached.
    }

    @Override
    public String toString() {
        return "EndGameState";
    }
}
