package com.tevinjeffrey.tictactoe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.orhanobut.hawk.Hawk;
import com.tevinjeffrey.stringpicker.StringPicker;
import com.tevinjeffrey.tictactoe.game.TicTacToe;
import com.tevinjeffrey.tictactoe.game.board.Cell;
import com.tevinjeffrey.tictactoe.game.players.Player;
import com.tevinjeffrey.tictactoe.game.players.impl.AiPlayer;
import com.tevinjeffrey.tictactoe.game.players.impl.LocalPlayer;
import com.tevinjeffrey.tictactoe.game.states.GameState;
import com.tevinjeffrey.tictactoe.game.states.impl.EndGameState;
import com.tevinjeffrey.tictactoe.game.states.impl.NewGameState;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerOneTurnState;
import com.tevinjeffrey.tictactoe.game.states.impl.PlayerTwoTurnState;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class GameActivity extends Activity {

    public static final String PREF_DIFFICULTY = "PREF_DIFFICULTY";
    public static final String PREF_PLAYER_ONE_NAME = "PREF_PLAYER_ONE_NAME";
    public static final String PREF_PLAYER_TWO_NAME = "PREF_PLAYER_TWO_NAME";
    public static final String PREF_PLAYER_ONE = "PREF_PLAYER_ONE";
    public static final String PREF_PLAYER_TWO = "PREF_PLAYER_TWO";

    public static final String LOCAL = "Local";
    public static final String AI = "AI";
    public static final String[] PLAYERS = {LOCAL, AI};

    public static final String PREF_PLAYER_ONE_SCORE = "PREF_PLAYER_ONE_SCORE";
    public static final String PREF_PLAYER_TWO_SCORE = "PREF_PLAYER_TWO_SCORE";

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
    @InjectView(R.id.btn_settings)
    ImageView btnSettings;
    @InjectView(R.id.preferencesLayout)
    View preferencesLayout;


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
    private View boardLayout;
    // Animates between the game board and the settings. For LOLLIPOP and above it uses the circular
    // reveal animation. Else it simply slide the two views in and out.
    private final View.OnClickListener settingsBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setupSettings();
            final View gameBoard = boardLayout;
            final View settings = preferencesLayout;

            //hides some of the UI when settings is open.
            if(gameBoard.getVisibility() == View.GONE) {
                btnRetry.setVisibility(View.VISIBLE);
                playerOneScore.setVisibility(View.VISIBLE);
                playerTwoScore.setVisibility(View.VISIBLE);
                playerOneTitle.setVisibility(View.VISIBLE);
                playerTwoTitle.setVisibility(View.VISIBLE);
                stateDisplay.setVisibility(View.VISIBLE);
            } else {
                btnRetry.setVisibility(View.GONE);
                playerOneScore.setVisibility(View.GONE);
                playerTwoScore.setVisibility(View.GONE);
                playerOneTitle.setVisibility(View.GONE);
                playerTwoTitle.setVisibility(View.GONE);
                stateDisplay.setVisibility(View.GONE);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                if (gameBoard.getVisibility() == View.GONE) {
                    // get the center for the clipping circle
                    int cx = (gameBoard.getLeft() + gameBoard.getRight()) / 2;
                    int cy = (gameBoard.getTop() + gameBoard.getBottom()) / 2;

                    // get the final radius for the clipping circle
                    int finalRadius = Math.max(gameBoard.getWidth(), gameBoard.getHeight());

                    // create the animator for this view (the start radius is zero)
                    Animator anim =
                            ViewAnimationUtils.createCircularReveal(gameBoard, cx, cy, 0, finalRadius);

                    // make the view visible and start the animation
                    gameBoard.setVisibility(View.VISIBLE);
                    anim.start();
                } else {
                    // previously visible view

                    // get the center for the clipping circle
                    int cx = (gameBoard.getLeft() + gameBoard.getRight()) / 2;
                    int cy = (gameBoard.getTop() + gameBoard.getBottom()) / 2;

                    // get the initial radius for the clipping circle
                    int initialRadius = gameBoard.getWidth();

                    // create the animation (the final radius is zero)
                    Animator anim =
                            ViewAnimationUtils.createCircularReveal(gameBoard, cx, cy, initialRadius, 0);

                    // make the view invisible when the animation is done
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            gameBoard.setVisibility(View.GONE);
                            settings.setVisibility(View.VISIBLE);

                        }
                    });


                    // start the animation
                    anim.start();
                }
            } else {
                if (gameBoard.getVisibility() == View.VISIBLE) {
                    YoYo.with(Techniques.SlideOutRight)
                            .duration(250)
                            .interpolate(new AccelerateInterpolator())
                            .withListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                                    gameBoard.setVisibility(View.GONE);
                                    YoYo.with(Techniques.SlideInLeft).duration(250).interpolate(new DecelerateInterpolator()).withListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
                                            settings.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

                                        }
                                    }).playOn(settings);
                                }

                                @Override
                                public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

                                }
                            })
                            .playOn(gameBoard);
                } else {
                    YoYo.with(Techniques.SlideOutLeft)
                            .duration(250)
                            .interpolate(new AccelerateInterpolator())
                            .withListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                                    settings.setVisibility(View.GONE);
                                    YoYo.with(Techniques.SlideInRight)
                                            .duration(250)
                                            .interpolate(new DecelerateInterpolator())
                                            .withListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
                                                @Override
                                                public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
                                                    gameBoard.setVisibility(View.VISIBLE);
                                                }

                                                @Override
                                                public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {

                                                }

                                                @Override
                                                public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

                                                }
                                            }).playOn(gameBoard);
                                }

                                @Override
                                public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

                                }
                            }).playOn(settings);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inject select views from activity_main.xml
        ButterKnife.inject(this);

        //Init simple key-value store. It does not work as intended though.
        Hawk.init(this);

        boardLayout = findViewById(R.id.boardLayout);

        //Check for config changes.
        if (savedInstanceState == null) {
            startNewGame();
            setupSettings();

        }

        btnSettings.setOnClickListener(settingsBtnListener);
    }

    // A new game starts when the app launches, and when the player configuration or difficulty is
    // changed in the settings.
    private void startNewGame() {
        if (Hawk.get(PREF_PLAYER_ONE, LOCAL).equals(LOCAL)) {
            playerOne = new LocalPlayer(Hawk.get(PREF_PLAYER_ONE_NAME, "Archer"));
            Hawk.put(PREF_PLAYER_ONE, LOCAL);

        } else {
            playerOne = new AiPlayer(Hawk.get(PREF_PLAYER_ONE_NAME, "Hal"), Hawk.get(PREF_DIFFICULTY, true));
            Hawk.put(PREF_PLAYER_ONE, AI);
        }

        if (Hawk.get(PREF_PLAYER_TWO, AI).equals(LOCAL)) {
            playerTwo = new LocalPlayer(Hawk.get(PREF_PLAYER_TWO_NAME, "Ray"));
            Hawk.put(PREF_PLAYER_TWO, LOCAL);
        } else {
            playerTwo = new AiPlayer(Hawk.get(PREF_PLAYER_TWO_NAME, "Jarvis"), Hawk.get(PREF_DIFFICULTY, true));
            Hawk.put(PREF_PLAYER_TWO, AI);
        }
        playerOne.setPlayerId(Cell.CellState.PLAYER_ONE);
        playerTwo.setPlayerId(Cell.CellState.PLAYER_TWO);
        playerOne.savePlayerName();
        playerTwo.savePlayerName();

        game = new TicTacToe(this, playerOne, playerTwo, mTTTGameCallback);
        game.setRetryButton(btnRetry);
        game.setBoard(boardLayout);
        game.start();
    }

    // Too much responsibility. It registers the click listeners. Resets the state of several Views and
    // can determine whether or not start a new game. Time does not persist for a full restructuring of this method.
    //TODO Break up this method
    private void setupSettings() {

        View preferences = ButterKnife.findById(this, R.id.preferences);

        View prefPlayerOne = ButterKnife.findById(preferences, R.id.prefPlayerOne);
        prefPlayerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(GameActivity.this)
                        .title(getString(R.string.enter_player_one_name))
                        .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if (!TextUtils.isEmpty(input)) {
                                    Hawk.put(PREF_PLAYER_ONE_NAME, input.toString());
                                    startNewGame();
                                }
                                setupSettings();
                            }
                        }).show();
            }
        });

        View prefPlayerTwo = ButterKnife.findById(preferences, R.id.prefPlayerTwo);
        prefPlayerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(GameActivity.this)
                        .title(getString(R.string.enter_player_one_name))
                        .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if (!TextUtils.isEmpty(input)) {
                                    Hawk.put(PREF_PLAYER_TWO_NAME, input.toString());
                                    startNewGame();
                                }
                                setupSettings();
                            }
                        }).show();
            }
        });

        View prefConfig = ButterKnife.findById(preferences, R.id.prefConfig);
        prefConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View pickerRoot = createPicker();
                new MaterialDialog.Builder(GameActivity.this)
                        .title("Choose players")
                        .customView(pickerRoot, false)
                        .positiveText("Done")
                        .negativeText("Cancel")
                        .showListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                String currentPlayerOne = Hawk.get(PREF_PLAYER_ONE);
                                String currentPlayerTwo = Hawk.get(PREF_PLAYER_TWO);

                                StringPicker playerOnePicker = (StringPicker) pickerRoot.findViewById(R.id.playerPicker1);
                                StringPicker playerTwoPicker = (StringPicker) pickerRoot.findViewById(R.id.playerPicker2);

                                playerOnePicker.setCurrent(Arrays.asList(PLAYERS).indexOf(currentPlayerOne));
                                playerTwoPicker.setCurrent(Arrays.asList(PLAYERS).indexOf(currentPlayerTwo));
                            }
                        })
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                StringPicker playerOnePicker = (StringPicker) pickerRoot.findViewById(R.id.playerPicker1);
                                StringPicker playerTwoPicker = (StringPicker) pickerRoot.findViewById(R.id.playerPicker2);

                                Hawk.put(PREF_PLAYER_ONE, playerOnePicker.getCurrentValue());
                                Hawk.put(PREF_PLAYER_TWO, playerTwoPicker.getCurrentValue());

                                setupSettings();
                                startNewGame();
                                super.onPositive(dialog);
                            }
                        }).show();
            }
        });

        TextView changePlayerOneTitle = ButterKnife.findById(preferences, R.id.changePlayerOneTitle);
        TextView changePlayerTwoTitle = ButterKnife.findById(preferences, R.id.changePlayerTwoTitle);
        TextView configSummary = ButterKnife.findById(preferences, R.id.configSummary);
        Switch changeDifficultySwitch = ButterKnife.findById(preferences, R.id.changeDifficultySwitch);

        setDifficulty(changeDifficultySwitch);
        setPlayerOneNameTitle(changePlayerOneTitle);
        setPlayerTwoNameTitle(changePlayerTwoTitle);
        setConfigSummary(configSummary);

    }

    private void incrementPlayerScore(Player player) {
        setPlayerScore(player, getPlayerScore(player) + 1);
    }

    private void setPlayerScore(Player player, int score) {
        player.setPlayerScore(score);
        if (player.equals(playerOne)) {
            playerOneScore.setText(String.valueOf(score));
        } else if (player.equals(playerTwo)) {
            playerTwoScore.setText(String.valueOf(score));
        }
    }

    private int getPlayerScore(Player player) {
        return player.getPlayerScore();
    }

    private void setDifficulty(Switch difficulty) {
        difficulty.setChecked(Hawk.get(PREF_DIFFICULTY, true));
        difficulty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Hawk.put(PREF_DIFFICULTY, isChecked);
                startNewGame();
            }
        });

    }

    private View createPicker() {
        final LinearLayout pickerRoot = (LinearLayout) this.getLayoutInflater().inflate(R.layout.picker, null);

        final StringPicker seasonPicker = (StringPicker) pickerRoot.findViewById(R.id.playerPicker1);
        seasonPicker.setValues(PLAYERS);

        final StringPicker yearPicker = (StringPicker) pickerRoot.findViewById(R.id.playerPicker2);
        yearPicker.setValues(PLAYERS);

        return pickerRoot;
    }

    private void setPlayerOneNameTitle(TextView playerOneNameTitle) {
        playerOneNameTitle.setText(playerOne.getPlayerName());
        playerOneTitle.setText(playerOne.getPlayerName());
    }

    private void setPlayerTwoNameTitle(TextView playerTwoNameTitle) {
        playerTwoNameTitle.setText(playerTwo.getPlayerName());
        playerTwoTitle.setText(playerTwo.getPlayerName());
    }

    private void setConfigSummary(TextView configSummary) {
        configSummary.setText(Hawk.get(PREF_PLAYER_ONE, playerOne.getPlayerName()) + " vs " +
                Hawk.get(PREF_PLAYER_TWO, playerTwo.getPlayerName()));
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
        setupSettings();

        game = new TicTacToe(this, playerOne, playerTwo, mTTTGameCallback);
        game.setRetryButton(btnRetry);
        game.setBoard(boardLayout);

        setPlayerScore(playerOne, getPlayerScore(playerOne));
        setPlayerScore(playerTwo, getPlayerScore(playerTwo));

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
    }
}
