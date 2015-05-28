package com.tevinjeffrey.tictactoe.settings;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import static com.tevinjeffrey.tictactoe.TTTApp.*;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.orhanobut.hawk.Hawk;
import com.tevinjeffrey.stringpicker.StringPicker;
import com.tevinjeffrey.tictactoe.R;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingsPanel {

    @InjectView(R.id.player1Title)
    TextView mPlayerOneTitle;
    @InjectView(R.id.player2Title)
    TextView mPlayerTwoTitle;
    @InjectView(R.id.player1Score)
    TextView mPlayerOneScore;
    @InjectView(R.id.player2Score)
    TextView mPlayerTwoScore;
    @InjectView(R.id.stateDisplay)
    TextView mStateDisplay;
    @InjectView(R.id.retry)
    Button btnRetry;
    @InjectView(R.id.boardLayout)
    ViewGroup mBoardLayout;
    @InjectView(R.id.btn_settings)
    ImageView mSettings;
    @InjectView(R.id.preferencesLayout)
    View mPreferencesLayout;


    private final Activity mActivity;
    private SettingsListener mSettingsListener;
    private boolean mSettingsOpen = false;

    public SettingsPanel(Activity activity) {
        this.mActivity = activity;
        ButterKnife.inject(this, mActivity);
    }

    public void setUp() {
        setPrefDefaultValues();

        mSettings.setOnClickListener(settingsBtnListener);

        View preferences = ButterKnife.findById(mActivity, R.id.preferences);
        setupSettingsViews(preferences);
        setupSettingsListeners(preferences);
    }

    public void setSettingsListener(SettingsListener mSettingsListener) {
        this.mSettingsListener = mSettingsListener;
    }

    private void setPrefDefaultValues() {
        if (!Hawk.contains(PREF_IMPOSSIBLE_DIFFICULTY))
            Hawk.put(PREF_IMPOSSIBLE_DIFFICULTY, true);

        if (!Hawk.contains(PREF_PLAYER_ONE_NAME))
            Hawk.put(PREF_PLAYER_ONE_NAME, mActivity.getString(R.string.default_player_one_name));

        if (!Hawk.contains(PREF_PLAYER_TWO_NAME))
            Hawk.put(PREF_PLAYER_TWO_NAME, mActivity.getString(R.string.default_player_two_name));

        if (!Hawk.contains(PREF_PLAYER_ONE_SCORE))
            Hawk.put(PREF_PLAYER_ONE_SCORE, 0);

        if (!Hawk.contains(PREF_PLAYER_TWO_SCORE))
            Hawk.put(PREF_PLAYER_TWO_SCORE, 0);

        if (!Hawk.contains(PREF_PLAYER_ONE))
            Hawk.put(PREF_PLAYER_ONE, LOCAL);

        if (!Hawk.contains(PREF_PLAYER_TWO))
            Hawk.put(PREF_PLAYER_TWO, AI);

        if (!Hawk.contains(FLAG_REST_GAME))
            Hawk.put(FLAG_REST_GAME, false);
    }

    // Too much responsibility. It registers the click listeners. Resets the state of several Views and
    // can determine whether or not start a new game. Time does not persist for a full restructuring of this method.
    private void setupSettingsViews(View preferences) {

        //View to be manipulated
        TextView changePlayerOneTitle = ButterKnife.findById(preferences, R.id.changePlayerOneTitle);
        TextView changePlayerTwoTitle = ButterKnife.findById(preferences, R.id.changePlayerTwoTitle);
        TextView configSummary = ButterKnife.findById(preferences, R.id.configSummary);
        Switch changeDifficultySwitch = ButterKnife.findById(preferences, R.id.changeDifficultySwitch);

        //Sets the player one name in the settings panel.
        changePlayerOneTitle.setText(Hawk.get(PREF_PLAYER_ONE_NAME, ""));

        //Sets the player two name in the settings panel.
        changePlayerTwoTitle.setText(Hawk.get(PREF_PLAYER_TWO_NAME, ""));

        //Sets the config summary in the settings panel
        configSummary.setText(Hawk.get(PREF_PLAYER_ONE) + " vs " +
                Hawk.get(PREF_PLAYER_TWO));

        //Sets the config summary in the settings panel
        changeDifficultySwitch.setChecked(Hawk.get(PREF_IMPOSSIBLE_DIFFICULTY, true));

    }

    private void setupSettingsListeners(View preferences) {
        View prefPlayerOne = ButterKnife.findById(preferences, R.id.prefPlayerOne);
        View prefPlayerTwo = ButterKnife.findById(preferences, R.id.prefPlayerTwo);
        View prefConfig = ButterKnife.findById(preferences, R.id.prefConfig);
        Switch changeDifficultySwitch = ButterKnife.findById(preferences, R.id.changeDifficultySwitch);

        prefPlayerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mActivity)
                        .title(mActivity.getString(R.string.enter_player_one_name))
                        .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if (!TextUtils.isEmpty(input)) {
                                    Hawk.put(PREF_PLAYER_ONE_NAME, input.toString());
                                    notifySettingChanged(SettingsEnum.PLAYER_ONE);
                                }
                            }
                        }).show();
            }
        });

        prefPlayerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mActivity)
                        .title(mActivity.getString(R.string.enter_player_two_name))
                        .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if (!TextUtils.isEmpty(input)) {
                                    Hawk.put(PREF_PLAYER_TWO_NAME, input.toString());
                                    notifySettingChanged(SettingsEnum.PLAYER_TWO);
                                }
                            }
                        }).show();
            }
        });

        prefConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View pickerRoot = createPicker();
                new MaterialDialog.Builder(mActivity)
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

                                notifySettingChanged(SettingsEnum.CONFIGURATION);
                                Hawk.put(FLAG_REST_GAME, true);

                                super.onPositive(dialog);
                            }
                        }).show();
            }
        });

        //Sets the listener for the difficulty setting in the settings panel
        changeDifficultySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (Hawk.get(PREF_IMPOSSIBLE_DIFFICULTY, false) != isChecked) {
                    Hawk.put(FLAG_REST_GAME, true);
                    Hawk.put(PREF_IMPOSSIBLE_DIFFICULTY, isChecked);
                    notifySettingChanged(SettingsEnum.DIFFICULTY);
                }
            }
        });
    }

    private View createPicker() {
        final LinearLayout pickerRoot = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.picker, null);

        final StringPicker seasonPicker = (StringPicker) pickerRoot.findViewById(R.id.playerPicker1);
        seasonPicker.setValues(PLAYERS);

        final StringPicker yearPicker = (StringPicker) pickerRoot.findViewById(R.id.playerPicker2);
        yearPicker.setValues(PLAYERS);

        return pickerRoot;
    }

    private void notifySettingChanged(SettingsEnum setting) {
        setUp();
        if (mSettingsListener != null) {
            mSettingsListener.onSettingsChanged(setting);
        }
    }

    private void notifyReset() {
        resetScores();
        if (mSettingsListener != null) {
            mSettingsListener.onReset();
        }
    }

    private void resetScores() {
        Hawk.put(PREF_PLAYER_ONE_SCORE, 0);
        Hawk.put(PREF_PLAYER_TWO_SCORE, 0);

    }

    private void notifySettingsOpen(boolean settingsOpen) {
        mSettingsOpen = settingsOpen;
        if (settingsOpen) {
            setUp();
        } else {
            if (Hawk.get(FLAG_REST_GAME, false)) {
                Hawk.put(FLAG_REST_GAME, false);
                notifyReset();
            }
        }
    }

    // Animates between the game board and the settings. For LOLLIPOP and above it uses the circular
    // reveal animation. Else it simply slide the two views in and out.
    private final View.OnClickListener settingsBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final View gameBoard = mBoardLayout;
            final View settings = mPreferencesLayout;

            animatePanelChange(gameBoard, settings, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    //hides some of the UI when settings is open.
                    if(gameBoard.getVisibility() == View.GONE) {
                        notifySettingsOpen(true);
                        displayUi(View.GONE);
                        disableUi(true);
                    } else {
                        notifySettingsOpen(false);
                        displayUi(View.VISIBLE);
                        disableUi(false);
                    }
                }
            });
        }

        private void displayUi(int visible) {
            btnRetry.setVisibility(visible);
            mPlayerOneScore.setVisibility(visible);
            mPlayerTwoScore.setVisibility(visible);
            mPlayerOneTitle.setVisibility(visible);
            mPlayerTwoTitle.setVisibility(visible);
            mStateDisplay.setVisibility(visible);
        }

        //For reasons unknown a view with visibility "GONE" is not enough to prevent
        // clicks from getting through
        private void disableUi(boolean visible) {
            mBoardLayout.setEnabled(!visible);
            mPreferencesLayout.setEnabled(visible);
        }


        private void animatePanelChange(final View gameBoard, final View settings, final AnimatorListenerAdapter listener) {
            //Separate animations for android versions less than Lollipop.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mSettingsOpen) {
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

                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            listener.onAnimationEnd(null);
                        }
                    });
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
                            listener.onAnimationEnd(null);
                        }
                    });


                    // start the animation
                    anim.start();
                }
            } else {
                if (!mSettingsOpen) {
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
                                            listener.onAnimationEnd(null);
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
                                                    listener.onAnimationEnd(null);
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
}
