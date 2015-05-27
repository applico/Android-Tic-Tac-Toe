package com.tevinjeffrey.tictactoe.game;

import android.app.Activity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.tevinjeffrey.tictactoe.R;
import com.tevinjeffrey.tictactoe.customviews.ImageCellView;
import com.tevinjeffrey.tictactoe.game.board.Board;
import com.tevinjeffrey.tictactoe.game.board.Cell;
import com.tevinjeffrey.tictactoe.game.board.impl.ThreeBoard;
import com.tevinjeffrey.tictactoe.game.players.impl.AiPlayer;
import com.tevinjeffrey.tictactoe.game.players.Player;
import com.tevinjeffrey.tictactoe.game.states.impl.EndGameState;
import com.tevinjeffrey.tictactoe.game.states.GameState;
import com.tevinjeffrey.tictactoe.game.states.impl.NewGameState;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerOneTurnState;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerTwoTurnState;


import java.util.Random;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class TicTacToe {

    private final Activity mActivity;
    private View mBoardLayout;

    private final Player mPlayerOne;
    private final Player mPlayerTwo;

    private Player currentPlayer;

    private GameState mGameState;
    private Board mBoard;
    private TTTGameCallback mTTTGameCallback;
    private TTTStateCallback mTTTStateCallback;

    private final PlayerOneTurnState mPlayerOneTurnState = new PlayerOneTurnState(this);
    private final PlayerTwoTurnState mPlayerTwoTurnState = new PlayerTwoTurnState(this);
    private final NewGameState mNewGameState = new NewGameState(this);
    private final EndGameState mEndGameState = new EndGameState(this);

    public GameState getGameState() {
        return mGameState;
    }

    public TicTacToe(Activity mActivity, Player playerOne, Player playerTwo, TTTGameCallback tttGameCallback) {
        this.mActivity = mActivity;
        this.mPlayerOne = playerOne;
        this.mPlayerTwo = playerTwo;
        this.mTTTGameCallback = tttGameCallback;
    }

    public void setBoard(View boardLayout) {
        this.mBoardLayout = boardLayout;
        mBoard = new ThreeBoard(this, boardLayout);
    }

    public Board getBoard() {
        return mBoard;
    }

    public void start() {
        setGameState(mNewGameState);

        //Chooses the starting user randomly.
        Random rand = new Random();
        int randUser = rand.nextInt(2);
        if (randUser == 0) {
            setGameState(mPlayerTwoTurnState);
        } else {
            setGameState(mPlayerOneTurnState);
        }
    }

    public void setGameState(GameState gameState) {
        this.mGameState = gameState;
        notifyStateCallback(gameState);
        if (gameState instanceof PlayerOneTurnState) {
            setCurrentPlayer(getPlayerOne());
            gameNotification(getPlayerOne().getPlayerName() + " turn.");
            checkIfAiShouldPlay(getPlayerOne());
        } else if (gameState instanceof PlayerTwoTurnState){
            setCurrentPlayer(getPlayerTwo());
            gameNotification(getPlayerTwo().getPlayerName() + " turn.");
            checkIfAiShouldPlay(getPlayerTwo());
        } else if (gameState instanceof EndGameState) {
            Player winner = mBoard.getWinner();
            if (winner != null) {
                animateWinner(mBoard.getWinningLine(mBoard.getCells()));
                notifyWinner(winner);
                gameNotification(winner.getPlayerName() + " wins.");
            } else if (mBoard.isDraw()) {
                notifyDraw();
                gameNotification("It's a draw!");

            }
        }

    }

    private void animateWinner(Cell[] winningTriple) {
        int delay = 250;
        for (final Cell cell : winningTriple) {
            animate((ImageCellView)cell).setDuration(250).setInterpolator(new DecelerateInterpolator()).rotation(0);
            ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor",
                    mActivity.getResources().getColor(R.color.primary),
                    mActivity.getResources().getColor(R.color.accent));
            colorAnim.setDuration(delay);
            colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ((ImageCellView) cell).setBackgroundColor((Integer) animation.getAnimatedValue());
                }
            });
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setStartDelay(250);
            colorAnim.start();
            delay = delay + 250;

        }
    }

    private void setCurrentPlayer(Player player) {
        notifyPlayerChange(player);
        currentPlayer = player;
    }

    private void checkIfAiShouldPlay(Player player) {
        if (player instanceof AiPlayer) {
            int pickIndex = ((AiPlayer)player).decide(this);
            mBoard.clickCell(mBoard.getCells().get(pickIndex - 1));
        }
    }

    public void setTTTStateCallback(TTTStateCallback mTTTStateCallback) {
        this.mTTTGameCallback = mTTTGameCallback;
    }

    public void notifyWinner(Player player) {
        if (mTTTGameCallback != null) {
            mTTTGameCallback.onWinner(player);
        }
    }

    private void notifyDraw() {
        if (mTTTGameCallback != null) {
            mTTTGameCallback.onDraw();
        }
    }

    private void notifyMessage(CharSequence message) {
        if (mTTTGameCallback != null) {
            mTTTGameCallback.onMessage(message);
        }
    }

    private void notifyPlayerChange(Player player) {
        if (mTTTGameCallback != null) {
            mTTTGameCallback.onPlayerChange(player);
        }
    }

    private void notifyStateCallback(GameState gameState) {
        if (mTTTStateCallback != null) {
            if (gameState instanceof PlayerOneTurnState) {
               mTTTStateCallback.onPlayerOneState(getPlayerOne());
            } else if (gameState instanceof PlayerTwoTurnState){
                mTTTStateCallback.onPlayerTwoSate(getPlayerTwo());
            } else if (gameState instanceof NewGameState) {
                mTTTStateCallback.onNewGameState(currentPlayer);
            } else if (gameState instanceof EndGameState) {
                mTTTStateCallback.onEndGameState(currentPlayer);
            }
        }
    }

    public void gameNotification(CharSequence message) {
        notifyMessage(message);
    }

    public Player getPlayerOne() {
        return mPlayerOne;
    }

    public Player getPlayerTwo() {
        return mPlayerTwo;
    }

    public PlayerOneTurnState getPlayerOneTurnState() {
        return mPlayerOneTurnState;
    }

    public PlayerTwoTurnState getPlayerTwoTurnState() {
        return mPlayerTwoTurnState;
    }

    public NewGameState getNewGameState() {
        return mNewGameState;
    }

    public EndGameState getEndGameState() {
        return mEndGameState;
    }

    public void setRetryButton(Button retryButton) {
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getGameState() instanceof PlayerOneTurnState) {
                    getGameState().playerOneNewGame();
                } else if (getGameState() instanceof PlayerTwoTurnState) {
                    getGameState().playerTwoNewGame();
                }
                setBoard(mBoardLayout);
                start();
            }
        });
    }

    public interface TTTGameCallback {
        void onWinner(Player player);
        void onMessage(CharSequence message);
        void onDraw();
        void onPlayerChange(Player currentPlayer);
    }

    public interface TTTStateCallback {
        void onNewGameState(Player player);
        void onEndGameState(Player player);
        void onPlayerOneState(Player player);
        void onPlayerTwoSate(Player player);
    }
}
