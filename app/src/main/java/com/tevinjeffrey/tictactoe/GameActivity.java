package com.tevinjeffrey.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.tevinjeffrey.tictactoe.game.TicTacToe;
import com.tevinjeffrey.tictactoe.game.cell.CellState;
import com.tevinjeffrey.tictactoe.game.players.Player;
import com.tevinjeffrey.tictactoe.game.players.impl.AiPlayer;
import com.tevinjeffrey.tictactoe.game.players.impl.LocalPlayer;
import com.tevinjeffrey.tictactoe.game.states.GameState;
import com.tevinjeffrey.tictactoe.game.states.impl.EndGameState;
import com.tevinjeffrey.tictactoe.game.states.impl.NewGameState;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerOneTurnState;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerTwoTurnState;
import com.tevinjeffrey.tictactoe.settings.SettingsEnum;
import com.tevinjeffrey.tictactoe.settings.SettingsListener;
import com.tevinjeffrey.tictactoe.settings.SettingsPanel;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.tevinjeffrey.tictactoe.TTTApp.LOCAL;
import static com.tevinjeffrey.tictactoe.TTTApp.PREF_IMPOSSIBLE_DIFFICULTY;
import static com.tevinjeffrey.tictactoe.TTTApp.PREF_PLAYER_ONE;
import static com.tevinjeffrey.tictactoe.TTTApp.PREF_PLAYER_ONE_NAME;
import static com.tevinjeffrey.tictactoe.TTTApp.PREF_PLAYER_ONE_SCORE;
import static com.tevinjeffrey.tictactoe.TTTApp.PREF_PLAYER_TWO;
import static com.tevinjeffrey.tictactoe.TTTApp.PREF_PLAYER_TWO_NAME;
import static com.tevinjeffrey.tictactoe.TTTApp.PREF_PLAYER_TWO_SCORE;


public class GameActivity extends Activity {

    @InjectView(R.id.player1Title)
    TextView playerOneTitle;
    @InjectView(R.id.player2Title)
    TextView playerTwoTitle;
    @InjectView(R.id.player1Score)
    TextView playerOneScore;
    @InjectView(R.id.player2Score)
    TextView playerTwoScore;
    @InjectView(R.id.stateDisplay)
    TextView stateDisplay;
    @InjectView(R.id.retry)
    Button btnRetry;

    private Player playerOne;
    private Player playerTwo;
    private final TicTacToe.TTTGameCallback mTTTGameCallback = new TicTacToe.TTTGameCallback() {
        @Override
        public void onWinner(Player player) {
            incrementPlayerScore(player);
        }

        @Override
        public void onMessage(CharSequence message) {
            stateDisplay.setText(message);
        }

        @Override
        public void onDraw() {
        }

        @Override
        public void onPlayerChange(Player currentPlayer) {

        }
    };
    private TicTacToe game;
    private ViewGroup boardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inject select views from activity_main.xml
        ButterKnife.inject(this);

        SettingsPanel settingsPanel = new SettingsPanel(this);
        settingsPanel.setUp();
        settingsPanel.setSettingsListener(new SettingsListener() {
            @Override
            public void onSettingsChanged(SettingsEnum setting) {
                refreshGameViews();
            }

            @Override
            public void onReset() {
                startNewGame();
            }
        });

        boardLayout = (ViewGroup) findViewById(R.id.boardLayout);

        //Check for config changes.
        if (savedInstanceState == null) {
            startNewGame();
        }

    }

    // A new game starts when the app launches, and when the player configuration or difficulty is
    // changed in the settings.
    private void startNewGame() {
        if (Hawk.get(PREF_PLAYER_ONE).equals(LOCAL)) {
            playerOne = new LocalPlayer(Hawk.get(PREF_PLAYER_ONE_NAME, ""));

        } else {
            playerOne = new AiPlayer(Hawk.get(PREF_PLAYER_ONE_NAME, ""), Hawk.get(PREF_IMPOSSIBLE_DIFFICULTY, true));
        }

        if (Hawk.get(PREF_PLAYER_TWO).equals(LOCAL)) {
            playerTwo = new LocalPlayer(Hawk.get(PREF_PLAYER_TWO_NAME, ""));
        } else {
            playerTwo = new AiPlayer(Hawk.get(PREF_PLAYER_TWO_NAME, ""), Hawk.get(PREF_IMPOSSIBLE_DIFFICULTY, true));
        }

        playerOne.setPlayerId(CellState.PLAYER_ONE);
        playerTwo.setPlayerId(CellState.PLAYER_TWO);
        playerOne.savePlayerName();
        playerTwo.savePlayerName();

        //Must be called after players were set.
        refreshGameViews();

        game = new TicTacToe(this, playerOne, playerTwo, mTTTGameCallback);
        game.setRetryButton(btnRetry);
        game.newGame(boardLayout);
        game.start();
    }

    private void refreshGameViews() {
        setPlayerOneName(Hawk.get(PREF_PLAYER_ONE_NAME, ""));
        setPlayerTwoName(Hawk.get(PREF_PLAYER_TWO_NAME, ""));

        setPlayerOneScore(Hawk.get(PREF_PLAYER_ONE_SCORE, 0));
        setPlayerTwoScore(Hawk.get(PREF_PLAYER_TWO_SCORE, 0));
    }

    private void setPlayerOneScore(Integer score) {
        playerOne.setPlayerScore(score);
        playerOneScore.setText(score.toString());
    }

    private void setPlayerTwoScore(Integer score) {
        playerTwo.setPlayerScore(score);
        playerTwoScore.setText(score.toString());
    }
    
    private void incrementPlayerScore(Player player) {
        if (player.getPlayerId().equals(CellState.PLAYER_ONE)) {
            setPlayerOneScore(playerOne.getPlayerScore() + 1);
        } else {
            setPlayerTwoScore(playerTwo.getPlayerScore() + 1);
        }
    }

    private void setPlayerOneName(String name) {
        playerOneTitle.setText(name);
    }

    private void setPlayerTwoName(String name) {
        playerTwoTitle.setText(name);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        GameState gs = game.getGameState();
        int state = 0;
        if (gs instanceof PlayerOneTurnState) {
            state = 1;
        } else if (gs instanceof PlayerTwoTurnState) {
            state = 2;
        } else if (gs instanceof NewGameState) {
            state = 3;
        } else if (gs instanceof EndGameState) {
            state = 4;
        }

        //Save only what is necessary of the recreation of the the activity
        outState.putInt("GAME_STATE", state);
        outState.putParcelable("PLAYER_ONE", playerOne);
        outState.putParcelable("PLAYER_TWO", playerTwo);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //Must call before restoring the game state as vital info about the game's state is
        // stored in the ImageCellView.
        super.onRestoreInstanceState(savedInstanceState);

        playerOne = savedInstanceState.getParcelable("PLAYER_ONE");
        playerTwo = savedInstanceState.getParcelable("PLAYER_TWO");

        game = new TicTacToe(this, playerOne, playerTwo, mTTTGameCallback);
        game.setRetryButton(btnRetry);


        int state = savedInstanceState.getInt("GAME_STATE");
        if (state == 1) {
            game.setGameState(new PlayerOneTurnState(game));
        } else if (state == 2) {
            game.setGameState(new PlayerTwoTurnState(game));
        } else if (state == 3) {
            game.setGameState(new NewGameState(game));
        } else if (state == 4) {
            game.setGameState(new EndGameState(game));
        }

        game.resumeGame(boardLayout);

        refreshGameViews();
    }

    //Intercept touch events while the AI is making a move.
    //TODO investigate intercepting clicks on the actual board.
    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if (game != null) {
            if((game.getCurrentPlayer() instanceof AiPlayer) && game.getGameState() != game.getEndGameState()) {
                return false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
