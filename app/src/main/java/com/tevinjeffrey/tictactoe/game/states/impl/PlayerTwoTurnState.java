package com.tevinjeffrey.tictactoe.game.states.impl;


import com.tevinjeffrey.tictactoe.game.TicTacToe;
import com.tevinjeffrey.tictactoe.game.board.Board;
import com.tevinjeffrey.tictactoe.game.states.GameState;

public class PlayerTwoTurnState implements GameState {

    private final TicTacToe ticTacToe;

    public PlayerTwoTurnState(TicTacToe ticTacToe) {
        this.ticTacToe = ticTacToe;
    }

    @Override
    public void playerOneNewGame() {
        //Reason - applies in only multiplayer across network.
        /*//It's player 2's turn when player 1 starts new game.
        ticTacToe.gameNotification(ticTacToe.getPlayerTwo().getPlayerName() + " wins!");
        ticTacToe.notifyWinner(ticTacToe.getPlayerTwo());

        ticTacToe.setGameState(ticTacToe.getNewGameState());*/
    }

    @Override
    public void playerTwoNewGame() {
        //It's player 2's turn when player 2 starts new game.
        ticTacToe.gameNotification(ticTacToe.getPlayerOne().getPlayerName() + " wins!");
        ticTacToe.notifyWinner(ticTacToe.getPlayerOne());

        ticTacToe.setGameState(ticTacToe.getNewGameState());
    }

    @Override
    public void playerOnePick(Board board, int pickIndex) {
        //It's player 1's turn when player 1 picks a cell.

    }

    @Override
    public void playerTwoPick(Board board, int pickIndex) {
        //It's player 2's turn when player 2 picks a cell.

        if(!board.getCells().get(pickIndex).isTaken()) {
            ticTacToe.getPlayerTwo().pick(board, pickIndex);

        } else {
            ticTacToe.gameNotification("That slot is taken");
            ticTacToe.setGameState(ticTacToe.getPlayerTwoTurnState());

        }
    }

    @Override
    public String toString() {
        return "PlayerTwoTurnState";
    }
}
