package com.tevinjeffrey.tictactoe.game;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.tevinjeffrey.tictactoe.R;
import com.tevinjeffrey.tictactoe.customviews.ImageCellView;
import com.tevinjeffrey.tictactoe.game.board.Board;
import com.tevinjeffrey.tictactoe.game.board.BoardCallback;
import com.tevinjeffrey.tictactoe.game.cell.Cell;
import com.tevinjeffrey.tictactoe.game.cell.CellState;
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
    private ViewGroup mBoardLayout;

    private final Player mPlayerOne;
    private final Player mPlayerTwo;

    private Player currentPlayer;

    private GameState mGameState;
    private Board mBoard;
    private final TTTGameCallback mTTTGameCallback;

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

    public void resumeGame(ViewGroup boardLayout) {
        this.mBoardLayout = boardLayout;
        mBoard = new ThreeBoard(mBoardCallback, boardLayout);
        evaluateGameState(true);
    }

    public void newGame(ViewGroup boardLayout) {
        this.mBoardLayout = boardLayout;
        mBoard = new ThreeBoard(mBoardCallback, boardLayout);
        mBoard.clear();
    }

    private void evaluateGameState(boolean resumedGame) {
        getBoard().evaluate(resumedGame);
    }

    public Board getBoard() {
        return mBoard;
    }

    public void start() {
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
        if (gameState instanceof PlayerOneTurnState) {
            setCurrentPlayer(getPlayerOne());
            gameNotification(getPlayerOne().getPlayerName() + " turn.");
            checkIfAiTurn();
        } else if (gameState instanceof PlayerTwoTurnState){
            setCurrentPlayer(getPlayerTwo());
            gameNotification(getPlayerTwo().getPlayerName() + " turn.");
            checkIfAiTurn();
        }

    }

    private final BoardCallback mBoardCallback = new BoardCallback() {
        @Override
        public void onPlayerPick(Board board, int pickIndex) {
            if (getGameState() instanceof PlayerOneTurnState)
                getGameState().playerOnePick(board, pickIndex);
            else if (getGameState() instanceof  PlayerTwoTurnState){
                getGameState().playerTwoPick(board, pickIndex);
            }
        }

        @Override
        public void onCellInvoke(CellState player) {
            //TODO play game sounds here
            evaluateGameState(false);
        }

        @Override
        public void onWinner(Cell[] winningCells) {
            animateWinner(winningCells);

            Player currentPlayer = getCurrentPlayer();
            if (currentPlayer == null) {
                currentPlayer = getPlayer(winningCells[0].getState());
            }

            notifyWinner(currentPlayer);
            gameNotification(currentPlayer.getPlayerName() + " wins.");

            setGameState(getEndGameState());
        }

        @Override
        public void nextPlayer() {
            if (getGameState() instanceof PlayerOneTurnState) {
                setGameState(getPlayerTwoTurnState());
            } else if (getGameState() instanceof PlayerTwoTurnState) {
                setGameState(getPlayerOneTurnState());
            }
        }

        @Override
        public void onClear() {
            setGameState(getNewGameState());
        }

        @Override
        public void onDraw() {
            notifyDraw();
            gameNotification("It's a draw!");

            setGameState(getEndGameState());
        }

    };

    private void checkIfAiTurn() {
        if (isAiTurn(currentPlayer)) {
            playAi((AiPlayer) currentPlayer);

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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private boolean isAiTurn(Player player) {
        return player instanceof AiPlayer;
    }

    private void playAi(final AiPlayer player) {
        final Thread aiThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                player.decide(TicTacToe.this, new AiPlayer.AiCallback() {
                    @Override
                    public void onComplete(final int pickIndex) {
                        if (mActivity != null) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mBoard.checkPlayerPick(mBoard.getCells().get(pickIndex - 1));
                                }
                            });
                        }
                    }
                });
            }
        };
        aiThread.start();
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

    public void gameNotification(CharSequence message) {
        notifyMessage(message);
    }

    public Player getPlayerOne() {
        return mPlayerOne;
    }

    public Player getPlayerTwo() {
        return mPlayerTwo;
    }

    private Player getPlayer(CellState cellState) {
        return cellState.equals(CellState.PLAYER_ONE)? getPlayerOne():getPlayerTwo();
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
                newGame(mBoardLayout);
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

}
