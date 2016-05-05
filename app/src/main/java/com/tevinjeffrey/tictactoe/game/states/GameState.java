package com.tevinjeffrey.tictactoe.game.states;

import com.tevinjeffrey.tictactoe.game.board.Board;

// To facilitate the finite state machine pattern that manages possible state a TicTacToe game can
// be in.
public interface GameState {

    void playerOneNewGame();

    void playerTwoNewGame();

    void playerOnePick(Board board, int pickIndex);

    void playerTwoPick(Board board, int pickIndex);

}
